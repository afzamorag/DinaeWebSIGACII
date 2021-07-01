/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.dto.SeguimientoPAEDTO;
import co.gov.policia.dinae.siedu.modelo.Capacitacion;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.CapacitacionFiltro;
import co.gov.policia.dinae.siedu.filtros.DashboardCapacitacionFiltro;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.DashboardCapacitacion;
import co.gov.policia.dinae.siedu.modelo.NecesidadConsolida;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
public class DashboardCapacitacionJPAService implements DashboardCapacitacionService {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardCapacitacionJPAService.class);
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

    @Override
    public List<DashboardCapacitacion> findByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
        List<DashboardCapacitacion> list;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c FROM DashboardCapacitacion c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getEscuela() != null) {
                jpql.append("AND c.codEscuela = :escuela ");
                params.put("escuela", filtro.getEscuela());
            }
            if (filtro.getVigencia() != null) {
                jpql.append("AND c.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
        }
        jpql.append("ORDER BY c.escuela, c.estrategia, c.carrera");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<DashboardCapacitacion> findGeneralTargetByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findGeneralTargetByFilter() ->> parametros: filtro {}", filtro);
        List<DashboardCapacitacion> list = new ArrayList<>();
        Long id = 0L;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c.escuela, c.estrategia, SUM(c.mT1), SUM(c.cT1), SUM(c.mT2), SUM(c.cT2), SUM(c.mT3), SUM(c.cT3), SUM(c.mT4), SUM(c.cT4), SUM(c.meta), SUM(c.total), c.vigencia FROM DashboardCapacitacion c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getEscuela() != null) {
                jpql.append("AND c.codEscuela = :escuela ");
                params.put("escuela", filtro.getEscuela());
            }
            if (filtro.getVigencia() != null) {
                jpql.append("AND c.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
        }
        jpql.append("GROUP BY c.escuela, c.estrategia, c.vigencia ");
        jpql.append("ORDER BY c.escuela, c.estrategia");
        try {
            List<Object[]> obj = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            if (!obj.isEmpty()) {
                for (Object[] i : obj) {
                    DashboardCapacitacion dbc = new DashboardCapacitacion();
                    id += 1;
                    dbc.setId(id);
                    dbc.setEscuela((String) i[0]);
                    dbc.setEstrategia((String) i[1]);
                    dbc.setmT1((Long) i[2]);
                    dbc.setcT1((Long) i[3]);
                    dbc.setmT2((Long) i[4]);
                    dbc.setcT2((Long) i[5]);
                    dbc.setmT3((Long) i[6]);
                    dbc.setcT3((Long) i[7]);
                    dbc.setmT4((Long) i[8]);
                    dbc.setcT4((Long) i[9]);
                    dbc.setMeta((Long) i[10]);
                    dbc.setTotal((Long) i[11]);
                    dbc.setVigencia((String) i[12]);
                    list.add(dbc);
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<DashboardCapacitacion> findYearGeneralTargetByFilter(DashboardCapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findGeneralTargetByFilter() ->> parametros: filtro {}", filtro);
        List<DashboardCapacitacion> list = new ArrayList<>();
        Long id = 0L;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c.escuela, c.estrategia, SUM(c.meta), SUM(c.total) FROM DashboardCapacitacion c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getEscuela() != null) {
                jpql.append("AND c.codEscuela = :escuela ");
                params.put("escuela", filtro.getEscuela());
            }
            if (filtro.getVigencia() != null) {
                jpql.append("AND c.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
        }
        jpql.append("GROUP BY c.escuela, c.estrategia ");
        jpql.append("ORDER BY c.escuela, c.estrategia");
        try {
            List<Object[]> obj = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            if (!obj.isEmpty()) {
                for (Object[] i : obj) {
                    DashboardCapacitacion dbc = new DashboardCapacitacion();
                    id += 1;
                    dbc.setId(id);
                    dbc.setEscuela((String) i[0]);
                    dbc.setEstrategia((String) i[1]);
                    dbc.setMeta((Long) i[2]);
                    dbc.setTotal((Long) i[3]);
                    list.add(dbc);
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<DashboardCapacitacion> findYearGeneralTarget(DashboardCapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findGeneralTargetByFilter() ->> parametros: filtro {}", filtro);
        List<DashboardCapacitacion> list = new ArrayList<>();
        Long id = 0L;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c.estrategia, SUM(c.mT1), SUM(c.cT1), SUM(c.mT2), SUM(c.cT2), SUM(c.mT3), SUM(c.cT3), SUM(c.mT4), SUM(c.cT4), SUM(c.meta), SUM(c.total) FROM DashboardCapacitacion c WHERE 1 = 1 ");
        params = new HashMap<>();
        jpql.append("AND c.vigencia = :vigencia ");
        params.put("vigencia", filtro.getVigencia());
        jpql.append("GROUP BY c.estrategia ");
        jpql.append("ORDER BY c.estrategia");
        try {
            List<Object[]> obj = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            if (!obj.isEmpty()) {
                for (Object[] i : obj) {
                    DashboardCapacitacion dbc = new DashboardCapacitacion();
                    id += 1;
                    dbc.setId(id);
                    dbc.setEstrategia((String) i[0]);
                    dbc.setmT1((Long) i[1]);
                    dbc.setcT1((Long) i[2]);
                    dbc.setmT2((Long) i[3]);
                    dbc.setcT2((Long) i[4]);
                    dbc.setmT3((Long) i[5]);
                    dbc.setcT3((Long) i[6]);
                    dbc.setmT4((Long) i[7]);
                    dbc.setcT4((Long) i[8]);
                    dbc.setMeta((Long) i[9]);
                    dbc.setTotal((Long) i[10]);
                    list.add(dbc);
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<SeguimientoPAEDTO> findByFilterGraph(DashboardCapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilterGraph() ->> parametros: filtro {}", filtro);
        List<SeguimientoPAEDTO> list = new ArrayList<>();
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c.escuela, c.estrategia, SUM(c.meta), SUM(c.total) FROM DashboardCapacitacion c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getEscuela() != null) {
                jpql.append("AND c.codEscuela = :escuela ");
                params.put("escuela", filtro.getEscuela());
            }
            if (filtro.getVigencia() != null) {
                jpql.append("AND c.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
        }
        jpql.append("GROUP BY c.escuela, c.estrategia ");
        jpql.append("ORDER BY c.escuela, c.estrategia");
        try {
            List<Object[]> obj = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            if (!obj.isEmpty()) {
                for (Object[] i : obj) {
                    SeguimientoPAEDTO dto = new SeguimientoPAEDTO();
                    dto.setEscuela(((String) i[0]));
                    dto.setEstrategia((String) i[1]);
                    Long m = ((Long) i[2]);
                    dto.setMeta(m.intValue());
                    Long t = ((Long) i[3]);
                    dto.setTotal(t.intValue());
                    list.add(dto);
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
