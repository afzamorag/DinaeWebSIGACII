package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IProgramasLocal;
import co.gov.policia.dinae.modelo.Programas;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RafaelGomez
 */
@Stateless
public class ProgramasEjbBean implements IProgramasLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return List<Programas>
   * @throws JpaDinaeException
   */
  @Override
  public List<Programas> getProgramas() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Programas.findAll")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<Programas> getProgramasByActivo() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Programas.findProgramasByActivo")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Programas> getProgramasByActivoAndIdUnidadPolicial(Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Programas.findProgramasByActivoAndIdUnidadPolicial")
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public Programas getProgramaByNombre(String nombre) throws JpaDinaeException {

    try {

      return (Programas) entityManager.createNamedQuery("Programas.findProgramaByNombre")
              .setParameter("nombre", nombre)
              .getSingleResult();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public Programas getProgramaByIdPrograma(Long idPrograma) throws JpaDinaeException {

    try {

      return (Programas) entityManager.createNamedQuery("Programas.findProgramaByIdPrograma")
              .setParameter("idPrograma", idPrograma)
              .getSingleResult();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

}
