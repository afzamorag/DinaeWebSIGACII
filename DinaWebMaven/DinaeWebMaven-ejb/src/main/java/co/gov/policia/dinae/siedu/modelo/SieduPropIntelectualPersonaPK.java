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
public class SieduPropIntelectualPersonaPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Column(name = "PINP_PERS")
  private Long idPersona;

  @Column(name = "PINP_PIN")
  private Long idPin;

  public SieduPropIntelectualPersonaPK() {
  }

  public SieduPropIntelectualPersonaPK(Long idPersona, Long idPin) {
    this.idPersona = idPersona;
    this.idPin = idPin;
  }

  public Long getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(Long idPersona) {
    this.idPersona = idPersona;
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
    hash = 67 * hash + Objects.hashCode(this.idPersona);
    hash = 67 * hash + Objects.hashCode(this.idPin);
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
    final SieduPropIntelectualPersonaPK other = (SieduPropIntelectualPersonaPK) obj;
    if (!Objects.equals(this.idPersona, other.idPersona)) {
      return false;
    }
    if (!Objects.equals(this.idPin, other.idPin)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPropIntelectualPersonaPK{" + "idPersona=" + idPersona + ", idPin=" + idPin + '}';
  }

  
  

}
