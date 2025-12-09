package gestion.mascotasarchivadas.dtos;

/**
 * DTO para encapsular el resultado de operaciones del módulo.
 * Facilita la comunicación de estados de éxito/error a la capa de presentación.
 */
public class ResultadoOperacion {

    private boolean exitoso;
    private String mensaje;

    public ResultadoOperacion() {
    }

    public ResultadoOperacion(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    // Métodos de fábrica para crear resultados comunes
    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje);
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje);
    }

    // Getters y Setters
    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResultadoOperacion{" +
                "exitoso=" + exitoso +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
