package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IGradosValoresLocal;
import co.gov.policia.dinae.modelo.GradosValores;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class GradosValoresEjbBean implements IGradosValoresLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param anio
   * @param grado
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public GradosValores getGradosValoresPorAnioYgrado(Integer anio, String grado) throws JpaDinaeException {

    try {

      return (GradosValores) entityManager.createNamedQuery("GradosValores.findGradValorPorAnioVegenciaYGrado")
              .setParameter("anioVigencia", anio)
              .setParameter("grado", grado)
              .getSingleResult();

    } catch (NoResultException e) {

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

}
