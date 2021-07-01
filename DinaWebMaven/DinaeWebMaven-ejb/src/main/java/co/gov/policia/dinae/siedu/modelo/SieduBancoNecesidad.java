package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import java.io.Serializable;
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
import javax.persistence.Transient;
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
@Table(name = "SIEDU_BANCO_NECESIDAD")
@NamedQueries({
  @NamedQuery(name = "SieduBancoNecesidad.findAll", query = "SELECT s FROM SieduBancoNecesidad s")})
@Cacheable(false)
@XmlRootElement
public class SieduBancoNecesidad implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SieduBancoNecesidad_seq_gen")
  @SequenceGenerator(name = "SieduBancoNecesidad_seq_gen", sequenceName = "siedu_banco_necesidad_seq", allocationSize = 1)
  @Column(name = "BNE_BNE")
  private Long identificadorBancoNecesidad;

  @JoinColumns({
    @JoinColumn(name = "BNE_ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadPolicial unidad;
  
  @JoinColumn(name = "BNE_ID_LINEA", referencedColumnName = "ID_LINEA")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Linea linea;  
  
  @Basic(optional = true)
  @Column(name = "BNE_FECHA_PROPUESTA", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaPropuesta;
  
  @Basic(optional = false)
  @Size(min = 1, max = 600)
  @Column(name = "BNE_TEMA", nullable = false, length = 600)
  private String tema;
  
  @Basic(optional = false)
  @Size(min = 1, max = 200)
  @Column(name = "BNE_TITULO", nullable = false, length = 200)
  private String titulo;  
  
  @Basic(optional = false)
  @Size(min = 1, max = 1000)
  @Column(name = "BNE_RESULTADO_ESPERADO", nullable = false, length = 1000)
  private String resultadoEsperado;   
  
  @Basic(optional = false)
  @Size(min = 1, max = 1000)
  @Column(name = "BNE_FUENTE_INFORMACION", nullable = false, length = 1000)
  private String fuenteInformacion; 

  @Basic(optional = false)
  @Size(min = 1, max = 1000)
  @Column(name = "BNE_POSIBLE_ACTIVIDAD", nullable = false, length = 1000)
  private String posibleActividad; 

  @Basic(optional = false)
  @Size(min = 1, max = 100)
  @Column(name = "BNE_NOMBRE_ARCHIVO", nullable = false, length = 100)
  private String nombreArchivo;   

  @Basic(optional = false)
  @Size(min = 1, max = 100)
  @Column(name = "BNE_NOMBRE_ARCHIVO_FISICO", nullable = false, length = 100)
  private String nombreArchivoFisico;
  
  @Basic(optional = false)
  @Column(name = "BNE_ESTADO")
  private Character estado;     

  @Size(max = 30)
  @Column(name = "BNE_USU_CREA", length = 30)
  private String bneUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "BNE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date bneFechaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "BNE_MAQUINA_CREA", nullable = false, length = 100)
  private String bneMaquinaCrea;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "BNE_IP_CREA", nullable = false, length = 100)
  private String bneIpCrea;
  
  @Size(max = 30)
  @Column(name = "BNE_USU_MOD", length = 30)
  private String bneUsuMod;
  
  @Column(name = "BNE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date bneFechaMod;
  
  @Size(max = 100)
  @Column(name = "BNE_MAQUINA_MOD", length = 100)
  private String bneMaquinaMod;
  
  @Size(max = 100)
  @Column(name = "BNE_IP_MOD", length = 100)
  private String bneIpMod;
  
  @Transient
  private Integer vigencia;

  public SieduBancoNecesidad() {
  }

  public SieduBancoNecesidad(Long identificadorBancoNecesidad, UnidadPolicial unidad, Linea linea, Date fechaPropuesta, String tema, String titulo, String resultadoEsperado, String fuenteInformacion, String posibleActividad, String nombreArchivo, String nombreArchivoFisico, Character estado, String bneUsuCrea, Date bneFechaCrea, String bneMaquinaCrea, String bneIpCrea, String bneUsuMod, Date bneFechaMod, String bneMaquinaMod, String bneIpMod) {
    this.identificadorBancoNecesidad = identificadorBancoNecesidad;
    this.unidad = unidad;
    this.linea = linea;
    this.fechaPropuesta = fechaPropuesta;
    this.tema = tema;
    this.titulo = titulo;
    this.resultadoEsperado = resultadoEsperado;
    this.fuenteInformacion = fuenteInformacion;
    this.posibleActividad = posibleActividad;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.estado = estado;
    this.bneUsuCrea = bneUsuCrea;
    this.bneFechaCrea = bneFechaCrea;
    this.bneMaquinaCrea = bneMaquinaCrea;
    this.bneIpCrea = bneIpCrea;
    this.bneUsuMod = bneUsuMod;
    this.bneFechaMod = bneFechaMod;
    this.bneMaquinaMod = bneMaquinaMod;
    this.bneIpMod = bneIpMod;
  }

  @XmlTransient
  public Long getIdentificadorBancoNecesidad() {
    return identificadorBancoNecesidad;
  }

  public void setIdentificadorBancoNecesidad(Long identificadorBancoNecesidad) {
    this.identificadorBancoNecesidad = identificadorBancoNecesidad;
  }

  @XmlTransient
  public UnidadPolicial getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadPolicial unidad) {
    this.unidad = unidad;
  }

  @XmlTransient
  public Linea getLinea() {
    return linea;
  }

  public void setLinea(Linea linea) {
    this.linea = linea;
  }

  @XmlTransient
  public Date getFechaPropuesta() {
    return fechaPropuesta;
  }

  public void setFechaPropuesta(Date fechaPropuesta) {
    this.fechaPropuesta = fechaPropuesta;
  }

  @XmlTransient
  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  @XmlTransient
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  @XmlTransient
  public String getResultadoEsperado() {
    return resultadoEsperado;
  }

  public void setResultadoEsperado(String resultadoEsperado) {
    this.resultadoEsperado = resultadoEsperado;
  }

  @XmlTransient
  public String getFuenteInformacion() {
    return fuenteInformacion;
  }

  public void setFuenteInformacion(String fuenteInformacion) {
    this.fuenteInformacion = fuenteInformacion;
  }

  @XmlTransient
  public String getPosibleActividad() {
    return posibleActividad;
  }

  public void setPosibleActividad(String posibleActividad) {
    this.posibleActividad = posibleActividad;
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
  public Character getEstado() {
    return estado;
  }

  @Transient
  public String getEstadoTexto() {
    return estado==null?"":estado=='P'?"Pendiente":estado=='A'?"Aprobado":estado=='S'?"Asignada":"No aprobado";
  }
  
  public void setEstado(Character estado) {
    this.estado = estado;
  }

  @XmlTransient
  public Date getBneFechaCrea() {
    return bneFechaCrea;
  }

  public void setBneFechaCrea(Date bneFechaCrea) {
    this.bneFechaCrea = bneFechaCrea;
  }

  @XmlTransient
  public String getBneMaquinaCrea() {
    return bneMaquinaCrea;
  }

  public void setBneMaquinaCrea(String bneMaquinaCrea) {
    this.bneMaquinaCrea = bneMaquinaCrea;
  }

  @XmlTransient
  public String getBneIpCrea() {
    return bneIpCrea;
  }

  public void setBneIpCrea(String bneIpCrea) {
    this.bneIpCrea = bneIpCrea;
  }

  @XmlTransient
  public String getBneUsuMod() {
    return bneUsuMod;
  }

  public void setBneUsuMod(String bneUsuMod) {
    this.bneUsuMod = bneUsuMod;
  }

  @XmlTransient
  public Date getBneFechaMod() {
    return bneFechaMod;
  }

  public void setBneFechaMod(Date bneFechaMod) {
    this.bneFechaMod = bneFechaMod;
  }

  @XmlTransient
  public String getBneMaquinaMod() {
    return bneMaquinaMod;
  }

  public void setBneMaquinaMod(String bneMaquinaMod) {
    this.bneMaquinaMod = bneMaquinaMod;
  }

  @XmlTransient
  public String getBneIpMod() {
    return bneIpMod;
  }

  public void setBneIpMod(String bneIpMod) {
    this.bneIpMod = bneIpMod;
  }

  @XmlTransient
  public String getBneUsuCrea() {
    return bneUsuCrea;
  }

  public void setBneUsuCrea(String bneUsuCrea) {
    this.bneUsuCrea = bneUsuCrea;
  }

  @Override
  public String toString() {
    return "SieduBancoNecesidad{" + "identificadorBancoNecesidad=" + identificadorBancoNecesidad + ", unidad=" + unidad + ", linea=" + linea + ", fechaPropuesta=" + fechaPropuesta + ", tema=" + tema + ", titulo=" + titulo + ", resultadoEsperado=" + resultadoEsperado + ", fuenteInformacion=" + fuenteInformacion + ", posibleActividad=" + posibleActividad + ", nombreArchivo=" + nombreArchivo + ", nombreArchivoFISICO=" + nombreArchivoFisico + ", estado=" + estado + ", bneUsuCrea=" + bneUsuCrea + ", bneFechaCrea=" + bneFechaCrea + ", bneMaquinaCrea=" + bneMaquinaCrea + ", bneIpCrea=" + bneIpCrea + ", bneUsuMod=" + bneUsuMod + ", bneFechaMod=" + bneFechaMod + ", bneMaquinaMod=" + bneMaquinaMod + ", bneIpMod=" + bneIpMod + '}';
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.identificadorBancoNecesidad);
    hash = 29 * hash + Objects.hashCode(this.unidad);
    hash = 29 * hash + Objects.hashCode(this.linea);
    hash = 29 * hash + Objects.hashCode(this.fechaPropuesta);
    hash = 29 * hash + Objects.hashCode(this.tema);
    hash = 29 * hash + Objects.hashCode(this.titulo);
    hash = 29 * hash + Objects.hashCode(this.resultadoEsperado);
    hash = 29 * hash + Objects.hashCode(this.fuenteInformacion);
    hash = 29 * hash + Objects.hashCode(this.posibleActividad);
    hash = 29 * hash + Objects.hashCode(this.nombreArchivo);
    hash = 29 * hash + Objects.hashCode(this.nombreArchivoFisico);
    hash = 29 * hash + Objects.hashCode(this.estado);
    hash = 29 * hash + Objects.hashCode(this.bneUsuCrea);
    hash = 29 * hash + Objects.hashCode(this.bneFechaCrea);
    hash = 29 * hash + Objects.hashCode(this.bneMaquinaCrea);
    hash = 29 * hash + Objects.hashCode(this.bneIpCrea);
    hash = 29 * hash + Objects.hashCode(this.bneUsuMod);
    hash = 29 * hash + Objects.hashCode(this.bneFechaMod);
    hash = 29 * hash + Objects.hashCode(this.bneMaquinaMod);
    hash = 29 * hash + Objects.hashCode(this.bneIpMod);
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
    final SieduBancoNecesidad other = (SieduBancoNecesidad) obj;
    if (!Objects.equals(this.tema, other.tema)) {
      return false;
    }
    if (!Objects.equals(this.titulo, other.titulo)) {
      return false;
    }
    if (!Objects.equals(this.resultadoEsperado, other.resultadoEsperado)) {
      return false;
    }
    if (!Objects.equals(this.fuenteInformacion, other.fuenteInformacion)) {
      return false;
    }
    if (!Objects.equals(this.posibleActividad, other.posibleActividad)) {
      return false;
    }
    if (!Objects.equals(this.nombreArchivo, other.nombreArchivo)) {
      return false;
    }
    if (!Objects.equals(this.nombreArchivoFisico, other.nombreArchivoFisico)) {
      return false;
    }
    if (!Objects.equals(this.bneUsuCrea, other.bneUsuCrea)) {
      return false;
    }
    if (!Objects.equals(this.bneMaquinaCrea, other.bneMaquinaCrea)) {
      return false;
    }
    if (!Objects.equals(this.bneIpCrea, other.bneIpCrea)) {
      return false;
    }
    if (!Objects.equals(this.bneUsuMod, other.bneUsuMod)) {
      return false;
    }
    if (!Objects.equals(this.bneMaquinaMod, other.bneMaquinaMod)) {
      return false;
    }
    if (!Objects.equals(this.bneIpMod, other.bneIpMod)) {
      return false;
    }
    if (!Objects.equals(this.identificadorBancoNecesidad, other.identificadorBancoNecesidad)) {
      return false;
    }
    if (!Objects.equals(this.unidad, other.unidad)) {
      return false;
    }
    if (!Objects.equals(this.linea, other.linea)) {
      return false;
    }
    if (!Objects.equals(this.fechaPropuesta, other.fechaPropuesta)) {
      return false;
    }
    if (!Objects.equals(this.estado, other.estado)) {
      return false;
    }
    if (!Objects.equals(this.bneFechaCrea, other.bneFechaCrea)) {
      return false;
    }
    if (!Objects.equals(this.bneFechaMod, other.bneFechaMod)) {
      return false;
    }
    return true;
  }

    public Integer getVigencia() {
        if (this.fechaPropuesta!=null)
            return this.fechaPropuesta.getYear()+1900;
        return null;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

  

}
