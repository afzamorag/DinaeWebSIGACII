/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 */
@Entity
public class SieduDatosparticipante implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  private Long identificacion;
  private Long consecutivoGrupoLabora;
  private Long fuerzaGrupoLabora;
  private String unidadGrupoLabora;
  private Long consecutivoLabora;
  private Long fuerzaLabora;
  private String siglaLabora;
  private String unidadPapa;
  private Integer anio;
  private Long cargCargo;
  private Long fuerzaCargo;
  private Long gradoNumerico;
  private Long categoriaGrado;
  private Integer tiempoPonal;

  public SieduDatosparticipante() {
  }

  public Long getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(Long identificacion) {
    this.identificacion = identificacion;
  }

  public Long getConsecutivoGrupoLabora() {
    return consecutivoGrupoLabora;
  }

  public void setConsecutivoGrupoLabora(Long consecutivoGrupoLabora) {
    this.consecutivoGrupoLabora = consecutivoGrupoLabora;
  }

  public Long getFuerzaGrupoLabora() {
    return fuerzaGrupoLabora;
  }

  public void setFuerzaGrupoLabora(Long fuerzaGrupoLabora) {
    this.fuerzaGrupoLabora = fuerzaGrupoLabora;
  }

  public String getUnidadGrupoLabora() {
    return unidadGrupoLabora;
  }

  public void setUnidadGrupoLabora(String unidadGrupoLabora) {
    this.unidadGrupoLabora = unidadGrupoLabora;
  }

  public Long getConsecutivoLabora() {
    return consecutivoLabora;
  }

  public void setConsecutivoLabora(Long consecutivoLabora) {
    this.consecutivoLabora = consecutivoLabora;
  }

  public Long getFuerzaLabora() {
    return fuerzaLabora;
  }

  public void setFuerzaLabora(Long fuerzaLabora) {
    this.fuerzaLabora = fuerzaLabora;
  }

  public String getSiglaLabora() {
    return siglaLabora;
  }

  public void setSiglaLabora(String siglaLabora) {
    this.siglaLabora = siglaLabora;
  }

  public String getUnidadPapa() {
    return unidadPapa;
  }

  public void setUnidadPapa(String unidadPapa) {
    this.unidadPapa = unidadPapa;
  }

  public Integer getAnio() {
    return anio;
  }

  public void setAnio(Integer anio) {
    this.anio = anio;
  }

  public Long getCargCargo() {
    return cargCargo;
  }

  public void setCargCargo(Long cargCargo) {
    this.cargCargo = cargCargo;
  }

  public Long getFuerzaCargo() {
    return fuerzaCargo;
  }

  public void setFuerzaCargo(Long fuerzaCargo) {
    this.fuerzaCargo = fuerzaCargo;
  }
  
  public Long getGradoNumerico() {
    return gradoNumerico;
  }

  public void setGradoNumerico(Long gradoNumerico) {
    this.gradoNumerico = gradoNumerico;
  }

  public Long getCategoriaGrado() {
    return categoriaGrado;
  }

  public void setCategoriaGrado(Long categoriaGrado) {
    this.categoriaGrado = categoriaGrado;
  }

  public Integer getTiempoPonal() {
    return tiempoPonal;
  }

  public void setTiempoPonal(Integer tiempoPonal) {
    this.tiempoPonal = tiempoPonal;
  }  
}
