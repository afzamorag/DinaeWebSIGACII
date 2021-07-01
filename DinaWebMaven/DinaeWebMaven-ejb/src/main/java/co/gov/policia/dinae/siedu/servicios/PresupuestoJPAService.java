/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.Presupuesto;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.PresupuestoFiltro;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class PresupuestoJPAService implements PresupuestoService {

  private static final Logger LOG = LoggerFactory.getLogger(PresupuestoJPAService.class);
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager em;
  @Inject
  @GenericSDOQualifier
  private SDO sdo;
  private String maquina;
  private String ip;

  @PostConstruct
  public void init() {
    LOG.trace("metodo: init()");
    if (sdo == null) {
      sdo = new GenericSDO();
    }
    try {
      maquina = InetAddress.getLocalHost().getHostName();
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException ex) {
      LOG.error("metodo: init() ->> mensaje: {}", ex.getMessage());
      throw new RuntimeException("No es posible obtener la informacion de nombre de la Maquina e IP");
    }
  }

  @Override
  public List<Presupuesto> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Presupuesto> list;
    try {
      list = sdo.getResultList(em, Presupuesto.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Presupuesto> findByFilter(PresupuestoFiltro filter) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", filter);
    List<Presupuesto> list = null;
    return list;
  }

  @Override
  public Presupuesto findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Presupuesto i = (Presupuesto) sdo.find(em, id, Presupuesto.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<Presupuesto> findByCapacitacion(Long id) throws ServiceException {
    LOG.debug("metodo: findByCapacitacion() ->> parametros: id {}", id);
    if (id == null) {
      throw new IllegalArgumentException("Elparametro [id] de la capacitacion es requerido.");
    }
    Map<String, Object> params = new HashMap();
    params.put("capacitacion", id);
    try {
      return sdo.getResultListByNamedQuery(em, Presupuesto.FIND_BY_CAPACITACION, params);
    } catch (Exception ex) {
      LOG.error("metodo: findByCapacitacion() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Presupuesto create(Presupuesto record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      record.setOrigen("DINAE");
      record.setCreaFecha(new Date()); // auditoria
      record.setCreaMaquina(maquina); // auditoria
      record.setCreaIP(ip); // auditoria
      sdo.persist(em, record);
      return record;
    } catch (Exception ex) {
      record.setId(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(Presupuesto record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      sdo.merge(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(Presupuesto record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      sdo.remove(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
