package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.EventoInvestigacionDTO;
import co.gov.policia.dinae.dto.EventoProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.modelo.EventoInvestigacion;
import co.gov.policia.dinae.modelo.EventoProyecto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class EventoProyectoEjbBean implements IEventoProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public List<EventoProyecto> getListaEventosPorProyecto(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventoProyecto.findAllByProyecto").
              setParameter("idProyecto", idProyecto).getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

  @Override
  public void insertEventoProyecto(EventoProyecto evento) throws JpaDinaeException {
    try {
      entityManager.persist(evento);
    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param eventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventoProyecto saveOrUpdate(EventoProyecto eventoProyecto) throws JpaDinaeException {

    try {
      if (eventoProyecto.getIdEventoProyecto() == null) {
        eventoProyecto.setFechaRegistro(new Date());
        entityManager.persist(eventoProyecto);
      } else {
        entityManager.merge(eventoProyecto);
      }

      return eventoProyecto;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param eventoProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void delete(EventoProyecto eventoProyecto) throws JpaDinaeException {
    try {
      EventoProyecto evento = entityManager.find(EventoProyecto.class, eventoProyecto.getIdEventoProyecto());
      entityManager.remove(evento);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EventoProyectoDTO> getListaEventosPorProyectoDTO(Long idProyecto) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventoProyectoDTO.findAllByProyectoDTO").
              setParameter("idProyecto", idProyecto).getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

  /**
   *
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventoProyecto findById(Long idEventoProyecto) throws JpaDinaeException {
    try {
      return entityManager.find(EventoProyecto.class, idEventoProyecto);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EventoInvestigacionDTO> getTodosListaEventoInvestigacion(Long tipoEvento, String anio) throws JpaDinaeException {

    try {

      if (tipoEvento == null && anio == null) {

        return entityManager.createNamedQuery("EventoInvestigacion.findAllDTO")
                .getResultList();

      } else if (tipoEvento != null && anio == null) {

        return entityManager.createNamedQuery("EventoInvestigacion.findAllDTOPorTipoEvento")
                .setParameter("idTipoEvento", tipoEvento)
                .getResultList();

      } else if (tipoEvento == null && anio != null) {

        return entityManager.createNamedQuery("EventoInvestigacion.findAllDTOPorAnio")
                .setParameter("anio", anio)
                .getResultList();
      } else {

        return entityManager.createNamedQuery("EventoInvestigacion.findAllDTOPorTipoEventoyAnio")
                .setParameter("idTipoEvento", tipoEvento)
                .setParameter("anio", anio)
                .getResultList();

      }

    } catch (Exception ex) {

      throw new JpaDinaeException(ex);

    }

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public Set<String> getTodosAniosListaEvento() throws JpaDinaeException {

    try {

      Object inicio = entityManager.createNamedQuery("EventoInvestigacion.findAllAniosEventosInvestigacion")
              .getSingleResult();

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }

  /**
   *
   * @param eventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventoInvestigacion guardarEventoInvestigacion(EventoInvestigacion eventoInvestigacion) throws JpaDinaeException {

    try {

      if (eventoInvestigacion.getIdEventoInvestigacion() == null) {

        eventoInvestigacion.setFechaRegistro(new Date());
        entityManager.persist(eventoInvestigacion);

      } else {

        eventoInvestigacion.setFechaActualiza(new Date());
        entityManager.merge(eventoInvestigacion);
      }

      return eventoInvestigacion;

    } catch (Exception ex) {

      throw new JpaDinaeException(ex);

    }

  }

  /**
   *
   * @param idEventoInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EventoInvestigacion buscarEventoInvestigacionPorId(Long idEventoInvestigacion) throws JpaDinaeException {

    try {

      return entityManager.find(EventoInvestigacion.class, idEventoInvestigacion);
    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }

  }

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EventoProyectoDTO> findAllByPlanTrabajoDTO(Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventoProyectoDTO.findAllByPlanTrabajoDTO").
              setParameter("idPlanTrabajo", idPlanTrabajo).getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

  /**
   *
   * @param idPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EventoProyecto> findAllByPlanTrabajo(Long idPlanTrabajo) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("EventoProyecto.findAllByPlanTrabajo").
              setParameter("idPlanTrabajo", idPlanTrabajo).getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getLocalizedMessage(), e);
    }
  }

}
