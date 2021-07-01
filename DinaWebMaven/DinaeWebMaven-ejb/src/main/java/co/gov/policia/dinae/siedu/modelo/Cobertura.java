/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "SIEDU_COBERTURA")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = Cobertura.FIND_ALL, query = "SELECT s FROM Cobertura s"),
  @NamedQuery (name = Cobertura.FIND_BY_ESCUELA, query = "SELECT c FROM Cobertura c WHERE c.escuela = :escuela AND c.pae = :pae AND c.estrategia = :estrategia")
})
public class Cobertura implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Cobertura.findAll";
  public static final String FIND_BY_ESCUELA = "Cobertura.findByEscuela";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "COBE_COBE", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_COBERTURA_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_COBERTURA_SEQ_GEN", sequenceName = "SIEDU_COBERTURA_SEQ", allocationSize = 1)
  private Long id;
  @JoinColumn(name = "COBE_PAE", referencedColumnName = "PAE_PAE")
  @ManyToOne(optional = false)
  private PAE pae;
  @JoinColumns({
    @JoinColumn(name = "COBE_UDE_ESCU", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "COBE_UDE_FUERZA_ESCU", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private UnidadDependencia escuela;
  @JoinColumn(name = "COBE_DOM_ESTRA", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio estrategia;
  @JoinColumns({
    @JoinColumn(name = "COBE_UDE_UFISI", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "COBE_UDE_FUERZA_UFISI", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private UnidadDependencia unidad;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "COBE_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COBE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COBE_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COBE_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "COBE_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "COBE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "COBE_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "COBE_IP_MOD", length = 100)
  private String modIP;

  public Cobertura() {
  }

  public Cobertura(Long id) {
    this.id = id;
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
   * @return the pae
   */
  public PAE getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(PAE pae) {
    this.pae = pae;
  }

  /**
   * @return the escuela
   */
  public UnidadDependencia getEscuela() {
    return escuela;
  }

  /**
   * @param escuela the escuela to set
   */
  public void setEscuela(UnidadDependencia escuela) {
    this.escuela = escuela;
  }

  /**
   * @return the estrategia
   */
  public Dominio getEstrategia() {
    return estrategia;
  }

  /**
   * @param estrategia the estrategia to set
   */
  public void setEstrategia(Dominio estrategia) {
    this.estrategia = estrategia;
  }

  /**
   * @return the unidad
   */
  public UnidadDependencia getUnidad() {
    return unidad;
  }

  /**
   * @param unidad the unidad to set
   */
  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }

  /**
   * @return the creaUsuario
   */
  public String getCreaUsuario() {
    return creaUsuario;
  }

  /**
   * @param creaUsuario the creaUsuario to set
   */
  public void setCreaUsuario(String creaUsuario) {
    this.creaUsuario = creaUsuario;
  }

  /**
   * @return the creaFecha
   */
  public Date getCreaFecha() {
    return creaFecha;
  }

  /**
   * @param creaFecha the creaFecha to set
   */
  public void setCreaFecha(Date creaFecha) {
    this.creaFecha = creaFecha;
  }

  /**
   * @return the creaMaquina
   */
  public String getCreaMaquina() {
    return creaMaquina;
  }

  /**
   * @param creaMaquina the creaMaquina to set
   */
  public void setCreaMaquina(String creaMaquina) {
    this.creaMaquina = creaMaquina;
  }

  /**
   * @return the creaIP
   */
  public String getCreaIP() {
    return creaIP;
  }

  /**
   * @param creaIP the creaIP to set
   */
  public void setCreaIP(String creaIP) {
    this.creaIP = creaIP;
  }

  /**
   * @return the modUsuario
   */
  public String getModUsuario() {
    return modUsuario;
  }

  /**
   * @param modUsuario the modUsuario to set
   */
  public void setModUsuario(String modUsuario) {
    this.modUsuario = modUsuario;
  }

  /**
   * @return the modFecha
   */
  public Date getModFecha() {
    return modFecha;
  }

  /**
   * @param modFecha the modFecha to set
   */
  public void setModFecha(Date modFecha) {
    this.modFecha = modFecha;
  }

  /**
   * @return the modMaquina
   */
  public String getModMaquina() {
    return modMaquina;
  }

  /**
   * @param modMaquina the modMaquina to set
   */
  public void setModMaquina(String modMaquina) {
    this.modMaquina = modMaquina;
  }

  /**
   * @return the modIP
   */
  public String getModIP() {
    return modIP;
  }

  /**
   * @param modIP the modIP to set
   */
  public void setModIP(String modIP) {
    this.modIP = modIP;
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
    if (!(object instanceof Cobertura)) {
      return false;
    }
    Cobertura other = (Cobertura) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.Cobertura[ cobeCobe=" + getId() + " ]";
  }
}
