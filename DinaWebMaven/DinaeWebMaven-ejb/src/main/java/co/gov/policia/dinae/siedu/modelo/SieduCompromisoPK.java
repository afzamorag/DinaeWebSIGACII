package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Embeddable
public class SieduCompromisoPK implements Serializable {

    private static final long serialVersionUID = -7265548787048841716L;

    @Column(name = "COMP_INVE")
    private Long idInvestigacion;
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_COMPROMISO_SEQ")
    @SequenceGenerator(name = "SIEDU_COMPROMISO_SEQ", sequenceName = "SIEDU_COMPROMISO_SEQ", allocationSize = 1)
    @Column(name = "COMP_COMP")
    private Long idCompromiso;

    public SieduCompromisoPK() {
    }

    public SieduCompromisoPK(Long idInvestigacion, Long idCompromiso) {
        this.idInvestigacion = idInvestigacion;
        this.idCompromiso = idCompromiso;
    }

    public Long getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(Long idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public Long getIdCompromiso() {
        return idCompromiso;
    }

    public void setIdCompromiso(Long idCompromiso) {
        this.idCompromiso = idCompromiso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idInvestigacion);
        hash = 67 * hash + Objects.hashCode(this.idCompromiso);
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
        final SieduCompromisoPK other = (SieduCompromisoPK) obj;
        if (!Objects.equals(this.idInvestigacion, other.idInvestigacion)) {
            return false;
        }
        if (!Objects.equals(this.idCompromiso, other.idCompromiso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduCompromisoPK{" + "idInvestigacion=" + idInvestigacion + ", idCompromiso=" + idCompromiso + '}';
    }

}
