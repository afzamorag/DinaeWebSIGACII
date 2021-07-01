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
public enum UnidadDependenciaTipoEnum {

  ESCUELAS(10L, "Escuela"),
  CENTRO_INSTRUCCION(15L, "Centro Instruccion");

  private final Long tipo;
  private final String descripcion;

  private UnidadDependenciaTipoEnum(Long tipo, String nombre) {
    this.tipo = tipo;
    this.descripcion = nombre;    
  }

  /**
   * @return the tipo
   */
  public Long getTipo() {
    return tipo;
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
  public static UnidadDependenciaTipoEnum findById(Integer id) {
    for (UnidadDependenciaTipoEnum c : UnidadDependenciaTipoEnum.values()) {
      if (c.getTipo().equals(id)) {
        return c;
      }
    }
    return null;
  }
}
