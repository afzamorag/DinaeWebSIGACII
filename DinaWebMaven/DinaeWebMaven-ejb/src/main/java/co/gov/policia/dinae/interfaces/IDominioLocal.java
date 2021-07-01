package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.DominioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface IDominioLocal {

  /**
   *
   * @param tipoDom
   * @return
   * @throws JpaDinaeException
   */
  List<DominioDTO> getSieduDominioVigenteByTipoDominio(Short tipoDom) throws JpaDinaeException;

}
