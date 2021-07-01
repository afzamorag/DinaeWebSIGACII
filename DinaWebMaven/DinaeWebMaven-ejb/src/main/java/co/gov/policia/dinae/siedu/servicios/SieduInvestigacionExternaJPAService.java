/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExterna;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduInvestigacionExternaParticipante;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
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
public class SieduInvestigacionExternaJPAService implements SieduInvestigacionExternaService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduInvestigacionExternaJPAService.class);
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
    public List<SieduInvestigacionExterna> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduInvestigacionExterna> list;
        try {
            list = sdo.getResultList(em, SieduInvestigacionExterna.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduInvestigacionExterna> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduInvestigacionExterna> list = new ArrayList<>();
        StringBuilder jpql = new StringBuilder();
        List<Long> idInves = new ArrayList<>();
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
                List<SieduInvestigacionExternaParticipante> listInvExt = (List<SieduInvestigacionExternaParticipante>) em.createNativeQuery("SELECT * FROM SIEDU_INVEST_EXT_PARTICIPA T WHERE T.INVP_PERS = ?1", SieduInvestigacionExternaParticipante.class)
                        .setParameter(1, idPersona)
                        .getResultList();
                if (!listInvExt.isEmpty()) {
                    for (SieduInvestigacionExternaParticipante investigacion : listInvExt) {
                        idInves.add(investigacion.getSieduInvestigacionExternaParticipantePK().getIdInvestigacionExterna());
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

        jpql.append("SELECT d FROM SieduInvestigacionExterna d WHERE 1 = 1 ");
        if (params != null) {
            // TODO: 
            if (params.get("entidad") != null) {
                jpql.append("AND d.entidad = :entidad ");
            }
            if (params.get("grupo") != null) {
                jpql.append("AND d.grupo = :grupo ");
            }
            if (params.get("titulo") != null) {
                jpql.append("AND d.titulo like :titulo ");
            }
            if (params.get("unidad") != null) {
                jpql.append("AND d.unidad = :unidad ");
            }
        }
        //jpql.append("ORDER BY d.descripcionSieduInvestigacionExterna");
        if (!idInves.isEmpty()) {
            List<SieduInvestigacionExterna> listtemp = new ArrayList<>();
            jpql.append("AND d.idInve = :idInvestigacion ");
            for (Long x : idInves) {
                params.put("idInvestigacion", x);                
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
        return list;
    }

    @Override
    public SieduInvestigacionExterna findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduInvestigacionExterna i = (SieduInvestigacionExterna) sdo.find(em, id, SieduInvestigacionExterna.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduInvestigacionExterna create(SieduInvestigacionExterna record) throws ServiceException {
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
    public void update(SieduInvestigacionExterna record) throws ServiceException {
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
    public void delete(SieduInvestigacionExterna record) throws ServiceException {
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
            sdo.remove(em, id, SieduInvestigacionExterna.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
