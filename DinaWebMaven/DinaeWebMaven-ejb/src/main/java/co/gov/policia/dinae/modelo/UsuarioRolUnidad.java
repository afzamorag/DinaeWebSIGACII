package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "USUARIO_ROL_UNIDAD")
@NamedQueries({
  @NamedQuery(name = "UsuarioRolUnidad.findAll", query = "SELECT u FROM UsuarioRolUnidad u"),
  @NamedQuery(name = "UsuarioRolUnidad.findAllUnidadRoles", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol, u.siglaUnidadPolicial) FROM UsuarioRolUnidad u WHERE u.idUnidadPolicial = :idUnidadPolicial AND u.idRol IN :roles AND u.estadoUsuarioRol = 'S' ORDER BY u.idRol, u.identificacionUsuario, u.nombreCompletoUsuario"),
  @NamedQuery(name = "UsuarioRolUnidad.findAllUnidadNoRolAdmin", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol, u.siglaUnidadPolicial) FROM UsuarioRolUnidad u WHERE u.idUnidadPolicial = :idUnidadPolicial AND u.idRol NOT IN :roles AND u.estadoUsuarioRol = 'S' AND u.rolRequiereUnidadPolicial IN ('Y','N') ORDER BY u.identificacionUsuario, u.nombreCompletoUsuario"),
  @NamedQuery(name = "UsuarioRolUnidad.findAllIdRol", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol, u.siglaUnidadPolicial) FROM UsuarioRolUnidad u WHERE u.idRol IN :roles AND u.estadoUsuarioRol = 'S' ORDER BY u.nombreCompletoUsuario ASC "),
  @NamedQuery(name = "UsuarioRolUnidad.findAllIdentificacion", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol, u.siglaUnidadPolicial) FROM UsuarioRolUnidad u WHERE u.identificacionUsuario = :identificacionUsuario AND u.estadoUsuarioRol = 'S' ORDER BY u.nombreRol ASC"),
  @NamedQuery(name = "UsuarioRolUnidad.findAllUnidad", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol) FROM UsuarioRolUnidad u WHERE u.idUnidadPolicial = :idUnidadPolicial AND u.estadoUsuarioRol = 'S' ORDER BY u.idUnidadPolicial"),
  @NamedQuery(name = "UsuarioRolUnidad.findAllVicin", query = "SELECT NEW co.gov.policia.dinae.dto.UsuarioRolUnidadDTO(u.idReg, u.idUnidadPolicial, u.nombreUnidad, u.idRol, u.nombreRol, u.identificacionUsuario, u.gradoUsuario, u.nombreCompletoUsuario, u.rolRequiereUnidadPolicial, u.cargoUsuario, u.correoUsuario, u.estadoUsuarioRol, u.siglaUnidadPolicial) FROM UsuarioRolUnidad u WHERE u.idRol IN (7,9,10,11,12,13,14,15,16,18,19,31,2,3,23,20,21,22) AND u.estadoUsuarioRol = 'S' ORDER BY u.identificacionUsuario, u.nombreCompletoUsuario")
})
public class UsuarioRolUnidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_REG")
  private Long idReg;

  @Column(name = "ID_UNIDAD_POLICIAL")
  private Long idUnidadPolicial;

  @Column(name = "NOMBRE_UNIDAD")
  private String nombreUnidad;

  @Column(name = "ID_ROL")
  private Long idRol;

  @Column(name = "NOMBRE_ROL")
  private String nombreRol;

  @Column(name = "IDENTIFICACION_USUARIO")
  private String identificacionUsuario;

  @Column(name = "GRADO_USUARIO")
  private String gradoUsuario;

  @Column(name = "NOMBRE_COMPLETO_USUARIO")
  private String nombreCompletoUsuario;

  @Column(name = "REQUIERE_UNIDAD_POLICIAL")
  private String rolRequiereUnidadPolicial;

  @Column(name = "CARGO_USUARIO")
  private String cargoUsuario;

  @Column(name = "CORREO_USUARIO")
  private String correoUsuario;

  @Column(name = "ESTADO_USUARIO_ROL")
  private String estadoUsuarioRol;

  @Column(name = "SIGLA_FISICA_UNID_POL")
  private String siglaUnidadPolicial;

  public UsuarioRolUnidad() {
  }

  public UsuarioRolUnidad(Long idReg, Long idUnidadPolicial, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial) {
    this.idReg = idReg;
    this.idUnidadPolicial = idUnidadPolicial;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
    this.identificacionUsuario = identificacionUsuario;
    this.gradoUsuario = gradoUsuario;
    this.nombreCompletoUsuario = nombreCompletoUsuario;
    this.rolRequiereUnidadPolicial = rolRequiereUnidadPolicial;
  }

  public UsuarioRolUnidad(Long idReg, Long idUnidadPolicial, String nombreUnidad, Long idRol, String nombreRol, String identificacionUsuario, String gradoUsuario, String nombreCompletoUsuario, String rolRequiereUnidadPolicial, String cargoUsuario, String correoUsuario, String estadoUsuarioRol) {
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

  public String getNombreUnidad() {
    return nombreUnidad;
  }

  public void setNombreUnidad(String nombreUnidad) {
    this.nombreUnidad = nombreUnidad;
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

  public String getEstadoUsuarioRol() {
    return estadoUsuarioRol;
  }

  public void setEstadoUsuarioRol(String estadoUsuarioRol) {
    this.estadoUsuarioRol = estadoUsuarioRol;
  }

  public String getSiglaUnidadPolicial() {
    return siglaUnidadPolicial;
  }

  public void setSiglaUnidadPolicial(String siglaUnidadPolicial) {
    this.siglaUnidadPolicial = siglaUnidadPolicial;
  }
}
