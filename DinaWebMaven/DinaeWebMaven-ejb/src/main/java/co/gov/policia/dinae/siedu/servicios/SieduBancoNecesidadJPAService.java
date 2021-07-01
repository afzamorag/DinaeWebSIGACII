/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduBancoNecesidad;
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
public class SieduBancoNecesidadJPAService implements SieduBancoNecesidadService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduBancoNecesidadJPAService.class);
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
  public List<SieduBancoNecesidad> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduBancoNecesidad> list;
    try {
      list = sdo.getResultList(em, SieduBancoNecesidad.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduBancoNecesidad> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduBancoNecesidad> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduBancoNecesidad d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("area") != null) { // Long
        jpql.append("AND d.linea.areaCienciaTecnologia.idAreaCienciaTecnologia = :area ");
      }
      if (params.get("linea") != null) {  // Long
        jpql.append("AND d.linea.idLinea = :linea ");
      }
      if (params.get("palabraClave") != null) { //String TODO:
        jpql.append("AND d.titulo LIKE :palabraClave ");
      }
      if (params.get("unidad") != null) { // Long
        jpql.append("AND d.unidad.idUnidadPolicial = :unidad ");
      }
      if (params.get("desde") != null) { // Date
        jpql.append("AND d.fechaPropuesta BETWEEN :desde AND :hasta ");
      }
      if (params.get("estado") != null) { // Character
        jpql.append("AND d.estado= :estado ");
      }
    }
    jpql.append("ORDER BY d.linea.areaCienciaTecnologia.nombre, d.linea.nombre, d.titulo");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduBancoNecesidad findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduBancoNecesidad i = (SieduBancoNecesidad) sdo.find(em, id, SieduBancoNecesidad.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduBancoNecesidad create(SieduBancoNecesidad record) throws ServiceException {
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
  public void update(SieduBancoNecesidad record) throws ServiceException {
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
  public void delete(SieduBancoNecesidad record) throws ServiceException {
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
      sdo.remove(em, id, SieduBancoNecesidad.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
