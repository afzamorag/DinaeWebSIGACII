/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;

/**
 *
 * @author ANDRES.ZAMORAG
 */
public class VwmPersonalCapacitadoDTO implements Serializable{
    
    private String vigencia;
    private String siglaEscuela;
    private String nivelAcademico;
    private String estrategia;
    private String modalidad;
    private Long codigoPrograma;
    private String programa;
    private int trimestre;
    private String fechaInicio;
    private String fechaFinal;
    private String direccionCapacitada;
    private String regionalCapacitada;
    private String ubicacionUnidad;
    private String unidadCapacitada;
    private String categoria;
    private String grado;
    private String genero;
    private String cargoAnterior;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String estadoParticipante;

    public VwmPersonalCapacitadoDTO() {
    }

    public VwmPersonalCapacitadoDTO(String vigencia, String siglaEscuela, String nivelAcademico, String estrategia, String modalidad, Long codigoPrograma, String programa, int trimestre, String fechaInicio, String fechaFinal, String direccionCapacitada, String regionalCapacitada, String ubicacionUnidad, String unidadCapacitada, String categoria, String grado, String genero, String cargoAnterior, String identificacion, String nombres, String apellidos, String estadoParticipante) {
        this.vigencia = vigencia;
        this.siglaEscuela = siglaEscuela;
        this.nivelAcademico = nivelAcademico;
        this.estrategia = estrategia;
        this.modalidad = modalidad;
        this.codigoPrograma = codigoPrograma;
        this.programa = programa;
        this.trimestre = trimestre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.direccionCapacitada = direccionCapacitada;
        this.regionalCapacitada = regionalCapacitada;
        this.ubicacionUnidad = ubicacionUnidad;
        this.unidadCapacitada = unidadCapacitada;
        this.categoria = categoria;
        this.grado = grado;
        this.genero = genero;
        this.cargoAnterior = cargoAnterior;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estadoParticipante = estadoParticipante;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getSiglaEscuela() {
        return siglaEscuela;
    }

    public void setSiglaEscuela(String siglaEscuela) {
        this.siglaEscuela = siglaEscuela;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Long getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(Long codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDireccionCapacitada() {
        return direccionCapacitada;
    }

    public void setDireccionCapacitada(String direccionCapacitada) {
        this.direccionCapacitada = direccionCapacitada;
    }

    public String getRegionalCapacitada() {
        return regionalCapacitada;
    }

    public void setRegionalCapacitada(String regionalCapacitada) {
        this.regionalCapacitada = regionalCapacitada;
    }

    public String getUbicacionUnidad() {
        return ubicacionUnidad;
    }

    public void setUbicacionUnidad(String ubicacionUnidad) {
        this.ubicacionUnidad = ubicacionUnidad;
    }

    public String getUnidadCapacitada() {
        return unidadCapacitada;
    }

    public void setUnidadCapacitada(String unidadCapacitada) {
        this.unidadCapacitada = unidadCapacitada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCargoAnterior() {
        return cargoAnterior;
    }

    public void setCargoAnterior(String cargoAnterior) {
        this.cargoAnterior = cargoAnterior;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEstadoParticipante() {
        return estadoParticipante;
    }

    public void setEstadoParticipante(String estadoParticipante) {
        this.estadoParticipante = estadoParticipante;
    }
    
    
            
}
