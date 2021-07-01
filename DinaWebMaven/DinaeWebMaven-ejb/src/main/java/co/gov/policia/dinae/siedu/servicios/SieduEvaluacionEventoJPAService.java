/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EventoEscuelaFiltro;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.modelo.SieduEvaluacionEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduEvaluacionEventoJPAService implements SieduEvaluacionEventoService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduEvaluacionEventoJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;
    @Resource(mappedName = "jdbc/DinaeWebDS")
    private DataSource datasource;
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

    /*@Override
    public List<SieduEvaluacionEvento> findById(Long id) throws ServiceException {
        LOG.trace("metodo: findById() ->> parametros: id {}", id);
        List<SieduEvaluacionEvento> list = new ArrayList<>();;
        if (id != null) {
            try {
                list = em.createNamedQuery(SieduEvaluacionEvento.FIND_BY_ID, SieduEvaluacionEvento.class)
                        .setParameter("id", id)
                        .getResultList();
                return list;
            } catch (Exception ex) {
                LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        } else {
            return null;
        }
    }*/
    @Override
    public List<SieduEvaluacionEvento> findById(Long id) throws ServiceException {
        LOG.trace("metodo: findById() ->> parametros: params {}", id);
        Long seq = 0L;
        List<SieduEvaluacionEvento> lst = new ArrayList<>();
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        params = new HashMap<>();        
        if (id != null) {
            jpql.append("SELECT s.aspecto, s.competencia, s.pregunta, s.calificacion, s.porcentaje FROM SieduEvaluacionEvento s WHERE s.eveeEvee = :evee ");
            params.put("evee", id);           
            jpql.append("ORDER BY s.aspecto, s.competencia, s.pregunta");
            try {
                List<Object[]> result = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
                if (!result.isEmpty()) {
                    for (Object[] i : result) {
                        SieduEvaluacionEvento evalEval = new SieduEvaluacionEvento();
                        evalEval.setId(seq);
                        evalEval.setAspecto((String) i[0]);
                        evalEval.setCompetencia((String) i[1]);
                        evalEval.setPregunta((String) i[2]);
                        evalEval.setCalificacion((double) i[3]);
                        evalEval.setPorcentaje((double) i[4]);
                        lst.add(evalEval);                      
                    }
                    return lst;
                } else {
                    return null;
                }

            } catch (Exception ex) {
                LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        } else {
            return null;
        }
    }

}
