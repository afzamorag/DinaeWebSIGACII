/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.SieduEventoEstadoEnum;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.dto.EventoDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoCategoria;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
import java.net.UnknownHostException;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduEventoJPAService implements SieduEventoService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEventoJPAService.class);
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
    public List<SieduEvento> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduEvento> list;
        try {
            list = sdo.getResultList(em, SieduEvento.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduEvento> findByFilter(EventoFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
        List<SieduEvento> list;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idCarrera", filtro.getCarrera().getCarrerasPK().getIdCarrera());
            params.put("idModalidad", filtro.getModalidad().getId());
            params.put("idProceso", filtro.getProceso().getId());
            list = sdo.getResultListByNamedQuery(em, SieduEvento.FIND_EVENTOS_BY_FILTRO, params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduEvento findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduEvento i = (SieduEvento) sdo.find(em, id, SieduEvento.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SieduEvento create(SieduEvento record, List<SieduCompetencia> competencias, List<Categoria> categorias) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record, competencias, categorias);
        try {
            record.setEstado(SieduEventoEstadoEnum.PENDIENTE.toString());
            record.setEvenFechaCrea(new Date());
            record.setEvenMaquinaCrea(maquina);
            record.setEvenIpCrea(ip);
            record.setEvenFechaVer(new Date());
            sdo.persist(em, record);
            if (categorias != null) {
                for (Categoria cat : categorias) {
                    SieduEventoCategoria categoriasEvento = new SieduEventoCategoria();
                    categoriasEvento.setEvcaFechaCrea(new Date());
                    categoriasEvento.setEvcaUsuCrea(record.getEvenUsuCrea());
                    categoriasEvento.setEvcaMaquinaCrea(maquina);
                    categoriasEvento.setEvcaIpCrea(ip);
                    categoriasEvento.setEvcaEven(record);
                    categoriasEvento.setEvcaCategoria(cat);
                    sdo.persist(em, categoriasEvento);
                }
            }
            if (competencias != null) {
                for (SieduCompetencia comp : competencias) {
                    SieduCompetenciaEvento competenciaEvento = new SieduCompetenciaEvento(record.getId(), comp.getCompComp());
                    competenciaEvento.setCompeUsuCrea(record.getEvenUsuCrea());
                    competenciaEvento.setCompeFechaCrea(new Date());
                    competenciaEvento.setCompeMaquinaCrea(maquina);
                    competenciaEvento.setCompeIpCrea(ip);
                    sdo.persist(em, competenciaEvento);
                }
            }
            em.flush();
            return record;
        } catch (Exception ex) {
            record.setId(null);
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SieduEvento update(SieduEvento record, List<SieduCompetencia> competencias, List<Categoria> categorias) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setEvenFechaMod(new Date());
            record.setEvenMaquinaMod(maquina);
            record.setEvenIpMod(ip);
            record.setEvenFechaVer(new Date());
            sdo.merge(em, record);
            em.flush();
            if (categorias != null) {
                List<SieduEventoCategoria> categoriasList;
                Map<String, Object> params = new HashMap<>();
                params.put("evento", record.getId());
                categoriasList = sdo.getResultListByNamedQuery(em, SieduEventoCategoria.FIND_BY_EVENTO, params);
                for (SieduEventoCategoria catEven : categoriasList) {
                    sdo.remove(em, catEven);
                }
                em.flush();
                for (Categoria cat : categorias) {
                    SieduEventoCategoria categoriasEvento = new SieduEventoCategoria();
                    categoriasEvento.setEvcaFechaCrea(new Date());
                    categoriasEvento.setEvcaUsuCrea(record.getEvenUsuCrea());
                    categoriasEvento.setEvcaMaquinaCrea(maquina);
                    categoriasEvento.setEvcaIpCrea(ip);
                    categoriasEvento.setEvcaEven(record);
                    categoriasEvento.setEvcaCategoria(cat);
                    sdo.persist(em, categoriasEvento);
                }
                em.flush();
            }
            if (competencias != null) {
                List<SieduCompetenciaEvento> competenciasList;
                Map<String, Object> params = new HashMap<>();
                params.put("evento", record.getId());
                competenciasList = sdo.getResultListByNamedQuery(em, SieduCompetenciaEvento.FIND_BY_EVENTO, params);
                for (SieduCompetenciaEvento comEven : competenciasList) {
                    sdo.remove(em, comEven);
                }
                em.flush();
                for (SieduCompetencia comp : competencias) {
                    SieduCompetenciaEvento competenciaEvento = new SieduCompetenciaEvento(record.getId(), comp.getCompComp());
                    competenciaEvento.setCompeUsuCrea(record.getEvenUsuCrea());
                    competenciaEvento.setCompeFechaCrea(new Date());
                    competenciaEvento.setCompeMaquinaCrea(maquina);
                    competenciaEvento.setCompeIpCrea(ip);
                    sdo.persist(em, competenciaEvento);
                }
                em.flush();
            }
        } catch (Exception ex) {
            record.setId(null);
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return record;
    }

    @Override
    public boolean hasAssociatedRecords(Long evento) {
        LOG.debug("metodo: enabledUpdate() ->> parametros: evento {}", evento);
        List<SieduEventoEscuela> list;
        Map<String, Object> params = new HashMap<>();
        params.put("evento", evento);
        list = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_EVENTO, params);
        return !(list == null || list.isEmpty());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(SieduEvento record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            record.setEvenFechaMod(new Date());
            record.setEvenMaquinaMod(maquina);
            record.setEvenIpMod(ip);
            sdo.remove(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void activate(SieduEvento record) throws ServiceException {
        LOG.debug("metodo: activate() ->> parametros: evento {}", record);
        if (record == null) {
            LOG.error("metodo: activate() ->>  el parametro id evento es requerido");
            throw new IllegalArgumentException("metodo: activate() ->>  el parametro id evento es requerido");
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idCarrera", record.getCarrera().getCarrerasPK().getIdCarrera());
            params.put("idModalidad", record.getDominioModalidad().getId());
            params.put("idProceso", record.getDominioProceso().getId());
            List<SieduEvento> list = sdo.getResultListByNamedQuery(em, SieduEvento.FIND_EVENTOS_BY_FILTRO, params);
            if (list != null && !list.isEmpty()) {
                for (SieduEvento oldRecord : list) {
                    // pasar a estado NOVIGENTE la version anterior
                    oldRecord.setEstado(SieduEventoEstadoEnum.NOVIGENTE.toString());
                    sdo.merge(em, oldRecord);
                }
                em.flush();
            }
            // activar el nuevo evento
            record.setEstado(SieduEventoEstadoEnum.VIGENTE.toString());
            record.setEvenFechaMod(new Date());
            record.setEvenMaquinaMod(maquina);
            record.setEvenIpMod(ip);
            em.merge(record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduEvento> findByNivelAcademico(Long idNivelAcademico) throws ServiceException {
        LOG.trace("metodo: findByNivelAcademico() ->> parametros: idNivelAcademico {}", idNivelAcademico);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idCarrera", idNivelAcademico);
            List<SieduEvento> list = sdo.getResultListByNamedQuery(em, SieduEvento.FIND_BY_NIVEL_ACADEMICO, params);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findByNivelAcademico() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduEvento findByIdCarreraVigente(SieduEventoEscuela eventoEscuela) throws ServiceException {
        LOG.trace("metodo: findByIdCarreraVigente() ->> mensaje: {}", eventoEscuela);
        try {
            SieduEvento evento = (SieduEvento) em.createNamedQuery(SieduEvento.FIND_BY_ID_CARRERA_VIGENTE, SieduEvento.class)
                    .setParameter("idCarrera", eventoEscuela.getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera())
                    .setParameter("idModalidad", eventoEscuela.getEveeCapa().getModalidad().getId())
                    .setParameter("idProceso", eventoEscuela.getEveeCapa().getProceso().getId())
                    .setHint("eclipselink.refresh", "true")
                    .setMaxResults(1)
                    .getSingleResult();
            return evento;
        } catch (Exception ex) {
            LOG.error("metodo: findByIdCarreraVigente() ->> mensaje: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<EventoDTO> findForEvaluationByParticipant(String identificacion) throws ServiceException {
        LOG.trace("metodo: findForEvaluationByParticipant() ->> parametros: {}", identificacion);
        try {
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, identificacion);
            List<Object[]> resultList = sdo.getResultListByNativeQuery(em, "SELECT c.ID_CARRERA, c.FUERZA, c.DESCRIPCION, e.EVEN_EVEN, e.EVEN_DOM_MODAL, e.EVEN_DOM_PROCE,pe.PARE_PARE "
                    + "FROM CARRERAS c "
                    + "JOIN SIEDU_EVENTO e "
                    + "  ON c.ID_CARRERA = e.EVEN_ID_CARRERA "
                    + " AND c.FUERZA = e.EVEN_FUERZA "
                    + "JOIN SIEDU_EVENTO_ESCUELA ee "
                    + "  ON e.EVEN_EVEN = ee.EVEE_EVEN "
                    + "JOIN SIEDU_PARTICIPANTE_EVENTO pe "
                    + "  ON ee.EVEE_EVEE = pe.PARE_EVEE "
                    + "JOIN SIEDU_PERSONA p "
                    + "  ON pe.PARE_PERS = p.PERS_PERS "
                    + " AND p.PERS_NROID = ?1 "
                    + "JOIN SIEDU_EVAL_PARTICIPANTE ep "
                    + "  ON pe.pare_pare = ep.evpa_pare "
                    + " AND ep.EVPA_ESTADO = 'A'", params);
            /*List<Object[]> resultList = sdo.getResultListByNativeQuery(em, "SELECT c.ID_CARRERA, c.FUERZA, c.DESCRIPCION, e.EVEN_EVEN, e.EVEN_DOM_MODAL, e.EVEN_DOM_PROCE, pe.PARE_PARE "
              + "FROM CARRERAS c "
              + "JOIN SIEDU_EVENTO e ON c.ID_CARRERA = e.EVEN_ID_CARRERA AND c.FUERZA = e.EVEN_FUERZA "
              + "JOIN SIEDU_EVENTO_ESCUELA ee ON e.EVEN_EVEN = ee.EVEE_EVEN "
              + "JOIN SIEDU_PARTICIPANTE_EVENTO pe ON ee.EVEE_EVEE = pe.PARE_EVEE "
              + "JOIN SIEDU_PERSONA p ON pe.PARE_PERS = p.PERS_PERS "
              + "JOIN SIEDU_EVALUACION eva ON e.EVEN_EVEN = eva.EVAL_EVEN "
              + "WHERE p.PERS_NROID = ?1 "
              + "AND EXISTS (SELECT 1 FROM SIEDU_EVAL_PARTICIPANTE ep WHERE eva.EVAL_EVAL = ep.EVPA_EVAL AND pe.PARE_PARE = ep.EVPA_PARE AND ep.EVPA_ESTADO = 'A')", params);*/
            List<EventoDTO> eventos = new ArrayList<>();
            for (Object[] objects : resultList) {
                EventoDTO evento = new EventoDTO();
                evento.setIdCarrera(((BigDecimal) objects[0]).longValue());
                evento.setFuerza(((BigDecimal) objects[1]).longValue());
                evento.setDescripcionCarrera((String) objects[2]);
                evento.setIdEvento(((BigDecimal) objects[3]).longValue());
                evento.setIdModalidad(((BigDecimal) objects[4]).longValue());
                evento.setIdProceso(((BigDecimal) objects[5]).longValue());
                evento.setIdParticipanteEvento(((BigDecimal) objects[6]).longValue());
                eventos.add(evento);
            }
            return eventos;
        } catch (Exception ex) {
            LOG.error("metodo: findForEvaluationByParticipant() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
