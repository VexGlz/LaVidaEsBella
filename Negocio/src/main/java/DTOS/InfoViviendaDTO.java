/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;


public class InfoViviendaDTO {

    private String tipoVivienda;         
    private String condicionesHogar;     
    private boolean tieneOtrasMascotas;
    private boolean tieneNinos;
    private String tiempoDisponibilidad; 

    public InfoViviendaDTO() {
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

    public boolean isTieneOtrasMascotas() {
        return tieneOtrasMascotas;
    }

    public void setTieneOtrasMascotas(boolean tieneOtrasMascotas) {
        this.tieneOtrasMascotas = tieneOtrasMascotas;
    }

    public boolean isTieneNinos() {
        return tieneNinos;
    }

    public void setTieneNinos(boolean tieneNinos) {
        this.tieneNinos = tieneNinos;
    }

    public String getTiempoDisponibilidad() {
        return tiempoDisponibilidad;
    }

    public void setTiempoDisponibilidad(String tiempoDisponibilidad) {
        this.tiempoDisponibilidad = tiempoDisponibilidad;
    }
}
