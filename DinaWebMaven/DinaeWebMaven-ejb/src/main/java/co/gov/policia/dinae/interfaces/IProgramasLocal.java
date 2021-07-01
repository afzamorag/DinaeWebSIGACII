package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Programas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author RafaelGomez
 */
@Local
public interface IProgramasLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<Programas> getProgramas() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<Programas> getProgramasByActivo() throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<Programas> getProgramasByActivoAndIdUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param nombre
   * @return
   * @throws JpaDinaeException
   */
  Programas getProgramaByNombre(String nombre) throws JpaDinaeException;

  /**
   *
   * @param idPrograma
   * @return
   * @throws JpaDinaeException
   */
  Programas getProgramaByIdPrograma(Long idPrograma) throws JpaDinaeException;

}
