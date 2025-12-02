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

    // Configuración del servidor SMTP (Gmail por defecto)
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final boolean SMTP_AUTH = true;
    public static final boolean SMTP_STARTTLS = true;

    // Credenciales del remitente
    // IMPORTANTE: Cambiar estos valores por tu correo y contraseña de aplicación
    public static final String CORREO_REMITENTE = "lavidaesbellaadopcion@gmail.com";
    public static final String PASSWORD_REMITENTE = "tu-contraseña-de-aplicacion";

    // Nombre del remitente que aparecerá en el correo
    public static final String NOMBRE_REMITENTE = "La Vida es Bella - Sistema de Adopciones";

    /**
     * NOTA IMPORTANTE SOBRE LA CONTRASEÑA:
     * 
     * Para Gmail, NO uses tu contraseña normal. Debes generar una "Contraseña de
     * Aplicación":
     * 
     * 1. Ir a https://myaccount.google.com/
     * 2. Seguridad → Verificación en dos pasos (activar si no está activa)
     * 3. Contraseñas de aplicaciones
     * 4. Seleccionar "Correo" y "Otro (nombre personalizado)"
     * 5. Copiar la contraseña de 16 caracteres generada
     * 6. Pegar esa contraseña en PASSWORD_REMITENTE
     * 
     * Para otros proveedores (Outlook, Yahoo, etc.), consulta su documentación.
     */
}
