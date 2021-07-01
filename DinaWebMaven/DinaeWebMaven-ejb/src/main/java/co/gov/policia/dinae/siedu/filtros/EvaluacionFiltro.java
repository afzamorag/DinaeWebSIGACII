/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.filtros;

import co.gov.policia.dinae.modelo.CarrerasPK;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.GradoPK;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Juan Jose Buzon
 */
public class EvaluacionFiltro implements Serializable {

    private static final long serialVersionUID = 3551044240756955597L;

    private String vigencia;
    private Long proceso;
    private Long nivelAcademico;
    private CarrerasPK eventoProgramaAcademico;
    private Long modalidad;
    private List<CategoriaPK> ploblacionNivel;
    private List<GradoPK> ploblacionGrado;
    private Long evaluacion;

    public EvaluacionFiltro() {
        ploblacionNivel = new ArrayList<>();
        ploblacionGrado = new ArrayList<>();
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public Long getProceso() {
        return proceso;
    }

    public void setProceso(Long proceso) {
        this.proceso = proceso;
    }

    public Long getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(Long nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public CarrerasPK getEventoProgramaAcademico() {
        return eventoProgramaAcademico;
    }

    public void setEventoProgramaAcademico(CarrerasPK eventoProgramaAcademico) {
        this.eventoProgramaAcademico = eventoProgramaAcademico;
    }

    public Long getModalidad() {
        return modalidad;
    }

    public void setModalidad(Long modalidad) {
        this.modalidad = modalidad;
    }

    public List<CategoriaPK> getPloblacionNivel() {
        return ploblacionNivel;
    }

    public void setPloblacionNivel(List<CategoriaPK> ploblacionNivel) {
        this.ploblacionNivel = ploblacionNivel;
    }

    public List<GradoPK> getPloblacionGrado() {
        return ploblacionGrado;
    }

    public void setPloblacionGrado(List<GradoPK> ploblacionGrado) {
        this.ploblacionGrado = ploblacionGrado;
    }

    public Long getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Long evaluacion) {
        this.evaluacion = evaluacion;
    }

}
