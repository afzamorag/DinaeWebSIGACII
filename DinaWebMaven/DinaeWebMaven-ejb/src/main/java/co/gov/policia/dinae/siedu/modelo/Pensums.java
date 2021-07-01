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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
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
@Table(name = "PENSUMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pensums.findAll", query = "SELECT p FROM Pensums p"),
    @NamedQuery(name = "Pensums.findByIdPensum", query = "SELECT p FROM Pensums p WHERE p.idPensum = :idPensum"),
    @NamedQuery(name = "Pensums.findByHabilitable", query = "SELECT p FROM Pensums p WHERE p.habilitable = :habilitable"),
    @NamedQuery(name = "Pensums.findByCampo", query = "SELECT p FROM Pensums p WHERE p.campo = :campo"),
    @NamedQuery(name = "Pensums.findByIntensidad", query = "SELECT p FROM Pensums p WHERE p.intensidad = :intensidad"),
    @NamedQuery(name = "Pensums.findByAsigIdAsignatura", query = "SELECT p FROM Pensums p WHERE p.asigIdAsignatura = :asigIdAsignatura"),
    @NamedQuery(name = "Pensums.findByAsigIdAreaEduc", query = "SELECT p FROM Pensums p WHERE p.asigIdAreaEduc = :asigIdAreaEduc"),
    @NamedQuery(name = "Pensums.findByAsigFuerza", query = "SELECT p FROM Pensums p WHERE p.asigFuerza = :asigFuerza"),
    @NamedQuery(name = "Pensums.findByCreadoPor", query = "SELECT p FROM Pensums p WHERE p.creadoPor = :creadoPor"),
    @NamedQuery(name = "Pensums.findByFechaCreacion", query = "SELECT p FROM Pensums p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Pensums.findByMaquinaCreacion", query = "SELECT p FROM Pensums p WHERE p.maquinaCreacion = :maquinaCreacion"),
    @NamedQuery(name = "Pensums.findByNroCredito", query = "SELECT p FROM Pensums p WHERE p.nroCredito = :nroCredito"),
    @NamedQuery(name = "Pensums.findByVigente", query = "SELECT p FROM Pensums p WHERE p.vigente = :vigente"),
    @NamedQuery(name = "Pensums.findByNivel", query = "SELECT p FROM Pensums p WHERE p.nivel = :nivel"),
    @NamedQuery(name = "Pensums.findByEvaluable", query = "SELECT p FROM Pensums p WHERE p.evaluable = :evaluable")})
public class Pensums implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PENSUM")
    private BigDecimal idPensum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "HABILITABLE")
    private String habilitable;
    @Column(name = "CAMPO")
    private BigInteger campo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTENSIDAD")
    private short intensidad;
    @Lob
    @Size(max = 0)
    @Column(name = "CONTENIDO_PROG")
    private String contenidoProg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASIG_ID_ASIGNATURA")
    private BigInteger asigIdAsignatura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASIG_ID_AREA_EDUC")
    private BigInteger asigIdAreaEduc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ASIG_FUERZA")
    private short asigFuerza;
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
    @Column(name = "NRO_CREDITO")
    private Short nroCredito;
    @Size(max = 2)
    @Column(name = "VIGENTE")
    private String vigente;
    @Column(name = "NIVEL")
    private BigInteger nivel;
    @Size(max = 2)
    @Column(name = "EVALUABLE")
    private String evaluable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pensIdPensum")
    private Collection<PensumDocentes> pensumDocentesCollection;
    @JoinColumns({
        @JoinColumn(name = "PERI_ID_PERIODO", referencedColumnName = "ID_PERIODO"),
        @JoinColumn(name = "PERI_PRAC_ID_PROGRAMACA", referencedColumnName = "PRAC_ID_PROGRAMACA")})
    @ManyToOne(optional = false)
    private Periodos periodos;

    public Pensums() {
    }

    public Pensums(BigDecimal idPensum) {
        this.idPensum = idPensum;
    }

    public Pensums(BigDecimal idPensum, String habilitable, short intensidad, BigInteger asigIdAsignatura, BigInteger asigIdAreaEduc, short asigFuerza, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idPensum = idPensum;
        this.habilitable = habilitable;
        this.intensidad = intensidad;
        this.asigIdAsignatura = asigIdAsignatura;
        this.asigIdAreaEduc = asigIdAreaEduc;
        this.asigFuerza = asigFuerza;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdPensum() {
        return idPensum;
    }

    public void setIdPensum(BigDecimal idPensum) {
        this.idPensum = idPensum;
    }

    public String getHabilitable() {
        return habilitable;
    }

    public void setHabilitable(String habilitable) {
        this.habilitable = habilitable;
    }

    public BigInteger getCampo() {
        return campo;
    }

    public void setCampo(BigInteger campo) {
        this.campo = campo;
    }

    public short getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(short intensidad) {
        this.intensidad = intensidad;
    }

    public String getContenidoProg() {
        return contenidoProg;
    }

    public void setContenidoProg(String contenidoProg) {
        this.contenidoProg = contenidoProg;
    }

    public BigInteger getAsigIdAsignatura() {
        return asigIdAsignatura;
    }

    public void setAsigIdAsignatura(BigInteger asigIdAsignatura) {
        this.asigIdAsignatura = asigIdAsignatura;
    }

    public BigInteger getAsigIdAreaEduc() {
        return asigIdAreaEduc;
    }

    public void setAsigIdAreaEduc(BigInteger asigIdAreaEduc) {
        this.asigIdAreaEduc = asigIdAreaEduc;
    }

    public short getAsigFuerza() {
        return asigFuerza;
    }

    public void setAsigFuerza(short asigFuerza) {
        this.asigFuerza = asigFuerza;
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

    public Short getNroCredito() {
        return nroCredito;
    }

    public void setNroCredito(Short nroCredito) {
        this.nroCredito = nroCredito;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public BigInteger getNivel() {
        return nivel;
    }

    public void setNivel(BigInteger nivel) {
        this.nivel = nivel;
    }

    public String getEvaluable() {
        return evaluable;
    }

    public void setEvaluable(String evaluable) {
        this.evaluable = evaluable;
    }

    @XmlTransient
    public Collection<PensumDocentes> getPensumDocentesCollection() {
        return pensumDocentesCollection;
    }

    public void setPensumDocentesCollection(Collection<PensumDocentes> pensumDocentesCollection) {
        this.pensumDocentesCollection = pensumDocentesCollection;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPensum != null ? idPensum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pensums)) {
            return false;
        }
        Pensums other = (Pensums) object;
        if ((this.idPensum == null && other.idPensum != null) || (this.idPensum != null && !this.idPensum.equals(other.idPensum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Pensums[ idPensum=" + idPensum + " ]";
    }
    
}
