/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.filtros;

import co.gov.policia.dinae.siedu.modelo.PAE;
import java.io.Serializable;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class FormacionFiltro implements Serializable {

  private PAE pae;
  private Long proceso;
  private Long estrategia;
  private Long carrera;
  private Long estado;

  /**
   * @return the pae
   */
  public PAE getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(PAE pae) {
    this.pae = pae;
  }

  /**
   * @return the proceso
   */
  public Long getProceso() {
    return proceso;
  }

  /**
   * @param proceso the proceso to set
   */
  public void setProceso(Long proceso) {
    this.proceso = proceso;
  }

  /**
   * @return the estrategia
   */
  public Long getEstrategia() {
    return estrategia;
  }

  /**
   * @param estrategia the estrategia to set
   */
  public void setEstrategia(Long estrategia) {
    this.estrategia = estrategia;
  }

  /**
   * @return the carrera
   */
  public Long getCarrera() {
    return carrera;
  }

  /**
   * @param carrera the carrera to set
   */
  public void setCarrera(Long carrera) {
    this.carrera = carrera;
  }

  /**
   * @return the estado
   */
  public Long getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(Long estado) {
    this.estado = estado;
  }
}
