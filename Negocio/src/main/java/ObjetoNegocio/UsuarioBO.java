/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;

/**
 *
 * @author Josel
 */
public class UsuarioBO implements IUsuarioBO {

    @Override
    public void registraUsuario(UsuarioDTO usuario) {
        // Lógica de validación antes de persistir
        if (usuario != null && usuario.getInfoPersonal().getCorreo() != null) {
            // Llamada al DAO para guardar
        }
    }

    @Override
    public void generaSolicitud(UsuarioDTO usuario, SolicitudAdopcionDTO solicitud) {
        if (solicitud != null) {
            solicitud.setUsuario(usuario);
        }
    }

    @Override
    public void adoptaMascota(UsuarioDTO usuario, Long idMascota) {
        // Lógica final para vincular mascota al usuario
        System.out.println("Usuario " + usuario.getId() + " adoptó mascota " + idMascota);
    }

    @Override
    public void registraCorreo(UsuarioDTO usuario, String correo) {
        if (usuario != null && usuario.getInfoPersonal() != null) {
            usuario.getInfoPersonal().setCorreo(correo);
        }
    }

    @Override
    public SolicitudAdopcionDTO obtieneSolicitudAdopcion(UsuarioDTO usuario) {
        // Buscaría la solicitud activa del usuario
        return new SolicitudAdopcionDTO();
    }
}