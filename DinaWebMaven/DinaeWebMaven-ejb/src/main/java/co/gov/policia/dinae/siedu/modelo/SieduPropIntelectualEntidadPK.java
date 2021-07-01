package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Embeddable;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class SieduPropIntelectualEntidadPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Column(name = "PINE_ENTI")
  private Long idEntidad;

  @Column(name = "PINE_PIN")
  private Long idPin;

  public SieduPropIntelectualEntidadPK() {
  }

  public SieduPropIntelectualEntidadPK(Long idEntidad, Long idPin) {
    this.idEntidad = idEntidad;
    this.idPin = idPin;
  }

  public Long getIdEntidad() {
    return idEntidad;
  }

  public void setIdEntidad(Long idEntidad) {
    this.idEntidad = idEntidad;
  }

  public Long getIdPin() {
    return idPin;
  }

  public void setIdPin(Long idPin) {
    this.idPin = idPin;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 79 * hash + Objects.hashCode(this.idEntidad);
    hash = 79 * hash + Objects.hashCode(this.idPin);
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
    final SieduPropIntelectualEntidadPK other = (SieduPropIntelectualEntidadPK) obj;
    if (!Objects.equals(this.idEntidad, other.idEntidad)) {
      return false;
    }
    if (!Objects.equals(this.idPin, other.idPin)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPropIntelectualEntidadPK{" + "idEntidad=" + idEntidad + ", idPin=" + idPin + '}';
  }

  
  

}
