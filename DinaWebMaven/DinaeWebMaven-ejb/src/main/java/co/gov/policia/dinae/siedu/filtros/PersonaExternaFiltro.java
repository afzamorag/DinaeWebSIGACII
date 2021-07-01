/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.filtros;

import java.io.Serializable;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class PersonaExternaFiltro implements Serializable {

  private String identificacionNro;
  private String nombres;
  private String apellidos;

  /**
   * @return the identificacionNro
   */
  public String getIdentificacionNro() {
    return identificacionNro;
  }

  /**
   * @param identificacionNro the identificacionNro to set
   */
  public void setIdentificacionNro(String identificacionNro) {
    this.identificacionNro = identificacionNro;
  }

  /**
   * @return the nombres
   */
  public String getNombres() {
    return nombres;
  }

  /**
   * @param nombres the nombres to set
   */
  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  /**
   * @return the apellidos
   */
  public String getApellidos() {
    return apellidos;
  }

  /**
   * @param apellidos the apellidos to set
   */
  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }
}
