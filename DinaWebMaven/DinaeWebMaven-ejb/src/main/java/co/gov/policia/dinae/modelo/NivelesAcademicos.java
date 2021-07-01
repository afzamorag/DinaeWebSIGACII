/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.siedu.modelo.ProgramasAcademico;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author OFITE
 */
@Entity
@Table(name = "NIVELES_ACADEMICOS")
@NamedQueries({
  @NamedQuery(name = "NivelesAcademicos.findAll", query = "SELECT n FROM NivelesAcademicos n"),
  @NamedQuery(name = "NivelesAcademicos.findByIdNivelAcademico", query = "SELECT n FROM NivelesAcademicos n WHERE n.idNivelAcademico = :idNivelAcademico"),
  @NamedQuery(name = "NivelesAcademicos.findByDescripcion", query = "SELECT n FROM NivelesAcademicos n WHERE n.descripcion = :descripcion"),
  @NamedQuery(name = "NivelesAcademicos.findByCreadoPor", query = "SELECT n FROM NivelesAcademicos n WHERE n.creadoPor = :creadoPor"),
  @NamedQuery(name = "NivelesAcademicos.findByFechaCreacion", query = "SELECT n FROM NivelesAcademicos n WHERE n.fechaCreacion = :fechaCreacion"),
  @NamedQuery(name = "NivelesAcademicos.findByMaquinaCreacion", query = "SELECT n FROM NivelesAcademicos n WHERE n.maquinaCreacion = :maquinaCreacion"),
  @NamedQuery(name = "NivelesAcademicos.findByActualizadoPor", query = "SELECT n FROM NivelesAcademicos n WHERE n.actualizadoPor = :actualizadoPor"),
  @NamedQuery(name = "NivelesAcademicos.findByFechaActualiza", query = "SELECT n FROM NivelesAcademicos n WHERE n.fechaActualiza = :fechaActualiza"),
  @NamedQuery(name = "NivelesAcademicos.findByMaquinaActualiza", query = "SELECT n FROM NivelesAcademicos n WHERE n.maquinaActualiza = :maquinaActualiza"),
  @NamedQuery(name = "NivelesAcademicos.findByOrdenImportancia", query = "SELECT n FROM NivelesAcademicos n WHERE n.ordenImportancia = :ordenImportancia"),
  @NamedQuery(name = NivelesAcademicos.FIND_ALL_ASC, query = "SELECT n FROM NivelesAcademicos n ORDER BY n.descripcion ASC")
})
@XmlRootElement
public class NivelesAcademicos implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "niacIdNivelAcademico")
    private Collection<ProgramasAcademico> programasAcademicoCollection;

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL_ASC = "NivelesAcademicos.findAllASC";
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_NIVEL_ACADEMICO")
  private Long idNivelAcademico;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 60)
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
  @Size(max = 30)
  @Column(name = "ACTUALIZADO_POR")
  private String actualizadoPor;
  @Column(name = "FECHA_ACTUALIZA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualiza;
  @Size(max = 30)
  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;
  @Column(name = "ORDEN_IMPORTANCIA")
  private BigInteger ordenImportancia;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelAcademico")
  private Collection<Carreras> carrerasCollection;

  public NivelesAcademicos() {
  }

  public NivelesAcademicos(Long idNivelAcademico) {
    this.idNivelAcademico = idNivelAcademico;
  }

  public NivelesAcademicos(Long idNivelAcademico, String descripcion, String creadoPor, Date fechaCreacion, String maquinaCreacion) {
    this.idNivelAcademico = idNivelAcademico;
    this.descripcion = descripcion;
    this.creadoPor = creadoPor;
    this.fechaCreacion = fechaCreacion;
    this.maquinaCreacion = maquinaCreacion;
  }

  public Long getIdNivelAcademico() {
    return idNivelAcademico;
  }

  public void setIdNivelAcademico(Long idNivelAcademico) {
    this.idNivelAcademico = idNivelAcademico;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @XmlTransient
  public String getCreadoPor() {
    return creadoPor;
  }

  public void setCreadoPor(String creadoPor) {
    this.creadoPor = creadoPor;
  }

  @XmlTransient
  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  @XmlTransient
  public String getMaquinaCreacion() {
    return maquinaCreacion;
  }

  public void setMaquinaCreacion(String maquinaCreacion) {
    this.maquinaCreacion = maquinaCreacion;
  }

  @XmlTransient
  public String getActualizadoPor() {
    return actualizadoPor;
  }

  public void setActualizadoPor(String actualizadoPor) {
    this.actualizadoPor = actualizadoPor;
  }

  @XmlTransient
  public Date getFechaActualiza() {
    return fechaActualiza;
  }

  public void setFechaActualiza(Date fechaActualiza) {
    this.fechaActualiza = fechaActualiza;
  }

  @XmlTransient
  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  @XmlTransient
  public BigInteger getOrdenImportancia() {
    return ordenImportancia;
  }

  public void setOrdenImportancia(BigInteger ordenImportancia) {
    this.ordenImportancia = ordenImportancia;
  }

  @XmlTransient
  public Collection<Carreras> getCarrerasCollection() {
    return carrerasCollection;
  }

  public void setCarrerasCollection(Collection<Carreras> carrerasCollection) {
    this.carrerasCollection = carrerasCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idNivelAcademico != null ? idNivelAcademico.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof NivelesAcademicos)) {
      return false;
    }
    NivelesAcademicos other = (NivelesAcademicos) object;
    if ((this.idNivelAcademico == null && other.idNivelAcademico != null) || (this.idNivelAcademico != null && !this.idNivelAcademico.equals(other.idNivelAcademico))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Pojos.NivelesAcademicos[ idNivelAcademico=" + idNivelAcademico + " ]";
  }

    @XmlTransient
    public Collection<ProgramasAcademico> getProgramasAcademicoCollection() {
        return programasAcademicoCollection;
    }

    public void setProgramasAcademicoCollection(Collection<ProgramasAcademico> programasAcademicoCollection) {
        this.programasAcademicoCollection = programasAcademicoCollection;
    }
}
