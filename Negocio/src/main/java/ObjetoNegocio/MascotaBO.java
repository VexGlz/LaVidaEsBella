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
 * Business Object para gestionar la lógica de negocio relacionada con Mascotas
 * Proporciona operaciones CRUD y conversión entre DTOs y entidades
 * 
 * @author Josel
 */
public class MascotaBO implements IMascotaBO {

    private MascotaDAO mascotaDAO;

    /**
     * Constructor que inicializa el DAO con la conexión a MongoDB
     */
    public MascotaBO() {
        this.mascotaDAO = new MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());
    }

    /**
     * Registra una nueva mascota en el sistema
     * 
     * @param mascotaDTO DTO con los datos de la mascota a registrar
     */
    @Override
    public void registrarMascota(MascotaDTO mascotaDTO) {
        if (mascotaDTO != null) {
            Mascota mascota = convertirAEntidad(mascotaDTO);
            ObjectId id = mascotaDAO.guardar(mascota);
            System.out.println("Mascota registrada con ID: " + id);
        }
    }

    /**
     * Busca una mascota por su identificador único
     * 
     * @param id Identificador hexadecimal de la mascota
     * @return MascotaDTO con los datos de la mascota o null si no existe
     */
    @Override
    public MascotaDTO buscarMascotaPorId(String id) {
        try {
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

    /**
     * Método auxiliar para buscar mascota usando ObjectId de MongoDB
     * 
     * @param idHex Identificador hexadecimal de MongoDB
     * @return MascotaDTO con los datos de la mascota
     */
    public MascotaDTO buscarMascotaPorIdMongo(String idHex) {
        Mascota mascota = mascotaDAO.buscarPorId(new ObjectId(idHex));
        return convertirADTO(mascota);
    }

    /**
     * Obtiene todas las mascotas registradas en el sistema
     * 
     * @return Lista de DTOs con todas las mascotas
     */
    @Override
    public List<MascotaDTO> buscarTodasLasMascotas() {
        List<Mascota> mascotas = mascotaDAO.buscarTodas();
        List<MascotaDTO> dtos = new ArrayList<>();
        for (Mascota m : mascotas) {
            dtos.add(convertirADTO(m));
        }
        return dtos;
    }

    /**
     * Obtiene todas las mascotas disponibles para adopción
     * 
     * @return Lista de DTOs con mascotas disponibles
     */
    @Override
    public List<MascotaDTO> buscarMascotasDisponibles() {
        List<Mascota> mascotas = mascotaDAO.buscarDisponibles();
        List<MascotaDTO> dtos = new ArrayList<>();
        for (Mascota m : mascotas) {
            dtos.add(convertirADTO(m));
        }
        return dtos;
    }

    /**
     * Actualiza los datos de una mascota existente
     * 
     * @param mascotaDTO DTO con los datos actualizados de la mascota
     */
    @Override
    public void actualizarMascota(MascotaDTO mascotaDTO) {
        if (mascotaDTO != null) {
            Mascota mascota = convertirAEntidad(mascotaDTO);
            mascotaDAO.actualizar(mascota);
            System.out.println("Mascota actualizada: " + mascota.getId());
        }
    }

    /**
     * Actualiza el estado de salud de una mascota
     * 
     * @param mascota     DTO de la mascota a actualizar
     * @param nuevoEstado Nuevo estado de salud
     */
    @Override
    public void actualizaEstadoSalud(MascotaDTO mascota, String nuevoEstado) {
        if (mascota != null) {
            mascota.setEstadoSalud(nuevoEstado);
            actualizarMascota(mascota);
        }
    }

    /**
     * Registra el dueño de una mascota, marcándola como no disponible
     * 
     * @param mascota DTO de la mascota adoptada
     * @param idDueño Identificador del nuevo dueño
     */
    @Override
    public void registraDueño(MascotaDTO mascota, Long idDueño) {
        if (mascota != null) {
            mascota.setDisponible(false);
            actualizarMascota(mascota);
        }
    }

    /**
     * Elimina una mascota del catálogo (soft delete)
     * Marca la mascota como no disponible en lugar de borrarla físicamente
     * 
     * @param id ID de la mascota a eliminar
     * @return true si se eliminó correctamente
     */
    @Override
    public boolean eliminarMascota(String id) {
        try {
            // Buscar la mascota
            MascotaDTO mascota = buscarMascotaPorId(id);

            if (mascota == null) {
                System.err.println("No se encontró mascota con ID: " + id);
                return false;
            }

            // Soft delete: marcar como no disponible
            mascota.setDisponible(false);
            mascota.setEstado("Eliminada del catálogo");

            // Actualizar en BD
            actualizarMascota(mascota);

            System.out.println("✓ Mascota eliminada del catálogo (soft delete): " + id);
            return true;

        } catch (Exception e) {
            System.err.println("Error al eliminar mascota: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una entidad Mascota a un DTO
     * 
     * @param entidad Entidad Mascota de la capa de persistencia
     * @return MascotaDTO para la capa de presentación
     */
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
        dto.setColor(entidad.getColor());
        dto.setRaza(entidad.getRaza());
        dto.setPeso(entidad.getPeso());

        return dto;
    }

    /**
     * Convierte un DTO de Mascota a una entidad
     * 
     * @param dto MascotaDTO de la capa de presentación
     * @return Entidad Mascota para la capa de persistencia
     */
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

        // Nuevos campos
        entidad.setColor(dto.getColor());
        entidad.setRaza(dto.getRaza());
        entidad.setPeso(dto.getPeso());

        return entidad;
    }
}
