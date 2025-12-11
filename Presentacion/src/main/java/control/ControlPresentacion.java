/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOS.CitaDTO;
import DTOS.CitaDisponibleDTO;
import DTOS.InfoPersonalDTO;
import DTOS.InfoViviendaDTO;
import DTOS.MascotaDTO;
import DTOS.RazonesAntecedentesDTO;
import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import gui.FrmCorreoConfirmacion;
import gui.FrmError;
import gui.flujoAdoptar.FrmInfoPersonal;
import gui.flujoAdoptar.FrmInfoVivienda;
import gui.flujoAdoptar.FrmInformacionMascota;
import gui.flujoAdoptar.FrmMenuPrincipal;
import gui.flujoAdoptar.FrmRazonesAntecedentes;
import gui.flujoAdoptar.MenuMostrarEspecies;

import javax.swing.*;
import java.util.List;

/**
 *
 * @author angel
 */
/**
 * Controlador principal de la capa de presentación.
 * Gestiona la navegación entre pantallas y la comunicación con el subsistema.
 * 
 * @author angel
 */
public class ControlPresentacion {

    private ControlSubsistemas controlSubsistemas;

    // Referencias a las pantallas
    private MenuMostrarEspecies menuMostrarEspecies;
    private FrmInformacionMascota frmInformacionMascota;
    private FrmInfoVivienda frmInfoVivienda;
    private FrmRazonesAntecedentes frmRazonesAntecedentes;
    private FrmInfoPersonal frmInfoPersonal;
    private FrmCorreoConfirmacion frmCorreoConfirmacion;
    private FrmError frmError;
    private FrmMenuPrincipal frmMenuPrincipal;

    // Variables de contexto
    private String idMascotaSeleccionada;
    private String idUsuarioActual;
    private UsuarioDTO usuarioActual;
    private SolicitudAdopcionDTO solicitudActual;
    private CitaDTO citaSeleccionada;

    // Borradores guardados
    private InfoPersonalDTO borradorInfoPersonal;
    private CitaDisponibleDTO borradorCita;

    /**
     * Constructor por defecto.
     * Inicializa el controlador de subsistemas.
     */
    public ControlPresentacion() {
        this.controlSubsistemas = new ControlSubsistemas();
        this.idUsuarioActual = null;
        this.controlSubsistemas.InicializarAdminUser();
    }

    /**
     * Inicia el sistema mostrando el menú principal.
     */
    public void iniciarSistema() {
        mostrarMenuPrincipal();
    }

    /**
     * Muestra la ventana del menú principal.
     */
    public void mostrarMenuPrincipal() {
        if (frmMenuPrincipal == null) {
            frmMenuPrincipal = new FrmMenuPrincipal();
            // Inyectar dependencia del control
            frmMenuPrincipal.setControl(this);
        }
        frmMenuPrincipal.setVisible(true);
    }

