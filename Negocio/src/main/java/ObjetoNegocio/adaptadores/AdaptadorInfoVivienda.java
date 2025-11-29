/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio.adaptadores;

/**
 *
 * @author Josel
 */

import negocio.dto.InfoPersonalDTO;
import persistencia.entidades.InfoPersonal;

public class AdaptadorInfoPersonal {

    public InfoPersonal toEntity(InfoPersonalDTO dto) {
        if (dto == null) return null;
        
        InfoPersonal entity = new InfoPersonal();
        entity.setNombre(dto.getNombre());
        entity.setCurp(dto.getCurp());
        entity.setDireccion(dto.getDireccion());
        entity.setCorreo(dto.getCorreo());
        entity.setTelefono(dto.getTelefono());
        
        return entity;
    }

    public InfoPersonalDTO toDTO(InfoPersonal entity) {
        if (entity == null) return null;
        
        InfoPersonalDTO dto = new InfoPersonalDTO();
        dto.setNombre(entity.getNombre());
        dto.setCurp(entity.getCurp());
        dto.setDireccion(entity.getDireccion());
        dto.setCorreo(entity.getCorreo());
        dto.setTelefono(entity.getTelefono());
        
        return dto;
    }
}
