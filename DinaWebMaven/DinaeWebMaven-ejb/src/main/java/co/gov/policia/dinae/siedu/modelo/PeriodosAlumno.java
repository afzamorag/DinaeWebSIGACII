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
@Table(name = "PERIODOS_ALUMNO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodosAlumno.findAll", query = "SELECT p FROM PeriodosAlumno p"),
    @NamedQuery(name = "PeriodosAlumno.findByIdPeriodo", query = "SELECT p FROM PeriodosAlumno p WHERE p.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "PeriodosAlumno.findByValidaArco", query = "SELECT p FROM PeriodosAlumno p WHERE p.validaArco = :validaArco"),
    @NamedQuery(name = "PeriodosAlumno.findBySeccion", query = "SELECT p FROM PeriodosAlumno p WHERE p.seccion = :seccion"),
    @NamedQuery(name = "PeriodosAlumno.findByEp1Periodo", query = "SELECT p FROM PeriodosAlumno p WHERE p.ep1Periodo = :ep1Periodo"),
    @NamedQuery(name = "PeriodosAlumno.findByLista", query = "SELECT p FROM PeriodosAlumno p WHERE p.lista = :lista"),
    @NamedQuery(name = "PeriodosAlumno.findByNroMatperdida", query = "SELECT p FROM PeriodosAlumno p WHERE p.nroMatperdida = :nroMatperdida"),
    @NamedQuery(name = "PeriodosAlumno.findByPromedioAcad", query = "SELECT p FROM PeriodosAlumno p WHERE p.promedioAcad = :promedioAcad"),
    @NamedQuery(name = "PeriodosAlumno.findByPromedioGralp", query = "SELECT p FROM PeriodosAlumno p WHERE p.promedioGralp = :promedioGralp"),
    @NamedQuery(name = "PeriodosAlumno.findByPuestoAcademico", query = "SELECT p FROM PeriodosAlumno p WHERE p.puestoAcademico = :puestoAcademico"),
    @NamedQuery(name = "PeriodosAlumno.findByPuestoCompania", query = "SELECT p FROM PeriodosAlumno p WHERE p.puestoCompania = :puestoCompania"),
    @NamedQuery(name = "PeriodosAlumno.findByPuestoSeccion", query = "SELECT p FROM PeriodosAlumno p WHERE p.puestoSeccion = :puestoSeccion"),
    @NamedQuery(name = "PeriodosAlumno.findByPuestoCurso", query = "SELECT p FROM PeriodosAlumno p WHERE p.puestoCurso = :puestoCurso"),
    @NamedQuery(name = "PeriodosAlumno.findByEmplConsecutivo", query = "SELECT p FROM PeriodosAlumno p WHERE p.emplConsecutivo = :emplConsecutivo"),
    @NamedQuery(name = "PeriodosAlumno.findByEmplUndeConsecutivo", query = "SELECT p FROM PeriodosAlumno p WHERE p.emplUndeConsecutivo = :emplUndeConsecutivo"),
    @NamedQuery(name = "PeriodosAlumno.findByEmplUndeFuerza", query = "SELECT p FROM PeriodosAlumno p WHERE p.emplUndeFuerza = :emplUndeFuerza"),
    @NamedQuery(name = "PeriodosAlumno.findByCreadoPor", query = "SELECT p FROM PeriodosAlumno p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "PeriodosAlumno.findByFechaCreacion", query = "SELECT p FROM PeriodosAlumno p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PeriodosAlumno.findByMaquinaCreacion", query = "SELECT p FROM PeriodosAlumno p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "PeriodosAlumno.findByGradAlfabetico", query = "SELECT p FROM PeriodosAlumno p WHERE p.gradAlfabetico = :gradAlfabetico"),
    @NamedQuery(name = "PeriodosAlumno.findByActivo", query = "SELECT p FROM PeriodosAlumno p WHERE p.activo = :activo"),
    @NamedQuery(name = "PeriodosAlumno.findByNroRemedial", query = "SELECT p FROM PeriodosAlumno p WHERE p.nroRemedial = :nroRemedial"),
    @NamedQuery(name = "PeriodosAlumno.findByValorDesempeno", query = "SELECT p FROM PeriodosAlumno p WHERE p.valorDesempeno = :valorDesempeno"),
    @NamedQuery(name = "PeriodosAlumno.findByNroSanciones", query = "SELECT p FROM PeriodosAlumno p WHERE p.nroSanciones = :nroSanciones"),
    @NamedQuery(name = "PeriodosAlumno.findByGrupoEtnico", query = "SELECT p FROM PeriodosAlumno p WHERE p.grupoEtnico = :grupoEtnico"),
    @NamedQuery(name = "PeriodosAlumno.findByAprobo", query = "SELECT p FROM PeriodosAlumno p WHERE p.aprobo = :aprobo"),
    @NamedQuery(name = "PeriodosAlumno.findByNroDiasanciones", query = "SELECT p FROM PeriodosAlumno p WHERE p.nroDiasanciones = :nroDiasanciones"),
    @NamedQuery(name = "PeriodosAlumno.findByActualizadoPor", query = "SELECT p FROM PeriodosAlumno p WHERE p.actualizadoPor = :actualizadoPor"),
    @NamedQuery(name = "PeriodosAlumno.findByFechaActualiza", query = "SELECT p FROM PeriodosAlumno p WHERE p.fechaActualiza = :fechaActualiza")})
