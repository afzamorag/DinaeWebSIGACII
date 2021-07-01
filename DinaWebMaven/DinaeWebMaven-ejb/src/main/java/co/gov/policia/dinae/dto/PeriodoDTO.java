package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class PeriodoDTO implements IDataModel, Serializable {

  private Long idPeriodo;
  private Short anio;
  private String descripcion;
  private Date fechaInicio;
  private Date fechaFin;
  private Short cantidad;
  private Character estado;
  private String nombreArchivo;
  private String tipoRegistro;
  private Long concecutivo;
  private String tipoPeriodo;
  private String nombreConvocatorio;
  private String nombreArchivoFisico;
  private String identificadorUsuarioCrea;
  private String identificadorUsuarioModif;
  private String estadoConvocatorio;
  private Long idEstadoConvocatorio;
  

  /**
   *
   * @param idPeriodo
   * @param anio
   * @param descripcion
   * @param fechaInicio
   * @param fechaFin
   */
  public PeriodoDTO(Long idPeriodo, Short anio, String descripcion, Date fechaInicio, Date fechaFin) {
    this.idPeriodo = idPeriodo;
    this.anio = anio;
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
  }

  /**
   * SELECT NEW
   *
   * @param idPeriodo
   * @param nombreConvocatorio
   * @param concecutivo
   * @param estadoConvocatorio
   * @param idEstadoConvocatorio
   */
  public PeriodoDTO(Long idPeriodo, String nombreConvocatorio, Long concecutivo, String estadoConvocatorio, Long idEstadoConvocatorio) {

    this.idPeriodo = idPeriodo;
    this.nombreConvocatorio = nombreConvocatorio;
    this.concecutivo = concecutivo;
    this.estadoConvocatorio = estadoConvocatorio;
    this.idEstadoConvocatorio = idEstadoConvocatorio;
  }

  /**
   * SELECT NEW
   *
   * @param idPeriodo
   * @param nombreConvocatorio
   * @param concecutivo
   * @param estadoConvocatorio
   * @param idEstadoConvocatorio
   * @param fechaInicio
   * @param fechaFin
   */
  public PeriodoDTO(Long idPeriodo, String nombreConvocatorio, Long concecutivo, String estadoConvocatorio, Long idEstadoConvocatorio, Date fechaInicio, Date fechaFin) {

    this.idPeriodo = idPeriodo;
    this.nombreConvocatorio = nombreConvocatorio;
    this.concecutivo = concecutivo;
    this.estadoConvocatorio = estadoConvocatorio;
    this.idEstadoConvocatorio = idEstadoConvocatorio;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
  }

  /**
   *
   */
  public PeriodoDTO() {
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Short getAnio() {
    return anio;
  }

  public void setAnio(Short anio) {
    this.anio = anio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Short getCantidad() {
    return cantidad;
  }

  public void setCantidad(Short cantidad) {
    this.cantidad = cantidad;
  }

  public Character getEstado() {
    return estado;
  }

  public void setEstado(Character estado) {
    this.estado = estado;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getTipoRegistro() {
    return tipoRegistro;
  }

  public void setTipoRegistro(String tipoRegistro) {
    this.tipoRegistro = tipoRegistro;
  }

  public Long getConcecutivo() {
    return concecutivo;
  }

  public void setConcecutivo(Long concecutivo) {
    this.concecutivo = concecutivo;
  }

  public String getTipoPeriodo() {
    return tipoPeriodo;
  }

  public void setTipoPeriodo(String tipoPeriodo) {
    this.tipoPeriodo = tipoPeriodo;
  }

  public String getNombreConvocatorio() {
    return nombreConvocatorio;
  }

  public void setNombreConvocatorio(String nombreConvocatorio) {
    this.nombreConvocatorio = nombreConvocatorio;
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  public String getIdentificadorUsuarioCrea() {
    return identificadorUsuarioCrea;
  }

  public void setIdentificadorUsuarioCrea(String identificadorUsuarioCrea) {
    this.identificadorUsuarioCrea = identificadorUsuarioCrea;
  }

  public String getIdentificadorUsuarioModif() {
    return identificadorUsuarioModif;
  }

  public void setIdentificadorUsuarioModif(String identificadorUsuarioModif) {
    this.identificadorUsuarioModif = identificadorUsuarioModif;
  }

  public String getEstadoConvocatorio() {
    return estadoConvocatorio;
  }

  public void setEstadoConvocatorio(String estadoConvocatorio) {
    this.estadoConvocatorio = estadoConvocatorio;
  }

  public Long getIdEstadoConvocatorio() {
    return idEstadoConvocatorio;
  }

  public void setIdEstadoConvocatorio(Long idEstadoConvocatorio) {
    this.idEstadoConvocatorio = idEstadoConvocatorio;
  }

  public String getNumeroConcecutivoConsulta() {
    return "N° ".concat(concecutivo.toString());
  }
  
  @Override
  public String getLlaveModel() {
    if (this.idPeriodo == null) {
      return null;
    }

    return this.idPeriodo.toString();
  }

  public String getDescripcionConcecutivo() {

    if (concecutivo == null) {
      return "";
    }

    return "N° ".concat(concecutivo.toString());

  }
}
