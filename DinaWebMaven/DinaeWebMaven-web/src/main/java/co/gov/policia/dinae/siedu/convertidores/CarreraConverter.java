/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.Carreras;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Juan Carlos Cifuentes Murcia <juan.cifuentes@correo.policia.gov.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("carreraConverter")
public class CarreraConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return Carreras.class;
  }
}
