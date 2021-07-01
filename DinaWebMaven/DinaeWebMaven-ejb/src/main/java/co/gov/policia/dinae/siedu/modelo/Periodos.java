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
@Table(name = "PERIODOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
    @NamedQuery(name = "Periodos.findByIdPeriodo", query = "SELECT p FROM Periodos p WHERE p.periodosPK.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "Periodos.findByPresencial", query = "SELECT p FROM Periodos p WHERE p.presencial = :presencial"),
    @NamedQuery(name = "Periodos.findByNroPeriodo", query = "SELECT p FROM Periodos p WHERE p.nroPeriodo = :nroPeriodo"),
    @NamedQuery(name = "Periodos.findByPracIdProgramaca", query = "SELECT p FROM Periodos p WHERE p.periodosPK.pracIdProgramaca = :pracIdProgramaca"),
    @NamedQuery(name = "Periodos.findByCreadoPor", query = "SELECT p FROM Periodos p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "Periodos.findByFechaCreacion", query = "SELECT p FROM Periodos p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Periodos.findByMaquinaCreacion", query = "SELECT p FROM Periodos p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "Periodos.findByVigente", query = "SELECT p FROM Periodos p WHERE p.vigente = :vigente"),
    @NamedQuery(name = "Periodos.findByFechaInicio", query = "SELECT p FROM Periodos p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Periodos.findByFechaFinal", query = "SELECT p FROM Periodos p WHERE p.fechaFinal = :fechaFinal")})
public class Periodos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PeriodosPK periodosPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PRESENCIAL")
    private String presencial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_PERIODO")
    private short nroPeriodo;
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
    @Size(max = 2)
    @Column(name = "VIGENTE")
    private String vigente;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @JoinColumn(name = "PRAC_ID_PROGRAMACA", referencedColumnName = "ID_PROGRAMACA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProgramasAcademico programasAcademico;
    @JoinColumn(name = "CICL_ID_CICLO", referencedColumnName = "ID_CICLO")
    @ManyToOne(optional = false)
    private Ciclos ciclIdCiclo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private Collection<Pensums> pensumsCollection;

    public Periodos() {
    }

    public Periodos(PeriodosPK periodosPK) {
        this.periodosPK = periodosPK;
    }

    public Periodos(PeriodosPK periodosPK, String presencial, short nroPeriodo, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.periodosPK = periodosPK;
        this.presencial = presencial;
        this.nroPeriodo = nroPeriodo;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public Periodos(BigInteger idPeriodo, BigInteger pracIdProgramaca) {
        this.periodosPK = new PeriodosPK(idPeriodo, pracIdProgramaca);
    }

    public PeriodosPK getPeriodosPK() {
        return periodosPK;
    }

    public void setPeriodosPK(PeriodosPK periodosPK) {
        this.periodosPK = periodosPK;
    }

    public String getPresencial() {
        return presencial;
    }

    public void setPresencial(String presencial) {
        this.presencial = presencial;
    }

    public short getNroPeriodo() {
        return nroPeriodo;
    }

    public void setNroPeriodo(short nroPeriodo) {
        this.nroPeriodo = nroPeriodo;
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

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
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

    public ProgramasAcademico getProgramasAcademico() {
        return programasAcademico;
    }

    public void setProgramasAcademico(ProgramasAcademico programasAcademico) {
        this.programasAcademico = programasAcademico;
    }

    public Ciclos getCiclIdCiclo() {
        return ciclIdCiclo;
    }

    public void setCiclIdCiclo(Ciclos ciclIdCiclo) {
        this.ciclIdCiclo = ciclIdCiclo;
    }

    @XmlTransient
    public Collection<Pensums> getPensumsCollection() {
        return pensumsCollection;
    }

    public void setPensumsCollection(Collection<Pensums> pensumsCollection) {
        this.pensumsCollection = pensumsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodosPK != null ? periodosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodos)) {
            return false;
        }
        Periodos other = (Periodos) object;
        if ((this.periodosPK == null && other.periodosPK != null) || (this.periodosPK != null && !this.periodosPK.equals(other.periodosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Periodos[ periodosPK=" + periodosPK + " ]";
    }
    
}
