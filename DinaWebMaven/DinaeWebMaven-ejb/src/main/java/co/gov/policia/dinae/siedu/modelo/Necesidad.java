/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.UnidadDependencia;
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
import javax.xml.bind.annotation.XmlAttribute;
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
        name = "SIEDU_NECESIDAD",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = {"NECE_UDE_REGION", "NECE_UDE_UFISI", "NECE_UDE_UDEPE", "NECE_ID_CARRERA", "NECE_UDE_FUERZA"})
        })
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Necesidad.FIND_ALL, query = "SELECT s FROM Necesidad s"),
  @NamedQuery(name = Necesidad.FIND_BY_PAE, query = "SELECT s FROM Necesidad s WHERE s.pae.id = :pae")
})
@XmlRootElement
public class Necesidad implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Necesidad.findAll";
  public static final String FIND_BY_PAE = "Necesidad.findByPAE";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "NECE_NECE", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_NECESIDAD_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_NECESIDAD_SEQ_GEN", sequenceName = "SIEDU_NECESIDAD_SEQ", allocationSize = 1)
  private Long id;
  @JoinColumn(name = "NECE_PAE", referencedColumnName = "PAE_PAE")
  @ManyToOne(optional = false)
  private PAE pae;
  @JoinColumn(name = "NECE_NOVE", referencedColumnName = "NOVE_NOVE")
  @ManyToOne(optional = false)
  private PAENovedad novedad;
  @JoinColumns({
    @JoinColumn(name = "NECE_UDE_REGION", referencedColumnName = "CODIGO")
  })
  @ManyToOne(optional = false)
  private Regionales region;
  @JoinColumns({
    @JoinColumn(name = "NECE_UDE_UFISI", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "NECE_UDE_FUERZA_UFISI", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private UnidadDependencia unidadFisica;
  @JoinColumns({
    @JoinColumn(name = "NECE_UDE_UDEPE", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "NECE_UDE_FUERZA_UDEPE", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private UnidadDependencia unidadDependencia;
  @JoinColumns({
    @JoinColumn(name = "NECE_ID_CARRERA", referencedColumnName = "ID_CARRERA"),
    @JoinColumn(name = "NECE_FUERZA_CARRERA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private Carreras carrera;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NECE_NRO", nullable = false)
  private Integer numero;
  @JoinColumn(name = "NECE_DOM_PROCE", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio proceso;
  @JoinColumn(name = "NECE_DOM_ESTRA", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio estrategia;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "NECE_ORIGEN", nullable = false, length = 10)
  private String origen;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NECE_ESTADO", nullable = false, length = 30)
  @XmlAttribute
  private String estado;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "necesidad", fetch = FetchType.LAZY)
  private List<NecesidadConsolida> consolidado;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "NECE_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "NECE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NECE_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NECE_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "NECE_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "NECE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "NECE_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "NECE_IP_MOD", length = 100)
  private String modIP;

  public Necesidad() {
  }

  public Necesidad(Long neceNece) {
    this.id = neceNece;
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
   * @return the novedad
   */
  public PAENovedad getNovedad() {
    return novedad;
  }

  /**
   * @param novedad the novedad to set
   */
  public void setNovedad(PAENovedad novedad) {
    this.novedad = novedad;
  }

  /**
   * @return the region
   */
  public Regionales getRegion() {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(Regionales region) {
    this.region = region;
  }

  /**
   * @return the unidadFisica
   */
  public UnidadDependencia getUnidadFisica() {
    return unidadFisica;
  }

  /**
   * @param unidadFisica the unidadFisica to set
   */
  public void setUnidadFisica(UnidadDependencia unidadFisica) {
    this.unidadFisica = unidadFisica;
  }

  /**
   * @return the unidadDependencia
   */
  public UnidadDependencia getUnidadDependencia() {
    return unidadDependencia;
  }

  /**
   * @param unidadDependencia the unidadDependencia to set
   */
  public void setUnidadDependencia(UnidadDependencia unidadDependencia) {
    this.unidadDependencia = unidadDependencia;
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
   * @return the numero
   */
  public Integer getNumero() {
    return numero;
  }

  /**
   * @param numero the numero to set
   */
  public void setNumero(Integer numero) {
    this.numero = numero;
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
   * @return the consolidado
   */
  public List<NecesidadConsolida> getConsolidado() {
    return consolidado;
  }

  /**
   * @param consolidado the consolidado to set
   */
  public void setConsolidado(List<NecesidadConsolida> consolidado) {
    this.consolidado = consolidado;
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
    if (!(object instanceof Necesidad)) {
      return false;
    }
    Necesidad other = (Necesidad) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.Necesidad[ neceNece=" + getId() + " ]";
  }
}
