package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ImplementacionesProyectoDTO;
import co.gov.policia.dinae.dto.ImplentacionProyectoCompromisosDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 * @since 2013/12/21
 */
@Local
public interface IImplementacionProyectoLocal {

  /**
   *
   * @param idImplementacionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  public ImplementacionesProyecto getImplementacionProyecto(Long idImplementacionesProyecto) throws JpaDinaeException;

  /**
   *
   * @param implementacionProy
   * @param compromisoPlanTrabajo
   * @param compromisoInformeAvance
   * @param compromisoInformeFinal
   * @throws JpaDinaeException
   */
  public void habilitarImplementacionProyecto(ImplementacionesProyecto implementacionProy,
          CompromisoImplementacion compromisoPlanTrabajo, CompromisoImplementacion compromisoInformeAvance,
          CompromisoImplementacion compromisoInformeFinal) throws JpaDinaeException;

  /**
   *
   * @param listaIdEstadoImpl
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<ImplentacionProyectoCompromisosDTO> findAllImplementacionesVigentes(List<Long> listaIdEstadoImpl, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @param unidadResponsable
   * @return
   * @throws JpaDinaeException
   */
  ImplementacionesProyecto getImplementacionProyectoByProyectoAndUnidadPolicial(Long idProyecto, Long unidadResponsable) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ImplementacionesProyectoDTO> getImplementacionProyectoByProyecto(Long idProyecto) throws JpaDinaeException;

}
