package cubuscarmascotaideal.control;

import cubuscarmascotaideal.dtos.EncuestaDTO;
import cubuscarmascotaideal.dtos.MascotaResultadoDTO;
import cubuscarmascotaideal.dtos.ResultadoOperacion;

import java.util.List;

/**
 * Fachada principal del módulo de búsqueda de mascotas (Boundary).
 * Proporciona una interfaz simplificada para la capa de presentación,
 * maneja excepciones y traduce errores a mensajes de usuario.
 */
public class ControlPresentacion {

    private final ControlSubsistemas controlSubsistemas;

    public ControlPresentacion() {
        this.controlSubsistemas = new ControlSubsistemas();
    }

    // Constructor con inyección de dependencias
    public ControlPresentacion(ControlSubsistemas controlSubsistemas) {
        this.controlSubsistemas = controlSubsistemas;
    }

    /**
     * Busca mascotas compatibles según las preferencias del usuario
     * 
     * @param encuesta Datos de la encuesta del usuario
     * @return Lista de mascotas compatibles (>=50% compatibilidad)
     */
    public List<MascotaResultadoDTO> buscarMascotasCompatibles(EncuestaDTO encuesta) {
        try {
            if (encuesta == null) {
                System.err.println("Encuesta no puede ser nula");
                return List.of();
            }

            return controlSubsistemas.buscarMascotasCompatibles(encuesta);

        } catch (Exception e) {
            System.err.println("Error al buscar mascotas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtiene todas las mascotas disponibles con su porcentaje de compatibilidad
     * 
     * @param encuesta Preferencias para calcular compatibilidad
     * @return Lista de todas las mascotas con scores
     */
    public List<MascotaResultadoDTO> obtenerTodasConCompatibilidad(EncuestaDTO encuesta) {
        try {
            if (encuesta == null) {
                return List.of();
            }

            return controlSubsistemas.obtenerTodasConCompatibilidad(encuesta);

        } catch (Exception e) {
            System.err.println("Error al obtener mascotas: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Valida que la encuesta tenga datos completos antes de buscar
     * 
     * @param encuesta Encuesta a validar
     * @return Resultado de la validación
     */
    public ResultadoOperacion validarEncuesta(EncuestaDTO encuesta) {
        if (encuesta == null) {
            return ResultadoOperacion.error("La encuesta no puede estar vacía");
        }

        if (encuesta.getTamano() == null || encuesta.getTamano().trim().isEmpty()) {
            return ResultadoOperacion.error("Debe seleccionar un tamaño");
        }

        if (encuesta.getNivelActividad() == null || encuesta.getNivelActividad().trim().isEmpty()) {
            return ResultadoOperacion.error("Debe seleccionar un nivel de actividad");
        }

        if (encuesta.getCostoMantenimiento() == null || encuesta.getCostoMantenimiento().trim().isEmpty()) {
            return ResultadoOperacion.error("Debe seleccionar un costo de mantenimiento");
        }

        return ResultadoOperacion.exito("Encuesta válida");
    }
}
