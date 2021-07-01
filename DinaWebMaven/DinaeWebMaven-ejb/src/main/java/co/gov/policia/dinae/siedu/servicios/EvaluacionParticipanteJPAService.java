/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipantePK;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Juan Jose Buzon
 */
@Stateless
public class EvaluacionParticipanteJPAService implements EvaluacionParticipanteService {

  private static final Logger LOG = LoggerFactory.getLogger(EvaluacionParticipanteJPAService.class);
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
  public List<EvaluacionParticipante> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    try {
      return sdo.getResultList(em, EvaluacionParticipante.class);
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public EvaluacionParticipante findById(EvaluacionParticipantePK id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      return (EvaluacionParticipante) sdo.find(em, id, EvaluacionParticipante.class);
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public EvaluacionParticipante create(EvaluacionParticipante record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {

      String hostName = InetAddress.getLocalHost().getHostName();
      String hostAddress = InetAddress.getLocalHost().getHostAddress();
      Date fecha = new Date();

      record.setFechaCrea(fecha);
      record.setMaquinaCrea(hostName);
      record.setIpCrea(hostAddress);

      return (EvaluacionParticipante) sdo.persist(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(EvaluacionParticipante record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {

      String hostName = InetAddress.getLocalHost().getHostName();
      String hostAddress = InetAddress.getLocalHost().getHostAddress();
      Date fecha = new Date();

      record.setFechaModifica(fecha);
      record.setMaquinaModifica(hostName);
      record.setIpModifica(hostAddress);

      sdo.merge(em, record);
      // em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(EvaluacionParticipante record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      sdo.remove(em, record);
      // em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(EvaluacionParticipantePK id) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: id {}", id);
    try {
      sdo.remove(em, id, EvaluacionParticipante.class);
      // em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<EvaluacionParticipante> findByEvaluation(Long idEvaluation) throws ServiceException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<EvaluacionParticipante> findByPareEvee(SieduEventoEscuela evento) throws ServiceException {
    LOG.debug("metodo: findByPareEvee() ->> parametros: evento{}", evento);
    try {
      List<EvaluacionParticipante> list = em.createNamedQuery(EvaluacionParticipante.FIND_BY_PARE_EVEE, EvaluacionParticipante.class)
              .setParameter("evento", evento.getEveeEvee())
              .getResultList();
      return list;
    } catch (Exception ex) {
      LOG.error("metodo: findByPareEvee() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

}
