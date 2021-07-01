/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduObservacionCompromisoPK;
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
public class SieduObservacionCompromisoJPAService implements SieduObservacionCompromisoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduObservacionCompromisoJPAService.class);
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
  public List<SieduObservacionCompromiso> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduObservacionCompromiso> list;
    try {
      list = sdo.getResultList(em, SieduObservacionCompromiso.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }
  
    @Override
  public List<SieduObservacionCompromiso> findByIdCompromiso(Long idCompromiso) throws ServiceException {
    LOG.trace("metodo: findByIdCompromiso()");    
    try {
        List<SieduObservacionCompromiso> list = (List<SieduObservacionCompromiso>) em.createNativeQuery("SELECT T.* FROM SIEDU_OBSERVACION_COMPROMISO T WHERE T.OBCO_COMP = ?1", SieduObservacionCompromiso.class)
                .setParameter(1, idCompromiso)
                .getResultList();
    return list;
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }

  }
  //  JOIN FETCH d.persona JOIN FETCH p.unidad 
  
  @Override
  public List<SieduObservacionCompromiso> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduObservacionCompromiso> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduObservacionCompromiso d WHERE 1 = 1 ");
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
  public SieduObservacionCompromiso findById(SieduObservacionCompromisoPK id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduObservacionCompromiso i = (SieduObservacionCompromiso) sdo.find(em, id, SieduObservacionCompromiso.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduObservacionCompromiso create(SieduObservacionCompromiso record) throws ServiceException {
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
  public void update(SieduObservacionCompromiso record) throws ServiceException {
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
  public void delete(SieduObservacionCompromiso record) throws ServiceException {
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
  public void delete(SieduObservacionCompromisoPK id) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: id {}", id);
    try {
      sdo.remove(em, id, SieduObservacionCompromiso.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
