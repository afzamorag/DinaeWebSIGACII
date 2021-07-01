/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDRES FELIPE ZAMORA GARZON <af.zamorag@gmail.com>
 */
@Entity
@Table(name = "CARGOS", schema = "USR_REHU")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Cargos.findAll", query = "SELECT s FROM Cargos s")
})
public class Cargos implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private CargosPK id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "DESCRIPCION", nullable = false, length = 100)
  private String descripcion;

  public Cargos() {
  }

  public CargosPK getId() {
    return id;
  }

  public void setId(CargosPK id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public String toString() {
    return "Cargos{" + "id=" + id + ", descripcion=" + descripcion + '}';
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + Objects.hashCode(this.id);
    hash = 53 * hash + Objects.hashCode(this.descripcion);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Cargos other = (Cargos) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (!Objects.equals(this.descripcion, other.descripcion)) {
      return false;
    }
    return true;
  }

}
