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
public class PresupuestoFiltro implements Serializable {

  private Long pae;
  private Long escuela;
  private Long proceso;
  private Long estrategia;
  private Long carrera;
  private Long estado;
  private Long modalidad;
  private Long presupuesto;

  /**
   * @return the pae
   */
  public Long getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(Long pae) {
    this.pae = pae;
  }

  /**
   * @return the escuela
   */
  public Long getEscuela() {
    return escuela;
  }

  /**
   * @param escuela the escuela to set
   */
  public void setEscuela(Long escuela) {
    this.escuela = escuela;
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

  /**
   * @return the modalidad
   */
  public Long getModalidad() {
    return modalidad;
  }

  /**
   * @param modalidad the modalidad to set
   */
  public void setModalidad(Long modalidad) {
    this.modalidad = modalidad;
  }

  /**
   * @return the presupuesto
   */
  public Long getPresupuesto() {
    return presupuesto;
  }

  /**
   * @param presupuesto the presupuesto to set
   */
  public void setPresupuesto(Long presupuesto) {
    this.presupuesto = presupuesto;
  }
}
