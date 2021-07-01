package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ActividadesImplementacion;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IActividadesImplementacionLocal {

  /**
   *
   * @param idInformAvanceImpl
   * @param idActividadPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  ActividadesImplementacion getActividadesImplementacionPorInformeAvanceImplYActividadPlanTrabajo(Long idInformAvanceImpl, Long idActividadPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param actividadesImplementacion
   * @return
   * @throws JpaDinaeException
   */
  ActividadesImplementacion guardarActividadesImplementacion(ActividadesImplementacion actividadesImplementacion) throws JpaDinaeException;

  /**
   *
   * @param actividadesImplementacion
   * @param actividadesPlanImplementacion
   * @return
   * @throws JpaDinaeException
   */
  ActividadesImplementacion guardarActividadesImplementacionYactualizaEstadoActividadPlanTrabajo(ActividadesImplementacion actividadesImplementacion, ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvance
   * @param listaEstadoActividad
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesImplementacion> getListaActividadesImplementacionInformeAvanceYestadosActividad(Long idInformeAvance, List<Long> listaEstadoActividad) throws JpaDinaeException;

  /**
   *
   * @param idInformeAvanceImpl
   * @return
   * @throws JpaDinaeException
   */
  List<ActividadesImplementacion> getListaActividadesImplementacionPorInformeAvanceImpl(Long idInformeAvanceImpl) throws JpaDinaeException;

}
