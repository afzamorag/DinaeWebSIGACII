package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SugerenciasProyectoDTO implements Serializable {

  private Long idSugerenciaProyecto;
  private String sugerencia;

  /**
   *
   */
  public SugerenciasProyectoDTO() {
  }

  /**
   *
   * @param idSugerenciaProyecto
   * @param sugerencia
   */
  public SugerenciasProyectoDTO(Long idSugerenciaProyecto, String sugerencia) {
    this.idSugerenciaProyecto = idSugerenciaProyecto;
    this.sugerencia = sugerencia;
  }

  public Long getIdSugerenciaProyecto() {
    return idSugerenciaProyecto;
  }

  public void setIdSugerenciaProyecto(Long idSugerenciaProyecto) {
    this.idSugerenciaProyecto = idSugerenciaProyecto;
  }

  public String getSugerencia() {
    return sugerencia;
  }

  public void setSugerencia(String sugerencia) {
    this.sugerencia = sugerencia;
  }

}
