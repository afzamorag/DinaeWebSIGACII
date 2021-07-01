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
@Table(name = "SIEDU_BANCO_NECESIDAD_PERSONA")
@NamedQueries({
    @NamedQuery(name = "SieduBancoNecesidadPersona.findAll", query = "SELECT s FROM SieduBancoNecesidadPersona s")})
@Cacheable(false)
@XmlRootElement
public class SieduBancoNecesidadPersona implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SieduBancoNecesidadPersonaPK sieduBancoNecesidadPersonaPK;

    @JoinColumn(name = "BNPE_PERS", referencedColumnName = "PERS_PERS", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduPersona persona;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "BNPE_USU_CREA", nullable = false, length = 30)
    private String bnpeUsuCrea;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BNPE_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bnpeFechaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BNPE_MAQUINA_CREA", nullable = false, length = 100)
    private String bnpeMaquinaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BNPE_IP_CREA", nullable = false, length = 100)
    private String bnpeIpCrea;

    @Size(max = 30)
    @Column(name = "BNPE_USU_MOD", length = 30)
    private String bnpeUsuMod;

    @Column(name = "BNPE_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bnpeFechaMod;

    @Size(max = 100)
    @Column(name = "BNPE_MAQUINA_MOD", length = 100)
    private String bnpeMaquinaMod;

    @Size(max = 100)
    @Column(name = "BNPE_IP_MOD", length = 100)
    private String bnpeIpMod;

    public SieduBancoNecesidadPersona() {
    }

    public SieduBancoNecesidadPersona(SieduBancoNecesidadPersonaPK sieduBancoNecesidadPersonaPK, String bnpeUsuCrea, Date bnpeFechaCrea, String bnpeMaquinaCrea, String bnpeIpCrea, String bnpeUsuMod, Date bnpeFechaMod, String bnpeMaquinaMod, String bnpeIpMod) {
        this.sieduBancoNecesidadPersonaPK = sieduBancoNecesidadPersonaPK;
        this.bnpeUsuCrea = bnpeUsuCrea;
        this.bnpeFechaCrea = bnpeFechaCrea;
        this.bnpeMaquinaCrea = bnpeMaquinaCrea;
        this.bnpeIpCrea = bnpeIpCrea;
        this.bnpeUsuMod = bnpeUsuMod;
        this.bnpeFechaMod = bnpeFechaMod;
        this.bnpeMaquinaMod = bnpeMaquinaMod;
        this.bnpeIpMod = bnpeIpMod;
    }

    @XmlTransient
    public SieduBancoNecesidadPersonaPK getSieduBancoNecesidadPersonaPK() {
        return sieduBancoNecesidadPersonaPK;
    }

    public void setSieduBancoNecesidadPersonaPK(SieduBancoNecesidadPersonaPK sieduBancoNecesidadPersonaPK) {
        this.sieduBancoNecesidadPersonaPK = sieduBancoNecesidadPersonaPK;
    }

    @XmlTransient
    public String getBnpeUsuCrea() {
        return bnpeUsuCrea;
    }

    public void setBnpeUsuCrea(String bnpeUsuCrea) {
        this.bnpeUsuCrea = bnpeUsuCrea;
    }

    @XmlTransient
    public Date getBnpeFechaCrea() {
        return bnpeFechaCrea;
    }

    public void setBnpeFechaCrea(Date bnpeFechaCrea) {
        this.bnpeFechaCrea = bnpeFechaCrea;
    }

    @XmlTransient
    public String getBnpeMaquinaCrea() {
        return bnpeMaquinaCrea;
    }

    public void setBnpeMaquinaCrea(String bnpeMaquinaCrea) {
        this.bnpeMaquinaCrea = bnpeMaquinaCrea;
    }

    @XmlTransient
    public String getBnpeIpCrea() {
        return bnpeIpCrea;
    }

    public void setBnpeIpCrea(String bnpeIpCrea) {
        this.bnpeIpCrea = bnpeIpCrea;
    }

    @XmlTransient
    public String getBnpeUsuMod() {
        return bnpeUsuMod;
    }

    public void setBnpeUsuMod(String bnpeUsuMod) {
        this.bnpeUsuMod = bnpeUsuMod;
    }

    @XmlTransient
    public Date getBnpeFechaMod() {
        return bnpeFechaMod;
    }

    public void setBnpeFechaMod(Date bnpeFechaMod) {
        this.bnpeFechaMod = bnpeFechaMod;
    }

    @XmlTransient
    public String getBnpeMaquinaMod() {
        return bnpeMaquinaMod;
    }

    public void setBnpeMaquinaMod(String bnpeMaquinaMod) {
        this.bnpeMaquinaMod = bnpeMaquinaMod;
    }

    @XmlTransient
    public String getBnpeIpMod() {
        return bnpeIpMod;
    }

    public void setBnpeIpMod(String bnpeIpMod) {
        this.bnpeIpMod = bnpeIpMod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.sieduBancoNecesidadPersonaPK);
        hash = 67 * hash + Objects.hashCode(this.bnpeUsuCrea);
        hash = 67 * hash + Objects.hashCode(this.bnpeFechaCrea);
        hash = 67 * hash + Objects.hashCode(this.bnpeMaquinaCrea);
        hash = 67 * hash + Objects.hashCode(this.bnpeIpCrea);
        hash = 67 * hash + Objects.hashCode(this.bnpeUsuMod);
        hash = 67 * hash + Objects.hashCode(this.bnpeFechaMod);
        hash = 67 * hash + Objects.hashCode(this.bnpeMaquinaMod);
        hash = 67 * hash + Objects.hashCode(this.bnpeIpMod);
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
        final SieduBancoNecesidadPersona other = (SieduBancoNecesidadPersona) obj;
        if (!Objects.equals(this.bnpeUsuCrea, other.bnpeUsuCrea)) {
            return false;
        }
        if (!Objects.equals(this.bnpeMaquinaCrea, other.bnpeMaquinaCrea)) {
            return false;
        }
        if (!Objects.equals(this.bnpeIpCrea, other.bnpeIpCrea)) {
            return false;
        }
        if (!Objects.equals(this.bnpeUsuMod, other.bnpeUsuMod)) {
            return false;
        }
        if (!Objects.equals(this.bnpeMaquinaMod, other.bnpeMaquinaMod)) {
            return false;
        }
        if (!Objects.equals(this.bnpeIpMod, other.bnpeIpMod)) {
            return false;
        }
        if (!Objects.equals(this.sieduBancoNecesidadPersonaPK, other.sieduBancoNecesidadPersonaPK)) {
            return false;
        }
        if (!Objects.equals(this.bnpeFechaCrea, other.bnpeFechaCrea)) {
            return false;
        }
        if (!Objects.equals(this.bnpeFechaMod, other.bnpeFechaMod)) {
            return false;
        }
        if (this.persona.getPersPers() != null) {
            return this.persona.getPersPers().equals(other.persona.getPersPers());
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduBancoNecesidadPersona{" + "sieduBancoNecesidadPersonaPK=" + sieduBancoNecesidadPersonaPK + ", bnpeUsuCrea=" + bnpeUsuCrea + ", bnpeFechaCrea=" + bnpeFechaCrea + ", bnpeMaquinaCrea=" + bnpeMaquinaCrea + ", bnpeIpCrea=" + bnpeIpCrea + ", bnpeUsuMod=" + bnpeUsuMod + ", bnpeFechaMod=" + bnpeFechaMod + ", bnpeMaquinaMod=" + bnpeMaquinaMod + ", bnpeIpMod=" + bnpeIpMod + '}';
    }

    @XmlTransient
    public SieduPersona getPersona() {
        return persona;
    }

    public void setPersona(SieduPersona persona) {
        this.persona = persona;
    }

}
