/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "HORAS_DOCENTE_SIGAC")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = HorasDocenteSigac.FIND_BY_FECHA, query = "SELECT s FROM HorasDocenteSigac s WHERE FUNC('TO_CHAR', s.fechaImparte, 'DD/MON/YYYY') BETWEEN :fecha_ini AND :fecha_fin AND FUNC('TO_CHAR', s.fechaCrea, 'DD/MON/YYYY') BETWEEN :fecha_ini AND :fecha_crea AND s.identificacion = :identificacion AND s.codEscuela = :codEscuela ORDER BY s.fechaImparte"),
    @NamedQuery(name = HorasDocenteSigac.FIND_ALL_BY_FECHA, query = "SELECT s FROM HorasDocenteSigac s WHERE FUNC('TO_CHAR', s.fechaImparte, 'DD/MON/YYYY') BETWEEN :fecha_ini AND :fecha_fin AND FUNC('TO_CHAR', s.fechaCrea, 'DD/MON/YYYY') BETWEEN :fecha_ini AND :fecha_crea ORDER BY s.escuela, s.proceso, s.nombres, s.fechaImparte, s.semana"),
    @NamedQuery(name = HorasDocenteSigac.FIND_ALL, query = "SELECT s FROM HorasDocenteSigac s")})
@XmlRootElement

public class HorasDocenteSigac implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_FECHA = "HorasDocenteSigac.findByFecha";
    public static final String FIND_ALL_BY_FECHA = "HorasDocenteSigac.findAllByFecha";
    public static final String FIND_ALL = "HorasDocenteSigac.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Id    
    @Column(name = "ID")
    private String id;
    @Column(name = "IDENTIFICACION", nullable = false)
    private Long identificacion;
    @Column(name = "NOMBRES", nullable = false)
    private String nombres;
    @Column(name = "PROCESO", nullable = false)
    private String proceso;
    @Column(name = "CURSO", nullable = false)
    private String curso;
    @Column(name = "SECCION", nullable = false)
    private String seccion;
    @Column(name = "ESCUELA", nullable = false)
    private String escuela;
    @Column(name = "COD_ESCUELA", nullable = false)
    private Long codEscuela;
    @Column(name = "PROGRAMA", nullable = false)
    private String programa;
    @Column(name = "ASIGNATURA", nullable = false)
    private String eventoAsignatura;
    @Column(name = "F_INI_PROGRAMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "F_FIN_PROGRAMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "F_IMPARTE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaImparte;
    @Column(name = "F_CREA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCrea;
    @Column(name = "HORAS", nullable = false)
    private Long horasDictadas;
    @Column(name = "HORAS_LIQUIDADAS", nullable = false)
    private Long horasLiquidadas;
    @Column(name = "SEMANA", nullable = false)
    private String semana;
    @Column(name = "MODALIDAD", nullable = false)
    private String modalidad;

    public HorasDocenteSigac() {
    }   

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public Long getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(Long codEscuela) {
        this.codEscuela = codEscuela;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getEventoAsignatura() {
        return eventoAsignatura;
    }

    public void setEventoAsignatura(String eventoAsignatura) {
        this.eventoAsignatura = eventoAsignatura;
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

    public Long getHorasDictadas() {
        return horasDictadas;
    }

    public void setHorasDictadas(Long horasDictadas) {
        this.horasDictadas = horasDictadas;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public Long getHorasLiquidadas() {
        return horasLiquidadas;
    }

    public void setHorasLiquidadas(Long horasLiquidadas) {
        this.horasLiquidadas = horasLiquidadas;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.identificacion);
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
        final HorasDocenteSigac other = (HorasDocenteSigac) obj;
        if (!Objects.equals(this.identificacion, other.identificacion)) {
            return false;
        }
        return true;
    }

    public Long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Date getFechaImparte() {
        return fechaImparte;
    }

    public void setFechaImparte(Date fechaImparte) {
        this.fechaImparte = fechaImparte;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
