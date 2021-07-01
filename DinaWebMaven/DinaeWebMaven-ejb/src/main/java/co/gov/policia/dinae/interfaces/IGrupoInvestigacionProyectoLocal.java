package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.GrupoInvestigacionProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IGrupoInvestigacionProyectoLocal {

  /**
   *
   * @param idGrupoInvestigacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  GrupoInvestigacionProyecto obtenerGrupoInvestigacionProyectoPorId(Long idGrupoInvestigacionProyecto) throws JpaDinaeException;

  /**
   *
   * @param grupoInvestigacionProyecto
   * @return
   * @throws JpaDinaeException
   */
  GrupoInvestigacionProyecto guardarGrupoInvestigacionProyecto(GrupoInvestigacionProyecto grupoInvestigacionProyecto) throws JpaDinaeException;

  /**
   *
   * @param idGrupoInvestigacionProyecto
   * @throws JpaDinaeException
   */
  void eliminarGrupoInvestigacionProyecto(Long idGrupoInvestigacionProyecto) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoTodos() throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<GrupoInvestigacionProyectoDTO> getListaGrupoInvestigacionProyectoDTOPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoPorProyecto(Long idProyecto) throws JpaDinaeException;

}
