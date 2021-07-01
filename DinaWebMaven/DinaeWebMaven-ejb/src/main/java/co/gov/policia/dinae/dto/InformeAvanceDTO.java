package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class InformeAvanceDTO implements Serializable {

  private Long idInformeAvance;

  private Date fechaInicio;

  private Date fechaFinalizacion;

  private Date fechaRegistro;

  private String nombreArchivo;

  private String nombreArchivoFisico;

  private String tipoContenidoArchivo;

  private ProyectoDTO proyecto;

  public InformeAvanceDTO() {
  }

  public InformeAvanceDTO(Long idInformeAvance, Date fechaInicio, Date fechaFinalizacion, Date fechaRegistro, String nombreArchivo, String nombreArchivoFisico, String tipoContenidoArchivo, ProyectoDTO proyecto) {
    this.idInformeAvance = idInformeAvance;
    this.fechaInicio = fechaInicio;
    this.fechaFinalizacion = fechaFinalizacion;
    this.fechaRegistro = fechaRegistro;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.tipoContenidoArchivo = tipoContenidoArchivo;
    this.proyecto = proyecto;
  }

  public Long getIdInformeAvance() {
    return idInformeAvance;
  }

  public void setIdInformeAvance(Long idInformeAvance) {
    this.idInformeAvance = idInformeAvance;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFinalizacion() {
    return fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date fechaFinalizacion) {
    this.fechaFinalizacion = fechaFinalizacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  public String getTipoContenidoArchivo() {
    return tipoContenidoArchivo;
  }

  public void setTipoContenidoArchivo(String tipoContenidoArchivo) {
    this.tipoContenidoArchivo = tipoContenidoArchivo;
  }

  public ProyectoDTO getProyecto() {
    return proyecto;
  }

  public void setProyecto(ProyectoDTO proyecto) {
    this.proyecto = proyecto;
  }

}
