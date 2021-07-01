package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;

import javax.persistence.Embeddable;
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
public class SieduSubtipoElementoPK implements Serializable {

  private static final long serialVersionUID = -7265548787048841716L;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "STEL_TPEL", nullable = false, length = 20)
  private String idTipo;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "STEL_STEL", nullable = false, length = 50)
  private String idSubtipo;

  public SieduSubtipoElementoPK() {
  }

  public SieduSubtipoElementoPK(String idTipo, String idSubtipo) {
    this.idTipo = idTipo;
    this.idSubtipo = idSubtipo;
  }

  public String getIdTipo() {
    return idTipo;
  }

  public void setIdTipo(String idTipo) {
    this.idTipo = idTipo;
  }

  public String getIdSubtipo() {
    return idSubtipo;
  }

  public void setIdSubtipo(String idSubtipo) {
    this.idSubtipo = idSubtipo;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + Objects.hashCode(this.idTipo);
    hash = 53 * hash + Objects.hashCode(this.idSubtipo);
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
    final SieduSubtipoElementoPK other = (SieduSubtipoElementoPK) obj;
    if (!Objects.equals(this.idTipo, other.idTipo)) {
      return false;
    }
    if (!Objects.equals(this.idSubtipo, other.idSubtipo)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduSubtipoElementoPK{" + "idTipo=" + idTipo + ", idSubtipo=" + idSubtipo + '}';
  }
  
  
  
  

}
