package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class CompromisoDTO implements Serializable {

  private Long idCompromisoPeriodo;
  private Integer numeroIncrementa;
  private Date fecha;
  private Long idPeriodo;
  private Long tipoCompromiso;
  private String estado;
  private String compromiso;
  private boolean muestraLink;
  private String tipoInforme;

  private String nombreCompromiso;
  private Date fechaLimieGeneral;
  private Date fechaPrentacion;

  private Long idCompromisoProyecto;
  private Long idCompromisoCorreccion;
  private Long idCompromisoPadre;
  private Long idCompromisoHijo;
  private Long idEstadoCompromiso;

  private String origenCompromiso;

  private String casoUso;

  private boolean actualizoFechaContenporanea;

  /**
   *
   */
  public CompromisoDTO() {

    actualizoFechaContenporanea = false;

  }

  /**
   *
   * @param idCompromisoPeriodo
   * @param idCompromisoProyecto
   * @param estado
   * @param nombreCompromiso
   * @param fechaLimieGeneral
   * @param fechaPrentacion
   * @param numeroIncrementa
   */
  public CompromisoDTO(Long idCompromisoPeriodo, Long idCompromisoProyecto, String estado,
          String nombreCompromiso, Date fechaLimieGeneral, Date fechaPrentacion, short numeroIncrementa) {

    this.idCompromisoPeriodo = idCompromisoPeriodo;
    this.idCompromisoProyecto = idCompromisoProyecto;
    this.estado = estado;
    this.nombreCompromiso = nombreCompromiso;
    this.fechaLimieGeneral = fechaLimieGeneral;
    this.fechaPrentacion = fechaPrentacion;
    if (numeroIncrementa > 0) {
      this.numeroIncrementa = (int) numeroIncrementa;
    }

    actualizoFechaContenporanea = false;

  }

  /**
   *
   * @param idCompromisoPeriodo
   * @param numeroIncrementa
   * @param fecha
   * @param idPeriodo
   * @param tipoCompromiso
   * @param estado
   * @param compromiso
   */
  public CompromisoDTO(Long idCompromisoPeriodo, Integer numeroIncrementa, Date fecha, Long idPeriodo, Long tipoCompromiso, String estado, String compromiso) {

    this.idCompromisoPeriodo = idCompromisoPeriodo;
    this.numeroIncrementa = numeroIncrementa;
    this.fecha = fecha;
    this.idPeriodo = idPeriodo;
    this.tipoCompromiso = tipoCompromiso;
    this.estado = estado;
    this.compromiso = compromiso;

    actualizoFechaContenporanea = false;
  }

  public Long getIdCompromisoPeriodo() {
    return idCompromisoPeriodo;
  }

  public void setIdCompromisoPeriodo(Long idCompromisoPeriodo) {
    this.idCompromisoPeriodo = idCompromisoPeriodo;
  }

  public Integer getNumeroIncrementa() {
    return numeroIncrementa;
  }

  public void setNumeroIncrementa(Integer numeroIncrementa) {
    this.numeroIncrementa = numeroIncrementa;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Long getTipoCompromiso() {
    return tipoCompromiso;
  }

  public void setTipoCompromiso(Long tipoCompromiso) {
    this.tipoCompromiso = tipoCompromiso;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getCompromiso() {
    return compromiso;
  }

  public void setCompromiso(String compromiso) {
    this.compromiso = compromiso;
  }

  public boolean isMuestraLink() {
    return muestraLink;
  }

  public void setMuestraLink(boolean muestraLink) {
    this.muestraLink = muestraLink;
  }

  public String getTipoInforme() {
    return tipoInforme;
  }

  public void setTipoInforme(String tipoInforme) {
    this.tipoInforme = tipoInforme;
  }

  public String getNumeroCompromisoValor() {
    if (numeroIncrementa == null || numeroIncrementa.equals(Integer.valueOf(0))) {
      return "";
    }
    return numeroIncrementa.toString();
  }

  public Long getIdCompromisoProyecto() {
    return idCompromisoProyecto;
  }

  public void setIdCompromisoProyecto(Long idCompromisoProyecto) {
    this.idCompromisoProyecto = idCompromisoProyecto;
  }

  public String getNombreCompromiso() {
    return nombreCompromiso;
  }

  public void setNombreCompromiso(String nombreCompromiso) {
    this.nombreCompromiso = nombreCompromiso;
  }

  public Date getFechaLimieGeneral() {
    return fechaLimieGeneral;
  }

  public void setFechaLimieGeneral(Date fechaLimieGeneral) {
    this.fechaLimieGeneral = fechaLimieGeneral;
  }

  public Date getFechaPrentacion() {
    return fechaPrentacion;
  }

  public void setFechaPrentacion(Date fechaPrentacion) {
    this.fechaPrentacion = fechaPrentacion;
  }

  public String getCasoUso() {
    return casoUso;
  }

  public void setCasoUso(String casoUso) {
    this.casoUso = casoUso;
  }

  public Long getIdCompromisoCorreccion() {
    return idCompromisoCorreccion;
  }

  public void setIdCompromisoCorreccion(Long idCompromisoCorreccion) {
    this.idCompromisoCorreccion = idCompromisoCorreccion;
  }

  public Long getIdCompromisoPadre() {
    return idCompromisoPadre;
  }

  public void setIdCompromisoPadre(Long idCompromisoPadre) {
    this.idCompromisoPadre = idCompromisoPadre;
  }

  public Long getIdCompromisoHijo() {
    return idCompromisoHijo;
  }

  public void setIdCompromisoHijo(Long idCompromisoHijo) {
    this.idCompromisoHijo = idCompromisoHijo;
  }

  public Long getIdEstadoCompromiso() {
    return idEstadoCompromiso;
  }

  public void setIdEstadoCompromiso(Long idEstadoCompromiso) {
    this.idEstadoCompromiso = idEstadoCompromiso;
  }

  public String getOrigenCompromiso() {
    return origenCompromiso;
  }

  public void setOrigenCompromiso(String origenCompromiso) {
    this.origenCompromiso = origenCompromiso;
  }

  public boolean isActualizoFechaContenporanea() {
    return actualizoFechaContenporanea;
  }

  public void setActualizoFechaContenporanea(boolean actualizoFechaContenporanea) {
    this.actualizoFechaContenporanea = actualizoFechaContenporanea;
  }

}
