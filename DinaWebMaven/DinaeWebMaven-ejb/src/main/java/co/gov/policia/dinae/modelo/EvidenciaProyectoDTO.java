package co.gov.policia.dinae.modelo;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class EvidenciaProyectoDTO implements Serializable {

  private Long idEvidenciaProyecto;
  private String descripcion;
  private String nombreArchivo;
  private String nombreArchivoFisico;
  private Long idTipoArchivo;
  private String nombreTipoArchivo;
  private Long idProyecto;

  public EvidenciaProyectoDTO() {
  }

  public EvidenciaProyectoDTO(Long idEvidenciaProyecto, String descripcion, String nombreArchivo, String nombreArchivoFisico, Long idTipoArchivo, String nombreTipoArchivo, Long idProyecto) {
    this.idEvidenciaProyecto = idEvidenciaProyecto;
    this.descripcion = descripcion;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.idTipoArchivo = idTipoArchivo;
    this.nombreTipoArchivo = nombreTipoArchivo;
    this.idProyecto = idProyecto;
  }

  public Long getIdEvidenciaProyecto() {
    return idEvidenciaProyecto;
  }

  public void setIdEvidenciaProyecto(Long idEvidenciaProyecto) {
    this.idEvidenciaProyecto = idEvidenciaProyecto;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
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

  public Long getIdTipoArchivo() {
    return idTipoArchivo;
  }

  public void setIdTipoArchivo(Long idTipoArchivo) {
    this.idTipoArchivo = idTipoArchivo;
  }

  public String getNombreTipoArchivo() {
    return nombreTipoArchivo;
  }

  public void setNombreTipoArchivo(String nombreTipoArchivo) {
    this.nombreTipoArchivo = nombreTipoArchivo;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

}
