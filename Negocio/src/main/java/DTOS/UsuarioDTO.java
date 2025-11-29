/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

public class UsuarioDTO {

    //Info Personal
    private Long id;
    private String nombre;
    private String curp;
    private String direccion;
    private String correo;
    private boolean adoptadoPreviamente;

    //Info Vivienda
    private String tipoVivienda;         
    private String condicionesHogar;     
    private String convivencia;          
    private String tiempoDisponibilidad; 

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombre, String curp, String direccion, String correo,String tipoVivienda, String condicionesHogar) {
        this.nombre = nombre;
        this.curp = curp;
        this.direccion = direccion;
        this.correo = correo;
        this.tipoVivienda = tipoVivienda;
        this.condicionesHogar = condicionesHogar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isAdoptadoPreviamente() {
        return adoptadoPreviamente;
    }

    public void setAdoptadoPreviamente(boolean adoptadoPreviamente) {
        this.adoptadoPreviamente = adoptadoPreviamente;
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

    public String getConvivencia() {
        return convivencia;
    }

    public void setConvivencia(String convivencia) {
        this.convivencia = convivencia;
    }

    public String getTiempoDisponibilidad() {
        return tiempoDisponibilidad;
    }

    public void setTiempoDisponibilidad(String tiempoDisponibilidad) {
        this.tiempoDisponibilidad = tiempoDisponibilidad;
    }
}
