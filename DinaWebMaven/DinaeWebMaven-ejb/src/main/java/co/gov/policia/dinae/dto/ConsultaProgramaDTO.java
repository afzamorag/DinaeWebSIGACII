/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author OFITE
 */
public class ConsultaProgramaDTO implements Serializable {

  int idPae;
  String descripcion;

  public ConsultaProgramaDTO() {
  }

  public ConsultaProgramaDTO(int idPae, String descripcion) {

    this.idPae = idPae;
    this.descripcion = descripcion;
  }

  public long getIdPae() {
    return idPae;
  }

  public void setIdPae(int idPae) {
    this.idPae = idPae;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String Descripcion) {
    this.descripcion = Descripcion;
  }

}
