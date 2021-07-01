package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author RafaelGomez
 */
public class EvaluadoresProyectoGradoDTO implements Serializable {

  //de la entidad
  private Long idEvaluadorProy;
  private String identificacion;
  private Date fechaRegistro;
  private String grado;
  private String correo;
  private String cargo;
  private String telefono;

  //Transientes
  private String nombreCompleto;

  public EvaluadoresProyectoGradoDTO() {
  }

  public EvaluadoresProyectoGradoDTO(Long idEvaluadorProy, String identificacion, Date fechaRegistro, String grado, String correo, String cargo, String telefono) {
    this.idEvaluadorProy = idEvaluadorProy;
    this.identificacion = identificacion;
    this.fechaRegistro = fechaRegistro;
    this.grado = grado;
    this.correo = correo;
    this.cargo = cargo;
    this.telefono = telefono;
  }

  /**
   * @return the idEvaluadorProy
   */
  public Long getIdEvaluadorProy() {
    return idEvaluadorProy;
  }

  /**
   * @param idEvaluadorProy the idEvaluadorProy to set
   */
  public void setIdEvaluadorProy(Long idEvaluadorProy) {
    this.idEvaluadorProy = idEvaluadorProy;
  }

  /**
   * @return the identificacion
   */
  public String getIdentificacion() {
    return identificacion;
  }

  /**
   * @param identificacion the identificacion to set
   */
  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  /**
   * @return the fechaRegistro
   */
  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  /**
   * @param fechaRegistro the fechaRegistro to set
   */
  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  /**
   * @return the grado
   */
  public String getGrado() {
    return grado;
  }

  /**
   * @param grado the grado to set
   */
  public void setGrado(String grado) {
    this.grado = grado;
  }

  /**
   * @return the correo
   */
  public String getCorreo() {
    return correo;
  }

  /**
   * @param correo the correo to set
   */
  public void setCorreo(String correo) {
    this.correo = correo;
  }

  /**
   * @return the cargo
   */
  public String getCargo() {
    return cargo;
  }

  /**
   * @param cargo the cargo to set
   */
  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  /**
   * @return the telefono
   */
  public String getTelefono() {
    return telefono;
  }

  /**
   * @param telefono the telefono to set
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   * @return the nombreCompleto
   */
  public String getNombreCompleto() {
    return nombreCompleto;
  }

  /**
   * @param nombreCompleto the nombreCompleto to set
   */
  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

}
