package co.gov.policia.dinae.dto;

import java.io.Serializable;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class ParticipanteEventoViewDTO implements Serializable {

  private int consecutivo;
  private int undeConsecutivo;
  private int undeFuerza;
  private int identificacion;
  private Short edad;
  private Short tiempoPonal;
  private String sexo;
  private int undeConsecutivoNominado;
  private String raza;
  private int codGrado;
  private int codCategoriaGr;
  private String grupoIndigena;
  private int cargCargo;

  /**
   *
   */
  public ParticipanteEventoViewDTO() {
  }

  public ParticipanteEventoViewDTO(int consecutivo, int undeConsecutivo, int undeFuerza, int identificacion, Short edad, Short tiempoPonal, String sexo, int undeConsecutivoNominado, String raza, int codGrado, int codCategoriaGr, String grupoIndigena, int cargCargo) {
    this.consecutivo = consecutivo;
    this.undeConsecutivo = undeConsecutivo;
    this.undeFuerza = undeFuerza;
    this.identificacion = identificacion;
    this.edad = edad;
    this.tiempoPonal = tiempoPonal;
    this.sexo = sexo;
    this.undeConsecutivoNominado = undeConsecutivoNominado;
    this.raza = raza;
    this.codGrado = codGrado;
    this.codCategoriaGr = codCategoriaGr;
    this.grupoIndigena = grupoIndigena;
    this.cargCargo = cargCargo;
  }

  public int getConsecutivo() {
    return consecutivo;
  }

  public void setConsecutivo(int consecutivo) {
    this.consecutivo = consecutivo;
  }

  public int getUndeConsecutivo() {
    return undeConsecutivo;
  }

  public void setUndeConsecutivo(int undeConsecutivo) {
    this.undeConsecutivo = undeConsecutivo;
  }

  public int getUndeFuerza() {
    return undeFuerza;
  }

  public void setUndeFuerza(int undeFuerza) {
    this.undeFuerza = undeFuerza;
  }

  public int getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(int identificacion) {
    this.identificacion = identificacion;
  }

  public Short getEdad() {
    return edad;
  }

  public void setEdad(Short edad) {
    this.edad = edad;
  }

  public Short getTiempoPonal() {
    return tiempoPonal;
  }

  public void setTiempoPonal(Short tiempoPonal) {
    this.tiempoPonal = tiempoPonal;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public int getUndeConsecutivoNominado() {
    return undeConsecutivoNominado;
  }

  public void setUndeConsecutivoNominado(int undeConsecutivoNominado) {
    this.undeConsecutivoNominado = undeConsecutivoNominado;
  }

  public String getRaza() {
    return raza;
  }

  public void setRaza(String raza) {
    this.raza = raza;
  }

  public int getCodGrado() {
    return codGrado;
  }

  public void setCodGrado(int codGrado) {
    this.codGrado = codGrado;
  }

  public int getCodCategoriaGr() {
    return codCategoriaGr;
  }

  public void setCodCategoriaGr(int codCategoriaGr) {
    this.codCategoriaGr = codCategoriaGr;
  }

  public String getGrupoIndigena() {
    return grupoIndigena;
  }

  public void setGrupoIndigena(String grupoIndigena) {
    this.grupoIndigena = grupoIndigena;
  }

  public int getCargCargo() {
    return cargCargo;
  }

  public void setCargCargo(int cargCargo) {
    this.cargCargo = cargCargo;
  }

}
