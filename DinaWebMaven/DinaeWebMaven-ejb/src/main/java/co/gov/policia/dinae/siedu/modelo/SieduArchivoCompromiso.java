package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_ARCHIVO_COMPROMISO")
@NamedQueries({
  @NamedQuery(name = "SieduArchivoCompromiso.findAll", query = "SELECT s FROM SieduArchivoCompromiso s ")})
@Cacheable(false)
@XmlRootElement
public class SieduArchivoCompromiso implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SieduArchivoCompromisoPK sieduArchivoCompromisoPK;
  
  @JoinColumns({
    @JoinColumn(name = "ARCO_INVE", referencedColumnName = "COMP_INVE", insertable = false, updatable = false),
    @JoinColumn(name = "ARCO_COMP", referencedColumnName = "COMP_COMP", insertable = false, updatable = false)
  })
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private SieduCompromiso compromiso;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCO_NOMBRE_ARCHIVO", nullable = false, length = 100)
  private String nombreArchivo;  
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCO_NOMBRE_ARCHIVO_FISICO", nullable = false, length = 100)
  private String nombreArchivoFisico;  

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "ARCO_USU_CREA", nullable = false, length = 30)
  private String arcoUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "ARCO_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date arcoFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCO_MAQUINA_CREA", nullable = false, length = 100)
  private String arcoMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCO_IP_CREA", nullable = false, length = 100)
  private String arcoIpCrea;
  
  @Size(max = 30)
  @Column(name = "ARCO_USU_MOD", length = 30)
  private String arcoUsuMod;
  
  @Column(name = "ARCO_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date arcoFechaMod;
  
  @Size(max = 100)
  @Column(name = "ARCO_MAQUINA_MOD", length = 100)
  private String arcoMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "ARCO_IP_MOD", length = 100)
  private String arcoIpMod;

  public SieduArchivoCompromiso() {
  }

  public SieduArchivoCompromiso(SieduArchivoCompromisoPK sieduArchivoCompromisoPK, SieduCompromiso compromiso, String nombreArchivo, String nombreArchivoFisico, String arcoUsuCrea, Date arcoFechaCrea, String arcoMaquinaCrea, String arcoIpCrea, String arcoUsuMod, Date arcoFechaMod, String arcoMaquinaMod, String arcoIpMod) {
    this.sieduArchivoCompromisoPK = sieduArchivoCompromisoPK;
    this.compromiso = compromiso;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.arcoUsuCrea = arcoUsuCrea;
    this.arcoFechaCrea = arcoFechaCrea;
    this.arcoMaquinaCrea = arcoMaquinaCrea;
    this.arcoIpCrea = arcoIpCrea;
    this.arcoUsuMod = arcoUsuMod;
    this.arcoFechaMod = arcoFechaMod;
    this.arcoMaquinaMod = arcoMaquinaMod;
    this.arcoIpMod = arcoIpMod;
  }

  @XmlTransient
  public SieduArchivoCompromisoPK getSieduArchivoCompromisoPK() {
    return sieduArchivoCompromisoPK;
  }

  public void setSieduArchivoCompromisoPK(SieduArchivoCompromisoPK sieduArchivoCompromisoPK) {
    this.sieduArchivoCompromisoPK = sieduArchivoCompromisoPK;
  }

  @XmlTransient
  public SieduCompromiso getCompromiso() {
    return compromiso;
  }

  public void setCompromiso(SieduCompromiso compromiso) {
    this.compromiso = compromiso;
  }

  @XmlTransient
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  @XmlTransient
  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  @XmlTransient
  public String getArcoUsuCrea() {
    return arcoUsuCrea;
  }

  public void setArcoUsuCrea(String arcoUsuCrea) {
    this.arcoUsuCrea = arcoUsuCrea;
  }

  @XmlTransient
  public Date getArcoFechaCrea() {
    return arcoFechaCrea;
  }

  public void setArcoFechaCrea(Date arcoFechaCrea) {
    this.arcoFechaCrea = arcoFechaCrea;
  }

  @XmlTransient
  public String getArcoMaquinaCrea() {
    return arcoMaquinaCrea;
  }

  public void setArcoMaquinaCrea(String arcoMaquinaCrea) {
    this.arcoMaquinaCrea = arcoMaquinaCrea;
  }

  @XmlTransient
  public String getArcoIpCrea() {
    return arcoIpCrea;
  }

  public void setArcoIpCrea(String arcoIpCrea) {
    this.arcoIpCrea = arcoIpCrea;
  }

  @XmlTransient
  public String getArcoUsuMod() {
    return arcoUsuMod;
  }

  public void setArcoUsuMod(String arcoUsuMod) {
    this.arcoUsuMod = arcoUsuMod;
  }

  @XmlTransient
  public Date getArcoFechaMod() {
    return arcoFechaMod;
  }

  public void setArcoFechaMod(Date arcoFechaMod) {
    this.arcoFechaMod = arcoFechaMod;
  }

  @XmlTransient
  public String getArcoMaquinaMod() {
    return arcoMaquinaMod;
  }

  public void setArcoMaquinaMod(String arcoMaquinaMod) {
    this.arcoMaquinaMod = arcoMaquinaMod;
  }
  
  @XmlTransient
  public String getArcoIpMod() {
    return arcoIpMod;
  }

  public void setArcoIpMod(String arcoIpMod) {
    this.arcoIpMod = arcoIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + Objects.hashCode(this.sieduArchivoCompromisoPK);
    hash = 67 * hash + Objects.hashCode(this.compromiso);
    hash = 67 * hash + Objects.hashCode(this.nombreArchivo);
    hash = 67 * hash + Objects.hashCode(this.nombreArchivoFisico);
    hash = 67 * hash + Objects.hashCode(this.arcoUsuCrea);
    hash = 67 * hash + Objects.hashCode(this.arcoFechaCrea);
    hash = 67 * hash + Objects.hashCode(this.arcoMaquinaCrea);
    hash = 67 * hash + Objects.hashCode(this.arcoIpCrea);
    hash = 67 * hash + Objects.hashCode(this.arcoUsuMod);
    hash = 67 * hash + Objects.hashCode(this.arcoFechaMod);
    hash = 67 * hash + Objects.hashCode(this.arcoMaquinaMod);
    hash = 67 * hash + Objects.hashCode(this.arcoIpMod);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SieduArchivoCompromiso other = (SieduArchivoCompromiso) obj;
    if (!Objects.equals(this.nombreArchivo, other.nombreArchivo)) {
      return false;
    }
    if (!Objects.equals(this.nombreArchivoFisico, other.nombreArchivoFisico)) {
      return false;
    }
    if (!Objects.equals(this.arcoUsuCrea, other.arcoUsuCrea)) {
      return false;
    }
    if (!Objects.equals(this.arcoMaquinaCrea, other.arcoMaquinaCrea)) {
      return false;
    }
    if (!Objects.equals(this.arcoIpCrea, other.arcoIpCrea)) {
      return false;
    }
    if (!Objects.equals(this.arcoUsuMod, other.arcoUsuMod)) {
      return false;
    }
    if (!Objects.equals(this.arcoMaquinaMod, other.arcoMaquinaMod)) {
      return false;
    }
    if (!Objects.equals(this.arcoIpMod, other.arcoIpMod)) {
      return false;
    }
    if (!Objects.equals(this.sieduArchivoCompromisoPK, other.sieduArchivoCompromisoPK)) {
      return false;
    }
    if (!Objects.equals(this.compromiso, other.compromiso)) {
      return false;
    }
    if (!Objects.equals(this.arcoFechaCrea, other.arcoFechaCrea)) {
      return false;
    }
    if (!Objects.equals(this.arcoFechaMod, other.arcoFechaMod)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduArchivoCompromiso{" + "sieduArchivoCompromisoPK=" + sieduArchivoCompromisoPK + ", compromiso=" + compromiso + ", nombreArchivo=" + nombreArchivo + ", nombreArchivoFisico=" + nombreArchivoFisico + ", arcoUsuCrea=" + arcoUsuCrea + ", arcoFechaCrea=" + arcoFechaCrea + ", arcoMaquinaCrea=" + arcoMaquinaCrea + ", arcoIpCrea=" + arcoIpCrea + ", arcoUsuMod=" + arcoUsuMod + ", arcoFechaMod=" + arcoFechaMod + ", arcoMaquinaMod=" + arcoMaquinaMod + ", arcoIpMod=" + arcoIpMod + '}';
  }


  
  
  
}
