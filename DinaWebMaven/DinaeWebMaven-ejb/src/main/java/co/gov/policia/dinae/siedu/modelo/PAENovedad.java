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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_NOVEDAD_PAE")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = PAENovedad.FIND_ALL, query = "SELECT n FROM PAENovedad n"),
  @NamedQuery(name = PAENovedad.FIND_BY_PAE, query = "SELECT n FROM PAENovedad n WHERE n.pae.id = :pae ORDER BY n.id DESC"),
  @NamedQuery(name = PAENovedad.FIND_BY_ESTADO_PAE, query = "SELECT DISTINCT n FROM PAENovedad n WHERE n.pae.estado = (:estado) ORDER BY n.id ASC")
})
@XmlRootElement
public class PAENovedad implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "PAENovedad.findAll";
  public static final String FIND_BY_PAE = "PAENovedad.findByPAE";
  public static final String FIND_BY_ESTADO_PAE = "PAENovedad.findByEstadoPAE";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "NOVE_NOVE", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_NOVEDAD_PAE_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_NOVEDAD_PAE_SEQ_GEN", sequenceName = "SIEDU_NOVEDAD_PAE_SEQ", allocationSize = 1)
  private Long id;
  @JoinColumn(name = "NOVE_PAE", referencedColumnName = "PAE_PAE")
  @ManyToOne(optional = false)
  private PAE pae;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NOVE_FECHA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;
  @JoinColumn(name = "NOVE_TPO_DOC", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio tipoDocumento;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NOVE_NRO_DOC", nullable = false, length = 30)
  private String numeroDocumento;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NOVE_FECHA_DOC", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date fechaDocumento;
  @Size(max = 1000)
  @Column(name = "NOVE_OBSERVA", length = 1000)
  private String observacion;
  @JoinColumn(name = "NOVE_ANEXO_PDF", referencedColumnName = "ARCH_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Archivo documento;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NOVE_PROCEDI", nullable = false, length = 30)
  private String procedimiento;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NOVE_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NOVE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOVE_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOVE_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "NOVE_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "NOVE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "NOVE_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "NOVE_IP_MOD", length = 100)
  private String modIP;

  public PAENovedad() {
  }

  public PAENovedad(Long id) {
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
   * @return the fecha
   */
  @XmlTransient
  public Date getFecha() {
    return fecha;
  }

  /**
   * @param fecha the fecha to set
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  /**
   * @return the procedimiento
   */
  @XmlTransient
  public String getProcedimiento() {
    return procedimiento;
  }

  /**
   * @param procedimiento the procedimiento to set
   */
  public void setProcedimiento(String procedimiento) {
    this.procedimiento = procedimiento;
  }

  /**
   * @return the tipoDocumento
   */
  @XmlTransient
  public Dominio getTipoDocumento() {
    return tipoDocumento;
  }

  /**
   * @param tipoDocumento the tipoDocumento to set
   */
  public void setTipoDocumento(Dominio tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  /**
   * @return the numeroDocumento
   */
  @XmlTransient
  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  /**
   * @param numeroDocumento the numeroDocumento to set
   */
  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  /**
   * @return the fechaDocumento
   */
  @XmlTransient
  public Date getFechaDocumento() {
    return fechaDocumento;
  }

  /**
   * @param fechaDocumento the fechaDocumento to set
   */
  public void setFechaDocumento(Date fechaDocumento) {
    this.fechaDocumento = fechaDocumento;
  }

  /**
   * @return the observacion
   */
  @XmlTransient
  public String getObservacion() {
    return observacion;
  }

  /**
   * @param observacion the observacion to set
   */
  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  /**
   * @return the documento
   */
  @XmlTransient
  public Archivo getDocumento() {
    return documento;
  }

  /**
   * @param documento the documento to set
   */
  public void setDocumento(Archivo documento) {
    this.documento = documento;
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
    if (!(object instanceof PAENovedad)) {
      return false;
    }
    PAENovedad other = (PAENovedad) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduNovedadPae[ noveNove=" + getId() + " ]";
  }
}
