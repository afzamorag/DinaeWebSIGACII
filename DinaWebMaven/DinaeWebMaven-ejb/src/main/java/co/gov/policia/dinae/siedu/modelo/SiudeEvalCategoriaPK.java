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
public class SiudeEvalCategoriaPK implements Serializable {

    private static final long serialVersionUID = -4571727257239243844L;

    /* evcr_eval         	NUMBER (8) NOT NULL ,
    evcr_fuerza       	NUMBER (1) NOT NULL ,
    evcr_id_categoria 	NUMBER NOT NULL*/
    @Column(name = "EVCR_EVAL")
    private Long eval;

    @Column(name = "EVCR_FUERZA")
    private Long fuerza;

    @Column(name = "EVCR_ID_CATEGORIA")
    private Long idCategoria;

    public Long getEval() {
        return eval;
    }

    public void setEval(Long eval) {
        this.eval = eval;
    }

    public Long getFuerza() {
        return fuerza;
    }

    public void setFuerza(Long fuerza) {
        this.fuerza = fuerza;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.eval);
        hash = 53 * hash + Objects.hashCode(this.fuerza);
        hash = 53 * hash + Objects.hashCode(this.idCategoria);
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
        final SiudeEvalCategoriaPK other = (SiudeEvalCategoriaPK) obj;
        if (!Objects.equals(this.eval, other.eval)) {
            return false;
        }
        if (!Objects.equals(this.fuerza, other.fuerza)) {
            return false;
        }
        if (!Objects.equals(this.idCategoria, other.idCategoria)) {
            return false;
        }
        return true;
    }

}
