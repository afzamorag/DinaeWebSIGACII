package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import javax.ejb.EJB;

import javax.ejb.Stateless;

@Stateless
public class PreguntaEvaluacionJPAService implements PreguntaEvaluacionService {

    private static final Logger LOG = LoggerFactory.getLogger(PreguntaEvaluacionJPAService.class);

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;

    @EJB
    private EvaluacionService evaluacionService;

    @Inject
    @GenericSDOQualifier
    private SDO<PreguntaEvaluacion, PreguntaEvaluacionPK> sdo;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO<PreguntaEvaluacion, PreguntaEvaluacionPK>();
        }
    }

    @Override
    public List<PreguntaEvaluacion> findByEvaluation(Long idEvaluation)
            throws ServiceException {
        LOG.debug("metodo: findByEvaluation() ->> params: idEvaluation {}", idEvaluation);
        List<PreguntaEvaluacion> list;
        try {
            list = em.createNamedQuery(PreguntaEvaluacion.FIND_BY_ID_EVALUACION, PreguntaEvaluacion.class)
                    .setParameter("idEvaluacion", idEvaluation)
                    .getResultList();
        } catch (Exception ex) {
            LOG.error("metodo: findByEvaluation() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public void delete(PreguntaEvaluacion record) throws ServiceException {
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
    public void delete(PreguntaEvaluacionPK id) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: id {}", id);
        try {
            sdo.remove(em, id, PreguntaEvaluacion.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public int deleteByEvaluation(Long idEvaluation) throws ServiceException {
        LOG.debug("metodo: deleteByEvaluation() ->> params: idEvaluation {}", idEvaluation);
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("idEvaluacion", idEvaluation);
            return sdo.executeNamedQuery(em, PreguntaEvaluacion.DELETE_BY_EVALUACION, params);
        } catch (Exception ex) {
            LOG.error("metodo: deleteByEvaluation() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }

    }

    @Override
    public PreguntaEvaluacion create(PreguntaEvaluacion record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setAspecto(em.getReference(ParametroEvaluacion.class, record.getAspecto().getId()));
            record.setPregunta(em.getReference(ParametroEvaluacion.class, record.getPregunta().getId()));
            record.setCompetencia(em.getReference(SieduCompetencia.class, record.getCompetencia().getCompComp()));

            record.setFechaCrea(fecha);
            record.setMaquinaCrea(hostName);
            record.setIpCrea(hostAddress);

            record = sdo.persist(em, record);

            double porcentaje = 0;
            boolean existPercentage = false;
            Evaluacion evaluacion = record.getEvaluacion();
            final List<PreguntaEvaluacion> preguntasEvaluacion = this.findByEvaluation(evaluacion.getId());
            for (PreguntaEvaluacion preguntaEvaluacion1 : preguntasEvaluacion) {
                if (preguntaEvaluacion1.getValorPorcentaje() != null) {
                    existPercentage = true;
                    porcentaje += preguntaEvaluacion1.getValorPorcentaje().doubleValue();
                }
            }
            if (preguntasEvaluacion.size() == evaluacion.getEvalNroPreg() && ((existPercentage && porcentaje == 100) || (!existPercentage))) {
                evaluacion.setEstadoEvaluacion(EstadoEvaluacionEnum.ACTIVA);
                evaluacion.setEvalUsuMod(record.getUsuarioCrea());
                this.evaluacionService.update(evaluacion);
            }

            return record;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(PreguntaEvaluacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setFechaModifica(fecha);
            record.setMaquinaModifica(hostName);
            record.setIpModifica(hostAddress);

            sdo.merge(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
