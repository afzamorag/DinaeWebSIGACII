package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class EjecutorNecesidadDTO implements Serializable {

  private Long idEjecutorNecesidad;
  private Long idUnidadPolicial;
  private String nombreUnidadPolicial;
  private Long idRol;
  private String nombreRol;

  public EjecutorNecesidadDTO() {
  }

  /**
   *
   * @param idEjecutorNecesidad
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param idRol
   * @param nombreRol
   */
  public EjecutorNecesidadDTO(Long idEjecutorNecesidad, Long idUnidadPolicial, String nombreUnidadPolicial, Long idRol, String nombreRol) {
    this.idEjecutorNecesidad = idEjecutorNecesidad;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.idRol = idRol;
    this.nombreRol = nombreRol;
  }

  public Long getIdEjecutorNecesidad() {
    return idEjecutorNecesidad;
  }

  public void setIdEjecutorNecesidad(Long idEjecutorNecesidad) {
    this.idEjecutorNecesidad = idEjecutorNecesidad;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
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

}
