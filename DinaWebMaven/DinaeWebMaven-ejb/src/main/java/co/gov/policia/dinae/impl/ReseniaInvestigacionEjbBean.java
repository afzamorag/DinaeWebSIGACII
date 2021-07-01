package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IReseniaInvestigacionLocal;
import co.gov.policia.dinae.modelo.ReseniaInvestigacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class ReseniaInvestigacionEjbBean implements IReseniaInvestigacionLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   * findAll
   *
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("ReseniaInvestigacion.findAll", ReseniaInvestigacion.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   * findByIdReseniaInvestigacion
   *
   * @param idReseniaInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ReseniaInvestigacion findByIdReseniaInvestigacion(Long idReseniaInvestigacion) throws JpaDinaeException {
    try {

      ReseniaInvestigacion reseniaInvestigacion = null;

      List results = entityManager.createNamedQuery("ResultadosInvestigacion.findByIdResultadoInvestigacion", ReseniaInvestigacion.class)
              .setParameter("idResultadoInvestigacion", idReseniaInvestigacion)
              .getResultList();

      if (!results.isEmpty()) {
        reseniaInvestigacion = (ReseniaInvestigacion) results.get(0);
      }

      return reseniaInvestigacion;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   * saveOrUpdate
   *
   * @param reseniaInvestigacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ReseniaInvestigacion saveOrUpdate(ReseniaInvestigacion reseniaInvestigacion) throws JpaDinaeException {
    try {
      if (reseniaInvestigacion.getIdReseniaInvestigacion() == null) {
        reseniaInvestigacion.setFechaRegistro(new Date());
        entityManager.persist(reseniaInvestigacion);
      } else {
        entityManager.merge(reseniaInvestigacion);
      }

      return reseniaInvestigacion;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   * findByInformeFinalProyecto
   *
   * @param idInformeAvance
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ReseniaInvestigacion findByInformeFinalProyecto(Long idInformeAvance, Long idProyecto) throws JpaDinaeException {
    try {
      ReseniaInvestigacion reseniaInvestigacion = null;

      List results = entityManager.createNamedQuery("ReseniaInvestigacion.findByInformeFinalProyecto", ReseniaInvestigacion.class)
              .setParameter("idInformeAvance", idInformeAvance)
              .setParameter("idProyecto", idProyecto)
              .getResultList();

      if (!results.isEmpty()) {
        reseniaInvestigacion = (ReseniaInvestigacion) results.get(0);
      }

      return reseniaInvestigacion;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
