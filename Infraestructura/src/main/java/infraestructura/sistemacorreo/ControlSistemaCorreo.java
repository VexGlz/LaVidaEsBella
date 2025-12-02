/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

import infraestructura.dto.CorreoDTO;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Control que implementa la lógica de validación y envío de correos con SMTP.
 * 
 * @author System
 */
public class ControlSistemaCorreo {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public ControlSistemaCorreo() {
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
     * Envía un correo electrónico usando SMTP.
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

        // Enviar correo
        enviarCorreoSMTP(correo);
    }

    /**
     * Envía un correo electrónico usando SMTP.
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
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(
                    ConfiguracionCorreo.CORREO_REMITENTE,
                    ConfiguracionCorreo.NOMBRE_REMITENTE));
            mensaje.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(correo.getDestinatario()));
            mensaje.setSubject(correo.getAsunto());
            mensaje.setText(correo.getMensaje());

            // Enviar correo
            Transport.send(mensaje);

        } catch (MessagingException e) {
            throw new Exception("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
