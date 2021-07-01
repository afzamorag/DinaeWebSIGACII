package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SieduEntidadDTO implements Serializable {

  private int idEntidades;
  private String nombre;

  public SieduEntidadDTO(int idEntidades, String nombre) {
    this.idEntidades = idEntidades;
    this.nombre = nombre;
  }

  public int getIdEntidades() {
    return idEntidades;
  }

  public void setIdEntidades(int idEntidades) {
    this.idEntidades = idEntidades;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
