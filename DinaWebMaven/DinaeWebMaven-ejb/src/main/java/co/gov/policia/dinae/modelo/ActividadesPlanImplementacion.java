package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "ACTIVIDADES_PLAN_TRABAJO")
@NamedQueries({
  @NamedQuery(name = "ActividadesPlanImplementacion.findAll", query = "SELECT a FROM ActividadesPlanImplementacion a"),
  @NamedQuery(name = "ActividadesPlanImplementacion.findidPlanTrabajoImpl", query = "SELECT a FROM ActividadesPlanImplementacion a WHERE a.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajoImpl"),
  @NamedQuery(name = "ActividadesPlanImplementacion.findidPlanTrabajoImplYestados", query = "SELECT a FROM ActividadesPlanImplementacion a WHERE a.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajoImpl AND a.estado.idConstantes IN :idListaEstado")
})
public class ActividadesPlanImplementacion implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ActividadesPlanImplementacion_seq_gen")
  @SequenceGenerator(name = "ActividadesPlanImplementacion_seq_gen", sequenceName = "SEC_ACTIVIDADES_PLAN_TRABAJO", allocationSize = 1)
  @Column(name = "ID_ACTIV_PLAN_TRABAJO")
  private Long idActividadPlanImplementacion;

  @Column(name = "ACTIVIDAD")
  private String actividad;

  @Column(name = "OBJETIVO")
  private String objetivo;

  @Column(name = "ESTRATEGIA")
  private String estrategia;

  @Column(name = "FECHA_INCIO")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;

  @Column(name = "IMPACTO_INSTITUCIONAL_SOCIAL")
  private String impactoInstitucional;

  @Column(name = "DIFUSION")
  private String difusion;

  @Column(name = "SOPORTE_ENTREGAR")
  private String soporteEntregar;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO_CREACION")
  private String usuarioCreacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estado;

  @JoinColumn(name = "ESTADO_AVANCE_FINAL", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estadoInformeFinal;

  @JoinColumn(name = "ID_PLAN_TRABAJO", referencedColumnName = "ID_PLAN_TRABAJO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private PlanTrabajoImplementacion planTrabajoImplementacion;

  @OneToMany(mappedBy = "actividadesPlanImplementacion", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<ResponsableActividaImplementacion> responsableActividaImplementacionList;

  @Column(name = "PORCENTAJE")
  private BigDecimal porcentaje;

  @Column(name = "PORCENTAJE_AVANCE_FINAL")
  private BigDecimal porcentajeInformeFinal;

  @Transient
  private String descripcionEstado;

  @Transient
  private String descripcionEstadoInformeFinal;

  @Column(name = "ID_PLAN_TRABAJO_ELIMINADO")
  private Long idPlanTrabajoEliminado;

  public ActividadesPlanImplementacion() {
  }

  public ActividadesPlanImplementacion(Long idActividadImplementacion) {
    this.idActividadPlanImplementacion = idActividadImplementacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idActividadPlanImplementacion != null ? idActividadPlanImplementacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ActividadesPlanImplementacion)) {
      return false;
    }
    ActividadesPlanImplementacion other = (ActividadesPlanImplementacion) object;
    if ((this.idActividadPlanImplementacion == null && other.idActividadPlanImplementacion != null) || (this.idActividadPlanImplementacion != null && !this.idActividadPlanImplementacion.equals(other.idActividadPlanImplementacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ActividadesPlanImplementacion[ idActividadPlanImplementacion=" + idActividadPlanImplementacion + " ]";
  }

  public Long getIdActividadPlanImplementacion() {
    return idActividadPlanImplementacion;
  }

  public void setIdActividadPlanImplementacion(Long idActividadPlanImplementacion) {
    this.idActividadPlanImplementacion = idActividadPlanImplementacion;
  }

  public String getActividad() {
    return actividad;
  }

  public void setActividad(String actividad) {
    this.actividad = actividad;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public String getEstrategia() {
    return estrategia;
  }

  public void setEstrategia(String estrategia) {
    this.estrategia = estrategia;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getImpactoInstitucional() {
    return impactoInstitucional;
  }

  public void setImpactoInstitucional(String impactoInstitucional) {
    this.impactoInstitucional = impactoInstitucional;
  }

  public String getDifusion() {
    return difusion;
  }

  public void setDifusion(String difusion) {
    this.difusion = difusion;
  }

  public String getSoporteEntregar() {
    return soporteEntregar;
  }

  public void setSoporteEntregar(String soporteEntregar) {
    this.soporteEntregar = soporteEntregar;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public List<ResponsableActividaImplementacion> getResponsableActividaImplementacionList() {
    return responsableActividaImplementacionList;
  }

  public void setResponsableActividaImplementacionList(List<ResponsableActividaImplementacion> responsableActividaImplementacionList) {
    this.responsableActividaImplementacionList = responsableActividaImplementacionList;
  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Constantes getEstado() {
    return estado;
  }

  public void setEstado(Constantes estado) {
    this.estado = estado;
  }

  public String getDescripcionEstado() {

    descripcionEstado = null;

    if (estado != null) {
      descripcionEstado = estado.getValor();
    }

    return descripcionEstado;
  }

  public BigDecimal getPorcentaje() {
    return porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  @Override
  public String getLlaveModel() {

    return String.valueOf(idActividadPlanImplementacion);

  }

  public Constantes getEstadoInformeFinal() {
    return estadoInformeFinal;
  }

  public void setEstadoInformeFinal(Constantes estadoInformeFinal) {
    this.estadoInformeFinal = estadoInformeFinal;
  }

  public BigDecimal getPorcentajeInformeFinal() {
    return porcentajeInformeFinal;
  }

  public void setPorcentajeInformeFinal(BigDecimal porcentajeInformeFinal) {
    this.porcentajeInformeFinal = porcentajeInformeFinal;
  }

  public String getDescripcionEstadoInformeFinal() {
    descripcionEstadoInformeFinal = null;

    if (estadoInformeFinal != null) {
      descripcionEstadoInformeFinal = estadoInformeFinal.getValor();
    }

    return descripcionEstadoInformeFinal;
  }

  public Long getIdPlanTrabajoEliminado() {
    return idPlanTrabajoEliminado;
  }

  public void setIdPlanTrabajoEliminado(Long idPlanTrabajoEliminado) {
    this.idPlanTrabajoEliminado = idPlanTrabajoEliminado;
  }

}
