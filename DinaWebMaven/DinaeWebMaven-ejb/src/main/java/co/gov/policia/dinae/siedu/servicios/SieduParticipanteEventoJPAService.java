/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.SieduDatosEmpleadoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.net.InetAddress;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
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
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class SieduParticipanteEventoJPAService implements SieduParticipanteEventoService {

    private static final Logger LOG = LoggerFactory.getLogger(SieduParticipanteEventoJPAService.class);
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
    public List<SieduParticipanteEvento> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<SieduParticipanteEvento> list;
        try {
            list = sdo.getResultList(em, SieduParticipanteEvento.class);
            em.clear();
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<SieduParticipanteEvento> findByFilter(Map<String, Object> params) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: params {}", params);
        List<SieduParticipanteEvento> list;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT d FROM SieduParticipanteEvento d WHERE 1 = 1 ");
        if (params != null) {
            if (params.get("descripcion") != null) {
                jpql.append("AND d.descripcionSieduParticipanteEvento = :descripcion ");
            }
            if (params.get("vigente") != null) {
                jpql.append("AND d.vigente = :vigente ");
            }
            if (params.get("tipo") != null) {
                jpql.append("AND d.idTipoSieduParticipanteEvento.idTipoSieduParticipanteEvento = :tipo ");
            }
        }
        jpql.append("ORDER BY d.descripcionSieduParticipanteEvento");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public SieduParticipanteEvento findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            SieduParticipanteEvento i = (SieduParticipanteEvento) sdo.find(em, id, SieduParticipanteEvento.class);
            em.clear();
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public String create(String identificacion, String v_vigencia, Long v_id_carrera, Long v_evee, String v_usu_crea, Integer v_trimestre, Long v_escuela) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", identificacion, v_evee);
        String v_dip_crea;
        String v_maq_crea;
        String v_mensaje;
        try {
            v_dip_crea = InetAddress.getLocalHost().getHostAddress();
            v_maq_crea = InetAddress.getLocalHost().getHostName();
            try {
                Connection connection = this.datasource.getConnection();
                StringBuilder sb = new StringBuilder();
                sb.append("call ");
                sb.append(SPEnum.SP_INSERTAR_PARTICIPANTE_EVENTO.getNombreSP());
                sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                CallableStatement cs = connection.prepareCall(sb.toString());
                cs.setString(1, identificacion);
                cs.setString(2, v_vigencia);
                cs.setLong(3, v_id_carrera);
                cs.setLong(4, v_evee);
                cs.setString(5, v_usu_crea);
                cs.setString(6, v_dip_crea);
                cs.setString(7, v_maq_crea);
                cs.setInt(8, v_trimestre);
                cs.setLong(9, v_escuela);
                cs.registerOutParameter(10, Types.VARCHAR);
                cs.executeQuery();
                v_mensaje = cs.getString(10);
                return v_mensaje;
            } catch (SQLException ex) {
                LOG.error("metodo: create() ->> mensaje ->> {}", ex.getMessage());
                throw new ServiceException(ex);
            }
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduParticipanteEvento create(SieduParticipanteEvento record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            record.setPareFechaCrea(new Date());
            record.setPareMaquinaCrea(InetAddress.getLocalHost().getHostName());
            record.setPareIpCrea(InetAddress.getLocalHost().getHostAddress());
            sdo.persist(em, record);
            em.flush();
            return record;
        } catch (Exception ex) {
            record.setParePare(null);
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduParticipanteEvento findByPareeveeParenroiden(Long evento, String identificacion) throws ServiceException {
        LOG.debug("metodo: findByPareeveeParenroiden() ->> parametros: evento,  identificacion{}", evento, identificacion);
        try {
            SieduParticipanteEvento participanteEvento = (SieduParticipanteEvento) em.createNamedQuery(SieduParticipanteEvento.FIND_BY_EVEN_PARENROIDEN, SieduParticipanteEvento.class)
                    .setParameter("evento", evento)
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
            return participanteEvento;
        } catch (Exception ex) {
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(SieduParticipanteEvento record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setPareFechaMod(new Date());
            record.setPareMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setPareIpMod(InetAddress.getLocalHost().getHostAddress());
            sdo.merge(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(SieduParticipanteEvento record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            record.setPareFechaMod(new Date());
            record.setPareMaquinaMod(InetAddress.getLocalHost().getHostName());
            record.setPareIpMod(InetAddress.getLocalHost().getHostAddress());
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
            sdo.remove(em, id, SieduParticipanteEvento.class);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduDatosEmpleadoDTO findDatosEmpleadoPolicial(Long identificacion) throws ServiceException {
        LOG.debug("metodo: findDatosEmpleadoPolicial() ->> parametros: identificacion {}", identificacion);
        SieduDatosEmpleadoDTO empleado = new SieduDatosEmpleadoDTO();
        try {
            empleado = (SieduDatosEmpleadoDTO) em.createNativeQuery(SieduDatosEmpleadoDTO.BUSCAR_DATOS_EMPLEADO_POLICIA, SieduDatosEmpleadoDTO.class)
                    .setParameter(1, identificacion)
                    //              .setParameter("identi", identificacion) 
                    .setMaxResults(1)
                    .getSingleResult();
            return empleado;
        } catch (Exception ex) {
            LOG.error("metodo: findDatosEmpleadoPolicial() ->> mensaje: {}", identificacion + "" + ex.getMessage());
            //throw new ServiceException(ex);
            return empleado;
        }
    }

    @Override
    public List<SieduParticipanteEvento> findParticipanteByVigencia(SieduEventoEscuela evento, String identificacion) throws ServiceException {
        LOG.debug("metodo: findParticipanteByVigencia() ->> Parametros: evento, identificacion{}", evento, identificacion);
        try {
            List<SieduParticipanteEvento> list = (List<SieduParticipanteEvento>) em.createNativeQuery("SELECT T.*\n"
                    + "FROM SIEDU_PARTICIPANTE_EVENTO T, SIEDU_EVENTO_ESCUELA E, SIEDU_PERSONA P, SIEDU_PAE_CAPACITACION PC\n"
                    + "WHERE T.PARE_EVEE = E.EVEE_EVEE\n"
                    + "AND T.PARE_PERS = P.PERS_PERS\n"
                    + "AND PC.CAPA_CAPA = E.EVEE_CAPA\n"
                    + "AND PC.CAPA_ID_CARRERA = ?1\n"
                    + "AND P.PERS_NROID = ?2\n"
                    + "AND TO_CHAR(E.EVEE_FECHAINI, 'YYYY') = ?3", SieduParticipanteEvento.class)
                    .setParameter(1, evento.getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera())
                    .setParameter(2, identificacion)
                    .setParameter(3, evento.getEveeCapa().getPae().getVigencia())
                    .getResultList();
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: findParticipanteByVigencia() ->> mensaje: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<SieduParticipanteEvento> findListById(SieduEventoEscuela id) throws ServiceException {
        LOG.debug("metodo: findListById()", id);
        try {
            return em.createNamedQuery(SieduParticipanteEvento.FIND_BY_PARE_EVEE, SieduParticipanteEvento.class)
                    .setParameter("evento", id)
                    .setHint("eclipselink.refresh", "true")
                    .getResultList();
        } catch (Exception ex) {
            LOG.error("metodo: metodo: findListById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public SieduParticipanteEvento findByPareeveeParepers(SieduEventoEscuela evento, SieduPersona participante) throws ServiceException {
        LOG.debug("metodo: findByPareeveeParepers()", evento, participante);
        try {
            SieduParticipanteEvento participanteEvento = em.createNamedQuery(SieduParticipanteEvento.FIND_BY_PAREEVEE_PAREPERS, SieduParticipanteEvento.class)
                    .setParameter("evento", evento.getEveeEvee())
                    .setParameter("participante", participante.getPersPers())
                    .getSingleResult();
            return participanteEvento;
        } catch (Exception ex) {
            //LOG.error("metodo: metodo: findByPareeveeParepers() ->> mensaje: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<SieduParticipanteEvento> findCapacitacionFuncionario(String identificacion) throws ServiceException {
        LOG.debug("findCapacitacionFuncionario()", identificacion);
        List<SieduParticipanteEvento> list;
        try {
            list = em.createNamedQuery(SieduParticipanteEvento.FIND_CAPACITACION_FUNCIONARIO, SieduParticipanteEvento.class)
                    .setParameter("identificacion", identificacion)
                    .getResultList();
            return list;
        } catch (Exception ex) {
            LOG.error("metodo: mfindCapacitacionFuncionario() ->> mensaje: {}", ex.getMessage());
            return null;
        }
    }
}
