/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoEscuelaFiltro;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduEventoEscuelaJPAService implements SieduEventoEscuelaService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEventoEscuelaJPAService.class);
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
    public List<SieduEventoEscuela> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduEventoEscuela> list;
        try {
            list = sdo.getResultList(em, SieduEventoEscuela.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduEventoEscuela> findByFilter(EventoEscuelaFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", filtro);
        List<SieduEventoEscuela> list;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM SieduEventoEscuela d WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getVigencia() != null) {
                jpql.append("AND d.eveeCapa.pae.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
            if (filtro.getCapaUdeEscu() != null) {
                jpql.append("AND d.eveeCapa.escuela.consecutivo = :capaUdeEscu ");
                params.put("capaUdeEscu", filtro.getCapaUdeEscu());
            }
            if (filtro.getIdModalidad() != null) {
                jpql.append("AND d.eveeCapa.modalidad.id = :modalidad ");
                params.put("modalidad", filtro.getIdModalidad());
            }
            if (filtro.getIdCarrera() != null) {
                jpql.append("AND d.eveeCapa.carrera.carrerasPK.idCarrera = :carrera ");
                params.put("carrera", filtro.getIdCarrera());
            }
        }
        jpql.append("ORDER BY d.eveeCapa.carrera.carrerasPK.idCarrera, d.eveeTrimes, d.eveeNroEvento");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduEventoEscuela findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduEventoEscuela i = (SieduEventoEscuela) sdo.find(em, id, SieduEventoEscuela.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduEventoEscuela create(SieduEventoEscuela record) throws ServiceException {
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
    public void update(SieduEventoEscuela record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setEveeFechaMod(new Date());
            record.setEveeMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setEveeIpMod(InetAddress.getLocalHost().getHostAddress());
            sdo.merge(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduEventoEscuela record) throws ServiceException {
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
            sdo.remove(em, id, SieduEventoEscuela.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    /**
     *
     * @param usuario
     * @param evento
     * @throws ServiceException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cerrarEvento(String usuario, SieduEventoEscuela evento) throws ServiceException {
        LOG.debug("metodo: cerrarEvento()");
        try {
            String maquina = InetAddress.getLocalHost().getHostName();
            String ip = InetAddress.getLocalHost().getHostAddress();
            try {
                // ejecutar procedimiento que Cerrar Evento Escuela
                Connection connection = this.datasource.getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("call ");
                sb.append(SPEnum.SP_CERRAR_EVENTO_ESCUELA.getNombreSP());
                sb.append("(?, ?, ?, ?,?,?)");
                CallableStatement cs = connection.prepareCall(sb.toString());
                cs.setLong(1, evento.getEveeEvee());
                cs.setString(2, usuario); // auditoria
                cs.setString(3, maquina); // auditoria
                cs.setString(4, ip); // auditoria
                cs.setLong(5, evento.getEvaluacion().getId());
                cs.registerOutParameter(6, Types.VARCHAR);
                cs.executeQuery();
                String msgs = cs.getString(6);
                if (msgs != null) {
                    throw new ServiceException(msgs);
                }
            } catch (SQLException ex) {
                LOG.error("metodo: cerrarPAE() ->> mensaje ->> {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        } catch (Exception ex) {
            LOG.error("metodo: cerrarPAE() ->> mensaje ->> {}", ex.getMessage());
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Evaluacion consultarEvaluacionEvento(SieduEventoEscuela evento) throws ServiceException {
        LOG.debug("metodo: consultarEvaluacionEvento() ->> Parametros: evento", evento);
        String query;
        try {
            query = "select t.* from siedu_evaluacion t where t.eval_pae = ?1 and t.eval_estado = 'ACTIVA' and t.eval_descri like '%";
            query = query + evento.getEveeEven().getDominioModalidad().getNombre() + "%'";            
            Evaluacion evaluacion = (Evaluacion) em.createNativeQuery(query, Evaluacion.class)
                    //Evaluacion evaluacion = (Evaluacion) em.createNativeQuery("SELECT T.* FROM SIEDU_EVALUACION T, SIEDU_EVENTO S WHERE T.EVAL_EVEN = S.EVEN_EVEN AND T.EVAL_EVEN = ?1 AND T.EVAL_ESTADO = 'ACTIVA'", Evaluacion.class)
                    .setParameter(1, evento.getEveeCapa().getPae().getId())
                    .getSingleResult();
            return evaluacion;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        } catch (Exception ex) {
            LOG.error("metodo: consultarEvaluacionEvento() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduFechasMaxMinEventoDTO consultasFechasMaxMinInasistencia(SieduEventoEscuela evento) throws ServiceException {
        LOG.debug("metodo: consultasFechasMAxMinInasistencia() ->> Parametros: evento", evento);
        try {
            SieduFechasMaxMinEventoDTO fechasEventoDTO = new SieduFechasMaxMinEventoDTO();
            Object[] fechasEvento = (Object[]) em.createNativeQuery("SELECT MIN(t.inae_fecha) AS minFecha, MAX(t.inae_fecha) AS maxFecha FROM siedu_inasiste_evento t, siedu_participante_evento s, siedu_evento_escuela e WHERE t.inae_pare = s.pare_pare AND s.pare_evee = e.evee_evee AND e.evee_evee = ?1")
                    .setParameter(1, evento.getEveeEvee())
                    .getSingleResult();
            fechasEventoDTO.setMinFecha((Date) fechasEvento[0]);
            fechasEventoDTO.setMaxFecha((Date) fechasEvento[1]);
            return fechasEventoDTO;
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        } catch (Exception ex) {
            LOG.error("metodo: consultasFechasMAxMinInasistencia() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduEventoEscuela> findByPaeCapacitacion(Long capaPae) throws ServiceException {
        LOG.debug("metodo: findByPaeCapacitacion() ->> Parametros: evento", capaPae);
        List<SieduEventoEscuela> lst = new ArrayList<>();
        try {
            lst = em.createNamedQuery(SieduEventoEscuela.FIND_BY_PAE_CAPACITACION, SieduEventoEscuela.class)
                    .setParameter("eveCapa", capaPae)
                    .getResultList();
            return lst;
        } catch (Exception ex) {
            LOG.error("metodo: findByPaeCapacitacion() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
