/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.Carreras;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(
        name = "SIEDU_PAE_FORMACION",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = {"FORM_PAE", "FORM_FUERZA", "FORM_ID_CARRERA"})
        })
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Formacion.FIND_ALL, query = "SELECT s FROM Formacion s"),
  @NamedQuery(name = Formacion.FIND_FULL_BY_ID, query = "SELECT s FROM Formacion s LEFT JOIN FETCH s.escuelas WHERE s.id = :id")
})
@XmlRootElement
public class Formacion implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Formacion.findAll";
  public static final String FIND_FULL_BY_ID = "Formacion.findFullByID";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "FORM_FORM", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PAE_FORMACION_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_PAE_FORMACION_SEQ_GEN", sequenceName = "SIEDU_FORMACION_SEQ", allocationSize = 1)
  private Long id;
  @JoinColumn(name = "FORM_PAE", referencedColumnName = "PAE_PAE")
  @ManyToOne(optional = false)
  private PAE pae;
  @JoinColumn(name = "FORM_DOM_PROCE", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = true)
  private Dominio proceso;
  @JoinColumn(name = "FORM_DOM_ESTRA", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = true)
  private Dominio estrategia;
  @JoinColumns({
    @JoinColumn(name = "FORM_ID_CARRERA", referencedColumnName = "ID_CARRERA"),
    @JoinColumn(name = "FORM_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private Carreras carrera;
  @Column(name = "FORM_NECESI")
  private Integer necesidad;
  @Column(name = "FORM_ESTADO")
  private String estado;
  @OneToMany(mappedBy = "formacion")
  private List<FormacionEscuela> escuelas;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "FORM_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FORM_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FORM_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FORM_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "FORM_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "FORM_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "FORM_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "FORM_IP_MOD", length = 100)
  private String modIP;

  public Formacion() {
  }

  public Formacion(Long formForm) {
    this.id = formForm;
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
   * @return the proceso
   */
  public Dominio getProceso() {
    return proceso;
  }

  /**
   * @param proceso the proceso to set
   */
  public void setProceso(Dominio proceso) {
    this.proceso = proceso;
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
   * @return the carrera
   */
  public Carreras getCarrera() {
    return carrera;
  }

  /**
   * @param carrera the carrera to set
   */
  public void setCarrera(Carreras carrera) {
    this.carrera = carrera;
  }

  /**
   * @return the necesidad
   */
  public Integer getNecesidad() {
    return necesidad;
  }

  /**
   * @param necesidad the necesidad to set
   */
  public void setNecesidad(Integer necesidad) {
    this.necesidad = necesidad;
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
   * @return the escuelas
   */
  @XmlTransient
  public List<FormacionEscuela> getEscuelas() {
    return escuelas;
  }

  /**
   * @param escuelas the escuelas to set
   */
  public void setEscuelas(List<FormacionEscuela> escuelas) {
    this.escuelas = escuelas;
  }

  /**
   * @return the creaUsuario
   */
  @XmlTransient
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
  @XmlTransient
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
  @XmlTransient
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
  @XmlTransient
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
  @XmlTransient
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Formacion)) {
      return false;
    }
    Formacion other = (Formacion) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.Formacion[ formForm=" + getId() + " ]";
  }

}
