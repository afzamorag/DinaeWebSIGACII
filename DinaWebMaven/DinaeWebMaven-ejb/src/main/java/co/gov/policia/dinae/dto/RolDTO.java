package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;

/**
 *
 * @author cguzman
 */
public class RolDTO implements Serializable, IDataModel {

  private Long idRol;
  private String nombre;
  private Character requiereUnidadPolicial;

  /**
   *
   * @param idRol
   * @param nombre
   * @param requiereUnidadPolicial
   */
  public RolDTO(Long idRol, String nombre, Character requiereUnidadPolicial) {
    this.idRol = idRol;
    this.nombre = nombre;
    this.requiereUnidadPolicial = requiereUnidadPolicial;
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Character getRequiereUnidadPolicial() {
    return requiereUnidadPolicial;
  }

  public void setRequiereUnidadPolicial(Character requiereUnidadPolicial) {
    this.requiereUnidadPolicial = requiereUnidadPolicial;
  }

  @Override
  public String getLlaveModel() {
    if (this.idRol == null) {
      return null;
    }

    return this.idRol.toString();
  }

}
