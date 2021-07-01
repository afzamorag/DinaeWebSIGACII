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
public class CompaniasCursoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPCURSO")
    private int idCompcurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_CURSO")
    private short nroCurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNDE_CONSECUTIVO")
    private BigInteger undeConsecutivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNDE_FUERZA")
    private short undeFuerza;

    public CompaniasCursoPK() {
    }

    public CompaniasCursoPK(int idCompcurso, short nroCurso, BigInteger undeConsecutivo, short undeFuerza) {
        this.idCompcurso = idCompcurso;
        this.nroCurso = nroCurso;
        this.undeConsecutivo = undeConsecutivo;
        this.undeFuerza = undeFuerza;
    }

    public int getIdCompcurso() {
        return idCompcurso;
    }

    public void setIdCompcurso(int idCompcurso) {
        this.idCompcurso = idCompcurso;
    }

    public short getNroCurso() {
        return nroCurso;
    }

    public void setNroCurso(short nroCurso) {
        this.nroCurso = nroCurso;
    }

    public BigInteger getUndeConsecutivo() {
        return undeConsecutivo;
    }

    public void setUndeConsecutivo(BigInteger undeConsecutivo) {
        this.undeConsecutivo = undeConsecutivo;
    }

    public short getUndeFuerza() {
        return undeFuerza;
    }

    public void setUndeFuerza(short undeFuerza) {
        this.undeFuerza = undeFuerza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCompcurso;
        hash += (int) nroCurso;
        hash += (undeConsecutivo != null ? undeConsecutivo.hashCode() : 0);
        hash += (int) undeFuerza;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompaniasCursoPK)) {
            return false;
        }
        CompaniasCursoPK other = (CompaniasCursoPK) object;
        if (this.idCompcurso != other.idCompcurso) {
            return false;
        }
        if (this.nroCurso != other.nroCurso) {
            return false;
        }
        if ((this.undeConsecutivo == null && other.undeConsecutivo != null) || (this.undeConsecutivo != null && !this.undeConsecutivo.equals(other.undeConsecutivo))) {
            return false;
        }
        if (this.undeFuerza != other.undeFuerza) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.CompaniasCursoPK[ idCompcurso=" + idCompcurso + ", nroCurso=" + nroCurso + ", undeConsecutivo=" + undeConsecutivo + ", undeFuerza=" + undeFuerza + " ]";
    }
    
}
