package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Comentario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IComentarioLocal {

  /**
   * Consulta todos los comentarios que tiene asiganado una propuesta
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  List<Comentario> getComentarioNecesidadPorPropuestaNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param idProyeto
   * @return
   * @throws JpaDinaeException
   */
  List<Comentario> getComentarioProyectoPorProyecto(Long idProyeto) throws JpaDinaeException;

}
