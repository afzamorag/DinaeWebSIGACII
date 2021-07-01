package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class InformacionAdicionalPersonaSiatDTO implements Serializable {

  private String identificacacion;
  private String profesorPolicial;

  public InformacionAdicionalPersonaSiatDTO() {
  }

  public InformacionAdicionalPersonaSiatDTO(String identificacacion, String profesorPolicial) {
    this.identificacacion = identificacacion;
    this.profesorPolicial = profesorPolicial;
  }

  public String getIdentificacacion() {
    return identificacacion;
  }

  public void setIdentificacacion(String identificacacion) {
    this.identificacacion = identificacacion;
  }

  public String getProfesorPolicial() {
    return profesorPolicial;
  }

  public void setProfesorPolicial(String profesorPolicial) {
    this.profesorPolicial = profesorPolicial;
  }
}
