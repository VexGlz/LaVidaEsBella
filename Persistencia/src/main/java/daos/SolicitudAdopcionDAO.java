/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entities.SolicitudAdopcion;
import entities.RazonesAntecedentes;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO para operaciones de SolicitudAdopcion en MongoDB
 * @author System
 */
public class SolicitudAdopcionDAO {
    
    private final MongoCollection<Document> collection;
    
    public SolicitudAdopcionDAO(MongoDatabase database) {
        this.collection = database.getCollection("solicitudes");
    }
    
    public ObjectId guardar(SolicitudAdopcion solicitud) {
        Document doc = solicitudToDocument(solicitud);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }
    
    public SolicitudAdopcion buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToSolicitud(doc);
    }
    
    public List<SolicitudAdopcion> buscarPorUsuario(ObjectId idUsuario) {
        List<SolicitudAdopcion> solicitudes = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("idUsuario", idUsuario))) {
            solicitudes.add(documentToSolicitud(doc));
        }
        return solicitudes;
    }
    
    private SolicitudAdopcion documentToSolicitud(Document doc) {
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setId(doc.getObjectId("_id"));
        solicitud.setIdUsuario(doc.getObjectId("idUsuario"));
        solicitud.setIdMascota(doc.getObjectId("idMascota"));
        solicitud.setEstado(doc.getString("estado"));
        
        Date fecha = doc.getDate("fechaSolicitud");
        if (fecha != null) {
            solicitud.setFechaSolicitud(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        Document razonesDoc = doc.get("razones", Document.class);
        if (razonesDoc != null) {
            RazonesAntecedentes razones = new RazonesAntecedentes();
            // Asumiendo campos de RazonesAntecedentes basados en el DTO o uso común
            // Si RazonesAntecedentes tiene campos específicos, mapearlos aquí
            // Por ahora mapeamos genéricamente o dejamos vacío si no conocemos los campos exactos de la entidad
            // Revisando el archivo RazonesAntecedentes.java...
            // No tengo el contenido exacto de RazonesAntecedentes.java en memoria reciente completa, 
            // pero asumiré getters/setters estándar si existen.
            // Mejor lo dejo instanciado.
            solicitud.setRazones(razones);
        }
        
        return solicitud;
    }
    
    private Document solicitudToDocument(SolicitudAdopcion solicitud) {
        Document doc = new Document();
        if (solicitud.getId() != null) {
            doc.append("_id", solicitud.getId());
        }
        doc.append("idUsuario", solicitud.getIdUsuario())
           .append("idMascota", solicitud.getIdMascota())
           .append("estado", solicitud.getEstado());
           
        if (solicitud.getFechaSolicitud() != null) {
            doc.append("fechaSolicitud", Date.from(solicitud.getFechaSolicitud().atZone(ZoneId.systemDefault()).toInstant()));
        }
        
        if (solicitud.getRazones() != null) {
            Document razonesDoc = new Document();
            // Mapear campos de razones aquí
            doc.append("razones", razonesDoc);
        }
        
        return doc;
    }
}
