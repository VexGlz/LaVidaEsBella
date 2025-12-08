/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entities.Mascota;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones de Mascota en MongoDB
 * 
 * @author System
 */
public class MascotaDAO {

    private final MongoCollection<Document> collection;

    /**
     * Constructor del DAO de Mascota
     * 
     * @param database Base de datos MongoDB a utilizar
     */
    public MascotaDAO(MongoDatabase database) {
        this.collection = database.getCollection("mascotas");
    }

    /**
     * Guarda una nueva mascota en la base de datos
     * 
     * @param mascota Mascota a guardar
     * @return ObjectId generado para la mascota guardada
     */
    public ObjectId guardar(Mascota mascota) {
        Document doc = mascotaToDocument(mascota);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Busca una mascota por su identificador
     * 
     * @param id ObjectId de la mascota a buscar
     * @return Mascota encontrada o null si no existe
     */
    public Mascota buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToMascota(doc);
    }

    /**
     * Obtiene todas las mascotas registradas en el sistema
     * 
     * @return Lista de todas las mascotas
     */
    public List<Mascota> buscarTodas() {
        List<Mascota> mascotas = new ArrayList<>();
        for (Document doc : collection.find()) {
            mascotas.add(documentToMascota(doc));
        }
        return mascotas;
    }

    /**
     * Obtiene todas las mascotas que están disponibles para adopción
     * 
     * @return Lista de mascotas disponibles
     */
    public List<Mascota> buscarDisponibles() {
        List<Mascota> mascotas = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("disponible", true))) {
            mascotas.add(documentToMascota(doc));
        }
        return mascotas;
    }

    /**
     * Convierte un Document de MongoDB a una entidad Mascota
     * 
     * @param doc Document de MongoDB con los datos de la mascota
     * @return Entidad Mascota poblada con los datos del documento
     */
    private Mascota documentToMascota(Document doc) {
        Mascota mascota = new Mascota();
        mascota.setId(doc.getObjectId("_id"));
        mascota.setNombre(doc.getString("nombre"));
        mascota.setEspecie(doc.getString("especie"));
        mascota.setEstadoSalud(doc.getString("estadoSalud"));
        mascota.setPersonalidad(doc.getString("personalidad"));
        mascota.setUrlImagen(doc.getString("urlImagen"));
        mascota.setEdad(doc.getInteger("edad"));
        mascota.setDisponible(doc.getBoolean("disponible"));
        mascota.setEstado(doc.getString("estado"));

        // Nuevos campos
        mascota.setColor(doc.getString("color"));
        mascota.setRaza(doc.getString("raza"));
        Double peso = doc.getDouble("peso");
        mascota.setPeso(peso != null ? peso : 0.0);

        return mascota;
    }

    /**
     * Actualiza los datos de una mascota existente
     * 
     * @param mascota Mascota con los datos actualizados (debe tener ID)
     */
    public void actualizar(Mascota mascota) {
        if (mascota.getId() != null) {
            Document doc = mascotaToDocument(mascota);
            collection.replaceOne(Filters.eq("_id", mascota.getId()), doc);
        }
    }

    /**
     * Convierte una entidad Mascota a un Document de MongoDB
     * 
     * @param mascota Entidad Mascota a convertir
     * @return Document listo para insertar/actualizar en MongoDB
     */
    private Document mascotaToDocument(Mascota mascota) {
        Document doc = new Document();
        if (mascota.getId() != null) {
            doc.append("_id", mascota.getId());
        }
        doc.append("nombre", mascota.getNombre())
                .append("especie", mascota.getEspecie())
                .append("estadoSalud", mascota.getEstadoSalud())
                .append("personalidad", mascota.getPersonalidad())
                .append("urlImagen", mascota.getUrlImagen())
                .append("edad", mascota.getEdad())
                .append("disponible", mascota.isDisponible())
                .append("estado", mascota.getEstado())
                // Nuevos campos
                .append("color", mascota.getColor())
                .append("raza", mascota.getRaza())
                .append("peso", mascota.getPeso());
        return doc;
    }
}
