/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;
import co.gov.policia.dinae.siedu.dto.EventoDTO;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.GradoPK;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.util.EvaluacionUtil;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Juan Jose Buzon
 */
@Stateless
public class EvaluacionJPAService implements EvaluacionService {

    private static final Logger LOG = LoggerFactory
            .getLogger(EvaluacionJPAService.class);

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;

    @Inject
    @GenericSDOQualifier
    private SDO sdo;

    @EJB
    private EvaluacionGradoService evaluacionGradoService;
    @EJB
    private EvaluacionCategoriaService evaluacionCategoriaService;
    @EJB
    private PreguntaEvaluacionService preguntaEvaluacionService;
    @EJB
    private SieduEventoService eventoService;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public List<Evaluacion> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<Evaluacion> list;
        try {
            list = sdo.getResultList(em, Evaluacion.class);
            em.clear();
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            return (Evaluacion) sdo.find(em, id, Evaluacion.class);
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(Evaluacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

            int tamListaPregEval = preguntaEvaluacionService.findByEvaluation(record.getId()).size();
            Long evalNroPreg = record.getEvalNroPreg();

            if (evalNroPreg < 1 || evalNroPreg < tamListaPregEval) {
                throw new ServiceException("Número de preguntas no válido");
            }

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setEvalFechaMod(fecha);
            record.setEvalMaquinaMod(hostName);
            record.setEvalIpMod(hostAddress);

            sdo.merge(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void updateWithDegrees(Evaluacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

            int tamListaPregEval = preguntaEvaluacionService.findByEvaluation(record.getId()).size();
            Long evalNroPreg = record.getEvalNroPreg();

            if (evalNroPreg < 1 || evalNroPreg < tamListaPregEval) {
                throw new ServiceException("Número de preguntas no válido");
            }

            final List<SieduEvalGrado> evalGrados = record.getEvalGrados();
            record.setEvalGrados(new ArrayList<SieduEvalGrado>());

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setEvalFechaMod(fecha);
            record.setEvalMaquinaMod(hostName);
            record.setEvalIpMod(hostAddress);

            sdo.merge(em, record);

            evaluacionGradoService.deleteByEvaluation(record.getId());
            for (SieduEvalGrado sieduEvalGrado : evalGrados) {
                sieduEvalGrado.getId().setEvgrEval(record.getId());
                sieduEvalGrado.setEvaluacion(record);
                sieduEvalGrado.setEvgrUsuCrea(record.getEvalUsuMod());
                record.getEvalGrados().add(
                        evaluacionGradoService.create(sieduEvalGrado));
            }
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(Evaluacion record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            sdo.remove(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: id {}", id);
        try {
            sdo.remove(em, id, Evaluacion.class);
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Evaluacion> findActives() throws ServiceException {
        LOG.trace("metodo: findActives()");
        List<Evaluacion> list;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("estado", EstadoEvaluacionEnum.ACTIVA);
            list = sdo.getResultListByJPQLQuery(em, Evaluacion.FIND_BY_STATUS,
                    params, Evaluacion.class);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findActives() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Evaluacion> findAllLoadIdAndDescription()
            throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<Evaluacion> list;
        try {
            list = sdo.getResultListByNamedQuery(em,
                    Evaluacion.FIND_ALL_LIGHT_LOAD);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Evaluacion> findByEvaluationFilter(EvaluacionFiltro filtro)
            throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", filtro);
        try {
            Map<Integer, Object> params = new HashMap<>();
            StringBuilder nativeQuery = new StringBuilder(
                    ""
                    + "SELECT e.EVAL_EVAL, e.EVAL_DESCRI, e.EVAL_NRO_PREG, e.EVAL_FECHA, "
                    + "e.EVAL_ESTADO, e.EVAL_APLICADA, e.EVAL_USU_CREA, e.EVAL_FECHA_CREA, "
                    + "e.EVAL_MAQUINA_CREA, e.EVAL_IP_CREA, pr.ID_DOMINIO, pr.NOMBRE, "
                    + "mo.ID_DOMINIO, mo.NOMBRE, g.FUERZA, g.ALFABETICO, g.DESCRIPCION, "
                    + "c.FUERZA, c.ID_CATEGORIA, c.DESCRIPCION, p.PAE_PAE, p.PAE_VIGENCIA, "
                    + "ev.EVEN_EVEN, cr.ID_CARRERA, cr.DESCRIPCION, cr.TITULO, na.ID_NIVEL_ACADEMICO, na.DESCRIPCION, cr.FUERZA "
                    + "FROM SIEDU_EVALUACION e "
                    + "JOIN SIEDU_EVAL_GRADO eg ON e.EVAL_EVAL = eg.EVGR_EVAL "
                    + "JOIN USR_REHU.GRADOS g ON (eg.EVGR_GRAD_FUERZA = g.FUERZA AND eg.EVGR_GRAD_ALFABETICO = g.ALFABETICO) "
                    + "JOIN USR_REHU.CATEGORIAS c ON (g.FUERZA = c.FUERZA AND g.ID_CATEGORIA = c.ID_CATEGORIA) "
                    + "JOIN SIEDU_PAE p ON e.EVAL_PAE = p.PAE_PAE "
                    + "JOIN SIEDU_EVENTO ev ON e.EVAL_EVEN = ev.EVEN_EVEN "
                    + "JOIN CARRERAS cr ON ev.EVEN_ID_CARRERA = cr.ID_CARRERA "
                    + "JOIN NIVELES_ACADEMICOS na ON cr.ID_NIVEL_ACADEMICO = na.ID_NIVEL_ACADEMICO "
                    + "JOIN SIEDU_DOMINIO pr ON ev.EVEN_DOM_PROCE = pr.ID_DOMINIO "
                    + "JOIN SIEDU_DOMINIO mo ON ev.EVEN_DOM_MODAL = mo.ID_DOMINIO "
                    + "WHERE 1 = 1");

            int i = 0;
            int j = 0;
            int k = 0;

            if (filtro.getVigencia() != null && !filtro.getVigencia().isEmpty()) {
                nativeQuery.append(" AND p.PAE_VIGENCIA = ?").append(++i);
                params.put(i, filtro.getVigencia());
            }

            if (filtro.getProceso() != null && -1L != filtro.getProceso()) {
                nativeQuery.append(" AND pr.ID_DOMINIO = ?").append(++i);
                params.put(i, filtro.getVigencia());
            }

            if (filtro.getNivelAcademico() != null
                    && -1L != filtro.getNivelAcademico()) {
                nativeQuery.append(" AND na.ID_NIVEL_ACADEMICO = ?")
                        .append(++i);
                params.put(i, filtro.getNivelAcademico());
            }

            if (filtro.getEventoProgramaAcademico() != null) {
                j = ++i;
                k = ++i;
                nativeQuery.append(" AND cr.ID_CARRERA = ?")
                        .append(j).append(" AND cr.FUERZA = ?").append(k);
                params.put(j, filtro.getEventoProgramaAcademico()
                        .getIdCarrera());
                params.put(k, filtro.getEventoProgramaAcademico().getFuerza());
            }

            if (filtro.getModalidad() != null && -1L != filtro.getModalidad()) {
                nativeQuery.append(" AND mo.ID_DOMINIO = ?").append(++i);
                params.put(i, filtro.getModalidad());
            }

            if (filtro.getEvaluacion() != null && -1L != filtro.getEvaluacion()) {
                nativeQuery.append(" AND e.EVAL_EVAL = ?").append(++i);
                params.put(i, filtro.getEvaluacion());
            }

            if (filtro.getPloblacionNivel() != null
                    && !filtro.getPloblacionNivel().isEmpty()) {
                nativeQuery.append(" AND (c.id_categoria, c.fuerza) in (");
                String prefix = "";
                for (CategoriaPK categoriaPK : filtro.getPloblacionNivel()) {
                    nativeQuery.append(prefix);
                    prefix = ",";
                    j = ++i;
                    k = ++i;
                    nativeQuery.append("(?").append(j).append(", ?").append(k)
                            .append(") ");
                    params.put(j, categoriaPK.getIdCategoria());
                    params.put(k, categoriaPK.getFuerza());
                }
                nativeQuery.append(")");
            }

            if (filtro.getPloblacionGrado() != null
                    && !filtro.getPloblacionGrado().isEmpty()) {
                nativeQuery.append(" AND (g.FUERZA, g.ALFABETICO) in (");
                String prefix = "";
                for (GradoPK gradoPK : filtro.getPloblacionGrado()) {
                    nativeQuery.append(prefix);
                    prefix = ",";
                    j = ++i;
                    k = ++i;
                    nativeQuery.append("(?").append(j).append(", ?").append(k)
                            .append(") ");
                    params.put(j, gradoPK.getFuerza());
                    params.put(k, gradoPK.getAlfabetico());
                }
                nativeQuery.append(")");
            }
            nativeQuery.append(" ORDER BY e.EVAL_DESCRI ASC");

            List<Object[]> list = sdo.getResultListByNativeQuery(em,
                    nativeQuery.toString(), params);

            List<Evaluacion> evaluaciones = new ArrayList<>();
            Evaluacion evaluacion = null;
            for (Object[] objects : list) {
                if (evaluacion == null
                        || ((BigDecimal) objects[0]).longValue() != evaluacion
                        .getId()) {
                    evaluacion = EvaluacionUtil.obtenerEvaluacion(objects);
                    evaluaciones.add(evaluacion);
                }
                evaluacion.getEvalGrados().add(
                        EvaluacionUtil.obtenerGradoEvaluacion(objects));
            }

            return evaluaciones;
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion newVersion(Evaluacion record) throws ServiceException {
        LOG.debug("metodo: newVersion() ->> parametros: record {}", record);
        try {

            record.setEstadoEvaluacion(EstadoEvaluacionEnum.INACTIVA);
            update(record);

            Evaluacion evaluacion = new Evaluacion(record);
            evaluacion.setEvalUsuCrea(record.getEvalUsuMod());
            return create(evaluacion);
        } catch (Exception ex) {
            LOG.error("metodo: newVersion() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion create(Evaluacion record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {

            int tamListaPregEval = record.getPreguntasEvaluacion().size();
            Long evalNroPreg = record.getEvalNroPreg();

            if (evalNroPreg < 1 || evalNroPreg < tamListaPregEval) {
                throw new ServiceException("Número de preguntas no válido");
            } else if (tamListaPregEval == evalNroPreg) {
                record.setEstadoEvaluacion(EstadoEvaluacionEnum.ACTIVA);
            } else {
                record.setEstadoEvaluacion(EstadoEvaluacionEnum.PENDIENTE);
            }

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setId(null);
            record.setAplicada("N");
            record.setEvalFecha(fecha);
            record.setEvalFechaCrea(fecha);
            record.setEvalMaquinaCrea(hostName);
            record.setEvalIpCrea(hostAddress);
            record.setEvalFechaMod(null);
            record.setEvalIpMod(null);
            record.setEvalUsuMod(null);
            // record.setEvento(evento);
            record.setEvalGrados(new ArrayList<SieduEvalGrado>());
            record.setPreguntasEvaluacion(new ArrayList<PreguntaEvaluacion>());

            record = (Evaluacion) sdo.persist(em, record);
            record.setModalidad(record.getEvento().getDominioModalidad());
            record.setProceso(record.getEvento().getDominioProceso());
            record.setPae((PAE) sdo.find(em, record.getPae().getId(), PAE.class));

            return record;

        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion findForDevelopByEvent(Long idEvent, PAE pae)
            throws ServiceException {
        LOG.trace("metodo: findForDevelopByEvent() ->> parametros: params {}",
                idEvent);
        try {

//            Map<String, Object> params = new HashMap<>();
//            params.put("estado", EstadoEvaluacionEnum.ACTIVA);
//            params.put("idEvent", idEvent);
//            return (Evaluacion) sdo.findByNamedQuery(em,
//                    Evaluacion.FIND_FOR_DEVELOP_BY_EVENT, params);
            Evaluacion evaluacion = null;
            SieduEvento evento = new SieduEvento();
            evento = eventoService.findById(idEvent);
            //Ajuste realizado 05/06/2019 Se genera una unica evlación por modalidad (Presencia - Virtual)
            /*String nativeQuery = "SELECT eva.EVAL_EVAL, eva.EVAL_DESCRI, epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN, epr.EVPRE_VLR_PORC, peva.PEVAL_PEVAL, peva.PEVAL_DESCRI, pevp.PEVAL_PEVAL, pevp.PEVAL_DESCRI "
                    + "FROM SIEDU_EVALUACION eva "
                    + "JOIN SIEDU_EVAL_PREGUNTA epr ON epr.EVPRE_EVAL = eva.EVAL_EVAL "
                    + "JOIN SIEDU_PARAMETRO_EVALUACION peva ON peva.PEVAL_PEVAL = epr.EVPRE_PEVAL_ASPEC "
                    + "JOIN SIEDU_PARAMETRO_EVALUACION pevp ON pevp.PEVAL_PEVAL = epr.EVPRE_PEVAL_PREG "
                    + "WHERE eva.EVAL_ESTADO = ? "
                    + "AND eva.EVAL_EVEN = ? ORDER BY epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN";*/
            String nativeQuery = "SELECT eva.EVAL_EVAL, eva.EVAL_DESCRI, epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN, epr.EVPRE_VLR_PORC, peva.PEVAL_PEVAL, peva.PEVAL_DESCRI, pevp.PEVAL_PEVAL, pevp.PEVAL_DESCRI "
                    + "                     FROM SIEDU_EVALUACION eva "
                    + "                     JOIN SIEDU_EVAL_PREGUNTA epr ON epr.EVPRE_EVAL = eva.EVAL_EVAL "
                    + "                     JOIN SIEDU_PARAMETRO_EVALUACION peva ON peva.PEVAL_PEVAL = epr.EVPRE_PEVAL_ASPEC "
                    + "                     JOIN SIEDU_PARAMETRO_EVALUACION pevp ON pevp.PEVAL_PEVAL = epr.EVPRE_PEVAL_PREG "
                    + "                     WHERE eva.EVAL_ESTADO = ? "
                    + "                     AND eva.EVAL_PAE = ? "
                    + "                     AND eva.EVAL_DESCRI LIKE '%" + evento.getDominioModalidad().getNombre() + "%'"
                    + "                     ORDER BY epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN";
            
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, EstadoEvaluacionEnum.ACTIVA.getName());
            params.put(2, pae.getId());           
//            List<Object[]> preguntasEvaluacion = sdo.getResultListByNamedQuery(em, Evaluacion.FIND_FOR_DEVELOP_BY_EVENT, params);
            List<Object[]> preguntasEvaluacion = sdo.getResultListByNativeQuery(em, nativeQuery, params);
            if (preguntasEvaluacion != null && !preguntasEvaluacion.isEmpty()) {
                evaluacion = new Evaluacion();
                evaluacion.setId(((BigDecimal) preguntasEvaluacion.get(0)[0]).longValue());
                evaluacion.setEvalDescri((String) preguntasEvaluacion.get(0)[1]);
                evaluacion.setPreguntasEvaluacion(new ArrayList<PreguntaEvaluacion>());

                PreguntaEvaluacion preguntaEvaluacion = null;
                PreguntaEvaluacionPK id = null;
                ParametroEvaluacion aspecto = null;
                ParametroEvaluacion pregunta = null;
                for (Object[] objects : preguntasEvaluacion) {

                    id = new PreguntaEvaluacionPK();
                    id.setIdEvaluacion(((BigDecimal) objects[0]).longValue());
                    id.setIdPregunta(((BigDecimal) objects[2]).longValue());

                    preguntaEvaluacion = new PreguntaEvaluacion();
                    preguntaEvaluacion.setId(id);
                    preguntaEvaluacion.setOrden(((BigDecimal) objects[3]).longValue());
                    preguntaEvaluacion.setValorPorcentaje((BigDecimal) objects[4]);

                    aspecto = new ParametroEvaluacion();
                    aspecto.setId(((BigDecimal) objects[5]).longValue());
                    aspecto.setDescripcion((String) objects[6]);
                    preguntaEvaluacion.setAspecto(aspecto);

                    pregunta = new ParametroEvaluacion();
                    pregunta.setId(((BigDecimal) objects[7]).longValue());
                    pregunta.setDescripcion((String) objects[8]);
                    preguntaEvaluacion.setPregunta(pregunta);

                    evaluacion.getPreguntasEvaluacion().add(preguntaEvaluacion);
                }
            }
            return evaluacion;
        } catch (Exception ex) {
            LOG.error("metodo: findForDevelopByEvent() ->> mensaje: {}",
                    ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion findByIdLoadQuestions(Long idEvaluation) throws ServiceException {
        LOG.trace("metodo: findByIdLoadQuestions() ->> parametros: params {}",
                idEvaluation);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idEvaluation", idEvaluation);
            return (Evaluacion) sdo.findByNamedQuery(em,
                    Evaluacion.FIND_BY_ID_LOAD_QUESTIONS, params);
        } catch (Exception ex) {
            LOG.error("metodo: findByIdLoadQuestions() ->> mensaje: {}",
                    ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion create(Evaluacion record, List<SieduEvalGrado> evalGrados) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            record = this.create(record);

            for (SieduEvalGrado sieduEvalGrado : evalGrados) {
                sieduEvalGrado.getId().setEvgrEval(record.getId());
                sieduEvalGrado.setEvaluacion(record);
                sieduEvalGrado.setEvgrUsuCrea(record.getEvalUsuCrea());
                record.getEvalGrados().add(
                        evaluacionGradoService.create(sieduEvalGrado));
            }

            return record;

        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Evaluacion create(Evaluacion record, List<SieduEvalGrado> evalGrados, List<PreguntaEvaluacion> preguntasEvaluacion) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {

            record = this.create(record, evalGrados);

            for (PreguntaEvaluacion preguntaEvaluacion : preguntasEvaluacion) {
                preguntaEvaluacion.getId().setIdEvaluacion(record.getId());
                preguntaEvaluacion.setId(preguntaEvaluacion.getId());
                preguntaEvaluacion.setEvaluacion(record);
                preguntaEvaluacion.setUsuarioCrea(record.getEvalUsuCrea());
                record.getPreguntasEvaluacion().add(
                        preguntaEvaluacionService.create(preguntaEvaluacion));
            }

            return record;

        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
