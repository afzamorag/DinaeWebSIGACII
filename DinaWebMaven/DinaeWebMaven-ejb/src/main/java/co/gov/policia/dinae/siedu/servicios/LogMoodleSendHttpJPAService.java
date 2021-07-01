/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendHttp;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.ArrayList;
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
import javax.sql.DataSource;

/**
 *
 * @author Ferney Duran Catedra
 */
@Stateless
public class LogMoodleSendHttpJPAService implements LogMoodleSendHttpService {

    private static final Logger LOG = LoggerFactory.getLogger(LogMoodleSendHttpJPAService.class);
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
    public LogMoodleSendHttp findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
          LogMoodleSendHttp i = (LogMoodleSendHttp) sdo.find(em, id, LogMoodleSendHttp.class);
          em.clear();
          return i;
        } catch (Exception ex) {
          LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
    }
    
    @Override
    public List<LogMoodleSendHttp> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<LogMoodleSendHttp> list;
        try {
          list = sdo.getResultList(em, LogMoodleSendHttp.class);
          em.clear();
        } catch (Exception ex) {
          LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<LogMoodleSendHttp> findFiltro(String where) throws ServiceException {
        List<LogMoodleSendHttp> lst = new ArrayList<LogMoodleSendHttp>();
        try {
          String sQuery = "SELECT s FROM LogMoodleSendHttp s WHERE 1=1 " + where;
          sQuery += " Order By s.id desc ";
          lst.addAll(sdo.getResultListByJPQLQuery(em,sQuery));
        } catch (Exception e) {
          throw new ServiceException(e.getMessage());
        }
        return lst;
    }
    
    @Override
    public void deleteEstado(Integer estado) throws ServiceException {
        try {

          em.createNamedQuery(LogMoodleSendHttp.DELETE_ESTADO)
                  .setParameter("ESTADO", estado)
                  .executeUpdate();

        } catch (Exception e) {

          throw new ServiceException(e.getMessage());
        }    
    }

    @Override
    public void deleteHistory(Date fecha) throws ServiceException {
        try {

          em.createNamedQuery(LogMoodleSendHttp.DELETE_HISTORY)
                  .setParameter("FECHA", fecha)
                  .executeUpdate();

        } catch (Exception e) {

          throw new ServiceException(e.getMessage());
        }    
    }


    @Override
    public LogMoodleSendHttp create(LogMoodleSendHttp record) throws ServiceException {
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
    public void update(LogMoodleSendHttp record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
          sdo.merge(em, record);
        } catch (Exception ex) {
          LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(LogMoodleSendHttp record) throws ServiceException {
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
          sdo.remove(em, id, LogMoodleSendHttp.class);
          em.flush();
        } catch (Exception ex) {
          LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
    }
    
}
