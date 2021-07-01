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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "LOG_MOODLE_SEND_DATA")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = LogMoodleSendData.FIND_ALL, query = "SELECT s FROM LogMoodleSendData s ORDER BY s.fecha DESC")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class LogMoodleSendData implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "LogMoodleSendData.findAll";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "LOG_MOODLE_LOG", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_LOG_MOODLE")
  @SequenceGenerator(name = "SEC_LOG_MOODLE", sequenceName = "SEC_LOG_MOODLE", allocationSize = 1)
  private Long id;  
  @Basic(optional = false)
  @NotNull
  @Column(name = "FECHA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "MODULO", nullable = false, length = 50)
  private String modulo; 
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "ITEM", nullable = false, length = 50)
  private String item; 
    @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "TIPO", nullable = false, length = 50)
  private String tipo;  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "CODIGO", nullable = false, length = 50)
  private String codigo;  
    @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2000)
  @Column(name = "DESCRIPCION", nullable = false, length = 2000)
  private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    if (!(object instanceof LogMoodleSendData)) {
      return false;
    }
    LogMoodleSendData other = (LogMoodleSendData) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.LogMoodleSendData[ logMoodleLog=" + getId() + " ]";
  }

}
