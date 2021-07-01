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
@Table(name = "CICLOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciclos.findAll", query = "SELECT c FROM Ciclos c"),
    @NamedQuery(name = "Ciclos.findByIdCiclo", query = "SELECT c FROM Ciclos c WHERE c.idCiclo = :idCiclo"),
    @NamedQuery(name = "Ciclos.findByDescripcion", query = "SELECT c FROM Ciclos c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Ciclos.findByCreadoPor", query = "SELECT c FROM Ciclos c WHERE c.creadoPor = :creadoPor"),
    @NamedQuery(name = "Ciclos.findByFechaCreacion", query = "SELECT c FROM Ciclos c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Ciclos.findByMaquinaCreacion", query = "SELECT c FROM Ciclos c WHERE c.maquinaCreacion = :maquinaCreacion")})
public class Ciclos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CICLO")
    private BigDecimal idCiclo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciclIdCiclo")
    private Collection<Periodos> periodosCollection;

    public Ciclos() {
    }

    public Ciclos(BigDecimal idCiclo) {
        this.idCiclo = idCiclo;
    }

    public Ciclos(BigDecimal idCiclo, String descripcion, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idCiclo = idCiclo;
        this.descripcion = descripcion;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public BigDecimal getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(BigDecimal idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @XmlTransient
    public Collection<Periodos> getPeriodosCollection() {
        return periodosCollection;
    }

    public void setPeriodosCollection(Collection<Periodos> periodosCollection) {
        this.periodosCollection = periodosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiclo != null ? idCiclo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciclos)) {
            return false;
        }
        Ciclos other = (Ciclos) object;
        if ((this.idCiclo == null && other.idCiclo != null) || (this.idCiclo != null && !this.idCiclo.equals(other.idCiclo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Ciclos[ idCiclo=" + idCiclo + " ]";
    }
    
}
