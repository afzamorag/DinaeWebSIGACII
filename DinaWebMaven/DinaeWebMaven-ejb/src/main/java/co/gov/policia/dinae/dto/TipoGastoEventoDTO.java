package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.TipoGastoEvento;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class TipoGastoEventoDTO implements IDataModel, Serializable {

  private Long idGastoEvento;
  private Long idTipoGasto;
  private String nombreTipoGasto;
  private Long idEventoProyecto;
  private String tituloEventoProyecto;
  private Date fechaRegistro;
  private Long idUsuarioRol;
  private String maquina;

  public TipoGastoEventoDTO() {
  }

  public TipoGastoEventoDTO(TipoGastoEvento t) {

    this.idGastoEvento = t.getIdGastoEvento();
    this.idTipoGasto = t.getTipoGasto().getIdConstantes();
    this.nombreTipoGasto = t.getTipoGasto().getValor();
    this.idEventoProyecto = t.getEventoProyecto().getIdEventoProyecto();
    this.tituloEventoProyecto = t.getEventoProyecto().getTituloEvento();
    this.fechaRegistro = t.getFechaRegistro();
    this.idUsuarioRol = t.getUsuarioRol().getIdUsuarioRol();
    this.maquina = t.getMaquina();
  }

  public TipoGastoEventoDTO(Long idGastoEvento, Long idTipoGasto, String nombreTipoGasto, Long idEventoProyecto, String tituloEventoProyecto, Date fechaRegistro, Long idUsuarioRol, String maquina) {
    this.idGastoEvento = idGastoEvento;
    this.idTipoGasto = idTipoGasto;
    this.nombreTipoGasto = nombreTipoGasto;
    this.idEventoProyecto = idEventoProyecto;
    this.tituloEventoProyecto = tituloEventoProyecto;
    this.fechaRegistro = fechaRegistro;
    this.idUsuarioRol = idUsuarioRol;
    this.maquina = maquina;
  }

  public Long getIdGastoEvento() {
    return idGastoEvento;
  }

  public void setIdGastoEvento(Long idGastoEvento) {
    this.idGastoEvento = idGastoEvento;
  }

  public Long getIdTipoGasto() {
    return idTipoGasto;
  }

  public void setIdTipoGasto(Long idTipoGasto) {
    this.idTipoGasto = idTipoGasto;
  }

  public String getNombreTipoGasto() {
    return nombreTipoGasto;
  }

  public void setNombreTipoGasto(String nombreTipoGasto) {
    this.nombreTipoGasto = nombreTipoGasto;
  }

  public Long getIdEventoProyecto() {
    return idEventoProyecto;
  }

  public void setIdEventoProyecto(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public String getTituloEventoProyecto() {
    return tituloEventoProyecto;
  }

  public void setTituloEventoProyecto(String tituloEventoProyecto) {
    this.tituloEventoProyecto = tituloEventoProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  @Override
  public String getLlaveModel() {
    if (this.idGastoEvento == null) {
      return null;
    }

    return this.idGastoEvento.toString();
  }

}
