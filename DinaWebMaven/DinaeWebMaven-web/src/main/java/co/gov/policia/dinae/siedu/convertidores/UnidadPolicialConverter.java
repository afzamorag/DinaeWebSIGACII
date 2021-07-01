/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.UnidadPolicial;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("unidadPolicialConverter")
public class UnidadPolicialConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return UnidadPolicial.class;
  }
}
