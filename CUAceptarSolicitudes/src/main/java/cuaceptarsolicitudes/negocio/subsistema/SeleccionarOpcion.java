package cuaceptarsolicitudes.negocio.subsistema;

import cuaceptarsolicitudes.dtos.SolicitudDTO;
import cuaceptarsolicitudes.negocio.adaptadores.AdaptadorSolicitud;
import cuaceptarsolicitudes.negocio.BO.SolicitudBO;
import cuaceptarsolicitudes.negocio.BO.ISolicitudBO;
import conexion.ConexionMongoDB;
import daos.SolicitudAdopcionDAO;
import daos.MascotaDAO;
import daos.UsuarioDAO;
import daos.CitaDAO;
import entities.SolicitudAdopcion;
import entities.Mascota;
import entities.Usuario;
import entities.Cita;
import infraestructura.dto.CorreoDTO;
import infraestructura.sistemacorreo.FachadaCorreo;
import infraestructura.sistemacorreo.ISistemaCorreo;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del subsistema de selección de opciones para solicitudes.
 * Coordina entre DAOs, BOs, adaptadores e infraestructura (correo).
 * 
 * Operaciones:
 * - Aceptar: Acepta la cita, marca mascota como adoptada, libera usuario, envía
 * correo
 * - Rechazar: Cancela solicitud, libera mascota y usuario, envía correo
 * - Modificar: Pone en pendiente, notifica usuario por correo
 */
public class SeleccionarOpcion implements ISeleccionarOpcion {

    private final SolicitudAdopcionDAO solicitudDAO;
    private final MascotaDAO mascotaDAO;
    private final UsuarioDAO usuarioDAO;
    private final CitaDAO citaDAO;
    private final ISolicitudBO solicitudBO;
    private final ISistemaCorreo sistemaCorreo;

    public SeleccionarOpcion() {
        this.solicitudDAO = new SolicitudAdopcionDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.mascotaDAO = new MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.usuarioDAO = new UsuarioDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.citaDAO = new CitaDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.solicitudBO = new SolicitudBO();
        this.sistemaCorreo = new FachadaCorreo();
    }

    // Constructor con inyección de dependencias (para testing)
    public SeleccionarOpcion(SolicitudAdopcionDAO solicitudDAO, MascotaDAO mascotaDAO,
            UsuarioDAO usuarioDAO, ISolicitudBO solicitudBO, ISistemaCorreo sistemaCorreo) {
        this.solicitudDAO = solicitudDAO;
        this.mascotaDAO = mascotaDAO;
        this.usuarioDAO = usuarioDAO;
        this.citaDAO = new CitaDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.solicitudBO = solicitudBO;
        this.sistemaCorreo = sistemaCorreo;
    }

    @Override
    public List<SolicitudDTO> obtenerTodasLasSolicitudes() {
        List<SolicitudAdopcion> solicitudes = solicitudDAO.buscarTodas();
        List<SolicitudDTO> solicitudesDTO = new ArrayList<>();

        for (SolicitudAdopcion solicitud : solicitudes) {
            // Obtener usuario y mascota para enriquecer el DTO
            Usuario usuario = null;
            Mascota mascota = null;

            if (solicitud.getIdUsuario() != null) {
                usuario = usuarioDAO.buscarPorId(solicitud.getIdUsuario());
            }

            if (solicitud.getIdMascota() != null) {
                mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
            }

            SolicitudDTO dto = AdaptadorSolicitud.entidadADTO(solicitud, usuario, mascota);

            // Buscar la cita para obtener la fecha
            // Primero intentar por idCita, si no existe buscar por usuario+mascota
            try {
                Cita citaEncontrada = null;

                // Método 1: Buscar por idCita si existe
                if (solicitud.getIdCita() != null) {
                    citaEncontrada = citaDAO.buscarPorId(solicitud.getIdCita());
                }

                // Método 2: Si no hay idCita o no se encontró, buscar por usuario+mascota
                if (citaEncontrada == null && solicitud.getIdUsuario() != null && solicitud.getIdMascota() != null) {
                    java.util.List<Cita> citasUsuario = citaDAO.buscarPorUsuario(solicitud.getIdUsuario());
                    for (Cita c : citasUsuario) {
                        if (c.getIdMascota() != null && c.getIdMascota().equals(solicitud.getIdMascota())) {
                            citaEncontrada = c;
                            dto.setIdCita(c.getId().toString());
                            break;
                        }
                    }
                }

                // Setear la fecha de la cita si se encontró
                if (citaEncontrada != null && citaEncontrada.getFechaHora() != null) {
                    dto.setFechaCita(citaEncontrada.getFechaHora());
                }
            } catch (Exception e) {
                System.err.println("Error al obtener cita: " + e.getMessage());
            }

            solicitudesDTO.add(dto);
        }

        return solicitudesDTO;
    }

