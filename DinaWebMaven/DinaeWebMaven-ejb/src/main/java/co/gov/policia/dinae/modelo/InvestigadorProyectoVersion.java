package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IDataModel;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "INVESTIGADORES_PROY_VERSION")
@NamedQueries({
  @javax.persistence.NamedQuery(name = "InvestigadorProyectoVersion.findAllPorProyecto", query = "SELECT i FROM InvestigadorProyectoVersion i WHERE i.proyectoVersion.idProyectoVersion = :idProyectoVersion")
})
public class InvestigadorProyectoVersion implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_INVESTIGADOR_PROYECTO")
  private Long idInvestigadorProyecto;

  @Column(name = "VALOR_ESPECIE", scale = 2)
  private BigDecimal valorEspecie;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "FUNCION_PROYECTO")
  private String funcionProyecto;

  @Column(name = "HORAS_DEDICACION", precision = 3, scale = 0)
  private Long horasDedicacion;

  @Column(name = "INVESTIGADO_3ANIOS")
  private Character investigado3anios;

  @Column(name = "INVESTIGADO_ANUAL")
  private Character investigadoAnual;

  @Column(name = "VALOR_HORA", scale = 2)
  private BigDecimal valorHora;

  @Column(name = "ACTIVO")
  private Character activo;

  @Column(name = "VALOR_EFECTIVO", scale = 2)
  private BigDecimal valorEfectivo;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "CARGO")
  private String cargo;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "CALCULO_HORA_TOTAL")
  private BigDecimal calculoTotalHora;

  @Column(name = "INSTITUCION_EXTERNA")
  private String institucionExterna;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProyectoVersion proyectoVersion;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private FuenteProyecto fuenteProyecto;

  @JoinColumn(name = "ID_TIPO_VINCULACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.LAZY)
  private Constantes tipoVinculacion;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_TIPO_INVESTIGADOR", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoInvestigador;

  @JoinColumn(name = "ID_PLAN_TRABAJO", referencedColumnName = "ID_PLAN_TRABAJO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private PlanTrabajoImplementacion planTrabajoImplementacion;

  @Transient
  private String descripcionActivo;

  @Transient
  private String nombreUnidadPolicial;

  @Transient
  private String codigoUnidadPolicial;

  @Column(name = "ORIGEN_SIAF_O_INVESTI")
  private Character origenSiafOinvestigador;

  @Column(name = "HORAS_TOTALES_IMPL")
  private BigDecimal horasTotalesImplementacion;

  @Column(name = "HORAS_DEDICADA_AVANCE_IMPL")
  private BigDecimal horasDedicadasImplementacion;

  @Column(name = "HORAS_DEDICADA_AVANC_FIN_IMPL")
  private BigDecimal horasDedicadasAvanceFinalImplementacion;

  @Transient
  private String fuenteAsociada;

  public InvestigadorProyectoVersion() {
  }

  public InvestigadorProyectoVersion(Long idInvestigadorProyecto) {
    this.idInvestigadorProyecto = idInvestigadorProyecto;
  }

  public Long getIdInvestigadorProyecto() {
    return this.idInvestigadorProyecto;
  }

  public void setIdInvestigadorProyecto(Long idInvestigadorProyecto) {
    this.idInvestigadorProyecto = idInvestigadorProyecto;
  }

  public BigDecimal getValorEspecie() {
    return this.valorEspecie;
  }

  public void setValorEspecie(BigDecimal valorEspecie) {
    this.valorEspecie = valorEspecie;
  }

  public String getIdentificacion() {
    return this.identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getFuncionProyecto() {
    return this.funcionProyecto;
  }

  public void setFuncionProyecto(String funcionProyecto) {
    this.funcionProyecto = funcionProyecto;
  }

  public Long getHorasDedicacion() {
    return this.horasDedicacion;
  }

  public void setHorasDedicacion(Long horasDedicacion) {
    this.horasDedicacion = horasDedicacion;
  }

  public Character getInvestigado3anios() {
    return this.investigado3anios;
  }

  public void setInvestigado3anios(Character investigado3anios) {
    this.investigado3anios = investigado3anios;
  }

  public Character getInvestigadoAnual() {
    return this.investigadoAnual;
  }

  public void setInvestigadoAnual(Character investigadoAnual) {
    this.investigadoAnual = investigadoAnual;
  }

  public BigDecimal getValorHora() {
    return this.valorHora;
  }

  public void setValorHora(BigDecimal valorHora) {
    this.valorHora = valorHora;
  }

  public Character getActivo() {
    return this.activo;
  }

  public void setActivo(Character activo) {
    this.activo = activo;
  }

  public BigDecimal getValorEfectivo() {
    return this.valorEfectivo;
  }

  public void setValorEfectivo(BigDecimal valorEfectivo) {
    this.valorEfectivo = valorEfectivo;
  }

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
  }

  public FuenteProyecto getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(FuenteProyecto fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
  }

  public String getNombreCompleto() {
    return this.nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getCorreo() {
    return this.correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getGrado() {
    return this.grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCargo() {
    return this.cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.InvestigadoresProyecto[ idInvestigadorProyecto=" + this.idInvestigadorProyecto + " ]";
  }

  public Constantes getTipoVinculacion() {
    return this.tipoVinculacion;
  }

  public void setTipoVinculacion(Constantes tipoVinculacion) {
    this.tipoVinculacion = tipoVinculacion;
  }

  @Override
  public String getLlaveModel() {

    if (idInvestigadorProyecto == null) {

      return identificacion;

    }

    return String.valueOf(idInvestigadorProyecto);
  }

  public String getDescripcionActivo() {
    if (this.activo == null) {
      descripcionActivo = "";
      return descripcionActivo;
    }
    descripcionActivo = this.activo.equals(IConstantes.FUNCIONARIO_INVESTIGADOR_ACTIVO) ? "Activo" : "Inactivo";
    return descripcionActivo;
  }

  public Date getFechaRegistro() {
    return this.fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public BigDecimal getCalculoTotalHora() {
    return calculoTotalHora;
  }

  public void setCalculoTotalHora(BigDecimal calculoTotalHora) {
    this.calculoTotalHora = calculoTotalHora;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Constantes getTipoInvestigador() {
    return tipoInvestigador;
  }

  public void setTipoInvestigador(Constantes tipoInvestigador) {
    this.tipoInvestigador = tipoInvestigador;
  }

  /**
   * @return the institucionExterna
   */
  public String getInstitucionExterna() {
    return institucionExterna;
  }

  /**
   * @param institucionExterna the institucionExterna to set
   */
  public void setInstitucionExterna(String institucionExterna) {
    this.institucionExterna = institucionExterna;
  }

  public Character getOrigenSiafOinvestigador() {
    return origenSiafOinvestigador;
  }

  public void setOrigenSiafOinvestigador(Character origenSiafOinvestigador) {
    this.origenSiafOinvestigador = origenSiafOinvestigador;
  }

  public String getNombreUnidadPolicial() {

    if (unidadPolicial == null) {

      nombreUnidadPolicial = "";

    } else {

      nombreUnidadPolicial = unidadPolicial.getNombre();
    }

    return nombreUnidadPolicial;
  }

  public String getCodigoUnidadPolicial() {

    if (unidadPolicial == null) {

      codigoUnidadPolicial = "";

    } else {

      codigoUnidadPolicial = unidadPolicial.getSiglaFisica();
    }

    return codigoUnidadPolicial;

  }

  public PlanTrabajoImplementacion getPlanTrabajoImplementacion() {
    return planTrabajoImplementacion;
  }

  public void setPlanTrabajoImplementacion(PlanTrabajoImplementacion planTrabajoImplementacion) {
    this.planTrabajoImplementacion = planTrabajoImplementacion;
  }

  public BigDecimal getHorasTotalesImplementacion() {
    return horasTotalesImplementacion;
  }

  public void setHorasTotalesImplementacion(BigDecimal horasTotalesImplementacion) {
    this.horasTotalesImplementacion = horasTotalesImplementacion;
  }

  public BigDecimal getHorasDedicadasImplementacion() {
    return horasDedicadasImplementacion;
  }

  public void setHorasDedicadasImplementacion(BigDecimal horasDedicadasImplementacion) {
    this.horasDedicadasImplementacion = horasDedicadasImplementacion;
  }

  public BigDecimal getHorasDedicadasAvanceFinalImplementacion() {
    return horasDedicadasAvanceFinalImplementacion;
  }

  public void setHorasDedicadasAvanceFinalImplementacion(BigDecimal horasDedicadasAvanceFinalImplementacion) {
    this.horasDedicadasAvanceFinalImplementacion = horasDedicadasAvanceFinalImplementacion;
  }

  public String getFuenteAsociada() {
    return fuenteAsociada;
  }

  public void setFuenteAsociada(String fuenteAsociada) {
    this.fuenteAsociada = fuenteAsociada;
  }

}
