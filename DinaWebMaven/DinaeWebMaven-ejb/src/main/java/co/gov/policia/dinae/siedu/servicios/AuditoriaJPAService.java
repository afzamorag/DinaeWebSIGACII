/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.Auditoria;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.AuditoriaFiltro;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
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
public class AuditoriaJPAService implements AuditoriaService {

  private static final Logger LOG = LoggerFactory.getLogger(AuditoriaJPAService.class);
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
  public List<Auditoria> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Auditoria> list;
    try {
      list = sdo.getResultListByNamedQuery(em, Auditoria.FIND_ALL);
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Auditoria> findByFilter(AuditoriaFiltro filtro) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
    List<Auditoria> list;
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT x FROM Auditoria x WHERE 1 = 1 ");
    if (filtro != null) {
      params = new HashMap<>();
      if (filtro.getFecha() != null) {
        jpql.append("AND x.fecha BETWEEN :fechaInicial AND :fechaFinal ");
        params.put("fecha", filtro.getFecha());
      }
      if (filtro.getTabla() != null) {
        jpql.append("AND x.tabla LIKE :tabla ");
        params.put("tabla", "%" + filtro.getTabla() + "%");
      }
      if (filtro.getOperacion() != null) {
        jpql.append("AND x.operacion = :operacion ");
        params.put("operacion", filtro.getOperacion());
      }
      if (filtro.getUsuario() != null) {
        jpql.append("AND x.usuario LIKE :usuario ");
        params.put("usuario", "%" + filtro.getUsuario() + "%");
      }
      if (filtro.getMaquina() != null) {
        jpql.append("AND x.maquina LIKE :maquina ");
        params.put("maquina", "%" + filtro.getMaquina() + "%");
      }
      if (filtro.getIp() != null) {
        jpql.append("AND x.ip LIKE :ip ");
        params.put("ip", "%" + filtro.getIp() + "%");
      }
    }
    jpql.append("ORDER BY x.id DESC");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
      return list;
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Auditoria findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Auditoria i = (Auditoria) sdo.find(em, id, Auditoria.class);
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Auditoria create(Auditoria record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      sdo.persist(em, record);
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(Auditoria record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      sdo.merge(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(Auditoria record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      sdo.remove(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
