/*
 * Soft Studio LTDA
 * Copyrigth .2017.
 */
package co.gov.policia.dinae.siedu.convertidores;


import co.gov.policia.dinae.siedu.modelo.SieduSubtipoElemento;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("subtipoElementoConverter")
public class SubtipoElementoConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return SieduSubtipoElemento.class;
  }
}
