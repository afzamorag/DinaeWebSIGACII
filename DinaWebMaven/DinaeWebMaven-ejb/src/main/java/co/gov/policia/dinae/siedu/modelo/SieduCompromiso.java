package co.gov.policia.dinae.siedu.modelo;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
@Table(name = "SIEDU_COMPROMISO")
@NamedQueries({
    @NamedQuery(name = "SieduCompromiso.findAll", query = "SELECT s FROM SieduCompromiso s")})
@Cacheable(false)
@XmlRootElement
public class SieduCompromiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SieduCompromisoPK sieduCompromisoPK;

    @JoinColumn(name = "COMP_INVE", referencedColumnName = "INVE_INVE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SieduInvestigacionExterna investigacionExterna;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "COMP_DESCRI", nullable = false, length = 500)
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "COMP_FECHA_ENTREGA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "COMP_RESULTADO_ESPERADO", nullable = false, length = 500)
    private String resultadoEsperado;

    @Basic(optional = false)
    @Column(name = "COMP_ESTADO")
    private Character estado;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "COMP_USU_CREA", nullable = false, length = 30)
    private String compUsuCrea;

    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_FECHA_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date compFechaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMP_MAQUINA_CREA", nullable = false, length = 100)
    private String compMaquinaCrea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMP_IP_CREA", nullable = false, length = 100)
    private String compIpCrea;

    @Size(max = 30)
    @Column(name = "COMP_USU_MOD", length = 30)
    private String compUsuMod;

    @Column(name = "COMP_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date compFechaMod;

    @Size(max = 100)
    @Column(name = "COMP_MAQUINA_MOD", length = 100)
    private String compMaquinaMod;

    @Size(max = 100)
    @Column(name = "COMP_IP_MOD", length = 100)
    private String compIpMod;

    @OneToMany(mappedBy = "compromiso", fetch = FetchType.LAZY)
    private List<SieduObservacionCompromiso> observaciones;

    @OneToMany(mappedBy = "compromiso", fetch = FetchType.LAZY)
    private List<SieduArchivoCompromiso> archivos;

    public SieduCompromiso() {
    }

    public SieduCompromiso(SieduCompromisoPK sieduCompromisoPK, SieduInvestigacionExterna investigacionExterna, String descripcion, Date fechaEntrega, String resultadoEsperado, Character estado, String compUsuCrea, Date compFechaCrea, String compMaquinaCrea, String compIpCrea, String compUsuMod, Date compFechaMod, String compMaquinaMod, String compIpMod, List<SieduObservacionCompromiso> observaciones, List<SieduArchivoCompromiso> archivos) {
        this.sieduCompromisoPK = sieduCompromisoPK;
        this.investigacionExterna = investigacionExterna;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.resultadoEsperado = resultadoEsperado;
        this.estado = estado;
        this.compUsuCrea = compUsuCrea;
        this.compFechaCrea = compFechaCrea;
        this.compMaquinaCrea = compMaquinaCrea;
        this.compIpCrea = compIpCrea;
        this.compUsuMod = compUsuMod;
        this.compFechaMod = compFechaMod;
        this.compMaquinaMod = compMaquinaMod;
        this.compIpMod = compIpMod;
        this.observaciones = observaciones;
        this.archivos = archivos;
    }

    @XmlTransient
    public SieduCompromisoPK getSieduCompromisoPK() {
        return sieduCompromisoPK;
    }

    public void setSieduCompromisoPK(SieduCompromisoPK sieduCompromisoPK) {
        this.sieduCompromisoPK = sieduCompromisoPK;
    }

    @XmlTransient
    public SieduInvestigacionExterna getInvestigacionExterna() {
        return investigacionExterna;
    }

    public void setInvestigacionExterna(SieduInvestigacionExterna investigacionExterna) {
        this.investigacionExterna = investigacionExterna;
    }

    @XmlTransient
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @XmlTransient
    public String getResultadoEsperado() {
        return resultadoEsperado;
    }

    public void setResultadoEsperado(String resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
    }

    @XmlTransient
    public Character getEstado() {
        return estado;
    }

    @Transient
    public String getEstadoTexto() {
        return estado == null ? "" : estado == 'P' ? "Pendiente" : estado == 'A' ? "Aprobado" : estado == 'E' ? "Entregado" : "Rechazado";
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    @XmlTransient
    public String getCompUsuCrea() {
        return compUsuCrea;
    }

    public void setCompUsuCrea(String compUsuCrea) {
        this.compUsuCrea = compUsuCrea;
    }

    @XmlTransient
    public Date getCompFechaCrea() {
        return compFechaCrea;
    }

    public void setCompFechaCrea(Date compFechaCrea) {
        this.compFechaCrea = compFechaCrea;
    }

    @XmlTransient
    public String getCompMaquinaCrea() {
        return compMaquinaCrea;
    }

    public void setCompMaquinaCrea(String compMaquinaCrea) {
        this.compMaquinaCrea = compMaquinaCrea;
    }

    @XmlTransient
    public String getCompIpCrea() {
        return compIpCrea;
    }

    public void setCompIpCrea(String compIpCrea) {
        this.compIpCrea = compIpCrea;
    }

    @XmlTransient
    public String getCompUsuMod() {
        return compUsuMod;
    }

    public void setCompUsuMod(String compUsuMod) {
        this.compUsuMod = compUsuMod;
    }

    @XmlTransient
    public Date getCompFechaMod() {
        return compFechaMod;
    }

    public void setCompFechaMod(Date compFechaMod) {
        this.compFechaMod = compFechaMod;
    }

    @XmlTransient
    public String getCompMaquinaMod() {
        return compMaquinaMod;
    }

    public void setCompMaquinaMod(String compMaquinaMod) {
        this.compMaquinaMod = compMaquinaMod;
    }

    @XmlTransient
    public String getCompIpMod() {
        return compIpMod;
    }

    public void setCompIpMod(String compIpMod) {
        this.compIpMod = compIpMod;
    }

    @XmlTransient
    public List<SieduObservacionCompromiso> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<SieduObservacionCompromiso> observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<SieduArchivoCompromiso> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<SieduArchivoCompromiso> archivos) {
        this.archivos = archivos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.sieduCompromisoPK);
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
        final SieduCompromiso other = (SieduCompromiso) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.resultadoEsperado, other.resultadoEsperado)) {
            return false;
        }
        if (!Objects.equals(this.sieduCompromisoPK, other.sieduCompromisoPK)) {
            return false;
        }
        if (!Objects.equals(this.investigacionExterna, other.investigacionExterna)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrega, other.fechaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (this.investigacionExterna.getIdInve() != null) {
            return this.investigacionExterna.getIdInve().equals(other.investigacionExterna.getIdInve());
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduCompromiso{" + "sieduCompromisoPK=" + sieduCompromisoPK + ", investigacionExterna=" + investigacionExterna + ", descripcion=" + descripcion + ", fechaEntrega=" + fechaEntrega + ", resultadoEsperado=" + resultadoEsperado + ", estado=" + estado + ", compUsuCrea=" + compUsuCrea + ", compFechaCrea=" + compFechaCrea + ", compMaquinaCrea=" + compMaquinaCrea + ", compIpCrea=" + compIpCrea + ", compUsuMod=" + compUsuMod + ", compFechaMod=" + compFechaMod + ", compMaquinaMod=" + compMaquinaMod + ", compIpMod=" + compIpMod + ", observaciones=" + observaciones + ", archivos=" + archivos + '}';
    }

}
