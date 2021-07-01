package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITipoGastoEventoLocal;
import co.gov.policia.dinae.modelo.TipoGastoEvento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/03
 */
@Stateless
public class TipoGastoEventoEjbBean implements ITipoGastoEventoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  EntityManager entityManager;

  @Override
  public TipoGastoEvento getTipoGastoEventoByNombreTipoGasto(String nombreTipoGasto, String tipo) throws JpaDinaeException {
    try {
      return (TipoGastoEvento) entityManager.createNamedQuery("TipoGastoEvento.findTipoGastoEventoByNombreTipoGastoAndTipo").
              setParameter("tipoGastoValor", nombreTipoGasto).
              setParameter("tipoGastoTipo", tipo).getSingleResult();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  @Override
  public void insertTipoGasto(TipoGastoEvento gastoEvento) throws JpaDinaeException {
    try {
      entityManager.persist(gastoEvento);
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @param tipoGastoEvento
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public TipoGastoEvento saveOrUpdate(TipoGastoEvento tipoGastoEvento) throws JpaDinaeException {
    try {
      if (tipoGastoEvento.getIdGastoEvento() == null) {
        tipoGastoEvento.setFechaRegistro(new Date());
        entityManager.persist(tipoGastoEvento);
      } else {
        entityManager.merge(tipoGastoEvento);
      }

      return tipoGastoEvento;

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idEventoProyecto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<TipoGastoEvento> findTipoGastoEventioByEvento(Long idEventoProyecto) throws JpaDinaeException {

    List<TipoGastoEvento> results = new ArrayList<TipoGastoEvento>();
    try {
      results = entityManager.createNamedQuery("TipoGastoEvento.findTipoGastoEventioByEvento", TipoGastoEvento.class)
              .setParameter("idEventoProyecto", idEventoProyecto)
              .getResultList();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }

    return results;
  }

  /**
   *
   * @param _old
   * @param _new
   * @throws JpaDinaeException
   */
  @Override
  public void replaceTipoGastoProyecto(List<TipoGastoEvento> _old, List<TipoGastoEvento> _new) throws JpaDinaeException {
    try {
      for (TipoGastoEvento gastoEvento : _old) {
        TipoGastoEvento tipoGastoEvento = entityManager.find(TipoGastoEvento.class, gastoEvento.getIdGastoEvento());
        entityManager.remove(tipoGastoEvento);
      }

      for (TipoGastoEvento gastoEvento : _new) {
        saveOrUpdate(gastoEvento);
      }
    } catch (JpaDinaeException ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
