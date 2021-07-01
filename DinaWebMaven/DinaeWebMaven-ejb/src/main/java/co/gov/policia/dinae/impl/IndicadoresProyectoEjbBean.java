package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IIndicadoresProyectoLocal;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresCompromisoProyecto;
import co.gov.policia.dinae.modelo.IndicadoresProyecto;
import java.io.Serializable;
import java.util.ArrayList;
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
public class IndicadoresProyectoEjbBean implements IIndicadoresProyectoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idIndicadoresProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresProyecto obtenerIndicadoresProyectoPorId(Long idIndicadoresProyecto) throws JpaDinaeException {
    try {

      return entityManager.find(IndicadoresProyecto.class, idIndicadoresProyecto);

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param indicadoresProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresProyecto guardarIndicadoresProyecto(IndicadoresProyecto indicadoresProyecto) throws JpaDinaeException {
    try {

      if (indicadoresProyecto.getIdIndicadorProyecto() == null) {

        indicadoresProyecto.setFechaRegistro(new Date());
        entityManager.persist(indicadoresProyecto);

      } else {

        entityManager.merge(indicadoresProyecto);

      }

      return indicadoresProyecto;

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param indicadoresCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public IndicadoresCompromisoProyecto guardarIndicadoresCompromisoProyecto(IndicadoresCompromisoProyecto indicadoresCompromisoProyecto) throws JpaDinaeException {
    try {

      if (indicadoresCompromisoProyecto.getIndicadoresProyecto().getIdIndicadorProyecto() == null) {

        indicadoresCompromisoProyecto.getIndicadoresProyecto().setFechaRegistro(new Date());
        entityManager.persist(indicadoresCompromisoProyecto.getIndicadoresProyecto());

      } else {

        entityManager.merge(indicadoresCompromisoProyecto.getIndicadoresProyecto());

      }

      if (indicadoresCompromisoProyecto.getIdIndicadorCompromisoProyecto() == null) {

        indicadoresCompromisoProyecto.setFechaRegistro(new Date());
        entityManager.persist(indicadoresCompromisoProyecto);

      } else {

        indicadoresCompromisoProyecto.setFechaModifica(new Date());
        entityManager.merge(indicadoresCompromisoProyecto);

      }

      return indicadoresCompromisoProyecto;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresProyecto> getListaIndicadoresProyectoTodos() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("IndicadoresProyecto.findAll")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param idProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresProyecto> getListaIndicadoresProyectoPorProyectoEindicadorBase(Long idProyecto, Character indicadorBase, String claveCasoUso) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("IndicadoresProyecto.findProyectoEindicadorBaseYcasoUso")
              .setParameter("idProyecto", idProyecto)
              .setParameter("indicadorBase", indicadorBase)
              .setParameter("casoUso", claveCasoUso)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idProyecto
   * @param indicadorBase
   * @param claveCasoUso
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyectoPorProyectoEindicadorBase(Long idProyecto, Character indicadorBase, String claveCasoUso, Long idCompromisoProyecto) throws JpaDinaeException {

    try {
      List<IndicadoresProyecto> listaIndicadoresProyecto = getListaIndicadoresProyectoPorProyectoEindicadorBase(idProyecto, indicadorBase, claveCasoUso);

      if (listaIndicadoresProyecto.isEmpty()) {
        return new ArrayList<IndicadoresCompromisoProyecto>();
      }

      List<IndicadoresCompromisoProyecto> listaIndicadoresCompromisoProyecto = new ArrayList<IndicadoresCompromisoProyecto>(listaIndicadoresProyecto.size());

      CompromisoProyecto compromisoProyecto = entityManager.find(CompromisoProyecto.class, idCompromisoProyecto);

      for (IndicadoresProyecto unIndicadoresProyecto : listaIndicadoresProyecto) {

        IndicadoresCompromisoProyecto indicadoresCompromisoProyecto;
        try {

          indicadoresCompromisoProyecto = (IndicadoresCompromisoProyecto) entityManager.createNamedQuery("IndicadoresCompromisoProyecto.findPorCompromisoProyectoEindicadorProyecto")
                  .setParameter("idIndicadorProyecto", unIndicadoresProyecto.getIdIndicadorProyecto())
                  .setParameter("idCompromisoProyecto", idCompromisoProyecto)
                  .getSingleResult();

        } catch (NoResultException e) {
          //NO SE HAN REGSITRADO VALORES
          indicadoresCompromisoProyecto = new IndicadoresCompromisoProyecto();
          indicadoresCompromisoProyecto.setCompromisoProyecto(compromisoProyecto);
          indicadoresCompromisoProyecto.setIndicadoresProyecto(unIndicadoresProyecto);

        }

        listaIndicadoresCompromisoProyecto.add(indicadoresCompromisoProyecto);
      }

      return listaIndicadoresCompromisoProyecto;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idIndicadorProyecto
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarIndicadoresProyecto(Long idIndicadorProyecto) throws JpaDinaeException {

    try {
      entityManager.createNamedQuery("IndicadoresProyecto.EliminarIndicadorPorId")
              .setParameter("idIndicadorProyecto", idIndicadorProyecto)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idCompromisoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<IndicadoresCompromisoProyecto> getListaIndicadoresCompromisoProyecto(Long idCompromisoProyecto) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("IndicadoresCompromisoProyecto.findAllPorCompromisoProyecto")
              .setParameter("idCompromisoProyecto", idCompromisoProyecto)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }

  }

  /**
   *
   * @param indicadoresProyectoList
   * @throws JpaDinaeException
   */
  @Override
  public void guardarListaIndicadoresProyecto(List<IndicadoresProyecto> indicadoresProyectoList) throws JpaDinaeException {

    try {

      for (IndicadoresProyecto indicadoresProyecto : indicadoresProyectoList) {

        guardarIndicadoresProyecto(indicadoresProyecto);

      }
    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }
}
