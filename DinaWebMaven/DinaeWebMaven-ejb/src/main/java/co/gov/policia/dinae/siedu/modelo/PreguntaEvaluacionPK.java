package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreguntaEvaluacionPK implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7796684720309881728L;

    @Column(name = "EVPRE_EVAL")
    private Long idEvaluacion;

    @Column(name = "EVPRE_PEVAL_PREG")
    private Long idPregunta;

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idEvaluacion == null) ? 0 : idEvaluacion.hashCode());
        result = prime * result
                + ((idPregunta == null) ? 0 : idPregunta.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PreguntaEvaluacionPK other = (PreguntaEvaluacionPK) obj;
        if (idEvaluacion == null) {
            if (other.idEvaluacion != null) {
                return false;
            }
        } else if (!idEvaluacion.equals(other.idEvaluacion)) {
            return false;
        }
        if (idPregunta == null) {
            if (other.idPregunta != null) {
                return false;
            }
        } else if (!idPregunta.equals(other.idPregunta)) {
            return false;
        }
        return true;
    }

}
