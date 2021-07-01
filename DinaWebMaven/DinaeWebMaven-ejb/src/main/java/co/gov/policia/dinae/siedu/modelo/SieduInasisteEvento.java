/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "SIEDU_INASISTE_EVENTO")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduInasisteEvento.findAll", query = "SELECT s FROM SieduInasisteEvento s"),
  @NamedQuery(name = SieduInasisteEvento.FIND_BY_INAEPARE_INAE_FECHA, query = "SELECT s FROM SieduInasisteEvento s WHERE s.inaePare.parePers.persNroid =:identificacion AND s.inaeFecha =:fecha"),
  @NamedQuery(name = SieduInasisteEvento.FIND_BY_INAEPARE_EVEE, query = "SELECT s FROM SieduInasisteEvento s WHERE s.inaePare.pareEvee.eveeEvee =:evento"),
  @NamedQuery(name = SieduInasisteEvento.FIND_BY_INAEPARE, query = "SELECT s FROM SieduInasisteEvento s WHERE s.inaePare.parePers.persNroid =:identificacion AND s.inaePare.pareEvee.eveeEvee =:evento")
})
public class SieduInasisteEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_INAEPARE_INAE_FECHA = "SieduInasisteEvento.findByInaepareInaefecha";
  public static final String FIND_BY_INAEPARE_EVEE = "SieduInasisteEvento.findByInaepareEvee";
  public static final String FIND_BY_INAEPARE = "SieduInasisteEvento.findByInaepare";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "INAE_INAE", nullable = false)
  @SequenceGenerator(name = "SIEDU_INASISTE_EVENTO_SEQ_GEN", sequenceName = "SIEDU_INASISTE_EVENTO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PARTICIPANTE_EVENTO_SEQ_GEN")
  private Long inaeInae;
  @Basic(optional = false)
  @NotNull
  @Column(name = "INAE_FECHA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date inaeFecha;
  @Column(name = "INAE_TIEMPO")
  private Short inaeTiempo;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "INAE_USU_CREA", nullable = false, length = 30)
  private String inaeUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "INAE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date inaeFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "INAE_MAQUINA_CREA", nullable = false, length = 100)
  private String inaeMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "INAE_IP_CREA", nullable = false, length = 100)
  private String inaeIpCrea;
  @Size(max = 30)
  @Column(name = "INAE_USU_MOD", length = 30)
  private String inaeUsuMod;
  @Column(name = "INAE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date inaeFechaMod;
  @Size(max = 100)
  @Column(name = "INAE_MAQUINA_MOD", length = 100)
  private String inaeMaquinaMod;
  @Size(max = 100)
  @Column(name = "INAE_IP_MOD", length = 100)
  private String inaeIpMod;
  @JoinColumn(name = "INAE_PARE", referencedColumnName = "PARE_PARE", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduParticipanteEvento inaePare;

  public SieduInasisteEvento() {
  }

  public SieduInasisteEvento(Long inaeInae) {
    this.inaeInae = inaeInae;
  }

  public SieduInasisteEvento(Long inaeInae, Date inaeFecha, String inaeUsuCrea, Date inaeFechaCrea, String inaeMaquinaCrea, String inaeIpCrea) {
    this.inaeInae = inaeInae;
    this.inaeFecha = inaeFecha;
    this.inaeUsuCrea = inaeUsuCrea;
    this.inaeFechaCrea = inaeFechaCrea;
    this.inaeMaquinaCrea = inaeMaquinaCrea;
    this.inaeIpCrea = inaeIpCrea;
  }

  public Long getInaeInae() {
    return inaeInae;
  }

  public void setInaeInae(Long inaeInae) {
    this.inaeInae = inaeInae;
  }

  public Date getInaeFecha() {
    return inaeFecha;
  }

  public void setInaeFecha(Date inaeFecha) {
    this.inaeFecha = inaeFecha;
  }

  public Short getInaeTiempo() {
    return inaeTiempo;
  }

  public void setInaeTiempo(Short inaeTiempo) {
    this.inaeTiempo = inaeTiempo;
  }

  public String getInaeUsuCrea() {
    return inaeUsuCrea;
  }

  public void setInaeUsuCrea(String inaeUsuCrea) {
    this.inaeUsuCrea = inaeUsuCrea;
  }

  public Date getInaeFechaCrea() {
    return inaeFechaCrea;
  }

  public void setInaeFechaCrea(Date inaeFechaCrea) {
    this.inaeFechaCrea = inaeFechaCrea;
  }

  public String getInaeMaquinaCrea() {
    return inaeMaquinaCrea;
  }

  public void setInaeMaquinaCrea(String inaeMaquinaCrea) {
    this.inaeMaquinaCrea = inaeMaquinaCrea;
  }

  public String getInaeIpCrea() {
    return inaeIpCrea;
  }

  public void setInaeIpCrea(String inaeIpCrea) {
    this.inaeIpCrea = inaeIpCrea;
  }

  public String getInaeUsuMod() {
    return inaeUsuMod;
  }

  public void setInaeUsuMod(String inaeUsuMod) {
    this.inaeUsuMod = inaeUsuMod;
  }

  public Date getInaeFechaMod() {
    return inaeFechaMod;
  }

  public void setInaeFechaMod(Date inaeFechaMod) {
    this.inaeFechaMod = inaeFechaMod;
  }

  public String getInaeMaquinaMod() {
    return inaeMaquinaMod;
  }

  public void setInaeMaquinaMod(String inaeMaquinaMod) {
    this.inaeMaquinaMod = inaeMaquinaMod;
  }

  public String getInaeIpMod() {
    return inaeIpMod;
  }

  public void setInaeIpMod(String inaeIpMod) {
    this.inaeIpMod = inaeIpMod;
  }

  public SieduParticipanteEvento getInaePare() {
    return inaePare;
  }

  public void setInaePare(SieduParticipanteEvento inaePare) {
    this.inaePare = inaePare;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (inaeInae != null ? inaeInae.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduInasisteEvento)) {
      return false;
    }
    SieduInasisteEvento other = (SieduInasisteEvento) object;
    if ((this.inaeInae == null && other.inaeInae != null) || (this.inaeInae != null && !this.inaeInae.equals(other.inaeInae))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduInasisteEvento[ inaeInae=" + inaeInae + " ]";
  }

}
