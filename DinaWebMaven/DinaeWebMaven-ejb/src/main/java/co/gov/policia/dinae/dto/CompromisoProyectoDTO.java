package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class CompromisoProyectoDTO implements IDataModel, Serializable {

  private Long idCompromiso;
  private String codigoProyecto;
  private String tituloProyecto;
  private String compromiso;
  private Date fechaLimite;
  private Date fechaPresentacion;
  private Long resultadoTemporal;
  private String comentario;
  private String origenCompromiso;
  private Long idUnidadPolicial;
  private String nombreUnidadPolicial;
  private Long idEstado;
  private String nombreEstado;
  private Long idResultadoRetroalimentacion;
  private Date fechaNuevoCompromiso;
  private Long idCompromisoPeriodo;
  private Long idPeriodo;
  private Long idProyecto;

  private Long idTipoCompromiso;
  private Date fechaLimieGeneral;
  private Date fechaLimieOtorgada;

  /**
   *
   */
  public CompromisoProyectoDTO() {
  }

  private String nombreCompromisoCorreccion;

  /**
   *
   * @param idCompromiso
   * @param nombreCompromisoCorreccion
   * @param codigoProyecto
   */
  public CompromisoProyectoDTO(Long idCompromiso, String nombreCompromisoCorreccion, String codigoProyecto) {

    this.idCompromiso = idCompromiso;
    this.nombreCompromisoCorreccion = nombreCompromisoCorreccion;
    this.codigoProyecto = codigoProyecto;

  }

  /**
   * SELECCT NEW..
   *
   * @param idCompromiso
   * @param codigoProyecto
   * @param tituloProyecto
   * @param compromiso
   * @param fechaLimite
   * @param resultadoTemporal
   * @param comentario
   */
  public CompromisoProyectoDTO(Long idCompromiso, String codigoProyecto, String tituloProyecto, String compromiso, Date fechaLimite, Long resultadoTemporal, String comentario) {
    this.idCompromiso = idCompromiso;
    this.codigoProyecto = codigoProyecto;
    this.tituloProyecto = tituloProyecto;
    this.compromiso = compromiso;
    this.fechaLimite = fechaLimite;
    this.resultadoTemporal = resultadoTemporal;
    this.comentario = comentario;
    this.origenCompromiso = IConstantes.ORIGEN_COMPROMISO_PROYECTO;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloProyecto
   * @param idTipoCompromiso
   * @param compromiso
   * @param numeroIncrementa
   * @param fechaLimieGeneral
   * @param fechaLimieOtorgada
   */
  public CompromisoProyectoDTO(Long idUnidadPolicial, String nombreUnidadPolicial, String tituloProyecto, Long idTipoCompromiso, String compromiso, short numeroIncrementa, Date fechaLimieGeneral, Date fechaLimieOtorgada) {

    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.tituloProyecto = tituloProyecto;
    this.idTipoCompromiso = idTipoCompromiso;

    if (idTipoCompromiso == null) {
      this.compromiso = "";
    } else {

      this.compromiso = compromiso.concat(
              idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
              ? " ".concat(String.valueOf(numeroIncrementa)) : "");
    }

    this.fechaLimieGeneral = fechaLimieGeneral;
    this.fechaLimieOtorgada = fechaLimieOtorgada;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloProyecto
   * @param idTipoCompromiso
   * @param compromiso
   * @param numeroIncrementa
   * @param fechaLimieGeneral
   */
  public CompromisoProyectoDTO(Long idUnidadPolicial, String nombreUnidadPolicial, String tituloProyecto, Long idTipoCompromiso, String compromiso, short numeroIncrementa, Date fechaLimieGeneral, Long idCompromisoPeriodo) {

    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.tituloProyecto = tituloProyecto;
    this.idTipoCompromiso = idTipoCompromiso;

    if (idTipoCompromiso == null) {
      this.compromiso = "";
    } else {

      this.compromiso = compromiso.concat(
              idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
              ? " ".concat(String.valueOf(numeroIncrementa)) : "");
    }

    this.fechaLimieGeneral = fechaLimieGeneral;
    this.idCompromisoPeriodo = idCompromisoPeriodo;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloProyecto
   * @param idTipoCompromiso
   * @param compromiso
   * @param fechaLimieGeneral
   */
  public CompromisoProyectoDTO(Long idUnidadPolicial, String nombreUnidadPolicial, String tituloProyecto, Long idTipoCompromiso, String compromiso, Date fechaLimieGeneral) {

    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.tituloProyecto = tituloProyecto;
    this.idTipoCompromiso = idTipoCompromiso;
    this.compromiso = compromiso;
    this.fechaLimieGeneral = fechaLimieGeneral;
  }

  public Long getIdCompromiso() {
    return idCompromiso;
  }

  public void setIdCompromiso(Long idCompromiso) {
    this.idCompromiso = idCompromiso;
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

  public String getCompromiso() {
    return compromiso;
  }

  public void setCompromiso(String compromiso) {
    this.compromiso = compromiso;
  }

  public Date getFechaLimite() {
    return fechaLimite;
  }

  public void setFechaLimite(Date fechaLimite) {
    this.fechaLimite = fechaLimite;
  }

  public Long getResultadoTemporal() {
    return resultadoTemporal;
  }

  public void setResultadoTemporal(Long resultadoTemporal) {
    this.resultadoTemporal = resultadoTemporal;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  @Override
  public String getLlaveModel() {

    return origenCompromiso.concat("_").concat(String.valueOf(idCompromiso));

  }

  public String getOrigenCompromiso() {
    return origenCompromiso;
  }

  public void setOrigenCompromiso(String origenCompromiso) {
    this.origenCompromiso = origenCompromiso;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  public Date getFechaPresentacion() {
    return fechaPresentacion;
  }

  public void setFechaPresentacion(Date fechaPresentacion) {
    this.fechaPresentacion = fechaPresentacion;
  }

  public Long getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
  }

  public String getNombreEstado() {
    return nombreEstado;
  }

  public void setNombreEstado(String nombreEstado) {
    this.nombreEstado = nombreEstado;
  }

  public Long getIdResultadoRetroalimentacion() {
    return idResultadoRetroalimentacion;
  }

  public void setIdResultadoRetroalimentacion(Long idResultadoRetroalimentacion) {
    this.idResultadoRetroalimentacion = idResultadoRetroalimentacion;
  }

  public Date getFechaNuevoCompromiso() {
    return fechaNuevoCompromiso;
  }

  public void setFechaNuevoCompromiso(Date fechaNuevoCompromiso) {
    this.fechaNuevoCompromiso = fechaNuevoCompromiso;
  }

  public Date getFechaLimieGeneral() {
    return fechaLimieGeneral;
  }

  public void setFechaLimieGeneral(Date fechaLimieGeneral) {
    this.fechaLimieGeneral = fechaLimieGeneral;
  }

  public Date getFechaLimieOtorgada() {
    return fechaLimieOtorgada;
  }

  public void setFechaLimieOtorgada(Date fechaLimieOtorgada) {
    this.fechaLimieOtorgada = fechaLimieOtorgada;
  }

  public Long getIdTipoCompromiso() {
    return idTipoCompromiso;
  }

  public void setIdTipoCompromiso(Long idTipoCompromiso) {
    this.idTipoCompromiso = idTipoCompromiso;
  }

  public Long getIdCompromisoPeriodo() {
    return idCompromisoPeriodo;
  }

  public void setIdCompromisoPeriodo(Long idCompromisoPeriodo) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getIdCompromisoPeriodoYtipo() {

    return idCompromiso.toString().concat(":COMPROMISO_PROYECTO");

  }

  public String getNombreCompromisoCorreccion() {
    return nombreCompromisoCorreccion;
  }

  public void setNombreCompromisoCorreccion(String nombreCompromisoCorreccion) {
    this.nombreCompromisoCorreccion = nombreCompromisoCorreccion;
  }

}
