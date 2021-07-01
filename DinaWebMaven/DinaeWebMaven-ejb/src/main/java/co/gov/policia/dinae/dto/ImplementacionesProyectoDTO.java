package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ImplementacionesProyectoDTO implements Serializable {

  private UnidadPolicialDTO unidadPolicialDTO;
  private String codigoProyecto;
  private Date fechaRegistro;

  public ImplementacionesProyectoDTO() {
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param codigoProyecto
   * @param fechaRegistro
   */
  public ImplementacionesProyectoDTO(Long idUnidadPolicial, String nombre, String codigoProyecto, Date fechaRegistro) {

    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre);
    this.codigoProyecto = codigoProyecto;
    this.fechaRegistro = fechaRegistro;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaPadreUnidad
   * @param codigoProyecto
   * @param fechaRegistro
   */
  public ImplementacionesProyectoDTO(Long idUnidadPolicial, String nombre, String siglaPadreUnidad, String codigoProyecto, Date fechaRegistro) {

    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre, siglaPadreUnidad);
    this.codigoProyecto = codigoProyecto;
    this.fechaRegistro = fechaRegistro;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UnidadPolicialDTO getUnidadPolicialDTO() {
    return unidadPolicialDTO;
  }

  public void setUnidadPolicialDTO(UnidadPolicialDTO unidadPolicialDTO) {
    this.unidadPolicialDTO = unidadPolicialDTO;
  }

}
