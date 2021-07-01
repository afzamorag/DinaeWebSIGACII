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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Cobertura;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;

@Stateless
public class ParametroEvaluacionJPAService implements
        ParametroEvaluacionService {

    private static final Logger LOG = LoggerFactory.getLogger(ParametroEvaluacionJPAService.class);
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
    public List<ParametroEvaluacion> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<Cobertura> list;
        try {
            return sdo.getResultList(em, ParametroEvaluacion.class);
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public ParametroEvaluacion findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            return (ParametroEvaluacion) sdo.find(em, id, ParametroEvaluacion.class);
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public ParametroEvaluacion create(ParametroEvaluacion record)
            throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();
            String usuario = "tmp";

            record.setUsuarioCrea(usuario);
            record.setFechaCrea(fecha);
            record.setMaquinaCrea(hostName);
            record.setIpCrea(hostAddress);

            return (ParametroEvaluacion) sdo.persist(em, record);
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(ParametroEvaluacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

            String hostName = InetAddress.getLocalHost().getHostName();
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Date fecha = new Date();
            String usuario = "tmp";

            record.setUsuarioModifica(usuario);
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
    public void delete(ParametroEvaluacion record) throws ServiceException {
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
            sdo.remove(em, id, ParametroEvaluacion.class);
            // em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<ParametroEvaluacion> findByType(Long tipo)
            throws ServiceException {
        LOG.debug("metodo: findByType() ->> parametros: id {}", tipo);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("idTipo", tipo);
            return sdo.getResultListByNamedQuery(em, ParametroEvaluacion.FIND_BY_TIPO, params);
        } catch (Exception ex) {
            LOG.error("metodo: findByEvaluation() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

}
