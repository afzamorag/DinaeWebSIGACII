package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.TipoUnidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author juan
 */
@Local
public interface ITipoUnidadLocal {

  /**
   * Método que retorna los tipos de unidades existentes
   *
   * @return
   * @throws JpaDinaeException
   */
  List<TipoUnidad> getTipoUnidades() throws JpaDinaeException;

  /**
   *
   * @param idTipoUnidad
   * @return
   * @throws JpaDinaeException
   */
  TipoUnidad getTipoUnidadById(Long idTipoUnidad) throws JpaDinaeException;
}
