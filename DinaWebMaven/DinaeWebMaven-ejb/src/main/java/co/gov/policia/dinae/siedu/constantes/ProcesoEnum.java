/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.constantes;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public enum ProcesoEnum {

  CAPACITACION(11L, "CAPACITACIÓN"),
  FORMACION(12L, "FORMACIÓN");

  private final Long id;
  private final String descripcion;

  private ProcesoEnum(Long tipo, String nombre) {
    this.id = tipo;
    this.descripcion = nombre;    
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   *
   * @param id
   * @return
   */
  public static ProcesoEnum findById(Integer id) {
    for (ProcesoEnum c : ProcesoEnum.values()) {
      if (c.getId().equals(id)) {
        return c;
      }
    }
    return null;
  }
}
