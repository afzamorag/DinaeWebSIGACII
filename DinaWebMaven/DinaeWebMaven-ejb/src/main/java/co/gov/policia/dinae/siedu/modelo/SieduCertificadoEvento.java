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
@Table(name = "SIEDU_CERTIFICADO_EVENTO")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduCertificadoEvento.findAll", query = "SELECT s FROM SieduCertificadoEvento s")})
public class SieduCertificadoEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "CERT_CERT", nullable = false)
  private Long certCert;  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "CERT_USU_CREA", nullable = false, length = 30)
  private String certUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CERT_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date certFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CERT_MAQUINA_CREA", nullable = false, length = 100)
  private String certMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CERT_IP_CREA", nullable = false, length = 100)
  private String certIpCrea;
  @Size(max = 30)
  @Column(name = "CERT_USU_MOD", length = 30)
  private String certUsuMod;
  @Column(name = "CERT_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date certFechaMod;
  @Size(max = 100)
  @Column(name = "CERT_MAQUINA_MOD", length = 100)
  private String certMaquinaMod;
  @Size(max = 100)
  @Column(name = "CERT_IP_MOD", length = 100)
  private String certIpMod;
  @JoinColumn(name = "CERT_PARE", referencedColumnName = "PARE_PARE", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduParticipanteEvento certPare;
  @JoinColumn(name = "CERT_ACTA", referencedColumnName = "ACTA_ACTA", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduActaEvento certActa;

  public SieduCertificadoEvento() {
  }

  public SieduCertificadoEvento(Long certCert) {
    this.certCert = certCert;
  }

  public SieduCertificadoEvento(Long certCert, String certUsuCrea, Date certFechaCrea, String certMaquinaCrea, String certIpCrea, SieduParticipanteEvento certPare, SieduActaEvento certActa) {
    this.certCert = certCert;
    this.certUsuCrea = certUsuCrea;
    this.certFechaCrea = certFechaCrea;
    this.certMaquinaCrea = certMaquinaCrea;
    this.certIpCrea = certIpCrea;
    this.certPare = certPare;
    this.certActa = certActa;
  }



  public Long getCertCert() {
    return certCert;
  }

  public void setCertCert(Long certCert) {
    this.certCert = certCert;
  }

  public String getCertUsuCrea() {
    return certUsuCrea;
  }

  public void setCertUsuCrea(String certUsuCrea) {
    this.certUsuCrea = certUsuCrea;
  }

  public Date getCertFechaCrea() {
    return certFechaCrea;
  }

  public void setCertFechaCrea(Date certFechaCrea) {
    this.certFechaCrea = certFechaCrea;
  }

  public String getCertMaquinaCrea() {
    return certMaquinaCrea;
  }

  public void setCertMaquinaCrea(String certMaquinaCrea) {
    this.certMaquinaCrea = certMaquinaCrea;
  }

  public String getCertIpCrea() {
    return certIpCrea;
  }

  public void setCertIpCrea(String certIpCrea) {
    this.certIpCrea = certIpCrea;
  }

  public String getCertUsuMod() {
    return certUsuMod;
  }

  public void setCertUsuMod(String certUsuMod) {
    this.certUsuMod = certUsuMod;
  }

  public Date getCertFechaMod() {
    return certFechaMod;
  }

  public void setCertFechaMod(Date certFechaMod) {
    this.certFechaMod = certFechaMod;
  }

  public String getCertMaquinaMod() {
    return certMaquinaMod;
  }

  public void setCertMaquinaMod(String certMaquinaMod) {
    this.certMaquinaMod = certMaquinaMod;
  }

  public String getCertIpMod() {
    return certIpMod;
  }

  public void setCertIpMod(String certIpMod) {
    this.certIpMod = certIpMod;
  }

  public SieduParticipanteEvento getCertPare() {
    return certPare;
  }

  public void setCertPare(SieduParticipanteEvento certPare) {
    this.certPare = certPare;
  }

  public SieduActaEvento getCertActa() {
    return certActa;
  }

  public void setCertActa(SieduActaEvento certActa) {
    this.certActa = certActa;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (certCert != null ? certCert.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduCertificadoEvento)) {
      return false;
    }
    SieduCertificadoEvento other = (SieduCertificadoEvento) object;
    if ((this.certCert == null && other.certCert != null) || (this.certCert != null && !this.certCert.equals(other.certCert))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCertificadoEvento[ certCert=" + certCert + " ]";
  }
  
}
