package co.gov.policia.dinae.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Édder Javier Peña Barranco
 * @since 2013/11/23
 */
public class InvestigadorProyectoDTO implements Serializable {

  private Long idInvestigadorProyecto;

  private BigDecimal valorEspecie;

  private String identificacion;

  private String funcionProyecto;

  private Long horasDedicacion;

  private Character investigado3anios;

  private Character investigadoAnual;

  private BigDecimal valorHora;

  private String activo;

  private BigDecimal valorEfectivo;

  private String nombreCompleto;

  private String correo;

  private String grado;

  private String telefono;

  private String cargo;

  private Date fechaRegistro;

  private Long idProyecto;

  private Long idFuenteProyecto;

  private String fuenteProyecto;

  private Long idTipoVinculacion;

  private String tipoVinculacion;

  private Long idUnidadPolicial;

  private String unidadPolicial;

  private Long idTipoInvestigador;
  private String tipoInvestigador;
  private Integer horasInvestigacionTabajadasPeriodo;

  private String nombreFuenteFinanciera;

  private String codigoUnidadPolicial;

  public InvestigadorProyectoDTO() {
  }

  /**
   *
   * @param idInvestigadorProyecto
   * @param idTipoInvestigador
   * @param tipoInvestigador
   * @param grado
   * @param nombreCompleto
   */
  public InvestigadorProyectoDTO(Long idInvestigadorProyecto, Long idTipoInvestigador, String tipoInvestigador,
          String grado, String nombreCompleto) {

    this.idInvestigadorProyecto = idInvestigadorProyecto;
    this.idTipoInvestigador = idTipoInvestigador;
    this.tipoInvestigador = tipoInvestigador;
    this.grado = grado;
    this.nombreCompleto = nombreCompleto;
  }

  /**
   *
   * @param idInvestigadorProyecto
   * @param idTipoInvestigador
   * @param tipoInvestigador
   * @param grado
   * @param nombreCompleto
   * @param identificacion
   */
  public InvestigadorProyectoDTO(Long idInvestigadorProyecto, Long idTipoInvestigador, String tipoInvestigador,
          String grado, String nombreCompleto, String identificacion) {

    this.idInvestigadorProyecto = idInvestigadorProyecto;
    this.idTipoInvestigador = idTipoInvestigador;
    this.tipoInvestigador = tipoInvestigador;
    this.grado = grado;
    this.nombreCompleto = nombreCompleto;
    this.identificacion = identificacion;
  }

  private BigDecimal calculoTotalHora;

  public Long getIdInvestigadorProyecto() {
    return idInvestigadorProyecto;
  }

  public void setIdInvestigadorProyecto(Long idInvestigadorProyecto) {
    this.idInvestigadorProyecto = idInvestigadorProyecto;
  }

  public BigDecimal getValorEspecie() {
    return valorEspecie;
  }

