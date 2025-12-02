/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import DTOS.InfoPersonalDTO;
import daos.UsuarioDAO;
import conexion.ConexionMongoDB;
import entities.Usuario;
import org.bson.types.ObjectId;

/**
 *
 * @author Josel
 */
public class UsuarioBO implements IUsuarioBO {

    private UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAO(ConexionMongoDB.getInstancia().getDatabase());
    }

    @Override
    public void registraUsuario(UsuarioDTO usuarioDTO) {
        // Lógica de validación antes de persistir
        if (usuarioDTO != null && usuarioDTO.getInfoPersonal() != null &&
                usuarioDTO.getInfoPersonal().getCorreo() != null) {

            // Verificar si el correo ya existe
            if (usuarioDAO.existeCorreo(usuarioDTO.getInfoPersonal().getCorreo())) {
                throw new RuntimeException("El correo ya está registrado");
            }

            // Convertir DTO a entidad
            Usuario usuario = convertirAEntidad(usuarioDTO);

            // Guardar en base de datos
            ObjectId id = usuarioDAO.guardar(usuario);
            System.out.println("Usuario guardado con ID: " + id);
        }
    }

    /**
     * Busca y valida un usuario por correo y contraseña
     */
    public UsuarioDTO buscarYValidarUsuario(String correo, String password) {
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!usuario.getContrasena().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return convertirADTO(usuario);
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

    /**
     * Convierte una entidad Usuario a UsuarioDTO
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();

        if (usuario.getId() != null) {
            dto.setId(usuario.getId().toHexString());
        }

        dto.setContrasena(usuario.getContrasena());

        if (usuario.getInfoPersonal() != null) {
            InfoPersonalDTO infoDTO = new InfoPersonalDTO();
            infoDTO.setNombre(usuario.getInfoPersonal().getNombre());
            infoDTO.setCorreo(usuario.getInfoPersonal().getCorreo());
            infoDTO.setCurp(usuario.getInfoPersonal().getCurp());
            infoDTO.setDireccion(usuario.getInfoPersonal().getDireccion());
            infoDTO.setTelefono(usuario.getInfoPersonal().getTelefono());
            dto.setInfoPersonal(infoDTO);
        }

        return dto;
    }

    /**
     * Convierte un UsuarioDTO a entidad Usuario
     */
    private Usuario convertirAEntidad(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setContrasena(dto.getContrasena());

        if (dto.getInfoPersonal() != null) {
            entities.InfoPersonal infoPersonal = new entities.InfoPersonal();
            infoPersonal.setNombre(dto.getInfoPersonal().getNombre());
            infoPersonal.setCorreo(dto.getInfoPersonal().getCorreo());
            infoPersonal.setCurp(dto.getInfoPersonal().getCurp());
            infoPersonal.setDireccion(dto.getInfoPersonal().getDireccion());
            infoPersonal.setTelefono(dto.getInfoPersonal().getTelefono());
            usuario.setInfoPersonal(infoPersonal);
        }

        return usuario;
    }
}