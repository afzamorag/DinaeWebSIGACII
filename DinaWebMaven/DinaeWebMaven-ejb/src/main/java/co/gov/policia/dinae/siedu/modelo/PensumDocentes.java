/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "PENSUM_DOCENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PensumDocentes.findAll", query = "SELECT p FROM PensumDocentes p"),
    @NamedQuery(name = "PensumDocentes.findByIdPendocente", query = "SELECT p FROM PensumDocentes p WHERE p.idPendocente = :idPendocente"),
    @NamedQuery(name = "PensumDocentes.findByValidaArco", query = "SELECT p FROM PensumDocentes p WHERE p.validaArco = :validaArco"),
    @NamedQuery(name = "PensumDocentes.findByEmplConsecutivo", query = "SELECT p FROM PensumDocentes p WHERE p.emplConsecutivo = :emplConsecutivo"),
    @NamedQuery(name = "PensumDocentes.findByEmplUndeConsecutivo", query = "SELECT p FROM PensumDocentes p WHERE p.emplUndeConsecutivo = :emplUndeConsecutivo"),
    @NamedQuery(name = "PensumDocentes.findByEmplUndeFuerza", query = "SELECT p FROM PensumDocentes p WHERE p.emplUndeFuerza = :emplUndeFuerza"),
    @NamedQuery(name = "PensumDocentes.findByCreadoPor", query = "SELECT p FROM PensumDocentes p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "PensumDocentes.findByFechaCreacion", query = "SELECT p FROM PensumDocentes p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PensumDocentes.findByMaquinaCreacion", query = "SELECT p FROM PensumDocentes p WHERE p.maquinaCreacion = :maquinaCreacion")})
public class PensumDocentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PENDOCENTE")
    private Integer idPendocente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "VALIDA_ARCO")
    private String validaArco;
    @Column(name = "EMPL_CONSECUTIVO")
    private BigInteger emplConsecutivo;
    @Column(name = "EMPL_UNDE_CONSECUTIVO")
    private BigInteger emplUndeConsecutivo;
    @Column(name = "EMPL_UNDE_FUERZA")
    private Short emplUndeFuerza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CREADO_POR")
    private String creadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "MAQUINA_CREACION")
    private String maquinaCreacion;
    @JoinColumn(name = "PEEX_ID_ALUMEXTERNO", referencedColumnName = "ID_ALUMEXTERNO")
    @ManyToOne
    private PersonalExternos peexIdAlumexterno;
    @JoinColumn(name = "PENS_ID_PENSUM", referencedColumnName = "ID_PENSUM")
    @ManyToOne(optional = false)
    private Pensums pensIdPensum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedoIdPendocente")
    private Collection<ProgramasPensums> programasPensumsCollection;

    public PensumDocentes() {
    }

    public PensumDocentes(Integer idPendocente) {
        this.idPendocente = idPendocente;
    }

    public PensumDocentes(Integer idPendocente, String validaArco, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idPendocente = idPendocente;
        this.validaArco = validaArco;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public Integer getIdPendocente() {
        return idPendocente;
    }

    public void setIdPendocente(Integer idPendocente) {
        this.idPendocente = idPendocente;
    }

    public String getValidaArco() {
        return validaArco;
    }

    public void setValidaArco(String validaArco) {
        this.validaArco = validaArco;
    }

    public BigInteger getEmplConsecutivo() {
        return emplConsecutivo;
    }

    public void setEmplConsecutivo(BigInteger emplConsecutivo) {
        this.emplConsecutivo = emplConsecutivo;
    }

    public BigInteger getEmplUndeConsecutivo() {
        return emplUndeConsecutivo;
    }

    public void setEmplUndeConsecutivo(BigInteger emplUndeConsecutivo) {
        this.emplUndeConsecutivo = emplUndeConsecutivo;
    }

    public Short getEmplUndeFuerza() {
        return emplUndeFuerza;
    }

    public void setEmplUndeFuerza(Short emplUndeFuerza) {
        this.emplUndeFuerza = emplUndeFuerza;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMaquinaCreacion() {
        return maquinaCreacion;
    }

    public void setMaquinaCreacion(String maquinaCreacion) {
        this.maquinaCreacion = maquinaCreacion;
    }

    public PersonalExternos getPeexIdAlumexterno() {
        return peexIdAlumexterno;
    }

    public void setPeexIdAlumexterno(PersonalExternos peexIdAlumexterno) {
        this.peexIdAlumexterno = peexIdAlumexterno;
    }

    public Pensums getPensIdPensum() {
        return pensIdPensum;
    }

    public void setPensIdPensum(Pensums pensIdPensum) {
        this.pensIdPensum = pensIdPensum;
    }

    @XmlTransient
    public Collection<ProgramasPensums> getProgramasPensumsCollection() {
        return programasPensumsCollection;
    }

    public void setProgramasPensumsCollection(Collection<ProgramasPensums> programasPensumsCollection) {
        this.programasPensumsCollection = programasPensumsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPendocente != null ? idPendocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PensumDocentes)) {
            return false;
        }
        PensumDocentes other = (PensumDocentes) object;
        if ((this.idPendocente == null && other.idPendocente != null) || (this.idPendocente != null && !this.idPendocente.equals(other.idPendocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.PensumDocentes[ idPendocente=" + idPendocente + " ]";
    }
    
}
