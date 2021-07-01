package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_SUBTIPO_ELEMENTO")
@NamedQueries({
  @NamedQuery(name = "SieduSubtipoElemento.findAll", query = "SELECT s FROM SieduSubtipoElemento s")})
@Cacheable(false)
@XmlRootElement
public class SieduSubtipoElemento implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduSubtipoElementoPK sieduSubtipoElementoPK;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "STEL_DESCRI", nullable = false, length = 100)
  private String descripcion;  

  public SieduSubtipoElemento() {
  }

  public SieduSubtipoElemento(SieduSubtipoElementoPK sieduSubtipoElementoPK, String descripcion) {
    this.sieduSubtipoElementoPK = sieduSubtipoElementoPK;
    this.descripcion = descripcion;
  }

  public SieduSubtipoElementoPK getSieduSubtipoElementoPK() {
    return sieduSubtipoElementoPK;
  }

  public void setSieduSubtipoElementoPK(SieduSubtipoElementoPK sieduSubtipoElementoPK) {
    this.sieduSubtipoElementoPK = sieduSubtipoElementoPK;
  }
  
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + Objects.hashCode(this.sieduSubtipoElementoPK);
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
    final SieduSubtipoElemento other = (SieduSubtipoElemento) obj;
    if (!Objects.equals(this.sieduSubtipoElementoPK, other.sieduSubtipoElementoPK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduSubtipoElemento{" + "sieduSubtipoElementoPK=" + sieduSubtipoElementoPK + ", descripcion=" + descripcion + '}';
  }

  
  
  
  
}
