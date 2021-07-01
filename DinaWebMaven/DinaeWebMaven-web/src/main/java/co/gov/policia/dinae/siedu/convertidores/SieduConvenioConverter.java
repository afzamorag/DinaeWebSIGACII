/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.SieduConvenio;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@logike.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("sieduConvenioConverter")
public class SieduConvenioConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return SieduConvenio.class;
  }
}
