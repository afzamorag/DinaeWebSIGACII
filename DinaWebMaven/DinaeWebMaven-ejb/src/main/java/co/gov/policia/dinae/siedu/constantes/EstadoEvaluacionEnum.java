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
public enum EstadoEvaluacionEnum {
    
    PENDIENTE("PENDIENTE"), INACTIVA("INACTIVA"), ACTIVA("ACTIVA");
    
    private String name;
    
    EstadoEvaluacionEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
