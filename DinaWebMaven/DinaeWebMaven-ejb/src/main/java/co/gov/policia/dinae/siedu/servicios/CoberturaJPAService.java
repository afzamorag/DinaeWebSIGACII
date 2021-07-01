/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.modelo.Cobertura;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.CoberturaFiltro;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
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
public class CoberturaJPAService implements CoberturaService {

  private static final Logger LOG = LoggerFactory.getLogger(CoberturaJPAService.class);
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
  public List<Cobertura> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Cobertura> list;
    try {
      list = sdo.getResultList(em, Cobertura.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }
  
  @Override
  public List<Cobertura> findByEscuela(UnidadDependencia escuela, PAE pae, Dominio estrategia) throws ServiceException {
    LOG.trace("metodo: findByEscuela()");
    List<Cobertura> list;
    try {
      list = em.createNamedQuery(Cobertura.FIND_BY_ESCUELA, Cobertura.class)
              .setParameter("escuela", escuela)
              .setParameter("pae", pae)
              .setParameter("estrategia", estrategia)
              .getResultList();      
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Cobertura> findByFilter(CoberturaFiltro filtro) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
    List<Cobertura> list;
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT c FROM Cobertura c WHERE 1 = 1 ");
    if (filtro != null) {
      params = new HashMap<>();
      if (filtro.getPae() != null) {
        jpql.append("AND c.pae.id = :pae  ");
        params.put("pae", filtro.getPae().getId());
      }
      if (filtro.getEscuela() != null) {
        jpql.append("AND c.escuela.consecutivo = :escuela ");
        params.put("escuela", filtro.getEscuela().getConsecutivo());
      }
      if (filtro.getEstrategia() != null) {
        jpql.append("AND c.estrategia.id = :estrategia ");
        params.put("estrategia", filtro.getEstrategia().getId());
      }
    }
    jpql.append("ORDER BY c.escuela.descripcionDependencia ASC");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public Cobertura findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Cobertura i = (Cobertura) sdo.find(em, id, Cobertura.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void create(Cobertura record, List<UnidadDependencia> dependencias) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    if (dependencias == null || dependencias.isEmpty()) {
      throw new IllegalArgumentException("Es requerido, mínimo una depenencia");
    }
    for (UnidadDependencia unidad : dependencias) {
      try {
        Cobertura cobertura = new Cobertura();
        cobertura.setPae(record.getPae());
        cobertura.setEscuela(record.getEscuela());
        cobertura.setEstrategia(record.getEstrategia());
        cobertura.setUnidad(unidad);
        cobertura.setCreaUsuario(record.getCreaUsuario()); // auditoria
        cobertura.setCreaFecha(new Date()); // auditoria
        cobertura.setCreaMaquina(maquina); // auditoria
        cobertura.setCreaIP(ip); // auditoria
        // persistir el registro
        sdo.persist(em, cobertura);
      } catch (Exception ex) {
        LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
        throw new ServiceException(ex);
      }
    }
  }

  @Override
  public void update(Cobertura record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      // persistir el registro
      sdo.merge(em, record);
      em.flush();
    } catch (Exception ex) {
      record.setId(null);
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(Cobertura record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      // persistir el registro
      sdo.remove(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
