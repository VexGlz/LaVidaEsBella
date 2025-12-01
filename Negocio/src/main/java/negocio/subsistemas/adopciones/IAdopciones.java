/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.subsistemas.adopciones;

import DTOS.SolicitudAdopcionDTO;
import java.util.List;

/**
 * Interfaz para el subsistema de adopciones
 * @author System
 */
public interface IAdopciones {
    
    void crearSolicitud(SolicitudAdopcionDTO solicitud) throws Exception;
    
    SolicitudAdopcionDTO buscarSolicitudPorId(Long id) throws Exception;
    
    List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(Long idUsuario) throws Exception;
    
    void actualizarEstadoSolicitud(Long id, String nuevoEstado) throws Exception;
    
}
