package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUnidadPolicialPeriodoLocal;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class UnidadPolicialPeriodoEjbBean implements IUnidadPolicialPeriodoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idPeriodo
   * @param idEstado1
   * @param idEstado2
   * @param idEstado3
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getUnidadPolicialNoPresentanPropuesta(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllUnidadesNoHanPresentadoUnidadesByEstado")
              .setParameter("idPeriodo", idPeriodo)
              .setParameter("idEstado1", idEstado1)
              .setParameter("idEstado2", idEstado2)
              .setParameter("idEstado3", idEstado3)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Long> getIdsUnidadPolicialPorPeriodo(Long idPeriodo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllIdsUnidadPolicialPeriodo")
              .setParameter("idPeriodo", idPeriodo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<String> getCorreoUnidadPolicialPorPeriodoEisNotNullCorreo(Long idPeriodo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicialPeriodo.findAllCorreoUnidadPolicialPorPeriodoEisNotNullCorreo")
              .setParameter("idPeriodo", idPeriodo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

}
