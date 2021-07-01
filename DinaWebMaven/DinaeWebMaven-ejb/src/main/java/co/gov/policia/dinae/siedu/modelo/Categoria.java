/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Jose Buzon
 */
@Entity
@Table(name = "CATEGORIAS")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = Categoria.FIND_BY_ID, query = "SELECT s FROM Categoria s WHERE s.id =:id")
})
public class Categoria implements Serializable {

  private static final long serialVersionUID = -6316855019424843636L;
  public static final String FIND_BY_ID = "Categoria.findById";
  @EmbeddedId
  private CategoriaPK id;
  @Column(name = "DESCRIPCION")
  private String descripcion;
  @Column(name = "CREADO_POR")
  private String creadoPor;
  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.DATE)
  private Date fechaCracion;
  @Column(name = "MAQUINA_CREACION")
  private String maquinaCreacion;
  @Column(name = "ACTUALIZA_POR")
  private String actualizadoPor;
  @Column(name = "FECHA_ACTUALIZA")
  @Temporal(TemporalType.DATE)
  private Date fechaActualiza;
  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

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
  public Date getFechaCracion() {
    return fechaCracion;
  }

  public void setFechaCracion(Date fechaCracion) {
    this.fechaCracion = fechaCracion;
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

  public CategoriaPK getId() {
    return id;
  }

  public void setId(CategoriaPK id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Categoria)) {
      return false;
    }
    Categoria other = (Categoria) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.Categoria[ id=" + id + " ]";
  }

}
