/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_EVENTO_CATEGORIA")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduEventoCategoria.findAll", query = "SELECT s FROM SieduEventoCategoria s"),
  @NamedQuery(name = SieduEventoCategoria.FIND_BY_EVENTO, query = "SELECT s FROM SieduEventoCategoria s where s.evcaEven.id = :evento"),
  @NamedQuery(name = SieduEventoCategoria.FIND_CATEGORIES_BY_EVALUATION, query = "SELECT c FROM SieduEventoCategoria ec JOIN ec.evcaCategoria c WHERE ec.evcaEven.id = :idEvento")
})
public class SieduEventoCategoria implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_EVENTO = "SieduEventoCategoria.findByEvento";
  public static final String FIND_CATEGORIES_BY_EVALUATION = "Categoria.findCategoriesByEvaluation";

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVCA_EVCA", nullable = false)
  @SequenceGenerator(name = "EVENTO_CAT_SEQ_GEN", sequenceName = "SIEDU_EVENTO_CATEGORIA_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTO_CAT_SEQ_GEN")
  private Long evcaEvca;
  @Column(name = "EVCA_ORDEN")
  private Integer evcaOrden;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "EVCA_USU_CREA", nullable = false, length = 30)
  private String evcaUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVCA_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date evcaFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "EVCA_MAQUINA_CREA", nullable = false, length = 100)
  private String evcaMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "EVCA_IP_CREA", nullable = false, length = 100)
  private String evcaIpCrea;
  @Size(max = 30)
  @Column(name = "EVCA_USU_MOD", length = 30)
  private String evcaUsuMod;
  @Column(name = "EVCA_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date evcaFechaMod;
  @Size(max = 100)
  @Column(name = "EVCA_MAQUINA_MOD", length = 100)
  private String evcaMaquinaMod;
  @Size(max = 100)
  @Column(name = "EVCA_IP_MOD", length = 100)
  private String evcaIpMod;
  @JoinColumn(name = "EVCA_EVEN", referencedColumnName = "EVEN_EVEN", nullable = false)
  @ManyToOne(optional = false)
  private SieduEvento evcaEven;
  @JoinColumns({
    @JoinColumn(name = "EVCA_CATEGORIA_ID", referencedColumnName = "ID_CATEGORIA", nullable = false),
    @JoinColumn(name = "EVCA_FUERZA", referencedColumnName = "FUERZA", nullable = false)
  })
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Categoria evcaCategoria;

  public SieduEventoCategoria() {
  }

  public SieduEventoCategoria(Long evcaEvca) {
    this.evcaEvca = evcaEvca;
  }

  public Long getEvcaEvca() {
    return evcaEvca;
  }

  public void setEvcaEvca(Long evcaEvca) {
    this.evcaEvca = evcaEvca;
  }

  public Integer getEvcaOrden() {
    return evcaOrden;
  }

  public void setEvcaOrden(Integer evcaOrden) {
    this.evcaOrden = evcaOrden;
  }

  public String getEvcaUsuCrea() {
    return evcaUsuCrea;
  }

  public void setEvcaUsuCrea(String evcaUsuCrea) {
    this.evcaUsuCrea = evcaUsuCrea;
  }

  public Date getEvcaFechaCrea() {
    return evcaFechaCrea;
  }

  public void setEvcaFechaCrea(Date evcaFechaCrea) {
    this.evcaFechaCrea = evcaFechaCrea;
  }

  public String getEvcaMaquinaCrea() {
    return evcaMaquinaCrea;
  }

  public void setEvcaMaquinaCrea(String evcaMaquinaCrea) {
    this.evcaMaquinaCrea = evcaMaquinaCrea;
  }

  public String getEvcaIpCrea() {
    return evcaIpCrea;
  }

  public void setEvcaIpCrea(String evcaIpCrea) {
    this.evcaIpCrea = evcaIpCrea;
  }

  public String getEvcaUsuMod() {
    return evcaUsuMod;
  }

  public void setEvcaUsuMod(String evcaUsuMod) {
    this.evcaUsuMod = evcaUsuMod;
  }

  public Date getEvcaFechaMod() {
    return evcaFechaMod;
  }

  public void setEvcaFechaMod(Date evcaFechaMod) {
    this.evcaFechaMod = evcaFechaMod;
  }

  public String getEvcaMaquinaMod() {
    return evcaMaquinaMod;
  }

  public void setEvcaMaquinaMod(String evcaMaquinaMod) {
    this.evcaMaquinaMod = evcaMaquinaMod;
  }

  public String getEvcaIpMod() {
    return evcaIpMod;
  }

  public void setEvcaIpMod(String evcaIpMod) {
    this.evcaIpMod = evcaIpMod;
  }

  public SieduEvento getEvcaEven() {
    return evcaEven;
  }

  public void setEvcaEven(SieduEvento evcaEven) {
    this.evcaEven = evcaEven;
  }

  public Categoria getEvcaCategoria() {
    return evcaCategoria;
  }

  public void setEvcaCategoria(Categoria evcaCategoria) {
    this.evcaCategoria = evcaCategoria;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (evcaEvca != -1L ? evcaEvca.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduEventoCategoria)) {
      return false;
    }
    SieduEventoCategoria other = (SieduEventoCategoria) object;
    if ((this.evcaEvca == null && other.evcaEvca != null) || (this.evcaEvca != null && !this.evcaEvca.equals(other.evcaEvca))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduEventoCategoria[ evcaEvca=" + evcaEvca + " ]";
  }

}
