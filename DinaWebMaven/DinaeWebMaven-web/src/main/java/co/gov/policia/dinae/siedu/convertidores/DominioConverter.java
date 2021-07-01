/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.Dominio;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("dominioConverter")
public class DominioConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return Dominio.class;
  }
}
