package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "RESULTADOS_INVESTIGACION")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "ResultadosInvestigacion.findAll", query = "SELECT r FROM ResultadosInvestigacion r"),
  @NamedQuery(name = "ResultadosInvestigacion.findByIdResultadoInvestigacion", query = "SELECT r FROM ResultadosInvestigacion r WHERE r.idResultadoInvestigacion = :idResultadoInvestigacion"),
  @NamedQuery(name = "ResultadosInvestigacion.findByDescripcion", query = "SELECT r FROM ResultadosInvestigacion r WHERE r.descripcion = :descripcion"),
  @NamedQuery(name = "ResultadosInvestigacion.findByFechaRegistro", query = "SELECT r FROM ResultadosInvestigacion r WHERE r.fechaRegistro = :fechaRegistro"),
  @NamedQuery(name = "ResultadosInvestigacion.findByInformeAvanceProyecto", query = "SELECT r FROM ResultadosInvestigacion r WHERE r.idInformeAvance.idInformeAvance = :idInformeAvance AND r.idProyecto.idProyecto = :idProyecto")})
public class ResultadosInvestigacion implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResultadosInvestigacion_seq_gen")
  @SequenceGenerator(name = "ResultadosInvestigacion_seq_gen", sequenceName = "SEC_RESULTADOS_INVESTIGACION", allocationSize = 1)
  @Column(name = "ID_RESULTADO_INVESTIGACION")
  private Long idResultadoInvestigacion;

  @Size(max = 512)
  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Basic(optional = false)
  @NotNull
  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false)
  private UsuarioRol idUsuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto idProyecto;

  @JoinColumn(name = "ID_INFORME_AVANCE", referencedColumnName = "ID_INFORME_AVANCE")
  @ManyToOne
  private InformeAvance idInformeAvance;

  @JoinColumn(name = "ID_TIPO_PRODUCTO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes idTipoProducto;

  public ResultadosInvestigacion() {
  }

  public ResultadosInvestigacion(Long idResultadoInvestigacion) {
    this.idResultadoInvestigacion = idResultadoInvestigacion;
  }

  public ResultadosInvestigacion(Long idResultadoInvestigacion, Date fechaRegistro) {
    this.idResultadoInvestigacion = idResultadoInvestigacion;
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdResultadoInvestigacion() {
    return idResultadoInvestigacion;
  }

  public void setIdResultadoInvestigacion(Long idResultadoInvestigacion) {
    this.idResultadoInvestigacion = idResultadoInvestigacion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(UsuarioRol idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Proyecto getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Proyecto idProyecto) {
    this.idProyecto = idProyecto;
  }

  public InformeAvance getIdInformeAvance() {
    return idInformeAvance;
  }

  public void setIdInformeAvance(InformeAvance idInformeAvance) {
    this.idInformeAvance = idInformeAvance;
  }

  public Constantes getIdTipoProducto() {
    return idTipoProducto;
  }

  public void setIdTipoProducto(Constantes idTipoProducto) {
    this.idTipoProducto = idTipoProducto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idResultadoInvestigacion != null ? idResultadoInvestigacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ResultadosInvestigacion)) {
      return false;
    }
    ResultadosInvestigacion other = (ResultadosInvestigacion) object;
    if ((this.idResultadoInvestigacion == null && other.idResultadoInvestigacion != null) || (this.idResultadoInvestigacion != null && !this.idResultadoInvestigacion.equals(other.idResultadoInvestigacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ResultadosInvestigacion[ idResultadoInvestigacion=" + idResultadoInvestigacion + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (idResultadoInvestigacion == null) {
      return null;
    }
    return idResultadoInvestigacion.toString();
  }

}
