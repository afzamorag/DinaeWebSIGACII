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
@Table(name = "SIEDU_INVEST_EXT_PARTICIPA")
@NamedQueries({
  @NamedQuery(name = "SieduInvestigacionExternaParticipante.findAll", query = "SELECT s FROM SieduInvestigacionExternaParticipante s")})
@Cacheable(false)
@XmlRootElement
public class SieduInvestigacionExternaParticipante implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduInvestigacionExternaParticipantePK sieduInvestigacionExternaParticipantePK;
  
  @JoinColumn(name = "INVP_INVE", referencedColumnName = "INVE_INVE", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduInvestigacionExterna investigacionExterna;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "INVP_USU_CREA", nullable = false, length = 30)
  private String invpUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "INVP_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date invpFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "INVP_MAQUINA_CREA", nullable = false, length = 100)
  private String invpMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "INVP_IP_CREA", nullable = false, length = 100)
  private String invpIpCrea;
  
  @Size(max = 30)
  @Column(name = "INVP_USU_MOD", length = 30)
  private String invpUsuMod;
  
  @Column(name = "INVP_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date invpFechaMod;
  
  @Size(max = 100)
  @Column(name = "INVP_MAQUINA_MOD", length = 100)
  private String invpMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "INVP_IP_MOD", length = 100)
  private String invpIpMod;
  
  @JoinColumn(name = "INVP_PERS", referencedColumnName = "PERS_PERS", insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduPersona persona;

  public SieduInvestigacionExternaParticipante() {
  }

  public SieduInvestigacionExternaParticipante(SieduInvestigacionExternaParticipantePK sieduInvestigacionExternaParticipantePK, SieduInvestigacionExterna investigacionExterna, String invpUsuCrea, Date invpFechaCrea, String invpMaquinaCrea, String invpIpCrea, String invpUsuMod, Date invpFechaMod, String invpMaquinaMod, String invpIpMod) {
    this.sieduInvestigacionExternaParticipantePK = sieduInvestigacionExternaParticipantePK;
    this.investigacionExterna = investigacionExterna;
    this.invpUsuCrea = invpUsuCrea;
    this.invpFechaCrea = invpFechaCrea;
    this.invpMaquinaCrea = invpMaquinaCrea;
    this.invpIpCrea = invpIpCrea;
    this.invpUsuMod = invpUsuMod;
    this.invpFechaMod = invpFechaMod;
    this.invpMaquinaMod = invpMaquinaMod;
    this.invpIpMod = invpIpMod;
  }

  @XmlTransient
  public SieduInvestigacionExternaParticipantePK getSieduInvestigacionExternaParticipantePK() {
    return sieduInvestigacionExternaParticipantePK;
  }

  public void setSieduInvestigacionExternaParticipantePK(SieduInvestigacionExternaParticipantePK sieduInvestigacionExternaParticipantePK) {
    this.sieduInvestigacionExternaParticipantePK = sieduInvestigacionExternaParticipantePK;
  }

  @XmlTransient
  public SieduInvestigacionExterna getInvestigacionExterna() {
    return investigacionExterna;
  }

  public void setInvestigacionExterna(SieduInvestigacionExterna investigacionExterna) {
    this.investigacionExterna = investigacionExterna;
  }

  @XmlTransient
  public String getInvpUsuCrea() {
    return invpUsuCrea;
  }

  public void setInvpUsuCrea(String invpUsuCrea) {
    this.invpUsuCrea = invpUsuCrea;
  }

  @XmlTransient
  public Date getInvpFechaCrea() {
    return invpFechaCrea;
  }

  public void setInvpFechaCrea(Date invpFechaCrea) {
    this.invpFechaCrea = invpFechaCrea;
  }

  @XmlTransient
  public String getInvpMaquinaCrea() {
    return invpMaquinaCrea;
  }

  public void setInvpMaquinaCrea(String invpMaquinaCrea) {
    this.invpMaquinaCrea = invpMaquinaCrea;
  }

  @XmlTransient
  public String getInvpIpCrea() {
    return invpIpCrea;
  }

  public void setInvpIpCrea(String invpIpCrea) {
    this.invpIpCrea = invpIpCrea;
  }
  
  @XmlTransient
  public String getInvpUsuMod() {
    return invpUsuMod;
  }

  public void setInvpUsuMod(String invpUsuMod) {
    this.invpUsuMod = invpUsuMod;
  }

  @XmlTransient
  public Date getInvpFechaMod() {
    return invpFechaMod;
  }

  public void setInvpFechaMod(Date invpFechaMod) {
    this.invpFechaMod = invpFechaMod;
  }

  @XmlTransient
  public String getInvpMaquinaMod() {
    return invpMaquinaMod;
  }

  public void setInvpMaquinaMod(String invpMaquinaMod) {
    this.invpMaquinaMod = invpMaquinaMod;
  }

  @XmlTransient
  public String getInvpIpMod() {
    return invpIpMod;
  }

  public void setInvpIpMod(String invpIpMod) {
    this.invpIpMod = invpIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.sieduInvestigacionExternaParticipantePK);
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
    final SieduInvestigacionExternaParticipante other = (SieduInvestigacionExternaParticipante) obj;
    if (!Objects.equals(this.sieduInvestigacionExternaParticipantePK, other.sieduInvestigacionExternaParticipantePK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduInvestigacionExternaParticipante{" + "sieduInvestigacionExternaParticipantePK=" + sieduInvestigacionExternaParticipantePK + ", investigacionExterna=" + investigacionExterna + ", invpUsuCrea=" + invpUsuCrea + ", invpFechaCrea=" + invpFechaCrea + ", invpMaquinaCrea=" + invpMaquinaCrea + ", invpIpCrea=" + invpIpCrea + ", invpUsuMod=" + invpUsuMod + ", invpFechaMod=" + invpFechaMod + ", invpMaquinaMod=" + invpMaquinaMod + ", invpIpMod=" + invpIpMod + '}';
  }

    public SieduPersona getPersona() {
        return persona;
    }

    public void setPersona(SieduPersona persona) {
        this.persona = persona;
    }
  
  

  
}