public class PeriodosAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERIODO")
    private BigDecimal idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "VALIDA_ARCO")
    private String validaArco;
    @Column(name = "SECCION")
    private Short seccion;
    @Column(name = "EP1_PERIODO")
    private BigDecimal ep1Periodo;
    @Size(max = 2)
    @Column(name = "LISTA")
    private String lista;
    @Column(name = "NRO_MATPERDIDA")
    private Short nroMatperdida;
    @Column(name = "PROMEDIO_ACAD")
    private BigDecimal promedioAcad;
    @Column(name = "PROMEDIO_GRALP")
    private BigDecimal promedioGralp;
    @Column(name = "PUESTO_ACADEMICO")
    private Short puestoAcademico;
    @Column(name = "PUESTO_COMPANIA")
    private Short puestoCompania;
    @Column(name = "PUESTO_SECCION")
    private Short puestoSeccion;
    @Column(name = "PUESTO_CURSO")
    private Short puestoCurso;
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
    @Size(max = 5)
    @Column(name = "GRAD_ALFABETICO")
    private String gradAlfabetico;
    @Size(max = 2)
    @Column(name = "ACTIVO")
    private String activo;
    @Column(name = "NRO_REMEDIAL")
    private Short nroRemedial;
    @Column(name = "VALOR_DESEMPENO")
    private Short valorDesempeno;
    @Column(name = "NRO_SANCIONES")
    private BigInteger nroSanciones;
    @Size(max = 30)
    @Column(name = "GRUPO_ETNICO")
    private String grupoEtnico;
    @Size(max = 2)
    @Column(name = "APROBO")
    private String aprobo;
    @Column(name = "NRO_DIASANCIONES")
    private BigInteger nroDiasanciones;
    @Size(max = 30)
    @Column(name = "ACTUALIZADO_POR")
    private String actualizadoPor;
    @Column(name = "FECHA_ACTUALIZA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualiza;
    @JoinColumn(name = "PRPE_ID_PROGPERIODO", referencedColumnName = "ID_PROGPERIODO")
    @ManyToOne(optional = false)
    private ProgramacionPeriodos prpeIdProgperiodo;
    @JoinColumn(name = "PEEX_ID_ALUMEXTERNO", referencedColumnName = "ID_ALUMEXTERNO")
    @ManyToOne
    private PersonalExternos peexIdAlumexterno;
    @OneToMany(mappedBy = "pealIdPeriodo")
    private Collection<ProgramacionAlumnos> programacionAlumnosCollection;

    public PeriodosAlumno() {
    }

    public PeriodosAlumno(BigDecimal idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public PeriodosAlumno(BigDecimal idPeriodo, String validaArco, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idPeriodo = idPeriodo;
        this.validaArco = validaArco;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(BigDecimal idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getValidaArco() {
        return validaArco;
    }

    public void setValidaArco(String validaArco) {
        this.validaArco = validaArco;
    }

    public Short getSeccion() {
        return seccion;
    }

    public void setSeccion(Short seccion) {
        this.seccion = seccion;
    }

    public BigDecimal getEp1Periodo() {
        return ep1Periodo;
    }

    public void setEp1Periodo(BigDecimal ep1Periodo) {
        this.ep1Periodo = ep1Periodo;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public Short getNroMatperdida() {
        return nroMatperdida;
    }

    public void setNroMatperdida(Short nroMatperdida) {
        this.nroMatperdida = nroMatperdida;
    }

    public BigDecimal getPromedioAcad() {
        return promedioAcad;
    }

    public void setPromedioAcad(BigDecimal promedioAcad) {
        this.promedioAcad = promedioAcad;
    }

    public BigDecimal getPromedioGralp() {
        return promedioGralp;
    }

    public void setPromedioGralp(BigDecimal promedioGralp) {
        this.promedioGralp = promedioGralp;
    }

    public Short getPuestoAcademico() {
        return puestoAcademico;
    }

    public void setPuestoAcademico(Short puestoAcademico) {
        this.puestoAcademico = puestoAcademico;
    }

    public Short getPuestoCompania() {
        return puestoCompania;
    }

    public void setPuestoCompania(Short puestoCompania) {
        this.puestoCompania = puestoCompania;
    }

    public Short getPuestoSeccion() {
        return puestoSeccion;
    }

    public void setPuestoSeccion(Short puestoSeccion) {
        this.puestoSeccion = puestoSeccion;
    }

    public Short getPuestoCurso() {
        return puestoCurso;
    }

    public void setPuestoCurso(Short puestoCurso) {
        this.puestoCurso = puestoCurso;
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

    public String getGradAlfabetico() {
        return gradAlfabetico;
    }

    public void setGradAlfabetico(String gradAlfabetico) {
        this.gradAlfabetico = gradAlfabetico;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Short getNroRemedial() {
        return nroRemedial;
    }

    public void setNroRemedial(Short nroRemedial) {
        this.nroRemedial = nroRemedial;
    }

    public Short getValorDesempeno() {
        return valorDesempeno;
    }

    public void setValorDesempeno(Short valorDesempeno) {
        this.valorDesempeno = valorDesempeno;
    }

    public BigInteger getNroSanciones() {
        return nroSanciones;
    }

    public void setNroSanciones(BigInteger nroSanciones) {
        this.nroSanciones = nroSanciones;
    }

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public String getAprobo() {
        return aprobo;
    }

    public void setAprobo(String aprobo) {
        this.aprobo = aprobo;
    }

    public BigInteger getNroDiasanciones() {
        return nroDiasanciones;
    }

    public void setNroDiasanciones(BigInteger nroDiasanciones) {
        this.nroDiasanciones = nroDiasanciones;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(String actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    public ProgramacionPeriodos getPrpeIdProgperiodo() {
        return prpeIdProgperiodo;
    }

    public void setPrpeIdProgperiodo(ProgramacionPeriodos prpeIdProgperiodo) {
        this.prpeIdProgperiodo = prpeIdProgperiodo;
    }

    public PersonalExternos getPeexIdAlumexterno() {
        return peexIdAlumexterno;
    }

    public void setPeexIdAlumexterno(PersonalExternos peexIdAlumexterno) {
        this.peexIdAlumexterno = peexIdAlumexterno;
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
        hash += (idPeriodo != null ? idPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodosAlumno)) {
            return false;
        }
        PeriodosAlumno other = (PeriodosAlumno) object;
        if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.PeriodosAlumno[ idPeriodo=" + idPeriodo + " ]";
    }
    
}
