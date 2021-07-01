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
@Table(name = "SIEDU_PROPINTELECTUAL_PERSONA")
@NamedQueries({
  @NamedQuery(name = "SieduPropIntelectualPersona.findAll", query = "SELECT s FROM SieduPropIntelectualPersona s")})
@Cacheable(false)
@XmlRootElement
public class SieduPropIntelectualPersona implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduPropIntelectualPersonaPK sieduPropIntelectualPersonaPK;
  
  @JoinColumn(name = "PINP_PERS", referencedColumnName = "PERS_PERS", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduPersona persona;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PINP_USU_CREA", nullable = false, length = 30)
  private String pinpUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PINP_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date pinpFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PINP_MAQUINA_CREA", nullable = false, length = 100)
  private String pinpMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PINP_IP_CREA", nullable = false, length = 100)
  private String pinpIpCrea;
  
  @Size(max = 30)
  @Column(name = "PINP_USU_MOD", length = 30)
  private String pinpUsuMod;
  
  @Column(name = "PINP_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pinpFechaMod;
  
  @Size(max = 100)
  @Column(name = "PINP_MAQUINA_MOD", length = 100)
  private String pinpMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "PINP_IP_MOD", length = 100)
  private String pinpIpMod;

  public SieduPropIntelectualPersona() {
  }

  public SieduPropIntelectualPersona(SieduPropIntelectualPersonaPK sieduPropIntelectualPersonaPK, SieduPersona persona, String pinpUsuCrea, Date pinpFechaCrea, String pinpMaquinaCrea, String pinpIpCrea, String pinpUsuMod, Date pinpFechaMod, String pinpMaquinaMod, String pinpIpMod) {
    this.sieduPropIntelectualPersonaPK = sieduPropIntelectualPersonaPK;
    this.persona = persona;
    this.pinpUsuCrea = pinpUsuCrea;
    this.pinpFechaCrea = pinpFechaCrea;
    this.pinpMaquinaCrea = pinpMaquinaCrea;
    this.pinpIpCrea = pinpIpCrea;
    this.pinpUsuMod = pinpUsuMod;
    this.pinpFechaMod = pinpFechaMod;
    this.pinpMaquinaMod = pinpMaquinaMod;
    this.pinpIpMod = pinpIpMod;
  }

  public SieduPropIntelectualPersonaPK getSieduPropIntelectualPersonaPK() {
    return sieduPropIntelectualPersonaPK;
  }

  public void setSieduPropIntelectualPersonaPK(SieduPropIntelectualPersonaPK sieduPropIntelectualPersonaPK) {
    this.sieduPropIntelectualPersonaPK = sieduPropIntelectualPersonaPK;
  }

  @XmlTransient
  public SieduPersona getPersona() {
    return persona;
  }

  public void setPersona(SieduPersona persona) {
    this.persona = persona;
  }

  @XmlTransient
  public String getPinpUsuCrea() {
    return pinpUsuCrea;
  }

  public void setPinpUsuCrea(String pinpUsuCrea) {
    this.pinpUsuCrea = pinpUsuCrea;
  }

  @XmlTransient
  public Date getPinpFechaCrea() {
    return pinpFechaCrea;
  }

  public void setPinpFechaCrea(Date pinpFechaCrea) {
    this.pinpFechaCrea = pinpFechaCrea;
  }

  @XmlTransient
  public String getPinpMaquinaCrea() {
    return pinpMaquinaCrea;
  }

  public void setPinpMaquinaCrea(String pinpMaquinaCrea) {
    this.pinpMaquinaCrea = pinpMaquinaCrea;
  }

  @XmlTransient
  public String getPinpIpCrea() {
    return pinpIpCrea;
  }

  public void setPinpIpCrea(String pinpIpCrea) {
    this.pinpIpCrea = pinpIpCrea;
  }

  @XmlTransient
  public String getPinpUsuMod() {
    return pinpUsuMod;
  }

  public void setPinpUsuMod(String pinpUsuMod) {
    this.pinpUsuMod = pinpUsuMod;
  }

  @XmlTransient
  public Date getPinpFechaMod() {
    return pinpFechaMod;
  }

  public void setPinpFechaMod(Date pinpFechaMod) {
    this.pinpFechaMod = pinpFechaMod;
  }

  @XmlTransient
  public String getPinpMaquinaMod() {
    return pinpMaquinaMod;
  }

  public void setPinpMaquinaMod(String pinpMaquinaMod) {
    this.pinpMaquinaMod = pinpMaquinaMod;
  }

  @XmlTransient
  public String getPinpIpMod() {
    return pinpIpMod;
  }

  public void setPinpIpMod(String pinpIpMod) {
    this.pinpIpMod = pinpIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.sieduPropIntelectualPersonaPK);
    return hash;
  }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SieduPropIntelectualPersona other = (SieduPropIntelectualPersona) obj;
        if (!Objects.equals(this.sieduPropIntelectualPersonaPK, other.sieduPropIntelectualPersonaPK)) {
            return false;
        }
        if (this.persona.getPersPers() != null) {
            return this.persona.getPersPers().equals(other.persona.getPersPers());
        }
        return true;
    }


  @Override
  public String toString() {
    return "SieduPropIntelectualPersona{" + "sieduPropIntelectualPersonaPK=" + sieduPropIntelectualPersonaPK + ", persona=" + persona + ", pinpUsuCrea=" + pinpUsuCrea + ", pinpFechaCrea=" + pinpFechaCrea + ", pinpMaquinaCrea=" + pinpMaquinaCrea + ", pinpIpCrea=" + pinpIpCrea + ", pinpUsuMod=" + pinpUsuMod + ", pinpFechaMod=" + pinpFechaMod + ", pinpMaquinaMod=" + pinpMaquinaMod + ", pinpIpMod=" + pinpIpMod + '}';
  }

  
}
