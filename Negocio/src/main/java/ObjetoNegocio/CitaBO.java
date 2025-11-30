/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.CitaDTO;
import java.time.LocalDateTime;

public class CitaBO implements ICitaBO {

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
}
