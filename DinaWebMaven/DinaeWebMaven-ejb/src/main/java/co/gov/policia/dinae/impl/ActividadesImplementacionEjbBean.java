package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IActividadesImplementacionLocal;
import co.gov.policia.dinae.modelo.ActividadesImplementacion;
import co.gov.policia.dinae.modelo.ActividadesPlanImplementacion;
import java.io.Serializable;
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
public class ActividadesImplementacionEjbBean implements IActividadesImplementacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idInformAvanceImpl
   * @param idActividadPlanTrabajo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesImplementacion getActividadesImplementacionPorInformeAvanceImplYActividadPlanTrabajo(Long idInformAvanceImpl, Long idActividadPlanTrabajo) throws JpaDinaeException {

    try {

      return (ActividadesImplementacion) entityManager.createNamedQuery("ActividadesImplementacion.findPorInformeAvanceImplYActividadPlanTrabajo")
              .setParameter("idInformeAvanceImplementacion", idInformAvanceImpl)
              .setParameter("idActividadPlanImplementacion", idActividadPlanTrabajo)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param actividadesImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesImplementacion guardarActividadesImplementacion(ActividadesImplementacion actividadesImplementacion) throws JpaDinaeException {

    try {

      if (actividadesImplementacion.getIdActividadImplementacion() == null) {

        actividadesImplementacion.setFechaRegistro(new Date());
        entityManager.persist(actividadesImplementacion);

      } else {

        entityManager.merge(actividadesImplementacion);

      }

      return actividadesImplementacion;

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }

  /**
   *
   * @param actividadesImplementacion
   * @param actividadesPlanImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ActividadesImplementacion guardarActividadesImplementacionYactualizaEstadoActividadPlanTrabajo(ActividadesImplementacion actividadesImplementacion, ActividadesPlanImplementacion actividadesPlanImplementacion) throws JpaDinaeException {

    try {

      if (actividadesImplementacion.getIdActividadImplementacion() == null) {

        actividadesImplementacion.setFechaRegistro(new Date());
        entityManager.persist(actividadesImplementacion);

      } else {

        entityManager.merge(actividadesImplementacion);

      }

      entityManager.merge(actividadesPlanImplementacion);

      return actividadesImplementacion;

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param idInformeAvance
   * @param listaEstadoActividad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ActividadesImplementacion> getListaActividadesImplementacionInformeAvanceYestadosActividad(Long idInformeAvance, List<Long> listaEstadoActividad) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("ActividadesImplementacion.findPorInformeAvanceImplYestados")
              .setParameter("idInformeAvanceImplementacion", idInformeAvance)
              .setParameter("idListaEstado", listaEstadoActividad)
              .getResultList();
    } catch (Exception ex) {

      throw new JpaDinaeException(ex);
    }

  }

  /**
   *
   * @param idInformeAvanceImpl
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ActividadesImplementacion> getListaActividadesImplementacionPorInformeAvanceImpl(Long idInformeAvanceImpl) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("ActividadesImplementacion.findPorInformeAvanceImpl")
              .setParameter("idInformeAvanceImplementacion", idInformeAvanceImpl)
              .getResultList();
    } catch (Exception ex) {

      throw new JpaDinaeException(ex);
    }
  }
}
