package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IInformeAvanceImplementacionLocal {

  /**
   * Find all.
   *
   * @return
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  List<InformeAvanceImplementacion> findAll() throws JpaDinaeException;

  /**
   *
   * @param informeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  InformeAvanceImplementacion saveOrUpdate(InformeAvanceImplementacion informeAvanceImplementacion) throws JpaDinaeException;

  /**
   * findByIdInformeAvanceImplementacion
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  InformeAvanceImplementacion findByIdInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idImplemenacionProyecto
   * @param idCompromisoProyectoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  InformeAvanceImplementacion findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(Long idImplemenacionProyecto, Long idCompromisoProyectoImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idTipoCompromiso
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  InformeAvanceImplementacion getInformeAvanceImplementacionPorTipoCompromisoEImplementacionProy(Long idTipoCompromiso, Long idImplementacionProy) throws JpaDinaeException;

}
