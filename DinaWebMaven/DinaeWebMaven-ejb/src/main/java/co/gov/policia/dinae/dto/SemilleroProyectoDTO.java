package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SemilleroProyectoDTO implements Serializable {

  private Long idSemilleroProyecto;
  private Long idSemillero;
  private String nombreSemillero;
  private String aporteInvestigacion;
  private String nombreUnidadPolicial;
  private Long idUnidadPolicial;

  /**
   * SELECT NEW ..
   *
   * @param idSemilleroProyecto
   * @param idSemillero
   * @param nombreSemillero
   * @param aporteInvestigacion
   */
  public SemilleroProyectoDTO(Long idSemilleroProyecto, Long idSemillero, String nombreSemillero, String aporteInvestigacion) {

    this.idSemilleroProyecto = idSemilleroProyecto;
    this.idSemillero = idSemillero;
    this.nombreSemillero = nombreSemillero;
    this.aporteInvestigacion = aporteInvestigacion;
  }

  /**
   * SELECT NEW ..
   *
   * @param idSemilleroProyecto
   * @param aporteInvestigacion
   * @param nombreUnidadPolicial
   * @param idUnidadPolicial
   */
  public SemilleroProyectoDTO(Long idSemilleroProyecto, String aporteInvestigacion, String nombreUnidadPolicial, Long idUnidadPolicial) {
    this.idSemilleroProyecto = idSemilleroProyecto;
    this.aporteInvestigacion = aporteInvestigacion;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Long getIdSemilleroProyecto() {
    return idSemilleroProyecto;
  }

  public void setIdSemilleroProyecto(Long idSemilleroProyecto) {
    this.idSemilleroProyecto = idSemilleroProyecto;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public String getNombreSemillero() {
    return nombreSemillero;
  }

  public void setNombreSemillero(String nombreSemillero) {
    this.nombreSemillero = nombreSemillero;
  }

  public String getAporteInvestigacion() {
    return aporteInvestigacion;
  }

  public void setAporteInvestigacion(String aporteInvestigacion) {
    this.aporteInvestigacion = aporteInvestigacion;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

}
