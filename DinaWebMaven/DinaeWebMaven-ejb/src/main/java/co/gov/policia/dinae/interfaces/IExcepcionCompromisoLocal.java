package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.ExcepcionesCompromiso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IExcepcionCompromisoLocal {

  /**
   *
   * @param excepcionesCompromiso
   * @return
   * @throws JpaDinaeException
   */
  ExcepcionesCompromiso guardarExcepcionesCompromiso(ExcepcionesCompromiso excepcionesCompromiso) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<ExcepcionesCompromiso> getListaExcepcionesCompromisoPorUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param idCompromisoPeriodo
   * @return
   * @throws JpaDinaeException
   */
  ExcepcionesCompromiso getUltimaExcepcionesCompromisoPorUnidadPolicialYcompromisoPeriodo(Long idUnidadPolicial, Long idCompromisoPeriodo) throws JpaDinaeException;

}
