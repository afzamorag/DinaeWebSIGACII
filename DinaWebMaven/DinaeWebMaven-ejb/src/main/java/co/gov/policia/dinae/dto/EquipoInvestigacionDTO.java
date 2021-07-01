package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/11/28
 */
public class EquipoInvestigacionDTO implements Serializable, IDataModel {

  private Long idEquipoInvestigacion;

  private BigDecimal valor;

  private String especificaciones;

  private Long idOrigen;

  private Long valorOrigen;

  private String nombreEquipo;

  private Long idFuenteProyecto;

  private String nombreFuenteProyecto;

  private boolean seleccionable;

  public EquipoInvestigacionDTO() {
  }

  public EquipoInvestigacionDTO(Long idEquipoInvestigacion, BigDecimal valor, String especificaciones, Long idOrigen, Long valorOrigen, String nombreEquipo, Long idFuenteProyecto, String nombreFuenteProyecto, boolean seleccionable) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
    this.valor = valor;
    this.especificaciones = especificaciones;
    this.idOrigen = idOrigen;
    this.valorOrigen = valorOrigen;
    this.nombreEquipo = nombreEquipo;
    this.idFuenteProyecto = idFuenteProyecto;
    this.nombreFuenteProyecto = nombreFuenteProyecto;
    this.seleccionable = seleccionable;
  }

  public Long getIdEquipoInvestigacion() {
    return idEquipoInvestigacion;
  }

  public void setIdEquipoInvestigacion(Long idEquipoInvestigacion) {
    this.idEquipoInvestigacion = idEquipoInvestigacion;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getEspecificaciones() {
    return especificaciones;
  }

  public void setEspecificaciones(String especificaciones) {
    this.especificaciones = especificaciones;
  }

  public Long getIdOrigen() {
    return idOrigen;
  }

  public void setIdOrigen(Long idOrigen) {
    this.idOrigen = idOrigen;
  }

  public Long getValorOrigen() {
    return valorOrigen;
  }

  public void setValorOrigen(Long valorOrigen) {
    this.valorOrigen = valorOrigen;
  }

  public String getNombreEquipo() {
    return nombreEquipo;
  }

  public void setNombreEquipo(String nombreEquipo) {
    this.nombreEquipo = nombreEquipo;
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

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  @Override
  public String getLlaveModel() {
    if (this.idEquipoInvestigacion == null) {
      return null;
    }

    return this.idEquipoInvestigacion.toString();
  }

}
