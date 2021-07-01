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
public enum SexoEnum {

  BISEXUAL("BI", null, "Bisexual", "Bisexual"),
  HOMOSEXUAL_FEMENINO("HF", null, "Homosexual Femenino", "Homosexual Femenino"),
  FEMENINO("FE", null, "Femenino", "Femenino"),
  MASCULINO("MA", null, "Masculino", "Masculino"),
  NO_REPORTADO("NR", null, "No Reportado", "No Reportado"),
  HOMOSEXUAL_MASCULINO("HM", null, "Homosexual Masculino", "Homosexual Masculino");

  private final String lowValue;
  private final String highValue;
  private final String abbreviation;
  private final String meaning;

  private SexoEnum(String lowValue, String highValue, String abbreviation, String meaning) {
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
