package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CompromisoImplementacionDTO implements Serializable {

  private Long idCompromisoImplementacion;
  private Date fecha;
  private Long idEstadoCompromisoImpl;
  private String descripcionEstadoCompromisoImpl;
  private Long idTipoCompromiso;
  private String descripcionTipoCompromiso;
  private Long idCompromisoPadre;

  public CompromisoImplementacionDTO() {
  }

  /**
   *
   * @param idCompromisoImplementacion
   * @param fecha
   * @param idEstadoCompromisoImpl
   * @param descripcionEstadoCompromisoImpl
   * @param idTipoCompromiso
   * @param descripcionTipoCompromiso
   */
  public CompromisoImplementacionDTO(Long idCompromisoImplementacion, Date fecha, Long idEstadoCompromisoImpl, String descripcionEstadoCompromisoImpl, Long idTipoCompromiso, String descripcionTipoCompromiso) {
    this.idCompromisoImplementacion = idCompromisoImplementacion;
    this.fecha = fecha;
    this.idEstadoCompromisoImpl = idEstadoCompromisoImpl;
    this.descripcionEstadoCompromisoImpl = descripcionEstadoCompromisoImpl;
    this.idTipoCompromiso = idTipoCompromiso;
    this.descripcionTipoCompromiso = descripcionTipoCompromiso;
  }

  public Long getIdCompromisoImplementacion() {
    return idCompromisoImplementacion;
  }

  public void setIdCompromisoImplementacion(Long idCompromisoImplementacion) {
    this.idCompromisoImplementacion = idCompromisoImplementacion;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Long getIdEstadoCompromisoImpl() {
    return idEstadoCompromisoImpl;
  }

  public void setIdEstadoCompromisoImpl(Long idEstadoCompromisoImpl) {
    this.idEstadoCompromisoImpl = idEstadoCompromisoImpl;
  }

  public String getDescripcionEstadoCompromisoImpl() {
    return descripcionEstadoCompromisoImpl;
  }

  public void setDescripcionEstadoCompromisoImpl(String descripcionEstadoCompromisoImpl) {
    this.descripcionEstadoCompromisoImpl = descripcionEstadoCompromisoImpl;
  }

  public Long getIdTipoCompromiso() {
    return idTipoCompromiso;
  }

  public void setIdTipoCompromiso(Long idTipoCompromiso) {
    this.idTipoCompromiso = idTipoCompromiso;
  }

  public String getDescripcionTipoCompromiso() {
    return descripcionTipoCompromiso;
  }

  public void setDescripcionTipoCompromiso(String descripcionTipoCompromiso) {
    this.descripcionTipoCompromiso = descripcionTipoCompromiso;
  }

  public Long getIdCompromisoPadre() {
    return idCompromisoPadre;
  }

  public void setIdCompromisoPadre(Long idCompromisoPadre) {
    this.idCompromisoPadre = idCompromisoPadre;
  }
}
