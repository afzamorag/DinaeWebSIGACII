/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduConvocatoriaEvento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
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
public class SieduConvocatoriaEventoJPAService implements SieduConvocatoriaEventoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduConvocatoriaEventoJPAService.class);
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
  public List<SieduConvocatoriaEvento> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduConvocatoriaEvento> list;
    try {
      list = sdo.getResultList(em, SieduConvocatoriaEvento.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduConvocatoriaEvento> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduConvocatoriaEvento> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT i FROM SieduConvocatoriaEvento d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("descripcion") != null) {
        jpql.append("AND d.descripcionSieduConvocatoriaEvento = :descripcion ");
      }
      if (params.get("vigente") != null) {
        jpql.append("AND d.vigente = :vigente ");
      }
      if (params.get("tipo") != null) {
        jpql.append("AND d.idTipoSieduConvocatoriaEvento.idTipoSieduConvocatoriaEvento = :tipo ");
      }
    }
    jpql.append("ORDER BY d.descripcionSieduConvocatoriaEvento");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduConvocatoriaEvento findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduConvocatoriaEvento i = (SieduConvocatoriaEvento) sdo.find(em, id, SieduConvocatoriaEvento.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduConvocatoriaEvento create(SieduConvocatoriaEvento record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      record.setConeFechaCrea(new Date());
      record.setConeMaquinaCrea(InetAddress.getLocalHost().getHostName());
      record.setConeIpCrea(InetAddress.getLocalHost().getHostAddress());
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      record.setConeCone(null);
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(SieduConvocatoriaEvento record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setConeFechaMod(new Date());
      record.setConeMaquinaMod(InetAddress.getLocalHost().getHostName());
      record.setConeIpMod(InetAddress.getLocalHost().getHostAddress());
      sdo.merge(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(SieduConvocatoriaEvento record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      record.setConeFechaMod(new Date());
      record.setConeMaquinaMod(InetAddress.getLocalHost().getHostName());
      record.setConeIpMod(InetAddress.getLocalHost().getHostAddress());
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
      sdo.remove(em, id, SieduConvocatoriaEvento.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<SieduConvocatoriaEvento> findListById(SieduEventoEscuela id) throws ServiceException {
    LOG.debug("metodo: findListById()", id);
    try {
      return em.createNamedQuery(SieduConvocatoriaEvento.FIND_BY_CONE_EVEE, SieduConvocatoriaEvento.class)
              .setParameter("evento", id)
              .setHint("eclipselink.refresh", "true")
              .getResultList();
    } catch (Exception ex) {
      LOG.error("metodo: metodo: findListById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
