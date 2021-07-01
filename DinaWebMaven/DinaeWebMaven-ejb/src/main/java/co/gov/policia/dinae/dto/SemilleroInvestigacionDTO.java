package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class SemilleroInvestigacionDTO implements IDataModel, Serializable {

  private Long idSemillero;
  private String nombre;
  private Long idUnidadPolicial;
  private String nombreUnidadPolicial;
  private String siglaUnidadPolicial;
  private Date fechaRegistro;
  private String estadoEntreFecha;
  private Date fechaInicio;
  private Date fechaFin;

  private String jefeUnidadGrado;
  private String jefeUnidadNombres;
  private String jefeUnidadApellidos;
  private String jefeUnidadNombreCompleto;
  private String jefeUnidadCorreo;
  private String jefeUnidadTelefono;
  private String jefeUnidadCargo;
  private String jefeUnidadIdentificacion;
  private Long jefeIdUnidadUnidadPolicial;
  private String jefeNombreUnidadUnidadPolicial;

  private String trabajoIndependiente;
  private String tituloTrabajoIndependiente;

  public SemilleroInvestigacionDTO() {
  }

  /**
   *
   * @param idSemillero
   * @param nombre
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param fechaRegistro
   * @param fechaInicio
   * @param fechaFin
   * @param trabajoIndependiente
   * @param tituloTrabajoIndependiente
   */
  public SemilleroInvestigacionDTO(Long idSemillero, String nombre, Long idUnidadPolicial, String nombreUnidadPolicial,
          Date fechaRegistro, Date fechaInicio, Date fechaFin, String trabajoIndependiente, String tituloTrabajoIndependiente) {

    this.idSemillero = idSemillero;
    this.nombre = nombre;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.trabajoIndependiente = trabajoIndependiente;
    this.tituloTrabajoIndependiente = tituloTrabajoIndependiente;

    Date fechaActual = new Date();

    this.estadoEntreFecha = fechaActual.compareTo(fechaFin) <= 0 && fechaActual.compareTo(fechaInicio) >= 0 ? "Activo" : "Inactivo";

    if (fechaInicio != null && fechaFin != null) {

      //estadoEntreFecha = fechaInicio.compareTo(fechaFin) 
    }
  }

  /**
   *
   * @param idSemillero
   * @param nombre
   * @param idUnidadPolicial
   * @param nombreUnidadPolicial
   * @param fechaRegistro
   * @param fechaInicio
   * @param fechaFin
   * @param trabajoIndependiente
   * @param tituloTrabajoIndependiente
   * @param siglaUnidadPolicial
   */
  public SemilleroInvestigacionDTO(Long idSemillero, String nombre, Long idUnidadPolicial, String nombreUnidadPolicial,
          Date fechaRegistro, Date fechaInicio, Date fechaFin, String trabajoIndependiente, String tituloTrabajoIndependiente, String siglaUnidadPolicial) {

    this.idSemillero = idSemillero;
    this.nombre = nombre;
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombreUnidadPolicial = nombreUnidadPolicial;
    this.fechaRegistro = fechaRegistro;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.trabajoIndependiente = trabajoIndependiente;
    this.tituloTrabajoIndependiente = tituloTrabajoIndependiente;
    this.siglaUnidadPolicial = siglaUnidadPolicial;

    Date fechaActual = new Date();

    this.estadoEntreFecha = fechaActual.compareTo(fechaFin) <= 0 && fechaActual.compareTo(fechaInicio) >= 0 ? "Activo" : "Inactivo";

    if (fechaInicio != null && fechaFin != null) {

      //estadoEntreFecha = fechaInicio.compareTo(fechaFin) 
    }
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getEstadoEntreFecha() {
    return estadoEntreFecha;
  }

  public void setEstadoEntreFecha(String estadoEntreFecha) {
    this.estadoEntreFecha = estadoEntreFecha;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getJefeUnidadGrado() {
    return jefeUnidadGrado;
  }

  public void setJefeUnidadGrado(String jefeUnidadGrado) {
    this.jefeUnidadGrado = jefeUnidadGrado;
  }

  public String getJefeUnidadNombres() {
    return jefeUnidadNombres;
  }

  public void setJefeUnidadNombres(String jefeUnidadNombres) {
    this.jefeUnidadNombres = jefeUnidadNombres;
  }

  public String getJefeUnidadApellidos() {
    return jefeUnidadApellidos;
  }

  public void setJefeUnidadApellidos(String jefeUnidadApellidos) {
    this.jefeUnidadApellidos = jefeUnidadApellidos;
  }

  public String getJefeUnidadCorreo() {
    return jefeUnidadCorreo;
  }

  public void setJefeUnidadCorreo(String jefeUnidadCorreo) {
    this.jefeUnidadCorreo = jefeUnidadCorreo;
  }

  public String getJefeUnidadTelefono() {
    return jefeUnidadTelefono;
  }

  public void setJefeUnidadTelefono(String jefeUnidadTelefono) {
    this.jefeUnidadTelefono = jefeUnidadTelefono;
  }

  public String getJefeUnidadCargo() {
    return jefeUnidadCargo;
  }

  public void setJefeUnidadCargo(String jefeUnidadCargo) {
    this.jefeUnidadCargo = jefeUnidadCargo;
  }

  public String getJefeUnidadIdentificacion() {
    return jefeUnidadIdentificacion;
  }

  public void setJefeUnidadIdentificacion(String jefeUnidadIdentificacion) {
    this.jefeUnidadIdentificacion = jefeUnidadIdentificacion;
  }

  public Long getJefeIdUnidadUnidadPolicial() {
    return jefeIdUnidadUnidadPolicial;
  }

  public void setJefeIdUnidadUnidadPolicial(Long jefeIdUnidadUnidadPolicial) {
    this.jefeIdUnidadUnidadPolicial = jefeIdUnidadUnidadPolicial;
  }

  public String getJefeNombreUnidadUnidadPolicial() {
    return jefeNombreUnidadUnidadPolicial;
  }

  public void setJefeNombreUnidadUnidadPolicial(String jefeNombreUnidadUnidadPolicial) {
    this.jefeNombreUnidadUnidadPolicial = jefeNombreUnidadUnidadPolicial;
  }

  public String getJefeUnidadNombreCompleto() {
    return jefeUnidadNombreCompleto;
  }

  public void setJefeUnidadNombreCompleto(String jefeUnidadNombreCompleto) {
    this.jefeUnidadNombreCompleto = jefeUnidadNombreCompleto;
  }

  public String getTrabajoIndependiente() {
    return trabajoIndependiente;
  }

  public void setTrabajoIndependiente(String trabajoIndependiente) {
    this.trabajoIndependiente = trabajoIndependiente;
  }

  public String getTituloTrabajoIndependiente() {
    return tituloTrabajoIndependiente;
  }

  public void setTituloTrabajoIndependiente(String tituloTrabajoIndependiente) {
    this.tituloTrabajoIndependiente = tituloTrabajoIndependiente;
  }

  @Override
  public String getLlaveModel() {
    if (this.idSemillero == null) {
      return null;
    }

    return this.idSemillero.toString();
  }

  public String getSiglaUnidadPolicial() {
    return siglaUnidadPolicial;
  }

  public void setSiglaUnidadPolicial(String siglaUnidadPolicial) {
    this.siglaUnidadPolicial = siglaUnidadPolicial;
  }

}
