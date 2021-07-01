package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Tema;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface ITemaLocal {

  /**
   * Devuelve un tema activo por id
   *
   * @param idTema
   * @return
   * @throws JpaDinaeException
   */
  Tema obtenerTemaPorId(Long idTema) throws JpaDinaeException;

  /**
   *
   *
   * @param idTema
   * @return
   * @throws JpaDinaeException
   */
  Tema guardarTema(Tema idTema) throws JpaDinaeException;

  /**
   * Devuelve una lista de temas activos por destino
   *
   * @param destinoTema
   * @return
   * @throws JpaDinaeException
   */
  List<Tema> getListaTemaTodos(String destinoTema) throws JpaDinaeException;

  /**
   * Devuelve el conteo de los temas activos por destino
   *
   * @param destinoTema
   * @return
   * @throws JpaDinaeException
   */
  int cuentaTemaTodos(String destinoTema) throws JpaDinaeException;

  /**
   * Devuelve el listado de todos los temas sin importar el estado
   *
   * @return
   * @throws JpaDinaeException
   */
  List<Tema> findAllNoEstado() throws JpaDinaeException;

  /**
   * Devuelve el listado de temas sin importar el estado, dependiento del tipo
   *
   * @param tipoTema
   * @return
   * @throws JpaDinaeException
   */
  List<Tema> findAllTipoNoEstado(String tipoTema) throws JpaDinaeException;

}
