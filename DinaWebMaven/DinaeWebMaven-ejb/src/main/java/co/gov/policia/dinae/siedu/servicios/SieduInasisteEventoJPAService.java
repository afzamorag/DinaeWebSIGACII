/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduInasisteEvento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.util.ArrayList;
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
public class SieduInasisteEventoJPAService implements SieduInasisteEventoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduInasisteEventoJPAService.class);
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
  public List<SieduInasisteEvento> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduInasisteEvento> list;
    try {
      list = sdo.getResultList(em, SieduInasisteEvento.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduInasisteEvento> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduInasisteEvento> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduInasisteEvento d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("descripcion") != null) {
        jpql.append("AND d.descripcion = :descripcion ");
      }
    }
    jpql.append("ORDER BY d.descripcion");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduInasisteEvento findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduInasisteEvento i = (SieduInasisteEvento) sdo.find(em, id, SieduInasisteEvento.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduInasisteEvento create(SieduInasisteEvento record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      record.setInaeFechaCrea(new Date());
      record.setInaeMaquinaCrea(InetAddress.getLocalHost().getHostName());
      record.setInaeIpCrea(InetAddress.getLocalHost().getHostAddress());
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      record.setInaeInae(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(SieduInasisteEvento record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setInaeFechaMod(new Date());
      record.setInaeMaquinaMod(InetAddress.getLocalHost().getHostName());
      record.setInaeIpMod(InetAddress.getLocalHost().getHostAddress());
      sdo.merge(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(SieduInasisteEvento record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      record.setInaeFechaMod(new Date());
      record.setInaeMaquinaMod(InetAddress.getLocalHost().getHostName());
      record.setInaeIpMod(InetAddress.getLocalHost().getHostAddress());
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
      sdo.remove(em, id, SieduInasisteEvento.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduInasisteEvento findByInaepareInaefecha(SieduPersona inasistente, Date fechaInasiste) throws ServiceException {
    LOG.debug("metodo: findByInaepareInaefecha() ->> parametros: inasistente, fechaInasiste {}", inasistente, fechaInasiste);
    try {
      SieduInasisteEvento inasistenteFecha = (SieduInasisteEvento) em.createNamedQuery(SieduInasisteEvento.FIND_BY_INAEPARE_INAE_FECHA, SieduInasisteEvento.class)
              .setParameter("identificacion", inasistente.getPersNroid())
              .setParameter("fecha", fechaInasiste)
              .getSingleResult();
      return inasistenteFecha;
    } catch (Exception ex) {
      LOG.error("metodo: findByInaepareInaefecha() ->> mensaje: {}", ex.getMessage());
      return null;
    }
  }

  @Override
  public List<SieduInasisteEvento> findByInaepareEvee(SieduEventoEscuela evento) throws ServiceException {
    LOG.debug("metodo: findByInaepareEvee() ->> parametros; evento{}", evento);
    List<SieduInasisteEvento> listInasiste = new ArrayList();
    try {
      listInasiste = em.createNamedQuery(SieduInasisteEvento.FIND_BY_INAEPARE_EVEE, SieduInasisteEvento.class)
              .setParameter("evento", evento.getEveeEvee())
              .getResultList();
    } catch (Exception ex) {
      LOG.error("metodo: findByInaepareEvee() ->> mensaje: {}", ex.getMessage());
      return null;
    }
    return listInasiste;
  }

  @Override
  public List<SieduInasisteEvento> findByInaepare(String identificacion, SieduEventoEscuela evento) throws ServiceException {
    LOG.debug("metodo: findByInaepare() ->> parametros: persona{}", identificacion);
    List<SieduInasisteEvento> listInasistePers = new ArrayList();
    try {
      listInasistePers = em.createNamedQuery(SieduInasisteEvento.FIND_BY_INAEPARE, SieduInasisteEvento.class)
              .setParameter("identificacion", identificacion)
              .setParameter("evento", evento.getEveeEvee())
              .getResultList();
      return listInasistePers;
    } catch (Exception ex) {
      LOG.error("metodo: findByInaepare() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
