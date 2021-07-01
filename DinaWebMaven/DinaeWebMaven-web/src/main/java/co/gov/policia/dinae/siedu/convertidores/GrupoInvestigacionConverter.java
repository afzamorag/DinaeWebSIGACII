/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import javax.faces.convert.FacesConverter;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@FacesConverter("grupoInvestigacionConverter")
public class GrupoInvestigacionConverter extends AbstractXMLConverter {

  @Override
  public Class getClazz() {
    return GrupoInvestigacion.class;
  }
}
