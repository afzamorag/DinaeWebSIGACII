/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "PROGRAMACION_PERIODOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramacionPeriodos.findAll", query = "SELECT p FROM ProgramacionPeriodos p"),
    @NamedQuery(name = "ProgramacionPeriodos.findByIdProgperiodo", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.idProgperiodo = :idProgperiodo"),
    @NamedQuery(name = "ProgramacionPeriodos.findByNroPeriodo", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.nroPeriodo = :nroPeriodo"),
    @NamedQuery(name = "ProgramacionPeriodos.findByFechaInicio", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ProgramacionPeriodos.findByFechaFinal", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "ProgramacionPeriodos.findByCreadoPor", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "ProgramacionPeriodos.findByminProbatorio", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.minProbatorio = :minProbatorio"),
    @NamedQuery(name = "ProgramacionPeriodos.findByFechaCreacion", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ProgramacionPeriodos.findByMaquinaCreacion", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "ProgramacionPeriodos.findByPromocion", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.promocion = :promocion"),
    @NamedQuery(name = "ProgramacionPeriodos.findByFinalizado", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.finalizado = :finalizado"),
    @NamedQuery(name = "ProgramacionPeriodos.findByPeriodoVigente", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.periodoVigente = :periodoVigente"),
    @NamedQuery(name = "ProgramacionPeriodos.findByModalidad", query = "SELECT p FROM ProgramacionPeriodos p WHERE p.modalidad = :modalidad")})
public class ProgramacionPeriodos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGPERIODO")
    private Integer idProgperiodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_PERIODO")
    private short nroPeriodo;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MIN_PROBATORIO")
    private BigDecimal minProbatorio;
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
    @Size(max = 20)
    @Column(name = "PROMOCION")
    private String promocion;
    @Size(max = 2)
    @Column(name = "FINALIZADO")
    private String finalizado;
    @Size(max = 2)
    @Column(name = "PERIODO_VIGENTE")
    private String periodoVigente;
    @Size(max = 30)
    @Column(name = "MODALIDAD")
    private String modalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prpeIdProgperiodo")
    private Collection<PeriodosAlumno> periodosAlumnoCollection;
    @JoinColumns({
        @JoinColumn(name = "COCU_ID_COMPCURSO", referencedColumnName = "ID_COMPCURSO"),
        @JoinColumn(name = "COCU_UNDE_CONSECUTIVO", referencedColumnName = "UNDE_CONSECUTIVO"),
        @JoinColumn(name = "COCU_UNDE_FUERZA", referencedColumnName = "UNDE_FUERZA"),
        @JoinColumn(name = "COCU_NRO_CURSO", referencedColumnName = "NRO_CURSO")})
    @ManyToOne(optional = false)
    private CompaniasCurso companiasCurso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prpeIdProgperiodo")
    private Collection<ProgramasPensums> programasPensumsCollection;

    public ProgramacionPeriodos() {
    }

    public ProgramacionPeriodos(Integer idProgperiodo) {
        this.idProgperiodo = idProgperiodo;
    }

    public ProgramacionPeriodos(Integer idProgperiodo, short nroPeriodo, Date fechaInicio, Date fechaFinal, BigDecimal minProbatorio, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idProgperiodo = idProgperiodo;
        this.nroPeriodo = nroPeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.minProbatorio = minProbatorio;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public Integer getIdProgperiodo() {
        return idProgperiodo;
    }

    public void setIdProgperiodo(Integer idProgperiodo) {
        this.idProgperiodo = idProgperiodo;
    }

    public short getNroPeriodo() {
        return nroPeriodo;
    }

    public void setNroPeriodo(short nroPeriodo) {
        this.nroPeriodo = nroPeriodo;
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

    public BigDecimal getMinProbatorio() {
        return minProbatorio;
    }

    public void setMinProbatorio(BigDecimal minProbatorio) {
        this.minProbatorio = minProbatorio;
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

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public String getPeriodoVigente() {
        return periodoVigente;
    }

    public void setPeriodoVigente(String periodoVigente) {
        this.periodoVigente = periodoVigente;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    @XmlTransient
    public Collection<PeriodosAlumno> getPeriodosAlumnoCollection() {
        return periodosAlumnoCollection;
    }

    public void setPeriodosAlumnoCollection(Collection<PeriodosAlumno> periodosAlumnoCollection) {
        this.periodosAlumnoCollection = periodosAlumnoCollection;
    }

    public CompaniasCurso getCompaniasCurso() {
        return companiasCurso;
    }

    public void setCompaniasCurso(CompaniasCurso companiasCurso) {
        this.companiasCurso = companiasCurso;
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
        hash += (idProgperiodo != null ? idProgperiodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionPeriodos)) {
            return false;
        }
        ProgramacionPeriodos other = (ProgramacionPeriodos) object;
        if ((this.idProgperiodo == null && other.idProgperiodo != null) || (this.idProgperiodo != null && !this.idProgperiodo.equals(other.idProgperiodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.ProgramacionPeriodos[ idProgperiodo=" + idProgperiodo + " ]";
    }

}
