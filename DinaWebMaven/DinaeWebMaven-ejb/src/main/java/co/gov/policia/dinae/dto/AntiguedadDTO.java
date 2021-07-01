package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class AntiguedadDTO implements Serializable {

  private Long anosAntiguedad;
  private Long mesesAntiguiedad;
  private Long diasAntiguedad;

  /**
   *
   */
  public AntiguedadDTO() {
  }

  /**
   *
   * @param anosAntiguedad
   * @param mesesAntiguiedad
   * @param diasAntiguedad
   */
  public AntiguedadDTO(Long anosAntiguedad, Long mesesAntiguiedad, Long diasAntiguedad) {
    this.anosAntiguedad = anosAntiguedad;
    this.mesesAntiguiedad = mesesAntiguiedad;
    this.diasAntiguedad = diasAntiguedad;
  }

  public Long getAnosAntiguedad() {
    return anosAntiguedad;
  }

  public void setAnosAntiguedad(Long anosAntiguedad) {
    this.anosAntiguedad = anosAntiguedad;
  }

  public Long getMesesAntiguiedad() {
    return mesesAntiguiedad;
  }

  public void setMesesAntiguiedad(Long mesesAntiguiedad) {
    this.mesesAntiguiedad = mesesAntiguiedad;
  }

  public Long getDiasAntiguedad() {
    return diasAntiguedad;
  }

  public void setDiasAntiguedad(Long diasAntiguedad) {
    this.diasAntiguedad = diasAntiguedad;
  }

}
