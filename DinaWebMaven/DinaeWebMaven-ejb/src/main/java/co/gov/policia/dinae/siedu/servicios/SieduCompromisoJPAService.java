/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduCompromiso;
import co.gov.policia.dinae.siedu.modelo.SieduCompromisoPK;
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
public class SieduCompromisoJPAService implements SieduCompromisoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduCompromisoJPAService.class);
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
  public List<SieduCompromiso> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduCompromiso> list;
    try {
      list = sdo.getResultList(em, SieduCompromiso.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  //  JOIN FETCH d.persona JOIN FETCH p.unidad 
  
  @Override
  public List<SieduCompromiso> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduCompromiso> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduCompromiso d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("investigacion") != null) { // SieduInvestigacionExterna
        jpql.append("AND d.investigacionExterna = :investigacion ");
      }
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
  public SieduCompromiso findById(SieduCompromisoPK id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduCompromiso i = (SieduCompromiso) sdo.find(em, id, SieduCompromiso.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduCompromiso create(SieduCompromiso record) throws ServiceException {
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
  public void update(SieduCompromiso record) throws ServiceException {
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
  public void delete(SieduCompromiso record) throws ServiceException {
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
  public void delete(SieduCompromisoPK id) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: id {}", id);
    try {
      sdo.remove(em, id, SieduCompromiso.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
