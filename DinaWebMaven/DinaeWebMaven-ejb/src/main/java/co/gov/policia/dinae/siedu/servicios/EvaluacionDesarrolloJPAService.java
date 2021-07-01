package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.EstadoParticipanteEvaluacionEnum;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrollo;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrolloPK;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipantePK;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Juan Jose Buzon
 */
@Stateless
public class EvaluacionDesarrolloJPAService implements
        EvaluacionDesarrolloService {

    private static final Logger LOG = LoggerFactory.getLogger(EvaluacionDesarrolloJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;
    @EJB
    private EvaluacionService evaluacionService;
    @EJB
    private EvaluacionParticipanteService evaluacionParticipanteService;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public List<EvaluacionDesarrollo> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        try {
            return sdo.getResultList(em, EvaluacionDesarrollo.class);
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public EvaluacionDesarrollo findById(EvaluacionDesarrolloPK id)
            throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            return (EvaluacionDesarrollo) sdo.find(em, id, EvaluacionDesarrollo.class);
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public EvaluacionDesarrollo create(EvaluacionDesarrollo record)
            throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setFechaCrea(fecha);
            record.setMaquinaCrea(hostName);
            record.setIpCrea(hostAddress);

            return (EvaluacionDesarrollo) sdo.persist(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(EvaluacionDesarrollo record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            record.setFechaModifica(fecha);
            record.setMaquinaModifica(hostName);
            record.setIpModifica(hostAddress);

            sdo.merge(em, record);
            // em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(EvaluacionDesarrollo record) throws ServiceException {
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
            sdo.remove(em, id, EvaluacionDesarrollo.class);
            // em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<EvaluacionDesarrollo> create(List<EvaluacionDesarrollo> records, String observation) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", records);

        try {

            EvaluacionDesarrollo desarrollo = records.get(0);
            Long idEvaluacion = desarrollo.getEvaluacionDesarrolloPK().getIdEvaluacion();
            EvaluacionParticipantePK evaluacionParticipantePK = new EvaluacionParticipantePK();
            evaluacionParticipantePK.setIdEvaluacion(idEvaluacion);
            evaluacionParticipantePK.setIdParticipanteEvento(desarrollo.getEvaluacionDesarrolloPK().getIdParticipanteEvento());
            String user = desarrollo.getUsuarioCrea();

            for (EvaluacionDesarrollo record : records) {
                record = this.create(record);
            }
            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();

            EvaluacionParticipante evaluacionParticipante = this.evaluacionParticipanteService.findById(evaluacionParticipantePK);
            evaluacionParticipante.setObservacion(observation);
            evaluacionParticipante.setEstado(EstadoParticipanteEvaluacionEnum.D);
            evaluacionParticipante.setUsuarioModifica(user);            
            evaluacionParticipante.setIpModifica(hostAddress);
            evaluacionParticipante.setMaquinaModifica(hostName);
            evaluacionParticipante.setFechaModifica(fecha);
            this.evaluacionParticipanteService.update(evaluacionParticipante);
                        
            Evaluacion evaluacion = this.evaluacionService.findById(idEvaluacion);
            evaluacion.setAplicada("S");
            evaluacion.setEvalUsuMod(user);
            evaluacion.setEvalIpMod(hostAddress);
            evaluacion.setEvalMaquinaMod(hostName);
            evaluacion.setEvalFechaMod(fecha);
            
            this.evaluacionService.update(evaluacion);

            return records;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<EvaluacionDesarrollo> findByEvaluation(Long idEvaluation)
            throws ServiceException {
        LOG.debug("metodo: findByEvaluation() ->> params: idEvaluation {}", idEvaluation);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idEvaluacion", idEvaluation);
            return sdo.getResultListByNamedQuery(em, EvaluacionDesarrollo.FIND_BY_EVALUACION, params);
        } catch (Exception ex) {
            LOG.error("metodo: findByEvaluation() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
