/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class FormacionEscuelaPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "FRME_FORM", nullable = false)
  private Long formacion;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FRME_UDE_FUERZA", nullable = false)
  private Long escuelaFuerza;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FRME_UDE_ESCU", nullable = false)
  private Long escuela;

  public FormacionEscuelaPK() {
  }

  public FormacionEscuelaPK(Long frmeForm, Long frmeUdeFuerza, Long frmeUdeEscu) {
    this.formacion = frmeForm;
    this.escuelaFuerza = frmeUdeFuerza;
    this.escuela = frmeUdeEscu;
  }

  /**
   * @return the formacion
   */
  public Long getFormacion() {
    return formacion;
  }

  /**
   * @param formacion the formacion to set
   */
  public void setFormacion(Long formacion) {
    this.formacion = formacion;
  }

  /**
   * @return the escuelaFuerza
   */
  public Long getEscuelaFuerza() {
    return escuelaFuerza;
  }

  /**
   * @param escuelaFuerza the escuelaFuerza to set
   */
  public void setEscuelaFuerza(Long escuelaFuerza) {
    this.escuelaFuerza = escuelaFuerza;
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

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 67 * hash + Objects.hashCode(this.formacion);
    hash = 67 * hash + Objects.hashCode(this.escuelaFuerza);
    hash = 67 * hash + Objects.hashCode(this.escuela);
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
    final FormacionEscuelaPK other = (FormacionEscuelaPK) obj;
    if (!Objects.equals(this.formacion, other.formacion)) {
      return false;
    }
    if (!Objects.equals(this.escuelaFuerza, other.escuelaFuerza)) {
      return false;
    }
    if (!Objects.equals(this.escuela, other.escuela)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduPaeFormaEscuelaPK[ frmeForm=" + getFormacion() + ", frmeUdeFuerza=" + getEscuelaFuerza() + ", frmeUdeEscu=" + getEscuela() + " ]";
  }

}
