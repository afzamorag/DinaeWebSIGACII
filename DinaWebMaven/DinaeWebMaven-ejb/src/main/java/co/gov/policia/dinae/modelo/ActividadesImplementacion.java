package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "ACTIVIDADES_IMPLEMENTACION")
@NamedQueries({
  @NamedQuery(name = "ActividadesImplementacion.findAll", query = "SELECT a FROM ActividadesImplementacion a"),
  @NamedQuery(name = "ActividadesImplementacion.findPorInformeAvanceImplYActividadPlanTrabajo", query = "SELECT a FROM ActividadesImplementacion a WHERE a.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND a.actividadesPlanImplementacion.idActividadPlanImplementacion = :idActividadPlanImplementacion"),
  @NamedQuery(name = "ActividadesImplementacion.findPorInformeAvanceImplYestados", query = "SELECT a FROM ActividadesImplementacion a WHERE a.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion AND a.estadoActividad.idConstantes IN :idListaEstado"),
  @NamedQuery(name = "ActividadesImplementacion.findPorInformeAvanceImpl", query = "SELECT a FROM ActividadesImplementacion a WHERE a.informeAvanceImplementacion.idInformeAvanceImplementacion = :idInformeAvanceImplementacion")
})
public class ActividadesImplementacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ActividadesImplementacion_seq_gen")
  @SequenceGenerator(name = "ActividadesImplementacion_seq_gen", sequenceName = "SEC_ACTIVIDAD_IMPLEMENTACION", allocationSize = 1)
  @Column(name = "ID_ACTIVIDAD_IMPLEMENTACION")
  private Long idActividadImplementacion;

  @JoinColumn(name = "ID_INFORME_AVANCE_IMPL", referencedColumnName = "ID_INFORME_AVANCE_IMPL")
  @ManyToOne(fetch = FetchType.LAZY)
  private InformeAvanceImplementacion informeAvanceImplementacion;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_ACTIVIDAD_PLAN_TRABAJO", referencedColumnName = "ID_ACTIV_PLAN_TRABAJO")
  @ManyToOne(fetch = FetchType.LAZY)
  private ActividadesPlanImplementacion actividadesPlanImplementacion;

  @JoinColumn(name = "ID_ESTADO_ACTIVIDAD", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estadoActividad;

  @Column(name = "RESULTADO_ACTIVIDAD")
  private String resultadoActividad;

  @Column(name = "FECHA_REAL_INICIO")
  @Temporal(TemporalType.DATE)
  private Date fechaInicioReal;

  @Column(name = "FECHA_REAL_FIN")
  @Temporal(TemporalType.DATE)
  private Date fechaFinReal;

  @Column(name = "ARCHIVO_SOPORTE")
  private String archivoSoporte;

  @Column(name = "ARCHIVO_SOPORTE_FISICO")
  private String archivoSoporteFisico;

  @Column(name = "EVIDENCIA_FOTOGRAFICA")
  private String evidenciaFotografica;

  @Column(name = "EVIDENCIA_FOTOGRAFICA_FISICO")
  private String evidenciaFotograficaFisico;

  @Column(name = "PORC_CUMPLIMIENTO_PARCIAL")
  private BigDecimal porcentajeCumplimiento;

  @Column(name = "NUEVA_FECHA_FINALIZACION")
  @Temporal(TemporalType.DATE)
  private Date fechaNuevaFinalizacion;

  @Column(name = "JUSTIFICACION_PARCIAL")
  private String justificacionParcial;

  @Column(name = "ACCIONES_ADEL_LOGRO")
  private String accionesLogro;

  @Column(name = "NUEVA_FECHA_INICIO")
  @Temporal(TemporalType.DATE)
  private Date nuevaFechaInicio;

  @Column(name = "NUEVA_FECHA_FIN")
  @Temporal(TemporalType.DATE)
  private Date nuevaFechaFin;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  public ActividadesImplementacion() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idActividadImplementacion != null ? idActividadImplementacion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ActividadesImplementacion)) {
      return false;
    }
    ActividadesImplementacion other = (ActividadesImplementacion) object;
    if ((this.idActividadImplementacion == null && other.idActividadImplementacion != null) || (this.idActividadImplementacion != null && !this.idActividadImplementacion.equals(other.idActividadImplementacion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.ActividadesImplementacion[ idActividadImplementacion=" + idActividadImplementacion + " ]";
  }

  public Long getIdActividadImplementacion() {
    return idActividadImplementacion;
  }

  public void setIdActividadImplementacion(Long idActividadImplementacion) {
    this.idActividadImplementacion = idActividadImplementacion;
  }

  public InformeAvanceImplementacion getInformeAvanceImplementacion() {
    return informeAvanceImplementacion;
  }

  public void setInformeAvanceImplementacion(InformeAvanceImplementacion informeAvanceImplementacion) {
    this.informeAvanceImplementacion = informeAvanceImplementacion;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public ActividadesPlanImplementacion getActividadesPlanImplementacion() {
    return actividadesPlanImplementacion;
  }

  public void setActividadesPlanImplementacion(ActividadesPlanImplementacion actividadesPlanImplementacion) {
    this.actividadesPlanImplementacion = actividadesPlanImplementacion;
  }

  public Constantes getEstadoActividad() {
    return estadoActividad;
  }

  public void setEstadoActividad(Constantes estadoActividad) {
    this.estadoActividad = estadoActividad;
  }

  public String getResultadoActividad() {
    return resultadoActividad;
  }

  public void setResultadoActividad(String resultadoActividad) {
    this.resultadoActividad = resultadoActividad;
  }

  public Date getFechaInicioReal() {
    return fechaInicioReal;
  }

  public void setFechaInicioReal(Date fechaInicioReal) {
    this.fechaInicioReal = fechaInicioReal;
  }

  public Date getFechaFinReal() {
    return fechaFinReal;
  }

  public void setFechaFinReal(Date fechaFinReal) {
    this.fechaFinReal = fechaFinReal;
  }

  public String getArchivoSoporte() {
    return archivoSoporte;
  }

  public void setArchivoSoporte(String archivoSoporte) {
    this.archivoSoporte = archivoSoporte;
  }

  public String getArchivoSoporteFisico() {
    return archivoSoporteFisico;
  }

  public void setArchivoSoporteFisico(String archivoSoporteFisico) {
    this.archivoSoporteFisico = archivoSoporteFisico;
  }

  public String getEvidenciaFotografica() {
    return evidenciaFotografica;
  }

  public void setEvidenciaFotografica(String evidenciaFotografica) {
    this.evidenciaFotografica = evidenciaFotografica;
  }

  public String getEvidenciaFotograficaFisico() {
    return evidenciaFotograficaFisico;
  }

  public void setEvidenciaFotograficaFisico(String evidenciaFotograficaFisico) {
    this.evidenciaFotograficaFisico = evidenciaFotograficaFisico;
  }

  public BigDecimal getPorcentajeCumplimiento() {
    return porcentajeCumplimiento;
  }

  public void setPorcentajeCumplimiento(BigDecimal porcentajeCumplimiento) {
    this.porcentajeCumplimiento = porcentajeCumplimiento;
  }

  public Date getFechaNuevaFinalizacion() {
    return fechaNuevaFinalizacion;
  }

  public void setFechaNuevaFinalizacion(Date fechaNuevaFinalizacion) {
    this.fechaNuevaFinalizacion = fechaNuevaFinalizacion;
  }

  public String getJustificacionParcial() {
    return justificacionParcial;
  }

  public void setJustificacionParcial(String justificacionParcial) {
    this.justificacionParcial = justificacionParcial;
  }

  public String getAccionesLogro() {
    return accionesLogro;
  }

  public void setAccionesLogro(String accionesLogro) {
    this.accionesLogro = accionesLogro;
  }

  public Date getNuevaFechaInicio() {
    return nuevaFechaInicio;
  }

  public void setNuevaFechaInicio(Date nuevaFechaInicio) {
    this.nuevaFechaInicio = nuevaFechaInicio;
  }

  public Date getNuevaFechaFin() {
    return nuevaFechaFin;
  }

  public void setNuevaFechaFin(Date nuevaFechaFin) {
    this.nuevaFechaFin = nuevaFechaFin;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

}
