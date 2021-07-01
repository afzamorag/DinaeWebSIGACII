/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Juan Jose Buzon
 */
@Embeddable
public class EvaluacionParticipantePK implements Serializable {

    private static final long serialVersionUID = -4301065212653230002L;

    @Column(name = "EVPA_EVAL")
    private Long idEvaluacion;

    @Column(name = "EVPA_PARE")
    private Long idParticipanteEvento;

    public EvaluacionParticipantePK() {
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getIdParticipanteEvento() {
        return idParticipanteEvento;
    }

    public void setIdParticipanteEvento(Long idParticipanteEvento) {
        this.idParticipanteEvento = idParticipanteEvento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.idEvaluacion);
        hash = 47 * hash + Objects.hashCode(this.idParticipanteEvento);
        return hash;
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
        final EvaluacionParticipantePK other = (EvaluacionParticipantePK) obj;
        if (!Objects.equals(this.idEvaluacion, other.idEvaluacion)) {
            return false;
        }
        if (!Objects.equals(this.idParticipanteEvento, other.idParticipanteEvento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EvaluacionParticipantePK{" + "idEvaluacion=" + idEvaluacion + ", idParticipanteEvento=" + idParticipanteEvento + '}';
    }

}
