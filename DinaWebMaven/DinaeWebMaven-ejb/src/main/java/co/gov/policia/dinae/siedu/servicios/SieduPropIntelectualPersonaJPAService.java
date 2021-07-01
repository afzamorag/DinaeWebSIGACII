/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersona;
import co.gov.policia.dinae.siedu.modelo.SieduPropIntelectualPersonaPK;
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
import javax.persistence.Query;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduPropIntelectualPersonaJPAService implements SieduPropIntelectualPersonaService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduPropIntelectualPersonaJPAService.class);
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
  public List<SieduPropIntelectualPersona> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduPropIntelectualPersona> list;
    try {
      list = sdo.getResultList(em, SieduPropIntelectualPersona.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  //  JOIN FETCH d.persona JOIN FETCH p.unidad 
  
  @Override
  public List<SieduPropIntelectualPersona> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduPropIntelectualPersona> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduPropIntelectualPersona d WHERE 1 = 1 ");
    if (params != null) {
      
      // TODO: 
      
      /*if (params.get("bancoNecesidad") != null) { // Long
        jpql.append("AND d.sieduBancoNecesidadPersonaPK.idBancoNecesidad = :bancoNecesidad ");
      }
      if (params.get("documento") != null) { // Long
        jpql.append("AND d.persona.persNroid = :documento ");
      }
      
      if (params.get("vigencia") != null) {  // String
        jpql.append("AND d.vigencia = :vigencia ");
      }
      if (params.get("modalidad") != null) { //Dominio
        jpql.append("AND d.modalidad = :modalidad ");
      }*/
    }
    //jpql.append("ORDER BY d.vigencia, d.sieduProgramaInvestigacionPK.unidad.descripcionDependencia, d.modalidad.nombre");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduPropIntelectualPersona findById(SieduPropIntelectualPersonaPK id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduPropIntelectualPersona i = (SieduPropIntelectualPersona) sdo.find(em, id, SieduPropIntelectualPersona.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduPropIntelectualPersona create(SieduPropIntelectualPersona record) throws ServiceException {
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
  public void update(SieduPropIntelectualPersona record) throws ServiceException {
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
  public void delete(SieduPropIntelectualPersona record) throws ServiceException {
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
  public void delete(SieduPropIntelectualPersonaPK id) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: id {}", id);
    try {
      sdo.remove(em, id, SieduPropIntelectualPersona.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
  
      @Override
    public void deleteByPropiedadId(Long propiedadId) throws ServiceException {
        LOG.debug("metodo: deleteByPropiedadId() ->> parametros: propiedadId {}", propiedadId);
        try {
            Query query = em.createQuery(
                    "DELETE FROM SieduPropIntelectualPersona c WHERE c.sieduPropIntelectualPersonaPK.idPin = :p");
            int deletedCount = query.setParameter("p", propiedadId).executeUpdate();
        } catch (Exception ex) {
            LOG.error("metodo: deleteByPropiedadId() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
