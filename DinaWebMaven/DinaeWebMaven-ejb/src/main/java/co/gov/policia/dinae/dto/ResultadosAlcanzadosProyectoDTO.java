package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ResultadosAlcanzadosProyectoDTO implements Serializable {

  private Long idResultadosEsperadosProyecto;
  private String resultadoAlcanzado;

  /**
   *
   */
  public ResultadosAlcanzadosProyectoDTO() {
  }

  /**
   *
   * @param idResultadosEsperadosProyecto
   * @param resultadoAlcanzado
   */
  public ResultadosAlcanzadosProyectoDTO(Long idResultadosEsperadosProyecto, String resultadoAlcanzado) {
    this.idResultadosEsperadosProyecto = idResultadosEsperadosProyecto;
    this.resultadoAlcanzado = resultadoAlcanzado;
  }

  public Long getIdResultadosEsperadosProyecto() {
    return idResultadosEsperadosProyecto;
  }

  public void setIdResultadosEsperadosProyecto(Long idResultadosEsperadosProyecto) {
    this.idResultadosEsperadosProyecto = idResultadosEsperadosProyecto;
  }

  public String getResultadoAlcanzado() {
    return resultadoAlcanzado;
  }

  public void setResultadoAlcanzado(String resultadoAlcanzado) {
    this.resultadoAlcanzado = resultadoAlcanzado;
  }
}
