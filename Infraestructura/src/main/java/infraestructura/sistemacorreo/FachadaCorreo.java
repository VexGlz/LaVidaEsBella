/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

import infraestructura.dto.CorreoDTO;

/**
 * Fachada para el subsistema de correo.
 * MODO PRODUCCIÓN ACTIVADO - ENVÍO REAL CON OAUTH2
 * 
 * @author System
 */
public class FachadaCorreo implements ISistemaCorreo {

    private ControlSistemaCorreo controlCorreo;

    public FachadaCorreo() {
        this.controlCorreo = new ControlSistemaCorreo();
        // FORZAR MODO PRODUCCIÓN - ENVÍO REAL
        this.controlCorreo.activarModoProduccion();
        this.controlCorreo.setUsarOAuth2(true);

        System.out.println("✓ FachadaCorreo inicializada en MODO PRODUCCIÓN con OAuth2");
    }

    @Override
    public void enviarCorreo(CorreoDTO correo) throws Exception {
        controlCorreo.enviarCorreo(correo);
    }
}
