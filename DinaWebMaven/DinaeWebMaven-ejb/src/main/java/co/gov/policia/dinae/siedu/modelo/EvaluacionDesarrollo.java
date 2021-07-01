package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Juan Jose Buzon
 *
 */
@Entity
@Table(name = "SIEDU_EVAL_DESARROLLO")
@NamedQueries({
    @NamedQuery(name = EvaluacionDesarrollo.FIND_BY_EVALUACION, query = "SELECT ed FROM EvaluacionDesarrollo ed WHERE ed.evaluacionDesarrolloPK.idEvaluacion = :idEvaluacion ORDER BY ed.preguntaEvaluacion.aspecto.id, ed.preguntaEvaluacion.orden")})
public class EvaluacionDesarrollo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3362354243027519097L;
    public static final String FIND_BY_EVALUACION = "EvaluacionDesarrollo.findByEvaluacion";

    @EmbeddedId
    private EvaluacionDesarrolloPK evaluacionDesarrolloPK;

    @Column(name = "EVDE_VALOR")
    private Integer valor;

    @Column(name = "EVDE_USU_CREA")
    private String usuarioCrea;

    @Column(name = "EVDE_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;

    @Column(name = "EVDE_MAQUINA_CREA")
    private String maquinaCrea;

    @Column(name = "EVDE_IP_CREA")
    private String ipCrea;

    @Column(name = "EVDE_USU_MOD")
    private String usuarioModifica;

    @Column(name = "EVDE_FECHA_MOD")
    @Temporal(TemporalType.DATE)
    private Date fechaModifica;

    @Column(name = "EVDE_MAQUINA_MOD")
    private String maquinaModifica;

    @Column(name = "EVDE_IP_MOD")
    private String ipModifica;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EVDE_EVAL", referencedColumnName = "EVPA_EVAL", insertable = false, updatable = false),
        @JoinColumn(name = "EVDE_PARE", referencedColumnName = "EVPA_PARE", insertable = false, updatable = false)
    })
    private EvaluacionParticipante evaluacionParticipante;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EVDE_EVAL", referencedColumnName = "EVPRE_EVAL", insertable = false, updatable = false),
        @JoinColumn(name = "EVDE_PREG", referencedColumnName = "EVPRE_PEVAL_PREG", insertable = false, updatable = false)
    })
    private PreguntaEvaluacion preguntaEvaluacion;

    public EvaluacionDesarrolloPK getEvaluacionDesarrolloPK() {
        return evaluacionDesarrolloPK;
    }

    public void setEvaluacionDesarrolloPK(
            EvaluacionDesarrolloPK evaluacionDesarrolloPK) {
        this.evaluacionDesarrolloPK = evaluacionDesarrolloPK;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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

    public EvaluacionParticipante getEvaluacionParticipante() {
        return evaluacionParticipante;
    }

    public void setEvaluacionParticipante(
            EvaluacionParticipante evaluacionParticipante) {
        this.evaluacionParticipante = evaluacionParticipante;
    }

    public PreguntaEvaluacion getPreguntaEvaluacion() {
        return preguntaEvaluacion;
    }

    public void setPreguntaEvaluacion(PreguntaEvaluacion preguntaEvaluacion) {
        this.preguntaEvaluacion = preguntaEvaluacion;
    }

}
