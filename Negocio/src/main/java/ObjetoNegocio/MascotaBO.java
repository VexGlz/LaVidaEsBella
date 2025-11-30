/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;

import DTOS.MascotaDTO;

/**
 *
 * @author Josel
 */
public class MascotaBO implements IMascotaBO {

    @Override
    public void actualizaEstadoSalud(MascotaDTO mascota, String nuevoEstado) {
        if (mascota != null) {
            mascota.setEstadoSalud(nuevoEstado);
        }
    }

    @Override
    public void registraDueño(MascotaDTO mascota, Long idDueño) {
        if (mascota != null) {
            // Lógica de negocio: al tener dueño, deja de estar disponible
            mascota.setDisponible(false);
            // Aquí llamarías al DAO para persistir el cambio de dueño
        }
    }
}
