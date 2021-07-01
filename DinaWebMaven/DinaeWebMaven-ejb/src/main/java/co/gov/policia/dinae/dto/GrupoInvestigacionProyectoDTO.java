package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class GrupoInvestigacionProyectoDTO implements Serializable {

  private Long idGrupoInvestigacionProyecto;
  private Date fechaVinculacion;
  private Date fechaRegistro;
  private Long idProyecto;
  private Long idGrupoInvestigacion;
  private String descripcionGrupoInvestigacion;
  private String codigoGrupoInvestigacion;
  private Date fechaRegistroGrupoInvestigacion;

  /**
   *
   * @param idGrupoInvestigacionProyecto
   * @param fechaVinculacion
   * @param fechaRegistro
   * @param idProyecto
   * @param idGrupoInvestigacion
   * @param descripcionGrupoInvestigacion
   * @param codigoGrupoInvestigacion
   * @param fechaRegistroGrupoInvestigacion
   */
  public GrupoInvestigacionProyectoDTO(Long idGrupoInvestigacionProyecto, Date fechaVinculacion, Date fechaRegistro, Long idProyecto, Long idGrupoInvestigacion, String descripcionGrupoInvestigacion, String codigoGrupoInvestigacion, Date fechaRegistroGrupoInvestigacion) {
    this.idGrupoInvestigacionProyecto = idGrupoInvestigacionProyecto;
    this.fechaVinculacion = fechaVinculacion;
    this.fechaRegistro = fechaRegistro;
    this.idProyecto = idProyecto;
    this.idGrupoInvestigacion = idGrupoInvestigacion;
    this.descripcionGrupoInvestigacion = descripcionGrupoInvestigacion;
    this.codigoGrupoInvestigacion = codigoGrupoInvestigacion;
    this.fechaRegistroGrupoInvestigacion = fechaRegistroGrupoInvestigacion;
  }

  public String getDescripcionGrupoInvestigacion() {
    return descripcionGrupoInvestigacion;
  }

  public void setDescripcionGrupoInvestigacion(String descripcionGrupoInvestigacion) {
    this.descripcionGrupoInvestigacion = descripcionGrupoInvestigacion;
  }

  public String getCodigoGrupoInvestigacion() {
    return codigoGrupoInvestigacion;
  }

  public void setCodigoGrupoInvestigacion(String codigoGrupoInvestigacion) {
    this.codigoGrupoInvestigacion = codigoGrupoInvestigacion;
  }

  public Date getFechaRegistroGrupoInvestigacion() {
    return fechaRegistroGrupoInvestigacion;
  }

  public void setFechaRegistroGrupoInvestigacion(Date fechaRegistroGrupoInvestigacion) {
    this.fechaRegistroGrupoInvestigacion = fechaRegistroGrupoInvestigacion;
  }

  public Long getIdGrupoInvestigacionProyecto() {
    return idGrupoInvestigacionProyecto;
  }

  public void setIdGrupoInvestigacionProyecto(Long idGrupoInvestigacionProyecto) {
    this.idGrupoInvestigacionProyecto = idGrupoInvestigacionProyecto;
  }

  public Date getFechaVinculacion() {
    return fechaVinculacion;
  }

  public void setFechaVinculacion(Date fechaVinculacion) {
    this.fechaVinculacion = fechaVinculacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Long getIdGrupoInvestigacion() {
    return idGrupoInvestigacion;
  }

  public void setIdGrupoInvestigacion(Long idGrupoInvestigacion) {
    this.idGrupoInvestigacion = idGrupoInvestigacion;
  }

}
