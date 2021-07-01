package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "ProyectoVersion.getIdProyectoVersionPorVersionYproyecto", query = "SELECT p.idProyectoVersion FROM ProyectoVersion p WHERE p.versiones.idVersion = :idVersion AND p.idProyecto = :idProyecto"),})
public class ProyectoVersion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_PROYECTO_VERSION")
  private Long idProyectoVersion;

  @Column(name = "ID_PROYECTO")
  private Long idProyecto;

  @Column(name = "CODIGO_PROYECTO")
  private String codigoProyecto;

  @Column(name = "TITULO_PROPUESTO")
  private String tituloPropuesto;

  @Column(name = "FUNCION_PROYECTO")
  private String funcionProyecto;

  @Column(name = "FECHA_ESTIMADA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEstimadaInicio;

  @Column(name = "FECHA_ESTIMADA_FINALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEstimadaFinalizacion;

  @Column(name = "VALOR_TOTAL")
  private BigDecimal valorTotal;

  @Column(name = "CONVOCATORIA")
  private String convocatoria;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "TEMA")
  private String tema;

  @Column(name = "FECHA_APROBACION_COMITE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAprobacionComite;

  @Column(name = "FECHA_APROBACION_COMITE_2")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAprobacionComite2;

  @Column(name = "NRO_ACTA_APROBACION_COMITE")
  private String nroActaAprobacionComite;

  @Column(name = "NRO_ACTA_APROB_COMITE")
  private String nroActaAprobComite;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "RESUMEN_PROYECTO")
  private String resumenProyecto;

  @JoinColumn(name = "ID_VERSION", referencedColumnName = "ID_VERSION")
  @ManyToOne(fetch = FetchType.LAZY)
  private Versiones versiones;

  @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estado;

  @JoinColumn(name = "ID_LINEA", referencedColumnName = "ID_LINEA")
  @ManyToOne(fetch = FetchType.LAZY)
  private Linea linea;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Periodo periodo;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD")
  @ManyToOne(fetch = FetchType.LAZY)
  private PropuestaNecesidad propuestaNecesidad;

  @JoinColumn(name = "ID_PROPUESTA_CONVOCATORIA", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto propuestaConvocatoria;

  @JoinColumn(name = "ID_PROGRAMA", referencedColumnName = "ID_PROGRAMA")
  @ManyToOne(fetch = FetchType.LAZY)
  private Programas programas;

  @Column(name = "ESTADO_TEMPORAL_FINANCIA")
  private String estadoTemporalFinancia;

  @Column(name = "VALOR_FINANCIAR")
  private BigDecimal valorFinanciar;

  @JoinColumn(name = "ID_ESTADO_IMPLEMENTACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes estadoImplementacion;

  @Transient
  private String comentario;

  @Transient
  private Long idContantes;

  @Transient
  private String nombreInvestigadorPrincipal;

  @Column(name = "PRESUPUESTO_SOLICITADO")
  private BigDecimal presupuestoSolicitado;

  public ProyectoVersion() {
  }

  public Long getIdProyectoVersion() {
    return idProyectoVersion;
  }

  public void setIdProyectoVersion(Long idProyectoVersion) {
    this.idProyectoVersion = idProyectoVersion;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public String getTituloPropuesto() {
    return tituloPropuesto;
  }

  public void setTituloPropuesto(String tituloPropuesto) {
    this.tituloPropuesto = tituloPropuesto;
  }

  public String getFuncionProyecto() {
    return funcionProyecto;
  }

  public void setFuncionProyecto(String funcionProyecto) {
    this.funcionProyecto = funcionProyecto;
  }

  public Date getFechaEstimadaInicio() {
    return fechaEstimadaInicio;
  }

  public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }

  public Date getFechaEstimadaFinalizacion() {
    return fechaEstimadaFinalizacion;
  }

  public void setFechaEstimadaFinalizacion(Date fechaEstimadaFinalizacion) {
    this.fechaEstimadaFinalizacion = fechaEstimadaFinalizacion;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public String getConvocatoria() {
    return convocatoria;
  }

  public void setConvocatoria(String convocatoria) {
    this.convocatoria = convocatoria;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public Date getFechaAprobacionComite() {
    return fechaAprobacionComite;
  }

  public void setFechaAprobacionComite(Date fechaAprobacionComite) {
    this.fechaAprobacionComite = fechaAprobacionComite;
  }

  public Date getFechaAprobacionComite2() {
    return fechaAprobacionComite2;
  }

  public void setFechaAprobacionComite2(Date fechaAprobacionComite2) {
    this.fechaAprobacionComite2 = fechaAprobacionComite2;
  }

  public String getNroActaAprobacionComite() {
    return nroActaAprobacionComite;
  }

  public void setNroActaAprobacionComite(String nroActaAprobacionComite) {
    this.nroActaAprobacionComite = nroActaAprobacionComite;
  }

  public String getNroActaAprobComite() {
    return nroActaAprobComite;
  }

  public void setNroActaAprobComite(String nroActaAprobComite) {
    this.nroActaAprobComite = nroActaAprobComite;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getResumenProyecto() {
    return resumenProyecto;
  }

  public void setResumenProyecto(String resumenProyecto) {
    this.resumenProyecto = resumenProyecto;
  }

  public Versiones getVersiones() {
    return versiones;
  }

  public void setVersiones(Versiones versiones) {
    this.versiones = versiones;
  }

  public Constantes getEstado() {
    return estado;
  }

  public void setEstado(Constantes estado) {
    this.estado = estado;
  }

  public Linea getLinea() {
    return linea;
  }

  public void setLinea(Linea linea) {
    this.linea = linea;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }

  public Proyecto getPropuestaConvocatoria() {
    return propuestaConvocatoria;
  }

  public void setPropuestaConvocatoria(Proyecto propuestaConvocatoria) {
    this.propuestaConvocatoria = propuestaConvocatoria;
  }

  public Programas getProgramas() {
    return programas;
  }

  public void setProgramas(Programas programas) {
    this.programas = programas;
  }

  public String getEstadoTemporalFinancia() {
    return estadoTemporalFinancia;
  }

  public void setEstadoTemporalFinancia(String estadoTemporalFinancia) {
    this.estadoTemporalFinancia = estadoTemporalFinancia;
  }

  public BigDecimal getValorFinanciar() {
    return valorFinanciar;
  }

  public void setValorFinanciar(BigDecimal valorFinanciar) {
    this.valorFinanciar = valorFinanciar;
  }

  public Constantes getEstadoImplementacion() {
    return estadoImplementacion;
  }

  public void setEstadoImplementacion(Constantes estadoImplementacion) {
    this.estadoImplementacion = estadoImplementacion;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Long getIdContantes() {
    return idContantes;
  }

  public void setIdContantes(Long idContantes) {
    this.idContantes = idContantes;
  }

  public String getNombreInvestigadorPrincipal() {
    return nombreInvestigadorPrincipal;
  }

  public void setNombreInvestigadorPrincipal(String nombreInvestigadorPrincipal) {
    this.nombreInvestigadorPrincipal = nombreInvestigadorPrincipal;
  }

  public BigDecimal getPresupuestoSolicitado() {
    return presupuestoSolicitado;
  }

  public void setPresupuestoSolicitado(BigDecimal presupuestoSolicitado) {
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

}
