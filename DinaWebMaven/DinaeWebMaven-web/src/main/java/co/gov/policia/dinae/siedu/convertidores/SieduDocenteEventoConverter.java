/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("sieduDocenteEventoConverter")
public class SieduDocenteEventoConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return SieduDocenteEvento.class;
  }
}
