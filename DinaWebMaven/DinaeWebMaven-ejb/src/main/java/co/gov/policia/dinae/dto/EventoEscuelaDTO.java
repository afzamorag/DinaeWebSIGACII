package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class EventoEscuelaDTO implements Serializable {

  private int idEventoSeleccionado;
  private Long listaUnidadesItemSeleccionado;
  private Long listaEscuelaItemSeleccionada;
  private Date fechaInicioSeleccionado;
  private Date fechaFinSeleccionado;
  private int totalDiasSeleccionado;
  private int listaSieduLugarDeptoMunSeleccionado;
  private Long costoSeleccionado;
  private Short listaConvenioItemSeleccionado;
  private Short finaciacionItemSeleccionado;
  private int listaEntidadItemSeleccionado;
  private String maquinaCreacion;
  private String usuario;
  private Date fechaCreacion;
  private Integer listaPresupuestoItemSeleccionado;

  public EventoEscuelaDTO() {
  }

  public EventoEscuelaDTO(int idEventoSeleccionado, Long listaUnidadesItemSeleccionado, Long listaEscuelaItemSeleccionada, Date fechaInicioSeleccionado, Date fechaFinSeleccionado, int totalDiasSeleccionado, int listaSieduLugarDeptoMunSeleccionado, Long costoSeleccionado, Short listaConvenioItemSeleccionado, Short finaciacionItemSeleccionado, int listaEntidadItemSeleccionado, String maquinaCreacion, String usuario, Date fechaCreacion, Integer listaPresupuestoItemSeleccionado) {
    this.idEventoSeleccionado = idEventoSeleccionado;
    this.listaUnidadesItemSeleccionado = listaUnidadesItemSeleccionado;
    this.listaEscuelaItemSeleccionada = listaEscuelaItemSeleccionada;
    this.fechaInicioSeleccionado = fechaInicioSeleccionado;
    this.fechaFinSeleccionado = fechaFinSeleccionado;
    this.totalDiasSeleccionado = totalDiasSeleccionado;
    this.listaSieduLugarDeptoMunSeleccionado = listaSieduLugarDeptoMunSeleccionado;
    this.costoSeleccionado = costoSeleccionado;
    this.listaConvenioItemSeleccionado = listaConvenioItemSeleccionado;
    this.finaciacionItemSeleccionado = finaciacionItemSeleccionado;
    this.listaEntidadItemSeleccionado = listaEntidadItemSeleccionado;
    this.maquinaCreacion = maquinaCreacion;
    this.usuario = usuario;
    this.fechaCreacion = fechaCreacion;
    this.listaPresupuestoItemSeleccionado = listaPresupuestoItemSeleccionado;
  }

  public int getIdEventoSeleccionado() {
    return idEventoSeleccionado;
  }

  public void setIdEventoSeleccionado(int idEventoSeleccionado) {
    this.idEventoSeleccionado = idEventoSeleccionado;
  }

  public Long getListaUnidadesItemSeleccionado() {
    return listaUnidadesItemSeleccionado;
  }

  public void setListaUnidadesItemSeleccionado(Long listaUnidadesItemSeleccionado) {
    this.listaUnidadesItemSeleccionado = listaUnidadesItemSeleccionado;
  }

  public Long getListaEscuelaItemSeleccionada() {
    return listaEscuelaItemSeleccionada;
  }

  public void setListaEscuelaItemSeleccionada(Long listaEscuelaItemSeleccionada) {
    this.listaEscuelaItemSeleccionada = listaEscuelaItemSeleccionada;
  }

  public Date getFechaInicioSeleccionado() {
    return fechaInicioSeleccionado;
  }

  public void setFechaInicioSeleccionado(Date fechaInicioSeleccionado) {
    this.fechaInicioSeleccionado = fechaInicioSeleccionado;
  }

  public Date getFechaFinSeleccionado() {
    return fechaFinSeleccionado;
  }

  public void setFechaFinSeleccionado(Date fechaFinSeleccionado) {
    this.fechaFinSeleccionado = fechaFinSeleccionado;
  }

  public int getTotalDiasSeleccionado() {
    return totalDiasSeleccionado;
  }

  public void setTotalDiasSeleccionado(int totalDiasSeleccionado) {
    this.totalDiasSeleccionado = totalDiasSeleccionado;
  }

  public int getListaSieduLugarDeptoMunSeleccionado() {
    return listaSieduLugarDeptoMunSeleccionado;
  }

  public void setListaSieduLugarDeptoMunSeleccionado(int listaSieduLugarDeptoMunSeleccionado) {
    this.listaSieduLugarDeptoMunSeleccionado = listaSieduLugarDeptoMunSeleccionado;
  }

  public Long getCostoSeleccionado() {
    return costoSeleccionado;
  }

  public void setCostoSeleccionado(Long costoSeleccionado) {
    this.costoSeleccionado = costoSeleccionado;
  }

  public Short getListaConvenioItemSeleccionado() {
    return listaConvenioItemSeleccionado;
  }

  public void setListaConvenioItemSeleccionado(Short listaConvenioItemSeleccionado) {
    this.listaConvenioItemSeleccionado = listaConvenioItemSeleccionado;
  }

  public Short getFinaciacionItemSeleccionado() {
    return finaciacionItemSeleccionado;
  }

  public void setFinaciacionItemSeleccionado(Short finaciacionItemSeleccionado) {
    this.finaciacionItemSeleccionado = finaciacionItemSeleccionado;
  }

  public int getListaEntidadItemSeleccionado() {
    return listaEntidadItemSeleccionado;
  }

  public void setListaEntidadItemSeleccionado(int listaEntidadItemSeleccionado) {
    this.listaEntidadItemSeleccionado = listaEntidadItemSeleccionado;
  }

  public String getMaquinaCreacion() {
    return maquinaCreacion;
  }

  public void setMaquinaCreacion(String maquinaCreacion) {
    this.maquinaCreacion = maquinaCreacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Integer getListaPresupuestoItemSeleccionado() {
    return listaPresupuestoItemSeleccionado;
  }

  public void setListaPresupuestoItemSeleccionado(Integer listaPresupuestoItemSeleccionado) {
    this.listaPresupuestoItemSeleccionado = listaPresupuestoItemSeleccionado;
  }

  public void setListaPresupuestoItemSeleccionado(int listaPresupuestoItemSeleccionado) {
    this.listaPresupuestoItemSeleccionado = listaPresupuestoItemSeleccionado;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

}
