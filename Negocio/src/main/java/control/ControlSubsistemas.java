/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOS.CitaDTO;
import DTOS.CitaDisponibleDTO;
import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import ObjetoNegocio.CitaDisponibleBO;
import ObjetoNegocio.ICitaDisponibleBO;
import negocio.adopcionesdto.*;
import negocio.subsistemas.iniciosesion.FachadaInicioSesion;
import negocio.subsistemas.iniciosesion.IInicioSesion;
import negocio.subsistemas.mascotas.FachadaMascotas;
import negocio.subsistemas.mascotas.IMascotas;
import DTOS.MascotaDTO;
import infraestructura.dto.CorreoDTO;
import infraestructura.sistemacorreo.FachadaCorreo;
import infraestructura.sistemacorreo.ISistemaCorreo;

import java.util.List;

/**
 * Fachada principal para la Capa de Presentación.
 */
public class ControlSubsistemas {

    private IInicioSesion subsistemaInicioSesion;
    private IMascotas subsistemaMascotas;
    private ControlAdopcion controlAdopcion;
    private ControlCita controlCita;
    private ICitaDisponibleBO citaDisponibleBO;
    private ISistemaCorreo subsistemaCorreo;

    public ControlSubsistemas() {
        // Inicialización de subsistemas y controles
        this.subsistemaInicioSesion = new FachadaInicioSesion();
        this.subsistemaMascotas = new FachadaMascotas();
        this.controlAdopcion = new ControlAdopcion();
        this.controlCita = new ControlCita();
        this.citaDisponibleBO = new CitaDisponibleBO();
        this.subsistemaCorreo = new FachadaCorreo();
    }

    // --- MÉTODOS PARA INICIO DE SESIÓN ---
    public UsuarioDTO validarLogin(String correo, String password) throws Exception {
        return subsistemaInicioSesion.iniciarSesion(correo, password);
    }

    public void registrarUsuario(UsuarioDTO usuario) throws Exception {
        subsistemaInicioSesion.registrarUsuario(usuario);
    }

    public void actualizarUsuario(UsuarioDTO usuario) throws Exception {
        subsistemaInicioSesion.actualizarUsuario(usuario);
    }

    public void InicializarAdminUser() {
        try {
            // Intentar buscar al admin
            try {
                UsuarioDTO admin = subsistemaInicioSesion.iniciarSesion("admin@gmail.com", "admin");
                if (admin != null) {
                    return;
                }
            } catch (Exception e) {

            }

            UsuarioDTO newAdmin = new UsuarioDTO();
            newAdmin.setContrasena("admin");

            DTOS.InfoPersonalDTO info = new DTOS.InfoPersonalDTO();
            info.setNombre("Administrador");
            info.setCorreo("admin@gmail.com");

            newAdmin.setInfoPersonal(info);

            subsistemaInicioSesion.registrarUsuario(newAdmin);
            System.out.println("Admin creado correctamente");

        } catch (Exception e) {
            System.err.println("No se pudo inicializar el usuario Admin: " + e.getMessage());
        }
    }

    // --- MÉTODOS PARA MASCOTAS ---
    public MascotaDTO obtenerMascotaPorId(String id) {
        try {
            return subsistemaMascotas.buscarMascotaPorId(id);
        } catch (Exception e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
            return null;
        }
    }

    // --- MÉTODOS PARA CITAS DISPONIBLES ---
    public List<CitaDisponibleDTO> obtenerCitasDisponibles() {
        return citaDisponibleBO.obtenerCitasDisponibles();
    }

    public boolean reservarCita(String idCita, String idUsuario) {
        return citaDisponibleBO.reservarCita(idCita, idUsuario);
    }

    public boolean verificarDisponibilidadCita(String idCita) {
        return citaDisponibleBO.verificarDisponibilidad(idCita);
    }

    /**
     * Libera una cita que fue reservada temporalmente
     * 
     * @param idCita ID de la cita a liberar
     * @return true si se liberó exitosamente
     */
    public boolean liberarCita(String idCita) {
        return citaDisponibleBO.liberarCita(idCita);
    }

    // --- MÉTODOS PARA EL FLUJO DE ADOPCIÓN ---
    public void procesarSolicitudCompleta(SolicitudAdopcionDTO solicitud, CitaDTO cita) throws Exception {
        // 0. IMPORTANTE: Guardar el ID de la cita en la solicitud para poder liberarla
        // después
        if (cita != null && cita.getId() != null) {
            solicitud.setIdCita(cita.getId());
            System.out.println("→ ID de cita guardado en solicitud: " + cita.getId());
        }

        // 1. Guardar la solicitud de adopción (Negocio -> Persistencia)
        controlAdopcion.crearSolicitud(solicitud);

        // 2. Actualizar estado de la mascota a NO DISPONIBLE
        if (solicitud.getMascota() != null) {
            MascotaDTO mascota = solicitud.getMascota();
            mascota.setDisponible(false);
            subsistemaMascotas.actualizarMascota(mascota);
            System.out.println("Mascota " + mascota.getId() + " marcada como NO disponible.");
        }

        // 3. Agendar la cita y notificar (Negocio -> Infraestructura)
        // Asumiendo que obtenemos el correo del usuario del DTO
        String correoUsuario = "usuario@ejemplo.com";
        if (solicitud != null && solicitud.getUsuario() != null && solicitud.getUsuario().getInfoPersonal() != null) {
            correoUsuario = solicitud.getUsuario().getInfoPersonal().getCorreo();
        }
        controlCita.agendarCita(cita, correoUsuario);

        // 4. Enviar correo de confirmación
        enviarCorreoConfirmacion(solicitud);

        System.out.println("Flujo completo de solicitud finalizado exitosamente.");
    }

