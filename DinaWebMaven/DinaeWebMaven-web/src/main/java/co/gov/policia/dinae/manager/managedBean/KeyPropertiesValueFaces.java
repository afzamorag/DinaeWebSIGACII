package co.gov.policia.dinae.manager.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "properties")
@javax.enterprise.context.SessionScoped
public class KeyPropertiesValueFaces implements Serializable {

  private KeyPropertiesFactory kpf;

  /**
   *
   */
  public KeyPropertiesValueFaces() {
    kpf = KeyPropertiesFactory.getInstance();
  }

  /**
   *
   * @param key
   * @return
   */
  public String value(String key) {
    return kpf.value(key);
  }
}
