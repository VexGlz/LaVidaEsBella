/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

/**
 * Configuración para el servidor SMTP de correo.
 * 
 * @author System
 */
public class ConfiguracionCorreo {

    // Configuración del servidor SMTP (Gmail)
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final boolean SMTP_AUTH = true;
    public static final boolean SMTP_STARTTLS = true;

    // Credenciales del remitente
    public static final String CORREO_REMITENTE = "lavidaesbellavet@gmail.com";
    public static final String PASSWORD_REMITENTE = "maylcheessxlcnkb"; // Contraseña de aplicación (sin espacios)

    // Nombre del remitente que aparecerá en el correo
    public static final String NOMBRE_REMITENTE = "La Vida es Bella - Sistema de Adopciones";
}
