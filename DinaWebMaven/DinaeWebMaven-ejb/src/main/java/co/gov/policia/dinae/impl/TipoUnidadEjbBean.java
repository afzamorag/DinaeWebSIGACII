package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITipoUnidadLocal;
import co.gov.policia.dinae.modelo.TipoUnidad;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
@Stateless
public class TipoUnidadEjbBean implements ITipoUnidadLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   * Retorna los tipos de unidades ejecutando el NameQuery TipoUnidad.findAll
   *
   *
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<TipoUnidad> getTipoUnidades() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("TipoUnidad.findAll").getResultList();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public TipoUnidad getTipoUnidadById(Long idTipoUnidad) throws JpaDinaeException {
    try {
      return (TipoUnidad) entityManager.createNamedQuery("TipoUnidad.findById").setParameter("idTipoUnidad", idTipoUnidad).getSingleResult();
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }    //To change body of generated methods, choose Tools | Templates.
  }

}
