/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PROGRAMAS_PENSUMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramasPensums.findAll", query = "SELECT p FROM ProgramasPensums p"),
    @NamedQuery(name = "ProgramasPensums.findByIdProgpensum", query = "SELECT p FROM ProgramasPensums p WHERE p.idProgpensum = :idProgpensum"),
    @NamedQuery(name = "ProgramasPensums.findBySeccionPrograma", query = "SELECT p FROM ProgramasPensums p WHERE p.seccionPrograma = :seccionPrograma"),
    @NamedQuery(name = "ProgramasPensums.findByFechaInicio", query = "SELECT p FROM ProgramasPensums p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ProgramasPensums.findByFechaFinal", query = "SELECT p FROM ProgramasPensums p WHERE p.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "ProgramasPensums.findByCreadoPor", query = "SELECT p FROM ProgramasPensums p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "ProgramasPensums.findByFechaCreacion", query = "SELECT p FROM ProgramasPensums p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ProgramasPensums.findByMaquinaCreacion", query = "SELECT p FROM ProgramasPensums p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "ProgramasPensums.findByCodSeccional", query = "SELECT p FROM ProgramasPensums p WHERE p.codSeccional = :codSeccional"),
    @NamedQuery(name = "ProgramasPensums.findByNroCurso", query = "SELECT p FROM ProgramasPensums p WHERE p.nroCurso = :nroCurso"),
    @NamedQuery(name = "ProgramasPensums.findByVigente", query = "SELECT p FROM ProgramasPensums p WHERE p.vigente = :vigente"),
    @NamedQuery(name = "ProgramasPensums.findByEvaluado", query = "SELECT p FROM ProgramasPensums p WHERE p.evaluado = :evaluado"),
    @NamedQuery(name = "ProgramasPensums.findByHoras", query = "SELECT p FROM ProgramasPensums p WHERE p.horas = :horas"),
    @NamedQuery(name = "ProgramasPensums.findByClaseDocente", query = "SELECT p FROM ProgramasPensums p WHERE p.claseDocente = :claseDocente"),
    @NamedQuery(name = "ProgramasPensums.findByHorasTutoria", query = "SELECT p FROM ProgramasPensums p WHERE p.horasTutoria = :horasTutoria"),
    @NamedQuery(name = "ProgramasPensums.findByLineIdLineamiento", query = "SELECT p FROM ProgramasPensums p WHERE p.lineIdLineamiento = :lineIdLineamiento"),
    @NamedQuery(name = "ProgramasPensums.findByPedoPensIdPensum", query = "SELECT p FROM ProgramasPensums p WHERE p.pedoPensIdPensum = :pedoPensIdPensum"),
    @NamedQuery(name = "ProgramasPensums.findByModalidad", query = "SELECT p FROM ProgramasPensums p WHERE p.modalidad = :modalidad")})
