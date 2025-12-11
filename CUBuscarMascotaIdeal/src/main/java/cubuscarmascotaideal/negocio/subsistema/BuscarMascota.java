package cubuscarmascotaideal.negocio.subsistema;

import cubuscarmascotaideal.dtos.EncuestaDTO;
import cubuscarmascotaideal.dtos.MascotaResultadoDTO;
import cubuscarmascotaideal.negocio.adaptadores.AdaptadorMascotaResultado;
import cubuscarmascotaideal.negocio.BO.EncuestaBO;
import cubuscarmascotaideal.negocio.BO.IEncuestaBO;
import conexion.ConexionMongoDB;
import daos.MascotaDAO;
import entities.Mascota;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del subsistema de búsqueda de mascotas compatibles.
 * Calcula compatibilidad basada en: tamaño, nivel de actividad, peludo y costo.
 */
public class BuscarMascota implements IBuscarMascota {

    private final MascotaDAO mascotaDAO;
    private final IEncuestaBO encuestaBO;

    public BuscarMascota() {
        this.mascotaDAO = new MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());
        this.encuestaBO = new EncuestaBO();
    }

    // Constructor con inyección de dependencias (para testing)
    public BuscarMascota(MascotaDAO mascotaDAO, IEncuestaBO encuestaBO) {
        this.mascotaDAO = mascotaDAO;
        this.encuestaBO = encuestaBO;
    }

    @Override
    public List<MascotaResultadoDTO> buscarMascotasCompatibles(EncuestaDTO encuesta) {
        // Validar encuesta
        if (!encuestaBO.validarEncuesta(encuesta)) {
            System.err.println("Encuesta inválida");
            return new ArrayList<>();
        }

        // Obtener todas las mascotas disponibles
        List<Mascota> mascotasDisponibles = mascotaDAO.buscarPorDisponibilidad(true);

        // Calcular compatibilidad para cada una
        List<MascotaResultadoDTO> resultados = new ArrayList<>();
        for (Mascota mascota : mascotasDisponibles) {
            double porcentaje = calcularCompatibilidad(encuesta, mascota);
            MascotaResultadoDTO resultado = AdaptadorMascotaResultado.entidadADTO(mascota, porcentaje);
            resultados.add(resultado);
        }

        // Ordenar por compatibilidad (mayor a menor)
        resultados.sort(Comparator.comparingDouble(MascotaResultadoDTO::getPorcentajeCompatibilidad).reversed());

        // Filtrar solo las que tienen al menos 50% de compatibilidad
        return resultados.stream()
                .filter(r -> r.getPorcentajeCompatibilidad() >= 50.0)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaResultadoDTO> obtenerTodasConCompatibilidad(EncuestaDTO encuesta) {
        if (!encuestaBO.validarEncuesta(encuesta)) {
            return new ArrayList<>();
        }

        List<Mascota> todasMascotas = mascotaDAO.buscarPorDisponibilidad(true);
        List<MascotaResultadoDTO> resultados = new ArrayList<>();

        for (Mascota mascota : todasMascotas) {
            double porcentaje = calcularCompatibilidad(encuesta, mascota);
            resultados.add(AdaptadorMascotaResultado.entidadADTO(mascota, porcentaje));
        }

        resultados.sort(Comparator.comparingDouble(MascotaResultadoDTO::getPorcentajeCompatibilidad).reversed());
        return resultados;
    }

    /**
     * Calcula el porcentaje de compatibilidad entre encuesta y mascota.
     * Cada criterio tiene el mismo peso (25% cada uno).
     * 
     * @param encuesta Preferencias del usuario
     * @param mascota  Mascota a evaluar
     * @return Porcentaje de compatibilidad (0-100)
     */
    private double calcularCompatibilidad(EncuestaDTO encuesta, Mascota mascota) {
        int puntos = 0;
        int totalCriterios = 4;

        // Criterio 1: Tamaño (25%)
        if (encuesta.getTamano() != null && encuesta.getTamano().equalsIgnoreCase(mascota.getTamano())) {
            puntos++;
        }

        // Criterio 2: Nivel de actividad (25%)
        if (encuesta.getNivelActividad() != null
                && encuesta.getNivelActividad().equalsIgnoreCase(mascota.getNivelActividad())) {
            puntos++;
        }

        // Criterio 3: Peludo (25%)
        if (encuesta.isPeludo() == mascota.isPeludo()) {
            puntos++;
        }

        // Criterio 4: Costo de mantenimiento (25%)
        if (encuesta.getCostoMantenimiento() != null
                && encuesta.getCostoMantenimiento().equalsIgnoreCase(mascota.getCostoMantenimiento())) {
            puntos++;
        }

        // Calcular porcentaje
        return (puntos * 100.0) / totalCriterios;
    }
}
