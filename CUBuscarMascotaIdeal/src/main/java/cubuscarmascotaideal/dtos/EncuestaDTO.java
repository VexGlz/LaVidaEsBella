package cubuscarmascotaideal.dtos;

/**
 * DTO para las preferencias de encuesta del usuario.
 * Captura los criterios de búsqueda para encontrar mascotas compatibles.
 */
public class EncuestaDTO {

    private String tamano; // pequeño, mediano, grande
    private String nivelActividad; // bajo, medio, alto
    private boolean peludo;
    private String costoMantenimiento; // bajo, medio, alto

    public EncuestaDTO() {
    }

    public EncuestaDTO(String tamano, String nivelActividad, boolean peludo, String costoMantenimiento) {
        this.tamano = tamano;
        this.nivelActividad = nivelActividad;
        this.peludo = peludo;
        this.costoMantenimiento = costoMantenimiento;
    }

    // Getters y Setters
    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(String nivelActividad) {
        this.nivelActividad = nivelActividad;
    }

    public boolean isPeludo() {
        return peludo;
    }

    public void setPeludo(boolean peludo) {
        this.peludo = peludo;
    }

    public String getCostoMantenimiento() {
        return costoMantenimiento;
    }

    public void setCostoMantenimiento(String costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
    }

    @Override
    public String toString() {
        return "EncuestaDTO{" +
                "tamano='" + tamano + '\'' +
                ", nivelActividad='" + nivelActividad + '\'' +
                ", peludo=" + peludo +
                ", costoMantenimiento='" + costoMantenimiento + '\'' +
                '}';
    }
}
