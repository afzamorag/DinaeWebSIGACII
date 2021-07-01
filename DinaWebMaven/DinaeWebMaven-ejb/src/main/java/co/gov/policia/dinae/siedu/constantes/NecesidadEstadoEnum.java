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
public enum NecesidadEstadoEnum {

  APROBADO("APROBADO"),
  NO_APROBADO("NO APROBADO"),
  PENDIENTE("PENDIENTE");

  private final String estado;

  NecesidadEstadoEnum(String name) {
    this.estado = name;
  }

  public String getEstado() {
    return estado;
  }

}
