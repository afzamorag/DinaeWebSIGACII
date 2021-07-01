/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduTema;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
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
public class SieduTemaJPAService implements SieduTemaService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduTemaJPAService.class);
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
  public List<SieduTema> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduTema> list;
    try {
      list = sdo.getResultList(em, SieduTema.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduTema> findTemasByCarrera(SieduEventoEscuela eventoEscuela) throws ServiceException {
    LOG.trace("metodo: findTemasByEvento() ->> parametros: idCarrera {}", eventoEscuela);
    List<SieduTema> list;
    try {
      list = em.createNamedQuery(SieduTema.FIND_TEMAS_BY_CARRERA, SieduTema.class)
              .setParameter("idCarrera", eventoEscuela.getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera())
              .setParameter("idModalidad", eventoEscuela.getEveeCapa().getModalidad().getId())
              .setParameter("idProceso", eventoEscuela.getEveeCapa().getProceso().getId())
              .getResultList();
    } catch (Exception ex) {
      LOG.error("metodo: findTemasByEvento() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduTema> findTemasByEvento(Long idEvento) throws ServiceException {
    LOG.trace("metodo: findTemasByEvento() ->> parametros: idEvento {}", idEvento);
    List<SieduTema> list;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("idSieduEvento", idEvento);
      list = sdo.getResultListByNamedQuery(em, SieduTema.FIND_TEMAS_BY_EVENTO, params);
    } catch (Exception ex) {
      LOG.error("metodo: findTemasByEvento() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }
  
  
   @Override
  public List<SieduTema> buscarTemasByEvento(SieduEvento evento) throws ServiceException {  
    LOG.trace("metodo: buscarTemasByEvento() ->> parametros: evento {}", evento);
    List<SieduTema> listTemas;
    try {
        listTemas = em.createNamedQuery(SieduTema.FIND_TEMAS_BY_EVENT, SieduTema.class)
              .setParameter("evento", evento)
              .getResultList();              
    } catch (Exception ex) {
      LOG.error("metodo: buscarTemasByEvento() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return listTemas;
  }

  @Override
  public List<SieduTema> findTemasByEventoTemaPapa(Long idTema) throws ServiceException {
    LOG.trace("metodo: findTemasByEventoTemaPapa() ->> parametros: idTema {}", idTema);
    List<SieduTema> list;
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("idSieduTema", idTema);
      list = sdo.getResultListByNamedQuery(em, SieduTema.FIND_TEMAS_BY_EVENTO_TEMA_PAPA, params);
    } catch (Exception ex) {
      LOG.error("metodo: findTemasByEventoTemaPapa() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduTema> findTemasByTemaTemaPadre(Long temaTemaPadre, SieduEventoEscuela evento) throws Exception {
    LOG.trace("metodo: findTemasByTemaTemaPadre() ->> parametros: idTema {}", temaTemaPadre);
    List<SieduTema> listSubtema;
    try {
      return listSubtema = em.createNamedQuery(SieduTema.FIND_TEMAS_BY_TEMA_TEMA_PADRE, SieduTema.class)
              .setParameter("temaTemaPadre", temaTemaPadre)
              .setParameter("eveeEvee", evento.getEveeEvee())
              .getResultList();
    } catch (Exception ex) {
      LOG.error("metodo: findTemasByTemaTemaPadre() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduTema findTemasByTemaPapa(Long idTemaPapa) throws ServiceException {
    LOG.trace("metodo: findTemasByTemaPapa() ->> parametros: idTemaPapa {}", idTemaPapa);
    SieduTema tema;
    try {
      tema = (SieduTema) sdo.find(em, idTemaPapa, SieduTema.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findTemasByTemaPapa() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return tema;
  }

  @Override
  public SieduTema findSubtema(Long idTema) throws ServiceException {
    LOG.trace("metodo: findSubtema() ->> parametros: idTema {}", idTema);
    try {
      SieduTema subtema = (SieduTema) em.createNamedQuery(SieduTema.FIND_TEMAS_BY_EVENTO_TEMA_PAPA, SieduTema.class)
              .setParameter("idSieduTema", idTema)
              .setHint("eclipselink.refresh", "true")
              .setMaxResults(1)
              .getSingleResult();
      return subtema;
    } catch (Exception ex) {
      LOG.error("metodo: findSubtema ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduTema create(SieduTema record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      record.setTemaFechaCrea(new Date());
      record.setTemaMaquinaCrea(InetAddress.getLocalHost().getHostName());
      record.setTemaIpCrea(InetAddress.getLocalHost().getHostAddress());
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
  public void update(SieduTema record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setTemaFechaMod(new Date());
      record.setTemaMaquinaMod(InetAddress.getLocalHost().getHostName());
      record.setTemaIpMod(InetAddress.getLocalHost().getHostAddress());
      sdo.merge(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(SieduTema record, SieduEvento evento) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
//    List<SieduEventoEscuela> eventoEscuela;
//    Map<String, Object> params = new HashMap<>();
//    params.put("evento", evento);
//    eventoEscuela = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_EVENTOS_BY_EVENTO, params);
    try {
//      if (eventoEscuela.isEmpty()) {
      sdo.remove(em, record);
      em.flush();
//      }
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
