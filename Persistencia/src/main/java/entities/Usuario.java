/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author Josel
 */
public class Usuario implements Serializable {

    private Long _id;
    private String nombre;
    private String curp;
    private String direccion;
    private String correo;
    private boolean historialAdopcion;

    private String tipoVivienda;
    private String condicionesHogar;
    private String otrasMascotasONinos;
    private String disponibilidadTiempo;

    public Usuario() {
    }

    public Usuario(Long _id, String nombre, String curp, String direccion, String correo, boolean historialAdopcion, String tipoVivienda, String condicionesHogar, String otrasMascotasONinos, String disponibilidadTiempo) {
        this._id = _id;
        this.nombre = nombre;
        this.curp = curp;
        this.direccion = direccion;
        this.correo = correo;
        this.historialAdopcion = historialAdopcion;
        this.tipoVivienda = tipoVivienda;
        this.condicionesHogar = condicionesHogar;
        this.otrasMascotasONinos = otrasMascotasONinos;
        this.disponibilidadTiempo = disponibilidadTiempo;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isHistorialAdopcion() {
        return historialAdopcion;
    }

    public void setHistorialAdopcion(boolean historialAdopcion) {
        this.historialAdopcion = historialAdopcion;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getCondicionesHogar() {
        return condicionesHogar;
    }

    public void setCondicionesHogar(String condicionesHogar) {
        this.condicionesHogar = condicionesHogar;
    }

    public String getOtrasMascotasONinos() {
        return otrasMascotasONinos;
    }

    public void setOtrasMascotasONinos(String otrasMascotasONinos) {
        this.otrasMascotasONinos = otrasMascotasONinos;
    }

    public String getDisponibilidadTiempo() {
        return disponibilidadTiempo;
    }

    public void setDisponibilidadTiempo(String disponibilidadTiempo) {
        this.disponibilidadTiempo = disponibilidadTiempo;
    }
}
