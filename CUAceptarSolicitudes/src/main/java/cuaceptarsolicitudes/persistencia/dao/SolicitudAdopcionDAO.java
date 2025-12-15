package cuaceptarsolicitudes.persistencia.dao;

import cuaceptarsolicitudes.persistencia.entities.SolicitudAdopcion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO para operaciones CRUD de SolicitudAdopcion.
 */
public class SolicitudAdopcionDAO {

    private final MongoCollection<Document> collection;

    public SolicitudAdopcionDAO(MongoDatabase database) {
        this.collection = database.getCollection("solicitudes_adopcion");
    }

    public List<SolicitudAdopcion> buscarTodas() {
        List<SolicitudAdopcion> solicitudes = new ArrayList<>();
        for (Document doc : collection.find()) {
            solicitudes.add(documentToSolicitud(doc));
        }
        return solicitudes;
    }

    public SolicitudAdopcion buscarPorId(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc != null ? documentToSolicitud(doc) : null;
    }

    public List<SolicitudAdopcion> buscarPorUsuario(ObjectId idUsuario) {
        List<SolicitudAdopcion> solicitudes = new ArrayList<>();
        for (Document doc : collection.find(new Document("idUsuario", idUsuario))) {
            solicitudes.add(documentToSolicitud(doc));
        }
        return solicitudes;
    }

    public ObjectId insertar(SolicitudAdopcion solicitud) {
        Document doc = solicitudToDocument(solicitud);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    public void actualizar(SolicitudAdopcion solicitud) {
        Document doc = solicitudToDocument(solicitud);
        collection.replaceOne(new Document("_id", solicitud.getId()), doc);
    }

    public void eliminar(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }

    private Document solicitudToDocument(SolicitudAdopcion solicitud) {
        Document doc = new Document();
        if (solicitud.getId() != null) {
            doc.append("_id", solicitud.getId());
        }
        doc.append("idUsuario", solicitud.getIdUsuario());
        doc.append("idMascota", solicitud.getIdMascota());
        doc.append("idCita", solicitud.getIdCita());
        doc.append("estado", solicitud.getEstado());
        doc.append("mensajeCorreccion", solicitud.getMensajeCorreccion());
        if (solicitud.getFechaSolicitud() != null) {
            doc.append("fechaSolicitud", Date.from(solicitud.getFechaSolicitud()
                    .atZone(ZoneId.systemDefault()).toInstant()));
        }
        return doc;
    }

    private SolicitudAdopcion documentToSolicitud(Document doc) {
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setId(doc.getObjectId("_id"));
        solicitud.setIdUsuario(doc.getObjectId("idUsuario"));
        solicitud.setIdMascota(doc.getObjectId("idMascota"));
        solicitud.setIdCita(doc.getObjectId("idCita"));
        solicitud.setEstado(doc.getString("estado"));
        solicitud.setMensajeCorreccion(doc.getString("mensajeCorreccion"));
        Date fecha = doc.getDate("fechaSolicitud");
        if (fecha != null) {
            solicitud.setFechaSolicitud(LocalDateTime.ofInstant(
                    fecha.toInstant(), ZoneId.systemDefault()));
        }
        return solicitud;
    }
}