public class ProgramasPensums implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGPENSUM")
    private BigDecimal idProgpensum;
    @Column(name = "SECCION_PROGRAMA")
    private Short seccionPrograma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
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
    @Column(name = "COD_SECCIONAL")
    private BigInteger codSeccional;
    @Column(name = "NRO_CURSO")
    private BigInteger nroCurso;
    @Size(max = 2)
    @Column(name = "VIGENTE")
    private String vigente;
    @Size(max = 2)
    @Column(name = "EVALUADO")
    private String evaluado;
    @Column(name = "HORAS")
    private BigInteger horas;
    @Size(max = 10)
    @Column(name = "CLASE_DOCENTE")
    private String claseDocente;
    @Column(name = "HORAS_TUTORIA")
    private BigInteger horasTutoria;
    @Column(name = "LINE_ID_LINEAMIENTO")
    private BigInteger lineIdLineamiento;
    @Column(name = "PEDO_PENS_ID_PENSUM")
    private BigInteger pedoPensIdPensum;
    @Column(name = "MODALIDAD")
    private Short modalidad;
    @JoinColumn(name = "PRPE_ID_PROGPERIODO", referencedColumnName = "ID_PROGPERIODO")
    @ManyToOne(optional = false)
    private ProgramacionPeriodos prpeIdProgperiodo;
    @JoinColumn(name = "PEDO_ID_PENDOCENTE", referencedColumnName = "ID_PENDOCENTE")
    @ManyToOne(optional = false)
    private PensumDocentes pedoIdPendocente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prpnIdProgpensum")
    private Collection<ProgramacionAlumnos> programacionAlumnosCollection;

    public ProgramasPensums() {
    }

    public ProgramasPensums(BigDecimal idProgpensum) {
        this.idProgpensum = idProgpensum;
    }

    public ProgramasPensums(BigDecimal idProgpensum, Date fechaInicio, Date fechaFinal, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idProgpensum = idProgpensum;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdProgpensum() {
        return idProgpensum;
    }

    public void setIdProgpensum(BigDecimal idProgpensum) {
        this.idProgpensum = idProgpensum;
    }

    public Short getSeccionPrograma() {
        return seccionPrograma;
    }

    public void setSeccionPrograma(Short seccionPrograma) {
        this.seccionPrograma = seccionPrograma;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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

    public BigInteger getCodSeccional() {
        return codSeccional;
    }

    public void setCodSeccional(BigInteger codSeccional) {
        this.codSeccional = codSeccional;
    }

    public BigInteger getNroCurso() {
        return nroCurso;
    }

    public void setNroCurso(BigInteger nroCurso) {
        this.nroCurso = nroCurso;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public String getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
    }

    public BigInteger getHoras() {
        return horas;
    }

    public void setHoras(BigInteger horas) {
        this.horas = horas;
    }

    public String getClaseDocente() {
        return claseDocente;
    }

    public void setClaseDocente(String claseDocente) {
        this.claseDocente = claseDocente;
    }

    public BigInteger getHorasTutoria() {
        return horasTutoria;
    }

    public void setHorasTutoria(BigInteger horasTutoria) {
        this.horasTutoria = horasTutoria;
    }

    public BigInteger getLineIdLineamiento() {
        return lineIdLineamiento;
    }

    public void setLineIdLineamiento(BigInteger lineIdLineamiento) {
        this.lineIdLineamiento = lineIdLineamiento;
    }

    public BigInteger getPedoPensIdPensum() {
        return pedoPensIdPensum;
    }

    public void setPedoPensIdPensum(BigInteger pedoPensIdPensum) {
        this.pedoPensIdPensum = pedoPensIdPensum;
    }

    public Short getModalidad() {
        return modalidad;
    }

    public void setModalidad(Short modalidad) {
        this.modalidad = modalidad;
    }

    public ProgramacionPeriodos getPrpeIdProgperiodo() {
        return prpeIdProgperiodo;
    }

    public void setPrpeIdProgperiodo(ProgramacionPeriodos prpeIdProgperiodo) {
        this.prpeIdProgperiodo = prpeIdProgperiodo;
    }

    public PensumDocentes getPedoIdPendocente() {
        return pedoIdPendocente;
    }

    public void setPedoIdPendocente(PensumDocentes pedoIdPendocente) {
        this.pedoIdPendocente = pedoIdPendocente;
    }

    @XmlTransient
    public Collection<ProgramacionAlumnos> getProgramacionAlumnosCollection() {
        return programacionAlumnosCollection;
    }

    public void setProgramacionAlumnosCollection(Collection<ProgramacionAlumnos> programacionAlumnosCollection) {
        this.programacionAlumnosCollection = programacionAlumnosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgpensum != null ? idProgpensum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramasPensums)) {
            return false;
        }
        ProgramasPensums other = (ProgramasPensums) object;
        if ((this.idProgpensum == null && other.idProgpensum != null) || (this.idProgpensum != null && !this.idProgpensum.equals(other.idProgpensum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.ProgramasPensums[ idProgpensum=" + idProgpensum + " ]";
    }
    
}
