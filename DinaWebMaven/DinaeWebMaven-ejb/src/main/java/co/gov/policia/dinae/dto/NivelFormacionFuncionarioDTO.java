package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class NivelFormacionFuncionarioDTO implements Serializable {

  private String tituloObtenido;
  private String nivelAcademico;
  private String areaDelSaber;
  private Date fechaFinalizacion;

  /**
   *
   * @param tituloObtenido
   * @param nivelAcademico
   * @param areaDelSaber
   * @param fechaFinalizacion
   */
  public NivelFormacionFuncionarioDTO(String tituloObtenido, String nivelAcademico, String areaDelSaber, Date fechaFinalizacion) {
    this.tituloObtenido = tituloObtenido;
    this.nivelAcademico = nivelAcademico;
    this.areaDelSaber = areaDelSaber;
    this.fechaFinalizacion = fechaFinalizacion;
  }

  public NivelFormacionFuncionarioDTO() {
  }

  public String getTituloObtenido() {
    return tituloObtenido;
  }

  public void setTituloObtenido(String tituloObtenido) {
    this.tituloObtenido = tituloObtenido;
  }

  public String getNivelAcademico() {
    return nivelAcademico;
  }

  public void setNivelAcademico(String nivelAcademico) {
    this.nivelAcademico = nivelAcademico;
  }

  public String getAreaDelSaber() {
    return areaDelSaber;
  }

  public void setAreaDelSaber(String areaDelSaber) {
    this.areaDelSaber = areaDelSaber;
  }

  public Date getFechaFinalizacion() {
    return fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date fechaFinalizacion) {
    this.fechaFinalizacion = fechaFinalizacion;
  }

}
