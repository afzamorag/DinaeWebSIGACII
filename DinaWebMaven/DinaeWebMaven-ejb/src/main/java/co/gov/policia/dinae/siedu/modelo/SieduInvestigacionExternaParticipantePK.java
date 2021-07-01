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
public class SieduInvestigacionExternaParticipantePK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Column(name = "INVP_PERS")
  private Long idPersona;
  
  @Column(name = "INVP_INVE")
  private Long idInvestigacionExterna;

  public SieduInvestigacionExternaParticipantePK() {
  }

  public SieduInvestigacionExternaParticipantePK(Long idPersona, Long idInvestigacionExterna) {
    this.idPersona = idPersona;
    this.idInvestigacionExterna = idInvestigacionExterna;
  }

  public Long getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(Long idPersona) {
    this.idPersona = idPersona;
  }

  public Long getIdInvestigacionExterna() {
    return idInvestigacionExterna;
  }

  public void setIdInvestigacionExterna(Long idInvestigacionExterna) {
    this.idInvestigacionExterna = idInvestigacionExterna;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 89 * hash + Objects.hashCode(this.idPersona);
    hash = 89 * hash + Objects.hashCode(this.idInvestigacionExterna);
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
    final SieduInvestigacionExternaParticipantePK other = (SieduInvestigacionExternaParticipantePK) obj;
    if (!Objects.equals(this.idPersona, other.idPersona)) {
      return false;
    }
    if (!Objects.equals(this.idInvestigacionExterna, other.idInvestigacionExterna)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduInvestigacionExternaParticipantePK{" + "idPersona=" + idPersona + ", idInvestigacionExterna=" + idInvestigacionExterna + '}';
  }



  

}
