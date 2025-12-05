/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entities.Cita;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO para operaciones de Cita en MongoDB
 * 
 * @author System
 */
public class CitaDAO {

    private final MongoCollection<Document> collection;

    /**
     * Constructor del DAO de Cita
     * 
     * @param database Base de datos MongoDB a utilizar
     */
    public CitaDAO(MongoDatabase database) {
        this.collection = database.getCollection("citas");
    }

    /**
     * Guarda una nueva cita en la base de datos
     * 
     * @param cita Cita a guardar
     * @return ObjectId generado para la cita guardada
     */
    public ObjectId guardar(Cita cita) {
        Document doc = citaToDocument(cita);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Busca una cita por su identificador
     * 
     * @param id ObjectId de la cita a buscar
     * @return Cita encontrada o null si no existe
     */
    public Cita buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToCita(doc);
    }

    /**
     * Busca todas las citas de un usuario específico
     * 
     * @param idUsuario ObjectId del usuario
     * @return Lista de citas del usuario
     */
    public List<Cita> buscarPorUsuario(ObjectId idUsuario) {
        List<Cita> citas = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("idUsuario", idUsuario))) {
            citas.add(documentToCita(doc));
        }
        return citas;
    }

    /**
     * Convierte un Document de MongoDB a una entidad Cita
     * 
     * @param doc Document de MongoDB con los datos de la cita
     * @return Entidad Cita poblada con los datos del documento
     */
    private Cita documentToCita(Document doc) {
        Cita cita = new Cita();
        cita.setId(doc.getObjectId("_id"));
        cita.setIdUsuario(doc.getObjectId("idUsuario"));
        cita.setIdMascota(doc.getObjectId("idMascota"));

        Date fecha = doc.getDate("fechaHora");
        if (fecha != null) {
            cita.setFechaHora(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }

        return cita;
    }

    /**
     * Convierte una entidad Cita a un Document de MongoDB
     * 
     * @param cita Entidad Cita a convertir
     * @return Document listo para insertar/actualizar en MongoDB
     */
    private Document citaToDocument(Cita cita) {
        Document doc = new Document();
        if (cita.getId() != null) {
            doc.append("_id", cita.getId());
        }
        doc.append("idUsuario", cita.getIdUsuario())
                .append("idMascota", cita.getIdMascota());

        if (cita.getFechaHora() != null) {
            doc.append("fechaHora", Date.from(cita.getFechaHora().atZone(ZoneId.systemDefault()).toInstant()));
        }

        return doc;
    }
}
