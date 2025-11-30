/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.adopcionesdto;

import DTOS.CitaDTO;

/**
 * Control para el flujo de citas
 * @author System
 */
public class ControlCita {
    
    public void agendarCita(CitaDTO cita, String correoUsuario) {
        // TODO: Implementar lógica para agendar cita y enviar notificación
        if (cita != null && correoUsuario != null) {
            System.out.println("Cita agendada para: " + correoUsuario);
            System.out.println("Fecha: " + cita.getFechaHora());
        }
    }
}
