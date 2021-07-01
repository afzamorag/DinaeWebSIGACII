package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author cguzman
 */
public class ViajesProyectoDTO implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;

  private Long idViajeProyecto;

  private String evento;

  private BigDecimal costosPasajes;

  private BigDecimal costosViaticos;

  private Long idInvestigadoresProyecto;

  private String nombreInvestigadoresProyecto;

  private String grado;

  private Long idFuenteProyecto;

  private Long idProyecto;

  private String nombreFuenteProyecto;

  private String codigoCiudadOrigen;

  private String nombreCiudadOrigen;

  private String codigoCiudadDestino;

  private String nombreCiudadDestino;

  private boolean seleccionable;

  /**
   *
   * @param idViajeProyecto
   * @param evento
   * @param costosPasajes
   * @param costosViaticos
   * @param idInvestigadoresProyecto
   * @param nombreInvestigadoresProyecto
   * @param idFuenteProyecto
   * @param idProyecto
   * @param nombreFuenteProyecto
   * @param codigoCiudadOrigen
   * @param nombreCiudadOrigen
   * @param codigoCiudadDestino
   * @param nombreCiudadDestino
   */
  public ViajesProyectoDTO(Long idViajeProyecto, String evento, BigDecimal costosPasajes, BigDecimal costosViaticos, Long idInvestigadoresProyecto, String nombreInvestigadoresProyecto, String grado, Long idFuenteProyecto, Long idProyecto, String nombreFuenteProyecto, String codigoCiudadOrigen, String nombreCiudadOrigen, String codigoCiudadDestino, String nombreCiudadDestino) {
    this.idViajeProyecto = idViajeProyecto;
    this.evento = evento;
    this.costosPasajes = costosPasajes;
    this.costosViaticos = costosViaticos;
    this.idInvestigadoresProyecto = idInvestigadoresProyecto;
    this.nombreInvestigadoresProyecto = nombreInvestigadoresProyecto;
    this.idFuenteProyecto = idFuenteProyecto;
    this.idProyecto = idProyecto;
    this.nombreFuenteProyecto = nombreFuenteProyecto;
    this.codigoCiudadOrigen = codigoCiudadOrigen;
    this.nombreCiudadOrigen = nombreCiudadOrigen;
    this.codigoCiudadDestino = codigoCiudadDestino;
    this.nombreCiudadDestino = nombreCiudadDestino;
    this.grado = grado;
  }

  /**
   *
   * @param idViajeProyecto
   * @param evento
   * @param costosPasajes
   * @param costosViaticos
   * @param idInvestigadoresProyecto
   * @param nombreInvestigadoresProyecto
   * @param grado
   * @param idFuenteProyecto
   * @param nombreFuenteProyecto
   * @param codigoCiudadOrigen
   * @param nombreCiudadOrigen
   * @param codigoCiudadDestino
   * @param nombreCiudadDestino
   */
  public ViajesProyectoDTO(Long idViajeProyecto, String evento, BigDecimal costosPasajes, BigDecimal costosViaticos, Long idInvestigadoresProyecto, String nombreInvestigadoresProyecto, String grado, Long idFuenteProyecto, String nombreFuenteProyecto, String codigoCiudadOrigen, String nombreCiudadOrigen, String codigoCiudadDestino, String nombreCiudadDestino) {
    this.idViajeProyecto = idViajeProyecto;
    this.evento = evento;
    this.costosPasajes = costosPasajes;
    this.costosViaticos = costosViaticos;
    this.idInvestigadoresProyecto = idInvestigadoresProyecto;
    this.nombreInvestigadoresProyecto = nombreInvestigadoresProyecto;
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuenteProyecto = nombreFuenteProyecto;
    this.codigoCiudadOrigen = codigoCiudadOrigen;
    this.nombreCiudadOrigen = nombreCiudadOrigen;
    this.codigoCiudadDestino = codigoCiudadDestino;
    this.nombreCiudadDestino = nombreCiudadDestino;
    this.grado = grado;
  }

  public ViajesProyectoDTO(ViajesProyecto viajesProyecto) {

    this.idViajeProyecto = viajesProyecto.getIdViajeProyecto();
    this.evento = viajesProyecto.getEvento();
    this.costosPasajes = viajesProyecto.getCostosPasajes();
    this.costosViaticos = viajesProyecto.getCostosViaticos();
    this.idInvestigadoresProyecto = viajesProyecto.getInvestigadoresProyecto().getIdInvestigadorProyecto();
    this.nombreInvestigadoresProyecto = viajesProyecto.getInvestigadoresProyecto().getNombreCompleto();
    this.idFuenteProyecto = viajesProyecto.getFuenteProyecto().getIdFuenteProyecto();
    this.idProyecto = viajesProyecto.getFuenteProyecto().getProyecto().getIdProyecto();
    this.nombreFuenteProyecto = viajesProyecto.getFuenteProyecto().getNombreFuente();
    this.codigoCiudadOrigen = viajesProyecto.getCodigoCiudadOrigen();
    this.nombreCiudadOrigen = viajesProyecto.getNombreCiudadOrigen();
    this.codigoCiudadDestino = viajesProyecto.getCodigoCiudadDestino();
    this.nombreCiudadDestino = viajesProyecto.getNombreCiudadDestino();
    this.grado = viajesProyecto.getInvestigadoresProyecto().getGrado();
  }

  public Long getIdViajeProyecto() {
    return idViajeProyecto;
  }

  public void setIdViajeProyecto(Long idViajeProyecto) {
    this.idViajeProyecto = idViajeProyecto;
  }

  public String getEvento() {
    return evento;
  }

  public void setEvento(String evento) {
    this.evento = evento;
  }

  public BigDecimal getCostosPasajes() {
    return costosPasajes;
  }

  public void setCostosPasajes(BigDecimal costosPasajes) {
    this.costosPasajes = costosPasajes;
  }

  public BigDecimal getCostosViaticos() {
    return costosViaticos;
  }

  public void setCostosViaticos(BigDecimal costosViaticos) {
    this.costosViaticos = costosViaticos;
  }

  public Long getIdInvestigadoresProyecto() {
    return idInvestigadoresProyecto;
  }

  public void setIdInvestigadoresProyecto(Long idInvestigadoresProyecto) {
    this.idInvestigadoresProyecto = idInvestigadoresProyecto;
  }

  public String getNombreInvestigadoresProyecto() {
    return nombreInvestigadoresProyecto;
  }

  public void setNombreInvestigadoresProyecto(String nombreInvestigadoresProyecto) {
    this.nombreInvestigadoresProyecto = nombreInvestigadoresProyecto;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getNombreFuenteProyecto() {
    return nombreFuenteProyecto;
  }

  public void setNombreFuenteProyecto(String nombreFuenteProyecto) {
    this.nombreFuenteProyecto = nombreFuenteProyecto;
  }

  public String getCodigoCiudadOrigen() {
    return codigoCiudadOrigen;
  }

  public void setCodigoCiudadOrigen(String codigoCiudadOrigen) {
    this.codigoCiudadOrigen = codigoCiudadOrigen;
  }

  public String getNombreCiudadOrigen() {
    return nombreCiudadOrigen;
  }

  public void setNombreCiudadOrigen(String nombreCiudadOrigen) {
    this.nombreCiudadOrigen = nombreCiudadOrigen;
  }

  public String getCodigoCiudadDestino() {
    return codigoCiudadDestino;
  }

  public void setCodigoCiudadDestino(String codigoCiudadDestino) {
    this.codigoCiudadDestino = codigoCiudadDestino;
  }

  public String getNombreCiudadDestino() {
    return nombreCiudadDestino;
  }

  public void setNombreCiudadDestino(String nombreCiudadDestino) {
    this.nombreCiudadDestino = nombreCiudadDestino;
  }

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  @Override
  public String getLlaveModel() {
    if (this.idViajeProyecto == null) {
      return null;
    }

    return this.idViajeProyecto.toString();
  }

}
