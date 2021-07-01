package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cguzman
 */
public class EventoProyectoDTO implements IDataModel, Serializable {

  private Long idEventoProyecto;
  private BigDecimal costo;
  private String tituloEvento;
  private String objetivoEvento;
  private String codigoCiudad;
  private String nombreCiudad;
  private Date fechaRegistro;
  private Long idFuenteProyecto;
  private String nombreFuenteProyecto;
  private Long idProyecto;
  private String tituloPropuesto;
  private Long idUsuarioRol;
  private List<TipoGastoEventoDTO> gastoEventoList;
  private String tipoGastoEvento;
  private boolean seleccionable;

  /**
   *
   * @param idEventoProyecto
   * @param costo
   * @param tituloEvento
   * @param objetivoEvento
   * @param codigoCiudad
   * @param nombreCiudad
   * @param fechaRegistro
   * @param idFuenteProyecto
   * @param nombreFuenteProyecto
   * @param idProyecto
   * @param tituloPropuesto
   * @param idUsuarioRol
   */
  public EventoProyectoDTO(Long idEventoProyecto, BigDecimal costo, String tituloEvento, String objetivoEvento, String codigoCiudad, String nombreCiudad, Date fechaRegistro, Long idFuenteProyecto, String nombreFuenteProyecto, Long idProyecto, String tituloPropuesto, Long idUsuarioRol) {
    this.idEventoProyecto = idEventoProyecto;
    this.costo = costo;
    this.tituloEvento = tituloEvento;
    this.objetivoEvento = objetivoEvento;
    this.codigoCiudad = codigoCiudad;
    this.nombreCiudad = nombreCiudad;
    this.fechaRegistro = fechaRegistro;
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuenteProyecto = nombreFuenteProyecto;
    this.idProyecto = idProyecto;
    this.tituloPropuesto = tituloPropuesto;
    this.idUsuarioRol = idUsuarioRol;
  }

  /**
   *
   * @param idEventoProyecto
   * @param costo
   * @param tituloEvento
   * @param objetivoEvento
   * @param codigoCiudad
   * @param nombreCiudad
   * @param fechaRegistro
   * @param idFuenteProyecto
   * @param nombreFuenteProyecto
   * @param idUsuarioRol
   */
  public EventoProyectoDTO(Long idEventoProyecto, BigDecimal costo, String tituloEvento, String objetivoEvento, String codigoCiudad, String nombreCiudad, Date fechaRegistro, Long idFuenteProyecto, String nombreFuenteProyecto, Long idUsuarioRol) {
    this.idEventoProyecto = idEventoProyecto;
    this.costo = costo;
    this.tituloEvento = tituloEvento;
    this.objetivoEvento = objetivoEvento;
    this.codigoCiudad = codigoCiudad;
    this.nombreCiudad = nombreCiudad;
    this.fechaRegistro = fechaRegistro;
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuenteProyecto = nombreFuenteProyecto;
    this.idUsuarioRol = idUsuarioRol;
  }

  public Long getIdEventoProyecto() {
    return idEventoProyecto;
  }

  public void setIdEventoProyecto(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public BigDecimal getCosto() {
    return costo;
  }

  public void setCosto(BigDecimal costo) {
    this.costo = costo;
  }

  public String getTituloEvento() {
    return tituloEvento;
  }

  public void setTituloEvento(String tituloEvento) {
    this.tituloEvento = tituloEvento;
  }

  public String getObjetivoEvento() {
    return objetivoEvento;
  }

  public void setObjetivoEvento(String objetivoEvento) {
    this.objetivoEvento = objetivoEvento;
  }

  public String getCodigoCiudad() {
    return codigoCiudad;
  }

  public void setCodigoCiudad(String codigoCiudad) {
    this.codigoCiudad = codigoCiudad;
  }

  public String getNombreCiudad() {
    return nombreCiudad;
  }

  public void setNombreCiudad(String nombreCiudad) {
    this.nombreCiudad = nombreCiudad;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getNombreFuenteProyecto() {
    return nombreFuenteProyecto;
  }

  public void setNombreFuenteProyecto(String nombreFuenteProyecto) {
    this.nombreFuenteProyecto = nombreFuenteProyecto;
  }

  public Long getIdProyecto() {
    return idProyecto;
  }

  public void setIdProyecto(Long idProyecto) {
    this.idProyecto = idProyecto;
  }

  public String getTituloPropuesto() {
    return tituloPropuesto;
  }

  public void setTituloPropuesto(String tituloPropuesto) {
    this.tituloPropuesto = tituloPropuesto;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public List<TipoGastoEventoDTO> getGastoEventoList() {
    return gastoEventoList;
  }

  public void setGastoEventoList(List<TipoGastoEventoDTO> gastoEventoList) {
    this.gastoEventoList = gastoEventoList;
  }

  public String getTipoGastoEvento() {
    return tipoGastoEvento;
  }

  public void setTipoGastoEvento(String tipoGastoEvento) {
    this.tipoGastoEvento = tipoGastoEvento;
  }

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  @Override
  public String getLlaveModel() {
    if (this.idEventoProyecto == null) {
      return null;
    }

    return this.idEventoProyecto.toString();
  }

}
