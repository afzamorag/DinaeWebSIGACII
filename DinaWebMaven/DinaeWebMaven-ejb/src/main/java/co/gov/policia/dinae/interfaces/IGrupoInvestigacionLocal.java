package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IGrupoInvestigacionLocal {

  /**
   *
   * @param idGrupoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  GrupoInvestigacion obtenerGrupoInvestigacionPorId(Long idGrupoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idGrupoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  GrupoInvestigacion guardarGrupoInvestigacion(GrupoInvestigacion idGrupoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<GrupoInvestigacion> getListaGrupoInvestigacionTodos() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<GrupoInvestigacion> getListaGrupoInvestigacionTodosEstados() throws JpaDinaeException;

}
