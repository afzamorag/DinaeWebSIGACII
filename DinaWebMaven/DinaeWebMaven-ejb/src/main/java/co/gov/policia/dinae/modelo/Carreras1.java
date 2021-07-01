/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "CARRERAS")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Carreras1.FIND_BY_NIVEL_ACADEMICO_AND_CREADO, query = "SELECT c FROM Carreras1 c WHERE c.nivelAcademico.idNivelAcademico = :nivelAcademico and c.creadoPor = :creadoPor"),
  @NamedQuery(name = Carreras1.FIND_BY_ID, query = "SELECT c FROM Carreras1 c WHERE c.idCarrera = :idCarrera")
})
@XmlRootElement
public class Carreras1 implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_NIVEL_ACADEMICO_AND_CREADO = "Carreras1.findByNivelAcademico";
  public static final String FIND_BY_ID = "Carreras1.findById";

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_CARRERA")
  private Long idCarrera;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FUERZA")
  private Long fuerza;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1000)
  @Column(name = "DESCRIPCION")
  private String descripcion;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1000)
  @Column(name = "TITULO")
  private String titulo;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "VIGENTE")
  private String vigente;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "CREADO_POR")
  private String creadoPor;
  @JoinColumn(name = "ID_NIVEL_ACADEMICO", referencedColumnName = "ID_NIVEL_ACADEMICO")
  @ManyToOne(optional = false)
  private NivelesAcademicos nivelAcademico;

  public Carreras1() {
  }

  /**
   * @return the idCarrera
   */
  public Long getIdCarrera() {
    return idCarrera;
  }

  /**
   * @param idCarrera the idCarrera to set
   */
  public void setIdCarrera(Long idCarrera) {
    this.idCarrera = idCarrera;
  }

  /**
   * @return the fuerza
   */
  public Long getFuerza() {
    return fuerza;
  }

  /**
   * @param fuerza the fuerza to set
   */
  public void setFuerza(Long fuerza) {
    this.fuerza = fuerza;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the titulo
   */
  public String getTitulo() {
    return titulo;
  }

  /**
   * @param titulo the titulo to set
   */
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  /**
   * @return the creadoPor
   */
  public String getCreadoPor() {
    return creadoPor;
  }

  /**
   * @param creadoPor the creadoPor to set
   */
  public void setCreadoPor(String creadoPor) {
    this.creadoPor = creadoPor;
  }

  /**
   * @return the vigente
   */
  public String getVigente() {
    return vigente;
  }

  /**
   * @param vigente the vigente to set
   */
  public void setVigente(String vigente) {
    this.vigente = vigente;
  }

  /**
   * @return the nivelAcademico
   */
  public NivelesAcademicos getNivelAcademico() {
    return nivelAcademico;
  }

  /**
   * @param nivelAcademico the nivelAcademico to set
   */
  public void setNivelAcademico(NivelesAcademicos nivelAcademico) {
    this.nivelAcademico = nivelAcademico;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.getIdCarrera());
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
    final Carreras1 other = (Carreras1) obj;
    if (!Objects.equals(this.idCarrera, other.idCarrera)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Carreras1{" + "idCarrera=" + getIdCarrera() + '}';
  }
}
