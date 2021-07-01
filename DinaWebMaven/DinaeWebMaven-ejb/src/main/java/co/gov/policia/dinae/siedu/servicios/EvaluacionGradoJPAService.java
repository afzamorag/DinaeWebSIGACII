package co.gov.policia.dinae.siedu.servicios;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.modelo.Carreras1;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.GradoPK;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.SieduEvalCategoria;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;

@Stateless
public class EvaluacionGradoJPAService implements EvaluacionGradoService {

  private static final Logger LOG = LoggerFactory
          .getLogger(EvaluacionGradoJPAService.class);

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager em;

  @Inject
  @GenericSDOQualifier
  private SDO<SieduEvalGrado, Long> sdo;

  @PostConstruct
  public void init() {
    LOG.trace("metodo: init()");
    if (sdo == null) {
      sdo = new GenericSDO<SieduEvalGrado, Long>();
    }
  }

  @Override
  public List<SieduEvalGrado> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    try {
      return sdo.getResultList(em, SieduEvalGrado.class);
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<SieduEvalGrado> findByEvaluation(Long idEvaluation)
          throws ServiceException {
    LOG.debug("metodo: findByEvaluation() ->> parametros: idEvaluation {}", idEvaluation);
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("idEvaluacion", idEvaluation);
      return sdo.getResultListByNamedQuery(em, SieduEvalGrado.FIND_BY_EVALUATION, params);
    } catch (Exception e) {
      LOG.error("metodo: findByEvaluation() ->> mensaje: {}", e.getMessage());
      throw new ServiceException(e);
    }
  }

  @Override
  public List<SieduEvalGrado> findByFilter(EvaluacionFiltro filtro)
          throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: params {}", filtro);
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<SieduEvalGrado> cq = cb.createQuery(SieduEvalGrado.class);

      Root<SieduEvalGrado> evalGrado = cq.from(SieduEvalGrado.class);
      cq.select(evalGrado);

      Join<SieduEvalGrado, Grado> grado = evalGrado.join("grado");
      Join<Grado, Categoria> categoria = grado.join("categoria");
      Join<SieduEvalGrado, Evaluacion> evaluacion = evalGrado.join("evaluacion");
      Join<Evaluacion, PAE> pae = evaluacion.join("pae");
      Join<Evaluacion, SieduEvento> evento = evaluacion.join("evento");
      Join<SieduEvento, Carreras1> carrera = evento.join("carrera");
      Join<Carreras1, NivelesAcademicos> nivelesAcademico = carrera.join("nivelAcademico");

      Predicate predicate = cb.conjunction();
      if (filtro.getVigencia() != null) {
        ParameterExpression<String> pe = cb.parameter(String.class, "vigenciaPae");
        predicate = cb.and(predicate, cb.equal(pae.get("vigencia"), pe));
//                predicate = cb.and(predicate, cb.equal(pae.get("vigencia"), filtro.getVigencia()));
      }

      if (filtro.getProceso() != null && -1L != filtro.getModalidad()) {
        ParameterExpression<Long> pe = cb.parameter(Long.class, "procesoId");
        predicate = cb.and(predicate, cb.equal(evaluacion.get("proceso").get("id"), pe));
//                predicate = cb.and(predicate, cb.equal(evaluacion.get("proceso").get("id"), filtro.getProceso()));
      }

      if (filtro.getPloblacionNivel() != null && !filtro.getPloblacionNivel().isEmpty()) {
        ParameterExpression<CategoriaPK> pe = cb.parameter(CategoriaPK.class, "categoriasIds");
        predicate = cb.and(predicate, cb.in(categoria.get("id")).value(pe));
//                predicate = cb.and(predicate, cb.equal(categoria.get("id"), filtro.getPloblacionNivel()));
      }

      if (filtro.getPloblacionGrado() != null && !filtro.getPloblacionGrado().isEmpty()) {
        ParameterExpression<GradoPK> pe = cb.parameter(GradoPK.class, "gradosIds");
        predicate = cb.and(predicate, cb.in(grado.get("id")).value(pe));
//                predicate = cb.and(predicate, cb.equal(grado.get("id"), filtro.getPloblacionGrado()));
      }

