package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioUnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
@Stateless
public class UsuarioUnidadPolicialEjbBean implements IUsuarioUnidadPolicialLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  /**
   *
   * @param idUnidadPolicial
   * @param identificacion
   * @throws JpaDinaeException
   */
  @Override
  public void actualizarUnidadPolicialUsuario(Long idUnidadPolicial, String identificacion) throws JpaDinaeException {

    try {

      //PRIMERO CONSULTAMOS SI EL USUARIO TIENE UN UniarioUnidad
      UsuarioUnidadPolicial usuarioUnidadPolicial;
      try {

        usuarioUnidadPolicial = (UsuarioUnidadPolicial) entityManager.createNamedQuery("UsuarioUnidadPolicial.findUsusarioUnidadPolicialPorIdentificacion")
                .setParameter("identificacion", identificacion)
                .getSingleResult();

      } catch (NoResultException nre) {

        usuarioUnidadPolicial = new UsuarioUnidadPolicial();
        usuarioUnidadPolicial.setIdentificadorUsuario(identificacion);

      } catch (NonUniqueResultException nure) {

        throw nure;

      } catch (Exception e) {

        throw e;
      }

      if (idUnidadPolicial == null && usuarioUnidadPolicial.getIdUsuarioUnidadPolicial() != null) {
        //ELIMINAMOS EL REGISTRO
        //SIGNIFICA QUE EL USUARIO LE ELIMINAN LA UNIDAD POLICIAL
        entityManager.refresh(usuarioUnidadPolicial);
        entityManager.remove(usuarioUnidadPolicial);

      } else if (idUnidadPolicial != null) {

        //ASIGNAMOS LA UNIDAD POLICIAL
        usuarioUnidadPolicial.setUnidadPolicial(new UnidadPolicial(idUnidadPolicial));
        if (usuarioUnidadPolicial.getIdUsuarioUnidadPolicial() == null) {

          entityManager.persist(usuarioUnidadPolicial);

        } else {

          entityManager.merge(usuarioUnidadPolicial);

        }
      }

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);

    }
  }

  /**
   *
   * @param tipoUnidades
   * @param role
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<VistaFuncionario> getUsuarioByTipoUnidadByRole(List<TipoUnidadPeriodo> tipoUnidades, Long role) throws JpaDinaeException {

    try {
      for (TipoUnidadPeriodo t : tipoUnidades) {

        List<String> listaIdentificacionesUsuario = entityManager.createNamedQuery("UsuarioUnidadPolicial.findAllPorUnidadByRole")
                .setParameter("tipoUnidad", t.getTipoUnidad().getIdTipoUnidad())
                .setParameter("role", role)
                .getResultList();

        if (!listaIdentificacionesUsuario.isEmpty()) {

          String identificacionesSeparadas = UtilJPA.generateCollection(listaIdentificacionesUsuario, true);
          return entityManager.createQuery("SELECT v FROM VistaFuncionario v WHERE v.identificacion IN ".concat(identificacionesSeparadas))
                  .getResultList();
        }
      }

      return new ArrayList<VistaFuncionario>();

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param idUnidadPolicial
   * @param role
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<VistaFuncionario> getUsuarioByUnidadByRole(Long idUnidadPolicial, Long role) throws JpaDinaeException {
    try {
      List<VistaFuncionario> funcionarios = new ArrayList<VistaFuncionario>();
      List<String> usuarios = entityManager.createNamedQuery("UsuarioUnidadPolicial.findUsuarioPorUnidadByRole").
              setParameter("unidadPolicial", idUnidadPolicial).
              setParameter("role", role).
              getResultList();
      if (!usuarios.isEmpty()) {
        for (String usuario : usuarios) {
          VistaFuncionario vf = (VistaFuncionario) entityManager.createNamedQuery("VistaFuncionario.findAllPorIdentificacion")
                  .setParameter("identificacion", usuario.trim()).
                  getSingleResult();
          funcionarios.add(vf);
        }
      }
      return funcionarios;
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   * @param listaIdTipoUnidad
   * @param idRole
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<String> getCorreoFuncionarioPorListaTipoUnidadYRole(List<Long> listaIdTipoUnidad, Long idRole) throws JpaDinaeException {

    try {

      String sql = "SELECT v.correo FROM VistaFuncionario v "
              + " WHERE v.correo IS NOT NULL AND v.identificacion IN "
              + " ( SELECT u.identificadorUsuario FROM UsuarioUnidadPolicial u "
              + " WHERE u.unidadPolicial.tipoUnidad.idTipoUnidad IN " + UtilJPA.generateCollection(listaIdTipoUnidad, false) + " AND u.identificadorUsuario "
              + " IN (SELECT ur.identificadorUsuario FROM  UsuarioRol ur WHERE ur.rol.idRol = :idRol) )";

      return entityManager.createQuery(sql)
              .setParameter("idRol", idRole)
              .getResultList();

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage(), e);
    }

  }

  /**
   *
   * @param listaIdRoles
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<String> getCorreoFuncionarioPorListaRoles(List<Long> listaIdRoles) throws JpaDinaeException {

    try {

      String sql = "SELECT v.correo FROM VistaFuncionario v "
              + " WHERE v.correo IS NOT NULL AND v.identificacion IN "
              + " ( SELECT u.identificadorUsuario FROM UsuarioRol u "
              + " WHERE u.rol.idRol IN " + UtilJPA.generateCollection(listaIdRoles, false) + " AND u.activo = 'S' )";

      return entityManager.createQuery(sql)
              .getResultList();

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
  public UnidadPolicialDTO findAllPorIdentificacion(String identificacion) throws JpaDinaeException {
    try {
      List results = entityManager.createNamedQuery("UsuarioUnidadPolicial.findAllPorIdentificacion")
              .setParameter("identificacion", identificacion).getResultList();
      if (results != null && !results.isEmpty()) {
        return (UnidadPolicialDTO) results.get(0);
      }

      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

  @Override
  public List<UnidadPolicialDTO> getAllUnidadPolicialDTOPorIdentificacion(String identificacion) throws JpaDinaeException {

    try {
      return entityManager.createNamedQuery("UsuarioUnidadPolicial.findAllPorIdentificacion")
              .setParameter("identificacion", identificacion)
              .getResultList();

    } catch (NoResultException e) {
      return null;
    } catch (NonUniqueResultException e) {
      throw new JpaDinaeException(e.getMessage(), e);
    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  @Override
  public UsuarioUnidadPolicial guardarUsuarioUnidadPolicial(UsuarioUnidadPolicial usuarioUnidadPolicial) throws JpaDinaeException {
    try {

      if (usuarioUnidadPolicial.getIdUsuarioUnidadPolicial() == null) {
        entityManager.persist(usuarioUnidadPolicial);

      } else {
        entityManager.merge(usuarioUnidadPolicial);

      }

      return usuarioUnidadPolicial;

    } catch (Exception e) {

      throw new JpaDinaeException(e.getMessage());
    }
  }

  /**
   *
   * @param idUnidad
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public UsuarioUnidadPolicial findByIdUnidadIdentificacion(Long idUnidad, String identificacion) throws JpaDinaeException {
    try {
      List results = entityManager.createNamedQuery("UsuarioUnidadPolicial.findByIdUnidadIdentificacion")
              .setParameter("idUnidad", idUnidad)
              .setParameter("identificacion", identificacion)
              .getResultList();

      if (results != null && !results.isEmpty()) {
        return (UsuarioUnidadPolicial) results.get(0);
      }

      return null;
    } catch (Exception ex) {
      throw new JpaDinaeException(ex.getMessage());
    }
  }

}
