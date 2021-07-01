/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.dto;

import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;
import java.io.Serializable;

/**
 *
 * @author Juan Jose Buzon
 */
public class EvaluacionDTO implements Serializable {

    private static final long serialVersionUID = 402926263827811735L;

    private Long id;
    private String descripcion;
    private EstadoEvaluacionEnum estadoEvaluacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoEvaluacionEnum getEstadoEvaluacion() {
        return estadoEvaluacion;
    }

    public void setEstadoEvaluacion(EstadoEvaluacionEnum estadoEvaluacion) {
        this.estadoEvaluacion = estadoEvaluacion;
    }

}
