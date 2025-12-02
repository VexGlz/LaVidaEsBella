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
    public SolicitudAdopcionDTO buscarSolicitudPorId(String id) {
        // Mismo problema con IDs Long vs ObjectId
        return null;
    }

    @Override
    public List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(String idUsuario) {
        // Necesitamos el ObjectId del usuario, no el Long
        // Esto requeriría cambiar la firma o buscar el usuario primero
        return new ArrayList<>();
    }

    @Override
    public void actualizarEstadoSolicitud(String id, String nuevoEstado) {
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
        if (dto == null)
            return null;
        SolicitudAdopcion entidad = new SolicitudAdopcion();
        if (dto.getUsuario() != null && dto.getUsuario().getId() != null) {
            entidad.setIdUsuario(new ObjectId(dto.getUsuario().getId()));
        }

        if (dto.getMascota() != null && dto.getMascota().getId() != null) {
            entidad.setIdMascota(new ObjectId(dto.getMascota().getId()));
        }

        entidad.setEstado(dto.getEstado());
        entidad.setFechaSolicitud(dto.getFechaSolicitud());

        if (dto.getRazones() != null) {
            RazonesAntecedentes razones = new RazonesAntecedentes();
            razones.setMotivoAdopcion(dto.getRazones().getMotivoAdopcion());
            razones.setAntecedentesMascotas(dto.getRazones().getAntecedentesMascotas());
            entidad.setRazones(razones);
        }

        return entidad;
    }
}