    @Override
    public boolean aceptarSolicitud(String idSolicitud, String idAdmin) throws Exception {
        if (!solicitudBO.puedeAceptarSolicitud(idSolicitud)) {
            throw new IllegalArgumentException("La solicitud no puede ser aceptada");
        }

        // 1. Buscar solicitud
        SolicitudAdopcion solicitud = solicitudDAO.buscarPorId(new ObjectId(idSolicitud));
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no encontrada");
        }

        // 2. Actualizar estado de la solicitud
        solicitud.setEstado("APROBADA");
        solicitudDAO.actualizar(solicitud);

        // 3. Marcar mascota como adoptada
        if (solicitud.getIdMascota() != null) {
            Mascota mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
            if (mascota != null) {
                mascota.setDisponible(false);
                mascota.setEstado("adoptada");
                mascotaDAO.actualizar(mascota);
                System.out.println(" Mascota " + mascota.getNombre() + " marcada como adoptada");
            }
        }

        // 4. Obtener usuario para enviar correo
        Usuario usuario = null;
        if (solicitud.getIdUsuario() != null) {
            usuario = usuarioDAO.buscarPorId(solicitud.getIdUsuario());
        }

        // 5. Enviar correo de aceptación
        if (usuario != null && usuario.getInfoPersonal() != null) {
            enviarCorreoAceptacion(usuario, solicitud);
        }

