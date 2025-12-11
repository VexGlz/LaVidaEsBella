package cubuscarmascotaideal.negocio.adaptadores;

import cubuscarmascotaideal.dtos.MascotaResultadoDTO;
import entities.Mascota;

/**
 * Adaptador para convertir Entity Mascota a DTO con porcentaje de
 * compatibilidad.
 */
public class AdaptadorMascotaResultado {

    /**
     * Convierte una Mascota entity a MascotaResultadoDTO
     * 
     * @param mascota                  Entity de mascota
     * @param porcentajeCompatibilidad Porcentaje calculado (0-100)
     * @return DTO con datos de mascota y compatibilidad
     */
    public static MascotaResultadoDTO entidadADTO(Mascota mascota, double porcentajeCompatibilidad) {
        if (mascota == null) {
            return null;
        }

        MascotaResultadoDTO dto = new MascotaResultadoDTO();
        dto.setId(mascota.getId() != null ? mascota.getId().toString() : null);
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        dto.setRaza(mascota.getRaza());
        dto.setEdad(mascota.getEdad());
        dto.setTamano(mascota.getTamano());
        dto.setNivelActividad(mascota.getNivelActividad());
        dto.setPeludo(mascota.isPeludo());
        dto.setCostoMantenimiento(mascota.getCostoMantenimiento());
        dto.setDescripcion(mascota.getDescripcion());
        dto.setUrlImagen(mascota.getUrlImagen());
        dto.setPorcentajeCompatibilidad(porcentajeCompatibilidad);

        return dto;
    }
}
