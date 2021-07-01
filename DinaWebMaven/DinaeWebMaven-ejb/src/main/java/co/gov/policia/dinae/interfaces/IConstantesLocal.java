package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Constantes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IConstantesLocal {

  /**
   * Constantes activas por tipo
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<Constantes> getConstantesPorTipo(String tipo) throws JpaDinaeException;

  /**
   * Constantes activas por tipo
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<ConstantesDTO> getConstantesDTOPorTipo(String tipo) throws JpaDinaeException;

  /**
   * Constantes activas por tipo y codigo
   *
   * @param tipo
   * @param codigo
   * @return
   * @throws JpaDinaeException
   */
  List<Constantes> getConstantesPorTipoPorCodigo(String tipo, String codigo) throws JpaDinaeException;

  /**
   * Método que retorna constantes activas por un tipo y algunos codigos
   *
   * @param tipo tipo de contanstes
   * @param in Cadena separada por , que tiene los codigos que se van a retornar
   * @return
   * @throws JpaDinaeException
   */
  List<Constantes> getConstantesPorTipoPorCodigos(String tipo, String in) throws JpaDinaeException;

  /**
   * Método que busca una constante activa por un id
   *
   * @param idConstante
   * @return
   * @throws JpaDinaeException
   */
  Constantes getConstantesPorIdConstante(Long idConstante) throws JpaDinaeException;

  /**
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  List<Constantes> findAllPorTipoNoEstado(String tipo) throws JpaDinaeException;

  /**
   *
   * @param idConstantes
   * @return
   * @throws JpaDinaeException
   */
  Constantes obtenerConstantePorIdNoEstado(Long idConstantes) throws JpaDinaeException;

  /**
   * Método utilizado para crear o actualizar una constante del sistema
   *
   * @param constantes
   * @throws JpaDinaeException
   */
  void saveOrUpdate(Constantes constantes) throws JpaDinaeException;

  /**
   *
   * @param idConstante
   * @return
   * @throws JpaDinaeException
   */
  ConstantesDTO getConstantesDTOPorIdConstante(Long idConstante) throws JpaDinaeException;
}
