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
import daos.MascotaDAO;
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
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            ObjectId objectId = new ObjectId(id);
            SolicitudAdopcion entidad = solicitudDAO.buscarPorId(objectId);
            if (entidad == null) {
                return null;
            }

            // Mapear a DTO (reutilizando lógica si es posible, o duplicando por ahora para
            // rapidez)
            SolicitudAdopcionDTO dto = new SolicitudAdopcionDTO();
            dto.setId(entidad.getId().toHexString());
            dto.setEstado(entidad.getEstado());
            dto.setFechaSolicitud(entidad.getFechaSolicitud());

            // Mapear Usuario (solo ID)
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(entidad.getIdUsuario().toHexString());
            dto.setUsuario(usuarioDTO);

            // Mapear Mascota
            if (entidad.getIdMascota() != null) {
                daos.MascotaDAO mascotaDAO = new daos.MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());
                entities.Mascota mascotaEntidad = mascotaDAO.buscarPorId(entidad.getIdMascota());
                if (mascotaEntidad != null) {
                    MascotaDTO mascotaDTO = new MascotaDTO();
                    mascotaDTO.setId(mascotaEntidad.getId().toHexString());
                    mascotaDTO.setNombre(mascotaEntidad.getNombre());
                    mascotaDTO.setUrlImagen(mascotaEntidad.getUrlImagen());
                    mascotaDTO.setEspecie(mascotaEntidad.getEspecie());
                    mascotaDTO.setEdad(mascotaEntidad.getEdad());
                    mascotaDTO.setPersonalidad(mascotaEntidad.getPersonalidad());
                    mascotaDTO.setEstadoSalud(mascotaEntidad.getEstadoSalud());
                    mascotaDTO.setDisponible(mascotaEntidad.isDisponible());
                    mascotaDTO.setEstado(mascotaEntidad.getEstado());
                    dto.setMascota(mascotaDTO);
                }
            }

            return dto;
        } catch (IllegalArgumentException e) {
            System.err.println("ID de solicitud inválido: " + id);
            return null;
        }
    }

    @Override
    public List<SolicitudAdopcionDTO> buscarSolicitudesPorUsuario(String idUsuario) {
        List<SolicitudAdopcionDTO> resultados = new ArrayList<>();

        if (idUsuario == null || idUsuario.isEmpty()) {
            return resultados;
        }

        try {
            ObjectId objectId = new ObjectId(idUsuario);
            List<SolicitudAdopcion> solicitudes = solicitudDAO.buscarPorUsuario(objectId);

            daos.MascotaDAO mascotaDAO = new daos.MascotaDAO(ConexionMongoDB.getInstancia().getDatabase());

            for (SolicitudAdopcion entidad : solicitudes) {
                SolicitudAdopcionDTO dto = new SolicitudAdopcionDTO();
                dto.setId(entidad.getId().toHexString());
                dto.setEstado(entidad.getEstado());
                dto.setFechaSolicitud(entidad.getFechaSolicitud());

                // Mapear Usuario (solo ID por ahora)
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId(entidad.getIdUsuario().toHexString());
                dto.setUsuario(usuarioDTO);

                // Mapear Mascota
                if (entidad.getIdMascota() != null) {
                    entities.Mascota mascotaEntidad = mascotaDAO.buscarPorId(entidad.getIdMascota());
                    if (mascotaEntidad != null) {
                        MascotaDTO mascotaDTO = new MascotaDTO();
                        mascotaDTO.setId(mascotaEntidad.getId().toHexString());
                        mascotaDTO.setNombre(mascotaEntidad.getNombre());
                        mascotaDTO.setUrlImagen(mascotaEntidad.getUrlImagen());
                        mascotaDTO.setEspecie(mascotaEntidad.getEspecie());
                        mascotaDTO.setEdad(mascotaEntidad.getEdad());
                        mascotaDTO.setPersonalidad(mascotaEntidad.getPersonalidad());
                        mascotaDTO.setEstadoSalud(mascotaEntidad.getEstadoSalud());
                        mascotaDTO.setDisponible(mascotaEntidad.isDisponible());
                        mascotaDTO.setEstado(mascotaEntidad.getEstado());
                        dto.setMascota(mascotaDTO);
                    }
                }

                // Mapear Razones
                if (entidad.getRazones() != null) {
                    RazonesAntecedentesDTO razonesDTO = new RazonesAntecedentesDTO();
                    razonesDTO.setMotivoAdopcion(entidad.getRazones().getMotivoAdopcion());
                    razonesDTO.setAntecedentesMascotas(entidad.getRazones().getAntecedentesMascotas());
                    razonesDTO.setAceptaSeguimiento(entidad.getRazones().isAceptaSeguimiento());
                    dto.setRazones(razonesDTO);
                }

                resultados.add(dto);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("ID de usuario inválido: " + idUsuario);
        }

        return resultados;
    }

    @Override
    public void actualizarEstadoSolicitud(String id, String nuevoEstado) {
        if (id != null && !id.isEmpty()) {
            try {
                ObjectId objectId = new ObjectId(id);
                // Necesitamos un método en DAO para actualizar solo el estado
                // O buscamos, actualizamos y guardamos
                SolicitudAdopcion solicitud = solicitudDAO.buscarPorId(objectId);
                if (solicitud != null) {
                    solicitud.setEstado(nuevoEstado);
                    // Usamos guardar para actualizar (upsert o replace)
                    // El DAO actual usa insertOne, necesitamos update o replace
                    // Vamos a añadir actualizar al DAO o usar guardar si maneja updates (insertOne
                    // falla con duplicado _id)
                    solicitudDAO.actualizar(solicitud);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("ID inválido para actualizar: " + id);
            }
        }
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
