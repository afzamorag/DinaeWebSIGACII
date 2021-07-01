/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OFITE
 */
@Entity
@Table(name = "SIEDU_COMPETENCIA")
@NamedQueries({
  @NamedQuery(name = "SieduCompetencia.findAll", query = "SELECT s FROM SieduCompetencia s")
})
@XmlRootElement
public class SieduCompetencia implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_TIPO = "SieduCompetencia.findByTipo";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "COMP_COMP", nullable = false)
  private Long compComp;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "COMP_DESCRI", nullable = false, length = 200)
  private String compDescri;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COMP_DOM_TPCOMPE", nullable = false)
  private Long compDomTpcompe;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "COMP_USU_CREA", nullable = false, length = 30)
  private String compUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "COMP_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date compFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COMP_MAQUINA_CREA", nullable = false, length = 100)
  private String compMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "COMP_IP_CREA", nullable = false, length = 100)
  private String compIpCrea;
  @Size(max = 30)
  @Column(name = "COMP_USU_MOD", length = 30)
  private String compUsuMod;
  @Column(name = "COMP_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date compFechaMod;
  @Size(max = 100)
  @Column(name = "COMP_MAQUINA_MOD", length = 100)
  private String compMaquinaMod;
  @Size(max = 100)
  @Column(name = "COMP_IP_MOD", length = 100)
  private String compIpMod;

  public SieduCompetencia() {
  }

  public SieduCompetencia(Long compComp) {
    this.compComp = compComp;
  }

  public Long getCompComp() {
    return compComp;
  }

  public void setCompComp(Long compComp) {
    this.compComp = compComp;
  }

  public String getCompDescri() {
    return compDescri;
  }

  public void setCompDescri(String compDescri) {
    this.compDescri = compDescri;
  }

  public Long getCompDomTpcompe() {
    return compDomTpcompe;
  }

  public void setCompDomTpcompe(Long compDomTpcompe) {
    this.compDomTpcompe = compDomTpcompe;
  }

  @XmlTransient
  public String getCompUsuCrea() {
    return compUsuCrea;
  }

  public void setCompUsuCrea(String compUsuCrea) {
    this.compUsuCrea = compUsuCrea;
  }

  @XmlTransient
  public Date getCompFechaCrea() {
    return compFechaCrea;
  }

  public void setCompFechaCrea(Date compFechaCrea) {
    this.compFechaCrea = compFechaCrea;
  }

  @XmlTransient
  public String getCompMaquinaCrea() {
    return compMaquinaCrea;
  }

  public void setCompMaquinaCrea(String compMaquinaCrea) {
    this.compMaquinaCrea = compMaquinaCrea;
  }

  @XmlTransient
  public String getCompIpCrea() {
    return compIpCrea;
  }

  public void setCompIpCrea(String compIpCrea) {
    this.compIpCrea = compIpCrea;
  }

  @XmlTransient
  public String getCompUsuMod() {
    return compUsuMod;
  }

  public void setCompUsuMod(String compUsuMod) {
    this.compUsuMod = compUsuMod;
  }

  @XmlTransient
  public Date getCompFechaMod() {
    return compFechaMod;
  }

  public void setCompFechaMod(Date compFechaMod) {
    this.compFechaMod = compFechaMod;
  }

  @XmlTransient
  public String getCompMaquinaMod() {
    return compMaquinaMod;
  }

  public void setCompMaquinaMod(String compMaquinaMod) {
    this.compMaquinaMod = compMaquinaMod;
  }

  @XmlTransient
  public String getCompIpMod() {
    return compIpMod;
  }

  public void setCompIpMod(String compIpMod) {
    this.compIpMod = compIpMod;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.compComp);
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
    final SieduCompetencia other = (SieduCompetencia) obj;
    if (!Objects.equals(this.compComp, other.compComp)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCompetencia[ compComp=" + compComp + " ]";
  }

}
