/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.constantes.NecesidadEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.ProcesoEnum;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.modelo.Necesidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.NecesidadFiltro;
import co.gov.policia.dinae.siedu.filtros.SPFiltro;
import co.gov.policia.dinae.siedu.modelo.PAENovedad;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import java.math.BigDecimal;
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
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
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
public class NecesidadJPAService implements NecesidadService {

  private static final Logger LOG = LoggerFactory.getLogger(NecesidadJPAService.class);
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
  public List<Necesidad> findAll() throws ServiceException {
    LOG.trace("metodo: findAll()");
    List<Necesidad> list;
    try {
      list = sdo.getResultList(em, Necesidad.class);
      em.clear();
    } catch (Exception ex) {
      LOG.error("metodo: findAll() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public List<Necesidad> findByFilter(NecesidadFiltro filtro) throws ServiceException {
    LOG.trace("metodo: findByFilter() ->> parametros: filtro {}", filtro);
    List<Necesidad> list;
    Map<String, Object> params = null;
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT n FROM Necesidad n WHERE 1 = 1 ");
    if (filtro != null) {
      params = new HashMap<>();
      if (filtro.getPae() != null) {
        jpql.append("AND n.pae.id = :vigencia ");
        params.put("vigencia", filtro.getPae().getId());
      }
      if (filtro.getNovedad() != null) {
        jpql.append("AND n.novedad.id = :novedad ");
        params.put("novedad", filtro.getNovedad());
      }
      if (filtro.getRegion() != null) {
        jpql.append("AND n.region.id = :region ");
        params.put("region", filtro.getRegion());
      }
      if (filtro.getUnidadFisica() != null) {
        jpql.append("AND n.unidadFisica.id = :unidadFisica ");
        params.put("unidadFisica", filtro.getUnidadFisica());
      }
      if (filtro.getUnidadDependencia() != null) {
        jpql.append("AND n.unidadDependencia.id = :unidadDependencia ");
        params.put("unidadDependencia", filtro.getUnidadDependencia());
      }
      if (filtro.getCarrera() != null) {
        jpql.append("AND n.carrera.id = :carrera ");
        params.put("novedad", filtro.getCarrera());
      }
      if (filtro.getEstado() != null) {
        jpql.append("AND n.estado.id = :estado ");
        params.put("novedad", filtro.getEstado());
      }
      if (filtro.getProceso() != null) {
        jpql.append("AND n.proceso.id = :proceso ");
        params.put("proceso", filtro.getProceso());
      }
      if (filtro.getEstrategia() != null) {
        jpql.append("AND n.estrategia.id = :estrategia ");
        params.put("estrategia", filtro.getEstrategia());
      }
      if (filtro.getOrigen() != null) {
        jpql.append("AND n.origen = :origen ");
        params.put("origen", filtro.getOrigen().trim());
      }
    }
    jpql.append("ORDER BY n.id DESC");
    try {
      list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
    } catch (Exception ex) {
      LOG.error("metodo: findByFilter() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    return list;
  }

  @Override
  public Necesidad findById(Long id) throws ServiceException {
    LOG.debug("metodo: findById() ->> parametros: id {}", id);
    try {
      Necesidad i = (Necesidad) sdo.find(em, id, Necesidad.class);
      em.clear();
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findById() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public Necesidad create(Necesidad record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    // se debe consultar la ultima novedad asociada al PAE, para dejar constancia sobre cual novedad se crea la necesidad
    BigDecimal lastNeed = (BigDecimal) sdo.findByNativeQuery(em, "SELECT MAX(nove_nove) FROM siedu_novedad_pae WHERE nove_pae = ?", record.getPae().getId());
    try {
      if (lastNeed == null) {
        throw new IllegalArgumentException("El pae no tiene asociada una novedad, lo cual no es permitido, consulte al administrador del sistema");
      }
    } catch (IllegalArgumentException ex) {
      record.setId(null);
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
    try {
      record.setNovedad(new PAENovedad(lastNeed.longValue()));
      record.setEstado(NecesidadEstadoEnum.PENDIENTE.getEstado());
      record.setCreaFecha(new Date()); // auditoria
      record.setCreaMaquina(maquina); // auditoria
      record.setCreaIP(ip); // auditoria
      sdo.persist(em, record);
      return record;
    } catch (Exception ex) {
      record.setId(null);
      LOG.error("metodo: create() ->> Error de SDO. mensaje: {}", ex.getMessage());
      throw new ServiceException("Error de SDO.", ex);
    }
  }

  @Override
  public void update(Necesidad record) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: record {}", record);
    try {
      record.setEstado(NecesidadEstadoEnum.PENDIENTE.getEstado());
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
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void update(List<Necesidad> records) throws ServiceException {
    LOG.debug("metodo: update() ->> parametros: records {}", records);
    if (records != null) {
      try {
        for (Necesidad necesidad : records) {
          necesidad.setModFecha(new Date()); // auditoria
          necesidad.setModMaquina(maquina); // auditoria
          necesidad.setModIP(ip); // auditoria
          em.merge(necesidad);
        }
        em.flush();
      } catch (Exception ex) {
        LOG.error("metodo: update() ->> mensaje: {}", ex.getMessage());
        throw new ServiceException(ex);
      }
    }
  }

  @Override
  public void delete(Necesidad record) throws ServiceException {
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
  public String importNeeds(SPFiltro paramsSP) throws ServiceException {
    LOG.debug("metodo: importNeeds() ->> parametros: pae {}", paramsSP);
    try {
      Connection connection = this.datasource.getConnection();
      StringBuilder sb = new StringBuilder();
      sb.append("call ");
      sb.append(SPEnum.SP_OBTENER_NECESIDADES_DITHA.getNombreSP());
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
        return "Importación exitosa";
      } else {
        throw new ServiceException(msgs);
      }
    } catch (SQLException ex) {
      LOG.error("metodo: importNeeds() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<String> validarEstadoNecesidades(Long pae) throws ServiceException {
    LOG.debug("metodo: validarEstadoNecesidades() ->> parametros: pae {}", pae);
    if (pae == null || pae.equals(0L)) {
      throw new IllegalArgumentException("El parámetro pae, es requerido");
    }
    List<String> msgs = null;
    Map<String, Object> params = new HashMap<>();
    params.put("pae", pae);
    List<Necesidad> necesidades = sdo.getResultListByNamedQuery(em, Necesidad.FIND_BY_PAE, params);
    for (Necesidad necesidad : necesidades) {
      if (necesidad.getEstado().equals(NecesidadEstadoEnum.PENDIENTE.getEstado())) {
        if (msgs == null) {
          msgs = new ArrayList<>();
        }
        StringBuilder msg = new StringBuilder();
        msg.append("> ");
        msg.append("Necesidad ID: ");
        msg.append(necesidad.getId());
        msg.append(", estado ");
        msg.append(NecesidadEstadoEnum.PENDIENTE.toString());
        msgs.add(msg.toString());
      }
    }
    return msgs;
  }

  @Override
  public List<String> validarProcesoNecesidades(Long pae) throws ServiceException {
    LOG.debug("metodo: validarProcesoNecesidades() ->> parametros: pae {}", pae);
    if (pae == null || pae.equals(0L)) {
      throw new IllegalArgumentException("El parámetro pae, es requerido");
    }
    List<String> msgs = null;
    Map<String, Object> params = new HashMap<>();
    params.put("pae", pae);
    List<Necesidad> necesidades = sdo.getResultListByNamedQuery(em, Necesidad.FIND_BY_PAE, params);
    for (Necesidad necesidad : necesidades) {
      StringBuilder msg;
      if (necesidad.getProceso() == null) {
        if (msgs == null) {
          msgs = new ArrayList<>();
        }
        msg = new StringBuilder();
        msg.append("> ");
        msg.append("Necesidad ID: ");
        msg.append(necesidad.getId());
        msg.append(", pendiente asociar proceso");
        msgs.add(msg.toString());
      } else if (necesidad.getProceso().getId().equals(ProcesoEnum.CAPACITACION.getId())) {
        if (necesidad.getEstrategia() == null) {
          if (msgs == null) {
            msgs = new ArrayList<>();
          }
          msg = new StringBuilder();
          msg.append("> ");
          msg.append("Necesidad ID: ");
          msg.append(necesidad.getId());
          msg.append(", pendiente asociar estrategia");
          msgs.add(msg.toString());
        }
      }
    }
    return msgs;
  }

  @Override
  public Integer obtenerCantidadNecesidades(Long pae) throws ServiceException {
    LOG.debug("metodo: obtenerCantidadNecesidades() ->> parametros: pae {}", pae);
    try {
      BigDecimal cantidadNecesidades = (BigDecimal) sdo.findByNativeQuery(em, "SELECT COUNT(NECE_NECE) FROM SIEDU_NECESIDAD WHERE NECE_PAE = ?", pae);
      return cantidadNecesidades.intValue();
    } catch (Exception ex) {
      LOG.error("metodo: obtenerCantidadNecesidadesPorPAE() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }
}
