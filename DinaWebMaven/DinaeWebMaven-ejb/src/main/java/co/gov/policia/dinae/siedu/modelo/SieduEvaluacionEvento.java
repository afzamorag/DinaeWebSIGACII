/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_EVALUACION_EVENTO_VW")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = SieduEvaluacionEvento.FIND_ALL, query = "SELECT s FROM SieduEvaluacionEvento s"),
    @NamedQuery(name = SieduEvaluacionEvento.FIND_BY_ID, query = "SELECT s FROM SieduEvaluacionEvento s WHERE s.eveeEvee = :id")
})
@XmlRootElement
public class SieduEvaluacionEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "SieduEvaluacionEvento.findAll";
    public static final String FIND_BY_ID = "SieduEvaluacionEvento.findByIdDominio";

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVEE_EVEE")
    private Long eveeEvee;

    @Column(name = "ASPECTO")
    private String aspecto;

    @Column(name = "COMPETENCIA")
    private String competencia;

    @Column(name = "PREGUNTA")
    private String pregunta;

    @Column(name = "CALIFICACION")
    private double calificacion;
    
    @Column(name = "PORCENTAJE")
    private double porcentaje;

    public SieduEvaluacionEvento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEveeEvee() {
        return eveeEvee;
    }

    public void setEveeEvee(Long eveeEvee) {
        this.eveeEvee = eveeEvee;
    }

    public String getAspecto() {
        return aspecto;
    }

    public void setAspecto(String aspecto) {
        this.aspecto = aspecto;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final SieduEvaluacionEvento other = (SieduEvaluacionEvento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SieduEvaluacionEvento{" + "id=" + id + ", eveeEvee=" + eveeEvee + ", aspecto=" + aspecto + ", competencia=" + competencia + ", pregunta=" + pregunta + ", calificacion=" + calificacion + '}';
    }

}
