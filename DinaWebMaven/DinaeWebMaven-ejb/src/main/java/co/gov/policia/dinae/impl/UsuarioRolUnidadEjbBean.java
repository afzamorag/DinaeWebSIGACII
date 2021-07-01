package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.RolDTO;
import co.gov.policia.dinae.dto.UsuarioRolUnidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUsuarioRolUnidadLocal;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRolUnidad;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cguzman
 */
@Stateless
public class UsuarioRolUnidadEjbBean implements IUsuarioRolUnidadLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidad> findAll() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAll", UsuarioRolUnidad.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllUnidadRoles(boolean esOtro, Long idUnidadPolicial, Long... roles) throws JpaDinaeException {
    try {

      List<UsuarioRolUnidadDTO> lista = entityManager.createNamedQuery("UsuarioRolUnidad.findAllUnidadRoles", UsuarioRolUnidadDTO.class)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("roles", Arrays.asList(roles))
              .getResultList();

      if (esOtro) {

        List<UsuarioRolUnidadDTO> listaOtro = entityManager.createNamedQuery("UsuarioRolUnidadOtro.findAllUnidadRoles", UsuarioRolUnidadDTO.class)
                .setParameter("idUnidadPolicial", idUnidadPolicial)
                .setParameter("roles", Arrays.asList(roles))
                .getResultList();

        if (!listaOtro.isEmpty()) {

          lista.addAll(listaOtro);

        }
      }

      return lista;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllUnidadNoRolAdmin(Long idUnidadPolicial, Long... roles) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAllUnidadNoRolAdmin", UsuarioRolUnidadDTO.class)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .setParameter("roles", Arrays.asList(roles))
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllPorRol(List<Long> roles) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAllIdRol", UsuarioRolUnidadDTO.class)
              .setParameter("roles", roles)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllPorIdentificacion(String identificacion) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAllIdentificacion", UsuarioRolUnidadDTO.class)
              .setParameter("identificacionUsuario", identificacion)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllUnidad(Long idUnidadPolicial) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAllUnidad", UsuarioRolUnidadDTO.class)
              .setParameter("idUnidadPolicial", idUnidadPolicial)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRolUnidadDTO> findAllVicin() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("UsuarioRolUnidad.findAllVicin", UsuarioRolUnidadDTO.class).getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<RolDTO> findRolesByIds(Long... roles) throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("RolDTO.findRolesByIds", RolDTO.class)
              .setParameter("roles", Arrays.asList(roles))
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<RolDTO> findRolesUnidad() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("RolDTO.findRolesUnidad", RolDTO.class)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  @Override
  public List<RolDTO> findAllRoles() throws JpaDinaeException {
    try {
      return entityManager.createNamedQuery("RolDTO.findAll", RolDTO.class)
              .getResultList();
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }
}
