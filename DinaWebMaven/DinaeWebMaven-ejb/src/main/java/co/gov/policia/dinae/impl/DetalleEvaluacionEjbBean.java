package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IDetalleEvaluacionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.DetalleEvaluacion;
import co.gov.policia.dinae.modelo.EvaluacionProyecto;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class DetalleEvaluacionEjbBean implements IDetalleEvaluacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  /**
   *
   * @param detalleEvaluacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public DetalleEvaluacion saveOrUpdate(DetalleEvaluacion detalleEvaluacion) throws JpaDinaeException {
    try {

      if (detalleEvaluacion.getIdDetalleEvaluacion() == null) {
        entityManager.persist(detalleEvaluacion);
      } else {
        entityManager.merge(detalleEvaluacion);
      }

      return detalleEvaluacion;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param listaEvaluacionProyecto
   * @param detalleEvaluacion
   * @param idProyecto
   * @param idNuevoEstado
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public DetalleEvaluacion saveOrUpdateYActualizarEstadoProyecto(List<EvaluacionProyecto> listaEvaluacionProyecto, DetalleEvaluacion detalleEvaluacion, Long idProyecto, Long idNuevoEstado, Long idPeriodo) throws JpaDinaeException {

    try {

      //ACTUALIZAMOS LAS EVALUACIONES
      for (EvaluacionProyecto evaluacionProyecto : listaEvaluacionProyecto) {

        if (evaluacionProyecto.getIdEvaluacionProyecto() == null) {

          entityManager.persist(evaluacionProyecto);

        } else {

          entityManager.merge(evaluacionProyecto);

        }

      }
      //ACTUALIZAMOS EL DATELLA EVALUACION 
      saveOrUpdate(detalleEvaluacion);

      //ACTUALIZAMOS EL ESTADO DEL PROYECTO
      entityManager.createNamedQuery("Proyecto.UpdateEstadoProyecto")
              .setParameter("estado", new Constantes(idNuevoEstado))
              .setParameter("idProyecto", idProyecto)
              .executeUpdate();

      //ACTUALIZAMOS PERIODO
      int cuentaReg = iProyectoLocal.contarAllProyectosInvestigacionNoEvaluadosByPeriodo(IConstantes.TIPO_ESTADO_PROYECTO_EVALUADO, idPeriodo);

      if (cuentaReg == 0) {

        //ACTUALIZAMOS EL ESTADO DEL PERIODO
        entityManager.createNamedQuery("Periodo.UpdateEstadoPeriodo")
                .setParameter("estado", new Constantes(IConstantes.TIPO_ESTADO_CONVOCATORIA_CULMINADA))
                .setParameter("idPeriodo", idPeriodo)
                .executeUpdate();

      }

      return detalleEvaluacion;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }
}
