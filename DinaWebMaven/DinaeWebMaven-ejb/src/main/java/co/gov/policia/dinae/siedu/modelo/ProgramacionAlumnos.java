/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andres.zamorag
 */
@Entity
@Table(name = "PROGRAMACION_ALUMNOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramacionAlumnos.findAll", query = "SELECT p FROM ProgramacionAlumnos p"),
    @NamedQuery(name = "ProgramacionAlumnos.findById", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.idProgalumno = :idProgalumno"),
    @NamedQuery(name = "ProgramacionAlumnos.findByParcial1", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.parcial1 = :parcial1"),
    @NamedQuery(name = "ProgramacionAlumnos.findByParcial2", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.parcial2 = :parcial2"),
    @NamedQuery(name = "ProgramacionAlumnos.findByDefinitiva", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.definitiva = :definitiva"),
    @NamedQuery(name = "ProgramacionAlumnos.findByHabilita", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.habilita = :habilita"),
    @NamedQuery(name = "ProgramacionAlumnos.findByCreadoPor", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "ProgramacionAlumnos.findByFechaCreacion", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ProgramacionAlumnos.findByMaquinaCreacion", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "ProgramacionAlumnos.findByActualizadoPor", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.actualizadoPor = :actualizadoPor"),
    @NamedQuery(name = "ProgramacionAlumnos.findByFechaActualiza", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.fechaActualiza = :fechaActualiza"),
    @NamedQuery(name = "ProgramacionAlumnos.findByParcial3", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.parcial3 = :parcial3"),
@NamedQuery(name = "ProgramacionAlumnos.findByPrpnIdProgpensum", query = "SELECT p FROM ProgramacionAlumnos p WHERE p.prpnIdProgpensum = :prpnIdProgpensum")})
public class ProgramacionAlumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGALUMNO")
    private BigDecimal idProgalumno;
    @Column(name = "PARCIAL1")
    private BigDecimal parcial1;
    @Column(name = "PARCIAL2")
    private BigDecimal parcial2;
    @Column(name = "DEFINITIVA")
    private BigDecimal definitiva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "HABILITA")
    private String habilita;
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
    @Size(max = 30)
    @Column(name = "ACTUALIZADO_POR")
    private String actualizadoPor;
    @Column(name = "FECHA_ACTUALIZA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualiza;
    @Column(name = "PARCIAL3")
    private BigDecimal parcial3;
    @JoinColumn(name = "PRPN_ID_PROGPENSUM", referencedColumnName = "ID_PROGPENSUM")
    @ManyToOne(optional = false)
    private ProgramasPensums prpnIdProgpensum;
    @JoinColumn(name = "PEAL_ID_PERIODO", referencedColumnName = "ID_PERIODO")
    @ManyToOne
    private PeriodosAlumno pealIdPeriodo;

    public ProgramacionAlumnos() {
    }

    public ProgramacionAlumnos(BigDecimal idProgalumno) {
        this.idProgalumno = idProgalumno;
    }

    public ProgramacionAlumnos(BigDecimal idProgalumno, String habilita, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idProgalumno = idProgalumno;
        this.habilita = habilita;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdProgalumno() {
        return idProgalumno;
    }

    public void setIdProgalumno(BigDecimal idProgalumno) {
        this.idProgalumno = idProgalumno;
    }

    public BigDecimal getParcial1() {
        return parcial1;
    }

    public void setParcial1(BigDecimal parcial1) {
        this.parcial1 = parcial1;
    }

    public BigDecimal getParcial2() {
        return parcial2;
    }

    public void setParcial2(BigDecimal parcial2) {
        this.parcial2 = parcial2;
    }

    public BigDecimal getDefinitiva() {
        return definitiva;
    }

    public void setDefinitiva(BigDecimal definitiva) {
        this.definitiva = definitiva;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
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

    public BigDecimal getParcial3() {
        return parcial3;
    }

    public void setParcial3(BigDecimal parcial3) {
        this.parcial3 = parcial3;
    }

    public ProgramasPensums getPrpnIdProgpensum() {
        return prpnIdProgpensum;
    }

    public void setPrpnIdProgpensum(ProgramasPensums prpnIdProgpensum) {
        this.prpnIdProgpensum = prpnIdProgpensum;
    }

    public PeriodosAlumno getPealIdPeriodo() {
        return pealIdPeriodo;
    }

    public void setPealIdPeriodo(PeriodosAlumno pealIdPeriodo) {
        this.pealIdPeriodo = pealIdPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgalumno != null ? idProgalumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramacionAlumnos)) {
            return false;
        }
        ProgramacionAlumnos other = (ProgramacionAlumnos) object;
        if ((this.idProgalumno == null && other.idProgalumno != null) || (this.idProgalumno != null && !this.idProgalumno.equals(other.idProgalumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos[ idProgalumno=" + idProgalumno + " ]";
    }
    
}
