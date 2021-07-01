package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.InformeAvance;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * Business component to manage or add business logic related to Informe de Avaces
 *
 * @author cguzman: Carlos Guzman Pulido - PointMind S.A.S. carlos.guzman@pointmind.com
 */
@Stateless
public class InformeAvanceEjbBean implements IInformeAvanceLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<InformeAvance> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("InformeAvance.findAll", InformeAvance.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param informeAvance
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvance saveOrUpdate(InformeAvance informeAvance) throws JpaDinaeException {
    try {
      if (informeAvance.getIdInformeAvance() == null) {

        informeAvance.setFechaRegistro(new Date());
        entityManager.persist(informeAvance);
      } else {

        entityManager.merge(informeAvance);
      }

      return informeAvance;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idInformeAvance
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvance findByIdInformeAvance(Long idInformeAvance) throws JpaDinaeException {
    try {
      InformeAvance informeAvance = null;

      List results = entityManager.createNamedQuery("InformeAvance.findByIdInformeAvance", InformeAvance.class)
              .getResultList();

      if (!results.isEmpty()) {
        informeAvance = (InformeAvance) results.get(0);
      }

      return informeAvance;
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
  public InformeAvance findLastInformeAvanceByProyecto(Long idProyecto) throws JpaDinaeException {

    try {

      InformeAvance informeAvance = null;

      List results = entityManager.createNamedQuery("InformeAvance.findLastInformeAvanceByProyecto", InformeAvance.class)
              .setParameter("idProyecto", idProyecto)
              .setMaxResults(1)
              .getResultList();

      if (!results.isEmpty()) {
        informeAvance = (InformeAvance) results.get(0);
      }

      return informeAvance;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @param idConstante
   * @param idCompromiso
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvance findInformeAvanceFinalByProyecto(Long idProyecto, Long idConstante, Long idCompromiso) throws JpaDinaeException {
    try {

      InformeAvance informeAvance = null;

      List results = entityManager.createNamedQuery("InformeAvance.findInformeAvanceFinalByProyecto", InformeAvance.class)
              .setParameter("idProyecto", idProyecto)
              .setParameter("idConstante", idConstante)
              .setParameter("idCompromiso", idCompromiso)
              .getResultList();

      if (!results.isEmpty()) {
        informeAvance = (InformeAvance) results.get(0);
      }

      return informeAvance;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvance obtenerInformeAvancePorCompromidoProyectoYproyecto(Long idCompromisoProyecto, Long idProyecto) throws JpaDinaeException {

    try {

      return (InformeAvance) entityManager.createNamedQuery("InformeAvance.findAlllInformePorCompromisoProyectoYproyecto")
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .setParameter("idProyecto", idProyecto)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());

    }
  }

  /**
   *
   * @param informeAvance
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public InformeAvance crearInformeAvanceCompromidoProyecto(InformeAvance informeAvance) throws JpaDinaeException {

    try {

      try {

        InformeAvance informeAvanceBusqueda = (InformeAvance) entityManager.createNamedQuery("InformeAvance.findAlllInformePorCompromisoProyectoYproyecto")
                .setParameter("idCompromisoProyecto", informeAvance.getCompromisoProyecto().getIdCompromisoProyecto())
                .setParameter("idProyecto", informeAvance.getProyecto().getIdProyecto())
                .getSingleResult();

        informeAvanceBusqueda.setFechaFinalizacion(informeAvance.getFechaFinalizacion());

        entityManager.merge(informeAvanceBusqueda);

        return informeAvanceBusqueda;

      } catch (NoResultException e) {

        //SI NO EXISTE UN INFORM DE AVANACE PARA ESTE COMPROMISO PROYECTO
        //LO ADICIONAMOS
        informeAvance.setFechaRegistro(new Date());

        entityManager.persist(informeAvance);

        return informeAvance;
      }

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }

  }

  @Override
  public InformeAvance crearInformeAvanceCompromidoProyectoActualizarCompromiso(InformeAvance informeAvance, CompromisoProyecto compromisoProyecto) throws JpaDinaeException {

    try {

      InformeAvance informe = crearInformeAvanceCompromidoProyecto(informeAvance);

      entityManager.merge(compromisoProyecto);

      return informe;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }

  }

}
