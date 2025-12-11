package cubuscarmascotaideal.control;

import cubuscarmascotaideal.dtos.EncuestaDTO;
import cubuscarmascotaideal.dtos.MascotaResultadoDTO;
import cubuscarmascotaideal.negocio.subsistema.FachadaBuscarMascota;
import cubuscarmascotaideal.negocio.subsistema.IBuscarMascota;

import java.util.List;

/**
 * Control de subsistemas para búsqueda de mascotas.
 * Coordina las llamadas al subsistema de búsqueda.
 */
public class ControlSubsistemas {

    private final IBuscarMascota buscarMascota;

    public ControlSubsistemas() {
        this.buscarMascota = new FachadaBuscarMascota();
    }

    // Constructor con inyección de dependencias
    public ControlSubsistemas(IBuscarMascota buscarMascota) {
        this.buscarMascota = buscarMascota;
    }

    /**
     * Busca mascotas compatibles según encuesta
     * 
     * @param encuesta Preferencias del usuario
     * @return Lista de mascotas compatibles (>=50%)
     * @throws Exception si ocurre algún error
     */
    public List<MascotaResultadoDTO> buscarMascotasCompatibles(EncuestaDTO encuesta) throws Exception {
        return buscarMascota.buscarMascotasCompatibles(encuesta);
    }

    /**
     * Obtiene todas las mascotas con sus porcentajes de compatibilidad
     * 
     * @param encuesta Preferencias para calcular compatibilidad
     * @return Lista completa ordenada por compatibilidad
     * @throws Exception si ocurre algún error
     */
    public List<MascotaResultadoDTO> obtenerTodasConCompatibilidad(EncuestaDTO encuesta) throws Exception {
        return buscarMascota.obtenerTodasConCompatibilidad(encuesta);
    }
}
