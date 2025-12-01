package utils;

import DTOS.MascotaDTO;
import negocio.subsistemas.mascotas.FachadaMascotas;
import negocio.subsistemas.mascotas.IMascotas;

/**
 * Script para insertar mascotas de prueba en la base de datos
 */
public class InsertarMascotasPrueba {

    public static void main(String[] args) {
        IMascotas fachadaMascotas = new FachadaMascotas();

        try {
            // Mascota 1: Perro
            MascotaDTO mascota1 = new MascotaDTO();
            mascota1.setNombre("Max");
            mascota1.setEspecie("Perro");
            mascota1.setEdad(3);
            mascota1.setEstadoSalud("Excelente - Vacunas al día");
            mascota1.setPersonalidad("Juguetón y amigable con niños");
            mascota1.setUrlImagen("/images/mascotas/perro1.jpg"); // Ruta relativa desde resources
            mascota1.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota1);
            System.out.println("✓ Mascota 1 registrada: " + mascota1.getNombre());

            // Mascota 2: Gato
            MascotaDTO mascota2 = new MascotaDTO();
            mascota2.setNombre("Luna");
            mascota2.setEspecie("Gato");
            mascota2.setEdad(2);
            mascota2.setEstadoSalud("Saludable - Esterilizada");
            mascota2.setPersonalidad("Tranquila e independiente");
            mascota2.setUrlImagen("/images/mascotas/gato1.jpg");
            mascota2.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota2);
            System.out.println("✓ Mascota 2 registrada: " + mascota2.getNombre());

            // Mascota 3: Perro
            MascotaDTO mascota3 = new MascotaDTO();
            mascota3.setNombre("Rocky");
            mascota3.setEspecie("Perro");
            mascota3.setEdad(5);
            mascota3.setEstadoSalud("Buena - Requiere ejercicio diario");
            mascota3.setPersonalidad("Enérgico y leal");
            mascota3.setUrlImagen("/images/mascotas/perro2.jpg");
            mascota3.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota3);
            System.out.println("✓ Mascota 3 registrada: " + mascota3.getNombre());

            // Mascota 4: Gato
            MascotaDTO mascota4 = new MascotaDTO();
            mascota4.setNombre("Michi");
            mascota4.setEspecie("Gato");
            mascota4.setEdad(1);
            mascota4.setEstadoSalud("Excelente - Joven y saludable");
            mascota4.setPersonalidad("Curioso y juguetón");
            mascota4.setUrlImagen("/images/mascotas/gato2.jpg");
            mascota4.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota4);
            System.out.println("✓ Mascota 4 registrada: " + mascota4.getNombre());

            // Mascota 5: Pájaro
            MascotaDTO mascota5 = new MascotaDTO();
            mascota5.setNombre("Piolín");
            mascota5.setEspecie("Pájaro");
            mascota5.setEdad(1);
            mascota5.setEstadoSalud("Saludable");
            mascota5.setPersonalidad("Cantarín y sociable");
            mascota5.setUrlImagen("/images/mascotas/pajaro1.jpg");
            mascota5.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota5);
            System.out.println("✓ Mascota 5 registrada: " + mascota5.getNombre());

            // Mascota 6: Reptil
            MascotaDTO mascota6 = new MascotaDTO();
            mascota6.setNombre("Verde");
            mascota6.setEspecie("Reptil");
            mascota6.setEdad(2);
            mascota6.setEstadoSalud("Buena - Requiere terrario");
            mascota6.setPersonalidad("Tranquilo y de bajo mantenimiento");
            mascota6.setUrlImagen("/images/mascotas/reptil1.jpg");
            mascota6.setDisponible(true);
            fachadaMascotas.registrarMascota(mascota6);
            System.out.println("✓ Mascota 6 registrada: " + mascota6.getNombre());

            System.out.println("\n========================================");
            System.out.println("✓ TODAS LAS MASCOTAS HAN SIDO REGISTRADAS EXITOSAMENTE");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("Error al insertar mascotas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
