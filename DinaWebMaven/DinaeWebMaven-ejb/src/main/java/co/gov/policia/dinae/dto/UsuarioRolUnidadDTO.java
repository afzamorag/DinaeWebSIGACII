package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class UsuarioRolUnidadDTO implements IDataModel, Serializable, Comparable<UsuarioRolUnidadDTO> {

  private Long idReg;

  private Long idUnidadPolicial;
  private String nombreUnidad;
  private String codigoUnidadPolicial;
  private Long idRol;
  private String nombreRol;
  private String identificacionUsuario;
  private String gradoUsuario;
  private String nombreCompletoUsuario;
  private String rolRequiereUnidadPolicial;
  private String cargoUsuario;
  private String correoUsuario;
  private String rolesTexto;
  private Long[] rolesId;
  private String estadoUsuarioRol;

  public UsuarioRolUnidadDTO() {
  }

  /**
   *
   * @param idReg
   * @param idUnidadPolicial
   * @param nombreUnidad
   * @param idRol
   * @param nombreRol
   * @param identificacionUsuario
   * @param gradoUsuario
   * @param nombreCompletoUsuario
   * @param rolRequiereUnidadPolicial
   * @param cargoUsuario
   * @param correoUsuario
   * @param rolesTexto
   * @param rolesId
   */
  public UsuarioRolUnidadDTO(Long idReg, Long idUnidadPolicial, String nombreUnidad, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial, String cargoUsuario, String correoUsuario, String rolesTexto, Long[] rolesId) {
    this.idReg = idReg;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidad = nombreUnidad;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
    this.identificacionUsuario = identificacionUsuario;
    this.gradoUsuario = gradoUsuario;
    this.nombreCompletoUsuario = nombreCompletoUsuario;
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
    this.cargoUsuario = cargoUsuario;
    this.correoUsuario = correoUsuario;
    this.rolesTexto = rolesTexto;
    this.rolesId = rolesId;
  }

  /**
   *
   * @param idReg
   * @param idUnidadPolicial
   * @param nombreUnidad
   * @param idRol
   * @param nombreRol
   * @param identificacionUsuario
   * @param gradoUsuario
   * @param nombreCompletoUsuario
   * @param rolRequiereUnidadPolicial
   * @param cargoUsuario
   * @param correoUsuario
   * @param rolesTexto
   * @param rolesId
   * @param codigoUnidadPolicial
   */
  public UsuarioRolUnidadDTO(Long idReg, Long idUnidadPolicial, String nombreUnidad, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial, String cargoUsuario, String correoUsuario, String rolesTexto, Long[] rolesId, String codigoUnidadPolicial) {
    this.idReg = idReg;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidad = nombreUnidad;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
    this.identificacionUsuario = identificacionUsuario;
    this.gradoUsuario = gradoUsuario;
    this.nombreCompletoUsuario = nombreCompletoUsuario;
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
    this.cargoUsuario = cargoUsuario;
    this.correoUsuario = correoUsuario;
    this.rolesTexto = rolesTexto;
    this.rolesId = rolesId;
    this.codigoUnidadPolicial = codigoUnidadPolicial;
  }

  /**
   *
   * @param idReg
   * @param idUnidadPolicial
   * @param nombreUnidad
   * @param idRol
   * @param nombreRol
   * @param identificacionUsuario
   * @param gradoUsuario
   * @param nombreCompletoUsuario
   * @param rolRequiereUnidadPolicial
   * @param cargoUsuario
   * @param correoUsuario
   * @param estadoUsuarioRol
   */
  public UsuarioRolUnidadDTO(Long idReg, Long idUnidadPolicial, String nombreUnidad, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial, String cargoUsuario, String correoUsuario, String estadoUsuarioRol) {
    this.idReg = idReg;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidad = nombreUnidad;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
    this.identificacionUsuario = identificacionUsuario;
    this.gradoUsuario = gradoUsuario;
    this.nombreCompletoUsuario = nombreCompletoUsuario;
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
    this.cargoUsuario = cargoUsuario;
    this.correoUsuario = correoUsuario;
    this.estadoUsuarioRol = estadoUsuarioRol;

  }

  /**
   *
   * @param idReg
   * @param idUnidadPolicial
   * @param nombreUnidad
   * @param idRol
   * @param nombreRol
   * @param identificacionUsuario
   * @param gradoUsuario
   * @param nombreCompletoUsuario
   * @param rolRequiereUnidadPolicial
   * @param cargoUsuario
   * @param correoUsuario
   * @param estadoUsuarioRol
   * @param codigoUnidadPolicial
   */
  public UsuarioRolUnidadDTO(Long idReg, Long idUnidadPolicial, String nombreUnidad, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial, String cargoUsuario, String correoUsuario, String estadoUsuarioRol, String codigoUnidadPolicial) {
    this.idReg = idReg;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidad = nombreUnidad;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
    this.identificacionUsuario = identificacionUsuario;
    this.gradoUsuario = gradoUsuario;
    this.nombreCompletoUsuario = nombreCompletoUsuario;
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
    this.cargoUsuario = cargoUsuario;
    this.correoUsuario = correoUsuario;
    this.estadoUsuarioRol = estadoUsuarioRol;
    this.codigoUnidadPolicial = codigoUnidadPolicial;
  }

  public Long getIdReg() {
    return idReg;
  }

  public void setIdReg(Long idReg) {
    this.idReg = idReg;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombreUnidad() {
    return nombreUnidad;
  }

  public void setNombreUnidad(String nombreUnidad) {
    this.nombreUnidad = nombreUnidad;
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
  }

  public String getNombreRol() {
    return nombreRol;
  }

  public void setNombreRol(String nombreRol) {
    this.nombreRol = nombreRol;
  }

  public String getIdentificacionUsuario() {
    return identificacionUsuario;
  }

  public void setIdentificacionUsuario(String identificacionUsuario) {
    this.identificacionUsuario = identificacionUsuario;
  }

  public String getGradoUsuario() {
    return gradoUsuario;
  }

  public void setGradoUsuario(String gradoUsuario) {
    this.gradoUsuario = gradoUsuario;
  }

  public String getNombreCompletoUsuario() {
    return nombreCompletoUsuario;
  }

  public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
    this.nombreCompletoUsuario = nombreCompletoUsuario;
  }

  public String getRolRequiereUnidadPolicial() {
    return rolRequiereUnidadPolicial;
  }

  public void setRolRequiereUnidadPolicial(String rolRequiereUnidadPolicial) {
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
  }

  public String getCargoUsuario() {
    return cargoUsuario;
  }

  public void setCargoUsuario(String cargoUsuario) {
    this.cargoUsuario = cargoUsuario;
  }

  public String getCorreoUsuario() {
    return correoUsuario;
  }

  public void setCorreoUsuario(String correoUsuario) {
    this.correoUsuario = correoUsuario;
  }

  public String getRolesTexto() {
    return rolesTexto;
  }

  public void setRolesTexto(String rolesTexto) {
    this.rolesTexto = rolesTexto;
  }

  public Long[] getRolesId() {
    return rolesId;
  }

  public void setRolesId(Long[] rolesId) {
    this.rolesId = rolesId;
  }

  public String getEstadoUsuarioRol() {
    return estadoUsuarioRol;
  }

  public void setEstadoUsuarioRol(String estadoUsuarioRol) {
    this.estadoUsuarioRol = estadoUsuarioRol;
  }

  @Override
  public String getLlaveModel() {
    if (idReg == null) {
      return null;
    }

    return idReg.toString();
  }

  public String getCodigoUnidadPolicial() {
    return codigoUnidadPolicial;
  }

  public void setCodigoUnidadPolicial(String codigoUnidadPolicial) {
    this.codigoUnidadPolicial = codigoUnidadPolicial;
  }

  @Override
  public int compareTo(UsuarioRolUnidadDTO o) {

    if (nombreRol == null) {
      return -1;
    }
    return o.nombreRol.compareTo(o.getNombreRol());

  }

}
