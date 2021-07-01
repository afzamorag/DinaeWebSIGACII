/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.constantes;

/**
 *
 * @author Juan Jose Buzon
 */
public enum EstadoParticipanteEvaluacionEnum {

    A("A", "Activa"), I("I", "Inactiva"), D("D", "Desarrollada");
    
    private String code;
    private String descripcion;

    EstadoParticipanteEvaluacionEnum(String code, String descripcion) {
        this.code = code;
        this.descripcion = descripcion;
    }

    public String getCode() {
        return code;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
