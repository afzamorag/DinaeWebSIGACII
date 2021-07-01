package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "SIEDU_TIPO_ELEMENTO")
@NamedQueries({
  @NamedQuery(name = "SieduTipoElemento.findAll", query = "SELECT s FROM SieduTipoElemento s")})
@Cacheable(false)
@XmlRootElement
public class SieduTipoElemento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "TPEL_TPEL", nullable = false, length = 20)
  private String idTipoElemento;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "TPEL_DESCRI", nullable = false, length = 100)
  private String descripcion;  

  public SieduTipoElemento() {
  }

  public SieduTipoElemento(String idTipoElemento, String descripcion) {
    this.idTipoElemento = idTipoElemento;
    this.descripcion = descripcion;
  }
  @XmlAttribute
  public String getIdTipoElemento() {
    return idTipoElemento;
  }

  public void setIdTipoElemento(String idTipoElemento) {
    this.idTipoElemento = idTipoElemento;
  }
  
  @XmlAttribute
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 83 * hash + Objects.hashCode(this.idTipoElemento);
    return hash;
  }

   @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SieduTipoElemento other = (SieduTipoElemento) obj;
    if (!Objects.equals(this.idTipoElemento, other.idTipoElemento)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduTipoElemento{" + "idTipoElemento=" + idTipoElemento + ", descripcion=" + descripcion + '}';
  }
  
  
  
  
}
