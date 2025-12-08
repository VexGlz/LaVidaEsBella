
package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entities.ExpedienteMedico;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * DAO para gestionar expedientes médicos en MongoDB
 * 
 * @author angel
 */
public class ExpedienteMedicoDAO {

    private final MongoCollection<Document> collection;

    /**
     * Constructor del DAO de ExpedienteMedico
     * 
     * @param database Base de datos MongoDB a utilizar
     */
    public ExpedienteMedicoDAO(MongoDatabase database) {
        this.collection = database.getCollection("expedientes_medicos");
    }

    /**
     * Guarda un nuevo expediente médico en la base de datos
     * 
     * @param expediente Expediente médico a guardar
     * @return ObjectId generado para el expediente guardado
     */
    public ObjectId guardar(ExpedienteMedico expediente) {
        Document doc = expedienteToDocument(expediente);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Busca un expediente por el ID de la mascota
     * 
     * @param mascotaId ObjectId de la mascota
     * @return Expediente médico encontrado o null si no existe
     */
    public ExpedienteMedico buscarPorMascotaId(ObjectId mascotaId) {
        Document doc = collection.find(Filters.eq("mascotaId", mascotaId)).first();
        if (doc == null) {
            return null;
        }
        return documentToExpediente(doc);
    }

    /**
     * Actualiza un expediente médico existente
     * 
     * @param expediente Expediente con datos actualizados
     */
    public void actualizar(ExpedienteMedico expediente) {
        if (expediente.getId() != null) {
            Document doc = expedienteToDocument(expediente);
            collection.replaceOne(Filters.eq("_id", expediente.getId()), doc);
        }
    }

    /**
     * Convierte un Document de MongoDB a una entidad ExpedienteMedico
     * 
     * @param doc Document de MongoDB
     * @return Entidad ExpedienteMedico
     */
    private ExpedienteMedico documentToExpediente(Document doc) {
        ExpedienteMedico expediente = new ExpedienteMedico();
        expediente.setId(doc.getObjectId("_id"));
        expediente.setMascotaId(doc.getObjectId("mascotaId"));
        expediente.setCondicion(doc.getString("condicion"));
        expediente.setNivelEnergia(doc.getString("nivelEnergia"));
        expediente.setVacunaRabia(doc.getBoolean("vacunaRabia", false));
        expediente.setVacunaDesparasitacionExterna(doc.getBoolean("vacunaDesparasitacionExterna", false));
        expediente.setVacunaBordetella(doc.getBoolean("vacunaBordetella", false));
        expediente.setVacunaDesparasitacionInterna(doc.getBoolean("vacunaDesparasitacionInterna", false));
        expediente.setVacunaMultiple(doc.getBoolean("vacunaMultiple", false));
        expediente.setEsterilizado(doc.getBoolean("esterilizado", false));
        expediente.setAlergias(doc.getString("alergias"));
        expediente.setCondicionesEspeciales(doc.getString("condicionesEspeciales"));
        return expediente;
    }

    /**
     * Convierte una entidad ExpedienteMedico a un Document de MongoDB
     * 
     * @param expediente Entidad a convertir
     * @return Document para MongoDB
     */
    private Document expedienteToDocument(ExpedienteMedico expediente) {
        Document doc = new Document();
        if (expediente.getId() != null) {
            doc.append("_id", expediente.getId());
        }
        doc.append("mascotaId", expediente.getMascotaId())
                .append("condicion", expediente.getCondicion())
                .append("nivelEnergia", expediente.getNivelEnergia())
                .append("vacunaRabia", expediente.isVacunaRabia())
                .append("vacunaDesparasitacionExterna", expediente.isVacunaDesparasitacionExterna())
                .append("vacunaBordetella", expediente.isVacunaBordetella())
                .append("vacunaDesparasitacionInterna", expediente.isVacunaDesparasitacionInterna())
                .append("vacunaMultiple", expediente.isVacunaMultiple())
                .append("esterilizado", expediente.isEsterilizado())
                .append("alergias", expediente.getAlergias())
                .append("condicionesEspeciales", expediente.getCondicionesEspeciales());
        return doc;
    }
}