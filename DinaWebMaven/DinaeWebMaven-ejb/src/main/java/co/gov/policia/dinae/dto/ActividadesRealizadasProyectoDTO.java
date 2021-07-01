package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ActividadesRealizadasProyectoDTO implements Serializable {

  private Long idActividadesRealizadasProyecto;
  private String descripcionActividad;

  /**
   *
   */
  public ActividadesRealizadasProyectoDTO() {
  }

  /**
   *
   * @param idActividadesRealizadasProyecto
   * @param descripcionActividad
   */
  public ActividadesRealizadasProyectoDTO(Long idActividadesRealizadasProyecto, String descripcionActividad) {
    this.idActividadesRealizadasProyecto = idActividadesRealizadasProyecto;
    this.descripcionActividad = descripcionActividad;
  }

  public Long getIdActividadesRealizadasProyecto() {
    return idActividadesRealizadasProyecto;
  }

  public void setIdActividadesRealizadasProyecto(Long idActividadesRealizadasProyecto) {
    this.idActividadesRealizadasProyecto = idActividadesRealizadasProyecto;
  }

  public String getDescripcionActividad() {
    return descripcionActividad;
  }

  public void setDescripcionActividad(String descripcionActividad) {
    this.descripcionActividad = descripcionActividad;
  }

}
