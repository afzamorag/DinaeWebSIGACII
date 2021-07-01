package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_PROGRAMA_INVESTIGACION")
@NamedQueries({
  @NamedQuery(name = "SieduProgramaInvestigacion.findAll", query = "SELECT s FROM SieduProgramaInvestigacion s")})
@Cacheable(false)
@XmlRootElement
public class SieduProgramaInvestigacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduProgramaInvestigacionPK sieduProgramaInvestigacionPK;
  
  @JoinColumns({
    @JoinColumn(name = "PRIN_ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadPolicial unidad;
  
  @JoinColumn(name = "PRIN_DOM_MODALIDAD", referencedColumnName = "ID_DOMINIO", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Dominio modalidad;


  @Column(name = "PRIN_NRO", nullable = false)
  private Integer numeroInvestigaciones;


  @Column(name = "PRIN_NRO_BANCO", nullable = false)
  private Integer numeroInvestigacionesBanco;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PRIN_USU_CREA", nullable = false, length = 30)
  private String prinUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PRIN_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date prinFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PRIN_MAQUINA_CREA", nullable = false, length = 100)
  private String prinMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PRIN_IP_CREA", nullable = false, length = 100)
  private String prinIpCrea;
  
  @Size(max = 30)
  @Column(name = "PRIN_USU_MOD", length = 30)
  private String prinUsuMod;
  
  @Column(name = "PRIN_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date prinFechaMod;
  
  @Size(max = 100)
  @Column(name = "PRIN_MAQUINA_MOD", length = 100)
  private String prinMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "PRIN_IP_MOD", length = 100)
  private String prinIpMod;
  
  @Column(name = "PRIN_FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date prinFechaInicio;
  
  @Column(name = "PRIN_FECHA_FINAL")
  @Temporal(TemporalType.TIMESTAMP)
  private Date prinFechaFinal;
  
  @OneToMany(mappedBy="programaInvestigacion")
  private List<SieduPropuestaAsignada> propuestasAsignadas;
  
  public SieduProgramaInvestigacion() {
  }

  public SieduProgramaInvestigacion(SieduProgramaInvestigacionPK sieduProgramaInvestigacionPK, Integer numeroInvestigaciones, Integer numeroInvestigacionesBanco) {
    this.sieduProgramaInvestigacionPK = sieduProgramaInvestigacionPK;
    this.numeroInvestigaciones = numeroInvestigaciones;
    this.numeroInvestigacionesBanco = numeroInvestigacionesBanco;
  }

  public SieduProgramaInvestigacionPK getSieduProgramaInvestigacionPK() {
    return sieduProgramaInvestigacionPK;
  }

  public void setSieduProgramaInvestigacionPK(SieduProgramaInvestigacionPK sieduProgramaInvestigacionPK) {
    this.sieduProgramaInvestigacionPK = sieduProgramaInvestigacionPK;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.sieduProgramaInvestigacionPK);
    hash = 97 * hash + Objects.hashCode(this.numeroInvestigaciones);
    hash = 97 * hash + Objects.hashCode(this.numeroInvestigacionesBanco);
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
    final SieduProgramaInvestigacion other = (SieduProgramaInvestigacion) obj;
    if (!Objects.equals(this.sieduProgramaInvestigacionPK, other.sieduProgramaInvestigacionPK)) {
      return false;
    }
    if (!Objects.equals(this.numeroInvestigaciones, other.numeroInvestigaciones)) {
      return false;
    }
    if (!Objects.equals(this.numeroInvestigacionesBanco, other.numeroInvestigacionesBanco)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduProgramaInvestigacion{" + "sieduProgramaInvestigacionPK=" + sieduProgramaInvestigacionPK + ", numeroInvestigaciones=" + numeroInvestigaciones + ", numeroInvestigacionesBanco=" + numeroInvestigacionesBanco + '}';
  }

  public Integer getNumeroInvestigaciones() {
    return numeroInvestigaciones;
  }

  public void setNumeroInvestigaciones(Integer numeroInvestigaciones) {
    this.numeroInvestigaciones = numeroInvestigaciones;
  }

  public Integer getNumeroInvestigacionesBanco() {
    return numeroInvestigacionesBanco;
  }

  public void setNumeroInvestigacionesBanco(Integer numeroInvestigacionesBanco) {
    this.numeroInvestigacionesBanco = numeroInvestigacionesBanco;
  }

  public String getPrinUsuCrea() {
    return prinUsuCrea;
  }

  public void setPrinUsuCrea(String prinUsuCrea) {
    this.prinUsuCrea = prinUsuCrea;
  }

  public Date getPrinFechaCrea() {
    return prinFechaCrea;
  }

  public void setPrinFechaCrea(Date prinFechaCrea) {
    this.prinFechaCrea = prinFechaCrea;
  }

  public String getPrinMaquinaCrea() {
    return prinMaquinaCrea;
  }

  public void setPrinMaquinaCrea(String prinMaquinaCrea) {
    this.prinMaquinaCrea = prinMaquinaCrea;
  }

  public String getPrinIpCrea() {
    return prinIpCrea;
  }

  public void setPrinIpCrea(String prinIpCrea) {
    this.prinIpCrea = prinIpCrea;
  }

  public String getPrinUsuMod() {
    return prinUsuMod;
  }

  public void setPrinUsuMod(String prinUsuMod) {
    this.prinUsuMod = prinUsuMod;
  }

  public Date getPrinFechaMod() {
    return prinFechaMod;
  }

  public void setPrinFechaMod(Date prinFechaMod) {
    this.prinFechaMod = prinFechaMod;
  }

  public String getPrinMaquinaMod() {
    return prinMaquinaMod;
  }

  public void setPrinMaquinaMod(String prinMaquinaMod) {
    this.prinMaquinaMod = prinMaquinaMod;
  }

  public String getPrinIpMod() {
    return prinIpMod;
  }

  public void setPrinIpMod(String prinIpMod) {
    this.prinIpMod = prinIpMod;
  }

  public Dominio getModalidad() {
    return modalidad;
  }

  public void setModalidad(Dominio modalidad) {
    this.modalidad = modalidad;
  }

  public List<SieduPropuestaAsignada> getPropuestasAsignadas() {
    return propuestasAsignadas;
  }

  public void setPropuestasAsignadas(List<SieduPropuestaAsignada> propuestasAsignadas) {
    this.propuestasAsignadas = propuestasAsignadas;
  }

  public void addPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad, String ppaUsuCrea, Date ppaFechaCrea, String ppaIpCrea, String ppaMaquinaCrea, String ppaUsuMod, Date ppaFechaMod, String ppaIpMod, String ppaMaquinaMod) {
    
    SieduPropuestaAsignada spa = new SieduPropuestaAsignada();
    
    SieduPropuestaAsignadaPK spaPK = new SieduPropuestaAsignadaPK();
    spaPK.setUnidad(this.sieduProgramaInvestigacionPK.getUnidad());
    spaPK.setVigencia(this.sieduProgramaInvestigacionPK.getVigencia());
    spaPK.setIdModalidad(this.sieduProgramaInvestigacionPK.getIdModalidad());
    spaPK.setIdPropuestaNecesidad(propuestaNecesidad.getIdPropuestaNecesidad());
    
    spa.setSieduPropuestaAsignadaPK(spaPK);
    spa.setPpaFechaCrea(ppaFechaCrea);
    spa.setPpaFechaMod(ppaFechaMod);
    spa.setPpaIpCrea(ppaIpCrea);
    spa.setPpaIpMod(ppaIpMod);
    spa.setPpaMaquinaCrea(ppaMaquinaCrea);
    spa.setPpaMaquinaMod(ppaMaquinaMod);
    spa.setPpaUsuCrea(ppaUsuCrea);
    spa.setPpaUsuMod(ppaUsuMod);
    
    this.propuestasAsignadas.add(spa);
  }

  public UnidadPolicial getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadPolicial unidad) {
    this.unidad = unidad;
  } 

    public Date getPrinFechaInicio() {
        return prinFechaInicio;
    }

    public void setPrinFechaInicio(Date prinFechaInicio) {
        this.prinFechaInicio = prinFechaInicio;
    }

    public Date getPrinFechaFinal() {
        return prinFechaFinal;
    }

    public void setPrinFechaFinal(Date prinFechaFinal) {
        this.prinFechaFinal = prinFechaFinal;
    }
    
}
