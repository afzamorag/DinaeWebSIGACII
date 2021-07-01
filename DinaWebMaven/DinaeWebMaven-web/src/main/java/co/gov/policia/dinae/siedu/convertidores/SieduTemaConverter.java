/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.SieduTema;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("sieduTemaConverter")
public class SieduTemaConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return SieduTema.class;
  }
}
