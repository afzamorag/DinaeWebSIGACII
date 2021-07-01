/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduCertificadoEvento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
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
public class SieduCertificadoEventoJPAService implements SieduCertificadoEventoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduCertificadoEventoJPAService.class);
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
  public List<SieduCertificadoEvento> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduCertificadoEvento> list;
    try {
      list = sdo.getResultList(em, SieduCertificadoEvento.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduCertificadoEvento> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduCertificadoEvento> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduCertificadoEvento d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("descripcion") != null) {
        jpql.append("AND d.descripcionSieduCertificadoEvento = :descripcion ");
      }
      if (params.get("vigente") != null) {
        jpql.append("AND d.vigente = :vigente ");
      }
      if (params.get("tipo") != null) {
        jpql.append("AND d.idTipoSieduCertificadoEvento.idTipoSieduCertificadoEvento = :tipo ");
      }
    }
    jpql.append("ORDER BY d.descripcionSieduCertificadoEvento");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduCertificadoEvento findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduCertificadoEvento i = (SieduCertificadoEvento) sdo.find(em, id, SieduCertificadoEvento.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduCertificadoEvento create(SieduCertificadoEvento record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      record.setCertCert(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(SieduCertificadoEvento record) throws ServiceException {
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
  public void delete(SieduCertificadoEvento record) throws ServiceException {
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
      sdo.remove(em, id, SieduCertificadoEvento.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<SieduParticipanteEvento> findNotCertified(SieduEventoEscuela evento) throws ServiceException {
    LOG.debug("metodo: findNotCertified() ->> Parametros: evento{}", evento);
    try {
      List<SieduParticipanteEvento> list = (List<SieduParticipanteEvento>) em.createQuery("SELECT p FROM SieduParticipanteEvento p WHERE NOT EXISTS (SELECT i FROM p.pareEvee i) AND p.pareEvee.eveeEvee =:evento AND p.pareEstado = 'APROBADO'")
              .setParameter("evento", evento.getEveeEvee())
              .getResultList();
      return list;
    } catch (Exception ex) {
      LOG.error("findNotCertified() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