  public void setValorEspecie(BigDecimal valorEspecie) {
    this.valorEspecie = valorEspecie;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getFuncionProyecto() {
    return funcionProyecto;
  }

  public void setFuncionProyecto(String funcionProyecto) {
    this.funcionProyecto = funcionProyecto;
  }

  public Long getHorasDedicacion() {
    return horasDedicacion;
  }

  public void setHorasDedicacion(Long horasDedicacion) {
    this.horasDedicacion = horasDedicacion;
  }

  public Character getInvestigado3anios() {
    return investigado3anios;
  }

  public void setInvestigado3anios(Character investigado3anios) {
    this.investigado3anios = investigado3anios;
  }

  public Character getInvestigadoAnual() {
    return investigadoAnual;
  }

  public void setInvestigadoAnual(Character investigadoAnual) {
    this.investigadoAnual = investigadoAnual;
  }

  public BigDecimal getValorHora() {
    return valorHora;
  }

  public void setValorHora(BigDecimal valorHora) {
    this.valorHora = valorHora;
  }

  public String getActivo() {
    return activo;
  }

  public void setActivo(String activo) {
    this.activo = activo;
  }

  public BigDecimal getValorEfectivo() {
    return valorEfectivo;
  }

  public void setValorEfectivo(BigDecimal valorEfectivo) {
    this.valorEfectivo = valorEfectivo;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(String fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
  }

  public Long getIdTipoVinculacion() {
    return idTipoVinculacion;
  }

  public void setIdTipoVinculacion(Long idTipoVinculacion) {
    this.idTipoVinculacion = idTipoVinculacion;
  }

  public String getTipoVinculacion() {
    return tipoVinculacion;
  }

  public void setTipoVinculacion(String tipoVinculacion) {
    this.tipoVinculacion = tipoVinculacion;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(String unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + (this.idInvestigadorProyecto != null ? this.idInvestigadorProyecto.hashCode() : 0);
    hash = 47 * hash + (this.valorEspecie != null ? this.valorEspecie.hashCode() : 0);
    hash = 47 * hash + (this.identificacion != null ? this.identificacion.hashCode() : 0);
    hash = 47 * hash + (this.funcionProyecto != null ? this.funcionProyecto.hashCode() : 0);
    hash = 47 * hash + (this.horasDedicacion != null ? this.horasDedicacion.hashCode() : 0);
    hash = 47 * hash + (this.investigado3anios != null ? this.investigado3anios.hashCode() : 0);
    hash = 47 * hash + (this.investigadoAnual != null ? this.investigadoAnual.hashCode() : 0);
    hash = 47 * hash + (this.valorHora != null ? this.valorHora.hashCode() : 0);
    hash = 47 * hash + (this.activo != null ? this.activo.hashCode() : 0);
    hash = 47 * hash + (this.valorEfectivo != null ? this.valorEfectivo.hashCode() : 0);
    hash = 47 * hash + (this.nombreCompleto != null ? this.nombreCompleto.hashCode() : 0);
    hash = 47 * hash + (this.correo != null ? this.correo.hashCode() : 0);
    hash = 47 * hash + (this.grado != null ? this.grado.hashCode() : 0);
    hash = 47 * hash + (this.telefono != null ? this.telefono.hashCode() : 0);
    hash = 47 * hash + (this.cargo != null ? this.cargo.hashCode() : 0);
    hash = 47 * hash + (this.fechaRegistro != null ? this.fechaRegistro.hashCode() : 0);
    hash = 47 * hash + (this.idProyecto != null ? this.idProyecto.hashCode() : 0);
    hash = 47 * hash + (this.idFuenteProyecto != null ? this.idFuenteProyecto.hashCode() : 0);
    hash = 47 * hash + (this.fuenteProyecto != null ? this.fuenteProyecto.hashCode() : 0);
    hash = 47 * hash + (this.idTipoVinculacion != null ? this.idTipoVinculacion.hashCode() : 0);
    hash = 47 * hash + (this.tipoVinculacion != null ? this.tipoVinculacion.hashCode() : 0);
    hash = 47 * hash + (this.idUnidadPolicial != null ? this.idUnidadPolicial.hashCode() : 0);
    hash = 47 * hash + (this.unidadPolicial != null ? this.unidadPolicial.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final InvestigadorProyectoDTO other = (InvestigadorProyectoDTO) obj;
    if (this.idInvestigadorProyecto != other.idInvestigadorProyecto && (this.idInvestigadorProyecto == null || !this.idInvestigadorProyecto.equals(other.idInvestigadorProyecto))) {
      return false;
    }
    if (this.valorEspecie != other.valorEspecie && (this.valorEspecie == null || !this.valorEspecie.equals(other.valorEspecie))) {
      return false;
    }
    if ((this.identificacion == null) ? (other.identificacion != null) : !this.identificacion.equals(other.identificacion)) {
      return false;
    }
    if ((this.funcionProyecto == null) ? (other.funcionProyecto != null) : !this.funcionProyecto.equals(other.funcionProyecto)) {
      return false;
    }
    if (this.horasDedicacion != other.horasDedicacion && (this.horasDedicacion == null || !this.horasDedicacion.equals(other.horasDedicacion))) {
      return false;
    }
    if ((this.investigado3anios == null) ? (other.investigado3anios != null) : !this.investigado3anios.equals(other.investigado3anios)) {
      return false;
    }
    if ((this.investigadoAnual == null) ? (other.investigadoAnual != null) : !this.investigadoAnual.equals(other.investigadoAnual)) {
      return false;
    }
    if (this.valorHora != other.valorHora && (this.valorHora == null || !this.valorHora.equals(other.valorHora))) {
      return false;
    }
    if ((this.activo == null) ? (other.activo != null) : !this.activo.equals(other.activo)) {
      return false;
    }
    if (this.valorEfectivo != other.valorEfectivo && (this.valorEfectivo == null || !this.valorEfectivo.equals(other.valorEfectivo))) {
      return false;
    }
    if ((this.nombreCompleto == null) ? (other.nombreCompleto != null) : !this.nombreCompleto.equals(other.nombreCompleto)) {
      return false;
    }
    if ((this.correo == null) ? (other.correo != null) : !this.correo.equals(other.correo)) {
      return false;
    }
    if ((this.grado == null) ? (other.grado != null) : !this.grado.equals(other.grado)) {
      return false;
    }
    if ((this.telefono == null) ? (other.telefono != null) : !this.telefono.equals(other.telefono)) {
      return false;
    }
    if ((this.cargo == null) ? (other.cargo != null) : !this.cargo.equals(other.cargo)) {
      return false;
    }
    if (this.fechaRegistro != other.fechaRegistro && (this.fechaRegistro == null || !this.fechaRegistro.equals(other.fechaRegistro))) {
      return false;
    }
    if (this.idProyecto != other.idProyecto && (this.idProyecto == null || !this.idProyecto.equals(other.idProyecto))) {
      return false;
    }
    if (this.idFuenteProyecto != other.idFuenteProyecto && (this.idFuenteProyecto == null || !this.idFuenteProyecto.equals(other.idFuenteProyecto))) {
      return false;
    }
    if ((this.fuenteProyecto == null) ? (other.fuenteProyecto != null) : !this.fuenteProyecto.equals(other.fuenteProyecto)) {
      return false;
    }
    if (this.idTipoVinculacion != other.idTipoVinculacion && (this.idTipoVinculacion == null || !this.idTipoVinculacion.equals(other.idTipoVinculacion))) {
      return false;
    }
    if ((this.tipoVinculacion == null) ? (other.tipoVinculacion != null) : !this.tipoVinculacion.equals(other.tipoVinculacion)) {
      return false;
    }
    if (this.idUnidadPolicial != other.idUnidadPolicial && (this.idUnidadPolicial == null || !this.idUnidadPolicial.equals(other.idUnidadPolicial))) {
      return false;
    }
    if ((this.unidadPolicial == null) ? (other.unidadPolicial != null) : !this.unidadPolicial.equals(other.unidadPolicial)) {
      return false;
    }
    return true;
  }

  public void setCalculoTotalHoras(BigDecimal calculoTotalHora) {
    this.calculoTotalHora = calculoTotalHora;
  }

  public BigDecimal getCalculoTotalHora() {
    return calculoTotalHora;
  }

  public Long getIdTipoInvestigador() {
    return idTipoInvestigador;
  }

  public void setIdTipoInvestigador(Long idTipoInvestigador) {
    this.idTipoInvestigador = idTipoInvestigador;
  }

  public String getTipoInvestigador() {
    return tipoInvestigador;
  }

  public void setTipoInvestigador(String tipoInvestigador) {
    this.tipoInvestigador = tipoInvestigador;
  }

  public Integer getHorasInvestigacionTabajadasPeriodo() {
    return horasInvestigacionTabajadasPeriodo;
  }

  public void setHorasInvestigacionTabajadasPeriodo(Integer horasInvestigacionTabajadasPeriodo) {
    this.horasInvestigacionTabajadasPeriodo = horasInvestigacionTabajadasPeriodo;
  }

  public String getNombreFuenteFinanciera() {
    return nombreFuenteFinanciera;
  }

  public void setNombreFuenteFinanciera(String nombreFuenteFinanciera) {
    this.nombreFuenteFinanciera = nombreFuenteFinanciera;
  }

  public String getCodigoUnidadPolicial() {
    return codigoUnidadPolicial;
  }

  public void setCodigoUnidadPolicial(String codigoUnidadPolicial) {
    this.codigoUnidadPolicial = codigoUnidadPolicial;
  }

}
