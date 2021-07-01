package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.RecursoHumanoSemillero;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class RecursoHumanoSemilleroDTO implements Serializable, IDataModel {

  private Long idRecursoHumanoSemi;
  private String identificacion;
  private String cargo;
  private String telefono;
  private String correoElectronico;
  private String nombres;
  private String compania;
  private String cursoOPromocion;
  private Date fechaIncio;
  private Date fechaFin;
  private String estado;
  private Date fechaRegistro;
  private Date fechaInactiva;
  private String usuarioRegistra;
  private String maquina;
  private Date fechaActualizacion;
  private String usuarioActualiza;
  private String maquinaActualiza;
  private Long idSemillero;
  private Long idTipoRelacionSemillero;
  private String valorTipoRelacionSemillero;
  private Long idTipoVinculacion;
  private String valorTipoVinculacion;
  private Long idUsuarioRol;
  private Long idUsuarioRolActualiza;
  private String grado;

  /**
   *
   */
  public RecursoHumanoSemilleroDTO() {
  }

  /**
   *
   * @param idRecursoHumanoSemi
   * @param identificacion
   * @param cargo
   * @param telefono
   * @param correoElectronico
   * @param nombres
   * @param compania
   * @param cursoOPromocion
   * @param fechaIncio
   * @param fechaFin
   * @param estado
   * @param fechaRegistro
   * @param fechaInactiva
   * @param usuarioRegistra
   * @param maquina
   * @param fechaActualizacion
   * @param usuarioActualiza
   * @param maquinaActualiza
   * @param idSemillero
   * @param idTipoRelacionSemillero
   * @param valorTipoRelacionSemillero
   * @param idTipoVinculacion
   * @param valorTipoVinculacion
   * @param idUsuarioRol
   * @param idUsuarioRolActualiza
   */
  public RecursoHumanoSemilleroDTO(Long idRecursoHumanoSemi, String identificacion, String cargo, String telefono, String correoElectronico, String nombres, String compania, String cursoOPromocion, Date fechaIncio, Date fechaFin, String estado, Date fechaRegistro, Date fechaInactiva, String usuarioRegistra, String maquina, Date fechaActualizacion, String usuarioActualiza, String maquinaActualiza, Long idSemillero, Long idTipoRelacionSemillero, String valorTipoRelacionSemillero, Long idTipoVinculacion, String valorTipoVinculacion, Long idUsuarioRol, Long idUsuarioRolActualiza) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
    this.identificacion = identificacion;
    this.cargo = cargo;
    this.telefono = telefono;
    this.correoElectronico = correoElectronico;
    this.nombres = nombres;
    this.compania = compania;
    this.cursoOPromocion = cursoOPromocion;
    this.fechaIncio = fechaIncio;
    this.fechaFin = fechaFin;
    this.estado = estado;
    this.fechaRegistro = fechaRegistro;
    this.fechaInactiva = fechaInactiva;
    this.usuarioRegistra = usuarioRegistra;
    this.maquina = maquina;
    this.fechaActualizacion = fechaActualizacion;
    this.usuarioActualiza = usuarioActualiza;
    this.maquinaActualiza = maquinaActualiza;
    this.idSemillero = idSemillero;
    this.idTipoRelacionSemillero = idTipoRelacionSemillero;
    this.valorTipoRelacionSemillero = valorTipoRelacionSemillero;
    this.idTipoVinculacion = idTipoVinculacion;
    this.valorTipoVinculacion = valorTipoVinculacion;
    this.idUsuarioRol = idUsuarioRol;
    this.idUsuarioRolActualiza = idUsuarioRolActualiza;
  }

  /**
   *
   * @param idRecursoHumanoSemi
   * @param identificacion
   * @param cargo
   * @param telefono
   * @param correoElectronico
   * @param nombres
   * @param compania
   * @param cursoOPromocion
   * @param fechaIncio
   * @param fechaFin
   * @param estado
   * @param fechaRegistro
   * @param fechaInactiva
   * @param usuarioRegistra
   * @param maquina
   * @param fechaActualizacion
   * @param usuarioActualiza
   * @param maquinaActualiza
   * @param idSemillero
   * @param idTipoRelacionSemillero
   * @param valorTipoRelacionSemillero
   * @param idTipoVinculacion
   * @param valorTipoVinculacion
   * @param idUsuarioRol
   * @param idUsuarioRolActualiza
   * @param grado
   */
  public RecursoHumanoSemilleroDTO(Long idRecursoHumanoSemi, String identificacion, String cargo, String telefono, String correoElectronico, String nombres, String compania, String cursoOPromocion, Date fechaIncio, Date fechaFin, String estado, Date fechaRegistro, Date fechaInactiva, String usuarioRegistra, String maquina, Date fechaActualizacion, String usuarioActualiza, String maquinaActualiza, Long idSemillero, Long idTipoRelacionSemillero, String valorTipoRelacionSemillero, Long idTipoVinculacion, String valorTipoVinculacion, Long idUsuarioRol, Long idUsuarioRolActualiza, String grado) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
    this.identificacion = identificacion;
    this.cargo = cargo;
    this.telefono = telefono;
    this.correoElectronico = correoElectronico;
    this.nombres = nombres;
    this.compania = compania;
    this.cursoOPromocion = cursoOPromocion;
    this.fechaIncio = fechaIncio;
    this.fechaFin = fechaFin;
    this.estado = estado;
    this.fechaRegistro = fechaRegistro;
    this.fechaInactiva = fechaInactiva;
    this.usuarioRegistra = usuarioRegistra;
    this.maquina = maquina;
    this.fechaActualizacion = fechaActualizacion;
    this.usuarioActualiza = usuarioActualiza;
    this.maquinaActualiza = maquinaActualiza;
    this.idSemillero = idSemillero;
    this.idTipoRelacionSemillero = idTipoRelacionSemillero;
    this.valorTipoRelacionSemillero = valorTipoRelacionSemillero;
    this.idTipoVinculacion = idTipoVinculacion;
    this.valorTipoVinculacion = valorTipoVinculacion;
    this.idUsuarioRol = idUsuarioRol;
    this.idUsuarioRolActualiza = idUsuarioRolActualiza;
    this.grado = grado;
  }

  /**
   *
   * @param recursoHumanoSemillero
   */
  public RecursoHumanoSemilleroDTO(RecursoHumanoSemillero recursoHumanoSemillero) {
    this.idRecursoHumanoSemi = recursoHumanoSemillero.getIdRecursoHumanoSemi();
    this.identificacion = recursoHumanoSemillero.getIdentificacion();
    this.cargo = recursoHumanoSemillero.getCargo();
    this.telefono = recursoHumanoSemillero.getTelefono();
    this.correoElectronico = recursoHumanoSemillero.getCorreoElectronico();
    this.nombres = recursoHumanoSemillero.getNombres();
    this.compania = recursoHumanoSemillero.getCompania();
    this.cursoOPromocion = recursoHumanoSemillero.getCursoOPromocion();
    this.fechaIncio = recursoHumanoSemillero.getFechaIncio();
    this.fechaFin = recursoHumanoSemillero.getFechaFin();
    this.estado = recursoHumanoSemillero.getEstado();
    this.fechaRegistro = recursoHumanoSemillero.getFechaRegistro();
    this.fechaInactiva = recursoHumanoSemillero.getFechaInactiva();
    this.usuarioRegistra = recursoHumanoSemillero.getUsuarioRegistra();
    this.maquina = recursoHumanoSemillero.getMaquina();
    this.fechaActualizacion = recursoHumanoSemillero.getFechaActualizacion();
    this.usuarioActualiza = recursoHumanoSemillero.getUsuarioActualiza();
    this.maquinaActualiza = recursoHumanoSemillero.getMaquinaActualiza();
    this.idSemillero = recursoHumanoSemillero.getSemilleroInvestigacion().getIdSemillero();
    this.idTipoRelacionSemillero = recursoHumanoSemillero.getTipoRelacionSemillero().getIdConstantes();
    this.valorTipoRelacionSemillero = recursoHumanoSemillero.getTipoRelacionSemillero().getValor();
    this.idTipoVinculacion = recursoHumanoSemillero.getTipoVinculacion() == null ? null : recursoHumanoSemillero.getTipoVinculacion().getIdConstantes();
    this.valorTipoVinculacion = recursoHumanoSemillero.getTipoVinculacion() == null ? null : recursoHumanoSemillero.getTipoVinculacion().getValor();
    this.idUsuarioRol = recursoHumanoSemillero.getUsuarioRol().getIdUsuarioRol();
    this.idUsuarioRolActualiza = recursoHumanoSemillero.getUsuarioRolActualiza() == null ? null : recursoHumanoSemillero.getUsuarioRolActualiza().getIdUsuarioRol();
    this.grado = recursoHumanoSemillero.getGrado();
  }

  public Long getIdRecursoHumanoSemi() {
    return idRecursoHumanoSemi;
  }

  public void setIdRecursoHumanoSemi(Long idRecursoHumanoSemi) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico) {
    this.correoElectronico = correoElectronico;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getCompania() {
    return compania;
  }

  public void setCompania(String compania) {
    this.compania = compania;
  }

  public String getCursoOPromocion() {
    return cursoOPromocion;
  }

  public void setCursoOPromocion(String cursoOPromocion) {
    this.cursoOPromocion = cursoOPromocion;
  }

  public Date getFechaIncio() {
    return fechaIncio;
  }

  public void setFechaIncio(Date fechaIncio) {
    this.fechaIncio = fechaIncio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Date getFechaInactiva() {
    return fechaInactiva;
  }

  public void setFechaInactiva(Date fechaInactiva) {
    this.fechaInactiva = fechaInactiva;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getUsuarioActualiza() {
    return usuarioActualiza;
  }

  public void setUsuarioActualiza(String usuarioActualiza) {
    this.usuarioActualiza = usuarioActualiza;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public Long getIdTipoRelacionSemillero() {
    return idTipoRelacionSemillero;
  }

  public void setIdTipoRelacionSemillero(Long idTipoRelacionSemillero) {
    this.idTipoRelacionSemillero = idTipoRelacionSemillero;
  }

  public String getValorTipoRelacionSemillero() {
    return valorTipoRelacionSemillero;
  }

  public void setValorTipoRelacionSemillero(String valorTipoRelacionSemillero) {
    this.valorTipoRelacionSemillero = valorTipoRelacionSemillero;
  }

  public Long getIdTipoVinculacion() {
    return idTipoVinculacion;
  }

  public void setIdTipoVinculacion(Long idTipoVinculacion) {
    this.idTipoVinculacion = idTipoVinculacion;
  }

  public String getValorTipoVinculacion() {
    return valorTipoVinculacion;
  }

  public void setValorTipoVinculacion(String valorTipoVinculacion) {
    this.valorTipoVinculacion = valorTipoVinculacion;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Long getIdUsuarioRolActualiza() {
    return idUsuarioRolActualiza;
  }

  public void setIdUsuarioRolActualiza(Long idUsuarioRolActualiza) {
    this.idUsuarioRolActualiza = idUsuarioRolActualiza;
  }

  @Override
  public String getLlaveModel() {
    if (idRecursoHumanoSemi == null) {
      return null;
    }

    return idRecursoHumanoSemi.toString();
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

}
