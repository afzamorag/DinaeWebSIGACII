package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author juan
 */
public class FuncionarioDTO implements Serializable {

  private final String identificacion;
  private final String nombreCompleto;
  private final String correo;
  private final String grado;
  private final Long idUnidadPolicial;

  public FuncionarioDTO(String identificacion, String nombreCompleto, String correo, String grado, Long idUnidadPolicial) {
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.grado = grado;
    this.idUnidadPolicial = idUnidadPolicial;
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

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

}
