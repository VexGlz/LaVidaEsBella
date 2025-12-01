/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.subsistemas.adopciones;

import DTOS.SolicitudAdopcionDTO;
import ObjetoNegocio.ISolicitudAdopcionBO;
import ObjetoNegocio.SolicitudAdopcionBO;
import java.util.List;

/**
 * Fachada para el subsistema de adopciones
 * @author System
 */
public class FachadaAdopciones implements IAdopciones {
    
    private ISolicitudAdopcionBO solicitudBO;
    
    public FachadaAdopciones() {
        this.solicitudBO = new SolicitudAdopcionBO();
    }

    @Override
    public void crearSolicitud(SolicitudAdopcionDTO solicitud) throws Exception {
        solicitudBO.crearSolicitud(solicitud);
    }

    @Override
    public SolicitudAdopcionDTO buscarSolicitudPorId(Long id) throws Exception {
        return solicitudBO.buscarSolicitudPorId(id);
    }

    @Override
    public List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(Long idUsuario) throws Exception {
        return solicitudBO.buscarSolicitudesPorUsuario(idUsuario);
    }

    @Override
    public void actualizarEstadoSolicitud(Long id, String nuevoEstado) throws Exception {
        solicitudBO.actualizarEstadoSolicitud(id, nuevoEstado);
    }
}
