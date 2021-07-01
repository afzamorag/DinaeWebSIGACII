package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.SieduEntidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface IEntidadLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<SieduEntidadDTO> getEntidad() throws JpaDinaeException;

}
