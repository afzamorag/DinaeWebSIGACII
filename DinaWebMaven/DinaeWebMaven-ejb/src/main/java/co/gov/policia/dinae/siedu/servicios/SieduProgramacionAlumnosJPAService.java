/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Query;
import javax.sql.DataSource;


/**
 *
 * @author andres.zamorag
 */
@Stateless
public class SieduProgramacionAlumnosJPAService implements SieduProgramacionAlumnosService {
  private static final Logger LOG = LoggerFactory.getLogger(SieduProgramacionAlumnosJPAService.class);
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
  public List<ProgramacionAlumnos> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<ProgramacionAlumnos> list;
    try {
      list = sdo.getResultList(em, ProgramacionAlumnos.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }  
 
  @Override
  public ProgramacionAlumnos findById(BigDecimal id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      ProgramacionAlumnos i = (ProgramacionAlumnos) sdo.find(em, id, ProgramacionAlumnos.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public ProgramacionAlumnos create(ProgramacionAlumnos record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(ProgramacionAlumnos record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setFechaActualiza(new Date());
      record.setMaquinaCreacion(InetAddress.getLocalHost().getHostName());
      sdo.merge(em, record);
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(ProgramacionAlumnos record) throws ServiceException {
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
      sdo.remove(em, id, ProgramacionAlumnos.class);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
  
  @Override
  public void update(List<ProgramacionAlumnos> lst) throws ServiceException{
      LOG.debug("método: update() ->> parámetros lst {}", lst);
      try{
          for(ProgramacionAlumnos a: lst){
              this.update(a);
          }
      } catch(Exception ex){
          LOG.error("método: update() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
      }
  }
  
  @Override
  public List<ProgramacionAlumnos> findByProgDocente(BigDecimal id) throws ServiceException{
      LOG.debug("metodo: findByProgDocente() ->> parámetros id{}", id);
      try{
          List<ProgramacionAlumnos> lst;
          lst = (List<ProgramacionAlumnos>) em.createNamedQuery("ProgramacionAlumnos.findByPrpnIdProgpensum", ProgramacionAlumnos.class)
                  .setParameter("prpnIdProgpensum", id)
                  .getResultList();
          return lst;          
      } catch(Exception ex){
          LOG.error("metodo: findByProgDocente() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
      }
  }
}
