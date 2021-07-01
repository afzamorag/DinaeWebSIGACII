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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_DOMINIO")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Dominio.FIND_ALL, query = "SELECT s FROM Dominio s"),
  @NamedQuery(name = Dominio.FIND_BY_ID, query = "SELECT s FROM Dominio s WHERE s.id = :id"),
  @NamedQuery(name = Dominio.FIND_BY_NOMBRE, query = "SELECT s FROM Dominio s WHERE s.nombre = :nombre"),
  @NamedQuery(name = Dominio.FIND_BY_DESCRIPCION, query = "SELECT s FROM Dominio s WHERE s.descripcion = :descripcion"),
  @NamedQuery(name = Dominio.FIND_BY_TIPO, query = "SELECT s FROM Dominio s WHERE s.tipo.id = :tipo"),
  @NamedQuery(name = Dominio.FIND_BY_TIPO_AND_VIGENTE, query = "SELECT d FROM Dominio d WHERE d.tipo.id = :tipo AND d.vigente IN ('true', 'SI')")
})
@XmlRootElement
public class Dominio implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Dominio.findAll";
  public static final String FIND_BY_ID = "Dominio.findByIdDominio";
  public static final String FIND_BY_NOMBRE = "Dominio.findByNombre";
  public static final String FIND_BY_DESCRIPCION = "Dominio.findByDescripcion";
  public static final String FIND_BY_TIPO = "Dominio.findByTipo";
  public static final String FIND_BY_TIPO_AND_VIGENTE = "Dominio.findByTipoAndVigente";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_DOMINIO")
  @SequenceGenerator(name = "DOMINIO_SEQ_GEN", sequenceName = "SIEDU_DOMINIO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOMINIO_SEQ_GEN")
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOMBRE")
  private String nombre;
  @Column(name = "DESCRIPCION")
  private String descripcion;
  @Column(name = "VIGENTE")
  private String vigente;
  @JoinColumn(name = "ID_TIPO_DOMINIO", referencedColumnName = "ID_TIPO_DOMINIO")
  @ManyToOne(optional = false)
  private DominioTipo tipo;

  public Dominio() {
  }

  public Dominio(Long idDominio) {
    this.id = idDominio;
  }

  /**
   * @return the id
   */
  @XmlAttribute
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
  @XmlAttribute
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
  @XmlTransient
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the vigente
   */
  @XmlTransient
  public String getVigente() {
    return vigente;
  }

  /**
   * @param vigente the vigente to set
   */
  public void setVigente(String vigente) {
    this.vigente = vigente;
  }

  /**
   * @return the tipo
   */
  @XmlTransient
  public DominioTipo getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  public void setTipo(DominioTipo tipo) {
    this.tipo = tipo;
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
    if (!(object instanceof Dominio)) {
      return false;
    }
    Dominio other = (Dominio) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.Dominio[ id=" + getId() + " ]";
  }
}
