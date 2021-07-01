package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.AsesoriaProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author RafaelGomez
 */
@Local
public interface IAsesoriaProyectoLocal {

  /**
   *
   * @param idAsesoriaProyecto
   * @return
   * @throws JpaDinaeException
   */
  AsesoriaProyecto getAsesoriaProyecto(Long idAsesoriaProyecto) throws JpaDinaeException;

  /**
   *
   * @param asesoriaProyecto
   * @return
   * @throws JpaDinaeException
   */
  AsesoriaProyecto guardarAsesoriaProyecto(AsesoriaProyecto asesoriaProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<AsesoriaProyecto> getListaAsesoriaProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTOPorIdProyecto(Long idProyecto) throws JpaDinaeException;

}
