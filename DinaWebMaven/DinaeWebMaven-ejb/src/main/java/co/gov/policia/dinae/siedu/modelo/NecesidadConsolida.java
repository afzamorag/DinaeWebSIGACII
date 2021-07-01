/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "SIEDU_CONSOLIDA_PAE")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = NecesidadConsolida.FIND_BY_CAPACITACION, query = "SELECT s FROM NecesidadConsolida s WHERE s.capacitacion.id = :capacitacion"),
  @NamedQuery(name = NecesidadConsolida.FIND_BY_FORMACION, query = "SELECT s FROM NecesidadConsolida s WHERE s.formacion.id = :formacion")
})
@XmlRootElement
public class NecesidadConsolida implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_CAPACITACION = "NecesidadConsolida.findByCapacitacion";
  public static final String FIND_BY_FORMACION = "NecesidadConsolida.findByFormacion";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "CPAE_CPAE", nullable = false)
  private Integer pae;
  @JoinColumn(name = "CPAE_CAPA", referencedColumnName = "CAPA_CAPA")
  @ManyToOne(fetch = FetchType.LAZY)
  private Capacitacion capacitacion;
  @JoinColumn(name = "CPAE_FORM", referencedColumnName = "FORM_FORM")
  @ManyToOne(fetch = FetchType.LAZY)
  private Formacion formacion;
  @JoinColumn(name = "CPAE_NECE", referencedColumnName = "NECE_NECE", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Necesidad necesidad;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "CPAE_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CPAE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CPAE_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CPAE_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "CPAE_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "CPAE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "CPAE_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "CPAE_IP_MOD", length = 100)
  private String modIP;

  public NecesidadConsolida() {
  }

  /**
   * @return the pae
   */
  public Integer getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(Integer pae) {
    this.pae = pae;
  }

  /**
   * @return the capacitacion
   */
  public Capacitacion getCapacitacion() {
    return capacitacion;
  }

  /**
   * @param capacitacion the capacitacion to set
   */
  public void setCapacitacion(Capacitacion capacitacion) {
    this.capacitacion = capacitacion;
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
   * @return the necesidad
   */
  public Necesidad getNecesidad() {
    return necesidad;
  }

  /**
   * @param necesidad the necesidad to set
   */
  public void setNecesidad(Necesidad necesidad) {
    this.necesidad = necesidad;
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
    hash += (getPae() != null ? getPae().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof NecesidadConsolida)) {
      return false;
    }
    NecesidadConsolida other = (NecesidadConsolida) object;
    if ((this.getPae() == null && other.getPae() != null) || (this.getPae() != null && !this.pae.equals(other.pae))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduConsolidaPae[ cpaeCpae=" + getPae() + " ]";
  }

}
