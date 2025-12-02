package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Clase de utilidad para insertar datos de prueba en MongoDB
 * Ejecutar esta clase para poblar la base de datos
 */
public class InsertarDatosPrueba {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "lavidaesbella";

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            System.out.println("Conectado a la base de datos: " + DATABASE_NAME);

            // 1. Insertar Mascotas
            insertarMascotas(database);

            // 2. Insertar Usuario de Prueba
            insertarUsuarioPrueba(database);

            // 3. Insertar Citas Disponibles
            insertarCitasDisponibles(database);

            System.out.println("¡Datos de prueba insertados exitosamente!");

        } catch (Exception e) {
            System.err.println("Error al insertar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertarMascotas(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("mascotas");

        // Verificar si ya existen datos
        if (collection.countDocuments() > 0) {
            System.out.println("La colección 'mascotas' ya tiene datos. Saltando inserción.");
            return;
        }

        Document m1 = new Document()
                .append("nombre", "Max")
                .append("especie", "Perro")
                .append("raza", "Golden Retriever")
                .append("edad", 3)
                .append("sexo", "Macho")
                .append("tamano", "Grande")
                .append("descripcion", "Muy juguetón y amigable con niños.")
                .append("estadoSalud", "Vacunado y desparasitado")
                .append("nivelEnergia", "Alto")
                .append("convivencia", "Buena con otros perros")
                .append("imagenUrl", "https://images.unsplash.com/photo-1552053831-71594a27632d?w=500");

        Document m2 = new Document()
                .append("nombre", "Luna")
                .append("especie", "Gato")
                .append("raza", "Siamés")
                .append("edad", 2)
                .append("sexo", "Hembra")
                .append("tamano", "Mediano")
                .append("descripcion", "Tranquila y cariñosa, le gusta dormir.")
                .append("estadoSalud", "Esterilizada")
                .append("nivelEnergia", "Bajo")
                .append("convivencia", "Prefiere ser hija única")
                .append("imagenUrl", "https://images.unsplash.com/photo-1513245543132-31f507417b26?w=500");

        Document m3 = new Document()
                .append("nombre", "Rocky")
                .append("especie", "Perro")
                .append("raza", "Bulldog Francés")
                .append("edad", 4)
                .append("sexo", "Macho")
                .append("tamano", "Pequeño")
                .append("descripcion", "Le encanta pasear y comer.")
                .append("estadoSalud", "Sano")
                .append("nivelEnergia", "Medio")
                .append("convivencia", "Se lleva bien con gatos")
                .append("imagenUrl", "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=500");

        collection.insertMany(Arrays.asList(m1, m2, m3));
        System.out.println("Mascotas insertadas.");
    }

    private static void insertarUsuarioPrueba(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("usuarios");

        // Verificar si ya existe el usuario demo
        Document existente = collection.find(new Document("infoPersonal.correo", "demo@ejemplo.com")).first();
        if (existente != null) {
            System.out.println("El usuario demo ya existe. Saltando inserción.");
            return;
        }

        Document infoPersonal = new Document()
                .append("nombre", "Usuario Demo")
                .append("curp", "DEMO123456HDFR01")
                .append("direccion", "Calle Falsa 123")
                .append("correo", "demo@ejemplo.com");

        Document usuario = new Document()
                .append("infoPersonal", infoPersonal)
                .append("contrasena", "123456"); // En producción usar hash!

        collection.insertOne(usuario);
        System.out.println("Usuario demo insertado.");
    }

    private static void insertarCitasDisponibles(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("citasDisponibles");

        // Limpiar citas existentes para regenerar
        collection.deleteMany(new Document());
        System.out.println("Colección 'citasDisponibles' limpiada.");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1); // Empezar mañana

        String[] horas = { "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00" };
        Random rand = new Random();

        // Generar citas para los próximos 15 días
        for (int i = 0; i < 15; i++) {
            // Saltar fines de semana (Sábado=7, Domingo=1)
            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY) {

                Date fecha = cal.getTime();

                // Generar 4-6 citas por día
                int citasPorDia = 4 + rand.nextInt(3);

                for (int j = 0; j < citasPorDia; j++) {
                    String hora = horas[rand.nextInt(horas.length)];

                    // Verificar si ya existe esa hora ese día (simple check)
                    Document existe = collection.find(new Document("fecha", fecha).append("hora", hora)).first();
                    if (existe == null) {
                        Document cita = new Document()
                                .append("fecha", fecha)
                                .append("hora", hora)
                                .append("disponible", true)
                                .append("idUsuario", null);
                        collection.insertOne(cita);
                    }
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        System.out.println("Citas disponibles generadas.");
    }
}
