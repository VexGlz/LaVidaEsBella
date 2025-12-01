/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOS.CitaDTO;
import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import negocio.adopcionesdto.*;
import negocio.subsistemas.iniciosesion.FachadaInicioSesion;
import negocio.subsistemas.iniciosesion.IInicioSesion;
import negocio.subsistemas.mascotas.FachadaMascotas;
import negocio.subsistemas.mascotas.IMascotas;
import DTOS.MascotaDTO;

/**
 * Fachada principal para la Capa de Presentación.
 */
public class ControlSubsistemas {

    private IInicioSesion subsistemaInicioSesion;
    private IMascotas subsistemaMascotas;
    private ControlAdopcion controlAdopcion;
    private ControlCita controlCita;

    public ControlSubsistemas() {
        // Inicialización de subsistemas y controles
        this.subsistemaInicioSesion = new FachadaInicioSesion();
        this.subsistemaMascotas = new FachadaMascotas();
        this.controlAdopcion = new ControlAdopcion();
        this.controlCita = new ControlCita();
    }

    // --- MÉTODOS PARA INICIO DE SESIÓN ---
    public UsuarioDTO validarLogin(String correo, String password) throws Exception {
        return subsistemaInicioSesion.iniciarSesion(correo, password);
    }

    public void registrarUsuario(UsuarioDTO usuario) throws Exception {
        subsistemaInicioSesion.registrarUsuario(usuario);
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

    // --- MÉTODOS PARA EL FLUJO DE ADOPCIÓN ---
    public void procesarSolicitudCompleta(SolicitudAdopcionDTO solicitud, CitaDTO cita) throws Exception {
        // 1. Guardar la solicitud de adopción (Negocio -> Persistencia)
        controlAdopcion.crearSolicitud(solicitud);

        // 2. Agendar la cita y notificar (Negocio -> Infraestructura)
        // Asumiendo que obtenemos el correo del usuario del DTO
        String correoUsuario = "usuario@ejemplo.com";
        if (solicitud != null && solicitud.getUsuario() != null && solicitud.getUsuario().getInfoPersonal() != null) {
            correoUsuario = solicitud.getUsuario().getInfoPersonal().getCorreo();
        }
        controlCita.agendarCita(cita, correoUsuario);

        System.out.println("Flujo completo de solicitud finalizado exitosamente.");
    }
}
