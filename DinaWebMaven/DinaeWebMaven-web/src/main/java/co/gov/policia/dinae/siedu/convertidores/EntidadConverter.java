/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.SieduEntidad;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("entidadConverter")
public class EntidadConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return SieduEntidad.class;
  }
}
