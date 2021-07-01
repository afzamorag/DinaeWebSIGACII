package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.UsuarioRol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IUsuarioRolLocal {

  /**
   *
   * @param idRol
   * @param idUnidadPolicial filtro utilizado para consulta, si este es NULL no se tiene en cuenta en el filtro
   * @return
   * @throws JpaDinaeException
   */
  List<String> getIdentificacionesRolYUnidadPolical(Long idRol, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  List<RolUsuarioDTO> getAllRolUsuarioDTOByIdentificacion(String identificacion) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @param idRolesFiltro
   * @return
   * @throws JpaDinaeException
   */
  List<RolUsuarioDTO> getAllRolUsuarioDTOByIdentificacionYroles(String identificacion, Long[] idRolesFiltro) throws JpaDinaeException;

  /**
   *
   * @param usuarioRol
   * @return
   * @throws JpaDinaeException
   */
  UsuarioRol guardarUsuarioRol(UsuarioRol usuarioRol) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @throws JpaDinaeException
   */
  void guardarUsuarioRolGrupoInvestigacion(String identificacion) throws JpaDinaeException;

  /**
   *
   * @param idRol
   * @param identificadorUsuario
   * @return
   * @throws JpaDinaeException
   */
  UsuarioRol findByUsuarioYRol(Long idRol, String identificadorUsuario) throws JpaDinaeException;

  /**
   *
   * @param idUsuarioRol
   * @throws JpaDinaeException
   */
  void deshabilitarUsuarioRol(Long idUsuarioRol) throws JpaDinaeException;

  /**
   *
   * @param identificadorUsuario
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRol> findByUsuarioYAdminRoles(String identificadorUsuario, Long... roles) throws JpaDinaeException;

}
