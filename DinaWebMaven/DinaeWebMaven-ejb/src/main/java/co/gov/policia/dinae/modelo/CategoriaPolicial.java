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
@Table(name = "CATEGORIA_POLICIAL")
@NamedQueries({
  @NamedQuery(name = "CategoriaPolicial.findAll", query = "SELECT g FROM CategoriaPolicial g WHERE g.activo = 'SI' ORDER BY g.descripcion"),
  @NamedQuery(name = "CategoriaPolicialDTO.findAll", query = "SELECT NEW co.gov.policia.dinae.dto.CategoriaPolicialDTO( g.idCategoria, g.descripcion, g.activo ) FROM CategoriaPolicial g WHERE g.activo = 'SI' ORDER BY g.descripcion")
})
public class CategoriaPolicial implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_CATEGORIA")
  private Long idCategoria;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "ACTIVO")
  private String activo;

  public CategoriaPolicial() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCategoria != null ? idCategoria.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CategoriaPolicial)) {
      return false;
    }
    CategoriaPolicial other = (CategoriaPolicial) object;
    if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
      return false;
    }
    return true;
  }

  public Long getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Long idCategoria) {
    this.idCategoria = idCategoria;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getActivo() {
    return activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }
}
