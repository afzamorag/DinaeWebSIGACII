/*
 * Soft Studio LTDA
 * Copyrigth .2016.
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
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
@XmlRootElement
public class UnidadDependenciaPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "CONSECUTIVO")
  private Long consecutivo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FUERZA")
  private Long fuerza;

  public UnidadDependenciaPK() {
  }

  public UnidadDependenciaPK(Long idCarrera, Long fuerza) {
    this.consecutivo = idCarrera;
    this.fuerza = fuerza;
  }

  public Long getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(Long consecutivo) {
    this.consecutivo = consecutivo;
  }

  public Long getFuerza() {
    return fuerza;
  }

  public void setFuerza(Long fuerza) {
    this.fuerza = fuerza;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.consecutivo);
    hash = 37 * hash + Objects.hashCode(this.fuerza);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UnidadDependenciaPK)) {
      return false;
    }
    UnidadDependenciaPK other = (UnidadDependenciaPK) object;
    if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
      return false;
    }
    if (this.fuerza != other.fuerza) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Pojos.CarrerasPK[ idCarrera=" + consecutivo + ", fuerza=" + fuerza + " ]";
  }

}
