package cuaceptarsolicitudes.control;

import cuaceptarsolicitudes.dtos.ResultadoOperacion;
import cuaceptarsolicitudes.dtos.SolicitudDTO;

import java.util.List;

/**
 * Fachada principal del módulo de aceptar solicitudes (Boundary).
 * Proporciona una interfaz simplificada para la capa de presentación,
 * maneja excepciones y traduce errores a mensajes de usuario.
 */
public class ControlPresentacion {

    private final ControlSubsistemas controlSubsistemas;

    public ControlPresentacion() {
        // Inicializar con implementación por defecto
        this.controlSubsistemas = new ControlSubsistemas();
    }

    // Constructor con inyección de dependencias
    public ControlPresentacion(ControlSubsistemas controlSubsistemas) {
        this.controlSubsistemas = controlSubsistemas;
    }

    /**
     * Obtiene todas las solicitudes de adopción.
     * 
     * @return Lista de solicitudes o lista vacía en caso de error
     */
    public List<SolicitudDTO> obtenerSolicitudes() {
        try {
            return controlSubsistemas.obtenerSolicitudes();
        } catch (Exception e) {
            System.err.println("Error al obtener solicitudes: " + e.getMessage());
            return List.of(); // Retornar lista vacía en caso de error
        }
    }

    /**
     * Acepta una solicitud de adopción.
     * 
     * @param idSolicitud ID de la solicitud a aceptar
     * @param idAdmin     ID del administrador que acepta la solicitud
     * @return Resultado de la operación
     */
    public ResultadoOperacion aceptarSolicitud(String idSolicitud, String idAdmin) {
        try {
            // Validar ID
            if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
                return ResultadoOperacion.error("El ID de la solicitud es obligatorio");
            }

            boolean exito = controlSubsistemas.aceptarSolicitud(idSolicitud, idAdmin);

            if (exito) {
                return ResultadoOperacion.exito("Solicitud aceptada exitosamente. Se ha enviado un correo al usuario.");
            } else {
                return ResultadoOperacion.error("No se pudo aceptar la solicitud");
            }

        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.error("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al aceptar solicitud: " + e.getMessage());
            e.printStackTrace();
            return ResultadoOperacion.error("Error inesperado al aceptar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Rechaza una solicitud de adopción.
     * 
     * @param idSolicitud ID de la solicitud a rechazar
     * @param idAdmin     ID del administrador que rechaza la solicitud
     * @return Resultado de la operación
     */
    public ResultadoOperacion rechazarSolicitud(String idSolicitud, String idAdmin) {
        try {
            // Validar ID
            if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
                return ResultadoOperacion.error("El ID de la solicitud es obligatorio");
            }

            boolean exito = controlSubsistemas.rechazarSolicitud(idSolicitud, idAdmin);

            if (exito) {
                return ResultadoOperacion
                        .exito("Solicitud rechazada exitosamente. Se ha enviado un correo al usuario.");
            } else {
                return ResultadoOperacion.error("No se pudo rechazar la solicitud");
            }

        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.error("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al rechazar solicitud: " + e.getMessage());
            e.printStackTrace();
            return ResultadoOperacion.error("Error inesperado al rechazar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Marca una solicitud como pendiente de modificación.
     * 
     * @param idSolicitud       ID de la solicitud
     * @param idAdmin           ID del administrador que solicita la modificación
     * @param razonModificacion Razón por la cual se requiere modificación
     * @return Resultado de la operación
     */
    public ResultadoOperacion modificarSolicitud(String idSolicitud, String idAdmin, String razonModificacion) {
        try {
            // Validar parámetros
            if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
                return ResultadoOperacion.error("El ID de la solicitud es obligatorio");
            }

            if (razonModificacion == null || razonModificacion.trim().isEmpty()) {
                return ResultadoOperacion.error("La razón de modificación es obligatoria");
            }

            boolean exito = controlSubsistemas.modificarSolicitud(idSolicitud, idAdmin, razonModificacion);

            if (exito) {
                return ResultadoOperacion
                        .exito("Solicitud marcada como pendiente de modificación. Se ha enviado un correo al usuario.");
            } else {
                return ResultadoOperacion.error("No se pudo modificar la solicitud");
            }

        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.error("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al modificar solicitud: " + e.getMessage());
            e.printStackTrace();
            return ResultadoOperacion.error("Error inesperado al modificar la solicitud: " + e.getMessage());
        }
    }
}
