package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class PerfilUsuarioDTO implements Serializable {

  private final List<RolUsuarioDTO> listaRolUsuarioDTO;
  private final UnidadPolicialDTO unidadPolicial;
  private MaquinaDTO maquinaDTO;
  private final String identificacion;
  private final String nombreCompleto;
  private final String correo;
  private final String grado;
  private final String cargo;
  private final String nombres;
  private final String apellidos;
  private final String codigoCargo;
  private final String direccionEmpleado;
  private final String departamentoReside;
  private final String codigoDepartamentoReside;
  private final String ciudadReside;
  private final String codigoCiudadReside;
  private final String siglaLaborando;
  private final String codigoUnidadLaboral;
  private final String usuarioLogueado;
  private Set<String> listaPaginasAccesos;
  private String rolDepenencia;
  private Long unidadDependencia;

  /**
   *
   * @param rolUsuarioDTO
   * @param unidadPolicial
   * @param identificacion
   * @param nombreCompleto
   * @param correo
   * @param grado
   * @param cargo
   * @param nombres
   * @param apellidos
   * @param codigoCargo
   * @param direccionEmpleado
   * @param departamentoReside
   * @param codigoDepartamentoReside
   * @param ciudadReside
   * @param codigoCiudadReside
   * @param siglaLaborando
   * @param codigoUnidadLaboral
   * @param usuarioLogueado
   * @param maquinaDTO
   */
  public PerfilUsuarioDTO(List<RolUsuarioDTO> rolUsuarioDTO, UnidadPolicialDTO unidadPolicial,
          String identificacion, String nombreCompleto, String correo, String grado,
          String cargo, String nombres, String apellidos, String codigoCargo,
          String direccionEmpleado, String departamentoReside, String codigoDepartamentoReside,
          String ciudadReside, String codigoCiudadReside, String siglaLaborando, String codigoUnidadLaboral,
          String usuarioLogueado,
          MaquinaDTO maquinaDTO) {

    this.listaRolUsuarioDTO = rolUsuarioDTO;
    this.unidadPolicial = unidadPolicial;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.grado = grado;
    this.cargo = cargo;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.codigoCargo = codigoCargo;
    this.direccionEmpleado = direccionEmpleado;
    this.departamentoReside = departamentoReside;
    this.codigoDepartamentoReside = codigoDepartamentoReside;
    this.ciudadReside = ciudadReside;
    this.codigoCiudadReside = codigoCiudadReside;
    this.siglaLaborando = siglaLaborando;
    this.codigoUnidadLaboral = codigoUnidadLaboral;
    this.usuarioLogueado = usuarioLogueado;
    this.maquinaDTO = maquinaDTO;
  }

  public PerfilUsuarioDTO(String grado, String nombreCompleto, String correo, UnidadPolicialDTO unidadPolicial) {
    this.listaRolUsuarioDTO = new ArrayList<RolUsuarioDTO>();
    this.unidadPolicial = unidadPolicial;
    this.identificacion = null;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.grado = grado;
    this.cargo = null;
    this.nombres = null;
    this.apellidos = null;
    this.codigoCargo = null;
    this.direccionEmpleado = null;
    this.departamentoReside = null;
    this.codigoDepartamentoReside = null;
    this.ciudadReside = null;
    this.codigoCiudadReside = null;
    this.siglaLaborando = null;
    this.codigoUnidadLaboral = null;
    this.usuarioLogueado = null;
  }

  /**
   * Metodo que Valida si un ROL esta registrado a un usuario
   *
   * @param idRolValidar
   * @return
   */
  public boolean validarRol(Long idRolValidar) {

    if (listaRolUsuarioDTO == null) {
      return false;
    }
    for (RolUsuarioDTO rolUsuarioDTO : listaRolUsuarioDTO) {

      if (rolUsuarioDTO.getIdRol().equals(idRolValidar)) {
        return true;
      }
    }
    return false;
  }

  public UnidadPolicialDTO getUnidadPolicial() {
    return unidadPolicial;
  }

  /**
   *
   * @param idRolValidar
   * @return
   */
  public RolUsuarioDTO getRolUsuarioPorIdRolDTO(Long idRolValidar) {

    if (listaRolUsuarioDTO == null) {
      return null;
    }
    for (RolUsuarioDTO rolUsuarioDTO : listaRolUsuarioDTO) {

      if (rolUsuarioDTO.getIdRol().equals(idRolValidar)) {
        return rolUsuarioDTO;
      }
    }
    return null;

  }

  public List<RolUsuarioDTO> getListaRolUsuarioDTO() {
    return listaRolUsuarioDTO;
  }

  /**
   *
   * @return
   */
  public List<Long> getListaIdRolUsuario() {

    if (listaRolUsuarioDTO == null) {
      return new ArrayList<Long>();
    }

    List<Long> listaIdRol = new ArrayList<Long>();
    for (RolUsuarioDTO rolUsuarioDTO : listaRolUsuarioDTO) {

      listaIdRol.add(rolUsuarioDTO.getIdRol());
    }
    return listaIdRol;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public String getCorreo() {
    return correo;
  }

  public String getGrado() {
    return grado;
  }

  public String getCargo() {
    return cargo;
  }

  public String getNombres() {
    return nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public String getCodigoCargo() {
    return codigoCargo;
  }

  public String getDireccionEmpleado() {
    return direccionEmpleado;
  }

  public String getDepartamentoReside() {
    return departamentoReside;
  }

  public String getCodigoDepartamentoReside() {
    return codigoDepartamentoReside;
  }

  public String getCiudadReside() {
    return ciudadReside;
  }

  public String getCodigoCiudadReside() {
    return codigoCiudadReside;
  }

  public String getSiglaLaborando() {
    return siglaLaborando;
  }

  public String getCodigoUnidadLaboral() {
    return codigoUnidadLaboral;
  }

  public String getUsuarioLogueado() {
    return usuarioLogueado;
  }

  public MaquinaDTO getMaquinaDTO() {
    return maquinaDTO;
  }

  public void setMaquinaDTO(MaquinaDTO maquinaDTO) {
    this.maquinaDTO = maquinaDTO;
  }

  public Set<String> getListaPaginasAccesos() {

    if (listaPaginasAccesos == null) {
      listaPaginasAccesos = new HashSet<String>();
    }

    return listaPaginasAccesos;
  }

  public void setListaPaginasAccesos(Set<String> listaPaginasAccesos) {
    this.listaPaginasAccesos = listaPaginasAccesos;
  }

  public String getGradoYNombreCompleto() {
    if (grado == null) {
      return nombreCompleto;
    }
    return grado + " - " + nombreCompleto;
  }

  public String getRolDepenencia() {
    return rolDepenencia;
  }

  public void setRolDepenencia(String rolDepenencia) {
    this.rolDepenencia = rolDepenencia;
  }

  public Long getUnidadDependencia() {
    return unidadDependencia;
  }

  public void setUnidadDependencia(Long unidadDependencia) {
    this.unidadDependencia = unidadDependencia;
  }  
}
