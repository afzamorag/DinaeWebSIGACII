/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.modelo.Capacitacion;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.CapacitacionFiltro;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.NecesidadConsolida;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
import javax.jms.Message;
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class CapacitacionJPAService implements CapacitacionService {

    private static final Logger LOG = LoggerFactory.getLogger(CapacitacionJPAService.class);
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
    public void delete(Long id) throws ServiceException {
        LOG.trace("metodo: delete()");
        try {
            sdo.remove(em, id, Capacitacion.class);
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Capacitacion> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<Capacitacion> list;

        try {
            list = sdo.getResultList(em, Capacitacion.class
            );
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<Capacitacion> findByFilter(CapacitacionFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
        List<Capacitacion> list;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c FROM Capacitacion c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getPae() != null) {
                jpql.append("AND c.pae.id = :pae ");
                params.put("pae", filtro.getPae().getId());
            }
            if (filtro.getEscuela() != null) {
                jpql.append("AND c.escuela.pk.consecutivo = :escuela ");
                params.put("escuela", filtro.getEscuela());
            }
            if (filtro.getProceso() != null) {
                jpql.append("AND c.proceso.id = :proceso ");
                params.put("proceso", filtro.getProceso());
            }
            if (filtro.getEstrategia() != null) {
                jpql.append("AND c.estrategia.id = :estrategia ");
                params.put("estrategia", filtro.getEstrategia());
            }
            if (filtro.getCarrera() != null) {
                jpql.append("AND c.carrera.carrerasPK.idCarrera = :carrera ");
                params.put("carrera", filtro.getCarrera());
            }
            if (filtro.getEstado() != null) {
                jpql.append("AND c.estado.id = :estado ");
                params.put("estado", filtro.getEstado());
            }
            if (filtro.getModalidad() != null) {
                jpql.append("AND c.modalidad.id = :modalidad ");
                params.put("modalidad", filtro.getModalidad());
            }
            if (filtro.getPresupuesto() != null) {
                jpql.append("AND c.manejaPresupuesto = :manejaPresupuesto ");
                if (filtro.getPresupuesto()) {
                    params.put("manejaPresupuesto", DecisionEnum.SI.toString());
                } else {
                    params.put("manejaPresupuesto", DecisionEnum.NO.toString());
                }
            }
        }
        jpql.append("ORDER BY c.id DESC");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Capacitacion findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);

        try {
            Capacitacion i = (Capacitacion) sdo.find(em, id, Capacitacion.class
            );
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void aprobar(Capacitacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            if (record.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
                if (record.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || record.getEstado().equals(NecesidadEstadoEnum.NO_APROBADO.getEstado())) {
                    // cuando el pae se encuentra en estado ABIERTO y la capacitacion se encuentra en estado PENDIENTE 
                    // - debe cambiar el estado de la capacitacion a APROBADO y 
                    // - debe actualizar el estado de las necesidades que agrupa la capacitacion
                    record.setEstado(NecesidadEstadoEnum.APROBADO.getEstado());
                    record.setModFecha(new Date()); // auditoria
                    record.setModMaquina(maquina); // auditoria
                    record.setModIP(ip); // auditoria
                    sdo.merge(em, record);
                    Map<String, Object> params = new HashMap<>();
                    params.put("capacitacion", record.getId());
                    List<NecesidadConsolida> necesidades = sdo.getResultListByNamedQuery(em, NecesidadConsolida.FIND_BY_CAPACITACION, params);
                    if (necesidades != null) {
                        int affected = 0;
                        for (NecesidadConsolida registro : necesidades) {
                            registro.getNecesidad().setEstado(NecesidadEstadoEnum.APROBADO.getEstado());
                            registro.getNecesidad().setModUsuario(record.getModUsuario()); // auditoria
                            registro.getNecesidad().setModFecha(new Date()); // auditoria
                            registro.getNecesidad().setModMaquina(maquina); // auditoria
                            registro.getNecesidad().setModIP(ip); // auditoria
                            sdo.merge(em, registro.getNecesidad());
                            affected++;
                        }
                        LOG.debug("Necesidades afectadas: #{}", affected);
                    }
                    em.flush();
                } else {
                    // significa q estan modificando un registro ya aprobado
                    record.setModFecha(new Date()); // auditoria
                    record.setModMaquina(maquina); // auditoria
                    record.setModIP(ip); // auditoria
                    record.setEstado(NecesidadEstadoEnum.APROBADO.getEstado());
                    sdo.merge(em, record);
                    em.flush();

                }
            } else {
                // significa que el pae se encuentra en estado CERRADO, y por tanto solo permite modificar informacion aociada a los trimestres
                Capacitacion oldRecord = (Capacitacion) sdo.find(em, record.getId(), Capacitacion.class);
                if (!record.equals(oldRecord)) {
                    // 1er trimestre
                    {
                        Map<Integer, SieduEventoEscuela> map = new HashMap();
                        // se consultan los registros existentes para el primer trimestre
                        if (oldRecord.getNroEventosT1() != null) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("capacitacion", record.getId());
                            params.put("trimestre", 1);
                            List<SieduEventoEscuela> eventos = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_CAPACITACION_AND_TRIMESTE, params);
                            if (eventos != null) { // que no deberia
                                for (SieduEventoEscuela evento : eventos) {
                                    LOG.debug("Evento numero {}", evento.getEveeNroEvento());
                                    map.put(evento.getEveeNroEvento(), evento);
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea menor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT1().compareTo(record.getNroEventosT1()) < 0) {
                            if (record.getNroEventosT1() != 0) {
                                for (int i = 1; i <= record.getNroEventosT1(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento == null) {
                                        SieduEventoEscuela evento = new SieduEventoEscuela();
                                        evento.setEveeCapa(record);
                                        evento.setEveeNroEvento(i);
                                        evento.setEveeTrimes(1);
                                        evento.setEveeCerrado(DecisionEnum.NO.toString());
                                        evento.setEveeUsuCrea(record.getModUsuario());
                                        evento.setEveeFechaCrea(new Date());
                                        evento.setEveeMaquinaCrea(maquina);
                                        evento.setEveeIpCrea(ip);
                                        sdo.persist(em, evento);
                                    }
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea mayor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT1().compareTo(record.getNroEventosT1()) > 0) {
                            if (oldRecord.getNroEventosT1() != 0) {
                                for (int i = (record.getNroEventosT1() + 1); i <= oldRecord.getNroEventosT1(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento != null) {
                                        if (oldEvento.getEveeFechaini() == null && oldEvento.getEveeFechafin() == null) {
                                            sdo.remove(em, oldEvento);
                                        } else {
                                            BigDecimal count = (BigDecimal) sdo.findByNativeQuery(em, SieduEventoEscuela.NATIVE_QUERY_COUNT_BY_CAPACITACION_AND_TRIMESTE, record.getId(), 1);
                                            String msg = "La cantidad minima de eventos para el primer trimestre es de " + count;
                                            LOG.debug(msg);
                                            throw new ServiceException("ERRORT1: " + msg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 2do trimestre
                    {
                        Map<Integer, SieduEventoEscuela> map = new HashMap();
                        // se consultan los registros existentes para el segundo trimestre
                        if (oldRecord.getNroEventosT2() != null) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("capacitacion", record.getId());
                            params.put("trimestre", 2);
                            List<SieduEventoEscuela> eventos = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_CAPACITACION_AND_TRIMESTE, params);
                            if (eventos != null) { // que no deberia
                                for (SieduEventoEscuela evento : eventos) {
                                    LOG.debug("Evento numero {}", evento.getEveeNroEvento());
                                    map.put(evento.getEveeNroEvento(), evento);
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea menor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT2().compareTo(record.getNroEventosT2()) < 0) {
                            if (record.getNroEventosT2() != 0) {
                                for (int i = 1; i <= record.getNroEventosT2(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento == null) {
                                        SieduEventoEscuela evento = new SieduEventoEscuela();
                                        evento.setEveeCapa(record);
                                        evento.setEveeNroEvento(i);
                                        evento.setEveeTrimes(2);
                                        evento.setEveeCerrado(DecisionEnum.NO.toString());
                                        evento.setEveeUsuCrea(record.getModUsuario());
                                        evento.setEveeFechaCrea(new Date());
                                        evento.setEveeMaquinaCrea(maquina);
                                        evento.setEveeIpCrea(ip);
                                        sdo.persist(em, evento);
                                    }
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea mayor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT2().compareTo(record.getNroEventosT2()) > 0) {
                            if (oldRecord.getNroEventosT2() != 0) {
                                for (int i = (record.getNroEventosT2() + 1); i <= oldRecord.getNroEventosT2(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento != null) {
                                        if (oldEvento.getEveeFechaini() == null && oldEvento.getEveeFechafin() == null) {
                                            sdo.remove(em, oldEvento);
                                        } else {
                                            BigDecimal count = (BigDecimal) sdo.findByNativeQuery(em, SieduEventoEscuela.NATIVE_QUERY_COUNT_BY_CAPACITACION_AND_TRIMESTE, record.getId(), 2);
                                            String msg = "La cantidad minima de eventos para el segundo trimestre es de " + count;
                                            LOG.debug(msg);
                                            throw new ServiceException("ERRORT2: " + msg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 3er trimestre
                    {
                        Map<Integer, SieduEventoEscuela> map = new HashMap();
                        // se consultan los registros existentes para el tercer trimestre
                        if (oldRecord.getNroEventosT3() != null) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("capacitacion", record.getId());
                            params.put("trimestre", 3);
                            List<SieduEventoEscuela> eventos = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_CAPACITACION_AND_TRIMESTE, params);
                            if (eventos != null) { // que no deberia
                                for (SieduEventoEscuela evento : eventos) {
                                    LOG.debug("Evento numero {}", evento.getEveeNroEvento());
                                    map.put(evento.getEveeNroEvento(), evento);
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea menor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT3().compareTo(record.getNroEventosT3()) < 0) {
                            if (record.getNroEventosT3() != 0) {
                                for (int i = 1; i <= record.getNroEventosT3(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento == null) {
                                        SieduEventoEscuela evento = new SieduEventoEscuela();
                                        evento.setEveeCapa(record);
                                        evento.setEveeNroEvento(i);
                                        evento.setEveeTrimes(3);
                                        evento.setEveeCerrado(DecisionEnum.NO.toString());
                                        evento.setEveeUsuCrea(record.getModUsuario());
                                        evento.setEveeFechaCrea(new Date());
                                        evento.setEveeMaquinaCrea(maquina);
                                        evento.setEveeIpCrea(ip);
                                        sdo.persist(em, evento);
                                    }
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea mayor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT3().compareTo(record.getNroEventosT3()) > 0) {
                            if (oldRecord.getNroEventosT3() != 0) {
                                for (int i = (record.getNroEventosT3() + 1); i <= oldRecord.getNroEventosT3(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento != null) {
                                        if (oldEvento.getEveeFechaini() == null && oldEvento.getEveeFechafin() == null) {
                                            sdo.remove(em, oldEvento);
                                        } else {
                                            BigDecimal count = (BigDecimal) sdo.findByNativeQuery(em, SieduEventoEscuela.NATIVE_QUERY_COUNT_BY_CAPACITACION_AND_TRIMESTE, record.getId(), 3);
                                            String msg = "La cantidad minima de eventos para el tercer trimestre es de " + count;
                                            LOG.debug(msg);
                                            throw new ServiceException("ERRORT3: " + msg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 4to trimestre
                    {
                        Map<Integer, SieduEventoEscuela> map = new HashMap();
                        // se consultan los registros existentes para el tercer trimestre
                        if (oldRecord.getNroEventosT4() != null) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("capacitacion", record.getId());
                            params.put("trimestre", 4);
                            List<SieduEventoEscuela> eventos = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_CAPACITACION_AND_TRIMESTE, params);
                            if (eventos != null) { // que no deberia
                                for (SieduEventoEscuela evento : eventos) {
                                    LOG.debug("Evento numero {}", evento.getEveeNroEvento());
                                    map.put(evento.getEveeNroEvento(), evento);
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea menor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT4().compareTo(record.getNroEventosT4()) < 0) {
                            if (record.getNroEventosT4() != 0) {
                                for (int i = 1; i <= record.getNroEventosT4(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento == null) {
                                        SieduEventoEscuela evento = new SieduEventoEscuela();
                                        evento.setEveeCapa(record);
                                        evento.setEveeNroEvento(i);
                                        evento.setEveeTrimes(4);
                                        evento.setEveeCerrado(DecisionEnum.NO.toString());
                                        evento.setEveeUsuCrea(record.getModUsuario());
                                        evento.setEveeFechaCrea(new Date());
                                        evento.setEveeMaquinaCrea(maquina);
                                        evento.setEveeIpCrea(ip);
                                        sdo.persist(em, evento);
                                    }
                                }
                            }
                        }
                        // en caso q el numero de eventos del registro anterior, sea mayor que el numero de eventos del registro nuevo
                        if (oldRecord.getNroEventosT4().compareTo(record.getNroEventosT4()) > 0) {
                            if (oldRecord.getNroEventosT4() != 0) {
                                for (int i = (record.getNroEventosT4() + 1); i <= oldRecord.getNroEventosT4(); i++) {
                                    SieduEventoEscuela oldEvento = map.get(i);
                                    if (oldEvento != null) {
                                        if (oldEvento.getEveeFechaini() == null && oldEvento.getEveeFechafin() == null) {
                                            sdo.remove(em, oldEvento);
                                        } else {
                                            BigDecimal count = (BigDecimal) sdo.findByNativeQuery(em, SieduEventoEscuela.NATIVE_QUERY_COUNT_BY_CAPACITACION_AND_TRIMESTE, record.getId(), 4);
                                            String msg = "La cantidad minima de eventos para el cuarto trimestre es de " + count;
                                            LOG.debug(msg);
                                            throw new ServiceException("ERRORT4: " + msg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // actualizar el registro de capacitación
                    record.setModFecha(new Date()); // auditoria
                    record.setModMaquina(maquina); // auditoria
                    record.setModIP(ip); // auditoria
                    sdo.merge(em, record);
                }
                // Agregado 27/06/2018 para actualizar si manja presupuesto o externo cuando PAE CERRADO
                if (!oldRecord.getExterno().equals(record.getExterno()) || !oldRecord.getManejaPresupuesto().equals(record.getManejaPresupuesto())) {
                    // actualizar el registro de capacitación
                    record.setModFecha(new Date()); // auditoria
                    record.setModMaquina(maquina); // auditoria
                    record.setModIP(ip); // auditoria
                    sdo.merge(em, record);
                }
            }
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void reprobar(Capacitacion record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        if (record.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
            if (record.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || record.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())) {
                Map<String, Object> paramenter = new HashMap<>();
                paramenter.put("eveCapa", record.getId());
                List<SieduEventoEscuela> eventos = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_PAE_CAPACITACION, paramenter);
                try {
                    if (eventos.isEmpty()) {
                        List<SieduEventoEscuela> eventosDel = sdo.getResultListByNamedQuery(em, SieduEventoEscuela.FIND_BY_PAE_CAPACITACION_ALL, paramenter);
                        for (SieduEventoEscuela evento : eventosDel) {
                            sdo.remove(em, evento);
                        }
                        record.setEstado(NecesidadEstadoEnum.NO_APROBADO.getEstado());
                        record.setModalidad(null);
                        record.setManejaPresupuesto(DecisionEnum.NO.toString());
                        record.setExterno(DecisionEnum.NO.toString());
                        record.setNroEventosT1(0);
                        record.setNroEventosT2(0);
                        record.setNroEventosT3(0);
                        record.setNroEventosT4(0);
                        record.setTotalEventos(0);
                        record.setNroParticipantesT1(0);
                        record.setNroParticipantesT2(0);
                        record.setNroParticipantesT3(0);
                        record.setNroParticipantesT4(0);
                        record.setTotalParticipantes(0);
                        record.setModFecha(new Date()); // auditoria
                        record.setModMaquina(maquina); // auditoria
                        record.setModIP(ip); // auditoria
                        sdo.merge(em, record);
                        // actualizar el estado de las necesidades abarcadas por esta capacitacion.
                        Map<String, Object> params = new HashMap<>();
                        params.put("capacitacion", record.getId());
                        List<NecesidadConsolida> necesidades = sdo.getResultListByNamedQuery(em, NecesidadConsolida.FIND_BY_CAPACITACION, params);
                        if (necesidades != null) {
                            int affected = 0;
                            for (NecesidadConsolida registro : necesidades) {
                                registro.getNecesidad().setEstado(NecesidadEstadoEnum.NO_APROBADO.getEstado());
                                registro.getNecesidad().setModUsuario(record.getModUsuario()); // auditoria
                                registro.getNecesidad().setModFecha(new Date()); // auditoria
                                registro.getNecesidad().setModMaquina(maquina); // auditoria
                                registro.getNecesidad().setModIP(ip); // auditoria
                                sdo.merge(em, registro.getNecesidad());
                                affected++;
                            }
                            LOG.debug("Necesidades afectadas: #{}", affected);
                        }
                        em.flush();
                    }
                } catch (Exception ex) {
                    LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
                    throw new ServiceException(ex);
                }
            }
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public String consolidateTrainingNeeds(SPFiltro paramsSP) throws ServiceException {
        LOG.debug("metodo: consolidateTrainingNeeds() ->> parametros: paramsSP {}", paramsSP);
        try {
            Connection connection = this.datasource.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("call ");
            sb.append(SPEnum.SP_CONSOLIDAR_NECESIDADES_CAPACITACION.getNombreSP());
            sb.append("(?, ?, ?, ?, ?, ?)");
            CallableStatement cs = connection.prepareCall(sb.toString());
            cs.setLong(1, paramsSP.getPae());
            cs.setString(2, paramsSP.getUsuario()); // auditoria
            cs.setDate(3, new java.sql.Date((new Date()).getTime())); // auditoria
            cs.setString(4, maquina); // auditoria
            cs.setString(5, ip); // auditoria
            cs.registerOutParameter(6, Types.VARCHAR);
            cs.executeQuery();
            String msgs = cs.getString(6);
            if (msgs == null) {
                return "Consolidación exitosa";
            } else {
                throw new ServiceException(msgs);
            }
        } catch (SQLException ex) {
            LOG.error("metodo: consolidateTrainingNeeds() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }
}
