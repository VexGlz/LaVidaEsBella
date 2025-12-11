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
 * 
 * @author System
 */
public class SolicitudAdopcionDAO {

    private final MongoCollection<Document> collection;

    /**
     * Constructor del DAO de SolicitudAdopcion
     * 
     * @param database Base de datos MongoDB a utilizar
     */
    public SolicitudAdopcionDAO(MongoDatabase database) {
        this.collection = database.getCollection("solicitudes");
    }

    /**
     * Guarda una nueva solicitud de adopción en la base de datos
     * 
     * @param solicitud Solicitud de adopción a guardar
     * @return ObjectId generado para la solicitud guardada
     */
    public ObjectId guardar(SolicitudAdopcion solicitud) {
        Document doc = solicitudToDocument(solicitud);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Actualiza los datos de una solicitud de adopción existente
     * 
     * @param solicitud Solicitud con los datos actualizados (debe tener ID)
     */
    public void actualizar(SolicitudAdopcion solicitud) {
        Document doc = solicitudToDocument(solicitud);
        collection.replaceOne(Filters.eq("_id", solicitud.getId()), doc);
    }

    /**
     * Busca una solicitud de adopción por su identificador
     * 
     * @param id ObjectId de la solicitud a buscar
     * @return SolicitudAdopcion encontrada o null si no existe
     */
    public SolicitudAdopcion buscarPorId(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToSolicitud(doc);
    }

    /**
     * Busca todas las solicitudes de adopción de un usuario específico
     * 
     * @param idUsuario ObjectId del usuario
     * @return Lista de solicitudes del usuario
     */
    public List<SolicitudAdopcion> buscarPorUsuario(ObjectId idUsuario) {
        List<SolicitudAdopcion> solicitudes = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("idUsuario", idUsuario))) {
            solicitudes.add(documentToSolicitud(doc));
        }
        return solicitudes;
    }

    /**
     * Busca todas las solicitudes de adopción en el sistema
     * 
     * @return Lista de todas las solicitudes
     */
    public List<SolicitudAdopcion> buscarTodas() {
        List<SolicitudAdopcion> solicitudes = new ArrayList<>();
        for (Document doc : collection.find()) {
            solicitudes.add(documentToSolicitud(doc));
        }
        return solicitudes;
    }

    /**
     * Convierte un Document de MongoDB a una entidad SolicitudAdopcion
     * 
     * @param doc Document de MongoDB con los datos de la solicitud
     * @return Entidad SolicitudAdopcion poblada con los datos del documento
     */
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
            razones.setMotivoAdopcion(razonesDoc.getString("motivoAdopcion"));
            razones.setAntecedentesMascotas(razonesDoc.getString("antecedentesMascotas"));
            razones.setAceptaSeguimiento(razonesDoc.getBoolean("aceptaSeguimiento", false));
            solicitud.setRazones(razones);
        }

        solicitud.setMensajeCorreccion(doc.getString("mensajeCorreccion"));
        solicitud.setIdCita(doc.getObjectId("idCita"));

        return solicitud;
    }

    /**
     * Convierte una entidad SolicitudAdopcion a un Document de MongoDB
     * 
     * @param solicitud Entidad SolicitudAdopcion a convertir
     * @return Document listo para insertar/actualizar en MongoDB
     */
    private Document solicitudToDocument(SolicitudAdopcion solicitud) {
        Document doc = new Document();
        if (solicitud.getId() != null) {
            doc.append("_id", solicitud.getId());
        }
        doc.append("idUsuario", solicitud.getIdUsuario())
                .append("idMascota", solicitud.getIdMascota())
                .append("estado", solicitud.getEstado());

        if (solicitud.getFechaSolicitud() != null) {
            doc.append("fechaSolicitud",
                    Date.from(solicitud.getFechaSolicitud().atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (solicitud.getRazones() != null) {
            Document razonesDoc = new Document()
                    .append("motivoAdopcion", solicitud.getRazones().getMotivoAdopcion())
                    .append("antecedentesMascotas", solicitud.getRazones().getAntecedentesMascotas())
                    .append("aceptaSeguimiento", solicitud.getRazones().isAceptaSeguimiento());
            doc.append("razones", razonesDoc);
        }

        if (solicitud.getMensajeCorreccion() != null) {
            doc.append("mensajeCorreccion", solicitud.getMensajeCorreccion());
        }

        if (solicitud.getIdCita() != null) {
            doc.append("idCita", solicitud.getIdCita());
        }

        return doc;
    }
}
