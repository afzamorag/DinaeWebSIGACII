package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class SieduPropuestaAsignadaPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;

  @Column(name = "PPA_PRIN_VIGENCIA")
  private String vigencia;
  
  @Column(name = "PPA_ID_UNIDAD_POLICIAL")
  private Long unidad;
 
  @Column(name = "PPA_PRIN_DOM_MODALIDAD")
  private Long idModalidad;

  @Column(name = "PPA_ID_PROPUESTA_NECESIDAD")
  private Long idPropuestaNecesidad;


  public SieduPropuestaAsignadaPK() {
  }

  public SieduPropuestaAsignadaPK(String vigencia, Long unidad, Long idModalidad, Long idPropuestaNecesidad) {
    this.vigencia = vigencia;
    this.unidad = unidad;
    this.idModalidad = idModalidad;
    this.idPropuestaNecesidad = idPropuestaNecesidad;
  }

  public String getVigencia() {
    return vigencia;
  }

  public void setVigencia(String vigencia) {
    this.vigencia = vigencia;
  }

  public Long getUnidad() {
    return unidad;
  }

  public void setUnidad(Long unidad) {
    this.unidad = unidad;
  }

  public Long getIdModalidad() {
    return idModalidad;
  }

  public void setIdModalidad(Long idModalidad) {
    this.idModalidad = idModalidad;
  }

  public Long getIdPropuestaNecesidad() {
    return idPropuestaNecesidad;
  }

  public void setIdPropuestaNecesidad(Long idPropuestaNecesidad) {
    this.idPropuestaNecesidad = idPropuestaNecesidad;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + Objects.hashCode(this.vigencia);
    hash = 97 * hash + Objects.hashCode(this.unidad);
    hash = 97 * hash + Objects.hashCode(this.idModalidad);
    hash = 97 * hash + Objects.hashCode(this.idPropuestaNecesidad);
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
    final SieduPropuestaAsignadaPK other = (SieduPropuestaAsignadaPK) obj;
    if (!Objects.equals(this.vigencia, other.vigencia)) {
      return false;
    }
    if (!Objects.equals(this.unidad, other.unidad)) {
      return false;
    }
    if (!Objects.equals(this.idModalidad, other.idModalidad)) {
      return false;
    }
    if (!Objects.equals(this.idPropuestaNecesidad, other.idPropuestaNecesidad)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPropuestaAsignadaPK{" + "vigencia=" + vigencia + ", unidad=" + unidad + ", idModalidad=" + idModalidad + ", idPropuestaNecesidad=" + idPropuestaNecesidad + '}';
  }

  

}
