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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "SIEDU_COMPETENCIA_EVENTO")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduCompetenciaEvento.findAll", query = "SELECT s FROM SieduCompetenciaEvento s"),
  @NamedQuery(name = SieduCompetenciaEvento.FIND_BY_EVENTO, query = "SELECT s FROM SieduCompetenciaEvento s JOIN FETCH s.sieduCompetencia WHERE s.sieduEvento.id = :evento")
})
public class SieduCompetenciaEvento implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String FIND_BY_EVENTO = "SieduCompetenciaEvento.findByEvento";
  @EmbeddedId
  protected SieduCompetenciaEventoPK sieduCompetenciaEventoPK;
  @Column(name = "COMPE_ORDEN")
  private Long compeOrden;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "COMPE_USU_CREA", nullable = false, length = 30)
  private String compeUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COMPE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date compeFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COMPE_MAQUINA_CREA", nullable = false, length = 100)
  private String compeMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COMPE_IP_CREA", nullable = false, length = 100)
  private String compeIpCrea;
  @Size(max = 30)
  @Column(name = "COMPE_USU_MOD", length = 30)
  private String compeUsuMod;
  @Column(name = "COMPE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date compeFechaMod;
  @Size(max = 100)
  @Column(name = "COMPE_MAQUINA_MOD", length = 100)
  private String compeMaquinaMod;
  @Size(max = 100)
  @Column(name = "COMPE_IP_MOD", length = 100)
  private String compeIpMod;
  @JoinColumn(name = "COMPE_EVEN", referencedColumnName = "EVEN_EVEN", nullable = false, insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private SieduEvento sieduEvento;
  @JoinColumn(name = "COMPE_COMP", referencedColumnName = "COMP_COMP", nullable = false, insertable = false, updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private SieduCompetencia sieduCompetencia;

  public SieduCompetenciaEvento() {
  }

  public SieduCompetenciaEvento(SieduCompetenciaEventoPK sieduCompetenciaEventoPK) {
    this.sieduCompetenciaEventoPK = sieduCompetenciaEventoPK;
  }

  public SieduCompetenciaEvento(SieduCompetenciaEventoPK sieduCompetenciaEventoPK, Long compeOrden, String compeUsuCrea, Date compeFechaCrea, String compeMaquinaCrea, String compeIpCrea) {
    this.sieduCompetenciaEventoPK = sieduCompetenciaEventoPK;
    this.compeOrden = compeOrden;
    this.compeUsuCrea = compeUsuCrea;
    this.compeFechaCrea = compeFechaCrea;
    this.compeMaquinaCrea = compeMaquinaCrea;
    this.compeIpCrea = compeIpCrea;
  }

  public SieduCompetenciaEvento(Long compeEven, Long compeComp) {
    this.sieduCompetenciaEventoPK = new SieduCompetenciaEventoPK(compeEven, compeComp);
  }

  public SieduCompetenciaEventoPK getSieduCompetenciaEventoPK() {
    return sieduCompetenciaEventoPK;
  }

  public void setSieduCompetenciaEventoPK(SieduCompetenciaEventoPK sieduCompetenciaEventoPK) {
    this.sieduCompetenciaEventoPK = sieduCompetenciaEventoPK;
  }

  public Long getCompeOrden() {
    return compeOrden;
  }

  public void setCompeOrden(Long compeOrden) {
    this.compeOrden = compeOrden;
  }

  public String getCompeUsuCrea() {
    return compeUsuCrea;
  }

  public void setCompeUsuCrea(String compeUsuCrea) {
    this.compeUsuCrea = compeUsuCrea;
  }

  public Date getCompeFechaCrea() {
    return compeFechaCrea;
  }

  public void setCompeFechaCrea(Date compeFechaCrea) {
    this.compeFechaCrea = compeFechaCrea;
  }

  public String getCompeMaquinaCrea() {
    return compeMaquinaCrea;
  }

  public void setCompeMaquinaCrea(String compeMaquinaCrea) {
    this.compeMaquinaCrea = compeMaquinaCrea;
  }

  public String getCompeIpCrea() {
    return compeIpCrea;
  }

  public void setCompeIpCrea(String compeIpCrea) {
    this.compeIpCrea = compeIpCrea;
  }

  public String getCompeUsuMod() {
    return compeUsuMod;
  }

  public void setCompeUsuMod(String compeUsuMod) {
    this.compeUsuMod = compeUsuMod;
  }

  public Date getCompeFechaMod() {
    return compeFechaMod;
  }

  public void setCompeFechaMod(Date compeFechaMod) {
    this.compeFechaMod = compeFechaMod;
  }

  public String getCompeMaquinaMod() {
    return compeMaquinaMod;
  }

  public void setCompeMaquinaMod(String compeMaquinaMod) {
    this.compeMaquinaMod = compeMaquinaMod;
  }

  public String getCompeIpMod() {
    return compeIpMod;
  }

  public void setCompeIpMod(String compeIpMod) {
    this.compeIpMod = compeIpMod;
  }

  public SieduEvento getSieduEvento() {
    return sieduEvento;
  }

  public void setSieduEvento(SieduEvento sieduEvento) {
    this.sieduEvento = sieduEvento;
  }

  public SieduCompetencia getSieduCompetencia() {
    return sieduCompetencia;
  }

  public void setSieduCompetencia(SieduCompetencia sieduCompetencia) {
    this.sieduCompetencia = sieduCompetencia;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sieduCompetenciaEventoPK != null ? sieduCompetenciaEventoPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduCompetenciaEvento)) {
      return false;
    }
    SieduCompetenciaEvento other = (SieduCompetenciaEvento) object;
    if ((this.sieduCompetenciaEventoPK == null && other.sieduCompetenciaEventoPK != null) || (this.sieduCompetenciaEventoPK != null && !this.sieduCompetenciaEventoPK.equals(other.sieduCompetenciaEventoPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCompetenciaEvento[ sieduCompetenciaEventoPK=" + sieduCompetenciaEventoPK + " ]";
  }

}
