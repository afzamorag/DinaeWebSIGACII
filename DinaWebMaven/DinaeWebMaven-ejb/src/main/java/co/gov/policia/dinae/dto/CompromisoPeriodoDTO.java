package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.constantes.IConstantes;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CompromisoPeriodoDTO implements Serializable {

  private Long idCompromisoPeriodo;
  private Long idTipoCompromiso;
  private String nombreTipoCompromiso;
  private short numeroIncrementa;
  private Date fecha;

  public CompromisoPeriodoDTO() {
  }

  /**
   *
   * @param idCompromisoPeriodo
   * @param idTipoCompromiso
   * @param nombreTipoCompromiso
   * @param numeroIncrementa
   */
  public CompromisoPeriodoDTO(Long idCompromisoPeriodo, Long idTipoCompromiso, String nombreTipoCompromiso, short numeroIncrementa) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
    this.idTipoCompromiso = idTipoCompromiso;
    this.nombreTipoCompromiso = nombreTipoCompromiso;
    this.numeroIncrementa = numeroIncrementa;
  }

  /**
   *
   * @param idCompromisoPeriodo
   * @param idTipoCompromiso
   * @param nombreTipoCompromiso
   * @param numeroIncrementa
   * @param fecha
   */
  public CompromisoPeriodoDTO(Long idCompromisoPeriodo, Long idTipoCompromiso, String nombreTipoCompromiso, short numeroIncrementa, Date fecha) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
    this.idTipoCompromiso = idTipoCompromiso;
    this.nombreTipoCompromiso = nombreTipoCompromiso;
    this.numeroIncrementa = numeroIncrementa;
    this.fecha = fecha;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getNombreCompromisoNumeroIncrementa() {
    if (idTipoCompromiso == null || nombreTipoCompromiso == null) {
      return "";
    }
    return nombreTipoCompromiso.concat(
            idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
            ? " ".concat(String.valueOf(numeroIncrementa)) : "");
  }

  public Long getIdCompromisoPeriodo() {
    return idCompromisoPeriodo;
  }

  public void setIdCompromisoPeriodo(Long idCompromisoPeriodo) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
  }

  public Long getIdTipoCompromiso() {
    return idTipoCompromiso;
  }

  public void setIdTipoCompromiso(Long idTipoCompromiso) {
    this.idTipoCompromiso = idTipoCompromiso;
  }

  public String getNombreTipoCompromiso() {
    return nombreTipoCompromiso;
  }

  public void setNombreTipoCompromiso(String nombreTipoCompromiso) {
    this.nombreTipoCompromiso = nombreTipoCompromiso;
  }

  public short getNumeroIncrementa() {
    return numeroIncrementa;
  }

  public void setNumeroIncrementa(short numeroIncrementa) {
    this.numeroIncrementa = numeroIncrementa;
  }

}
