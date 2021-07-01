package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class AsesoriaProyectoDTO implements IDataModel, Serializable {

  private Long id;
  private Date fechaRegistro;
  private String resultadoAsesoria;
  private String modalidad;
  private String evaluador;

  private Character virtual;
  private Character presencial;
  private Character telefonica;
  private Character otros;

  private List<String> opcionesSeleccionadaModalidad;

  public AsesoriaProyectoDTO() {
  }

  /**
   *
   * @param id
   * @param fechaRegistro
   * @param virtual
   * @param presencial
   * @param telefonica
   * @param otros
   * @param resultadoAsesoria
   * @param evaluador
   */
  public AsesoriaProyectoDTO(Long id, Date fechaRegistro, Character virtual, Character presencial, Character telefonica, Character otros, String resultadoAsesoria, String evaluador) {
    this.id = id;
    this.fechaRegistro = fechaRegistro;
    this.resultadoAsesoria = resultadoAsesoria;
    opcionesSeleccionadaModalidad = new ArrayList<String>();

    if ('S' == virtual) {

      this.modalidad = "Virtual";
      opcionesSeleccionadaModalidad.add("V");
    }

    if ('S' == presencial) {

      if (this.modalidad == null) {
        this.modalidad = "";
      }
      this.modalidad = modalidad.concat(" - Presencial");
      opcionesSeleccionadaModalidad.add("P");
    }

    if ('S' == telefonica) {

      if (this.modalidad == null) {
        this.modalidad = "";
      }
      this.modalidad = modalidad.concat(" - Telefonica");
      opcionesSeleccionadaModalidad.add("T");
    }

    if ('S' == otros) {

      if (this.modalidad == null) {
        this.modalidad = "";
      }
      this.modalidad = modalidad.concat(" - Otros");
      opcionesSeleccionadaModalidad.add("O");
    }

    if (this.modalidad.startsWith(" - ")) {
      this.modalidad = this.modalidad.replaceFirst(" - ", "");
    }

    this.evaluador = evaluador;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getResultadoAsesoria() {
    return resultadoAsesoria;
  }

  public void setResultadoAsesoria(String resultadoAsesoria) {
    this.resultadoAsesoria = resultadoAsesoria;
  }

  public String getModalidad() {
    return modalidad;
  }

  public void setModalidad(String modalidad) {
    this.modalidad = modalidad;
  }

  public String getEvaluador() {
    return evaluador;
  }

  public void setEvaluador(String evaluador) {
    this.evaluador = evaluador;
  }

  public Character getVirtual() {
    return virtual;
  }

  public void setVirtual(Character virtual) {
    this.virtual = virtual;
  }

  public Character getPresencial() {
    return presencial;
  }

  public void setPresencial(Character presencial) {
    this.presencial = presencial;
  }

  public Character getTelefonica() {
    return telefonica;
  }

  public void setTelefonica(Character telefonica) {
    this.telefonica = telefonica;
  }

  public Character getOtros() {
    return otros;
  }

  public void setOtros(Character otros) {
    this.otros = otros;
  }

  public List<String> getOpcionesSeleccionadaModalidad() {
    if (opcionesSeleccionadaModalidad == null) {
      opcionesSeleccionadaModalidad = new ArrayList<String>();
    }
    return opcionesSeleccionadaModalidad;
  }

  public void setOpcionesSeleccionadaModalidad(List<String> opcionesSeleccionadaModalidad) {
    this.opcionesSeleccionadaModalidad = opcionesSeleccionadaModalidad;
  }

  @Override
  public String getLlaveModel() {
    return String.valueOf(id);
  }

}
