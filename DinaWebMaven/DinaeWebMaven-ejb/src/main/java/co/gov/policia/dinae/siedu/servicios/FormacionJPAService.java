/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.modelo.Formacion;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.FormacionFiltro;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuela;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuelaCohorte;
import co.gov.policia.dinae.siedu.modelo.FormacionEscuelaPK;
import co.gov.policia.dinae.siedu.modelo.NecesidadConsolida;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
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
import javax.sql.DataSource;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class FormacionJPAService implements FormacionService {

  private static final Logger LOG = LoggerFactory.getLogger(FormacionJPAService.class);
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
  public List<Formacion> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Formacion> list;
    try {
      list = sdo.getResultList(em, Formacion.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Formacion> findByFilter(FormacionFiltro filtro) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
    List<Formacion> list;
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT f FROM Formacion f WHERE 1 = 1 ");
    if (filtro != null) {
      params = new HashMap<>();
      if (filtro.getPae() != null) {
        jpql.append("AND f.pae.id = :pae ");
        params.put("pae", filtro.getPae().getId());
      }
      if (filtro.getProceso() != null) {
        jpql.append("AND f.proceso.id = :proceso ");
        params.put("proceso", filtro.getProceso());
      }
      if (filtro.getEstrategia() != null) {
        jpql.append("AND f.estrategia.id = :estrategia ");
        params.put("estrategia", filtro.getEstrategia());
      }
      if (filtro.getCarrera() != null) {
        jpql.append("AND f.carrera.carrerasPK.idCarrera = :carrera ");
        params.put("carrera", filtro.getCarrera());
      }
      if (filtro.getEstado() != null) {
        jpql.append("AND f.estado.id = :estado ");
        params.put("estado", filtro.getEstado());
      }
    }
    jpql.append("ORDER BY f.id DESC");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
      return list;
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Formacion findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Formacion i = (Formacion) sdo.find(em, id, Formacion.class);
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Formacion findFullById(Long id) throws ServiceException {
    LOG.debug("metodo: findFullById() ->> parametros: id {}", id);
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("id", id);
      Formacion record = (Formacion) sdo.findByNamedQuery(em, Formacion.FIND_FULL_BY_ID, params);
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: findFullById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void administrarEscuelas(Formacion formacion, List<UnidadDependencia> selecteds) throws ServiceException {
    LOG.debug("metodo: administrarEscuelas() ->> parametros: formacion {}", formacion);
    try {
      List<UnidadDependencia> preselecteds = null;
      if (formacion.getEscuelas() != null && !formacion.getEscuelas().isEmpty()) {
        preselecteds = new ArrayList<>();
        for (FormacionEscuela record : formacion.getEscuelas()) {
          preselecteds.add(record.getEscuela());
        }
      }
      // comparo primero contra la lista de preseleccionados, para determinar que registros se deben agregar.
      if (selecteds != null && !selecteds.isEmpty()) {
        for (UnidadDependencia selected : selecteds) {
          // si no existe se debe eliminar
          if (preselecteds == null || !preselecteds.contains(selected)) {
            FormacionEscuela formacionEscuela = new FormacionEscuela(formacion.getId(), selected.getPk().getFuerza(), selected.getPk().getConsecutivo());
            formacionEscuela.setCreaUsuario(formacion.getModUsuario());
            formacionEscuela.setCreaFecha(new Date()); // auditoria
            formacionEscuela.setCreaMaquina(maquina); // auditoria
            formacionEscuela.setCreaIP(ip); // auditoria
            sdo.persist(em, formacionEscuela);
          }
        }
      }
      // recorro despues la lista de los preseleccionados contra los seleccionados, de esta manera determino que registros se eben eliminar
      if (preselecteds != null && !preselecteds.isEmpty()) {
        for (UnidadDependencia preselected : preselecteds) {
          // si no existe se debe eliminar
          if (selecteds == null || !selecteds.contains(preselected)) {
            FormacionEscuela formacionEscuela = (FormacionEscuela) sdo.find(em, new FormacionEscuelaPK(formacion.getId(), preselected.getPk().getFuerza(), preselected.getPk().getConsecutivo()), FormacionEscuela.class);
            formacionEscuela.setModUsuario(formacion.getModUsuario());
            formacionEscuela.setModFecha(new Date()); // auditoria
            formacionEscuela.setModMaquina(maquina); // auditoria
            formacionEscuela.setModIP(ip); // auditoria
            sdo.remove(em, formacionEscuela);
          }
        }
      }
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void aprobar(Formacion record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      if (record.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
        if (record.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado())) {
          // cuando el pae se encuentra en estado ABIERTO y la formacion se encuentra en estado PENDIENTE 
          // - debe cambiar el estado de la formacion a APROBADO y 
          // - debe actualizar el estado de las necesidades que agrupa la formacion
          record.setEstado(NecesidadEstadoEnum.APROBADO.getEstado());
          record.setModFecha(new Date()); // auditoria
          record.setModMaquina(maquina); // auditoria
          record.setModIP(ip); // auditoria
          sdo.merge(em, record);
          Map<String, Object> params = new HashMap<>();
          params.put("formacion", record.getId());
          List<NecesidadConsolida> necesidades = sdo.getResultListByNamedQuery(em, NecesidadConsolida.FIND_BY_FORMACION, params);
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
        }
      }
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void reprobar(Formacion record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    if (record.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
      if (record.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado()) || record.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())) {
        try {
          // actualizar el estado de la necesidad de formacion
          record.setEstado(NecesidadEstadoEnum.NO_APROBADO.getEstado());
          record.setModFecha(new Date()); // auditoria
          record.setModMaquina(maquina); // auditoria
          record.setModIP(ip); // auditoria
          sdo.merge(em, record);
          em.flush();
          // eliminar si existen los registros asociados a la capacitacion
          if (record.getEscuelas() != null && !record.getEscuelas().isEmpty()) {
            for (FormacionEscuela formacionEscuela : record.getEscuelas()) {
              formacionEscuela.setModUsuario(record.getModUsuario());
              formacionEscuela.setModFecha(new Date()); // auditoria
              formacionEscuela.setModMaquina(maquina); // auditoria
              formacionEscuela.setModIP(ip); // auditoria
              sdo.remove(em, formacionEscuela);
            }
          }
          em.flush();
          // actualizar el estado de las necesidades abarcadas por esta capacitacion.
          Map<String, Object> params = new HashMap<>();
          params.put("formacion", record.getId());
          List<NecesidadConsolida> necesidades = sdo.getResultListByNamedQuery(em, NecesidadConsolida.FIND_BY_FORMACION, params);
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
        } catch (Exception ex) {
          LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
      }
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void pendiente(Formacion record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    if (record.getPae().getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
      if ((record.getEstado().equals(NecesidadEstadoEnum.APROBADO.getEstado())) || (record.getEstado().equals(NecesidadEstadoEnum.NO_APROBADO.getEstado()))) {
        try {
          // actualizar el estado de la necesidad de formacion
          record.setEstado(NecesidadEstadoEnum.PENDIENTE.getEstado());
          record.setModFecha(new Date()); // auditoria
          record.setModMaquina(maquina); // auditoria
          record.setModIP(ip); // auditoria
          sdo.merge(em, record);
          em.flush();
          // actualizar el estado de las necesidades abarcadas por esta capacitacion.
          Map<String, Object> params = new HashMap<>();
          params.put("formacion", record.getId());
          List<NecesidadConsolida> necesidades = sdo.getResultListByNamedQuery(em, NecesidadConsolida.FIND_BY_FORMACION, params);
          if (necesidades != null) {
            int affected = 0;
            for (NecesidadConsolida registro : necesidades) {
              registro.getNecesidad().setEstado(NecesidadEstadoEnum.PENDIENTE.getEstado());
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
        } catch (Exception ex) {
          LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
          throw new ServiceException(ex);
        }
      }
    }
  }

  @Override
  public FormacionEscuelaCohorte createCohorte(FormacionEscuelaCohorte record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      record.setCreaFecha(new Date()); // auditoria
      record.setCreaMaquina(maquina); // auditoria
      record.setCreaIP(ip); // auditoria
      sdo.persist(em, record);
      em.flush();
      return record;
    } catch (Exception ex) {
      record.getPk().setCohorte(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void updateCohorte(FormacionEscuelaCohorte record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      sdo.merge(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public void deleteCohorte(FormacionEscuelaCohorte record) throws ServiceException {
    LOG.debug("metodo: delete() ->> parametros: record {}", record);
    try {
      record.setModFecha(new Date()); // auditoria
      record.setModMaquina(maquina); // auditoria
      record.setModIP(ip); // auditoria
      sdo.remove(em, record);
      em.flush();
    } catch (Exception ex) {
      LOG.error("metodo: delete() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
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
      sb.append(SPEnum.SP_CONSOLIDAR_NECESIDADES_FORMACION.getNombreSP());
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
