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

    public MascotaDAO(MongoDatabase database) {
        this.collection = database.getCollection("mascotas");
    }

    public ObjectId guardar(Mascota mascota) {
        Document doc = mascotaToDocument(mascota);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public Mascota buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToMascota(doc);
    }

    public List<Mascota> buscarTodas() {
        List<Mascota> mascotas = new ArrayList<>();
        for (Document doc : collection.find()) {
            mascotas.add(documentToMascota(doc));
        }
        return mascotas;
    }

    public List<Mascota> buscarDisponibles() {
        List<Mascota> mascotas = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("disponible", true))) {
            mascotas.add(documentToMascota(doc));
        }
        return mascotas;
    }

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
        return mascota;
    }

    public void actualizar(Mascota mascota) {
        if (mascota.getId() != null) {
            Document doc = mascotaToDocument(mascota);
            collection.replaceOne(Filters.eq("_id", mascota.getId()), doc);
        }
    }

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
                .append("estado", mascota.getEstado());
        return doc;
    }
}
