/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.siedu.constantes.DecisionEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.PAEProcedimientoEnum;
import co.gov.policia.dinae.siedu.constantes.SPEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.PAENovedad;
import co.gov.policia.dinae.siedu.sdo.ArchivoFSSDO;
import co.gov.policia.dinae.siedu.sdo.ArchivoSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.util.file.FileUtil;
import java.io.IOException;
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
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class PAEJPAService implements PAEService {

  private static final Logger LOG = LoggerFactory.getLogger(PAEJPAService.class);
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager em;
  @Inject
  @GenericSDOQualifier
  private SDO sdo;
//  @Inject
//  @ArchivoSDOQualifier
  private ArchivoSDO archivoSDO;
  @Resource(mappedName = "jdbc/DinaeWebDS")
  private DataSource datasource;
  private String maquina;
  private String ip;

  @PostConstruct
  public void init() {
    LOG.trace("entró al método: init()");
    if (sdo == null) {
      sdo = new GenericSDO();
    }
    if (archivoSDO == null) {
      archivoSDO = new ArchivoFSSDO();
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
  public List<PAE> consultarVigencias() throws ServiceException {
    LOG.trace("metodo: consultarVigencias()");
    try {
      return sdo.getResultListByNamedQuery(em, PAE.FIND_ALL);
    } catch (Exception ex) {
      LOG.error("metodo: consultarVigencias() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<PAE> consultarVigenciasAbiertas() throws ServiceException {
    LOG.trace("metodo: consultarVigenciasAbiertas()");
    try {
      Map<String, Object> params;
      params = new HashMap<>();
      params.put("estado", PAEEstadoEnum.ABIERTO.toString());
      return sdo.getResultListByNamedQuery(em, PAE.FIND_BY_ESTADO, params);
    } catch (Exception ex) {
      LOG.error("metodo: consultarVigenciasAbiertas() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public List<PAE> consultarVigenciasCerradas() throws ServiceException {
    LOG.trace("metodo: consultarVigenciasCerradas()");
    try {
      Map<String, Object> params;
      params = new HashMap<>();
      params.put("estado", PAEEstadoEnum.CERRADO.toString());
      return sdo.getResultListByNamedQuery(em, PAE.FIND_BY_ESTADO, params);
    } catch (Exception ex) {
      LOG.error("metodo: consultarVigenciasCerradas() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public PAE consultarPAE(Long id) throws ServiceException {
    LOG.debug("metodo: consultarPAE() ->> parametros: id {}", id);
    if (id == null) {
      throw new IllegalArgumentException("El parametro [id] es obligatorio");
    }
    try {
      PAE i = (PAE) sdo.find(em, id, PAE.class);
      return i;
    } catch (Exception ex) {
      LOG.error("metodo: findPAE() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  public PAE consultarPAE(String vigencia) throws ServiceException {
    LOG.trace("metodo: consultarPAE()");
    if (vigencia == null || vigencia.isEmpty()) {
      throw new IllegalArgumentException("El parametro [vigencia] es obligatorio");
    }
    PAE pae;
    Map<String, Object> params;
    // consultar si existe un pae para la vigencia seleccionada
    try {
      params = new HashMap<>();
      params.put("vigencia", vigencia);
      pae = (PAE) sdo.findByNamedQuery(em, PAE.FIND_BY_VIGENCIA, params);
    } catch (Exception ex) {
      LOG.error("metodo: consultarPAE() ->> error: No se encontró un registro para la vigencia [{}]. ->> mensaje ->> {}", vigencia, ex.getMessage());
      return null;
    }
    // consultar las novedades asociadas al pae
    if (pae != null) {
      try {
        params = new HashMap<>();
        params.put("pae", pae.getId());
        pae.setNovedades(sdo.getResultListByNamedQuery(em, PAENovedad.FIND_BY_PAE, params));
        if (pae.getNovedades() == null) {
          LOG.error("El pae id = {}, debe tener una novedad asociada minimo una novedad", pae.getId());
          throw new ServiceException("El pae no tiene novedades asociadas");
        }
      } catch (Exception ex) {
        LOG.error("metodo: consultarPAE() ->> error: problemas al consultar la(s) novedade(s) asociada(s) al pae id = {} ->> mensaje ->> {}", pae.getId(), ex.getMessage());
      }
    }
    // consultar los archivos asociados a las novedades
    if (pae.getNovedades() != null) {
      List<String> msgs = null;
      for (PAENovedad novedad : pae.getNovedades()) {
        if (novedad.getDocumento() != null) {
          StringBuilder pathname = new StringBuilder();
          pathname.append(KeyPropertiesFactory.getPathFilesVIECO());
          pathname.append(novedad.getDocumento().getId());
          pathname.append(".");
          pathname.append(novedad.getDocumento().getExt());
          try {
            novedad.getDocumento().setInputStream(FileUtil.findFile(pathname.toString()));
          } catch (IOException ex) {
            LOG.error("metodo: consultarPAE() ->> error: no se pudo obtener el archivo [{}]. ->> mensaje ->> {}", pathname.toString(), ex.getMessage());
            if (msgs == null) {
              msgs = new ArrayList<>();
            }
            msgs.add(ex.getMessage());
          }
        }
      }
      if (msgs != null && !msgs.isEmpty()) {
        throw new ServiceException(msgs.toString());
      }
    }
    return pae;
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public PAE abrirPAE(PAE pae, PAENovedad novedad) throws ServiceException {
    LOG.debug("metodo: abrirPAE()");
    try {
      // persistir el PAE
      pae.setNecesidadesImportadas(DecisionEnum.NO.toString());
      pae.setEstado(PAEEstadoEnum.ABIERTO.toString());
      pae.setCreaFecha(new Date()); // auditoria
      pae.setCreaMaquina(maquina); // auditoria
      pae.setCreaIP(ip); // auditoria
      sdo.persist(em, pae);
      // persistir el documento asociado a la novedad
      novedad.getDocumento().setCreaUsuario(pae.getCreaUsuario()); // auditoria
      novedad.getDocumento().setCreaFecha(new Date()); // auditoria
      novedad.getDocumento().setCreaMaquina(maquina); // auditoria
      novedad.getDocumento().setCreaIP(ip); // auditoria
      archivoSDO.persist(em, novedad.getDocumento(), KeyPropertiesFactory.getPathFilesVIECO());
      // persistir la novedad asociada al PAE
      novedad.setPae(pae);
      novedad.setFecha(new Date());
      novedad.setProcedimiento(PAEProcedimientoEnum.CONSTRUCCION.toString());
      novedad.setCreaFecha(new Date()); // auditoria
      novedad.setCreaMaquina(maquina); // auditoria
      novedad.setCreaIP(ip); // auditoria
      sdo.persist(em, novedad);
      em.flush();
      return pae;
    } catch (Exception ex) {
      pae.setId(null);
      novedad.setId(null);
      LOG.error("metodo: addPAE() ->> mensaje ->> {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void reabrirPAE(PAE pae, PAENovedad novedad) throws ServiceException {
    LOG.debug("metodo: reabrirPAE()");
    try {
      // persistir el registro del PAE
      pae.setEstado(PAEEstadoEnum.ABIERTO.toString());
      pae.setModFecha(new Date()); // auditoria
      pae.setModMaquina(maquina); // auditoria
      pae.setModIP(ip); // auditoria
      sdo.merge(em, pae);
      // persistir el documento asociado a la novedad
      novedad.getDocumento().setCreaUsuario(pae.getCreaUsuario()); // auditoria
      novedad.getDocumento().setCreaFecha(new Date()); // auditoria
      novedad.getDocumento().setCreaMaquina(maquina); // auditoria
      novedad.getDocumento().setCreaIP(ip); // auditoria
      archivoSDO.persist(em, novedad.getDocumento(), KeyPropertiesFactory.getPathFilesVIECO());
      // persistir la novedad asociada al PAE
      novedad.setPae(pae);
      novedad.setFecha(new Date());
      novedad.setProcedimiento(PAEProcedimientoEnum.MODIFICACION.toString());
      novedad.setCreaFecha(new Date()); // auditoria
      novedad.setCreaMaquina(maquina); // auditoria
      novedad.setCreaIP(ip); // auditoria
      sdo.persist(em, novedad);
    } catch (Exception ex) {
      LOG.error("metodo: addPAE() ->> mensaje ->> {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void cerrarPAE(PAE pae, PAENovedad novedad) throws ServiceException {
    LOG.debug("metodo: cerrarPAE()");
    try {
      // persistir el registro el PAE
      pae.setEstado(PAEEstadoEnum.CERRADO.toString());
      pae.setModFecha(new Date()); // auditoria
      pae.setModMaquina(maquina); // auditoria
      pae.setModIP(ip); // auditoria
      sdo.merge(em, pae);
      // persistir el documento asociado a la novedad
      novedad.getDocumento().setCreaUsuario(pae.getCreaUsuario()); // auditoria
      novedad.getDocumento().setCreaFecha(new Date()); // auditoria
      novedad.getDocumento().setCreaMaquina(maquina); // auditoria
      novedad.getDocumento().setCreaIP(ip); // auditoria
      archivoSDO.persist(em, novedad.getDocumento(), KeyPropertiesFactory.getPathFilesVIECO());
      // persistir la novedad asociada al PAE
      novedad.setPae(pae);
      novedad.setFecha(new Date());
      novedad.setProcedimiento(PAEProcedimientoEnum.CIERRE.toString());
      novedad.setCreaFecha(new Date()); // auditoria
      novedad.setCreaMaquina(maquina); // auditoria
      novedad.setCreaIP(ip); // auditoria
      sdo.persist(em, novedad);
      try {
        // ejecutar procedimiento que pobla la tabla de SIEDU_EVENTO_ESCUELA
        Connection connection = this.datasource.getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("call ");
        sb.append(SPEnum.SP_INSERTAR_METAS_PAE.getNombreSP());
        sb.append("(?, ?, ?, ?, ?, ?)");
        CallableStatement cs = connection.prepareCall(sb.toString());
        cs.setLong(1, pae.getId());
        cs.setString(2, pae.getModUsuario()); // auditoria
        cs.setDate(3, new java.sql.Date((new Date()).getTime())); // auditoria
        cs.setString(4, maquina); // auditoria
        cs.setString(5, ip); // auditoria
        cs.registerOutParameter(6, Types.VARCHAR);
        cs.executeQuery();
        String msgs = cs.getString(6);
        if (msgs != null) {
          throw new ServiceException(msgs);
        }
      } catch (SQLException ex) {
        LOG.error("metodo: cerrarPAE() ->> mensaje ->> {}", ex.getMessage());
        throw new ServiceException(ex);
      }
    } catch (Exception ex) {
      LOG.error("metodo: cerrarPAE() ->> mensaje ->> {}", ex.getMessage());
      throw new ServiceException(ex.getMessage());
    }
  }
}
