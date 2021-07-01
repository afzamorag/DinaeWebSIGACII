/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.HorasDocenteCapacitacion;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduHorasDocenteEventoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
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
import javax.persistence.Query;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduDocenteEventoJPAService implements SieduDocenteEventoService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduDocenteEventoJPAService.class);
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
    public List<SieduDocenteEvento> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduDocenteEvento> list;
        try {
            list = sdo.getResultList(em, SieduDocenteEvento.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduDocenteEvento> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduDocenteEvento> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT i FROM SieduDocenteEvento d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("descripcion") != null) {
                jpql.append("AND d.descripcionSieduDocenteEvento = :descripcion ");
            }
            if (params.get("vigente") != null) {
                jpql.append("AND d.vigente = :vigente ");
            }
            if (params.get("tipo") != null) {
                jpql.append("AND d.idTipoSieduDocenteEvento.idTipoSieduDocenteEvento = :tipo ");
            }
        }
        jpql.append("ORDER BY d.descripcionSieduDocenteEvento");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduDocenteEvento findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduDocenteEvento i = (SieduDocenteEvento) sdo.find(em, id, SieduDocenteEvento.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduDocenteEvento create(SieduDocenteEvento record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            record.setDoceFechaCrea(new Date());
            record.setDoceMaquinaCrea(InetAddress.getLocalHost().getHostName());
            record.setDoceIpCrea(InetAddress.getLocalHost().getHostAddress());
            sdo.persist(em, record);
            em.flush();
            return record;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            record.setDoceDoce(null);
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(SieduDocenteEvento record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setDoceFechaMod(new Date());
            record.setDoceMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setDoceIpMod(InetAddress.getLocalHost().getHostAddress());
            sdo.merge(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            record.setDoceDoce(null);
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduDocenteEvento record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            record.setDoceFechaMod(new Date());
            record.setDoceMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setDoceIpMod(InetAddress.getLocalHost().getHostAddress());
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
            sdo.remove(em, id, SieduDocenteEvento.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduDocenteEvento> findListById(SieduEventoEscuela id) throws ServiceException {
        LOG.debug("metodo: findListById()", id);
        try {
            return em.createNamedQuery(SieduDocenteEvento.FIND_BY_DOCEEVEE, SieduDocenteEvento.class)
                    .setParameter("doceEvee", id.getEveeEvee())
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();
        } catch (Exception ex) {
            LOG.error("metodo: metodo: findListById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduHorasDocenteEventoDTO> findListByIdDistinct(SieduEventoEscuela id) throws ServiceException {
        LOG.debug("metodo: findListByIdDistinct()", id);
        try {
            List<SieduHorasDocenteEventoDTO> horasDictadasEvento = (List<SieduHorasDocenteEventoDTO>) sdo.getResultListByNativeQuery(em, "SELECT DISTINCT D.DOCE_EVEE AS idEvento, D.DOCE_TEMA AS idTema, T.TEMA_HORAS AS horasTema FROM SIEDU_DOCENTE_EVENTO D, SIEDU_TEMA T WHERE D.DOCE_TEMA = T.TEMA_TEMA AND D.DOCE_EVEE = ?1", SieduHorasDocenteEventoDTO.class, id.getEveeEvee());
            return horasDictadasEvento;
        } catch (Exception ex) {
            LOG.error("metodo: findDatosEmpleadoPolicial() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SieduDocenteEvento> findByDoceeveeDocepers(SieduPersona persona, SieduEventoEscuela eventoEscuela) throws ServiceException {
        LOG.debug("metodo:findByDoceeveeDocepers()", persona, eventoEscuela);
        try {
            return em.createNamedQuery(SieduDocenteEvento.FIND_BY_DOCEEVEE_DOCEPERS, SieduDocenteEvento.class)
                    .setParameter("doceEvee", eventoEscuela.getEveeEvee())
                    .setParameter("docePers", persona.getPersPers())
                    .getResultList();
        } catch (Exception ex) {
            LOG.error("metodo: metodo: metodo:findByDoceeveeDocepers() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<HorasDocenteCapacitacion> findByPeriod(Map<String, Object> params) throws ServiceException {
        LOG.debug("metodo: findByPeriod()", params);
        List<HorasDocenteCapacitacion> lst = new ArrayList<>();
        List<Object[]> lstTmp;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d.escuela, d.identificacion, d.docente, SUM(d.hora) FROM HorasDocenteCapacitacion d WHERE 1 = 1 ");
        if (params != null) {
            jpql.append("AND d.codEscuela = :escuela ");
            jpql.append("AND d.fecha BETWEEN :fechaini AND :fechafin ");
            jpql.append("AND d.fechaRegistro BETWEEN :fechaini AND :fecharegistro ");
            if (params.get("identificacion") != null) {
                jpql.append("AND d.identificacion = :identificacion ");
            }
            jpql.append("GROUP BY d.escuela, d.identificacion, d.docente ");
            jpql.append("ORDER BY d.escuela, d.identificacion, d.docente");
            try {
                lstTmp = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
                if (!lstTmp.isEmpty()) {
                    for (Object[] i : lstTmp) {
                        HorasDocenteCapacitacion h = new HorasDocenteCapacitacion();
                        h.setId(Long.valueOf((String) i[1]));
                        h.setEscuela((String) i[0]);
                        h.setIdentificacion((String) i[1]);
                        h.setDocente((String) i[2]);
                        Long l = ((Long) i[3]);
                        h.setHora(l.intValue());
                        lst.add(h);
                    }
                }
            } catch (Exception ex) {
                LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        }
        return lst;
    }
}
