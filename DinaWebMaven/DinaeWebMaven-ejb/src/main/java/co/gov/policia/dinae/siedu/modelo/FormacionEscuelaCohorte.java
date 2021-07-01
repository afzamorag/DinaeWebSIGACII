/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(
        name = "SIEDU_FORMA_ESCUELA_CORTE",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = {"FREH_FORM", "FREH_FUERZA", "FREH_ESCU", "FREH_COHO_CONSECU"})
        })
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = "SieduFormaEscuelaCorte.findAll", query = "SELECT s FROM FormacionEscuelaCohorte s")
})
@XmlRootElement
public class FormacionEscuelaCohorte implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  private FormacionEscuelaCohortePK pk;
  @Column(name = "FREH_COHO_CONSECU")
  private Integer consecutivo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_FECHA_INI", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicial;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_FECHA_FIN", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFinal;
  @Column(name = "FREH_NRO_ESTU")
  private Integer numeroEstudiantes;
  @JoinColumn(name = "FREH_DOM_MODAL", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio modalidad;
  @JoinColumns({
    @JoinColumn(name = "FREH_FORM", referencedColumnName = "FRME_FORM", nullable = false, insertable = false, updatable = false),
    @JoinColumn(name = "FREH_FUERZA", referencedColumnName = "FRME_UDE_FUERZA", nullable = false, insertable = false, updatable = false),
    @JoinColumn(name = "FREH_ESCU", referencedColumnName = "FRME_UDE_ESCU", nullable = false, insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private FormacionEscuela formacionEscuela;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "FREH_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FREH_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FREH_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FREH_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "FREH_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "FREH_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "FREH_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "FREH_IP_MOD", length = 100)
  private String modIP;

  public FormacionEscuelaCohorte() {
  }

  public FormacionEscuelaCohorte(FormacionEscuelaCohortePK sieduFormaEscuelaCortePK) {
    this.pk = sieduFormaEscuelaCortePK;
  }

  public FormacionEscuelaCohorte(Long frehCoho,Long frehForm, Long frehFuerza, Long frehEscu) {
    this.pk = new FormacionEscuelaCohortePK(frehCoho, frehForm, frehFuerza, frehEscu);
  }

  /**
   * @return the pk
   */
  public FormacionEscuelaCohortePK getPk() {
    return pk;
  }

  /**
   * @param pk the pk to set
   */
  public void setPk(FormacionEscuelaCohortePK pk) {
    this.pk = pk;
  }

  /**
   * @return the consecutivo
   */
  public Integer getConsecutivo() {
    return consecutivo;
  }

  /**
   * @param consecutivo the consecutivo to set
   */
  public void setConsecutivo(Integer consecutivo) {
    this.consecutivo = consecutivo;
  }

  /**
   * @return the fechaInicial
   */
  public Date getFechaInicial() {
    return fechaInicial;
  }

  /**
   * @param fechaInicial the fechaInicial to set
   */
  public void setFechaInicial(Date fechaInicial) {
    this.fechaInicial = fechaInicial;
  }

  /**
   * @return the fechaFinal
   */
  public Date getFechaFinal() {
    return fechaFinal;
  }

  /**
   * @param fechaFinal the fechaFinal to set
   */
  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  /**
   * @return the numeroEstudiantes
   */
  public Integer getNumeroEstudiantes() {
    return numeroEstudiantes;
  }

  /**
   * @param numeroEstudiantes the numeroEstudiantes to set
   */
  public void setNumeroEstudiantes(Integer numeroEstudiantes) {
    this.numeroEstudiantes = numeroEstudiantes;
  }

  /**
   * @return the modalidad
   */
  public Dominio getModalidad() {
    return modalidad;
  }

  /**
   * @param modalidad the modalidad to set
   */
  public void setModalidad(Dominio modalidad) {
    this.modalidad = modalidad;
  }

  /**
   * @return the formacionEscuela
   */
  public FormacionEscuela getFormacionEscuela() {
    return formacionEscuela;
  }

  /**
   * @param formacionEscuela the formacionEscuela to set
   */
  public void setFormacionEscuela(FormacionEscuela formacionEscuela) {
    this.formacionEscuela = formacionEscuela;
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
    hash += (getPk() != null ? getPk().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FormacionEscuelaCohorte)) {
      return false;
    }
    FormacionEscuelaCohorte other = (FormacionEscuelaCohorte) object;
    if ((this.getPk() == null && other.getPk() != null) || (this.getPk() != null && !this.pk.equals(other.pk))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduFormaEscuelaCorte[ sieduFormaEscuelaCortePK=" + getPk() + " ]";
  }

}
