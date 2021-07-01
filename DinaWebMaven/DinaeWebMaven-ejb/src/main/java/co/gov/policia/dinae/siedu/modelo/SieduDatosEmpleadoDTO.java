/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author ANDRES.ZAMORAG
 */
@Entity
public class SieduDatosEmpleadoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String BUSCAR_DATOS_EMPLEADO_POLICIA = "SELECT T.IDENTIFICACION AS identificacion,\n"
          + "       UD.CONSECUTIVO   AS consecutivoGrupoLabora,\n"
          + "       UD.FUERZA        AS fuerzaGrupoLabora,\n"
          + "       UD.DESCRIPCION_DEPENDENCIA  AS unidadGrupoLabora,\n"
          + "       UD1.CONSECUTIVO AS consecutivoLabora,\n"
          + "       UD1.FUERZA AS fuerzaLabora,\n"
          + "       UD1.SIGLA_PAPA AS siglaLabora,\n"
          + "       VM.DIRECCION AS unidadPapa,\n"
          + "       VM.ANIO AS anio,\n"
          + "       T.CARG_CARGO AS cargo,\n"
          + "       T.CARG_FUERZA AS cargoFuerza,\n"
          + "       VM.GRAD_ALFABETICO AS gradoAlfabetico,\n"
          + "       VM.CATEGORIA AS categoriaGrado,\n"
          + "       VM.TIEMPO_PONAL AS tiempoPonal\n"
          + "  FROM EMPLEADOS T, UNIDADES_DEPENDENCIA UD, UNIDADES_DEPENDENCIA UD1, VM_PERSONAL_AGRUPADO VM\n"
          + " WHERE T.UNDE_CONSECUTIVO_LABORANDO = UD.CONSECUTIVO\n"
          + "   AND T.UNDE_FUERZA_LABORANDO = UD.FUERZA\n"
          + "   AND T.UNDE_CONSECUTIVO_NOMINADO = UD1.CONSECUTIVO\n"
          + "   AND VM.IDENTIFICACION = T.IDENTIFICACION\n"
          + "   AND VM.CONSECUTIVO = T.CONSECUTIVO\n"
          + "   AND VM.UNDE_CONSECUTIVO = T.UNDE_CONSECUTIVO\n"
          + "   AND VM.IDENTIFICACION = ?1";
  
  @Id
  private Long identificacion; 
  private Long consecutivoGrupoLabora;
  private Long fuerzaGrupoLabora;
  private String unidadGrupoLabora;
  private Long consecutivoLabora;
  private Long fuerzaLabora;
  private String siglaLabora;
  private String unidadPapa;
  private String anio;
  private Long cargoFuerza;
  private Long cargo;
  private String gradoAlfabetico;
  private String categoriaGrado;
  private Integer tiempoPonal;

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

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public Long getCargo() {
    return cargo;
  }

  public void setCargo(Long cargo) {
    this.cargo = cargo;
  }

  public String getgradoAlfabetico() {
    return gradoAlfabetico;
  }

  public void setgradoAlfabetico(String gradoAlfabetico) {
    this.gradoAlfabetico = gradoAlfabetico;
  }

  public String getCategoriaGrado() {
    return categoriaGrado;
  }

  public void setCategoriaGrado(String categoriaGrado) {
    this.categoriaGrado = categoriaGrado;
  }

  public Integer getTiempoPonal() {
    return tiempoPonal;
  }

  public void setTiempoPonal(Integer tiempoPonal) {
    this.tiempoPonal = tiempoPonal;
  }

  public Long getCargoFuerza() {
    return cargoFuerza;
  }

  public void setCargoFuerza(Long cargoFuerza) {
    this.cargoFuerza = cargoFuerza;
  }

  public String getGradoAlfabetico() {
    return gradoAlfabetico;
  }

  public void setGradoAlfabetico(String gradoAlfabetico) {
    this.gradoAlfabetico = gradoAlfabetico;
  }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.identificacion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SieduDatosEmpleadoDTO other = (SieduDatosEmpleadoDTO) obj;
        if (!Objects.equals(this.identificacion, other.identificacion)) {
            return false;
        }
        return true;
    }

  
}
