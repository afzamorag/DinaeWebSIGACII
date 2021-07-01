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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 */
@Embeddable
@XmlRootElement
public class CargosPK implements Serializable {

    private static final long serialVersionUID = 3010207020444762403L;
    
    @Column(name = "FUERZA")
    private Long fuerza;
    
    @Column(name = "CARGO")
    private Long cargo;

    public Long getFuerza() {
        return fuerza;
    }

    public void setFuerza(Long fuerza) {
        this.fuerza = fuerza;
    }

    public Long getCargo() {
        return cargo;
    }

    public void setCargo(Long cargo) {
        this.cargo = cargo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.fuerza);
        hash = 67 * hash + Objects.hashCode(this.cargo);
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
        final CargosPK other = (CargosPK) obj;
        if (!Objects.equals(this.fuerza, other.fuerza)) {
            return false;
        }
        return true;
    }
}
