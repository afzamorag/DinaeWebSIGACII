package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import co.gov.policia.dinae.modelo.UsuarioUnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Local
public interface IUsuarioUnidadPolicialLocal {

  /**
   *
   * @param idUnidadPolicial
   * @param identificacion
   * @throws JpaDinaeException
   */
  void actualizarUnidadPolicialUsuario(Long idUnidadPolicial, String identificacion) throws JpaDinaeException;

  /**
   *
   * @param tipoUnidades
   * @param role
   * @return
   * @throws JpaDinaeException
   */
  List<VistaFuncionario> getUsuarioByTipoUnidadByRole(List<TipoUnidadPeriodo> tipoUnidades, Long role) throws JpaDinaeException;

  /**
   *
   * @param idUnidadPolicial
   * @param role
   * @return
   * @throws JpaDinaeException
   */
  List<VistaFuncionario> getUsuarioByUnidadByRole(Long idUnidadPolicial, Long role) throws JpaDinaeException;

  /**
   *
   * @param listaIdTipoUnidad
   * @param idRole
   * @return
   * @throws JpaDinaeException
   */
  List<String> getCorreoFuncionarioPorListaTipoUnidadYRole(List<Long> listaIdTipoUnidad, Long idRole) throws JpaDinaeException;

  /**
   *
   * @param listaIdRoles
   * @return
   * @throws JpaDinaeException
   */
  List<String> getCorreoFuncionarioPorListaRoles(List<Long> listaIdRoles) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  UnidadPolicialDTO findAllPorIdentificacion(String identificacion) throws JpaDinaeException;

  /**
   *
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  List<UnidadPolicialDTO> getAllUnidadPolicialDTOPorIdentificacion(String identificacion) throws JpaDinaeException;

  /**
   *
   * @param usuarioUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  UsuarioUnidadPolicial guardarUsuarioUnidadPolicial(UsuarioUnidadPolicial usuarioUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idUnidad
   * @param identificacion
   * @return
   * @throws JpaDinaeException
   */
  UsuarioUnidadPolicial findByIdUnidadIdentificacion(Long idUnidad, String identificacion) throws JpaDinaeException;

}
