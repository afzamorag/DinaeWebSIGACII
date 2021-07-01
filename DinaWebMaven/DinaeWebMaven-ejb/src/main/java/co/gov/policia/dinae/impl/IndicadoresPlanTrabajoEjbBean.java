package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IIndicadoresPlanTrabajoLocal;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajo;
import co.gov.policia.dinae.modelo.IndicadoresPlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class IndicadoresPlanTrabajoEjbBean implements IIndicadoresPlanTrabajoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idIndicadoresPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresPlanTrabajo obtenerIndicadoresPlanTrabajoPorId(Long idIndicadoresPlanTrabajo) throws JpaDinaeException {
    try {

      return entityManager.find(IndicadoresPlanTrabajo.class, idIndicadoresPlanTrabajo);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param indicadoresPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresPlanTrabajo guardarIndicadoresPlanTrabajo(IndicadoresPlanTrabajo indicadoresPlanTrabajo) throws JpaDinaeException {
    try {

      if (indicadoresPlanTrabajo.getIdIndicadorPlanTrabajo() == null) {

        indicadoresPlanTrabajo.setFechaRegistro(new Date());
        entityManager.persist(indicadoresPlanTrabajo);

      } else {

        entityManager.merge(indicadoresPlanTrabajo);

      }

      return indicadoresPlanTrabajo;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idPlanTrabajoImplementacion
   * @return @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresPlanTrabajo> getListaIndicadoresPlanTrabajoPorPlanTrabajoImplementacion(Long idPlanTrabajoImplementacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("IndicadoresPlanTrabajo.findPlanTrabajoImplementacion")
              .setParameter("idPlanTrabajo", idPlanTrabajoImplementacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idIndicadoresPlanTrabajo
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarIndicadoresPlanTrabajo(Long idIndicadoresPlanTrabajo) throws JpaDinaeException {

    try {
      entityManager.createNamedQuery("IndicadoresPlanTrabajo.EliminarIndicadorPorId")
              .setParameter("idIndicadorPlanTrabajo", idIndicadoresPlanTrabajo)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @param idPlanTrabajoImpl
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresPlanTrabajoImplementacion> getListaIndicadoresPlanTrabajoImplementacionPorPlanTrabajoImplYidInformeAvanceImplementacion(Long idInformeAvanceImplementacion, Long idPlanTrabajoImpl) throws JpaDinaeException {

    try {

      //BUSCAMOS LOS INDICADORES PLAN TRABAJO POR idPlanTrabajoImpl
      List<IndicadoresPlanTrabajo> listaIndicadoresPlanTrabajo = getListaIndicadoresPlanTrabajoPorPlanTrabajoImplementacion(idPlanTrabajoImpl);

      List<IndicadoresPlanTrabajoImplementacion> listaIndicadoresPlanTrabajoImplementacion = new ArrayList<IndicadoresPlanTrabajoImplementacion>();

      for (IndicadoresPlanTrabajo unIndicadoresPlanTrabajo : listaIndicadoresPlanTrabajo) {

        IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoImplementacion;
        try {

          indicadoresPlanTrabajoImplementacion = (IndicadoresPlanTrabajoImplementacion) entityManager.createNamedQuery("IndicadoresPlanTrabajoImplementacion.findPorPlanTrabajoImplYidInformeAvanceImplementacion")
                  .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
                  .setParameter("idIndicadorPlanTrabajo", unIndicadoresPlanTrabajo.getIdIndicadorPlanTrabajo())
                  .getSingleResult();

        } catch (NoResultException e) {
          //SI NO EXISTE, LO CREAMOS
          indicadoresPlanTrabajoImplementacion = new IndicadoresPlanTrabajoImplementacion();
          indicadoresPlanTrabajoImplementacion.setIndicadoresPlanTrabajo(unIndicadoresPlanTrabajo);
          indicadoresPlanTrabajoImplementacion.setInformeAvanceImplementacion(new InformeAvanceImplementacion(idInformeAvanceImplementacion));

          entityManager.persist(indicadoresPlanTrabajoImplementacion);

        }

        listaIndicadoresPlanTrabajoImplementacion.add(indicadoresPlanTrabajoImplementacion);

      }

      return listaIndicadoresPlanTrabajoImplementacion;

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param indicadoresPlanTrabajoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresPlanTrabajoImplementacion guardarIndicadoresPlanTrabajoImplementacion(IndicadoresPlanTrabajoImplementacion indicadoresPlanTrabajoImplementacion) throws JpaDinaeException {

    if (indicadoresPlanTrabajoImplementacion.getIdIndicadorPlanTrabajoImplementacion() == null) {

      entityManager.persist(indicadoresPlanTrabajoImplementacion);

    } else {

      entityManager.merge(indicadoresPlanTrabajoImplementacion);

    }

    return indicadoresPlanTrabajoImplementacion;

  }

  /**
   *
   * @param idInformeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresPlanTrabajoImplementacion> getListaIndicadoresPlanTrabajoImplementacionPorInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("IndicadoresPlanTrabajoImplementacion.findPorInformeAvanceImplementacion")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImplementacion)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }
}
