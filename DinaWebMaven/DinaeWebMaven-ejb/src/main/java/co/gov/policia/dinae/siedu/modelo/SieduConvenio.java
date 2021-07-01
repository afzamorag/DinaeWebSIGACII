/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_CONVENIO", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CONV_DESCRI", "CONV_FECHA_INI", "CONV_FECHA_FIN"})})
@NamedQueries({
    @NamedQuery(name = "SieduConvenio.findAll", query = "SELECT s FROM SieduConvenio s")})
@XmlRootElement
@Cacheable(false)
public class SieduConvenio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONV_CONV", nullable = false)
    @XmlAttribute
    private Long convConv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CONV_DESCRI", nullable = false, length = 50)
    private String convDescri;
    @Column(name = "CONV_FECHA_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date convFechaIni;
    @Column(name = "CONV_FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date convFechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CONV_USU_CREA", nullable = false, length = 30)
    private String convUsuCrea;
    @JoinColumn(name = "CONVE_DOM_CONV", referencedColumnName = "ID_DOMINIO", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Dominio tipoConvenio;
    @JoinColumn(name = "CONVE_ENTI", referencedColumnName = "ENTI_ENTI", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduEntidad entidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CONV_NOMBRE", nullable = false, length = 100)
    @XmlAttribute
    private String convNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CONV_VIGENTE", nullable = false, length = 2)
    private String convVigente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONV_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date convFechaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CONV_MAQUINA_CREA", nullable = false, length = 100)
    private String convMaquinaCrea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CONV_IP_CREA", nullable = false, length = 100)
    private String convIpCrea;
    @Size(max = 30)
    @Column(name = "CONV_USU_MOD", length = 30)
    private String convUsuMod;
    @Column(name = "CONV_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date convFechaMod;
    @Size(max = 100)
    @Column(name = "CONV_MAQUINA_MOD", length = 100)
    private String convMaquinaMod;
    @Size(max = 100)
    @Column(name = "CONV_IP_MOD", length = 100)
    private String convIpMod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eveeConv", fetch = FetchType.LAZY)
    private List<SieduEventoEscuela> sieduEventoEscuelaList;

    public SieduConvenio(Long convConv, String convDescri, Date convFechaIni, Date convFechaFin, String convUsuCrea, Dominio tipoConvenio, SieduEntidad entidad, String convNombre, String convVigente, Date convFechaCrea, String convMaquinaCrea, String convIpCrea) {
        this.convConv = convConv;
        this.convDescri = convDescri;
        this.convFechaIni = convFechaIni;
        this.convFechaFin = convFechaFin;
        this.convUsuCrea = convUsuCrea;
        this.tipoConvenio = tipoConvenio;
        this.entidad = entidad;
        this.convNombre = convNombre;
        this.convVigente = convVigente;
        this.convFechaCrea = convFechaCrea;
        this.convMaquinaCrea = convMaquinaCrea;
        this.convIpCrea = convIpCrea;
    }

    public SieduConvenio() {
    }

    public SieduConvenio(Long convConv) {
        this.convConv = convConv;
    }

    public Long getConvConv() {
        return convConv;
    }

    public void setConvConv(Long convConv) {
        this.convConv = convConv;
    }

    public Dominio getConvenio() {
        return tipoConvenio;
    }

    public void setConvenio(Dominio tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }

    public String getConvVigente() {
        return convVigente;
    }

    public void setConvVigente(String convVigente) {
        this.convVigente = convVigente;
    }

    public String getConvNombre() {
        return convNombre;
    }

    public void setConvNombre(String convNombre) {
        this.convNombre = convNombre;
    }

    public SieduEntidad getEntidad() {
        return entidad;
    }

    public void setEntidad(SieduEntidad entidad) {
        this.entidad = entidad;
    }

    public String getConvDescri() {
        return convDescri;
    }

    public void setConvDescri(String convDescri) {
        this.convDescri = convDescri;
    }

    public Date getConvFechaIni() {
        return convFechaIni;
    }

    public void setConvFechaIni(Date convFechaIni) {
        this.convFechaIni = convFechaIni;
    }

    public Date getConvFechaFin() {
        return convFechaFin;
    }

    public void setConvFechaFin(Date convFechaFin) {
        this.convFechaFin = convFechaFin;
    }

    public String getConvUsuCrea() {
        return convUsuCrea;
    }

    public void setConvUsuCrea(String convUsuCrea) {
        this.convUsuCrea = convUsuCrea;
    }

    public Date getConvFechaCrea() {
        return convFechaCrea;
    }

    public void setConvFechaCrea(Date convFechaCrea) {
        this.convFechaCrea = convFechaCrea;
    }

    public String getConvMaquinaCrea() {
        return convMaquinaCrea;
    }

    public void setConvMaquinaCrea(String convMaquinaCrea) {
        this.convMaquinaCrea = convMaquinaCrea;
    }

    public String getConvIpCrea() {
        return convIpCrea;
    }

    public void setConvIpCrea(String convIpCrea) {
        this.convIpCrea = convIpCrea;
    }

    public String getConvUsuMod() {
        return convUsuMod;
    }

    public void setConvUsuMod(String convUsuMod) {
        this.convUsuMod = convUsuMod;
    }

    public Date getConvFechaMod() {
        return convFechaMod;
    }

    public void setConvFechaMod(Date convFechaMod) {
        this.convFechaMod = convFechaMod;
    }

    public String getConvMaquinaMod() {
        return convMaquinaMod;
    }

    public void setConvMaquinaMod(String convMaquinaMod) {
        this.convMaquinaMod = convMaquinaMod;
    }

    public String getConvIpMod() {
        return convIpMod;
    }

    public void setConvIpMod(String convIpMod) {
        this.convIpMod = convIpMod;
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
        hash += (convConv != null ? convConv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieduConvenio)) {
            return false;
        }
        SieduConvenio other = (SieduConvenio) object;
        if ((this.convConv == null && other.convConv != null) || (this.convConv != null && !this.convConv.equals(other.convConv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.SieduConvenio[ convConv=" + convConv + " ]";
    }

}
