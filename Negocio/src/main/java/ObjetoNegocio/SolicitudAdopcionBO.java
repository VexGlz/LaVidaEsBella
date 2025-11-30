/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObjetoNegocio;


import DTOS.SolicitudAdopcionDTO;
import DTOS.UsuarioDTO;
import DTOS.MascotaDTO;
import DTOS.RazonesAntecedentesDTO; // Si usas el DTO compuesto

public class SolicitudAdopcionBO implements ISolicitudAdopcionBO {

    @Override
    public void registraRA(SolicitudAdopcionDTO solicitud, String razones, String antecedentes) {
        if (solicitud != null) {
            // Si usas DTOs segregados, actualizamos el objeto interno
            if (solicitud.getRazones() == null) {
                solicitud.setRazones(new RazonesAntecedentesDTO());
            }
            solicitud.getRazones().setMotivoAdopcion(razones);
            solicitud.getRazones().setAntecedentesMascotas(antecedentes);

            // Si usas el DTO plano original, sería:
            // solicitud.setMotivoAdopcion(razones);
            // solicitud.setAntecedentesMascotas(antecedentes);
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
}
