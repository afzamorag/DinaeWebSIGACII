/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author OFITE
 */
@Embeddable
public class SieduCompetenciaEventoPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "COMPE_EVEN", nullable = false)
  private Long compeEven;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COMPE_COMP", nullable = false)
  private Long compeComp;

  public SieduCompetenciaEventoPK() {
  }

  public SieduCompetenciaEventoPK(Long compeEven, Long compeComp) {
    this.compeEven = compeEven;
    this.compeComp = compeComp;
  }

  public Long getCompeEven() {
    return compeEven;
  }

  public void setCompeEven(Long compeEven) {
    this.compeEven = compeEven;
  }

  public Long getCompeComp() {
    return compeComp;
  }

  public void setCompeComp(Long compeComp) {
    this.compeComp = compeComp;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (Long) compeEven;
    hash += (Long) compeComp;
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduCompetenciaEventoPK)) {
      return false;
    }
    SieduCompetenciaEventoPK other = (SieduCompetenciaEventoPK) object;
    if (this.compeEven != other.compeEven) {
      return false;
    }
    if (this.compeComp != other.compeComp) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEventoPK[ compeEven=" + compeEven + ", compeComp=" + compeComp + " ]";
  }

}
