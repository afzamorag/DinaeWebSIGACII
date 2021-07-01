/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * description
 *
 * @author: Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduPersonaJPAService implements SieduPersonaService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduPersonaJPAService.class);
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
    public List<SieduPersona> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduPersona> list;
        try {
            list = sdo.getResultList(em, SieduPersona.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduPersona> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduPersona> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM SieduPersona d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("descripcion") != null) {
                jpql.append("AND d.descripcionSieduPersona = :descripcion ");
            }
            if (params.get("vigente") != null) {
                jpql.append("AND d.vigente = :vigente ");
            }
            if (params.get("tipo") != null) {
                jpql.append("AND d.idTipoSieduPersona.idTipoSieduPersona = :tipo ");
            }
            if (params.get("documento") != null) {
                jpql.append("AND d.persNroid = :documento ");
            }
        }
        //jpql.append("ORDER BY d.descripcionSieduPersona");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduPersona findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduPersona i = (SieduPersona) sdo.find(em, id, SieduPersona.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduPersona findByIdentificacion(String identificacion) throws ServiceException {
        LOG.trace("metodo: findByIdentificacion ->> parametros: identificacion {}", identificacion);
        try {
            SieduPersona persona = (SieduPersona) em.createNamedQuery(SieduPersona.FIND_BY_IDENTIFICACION, SieduPersona.class)
                    .setParameter("identificacion", identificacion.trim())
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
            return persona;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        } catch (Exception ex) {
            LOG.error("metodo: findTemasByEventoTemaPapa() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduPersona create(SieduPersona record) throws ServiceException {
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
    public void update(SieduPersona record) throws ServiceException {
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
    public void delete(SieduPersona record) throws ServiceException {
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
            sdo.remove(em, id, SieduPersona.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
