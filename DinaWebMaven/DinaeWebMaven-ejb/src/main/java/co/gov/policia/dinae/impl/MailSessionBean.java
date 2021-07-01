package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.dto.CorreoParametrizaDTO;
import co.gov.policia.dinae.dto.MDBDatosDetalleCorreoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.CorreoEnvio;
import co.gov.policia.dinae.modelo.CorreoParametriza;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class MailSessionBean implements IMailSessionLocal, Serializable {

  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;

  @Resource(mappedName = "jms/DinaeJMSFactory")
  private javax.jms.ConnectionFactory connectionFactory;

  @Resource(mappedName = "jms/MDBEnviaCorreoMasivo")
  private javax.jms.Queue queueMDBEnviaCorreoMasivo;

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param idRol
   * @param idUnidadPolicial
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void enviarMail_RolUnidadPolicial(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, Long idRol, Long idUnidadPolicial) {

    try {

      List<VistaFuncionario> listaFuncionarios = iUsuarioUnidadPolicialLocal.getUsuarioByUnidadByRole(
              idUnidadPolicial,
              idRol);

      if (listaFuncionarios.isEmpty()) {
        return;
      }

      List<String> listaCorreo = new ArrayList<String>();
      for (VistaFuncionario unaVistaFuncionario : listaFuncionarios) {

        if (unaVistaFuncionario.getCorreo() == null || unaVistaFuncionario.getCorreo().trim().length() == 0) {
          continue;
        }
        listaCorreo.add(unaVistaFuncionario.getCorreo().trim());
      }

      enviarMail_ListaCorreo(codigoMail, parametrosAsunto, parametrosContenido, listaCorreo);

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, " MailSessionBean ", e);

    }

  }

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param lisaIdsTipoUnidades
   * @param listaIdRoles
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void enviarMail_ListaTipoUnidades_ListaRoles(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<Long> lisaIdsTipoUnidades, List<Long> listaIdRoles) {

    try {

      List<String> listaCorreo = new ArrayList<String>();
      for (Long unRol : listaIdRoles) {

        listaCorreo.addAll(iUsuarioUnidadPolicialLocal.getCorreoFuncionarioPorListaTipoUnidadYRole(
                lisaIdsTipoUnidades,
                unRol));

      }

      if (listaCorreo.isEmpty()) {
        return;
      }
      enviarMail_ListaCorreo(codigoMail, parametrosAsunto, parametrosContenido, listaCorreo);

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, " MailSessionBean ", e);

    }

  }

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaRoles
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void enviarMail_ListaRoles(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<Long> listaRoles) {

    try {

      List<String> listaCorreo = iUsuarioUnidadPolicialLocal.getCorreoFuncionarioPorListaRoles(listaRoles);

      if (listaCorreo.isEmpty()) {
        return;
      }

      enviarMail_ListaCorreo(codigoMail, parametrosAsunto, parametrosContenido, listaCorreo);

    } catch (Exception e) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, " MailSessionBean ", e);

    }
  }

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaCorreo
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void enviarMail_ListaCorreo(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<String> listaCorreo) {

    try {

      javax.jms.Connection connection = connectionFactory.createConnection();

      try {

        javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        try {

          javax.jms.MessageProducer messageProducer = session.createProducer(queueMDBEnviaCorreoMasivo);

          connection.start();

          javax.jms.Message message = session.createObjectMessage(new MDBDatosDetalleCorreoDTO(codigoMail, parametrosAsunto, parametrosContenido, listaCorreo));
          messageProducer.send(message);

        } finally {
          session.close();
        }
      } finally {

        connection.close();
      }
    } catch (javax.jms.JMSException ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, " MailSessionBean ", ex);

    } finally {

    }

  }

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public CorreoEnvio actualizarCorreoEnvio(CorreoEnvio correoEnvio) throws JpaDinaeException {

    try {

      if (correoEnvio.getId() == null) {

        entityManager.persist(correoEnvio);
      } else {

        entityManager.merge(correoEnvio);

      }

      return correoEnvio;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param codigo
   * @return
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public CorreoParametriza getCorreoParametriza(String codigo) {

    return entityManager.find(CorreoParametriza.class, codigo);

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public List<CorreoParametrizaDTO> getListCorreoEnvioDTO() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("CorreoParametrizaDTO.findColdigoLabelAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param codigo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public CorreoParametriza getCorreoParametrizaPorCodigo(String codigo) throws JpaDinaeException {

    try {

      return entityManager.find(CorreoParametriza.class, codigo);

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param correoParametriza
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public CorreoParametriza actualizarCorreoParametriza(CorreoParametriza correoParametriza) throws JpaDinaeException {

    try {

      if (correoParametriza.getCodigo() == null) {

        entityManager.persist(correoParametriza);
      } else {

        entityManager.merge(correoParametriza);

      }

      //ACTUALIZAMOS LA PLANTILLA DE CORREO CARGADAS
      List<CorreoParametrizaDTO> listaCorreoParametriza = entityManager.createNamedQuery("CorreoParametrizaDTO.findColdigoLabelAllProperties")
              .getResultList();

      //LLIMPIAMOS EL MAPA
      KeyPropertiesFactory.getMapaCorreoParametrizaDTO().clear();

      for (CorreoParametrizaDTO unCorreoParametrizaDTO : listaCorreoParametriza) {
        //CARGAMOS ELL MAPA
        KeyPropertiesFactory.getMapaCorreoParametrizaDTO().put(unCorreoParametrizaDTO.getCodigo(), unCorreoParametrizaDTO);

      }

      return correoParametriza;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }
}
