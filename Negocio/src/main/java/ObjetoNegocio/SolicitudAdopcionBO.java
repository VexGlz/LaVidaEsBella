/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;


import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import DTOS.MascotaDTO;
import DTOS.RazonesAntecedentesDTO;
import daos.SolicitudAdopcionDAO;
import conexion.ConexionMongoDB;
import entities.SolicitudAdopcion;
import entities.RazonesAntecedentes;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class SolicitudAdopcionBO implements ISolicitudAdopcionBO {
    
    private SolicitudAdopcionDAO solicitudDAO;
    
    public SolicitudAdopcionBO() {
        this.solicitudDAO = new SolicitudAdopcionDAO(ConexionMongoDB.getInstancia().getDatabase());
    }
    
    @Override
    public void crearSolicitud(SolicitudAdopcionDTO solicitudDTO) {
        if (solicitudDTO != null) {
            SolicitudAdopcion solicitud = convertirAEntidad(solicitudDTO);
            solicitudDAO.guardar(solicitud);
        }
    }
    
    @Override
    public SolicitudAdopcionDTO buscarSolicitudPorId(Long id) {
        // Mismo problema con IDs Long vs ObjectId
        return null;
    }
    
    @Override
    public List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(Long idUsuario) {
        // Necesitamos el ObjectId del usuario, no el Long
        // Esto requeriría cambiar la firma o buscar el usuario primero
        return new ArrayList<>();
    }
    
    @Override
    public void actualizarEstadoSolicitud(Long id, String nuevoEstado) {
        // TODO: Implementar
    }

    @Override
    public void registraRA(SolicitudAdopcionDTO solicitud, String razones, String antecedentes) {
        if (solicitud != null) {
            if (solicitud.getRazones() == null) {
                solicitud.setRazones(new RazonesAntecedentesDTO());
            }
            solicitud.getRazones().setMotivoAdopcion(razones);
            solicitud.getRazones().setAntecedentesMascotas(antecedentes);
        }
    }

    @Override
    public UsuarioDTO obtieneUsuario(SolicitudAdopcionDTO solicitud) {
        return (solicitud != null) ? solicitud.getUsuario() : null;
    }

    @Override
    public MascotaDTO obtieneMascota(SolicitudAdopcionDTO solicitud) {
        return (solicitud != null) ? solicitud.getMascota() : null;
    }
    
    private SolicitudAdopcion convertirAEntidad(SolicitudAdopcionDTO dto) {
        if (dto == null) return null;
        SolicitudAdopcion entidad = new SolicitudAdopcion();
        // Mapeo de IDs es problemático sin ObjectIds en DTOs
        // entidad.setIdUsuario(...);
        // entidad.setIdMascota(...);
        entidad.setEstado(dto.getEstado());
        entidad.setFechaSolicitud(dto.getFechaSolicitud());
        
        if (dto.getRazones() != null) {
            RazonesAntecedentes razones = new RazonesAntecedentes();
            // Mapear campos de razones
            entidad.setRazones(razones);
        }
        
        return entidad;
    }
}
