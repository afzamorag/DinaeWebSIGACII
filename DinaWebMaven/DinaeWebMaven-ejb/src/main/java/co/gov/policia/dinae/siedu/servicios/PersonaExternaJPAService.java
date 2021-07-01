/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.modelo.PersonaExterna;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.PersonaExternaFiltro;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
import java.net.InetAddress;
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

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class PersonaExternaJPAService implements PersonaExternaService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaExternaJPAService.class);
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
    public List<PersonaExterna> findAll() throws ServiceException {
        LOG.trace("metodo: findAll()");
        List<PersonaExterna> list;
        try {
            list = sdo.getResultList(em, PersonaExterna.class);
        } catch (Exception ex) {
            LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public List<PersonaExterna> findByFilter(PersonaExternaFiltro filtro) throws ServiceException {
        LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
        List<PersonaExterna> list;
        Map<String, Object> params = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT c FROM PersonaExterna c WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getIdentificacionNro() != null) {
                jpql.append("AND c.identificacionNro LIKE (:identificacionNro) ");
                params.put("identificacionNro", "%" + filtro.getIdentificacionNro() + "%");
            }
            if (filtro.getNombres() != null) {
                jpql.append("AND c.nombres LIKE (:nombres) ");
                params.put("nombres", "%" + filtro.getNombres() + "%");
            }
            if (filtro.getApellidos() != null) {
                jpql.append("AND c.apellidos LIKE (:apellidos) ");
                params.put("apellidos", "%" + filtro.getApellidos() + "%");
            }
        }
        jpql.append("ORDER BY c.nombres, c.apellidos ASC");
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
        } catch (Exception ex) {
            LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return list;
    }

    @Override
    public PersonaExterna findById(Long id) throws ServiceException {
        LOG.debug("metodo: findById() ->> parametros: id {}", id);
        try {
            PersonaExterna i = (PersonaExterna) sdo.find(em, id, PersonaExterna.class);
            return i;
        } catch (Exception ex) {
            LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public PersonaExterna create(PersonaExterna record) throws ServiceException {
        LOG.debug("metodo: create() ->> parametros: record {}", record);
        try {
            record.setCreaFecha(new Date());
            record.setCreaMaquina(InetAddress.getLocalHost().getHostName());
            record.setCreaIP(InetAddress.getLocalHost().getHostAddress());
            record.setIdentificacionNro(record.getIdentificacionNro().trim());
            record.setApellidos(record.getApellidos().trim());
            record.setNombres(record.getNombres().trim());
            sdo.persist(em, record);
            // agregar registro en la siedu_persona           
            SieduPersona persona = new SieduPersona();
            persona.setPersTpoid(record.getIdentificacionTipo());
            persona.setPersNroid(record.getIdentificacionNro());
            persona.setPersNombres(record.getNombres());
            persona.setPersApellidos(record.getApellidos());
            persona.setPersEmpe(record);
            persona.setPersUsuCrea(record.getCreaUsuario());
            persona.setPersFechaCrea(new Date());
            persona.setPersMaquinaCrea(InetAddress.getLocalHost().getHostName());
            persona.setPersIpCrea(InetAddress.getLocalHost().getHostAddress());
            persona.setPersEmail(record.getEmail());
            //Modificación para insercion de usuario empresarial al personal externo
            persona.setPersUsuarioEmpresarial(record.getIdentificacionTipo().toLowerCase() + record.getIdentificacionNro());
            sdo.persist(em, persona);
            em.flush();
            return record;
        } catch (Exception ex) {
            record.setId(null);
            LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void update(PersonaExterna record) throws ServiceException {
        LOG.debug("metodo: update() ->> parametros: record {}", record);
        try {
            record.setModFecha(new Date());
            record.setModMaquina(InetAddress.getLocalHost().getHostName());
            record.setModIP(InetAddress.getLocalHost().getHostAddress());
            record.setIdentificacionNro(record.getIdentificacionNro().trim());
            record.setApellidos(record.getApellidos().trim());
            record.setNombres(record.getNombres().trim());
            sdo.merge(em, record);
            // actualizar registro en la siedu_persona
            Map<String, Object> params = new HashMap();
            params.put("personaExterna", record.getId());
            SieduPersona persona = (SieduPersona) sdo.findByNamedQuery(em, SieduPersona.FIND_BY_EMPLEADO_EXTERNO, params);
            persona.setPersTpoid(record.getIdentificacionTipo());
            persona.setPersNroid(record.getIdentificacionNro());
            persona.setPersNombres(record.getNombres());
            persona.setPersApellidos(record.getApellidos());
            persona.setPersEmpe(record);
            persona.setPersUsuMod(record.getModUsuario());
            persona.setPersFechaMod(new Date());
            persona.setPersMaquinaMod(InetAddress.getLocalHost().getHostName());
            persona.setPersIpMod(InetAddress.getLocalHost().getHostAddress());
            sdo.merge(em, persona);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public void delete(PersonaExterna record) throws ServiceException {
        LOG.debug("metodo: delete() ->> parametros: record {}", record);
        try {
            // eliminar registro en la siedu_persona
            Map<String, Object> params = new HashMap();
            params.put("personaExterna", record.getId());
            SieduPersona persona = (SieduPersona) sdo.findByNamedQuery(em, SieduPersona.FIND_BY_EMPLEADO_EXTERNO, params);
            sdo.remove(em, persona);
            // eliminar registro en siedu_empleado_externo
            record.setModFecha(new Date());
            record.setModMaquina(InetAddress.getLocalHost().getHostName());
            record.setModIP(InetAddress.getLocalHost().getHostAddress());
            sdo.remove(em, record);
            em.flush();
        } catch (Exception ex) {
            LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean validarNumeroIdentificacion(String numeroIdentificacion) {
        LOG.debug("metodo: validarNumeroIdentificacion() ->> parametros: numeroIdentificacion {}", numeroIdentificacion);
        if (numeroIdentificacion == null || numeroIdentificacion.isEmpty()) {
            throw new IllegalArgumentException("El parámetro [numeroIdentificacion] es requerido");
        }
        try {
            BigDecimal consecutivo = (BigDecimal) sdo.findByNativeQuery(em, "SELECT CONSECUTIVO FROM USR_REHU.EMPLEADOS WHERE IDENTIFICACION = ? AND ACTIVO = 'SI'", numeroIdentificacion);
            return true;
        } catch (Exception ex) {
            LOG.error("metodo: validarNumeroIdentificacion() ->> mensaje: {}", ex.getMessage());
            return false;
        }
    }
}
