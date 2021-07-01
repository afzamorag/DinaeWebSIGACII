package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUsuarioRolLocal;
import co.gov.policia.dinae.modelo.Rol;
import co.gov.policia.dinae.modelo.UsuarioRol;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class UsuarioRolEjbBean implements IUsuarioRolLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idRol
   * @param idUnidadPolicial filtro utilizado para consulta, si este es NULL no se tiene en cuenta en el filtro
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<String> getIdentificacionesRolYUnidadPolical(Long idRol, Long idUnidadPolicial) throws JpaDinaeException {

    try {

      if (idUnidadPolicial == null) {

        return entityManager.createNamedQuery("IdentificacionUsuarioRol.findJefeUnidadPorUnidadPolicialYrol")
                .setParameter("idRol", idRol)
                .getResultList();

      } else {

        return entityManager.createNamedQuery("UsuarioUnidadPolicial.findUsuarioPorUnidadByRole")
                .setParameter("role", idRol)
                .setParameter("unidadPolicial", idUnidadPolicial)
                .getResultList();

      }

    } catch (NoResultException e) {
      return null;
    } catch (NonUniqueResultException e) {
      throw new JpaDinaeException(e.getMessage(), e);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<RolUsuarioDTO> getAllRolUsuarioDTOByIdentificacion(String identificacion) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("UsuarioRol.findAllPorIdentificacion")
              .setParameter("identificacion", identificacion)
              .getResultList();

    } catch (NoResultException e) {
      return null;
    } catch (NonUniqueResultException e) {
      throw new JpaDinaeException(e.getMessage(), e);
    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param identificacion
   * @param idRolesFiltro
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<RolUsuarioDTO> getAllRolUsuarioDTOByIdentificacionYroles(String identificacion, Long[] idRolesFiltro) throws JpaDinaeException {

    try {

      return entityManager.createNamedQuery("UsuarioRol.findAllPorIdentificacionYroles")
              .setParameter("identificacion", identificacion)
              .setParameter("roles", Arrays.asList(idRolesFiltro))
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param identificacion
   * @throws JpaDinaeException
   */
  @Override
  public void guardarUsuarioRolGrupoInvestigacion(String identificacion) throws JpaDinaeException {

    try {

      for (Long idRolGrupoJefeInvestigacion : IConstantesRole.ROLES_DEPENDE_JEFE_GRUPO_INVESTIGACION_UNIDAD) {

        UsuarioRol nuevoUsuarioRol = new UsuarioRol();
        nuevoUsuarioRol.setIdentificadorUsuario(identificacion);
        nuevoUsuarioRol.setRol(new Rol(idRolGrupoJefeInvestigacion));
        nuevoUsuarioRol.setFechaInicio(new Date());
        nuevoUsuarioRol.setActivo("S");

        entityManager.persist(nuevoUsuarioRol);
      }

    } catch (Exception e) {

      throw new JpaDinaeException(e);

    }

  }

  /**
   *
   * @param usuarioRol
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UsuarioRol guardarUsuarioRol(UsuarioRol usuarioRol) throws JpaDinaeException {
    try {

      if (usuarioRol.getIdUsuarioRol() == null) {
        entityManager.persist(usuarioRol);

      } else {
        entityManager.merge(usuarioRol);

      }

      return usuarioRol;

    } catch (Exception e) {

      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idRol
   * @param identificadorUsuario
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UsuarioRol findByUsuarioYRol(Long idRol, String identificadorUsuario) throws JpaDinaeException {
    try {

      List results = entityManager.createNamedQuery("UsuarioRol.findByUsuarioYRol", UsuarioRol.class)
              .setParameter("idRol", idRol)
              .setParameter("identificadorUsuario", identificadorUsuario)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (UsuarioRol) results.get(0);
      }

      return null;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param idUsuarioRol
   * @throws JpaDinaeException
   */
  @Override
  public void deshabilitarUsuarioRol(Long idUsuarioRol) throws JpaDinaeException {
    try {

      UsuarioRol obj = entityManager.find(UsuarioRol.class, idUsuarioRol);
      obj.setFechaFin(new Date());
      obj.setActivo("N");

      entityManager.merge(obj);

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

  /**
   *
   * @param identificadorUsuario
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<UsuarioRol> findByUsuarioYAdminRoles(String identificadorUsuario, Long... roles) throws JpaDinaeException {
    try {

      return entityManager.createNamedQuery("UsuarioRol.findByUsuarioYAdminRoles", UsuarioRol.class)
              .setParameter("identificadorUsuario", identificadorUsuario)
              .setParameter("roles", Arrays.asList(roles))
              .getResultList();

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }
  }

}
