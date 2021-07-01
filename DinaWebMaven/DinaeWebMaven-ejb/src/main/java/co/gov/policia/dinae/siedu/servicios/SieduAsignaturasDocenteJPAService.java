/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduAsignaturasDocente;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigInteger;
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
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author andres.zamorag
 */
@Stateless
public class SieduAsignaturasDocenteJPAService implements SieduAsignaturasDocenteService {
   
  private static final Logger LOG = LoggerFactory.getLogger(SieduAsignaturasDocenteJPAService.class);
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager em;
  @Inject
  @GenericSDOQualifier
  private SDO sdo;
  @Resource(mappedName = "jdbc/DinaeWebDS")
  private DataSource datasource;

  @PostConstruct
  public void init() {
    LOG.trace("metodo: init()");
    if (sdo == null) {
      sdo = new GenericSDO();
    }
  }

  @Override
  public List<SieduAsignaturasDocente> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduAsignaturasDocente> list;
    try {
      list = sdo.getResultList(em, SieduAsignaturasDocente.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<SieduAsignaturasDocente> findByProgamaDocente(BigInteger idProgramacionAsignatura, String identificacion) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", idProgramacionAsignatura);
    List<SieduAsignaturasDocente> list;
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduAsignaturasDocente d WHERE 1 = 1 ");
    if (idProgramacionAsignatura != null) {
        params = new HashMap<>();
        jpql.append("AND d.idProgramacionAsignatura = :idProgramacionAsignatura ");
        params.put("idProgramacionAsignatura", idProgramacionAsignatura);
        if (identificacion != null) {
          jpql.append("AND d.identificacion = :identificacion ");
          params.put("identificacion", identificacion);
        }
    }
    //jpql.append("ORDER BY d.eveeCapa.carrera.carrerasPK.idCarrera, d.eveeTrimes, d.eveeNroEvento");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

}
