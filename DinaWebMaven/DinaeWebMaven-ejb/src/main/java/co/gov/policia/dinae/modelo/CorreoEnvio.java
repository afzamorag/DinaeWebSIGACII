package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "CORREO_ENVIO")
@NamedQueries({
  @NamedQuery(name = "CorreoEnvio.findAll", query = "SELECT c FROM CorreoEnvio c")
})
public class CorreoEnvio implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CorreoEnvio_seq_gen")
  @SequenceGenerator(name = "CorreoEnvio_seq_gen", sequenceName = "SEC_CORREO_ENVIO", allocationSize = 1)
  private Long id;

  @Column(name = "CODIGO")
  private String codigo;

  @Column(name = "ASUNTO")
  private String asunto;

  @Lob()
  @Column(name = "TEXTO")
  private byte[] texto;

  @Lob()
  @Column(name = "DETALLEFALLO")
  private byte[] detalleFallo;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "FECHA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "NOMBRE_ADJUNTO")
  private String nombreAdjunto;

  @Column(name = "CONTENIDO_ADJUNTO")
  private byte[] contenidoAdjunto;

  public CorreoEnvio() {
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (codigo != null ? codigo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CorreoEnvio)) {
      return false;
    }
    CorreoEnvio other = (CorreoEnvio) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public byte[] getTexto() {
    return texto;
  }

  public void setTexto(byte[] texto) {
    this.texto = texto;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public byte[] getDetalleFallo() {
    return detalleFallo;
  }

  public void setDetalleFallo(byte[] detalleFallo) {
    this.detalleFallo = detalleFallo;
  }

  public String getNombreAdjunto() {
    return nombreAdjunto;
  }

  public void setNombreAdjunto(String nombreAdjunto) {
    this.nombreAdjunto = nombreAdjunto;
  }

  public byte[] getContenidoAdjunto() {
    return contenidoAdjunto;
  }

  public void setContenidoAdjunto(byte[] contenidoAdjunto) {
    this.contenidoAdjunto = contenidoAdjunto;
  }
}
