package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class FuenteProyectoDTO implements IDataModel, Serializable {

  private Long idFuenteProyecto;

  private String nombreFuente;

  private Character fuenteBase;

  private Long idUsuarioRol;

  private String identificacionUsuario;

  private Long idRol;

  private Long idProyecto;

  private String codigoProyecto;

  private String tituloProyecto;

  private Long idTipoFuente;

  private String valorTipoFuente;

  private Date fechaRegistro;

  private boolean muestraLink;

  private Long idPlanTrabajo;

  private Long idImplementacionesProyecto;

  public FuenteProyectoDTO() {
  }

  public FuenteProyectoDTO(Long idFuenteProyecto, String nombreFuente, Character fuenteBase, Long idUsuarioRol, String identificacionUsuario, Long idRol, Long idProyecto, String codigoProyecto, String tituloProyecto, Long idTipoFuente, String valorTipoFuente, Date fechaRegistro) {
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuente = nombreFuente;
    this.fuenteBase = fuenteBase;
    this.idUsuarioRol = idUsuarioRol;
    this.identificacionUsuario = identificacionUsuario;
    this.idRol = idRol;
    this.idProyecto = idProyecto;
    this.codigoProyecto = codigoProyecto;
    this.tituloProyecto = tituloProyecto;
    this.idTipoFuente = idTipoFuente;
    this.valorTipoFuente = valorTipoFuente;
    this.fechaRegistro = fechaRegistro;
  }

  public FuenteProyectoDTO(Long idFuenteProyecto, String nombreFuente, Character fuenteBase, Long idUsuarioRol, String identificacionUsuario, Long idRol, Long idPlanTrabajo, Long idImplementacionesProyecto, Long idProyecto, String codigoProyecto, String tituloProyecto, Long idTipoFuente, String valorTipoFuente, Date fechaRegistro) {
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuente = nombreFuente;
    this.fuenteBase = fuenteBase;
    this.idUsuarioRol = idUsuarioRol;
    this.identificacionUsuario = identificacionUsuario;
    this.idRol = idRol;
    this.idProyecto = idProyecto;
    this.codigoProyecto = codigoProyecto;
    this.tituloProyecto = tituloProyecto;
    this.idTipoFuente = idTipoFuente;
    this.valorTipoFuente = valorTipoFuente;
    this.fechaRegistro = fechaRegistro;
    this.idPlanTrabajo = idPlanTrabajo;
    this.idImplementacionesProyecto = idImplementacionesProyecto;
  }

  public Long getIdFuenteProyecto() {
    return idFuenteProyecto;
  }

  public void setIdFuenteProyecto(Long idFuenteProyecto) {
    this.idFuenteProyecto = idFuenteProyecto;
  }

  public String getNombreFuente() {
    return nombreFuente;
  }

  public void setNombreFuente(String nombreFuente) {
    this.nombreFuente = nombreFuente;
  }

  public Character getFuenteBase() {
    return fuenteBase;
  }

  public void setFuenteBase(Character fuenteBase) {
    this.fuenteBase = fuenteBase;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public String getIdentificacionUsuario() {
    return identificacionUsuario;
  }

  public void setIdentificacionUsuario(String identificacionUsuario) {
    this.identificacionUsuario = identificacionUsuario;
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
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

  public String getTituloProyecto() {
    return tituloProyecto;
  }

  public void setTituloProyecto(String tituloProyecto) {
    this.tituloProyecto = tituloProyecto;
  }

  public Long getIdTipoFuente() {
    return idTipoFuente;
  }

  public void setIdTipoFuente(Long idTipoFuente) {
    this.idTipoFuente = idTipoFuente;
  }

  public String getValorTipoFuente() {
    return valorTipoFuente;
  }

  public void setValorTipoFuente(String valorTipoFuente) {
    this.valorTipoFuente = valorTipoFuente;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public boolean isMuestraLink() {
    if (idTipoFuente != null) {
      muestraLink = (idTipoFuente.compareTo(IConstantes.ID_TIPO_FUENTE_FINANCIERA_EXTERNA) == 0
              || (idTipoFuente.compareTo(IConstantes.ID_TIPO_FUENTE_FINANCIERA_INTERNA) == 0 && fuenteBase.compareTo('N') == 0));
    }
    return muestraLink;
  }

  public void setMuestraLink(boolean muestraLink) {
    this.muestraLink = muestraLink;
  }

  @Override
  public String getLlaveModel() {
    if (this.idFuenteProyecto == null) {
      return null;
    }

    return this.idFuenteProyecto.toString();
  }

  public Long getIdPlanTrabajo() {
    return idPlanTrabajo;
  }

  public void setIdPlanTrabajo(Long idPlanTrabajo) {
    this.idPlanTrabajo = idPlanTrabajo;
  }

  public Long getIdImplementacionesProyecto() {
    return idImplementacionesProyecto;
  }

  public void setIdImplementacionesProyecto(Long idImplementacionesProyecto) {
    this.idImplementacionesProyecto = idImplementacionesProyecto;
  }

}
