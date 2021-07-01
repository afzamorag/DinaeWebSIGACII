package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class UnidadPolicialDTO implements Serializable {

  private Long idUnidadPolicial;
  private String nombre;
  private String siglaFisicaUnidad;
  private String siglaPadre;
  private Long idTipoUnidad;
  private boolean seleccionado;
  private String mail;

  private String nombreTipoUnidad;

  /**
   *
   */
  public UnidadPolicialDTO() {
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaFisicaUnidad
   */
  public UnidadPolicialDTO(Long idUnidadPolicial, String nombre, String siglaFisicaUnidad) {
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombre = nombre;
    this.siglaFisicaUnidad = siglaFisicaUnidad;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaFisicaUnidad
   * @param siglaPadre
   */
  public UnidadPolicialDTO(Long idUnidadPolicial, String nombre, String siglaFisicaUnidad, String siglaPadre) {
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombre = nombre;
    this.siglaFisicaUnidad = siglaFisicaUnidad;
    this.siglaPadre = siglaPadre;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaFisicaUnidad
   * @param siglaPadre
   * @param idTipoUnidad
   * @param nombreTipoUnidad
   */
  public UnidadPolicialDTO(Long idUnidadPolicial, String nombre, String siglaFisicaUnidad, String siglaPadre, Long idTipoUnidad, String nombreTipoUnidad) {
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombre = nombre;
    this.siglaFisicaUnidad = siglaFisicaUnidad;
    this.siglaPadre = siglaPadre;
    this.idTipoUnidad = idTipoUnidad;
    this.nombreTipoUnidad = nombreTipoUnidad;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   * @param siglaFisicaUnidad
   * @param siglaPadre
   * @param idTipoUnidad
   * @param nombreTipoUnidad
   * @param mail
   */
  public UnidadPolicialDTO(Long idUnidadPolicial, String nombre, String siglaFisicaUnidad, String siglaPadre, Long idTipoUnidad, String nombreTipoUnidad, String mail) {
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombre = nombre;
    this.siglaFisicaUnidad = siglaFisicaUnidad;
    this.siglaPadre = siglaPadre;
    this.idTipoUnidad = idTipoUnidad;
    this.nombreTipoUnidad = nombreTipoUnidad;
    this.mail = mail;
  }

  /**
   *
   * @param idUnidadPolicial
   * @param nombre
   */
  public UnidadPolicialDTO(Long idUnidadPolicial, String nombre) {
    this.idUnidadPolicial = idUnidadPolicial;
    this.nombre = nombre;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getSiglaFisicaUnidad() {
    return siglaFisicaUnidad;
  }

  public void setSiglaFisicaUnidad(String siglaFisicaUnidad) {
    this.siglaFisicaUnidad = siglaFisicaUnidad;
  }

  public String getSiglaPadre() {
    return siglaPadre;
  }

  public void setSiglaPadre(String siglaPadre) {
    this.siglaPadre = siglaPadre;
  }

  public Long getIdTipoUnidad() {
    return idTipoUnidad;
  }

  public void setIdTipoUnidad(Long idTipoUnidad) {
    this.idTipoUnidad = idTipoUnidad;
  }

  public String getNombreTipoUnidad() {
    return nombreTipoUnidad;
  }

  public void setNombreTipoUnidad(String nombreTipoUnidad) {
    this.nombreTipoUnidad = nombreTipoUnidad;
  }

  public String getSiglaFisicaYnombreUnidad() {

    if (siglaFisicaUnidad != null && nombre != null) {
      return siglaFisicaUnidad.concat(" - ").concat(nombre);
    }

    return nombre;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
}
