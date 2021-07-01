package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author RafaelGomez
 */
public class DocumentacionProyectoDTO implements Serializable {

  private Long idDocumProyecto;
  private String descripcion;
  private String nombreArchivo;
  private String nombreArchivoFisico;
  private Date fechaPresentacion;
  private String comentarioDocumento;
  private String nombreTipoDocumento;
  private String maquina;
  private Long idProyecto;
  private Long idUsuarioRol;
  private boolean tieneComentarios;

  public DocumentacionProyectoDTO() {
  }

  public DocumentacionProyectoDTO(Long idDocumProyecto, String descripcion, String nombreArchivo, String nombreArchivoFisico, Date fechaPresentacion, String comentarioDocumento, String nombreTipoDocumento, String maquina, Long idProyecto, Long idUsuarioRol) {
    this.idDocumProyecto = idDocumProyecto;
    this.descripcion = descripcion;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.fechaPresentacion = fechaPresentacion;
    this.comentarioDocumento = comentarioDocumento;
    this.nombreTipoDocumento = nombreTipoDocumento;
    this.maquina = maquina;
    this.idProyecto = idProyecto;
    this.idUsuarioRol = idUsuarioRol;
    this.tieneComentarios = false;
  }

  /**
   * @return the idDocumProyecto
   */
  public Long getIdDocumProyecto() {
    return idDocumProyecto;
  }

  /**
   * @param idDocumProyecto the idDocumProyecto to set
   */
  public void setIdDocumProyecto(Long idDocumProyecto) {
    this.idDocumProyecto = idDocumProyecto;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the nombreArchivo
   */
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  /**
   * @param nombreArchivo the nombreArchivo to set
   */
  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  /**
   * @return the nombreArchivoFisico
   */
  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  /**
   * @param nombreArchivoFisico the nombreArchivoFisico to set
   */
  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  /**
   * @return the fechaPresentacion
   */
  public Date getFechaPresentacion() {
    return fechaPresentacion;
  }

  /**
   * @param fechaPresentacion the fechaPresentacion to set
   */
  public void setFechaPresentacion(Date fechaPresentacion) {
    this.fechaPresentacion = fechaPresentacion;
  }

  /**
   * @return the comentarioDocumento
   */
  public String getComentarioDocumento() {
    return comentarioDocumento;
  }

  /**
   * @param comentarioDocumento the comentarioDocumento to set
   */
  public void setComentarioDocumento(String comentarioDocumento) {
    this.comentarioDocumento = comentarioDocumento;
  }

  /**
   * @return the nombreTipoDocumento
   */
  public String getNombreTipoDocumento() {
    return nombreTipoDocumento;
  }

  /**
   * @param nombreTipoDocumento the nombreTipoDocumento to set
   */
  public void setNombreTipoDocumento(String nombreTipoDocumento) {
    this.nombreTipoDocumento = nombreTipoDocumento;
  }

  /**
   * @return the maquina
   */
  public String getMaquina() {
    return maquina;
  }

  /**
   * @param maquina the maquina to set
   */
  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  /**
   * @return the idProyecto
   */
  public Long getIdProyecto() {
    return idProyecto;
  }

  /**
   * @param idProyecto the idProyecto to set
   */
  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  /**
   * @return the idUsuarioRol
   */
  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  /**
   * @param idUsuarioRol the idUsuarioRol to set
   */
  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  /**
   * @return the tieneComentarios
   */
  public boolean isTieneComentarios() {
    return tieneComentarios;
  }

  /**
   * @param tieneComentarios the tieneComentarios to set
   */
  public void setTieneComentarios(boolean tieneComentarios) {
    this.tieneComentarios = tieneComentarios;
  }

}