    // --- MÉTODOS PARA GESTIÓN DE SOLICITUDES ---

    /**
     * Busca todas las solicitudes de un usuario
     */
    public List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(String idUsuario) throws Exception {
        return controlAdopcion.buscarSolicitudesPorUsuario(idUsuario);
    }

    /**
     * Actualiza el estado de una solicitud
     */
    public void actualizarEstadoSolicitud(String idSolicitud, String nuevoEstado) throws Exception {
        controlAdopcion.actualizarEstadoSolicitud(idSolicitud, nuevoEstado);
    }

    public SolicitudAdopcionDTO buscarSolicitudPorId(String idSolicitud) throws Exception {
        return controlAdopcion.buscarSolicitudPorId(idSolicitud);
    }

    public void cancelarCitaDeSolicitud(String idSolicitud) throws Exception {
        // 1. Obtener la solicitud para saber qué mascota liberar
        SolicitudAdopcionDTO solicitud = controlAdopcion.buscarSolicitudPorId(idSolicitud);

        if (solicitud != null && solicitud.getMascota() != null) {
            // 2. Liberar la mascota (ponerla disponible)
            MascotaDTO mascota = solicitud.getMascota();
            mascota.setDisponible(true);
            subsistemaMascotas.actualizarMascota(mascota);
            System.out.println("Mascota " + mascota.getId() + " liberada (disponible) por cancelación de cita.");
        }

        // 2.5. Liberar la cita (marcarla como disponible)
        if (solicitud != null && solicitud.getIdCita() != null && !solicitud.getIdCita().isEmpty()) {
            String idCita = solicitud.getIdCita();
            if (citaDisponibleBO.liberarCita(idCita)) {
                System.out.println("✓ Cita " + idCita + " liberada (disponible) por cancelación.");
            } else {
                System.err.println("⚠ No se pudo liberar la cita: " + idCita);
            }
        }

        // 3. Actualizar estado de la solicitud
        controlAdopcion.actualizarEstadoSolicitud(idSolicitud, "Cita Cancelada");
    }

    public void cancelarSolicitudAdopcion(String idSolicitud) throws Exception {
        // 1. Obtener la solicitud para saber qué mascota liberar
        SolicitudAdopcionDTO solicitud = controlAdopcion.buscarSolicitudPorId(idSolicitud);

        if (solicitud != null && solicitud.getMascota() != null) {
            // 2. Liberar la mascota (ponerla disponible)
            MascotaDTO mascota = solicitud.getMascota();
            mascota.setDisponible(true);
            subsistemaMascotas.actualizarMascota(mascota);
            System.out.println("Mascota " + mascota.getId() + " liberada (disponible) por cancelación de solicitud.");
        }

        // 3. Actualizar estado de la solicitud
        controlAdopcion.actualizarEstadoSolicitud(idSolicitud, "Cancelada");
    }

    private void enviarCorreoConfirmacion(SolicitudAdopcionDTO solicitud) {
        try {
            if (solicitud != null && solicitud.getUsuario() != null
                    && solicitud.getUsuario().getInfoPersonal() != null) {
                String destinatario = solicitud.getUsuario().getInfoPersonal().getCorreo();
                String nombreUsuario = solicitud.getUsuario().getInfoPersonal().getNombre();
                String nombreMascota = (solicitud.getMascota() != null) ? solicitud.getMascota().getNombre()
                        : "la mascota";

                String asunto = "Confirmación de Solicitud de Adopción - La Vida es Bella";
                String mensaje = "Hola " + nombreUsuario + ",\n\n" +
                        "Tu solicitud de adopción para " + nombreMascota + " ha sido recibida exitosamente.\n" +
                        "Nos pondremos en contacto contigo pronto para los siguientes pasos.\n\n" +
                        "Gracias por querer dar un hogar a uno de nuestros amigos peludos.\n\n" +
                        "Atentamente,\n" +
                        "El equipo de La Vida es Bella";

                CorreoDTO correo = new CorreoDTO(destinatario, asunto, mensaje);
                subsistemaCorreo.enviarCorreo(correo);
            }
        } catch (Exception e) {
            System.err.println("Error al enviar correo de confirmación: " + e.getMessage());
            // No lanzamos la excepción para no interrumpir el flujo principal si falla el
            // correo
        }
    }
}
