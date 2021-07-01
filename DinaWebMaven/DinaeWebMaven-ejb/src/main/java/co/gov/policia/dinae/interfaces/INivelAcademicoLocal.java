package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.NivelAcademicoDTO;
import co.gov.policia.dinae.dto.UnidadDependenciaDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.NivelAcademico;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface INivelAcademicoLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<NivelAcademicoDTO> getNivelAcademicoVigenteByTipo() throws JpaDinaeException;
  
  NivelAcademico getNivelAcademicoByID(Long id) throws JpaDinaeException;

}
