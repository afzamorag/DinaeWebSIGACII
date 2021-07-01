/*
 * SoftStudio
 * Copyrigth .2017.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.VDatosPersona;
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
public class VDatosPersonaJPAService implements VDatosPersonaService {

    private static final Logger LOG = LoggerFactory.getLogger(VDatosPersonaJPAService.class);
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
    public List<VDatosPersona> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<VDatosPersona> list;
        try {
            list = sdo.getResultList(em, VDatosPersona.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<VDatosPersona> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<VDatosPersona> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT i FROM VDatosPersona d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("ids") != null) { // Long
                jpql.append("AND d.persPers IN ( ").append(params.get("ids")).append(")");
            }
            if (params.get("documento") != null) { // Long
                jpql.append("AND d.nroId = :documento");
            }
        }
        jpql.append("ORDER BY d.nombre");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public VDatosPersona findById(String id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            VDatosPersona i = (VDatosPersona) sdo.find(em, id, VDatosPersona.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public VDatosPersona findByIdentificacion(String identificacion) throws ServiceException {
        LOG.debug("metodo: findByIdentificacion() ->> parametros: identificacion {}", identificacion);
        try {
            VDatosPersona i = (VDatosPersona) em.createNamedQuery(VDatosPersona.FIND_BY_IDENTIFICACION, VDatosPersona.class)
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
