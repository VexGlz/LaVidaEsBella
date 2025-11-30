/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import org.bson.types.ObjectId;
import java.time.LocalDateTime;

public class SolicitudAdopcion {

    private ObjectId id;

    private ObjectId idUsuario; // Referencia
    private ObjectId idMascota; // Referencia

    private RazonesAntecedentes razones; // Embebido

    private LocalDateTime fechaSolicitud;
    private String estado;

    public SolicitudAdopcion() {
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(ObjectId idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ObjectId getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(ObjectId idMascota) {
        this.idMascota = idMascota;
    }

    public RazonesAntecedentes getRazones() {
        return razones;
    }

    public void setRazones(RazonesAntecedentes razones) {
        this.razones = razones;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime f) {
        this.fechaSolicitud = f;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
