package co.gov.policia.dinae.siedu.modelo;

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
@Table(name = "SIEDU_OBSERVACION_COMPROMISO")
@NamedQueries({
  @NamedQuery(name = "SieduObservacionCompromiso.findAll", query = "SELECT s FROM SieduObservacionCompromiso s")})
@Cacheable(false)
@XmlRootElement
public class SieduObservacionCompromiso implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduObservacionCompromisoPK sieduObservacionCompromisoPK;
  
  @JoinColumns({
    @JoinColumn(name = "OBCO_INVE", referencedColumnName = "COMP_INVE", updatable = false, insertable = false),
    @JoinColumn(name = "OBCO_COMP", referencedColumnName = "COMP_COMP", updatable = false, insertable = false)
  })
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private SieduCompromiso compromiso;
  
  @Basic(optional = false)
  @Column(name = "OBCO_FECHA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha; 
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 500)
  @Column(name = "OBCO_OBSERVACION", nullable = false, length = 500)
  private String observacion;  
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "OBCO_USUARIO_OBSERVA", nullable = false, length = 100)
  private String usuarioObserva;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "OBCO_USU_CREA", nullable = false, length = 30)
  private String obcoUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "OBCO_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date obcoFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "OBCO_MAQUINA_CREA", nullable = false, length = 100)
  private String obcoMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "OBCO_IP_CREA", nullable = false, length = 100)
  private String obcoIpCrea;
  
  @Size(max = 30)
  @Column(name = "OBCO_USU_MOD", length = 30)
  private String obcoUsuMod;
  
  @Column(name = "OBCO_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date obcoFechaMod;
  
  @Size(max = 100)
  @Column(name = "OBCO_MAQUINA_MOD", length = 100)
  private String obcoMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "OBCO_IP_MOD", length = 100)
  private String obcoIpMod;

  public SieduObservacionCompromiso() {
  }

  public SieduObservacionCompromiso(SieduObservacionCompromisoPK sieduObservacionCompromisoPK, SieduCompromiso compromiso, Date fecha, String observacion, String usuarioObserva, String obcoUsuCrea, Date obcoFechaCrea, String obcoMaquinaCrea, String obcoIpCrea, String obcoUsuMod, Date obcoFechaMod, String obcoMaquinaMod, String obcoIpMod) {
    this.sieduObservacionCompromisoPK = sieduObservacionCompromisoPK;
    this.compromiso = compromiso;
    this.fecha = fecha;
    this.observacion = observacion;
    this.usuarioObserva = usuarioObserva;
    this.obcoUsuCrea = obcoUsuCrea;
    this.obcoFechaCrea = obcoFechaCrea;
    this.obcoMaquinaCrea = obcoMaquinaCrea;
    this.obcoIpCrea = obcoIpCrea;
    this.obcoUsuMod = obcoUsuMod;
    this.obcoFechaMod = obcoFechaMod;
    this.obcoMaquinaMod = obcoMaquinaMod;
    this.obcoIpMod = obcoIpMod;
  }

  @XmlTransient
  public SieduObservacionCompromisoPK getSieduObservacionCompromisoPK() {
    return sieduObservacionCompromisoPK;
  }

  public void setSieduObservacionCompromisoPK(SieduObservacionCompromisoPK sieduObservacionCompromisoPK) {
    this.sieduObservacionCompromisoPK = sieduObservacionCompromisoPK;
  }

  @XmlTransient
  public SieduCompromiso getCompromiso() {
    return compromiso;
  }

  public void setCompromiso(SieduCompromiso compromiso) {
    this.compromiso = compromiso;
  }

  @XmlTransient
  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  @XmlTransient
  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  @XmlTransient
  public String getUsuarioObserva() {
    return usuarioObserva;
  }

  public void setUsuarioObserva(String usuarioObserva) {
    this.usuarioObserva = usuarioObserva;
  }

  @XmlTransient
  public String getObcoUsuCrea() {
    return obcoUsuCrea;
  }

  public void setObcoUsuCrea(String obcoUsuCrea) {
    this.obcoUsuCrea = obcoUsuCrea;
  }

  @XmlTransient
  public Date getObcoFechaCrea() {
    return obcoFechaCrea;
  }

  public void setObcoFechaCrea(Date obcoFechaCrea) {
    this.obcoFechaCrea = obcoFechaCrea;
  }

  @XmlTransient
  public String getObcoMaquinaCrea() {
    return obcoMaquinaCrea;
  }

  public void setObcoMaquinaCrea(String obcoMaquinaCrea) {
    this.obcoMaquinaCrea = obcoMaquinaCrea;
  }

  @XmlTransient
  public String getObcoIpCrea() {
    return obcoIpCrea;
  }

  public void setObcoIpCrea(String obcoIpCrea) {
    this.obcoIpCrea = obcoIpCrea;
  }

  @XmlTransient
  public String getObcoUsuMod() {
    return obcoUsuMod;
  }

  public void setObcoUsuMod(String obcoUsuMod) {
    this.obcoUsuMod = obcoUsuMod;
  }

  @XmlTransient
  public Date getObcoFechaMod() {
    return obcoFechaMod;
  }

  public void setObcoFechaMod(Date obcoFechaMod) {
    this.obcoFechaMod = obcoFechaMod;
  }

  @XmlTransient
  public String getObcoMaquinaMod() {
    return obcoMaquinaMod;
  }

  public void setObcoMaquinaMod(String obcoMaquinaMod) {
    this.obcoMaquinaMod = obcoMaquinaMod;
  }

  @XmlTransient
  public String getObcoIpMod() {
    return obcoIpMod;
  }

  public void setObcoIpMod(String obcoIpMod) {
    this.obcoIpMod = obcoIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.sieduObservacionCompromisoPK);
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
    final SieduObservacionCompromiso other = (SieduObservacionCompromiso) obj;
    if (!Objects.equals(this.sieduObservacionCompromisoPK, other.sieduObservacionCompromisoPK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduObservacionCompromiso{" + "sieduObservacionCompromisoPK=" + sieduObservacionCompromisoPK + ", Compromiso=" + compromiso + ", fecha=" + fecha + ", observacion=" + observacion + ", usuarioObserva=" + usuarioObserva + ", obcoUsuCrea=" + obcoUsuCrea + ", obcoFechaCrea=" + obcoFechaCrea + ", obcoMaquinaCrea=" + obcoMaquinaCrea + ", obcoIpCrea=" + obcoIpCrea + ", obcoUsuMod=" + obcoUsuMod + ", obcoFechaMod=" + obcoFechaMod + ", obcoMaquinaMod=" + obcoMaquinaMod + ", obcoIpMod=" + obcoIpMod + '}';
  }

  
  
  
}
