package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Édder Peña Barranco
 */
@Entity
@Table(name = "IMPLEMENTACIONES_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "ImplementacionesProyecto.findAll", query = "SELECT i FROM ImplementacionesProyecto i"),
  @NamedQuery(name = "ImplementacionesProyecto.findByIdImplementacionProy", query = "SELECT i FROM ImplementacionesProyecto i WHERE i.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "ImplementacionesProyecto.findProyectoByIdImplementacionProy", query = "SELECT i.proyecto FROM ImplementacionesProyecto i WHERE i.idImplementacionProy = :idImplementacionProy"),
  @NamedQuery(name = "ImplementacionesProyecto.findByIdUnidadPolicial", query = "SELECT i FROM ImplementacionesProyecto i WHERE i.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "ImplementacionesProyecto.findByIdProyecto", query = "SELECT i FROM ImplementacionesProyecto i WHERE i.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ImplementacionesProyectoDTO.findByIdProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ImplementacionesProyectoDTO( i.unidadPolicial.idUnidadPolicial, i.unidadPolicial.nombre, i.unidadPolicial.siglaFisica , i.proyecto.codigoProyecto, i.fechaRegistro ) FROM ImplementacionesProyecto i WHERE i.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "ImplementacionesProyecto.findByIdProyectoAndIdUnidadPolicial", query = "SELECT i FROM ImplementacionesProyecto i WHERE i.proyecto.idProyecto = :idProyecto AND i.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "ImplementacionesProyecto.findAllImplementacionesVigentes", query = "SELECT i FROM ImplementacionesProyecto i WHERE i.estadoImplementacion.idConstantes = :idEstadoImpl AND i.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "ImplentacionProyectoCompromisosDTO.findAllImplementacionesVigentes", query = "SELECT NEW co.gov.policia.dinae.dto.ImplentacionProyectoCompromisosDTO(i.idImplementacionProy, i.unidadPolicial.idUnidadPolicial, i.fechaRegistro, i.usuario, i.usuarioRol.idUsuarioRol, i.proyecto.idProyecto, i.estadoImplementacion.idConstantes, i.maquina, i.proyecto.codigoProyecto, i.proyecto.tituloPropuesto, i.proyecto.fechaActualizacion, i.proyecto.periodo.anio) FROM ImplementacionesProyecto i WHERE i.estadoImplementacion.idConstantes IN :listaIdEstadoImpl AND i.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "ImplementacionesProyecto.findProyectoPorIdImplementgacionProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.proyecto.idProyecto, p.proyecto.unidadPolicial.idUnidadPolicial, p.proyecto.unidadPolicial.nombre, p.proyecto.unidadPolicial.siglaFisica , p.proyecto.tituloPropuesto, p.proyecto.fechaActualizacion, p.proyecto.estado.idConstantes, p.proyecto.estado.valor, p.proyecto.fechaEstimadaInicio, p.proyecto.fechaEstimadaFinalizacion ) FROM ImplementacionesProyecto p WHERE p.idImplementacionProy = :idImplementacionProy")

})
public class ImplementacionesProyecto implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_IMPLEMENTACION_PROY")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ImplementacionProyectoSeqGen")
  @SequenceGenerator(name = "ImplementacionProyectoSeqGen", sequenceName = "SEC_IMPLEMENTACION_PROYECTO", allocationSize = 1)
  private Long idImplementacionProy;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.EAGER)
  private UnidadPolicial unidadPolicial;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO")
  private String usuario;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "implementacionesProyecto")
  private List<PlanTrabajoImplementacion> planTrabajoImplementacionList;

  @JoinColumn(name = "ESTADO_IMPLEMENTACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes estadoImplementacion;

  @Column(name = "MAQUINA")
  private String maquina;

  public ImplementacionesProyecto() {
  }

  public ImplementacionesProyecto(Long idImplementacionProy) {
    this.idImplementacionProy = idImplementacionProy;
  }

  public ImplementacionesProyecto(Long idImplementacionProy, UnidadPolicial unidadPolicial) {
    this.idImplementacionProy = idImplementacionProy;
    this.unidadPolicial = unidadPolicial;
  }

  public Long getIdImplementacionProy() {
    return idImplementacionProy;
  }

  public void setIdImplementacionProy(Long idImplementacionProy) {
    this.idImplementacionProy = idImplementacionProy;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setIdUsuarioRol(UsuarioRol idUsuarioRol) {
    this.usuarioRol = idUsuarioRol;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idImplementacionProy != null ? idImplementacionProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ImplementacionesProyecto)) {
      return false;
    }
    ImplementacionesProyecto other = (ImplementacionesProyecto) object;
    if ((this.idImplementacionProy == null && other.idImplementacionProy != null) || (this.idImplementacionProy != null && !this.idImplementacionProy.equals(other.idImplementacionProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ImplementacionesProyecto[ idImplementacionProy=" + idImplementacionProy + " ]";
  }

  public List<PlanTrabajoImplementacion> getPlanTrabajoImplementacionList() {
    return planTrabajoImplementacionList;
  }

  public void setPlanTrabajoImplementacionList(List<PlanTrabajoImplementacion> planTrabajoImplementacionList) {
    this.planTrabajoImplementacionList = planTrabajoImplementacionList;
  }

  public Constantes getEstadoImplementacion() {
    return estadoImplementacion;
  }

  public void setEstadoImplementacion(Constantes estadoImplementacion) {
    this.estadoImplementacion = estadoImplementacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

}
