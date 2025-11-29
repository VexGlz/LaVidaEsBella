/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

import java.time.LocalDateTime;

public class SolicitudAdopcionDTO {

    //Solicitud
    private Long id;
    private UsuarioDTO usuario;
    private MascotaDTO mascota;
    private LocalDateTime fechaSolicitud;
    private String estado;

    //Info Razones
    private String motivoAdopcion;
    private String antecedentesMascotas;
    private boolean aceptaCondicionesSeguimiento;

    public SolicitudAdopcionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public MascotaDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaDTO mascota) {
        this.mascota = mascota;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoAdopcion() {
        return motivoAdopcion;
    }

    public void setMotivoAdopcion(String motivoAdopcion) {
        this.motivoAdopcion = motivoAdopcion;
    }

    public String getAntecedentesMascotas() {
        return antecedentesMascotas;
    }

    public void setAntecedentesMascotas(String antecedentesMascotas) {
        this.antecedentesMascotas = antecedentesMascotas;
    }

    public boolean isAceptaCondicionesSeguimiento() {
        return aceptaCondicionesSeguimiento;
    }

    public void setAceptaCondicionesSeguimiento(boolean aceptaCondicionesSeguimiento) {
        this.aceptaCondicionesSeguimiento = aceptaCondicionesSeguimiento;
    }
}
