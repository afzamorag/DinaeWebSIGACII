package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CategoriaPolicialDTO implements Serializable {

  private Long idCategoria;
  private String descripcion;
  private String activo;

  public CategoriaPolicialDTO() {
  }

  public CategoriaPolicialDTO(Long idCategoria, String descripcion, String activo) {
    this.idCategoria = idCategoria;
    this.descripcion = descripcion;
    this.activo = activo;
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
