/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
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
@Table(name = "REGIONALES")
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = Regionales.FIND_ALL, query = "SELECT s FROM Regionales s")
})
@XmlRootElement
public class Regionales implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "Regionales.findAll";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO", nullable = false)
    private Long id;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public Regionales() {
    }

    public Regionales(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regionales)) {
            return false;
        }
        Regionales other = (Regionales) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.gov.policia.dinae.siedu.modelo.Necesidad[ neceNece=" + getId() + " ]";
    }
}
