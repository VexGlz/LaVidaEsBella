/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.MascotaDTO;
import daos.MascotaDAO;
import conexion.ConexionMongoDB;
import entities.Mascota;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josel
 */
public class MascotaBO implements IMascotaBO {
    
    private MascotaDAO mascotaDAO;
    
    public MascotaBO() {
        this.mascotaDAO = new MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());
    }

    @Override
    public void registrarMascota(MascotaDTO mascotaDTO) {
        if (mascotaDTO != null) {
            Mascota mascota = convertirAEntidad(mascotaDTO);
            ObjectId id = mascotaDAO.guardar(mascota);
            // Actualizar ID en DTO si es necesario, aunque el hashcode es limitado
            System.out.println("Mascota registrada con ID: " + id);
        }
    }
    
    @Override
    public MascotaDTO buscarMascotaPorId(Long id) {
        // Nota: El DTO usa Long para ID pero MongoDB usa ObjectId.
        // Esto es un problema de diseño en los DTOs originales.
        // Por ahora no podemos buscar por ID Long directamente en Mongo sin un mapeo.
        // Asumiremos que el Long es el hashcode del ObjectId, lo cual no es reversible.
        // TODO: Refactorizar DTOs para usar String o ObjectId para IDs.
        return null; 
    }
    
    // Método auxiliar para buscar por ObjectId (String)
    public MascotaDTO buscarMascotaPorIdMongo(String idHex) {
        Mascota mascota = mascotaDAO.buscarPorId(new ObjectId(idHex));
        return convertirADTO(mascota);
    }
    
    @Override
    public List<MascotaDTO> buscarTodasLasMascotas() {
        List<Mascota> mascotas = mascotaDAO.buscarTodas();
        List<MascotaDTO> dtos = new ArrayList<>();
        for (Mascota m : mascotas) {
            dtos.add(convertirADTO(m));
        }
        return dtos;
    }
    
    @Override
    public List<MascotaDTO> buscarMascotasDisponibles() {
        List<Mascota> mascotas = mascotaDAO.buscarDisponibles();
        List<MascotaDTO> dtos = new ArrayList<>();
        for (Mascota m : mascotas) {
            dtos.add(convertirADTO(m));
        }
        return dtos;
    }
    
    @Override
    public void actualizarMascota(MascotaDTO mascotaDTO) {
        // TODO: Implementar actualizar en DAO
    }

    @Override
    public void actualizaEstadoSalud(MascotaDTO mascota, String nuevoEstado) {
        if (mascota != null) {
            mascota.setEstadoSalud(nuevoEstado);
            actualizarMascota(mascota);
        }
    }

    @Override
    public void registraDueño(MascotaDTO mascota, Long idDueño) {
        if (mascota != null) {
            // Lógica de negocio: al tener dueño, deja de estar disponible
            mascota.setDisponible(false);
            actualizarMascota(mascota);
        }
    }
    
    private MascotaDTO convertirADTO(Mascota entidad) {
        if (entidad == null) return null;
        MascotaDTO dto = new MascotaDTO();
        if (entidad.getId() != null) {
            dto.setId(Long.valueOf(entidad.getId().toString().hashCode()));
        }
        dto.setNombre(entidad.getNombre());
        dto.setEspecie(entidad.getEspecie());
        dto.setEstadoSalud(entidad.getEstadoSalud());
        dto.setPersonalidad(entidad.getPersonalidad());
        dto.setUrlImagen(entidad.getUrlImagen());
        dto.setEdad(entidad.getEdad());
        dto.setDisponible(entidad.isDisponible());
        return dto;
    }
    
    private Mascota convertirAEntidad(MascotaDTO dto) {
        if (dto == null) return null;
        Mascota entidad = new Mascota();
        // No seteamos ID porque se genera en Mongo
        entidad.setNombre(dto.getNombre());
        entidad.setEspecie(dto.getEspecie());
        entidad.setEstadoSalud(dto.getEstadoSalud());
        entidad.setPersonalidad(dto.getPersonalidad());
        entidad.setUrlImagen(dto.getUrlImagen());
        entidad.setEdad(dto.getEdad());
        entidad.setDisponible(dto.isDisponible());
        return entidad;
    }
}
