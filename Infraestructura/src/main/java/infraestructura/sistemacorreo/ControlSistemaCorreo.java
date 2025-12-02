/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import infraestructura.dto.CorreoDTO;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Control que implementa la lógica de validación y envío de correos.
 * Soporta tanto envío SMTP tradicional como OAuth2 con Gmail API.
 * 
 * @author System
 */
public class ControlSistemaCorreo {

    // Regex simple para validación de correo
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String APPLICATION_NAME = "La Vida es Bella";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    // Modo de envío
    private boolean modoProduccion = false;
    private boolean usarOAuth2 = true; // Por defecto usa OAuth2

    public ControlSistemaCorreo() {
    }

    /**
     * Activa el modo de producción para envío real de correos.
     */
    public void activarModoProduccion() {
        this.modoProduccion = true;
    }

    /**
     * Desactiva el modo de producción (modo simulado).
     */
    public void desactivarModoProduccion() {
        this.modoProduccion = false;
    }

    /**
     * Configura si se debe usar OAuth2 o SMTP tradicional.
     * 
     * @param usar true para OAuth2, false para SMTP con contraseña
     */
    public void setUsarOAuth2(boolean usar) {
        this.usarOAuth2 = usar;
    }

    /**
     * Valida si un correo electrónico tiene un formato correcto.
     * 
     * @param correo Dirección de correo a validar.
     * @return true si es válido, false en caso contrario.
     */
    public boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    /**
     * Envía un correo electrónico (real o simulado según configuración).
     * 
     * @param correo DTO con la información del correo.
     * @throws Exception Si el correo es inválido o hay error en el envío.
     */
    public void enviarCorreo(CorreoDTO correo) throws Exception {
        // Validaciones
        if (correo == null) {
            throw new Exception("El objeto correo no puede ser nulo.");
        }

        if (!validarCorreo(correo.getDestinatario())) {
            throw new Exception("La dirección de correo destinatario es inválida: " + correo.getDestinatario());
        }

        if (correo.getAsunto() == null || correo.getAsunto().isEmpty()) {
            throw new Exception("El asunto del correo no puede estar vacío.");
        }

        if (correo.getMensaje() == null || correo.getMensaje().isEmpty()) {
            throw new Exception("El mensaje del correo no puede estar vacío.");
        }

        // Elegir entre envío real o simulado
        if (modoProduccion) {
            if (usarOAuth2) {
                enviarCorreoOAuth2(correo);
            } else {
                enviarCorreoSMTP(correo);
            }
        } else {
            enviarCorreoSimulado(correo);
        }
    }

    /**
     * Envía un correo usando Gmail API con OAuth2.
     * 
     * @param correo DTO con la información del correo.
     * @throws Exception Si hay error en el envío.
     */
    private void enviarCorreoOAuth2(CorreoDTO correo) throws Exception {
        try {
            // Obtener credenciales OAuth2
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = OAuth2TokenManager.getCredentials(HTTP_TRANSPORT);

            // Crear servicio de Gmail
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Crear mensaje MIME
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage email = new MimeMessage(session);

            email.setFrom(new InternetAddress("me")); // "me" = usuario autenticado
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(correo.getDestinatario()));
            email.setSubject(correo.getAsunto());
            email.setText(correo.getMensaje());

            // Codificar el mensaje
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = java.util.Base64.getUrlEncoder()
                    .encodeToString(rawMessageBytes);

            // Crear el mensaje de Gmail
            Message message = new Message();
            message.setRaw(encodedEmail);

            // Enviar
            message = service.users().messages().send("me", message).execute();

            System.out.println("✓ Correo enviado exitosamente (OAuth2) a: " + correo.getDestinatario());
            System.out.println("  ID del mensaje: " + message.getId());

        } catch (Exception e) {
            System.err.println("✗ Error al enviar correo con OAuth2: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al enviar el correo con OAuth2: " + e.getMessage(), e);
        }
    }

    /**
     * Envía un correo electrónico usando SMTP tradicional (requiere contraseña).
     * 
     * @param correo DTO con la información del correo.
     * @throws Exception Si hay error en el envío.
     */
    private void enviarCorreoSMTP(CorreoDTO correo) throws Exception {
        try {
            // Configurar propiedades del servidor SMTP
            Properties props = new Properties();
            props.put("mail.smtp.auth", String.valueOf(ConfiguracionCorreo.SMTP_AUTH));
            props.put("mail.smtp.starttls.enable", String.valueOf(ConfiguracionCorreo.SMTP_STARTTLS));
            props.put("mail.smtp.host", ConfiguracionCorreo.SMTP_HOST);
            props.put("mail.smtp.port", ConfiguracionCorreo.SMTP_PORT);

            // Crear sesión con autenticación
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            ConfiguracionCorreo.CORREO_REMITENTE,
                            ConfiguracionCorreo.PASSWORD_REMITENTE);
                }
            });

            // Crear mensaje
            javax.mail.Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(
                    ConfiguracionCorreo.CORREO_REMITENTE,
                    ConfiguracionCorreo.NOMBRE_REMITENTE));
            mensaje.setRecipients(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(correo.getDestinatario()));
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getMensaje());

            // Enviar correo
            Transport.send(mensaje);

            System.out.println("✓ Correo enviado exitosamente (SMTP) a: " + correo.getDestinatario());

        } catch (MessagingException e) {
            System.err.println("✗ Error al enviar correo con SMTP: " + e.getMessage());
            throw new Exception("Error al enviar el correo con SMTP: " + e.getMessage(), e);
        }
    }

    /**
     * Simula el envío de un correo electrónico (para testing).
     * 
     * @param correo DTO con la información del correo.
     */
    private void enviarCorreoSimulado(CorreoDTO correo) {
        System.out.println("=== SIMULACIÓN DE ENVÍO DE CORREO ===");
        System.out.println("Para: " + correo.getDestinatario());
        System.out.println("Asunto: " + correo.getAsunto());
        System.out.println("Mensaje: \n" + correo.getMensaje());
        System.out.println("=====================================");
        System.out.println("✓ Correo enviado exitosamente (Simulado)");
    }
}
