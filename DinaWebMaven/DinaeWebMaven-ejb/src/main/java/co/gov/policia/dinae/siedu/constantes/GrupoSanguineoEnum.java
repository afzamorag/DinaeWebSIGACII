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
public enum GrupoSanguineoEnum {

  O("O", null, "O", "O"),
  A("A", null, "A", "A"),
  B("B", null, "B", "B"),
  AB("AB", null, "AB", "AB"),
  NR("NR", null, "No reportado", "No reportado");

  private final String lowValue;
  private final String highValue;
  private final String abbreviation;
  private final String meaning;

  private GrupoSanguineoEnum(String lowValue, String highValue, String abbreviation, String meaning) {
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
