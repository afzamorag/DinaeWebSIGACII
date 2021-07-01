package co.gov.policia.dinae.siedu.modelo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PreguntaEvaluacion
 *
 */
@Entity
@Table(name = "SIEDU_EVAL_PREGUNTA")
@NamedQueries({
    @NamedQuery(name = PreguntaEvaluacion.FIND_BY_ID_EVALUACION, query = "SELECT pe FROM PreguntaEvaluacion pe WHERE pe.evaluacion.id = :idEvaluacion ORDER BY pe.aspecto.id, pe.orden"),
    @NamedQuery(name = PreguntaEvaluacion.DELETE_BY_EVALUACION, query = "DELETE FROM PreguntaEvaluacion pe WHERE pe.evaluacion.id = :idEvaluacion")})
public class PreguntaEvaluacion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4093640184135681269L;

    public static final String FIND_BY_ID_EVALUACION = "PreguntaEvaluacion.findByEvaluacion";
    public static final String DELETE_BY_EVALUACION = "PreguntaEvaluacion.deleteByEvaluacion";

    @EmbeddedId
    private PreguntaEvaluacionPK id;

    @Column(name = "EVPRE_ORDEN")
    private Long orden;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EVPRE_COMP", referencedColumnName = "COMP_COMP")
    private SieduCompetencia competencia;

    @Column(name = "EVPRE_VLR_PORC")
    private BigDecimal valorPorcentaje;

    @Column(name = "EVPRE_USU_CREA")
    private String usuarioCrea;

    @Column(name = "EVPRE_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;

    @Column(name = "EVPRE_MAQUINA_CREA")
    private String maquinaCrea;

    @Column(name = "EVPRE_IP_CREA")
    private String ipCrea;

    @Column(name = "EVPRE_USU_MOD")
    private String usuarioModifica;

    @Column(name = "EVPRE_FECHA_MOD")
    @Temporal(TemporalType.DATE)
    private Date fechaModifica;

    @Column(name = "EVPRE_MAQUINA_MOD")
    private String maquinaModifica;

    @Column(name = "EVPRE_IP_MOD")
    private String ipModifica;

    @ManyToOne
    @JoinColumn(name = "EVPRE_PEVAL_PREG", referencedColumnName = "PEVAL_PEVAL", insertable = false, updatable = false)
    private ParametroEvaluacion pregunta;

    @ManyToOne
    @JoinColumn(name = "EVPRE_PEVAL_ASPEC", referencedColumnName = "PEVAL_PEVAL")
    private ParametroEvaluacion aspecto;

    @ManyToOne
    @JoinColumn(name = "EVPRE_PEVAL_FACTOR", referencedColumnName = "PEVAL_PEVAL")
    private ParametroEvaluacion factor;

    @ManyToOne
    @JoinColumn(name = "EVPRE_EVAL", referencedColumnName = "EVAL_EVAL", insertable = false, updatable = false)
    private Evaluacion evaluacion;
    

    public PreguntaEvaluacion() {
        super();
    }

    public PreguntaEvaluacionPK getId() {
        return this.id;
    }

    public void setId(PreguntaEvaluacionPK id) {
        this.id = id;
    }

    public Long getOrden() {
        return this.orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public SieduCompetencia getCompetencia() {
        return this.competencia;
    }

    public void setCompetencia(SieduCompetencia competencia) {
        this.competencia = competencia;
    }

    public BigDecimal getValorPorcentaje() {
        return this.valorPorcentaje;
    }

    public void setValorPorcentaje(BigDecimal valorPorcentaje) {
        this.valorPorcentaje = valorPorcentaje;
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

    public ParametroEvaluacion getPregunta() {
        return pregunta;
    }

    public void setPregunta(ParametroEvaluacion pregunta) {
        this.pregunta = pregunta;
    }

    public ParametroEvaluacion getAspecto() {
        return aspecto;
    }

    public void setAspecto(ParametroEvaluacion aspecto) {
        this.aspecto = aspecto;
    }

    public ParametroEvaluacion getFactor() {
        return factor;
    }

    public void setFactor(ParametroEvaluacion factor) {
        this.factor = factor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        PreguntaEvaluacion other = (PreguntaEvaluacion) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
