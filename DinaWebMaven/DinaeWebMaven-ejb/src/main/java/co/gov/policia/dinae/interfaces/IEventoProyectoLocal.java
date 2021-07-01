package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.EventoInvestigacionDTO;
import co.gov.policia.dinae.dto.EventoProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EventoInvestigacion;
import co.gov.policia.dinae.modelo.EventoProyecto;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IEventoProyectoLocal {

  /**
   *
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  public EventoProyecto findById(Long idEventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  public List<EventoProyecto> getListaEventosPorProyecto(Long idProyecto) throws JpaDinaeException;

  /**
   *
   * @param evento
   * @throws JpaDinaeException
   */
  public void insertEventoProyecto(EventoProyecto evento) throws JpaDinaeException;

  /**
   *
   * @param eventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  public EventoProyecto saveOrUpdate(EventoProyecto eventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param eventoProyecto
   * @throws co.gov.policia.dinae.exceptions.JpaDinaeException
   */
  void delete(EventoProyecto eventoProyecto) throws JpaDinaeException;

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  List<EventoProyectoDTO> getListaEventosPorProyectoDTO(Long idProyecto) throws JpaDinaeException;

  Set<String> getTodosAniosListaEvento() throws JpaDinaeException;

  /**
   *
   * @param tipoEvento
   * @param anio
   * @return
   * @throws JpaDinaeException
   */
  List<EventoInvestigacionDTO> getTodosListaEventoInvestigacion(Long tipoEvento, String anio) throws JpaDinaeException;

  /**
   *
   * @param eventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  EventoInvestigacion guardarEventoInvestigacion(EventoInvestigacion eventoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idEventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  EventoInvestigacion buscarEventoInvestigacionPorId(Long idEventoInvestigacion) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<EventoProyectoDTO> findAllByPlanTrabajoDTO(Long idPlanTrabajo) throws JpaDinaeException;

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  List<EventoProyecto> findAllByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException;

}
