/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;

/**
 *
 * @author Juan Jose Buzon
 */
public class PreguntaEvaluacionReporteDTO implements Serializable {

    private static final long serialVersionUID = -4060870398195377377L;
    
    private Long idAspecto;
    private String descripcionAspecto;
    private String pregunta;
    private String valor1;
    private String valor2;
    private String valor3;
    private String valor4;
    private String valor5;

    public PreguntaEvaluacionReporteDTO() {
    }

    public Long getIdAspecto() {
        return idAspecto;
    }

    public void setIdAspecto(Long idAspecto) {
        this.idAspecto = idAspecto;
    }

    public String getDescripcionAspecto() {
        return descripcionAspecto;
    }

    public void setDescripcionAspecto(String descripcionAspecto) {
        this.descripcionAspecto = descripcionAspecto;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getValor3() {
        return valor3;
    }

    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

    public String getValor4() {
        return valor4;
    }

    public void setValor4(String valor4) {
        this.valor4 = valor4;
    }

    public String getValor5() {
        return valor5;
    }

    public void setValor5(String valor5) {
        this.valor5 = valor5;
    }
    
    
}