        System.out.println("Solicitud aceptada exitosamente");
        return true;
    }

    @Override
    public boolean rechazarSolicitud(String idSolicitud, String idAdmin) throws Exception {
        if (!solicitudBO.puedeRechazarSolicitud(idSolicitud)) {
            throw new IllegalArgumentException("La solicitud no puede ser rechazada");
        }

        // 1. Buscar solicitud
        SolicitudAdopcion solicitud = solicitudDAO.buscarPorId(new ObjectId(idSolicitud));
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no encontrada");
        }

        // 2. Actualizar estado de la solicitud
        solicitud.setEstado("RECHAZADA");
        solicitudDAO.actualizar(solicitud);

        // 3. Liberar mascota (hacerla disponible nuevamente)
        if (solicitud.getIdMascota() != null) {
            Mascota mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
            if (mascota != null) {
                mascota.setDisponible(true);
                mascota.setEstado("disponible");
                mascotaDAO.actualizar(mascota);
                System.out.println("Mascota " + mascota.getNombre() + " liberada (disponible)");
            }
        }

        // 4. Obtener usuario para enviar correo
        Usuario usuario = null;
        if (solicitud.getIdUsuario() != null) {
            usuario = usuarioDAO.buscarPorId(solicitud.getIdUsuario());
        }

        // 5. Enviar correo de rechazo
        if (usuario != null && usuario.getInfoPersonal() != null) {
            enviarCorreoRechazo(usuario, solicitud);
        }

        System.out.println(" Solicitud rechazada exitosamente");
        return true;
    }

    @Override
    public boolean modificarSolicitud(String idSolicitud, String idAdmin, String razonModificacion) throws Exception {
        if (!solicitudBO.puedeModificarSolicitud(idSolicitud)) {
            throw new IllegalArgumentException("La solicitud no puede ser modificada");
        }

        // 1. Buscar solicitud
        SolicitudAdopcion solicitud = solicitudDAO.buscarPorId(new ObjectId(idSolicitud));
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no encontrada");
        }

        // 2. Actualizar estado a pendiente de modificación
        solicitud.setEstado("Requiere Modificación");
        solicitud.setMensajeCorreccion(razonModificacion);
        solicitudDAO.actualizar(solicitud);

        // 3. Obtener usuario para enviar correo
        Usuario usuario = null;
        if (solicitud.getIdUsuario() != null) {
            usuario = usuarioDAO.buscarPorId(solicitud.getIdUsuario());
        }

        // 4. Enviar correo notificando la modificación requerida
        if (usuario != null && usuario.getInfoPersonal() != null) {
            enviarCorreoModificacion(usuario, solicitud, razonModificacion);
        }

        System.out.println("Solicitud marcada como pendiente de modificación");
        return true;
    }

    // --- Métodos privados para envío de correos ---

    private void enviarCorreoAceptacion(Usuario usuario, SolicitudAdopcion solicitud) {
        try {
            String destinatario = usuario.getInfoPersonal().getCorreo();
            String nombreUsuario = usuario.getInfoPersonal().getNombre();

            Mascota mascota = null;
            String nombreMascota = "tu nueva mascota";
            if (solicitud.getIdMascota() != null) {
                mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
                if (mascota != null) {
                    nombreMascota = mascota.getNombre();
                }
            }

            String asunto = "Tu solicitud de adopción ha sido aceptada";

            // Obtener fecha de cita si existe
            String infoCita = "";
            if (solicitud.getFechaSolicitud() != null) {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm");
                infoCita = "\n\nFecha de tu cita: " + solicitud.getFechaSolicitud().format(formatter);
            }

            String mensaje = String.format(
                    "Hola %s,\n\n" +
                            "¡Tenemos excelentes noticias! Tu solicitud de adopción para %s ha sido ACEPTADA.%s\n\n" +
                            "Por favor, preséntate en la fecha y hora indicada en nuestra oficina.\n\n" +
                            "Gracias por dar un hogar a uno de nuestros amigos peludos.\n\n" +
                            "Atentamente,\n" +
                            "El equipo de La Vida es Bella",
                    nombreUsuario, nombreMascota, infoCita);

            CorreoDTO correo = new CorreoDTO(destinatario, asunto, mensaje);
            sistemaCorreo.enviarCorreo(correo);
            System.out.println("Correo de aceptación enviado a: " + destinatario);
        } catch (Exception e) {
            System.err.println("Error al enviar correo de aceptación: " + e.getMessage());
        }
    }

    private void enviarCorreoRechazo(Usuario usuario, SolicitudAdopcion solicitud) {
        try {
            String destinatario = usuario.getInfoPersonal().getCorreo();
            String nombreUsuario = usuario.getInfoPersonal().getNombre();

            Mascota mascota = null;
            String nombreMascota = "la mascota";
            if (solicitud.getIdMascota() != null) {
                mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
                if (mascota != null) {
                    nombreMascota = mascota.getNombre();
                }
            }

            String asunto = "Actualización sobre tu solicitud de adopción";
            String mensaje = String.format(
                    "Hola %s,\n\n" +
                            "Lamentamos informarte que tu solicitud de adopción para %s no ha sido aprobada en esta ocasión.\n\n"
                            +
                            "Te invitamos a revisar nuestro catálogo de mascotas disponibles y solicitar la adopción de otra mascota.\n\n"
                            +
                            "Si tienes alguna pregunta, no dudes en contactarnos.\n\n" +
                            "Atentamente,\n" +
                            "El equipo de La Vida es Bella",
                    nombreUsuario, nombreMascota);

            CorreoDTO correo = new CorreoDTO(destinatario, asunto, mensaje);
            sistemaCorreo.enviarCorreo(correo);
            System.out.println("Correo de rechazo enviado a: " + destinatario);
        } catch (Exception e) {
            System.err.println("Error al enviar correo de rechazo: " + e.getMessage());
        }
    }

    private void enviarCorreoModificacion(Usuario usuario, SolicitudAdopcion solicitud, String razonModificacion) {
        try {
            String destinatario = usuario.getInfoPersonal().getCorreo();
            String nombreUsuario = usuario.getInfoPersonal().getNombre();

            Mascota mascota = null;
            String nombreMascota = "la mascota";
            if (solicitud.getIdMascota() != null) {
                mascota = mascotaDAO.buscarPorId(solicitud.getIdMascota());
                if (mascota != null) {
                    nombreMascota = mascota.getNombre();
                }
            }

            String asunto = "Se requiere modificación en tu solicitud de adopción";
            String mensaje = String.format(
                    "Hola %s,\n\n" +
                            "Tu solicitud de adopción para %s requiere algunas modificaciones.\n\n" +
                            "Razón: %s\n\n" +
                            "Por favor, ingresa al sistema y actualiza tu solicitud con la información requerida.\n\n" +
                            "Si tienes alguna pregunta, no dudes en contactarnos.\n\n" +
                            "Atentamente,\n" +
                            "El equipo de La Vida es Bella",
                    nombreUsuario, nombreMascota, razonModificacion);

            CorreoDTO correo = new CorreoDTO(destinatario, asunto, mensaje);
            sistemaCorreo.enviarCorreo(correo);
            System.out.println("Correo de modificación enviado a: " + destinatario);
        } catch (Exception e) {
            System.err.println("Error al enviar correo de modificación: " + e.getMessage());
        }
    }
}
