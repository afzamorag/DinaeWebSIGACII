package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class GradoCategoriaDTO implements Serializable {

  private Long id;
  private String alfabitico;
  private String naturaleza;
  private String descripcion;
  private Long idCategoria;
  private String vigente;

  public GradoCategoriaDTO() {
  }

  /**
   *
   * @param id
   * @param alfabitico
   * @param naturaleza
   * @param descripcion
   * @param idCategoria
   * @param vigente
   */
  public GradoCategoriaDTO(Long id, String alfabitico, String naturaleza, String descripcion, Long idCategoria, String vigente) {
    this.id = id;
    this.alfabitico = alfabitico;
    this.naturaleza = naturaleza;
    this.descripcion = descripcion;
    this.idCategoria = idCategoria;
    this.vigente = vigente;
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

  public String getAlbabeticoDescripcion() {

    return alfabitico != null ? alfabitico.concat(" - ").concat(descripcion) : descripcion;

  }
}
