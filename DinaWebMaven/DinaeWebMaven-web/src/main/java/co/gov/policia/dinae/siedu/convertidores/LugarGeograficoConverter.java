/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.LugarGeografico;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("lugarGeograficoConverter")
public class LugarGeograficoConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return LugarGeografico.class;
  }
}
