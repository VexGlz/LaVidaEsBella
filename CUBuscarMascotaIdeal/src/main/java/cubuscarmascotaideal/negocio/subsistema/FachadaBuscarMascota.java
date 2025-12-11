package cubuscarmascotaideal.negocio.subsistema;

import cubuscarmascotaideal.dtos.EncuestaDTO;
import cubuscarmascotaideal.dtos.MascotaResultadoDTO;

import java.util.List;

/**
 * Fachada del subsistema de búsqueda de mascotas.
 * Simplifica el acceso a la funcionalidad del subsistema.
 */
public class FachadaBuscarMascota implements IBuscarMascota {

    private final IBuscarMascota buscarMascota;

    public FachadaBuscarMascota() {
        this.buscarMascota = new BuscarMascota();
    }

    // Constructor con inyección de dependencias
    public FachadaBuscarMascota(IBuscarMascota buscarMascota) {
        this.buscarMascota = buscarMascota;
    }

    @Override
    public List<MascotaResultadoDTO> buscarMascotasCompatibles(EncuestaDTO encuesta) {
        return buscarMascota.buscarMascotasCompatibles(encuesta);
    }

    @Override
    public List<MascotaResultadoDTO> obtenerTodasConCompatibilidad(EncuestaDTO encuesta) {
        return buscarMascota.obtenerTodasConCompatibilidad(encuesta);
    }
}
