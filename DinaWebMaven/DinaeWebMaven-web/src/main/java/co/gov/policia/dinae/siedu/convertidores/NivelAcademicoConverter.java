/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.NivelesAcademicos;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("nivelAcademicoConverter")
public class NivelAcademicoConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return NivelesAcademicos.class;
  }
}