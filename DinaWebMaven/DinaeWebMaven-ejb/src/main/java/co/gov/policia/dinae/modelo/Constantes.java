package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "CONSTANTES")
@NamedQueries({
    @NamedQuery(name = "Constantes.findAll", query = "SELECT c FROM Constantes c WHERE c.estado = 'ACTIVO' ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "Constantes.findAPorIdConstantes", query = "SELECT c FROM Constantes c WHERE c.idConstantes = :idConstantes AND c.estado = 'ACTIVO'"),
    @NamedQuery(name = "ConstantesDTO.findAllId", query = "SELECT NEW co.gov.policia.dinae.dto.ConstantesDTO ( c.idConstantes, c.tipo, c.codigo, c.valor, c.estado ) FROM Constantes c WHERE c.idConstantes = :idConstantes"),
    @NamedQuery(name = "Constantes.findAllPorTipo", query = "SELECT c FROM Constantes c WHERE c.tipo = :tipo AND c.estado = 'ACTIVO' ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "ConstantesDTO.findAllPorTipo", query = "SELECT NEW co.gov.policia.dinae.dto.ConstantesDTO ( c.idConstantes, c.tipo, c.codigo, c.valor, c.estado ) FROM Constantes c WHERE c.tipo = :tipo AND c.estado = 'ACTIVO' ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "Constantes.findAllPorTipoPorIdConstantes", query = "SELECT c FROM Constantes c WHERE c.tipo = :tipo AND c.idConstantes IN (:codigos) AND c.estado = 'ACTIVO' ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "Constantes.findAllPorTipoPorCodigo", query = "SELECT c FROM Constantes c WHERE c.tipo = :tipo AND c.codigo= :codigo AND c.estado = 'ACTIVO' ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "Constantes.findAllPorTipoNoEstado", query = "SELECT c FROM Constantes c WHERE c.tipo = :tipo ORDER BY c.idConstantes ASC"),
    @NamedQuery(name = "Constantes.findAllPorIdNoEstado", query = "SELECT c FROM Constantes c WHERE c.idConstantes = :idConstantes")
})
@XmlRootElement
public class Constantes implements IDataModel, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Constantes_seq_gen")
    //@SequenceGenerator(name = "Constantes_seq_gen", sequenceName = "SEC_CONSTANTES")
    @Column(name = "ID_CONSTANTES")
    private Long idConstantes;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "ESTADO", length = 20, nullable = false)
    @Pattern(regexp = "ACTIVO|INACTIVO")
    private String estado;

    public Constantes() {
    }

    public Constantes(Long idConstantes) {
        this.idConstantes = idConstantes;
    }

    public Constantes(Long idConstantes, String tipo, String codigo, String valor) {
        this.idConstantes = idConstantes;
        this.tipo = tipo;
        this.codigo = codigo;
        this.valor = valor;
    }

    @XmlAttribute
    public Long getIdConstantes() {
        return idConstantes;
    }

    public void setIdConstantes(Long idConstantes) {
        this.idConstantes = idConstantes;
    }

    @XmlAttribute
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlAttribute
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlAttribute
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @XmlAttribute
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConstantes != null ? idConstantes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Constantes)) {
            return false;
        }
        Constantes other = (Constantes) object;
        if ((this.idConstantes == null && other.idConstantes != null) || (this.idConstantes != null && !this.idConstantes.equals(other.idConstantes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.modelo.Constantes[ idConstantes=" + idConstantes + " ]";
    }

    @Override
    public String getLlaveModel() {
        if (idConstantes == null) {
            return null;
        }
        return idConstantes.toString();
    }
}
