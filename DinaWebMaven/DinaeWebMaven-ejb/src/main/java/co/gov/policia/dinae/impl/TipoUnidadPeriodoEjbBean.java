package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ITipoUnidadPeriodoLocal;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
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
public class TipoUnidadPeriodoEjbBean implements ITipoUnidadPeriodoLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public List<TipoUnidadPeriodo> getTipoUnidadPeriodoByPeriodo(Periodo periodo) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("TipoUnidadPeriodo.findByPeriodo")
              .setParameter("idPeriodo", periodo.getIdPeriodo())
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }  //To change body of generated methods, choose Tools | Templates.
  }

}
