package co.gov.policia.dinae.mdb;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.ArchivoAdjuntoDTO;
import co.gov.policia.dinae.dto.ArchivoReporte;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.CorreoParametrizaDTO;
import co.gov.policia.dinae.dto.MDBDatosDetalleCorreoDTO;
import co.gov.policia.dinae.dto.MailDTO;
import co.gov.policia.dinae.dto.StringUtils;
import co.gov.policia.dinae.interfaces.IMailSessionBMTLocal;
import co.gov.policia.dinae.modelo.CorreoEnvio;
import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.security.Security;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@MessageDriven(mappedName = "jms/MDBEnviaCorreo", name = "MDBEnviaCorreo", activationConfig = {
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
  @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MDBEnviaCorreo implements MessageListener, Serializable {

  @EJB
  private IMailSessionBMTLocal iMailSessionBMTLocal;

  /**
   *
   * @param message
   */
  @Override
  public void onMessage(javax.jms.Message message) {

    Logger.getLogger(getClass().getName()).log(Level.INFO, "-------------------------  INICIA ENVIA DE CORREO -------------------------");

    try {

      ObjectMessage om = (ObjectMessage) message;

      MDBDatosDetalleCorreoDTO mDBDatosDetalleCorreoDTO = (MDBDatosDetalleCorreoDTO) om.getObject();
      enviarMail_ListaCorreo(
              mDBDatosDetalleCorreoDTO.getCodigoMail(),
              mDBDatosDetalleCorreoDTO.getParametrosAsunto(),
              mDBDatosDetalleCorreoDTO.getParametrosContenido(),
              mDBDatosDetalleCorreoDTO.getListaCorreo());

    } catch (JMSException ex) {

      Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, "ERROR ENVIA CORREO: ".concat(ex.getMessage()), ex);
    }
    Logger.getLogger(getClass().getName()).log(Level.INFO, "-------------------------  FIN ENVIA DE CORREO -------------------------");
  }

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaCorreo
   */
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  private void enviarMail_ListaCorreo(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<String> listaCorreo) {

    String asunto = "-";
    String mensaje = "-";
    try {

      CorreoParametrizaDTO correoParametrizaDTO = KeyPropertiesFactory.getMapaCorreoParametrizaDTO().get(codigoMail);

      asunto = correoParametrizaDTO.getAsunto();

      if (parametrosAsunto != null && !parametrosAsunto.isEmpty()) {

        Iterator<Map.Entry<String, String>> iteraMapa = parametrosAsunto.entrySet().iterator();
        while (iteraMapa.hasNext()) {

          Map.Entry<String, String> dato = iteraMapa.next();
          asunto = asunto.replaceAll("\\".concat(dato.getKey()), dato.getValue());

        }
      }

      mensaje = StringUtils.transformarTextoCadenaACodigoHTML(new String(correoParametrizaDTO.getTexto()));
      if (parametrosContenido != null && !parametrosContenido.isEmpty()) {

        Iterator<Map.Entry<String, Object>> iteraMapa = parametrosContenido.entrySet().iterator();
        while (iteraMapa.hasNext()) {

          Map.Entry<String, Object> dato = iteraMapa.next();
          if (!dato.getKey().equals(IConstantes.CONTENIDO_ADJUNTO_MAIL)) {
            mensaje = mensaje.replaceAll("\\".concat(dato.getKey()), String.valueOf(dato.getValue()));
          }

        }

      }

      Set<String> destinatarios = new HashSet<String>();
      for (String unCorreo : listaCorreo) {

        //VALIDAMOS EL CORREO: PENDIENTE
        //LO ADICIONAMOS A LA LISTA
        destinatarios.add(unCorreo.trim());
      }

      MailDTO mail = new MailDTO(asunto, destinatarios, mensaje);
      mail.setFormato(correoParametrizaDTO.getFormato());

      try {
        if (parametrosContenido != null && parametrosContenido.containsKey(IConstantes.CONTENIDO_ADJUNTO_MAIL)) {

          ArchivoReporte archivoReporte = (ArchivoReporte) parametrosContenido.get(IConstantes.CONTENIDO_ADJUNTO_MAIL);
          ArchivoAdjuntoDTO adjunto = new ArchivoAdjuntoDTO(archivoReporte.getArregloBytes(), archivoReporte.getNombreArchivo());
          mail.setAdjunto(adjunto);

        }
      } catch (Exception e) {
        String mensajeError = "ADVERTENCIA::: EL CORREO SERÁ ENVIADO SIN ARCHIVO ADJUNTO";
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, mensajeError, e);
      }

      enviarNotificacion(mail, codigoMail);

    } catch (Exception e) {

      try {

        //INSERTAMOS EL REGISTRO: MAIL ENVIADO CORRECTAMENTE
        CorreoEnvio correoEnvioEstado = new CorreoEnvio();
        correoEnvioEstado.setAsunto(asunto);
        correoEnvioEstado.setCodigo(codigoMail);
        correoEnvioEstado.setEstado(IConstantes.ESTADO_CORREO_ENVIO_FALLIDO);
        correoEnvioEstado.setTexto(mensaje.getBytes());
        correoEnvioEstado.setDetalleFallo(getPrintStackTrace(e).getBytes());
        correoEnvioEstado.setFecha(new Date());

        iMailSessionBMTLocal.actualizarCorreoEnvio(correoEnvioEstado);

      } catch (Exception e2) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROR::: REGISTRAR ESTADO ENVIO DE CORREO: (enviarNotificacion)", e2);
      }

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, " MailSessionBean ", e);

    }
  }

  /**
   *
   * @param mail
   */
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  private void enviarNotificacion(MailDTO mail, String codigoMail) {

    try {

      Properties propertiesMail = new Properties();

      for (ConstantesDTO unaConstantesDTO : KeyPropertiesFactory.getListaConstantesCorreo()) {
        propertiesMail.put(unaConstantesDTO.getCodigo(), unaConstantesDTO.getValor());
      }

      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

      Authenticator auth = new SMTPAuthenticator();
      Session session = Session.getInstance(propertiesMail, auth);
      session.setDebug(KeyPropertiesFactory.isDebugConsole());

      Date fechaEnvioMail = new Date();
      Set<String> destinatarios = mail.getDestinatarios();
      for (String destino : destinatarios) {

        //VERIFICAMOS EL CORREO
        if (destino == null || destino.trim().length() == 0) {
          continue;
        }

        CorreoEnvio correoEnvioEstado = new CorreoEnvio();
        //SETEAMOS INFORMACION BASICA PARA LOG DE REGISTRO DEL MAIL
        try {

          correoEnvioEstado.setAsunto(mail.getAsunto());
          correoEnvioEstado.setCodigo(codigoMail);
          correoEnvioEstado.setTexto(mail.getContenido().getBytes());
          correoEnvioEstado.setCorreo(destino);

        } catch (Exception e) {
          Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "ADVERTENCIA::: NO SE DEJARA LOG DE ENVIO: FALLO CARGAR DATOS INICIALES ENVIO DE CORREO: (enviarNotificacion)", e);
        }

        try {

          MimeMessage mensaje = new MimeMessage(session);

          mensaje.setRecipients(javax.mail.Message.RecipientType.TO, destino);//TO
          Address from = new InternetAddress(KeyPropertiesFactory.getUsuarioMail());
          mensaje.setFrom(from);//FROM

          mensaje.setSubject(mail.getAsunto());//ASUNTO
          mensaje.setSentDate(fechaEnvioMail);//FECHA

          Multipart contenidoCorreo = new MimeMultipart();

          BodyPart textoMensaje = new MimeBodyPart();

          if (mail.getContenido() != null) {
            textoMensaje.setContent(mail.getContenido(), mail.getFormato());
            contenidoCorreo.addBodyPart(textoMensaje);
          }

          try {
            if (mail.getAdjunto() != null && mail.getAdjunto().getArchivo() != null && mail.getAdjunto().getArchivo().isFile()) {

              MimeBodyPart adjunto = new MimeBodyPart();
              adjunto.attachFile(mail.getAdjunto().getArchivo());
              adjunto.setFileName(mail.getAdjunto().getNombreArchivo());

              contenidoCorreo.addBodyPart(adjunto);
              correoEnvioEstado.setNombreAdjunto(mail.getAdjunto().getNombreArchivo());
            }
          } catch (Exception e) {
            String mensajeError = "ADVERTENCIA::: EL CORREO SERÁ ENVIADO SIN ARCHIVO ADJUNTO";
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, mensajeError, e);
          }

          mensaje.setContent(contenidoCorreo);

          Transport.send(mensaje);
          correoEnvioEstado.setEstado(IConstantes.ESTADO_CORREO_ENVIO_EXITOSO);

        } catch (Exception e) {

          correoEnvioEstado.setEstado(IConstantes.ESTADO_CORREO_ENVIO_FALLIDO);
          correoEnvioEstado.setDetalleFallo(getPrintStackTrace(e).getBytes());

          if (mail.getAdjunto() != null && mail.getAdjunto().getArchivo() != null && mail.getAdjunto().getArchivo().isFile()) {
            correoEnvioEstado.setContenidoAdjunto(mail.getAdjunto().getContenidoArchivo());
          }

        }

        //GUARDAMOS EL LOG DE ENVIO. FALLIDO O EXISTOSO
        try {

          //INSERTAMOS EL REGISTRO
          correoEnvioEstado.setFecha(fechaEnvioMail);
          iMailSessionBMTLocal.actualizarCorreoEnvio(correoEnvioEstado);

        } catch (Exception e) {

          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROR::: REGISTRAR ESTADO ENVIO DE CORREO: (enviarNotificacion)", e);
        }
      }//FIN FOR: LISTA DE CORREOS

    } catch (Exception mex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROR::: ENVIO DE CORREO: (enviarNotificacion)", mex);

    } finally {

      try {
        if (mail.getAdjunto() != null && mail.getAdjunto().getArchivo() != null && mail.getAdjunto().getArchivo().isFile() && mail.getAdjunto().getNombreTemporal() != null) {

          File archivo = mail.getAdjunto().getArchivo();

          if (!FileUtils.deleteQuietly(archivo)) {
            Exception e = new Exception("ERROR::: EL ARCHIVO TEMPORAL '" + archivo.getName() + "' NO SE PUEDE SER BORRADO");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
          }
        }
      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
      }
    }
  }

  /**
   *
   */
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  private static class SMTPAuthenticator extends javax.mail.Authenticator {

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

      return new PasswordAuthentication(KeyPropertiesFactory.getUsuarioMail(), KeyPropertiesFactory.getUsuarioClave());

    }
  }

  /**
   *
   * @param throwable
   * @return
   */
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  private String getPrintStackTrace(Throwable throwable) {

    try {

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      throwable.printStackTrace(pw);
      return sw.toString();

    } catch (Exception e) {

      return "[ERROR]".concat(e.toString());
    }

  }
}
