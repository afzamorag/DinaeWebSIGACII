/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.AlumnosAsignatura;
import co.gov.policia.dinae.siedu.modelo.SieduAsignaturasDocente;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andres.zamorag
 */
@Stateless
public class SieduAlumnosAsignaturaJPAService implements SieduAlumnosAsignaturaService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduAlumnosAsignaturaJPAService.class);
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
  public List<AlumnosAsignatura> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<AlumnosAsignatura> list;
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
  public List<AlumnosAsignatura> findByProgamaAlumno(BigInteger idProgramacionAsignatura, String identificacion) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", idProgramacionAsignatura);
    List<AlumnosAsignatura> list=new ArrayList<>();
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM AlumnosAsignatura d WHERE 1 = 1 ");
    if (idProgramacionAsignatura != null) {
        params = new HashMap<>();
        jpql.append("AND d.idProgpensum = :idProgramacionAsignatura ");
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
