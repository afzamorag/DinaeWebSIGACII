package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_PROPUESTA_ASIGNADA")
@NamedQueries({
  @NamedQuery(name = "SieduPropuestaAsignada.findAll", query = "SELECT s FROM SieduPropuestaAsignada s"),
  @NamedQuery(name = "SieduPropuestaAsignada.findByPropuestaNecesidad", query = "SELECT s FROM SieduPropuestaAsignada s WHERE s.propuestaNecesidad =:propuestaNecesidad"),
  @NamedQuery(name = "SieduPropuestaAsignada.findByVigencia", query = "SELECT s FROM SieduPropuestaAsignada s WHERE s.sieduPropuestaAsignadaPK.vigencia =:vigencia")
})
@Cacheable(false)
@XmlRootElement
public class SieduPropuestaAsignada implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduPropuestaAsignadaPK sieduPropuestaAsignadaPK;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PPA_USU_CREA", nullable = false, length = 30)
  private String ppaUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PPA_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date ppaFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PPA_MAQUINA_CREA", nullable = false, length = 100)
  private String ppaMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PPA_IP_CREA", nullable = false, length = 100)
  private String ppaIpCrea;
  
  @Size(max = 30)
  @Column(name = "PPA_USU_MOD", length = 30)
  private String ppaUsuMod;
  
  @Column(name = "PPA_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date ppaFechaMod;
  
  @Size(max = 100)
  @Column(name = "PPA_MAQUINA_MOD", length = 100)
  private String ppaMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "PPA_IP_MOD", length = 100)
  private String ppaIpMod;
  
  @JoinColumn(name = "PPA_PRIN_DOM_MODALIDAD", referencedColumnName = "ID_DOMINIO", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Dominio modalidad;

  @JoinColumns({
    @JoinColumn(name = "PPA_ID_UNIDAD_POLICIAL", referencedColumnName = "PRIN_ID_UNIDAD_POLICIAL", insertable = false, updatable = false),
    @JoinColumn(name = "PPA_PRIN_VIGENCIA", referencedColumnName = "PRIN_VIGENCIA", insertable = false, updatable = false),
    @JoinColumn(name = "PPA_PRIN_DOM_MODALIDAD", referencedColumnName = "PRIN_DOM_MODALIDAD", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduProgramaInvestigacion programaInvestigacion;

  @JoinColumns({
    @JoinColumn(name = "PPA_ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private PropuestaNecesidad propuestaNecesidad;

  @JoinColumns({
    @JoinColumn(name = "PPA_ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadPolicial unidad;

  public SieduPropuestaAsignada() {
  }

  public SieduPropuestaAsignada(SieduPropuestaAsignadaPK sieduPropuestaAsignadaPK, String ppaUsuCrea, Date ppaFechaCrea, String ppaMaquinaCrea, String ppaIpCrea, String ppaUsuMod, Date ppaFechaMod, String ppaMaquinaMod, String ppaIpMod, Dominio modalidad) {
    this.sieduPropuestaAsignadaPK = sieduPropuestaAsignadaPK;
    this.ppaUsuCrea = ppaUsuCrea;
    this.ppaFechaCrea = ppaFechaCrea;
    this.ppaMaquinaCrea = ppaMaquinaCrea;
    this.ppaIpCrea = ppaIpCrea;
    this.ppaUsuMod = ppaUsuMod;
    this.ppaFechaMod = ppaFechaMod;
    this.ppaMaquinaMod = ppaMaquinaMod;
    this.ppaIpMod = ppaIpMod;
    this.modalidad = modalidad;
  }

  @XmlTransient
  public SieduPropuestaAsignadaPK getSieduPropuestaAsignadaPK() {
    return sieduPropuestaAsignadaPK;
  }

  public void setSieduPropuestaAsignadaPK(SieduPropuestaAsignadaPK sieduPropuestaAsignadaPK) {
    this.sieduPropuestaAsignadaPK = sieduPropuestaAsignadaPK;
  }

  @XmlTransient
  public String getPpaUsuCrea() {
    return ppaUsuCrea;
  }

  public void setPpaUsuCrea(String ppaUsuCrea) {
    this.ppaUsuCrea = ppaUsuCrea;
  }

  @XmlTransient
  public Date getPpaFechaCrea() {
    return ppaFechaCrea;
  }

  public void setPpaFechaCrea(Date ppaFechaCrea) {
    this.ppaFechaCrea = ppaFechaCrea;
  }

  @XmlTransient
  public String getPpaMaquinaCrea() {
    return ppaMaquinaCrea;
  }

  public void setPpaMaquinaCrea(String ppaMaquinaCrea) {
    this.ppaMaquinaCrea = ppaMaquinaCrea;
  }

  @XmlTransient
  public String getPpaIpCrea() {
    return ppaIpCrea;
  }

  public void setPpaIpCrea(String ppaIpCrea) {
    this.ppaIpCrea = ppaIpCrea;
  }

  @XmlTransient
  public String getPpaUsuMod() {
    return ppaUsuMod;
  }

  public void setPpaUsuMod(String ppaUsuMod) {
    this.ppaUsuMod = ppaUsuMod;
  }

  @XmlTransient
  public Date getPpaFechaMod() {
    return ppaFechaMod;
  }

  public void setPpaFechaMod(Date ppaFechaMod) {
    this.ppaFechaMod = ppaFechaMod;
  }

  @XmlTransient
  public String getPpaMaquinaMod() {
    return ppaMaquinaMod;
  }

  public void setPpaMaquinaMod(String ppaMaquinaMod) {
    this.ppaMaquinaMod = ppaMaquinaMod;
  }

  @XmlTransient
  public String getPpaIpMod() {
    return ppaIpMod;
  }

  public void setPpaIpMod(String ppaIpMod) {
    this.ppaIpMod = ppaIpMod;
  }

  @XmlTransient
  public Dominio getModalidad() {
    return modalidad;
  }

  public void setModalidad(Dominio modalidad) {
    this.modalidad = modalidad;
  }

  @XmlTransient
  public SieduProgramaInvestigacion getProgramaInvestigacion() {
    return programaInvestigacion;
  }

  public void setProgramaInvestigacion(SieduProgramaInvestigacion programaInvestigacion) {
    this.programaInvestigacion = programaInvestigacion;
  }

  @XmlTransient
  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }

  @XmlTransient
  public UnidadPolicial getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadPolicial unidad) {
    this.unidad = unidad;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.sieduPropuestaAsignadaPK);
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
    final SieduPropuestaAsignada other = (SieduPropuestaAsignada) obj;
    if (!Objects.equals(this.sieduPropuestaAsignadaPK, other.sieduPropuestaAsignadaPK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPropuestaAsignada{" + "sieduPropuestaAsignadaPK=" + sieduPropuestaAsignadaPK + ", ppaUsuCrea=" + ppaUsuCrea + ", ppaFechaCrea=" + ppaFechaCrea + ", ppaMaquinaCrea=" + ppaMaquinaCrea + ", ppaIpCrea=" + ppaIpCrea + ", ppaUsuMod=" + ppaUsuMod + ", ppaFechaMod=" + ppaFechaMod + ", ppaMaquinaMod=" + ppaMaquinaMod + ", ppaIpMod=" + ppaIpMod + ", modalidad=" + modalidad + ", programaInvestigacion=" + programaInvestigacion + ", propuestaNecesidad=" + propuestaNecesidad + ", unidad=" + unidad + '}';
  }
  
}
