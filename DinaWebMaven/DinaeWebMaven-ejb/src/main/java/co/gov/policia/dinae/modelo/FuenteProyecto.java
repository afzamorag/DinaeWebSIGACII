package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IDataModel;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "FUENTE_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "FuenteProyecto.findAll", query = "SELECT f FROM FuenteProyecto f"),
  @NamedQuery(name = "FuenteProyecto.findFuentesByProyecto", query = "SELECT f FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto ORDER BY f.idFuenteProyecto"),
  @NamedQuery(name = "FuenteProyecto.findFuentesByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.FuenteProyectoDTO(f.idFuenteProyecto, f.nombreFuente, f.fuenteBase, f.usuarioRol.idUsuarioRol, f.usuarioRol.identificadorUsuario, f.usuarioRol.rol.idRol, f.proyecto.idProyecto, f.proyecto.codigoProyecto, f.proyecto.tituloPropuesto, f.tipoFuente.idConstantes, f.tipoFuente.valor, f.fechaRegistro) FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto ORDER BY f.idFuenteProyecto"),
  @NamedQuery(name = "FuenteProyecto.countFuentesBaseByProyecto", query = "SELECT COUNT( f ) FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto AND f.fuenteBase = 'Y'"),
  @NamedQuery(name = "FuenteProyecto.countFuentesNOBaseByProyecto", query = "SELECT COUNT( f ) FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto AND f.fuenteBase = 'N'"),
  @NamedQuery(name = "FuenteProyecto.findNombresFuentesNOBaseByProyecto", query = "SELECT f.nombreFuente FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto AND f.fuenteBase = 'N' ORDER BY f.idFuenteProyecto ASC"),
  @NamedQuery(name = "FuenteProyecto.countFuentesBaseByProyectoAndUnidad", query = "SELECT COUNT( f ) FROM FuenteProyecto f WHERE f.proyecto.idProyecto = :idProyecto AND f.fuenteBase = 'Y' AND f.nombreFuente IN (SELECT e.unidadPolicial.nombre FROM EjecutorNecesidad e where e.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND e.proyecto.idProyecto = :idProyecto)"),
  @NamedQuery(name = "FuenteProyectoDTO.findFuentesByPlanTrabajoImpl", query = "SELECT NEW co.gov.policia.dinae.dto.FuenteProyectoDTO(f.idFuenteProyecto, f.nombreFuente, f.fuenteBase, f.usuarioRol.idUsuarioRol, f.usuarioRol.identificadorUsuario, f.usuarioRol.rol.idRol, f.planTrabajoImplementacion.idPlanTrabajo, f.planTrabajoImplementacion.implementacionesProyecto.idImplementacionProy, f.planTrabajoImplementacion.implementacionesProyecto.proyecto.idProyecto, f.planTrabajoImplementacion.implementacionesProyecto.proyecto.codigoProyecto, f.planTrabajoImplementacion.implementacionesProyecto.proyecto.tituloPropuesto, f.tipoFuente.idConstantes, f.tipoFuente.valor, f.fechaRegistro) FROM FuenteProyecto f WHERE f.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo ORDER BY f.idFuenteProyecto"),
  @NamedQuery(name = "FuenteProyecto.findFuentesByPlanTrabajo", query = "SELECT f FROM FuenteProyecto f WHERE f.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo ORDER BY f.idFuenteProyecto")
})
public class FuenteProyecto implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FuenteProyecto_seq_gen")
  @SequenceGenerator(name = "FuenteProyecto_seq_gen", sequenceName = "SEC_FUENTE_PROYECTO", allocationSize = 1)
  @Column(name = "ID_FUENTE_PROYECTO")
  private Long idFuenteProyecto;

  @Size(min = 1, max = 100)
  @Column(name = "NOMBRE_FUENTE")
  private String nombreFuente;

  @Column(name = "FUENTE_BASE")
  private Character fuenteBase;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_TIPO_FUENTE", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoFuente;

  /*@Column(name = "ID_TIPO_FUENTE")
    private Long tipoFuente;*/
  @JoinColumn(name = "ID_PLAN_TRABAJO", referencedColumnName = "ID_PLAN_TRABAJO")
  @ManyToOne(optional = false)
  private PlanTrabajoImplementacion planTrabajoImplementacion;

  @Column(name = "USUARIO_REGISTRA")
  private String usuarioRegistra;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fuenteProyecto")
  private List<EquiposInvestigacion> equiposInvestigacionList;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fuenteProyecto")
  private List<EventoProyecto> eventoProyectoList;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fuenteProyecto")
  private List<ViajesProyecto> viajesProyectoList;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fuenteProyecto")
  private List<OtrosGastosProyecto> otrosGastosProyectoList;

  @Transient
  private boolean muestraLink;

  public FuenteProyecto() {
  }

  public FuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public List<EquiposInvestigacion> getEquiposInvestigacionList() {
    return equiposInvestigacionList;
  }

  public void setEquiposInvestigacionList(List<EquiposInvestigacion> equiposInvestigacionList) {
    this.equiposInvestigacionList = equiposInvestigacionList;
  }

  public List<EventoProyecto> getEventoProyectoList() {
    return eventoProyectoList;
  }

  public void setEventoProyectoList(List<EventoProyecto> eventoProyectoList) {
    this.eventoProyectoList = eventoProyectoList;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  /*
    public List<InvestigadoresProyecto> getInvestigadoresProyectoList() {
        return investigadoresProyectoList;
    }

    public void setInvestigadoresProyectoList(List<InvestigadoresProyecto> investigadoresProyectoList) {
        this.investigadoresProyectoList = investigadoresProyectoList;
    }
   */
  public List<ViajesProyecto> getViajesProyectoList() {
    return viajesProyectoList;
  }

  public void setViajesProyectoList(List<ViajesProyecto> viajesProyectoList) {
    this.viajesProyectoList = viajesProyectoList;
  }

  public List<OtrosGastosProyecto> getOtrosGastosProyectoList() {
    return otrosGastosProyectoList;
  }

  public void setOtrosGastosProyectoList(List<OtrosGastosProyecto> otrosGastosProyectoList) {
    this.otrosGastosProyectoList = otrosGastosProyectoList;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  /*public Long getTipoFuente() {
        return tipoFuente;
    }

    public void setTipoFuente(Long tipoFuente) {
        this.tipoFuente = tipoFuente;
    }*/
  public Constantes getTipoFuente() {
    return tipoFuente;
  }

  public void setTipoFuente(Constantes tipoFuente) {
    this.tipoFuente = tipoFuente;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFuenteProyecto != null ? idFuenteProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FuenteProyecto)) {
      return false;
    }
    FuenteProyecto other = (FuenteProyecto) object;
    if ((this.idFuenteProyecto == null && other.idFuenteProyecto != null) || (this.idFuenteProyecto != null && !this.idFuenteProyecto.equals(other.idFuenteProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.FuenteProyecto[ idFuenteProyecto=" + idFuenteProyecto + " ]";
  }

  public String getNombreFuente() {
    return nombreFuente;
  }

  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  public Character getFuenteBase() {
    return fuenteBase;
  }

  public void setFuenteBase(Character fuenteBase) {
    this.fuenteBase = fuenteBase;
  }

  public boolean isMuestraLink() {
    if (tipoFuente != null) {
      muestraLink = (tipoFuente.getIdConstantes().compareTo(IConstantes.ID_TIPO_FUENTE_FINANCIERA_EXTERNA) == 0
              || (tipoFuente.getIdConstantes().compareTo(IConstantes.ID_TIPO_FUENTE_FINANCIERA_INTERNA) == 0 && fuenteBase.compareTo('N') == 0));
    }
    return muestraLink;
  }

  public void setEsExterna(boolean esExterna) {
    this.muestraLink = esExterna;
  }

  @Override
  public String getLlaveModel() {
    if (idFuenteProyecto == null) {
      return null;
    }

    return idFuenteProyecto.toString();
  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

}
