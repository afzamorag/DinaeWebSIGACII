package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class ProyectoDTO implements IDataModel, Serializable {

  private Long id;
  private Long idProyectoVersion;
  private Long idVersion;
  private String codigoProyecto;
  private String tituloPropuesto;
  private String funcionProyecto;
  private Date fechaEstimadaInicio;
  private Date fechaEstimadaFinalizacion;
  private BigDecimal valorTotal;
  private String convocatoria;
  private Date fechaRegistro;
  private String tema;
  private Date fechaAprobacionComite;
  private Date fechaAprobacionComite2;
  private String nroActaAprobacionComite;
  private String nroActaAprobComite;
  private Date fechaActualizacion;
  private String resumenProyecto;
  private Long idEstado;
  private String estado;
  private Long idLinea;
  private String nombreLinea;
  private Long idPeriodo;
  private Long idUusuarioRol;
  private Long idUnidadPolicial;
  private String nombreUnidadPolicial;
  private Long idPropuestaNecesidad;
  private Long idPropuestaConvocatoria;
  private Long idProgramas;
  private String estadoTemporalFinancia;
  private BigDecimal valorFinanciar;
  private Long idEstadoImplementacion;
  private BigDecimal presupuestoSolicitado;

  private String nombreArea;
  private Date fechaPresentacion;
  private int concecutivo;
  private int concecutivoDE;
  private Integer sumaValorCriterioIngresado;
  private UnidadPolicialDTO unidadPolicialDTO;
  private String siglaFisicaUnidadPolicial;
  private boolean selecconado;
  private List<CompromisoDTO> compromisos;
  private String anio;
  private Long numeroConvocatoria;
  private String tipoProyecto;
  private Long idTipoPeriodo;
  private SieduPropuestaAsignada propuestaAsignada;
  
  private boolean mostrarLinkActualizarProyecto;

  private BigDecimal costoTotalProyectoMigrado;

  public ProyectoDTO() {
  }

  private String siglaFisica;

  /**
   *
   * @param id
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaRegistro
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param siglaFisica
   * @param nombreArea
   */
  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, Date fechaRegistro, Long idUnidadPolicial, String nombreUnidadPolicial, String siglaFisica, String nombreArea) {

    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.siglaFisica = siglaFisica;
    this.nombreArea = nombreArea;
  }

  /**
   *
   * @param id
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaRegistro
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param siglaFisica
   * @param nombreArea
   * @param fechaEstimadaInicio
   * @param fechaEstimadaFinalizacion
   */
  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, Date fechaRegistro, Long idUnidadPolicial, String nombreUnidadPolicial, String siglaFisica, String nombreArea, Date fechaEstimadaInicio, Date fechaEstimadaFinalizacion) {

    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.siglaFisica = siglaFisica;
    this.nombreArea = nombreArea;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.fechaEstimadaFinalizacion = fechaEstimadaFinalizacion;
  }

  private String concecutivoProyectoGrado;

  /**
   *
   * @param id
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaRegistro
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param siglaFisica
   * @param nombreArea
   * @param concecutivoProyectoGrado
   */
  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, Date fechaRegistro, Long idUnidadPolicial, String nombreUnidadPolicial, String siglaFisica, String nombreArea, String concecutivoProyectoGrado) {

    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.siglaFisica = siglaFisica;
    this.nombreArea = nombreArea;
    if (concecutivoProyectoGrado != null && codigoProyecto == null) {

      this.codigoProyecto = "TG-" + concecutivoProyectoGrado;

    }
  }

  /**
   *
   * @param id
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param funcionProyecto
   * @param fechaEstimadaInicio
   * @param fechaEstimadaFinalizacion
   * @param valorTotal
   * @param convocatoria
   * @param fechaRegistro
   * @param tema
   * @param fechaAprobacionComite
   * @param fechaAprobacionComite2
   * @param nroActaAprobacionComite
   * @param nroActaAprobComite
   * @param fechaActualizacion
   * @param resumenProyecto
   * @param idEstado
   * @param estado
   * @param idLinea
   * @param nombreLinea
   * @param idPeriodo
   * @param idUusuarioRol
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param idPropuestaNecesidad
   * @param idPropuestaConvocatoria
   * @param idProgramas
   * @param estadoTemporalFinancia
   * @param valorFinanciar
   * @param idEstadoImplementacion
   * @param presupuestoSolicitado
   */
  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, String funcionProyecto, Date fechaEstimadaInicio, Date fechaEstimadaFinalizacion, BigDecimal valorTotal, String convocatoria, Date fechaRegistro, String tema, Date fechaAprobacionComite, Date fechaAprobacionComite2, String nroActaAprobacionComite, String nroActaAprobComite, Date fechaActualizacion, String resumenProyecto, Long idEstado, String estado, Long idLinea, String nombreLinea, Long idPeriodo, Long idUusuarioRol, Long idUnidadPolicial, String nombreUnidadPolicial, Long idPropuestaNecesidad, Long idPropuestaConvocatoria, Long idProgramas, String estadoTemporalFinancia, BigDecimal valorFinanciar, Long idEstadoImplementacion, BigDecimal presupuestoSolicitado) {
    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.funcionProyecto = funcionProyecto;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.fechaEstimadaFinalizacion = fechaEstimadaFinalizacion;
    this.valorTotal = valorTotal;
    this.convocatoria = convocatoria;
    this.fechaRegistro = fechaRegistro;
    this.tema = tema;
    this.fechaAprobacionComite = fechaAprobacionComite;
    this.fechaAprobacionComite2 = fechaAprobacionComite2;
    this.nroActaAprobacionComite = nroActaAprobacionComite;
    this.nroActaAprobComite = nroActaAprobComite;
    this.fechaActualizacion = fechaActualizacion;
    this.resumenProyecto = resumenProyecto;
    this.idEstado = idEstado;
    this.estado = estado;
    this.idLinea = idLinea;
    this.nombreLinea = nombreLinea;
    this.idPeriodo = idPeriodo;
    this.idUusuarioRol = idUusuarioRol;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.idPropuestaNecesidad = idPropuestaNecesidad;
    this.idPropuestaConvocatoria = idPropuestaConvocatoria;
    this.idProgramas = idProgramas;
    this.estadoTemporalFinancia = estadoTemporalFinancia;
    this.valorFinanciar = valorFinanciar;
    this.idEstadoImplementacion = idEstadoImplementacion;
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param unidadPolicial
   * @param codigoProyecto
   */
  public ProyectoDTO(Long id, String tituloPropuesto, Date fechaPresentacion, String unidadPolicial, String codigoProyecto) {

    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.nombreUnidadPolicial = unidadPolicial;
    this.codigoProyecto = codigoProyecto;

    if (codigoProyecto == null) {

      tipoProyecto = "Trabajo de grado";

    } else if (codigoProyecto.startsWith("VIC")) {

      tipoProyecto = "Proyecto institucional";

    } else if (codigoProyecto.startsWith("CONV")) {

      tipoProyecto = "De convocatoria de financiación";

    }
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombre
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaInicio
   * @param nombreArea
   * @param nombreLinea
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombre, String codigoProyecto, String tituloPropuesto, Date fechaInicio, String nombreArea, String nombreLinea) {

    this.id = id;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre);
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaInicio;
    this.nombreArea = nombreArea;
    this.nombreLinea = nombreLinea;

  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombre
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaInicio
   * @param nombreArea
   * @param nombreLinea
   * @param estado
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombre, String codigoProyecto, String tituloPropuesto, Date fechaInicio, String nombreArea, String nombreLinea, String estado) {

    this.id = id;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre);
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaInicio;
    this.nombreArea = nombreArea;
    this.nombreLinea = nombreLinea;
    this.estado = estado;
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaFisica
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaInicio
   * @param nombreArea
   * @param nombreLinea
   * @param estado
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombre, String siglaFisica, String codigoProyecto, String tituloPropuesto, Date fechaInicio, String nombreArea, String nombreLinea, String estado) {

    this.id = id;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre, siglaFisica);
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaInicio;
    this.nombreArea = nombreArea;
    this.nombreLinea = nombreLinea;
    this.estado = estado;
  }

  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombre, String codigoProyecto, String tituloPropuesto, Date fechaPresenta) {

    this.id = id;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombre);
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresenta;

  }

  /**
   *
   * @param id
   * @param codigoProyecto
   * @param tituloPropuesto
   * @param fechaActualizacion
   * @param anio
   * @param fechaInicioPeiodo
   * @param nroActaAprobacionComite
   * @param concecutivo
   * @param idPeriodo
   */
  private Short anioConvocatoria;
  private Date fechaInicioPeriodo;
  private Long concecutivoPeriodo;

  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, Date fechaActualizacion, Short anioConvocatoria, Date fechaInicioPeriodo, String nroActaAprobacionComite, Long concecutivo, Long idPeriodo) {
    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaActualizacion = fechaActualizacion;
    this.anioConvocatoria = anioConvocatoria;
    this.fechaInicioPeriodo = fechaInicioPeriodo;
    this.nroActaAprobacionComite = nroActaAprobacionComite;
    this.concecutivoPeriodo = concecutivo;
    this.idPeriodo = idPeriodo;
  }
  
  public ProyectoDTO(Long id, String codigoProyecto, String tituloPropuesto, Date fechaActualizacion, Short anioConvocatoria, Date fechaInicioPeriodo, String nroActaAprobacionComite, Long concecutivo, Long idPeriodo, SieduPropuestaAsignada propuestaAsignada) {
    this.id = id;
    this.codigoProyecto = codigoProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaActualizacion = fechaActualizacion;
    this.anioConvocatoria = anioConvocatoria;
    this.fechaInicioPeriodo = fechaInicioPeriodo;
    this.nroActaAprobacionComite = nroActaAprobacionComite;
    this.concecutivoPeriodo = concecutivo;
    this.idPeriodo = idPeriodo;
    this.propuestaAsignada = propuestaAsignada;
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param idEstado
   * @param estado
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombreUnidadPolicial, String tituloPropuesto, Date fechaPresentacion, Long idEstado, String estado) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.idEstado = idEstado;
    this.estado = estado;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombreUnidadPolicial);
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param idEstado
   * @param estado
   * @param valorFinanciar
   * @param presupuestoSolicitado
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombreUnidadPolicial, String tituloPropuesto, Date fechaPresentacion, Long idEstado, String estado, BigDecimal valorFinanciar, BigDecimal presupuestoSolicitado) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.idEstado = idEstado;
    this.estado = estado;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombreUnidadPolicial);
    this.valorFinanciar = valorFinanciar;
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param siglaFisica
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param idEstado
   * @param estado
   * @param valorFinanciar
   * @param presupuestoSolicitado
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombreUnidadPolicial, String siglaFisica, String tituloPropuesto, Date fechaPresentacion, Long idEstado, String estado, BigDecimal valorFinanciar, BigDecimal presupuestoSolicitado) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.idEstado = idEstado;
    this.estado = estado;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombreUnidadPolicial, siglaFisica, null);
    this.valorFinanciar = valorFinanciar;
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param idEstado
   * @param estado
   * @param estadoTemporalFinancia
   * @param valorFinanciar
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombreUnidadPolicial, String tituloPropuesto,
          Date fechaPresentacion, Long idEstado, String estado, String estadoTemporalFinancia, BigDecimal valorFinanciar) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.idEstado = idEstado;
    this.estado = estado;
    this.unidadPolicialDTO = new UnidadPolicialDTO(idUnidadPolicial, nombreUnidadPolicial);
    this.estadoTemporalFinancia = estadoTemporalFinancia;
    this.valorFinanciar = valorFinanciar;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param nombreLinea
   * @param nombreArea
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String nombreLinea, String nombreArea) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.nombreLinea = nombreLinea;
    this.nombreArea = nombreArea;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param nombreLinea
   * @param nombreArea
   * @param idEstado
   * @param estado
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String nombreLinea, String nombreArea, Long idEstado, String estado) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.nombreLinea = nombreLinea;
    this.nombreArea = nombreArea;
    this.idEstado = idEstado;
    this.estado = estado;
  }

  public ProyectoDTO(Long id, String tituloPropuesto, String nombreLinea, String nombreArea, Long idEstado, String estado, Date fechaPresentacion, int concecutivo, int concecutivoDE, Integer sumaValorCriterioIngresado, UnidadPolicialDTO unidadPolicialDTO, String estadoTemporalFinancia, BigDecimal valorFinanciar, boolean selecconado, List<CompromisoDTO> compromisos) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.nombreLinea = nombreLinea;
    this.nombreArea = nombreArea;
    this.idEstado = idEstado;
    this.estado = estado;
    this.fechaPresentacion = fechaPresentacion;
    this.concecutivo = concecutivo;
    this.concecutivoDE = concecutivoDE;
    this.sumaValorCriterioIngresado = sumaValorCriterioIngresado;
    this.unidadPolicialDTO = unidadPolicialDTO;
    this.estadoTemporalFinancia = estadoTemporalFinancia;
    this.valorFinanciar = valorFinanciar;
    this.selecconado = selecconado;
    this.compromisos = compromisos;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param codigoProyecto
   * @param fechaEstimadaInicio
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param idPeriodo
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String codigoProyecto, Date fechaEstimadaInicio, Long idUnidadPolicial, String nombreUnidadPolicial, Long idPeriodo) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.codigoProyecto = codigoProyecto;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.idPeriodo = idPeriodo;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param codigoProyecto
   * @param fechaEstimadaInicio
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String codigoProyecto, Date fechaEstimadaInicio, Long idUnidadPolicial, String nombreUnidadPolicial) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.codigoProyecto = codigoProyecto;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  /**
   *
   * @param id
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param siglaFisicaUnidadPolicial
   * @param tituloPropuesto
   * @param fechaPresentacion
   * @param idEstado
   * @param estado
   * @param fechaEstimadaInicio
   * @param fechaEstimadaFinalizacion
   */
  public ProyectoDTO(Long id, Long idUnidadPolicial, String nombreUnidadPolicial, String siglaFisicaUnidadPolicial, String tituloPropuesto, Date fechaPresentacion, Long idEstado, String estado, Date fechaEstimadaInicio, Date fechaEstimadaFinalizacion) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.fechaPresentacion = fechaPresentacion;
    this.idEstado = idEstado;
    this.estado = estado;
    this.fechaEstimadaInicio = fechaEstimadaInicio;
    this.fechaEstimadaFinalizacion = fechaEstimadaFinalizacion;

    UnidadPolicialDTO up = new UnidadPolicialDTO(idUnidadPolicial, nombreUnidadPolicial);
    up.setSiglaFisicaUnidad(siglaFisicaUnidadPolicial);

    this.unidadPolicialDTO = up;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param nombreLinea
   * @param nombreArea
   * @param idEstado
   * @param estado
   * @param fechaPresentacion
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String nombreLinea, String nombreArea, Long idEstado, String estado, Date fechaPresentacion, Long idUnidadPolicial, String nombreUnidadPolicial) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.nombreLinea = nombreLinea;
    this.nombreArea = nombreArea;
    this.idEstado = idEstado;
    this.estado = estado;
    this.fechaPresentacion = fechaPresentacion;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  /**
   *
   * @param id
   * @param tituloPropuesto
   * @param nombreLinea
   * @param nombreArea
   * @param idEstado
   * @param estado
   * @param fechaPresentacion
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param codigoProyecto
   */
  public ProyectoDTO(Long id, String tituloPropuesto, String nombreLinea, String nombreArea, Long idEstado, String estado, Date fechaPresentacion, Long idUnidadPolicial, String nombreUnidadPolicial, String codigoProyecto) {
    this.id = id;
    this.tituloPropuesto = tituloPropuesto;
    this.nombreLinea = nombreLinea;
    this.nombreArea = nombreArea;
    this.idEstado = idEstado;
    this.estado = estado;
    this.fechaPresentacion = fechaPresentacion;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.codigoProyecto = codigoProyecto;
  }

  public String getIdRowKey() {

    return String.valueOf(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTituloPropuesto() {
    return tituloPropuesto;
  }

  public void setTituloPropuesto(String tituloPropuesto) {
    this.tituloPropuesto = tituloPropuesto;
  }

  public String getNombreLinea() {
    return nombreLinea;
  }

  public void setNombreLinea(String nombreLinea) {
    this.nombreLinea = nombreLinea;
  }

  public String getNombreArea() {
    return nombreArea;
  }

  public void setNombreArea(String nombreArea) {
    this.nombreArea = nombreArea;
  }

  public Long getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(Long idEstado) {
    this.idEstado = idEstado;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public boolean isSelecconado() {
    return selecconado;
  }

  public void setSelecconado(boolean selecconado) {
    this.selecconado = selecconado;
  }

  public Date getFechaPresentacion() {
    return fechaPresentacion;
  }

  public void setFechaPresentacion(Date fechaPresentacion) {
    this.fechaPresentacion = fechaPresentacion;
  }

  public UnidadPolicialDTO getUnidadPolicialDTO() {
    return unidadPolicialDTO;
  }

  public void setUnidadPolicialDTO(UnidadPolicialDTO unidadPolicialDTO) {
    this.unidadPolicialDTO = unidadPolicialDTO;
  }

  @Override
  public String getLlaveModel() {
    return String.valueOf(id);
  }

  public int getConcecutivo() {
    return concecutivo;
  }

  public void setConcecutivo(int concecutivo) {
    this.concecutivo = concecutivo;
  }

  public int getConcecutivoDE() {
    return concecutivoDE;
  }

  public void setConcecutivoDE(int concecutivoDE) {
    this.concecutivoDE = concecutivoDE;
  }

  public Integer getSumaValorCriterioIngresado() {
    return sumaValorCriterioIngresado;
  }

  public void setSumaValorCriterioIngresado(Integer sumaValorCriterioIngresado) {
    this.sumaValorCriterioIngresado = sumaValorCriterioIngresado;
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

  public List<CompromisoDTO> getCompromisos() {
    return compromisos;
  }

  public void setCompromisos(List<CompromisoDTO> compromisos) {
    this.compromisos = compromisos;
  }

  public String getCodigoProyecto() {
    return codigoProyecto;
  }

  public void setCodigoProyecto(String codigoProyecto) {
    this.codigoProyecto = codigoProyecto;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getNroActaAprobacionComite() {
    return nroActaAprobacionComite;
  }

  public void setNroActaAprobacionComite(String nroActaAprobacionComite) {
    this.nroActaAprobacionComite = nroActaAprobacionComite;
  }

  public Long getNumeroConvocatoria() {
    return numeroConvocatoria;
  }

  public void setNumeroConvocatoria(Long numeroConvocatoria) {
    this.numeroConvocatoria = numeroConvocatoria;
  }

  public Short getAnioConvocatoria() {
    return anioConvocatoria;
  }

  public void setAnioConvocatoria(Short anioConvocatoria) {
    this.anioConvocatoria = anioConvocatoria;
  }

  public Date getFechaInicioPeriodo() {
    return fechaInicioPeriodo;
  }

  public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
    this.fechaInicioPeriodo = fechaInicioPeriodo;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Long getConcecutivoPeriodo() {
    return concecutivoPeriodo;
  }

  public void setConcecutivoPeriodo(Long concecutivoPeriodo) {
    this.concecutivoPeriodo = concecutivoPeriodo;
  }

  public BigDecimal getPresupuestoSolicitado() {
    return presupuestoSolicitado;
  }

  public void setPresupuestoSolicitado(BigDecimal presupuestoSolicitado) {
    this.presupuestoSolicitado = presupuestoSolicitado;
  }

  public Date getFechaEstimadaInicio() {
    return fechaEstimadaInicio;
  }

  public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
    this.fechaEstimadaInicio = fechaEstimadaInicio;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  public String getSiglaFisicaUnidadPolicial() {
    return siglaFisicaUnidadPolicial;
  }

  public void setSiglaFisicaUnidadPolicial(String siglaFisicaUnidadPolicial) {
    this.siglaFisicaUnidadPolicial = siglaFisicaUnidadPolicial;
  }

  public Date getFechaEstimadaFinalizacion() {
    return fechaEstimadaFinalizacion;
  }

  public void setFechaEstimadaFinalizacion(Date fechaEstimadaFinalizacion) {
    this.fechaEstimadaFinalizacion = fechaEstimadaFinalizacion;
  }

  public String getTipoProyecto() {
    return tipoProyecto;
  }

  public void setTipoProyecto(String tipoProyecto) {
    this.tipoProyecto = tipoProyecto;
  }

  public String getFuncionProyecto() {
    return funcionProyecto;
  }

  public void setFuncionProyecto(String funcionProyecto) {
    this.funcionProyecto = funcionProyecto;
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

  public String getNroActaAprobComite() {
    return nroActaAprobComite;
  }

  public void setNroActaAprobComite(String nroActaAprobComite) {
    this.nroActaAprobComite = nroActaAprobComite;
  }

  public String getResumenProyecto() {
    return resumenProyecto;
  }

  public void setResumenProyecto(String resumenProyecto) {
    this.resumenProyecto = resumenProyecto;
  }

  public Long getIdLinea() {
    return idLinea;
  }

  public void setIdLinea(Long idLinea) {
    this.idLinea = idLinea;
  }

  public Long getIdUusuarioRol() {
    return idUusuarioRol;
  }

  public void setIdUusuarioRol(Long idUusuarioRol) {
    this.idUusuarioRol = idUusuarioRol;
  }

  public Long getIdPropuestaNecesidad() {
    return idPropuestaNecesidad;
  }

  public void setIdPropuestaNecesidad(Long idPropuestaNecesidad) {
    this.idPropuestaNecesidad = idPropuestaNecesidad;
  }

  public Long getIdPropuestaConvocatoria() {
    return idPropuestaConvocatoria;
  }

  public void setIdPropuestaConvocatoria(Long idPropuestaConvocatoria) {
    this.idPropuestaConvocatoria = idPropuestaConvocatoria;
  }

  public Long getIdProgramas() {
    return idProgramas;
  }

  public void setIdProgramas(Long idProgramas) {
    this.idProgramas = idProgramas;
  }

  public Long getIdEstadoImplementacion() {
    return idEstadoImplementacion;
  }

  public void setIdEstadoImplementacion(Long idEstadoImplementacion) {
    this.idEstadoImplementacion = idEstadoImplementacion;
  }

  public Long getIdProyectoVersion() {
    return idProyectoVersion;
  }

  public void setIdProyectoVersion(Long idProyectoVersion) {
    this.idProyectoVersion = idProyectoVersion;
  }

  public Long getIdVersion() {
    return idVersion;
  }

  public void setIdVersion(Long idVersion) {
    this.idVersion = idVersion;
  }

  public Long getIdTipoPeriodo() {
    return idTipoPeriodo;
  }

  public void setIdTipoPeriodo(Long idTipoPeriodo) {
    this.idTipoPeriodo = idTipoPeriodo;
  }

  public boolean isMostrarLinkActualizarProyecto() {
    return mostrarLinkActualizarProyecto;
  }

  public void setMostrarLinkActualizarProyecto(boolean mostrarLinkActualizarProyecto) {
    this.mostrarLinkActualizarProyecto = mostrarLinkActualizarProyecto;
  }

  public String getSiglaFisica() {
    return siglaFisica;
  }

  public void setSiglaFisica(String siglaFisica) {
    this.siglaFisica = siglaFisica;
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

    public SieduPropuestaAsignada getPropuestaAsignada() {
        return propuestaAsignada;
    }

    public void setPropuestaAsignada(SieduPropuestaAsignada propuestaAsignada) {
        this.propuestaAsignada = propuestaAsignada;
    }

}
