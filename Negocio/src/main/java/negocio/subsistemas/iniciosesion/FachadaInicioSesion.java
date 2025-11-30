package negocio.subsistemas.iniciosesion;

import DTOS.UsuarioDTO;
import DTOS.InfoPersonalDTO;
import ObjetoNegocio.IUsuarioBO;
import ObjetoNegocio.UsuarioBO;

/**
 * Fachada para el subsistema de inicio de sesión
 * @author System
 */
public class FachadaInicioSesion implements IInicioSesion {
    
    private IUsuarioBO usuarioBO;
    
    public FachadaInicioSesion() {
        this.usuarioBO = new UsuarioBO();
    }
    
    @Override
    public UsuarioDTO iniciarSesion(String correo, String password) throws Exception {
        if (correo == null || correo.trim().isEmpty()) {
            throw new Exception("El correo es requerido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("La contraseña es requerida");
        }
        
        try {
            // Delegar al BO para buscar y validar usuario
            return usuarioBO.buscarYValidarUsuario(correo, password);
        } catch (RuntimeException e) {
            // Convertir RuntimeException a Exception con el mensaje
            throw new Exception(e.getMessage());
        }
    }
    
    @Override
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO == null) {
            throw new Exception("El usuario no puede ser nulo");
        }
        
        if (usuarioDTO.getInfoPersonal() == null || 
            usuarioDTO.getInfoPersonal().getCorreo() == null || 
            usuarioDTO.getInfoPersonal().getCorreo().trim().isEmpty()) {
            throw new Exception("El correo es requerido");
        }
        
        if (usuarioDTO.getContrasena() == null || usuarioDTO.getContrasena().trim().isEmpty()) {
            throw new Exception("La contraseña es requerida");
        }
        
        try {
            // Delegar al BO para el registro
            usuarioBO.registraUsuario(usuarioDTO);
            System.out.println("Usuario registrado exitosamente");
        } catch (RuntimeException e) {
            // Convertir RuntimeException a Exception con el mensaje
            throw new Exception(e.getMessage());
        }
    }
}
