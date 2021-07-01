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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class FormacionEscuelaCohortePK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_COHO", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_FORMA_ESCUELA_CORTE_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_FORMA_ESCUELA_CORTE_SEQ_GEN", sequenceName = "SIEDU_FORMA_ESCUELA_CORTE_SEQ", allocationSize = 1)
  private Long cohorte;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_FORM", nullable = false)
  private Long formacion;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_FUERZA", nullable = false)
  private Long escuelaFuerza;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_ESCU", nullable = false)
  private Long escuela;

  public FormacionEscuelaCohortePK() {
  }

  public FormacionEscuelaCohortePK(Long cohorte, Long formacion, Long escuelaFuerza, Long escuela) {
    this.cohorte = cohorte;
    this.formacion = formacion;
    this.escuelaFuerza = escuelaFuerza;
    this.escuela = escuela;
  }

  /**
   * @return the cohorte
   */
  public Long getCohorte() {
    return cohorte;
  }

  /**
   * @param cohorte the cohorte to set
   */
  public void setCohorte(Long cohorte) {
    this.cohorte = cohorte;
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
    int hash = 5;
    hash = 17 * hash + Objects.hashCode(this.getFormacion());
    hash = 17 * hash + Objects.hashCode(this.getEscuelaFuerza());
    hash = 17 * hash + Objects.hashCode(this.getEscuela());
    hash = 17 * hash + Objects.hashCode(this.getCohorte());
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
    final FormacionEscuelaCohortePK other = (FormacionEscuelaCohortePK) obj;
    if (!Objects.equals(this.formacion, other.formacion)) {
      return false;
    }
    if (!Objects.equals(this.escuelaFuerza, other.escuelaFuerza)) {
      return false;
    }
    if (!Objects.equals(this.escuela, other.escuela)) {
      return false;
    }
    if (!Objects.equals(this.cohorte, other.cohorte)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduFormaEscuelaCortePK[ frehForm=" + getFormacion() + ", frehFuerza=" + getEscuelaFuerza() + ", frehEscu=" + getEscuela() + ", frehCoho=" + getCohorte() + " ]";
  }
}
