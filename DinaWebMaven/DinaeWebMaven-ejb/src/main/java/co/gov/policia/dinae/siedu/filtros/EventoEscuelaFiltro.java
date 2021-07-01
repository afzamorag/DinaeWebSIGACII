/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.filtros;

/**
 *
 * @author ANDRES.ZAMORAG <af.zamorag@gmail.com>
 */
public class EventoEscuelaFiltro {

  private String vigencia;
  private Long capaUdeEscu;
  private Long idModalidad;
  private Long idCarrera;

  public String getVigencia() {
    return vigencia;
  }

  public void setVigencia(String vigencia) {
    this.vigencia = vigencia;
  }

  public Long getCapaUdeEscu() {
    return capaUdeEscu;
  }

  public void setCapaUdeEscu(Long capaUdeEscu) {
    this.capaUdeEscu = capaUdeEscu;
  }

  public Long getIdModalidad() {
    return idModalidad;
  }

  public void setIdModalidad(Long idModalidad) {
    this.idModalidad = idModalidad;
  }

  public Long getIdCarrera() {
    return idCarrera;
  }

  public void setIdCarrera(Long idCarrera) {
    this.idCarrera = idCarrera;
  }  
  
}
