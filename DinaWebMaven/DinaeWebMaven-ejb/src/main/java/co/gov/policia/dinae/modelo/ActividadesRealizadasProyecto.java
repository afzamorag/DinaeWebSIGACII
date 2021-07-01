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
@Table(name = "ACTIVIDADES_REALIZADAS_PROY")
@NamedQueries({
  @NamedQuery(name = "ActividadesRealizadasProyecto.findAll", query = "SELECT c FROM ActividadesRealizadasProyecto c"),
  @NamedQuery(name = "ActividadesRealizadasProyecto.DELETEactividad", query = "DELETE FROM ActividadesRealizadasProyecto c WHERE c.idActividadesRealizadasProyecto = :idActividadesRealizadasProyecto"),
  @NamedQuery(name = "ActividadesRealizadasProyectoDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO( c.idActividadesRealizadasProyecto, c.descripcionActividad ) FROM ActividadesRealizadasProyecto c WHERE c.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ActividadesRealizadasProyectoDTO.findAllPorProyectoYcompromisoProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ActividadesRealizadasProyectoDTO( c.idActividadesRealizadasProyecto, c.descripcionActividad ) FROM ActividadesRealizadasProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "ActividadesRealizadasProyecto.findAllPorProyectoYcompromisoProyecto", query = "SELECT c FROM ActividadesRealizadasProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto")
})
public class ActividadesRealizadasProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ActividadesRealizadasProyecto_seq_gen")
  @SequenceGenerator(name = "ActividadesRealizadasProyecto_seq_gen", sequenceName = "SEC_ACTIVIDADES_REALIZADAS_PRO", allocationSize = 1)
  @Column(name = "ID_ACTIVIDAD_REALIZADA_PROY")
  private Long idActividadesRealizadasProyecto;

  @Size(max = 512)
  @Column(name = "DESCRIPCION_ACTIVIDAD")
  private String descripcionActividad;

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

  public ActividadesRealizadasProyecto() {
  }

  public ActividadesRealizadasProyecto(Long idActividadesRealizadasProyecto) {
    this.idActividadesRealizadasProyecto = idActividadesRealizadasProyecto;
  }

  public Long getIdActividadesRealizadasProyecto() {
    return idActividadesRealizadasProyecto;
  }

  public void setIdActividadesRealizadasProyecto(Long idActividadesRealizadasProyecto) {
    this.idActividadesRealizadasProyecto = idActividadesRealizadasProyecto;
  }

  public String getDescripcionActividad() {
    return descripcionActividad;
  }

  public void setDescripcionActividad(String descripcionActividad) {
    this.descripcionActividad = descripcionActividad;
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
    hash += (idActividadesRealizadasProyecto != null ? idActividadesRealizadasProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ActividadesRealizadasProyecto)) {
      return false;
    }
    ActividadesRealizadasProyecto other = (ActividadesRealizadasProyecto) object;
    if ((this.idActividadesRealizadasProyecto == null && other.idActividadesRealizadasProyecto != null) || (this.idActividadesRealizadasProyecto != null && !this.idActividadesRealizadasProyecto.equals(other.idActividadesRealizadasProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ActividadesRealizadasProyecto[ idActividadesRealizadasProyecto=" + idActividadesRealizadasProyecto + " ]";
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
