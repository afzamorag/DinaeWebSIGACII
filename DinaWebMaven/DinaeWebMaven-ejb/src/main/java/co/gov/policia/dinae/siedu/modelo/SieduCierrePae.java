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
import javax.persistence.Id;
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
@Table(name = "SIEDU_CIERRE_PAE")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduCierrePae.findAll", query = "SELECT s FROM SieduCierrePae s")})
public class SieduCierrePae implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "RPAE_RPAE", nullable = false)
  private Integer rpaeRpae;
  @Basic(optional = false)
  @NotNull
  @Column(name = "RPAE_CAPA_NRO_NECE", nullable = false)
  private short rpaeCapaNroNece;
  @Basic(optional = false)
  @NotNull
  @Column(name = "RPAE_FORM_NRO_NECE", nullable = false)
  private short rpaeFormNroNece;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "RPAE_USU_CREA", nullable = false, length = 30)
  private String rpaeUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "RPAE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date rpaeFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "RPAE_MAQUINA_CREA", nullable = false, length = 100)
  private String rpaeMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "RPAE_IP_CREA", nullable = false, length = 100)
  private String rpaeIpCrea;
  @Size(max = 30)
  @Column(name = "RPAE_USU_MOD", length = 30)
  private String rpaeUsuMod;
  @Column(name = "RPAE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date rpaeFechaMod;
  @Size(max = 100)
  @Column(name = "RPAE_MAQUINA_MOD", length = 100)
  private String rpaeMaquinaMod;
  @Size(max = 100)
  @Column(name = "RPAE_IP_MOD", length = 100)
  private String rpaeIpMod;
  @JoinColumn(name = "RPAE_FORM", referencedColumnName = "FORM_FORM", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Formacion rpaeForm;
  @JoinColumn(name = "RPAE_CAPA", referencedColumnName = "CAPA_CAPA", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Capacitacion rpaeCapa;

  public SieduCierrePae() {
  }

  public SieduCierrePae(Integer rpaeRpae) {
    this.rpaeRpae = rpaeRpae;
  }

  public SieduCierrePae(Integer rpaeRpae, short rpaeCapaNroNece, short rpaeFormNroNece, String rpaeUsuCrea, Date rpaeFechaCrea, String rpaeMaquinaCrea, String rpaeIpCrea) {
    this.rpaeRpae = rpaeRpae;
    this.rpaeCapaNroNece = rpaeCapaNroNece;
    this.rpaeFormNroNece = rpaeFormNroNece;
    this.rpaeUsuCrea = rpaeUsuCrea;
    this.rpaeFechaCrea = rpaeFechaCrea;
    this.rpaeMaquinaCrea = rpaeMaquinaCrea;
    this.rpaeIpCrea = rpaeIpCrea;
  }

  public Integer getRpaeRpae() {
    return rpaeRpae;
  }

  public void setRpaeRpae(Integer rpaeRpae) {
    this.rpaeRpae = rpaeRpae;
  }

  public short getRpaeCapaNroNece() {
    return rpaeCapaNroNece;
  }

  public void setRpaeCapaNroNece(short rpaeCapaNroNece) {
    this.rpaeCapaNroNece = rpaeCapaNroNece;
  }

  public short getRpaeFormNroNece() {
    return rpaeFormNroNece;
  }

  public void setRpaeFormNroNece(short rpaeFormNroNece) {
    this.rpaeFormNroNece = rpaeFormNroNece;
  }

  public String getRpaeUsuCrea() {
    return rpaeUsuCrea;
  }

  public void setRpaeUsuCrea(String rpaeUsuCrea) {
    this.rpaeUsuCrea = rpaeUsuCrea;
  }

  public Date getRpaeFechaCrea() {
    return rpaeFechaCrea;
  }

  public void setRpaeFechaCrea(Date rpaeFechaCrea) {
    this.rpaeFechaCrea = rpaeFechaCrea;
  }

  public String getRpaeMaquinaCrea() {
    return rpaeMaquinaCrea;
  }

  public void setRpaeMaquinaCrea(String rpaeMaquinaCrea) {
    this.rpaeMaquinaCrea = rpaeMaquinaCrea;
  }

  public String getRpaeIpCrea() {
    return rpaeIpCrea;
  }

  public void setRpaeIpCrea(String rpaeIpCrea) {
    this.rpaeIpCrea = rpaeIpCrea;
  }

  public String getRpaeUsuMod() {
    return rpaeUsuMod;
  }

  public void setRpaeUsuMod(String rpaeUsuMod) {
    this.rpaeUsuMod = rpaeUsuMod;
  }

  public Date getRpaeFechaMod() {
    return rpaeFechaMod;
  }

  public void setRpaeFechaMod(Date rpaeFechaMod) {
    this.rpaeFechaMod = rpaeFechaMod;
  }

  public String getRpaeMaquinaMod() {
    return rpaeMaquinaMod;
  }

  public void setRpaeMaquinaMod(String rpaeMaquinaMod) {
    this.rpaeMaquinaMod = rpaeMaquinaMod;
  }

  public String getRpaeIpMod() {
    return rpaeIpMod;
  }

  public void setRpaeIpMod(String rpaeIpMod) {
    this.rpaeIpMod = rpaeIpMod;
  }

  public Formacion getRpaeForm() {
    return rpaeForm;
  }

  public void setRpaeForm(Formacion rpaeForm) {
    this.rpaeForm = rpaeForm;
  }

  public Capacitacion getRpaeCapa() {
    return rpaeCapa;
  }

  public void setRpaeCapa(Capacitacion rpaeCapa) {
    this.rpaeCapa = rpaeCapa;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (rpaeRpae != null ? rpaeRpae.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduCierrePae)) {
      return false;
    }
    SieduCierrePae other = (SieduCierrePae) object;
    if ((this.rpaeRpae == null && other.rpaeRpae != null) || (this.rpaeRpae != null && !this.rpaeRpae.equals(other.rpaeRpae))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCierrePae[ rpaeRpae=" + rpaeRpae + " ]";
  }
  
}
