package infraestructura.sistemacorreo.test;

import infraestructura.dto.CorreoDTO;
import infraestructura.sistemacorreo.FachadaCorreo;

/**
 * Clase de prueba para verificar que el sistema de correos funciona
 * correctamente.
 * 
 * @author System
 */
public class PruebaEnvioCorreo {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE ENVÍO DE CORREO ===");

        try {
            // Crear instancia de la fachada de correo
            FachadaCorreo fachadaCorreo = new FachadaCorreo();
            System.out.println("✓ FachadaCorreo creada correctamente");

            // Crear correo de prueba
            String destinatario = "TU_CORREO_AQUI@gmail.com"; // <<< CAMBIAR POR TU CORREO
            String asunto = "Prueba de Sistema de Correos - La Vida Es Bella";
            String mensaje = "Este es un correo de prueba.\n\n"
                    + "Si recibes este mensaje, el sistema de correos está funcionando correctamente.\n\n"
                    + "Saludos,\n"
                    + "El equipo de La Vida Es Bella";

            CorreoDTO correo = new CorreoDTO(destinatario, asunto, mensaje);
            System.out.println("✓ CorreoDTO creado correctamente");
            System.out.println("  - Destinatario: " + destinatario);
            System.out.println("  - Asunto: " + asunto);

            // Enviar correo
            System.out.println("\n>> Enviando correo...");
            fachadaCorreo.enviarCorreo(correo);

            System.out.println("✓✓✓ CORREO ENVIADO EXITOSAMENTE ✓✓✓");
            System.out.println("\nRevisa tu bandeja de entrada en: " + destinatario);
            System.out.println("(También revisa la carpeta de spam/correo no deseado)");

        } catch (Exception e) {
            System.err.println("✗✗✗ ERROR AL ENVIAR CORREO ✗✗✗");
            System.err.println("Tipo de error: " + e.getClass().getName());
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("\nDetalles completos del error:");
            e.printStackTrace();
        }

        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}
