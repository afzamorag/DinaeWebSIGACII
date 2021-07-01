/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.Cargos;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.CargosPK;
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
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class CargosJPAService implements CargosService {

  private static final Logger LOG = LoggerFactory.getLogger(CargosJPAService.class);
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
  public List<Cargos> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Cargos> list;
    try {
      list = sdo.getResultList(em, Cargos.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Cargos> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<Cargos> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM Cargos d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("descripcion") != null) {
        jpql.append("AND d.descripcionCargos = :descripcion ");
      }
      if (params.get("vigente") != null) {
        jpql.append("AND d.vigente = :vigente ");
      }
      if (params.get("tipo") != null) {
        jpql.append("AND d.idTipoCargos.idTipoCargos = :tipo ");
      }
    }
    jpql.append("ORDER BY d.descripcionCargos");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public Cargos findById(CargosPK id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Cargos i = (Cargos) sdo.find(em, id, Cargos.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Cargos create(Cargos record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      record.setId(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(Cargos record) throws ServiceException {
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
  public void delete(Cargos record) throws ServiceException {
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
      sdo.remove(em, id, Cargos.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
