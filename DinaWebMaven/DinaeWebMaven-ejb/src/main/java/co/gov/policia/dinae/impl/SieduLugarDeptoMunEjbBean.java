package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.SieduLugarDeptoMunDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ISieduLugarDeptoMunLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
/**
 *
 * @author Andrés Felipe Zamora Garzón - Juan Carlos Cifuentes Murcia
 */
@Stateless
public class SieduLugarDeptoMunEjbBean implements ISieduLugarDeptoMunLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override

  public List<SieduLugarDeptoMunDTO> getLugarDeptoMun() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("SieduLugarDeptoMun.findAll")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }
}
