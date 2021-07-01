package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.SieduEntidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEntidadLocal;
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
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class EntidadEjbBean implements IEntidadLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override

  public List<SieduEntidadDTO> getEntidad() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("SieduEntidad.findByIdDescripcion")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

}
