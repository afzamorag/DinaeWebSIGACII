package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cguzman
 */
public class ImplentacionProyectoCompromisosDTO implements Serializable {

  private Long idImplementacionProy;

  private Long idUnidadPolicial;

  private Date fechaRegistro;

  private String usuario;

  private Long idUsuarioRol;

  private Long idProyecto;

  private Long idEstadoImplementacion;

  private String maquina;

  private String codigoProyecto;

  private String tituloProyecto;

  private Date fechaActualizacion;

  private Short anio;

  private List<CompromisoImplementacionDTO> compromisosImplProyecto;

  /**
   *
   * @param idImplementacionProy
   * @param idUnidadPolicial
   * @param fechaRegistro
   * @param usuario
   * @param idUsuarioRol
   * @param idProyecto
   * @param idEstadoImplementacion
   * @param maquina
   * @param codigoProyecto
   * @param tituloProyecto
   * @param fechaActualizacion
   * @param anio
   */
  public ImplentacionProyectoCompromisosDTO(Long idImplementacionProy, Long idUnidadPolicial, Date fechaRegistro, String usuario, Long idUsuarioRol, Long idProyecto, Long idEstadoImplementacion, String maquina, String codigoProyecto, String tituloProyecto, Date fechaActualizacion, Short anio) {
    this.idImplementacionProy = idImplementacionProy;
    this.idUnidadPolicial = idUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.usuario = usuario;
    this.idUsuarioRol = idUsuarioRol;
    this.idProyecto = idProyecto;
    this.idEstadoImplementacion = idEstadoImplementacion;
    this.maquina = maquina;
    this.codigoProyecto = codigoProyecto;
    this.tituloProyecto = tituloProyecto;
    this.fechaActualizacion = fechaActualizacion;
    this.anio = anio;
  }

  public ImplentacionProyectoCompromisosDTO() {
  }

  public List<CompromisoImplementacionDTO> getCompromisosImplProyecto() {
    return compromisosImplProyecto;
  }

  public void setCompromisosImplProyecto(List<CompromisoImplementacionDTO> compromisosImplProyecto) {
    this.compromisosImplProyecto = compromisosImplProyecto;
  }

  public Long getIdImplementacionProy() {
    return idImplementacionProy;
  }

  public void setIdImplementacionProy(Long idImplementacionProy) {
    this.idImplementacionProy = idImplementacionProy;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Long getIdEstadoImplementacion() {
    return idEstadoImplementacion;
  }

  public void setIdEstadoImplementacion(Long idEstadoImplementacion) {
    this.idEstadoImplementacion = idEstadoImplementacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public String getTituloProyecto() {
    return tituloProyecto;
  }

  public void setTituloProyecto(String tituloProyecto) {
    this.tituloProyecto = tituloProyecto;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public Short getAnio() {
    return anio;
  }

  public void setAnio(Short anio) {
    this.anio = anio;
  }

}
