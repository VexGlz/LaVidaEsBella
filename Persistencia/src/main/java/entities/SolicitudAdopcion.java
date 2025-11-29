/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Josel
 */
public class SolicitudAdopcion implements Serializable {

    private Long id;
    private Usuario usuario;
    private Mascota mascota;
    private LocalDateTime fechaSolicitud;
    private String estado;

    private String motivoAdopcion;
    private String antecedentesMascotas;
    private boolean terminosAceptados;

    public SolicitudAdopcion() {
    }

    public SolicitudAdopcion(Long id, Usuario usuario, Mascota mascota, LocalDateTime fechaSolicitud, String estado, String motivoAdopcion, String antecedentesMascotas, boolean terminosAceptados) {
        this.id = id;
        this.usuario = usuario;
        this.mascota = mascota;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.motivoAdopcion = motivoAdopcion;
        this.antecedentesMascotas = antecedentesMascotas;
        this.terminosAceptados = terminosAceptados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
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

    public boolean isTerminosAceptados() {
        return terminosAceptados;
    }

    public void setTerminosAceptados(boolean terminosAceptados) {
        this.terminosAceptados = terminosAceptados;
    }
}
