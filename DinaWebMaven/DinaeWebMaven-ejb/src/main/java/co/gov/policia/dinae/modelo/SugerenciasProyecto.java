package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SUGERENCIAS_PROY")
@NamedQueries({
  @NamedQuery(name = "SugerenciasProyecto.findAll", query = "SELECT c FROM SugerenciasProyecto c"),
  @NamedQuery(name = "SugerenciasProyecto.findAllPorProyectoYCompromisoProyecto", query = "SELECT c FROM SugerenciasProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "SugerenciasProyecto.DELETEactividad", query = "DELETE FROM SugerenciasProyecto c WHERE c.idSugerenciasProyecto = :idSugerenciasProyecto"),
  @NamedQuery(name = "SugerenciasProyectoDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.SugerenciasProyectoDTO( c.idSugerenciasProyecto, c.sugerencia ) FROM SugerenciasProyecto c WHERE c.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "SugerenciasProyectoDTO.findAllPorProyectoYcompromisoProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.SugerenciasProyectoDTO( c.idSugerenciasProyecto, c.sugerencia ) FROM SugerenciasProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto")
})
public class SugerenciasProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SugerenciasProyecto_seq_gen")
  @SequenceGenerator(name = "SugerenciasProyecto_seq_gen", sequenceName = "SEC_SUGERENCIAS_PRO", allocationSize = 1)
  @Column(name = "ID_SUGERENCIAS_PROY")
  private Long idSugerenciasProyecto;

  @Size(max = 512)
  @Column(name = "SUGERENCIA")
  private String sugerencia;

  @JoinColumn(name = "ID_COMPROMISO_PROYECTO", referencedColumnName = "ID_COMPROMISO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CompromisoProyecto compromisoProyecto;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Size(max = 100)
  @Column(name = "MAQUINA")
  private String maquina;

  public SugerenciasProyecto() {
  }

  public SugerenciasProyecto(Long idSugerenciasProyecto) {
    this.idSugerenciasProyecto = idSugerenciasProyecto;
  }

  public Long getIdSugerenciasProyecto() {
    return idSugerenciasProyecto;
  }

  public void setIdSugerenciasProyecto(Long idSugerenciasProyecto) {
    this.idSugerenciasProyecto = idSugerenciasProyecto;
  }

  public String getSugerencia() {
    return sugerencia;
  }

  public void setSugerencia(String sugerencia) {
    this.sugerencia = sugerencia;
  }

  public CompromisoProyecto getCompromisoProyecto() {
    return compromisoProyecto;
  }

  public void setCompromisoProyecto(CompromisoProyecto compromisoProyecto) {
    this.compromisoProyecto = compromisoProyecto;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idSugerenciasProyecto != null ? idSugerenciasProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SugerenciasProyecto)) {
      return false;
    }
    SugerenciasProyecto other = (SugerenciasProyecto) object;
    if ((this.idSugerenciasProyecto == null && other.idSugerenciasProyecto != null) || (this.idSugerenciasProyecto != null && !this.idSugerenciasProyecto.equals(other.idSugerenciasProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.SugerenciasProyecto[ idSugerenciasProyecto=" + idSugerenciasProyecto + " ]";
  }

  @Column(name = "CORRECION")
  private Character correccion;

  public Character getCorreccion() {
    return correccion;
  }

  public void setCorreccion(Character correccion) {
    this.correccion = correccion;
  }
}
