/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "CARRERAS")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = "Carreras.findAll", query = "SELECT c FROM Carreras c"),
  @NamedQuery(name = "Carreras.findByIdCarrera", query = "SELECT c FROM Carreras c WHERE c.carrerasPK.idCarrera = :idCarrera"),
  @NamedQuery(name = "Carreras.findByFuerza", query = "SELECT c FROM Carreras c WHERE c.carrerasPK.fuerza = :fuerza"),
  @NamedQuery(name = "Carreras.findByDescripcion", query = "SELECT c FROM Carreras c WHERE c.descripcion = :descripcion"),
  @NamedQuery(name = "Carreras.findByClaseEducacion", query = "SELECT c FROM Carreras c WHERE c.claseEducacion = :claseEducacion"),
  @NamedQuery(name = "Carreras.findByTipoPrograma", query = "SELECT c FROM Carreras c WHERE c.tipoPrograma = :tipoPrograma"),
  @NamedQuery(name = "Carreras.findByTitulo", query = "SELECT c FROM Carreras c WHERE c.titulo = :titulo"),
  @NamedQuery(name = "Carreras.findByObservacion", query = "SELECT c FROM Carreras c WHERE c.observacion = :observacion"),
  @NamedQuery(name = "Carreras.findByAbreviatura", query = "SELECT c FROM Carreras c WHERE c.abreviatura = :abreviatura"),
  @NamedQuery(name = "Carreras.findByCreadoPor", query = "SELECT c FROM Carreras c WHERE c.creadoPor = :creadoPor"),
  @NamedQuery(name = "Carreras.findByFechaCreacion", query = "SELECT c FROM Carreras c WHERE c.fechaCreacion = :fechaCreacion"),
  @NamedQuery(name = "Carreras.findByMaquinaCreacion", query = "SELECT c FROM Carreras c WHERE c.maquinaCreacion = :maquinaCreacion"),
  @NamedQuery(name = "Carreras.findByActualizadoPor", query = "SELECT c FROM Carreras c WHERE c.actualizadoPor = :actualizadoPor"),
  @NamedQuery(name = "Carreras.findByFechaActualiza", query = "SELECT c FROM Carreras c WHERE c.fechaActualiza = :fechaActualiza"),
  @NamedQuery(name = "Carreras.findByMaquinaActualiza", query = "SELECT c FROM Carreras c WHERE c.maquinaActualiza = :maquinaActualiza"),
  @NamedQuery(name = "Carreras.findByVigente", query = "SELECT c FROM Carreras c WHERE c.vigente = :vigente"),
  @NamedQuery(name = Carreras.FIND_CARRERAS_VIGENTES_BY_NIVEL_ACADEMICO, query = "SELECT c FROM Carreras c WHERE c.nivelAcademico.idNivelAcademico = :nivelAcademico AND c.vigente = 'SI'"),
  @NamedQuery(name = Carreras.FIND_BY_NIVEL_ACADEMICO_AND_CREADO, query = "SELECT c FROM Carreras c WHERE c.nivelAcademico.idNivelAcademico = :nivelAcademico AND c.vigente = 'SI' AND c.creadoPor = :creadoPor AND c.carrerasPK.fuerza = 6 AND c.ofertaVigente = 1")
})
@XmlRootElement
public class Carreras implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_CARRERAS_VIGENTES_BY_NIVEL_ACADEMICO = "Carreras.findByNivelAcademico";
  public static final String FIND_BY_NIVEL_ACADEMICO_AND_CREADO = "Carreras.findByNivelAcademicoAndCreado";
  @EmbeddedId
  private CarrerasPK carrerasPK;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 300)
  @Column(name = "DESCRIPCION")
  private String descripcion;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2)
  @Column(name = "CLASE_EDUCACION")
  private String claseEducacion;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TIPO_PROGRAMA")
  private Character tipoPrograma;
  @Size(max = 300)
  @Column(name = "TITULO")
  private String titulo;
  @Size(max = 250)
  @Column(name = "OBSERVACION")
  private String observacion;
  @Size(max = 40)
  @Column(name = "ABREVIATURA")
  private String abreviatura;
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
  @Size(min = 1, max = 150)
  @Column(name = "MAQUINA_CREACION")
  private String maquinaCreacion;
  @Size(max = 150)
  @Column(name = "ACTUALIZADO_POR")
  private String actualizadoPor;
  @Column(name = "FECHA_ACTUALIZA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualiza;
  @Size(max = 30)
  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;
  @Size(max = 2)
  @Column(name = "VIGENTE")
  private String vigente;
  @JoinColumn(name = "ID_NIVEL_ACADEMICO", referencedColumnName = "ID_NIVEL_ACADEMICO")
  @ManyToOne(optional = false)
  private NivelesAcademicos nivelAcademico;
  @Column(name = "OFERTA_VIGENTE")
  private Long ofertaVigente;

  public Carreras() {
  }

  public Carreras(CarrerasPK carrerasPK) {
    this.carrerasPK = carrerasPK;
  }

  public Carreras(Long idCarrera, Long fuerza) {
    this.carrerasPK = new CarrerasPK(idCarrera, fuerza);
  }

  public CarrerasPK getCarrerasPK() {
    return carrerasPK;
  }

  public void setCarrerasPK(CarrerasPK carrerasPK) {
    this.carrerasPK = carrerasPK;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getClaseEducacion() {
    return claseEducacion;
  }

  public void setClaseEducacion(String claseEducacion) {
    this.claseEducacion = claseEducacion;
  }

  public Character getTipoPrograma() {
    return tipoPrograma;
  }

  public void setTipoPrograma(Character tipoPrograma) {
    this.tipoPrograma = tipoPrograma;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public String getAbreviatura() {
    return abreviatura;
  }

  public void setAbreviatura(String abreviatura) {
    this.abreviatura = abreviatura;
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

  public Date getFechaActualiza() {
    return fechaActualiza;
  }

  @XmlTransient
  public void setFechaActualiza(Date fechaActualiza) {
    this.fechaActualiza = fechaActualiza;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  @XmlTransient
  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public String getVigente() {
    return vigente;
  }

  public void setVigente(String vigente) {
    this.vigente = vigente;
  }

  public NivelesAcademicos getNivelAcademico() {
    return nivelAcademico;
  }

  public void setNivelAcademico(NivelesAcademicos nivelAcademico) {
    this.nivelAcademico = nivelAcademico;
  }

    public Long getOfertaVigente() {
        return ofertaVigente;
    }

    public void setOfertaVigente(Long ofertaVigente) {
        this.ofertaVigente = ofertaVigente;
    }  

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 43 * hash + Objects.hashCode(this.carrerasPK);
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
    final Carreras other = (Carreras) obj;
    if (!Objects.equals(this.carrerasPK, other.carrerasPK)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Carreras{" + "carrerasPK=" + carrerasPK + ", descripcion=" + descripcion + '}';
  }
}
