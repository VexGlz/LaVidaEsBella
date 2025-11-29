/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio.adaptadores;

/**
 *
 * @author Josel
 */

import negocio.dto.SolicitudAdopcionDTO;
import persistencia.entidades.SolicitudAdopcion;
// import org.bson.types.ObjectId;

public class AdaptadorSolicitud {
    
    private AdaptadorUsuario adpUsuario = new AdaptadorUsuario();
    private AdaptadorMascota adpMascota = new AdaptadorMascota();
    private AdaptadorRazonesAntecedentes adpRazones = new AdaptadorRazonesAntecedentes();

    public SolicitudAdopcion toEntity(SolicitudAdopcionDTO dto) {
        if (dto == null) return null;
        
        SolicitudAdopcion entity = new SolicitudAdopcion();
        
        entity.setUsuario(adpUsuario.toEntity(dto.getUsuario()));
        // entity.setMascota(adpMascota.toEntity(dto.getMascota())); // Si aplica mapeo completo
        
        entity.setFechaSolicitud(dto.getFechaSolicitud());
        entity.setEstado(dto.getEstado());

        // Usamos el adaptador específico para Razones
        entity.setRazones(adpRazones.toEntity(dto.getRazones()));
        
        return entity;
    }

    public SolicitudAdopcionDTO toDTO(SolicitudAdopcion entity) {
        if (entity == null) return null;
        
        SolicitudAdopcionDTO dto = new SolicitudAdopcionDTO();
        
        dto.setUsuario(adpUsuario.toDTO(entity.getUsuario()));
        // dto.setMascota(adpMascota.toDTO(entity.getMascota()));
        
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setEstado(entity.getEstado());
        
        // Usamos el adaptador específico para Razones
        dto.setRazones(adpRazones.toDTO(entity.getRazones()));
        
        return dto;
    }
}
