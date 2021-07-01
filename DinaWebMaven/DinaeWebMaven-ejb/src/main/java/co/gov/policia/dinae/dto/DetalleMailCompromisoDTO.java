package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class DetalleMailCompromisoDTO implements Serializable {

  private Map<String, String> parametrosAsunto = new HashMap<String, String>();
  private Map<String, Object> parametrosContenido = new HashMap<String, Object>();
  private Long idRol;
  private Long idUnidadPolicial;

  public DetalleMailCompromisoDTO() {
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Map<String, String> getParametrosAsunto() {
    return parametrosAsunto;
  }

  public void setParametrosAsunto(Map<String, String> parametrosAsunto) {
    this.parametrosAsunto = parametrosAsunto;
  }

  public Map<String, Object> getParametrosContenido() {
    return parametrosContenido;
  }

  public void setParametrosContenido(Map<String, Object> parametrosContenido) {
    this.parametrosContenido = parametrosContenido;
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
  }

}
