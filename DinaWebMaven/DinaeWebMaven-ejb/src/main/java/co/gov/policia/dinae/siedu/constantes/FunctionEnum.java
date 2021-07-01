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
public enum FunctionEnum {

  FUNCTION_VALIDAR_DEPENDENCIA_ROL("VALIDAR_DEPENDENCIA_ROL_MAP");

  private final String nombreSP;

  FunctionEnum(String name) {
    this.nombreSP = name;
  }

  public String getNombreSP() {
    return nombreSP;
  }

}
