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
@Table(name = "SIEDU_PROPINTELECTUAL_ENTIDAD")
@NamedQueries({
  @NamedQuery(name = "SieduPropIntelectualEntidad.findAll", query = "SELECT s FROM SieduPropIntelectualEntidad s")})
@Cacheable(false)
@XmlRootElement
public class SieduPropIntelectualEntidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduPropIntelectualEntidadPK sieduPropIntelectualEntidadPK;
  
  @JoinColumn(name = "PINE_ENTI", referencedColumnName = "ENTI_ENTI", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduEntidad entidad;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PINE_USU_CREA", nullable = false, length = 30)
  private String pineUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PINE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date pineFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PINE_MAQUINA_CREA", nullable = false, length = 100)
  private String pineMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PINE_IP_CREA", nullable = false, length = 100)
  private String pineIpCrea;
  
  @Size(max = 30)
  @Column(name = "PINE_USU_MOD", length = 30)
  private String pineUsuMod;
  
  @Column(name = "PINE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pineFechaMod;
  
  @Size(max = 100)
  @Column(name = "PINE_MAQUINA_MOD", length = 100)
  private String pineMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "PINE_IP_MOD", length = 100)
  private String pineIpMod;

  public SieduPropIntelectualEntidad() {
  }

  public SieduPropIntelectualEntidad(SieduPropIntelectualEntidadPK sieduPropIntelectualEntidadPK, SieduEntidad entidad, String pineUsuCrea, Date pineFechaCrea, String pineMaquinaCrea, String pineIpCrea, String pineUsuMod, Date pineFechaMod, String pineMaquinaMod, String pineIpMod) {
    this.sieduPropIntelectualEntidadPK = sieduPropIntelectualEntidadPK;
    this.entidad = entidad;
    this.pineUsuCrea = pineUsuCrea;
    this.pineFechaCrea = pineFechaCrea;
    this.pineMaquinaCrea = pineMaquinaCrea;
    this.pineIpCrea = pineIpCrea;
    this.pineUsuMod = pineUsuMod;
    this.pineFechaMod = pineFechaMod;
    this.pineMaquinaMod = pineMaquinaMod;
    this.pineIpMod = pineIpMod;
  }

  public SieduPropIntelectualEntidadPK getSieduPropIntelectualEntidadPK() {
    return sieduPropIntelectualEntidadPK;
  }

  public void setSieduPropIntelectualEntidadPK(SieduPropIntelectualEntidadPK sieduPropIntelectualEntidadPK) {
    this.sieduPropIntelectualEntidadPK = sieduPropIntelectualEntidadPK;
  }

  @XmlTransient
  public SieduEntidad getEntidad() {
    return entidad;
  }

  public void setEntidad(SieduEntidad entidad) {
    this.entidad = entidad;
  }

  @XmlTransient
  public String getPineUsuCrea() {
    return pineUsuCrea;
  }

  public void setPineUsuCrea(String pineUsuCrea) {
    this.pineUsuCrea = pineUsuCrea;
  }

  @XmlTransient
  public Date getPineFechaCrea() {
    return pineFechaCrea;
  }

  public void setPineFechaCrea(Date pineFechaCrea) {
    this.pineFechaCrea = pineFechaCrea;
  }

  @XmlTransient
  public String getPineMaquinaCrea() {
    return pineMaquinaCrea;
  }

  public void setPineMaquinaCrea(String pineMaquinaCrea) {
    this.pineMaquinaCrea = pineMaquinaCrea;
  }

  @XmlTransient
  public String getPineIpCrea() {
    return pineIpCrea;
  }

  public void setPineIpCrea(String pineIpCrea) {
    this.pineIpCrea = pineIpCrea;
  }

  @XmlTransient
  public String getPineUsuMod() {
    return pineUsuMod;
  }

  public void setPineUsuMod(String pineUsuMod) {
    this.pineUsuMod = pineUsuMod;
  }

  @XmlTransient
  public Date getPineFechaMod() {
    return pineFechaMod;
  }

  public void setPineFechaMod(Date pineFechaMod) {
    this.pineFechaMod = pineFechaMod;
  }

  @XmlTransient
  public String getPineMaquinaMod() {
    return pineMaquinaMod;
  }

  public void setPineMaquinaMod(String pineMaquinaMod) {
    this.pineMaquinaMod = pineMaquinaMod;
  }

  @XmlTransient
  public String getPineIpMod() {
    return pineIpMod;
  }

  public void setPineIpMod(String pineIpMod) {
    this.pineIpMod = pineIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 79 * hash + Objects.hashCode(this.sieduPropIntelectualEntidadPK);
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
    final SieduPropIntelectualEntidad other = (SieduPropIntelectualEntidad) obj;
    if (!Objects.equals(this.sieduPropIntelectualEntidadPK, other.sieduPropIntelectualEntidadPK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduPropIntelectualEntidad{" + "sieduPropIntelectualEntidadPK=" + sieduPropIntelectualEntidadPK + ", entidad=" + entidad + ", pineUsuCrea=" + pineUsuCrea + ", pineFechaCrea=" + pineFechaCrea + ", pineMaquinaCrea=" + pineMaquinaCrea + ", pineIpCrea=" + pineIpCrea + ", pineUsuMod=" + pineUsuMod + ", pineFechaMod=" + pineFechaMod + ", pineMaquinaMod=" + pineMaquinaMod + ", pineIpMod=" + pineIpMod + '}';
  }


  
}
