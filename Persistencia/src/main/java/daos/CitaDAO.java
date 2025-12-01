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
 * @author System
 */
public class CitaDAO {
    
    private final MongoCollection<Document> collection;
    
    public CitaDAO(MongoDatabase database) {
        this.collection = database.getCollection("citas");
    }
    
    public ObjectId guardar(Cita cita) {
        Document doc = citaToDocument(cita);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }
    
    public Cita buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToCita(doc);
    }
    
    public List<Cita> buscarPorUsuario(ObjectId idUsuario) {
        List<Cita> citas = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("idUsuario", idUsuario))) {
            citas.add(documentToCita(doc));
        }
        return citas;
    }
    
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
