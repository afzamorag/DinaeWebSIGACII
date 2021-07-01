/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduPropuestaAsignadaJPAService implements SieduPropuestaAsignadaService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduPropuestaAsignadaJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public List<SieduPropuestaAsignada> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduPropuestaAsignada> list;
        try {
            list = sdo.getResultList(em, SieduPropuestaAsignada.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduPropuestaAsignada> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduPropuestaAsignada> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM SieduPropuestaAsignada d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("idUnidadPolicial") != null) { // Long
                jpql.append("AND d.sieduPropuestaAsignadaPK.unidad = :idUnidadPolicial ");
            }
            if (params.get("vigencia") != null) {  // String
                jpql.append("AND d.sieduPropuestaAsignadaPK.vigencia = :vigencia ");
            }
            if (params.get("idModalidad") != null) { //Long
                jpql.append("AND d.sieduPropuestaAsignadaPK.idModalidad = :idModalidad ");
            }
            if (params.get("idConstante") != null) { //Long
                jpql.append("AND d.propuestaNecesidad.constantes.idConstantes in :idConstante ");
            }
        }
        //jpql.append("ORDER BY d.linea.areaCienciaTecnologia.nombre, d.linea.nombre, d.titulo");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduPropuestaAsignada findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduPropuestaAsignada i = (SieduPropuestaAsignada) sdo.find(em, id, SieduPropuestaAsignada.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduPropuestaAsignada create(SieduPropuestaAsignada record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            sdo.persist(em, record);
            em.flush();
            return record;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(SieduPropuestaAsignada record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            sdo.merge(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduPropuestaAsignada record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            sdo.remove(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: id {}", id);
        try {
            sdo.remove(em, id, SieduPropuestaAsignada.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduPropuestaAsignada> findByVigencia(PropuestaNecesidad propuestaNecesidad) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: PropuestaNecesidad {}", propuestaNecesidad);
        try {
            SieduPropuestaAsignada s = (SieduPropuestaAsignada) em.createNamedQuery("SieduPropuestaAsignada.findByPropuestaNecesidad", SieduPropuestaAsignada.class)
                    .setParameter("propuestaNecesidad", propuestaNecesidad)
                    .setMaxResults(1)
                    .getSingleResult();
            List<SieduPropuestaAsignada> lst = em.createNamedQuery("SieduPropuestaAsignada.findByVigencia", SieduPropuestaAsignada.class)
                    .setParameter("vigencia", s.getSieduPropuestaAsignadaPK().getVigencia())
                    .getResultList();
            return lst;
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
