package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ProyectoVersionDTO implements Serializable {

  private Long idVersion;
  private Long idProyecto;
  private Date fechaVersion;

  public ProyectoVersionDTO() {
  }

  /**
   *
   * @param idVersion
   * @param idProyecto
   * @param fechaVersion
   */
  public ProyectoVersionDTO(Long idVersion, Long idProyecto, Date fechaVersion) {
    this.idVersion = idVersion;
    this.idProyecto = idProyecto;
    this.fechaVersion = fechaVersion;
  }

  public Long getIdVersion() {
    return idVersion;
  }

  public void setIdVersion(Long idVersion) {
    this.idVersion = idVersion;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Date getFechaVersion() {
    return fechaVersion;
  }

  public void setFechaVersion(Date fechaVersion) {
    this.fechaVersion = fechaVersion;
  }

}
