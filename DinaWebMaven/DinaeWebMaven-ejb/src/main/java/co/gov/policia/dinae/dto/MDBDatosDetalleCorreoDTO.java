package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class MDBDatosDetalleCorreoDTO implements Serializable {

  private final String codigoMail;
  private final Map<String, String> parametrosAsunto;
  private final Map<String, Object> parametrosContenido;
  private final List<String> listaCorreo;

  /**
   *
   * @param codigoMail
   * @param parametrosAsunto
   * @param parametrosContenido
   * @param listaCorreo
   */
  public MDBDatosDetalleCorreoDTO(String codigoMail, Map<String, String> parametrosAsunto, Map<String, Object> parametrosContenido, List<String> listaCorreo) {
    this.codigoMail = codigoMail;
    this.parametrosAsunto = parametrosAsunto;
    this.parametrosContenido = parametrosContenido;
    this.listaCorreo = listaCorreo;
  }

  public String getCodigoMail() {
    return codigoMail;
  }

  public Map<String, String> getParametrosAsunto() {
    return parametrosAsunto;
  }

  public Map<String, Object> getParametrosContenido() {
    return parametrosContenido;
  }

  public List<String> getListaCorreo() {
    return listaCorreo;
  }
}
