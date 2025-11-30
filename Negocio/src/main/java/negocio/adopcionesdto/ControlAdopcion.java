/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.adopcionesdto;

import DTOS.SolicitudAdopcionDTO;

/**
 * Control para el flujo de adopciones
 * @author System
 */
public class ControlAdopcion {
    
    public void crearSolicitud(SolicitudAdopcionDTO solicitud) {
        // TODO: Implementar lógica para crear solicitud de adopción
        if (solicitud != null && solicitud.getMascota() != null) {
            System.out.println("Solicitud de adopción creada para mascota: " + solicitud.getMascota().getId());
        }
    }
}
