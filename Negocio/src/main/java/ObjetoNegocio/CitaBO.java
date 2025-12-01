/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.CitaDTO;
import daos.CitaDAO;
import conexion.ConexionMongoDB;
import entities.Cita;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaBO implements ICitaBO {
    
    private CitaDAO citaDAO;
    
    public CitaBO() {
        this.citaDAO = new CitaDAO(ConexionMongoDB.getInstancia().getDatabase());
    }

    @Override
    public void agendarCita(CitaDTO citaDTO) {
        if (citaDTO != null) {
            Cita cita = convertirAEntidad(citaDTO);
            citaDAO.guardar(cita);
        }
    }
    
    @Override
    public CitaDTO buscarCitaPorId(Long id) {
        return null; // Problema de ID
    }
    
    @Override
    public List<CitaDTO> buscarCitasPorUsuario(Long idUsuario) {
        return new ArrayList<>(); // Problema de ID
    }

    @Override
    public LocalDateTime obtieneFechaHora(CitaDTO cita) {
        if (cita != null) {
            return cita.getFechaHora();
        }
        return null;
    }

    @Override
    public void asociaUsuario(CitaDTO cita, Long idUsuario) {
        if (cita != null) {
            cita.setIdUsuario(idUsuario);
        }
    }
    
    private Cita convertirAEntidad(CitaDTO dto) {
        if (dto == null) return null;
        Cita entidad = new Cita();
        entidad.setFechaHora(dto.getFechaHora());
        // Mapeo de IDs pendiente
        return entidad;
    }
}
