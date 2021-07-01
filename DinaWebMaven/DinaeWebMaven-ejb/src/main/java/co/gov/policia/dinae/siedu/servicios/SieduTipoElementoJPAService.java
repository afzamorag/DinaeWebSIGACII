/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduTipoElemento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduTipoElementoJPAService implements SieduTipoElementoService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduTipoElementoJPAService.class);
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
  public List<SieduTipoElemento> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduTipoElemento> list;
    try {
      list = sdo.getResultList(em, SieduTipoElemento.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduTipoElemento> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduTipoElemento> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduTipoElemento d WHERE 1 = 1 ");
    if (params != null) {
      // TODO: 
      /*if (params.get("descripcion") != null) {
        jpql.append("AND d.descripcionSieduTipoElemento = :descripcion ");
      }
      if (params.get("vigente") != null) {
        jpql.append("AND d.vigente = :vigente ");
      }
      if (params.get("tipo") != null) {
        jpql.append("AND d.idTipoSieduTipoElemento.idTipoSieduTipoElemento = :tipo ");
      }
      if (params.get("documento") != null) {
        jpql.append("AND d.persNroid = :documento ");
      }*/
    }
    //jpql.append("ORDER BY d.descripcionSieduTipoElemento");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public SieduTipoElemento findById(String id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      SieduTipoElemento i = (SieduTipoElemento) sdo.find(em, id, SieduTipoElemento.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

}
