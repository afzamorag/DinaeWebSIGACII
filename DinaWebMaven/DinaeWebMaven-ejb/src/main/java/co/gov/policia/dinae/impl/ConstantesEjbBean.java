package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.modelo.Constantes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class ConstantesEjbBean implements IConstantesLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Constantes> getConstantesPorTipo(String tipo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<ConstantesDTO> getConstantesDTOPorTipo(String tipo) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("ConstantesDTO.findAllPorTipo")
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param tipo
   * @param codigo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Constantes> getConstantesPorTipoPorCodigo(String tipo, String codigo) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("Constantes.findAllPorTipoPorCodigo")
              .setParameter("tipo", tipo)
              .setParameter("codigo", codigo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public List<Constantes> getConstantesPorTipoPorCodigos(String tipo, String in) throws JpaDinaeException {
    try {
      String query = "SELECT c FROM Constantes c WHERE c.tipo = :tipo AND c.estado = 'ACTIVO' AND c.idConstantes IN (" + in + ")";
      return entityManager.createQuery(query)
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public Constantes getConstantesPorIdConstante(Long idConstante) throws JpaDinaeException {
    try {

      Constantes constantes = entityManager.find(Constantes.class, idConstante);

      return constantes;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idConstante
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public ConstantesDTO getConstantesDTOPorIdConstante(Long idConstante) throws JpaDinaeException {
    try {

      return (ConstantesDTO) entityManager.createNamedQuery("ConstantesDTO.findAllId")
              .setParameter("idConstantes", idConstante)
              .getSingleResult();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param tipo
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Constantes> findAllPorTipoNoEstado(String tipo) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("Constantes.findAllPorTipoNoEstado")
              .setParameter("tipo", tipo)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idConstantes
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Constantes obtenerConstantePorIdNoEstado(Long idConstantes) throws JpaDinaeException {
    try {

      List<Constantes> results = entityManager.createNamedQuery("Constantes.findAllPorIdNoEstado")
              .setParameter("idConstantes", idConstantes)
              .getResultList();

      if (results.isEmpty()) {
        return null;
      }

      return results.get(0);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public void saveOrUpdate(Constantes constantes) throws JpaDinaeException {
    try {

      if (constantes.getIdConstantes() == null) {

        List last = entityManager.createNativeQuery("SELECT MAX(ID_CONSTANTES) AS LAST_ID FROM CONSTANTES").getResultList();
        Long next = (!last.isEmpty()) ? ((BigDecimal) last.get(0)).longValue() + 1L : 0L;
        constantes.setIdConstantes(next);

        if (constantes.getCodigo() == null) {
          constantes.setCodigo(constantes.getCodigo());
        }

        entityManager.persist(constantes);

      } else {
        entityManager.merge(constantes);
      }
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

}
