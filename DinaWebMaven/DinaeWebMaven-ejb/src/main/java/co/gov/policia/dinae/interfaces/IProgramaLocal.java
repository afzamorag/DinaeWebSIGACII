package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ConsultaProgramaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface IProgramaLocal {

  /**
   *
   * @param escuela
   * @param nivelAcademico
   * @param metodologia
   * @param vigencia
   * @return
   * @throws JpaDinaeException
   */
  List<ConsultaProgramaDTO> getProgramaPorNivelEstrategia(Long escuela, Long nivelAcademico, Long metodologia, Long vigencia) throws JpaDinaeException;

}
