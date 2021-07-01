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
public class NecesidadFiltro implements Serializable {

  private PAE pae;
  private Long novedad;
  private Long region;
  private Long unidadFisica;
  private Long unidadDependencia;
  private Long carrera;
  private Long estado;
  private Long proceso;
  private Long estrategia;
  private String origen;

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
   * @return the novedad
   */
  public Long getNovedad() {
    return novedad;
  }

  /**
   * @param novedad the novedad to set
   */
  public void setNovedad(Long novedad) {
    this.novedad = novedad;
  }

  /**
   * @return the region
   */
  public Long getRegion() {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(Long region) {
    this.region = region;
  }

  /**
   * @return the unidadFisica
   */
  public Long getUnidadFisica() {
    return unidadFisica;
  }

  /**
   * @param unidadFisica the unidadFisica to set
   */
  public void setUnidadFisica(Long unidadFisica) {
    this.unidadFisica = unidadFisica;
  }

  /**
   * @return the unidadDependencia
   */
  public Long getUnidadDependencia() {
    return unidadDependencia;
  }

  /**
   * @param unidadDependencia the unidadDependencia to set
   */
  public void setUnidadDependencia(Long unidadDependencia) {
    this.unidadDependencia = unidadDependencia;
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
   * @return the origen
   */
  public String getOrigen() {
    return origen;
  }

  /**
   * @param origen the origen to set
   */
  public void setOrigen(String origen) {
    this.origen = origen;
  }
}
