package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.servicios.SieduBancoNecesidadPersonaJPAService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import static org.castor.mapping.AbstractMappingLoaderFactory.LOG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class FuncionarioNecesidadEjbBean implements IFuncionarioNecesidadLocal, Serializable {
  
    private static final Logger LOG = LoggerFactory.getLogger(SieduBancoNecesidadPersonaJPAService.class);
  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;
  @Inject
  @GenericSDOQualifier
  private SDO sdo;

  @PostConstruct
  public void init() {
    LOG.trace("metodo: init()");
    if (sdo == null) {
      sdo = new GenericSDO();
    }
  }

  /**
   *
   * @param idFuncionario
   * @throws JpaDinaeException
   */
  @Override
  public void eliminarFuncionarioNecesidad(Long idFuncionario) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("FuncionarioNecesidad.EliminarPorID")
              .setParameter("idFuncionarioNecesidad", idFuncionario)
              .executeUpdate();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }

  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<FuncionarioNecesidad> getListaFuncionarioNecesidad(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("FuncionarioNecesidad.findAllPorPropuestaNecesidad")
              .setParameter("idPropuestaNecesidad", idPropuestaNecesidad)
              .getResultList();

    } catch (NoResultException e) {

      //NO EXISTE
      return null;

    } catch (NonUniqueResultException e) {

      throw new JpaDinaeException("Existe mas  para esta propuesta: ".concat(idPropuestaNecesidad.toString()), e);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }
  
    @Override
  public FuncionarioNecesidad create(FuncionarioNecesidad record) throws ServiceException {
    LOG.debug("metodo: create() ->> parametros: record {}", record);
    try {
      sdo.persist(entityManager, record);
      entityManager.flush();
      return record;
    } catch (Exception ex) {
      LOG.error("metodo: create() ->> mensaje: {}", ex.getMessage());
      throw new ServiceException(ex);
    }
  }

}
