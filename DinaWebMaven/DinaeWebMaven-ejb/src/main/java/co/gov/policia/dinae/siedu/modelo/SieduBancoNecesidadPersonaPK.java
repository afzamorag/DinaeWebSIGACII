package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class SieduBancoNecesidadPersonaPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;

  /*@JoinColumn(name = "BNPE_PERS", referencedColumnName = "PERS_PERS")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduPersona persona;*/
  
  @Column(name = "BNPE_PERS")
  private Long idPersona;

  /*@JoinColumn(name = "BNPE_BNE", referencedColumnName = "BNE_BNE")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduBancoNecesidad bancoNecesidad;*/
  
  @Column(name = "BNPE_BNE")
  private Long idBancoNecesidad;

  public SieduBancoNecesidadPersonaPK() {
  }

  public SieduBancoNecesidadPersonaPK(Long idPersona, Long idBancoNecesidad) {
    this.idPersona = idPersona;
    this.idBancoNecesidad = idBancoNecesidad;
  }

  public Long getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(Long idPersona) {
    this.idPersona = idPersona;
  }

  public Long getIdBancoNecesidad() {
    return idBancoNecesidad;
  }

  public void setIdBancoNecesidad(Long idBancoNecesidad) {
    this.idBancoNecesidad = idBancoNecesidad;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 73 * hash + Objects.hashCode(this.idPersona);
    hash = 73 * hash + Objects.hashCode(this.idBancoNecesidad);
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
    final SieduBancoNecesidadPersonaPK other = (SieduBancoNecesidadPersonaPK) obj;
    if (!Objects.equals(this.idPersona, other.idPersona)) {
      return false;
    }
    if (!Objects.equals(this.idBancoNecesidad, other.idBancoNecesidad)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduBancoNecesidadPersonaPK{" + "idPersona=" + idPersona + ", idBancoNecesidad=" + idBancoNecesidad + '}';
  }


  

}