      if (filtro.getNivelAcademico() != null && -1L != filtro.getNivelAcademico()) {
        ParameterExpression<Long> pe = cb.parameter(Long.class, "nivelAcademicoId");
        predicate = cb.and(predicate, cb.equal(nivelesAcademico.get("idNivelAcademico"), pe));
//                predicate = cb.and(predicate, cb.equal(nivelesAcademico.get("idNivelAcademico"), filtro.getNivelAcademico()));
      }

      if (filtro.getModalidad() != null && -1L != filtro.getModalidad()) {
        ParameterExpression<Long> pe = cb.parameter(Long.class, "modalidadId");
        predicate = cb.and(predicate, cb.equal(evaluacion.get("modalidad").get("id"), pe));
//                predicate = cb.and(predicate, cb.equal(evaluacion.get("modalidad").get("id"), filtro.getModalidad()));
      }

      if (filtro.getEvaluacion() != null && -1L != filtro.getEvaluacion()) {
        ParameterExpression<Long> pe = cb.parameter(Long.class, "evaluacionId");
        predicate = cb.and(predicate, cb.equal(evaluacion.get("id"), pe));
//                predicate = cb.and(predicate, cb.equal(evaluacion.get("id"), filtro.getEvaluacion()));
      }

      cq.where(predicate);
      TypedQuery<SieduEvalGrado> q = em.createQuery(cq);

      if (filtro.getVigencia() != null) {
        q.setParameter("vigenciaPae", filtro.getVigencia());
      }

      if (filtro.getProceso() != null && -1L != filtro.getProceso()) {
        q.setParameter("procesoId", filtro.getProceso());
      }

      if (filtro.getPloblacionNivel() != null && !filtro.getPloblacionNivel().isEmpty()) {
        q.setParameter("categoriasIds", filtro.getPloblacionNivel());
      }

      if (filtro.getPloblacionGrado() != null && !filtro.getPloblacionGrado().isEmpty()) {
        q.setParameter("gradosIds", filtro.getPloblacionGrado());
      }

      if (filtro.getNivelAcademico() != null && -1L != filtro.getNivelAcademico()) {
        q.setParameter("nivelAcademicoId", filtro.getVigencia());
      }

      if (filtro.getModalidad() != null && -1L != filtro.getModalidad()) {
        q.setParameter("modalidadId", filtro.getVigencia());
      }

      if (filtro.getEvaluacion() != null && -1L != filtro.getEvaluacion()) {
        q.setParameter("evaluacionId", filtro.getVigencia());
      }

      List<SieduEvalGrado> list = q.getResultList();

      return list;
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduEvalGrado findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      return sdo.find(em, id,
              SieduEvalGrado.class);
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public SieduEvalGrado create(SieduEvalGrado record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {

      String hostName = InetAddress.getLocalHost().getHostName();
      String hostAddress = InetAddress.getLocalHost().getHostAddress();
      Date fecha = new Date();

      record.setEvgrFechaCrea(fecha);
      record.setEvgrMaquinaCrea(hostName);
      record.setEvgrIpCrea(hostAddress);

      sdo.persist(em, record);
      // em.flush();
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void update(SieduEvalGrado record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {

      String hostName = InetAddress.getLocalHost().getHostName();
      String hostAddress = InetAddress.getLocalHost().getHostAddress();
      Date fecha = new Date();
      String usuario = "tmp";

      record.setEvgrUsuMod(usuario);
      record.setEvgrFechaMod(fecha);
      record.setEvgrMaquinaMod(hostName);
      record.setEvgrIpMod(hostAddress);

      sdo.merge(em, record);
      // em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void delete(SieduEvalGrado record) throws ServiceException {
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
  public void delete(Long id) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: id {}", id);
    try {
      sdo.remove(em, id, SieduEvalGrado.class);
      // em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public int deleteByEvaluation(Long idEvaluation) throws ServiceException {
    LOG.debug("metodo: deleteByEvaluation() ->> parametros: idEvaluation {}", idEvaluation);
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("idEvaluacion", idEvaluation);
      return sdo.executeNamedQuery(em, SieduEvalGrado.DELETE_BY_EVALUATION, params);
    } catch (Exception e) {
      LOG.error("metodo: deleteByEvaluation() ->> mensaje: {}", e.getMessage());
      throw new ServiceException(e);
    }
  }

}
