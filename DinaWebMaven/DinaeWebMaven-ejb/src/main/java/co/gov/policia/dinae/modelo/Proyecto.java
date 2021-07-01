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
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "PROYECTO")
@NamedQueries({
  @NamedQuery(name = "Proyecto.UPDATEfechaActualizacionPorProyecto", query = "UPDATE Proyecto p SET p.fechaActualizacion = :fechaActualizacion WHERE p.idProyecto = :idProyecto"),
  @NamedQuery(name = "Proyecto.findAllPorUnidadPolicialYlistaEstado", query = "SELECT p FROM Proyecto p WHERE p.codigoProyecto IS NOT NULL AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.estado.idConstantes IN :idListaEstado"),
  @NamedQuery(name = "Proyecto.contarTodos", query = "SELECT COUNT( p ) FROM Proyecto p WHERE p.codigoProyecto IS NOT NULL"),
  @NamedQuery(name = "Proyecto.contarTodosPorPeriodo", query = "SELECT COUNT( p ) FROM Proyecto p WHERE p.codigoProyecto LIKE 'VIC%' AND p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "Proyecto.contarTodosPorConvocatoria", query = "SELECT COUNT( p ) FROM Proyecto p WHERE p.codigoProyecto LIKE 'CONV%' AND p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "Proyecto.UpdateEstadoTemporalFinanciYPresupuestoAprobado", query = "UPDATE Proyecto p SET p.estadoTemporalFinancia = :estadoTemporalFinancia, p.valorFinanciar = :valorFinanciar, p.presupuestoSolicitado = :presupuestoSolicitado WHERE p.idProyecto = :idProyecto"),
  @NamedQuery(name = "Proyecto.UpdateEstadoProyectoAEnImplementacion", query = "UPDATE Proyecto p SET p.estado = :estadoImplementacionPro WHERE p.idProyecto = :idProyecto AND p.estado.idConstantes = :idEstadoPermiteActualizacion "),
  @NamedQuery(name = "Proyecto.UpdateEstadoConvocatoriaProyectoPublicar", query = "UPDATE Proyecto p SET p.estado = :estado, p.valorFinanciar = :valorFinanciar WHERE p.idProyecto = :idProyecto"),
  @NamedQuery(name = "Proyecto.UpdateEstadoProyecto", query = "UPDATE Proyecto p SET p.estado = :estado WHERE p.idProyecto = :idProyecto"),
  @NamedQuery(name = "Proyecto.UpdateFechaActualiacionProyecto", query = "UPDATE Proyecto p SET p.fechaActualizacion = :fechaActualizacion WHERE p.idProyecto = :idProyecto"),
  @NamedQuery(name = "ProyectoUnidadPolicialDTO.findAllUnidadaPolicialPorPeriodo", query = "SELECT DISTINCT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO(p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo ORDER BY p.unidadPolicial.nombre ASC"),
  @NamedQuery(name = "Proyecto.findAllPorPeriodo", query = "SELECT p FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "ProyectoDTO.findAllPorPeriodo", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.tituloPropuesto, p.linea.nombre, p.linea.areaCienciaTecnologia.nombre, p.estado.idConstantes, p.estado.valor ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodo", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.unidadPolicial.siglaFisica, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor, p.valorFinanciar, p.presupuestoSolicitado) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYestadoFinanciarYNoAprobada", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.unidadPolicial.siglaFisica, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor, p.valorFinanciar, p.presupuestoSolicitado) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.estado.idConstantes IN (86,87) ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaAfinanciarPorPeriodo", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor, p.estadoTemporalFinancia, p.valorFinanciar ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.idProyecto IN (SELECT DISTINCT ep.proyecto.idProyecto FROM EvaluacionProyecto ep WHERE ep.proyecto.periodo.idPeriodo = :idPeriodo)"),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYunidadPolicial", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaPorPeriodoYestado", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.estado.idConstantes = :idEstado ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.idProyecto=:idProyecto"),
  @NamedQuery(name = "Proyecto.findAllProyectosVigentesByCodigoProyecto", query = "SELECT p FROM Proyecto p WHERE p.codigoProyecto LIKE :prefijoCodigoProyecto AND p.estado.idConstantes =:idEstado and p.unidadPolicial.idUnidadPolicial =:idUnidadPolicial ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllProyectosVigentesByCodigoProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO(  p.idProyecto, p.codigoProyecto, p.tituloPropuesto, p.fechaActualizacion, p.periodo.anio, p.periodo.fechaInicio, p.nroActaAprobComite, p.periodo.concecutivo, p.periodo.idPeriodo) FROM Proyecto p WHERE p.codigoProyecto LIKE :prefijoCodigoProyecto AND p.estado.idConstantes =:idEstado and p.unidadPolicial.idUnidadPolicial =:idUnidadPolicial ORDER BY p.codigoProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllProyectosVigentesByModalidad", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO(  p.idProyecto, p.codigoProyecto, p.tituloPropuesto, p.fechaActualizacion, p.periodo.anio, p.periodo.fechaInicio, p.nroActaAprobComite, p.periodo.concecutivo, p.periodo.idPeriodo, n.propuestaAsignada) FROM Proyecto p,  PropuestaNecesidad n WHERE  p.estado.idConstantes =:idEstado and p.unidadPolicial.idUnidadPolicial =:idUnidadPolicial and n.idPropuestaNecesidad=p.propuestaNecesidad.idPropuestaNecesidad and n.propuestaAsignada.modalidad.id =:modalidad ORDER BY p.codigoProyecto ASC"),
  @NamedQuery(name = "Proyecto.findAllByIdProgramaNotNull", query = "SELECT p FROM Proyecto p WHERE p.programas.idPrograma IS NOT NULL"),
  @NamedQuery(name = "Proyecto.findAllByIdPrograma", query = "SELECT p FROM Proyecto p WHERE p.programas.idPrograma = :idPrograma"),
  @NamedQuery(name = "Proyecto.findAllAsignadosInvestigadorPrincipalByIdentificacion", query = "SELECT p FROM Proyecto p WHERE p.idProyecto IN (SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE i.tipoInvestigador.idConstantes = 186 AND i.identificacion = :identificacion)"),
  @NamedQuery(name = "Proyecto.findAllAllProyectosTipoTrabajoDeGrado", query = "SELECT p FROM Proyecto p WHERE p.programas.idPrograma IS NOT NULL ORDER BY p.fechaRegistro DESC"),
  @NamedQuery(name = "Proyecto.findAllAllProyectosByIdProgramaAndIdEstado", query = "SELECT p FROM Proyecto p WHERE (p.programas.idPrograma = :idPrograma AND p.estado.idConstantes = :idEstado) AND ( p.codigoProyecto NOT LIKE 'MVIC%' AND p.codigoProyecto NOT LIKE 'MTG%' ) ORDER BY p.fechaRegistro DESC"),
  @NamedQuery(name = "Proyecto.findAllAsignadosInvestigadorPrincipalByIdentificacionAndIdEstado", query = "SELECT p FROM Proyecto p WHERE ( p.codigoProyecto IS NULL OR ( p.codigoProyecto NOT LIKE 'MVIC%' AND p.codigoProyecto NOT LIKE 'MTG%' ) ) AND (p.estado.idConstantes = :idEstado AND p.idProyecto IN (SELECT i.proyecto.idProyecto FROM InvestigadorProyecto i WHERE i.tipoInvestigador.idConstantes = 186 AND i.identificacion = :identificacion))"),
  @NamedQuery(name = "Proyecto.findAllAllProyectosTipoTrabajoDeGradoByIdUnidadPolicial", query = "SELECT p FROM Proyecto p WHERE ( p.codigoProyecto IS NULL OR ( p.codigoProyecto NOT LIKE 'MVIC%' AND p.codigoProyecto NOT LIKE 'MTG%') ) AND p.programas.idPrograma IS NOT NULL AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ORDER BY p.fechaRegistro DESC"),
  @NamedQuery(name = "Proyecto.findAllAllProyectosTipoTrabajoDeGradoByIdUnidadPolicialAndIdEstado", query = "SELECT p FROM Proyecto p WHERE p.programas.idPrograma IS NOT NULL AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.estado.idConstantes = :idEstado ORDER BY p.fechaRegistro DESC"),
  @NamedQuery(name = "Proyecto.findAllProyectosInvestigacionNoEvaluadosByPeriodo", query = "SELECT COUNT( p ) FROM Proyecto p WHERE p.estado.idConstantes != :idEstado AND p.codigoProyecto IS NOT NULL AND p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaConvocatoriaPorConvocatoria", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.codigoProyecto IS NOT NULL ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllPropuestaNecesidadConvocatoriaByPeriodoEstados", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO( p.idProyecto, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.tituloPropuesto, p.fechaActualizacion, p.estado.idConstantes, p.estado.valor ) FROM Proyecto p WHERE p.periodo.idPeriodo = :idPeriodo AND p.codigoProyecto IS NULL AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.estado.idConstantes IN :estados ORDER BY p.idProyecto ASC "),
  @NamedQuery(name = "ProyectoDTO.findAllProyectosInvestigacionVigentesUnidad", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoDTO(p.idProyecto, p.tema, p.linea.nombre, p.linea.areaCienciaTecnologia.nombre, p.estado.idConstantes, p.estado.valor, p.fechaRegistro, p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre, p.codigoProyecto) FROM Proyecto p WHERE p.codigoProyecto IS NOT NULL AND p.programas IS NULL AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.estado.idConstantes = :idEstado"),
  @NamedQuery(name = "Proyecto.ultimoRegistroProyectoGrado", query = "SELECT MAX(p.concecutivoProyectoGrado) FROM Proyecto p WHERE p.concecutivoProyectoGrado IS NOT NULL"),
  @NamedQuery(name = "Proyecto.findByVigencia", query = "SELECT p FROM Proyecto p WHERE p.propuestaNecesidad.idPropuestaNecesidad in :idPropuestaNecesidad")
})
public class Proyecto implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_PROYECTO")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Proyecto_seq_gen")
  @SequenceGenerator(name = "Proyecto_seq_gen", sequenceName = "SEC_PROYECTO", allocationSize = 1)
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

  @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes estado;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "proyecto", fetch = FetchType.LAZY)
  private List<SemilleroProyecto> semilleroProyectoList;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "proyecto", fetch = FetchType.LAZY)
  private List<FuenteProyecto> fuenteProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<GrupoInvestigacionProyecto> grupoInvestProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<TemaProyecto> temaProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<InvestigadorProyecto> investigadoresProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<EjecutorNecesidad> ejecutorNecesidadList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<EvaluacionProyecto> evaluacionProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<IndicadoresProyecto> indicadoresProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<CompromisoProyecto> compromisoProyectoList;

  @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  private List<InstitucionesProyecto> institucionesProyectoList;

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
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes estadoImplementacion;

  @Column(name = "CONCECUTIVO_PROYECTO_GRADO")
  private String concecutivoProyectoGrado;

  @Column(name = "COSTO_PRO_MIGRADO")
  private BigDecimal costoTotalProyectoMigrado;

  @Transient
  private String comentario;

  @Transient
  private Long idContantes;

  @Transient
  private String nombreInvestigadorPrincipal;

  @Column(name = "PRESUPUESTO_SOLICITADO")
  private BigDecimal presupuestoSolicitado;

  public Proyecto() {
  }

  public Proyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
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

  public String getNroActaAprobacionComite() {
    return nroActaAprobacionComite;
  }

  public void setNroActaAprobacionComite(String nroActaAprobacionComite) {
    this.nroActaAprobacionComite = nroActaAprobacionComite;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public List<SemilleroProyecto> getSemilleroProyectoList() {
    return semilleroProyectoList;
  }

  public void setSemilleroProyectoList(List<SemilleroProyecto> semilleroProyectoList) {
    this.semilleroProyectoList = semilleroProyectoList;
  }

  public List<FuenteProyecto> getFuenteProyectoList() {
    return fuenteProyectoList;
  }

  public void setFuenteProyectoList(List<FuenteProyecto> fuenteProyectoList) {
    this.fuenteProyectoList = fuenteProyectoList;
  }

  public List<GrupoInvestigacionProyecto> getGrupoInvestProyectoList() {
    return grupoInvestProyectoList;
  }

  public void setGrupoInvestProyectoList(List<GrupoInvestigacionProyecto> grupoInvestProyectoList) {
    this.grupoInvestProyectoList = grupoInvestProyectoList;
  }

  public List<TemaProyecto> getTemaProyectoList() {
    return temaProyectoList;
  }

  public void setTemaProyectoList(List<TemaProyecto> temaProyectoList) {
    this.temaProyectoList = temaProyectoList;
  }

  public List<InvestigadorProyecto> getInvestigadoresProyectoList() {
    return investigadoresProyectoList;
  }

  public void setInvestigadoresProyectoList(List<InvestigadorProyecto> investigadoresProyectoList) {
    this.investigadoresProyectoList = investigadoresProyectoList;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idProyecto != null ? idProyecto.hashCode() : 0);
    return hash;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Proyecto)) {
      return false;
    }
    Proyecto other = (Proyecto) object;
    if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Proyecto[ idProyecto=" + idProyecto + " ]";
  }

  public List<EjecutorNecesidad> getEjecutorNecesidadList() {
    return ejecutorNecesidadList;
  }

  public void setEjecutorNecesidadList(List<EjecutorNecesidad> ejecutorNecesidadList) {
    this.ejecutorNecesidadList = ejecutorNecesidadList;
  }

  public Constantes getEstado() {
    return estado;
  }

  public void setEstado(Constantes estado) {
    this.estado = estado;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
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

  @Override
  public String getLlaveModel() {
    return String.valueOf(idProyecto);
  }

  public List<EvaluacionProyecto> getEvaluacionProyectoList() {
    return evaluacionProyectoList;
  }

  public void setEvaluacionProyectoList(List<EvaluacionProyecto> evaluacionProyectoList) {
    this.evaluacionProyectoList = evaluacionProyectoList;
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

  public List<IndicadoresProyecto> getIndicadoresProyectoList() {
    return indicadoresProyectoList;
  }

  public void setIndicadoresProyectoList(List<IndicadoresProyecto> indicadoresProyectoList) {
    this.indicadoresProyectoList = indicadoresProyectoList;
  }

  public List<CompromisoProyecto> getCompromisoProyectoList() {
    return compromisoProyectoList;
  }

  public void setCompromisoProyectoList(List<CompromisoProyecto> compromisoProyectoList) {
    this.compromisoProyectoList = compromisoProyectoList;
  }

  public List<InstitucionesProyecto> getInstitucionesProyectoList() {
    return institucionesProyectoList;
  }

  public void setInstitucionesProyectoList(List<InstitucionesProyecto> institucionesProyectoList) {
    this.institucionesProyectoList = institucionesProyectoList;
  }

  public Programas getProgramas() {
    return programas;
  }

  public void setProgramas(Programas programas) {
    this.programas = programas;
  }

  /**
   * @return the resumenProyecto
   */
  public String getResumenProyecto() {
    return resumenProyecto;
  }

  /**
   * @param resumenProyecto the resumenProyecto to set
   */
  public void setResumenProyecto(String resumenProyecto) {
    this.resumenProyecto = resumenProyecto;
  }

  /**
   * @return the fechaAprobacionComite2
   */
  public Date getFechaAprobacionComite2() {
    return fechaAprobacionComite2;
  }

  /**
   * @param fechaAprobacionComite2 the fechaAprobacionComite2 to set
   */
  public void setFechaAprobacionComite2(Date fechaAprobacionComite2) {
    this.fechaAprobacionComite2 = fechaAprobacionComite2;
  }

  /**
   * @return the nroActaAprobComite
   */
  public String getNroActaAprobComite() {
    return nroActaAprobComite;
  }

  /**
   * @param nroActaAprobComite the nroActaAprobComite to set
   */
  public void setNroActaAprobComite(String nroActaAprobComite) {
    this.nroActaAprobComite = nroActaAprobComite;
  }

  /**
   * @return the nombreInvestigadorPrincipal
   */
  public String getNombreInvestigadorPrincipal() {
    return nombreInvestigadorPrincipal;
  }

  /**
   * @param nombreInvestigadorPrincipal the nombreInvestigadorPrincipal to set
   */
  public void setNombreInvestigadorPrincipal(String nombreInvestigadorPrincipal) {
    this.nombreInvestigadorPrincipal = nombreInvestigadorPrincipal;
  }

  /**
   * @return the estadoImplementacion
   */
  public Constantes getEstadoImplementacion() {
    return estadoImplementacion;
  }

  /**
   * @param estadoImplementacion the estadoImplementacion to set
   */
  public void setEstadoImplementacion(Constantes estadoImplementacion) {
    this.estadoImplementacion = estadoImplementacion;
  }

  public BigDecimal getPresupuestoSolicitado() {
    return presupuestoSolicitado;
  }

  public void setPresupuestoSolicitado(BigDecimal presupuestoSolicitado) {
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

  public String getConcecutivoProyectoGrado() {
    return concecutivoProyectoGrado;
  }

  public void setConcecutivoProyectoGrado(String concecutivoProyectoGrado) {
    this.concecutivoProyectoGrado = concecutivoProyectoGrado;
  }

  public BigDecimal getCostoTotalProyectoMigrado() {
    if (costoTotalProyectoMigrado == null) {
      return BigDecimal.ZERO;
    }
    return costoTotalProyectoMigrado;
  }

  public void setCostoTotalProyectoMigrado(BigDecimal costoTotalProyectoMigrado) {
    this.costoTotalProyectoMigrado = costoTotalProyectoMigrado;
  }

}
