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

    public ControlPresentacion() {
        this.controlSubsistemas = new ControlSubsistemas();
        this.idUsuarioActual = null;
    }

    public void iniciarSistema() {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        if (frmMenuPrincipal == null) {
            frmMenuPrincipal = new FrmMenuPrincipal();
            // Inyectar dependencia del control
            frmMenuPrincipal.setControl(this);
        }
        frmMenuPrincipal.setVisible(true);
    }

    public void mostrarMenuMascotas() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInicio();
        }
    }

    public void procesarSeleccionMascota(String idMascota) {
        this.idMascotaSeleccionada = idMascota;
        mostrarInformacionMascota(idMascota);
    }

    public void mostrarInformacionMascota(String idMascota) {
        System.out.println("DEBUG: Solicitando información para mascota ID: " + idMascota);

        // Buscar la mascota por ID
        MascotaDTO mascota = controlSubsistemas.obtenerMascotaPorId(idMascota);

        if (mascota != null) {
            System.out.println("DEBUG: Mascota encontrada: " + mascota.getNombre());
        } else {
            System.err.println("DEBUG: No se encontró mascota con ID: " + idMascota);
        }

        // Crear ventana pasando la referencia al control
        frmInformacionMascota = new FrmInformacionMascota(mascota, this);
        frmInformacionMascota.setVisible(true);
    }

    public void continuarConSolicitud() {
        cerrarVentana(frmInformacionMascota);
        mostrarInfoPersonal();
    }

    public void mostrarInfoPersonal() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoPersonal();
        }
    }

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
                    System.out.println("✓ Usuario actualizado en BD con info personal");
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

            String nombreUsuario = (infoPersonal.getNombre() != null) ? infoPersonal.getNombre() : "Usuario";
            System.out.println("✓ Información personal guardada: " + nombreUsuario);

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

    public void mostrarInfoVivienda() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoVivienda();
        }
    }

    public void guardarInfoVivienda(InfoViviendaDTO infoVivienda) {
        try {
            // Asegurar que existe usuario y solicitud
            if (usuarioActual == null) {
                System.out.println("DEBUG: usuarioActual es null en guardarInfoVivienda. Creando nuevo.");
                usuarioActual = new UsuarioDTO();
            } else {
                System.out.println("DEBUG: usuarioActual ID: " + usuarioActual.getId());
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
                    System.out.println("✓ Usuario actualizado en BD con info vivienda");
                } catch (Exception e) {
                    System.err.println("Error al actualizar usuario en BD: " + e.getMessage());
                }
            }

            System.out.println("✓ Información de vivienda guardada: " + infoVivienda.getTipoVivienda());

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

    public void mostrarRazonesAntecedentes() {
        frmRazonesAntecedentes = new FrmRazonesAntecedentes();
        frmRazonesAntecedentes.setVisible(true);
    }

    public void guardarRazonesAntecedentes(RazonesAntecedentesDTO razones) {
        try {
            // Asegurar que existe solicitud
            if (solicitudActual == null) {
                solicitudActual = new SolicitudAdopcionDTO();
            }

            // Guardar razones en la solicitud
            solicitudActual.setRazones(razones);

            System.out.println("✓ Razones y antecedentes guardados: " + razones.getMotivoAdopcion());

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

    public void mostrarInfoResumen() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoResumen();
        }
    }


    // --- MÉTODOS PARA CITAS ---
    public java.util.List<DTOS.CitaDisponibleDTO> obtenerCitasDisponibles() {
        return controlSubsistemas.obtenerCitasDisponibles();
    }

    public boolean reservarCita(String idCita, String idUsuario) {
        return controlSubsistemas.reservarCita(idCita, idUsuario);
    }

    public void procesarCita(DTOS.CitaDTO cita) {
        try {
            // Recuperación de estado si solicitudActual es null
            if (solicitudActual == null) {
                System.out.println(
                        "⚠️ Advertencia: solicitudActual era null en procesarCita. Intentando recuperar estado.");
                solicitudActual = new SolicitudAdopcionDTO();

                if (usuarioActual != null) {
                    solicitudActual.setUsuario(usuarioActual);
                }

                if (idMascotaSeleccionada != null) {
                    MascotaDTO mascota = controlSubsistemas.obtenerMascotaPorId(idMascotaSeleccionada);
                    solicitudActual.setMascota(mascota);
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
            System.out.println("✓ Solicitud procesada exitosamente");

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
        System.out.println("→ Datos de solicitud limpiados (usuario logueado se mantiene)");
    }

    public void mostrarConfirmacion() {
        frmCorreoConfirmacion = new FrmCorreoConfirmacion();
        frmCorreoConfirmacion.setVisible(true);
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
        System.out.println("✗ Mostrando pantalla de error: " + mensajeError);
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

    public void finalizarProceso() {
        if (frmCorreoConfirmacion != null)
            frmCorreoConfirmacion.setVisible(false);
        solicitudActual = null;
        idMascotaSeleccionada = null;
        mostrarMenuPrincipal();
    }

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
            System.out.println("✓ Usuario logueado: " + usuario.getInfoPersonal().getNombre());
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

        System.out.println("✓ Sesión cerrada correctamente");
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

    public CitaDTO getCitaSeleccionada() {
        return citaSeleccionada;
    }

    public void setCitaSeleccionada(CitaDTO citaSeleccionada) {
        this.citaSeleccionada = citaSeleccionada;
    }

    public SolicitudAdopcionDTO getSolicitudActual() {
        return solicitudActual;
    }

    public String getIdMascotaSeleccionada() {
        return idMascotaSeleccionada;
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
     * Regresa desde FrmInfoPersonal a la información de la mascota
     */
    public void regresarDesdeFrmInfoPersonal() {
        if (frmMenuPrincipal != null && idMascotaSeleccionada != null) {
            // Volver a mostrar la información de la mascota
            mostrarInformacionMascota(idMascotaSeleccionada);
        }
    }

    /**
     * Regresa desde FrmInfoVivienda a FrmInfoPersonal
     */
    public void regresarDesdeFrmInfoVivienda() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoPersonal();
        }
    }

    /**
     * Regresa desde FrmRazonesAntecedentes a FrmInfoVivienda
     */
    public void regresarDesdeFrmRazonesAntecedentes() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarInfoVivienda();
        }
    }

    /**
     * Regresa desde JPInfoResumen a FrmRazonesAntecedentes
     */
    public void regresarDesdeInfoResumen() {
        if (frmMenuPrincipal != null) {
            frmMenuPrincipal.mostrarRazonesAntecedentes();
        }
    }

    /**
     * Regresa al menú de selección de especies
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
     * Guarda el borrador de información personal
     */
    public void guardarBorradorInfoPersonal(InfoPersonalDTO info) {
        this.borradorInfoPersonal = info;
        System.out.println("Borrador de información personal guardado");
    }

    /**
     * Guarda el borrador de cita seleccionada
     */
    public void guardarBorradorCita(CitaDisponibleDTO cita) {
        this.borradorCita = cita;
        System.out.println("Borrador de cita guardado");
    }

    /**
     * Obtiene el borrador de información personal
     */
    public InfoPersonalDTO getBorradorInfoPersonal() {
        return borradorInfoPersonal;
    }

    /**
     * Obtiene el borrador de cita
     */
    public CitaDisponibleDTO getBorradorCita() {
        return borradorCita;
    }

    // --- MÉTODOS PARA GESTIÓN DE SOLICITUDES ---

    /**
     * Obtiene todas las solicitudes del usuario actual
     */
    public List<SolicitudAdopcionDTO> obtenerSolicitudesUsuario() {
        System.out.println("DEBUG: Obteniendo solicitudes para usuario ID: " + idUsuarioActual);
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
     * Cancela una solicitud de adopción
     */
    public void cancelarSolicitudAdopcion(String idSolicitud) throws Exception {
        if (idSolicitud == null || idSolicitud.isEmpty()) {
            throw new Exception("ID de solicitud inválido");
        }

        controlSubsistemas.cancelarSolicitudAdopcion(idSolicitud);
        System.out.println("✓ Solicitud " + idSolicitud + " cancelada");
        
        // Actualizar el catálogo para mostrar la mascota liberada
        actualizarCatalogo();
    }

    /**
     * Cancela la cita asociada a una solicitud
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
        System.out.println("✓ Cita de solicitud " + idSolicitud + " cancelada");
        
        // Actualizar el catálogo para mostrar la mascota liberada
        actualizarCatalogo();
    }

    /**
     * Inicia el proceso de reprogramación de cita para una solicitud existente
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

        List<SolicitudAdopcionDTO> solicitudes = obtenerSolicitudesUsuario();
        if (solicitudes == null || solicitudes.isEmpty()) {
            return false;
        }

        // Buscar la última solicitud válida (que tenga datos)
        // Orden inverso para obtener la más reciente si la lista está ordenada
        // cronológicamente
        // O simplemente iterar y tomar la primera que tenga datos completos
        for (SolicitudAdopcionDTO sol : solicitudes) {
            // Ignorar la solicitud actual si ya está en la lista (aunque no debería estar
            // guardada aún)
            if (solicitudActual != null && sol.getId() != null && sol.getId().equals(solicitudActual.getId())) {
                continue;
            }

            boolean datosEncontrados = false;

            // 1. Cargar Info Vivienda
            if (sol.getUsuario() != null && sol.getUsuario().getInfoVivienda() != null) {
                if (usuarioActual == null) {
                    usuarioActual = new UsuarioDTO();
                    usuarioActual.setId(idUsuarioActual);
                }
                // Solo cargar si no tenemos ya info (o sobrescribir, según requerimiento. Aquí
                // sobrescribimos para "precargar")
                usuarioActual.setInfoVivienda(sol.getUsuario().getInfoVivienda());
                datosEncontrados = true;
                System.out.println("✓ Info Vivienda recuperada de solicitud previa: " + sol.getId());
            }

            // 2. Cargar Razones y Antecedentes
            if (sol.getRazones() != null) {
                if (solicitudActual == null) {
                    solicitudActual = new SolicitudAdopcionDTO();
                    solicitudActual.setUsuario(usuarioActual);
                    if (idMascotaSeleccionada != null) {
                        MascotaDTO m = controlSubsistemas.obtenerMascotaPorId(idMascotaSeleccionada);
                        solicitudActual.setMascota(m);
                    }
                }
                solicitudActual.setRazones(sol.getRazones());
                datosEncontrados = true;
                System.out.println("✓ Razones recuperadas de solicitud previa: " + sol.getId());
            }

            if (datosEncontrados) {
                return true;
            }
        }

        return false;
    }

    /**
     * Actualiza el catálogo de mascotas
     */
    public void actualizarCatalogo() {
        if (menuMostrarEspecies != null) {
            menuMostrarEspecies.cargarMascotas();
        }
    }
}