/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.filtros;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.siedu.modelo.Dominio;

/**
 *
 * @author JUAN.CIFUENTES
 */
public class EventoFiltro {

  private Carreras carrera;
  private Dominio modalidad;
  private Dominio proceso;

  public EventoFiltro() {
    carrera = new Carreras();
  }

  /**
   * @return the carrera
   */
  public Carreras getCarrera() {
    return carrera;
  }

  /**
   * @param carrera the carrera to set
   */
  public void setCarrera(Carreras carrera) {
    this.carrera = carrera;
  }

  /**
   * @return the modalidad
   */
  public Dominio getModalidad() {
    return modalidad;
  }

  /**
   * @param modalidad the modalidad to set
   */
  public void setModalidad(Dominio modalidad) {
    this.modalidad = modalidad;
  }

  /**
   * @return the proceso
   */
  public Dominio getProceso() {
    return proceso;
  }

  /**
   * @param proceso the proceso to set
   */
  public void setProceso(Dominio proceso) {
    this.proceso = proceso;
  }

}
