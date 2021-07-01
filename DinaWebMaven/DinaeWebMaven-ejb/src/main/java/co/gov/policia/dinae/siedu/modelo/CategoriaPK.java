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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Jose Buzon
 */
@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class CategoriaPK implements Serializable {

    private static final long serialVersionUID = -3834768195800474536L;
    
    @Column(name = "ID_CATEGORIA")
    @XmlAttribute
    private Long idCategoria;
    
    @Column(name = "FUERZA")
    @XmlAttribute
    private Long fuerza;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getFuerza() {
        return fuerza;
    }

    public void setFuerza(Long fuerza) {
        this.fuerza = fuerza;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idCategoria);
        hash = 83 * hash + Objects.hashCode(this.fuerza);
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
        final CategoriaPK other = (CategoriaPK) obj;
        if (!Objects.equals(this.idCategoria, other.idCategoria)) {
            return false;
        }
        if (!Objects.equals(this.fuerza, other.fuerza)) {
            return false;
        }
        return true;
    }
    
}
