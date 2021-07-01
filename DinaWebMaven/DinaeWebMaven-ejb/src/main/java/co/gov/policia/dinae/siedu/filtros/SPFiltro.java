/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.filtros;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public class SPFiltro implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long pae;
  private String usuario;
  private Date fecha;
  private String maquina;
  private String ip;

  public SPFiltro() {
  }

  /**
   * @return the pae
   */
  public Long getPae() {
    return pae;
  }

  /**
   * @param pae the pae to set
   */
  public void setPae(Long pae) {
    this.pae = pae;
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
