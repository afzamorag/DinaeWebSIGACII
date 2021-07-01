/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "SIEDU_PROGRAMACION_DOCENTE")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = SieduProgramacionDocente.FIND_ALL, query = "SELECT a FROM SieduProgramacionDocente a"),
    @NamedQuery(name = SieduProgramacionDocente.FIND_BY_IDENTIFICACION, query = "SELECT a FROM SieduProgramacionDocente a WHERE a.identificacion = :identificacion"),
    @NamedQuery(name = SieduProgramacionDocente.FIND_BY_IDENTIFICACION_UNIDAD, query = "SELECT a FROM SieduProgramacionDocente a WHERE a.identificacion = :identificacion AND a.codEscuela = :codEscuela"),
    @NamedQuery(name = SieduProgramacionDocente.FIND_BY_IDENTIFICACION_VIGENTE, query = "SELECT a FROM SieduProgramacionDocente a WHERE a.identificacion = :identificacion AND a.vigente = :vigente"),
    @NamedQuery(name = SieduProgramacionDocente.FIND_FECHAS_PROG_BY_ID_PROG_DOCENTE, query = "SELECT NEW co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO(a.fechaFin, a.fechaInicio) FROM SieduProgramacionDocente a WHERE a.idProgDocente = :idProgDocente")    
})
public class SieduProgramacionDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_IDENTIFICACION = "SieduProgramacionDocente.findByIdentificacion";
    public static final String FIND_ALL = "SieduProgramacionDocente.findAll";
    public static final String FIND_BY_IDENTIFICACION_UNIDAD = "SieduProgramacionDocente.findByIdentificacionUnidad";
    public static final String FIND_BY_IDENTIFICACION_VIGENTE = "SieduProgramacionDocente.findByIdentificacionVigente";
    public static final String FIND_FECHAS_PROG_BY_ID_PROG_DOCENTE = "SieduProgramacionDocente.findFechasProgByIdProgDocnte";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROG_DOCENTE")
    private BigDecimal idProgDocente;
    @Column(name = "ESCUELA")
    private String escuela;
    @Column(name = "SIGLA_ESCUELA")
    private String siglaEscuela;
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "ASIGNATURA")
    private String asignatura;
    @Column(name = "COMPANIA")
    private String compania;
    @Column(name = "PERIODO")
    private Long periodo;
    @Column(name = "SECCION")
    private Long seccion;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "COD_ESCUELA")
    private Long codEscuela;
    @Column(name = "VIGENTE")
    private String vigente;
    @Column(name = "NRO_CURSO")
    private Long nroCurso;
    @Column(name = "CODIGO_PROGRAMA")
    private Long codigoPrograma;
    @Column(name = "MINIMO_PROBATORIO")
    private BigDecimal minimoProbatorio; 

    public SieduProgramacionDocente() {
    }

    public BigDecimal getIdProgDocente() {
        return idProgDocente;
    }

    public void setIdProgDocente(BigDecimal idProgDocente) {
        this.idProgDocente = idProgDocente;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getSiglaEscuela() {
        return siglaEscuela;
    }

    public void setSiglaEscuela(String siglaEscuela) {
        this.siglaEscuela = siglaEscuela;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    public Long getSeccion() {
        return seccion;
    }

    public void setSeccion(Long seccion) {
        this.seccion = seccion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(Long codEscuela) {
        this.codEscuela = codEscuela;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public Long getNroCurso() {
        return nroCurso;
    }

    public void setNroCurso(Long nroCurso) {
        this.nroCurso = nroCurso;
    }

    public Long getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(Long codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public BigDecimal getMinimoProbatorio() {
        return minimoProbatorio;
    }

    public void setMinimoProbatorio(BigDecimal minimoProbatorio) {
        this.minimoProbatorio = minimoProbatorio;
    }

}
