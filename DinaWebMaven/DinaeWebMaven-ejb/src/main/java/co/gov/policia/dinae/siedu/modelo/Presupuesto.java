/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "SIEDU_PRESUPUESTO", uniqueConstraints = {
  @UniqueConstraint(columnNames = {"PPTO_CAPA", "PPTO_NRO_CONTRA"})})
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Presupuesto.FIND_ALL, query = "SELECT s FROM Presupuesto s"),
  @NamedQuery(name = Presupuesto.FIND_BY_CAPACITACION, query = "SELECT s FROM Presupuesto s WHERE s.capacitacion.id = :capacitacion")
})
@XmlRootElement
public class Presupuesto implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Presupuesto.findAll";
  public static final String FIND_BY_CAPACITACION = "Presupuesto.findByCapacitacion";
  @Id
  @Column(name = "PPTO_PPTO")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PRESUPUESTO_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_PRESUPUESTO_SEQ_GEN", sequenceName = "SIEDU_PRESUPUESTO_SEQ", allocationSize = 1)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "PPTO_NRO_CONTRA", nullable = false, length = 200)
  private String nroContrato;
  @Basic(optional = false)
  @NotNull
  @Column(name = "PPTO_VLR_CONTRA", nullable = false, precision = 12, scale = 2)
  private BigDecimal vlrContrato;
  @Column(name = "PPTO_FECHA_CONTRA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaContrato;
  @JoinColumn(name = "PPTO_DOM_RECU", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio recurso;
  @Size(max = 10)
  @Column(name = "PPTO_ORIGEN", length = 10)
  private String origen;
  @JoinColumn(name = "PPTO_CAPA", referencedColumnName = "CAPA_CAPA")
  @ManyToOne(optional = false)
  private Capacitacion capacitacion;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PPTO_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "PPTO_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PPTO_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PPTO_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "PPTO_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "PPTO_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "PPTO_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "PPTO_IP_MOD", length = 100)
  private String modIP;

  public Presupuesto() {
  }

  public Presupuesto(Long id) {
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
   * @return the nroContrato
   */
  public String getNroContrato() {
    return nroContrato;
  }

  /**
   * @param nroContrato the nroContrato to set
   */
  public void setNroContrato(String nroContrato) {
    this.nroContrato = nroContrato;
  }

  /**
   * @return the vlrContrato
   */
  public BigDecimal getVlrContrato() {
    return vlrContrato;
  }

  /**
   * @param vlrContrato the vlrContrato to set
   */
  public void setVlrContrato(BigDecimal vlrContrato) {
    this.vlrContrato = vlrContrato;
  }

  /**
   * @return the fechaContrato
   */
  public Date getFechaContrato() {
    return fechaContrato;
  }

  /**
   * @param fechaContrato the fechaContrato to set
   */
  public void setFechaContrato(Date fechaContrato) {
    this.fechaContrato = fechaContrato;
  }

  /**
   * @return the recurso
   */
  public Dominio getRecurso() {
    return recurso;
  }

  /**
   * @param recurso the recurso to set
   */
  public void setRecurso(Dominio recurso) {
    this.recurso = recurso;
  }

  /**
   * @return the origen
   */
  public String getOrigen() {
    return origen;
  }

  /**
   * @param origen the origen to set
   */
  public void setOrigen(String origen) {
    this.origen = origen;
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
    int hash = 5;
    hash = 41 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Presupuesto other = (Presupuesto) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Presupuesto{" + "id=" + id + '}';
  }
}
