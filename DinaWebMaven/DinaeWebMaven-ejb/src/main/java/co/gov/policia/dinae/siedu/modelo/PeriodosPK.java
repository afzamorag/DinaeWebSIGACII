/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author andres.zamorag
 */
@Embeddable
public class PeriodosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERIODO")
    private BigInteger idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRAC_ID_PROGRAMACA")
    private BigInteger pracIdProgramaca;

    public PeriodosPK() {
    }

    public PeriodosPK(BigInteger idPeriodo, BigInteger pracIdProgramaca) {
        this.idPeriodo = idPeriodo;
        this.pracIdProgramaca = pracIdProgramaca;
    }

    public BigInteger getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(BigInteger idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public BigInteger getPracIdProgramaca() {
        return pracIdProgramaca;
    }

    public void setPracIdProgramaca(BigInteger pracIdProgramaca) {
        this.pracIdProgramaca = pracIdProgramaca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodo != null ? idPeriodo.hashCode() : 0);
        hash += (pracIdProgramaca != null ? pracIdProgramaca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodosPK)) {
            return false;
        }
        PeriodosPK other = (PeriodosPK) object;
        if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
            return false;
        }
        if ((this.pracIdProgramaca == null && other.pracIdProgramaca != null) || (this.pracIdProgramaca != null && !this.pracIdProgramaca.equals(other.pracIdProgramaca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.PeriodosPK[ idPeriodo=" + idPeriodo + ", pracIdProgramaca=" + pracIdProgramaca + " ]";
    }
    
}
