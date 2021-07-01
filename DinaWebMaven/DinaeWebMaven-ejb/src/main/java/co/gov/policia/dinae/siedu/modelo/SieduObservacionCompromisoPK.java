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
public class SieduObservacionCompromisoPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Column(name = "OBCO_INVE")
  private Long idInvestigacion;

  @Column(name = "OBCO_COMP")
  private Long idCompromiso;
  
  @Column(name = "OBCO_OBCO")
  private Long idObservacion;

  public SieduObservacionCompromisoPK() {
  }

  public SieduObservacionCompromisoPK(Long idInvestigacion, Long idCompromiso, Long idObservacion) {
    this.idInvestigacion = idInvestigacion;
    this.idCompromiso = idCompromiso;
    this.idObservacion = idObservacion;
  }

  public Long getIdInvestigacion() {
    return idInvestigacion;
  }

  public void setIdInvestigacion(Long idInvestigacion) {
    this.idInvestigacion = idInvestigacion;
  }

  public Long getIdCompromiso() {
    return idCompromiso;
  }

  public void setIdCompromiso(Long idCompromiso) {
    this.idCompromiso = idCompromiso;
  }

  public Long getIdObservacion() {
    return idObservacion;
  }

  public void setIdObservacion(Long idObservacion) {
    this.idObservacion = idObservacion;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + Objects.hashCode(this.idInvestigacion);
    hash = 13 * hash + Objects.hashCode(this.idCompromiso);
    hash = 13 * hash + Objects.hashCode(this.idObservacion);
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
    final SieduObservacionCompromisoPK other = (SieduObservacionCompromisoPK) obj;
    if (!Objects.equals(this.idInvestigacion, other.idInvestigacion)) {
      return false;
    }
    if (!Objects.equals(this.idCompromiso, other.idCompromiso)) {
      return false;
    }
    if (!Objects.equals(this.idObservacion, other.idObservacion)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduObservacionCompromisoPK{" + "idInvestigacion=" + idInvestigacion + ", idCompromiso=" + idCompromiso + ", idObservacion=" + idObservacion + '}';
  }



  
  

}
