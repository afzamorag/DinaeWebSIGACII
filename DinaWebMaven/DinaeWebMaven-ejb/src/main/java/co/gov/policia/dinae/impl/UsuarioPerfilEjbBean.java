package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.OpcionMenuDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUsuarioLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioUnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Stateless
public class UsuarioPerfilEjbBean implements IUsuarioLocal, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  /**
   *
   * @param identificacion
   * @param usuarioLogeado
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public PerfilUsuarioDTO getPerfilUsuario(String identificacion, String usuarioLogeado) throws JpaDinaeException {

    try {

      //DATOS PERSONALES DEL USUARIO LOGUEADO
      VistaFuncionario vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacion);

      if (vistaFuncionario.getCodigoUnidadLaboral() == null || vistaFuncionario.getCodigoUnidadLaboral().trim().length() == 0) {

        throw new RuntimeException("No se encontró Unidad Policial asociada el usuario.");

      }

      //SE CONSULTAN LOS ROLES DEL USUARIO LOGUEADO
      List<RolUsuarioDTO> listaRolUsuarioDTO = entityManager.createNamedQuery("UsuarioRol.findAllPorIdentificacion")
              .setParameter("identificacion", identificacion)
              .getResultList();

      UnidadPolicialDTO unidadPolicialDTO = (UnidadPolicialDTO) entityManager.createQuery("SELECT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO(u.idUnidadPolicial, u.nombre, u.siglaFisica, u.siglaPapa, u.tipoUnidad.idTipoUnidad, u.tipoUnidad.nombre) FROM UnidadPolicial u WHERE u.idUnidadPolicial = :idUnidadPolicial")
              .setParameter("idUnidadPolicial", Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral().trim()))
              .getSingleResult();

      //ACTUALIZAMOS LA UNIDAD POLICIAL DEL USUARIO EN LA USUARIO_UNIDAD_POLICIAL
      UsuarioUnidadPolicial usuarioUnidadPolicial;
      try {

        usuarioUnidadPolicial = (UsuarioUnidadPolicial) entityManager.createNamedQuery("UsuarioUnidadPolicial.findUsusarioUnidadPolicialPorIdentificacion")
                .setParameter("identificacion", identificacion)
                .getSingleResult();

      } catch (NoResultException nre) {

        usuarioUnidadPolicial = new UsuarioUnidadPolicial();
        usuarioUnidadPolicial.setIdentificadorUsuario(identificacion);
        usuarioUnidadPolicial.setUnidadPolicial(new UnidadPolicial(unidadPolicialDTO.getIdUnidadPolicial()));

      }
      //VERIFICAMOS SI NO EXISTE REG.
      if (usuarioUnidadPolicial.getIdUsuarioUnidadPolicial() == null) {

        entityManager.persist(usuarioUnidadPolicial);

      } //SI LA UNIDAD ACTUAL ES DIFERENTE A LA ANTERIOR, ENTONCES ACTUALIZAMOS LA NUNEVA UNIDAD POL DEL USUARIO
      else if (usuarioUnidadPolicial.getIdUsuarioUnidadPolicial() != null
              && !usuarioUnidadPolicial.getUnidadPolicial().getIdUnidadPolicial().equals(unidadPolicialDTO.getIdUnidadPolicial())) {

        usuarioUnidadPolicial.setUnidadPolicial(new UnidadPolicial(unidadPolicialDTO.getIdUnidadPolicial()));
        entityManager.merge(usuarioUnidadPolicial);

      }

      //SE  CRE OBJETO A RETORNAR
      PerfilUsuarioDTO perfilUsuarioDTO = new PerfilUsuarioDTO(listaRolUsuarioDTO,
              unidadPolicialDTO,
              vistaFuncionario.getIdentificacion(),
              vistaFuncionario.getNombreCompleto(),
              vistaFuncionario.getCorreo(),
              vistaFuncionario.getGrado(),
              vistaFuncionario.getCargo(),
              vistaFuncionario.getNombres(),
              vistaFuncionario.getApellidos(),
              vistaFuncionario.getCodigoCargo(),
              vistaFuncionario.getDireccionEmpleado(),
              vistaFuncionario.getDepartamentoReside(),
              vistaFuncionario.getCodigoDepartamentoReside(),
              vistaFuncionario.getCiudadReside(),
              vistaFuncionario.getCodigoCiudadReside(),
              vistaFuncionario.getSiglaLaborando(),
              vistaFuncionario.getCodigoUnidadLaboral(),
              usuarioLogeado,
              null);

      return perfilUsuarioDTO;

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param listaIdRolUsuario
   * @param estaAutenticado
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<OpcionMenuDTO> getListaOpcionMenuUsuarioPrivado_Y_O_Publico(List<Long> listaIdRolUsuario, boolean estaAutenticado) throws JpaDinaeException {

    try {

      if (listaIdRolUsuario == null || listaIdRolUsuario.isEmpty()) {

        //CONSULTAMOS LOS MENUS PUBLICOS
        List<OpcionMenuDTO> listaOpcionesPublicasPrivadaYlogueado = entityManager.createNamedQuery("OpcionMenuDTO.findAllPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_PUBLICO)
                .getResultList();

        if (estaAutenticado) {

          listaOpcionesPublicasPrivadaYlogueado.addAll(
                  entityManager.createNamedQuery("OpcionMenuDTO.findAllPorRolPublico")
                  .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_LOGUEADO)
                  .getResultList());
        }

        return listaOpcionesPublicasPrivadaYlogueado;

      } else {

        //CONSULTAMOS LOS MENUS PUBLICOS
        List<OpcionMenuDTO> listaOpcionesPublicasPrivadaYlogueado = entityManager.createNamedQuery("OpcionMenuDTO.findAllPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_PUBLICO)
                .getResultList();

        listaOpcionesPublicasPrivadaYlogueado.addAll(
                entityManager.createNamedQuery("OpcionMenuDTO.findAllPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_LOGUEADO)
                .getResultList());

        //AHORA CONSULTAMOS LOS MENUS PRIVADOS A LOS QUE TIENE ACCESO
        String sql = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.OpcionMenuDTO( r.opcionMenu.idOpcionMenu, r.opcionMenu.nombre, r.opcionMenu.accion, r.opcionMenu.orden, r.opcionMenu.titulo ) FROM RolOpcionMenu r "
                + " WHERE r.opcionMenu.accion IS NULL AND r.opcionMenu.opcionMenu IS NULL "
                + " AND ( r.opcionMenu.tipoAcceso = :tipoAccesopr AND r.rol.idRol IN " + UtilJPA.generateCollection(listaIdRolUsuario, false) + " ) "
                + " ORDER BY r.opcionMenu.orden ASC ";

        listaOpcionesPublicasPrivadaYlogueado.addAll(
                entityManager.createQuery(sql)
                .setParameter("tipoAccesopr", IConstantes.ESTADO_MENU_PRIVADO)
                .getResultList());

        return listaOpcionesPublicasPrivadaYlogueado;
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param listaIdRolUsuario
   * @param idMenuPadre
   * @param estaAutenticado
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<OpcionMenuDTO> getListaOpcionSubMenuUsuarioPrivado_Y_O_Publico(List<Long> listaIdRolUsuario, Long idMenuPadre, boolean estaAutenticado) throws JpaDinaeException {

    try {

      if (listaIdRolUsuario == null || listaIdRolUsuario.isEmpty()) {

        //CONSULTAMOS LOS SUB MENUS PUBLICOS
        List<OpcionMenuDTO> listaOpcionesSumMenuPublicas = entityManager.createNamedQuery("OpcionMenuDTO.findAllSubMenuPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_PUBLICO)
                .setParameter("idMenuPadre", idMenuPadre)
                .getResultList();

        if (estaAutenticado) {

          listaOpcionesSumMenuPublicas.addAll(
                  entityManager.createNamedQuery("OpcionMenuDTO.findAllSubMenuPorRolPublico")
                  .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_LOGUEADO)
                  .setParameter("idMenuPadre", idMenuPadre)
                  .getResultList());
        }
        return listaOpcionesSumMenuPublicas;

      } else {

        //CONSULTAMOS LOS SUB MENUS PUBLICOS
        List<OpcionMenuDTO> listaOpcionesSumMenuPublicas = entityManager.createNamedQuery("OpcionMenuDTO.findAllSubMenuPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_PUBLICO)
                .setParameter("idMenuPadre", idMenuPadre)
                .getResultList();

        listaOpcionesSumMenuPublicas.addAll(
                entityManager.createNamedQuery("OpcionMenuDTO.findAllSubMenuPorRolPublico")
                .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_LOGUEADO)
                .setParameter("idMenuPadre", idMenuPadre)
                .getResultList());
        //AHORA CONSULTAMOS LOS MENUS PRIVADOS A LOS QUE TIENE ACCESO
        String sql = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.OpcionMenuDTO( r.opcionMenu.idOpcionMenu, r.opcionMenu.nombre, r.opcionMenu.accion, r.opcionMenu.orden, r.opcionMenu.titulo ) FROM RolOpcionMenu r "
                + " WHERE r.opcionMenu.opcionMenu.idOpcionMenu = :idMenuPadre "
                //+ " AND r.opcionMenu.accion IS NOT NULL "
                + " AND r.opcionMenu.tipoAcceso = :tipoAccesopr "
                + " AND r.rol.idRol IN " + UtilJPA.generateCollection(listaIdRolUsuario, false) + " ORDER BY r.opcionMenu.orden ASC ";

        listaOpcionesSumMenuPublicas.addAll(
                entityManager.createQuery(sql)
                .setParameter("tipoAccesopr", IConstantes.ESTADO_MENU_PRIVADO)
                .setParameter("idMenuPadre", idMenuPadre)
                .getResultList());

        return listaOpcionesSumMenuPublicas;
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

  /**
   *
   * @param idRol
   * @return
   * @throws JpaDinaeException
   */
  @Override
  public List<OpcionMenuDTO> getListaOpcionMenuUsuarioPorRol(Long idRol) throws JpaDinaeException {

    try {

      entityManager.createNamedQuery("RolOpcionMenu.findAllPorRolPrivado")
              .setParameter("idRol", idRol)
              .setParameter("tipoAcceso", IConstantes.ESTADO_MENU_PRIVADO)
              .getResultList();
      return null;
    } catch (Exception e) {
      throw new JpaDinaeException(e);
    }

  }

}