    /**
     * Muestra el menú de mascotas en el menú principal.
     */
    public void mostrarMenuMascotas() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInicio();
        }
    }

    /**
     * Procesa la selección de una mascota.
     * 
     * @param idMascota ID de la mascota seleccionada.
     */
    public void procesarSeleccionMascota(String idMascota) {
        this.idMascotaSeleccionada = idMascota;
        mostrarInformacionMascota(idMascota);
    }

    /**
     * Muestra la información detallada de una mascota.
     * 
     * @param idMascota ID de la mascota.
     */
    public void mostrarInformacionMascota(String idMascota) {
        // Buscar la mascota por ID
        MascotaDTO mascota = controlSubsistemas.obtenerMascotaPorId(idMascota);

        // Crear ventana pasando la referencia al control
        frmInformacionMascota = new FrmInformacionMascota(mascota, this);
        frmInformacionMascota.setVisible(true);
    }

    /**
     * Continúa con el flujo de solicitud de adopción.
     */
    public void continuarConSolicitud() {
        cerrarVentana(frmInformacionMascota);
        mostrarInfoPersonal();
    }

    /**
     * Muestra el formulario de información personal.
     */
    public void mostrarInfoPersonal() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoPersonal();
        }
    }

    /**
     * Guarda la información personal del usuario.
     * 
     * @param infoPersonal DTO con la información personal.
     */
    public void guardarInfoPersonal(InfoPersonalDTO infoPersonal) {
        try {
            // Validar que infoPersonal no sea nulo
            if (infoPersonal == null) {
                throw new IllegalArgumentException("La información personal no puede estar vacía");
            }

            // Crear o actualizar el usuario actual
            if (usuarioActual == null) {
                usuarioActual = new UsuarioDTO();
            }
            usuarioActual.setInfoPersonal(infoPersonal);

            // Guardar el usuario actualizado en la base de datos
            if (usuarioActual.getId() != null) {
                try {
                    controlSubsistemas.actualizarUsuario(usuarioActual);
                } catch (Exception e) {
                    System.err.println("Error al actualizar usuario en BD: " + e.getMessage());
                }
            }

            // Crear solicitud si no existe
            if (solicitudActual == null) {
                solicitudActual = new SolicitudAdopcionDTO();
                solicitudActual.setUsuario(usuarioActual);
                // Asignar la mascota seleccionada
                if (idMascotaSeleccionada != null) {
                    MascotaDTO mascota = controlSubsistemas.obtenerMascotaPorId(idMascotaSeleccionada);
                    solicitudActual.setMascota(mascota);
                }
            }

            // La navegación ahora es responsabilidad de la vista (FrmInfoPersonal)
            // para permitir saltar pasos si es necesario

        } catch (Exception e) {
            System.err.println("Error en guardarInfoPersonal: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al guardar información personal: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el formulario de información de vivienda.
     */
    public void mostrarInfoVivienda() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoVivienda();
        }
    }

    /**
     * Guarda la información de vivienda del usuario.
     * 
     * @param infoVivienda DTO con la información de vivienda.
     */
    public void guardarInfoVivienda(InfoViviendaDTO infoVivienda) {
        try {
            // Asegurar que existe usuario y solicitud
            if (usuarioActual == null) {
                usuarioActual = new UsuarioDTO();
            }

            if (solicitudActual == null) {
                solicitudActual = new SolicitudAdopcionDTO();
                solicitudActual.setUsuario(usuarioActual);
            }

            // Guardar información de vivienda en el usuario
            usuarioActual.setInfoVivienda(infoVivienda);

            // Guardar el usuario actualizado en la base de datos
            if (usuarioActual.getId() != null) {
                try {
                    controlSubsistemas.actualizarUsuario(usuarioActual);
                } catch (Exception e) {
                    System.err.println("Error al actualizar usuario en BD: " + e.getMessage());
                }
            }

            // Navegar al siguiente panel
            if (frmMenuPrincipal != null) {
                frmMenuPrincipal.mostrarRazonesAntecedentes();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar información de vivienda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el formulario de razones y antecedentes.
     */
    public void mostrarRazonesAntecedentes() {
        frmRazonesAntecedentes = new FrmRazonesAntecedentes();
        frmRazonesAntecedentes.setVisible(true);
    }

    /**
     * Guarda las razones y antecedentes de la solicitud.
     * 
     * @param razones DTO con las razones y antecedentes.
     */
    public void guardarRazonesAntecedentes(RazonesAntecedentesDTO razones) {
        try {
            // Asegurar que existe solicitud
            if (solicitudActual == null) {
                solicitudActual = new SolicitudAdopcionDTO();
            }

            // Guardar razones en la solicitud
            solicitudActual.setRazones(razones);

            // Navegar al siguiente panel
            if (frmMenuPrincipal != null) {
                frmMenuPrincipal.mostrarInfoResumen();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar razones y antecedentes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra el resumen de la solicitud.
     */
    public void mostrarInfoResumen() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoResumen();
        }
    }

    // --- MÉTODOS PARA CITAS ---
    /**
     * Obtiene la lista de citas disponibles.
     * 
     * @return Lista de DTOs de citas disponibles.
     */
    public java.util.List<DTOS.CitaDisponibleDTO> obtenerCitasDisponibles() {
        return controlSubsistemas.obtenerCitasDisponibles();
    }

    /**
     * Reserva una cita para un usuario.
     * 
     * @param idCita    ID de la cita.
     * @param idUsuario ID del usuario.
     * @return true si la reserva fue exitosa, false en caso contrario.
     */
    public boolean reservarCita(String idCita, String idUsuario) {
        return controlSubsistemas.reservarCita(idCita, idUsuario);
    }

    /**
     * Verifica la disponibilidad de una cita.
     * 
     * @param idCita ID de la cita.
     * @return true si la cita está disponible, false en caso contrario.
     */
    public boolean verificarDisponibilidadCita(String idCita) {
        return controlSubsistemas.verificarDisponibilidadCita(idCita);
    }

    /**
     * Procesa la solicitud completa incluyendo la cita seleccionada.
     * 
     * @param cita DTO de la cita seleccionada.
     */
    public void procesarCita(DTOS.CitaDTO cita) {
        try {
            // Recuperación de estado si solicitudActual es null
            if (solicitudActual == null) {
                solicitudActual = new SolicitudAdopcionDTO();

                if (usuarioActual != null) {
                    solicitudActual.setUsuario(usuarioActual);
                }

                if (idMascotaSeleccionada != null) {
                    MascotaDTO mascota = controlSubsistemas.obtenerMascotaPorId(idMascotaSeleccionada);
                    solicitudActual.setMascota(mascota);
                }
            }

            if (cita != null && cita.getId() != null) {
                System.out.println("→ Intentando reservar cita: " + cita.getId());
                if (!controlSubsistemas.reservarCita(cita.getId(), idUsuarioActual)) {
                    throw new Exception("No se pudo reservar la cita");
                }
            }

            // Asignar fecha de solicitud
            solicitudActual.setFechaSolicitud(java.time.LocalDateTime.now());
            solicitudActual.setEstado("Pendiente");

            // Registrar usuario si es nuevo
            if (usuarioActual.getId() == null) {
                // Asignar contraseña temporal si no tiene
                if (usuarioActual.getContrasena() == null || usuarioActual.getContrasena().isEmpty()) {
                    usuarioActual.setContrasena("temp123");
                }
                controlSubsistemas.registrarUsuario(usuarioActual);
            }

            // Asignar usuario a la solicitud
            solicitudActual.setUsuario(usuarioActual);

            // Guardar solicitud completa
            controlSubsistemas.procesarSolicitudCompleta(solicitudActual, cita);

            // IMPORTANTE: Marcar la cita como ocupada si viene de una selección de cita
            // disponible
            // Aquí asumimos que cita.getFecha() y cita.getHora() coinciden con una cita
            // disponible
            // En una implementación más robusta, pasaríamos el ID de la cita disponible

            limpiarDatosSolicitud();

            // Si todo fue exitoso, mostrar pantalla de confirmación
            mostrarConfirmacion();

        } catch (Exception e) {
            // Si ocurre un error, mostrar pantalla de error
            System.err.println("✗ Error al procesar solicitud: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error: " + e.getMessage());
        }
    }

    private void limpiarDatosSolicitud() {
        solicitudActual = null;
        // NO limpiamos usuarioActual para mantener al usuario logueado
        // y que sus datos estén disponibles para la siguiente solicitud
        idMascotaSeleccionada = null;
    }

    /**
     * Muestra la pantalla de confirmación.
     */
    public void mostrarConfirmacion() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarConfirmacion();

            // Recargar el catálogo de mascotas para que la mascota adoptada ya no aparezca
            frmMenuPrincipal.getMenuMostrarEspecies().cargarMascotas();
        }
    }

    /**
     * Muestra la pantalla de error con el mensaje especificado
     * 
     * @param mensajeError Mensaje de error a mostrar en lblDetalle
     */
    public void mostrarError(String mensajeError) {
        frmError = new FrmError();
        frmError.setControlPresentacion(this);

        // Buscar el label lblDetalle y setear el mensaje
        javax.swing.JLabel lblDetalle = findLabelByName(frmError, "lblDetalle");
        if (lblDetalle != null) {
            lblDetalle.setText(mensajeError);
        }

        frmError.setVisible(true);
    }

    /**
     * Método auxiliar para encontrar un JLabel por nombre en un componente
     */
    private javax.swing.JLabel findLabelByName(java.awt.Container container, String labelName) {
        for (java.awt.Component comp : container.getComponents()) {
            if (comp instanceof javax.swing.JLabel) {
                javax.swing.JLabel label = (javax.swing.JLabel) comp;
                if (labelName.equals(label.getName())) {
                    return label;
                }
            } else if (comp instanceof java.awt.Container) {
                javax.swing.JLabel found = findLabelByName((java.awt.Container) comp, labelName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /**
     * Finaliza el proceso de adopción.
     */
    public void finalizarProceso() {
        if (frmCorreoConfirmacion != null)
            frmCorreoConfirmacion.setVisible(false);
        solicitudActual = null;
        idMascotaSeleccionada = null;
        mostrarMenuPrincipal();
    }

    /**
     * Cancela el proceso de adopción.
     */
    public void cancelarProceso() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea cancelar el proceso de adopción?",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            limpiarContexto();
            mostrarMenuPrincipal();
        }
    }

    private void limpiarContexto() {
        solicitudActual = null;
        idMascotaSeleccionada = null;
        cerrarTodasLasVentanas();
    }

    private void cerrarVentana(JFrame ventana) {
        if (ventana != null) {
            ventana.dispose();
        }
    }

    private void cerrarTodasLasVentanas() {
        if (menuMostrarEspecies != null)
            menuMostrarEspecies.setVisible(false);
        cerrarVentana(frmInformacionMascota);
        if (frmInfoVivienda != null)
            frmInfoVivienda.setVisible(false);
        if (frmRazonesAntecedentes != null)
            frmRazonesAntecedentes.setVisible(false);
        if (frmInfoPersonal != null)
            frmInfoPersonal.setVisible(false);
        if (frmCorreoConfirmacion != null)
            frmCorreoConfirmacion.setVisible(false);
    }

    /**
     * Obtiene el controlador de subsistemas.
     * 
     * @return El controlador de subsistemas.
     */
    public ControlSubsistemas getControlSubsistemas() {
        return controlSubsistemas;
    }

    public String getIdUsuarioActual() {
        return idUsuarioActual;
    }

    /**
     * Valida las credenciales de login del usuario
     * 
     * @param correo   Correo del usuario
     * @param password Contraseña del usuario
     * @return UsuarioDTO si las credenciales son válidas
     * @throws Exception si las credenciales son inválidas
     */

    public UsuarioDTO validarLogin(String correo, String password) throws Exception {
        return controlSubsistemas.validarLogin(correo, password);
    }

    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @param usuario UsuarioDTO con los datos del nuevo usuario
     * @throws Exception si hay algún error en el registro
     */
    public void registrarUsuario(UsuarioDTO usuario) throws Exception {
        controlSubsistemas.registrarUsuario(usuario);
    }

    /**
     * Establece el usuario que ha iniciado sesión
     * Esto permite pre-llenar sus datos en los formularios
     * 
     * @param usuario UsuarioDTO del usuario que inició sesión
     */
    public void setUsuarioLogueado(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
        if (usuario != null && usuario.getId() != null) {
            this.idUsuarioActual = usuario.getId().toString();
        }
    }

    /**
     * Maneja el regreso desde el formulario de registro al inicio de sesión
     * 
     * @param frmRegistro     Ventana de registro a cerrar
     * @param frmInicioSesion Ventana de inicio de sesión a mostrar
     */
    public void regresarDesdeRegistro(javax.swing.JFrame frmRegistro, javax.swing.JFrame frmInicioSesion) {
        if (frmRegistro != null) {
            frmRegistro.dispose();
        }
        if (frmInicioSesion != null) {
            frmInicioSesion.setVisible(true);
        }
    }

    /**
     * Cierra la sesión del usuario actual
     * Limpia todos los datos del usuario y vuelve al inicio de sesión
     */
    public void cerrarSesion() {
        // Limpiar datos del usuario
        usuarioActual = null;
        idUsuarioActual = null;
        solicitudActual = null;
        idMascotaSeleccionada = null;
        citaSeleccionada = null;
        borradorInfoPersonal = null;
        borradorCita = null;

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(
                null,
                "Sesión cerrada exitosamente",
                "Sesión Cerrada",
                JOptionPane.INFORMATION_MESSAGE);

        // Cerrar el menú principal
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.setVisible(false);
            frmMenuPrincipal.dispose();
            frmMenuPrincipal = null;
        }

        // Mostrar formulario de inicio de sesión
        gui.FrmInicioSesion frmInicioSesion = new gui.FrmInicioSesion();
        frmInicioSesion.setVisible(true);
    }

    /**
     * Obtiene los datos del usuario actual
     * Útil para pre-llenar formularios con información existente
     * 
     * @return UsuarioDTO del usuario actual o null si no hay usuario logueado
     */
    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Obtiene la cita seleccionada actualmente.
     * 
     * @return El DTO de la cita seleccionada.
     */
    public CitaDTO getCitaSeleccionada() {
        return citaSeleccionada;
    }

    /**
     * Establece la cita seleccionada.
     * 
     * @param citaSeleccionada La cita seleccionada.
     */
    public void setCitaSeleccionada(CitaDTO citaSeleccionada) {
        this.citaSeleccionada = citaSeleccionada;
    }

    /**
     * Obtiene la solicitud de adopción actual.
     * 
     * @return El DTO de la solicitud de adopción.
     */
    public SolicitudAdopcionDTO getSolicitudActual() {
        return solicitudActual;
    }

    /**
     * Obtiene el ID de la mascota seleccionada.
     * 
     * @return El ID de la mascota seleccionada.
     */
    public String getIdMascotaSeleccionada() {
        return idMascotaSeleccionada;
    }

    /**
     * Establece el ID de la mascota seleccionada.
     * 
     * @param id El ID de la mascota seleccionada.
     */
    public void setIdMascotaSeleccionada(String id) {
        this.idMascotaSeleccionada = id;
    }

    /**
     * Obtiene las solicitudes del usuario actual.
     * 
     * @return Lista de solicitudes del usuario actual.
     */
    public List<SolicitudAdopcionDTO> obtenerSolicitudesUsuarioActual() {
        try {
            if (usuarioActual != null && usuarioActual.getId() != null) {
                return controlSubsistemas.buscarSolicitudesPorUsuario(usuarioActual.getId());
            }
            return new java.util.ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al obtener solicitudes del usuario: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Cancela el proceso de solicitud de adopción actual
     * Limpia todos los datos temporales y regresa al menú principal
     */
    public void cancelarSolicitud() {
        // Preguntar confirmación al usuario
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro que desea cancelar la solicitud de adopción?\nSe perderán todos los datos ingresados.",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Limpiar todos los datos de la solicitud
            limpiarDatosSolicitud();

            // Volver al menú principal
            if (frmMenuPrincipal != null) {
                frmMenuPrincipal.mostrarInicio();
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Solicitud de adopción cancelada.",
                    "Cancelación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Regresa desde FrmInfoPersonal a la información de la mascota.
     */
    public void regresarDesdeFrmInfoPersonal() {
        if (frmMenuPrincipal != null && idMascotaSeleccionada != null) {
            frmMenuPrincipal.mostrarInicio();
        }
    }

    /**
     * Regresa desde FrmInfoVivienda a FrmInfoPersonal.
     */
    public void regresarDesdeFrmInfoVivienda() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoPersonal();
        }
    }

    /**
     * Regresa desde FrmRazonesAntecedentes a FrmInfoVivienda.
     */
    public void regresarDesdeFrmRazonesAntecedentes() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoVivienda();
        }
    }

    /**
     * Regresa desde JPInfoResumen a FrmRazonesAntecedentes.
     */
    public void regresarDesdeInfoResumen() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarRazonesAntecedentes();
        }
    }

    /**
     * Regresa al menú de selección de especies.
     */
    public void regresarAlMenuEspecies() {
        if (menuMostrarEspecies == null) {
            menuMostrarEspecies = new MenuMostrarEspecies();
            menuMostrarEspecies.setControlPresentacion(this);
        }
        menuMostrarEspecies.setVisible(true);
    }

    // --- MÉTODOS PARA BORRADORES ---

    /**
     * Guarda el borrador de información personal.
     * 
     * @param info DTO con la información personal.
     */
    public void guardarBorradorInfoPersonal(InfoPersonalDTO info) {
        this.borradorInfoPersonal = info;
    }

    /**
     * Guarda el borrador de cita seleccionada.
     * 
     * @param cita DTO con la cita disponible.
     */
    public void guardarBorradorCita(CitaDisponibleDTO cita) {
        this.borradorCita = cita;
    }

    /**
     * Obtiene el borrador de información personal.
     * 
     * @return El DTO con la información personal.
     */
    public InfoPersonalDTO getBorradorInfoPersonal() {
        return borradorInfoPersonal;
    }

    /**
     * Obtiene el borrador de cita.
     * 
     * @return El DTO con la cita disponible.
     */
    public CitaDisponibleDTO getBorradorCita() {
        return borradorCita;
    }

    // --- MÉTODOS PARA GESTIÓN DE SOLICITUDES ---

    /**
     * Obtiene todas las solicitudes del usuario actual.
     * 
     * @return Lista de solicitudes del usuario.
     */
    public List<SolicitudAdopcionDTO> obtenerSolicitudesUsuario() {
        if (idUsuarioActual == null) {
            System.err.println("No hay usuario logueado");
            return new java.util.ArrayList<>();
        }

        try {
            return controlSubsistemas.buscarSolicitudesPorUsuario(idUsuarioActual);
        } catch (Exception e) {
            System.err.println("Error al obtener solicitudes: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Cancela una solicitud de adopción.
     * 
     * @param idSolicitud ID de la solicitud a cancelar.
     * @throws Exception Si hay un error al cancelar la solicitud.
     */
    public void cancelarSolicitudAdopcion(String idSolicitud) throws Exception {
        if (idSolicitud == null || idSolicitud.isEmpty()) {
            throw new Exception("ID de solicitud inválido");
        }

        controlSubsistemas.cancelarSolicitudAdopcion(idSolicitud);

        // Actualizar el catálogo para mostrar la mascota liberada
        actualizarCatalogo();
    }

    /**
     * Cancela la cita asociada a una solicitud.
     * 
     * @param idSolicitud ID de la solicitud.
     * @throws Exception Si hay un error al cancelar la cita.
     */
    public void cancelarCita(String idSolicitud) throws Exception {
        if (idSolicitud == null || idSolicitud.isEmpty()) {
            throw new Exception("ID de solicitud inválido");
        }

        // Aquí idealmente deberíamos liberar la cita en el sistema de citas
        // Por ahora, solo actualizamos el estado de la solicitud o limpiamos el campo
        // de cita
        // Como no tenemos un método específico en el subsistema para "desvincular"
        // cita,
        // asumiremos que cancelar la solicitud o cambiar su estado es suficiente,
        // o implementaremos un método específico si es necesario.

        // Opción A: Simplemente marcamos la solicitud como que requiere nueva cita o
        // similar
        // Pero el usuario pidió "cancelar cita".

        // Vamos a llamar a un nuevo método en controlSubsistemas
        controlSubsistemas.cancelarCitaDeSolicitud(idSolicitud);

        // Actualizar el catálogo para mostrar la mascota liberada
        frmMenuPrincipal.getMenuMostrarEspecies().cargarMascotas();
    }

    /**
     * Inicia el proceso de reprogramación de cita para una solicitud existente.
     * 
     * @param idSolicitud ID de la solicitud.
     * @throws Exception Si hay un error al iniciar la reprogramación.
     */
    public void iniciarReprogramacionCita(String idSolicitud) throws Exception {
        if (idSolicitud == null || idSolicitud.isEmpty()) {
            throw new Exception("ID de solicitud inválido");
        }

        // Buscar la solicitud completa
        SolicitudAdopcionDTO solicitud = controlSubsistemas.buscarSolicitudPorId(idSolicitud);

        if (solicitud == null) {
            throw new Exception("Solicitud no encontrada");
        }

        // Establecer como solicitud actual
        this.solicitudActual = solicitud;

        // Si tiene mascota, cargarla también para contexto
        if (solicitud.getMascota() != null && solicitud.getMascota().getId() != null) {
            this.idMascotaSeleccionada = solicitud.getMascota().getId();
        }
    }

    /**
     * Verifica si el usuario tiene solicitudes previas y carga sus datos
     * 
     * @return true si se encontraron datos previos y se cargaron, false si no
     */
    public boolean verificarYPreCargarDatosPrevios() {
        if (idUsuarioActual == null) {
            return false;
        }

        java.util.List<SolicitudAdopcionDTO> solicitudes = obtenerSolicitudesUsuario();
        if (solicitudes == null || solicitudes.isEmpty()) {
            return false;
        }

        boolean viviendaEncontrada = false;
        boolean razonesEncontradas = false;

        // Buscar la última solicitud válida (que tenga datos)
        // Iteramos sobre todas las solicitudes para encontrar la información más
        // reciente disponible
        for (SolicitudAdopcionDTO sol : solicitudes) {
            // Ignorar la solicitud actual si ya está en la lista
            if (solicitudActual != null && sol.getId() != null && sol.getId().equals(solicitudActual.getId())) {
                continue;
            }

            // 1. Cargar Info Vivienda (si no la hemos encontrado ya)
            if (!viviendaEncontrada && sol.getUsuario() != null && sol.getUsuario().getInfoVivienda() != null) {
                System.out.println("DEBUG: Encontrada solicitud previa con InfoVivienda");

                if (usuarioActual == null) {
                    usuarioActual = new UsuarioDTO();
                    usuarioActual.setId(idUsuarioActual);
                }

                usuarioActual.setInfoVivienda(sol.getUsuario().getInfoVivienda());
                viviendaEncontrada = true;
            }

            // 2. Cargar Razones y Antecedentes (si no las hemos encontrado ya)
            if (!razonesEncontradas && sol.getRazones() != null) {
                if (solicitudActual == null) {
                    solicitudActual = new SolicitudAdopcionDTO();
                    solicitudActual.setUsuario(usuarioActual);
                    if (idMascotaSeleccionada != null) {
                        MascotaDTO m = controlSubsistemas.obtenerMascotaPorId(idMascotaSeleccionada);
                        solicitudActual.setMascota(m);
                    }
                }
                solicitudActual.setRazones(sol.getRazones());
                razonesEncontradas = true;
            }

            // Si ya encontramos ambos, podemos terminar
            if (viviendaEncontrada && razonesEncontradas) {
                break;
            }
        }

        // AGREGAR ESTE BLOQUE AQUÍ ↓↓↓
        // Guardar los datos precargados en la BD para que estén disponibles la próxima
        // vez
        if (viviendaEncontrada && usuarioActual != null && usuarioActual.getId() != null) {
            try {
                controlSubsistemas.actualizarUsuario(usuarioActual);
                System.out.println("✓ Datos de vivienda precargados guardados en BD");
            } catch (Exception e) {
                System.err.println("Error al actualizar usuario con datos precargados: " + e.getMessage());
            }
        }
        // FIN DEL BLOQUE ↑↑↑

        return viviendaEncontrada || razonesEncontradas;
    }

    /**
     * Actualiza el catálogo de mascotas.
     */
    public void actualizarCatalogo() {
        if (menuMostrarEspecies != null) {
            menuMostrarEspecies.cargarMascotas();
        }
    }
}