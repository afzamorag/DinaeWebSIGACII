package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
public class UnidadPolicialEjbBean implements IUnidadPolicialLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IInvestigadorLocal iInvestigadorLocal;

  /**
   *
   * @param unidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UnidadPolicial atualizarUnidadPolicial(UnidadPolicial unidadPolicial) throws JpaDinaeException {

    try {

      if (unidadPolicial.getIdUnidadPolicial() == null) {

        entityManager.persist(unidadPolicial);
      } else {

        entityManager.merge(unidadPolicial);

      }

      return unidadPolicial;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UnidadPolicial obtenerUnidadPolicialPorId(Long idUnidadPolicial) throws JpaDinaeException {

    try {

      return entityManager.find(UnidadPolicial.class, idUnidadPolicial);

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public List<UnidadPolicial> getTipoUnidadPolicialByTipoUnidad(List<Long> listaTipoUnidad) throws JpaDinaeException {

    try {
      String tipoUnidad = "";
      for (Long l : listaTipoUnidad) {
        tipoUnidad += l + ",";
      }
      tipoUnidad = tipoUnidad.substring(0, tipoUnidad.length() - 1);

      return entityManager.createQuery("SELECT u FROM UnidadPolicial u WHERE u.activo = 'Y' AND u.tipoUnidad.idTipoUnidad IN (" + tipoUnidad + ")")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  @Override
  public List<UnidadPolicialDTO> getUnidadPolicial() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicial.findAllDto")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idUnidadPolicialExcepto
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getUnidadPolicialExcepto(Long idUnidadPolicialExcepto) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicial.findAllExcepto")
              .setParameter("idUnidadPolicial", idUnidadPolicialExcepto)
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getUnidadPolicialDiferenteAEjecutorPropuestaUnidad(Long idPropuestaNecesidad) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicial.findUnidadPolicialDiferenteAEjecutorPropuestaUnidad")
              .setParameter("idPropuestaNecesidad", idPropuestaNecesidad)
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param siglaFisica
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UnidadPolicialDTO obtenerUnidadPolicialDTOPorCodigoLaboral(String siglaFisica) throws JpaDinaeException {

    try {

      List results = entityManager.createNamedQuery("UnidadPolicial.findUnidadPolicialPorSiglaFisica")
              .setParameter("siglaFisica", siglaFisica)
              .setHint("eclipselink.refresh", "true")
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (UnidadPolicialDTO) results.get(0);
      }

      return null;

    } catch (NoResultException e) {
      Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "NO SE ENCONTRO UNIDAD POLICIAL PARA LA SIGLA DEPENDE ENVIADA:".concat(siglaFisica), e);
      return null;
    } catch (NonUniqueResultException e) {
      Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "SE ENCONTRARON MUCHAS UNIDADES POLICIALES PARA LA SIGLA DEPENDE ENVIADA:".concat(siglaFisica), e);
      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getAllUnidadesPolicialesActivas() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UnidadPolicial.findAll")
              .setHint("eclipselink.refresh", "true")
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getAllUnidadesPolicialesActivasOrdenAlfabetico() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UnidadPolicial.findAllOrderByNombre")
              .setHint("eclipselink.refresh", "true")
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicialDTO> getAllUnidadesPolicialesActivasDTO() throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("UnidadPolicialDTO.findAllActivas")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getTodasUnidadesActivasInactivas() throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UnidadPolicial.findAllActiviasInactivas")
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);

    }

  }

  /**
   *
   * @param filtroBusqueda
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UnidadPolicial> getTodasUnidadesActivasInactivas(String filtroBusqueda) throws JpaDinaeException {

    try {

      return entityManager.createQuery("SELECT u FROM UnidadPolicial u"
              + " WHERE upper(u.nombre) like '%" + filtroBusqueda.trim().toUpperCase() + "%' "
              + " OR u.siglaFisica = :siglaFisica OR u.siglaDepende = :siglaDepende OR u.siglaPapa = :siglaPapa"
              + " ORDER BY u.idUnidadPolicial ASC")
              .setParameter("siglaFisica", filtroBusqueda.trim())
              .setParameter("siglaDepende", filtroBusqueda.trim())
              .setParameter("siglaPapa", filtroBusqueda.trim())
              .setHint("eclipselink.refresh", "true")
              .getResultList();

    } catch (Exception ex) {

      throw new JpaDinaeException(ex.getLocalizedMessage(), ex);

    }

  }

  /**
   *
   * @param idTipoUnidad
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public int cuentaUnidadesActivasPorTipo(Long idTipoUnidad) throws JpaDinaeException {

    try {

      Object cuantos = entityManager.createNamedQuery("UnidadPolicial.cuentaUnidadesActivasYtipoUnidad")
              .setParameter("idTipoUnidad", idTipoUnidad)
              .getSingleResult();

      if (cuantos == null) {
        return 0;
      }

      return ((Number) cuantos).intValue();

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param identificacion
   * @param origen
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UnidadPolicial getUnidadPolicialPorIdentificacionOinvestigador(String identificacion, String origen) throws JpaDinaeException {

    try {

      if ("SIATH".equals(origen) && identificacion != null) {

        VistaFuncionario vistaFuncionario = iInvestigadorLocal.getInvestigadorSIATHByIdentificacion(identificacion);
        if (vistaFuncionario.getCodigoUnidadLaboral() != null) {

          return obtenerUnidadPolicialPorId(Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral().trim()));

        }
      }

      if ("INVESTIGADOR".equals(origen) && identificacion != null) {

        Investigador investigador = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(identificacion);
        if (investigador.getIdUnidadPolicialCrea() != null) {

          return obtenerUnidadPolicialPorId(investigador.getIdUnidadPolicialCrea());

        }

      }

      return null;

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }
  }
}
