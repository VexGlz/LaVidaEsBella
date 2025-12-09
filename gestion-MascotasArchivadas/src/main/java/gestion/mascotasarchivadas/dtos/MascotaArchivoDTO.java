package gestion.mascotasarchivadas.dtos;

/**
 * DTO para transferir información de mascotas archivadas.
 * Contiene los mismos campos que CatalogoDTO, adaptado para el contexto
 * de mascotas archivadas (estado = "baja").
 */
public class MascotaArchivoDTO {

    private String id;
    private String nombre;
    private String especie;
    private int edad;
    private String estadoSalud;
    private String personalidad;
    private String urlImagen;
    private String estado;
    private boolean disponible;
    private String expedienteMedico;
    private String color;
    private String raza;
    private double peso;
    private String alergias;
    private String condicionesEspeciales;
    private String nivelEnergia;

    // Constructor vacío
    public MascotaArchivoDTO() {
    }

    // Constructor completo
    public MascotaArchivoDTO(String id, String nombre, String especie, int edad,
            String estadoSalud, String personalidad, String urlImagen,
            String estado, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.estadoSalud = estadoSalud;
        this.personalidad = personalidad;
        this.urlImagen = urlImagen;
        this.estado = estado;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getPersonalidad() {
        return personalidad;
    }

    public void setPersonalidad(String personalidad) {
        this.personalidad = personalidad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getExpedienteMedico() {
        return expedienteMedico;
    }

    public void setExpedienteMedico(String expedienteMedico) {
        this.expedienteMedico = expedienteMedico;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getCondicionesEspeciales() {
        return condicionesEspeciales;
    }

    public void setCondicionesEspeciales(String condicionesEspeciales) {
        this.condicionesEspeciales = condicionesEspeciales;
    }

    public String getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(String nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    @Override
    public String toString() {
        return "MascotaArchivoDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", edad=" + edad +
                ", estado='" + estado + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
