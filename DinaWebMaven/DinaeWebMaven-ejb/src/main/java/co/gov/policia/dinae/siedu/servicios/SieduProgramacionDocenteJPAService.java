/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduProgramacionDocente;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
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
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduProgramacionDocenteJPAService implements SieduProgramacionDocenteService {

  private static final Logger LOG = LoggerFactory.getLogger(SieduProgramacionDocenteJPAService.class);
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
  public List<SieduProgramacionDocente> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<SieduProgramacionDocente> list;
    try {
      list = sdo.getResultList(em, SieduProgramacionDocente.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }
  
  @Override
  public List<SieduProgramacionDocente> findByIdentificacion(String identificacion) throws ServiceException{
      LOG.trace("metodo: findByIdentificacion()");
     List<SieduProgramacionDocente> list;
     try{
         list = (List<SieduProgramacionDocente>) em.createNamedQuery(SieduProgramacionDocente.FIND_BY_IDENTIFICACION, SieduProgramacionDocente.class)
                 .setParameter("identificacion", identificacion.trim())
                 .getResultList();
         return list;
     } catch (Exception ex){
         LOG.error("metodo: findByIdentificacion() ->> mensaje: {}", ex.getMessage());
         throw new ServiceException(ex);
     }
  }
  
  @Override
  public List<SieduProgramacionDocente> findByIdentificacionUnidad(String identificacion, Long unidad) throws ServiceException{
      LOG.trace("metodo: findByIdentificacionUnidad()");
      List<SieduProgramacionDocente> list;
      try{
          list = (List<SieduProgramacionDocente>) em.createNamedQuery(SieduProgramacionDocente.FIND_BY_IDENTIFICACION_UNIDAD, SieduProgramacionDocente.class)
                  .setParameter("identificacion", identificacion)
                  .setParameter("unidad", unidad)
                  .getResultList();
          return list;
      } catch (Exception ex){
          LOG.error("metodo: findByIdentificacionUnidad() ->> mensaje: {}", ex.getMessage());
         throw new ServiceException(ex);
      }
  }
  
  @Override
  public List<SieduProgramacionDocente> findByIdentificacionVigente(String identificacion, String vigente) throws ServiceException{
      LOG.trace("método: findByIdentificacionVigente()");
      List<SieduProgramacionDocente> list;
      try{
          list = (List<SieduProgramacionDocente>) em.createNamedQuery(SieduProgramacionDocente.FIND_BY_IDENTIFICACION_VIGENTE, SieduProgramacionDocente.class)
                  .setParameter("identificacion", identificacion)
                  .setParameter("vigente", vigente)
                  .getResultList();
          return list;
      } catch(Exception ex){
          LOG.error("método: findByIdentificacionVigente() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
      }
  }

  @Override
  public List<SieduProgramacionDocente> findByFilter(Map<String, Object> params) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
    List<SieduProgramacionDocente> list;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT d FROM SieduProgramacionDocente d WHERE 1 = 1 ");
    if (params != null) {
      if (params.get("codEscuela") != null) {
        jpql.append("AND d.codEscuela = :codEscuela ");
      }
      if (params.get("codigoPrograma") != null) {
        jpql.append("AND d.codigoPrograma = :codigoPrograma ");
      }
      if (params.get("identificacion") != null) {
        jpql.append("AND d.identificacion = :identificacion ");
      }
      if(params.get("vigente") != null){
          jpql.append("AND d.vigente = :vigente ");
      }
    }
    jpql.append("ORDER BY d.codEscuela, d.codigoPrograma");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }
  
  @Override
    public SieduFechasMaxMinEventoDTO findByidProgDocente(BigDecimal idProgDocente) throws ServiceException {
        LOG.trace("metodo: findByidProgDocente ->> parametros: identificacion {}", idProgDocente);
        try {
            SieduFechasMaxMinEventoDTO fechasDTO = (SieduFechasMaxMinEventoDTO) em.createNamedQuery(SieduProgramacionDocente.FIND_FECHAS_PROG_BY_ID_PROG_DOCENTE, SieduFechasMaxMinEventoDTO.class)
                    .setParameter("idProgDocente", idProgDocente)
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
            return fechasDTO;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        } catch (Exception ex) {
            LOG.error("metodo: findByIdentificacion() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
