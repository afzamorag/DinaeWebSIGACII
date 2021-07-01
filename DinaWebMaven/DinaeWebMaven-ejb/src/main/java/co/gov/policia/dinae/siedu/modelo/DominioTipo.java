/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_TIPO_DOMINIO")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = DominioTipo.FIND_ALL, query = "SELECT s FROM DominioTipo s"),
  @NamedQuery(name = DominioTipo.FIND_BY_ID, query = "SELECT s FROM DominioTipo s WHERE s.id = :id"),
  @NamedQuery(name = DominioTipo.FIND_BY_DESCRIPCION, query = "SELECT s FROM DominioTipo s WHERE s.descripcion = :descripcion")
})
@XmlRootElement
public class DominioTipo implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "DominioTipo.findAll";
  public static final String FIND_BY_ID = "DominioTipo.findById";
  public static final String FIND_BY_DESCRIPCION = "DominioTipo.findByDescripcion";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_TIPO_DOMINIO")
  @SequenceGenerator(name = "SIEDU_TIPO_DOMINIO_SEQ_GEN", sequenceName = "SIEDU_TIPO_DOMINIO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_TIPO_DOMINIO_SEQ_GEN")
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOMBRE")
  private String nombre;
  @Column(name = "DESCRIPCION")
  private String descripcion;

  public DominioTipo() {
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DominioTipo)) {
      return false;
    }
    DominioTipo other = (DominioTipo) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.DominioTipo[ id =" + getId() + " ]";
  }
}
