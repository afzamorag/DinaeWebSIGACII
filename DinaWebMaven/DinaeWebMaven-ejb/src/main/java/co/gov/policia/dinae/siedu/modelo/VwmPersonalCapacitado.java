/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ANDRES.ZAMORAG
 */
@Entity
@Table(name = "MVW_PERSONAL_CAPACITADO")
@Cacheable(false)
public class VwmPersonalCapacitado implements Serializable {

    @Column(name = "VIGENCIA", nullable = false)
    private String vigencia;
   
    @Column(name = "ID_ESCUELA", nullable = false)
    private Long idEscuela;

    @Column(name = "SIGLA_ESCUELA", nullable = false)
    private String siglaEscuela;
    
    @Column(name = "ID_NIVEL_ACADEMICO", nullable = false)
    private Long idNivelAcademico;

    @Column(name = "NIVEL_ACADEMICO", nullable = false)
    private String nivelAcademico;

    @Column(name = "ID_ESTRATEGIA", nullable = false)
    private Long idEstrategia;

    @Column(name = "ESTRATEGIA", nullable = false)
    private String estrategia;

    @Column(name = "ID_MODALIDAD", nullable = false)
    private String idModalidad;

    @Column(name = "MODALIDAD", nullable = false)
    private String modalidad;

    @Column(name = "CODIGO_PROGRAMA", nullable = false)
    private Long codigoPrograma;

    @Column(name = "PROGRAMA", nullable = false)
    private String programa;

    @Column(name = "TRIMESTRE", nullable = false)
    private int trimestre;

    @Column(name = "FECHA_INICIO", nullable = false)
    private String fechaInicio;

    @Column(name = "FECHA_FINAL", nullable = false)
    private String fechaFinal;

    @Column(name = "ID_DIRECCION_CAPACITADA", nullable = false)
    private Long idDireccionCapacitada;

    @Column(name = "DIRECCION_CAPACITADA", nullable = false)
    private String direccionCapacitada;

    @Column(name = "REGIONAL_CAPACITADA", nullable = false)
    private String regionalCapacitada;

    @Column(name = "UBICACION_UNIDAD", nullable = false)
    private String ubicacionUnidad;

    @Column(name = "ID_UNIDAD_CAPACITADA", nullable = false)
    private Long idUnidadCapacitada;

    @Column(name = "UNIDAD_CAPACITADA", nullable = false)
    private String unidadCapacitada;

    @Column(name = "CATEGORIA", nullable = false)
    private String categoria;

    @Column(name = "GRADO", nullable = false)
    private String grado;

    @Column(name = "GENERO", nullable = false)
    private String genero;

    @Column(name = "CARGO_ANTERIOR", nullable = false)
    private String cargoAnterior;

    @Id
    @Column(name = "CODIGO_PARTICIPANTE_EVENTO", nullable = false)
    private Long codigoParticipanteEvento;

    @Column(name = "IDENTIFICACION", nullable = false)
    private String identificacion;

    @Column(name = "NOMBRES", nullable = false)
    private String nombres;

    @Column(name = "APELLIDOS", nullable = false)
    private String apellidos;

    @Column(name = "ESTADO_PARTICIPANTE", nullable = false)
    private String estadoParticipante;

    public VwmPersonalCapacitado() {
    }

    public Long getCodigoParticipanteEvento() {
        return codigoParticipanteEvento;
    }

    public void setCodigoParticipanteEvento(Long codigoParticipanteEvento) {
        this.codigoParticipanteEvento = codigoParticipanteEvento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getSiglaEscuela() {
        return siglaEscuela;
    }

    public void setSiglaEscuela(String siglaEscuela) {
        this.siglaEscuela = siglaEscuela;
    }

    public Long getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(Long codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public Long getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(Long idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Long getIdEstrategia() {
        return idEstrategia;
    }

    public void setIdEstrategia(Long idEstrategia) {
        this.idEstrategia = idEstrategia;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(String idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public String getUnidadCapacitada() {
        return unidadCapacitada;
    }

    public void setUnidadCapacitada(String unidadCapacitada) {
        this.unidadCapacitada = unidadCapacitada;
    }

    public String getUbicacionUnidad() {
        return ubicacionUnidad;
    }

    public void setUbicacionUnidad(String ubicacionUnidad) {
        this.ubicacionUnidad = ubicacionUnidad;
    }

    public String getRegionalCapacitada() {
        return regionalCapacitada;
    }

    public void setRegionalCapacitada(String regionalCapacitada) {
        this.regionalCapacitada = regionalCapacitada;
    }

    public String getDireccionCapacitada() {
        return direccionCapacitada;
    }

    public void setDireccionCapacitada(String direccionCapacitada) {
        this.direccionCapacitada = direccionCapacitada;
    }

    public String getEstadoParticipante() {
        return estadoParticipante;
    }

    public void setEstadoParticipante(String estadoParticipante) {
        this.estadoParticipante = estadoParticipante;
    }

    public Long getIdDireccionCapacitada() {
        return idDireccionCapacitada;
    }

    public void setIdDireccionCapacitada(Long idDireccionCapacitada) {
        this.idDireccionCapacitada = idDireccionCapacitada;
    }

    public Long getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(Long idEscuela) {
        this.idEscuela = idEscuela;
    }

    public Long getIdUnidadCapacitada() {
        return idUnidadCapacitada;
    }

    public void setIdUnidadCapacitada(Long idUnidadCapacitada) {
        this.idUnidadCapacitada = idUnidadCapacitada;
    }
}
