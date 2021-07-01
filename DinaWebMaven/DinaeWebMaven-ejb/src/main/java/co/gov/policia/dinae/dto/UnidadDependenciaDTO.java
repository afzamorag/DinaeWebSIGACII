package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class UnidadDependenciaDTO implements Serializable {

  private Long consecutivo;
  private String descripcionDependencia;
  private Long tipoUnidadCodigo;
  private String siglaFisica;

  /**
   *
   */
  public UnidadDependenciaDTO() {
  }

  public UnidadDependenciaDTO(Long consecutivo, String descripcionDependencia, Long tipoUnidadCodigo) {
    this.consecutivo = consecutivo;
    this.descripcionDependencia = descripcionDependencia;
    this.tipoUnidadCodigo = tipoUnidadCodigo;
  }

  public UnidadDependenciaDTO(Long consecutivo, String siglaFisica) {
    this.consecutivo = consecutivo;
    this.siglaFisica = siglaFisica;
  }

  public Long getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  public String getDescripcionDependencia() {
    return descripcionDependencia;
  }

  public void setDescripcionDependencia(String descripcionDependencia) {
    this.descripcionDependencia = descripcionDependencia;
  }

  public Long getTipoUnidadCodigo() {
    return tipoUnidadCodigo;
  }

  public void setTipoUnidadCodigo(Long tipoUnidadCodigo) {
    this.tipoUnidadCodigo = tipoUnidadCodigo;
  }

  public String getSiglaFisica() {
    return siglaFisica;
  }

  public void setSiglaFisica(String siglaFisica) {
    this.siglaFisica = siglaFisica;
  }

}
