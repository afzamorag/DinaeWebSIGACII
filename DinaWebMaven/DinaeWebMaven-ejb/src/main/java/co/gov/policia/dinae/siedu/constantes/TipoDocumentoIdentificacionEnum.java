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
public enum TipoDocumentoIdentificacionEnum {

  CEDULA_EXTRANJERIA("CE", null, "CE", "CEDULA EXTRANJERIA"),
  TARJETA_IDENTIDAD("TI", null, "TI", "TARJETA IDENTIDAD"),
  NUMERO_IDENTIFICACION_TRIBUTARIA("NI", null, "NI", "NUMERO IDENTIFICACION TRIBUTARIA"),
  NUMERO_UNICO_IDENTIFICACION_PERSONAL("NU", null, "NU", "NUMERO UNICO DE IDENTIFICACION PERSONAL"),
  PASAPORTE("PA", null, "PA", "PASAPORTE"),
  CEDULA("CC", null, "CC", "CEDULA"),
  REGISTRO_CIVIL("RCRC", null, "RC", "REGISTRO CIVIL"),
  NO_REPORTADO("NR", null, "NR", "NO REPORTADO");

  private final String lowValue;
  private final String highValue;
  private final String abbreviation;
  private final String meaning;

  private TipoDocumentoIdentificacionEnum(String lowValue, String highValue, String abbreviation, String meaning) {
    this.lowValue = lowValue;
    this.highValue = highValue;
    this.abbreviation = abbreviation;
    this.meaning = meaning;
  }

  /**
   * @return the id
   */
  public String getLowValue() {
    return lowValue;
  }

  /**
   * @return the highValue
   */
  public String getHighValue() {
    return highValue;
  }

  /**
   *
   * @return
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   *
   * @return
   */
  public String getMeaning() {
    return meaning;
  }
}
