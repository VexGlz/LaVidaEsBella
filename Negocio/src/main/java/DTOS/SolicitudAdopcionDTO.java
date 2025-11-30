/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

import java.time.LocalDateTime;

public class SolicitudAdopcionDTO {

    private Long id;
    private UsuarioDTO usuario;
    private MascotaDTO mascota;
    private RazonesAntecedentesDTO razones; // Datos del paso 3

    private LocalDateTime fechaSolicitud;
    private String estado; // PENDIENTE, APROBADA, etc.
    private Long idCita;   // ID de la cita asociada

    public SolicitudAdopcionDTO() {
        this.razones = new RazonesAntecedentesDTO();
        this.fechaSolicitud = LocalDateTime.now();
    }

    // Getters y Setters
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

    public RazonesAntecedentesDTO getRazones() {
        return razones;
    }

    public void setRazones(RazonesAntecedentesDTO razones) {
        this.razones = razones;
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

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }
}
