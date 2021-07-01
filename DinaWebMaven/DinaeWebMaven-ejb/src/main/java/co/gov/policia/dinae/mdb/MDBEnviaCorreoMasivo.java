package co.gov.policia.dinae.mdb;

import co.gov.policia.dinae.dto.MDBDatosDetalleCorreoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@MessageDriven(mappedName = "jms/MDBEnviaCorreoMasivo", name = "MDBEnviaCorreoMasivo", activationConfig = {
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
  @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MDBEnviaCorreoMasivo implements MessageListener, Serializable {

  @Resource(mappedName = "jms/DinaeJMSFactory")
  private javax.jms.ConnectionFactory connectionFactory;

  @Resource(mappedName = "jms/MDBEnviaCorreo")
  private javax.jms.Queue queueMDBEnviaCorreo;

  /**
   *
   * @param message
   */
  @Override
  public void onMessage(javax.jms.Message message) {
    try {

      ObjectMessage om = (ObjectMessage) message;
      MDBDatosDetalleCorreoDTO mDBDatosDetalleCorreoDTO = (MDBDatosDetalleCorreoDTO) om.getObject();
      enviarMensajeCola(mDBDatosDetalleCorreoDTO);

    } catch (JMSException ex) {
      Logger.getLogger(MDBEnviaCorreoMasivo.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void enviarMensajeCola(MDBDatosDetalleCorreoDTO mDBDatosDetalleCorreoDTO) {
    Logger.getLogger(getClass().getName()).log(Level.INFO, "-------------------------  INICIA ENVIA DE CORREO MASIVO -------------------------");

    try {

      javax.jms.Connection connection = connectionFactory.createConnection();

      try {

        javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        try {

          List<String> listaCorreos = mDBDatosDetalleCorreoDTO.getListaCorreo();

          for (String correo : listaCorreos) {

            List<String> correoList = new ArrayList<String>(1);
            correoList.add(correo);

            javax.jms.MessageProducer messageProducer = session.createProducer(queueMDBEnviaCorreo);

            connection.start();

            javax.jms.Message messageMasivo = session.createObjectMessage(new MDBDatosDetalleCorreoDTO(
                    mDBDatosDetalleCorreoDTO.getCodigoMail(),
                    mDBDatosDetalleCorreoDTO.getParametrosAsunto(),
                    mDBDatosDetalleCorreoDTO.getParametrosContenido(),
                    correoList));

            messageProducer.send(messageMasivo);

          }

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

    Logger.getLogger(getClass().getName()).log(Level.INFO, "-------------------------  FIN ENVIA DE CORREO MASIVO -------------------------");
  }

}
