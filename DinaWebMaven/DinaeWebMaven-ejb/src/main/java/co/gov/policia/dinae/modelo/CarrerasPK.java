/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Embeddable
@XmlRootElement
public class CarrerasPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_CARRERA")
  private Long idCarrera;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FUERZA")
  private Long fuerza;

  public CarrerasPK() {
  }

  public CarrerasPK(Long idCarrera, Long fuerza) {
    this.idCarrera = idCarrera;
    this.fuerza = fuerza;
  }

  public Long getIdCarrera() {
    return idCarrera;
  }

  public void setIdCarrera(Long idCarrera) {
    this.idCarrera = idCarrera;
  }

  public Long getFuerza() {
    return fuerza;
  }

  public void setFuerza(Long fuerza) {
    this.fuerza = fuerza;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 41 * hash + Objects.hashCode(this.idCarrera);
    hash = 41 * hash + Objects.hashCode(this.fuerza);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CarrerasPK other = (CarrerasPK) obj;
    if (!Objects.equals(this.idCarrera, other.idCarrera)) {
      return false;
    }
    if (!Objects.equals(this.fuerza, other.fuerza)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "CarrerasPK{" + "idCarrera=" + idCarrera + ", fuerza=" + fuerza + '}';
  }
}
