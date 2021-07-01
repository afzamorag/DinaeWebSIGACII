/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.util.Date;
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
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduEntidadJPAService implements SieduEntidadService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEntidadJPAService.class);
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
    public List<SieduEntidad> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduEntidad> list;
        try {
            list = sdo.getResultList(em, SieduEntidad.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduEntidad> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduEntidad> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT i FROM SieduEntidad d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("descripcion") != null) {
                jpql.append("AND d.descripcionSieduEntidad = :descripcion ");
            }
            if (params.get("vigente") != null) {
                jpql.append("AND d.vigente = :vigente ");
            }
            if (params.get("tipo") != null) {
                jpql.append("AND d.idTipoSieduEntidad.idTipoSieduEntidad = :tipo ");
            }
        }
        jpql.append("ORDER BY d.descripcionSieduEntidad");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduEntidad findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduEntidad i = (SieduEntidad) sdo.find(em, id, SieduEntidad.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduEntidad create(SieduEntidad record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            record.setEntiFechaCrea(new Date());
            record.setEntiMaquinaCrea(InetAddress.getLocalHost().getHostName());
            record.setEntiIpCrea(InetAddress.getLocalHost().getHostAddress());
            sdo.persist(em, record);
            em.flush();
            return record;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(SieduEntidad record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setEntiFechaMod(new Date());
            record.setEntiMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setEntiIpMod(InetAddress.getLocalHost().getHostAddress());
            sdo.merge(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduEntidad record) throws ServiceException {
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
            sdo.remove(em, id, SieduEntidad.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
