package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.RolDTO;
import co.gov.policia.dinae.dto.UsuarioRolUnidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.UsuarioRolUnidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cguzman
 */
@Local
public interface IUsuarioRolUnidadLocal {

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<UsuarioRolUnidad> findAll() throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllUnidadRoles(boolean esOtro, Long idUnidadPolicial, Long... roles) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllUnidadNoRolAdmin(Long idUnidadPolicial, Long... roles) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllUnidad(Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllVicin() throws JpaDinaeException;

  /**
   *
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  List<RolDTO> findRolesByIds(Long... roles) throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<RolDTO> findRolesUnidad() throws JpaDinaeException;

  /**
   *
   * @return @throws JpaDinaeException
   */
  List<RolDTO> findAllRoles() throws JpaDinaeException;

  /**
   *
   * @param roles
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllPorRol(List<Long> roles) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  List<UsuarioRolUnidadDTO> findAllPorIdentificacion(String identificacion) throws JpaDinaeException;
}
