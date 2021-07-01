package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class RolUsuarioDTO implements Serializable {

  private final Long idUsuarioRol;
  private final Long idRol;
  private final String nombre;
  private final Character requiereUnidadPolicial;
  private String activo;

  /**
   *
   * @param idUsuarioRol
   * @param idRol
   * @param nombre
   * @param requiereUnidadPolicial
   */
  public RolUsuarioDTO(Long idUsuarioRol, Long idRol, String nombre, Character requiereUnidadPolicial, String activo) {
    this.idUsuarioRol = idUsuarioRol;
    this.idRol = idRol;
    this.nombre = nombre;
    this.requiereUnidadPolicial = requiereUnidadPolicial;
    this.activo = activo;
  }

  public Long getIdRol() {
    return idRol;
  }

  public String getNombre() {
    return nombre;
  }

  public Character getRequiereUnidadPolicial() {
    return requiereUnidadPolicial;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public String getActivo() {
    return activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }

}
