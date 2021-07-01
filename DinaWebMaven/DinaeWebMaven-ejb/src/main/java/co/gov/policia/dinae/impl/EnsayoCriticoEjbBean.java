package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cguzman
 */
@Stateless
public class EnsayoCriticoEjbBean implements IEnsayoCriticoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param ensayoCritico
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EnsayoCritico saveOrUpdate(EnsayoCritico ensayoCritico) throws JpaDinaeException {
    try {
      if (ensayoCritico.getIdEnsayoCritico() == null) {
        ensayoCritico.setFechaCreacion(new Date());
        entityManager.persist(ensayoCritico);
      } else {
        ensayoCritico.setFechaModificacion(new Date());
        entityManager.merge(ensayoCritico);
      }

      return ensayoCritico;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idEnsayoCritico
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EnsayoCritico findByIdEnsayoCritico(Long idEnsayoCritico) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EnsayoCritico.findByIdEnsayoCritico", EnsayoCritico.class)
              .setParameter("idEnsayoCritico", idEnsayoCritico)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (EnsayoCritico) results.get(0);
      }

      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EnsayoCritico> findByPeriodo(Long idPeriodo) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("EnsayoCritico.findByPeriodo", EnsayoCritico.class)
              .setParameter("idPeriodo", idPeriodo)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPeriodo
   * @param identificadorUsuario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public EnsayoCritico findByPeriodoUsuario(Long idPeriodo, String identificadorUsuario) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EnsayoCritico.findByPeriodoUsuario", EnsayoCritico.class)
              .setParameter("idPeriodo", idPeriodo)
              .setParameter("identificadorUsuario", identificadorUsuario)
              .getResultList();
      if (results != null && !results.isEmpty()) {
        return (EnsayoCritico) results.get(0);
      }

      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<EnsayoCritico> findAll() throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("EnsayoCritico.findAll", EnsayoCritico.class)
              .getResultList();

      return results;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param siglaPapa
   * @param idEstados
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EnsayoCritico> findByPeriodoUnidadCobertura(String siglaPapa, List<Long> idEstados, Long idPeriodo) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("EnsayoCritico.findByPeriodoUnidadCobertura", EnsayoCritico.class);
      query.setParameter("siglaPapa", siglaPapa);
      query.setParameter("idEstados", idEstados);
      query.setParameter("idPeriodo", idPeriodo);
      List results = query.getResultList();
      return results;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }

  }

  /**
   *
   * @param idEstados
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<EnsayoCritico> findByPeriodoAllUnidades(List<Long> idEstados, Long idPeriodo) throws JpaDinaeException {
    try {
      Query query = entityManager.createNamedQuery("EnsayoCritico.findByPeriodoAllUnidades", EnsayoCritico.class);
      query.setParameter("idEstados", idEstados);
      query.setParameter("idPeriodo", idPeriodo);
      List results = query.getResultList();
      return results;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idPeriodo
   * @return @throws JpaDinaeException
   */
  @Override
  public int countByPeriodoVicin(Long idPeriodo) throws JpaDinaeException {

    try {

      Object cuenta = entityManager.createNamedQuery("EnsayoCritico.countByPeriodoVicin")
              .setParameter("idPeriodo", idPeriodo)
              .getSingleResult();

      if (cuenta == null) {
        return 0;
      }

      return ((Number) cuenta).intValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idConvocatoria
   * @throws JpaDinaeException
   */
  @Override
  public void ejecutarProcedimientoResultadoConvocatoriaEnsayo(Long idConvocatoria) throws JpaDinaeException {

    try {
      Query query = entityManager.createNamedQuery("EnsayoCritico.resultadoConvocatoriaEnsayo");
      query.setParameter("idConvocatoria", idConvocatoria);
      query.executeUpdate();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
