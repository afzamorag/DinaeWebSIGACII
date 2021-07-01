package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class ValidacionCompromisoPeriodoDTO implements Serializable {

  private Long idTipoCompromiso;

  private Long cantidadCompromisos;

  private Long idPeriodo;

  public ValidacionCompromisoPeriodoDTO(Long idTipoCompromiso, Long idPeriodo, Long cantidadCompromisos) {
    this.idTipoCompromiso = idTipoCompromiso;
    this.cantidadCompromisos = cantidadCompromisos;
    this.idPeriodo = idPeriodo;
  }

  public Long getIdTipoCompromiso() {
    return idTipoCompromiso;
  }

  public void setIdTipoCompromiso(Long idTipoCompromiso) {
    this.idTipoCompromiso = idTipoCompromiso;
  }

  public Long getCantidadCompromisos() {
    return cantidadCompromisos;
  }

  public void setCantidadCompromisos(Long cantidadCompromisos) {
    this.cantidadCompromisos = cantidadCompromisos;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

}
