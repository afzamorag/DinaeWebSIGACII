package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.MaquinaDTO;
import co.gov.policia.dinae.dto.OpcionMenuDTO;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.OpcionMenu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IUsuarioLocal {

  /**
   *
   * @param identificacion
   * @param usuarioLogeado
   * @return
   * @throws JpaDinaeException
   */
  PerfilUsuarioDTO getPerfilUsuario(String identificacion, String usuarioLogeado) throws JpaDinaeException;

  /**
   *
   * @param idRol
   * @return
   * @throws JpaDinaeException
   */
  List<OpcionMenuDTO> getListaOpcionMenuUsuarioPorRol(Long idRol) throws JpaDinaeException;

  /**
   *
   * @param listaIdRolUsuario
   * @param estaAutenticado
   * @return
   * @throws JpaDinaeException
   */
  List<OpcionMenuDTO> getListaOpcionMenuUsuarioPrivado_Y_O_Publico(List<Long> listaIdRolUsuario, boolean estaAutenticado) throws JpaDinaeException;

  /**
   *
   * @param listaIdRolUsuario
   * @param idMenuPadre
   * @param estaAutenticado
   * @return
   * @throws JpaDinaeException
   */
  List<OpcionMenuDTO> getListaOpcionSubMenuUsuarioPrivado_Y_O_Publico(List<Long> listaIdRolUsuario, Long idMenuPadre, boolean estaAutenticado) throws JpaDinaeException;

}
