/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import negocio.adopcionesdto.*;
import negocio.subsistemas.iniciosesion.FachadaInicioSesion;
import negocio.subsistemas.iniciosesion.IInicioSesion;

/**
 * Fachada principal para la Capa de Presentación.
 */
public class ControlSubsistemas {

    private IInicioSesion subsistemaInicioSesion;
    private ControlAdopcion controlAdopcion;
    private ControlCita controlCita;

    public ControlSubsistemas() {
        // Inicialización de subsistemas y controles
        this.subsistemaInicioSesion = new FachadaInicioSesion();
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

    // --- MÉTODOS PARA EL FLUJO DE ADOPCIÓN ---
    public void procesarSolicitudCompleta(SolicitudAdopcionDTO solicitud, CitaDTO cita) {
        // 1. Guardar la solicitud de adopción (Negocio -> Persistencia)
        controlAdopcion.crearSolicitud(solicitud);

        // 2. Agendar la cita y notificar (Negocio -> Infraestructura)
        // Asumiendo que obtenemos el correo del usuario del DTO
        String correoUsuario = "usuario@ejemplo.com"; // Deberías sacarlo de solicitud.getUsuario().getCorreo()
        controlCita.agendarCita(cita, correoUsuario);

        System.out.println("Flujo completo de solicitud finalizado exitosamente.");
    }
}
