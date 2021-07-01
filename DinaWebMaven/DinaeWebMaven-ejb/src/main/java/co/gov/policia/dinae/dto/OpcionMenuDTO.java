package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class OpcionMenuDTO implements Comparable<OpcionMenuDTO>, Serializable {

  private Long idOpcionMenu;
  private String nombre;
  private String accion;
  private Integer orden;
  private String titulo;

  /**
   *
   */
  public OpcionMenuDTO() {
  }

  /**
   *
   * @param idOpcionMenu
   * @param nombre
   * @param accion
   * @param orden
   * @param titulo
   */
  public OpcionMenuDTO(Long idOpcionMenu, String nombre, String accion, Integer orden, String titulo) {
    this.idOpcionMenu = idOpcionMenu;
    this.nombre = nombre;
    this.accion = accion;
    this.orden = orden;
    this.titulo = titulo;

    if (this.orden == null) {
      this.orden = 999;
    }
  }

  public Long getIdOpcionMenu() {
    return idOpcionMenu;
  }

  public void setIdOpcionMenu(Long idOpcionMenu) {
    this.idOpcionMenu = idOpcionMenu;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getAccion() {
    return accion;
  }

  public void setAccion(String accion) {
    this.accion = accion;
  }

  public Integer getOrden() {
    return orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  @Override
  public int compareTo(OpcionMenuDTO o) {

    return orden.compareTo(o.getOrden());

  }
}
