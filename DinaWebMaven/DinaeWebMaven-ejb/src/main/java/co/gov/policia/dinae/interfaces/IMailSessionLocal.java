package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.CorreoParametrizaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CorreoEnvio;
import co.gov.policia.dinae.modelo.CorreoParametriza;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IMailSessionLocal {

  CorreoParametriza actualizarCorreoParametriza(CorreoParametriza correoParametriza) throws JpaDinaeException;

  /**
   *
   * @param codigo
   * @return
   * @throws JpaDinaeException
   */
  CorreoParametriza getCorreoParametrizaPorCodigo(String codigo) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<CorreoParametrizaDTO> getListCorreoEnvioDTO() throws JpaDinaeException;

  /**
   *
   * @param correoEnvio
   * @return
   * @throws JpaDinaeException
   */
  CorreoEnvio actualizarCorreoEnvio(CorreoEnvio correoEnvio) throws JpaDinaeException;

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param idRol
   * @param idUnidadPolicial
   */
  void enviarMail_RolUnidadPolicial(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, Long idRol, Long idUnidadPolicial);

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param lisaIdsTipoUnidades
   * @param listaIdRoles
   */
  void enviarMail_ListaTipoUnidades_ListaRoles(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<Long> lisaIdsTipoUnidades, List<Long> listaIdRoles);

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaRoles
   */
  void enviarMail_ListaRoles(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<Long> listaRoles);

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaCorreo
   */
  void enviarMail_ListaCorreo(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<String> listaCorreo);

  /**
   *
   * @param codigo
   * @return
   */
  CorreoParametriza getCorreoParametriza(String codigo);
}
