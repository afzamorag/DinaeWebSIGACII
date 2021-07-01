package co.gov.policia.dinae.dto;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class InvestigadorDTO {

  private Long id;
  private Long idTipoVinculacion;
  private String nombreTipoVinculacion;
  private String grado;
  private String identifica;
  private String nombresApellidos;
  private String correoElectronico;
  private String origen;

  public InvestigadorDTO() {
  }

  /**
   *
   * @param id
   * @param idTipoVinculacion
   * @param nombreTipoVinculacion
   * @param grado
   * @param identifica
   * @param nombresApellidos
   * @param correoElectronico
   * @param origen
   */
  public InvestigadorDTO(Long id, Long idTipoVinculacion, String nombreTipoVinculacion, String grado, String identifica, String nombresApellidos, String correoElectronico, String origen) {
    this.id = id;
    this.idTipoVinculacion = idTipoVinculacion;
    this.nombreTipoVinculacion = nombreTipoVinculacion;
    this.grado = grado;
    this.identifica = identifica;
    this.nombresApellidos = nombresApellidos;
    this.correoElectronico = correoElectronico;
    this.origen = origen;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdTipoVinculacion() {
    return idTipoVinculacion;
  }

  public void setIdTipoVinculacion(Long idTipoVinculacion) {
    this.idTipoVinculacion = idTipoVinculacion;
  }

  public String getNombreTipoVinculacion() {
    return nombreTipoVinculacion;
  }

  public void setNombreTipoVinculacion(String nombreTipoVinculacion) {
    this.nombreTipoVinculacion = nombreTipoVinculacion;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getNombresApellidos() {
    return nombresApellidos;
  }

  public void setNombresApellidos(String nombresApellidos) {
    this.nombresApellidos = nombresApellidos;
  }

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico) {
    this.correoElectronico = correoElectronico;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getIdentifica() {
    return identifica;
  }

  public void setIdentifica(String identifica) {
    this.identifica = identifica;
  }

}
