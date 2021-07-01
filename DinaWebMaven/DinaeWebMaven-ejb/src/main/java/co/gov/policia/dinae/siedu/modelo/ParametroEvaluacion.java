package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ParametroEvaluacion
 *
 */
@Entity
@Table(name = "SIEDU_PARAMETRO_EVALUACION")
@NamedQueries({
    @NamedQuery(name = ParametroEvaluacion.FIND_BY_TIPO, query = "SELECT pe FROM ParametroEvaluacion pe WHERE pe.tipo.id = :idTipo ORDER BY pe.descripcion")
})
public class ParametroEvaluacion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3000322847959384736L;

    public static final String FIND_BY_TIPO = "ParametroEvaluacion.findByTipo";

    @Id
    @Column(name = "PEVAL_PEVAL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PARAMETRO_EVALUACION_SEQ_GEN")
    @SequenceGenerator(name = "SIEDU_PARAMETRO_EVALUACION_SEQ_GEN", sequenceName = "SIEDU_PARAMETRO_EVALUACION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "PEVAL_DESCRI")
    private String descripcion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PEVAL_DOM_TIPO", referencedColumnName = "ID_DOMINIO")
    private Dominio tipo;

    @Column(name = "PEVAL_USU_CREA")
    private String usuarioCrea;

    @Column(name = "PEVAL_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;

    @Column(name = "PEVAL_MAQUINA_CREA")
    private String maquinaCrea;

    @Column(name = "PEVAL_IP_CREA")
    private String ipCrea;

    @Column(name = "PEVAL_USU_MOD")
    private String usuarioModifica;

    @Column(name = "PEVAL_FECHA_MOD")
    @Temporal(TemporalType.DATE)
    private Date fechaModifica;

    @Column(name = "PEVAL_MAQUINA_MOD")
    private String maquinaModifica;

    @Column(name = "PEVAL_IP_MOD")
    private String ipModifica;

    public ParametroEvaluacion() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dominio getTipo() {
        return tipo;
    }

    public void setTipo(Dominio tipo) {
        this.tipo = tipo;
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
        ParametroEvaluacion other = (ParametroEvaluacion) obj;
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
