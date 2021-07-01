package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.EjecutorNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EjecutorNecesidad;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public interface IEjecutorNecesidadLocal {

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  List<EjecutorNecesidad> getEjecutorNecesidadPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  List<EjecutorNecesidadDTO> getEjecutorNecesidadDTOPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  EjecutorNecesidad getEjecutorNecesidadPorPropuestaNecesidadYRolResponsable(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EjecutorNecesidad> getEjecutorNecesidadPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EjecutorNecesidadDTO> getEjecutorNecesidadDTOPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param listadoEjecutorNecesidad
   * @throws JpaDinaeException
   */
  void cambiarRolReponsableProyect(Long idProyecto, List<EjecutorNecesidadDTO> listadoEjecutorNecesidad) throws JpaDinaeException;
}
