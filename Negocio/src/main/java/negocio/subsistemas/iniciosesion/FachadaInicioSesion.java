package negocio.subsistemas.iniciosesion;

import DTOS.UsuarioDTO;
import DTOS.InfoPersonalDTO;
import ObjetoNegocio.IUsuarioBO;
import ObjetoNegocio.UsuarioBO;
import daos.UsuarioDAO;
import conexion.ConexionMongoDB;
import entities.Usuario;
import org.bson.types.ObjectId;

/**
 * Fachada para el subsistema de inicio de sesión
 * @author System
 */
public class FachadaInicioSesion implements IInicioSesion {
    
    private IUsuarioBO usuarioBO;
    private UsuarioDAO usuarioDAO;
    
    public FachadaInicioSesion() {
        this.usuarioBO = new UsuarioBO();
        this.usuarioDAO = new UsuarioDAO(ConexionMongoDB.getInstancia().getDatabase());
    }
    
    @Override
    public UsuarioDTO iniciarSesion(String correo, String password) throws Exception {
        if (correo == null || correo.trim().isEmpty()) {
            throw new Exception("El correo es requerido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("La contraseña es requerida");
        }
        
        // Buscar usuario por correo
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        
        // Validar contraseña
        if (!usuario.getContrasena().equals(password)) {
            throw new Exception("Contraseña incorrecta");
        }
        
        // Convertir a DTO
        return convertirADTO(usuario);
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
        
        // Verificar si el correo ya existe
        if (usuarioDAO.existeCorreo(usuarioDTO.getInfoPersonal().getCorreo())) {
            throw new Exception("El correo ya está registrado");
        }
        
        // Convertir DTO a entidad
        Usuario usuario = convertirAEntidad(usuarioDTO);
        
        // Guardar en base de datos
        ObjectId id = usuarioDAO.guardar(usuario);
        
        System.out.println("Usuario registrado exitosamente con ID: " + id);
    }
    
    /**
     * Convierte una entidad Usuario a UsuarioDTO
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        
        if (usuario.getId() != null) {
            dto.setId(Long.valueOf(usuario.getId().toString().hashCode()));
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
