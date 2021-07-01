package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.DominioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IDominioLocal;
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
public class DominioEjbBean implements IDominioLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param tipoDom
   * @return
   * @throws JpaDinaeException
   */
  @Override

  public List<DominioDTO> getSieduDominioVigenteByTipoDominio(Short tipoDom) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("SieduDominio.VigenteByTipoDominio")
              .setParameter("tipoDom", tipoDom)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

}
