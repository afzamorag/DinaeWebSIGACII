/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.filtros;

import java.io.Serializable;
import java.util.Date;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class AuditoriaFiltro implements Serializable {

  private Date fecha;
  private String tabla;
  private String operacion;
  private String usuario;
  private String maquina;
  private String ip;

  /**
   * @return the fecha
   */
  public Date getFecha() {
    return fecha;
  }

  /**
   * @param fecha the fecha to set
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  /**
   * @return the tabla
   */
  public String getTabla() {
    return tabla;
  }

  /**
   * @param tabla the tabla to set
   */
  public void setTabla(String tabla) {
    this.tabla = tabla;
  }

  /**
   * @return the operacion
   */
  public String getOperacion() {
    return operacion;
  }

  /**
   * @param operacion the operacion to set
   */
  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  /**
   * @return the usuario
   */
  public String getUsuario() {
    return usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(String usuario) {
    this.usuario = usuario;
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
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * @param ip the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }
}
