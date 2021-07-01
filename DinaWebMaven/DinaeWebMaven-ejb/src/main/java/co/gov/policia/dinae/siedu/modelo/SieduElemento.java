package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name = "SIEDU_ELEMENTO")
@NamedQueries({
  @NamedQuery(name = "SieduElemento.findAll", query = "SELECT s FROM SieduElemento s")})
@Cacheable(false)
@XmlRootElement
public class SieduElemento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SieduElemento_seq_gen")
  @SequenceGenerator(name = "SieduElemento_seq_gen", sequenceName = "siedu_elemento_seq", allocationSize = 1)
  @Column(name = "ELEM_ELEM")
  private Long idElemento;
  
  @Basic(optional = true)
  @Size(min = 1, max = 200)
  @Column(name = "ELEM_DESCRI", nullable = true, length = 200)
  private String descripcion;
  
  @Basic(optional = true)
  @Size(min = 1, max = 30)
  @Column(name = "ELEM_SERIAL", nullable = true, length = 30)
  private String serial;
  
  @Column(name = "ELEM_VALOR")
  private BigDecimal valor;
    
  @Basic(optional = true)
  @Column(name = "ELEM_FECHA_EXPIRACION", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaExpiracion;
    
  @Basic(optional = true)
  @Size(min = 1, max = 200)
  @Column(name = "ELEM_FABRICANTE", nullable = true, length = 200)
  private String fabricante;
  
  @JoinColumn(name = "ELEM_DOM_TIPO_INVESTIGACION", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio tipoInvestigacion;
  
  @Column(name = "ELEM_ESTADO")
  private Character estado;
  
  @JoinColumns({
    @JoinColumn(name = "ELEM_UDE_CONSECUTIVO", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "ELEM_UDE_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadDependencia unidad;
  
  @JoinColumn(name = "ELEM_TPEL", referencedColumnName = "TPEL_TPEL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduTipoElemento tipoElemento;
  
  @JoinColumns({
    @JoinColumn(name = "ELEM_STEL", referencedColumnName = "STEL_STEL"),
    @JoinColumn(name = "ELEM_TPEL", referencedColumnName = "STEL_TPEL", insertable = false, updatable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduSubtipoElemento subtipoElemento;
  
  @Size(max = 30)
  @Column(name = "ELEM_USU_CREA", length = 30)
  private String elemUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "ELEM_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date elemFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ELEM_MAQUINA_CREA", nullable = false, length = 100)
  private String elemMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ELEM_IP_CREA", nullable = false, length = 100)
  private String elemIpCrea;
  
  @Size(max = 30)
  @Column(name = "ELEM_USU_MOD", length = 30)
  private String elemUsuMod;
  
  @Column(name = "ELEM_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date elemFechaMod;
  
  @Size(max = 100)
  @Column(name = "ELEM_MAQUINA_MOD", length = 100)
  private String elemMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "ELEM_IP_MOD", length = 100)
  private String elemIpMod;

  public SieduElemento() {
  }

  public SieduElemento(Long idElemento, String descripcion, String serial, BigDecimal valor, Date fechaExpiracion, String fabricante, Dominio tipoInvestigacion, Character estado, UnidadDependencia unidad, SieduTipoElemento tipoElemento, SieduSubtipoElemento subtipoElemento, String elemUsuCrea, Date elemFechaCrea, String elemMaquinaCrea, String elemIpCrea, String elemUsuMod, Date elemFechaMod, String elemMaquinaMod, String elemIpMod) {
    this.idElemento = idElemento;
    this.descripcion = descripcion;
    this.serial = serial;
    this.valor = valor;
    this.fechaExpiracion = fechaExpiracion;
    this.fabricante = fabricante;
    this.tipoInvestigacion = tipoInvestigacion;
    this.estado = estado;
    this.unidad = unidad;
    this.tipoElemento = tipoElemento;
    this.subtipoElemento = subtipoElemento;
    this.elemUsuCrea = elemUsuCrea;
    this.elemFechaCrea = elemFechaCrea;
    this.elemMaquinaCrea = elemMaquinaCrea;
    this.elemIpCrea = elemIpCrea;
    this.elemUsuMod = elemUsuMod;
    this.elemFechaMod = elemFechaMod;
    this.elemMaquinaMod = elemMaquinaMod;
    this.elemIpMod = elemIpMod;
  }

  @XmlTransient
  public Long getIdElemento() {
    return idElemento;
  }

  public void setIdElemento(Long idElemento) {
    this.idElemento = idElemento;
  }

  @XmlTransient  
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @XmlTransient  
  public String getSerial() {
    return serial;
  }

  public void setSerial(String serial) {
    this.serial = serial;
  }

  @XmlTransient  
  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  @XmlTransient  
  public Date getFechaExpiracion() {
    return fechaExpiracion;
  }

  public void setFechaExpiracion(Date fechaExpiracion) {
    this.fechaExpiracion = fechaExpiracion;
  }

  @XmlTransient  
  public String getFabricante() {
    return fabricante;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  @XmlTransient  
  public Dominio getTipoInvestigacion() {
    return tipoInvestigacion;
  }

  public void setTipoInvestigacion(Dominio tipoInvestigacion) {
    this.tipoInvestigacion = tipoInvestigacion;
  }

  @XmlTransient  
  public Character getEstado() {
    return estado;
  }

  public void setEstado(Character estado) {
    this.estado = estado;
  }

  @XmlTransient  
  public UnidadDependencia getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }

  @XmlTransient  
  public SieduTipoElemento getTipoElemento() {
    return tipoElemento;
  }

  public void setTipoElemento(SieduTipoElemento tipoElemento) {
    this.tipoElemento = tipoElemento;
  }

  @XmlTransient  
  public SieduSubtipoElemento getSubtipoElemento() {
    return subtipoElemento;
  }

  public void setSubtipoElemento(SieduSubtipoElemento subtipoElemento) {
    this.subtipoElemento = subtipoElemento;
  }
  
  @XmlTransient
  public String getElemUsuCrea() {
    return elemUsuCrea;
  }

  public void setElemUsuCrea(String elemUsuCrea) {
    this.elemUsuCrea = elemUsuCrea;
  
  }
  @XmlTransient
  public Date getElemFechaCrea() {
    return elemFechaCrea;
  }

  public void setElemFechaCrea(Date elemFechaCrea) {
    this.elemFechaCrea = elemFechaCrea;
  }
  
  @XmlTransient
  public String getElemMaquinaCrea() {
    return elemMaquinaCrea;
  }

  public void setElemMaquinaCrea(String elemMaquinaCrea) {
    this.elemMaquinaCrea = elemMaquinaCrea;
  }
  
  @XmlTransient
  public String getElemIpCrea() {
    return elemIpCrea;
  }

  public void setElemIpCrea(String elemIpCrea) {
    this.elemIpCrea = elemIpCrea;
  }
  
  @XmlTransient
  public String getElemUsuMod() {
    return elemUsuMod;
  }

  public void setElemUsuMod(String elemUsuMod) {
    this.elemUsuMod = elemUsuMod;
  }
  
  @XmlTransient
  public Date getElemFechaMod() {
    return elemFechaMod;
  }

  public void setElemFechaMod(Date elemFechaMod) {
    this.elemFechaMod = elemFechaMod;
  }

  @XmlTransient
  public String getElemMaquinaMod() {
    return elemMaquinaMod;
  }

  public void setElemMaquinaMod(String elemMaquinaMod) {
    this.elemMaquinaMod = elemMaquinaMod;
  }

  @XmlTransient
  public String getElemIpMod() {
    return elemIpMod;
  }

  public void setElemIpMod(String elemIpMod) {
    this.elemIpMod = elemIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.idElemento);
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
    final SieduElemento other = (SieduElemento) obj;
    if (!Objects.equals(this.idElemento, other.idElemento)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduElemento{" + "idElemento=" + idElemento + ", descripcion=" + descripcion + ", serial=" + serial + ", valor=" + valor + ", fechaExpiracion=" + fechaExpiracion + ", fabricante=" + fabricante + ", tipoInvestigacion=" + tipoInvestigacion + ", estado=" + estado + ", unidad=" + unidad + ", tipoElemento=" + tipoElemento + ", subtipoElemento=" + subtipoElemento + ", elemUsuCrea=" + elemUsuCrea + ", elemFechaCrea=" + elemFechaCrea + ", elemMaquinaCrea=" + elemMaquinaCrea + ", elemIpCrea=" + elemIpCrea + ", elemUsuMod=" + elemUsuMod + ", elemFechaMod=" + elemFechaMod + ", elemMaquinaMod=" + elemMaquinaMod + ", elemIpMod=" + elemIpMod + '}';
  }

  

}
