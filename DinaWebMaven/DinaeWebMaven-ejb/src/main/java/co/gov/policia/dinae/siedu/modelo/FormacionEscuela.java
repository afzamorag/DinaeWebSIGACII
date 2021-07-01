/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "SIEDU_PAE_FORMA_ESCUELA")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = FormacionEscuela.FIND_ALL, query = "SELECT s FROM FormacionEscuela s"),
  @NamedQuery(name = FormacionEscuela.FIND_BY_FORMACION, query = "SELECT s FROM FormacionEscuela s WHERE s.id.formacion = :formacion")
})
@XmlRootElement
public class FormacionEscuela implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "FormacionEscuela.findAll";
  public static final String FIND_BY_FORMACION = "FormacionEscuela.findByFormacion";
  @EmbeddedId
  private FormacionEscuelaPK id;
  @JoinColumn(name = "FRME_FORM", referencedColumnName = "FORM_FORM", nullable = false, insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Formacion formacion;
  @JoinColumns({
    @JoinColumn(name = "FRME_UDE_ESCU", referencedColumnName = "CONSECUTIVO", nullable = false, insertable = false, updatable = false),
    @JoinColumn(name = "FRME_UDE_FUERZA", referencedColumnName = "FUERZA", nullable = false, insertable = false, updatable = false)
  })
  @ManyToOne(optional = false)
  private UnidadDependencia escuela;
  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "formacionEscuela", fetch = FetchType.LAZY)
  private List<FormacionEscuelaCohorte> cohortes;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "FRMS_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FRMS_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FRMS_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FRMS_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "FRMS_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "FRMS_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "FRMS_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "FRMS_IP_MOD", length = 100)
  private String modIP;

  public FormacionEscuela() {
  }

  public FormacionEscuela(FormacionEscuelaPK sieduPaeFormaEscuelaPK) {
    this.id = sieduPaeFormaEscuelaPK;
  }

  public FormacionEscuela(Long frmeForm, Long frmeUdeFuerza, Long frmeUdeEscu) {
    this.id = new FormacionEscuelaPK(frmeForm, frmeUdeFuerza, frmeUdeEscu);
  }

  public FormacionEscuelaPK getId() {
    return id;
  }

  public void setId(FormacionEscuelaPK id) {
    this.id = id;
  }

  /**
   * @return the formacion
   */
  public Formacion getFormacion() {
    return formacion;
  }

  /**
   * @param formacion the formacion to set
   */
  public void setFormacion(Formacion formacion) {
    this.formacion = formacion;
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
   * @return the cohortes
   */
  public List<FormacionEscuelaCohorte> getCohortes() {
    return cohortes;
  }

  /**
   * @param cohortes the cohortes to set
   */
  public void setCohortes(List<FormacionEscuelaCohorte> cohortes) {
    this.cohortes = cohortes;
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
    if (!(object instanceof FormacionEscuela)) {
      return false;
    }
    FormacionEscuela other = (FormacionEscuela) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduPaeFormaEscuela[ sieduPaeFormaEscuelaPK=" + getId() + " ]";
  }
}
