package co.gov.policia.dinae.siedu.servicios;

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
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.modelo.SieduEvalCategoria;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.modelo.SiudeEvalCategoriaPK;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;

import javax.ejb.Stateless;

@Stateless
public class EvaluacionCategoriaJPAService implements
		EvaluacionCategoriaService {

    private static final Logger LOG = LoggerFactory
            .getLogger(EvaluacionCategoriaJPAService.class);

    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;

    @Inject
    @GenericSDOQualifier
    private SDO<SieduEvalCategoria, SiudeEvalCategoriaPK> sdo;

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO<SieduEvalCategoria, SiudeEvalCategoriaPK>();
        }
    }

	@Override
	public List<SieduEvalCategoria> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        try {
            return sdo.getResultList(em, SieduEvalCategoria.class);
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
	}

	@Override
	public List<SieduEvalCategoria> findByEvaluation(Long idEvaluation)
			throws ServiceException {
		LOG.debug("metodo: findByEvaluation() ->> parametros: idEvaluation {}", idEvaluation);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idEvaluacion", idEvaluation);
			return sdo.getResultListByNamedQuery(em, "", params);
		} catch (Exception e) {
            LOG.error("metodo: findByEvaluation() ->> mensaje: {}", e.getMessage());
            throw new ServiceException(e);
		}
	}

    @Override
    public SieduEvalCategoria findById(SiudeEvalCategoriaPK id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            return sdo.find(em, id,
            		SieduEvalCategoria.class);
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduEvalCategoria create(SieduEvalCategoria record) throws ServiceException {
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
			
            sdo.persist(em, record);
            // em.flush();
            return record;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(SieduEvalCategoria record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {

			String hostName = InetAddress.getLocalHost().getHostName();
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			Date fecha = new Date();
			String usuario = "tmp";
			
			record.setUsuarioModifica(usuario);
			record.setFechaModifica(fecha);
			record.setMaquinaModifica(hostName);
			record.setIpModica(hostAddress);
			
            sdo.merge(em, record);
            // em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduEvalCategoria record) throws ServiceException {
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
    public void delete(SiudeEvalCategoriaPK id) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: id {}", id);
        try {
            sdo.remove(em, id, SieduEvalCategoria.class);
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
			return sdo.executeNamedQuery(em, "", params);
		} catch (Exception e) {
            LOG.error("metodo: deleteByEvaluation() ->> mensaje: {}", e.getMessage());
            throw new ServiceException(e);
		}
	}

}
