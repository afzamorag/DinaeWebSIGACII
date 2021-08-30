/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "SIEDU_PAE")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = PAE.FIND_ALL, query = "SELECT s FROM PAE s ORDER BY s.vigencia ASC"),
  @NamedQuery(name = PAE.FIND_BY_VIGENCIA, query = "SELECT s FROM PAE s WHERE s.vigencia = :vigencia"),
  @NamedQuery(name = PAE.FIND_BY_ESTADO, query = "SELECT s FROM PAE s WHERE s.estado = (:estado) ORDER BY s.vigencia DESC")
})
@XmlRootElement
public class PAE implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "PAE.findAll";
  public static final String FIND_BY_VIGENCIA = "PAE.findByVigencia";
  public static final String FIND_BY_ESTADO = "PAE.findByEstado";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "PAE_PAE", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PAE_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_PAE_SEQ_GEN", sequenceName = "SIEDU_PAE_SEQ", allocationSize = 1)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 7)
  @Column(name = "PAE_VIGENCIA", nullable = false, length = 7)
  private String vigencia;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PAE_ESTADO", nullable = false, length = 30)
  private String estado;
  @Column(name = "PAE_NECE_YA_IMPORTADA", nullable = false, length = 2)
  private String necesidadesImportadas;
  @OneToMany(mappedBy = "pae", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
  private List<PAENovedad> novedades;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PAE_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "PAE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PAE_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PAE_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "PAE_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "PAE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "PAE_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "PAE_IP_MOD", length = 100)
  private String modIP;

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
   * @return the vigencia
   */
  public String getVigencia() {
    return vigencia;
  }

  /**
   * @param vigencia the vigencia to set
   */
  public void setVigencia(String vigencia) {
    this.vigencia = vigencia;
  }

  /**
   * @return the estado
   */
  public String getEstado() {
    return estado;
  }

  /**
   * @param estado the estado to set
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * @return the necesidadesImportadas
   */
  public String getNecesidadesImportadas() {
    return necesidadesImportadas;
  }

  /**
   * @param necesidadesImportadas the necesidadesImportadas to set
   */
  public void setNecesidadesImportadas(String necesidadesImportadas) {
    this.necesidadesImportadas = necesidadesImportadas;
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
  @XmlTransient
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
  @XmlTransient
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
  @XmlTransient
  public String getModIP() {
    return modIP;
  }

  /**
   * @param modIP the modIP to set
   */
  public void setModIP(String modIP) {
    this.modIP = modIP;
  }

  /**
   * @return the novedades
   */
  @XmlTransient
  public List<PAENovedad> getNovedades() {
    return novedades;
  }

  /**
   * @param novedades the novedades to set
   */
  public void setNovedades(List<PAENovedad> novedades) {
    this.novedades = novedades;
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
    if (!(object instanceof PAE)) {
      return false;
    }
    PAE other = (PAE) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduPae[ paePae=" + getId() + " ]";
  }
}
