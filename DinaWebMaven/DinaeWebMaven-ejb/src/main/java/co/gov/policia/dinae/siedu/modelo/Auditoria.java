/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_AUDITORIA")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = Auditoria.FIND_ALL, query = "SELECT s FROM Auditoria s ORDER BY s.id DESC")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Auditoria implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "Auditoria.findAll";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "AUDI_AUDI", nullable = false)
  @XmlAttribute
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Column(name = "AUDI_FECHA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "AUDI_TABLA", nullable = false, length = 30)
  private String tabla;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "AUDI_OPER", nullable = false, length = 1)
  private String operacion;
  @Basic(optional = false)
  @NotNull
  @Lob
  @Column(name = "AUDI_REGANTES", nullable = false)
  private String valorAnterior;
  @Lob
  @Column(name = "AUDI_REGDESPUES")
  private String valorNuevo;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "AUDI_USUARIO", nullable = false, length = 30)
  private String usuario;
  @Size(max = 100)
  @Column(name = "AUDI_MAQUINA", length = 100)
  private String maquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "AUDI_IP", nullable = false, length = 50)
  private String ip;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
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
   * @return the valorAnterior
   */
  public String getValorAnterior() {
    return valorAnterior;
  }

  /**
   * @param valorAnterior the valorAnterior to set
   */
  public void setValorAnterior(String valorAnterior) {
    this.valorAnterior = valorAnterior;
  }

  /**
   * @return the valorNuevo
   */
  public String getValorNuevo() {
    return valorNuevo;
  }

  /**
   * @param valorNuevo the valorNuevo to set
   */
  public void setValorNuevo(String valorNuevo) {
    this.valorNuevo = valorNuevo;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Auditoria)) {
      return false;
    }
    Auditoria other = (Auditoria) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduAuditoria[ audiAudi=" + getId() + " ]";
  }

}
