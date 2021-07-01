package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IInformeAvanceImplementacionLocal;
import co.gov.policia.dinae.modelo.InformeAvanceImplementacion;
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
public class InformeAvanceImplementacionEjbBean implements IInformeAvanceImplementacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<InformeAvanceImplementacion> findAll() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("InformeAvanceImplementacion.findAll")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param informeAvanceImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvanceImplementacion saveOrUpdate(InformeAvanceImplementacion informeAvanceImplementacion) throws JpaDinaeException {

    try {

      if (informeAvanceImplementacion.getIdInformeAvanceImplementacion() == null) {

        informeAvanceImplementacion.setFechaCreacion(new Date());
        entityManager.persist(informeAvanceImplementacion);

      } else {

        entityManager.merge(informeAvanceImplementacion);

      }

      return informeAvanceImplementacion;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  @Override
  public InformeAvanceImplementacion findByIdInformeAvanceImplementacion(Long idInformeAvanceImplementacion) throws JpaDinaeException {

    try {

      return entityManager.find(InformeAvanceImplementacion.class, idInformeAvanceImplementacion);

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idImplemenacionProyecto
   * @param idCompromisoProyectoImplementacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvanceImplementacion findInformeAvanceImplementacionFinaByIdImplemenatcionProYIdCompromisoProImpl(Long idImplemenacionProyecto, Long idCompromisoProyectoImplementacion) throws JpaDinaeException {

    try {

      return (InformeAvanceImplementacion) entityManager.createNamedQuery("InformeAvanceImplementacion.findPorImplementacionProyectoYcompromisoProyectoImple")
              .setParameter("idImplementacionProy", idImplemenacionProyecto)
              .setParameter("idCompromisoImplementacion", idCompromisoProyectoImplementacion)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idTipoCompromiso
   * @param idImplementacionProy
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvanceImplementacion getInformeAvanceImplementacionPorTipoCompromisoEImplementacionProy(Long idTipoCompromiso, Long idImplementacionProy) throws JpaDinaeException {

    try {

      return (InformeAvanceImplementacion) entityManager.createNamedQuery("InformeAvanceImplementacion.findPorPorTipoCompromisoEImplementacionProy")
              .setParameter("idTipoCompromiso", idTipoCompromiso)
              .setParameter("idImplementacionProy", idImplementacionProy)
              .getSingleResult();

    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

}
