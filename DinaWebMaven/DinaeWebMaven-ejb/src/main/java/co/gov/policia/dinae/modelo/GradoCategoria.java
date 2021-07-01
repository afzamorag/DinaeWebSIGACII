package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "GRADO_CATEGORIA")
@NamedQueries({
  @NamedQuery(name = "GradoCategoria.findAllPorIdcategoria", query = "SELECT g FROM GradoCategoria g WHERE g.idCategoria = :idCategoria AND g.vigente = 'SI' ORDER BY g.descripcion")
})
public class GradoCategoria implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "ALFABETICO")
  private String alfabitico;

  @Column(name = "NATURALEZA")
  private String naturaleza;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "ID_CATEGORIA")
  private Long idCategoria;

  @Column(name = "VIGENTE")
  private String vigente;

  public GradoCategoria() {
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
    if (!(object instanceof GradoCategoria)) {
      return false;
    }
    GradoCategoria other = (GradoCategoria) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAlfabitico() {
    return alfabitico;
  }

  public void setAlfabitico(String alfabitico) {
    this.alfabitico = alfabitico;
  }

  public String getNaturaleza() {
    return naturaleza;
  }

  public void setNaturaleza(String naturaleza) {
    this.naturaleza = naturaleza;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }

  public String getVigente() {
    return vigente;
  }

  public void setVigente(String vigente) {
    this.vigente = vigente;
  }
}
