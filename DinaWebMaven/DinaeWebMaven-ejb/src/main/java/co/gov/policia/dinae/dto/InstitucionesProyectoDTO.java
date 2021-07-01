package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.constantes.IConstantes;
import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class InstitucionesProyectoDTO implements Serializable {

  private Long idInstitucionesProyecto;
  private String aporteInvestigicacion;
  private Long idInstitucion;
  private String nombreInstitucion;
  private Long idProyecto;
  private String valorOtroTipo;

  public InstitucionesProyectoDTO() {
  }

  /**
   *
   * @param idInstitucionesProyecto
   * @param aporteInvestigicacion
   * @param idInstitucion
   * @param nombreInstitucion
   * @param idProyecto
   * @param valorOtroTipo
   */
  public InstitucionesProyectoDTO(Long idInstitucionesProyecto, String aporteInvestigicacion, Long idInstitucion,
          String nombreInstitucion, Long idProyecto, String valorOtroTipo) {

    this.idInstitucionesProyecto = idInstitucionesProyecto;
    this.aporteInvestigicacion = aporteInvestigicacion;
    this.idInstitucion = idInstitucion;

    if (IConstantes.TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO_OTRO.equals(idInstitucion)) {

      this.nombreInstitucion = valorOtroTipo;

    } else {

      this.nombreInstitucion = nombreInstitucion;
    }

    this.idProyecto = idProyecto;
    this.valorOtroTipo = valorOtroTipo;
  }

  public Long getIdInstitucionesProyecto() {
    return idInstitucionesProyecto;
  }

  public void setIdInstitucionesProyecto(Long idInstitucionesProyecto) {
    this.idInstitucionesProyecto = idInstitucionesProyecto;
  }

  public String getAporteInvestigicacion() {
    return aporteInvestigicacion;
  }

  public void setAporteInvestigicacion(String aporteInvestigicacion) {
    this.aporteInvestigicacion = aporteInvestigicacion;
  }

  public Long getIdInstitucion() {
    return idInstitucion;
  }

  public void setIdInstitucion(Long idInstitucion) {
    this.idInstitucion = idInstitucion;
  }

  public String getNombreInstitucion() {
    return nombreInstitucion;
  }

  public void setNombreInstitucion(String nombreInstitucion) {
    this.nombreInstitucion = nombreInstitucion;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getValorOtroTipo() {
    return valorOtroTipo;
  }

  public void setValorOtroTipo(String valorOtroTipo) {
    this.valorOtroTipo = valorOtroTipo;
  }

}
