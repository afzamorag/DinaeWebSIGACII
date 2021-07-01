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
public enum PropiedadesEnum {

  FILES_PATH_SERVER("C:\\tmp\\");
  private final String property;

  private PropiedadesEnum(String property) {
    this.property = property;
  }

  /**
   * @return the property
   */
  public String getProperty() {
    return property;
  }
}
