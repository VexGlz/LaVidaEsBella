/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

import infraestructura.dto.CorreoDTO;

/**
 * Fachada para el subsistema de correo.
 * 
 * @author System
 */
public class FachadaCorreo implements ISistemaCorreo {

    private ControlSistemaCorreo controlCorreo;

    public FachadaCorreo() {
        this.controlCorreo = new ControlSistemaCorreo();
    }

    @Override
    public void enviarCorreo(CorreoDTO correo) throws Exception {
        if (ConfiguracionCorreo.MODO_DEBUG) {
            System.out.println("=== MODO SIMULACIÓN: CORREO NO ENVIADO REALMENTE ===");
            System.out.println("Destinatario: " + correo.getDestinatario());
            System.out.println("Asunto: " + correo.getAsunto());
            System.out.println("Mensaje: \n" + correo.getMensaje());
            System.out.println("====================================================");
            return; // Detener ejecución real
        }
        controlCorreo.enviarCorreo(correo);
    }
}
