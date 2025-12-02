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
    public MascotaDTO buscarMascotaPorId(String id) {
        try {
            // Convertir el String hex a ObjectId y buscar en MongoDB
            ObjectId objectId = new ObjectId(id);
            Mascota mascota = mascotaDAO.buscarPorId(objectId);
            return convertirADTO(mascota);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: ID de mascota inválido: " + id);
            return null;
        } catch (Exception e) {
            System.err.println("Error al buscar mascota: " + e.getMessage());
            return null;
        }
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
        if (mascotaDTO != null) {
            Mascota mascota = convertirAEntidad(mascotaDTO);
            mascotaDAO.actualizar(mascota);
            System.out.println("Mascota actualizada: " + mascota.getId());
        }
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
        if (entidad == null)
            return null;
        MascotaDTO dto = new MascotaDTO();
        if (entidad.getId() != null) {
            dto.setId(entidad.getId().toHexString());
        }
        dto.setNombre(entidad.getNombre());
        dto.setEspecie(entidad.getEspecie());
        dto.setEstadoSalud(entidad.getEstadoSalud());
        dto.setPersonalidad(entidad.getPersonalidad());
        dto.setUrlImagen(entidad.getUrlImagen());
        dto.setEdad(entidad.getEdad());
        dto.setDisponible(entidad.isDisponible());
        dto.setEstado(entidad.getEstado());
        return dto;
    }

    private Mascota convertirAEntidad(MascotaDTO dto) {
        if (dto == null)
            return null;
        Mascota entidad = new Mascota();
        // Si el DTO tiene un ID (actualización), convertirlo a ObjectId
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            try {
                entidad.setId(new ObjectId(dto.getId()));
            } catch (IllegalArgumentException e) {
                System.err.println("ID inválido en DTO, se generará uno nuevo: " + dto.getId());
            }
        }
        entidad.setNombre(dto.getNombre());
        entidad.setEspecie(dto.getEspecie());
        entidad.setEstadoSalud(dto.getEstadoSalud());
        entidad.setPersonalidad(dto.getPersonalidad());
        entidad.setUrlImagen(dto.getUrlImagen());
        entidad.setEdad(dto.getEdad());
        entidad.setDisponible(dto.isDisponible());
        entidad.setEstado(dto.getEstado());
        return entidad;
    }
}
