/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ObjetoNegocio;

import DTOS.MascotaDTO;

/**
 *
 * @author Josel
 */
public interface IMascotaBO {
    
    void registrarMascota(MascotaDTO mascota);
    
    MascotaDTO buscarMascotaPorId(Long id);
    
    java.util.List<MascotaDTO> buscarTodasLasMascotas();
    
    java.util.List<MascotaDTO> buscarMascotasDisponibles();
    
    void actualizarMascota(MascotaDTO mascota);
    
    void actualizaEstadoSalud(MascotaDTO mascota, String nuevoEstado);
    
    void registraDueño(MascotaDTO mascota, Long idDueño);
}
