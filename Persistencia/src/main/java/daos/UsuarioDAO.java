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
 * @author System
 */
public class UsuarioDAO {
    
    private final MongoCollection<Document> collection;
    
    public UsuarioDAO(MongoDatabase database) {
        this.collection = database.getCollection("usuarios");
    }
    
    /**
     * Busca un usuario por correo electrónico
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
     * @param usuario El usuario a guardar
     * @return El ObjectId del usuario guardado
     */
    public ObjectId guardar(Usuario usuario) {
        Document doc = usuarioToDocument(usuario);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }
    
    /**
     * Verifica si existe un usuario con el correo dado
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
            infoPersonal.setTelefono(infoPersonalDoc.getString("telefono"));
            usuario.setInfoPersonal(infoPersonal);
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
                .append("direccion", usuario.getInfoPersonal().getDireccion())
                .append("telefono", usuario.getInfoPersonal().getTelefono());
            doc.append("infoPersonal", infoPersonalDoc);
        }
        
        return doc;
    }
}
