package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.AsesoriaProyectoDTO;
import co.gov.policia.dinae.dto.InstitucionesProyectoDTO;
import co.gov.policia.dinae.dto.ProyectoVersionDTO;
import co.gov.policia.dinae.dto.SemilleroProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.GrupoInvestigacionProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyectoVersion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.TemaProyectoVersion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IVersionesLocal {

  /**
   *
   * @param idVersion
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  Long getIdProyectoVersion(Long idVersion, Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<ProyectoVersionDTO> getListaVersiones(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<AsesoriaProyectoDTO> getListaAsesoriaProyectoDTOPorIdProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @param indicadorBase
   * @param claveCasoUso
   * @return
   * @throws JpaDinaeException
   */
  List<IndicadoresProyectoVersion> getListaIndicadoresProyectoPorProyectoEindicadorBase(Long idProyectoVersion, Character indicadorBase, String claveCasoUso) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<InstitucionesProyectoDTO> getListaInstitucionesProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyectoUnidadPolicialParticipante(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<SemilleroProyectoDTO> getListaSemilleroProyectoDTOporProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<GrupoInvestigacionProyecto> getListaGrupoInvestigacionProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<Long> getIDTemaProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  List<InvestigadorProyecto> getListaInvestigadorProyectoPorProyecto(Long idProyectoVersion) throws JpaDinaeException;

  /**
   *
   * @param idTema
   * @param idProyectoVersion
   * @return
   * @throws JpaDinaeException
   */
  TemaProyectoVersion getTemaProyectoPorTemaYproyecto(Long idTema, Long idProyectoVersion) throws JpaDinaeException;
}
