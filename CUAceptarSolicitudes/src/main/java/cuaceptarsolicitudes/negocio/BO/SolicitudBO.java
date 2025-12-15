package cuaceptarsolicitudes.negocio.BO;

import cuaceptarsolicitudes.dtos.SolicitudDTO;

/**
 * Implementación de validaciones de negocio para solicitudes de adopción.
 */
public class SolicitudBO implements ISolicitudBO {

    @Override
    public void validarSolicitud(SolicitudDTO solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }

        if (solicitud.getId() == null || solicitud.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la solicitud es obligatorio");
        }

        if (solicitud.getEstado() == null || solicitud.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de la solicitud es obligatorio");
        }
    }

    @Override
    public boolean puedeAceptarSolicitud(String idSolicitud) {
        if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean puedeRechazarSolicitud(String idSolicitud) {
        if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean puedeModificarSolicitud(String idSolicitud) {
        if (idSolicitud == null || idSolicitud.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
