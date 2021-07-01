/*
 * Softstudio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.sdo;

import co.gov.policia.dinae.siedu.modelo.Archivo;
import co.gov.policia.dinae.siedu.util.file.FileUtil;
import java.io.IOException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Diego Poveda.
 * @name:
 * @descripcion:
 * @version: 1.0
 * @since: JDK_1.7
 */
@ArchivoSDOQualifier
public class ArchivoFSSDO extends GenericSDO<Archivo, Long> implements ArchivoSDO<Archivo, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(ArchivoFSSDO.class);

    public ArchivoFSSDO() {
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Archivo persist(EntityManager em, Archivo entity, String path) {
        LOG.trace("entry method: persist() params -->> em -> {}, entity -> {}, path -> {}", em, entity, path);
        if (entity == null || path == null || path.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // 1. crear el registro en la base de datos
        em.persist(entity);
        em.flush();
        // 2. crear el archivo en el gestor documental
        try {
            StringBuilder pathname = new StringBuilder();
            pathname.append(path);
            pathname.append(entity.getId());
            pathname.append(".");
            pathname.append(entity.getExt());
            if (entity.getBase64() != null) {
                FileUtil.createFile(pathname.toString(), FileUtil.base64ToInputStream(entity.getBase64()));
            }
        } catch (IOException ex) {
            LOG.error("Error en <<persist>> ->> mensaje ->> {}", ex.getMessage());
            entity.setId(null);
            throw new PersistenceException("No es posible crear el archivo en el Gestor Documental");
        }
        return entity;
    }

    @Override
    public void merge(EntityManager em, Archivo entity, String path) {
        LOG.trace("entry method: merge() params -->> em -> {}, entity -> {}, path -> {}", em, entity, path);
        if (entity == null || entity.getId() == null || path == null || path.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // 1. obtener el registro de la base de datos para comparar con los datos a guardar
        Archivo old = em.find(Archivo.class, entity.getId());
        StringBuilder pathnameold = new StringBuilder();
        pathnameold.append(path);
        pathnameold.append(old.getId());
        pathnameold.append(".");
        pathnameold.append(old.getExt());
        // 2. actualizar el registro en la base de datos
        em.merge(entity);
        em.flush();
        // 3. actualizar el archivo en el gestor documental
        if (entity.getBase64() != null) {
            //TODO: cuando el tipo de archivo cambia [otra extension], es necesario consultar el registro de la base de datos, 
            //para obtener la extension anterior, y así eliminar ese archivo del GD
            if (old.getExt().equals(entity.getExt())) {
                LOG.debug("Eliminar el archivo {}", pathnameold.toString());
                try {
                    FileUtil.deleteFile(pathnameold.toString());
                } catch (IOException ex) {
                    LOG.error("Error en <<merge>> ->> mensaje ->> {}", ex.getMessage());
                    throw new PersistenceException("No es posible actualizar el archivo del Gestor Documental");
                }
            }
            try {
                StringBuilder pathname = new StringBuilder();
                pathname.append(path);
                pathname.append(entity.getId());
                pathname.append(".");
                pathname.append(entity.getExt());
                FileUtil.createFile(pathname.toString(), FileUtil.base64ToInputStream(entity.getBase64()));
            } catch (IOException ex) {
                LOG.error("Error en <<merge>> ->> mensaje ->> {}", ex.getMessage());
                throw new PersistenceException("No es posible actualizar el archivo del Gestor Documental");
            }
        }
    }

    @Override
    public void remove(EntityManager em, Archivo entity, String path) {
        LOG.trace("entry method: remove() params -->> em -> {}, entity -> {}, path -> {}", em, entity, path);
        if (entity == null || entity.getId() == null || path == null || path.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // 1. eliminar el registro de la base de datos
        em.remove(em.merge(entity));
        em.flush();
        //2. eliminar el archivo del sistema
        try {
            StringBuilder pathname = new StringBuilder();
            pathname.append(path);
            pathname.append(entity.getId());
            pathname.append(".");
            pathname.append(entity.getExt());
            FileUtil.deleteFile(pathname.toString());
        } catch (IOException ex) {
            LOG.error("Error en <<remove>> ->> mensaje ->> {}", ex.getMessage());
            throw new PersistenceException("No es posible eliminar el archivo del Gestor Documental");
        }
    }

    @Override
    public void remove(EntityManager em, Long id, Class<Archivo> entityClass, String path) {
        LOG.trace("entry method: remove() params -->> em -> {}, id -> {}, entityClass -> {}, path -> {}", em, id, entityClass, path);
        if (id == null || path == null || path.isEmpty()) {
            throw new IllegalArgumentException();
        }
        // 1. eliminar el registro de la base de datos
        Archivo entity = em.find(entityClass, id);
        em.remove(entity);
        em.flush();
        //2. eliminar el archivo del sistema
        try {
            StringBuilder pathname = new StringBuilder();
            pathname.append(path);
            pathname.append(entity.getId());
            pathname.append(".");
            pathname.append(entity.getExt());
            FileUtil.deleteFile(pathname.toString());
        } catch (IOException ex) {
            LOG.error("Error en <<remove>> ->> mensaje ->> {}", ex.getMessage());
            throw new PersistenceException("No es posible eliminar el archivo del Gestor Documental");
        }
    }
}
