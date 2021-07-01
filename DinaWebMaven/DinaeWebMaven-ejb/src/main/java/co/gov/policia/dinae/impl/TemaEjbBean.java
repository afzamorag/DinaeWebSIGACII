package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.modelo.Tema;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Stateless
public class TemaEjbBean implements ITemaLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idTema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Tema obtenerTemaPorId(Long idTema) throws JpaDinaeException {
    try {

      return entityManager.find(Tema.class, idTema);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param tema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public Tema guardarTema(Tema tema) throws JpaDinaeException {
    try {

      if (tema.getIdTema() == null) {

        entityManager.persist(tema);

      } else {

        entityManager.merge(tema);

      }

      return tema;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param destinoTema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<Tema> getListaTemaTodos(String destinoTema) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("Tema.findAll")
              .setParameter("tipoTema", destinoTema)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param destinoTema
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int cuentaTemaTodos(String destinoTema) throws JpaDinaeException {

    try {

      Object cuenta = entityManager.createNamedQuery("Tema.CuentaAll")
              .setParameter("tipoTema", destinoTema)
              .getSingleResult();

      if (cuenta == null) {
        return 0;
      }

      return ((Number) cuenta).intValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  @Override
  public List<Tema> findAllNoEstado() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("Tema.findAllNoEstado")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  @Override
  public List<Tema> findAllTipoNoEstado(String tipoTema) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("Tema.findAllTipoNoEstado")
              .setParameter("tipoTema", tipoTema)
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

}
