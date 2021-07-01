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
public class SieduEvalGradoPK implements Serializable {

    private static final long serialVersionUID = -7265548787048841716L;

    @Column(name = "EVGR_EVAL")
    private Long evgrEval;

    @Column(name = "EVGR_GRAD_FUERZA")
    private Long evgrGradFuerza;

    @Column(name = "EVGR_GRAD_ALFABETICO")
    private String evgrGradAlfabetico;

    public Long getEvgrEval() {
        return evgrEval;
    }

    public void setEvgrEval(Long evgrEval) {
        this.evgrEval = evgrEval;
    }

    public Long getEvgrGradFuerza() {
        return evgrGradFuerza;
    }

    public void setEvgrGradFuerza(Long evgrGradFuerza) {
        this.evgrGradFuerza = evgrGradFuerza;
    }

    public String getEvgrGradAlfabetico() {
        return evgrGradAlfabetico;
    }

    public void setEvgrGradAlfabetico(String evgrGradAlfabetico) {
        this.evgrGradAlfabetico = evgrGradAlfabetico;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.evgrEval);
        hash = 37 * hash + Objects.hashCode(this.evgrGradFuerza);
        hash = 37 * hash + Objects.hashCode(this.evgrGradAlfabetico);
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
        final SieduEvalGradoPK other = (SieduEvalGradoPK) obj;
        if (!Objects.equals(this.evgrGradAlfabetico, other.evgrGradAlfabetico)) {
            return false;
        }
        if (!Objects.equals(this.evgrEval, other.evgrEval)) {
            return false;
        }
        if (!Objects.equals(this.evgrGradFuerza, other.evgrGradFuerza)) {
            return false;
        }
        return true;
    }

}
