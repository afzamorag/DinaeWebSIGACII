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
public enum TipoParametroEvaluacionEnum {
    
    ASPECTOS(85L, "Aspectos"),
    PREGUNTAS(84L, "Preguntas");
    
    Long code;
    String name;
    
    TipoParametroEvaluacionEnum(Long code, String name){
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
