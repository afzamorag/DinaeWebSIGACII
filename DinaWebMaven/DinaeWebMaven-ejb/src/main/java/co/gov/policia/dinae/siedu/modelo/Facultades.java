/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
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
@Table(name = "FACULTADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facultades.findAll", query = "SELECT f FROM Facultades f"),
    @NamedQuery(name = "Facultades.findByIdFacultad", query = "SELECT f FROM Facultades f WHERE f.idFacultad = :idFacultad"),
    @NamedQuery(name = "Facultades.findByDescripcion", query = "SELECT f FROM Facultades f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "Facultades.findByCreadoPor", query = "SELECT f FROM Facultades f WHERE f.creadoPor = :creadoPor"),
    @NamedQuery(name = "Facultades.findByFechaCreacion", query = "SELECT f FROM Facultades f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Facultades.findByMaquinaCreacion", query = "SELECT f FROM Facultades f WHERE f.maquinaCreacion = :maquinaCreacion")})
public class Facultades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FACULTAD")
    private Short idFacultad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facuIdFacultad")
    private Collection<ProgramasAcademico> programasAcademicoCollection;

    public Facultades() {
    }

    public Facultades(Short idFacultad) {
        this.idFacultad = idFacultad;
    }

    public Facultades(Short idFacultad, String descripcion, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
        this.idFacultad = idFacultad;
        this.descripcion = descripcion;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.maquinaCreacion = maquinaCreacion;
    }

    public Short getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Short idFacultad) {
        this.idFacultad = idFacultad;
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
    public Collection<ProgramasAcademico> getProgramasAcademicoCollection() {
        return programasAcademicoCollection;
    }

    public void setProgramasAcademicoCollection(Collection<ProgramasAcademico> programasAcademicoCollection) {
        this.programasAcademicoCollection = programasAcademicoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacultad != null ? idFacultad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facultades)) {
            return false;
        }
        Facultades other = (Facultades) object;
        if ((this.idFacultad == null && other.idFacultad != null) || (this.idFacultad != null && !this.idFacultad.equals(other.idFacultad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Facultades[ idFacultad=" + idFacultad + " ]";
    }
    
}
