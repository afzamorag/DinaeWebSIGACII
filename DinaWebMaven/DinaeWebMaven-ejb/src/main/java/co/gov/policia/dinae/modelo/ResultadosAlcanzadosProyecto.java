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
@Table(name = "RESULTADOS_ALCANZADOS_PROY")
@NamedQueries({
  @NamedQuery(name = "ResultadosAlcanzadosProyecto.findAll", query = "SELECT c FROM ResultadosAlcanzadosProyecto c"),
  @NamedQuery(name = "ResultadosAlcanzadosProyecto.DELETEactividad", query = "DELETE FROM ResultadosAlcanzadosProyecto c WHERE c.idResultadosAlcanzadosProyecto = :idResultadosAlcanzadosProyecto"),
  @NamedQuery(name = "ResultadosAlcanzadosProyectoDTO.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO( c.idResultadosAlcanzadosProyecto, c.resultadoAlcanzado ) FROM ResultadosAlcanzadosProyecto c WHERE c.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ResultadosAlcanzadosProyectoDTO.findAllPorProyectoYcompromisoProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ResultadosAlcanzadosProyectoDTO( c.idResultadosAlcanzadosProyecto, c.resultadoAlcanzado ) FROM ResultadosAlcanzadosProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto"),
  @NamedQuery(name = "ResultadosAlcanzadosProyecto.findAllPorProyectoYcompromisoProyecto", query = "SELECT c FROM ResultadosAlcanzadosProyecto c WHERE c.proyecto.idProyecto = :idProyecto AND c.compromisoProyecto.idCompromisoProyecto = :idCompromisoProyecto")
})
public class ResultadosAlcanzadosProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResultadosAlcanzadosProyecto_seq_gen")
  @SequenceGenerator(name = "ResultadosAlcanzadosProyecto_seq_gen", sequenceName = "SEC_RESULTADOS_ALCANZADOS_PRO", allocationSize = 1)
  @Column(name = "ID_RESULTADOS_ALCANZADOS_PROY")
  private Long idResultadosAlcanzadosProyecto;

  @Size(max = 512)
  @Column(name = "RESULTADO_ALCANZADO")
  private String resultadoAlcanzado;

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

  public ResultadosAlcanzadosProyecto() {
  }

  public ResultadosAlcanzadosProyecto(Long idResultadosAlcanzadosProyecto) {
    this.idResultadosAlcanzadosProyecto = idResultadosAlcanzadosProyecto;
  }

  public Long getIdResultadosAlcanzadosProyecto() {
    return idResultadosAlcanzadosProyecto;
  }

  public void setIdResultadosAlcanzadosProyecto(Long idResultadosAlcanzadosProyecto) {
    this.idResultadosAlcanzadosProyecto = idResultadosAlcanzadosProyecto;
  }

  public String getResultadoAlcanzado() {
    return resultadoAlcanzado;
  }

  public void setResultadoAlcanzado(String resultadoAlcanzado) {
    this.resultadoAlcanzado = resultadoAlcanzado;
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
    hash += (idResultadosAlcanzadosProyecto != null ? idResultadosAlcanzadosProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ResultadosAlcanzadosProyecto)) {
      return false;
    }
    ResultadosAlcanzadosProyecto other = (ResultadosAlcanzadosProyecto) object;
    if ((this.idResultadosAlcanzadosProyecto == null && other.idResultadosAlcanzadosProyecto != null) || (this.idResultadosAlcanzadosProyecto != null && !this.idResultadosAlcanzadosProyecto.equals(other.idResultadosAlcanzadosProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ResultadosAlcanzadosProyecto[ idResultadosAlcanzadosProyecto=" + idResultadosAlcanzadosProyecto + " ]";
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
