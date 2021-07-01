/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.siedu.constantes.EstadoParticipanteEvaluacionEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Juan Jose Buzon
 */
@Entity
@Table(name = "SIEDU_EVAL_PARTICIPANTE")
@NamedQueries({
  @NamedQuery(name = EvaluacionParticipante.FIND_BY_PARE_EVEE, query = "SELECT s FROM EvaluacionParticipante s WHERE s.participanteEvento.pareEvee.eveeEvee =:evento")
})
public class EvaluacionParticipante implements Serializable {

    private static final long serialVersionUID = 7139824352360083676L;
    public static final String FIND_BY_PARE_EVEE = "EvaluacionParticipante.findByPareEvee";

    @EmbeddedId
    private EvaluacionParticipantePK evaluacionParticipantePK;

    @Column(name = "EVPA_OBSERVA")
    private String observacion;
    
    @Column(name = "EVPA_ESTADO")
    @Enumerated(EnumType.STRING)
    private EstadoParticipanteEvaluacionEnum estado;

    @Column(name = "EVPA_USU_CREA")
    private String usuarioCrea;

    @Column(name = "EVPA_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;

    @Column(name = "EVPA_MAQUINA_CREA")
    private String maquinaCrea;

    @Column(name = "EVPA_IP_CREA")
    private String ipCrea;

    @Column(name = "EVPA_USU_MOD")
    private String usuarioModifica;

    @Column(name = "EVPA_FECHA_MOD")
    @Temporal(TemporalType.DATE)
    private Date fechaModifica;

    @Column(name = "EVPA_MAQUINA_MOD")
    private String maquinaModifica;

    @Column(name = "EVPA_IP_MOD")
    private String ipModifica;

    @ManyToOne
    @JoinColumn(name = "EVPA_EVAL", referencedColumnName = "EVAL_EVAL", insertable = false, updatable = false)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "EVPA_PARE", referencedColumnName = "PARE_PARE", insertable = false, updatable = false)
    private SieduParticipanteEvento participanteEvento;

    public EvaluacionParticipantePK getEvaluacionParticipantePK() {
        return evaluacionParticipantePK;
    }

    public void setEvaluacionParticipantePK(EvaluacionParticipantePK evaluacionParticipantePK) {
        this.evaluacionParticipantePK = evaluacionParticipantePK;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public EstadoParticipanteEvaluacionEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoParticipanteEvaluacionEnum estado) {
        this.estado = estado;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getMaquinaCrea() {
        return maquinaCrea;
    }

    public void setMaquinaCrea(String maquinaCrea) {
        this.maquinaCrea = maquinaCrea;
    }

    public String getIpCrea() {
        return ipCrea;
    }

    public void setIpCrea(String ipCrea) {
        this.ipCrea = ipCrea;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public String getMaquinaModifica() {
        return maquinaModifica;
    }

    public void setMaquinaModifica(String maquinaModifica) {
        this.maquinaModifica = maquinaModifica;
    }

    public String getIpModifica() {
        return ipModifica;
    }

    public void setIpModifica(String ipModifica) {
        this.ipModifica = ipModifica;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public SieduParticipanteEvento getParticipanteEvento() {
        return participanteEvento;
    }

    public void setParticipanteEvento(SieduParticipanteEvento participanteEvento) {
        this.participanteEvento = participanteEvento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.evaluacionParticipantePK);
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
        final EvaluacionParticipante other = (EvaluacionParticipante) obj;
        if (!Objects.equals(this.evaluacionParticipantePK, other.evaluacionParticipantePK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EvaluacionParticipante{" + "evaluacionParticipantePK=" + evaluacionParticipantePK + '}';
    }

}
