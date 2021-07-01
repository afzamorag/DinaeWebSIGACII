package co.gov.policia.dinae.siedu.dto;

import java.io.Serializable;

import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;

public class PreguntaDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8388058284601064285L;

    private PreguntaEvaluacionPK preguntaEvaluacionPK;
    private Long idParametroEvaluacion;
    private String descripcion;
    
    private Integer valor;

    public PreguntaEvaluacionPK getPreguntaEvaluacionPK() {
        return preguntaEvaluacionPK;
    }

    public void setPreguntaEvaluacionPK(
            PreguntaEvaluacionPK preguntaEvaluacionPK) {
        this.preguntaEvaluacionPK = preguntaEvaluacionPK;
    }

    public Long getIdParametroEvaluacion() {
        return idParametroEvaluacion;
    }

    public void setIdParametroEvaluacion(Long idParametroEvaluacion) {
        this.idParametroEvaluacion = idParametroEvaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

}
