/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entities.Usuario;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * DAO para operaciones de Usuario en MongoDB
 * 
 * @author System
 */
public class UsuarioDAO {

    private final MongoCollection<Document> collection;

    public UsuarioDAO(MongoDatabase database) {
        this.collection = database.getCollection("usuarios");
    }

    /**
     * Busca un usuario por correo electrónico
     * 
     * @param correo El correo del usuario
     * @return El usuario encontrado o null
     */
    public Usuario buscarPorCorreo(String correo) {
        Document doc = collection.find(Filters.eq("infoPersonal.correo", correo)).first();
        if (doc == null) {
            return null;
        }
        return documentToUsuario(doc);
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     * 
     * @param usuario El usuario a guardar
     * @return El ObjectId del usuario guardado
     */
    public ObjectId guardar(Usuario usuario) {
        Document doc = usuarioToDocument(usuario);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Actualiza un usuario existente
     * 
     * @param usuario El usuario con los datos actualizados
     */
    public void actualizar(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un ID para ser actualizado");
        }

        Document doc = usuarioToDocument(usuario);
        // Remover _id del documento de actualización para evitar errores de
        // inmutabilidad
        doc.remove("_id");

        collection.updateOne(Filters.eq("_id", usuario.getId()), new Document("$set", doc));
    }

    /**
     * Verifica si existe un usuario con el correo dado
     * 
     * @param correo El correo a verificar
     * @return true si existe, false si no
     */
    public boolean existeCorreo(String correo) {
        return collection.countDocuments(Filters.eq("infoPersonal.correo", correo)) > 0;
    }

    /**
     * Convierte un Document de MongoDB a un objeto Usuario
     */
    private Usuario documentToUsuario(Document doc) {
        Usuario usuario = new Usuario();
        usuario.setId(doc.getObjectId("_id"));
        usuario.setContrasena(doc.getString("contrasena"));

        // Convertir infoPersonal
        Document infoPersonalDoc = doc.get("infoPersonal", Document.class);
        if (infoPersonalDoc != null) {
            entities.InfoPersonal infoPersonal = new entities.InfoPersonal();
            infoPersonal.setNombre(infoPersonalDoc.getString("nombre"));
            infoPersonal.setCorreo(infoPersonalDoc.getString("correo"));
            infoPersonal.setCurp(infoPersonalDoc.getString("curp"));
            infoPersonal.setDireccion(infoPersonalDoc.getString("direccion"));
            usuario.setInfoPersonal(infoPersonal);
            usuario.setInfoPersonal(infoPersonal);
        }

        // Convertir infoVivienda
        Document infoViviendaDoc = doc.get("infoVivienda", Document.class);
        if (infoViviendaDoc != null) {
            entities.InfoVivienda infoVivienda = new entities.InfoVivienda();
            infoVivienda.setTipoVivienda(infoViviendaDoc.getString("tipoVivienda"));
            infoVivienda.setCondicionesHogar(infoViviendaDoc.getString("condicionesHogar"));
            infoVivienda.setTieneOtrasMascotas(infoViviendaDoc.getBoolean("tieneOtrasMascotas", false));
            infoVivienda.setTieneNinos(infoViviendaDoc.getBoolean("tieneNinos", false));
            infoVivienda.setTiempoDisponibilidad(infoViviendaDoc.getString("tiempoDisponibilidad"));
            infoVivienda.setUrlImagenVivienda(infoViviendaDoc.getString("urlImagenVivienda"));
            usuario.setInfoVivienda(infoVivienda);
        }

        return usuario;
    }

    /**
     * Convierte un objeto Usuario a un Document de MongoDB
     */
    private Document usuarioToDocument(Usuario usuario) {
        Document doc = new Document();

        if (usuario.getId() != null) {
            doc.append("_id", usuario.getId());
        }

        doc.append("contrasena", usuario.getContrasena());

        // Convertir infoPersonal
        if (usuario.getInfoPersonal() != null) {
            Document infoPersonalDoc = new Document()
                    .append("nombre", usuario.getInfoPersonal().getNombre())
                    .append("correo", usuario.getInfoPersonal().getCorreo())
                    .append("curp", usuario.getInfoPersonal().getCurp())
                    .append("direccion", usuario.getInfoPersonal().getDireccion());
            doc.append("infoPersonal", infoPersonalDoc);
        }

        // Convertir infoVivienda
        if (usuario.getInfoVivienda() != null) {
            Document infoViviendaDoc = new Document()
                    .append("tipoVivienda", usuario.getInfoVivienda().getTipoVivienda())
                    .append("condicionesHogar", usuario.getInfoVivienda().getCondicionesHogar())
                    .append("tieneOtrasMascotas", usuario.getInfoVivienda().isTieneOtrasMascotas())
                    .append("tieneNinos", usuario.getInfoVivienda().isTieneNinos())
                    .append("tiempoDisponibilidad", usuario.getInfoVivienda().getTiempoDisponibilidad())
                    .append("urlImagenVivienda", usuario.getInfoVivienda().getUrlImagenVivienda());
            doc.append("infoVivienda", infoViviendaDoc);
        }

        return doc;
    }
}
