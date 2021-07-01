package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.InstitucionesProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IInstitucionesProyectoLocal {

  /**
   *
   * @param idInstitucionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  InstitucionesProyecto obtenerInstitucionesProyectoPorId(Long idInstitucionesProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInstitucionesProyecto
   * @return
   * @throws JpaDinaeException
   */
  InstitucionesProyecto guardarInstitucionesProyecto(InstitucionesProyecto idInstitucionesProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<InstitucionesProyecto> getListaInstitucionesProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<InstitucionesProyectoDTO> getListaInstitucionesProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idInstitucionesProyecto
   * @throws JpaDinaeException
   */
  void eliminarInstitucionInvestigacion(Long idInstitucionesProyecto) throws JpaDinaeException;
}
