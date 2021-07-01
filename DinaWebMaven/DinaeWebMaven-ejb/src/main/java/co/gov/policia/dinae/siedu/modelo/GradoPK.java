/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Jose Buzon
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class GradoPK implements Serializable {

  private static final long serialVersionUID = 3010207020444762403L;

  @Column(name = "FUERZA")
  @XmlAttribute
  private Long fuerza;

  @Column(name = "ALFABETICO")
  @XmlAttribute
  private String alfabetico;

  public Long getFuerza() {
    return fuerza;
  }

  public void setFuerza(Long fuerza) {
    this.fuerza = fuerza;
  }

  public String getAlfabetico() {
    return alfabetico;
  }

  public void setAlfabetico(String alfabetico) {
    this.alfabetico = alfabetico;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + Objects.hashCode(this.fuerza);
    hash = 67 * hash + Objects.hashCode(this.alfabetico);
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
    final GradoPK other = (GradoPK) obj;
    if (!Objects.equals(this.alfabetico, other.alfabetico)) {
      return false;
    }
    if (!Objects.equals(this.fuerza, other.fuerza)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "GradoPK{" + "fuerza=" + fuerza + ", alfabetico=" + alfabetico + '}';
  }
}
