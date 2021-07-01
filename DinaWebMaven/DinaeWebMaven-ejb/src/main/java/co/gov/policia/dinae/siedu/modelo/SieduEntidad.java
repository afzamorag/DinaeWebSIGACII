/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_ENTIDAD", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ENTI_NOMBRE", "ENTI_DOM_TIPO", "ENTI_DOM_SECTOR"})})
@NamedQueries({
    @NamedQuery(name = "SieduEntidad.findAll", query = "SELECT s FROM SieduEntidad s")})
@Cacheable(false)
@XmlRootElement
public class SieduEntidad implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTI_ENTI", nullable = false, precision = 0, scale = -127)
    @SequenceGenerator(name = "ENTIDAD_SEQ_GEN", sequenceName = "SIEDU_ENTIDAD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENTIDAD_SEQ_GEN")
    private Long entiEnti;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENTI_NOMBRE", nullable = false, length = 100)
    @XmlAttribute
    private String entiNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTI_DOM_TIPO", nullable = false)
    private Long entiDomTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTI_LUGGEO", nullable = false)
    private int entiLuggeo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTI_DOM_SECTOR", nullable = false)
    private Long entiDomSector;
    @Size(max = 100)
    @Column(name = "ENTI_DIRECCION", length = 100)
    private String entiDireccion;
    @Column(name = "ENTI_TELEFONO")
    private BigInteger entiTelefono;
    @Size(max = 50)
    @Column(name = "ENTI_PAGE_WEB", length = 50)
    private String entiPageWeb;
    @Size(max = 50)
    @Column(name = "ENTI_EMAIL", length = 50)
    private String entiEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ENTI_USU_CREA", nullable = false, length = 30)
    private String entiUsuCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENTI_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entiFechaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENTI_MAQUINA_CREA", nullable = false, length = 100)
    private String entiMaquinaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ENTI_IP_CREA", nullable = false, length = 100)
    private String entiIpCrea;
    @Size(max = 30)
    @Column(name = "ENTI_USU_MOD", length = 30)
    private String entiUsuMod;
    @Column(name = "ENTI_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entiFechaMod;
    @Size(max = 100)
    @Column(name = "ENTI_MAQUINA_MOD", length = 100)
    private String entiMaquinaMod;
    @Size(max = 100)
    @Column(name = "ENTI_IP_MOD", length = 100)
    private String entiIpMod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eveeEnti", fetch = FetchType.LAZY)
    private List<SieduEventoEscuela> sieduEventoEscuelaList;    

    public SieduEntidad() {
    }

    public SieduEntidad(Long entiEnti) {
        this.entiEnti = entiEnti;
    }

    public SieduEntidad(Long entiEnti, String entiNombre, Long entiDomTipo, int entiLuggeo, Long entiDomSector, String entiUsuCrea, Date entiFechaCrea, String entiMaquinaCrea, String entiIpCrea) {
        this.entiEnti = entiEnti;
        this.entiNombre = entiNombre;
        this.entiDomTipo = entiDomTipo;
        this.entiLuggeo = entiLuggeo;
        this.entiDomSector = entiDomSector;
        this.entiUsuCrea = entiUsuCrea;
        this.entiFechaCrea = entiFechaCrea;
        this.entiMaquinaCrea = entiMaquinaCrea;
        this.entiIpCrea = entiIpCrea;
    }

    public Long getEntiEnti() {
        return entiEnti;
    }

    public void setEntiEnti(Long entiEnti) {
        this.entiEnti = entiEnti;
    }

    public String getEntiNombre() {
        return entiNombre;
    }

    public void setEntiNombre(String entiNombre) {
        this.entiNombre = entiNombre;
    }

    public Long getEntiDomTipo() {
        return entiDomTipo;
    }

    public void setEntiDomTipo(Long entiDomTipo) {
        this.entiDomTipo = entiDomTipo;
    }

    public int getEntiLuggeo() {
        return entiLuggeo;
    }

    public void setEntiLuggeo(int entiLuggeo) {
        this.entiLuggeo = entiLuggeo;
    }

    public Long getEntiDomSector() {
        return entiDomSector;
    }

    public void setEntiDomSector(Long entiDomSector) {
        this.entiDomSector = entiDomSector;
    }

    public String getEntiDireccion() {
        return entiDireccion;
    }

    public void setEntiDireccion(String entiDireccion) {
        this.entiDireccion = entiDireccion;
    }

    public BigInteger getEntiTelefono() {
        return entiTelefono;
    }

    public void setEntiTelefono(BigInteger entiTelefono) {
        this.entiTelefono = entiTelefono;
    }

    public String getEntiPageWeb() {
        return entiPageWeb;
    }

    public void setEntiPageWeb(String entiPageWeb) {
        this.entiPageWeb = entiPageWeb;
    }

    public String getEntiEmail() {
        return entiEmail;
    }

    public void setEntiEmail(String entiEmail) {
        this.entiEmail = entiEmail;
    }

    public String getEntiUsuCrea() {
        return entiUsuCrea;
    }

    public void setEntiUsuCrea(String entiUsuCrea) {
        this.entiUsuCrea = entiUsuCrea;
    }

    public Date getEntiFechaCrea() {
        return entiFechaCrea;
    }

    public void setEntiFechaCrea(Date entiFechaCrea) {
        this.entiFechaCrea = entiFechaCrea;
    }

    public String getEntiMaquinaCrea() {
        return entiMaquinaCrea;
    }

    public void setEntiMaquinaCrea(String entiMaquinaCrea) {
        this.entiMaquinaCrea = entiMaquinaCrea;
    }

    public String getEntiIpCrea() {
        return entiIpCrea;
    }

    public void setEntiIpCrea(String entiIpCrea) {
        this.entiIpCrea = entiIpCrea;
    }

    public String getEntiUsuMod() {
        return entiUsuMod;
    }

    public void setEntiUsuMod(String entiUsuMod) {
        this.entiUsuMod = entiUsuMod;
    }

    public Date getEntiFechaMod() {
        return entiFechaMod;
    }

    public void setEntiFechaMod(Date entiFechaMod) {
        this.entiFechaMod = entiFechaMod;
    }

    public String getEntiMaquinaMod() {
        return entiMaquinaMod;
    }

    public void setEntiMaquinaMod(String entiMaquinaMod) {
        this.entiMaquinaMod = entiMaquinaMod;
    }

    public String getEntiIpMod() {
        return entiIpMod;
    }

    public void setEntiIpMod(String entiIpMod) {
        this.entiIpMod = entiIpMod;
    }

    @XmlTransient
    public List<SieduEventoEscuela> getSieduEventoEscuelaList() {
        return sieduEventoEscuelaList;
    }

    public void setSieduEventoEscuelaList(List<SieduEventoEscuela> sieduEventoEscuelaList) {
        this.sieduEventoEscuelaList = sieduEventoEscuelaList;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entiEnti != null ? entiEnti.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieduEntidad)) {
            return false;
        }
        SieduEntidad other = (SieduEntidad) object;
        if ((this.entiEnti == null && other.entiEnti != null) || (this.entiEnti != null && !this.entiEnti.equals(other.entiEnti))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.SieduEntidad[ entiEnti=" + entiEnti + " ]";
    }

}
