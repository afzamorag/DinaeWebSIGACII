package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.InformeAvance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * Business component to manage or add business logic related to Informe de Avaces.
 *
 * @see InformeAvance
 * @author cguzman: Carlos Guzman Pulido - PointMind S.A.S. carlos.guzman@pointmind.com
 */
@Local
public interface IInformeAvanceLocal {

  /**
   * Find all.
   *
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List findAll() throws JpaDinaeException;

  /**
   *
   * @param informeAvance
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance saveOrUpdate(InformeAvance informeAvance) throws JpaDinaeException;

  /**
   * findByIdInformeAvance
   *
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance findByIdInformeAvance(Long idInformeAvance) throws JpaDinaeException;

  /**
   * findLastInformeAvanceByProyecto
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance findLastInformeAvanceByProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   * findInformeAvanceFinalByProyecto
   *
   * @param idProyecto
   * @param idConstante
   * @param idCompromiso
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance findInformeAvanceFinalByProyecto(Long idProyecto, Long idConstante, Long idCompromiso) throws JpaDinaeException;

  /**
   *
   * @param informeAvance
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance crearInformeAvanceCompromidoProyecto(InformeAvance informeAvance) throws JpaDinaeException;

  /**
   *
   * @param informeAvance
   * @param compromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance crearInformeAvanceCompromidoProyectoActualizarCompromiso(InformeAvance informeAvance, CompromisoProyecto compromisoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idCompromisoProyecto
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  InformeAvance obtenerInformeAvancePorCompromidoProyectoYproyecto(Long idCompromisoProyecto, Long idProyecto) throws JpaDinaeException;

}
