/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduPropiedadIntelectual;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersona;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.ArrayList;
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
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduPropiedadIntelectualJPAService implements SieduPropiedadIntelectualService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduPropiedadIntelectualJPAService.class);
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
    public List<SieduPropiedadIntelectual> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduPropiedadIntelectual> list;
        try {
            list = sdo.getResultList(em, SieduPropiedadIntelectual.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduPropiedadIntelectual> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduPropiedadIntelectual> list = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        List<Long> idPropIntelectual = new ArrayList<>();
        jpql.append("SELECT d FROM SieduPropiedadIntelectual d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("grupo") != null) {
                jpql.append("AND d.grupoInvestigacion = :grupo ");
            }
            // TODO
            if (params.get("identificacion") != null) {                
                Long idPersona = -1L;
                String documento = "";
                documento = params.get("identificacion").toString();
                try {
                    SieduPersona persona = (SieduPersona) em.createNamedQuery(SieduPersona.FIND_BY_IDENTIFICACION, SieduPersona.class)
                            .setParameter("identificacion", documento.trim())
                            .setHint("eclipselink.refresh", "true")
                            .setMaxResults(1)
                            .getSingleResult();
                    idPersona = persona.getPersPers();
                    List<SieduPropIntelectualPersona> listPropInt = (List<SieduPropIntelectualPersona>) em.createNativeQuery("SELECT * FROM SIEDU_PROPINTELECTUAL_PERSONA T WHERE T.PINP_PERS = ?1", SieduPropIntelectualPersona.class)
                            .setParameter(1, idPersona)
                            .getResultList();
                    if (!listPropInt.isEmpty()) {
                        for (SieduPropIntelectualPersona propiedad : listPropInt) {
                            idPropIntelectual.add(propiedad.getSieduPropIntelectualPersonaPK().getIdPin());
                        }
                    }
                    //params.put("idInvestigacion", idInves);
                    params.remove("identificacion");
                } catch (NoResultException | NonUniqueResultException e) {
                    return null;
                } catch (Exception ex) {
                    LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                    throw new ServiceException(ex);
                }
            }
            if (params.get("tipoProduccion") != null) {
                jpql.append("AND d.tipo = :tipoProduccion ");
            }
        }
        //jpql.append("ORDER BY d.descripcionSieduPropiedadIntelectual");
        
        if (!idPropIntelectual.isEmpty()) {
            List<SieduPropiedadIntelectual> listtemp = new ArrayList<>();
            jpql.append("AND d.idPin = :idPin ");
            for (Long x : idPropIntelectual) {
                params.put("idPin", x);                
                try {
                    listtemp = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
                    list.addAll(listtemp);
                } catch (Exception ex) {
                    LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                    throw new ServiceException(ex);
                }
            }
        } else {
            try {
                list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            } catch (Exception ex) {
                LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        }
        
        
        
        
        /*try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }*/
        return list;
    }

    @Override
    public SieduPropiedadIntelectual findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduPropiedadIntelectual i = (SieduPropiedadIntelectual) sdo.find(em, id, SieduPropiedadIntelectual.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduPropiedadIntelectual create(SieduPropiedadIntelectual record) throws ServiceException {
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
    public void update(SieduPropiedadIntelectual record) throws ServiceException {
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
    public void delete(SieduPropiedadIntelectual record) throws ServiceException {
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
            sdo.remove(em, id, SieduPropiedadIntelectual.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
