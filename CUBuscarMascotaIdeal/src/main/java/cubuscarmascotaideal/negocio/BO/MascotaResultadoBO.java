package cubuscarmascotaideal.negocio.BO;

import cubuscarmascotaideal.dtos.MascotaResultadoDTO;

/**
 * Business Object para validar resultados de búsqueda de mascotas.
 */
public class MascotaResultadoBO implements IMascotaResultadoBO {

    @Override
    public boolean validarResultado(MascotaResultadoDTO resultado) {
        if (resultado == null) {
            return false;
        }

        return esPorcentajeValido(resultado.getPorcentajeCompatibilidad()) &&
                tieneDatosCompletos(resultado);
    }

    @Override
    public boolean esPorcentajeValido(double porcentaje) {
        return porcentaje >= 0.0 && porcentaje <= 100.0;
    }

    @Override
    public boolean tieneDatosCompletos(MascotaResultadoDTO resultado) {
        return resultado.getId() != null && !resultado.getId().trim().isEmpty() &&
                resultado.getNombre() != null && !resultado.getNombre().trim().isEmpty() &&
                resultado.getEspecie() != null && !resultado.getEspecie().trim().isEmpty();
    }
}
