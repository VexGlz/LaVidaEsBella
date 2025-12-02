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

    public ControlSubsistemas() {
        // Inicialización de subsistemas y controles
        this.subsistemaInicioSesion = new FachadaInicioSesion();
        this.subsistemaMascotas = new FachadaMascotas();
        this.controlAdopcion = new ControlAdopcion();
        this.controlCita = new ControlCita();
        this.citaDisponibleBO = new CitaDisponibleBO();
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

    // --- MÉTODOS PARA EL FLUJO DE ADOPCIÓN ---
    public void procesarSolicitudCompleta(SolicitudAdopcionDTO solicitud, CitaDTO cita) throws Exception {
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
}
