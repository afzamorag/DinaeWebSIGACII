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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "COMPANIAS_CURSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompaniasCurso.findAll", query = "SELECT c FROM CompaniasCurso c"),
    @NamedQuery(name = "CompaniasCurso.findByIdCompcurso", query = "SELECT c FROM CompaniasCurso c WHERE c.companiasCursoPK.idCompcurso = :idCompcurso"),
    @NamedQuery(name = "CompaniasCurso.findByNroCurso", query = "SELECT c FROM CompaniasCurso c WHERE c.companiasCursoPK.nroCurso = :nroCurso"),
    @NamedQuery(name = "CompaniasCurso.findByUndeConsecutivo", query = "SELECT c FROM CompaniasCurso c WHERE c.companiasCursoPK.undeConsecutivo = :undeConsecutivo"),
    @NamedQuery(name = "CompaniasCurso.findByUndeFuerza", query = "SELECT c FROM CompaniasCurso c WHERE c.companiasCursoPK.undeFuerza = :undeFuerza"),
    @NamedQuery(name = "CompaniasCurso.findByDescripcion", query = "SELECT c FROM CompaniasCurso c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CompaniasCurso.findByFechaInicio", query = "SELECT c FROM CompaniasCurso c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "CompaniasCurso.findByFechaTermina", query = "SELECT c FROM CompaniasCurso c WHERE c.fechaTermina = :fechaTermina"),
    @NamedQuery(name = "CompaniasCurso.findByNroAlinician", query = "SELECT c FROM CompaniasCurso c WHERE c.nroAlinician = :nroAlinician"),
    @NamedQuery(name = "CompaniasCurso.findByNroAlterminan", query = "SELECT c FROM CompaniasCurso c WHERE c.nroAlterminan = :nroAlterminan"),
    @NamedQuery(name = "CompaniasCurso.findByCreadoPor", query = "SELECT c FROM CompaniasCurso c WHERE c.creadoPor = :creadoPor"),
    @NamedQuery(name = "CompaniasCurso.findByFechaCreacion", query = "SELECT c FROM CompaniasCurso c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "CompaniasCurso.findByMaquinaCreacion", query = "SELECT c FROM CompaniasCurso c WHERE c.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "CompaniasCurso.findByCodEscuela", query = "SELECT c FROM CompaniasCurso c WHERE c.codEscuela = :codEscuela"),
    @NamedQuery(name = "CompaniasCurso.findByPracIdProgramaca", query = "SELECT c FROM CompaniasCurso c WHERE c.pracIdProgramaca = :pracIdProgramaca"),
    @NamedQuery(name = "CompaniasCurso.findByCreditos", query = "SELECT c FROM CompaniasCurso c WHERE c.creditos = :creditos"),
    @NamedQuery(name = "CompaniasCurso.findByPiscina", query = "SELECT c FROM CompaniasCurso c WHERE c.piscina = :piscina"),
    @NamedQuery(name = "CompaniasCurso.findByAlojamiento", query = "SELECT c FROM CompaniasCurso c WHERE c.alojamiento = :alojamiento"),
    @NamedQuery(name = "CompaniasCurso.findByTerminado", query = "SELECT c FROM CompaniasCurso c WHERE c.terminado = :terminado")})
public class CompaniasCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompaniasCursoPK companiasCursoPK;
    @Size(max = 60)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_TERMINA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTermina;
    @Column(name = "NRO_ALINICIAN")
    private BigInteger nroAlinician;
    @Column(name = "NRO_ALTERMINAN")
    private BigInteger nroAlterminan;
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
    @Column(name = "COD_ESCUELA")
    private BigInteger codEscuela;
    @Column(name = "PRAC_ID_PROGRAMACA")
    private Short pracIdProgramaca;
    @Size(max = 2)
    @Column(name = "CREDITOS")
    private String creditos;
    @Size(max = 2)
    @Column(name = "PISCINA")
    private String piscina;
    @Size(max = 2)
    @Column(name = "ALOJAMIENTO")
    private String alojamiento;
    @Size(max = 2)
    @Column(name = "TERMINADO")
    private String terminado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiasCurso")
    private Collection<ProgramacionPeriodos> programacionPeriodosCollection;

    public CompaniasCurso() {
    }

    public CompaniasCurso(CompaniasCursoPK companiasCursoPK) {
        this.companiasCursoPK = companiasCursoPK;
    }

    public CompaniasCurso(CompaniasCursoPK companiasCursoPK, Date fechaInicio, Date fechaTermina, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.companiasCursoPK = companiasCursoPK;
        this.fechaInicio = fechaInicio;
        this.fechaTermina = fechaTermina;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public CompaniasCurso(int idCompcurso, short nroCurso, BigInteger undeConsecutivo, short undeFuerza) {
        this.companiasCursoPK = new CompaniasCursoPK(idCompcurso, nroCurso, undeConsecutivo, undeFuerza);
    }

    public CompaniasCursoPK getCompaniasCursoPK() {
        return companiasCursoPK;
    }

    public void setCompaniasCursoPK(CompaniasCursoPK companiasCursoPK) {
        this.companiasCursoPK = companiasCursoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermina() {
        return fechaTermina;
    }

    public void setFechaTermina(Date fechaTermina) {
        this.fechaTermina = fechaTermina;
    }

    public BigInteger getNroAlinician() {
        return nroAlinician;
    }

    public void setNroAlinician(BigInteger nroAlinician) {
        this.nroAlinician = nroAlinician;
    }

    public BigInteger getNroAlterminan() {
        return nroAlterminan;
    }

    public void setNroAlterminan(BigInteger nroAlterminan) {
        this.nroAlterminan = nroAlterminan;
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

    public BigInteger getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(BigInteger codEscuela) {
        this.codEscuela = codEscuela;
    }

    public Short getPracIdProgramaca() {
        return pracIdProgramaca;
    }

    public void setPracIdProgramaca(Short pracIdProgramaca) {
        this.pracIdProgramaca = pracIdProgramaca;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getPiscina() {
        return piscina;
    }

    public void setPiscina(String piscina) {
        this.piscina = piscina;
    }

    public String getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(String alojamiento) {
        this.alojamiento = alojamiento;
    }

    public String getTerminado() {
        return terminado;
    }

    public void setTerminado(String terminado) {
        this.terminado = terminado;
    }

    @XmlTransient
    public Collection<ProgramacionPeriodos> getProgramacionPeriodosCollection() {
        return programacionPeriodosCollection;
    }

    public void setProgramacionPeriodosCollection(Collection<ProgramacionPeriodos> programacionPeriodosCollection) {
        this.programacionPeriodosCollection = programacionPeriodosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companiasCursoPK != null ? companiasCursoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompaniasCurso)) {
            return false;
        }
        CompaniasCurso other = (CompaniasCurso) object;
        if ((this.companiasCursoPK == null && other.companiasCursoPK != null) || (this.companiasCursoPK != null && !this.companiasCursoPK.equals(other.companiasCursoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.CompaniasCurso[ companiasCursoPK=" + companiasCursoPK + " ]";
    }
    
}
