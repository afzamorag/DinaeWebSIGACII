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
public enum EstadoCivilEnum {

  NO_REPORTADO("NR", null, "No Reportado", "No Reportado"),
  DIVORCIADO("DI", null, "Divorciado", "Divorciado (a)"),
  UNION_LIBRE("UL", null, "Union Libre", "Union Libre"),
  CASADO("CA", null, "Casado", "Casado (a)"),
  SOLTERO("SO", null, "Soltero", "Soltero (a)"),
  SEPARADO("SE", null, "Separado", "Separado (a)"),
  VIUDO("VI", null, "Viudo", "Viudo (a)");

  private final String lowValue;
  private final String highValue;
  private final String abbreviation;
  private final String meaning;

  private EstadoCivilEnum(String lowValue, String highValue, String abbreviation, String meaning) {
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
