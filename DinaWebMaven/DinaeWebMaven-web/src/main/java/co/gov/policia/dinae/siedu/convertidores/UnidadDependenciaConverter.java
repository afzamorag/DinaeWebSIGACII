/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("unidadDependenciaConverter")
public class UnidadDependenciaConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return UnidadDependencia.class;
  }
}
