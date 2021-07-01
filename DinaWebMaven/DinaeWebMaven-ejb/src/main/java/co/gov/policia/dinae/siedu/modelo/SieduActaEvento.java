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
@Table(name = "SIEDU_ACTA_EVENTO")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "SieduActaEvento.findAll", query = "SELECT s FROM SieduActaEvento s")})
public class SieduActaEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ACTA_ACTA", nullable = false)
  private Long actaActa;
  @Size(max = 10)
  @Column(name = "ACTA_NRO_ACTA", length = 10)
  private String actaNroActa;
  @Column(name = "ACTA_FECHA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFecha;
  @Size(max = 10)
  @Column(name = "ACTA_NRO_LIBRO", length = 10)
  private String actaNroLibro;
  @Size(max = 10)
  @Column(name = "ACTA_NRO_FOLIO", length = 10)
  private String actaNroFolio;
  @Column(name = "ACTA_FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFechaInicio;
  @Column(name = "ACTA_FECHA_FIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFechaFin;
  @Column(name = "ACTA_FECHA_GRADO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFechaGrado;
  @Size(max = 30)
  @Column(name = "ACTA_USU_CREA", length = 30)
  private String actaUsuCrea;
  @Column(name = "ACTA_FECHA_CREA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFechaCrea;
  @Size(max = 100)
  @Column(name = "ACTA_MAQUINA_CREA", length = 100)
  private String actaMaquinaCrea;
  @Size(max = 100)
  @Column(name = "ACTA_IP_CREA", length = 100)
  private String actaIpCrea;
  @Column(name = "ACTA_USU_MOD", length = 30)
  private String actaUsuMod;
  @Column(name = "ACTA_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date actaFechaMod;
  @Size(max = 100)
  @Column(name = "ACTA_MAQUINA_MOD", length = 100)
  private String actaMaquinaMod;
  @Size(max = 100)
  @Column(name = "ACTA_IP_MOD", length = 100)
  private String actaIpMod;
  @JoinColumn(name = "ACTA_EVEE", referencedColumnName = "EVEE_EVEE", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduEventoEscuela actaEvee;

  public SieduActaEvento() {
  }

  public SieduActaEvento(Long actaActa) {
    this.actaActa = actaActa;
  }

  public SieduActaEvento(Long actaActa, String actaNroActa, Date actaFecha, String actaNroLibro, String actaNroFolio, Date actaFechaInicio, Date actaFechaFin, Date actaFechaGrado, String actaUsuCrea, Date actaFechaCrea, String actaMaquinaCrea, String actaIpCrea, SieduEventoEscuela actaEvee) {
    this.actaActa = actaActa;
    this.actaNroActa = actaNroActa;
    this.actaFecha = actaFecha;
    this.actaNroLibro = actaNroLibro;
    this.actaNroFolio = actaNroFolio;
    this.actaFechaInicio = actaFechaInicio;
    this.actaFechaFin = actaFechaFin;
    this.actaFechaGrado = actaFechaGrado;
    this.actaUsuCrea = actaUsuCrea;
    this.actaFechaCrea = actaFechaCrea;
    this.actaMaquinaCrea = actaMaquinaCrea;
    this.actaIpCrea = actaIpCrea;
    this.actaEvee = actaEvee;
  }

  public Long getActaActa() {
    return actaActa;
  }

  public void setActaActa(Long actaActa) {
    this.actaActa = actaActa;
  }

  public String getActaNroActa() {
    return actaNroActa;
  }

  public void setActaNroActa(String actaNroActa) {
    this.actaNroActa = actaNroActa;
  }

  public Date getActaFecha() {
    return actaFecha;
  }

  public void setActaFecha(Date actaFecha) {
    this.actaFecha = actaFecha;
  }

  public String getActaNroLibro() {
    return actaNroLibro;
  }

  public void setActaNroLibro(String actaNroLibro) {
    this.actaNroLibro = actaNroLibro;
  }

  public String getActaNroFolio() {
    return actaNroFolio;
  }

  public void setActaNroFolio(String actaNroFolio) {
    this.actaNroFolio = actaNroFolio;
  }

  public Date getActaFechaInicio() {
    return actaFechaInicio;
  }

  public void setActaFechaInicio(Date actaFechaInicio) {
    this.actaFechaInicio = actaFechaInicio;
  }

  public Date getActaFechaFin() {
    return actaFechaFin;
  }

  public void setActaFechaFin(Date actaFechaFin) {
    this.actaFechaFin = actaFechaFin;
  }

  public Date getActaFechaGrado() {
    return actaFechaGrado;
  }

  public void setActaFechaGrado(Date actaFechaGrado) {
    this.actaFechaGrado = actaFechaGrado;
  }

  public String getActaUsuCrea() {
    return actaUsuCrea;
  }

  public void setActaUsuCrea(String actaUsuCrea) {
    this.actaUsuCrea = actaUsuCrea;
  }

  public Date getActaFechaCrea() {
    return actaFechaCrea;
  }

  public void setActaFechaCrea(Date actaFechaCrea) {
    this.actaFechaCrea = actaFechaCrea;
  }

  public String getActaMaquinaCrea() {
    return actaMaquinaCrea;
  }

  public void setActaMaquinaCrea(String actaMaquinaCrea) {
    this.actaMaquinaCrea = actaMaquinaCrea;
  }

  public String getActaIpCrea() {
    return actaIpCrea;
  }

  public void setActaIpCrea(String actaIpCrea) {
    this.actaIpCrea = actaIpCrea;
  }

  public String getActaUsuMod() {
    return actaUsuMod;
  }

  public void setActaUsuMod(String actaUsuMod) {
    this.actaUsuMod = actaUsuMod;
  }

  public Date getActaFechaMod() {
    return actaFechaMod;
  }

  public void setActaFechaMod(Date actaFechaMod) {
    this.actaFechaMod = actaFechaMod;
  }

  public String getActaMaquinaMod() {
    return actaMaquinaMod;
  }

  public void setActaMaquinaMod(String actaMaquinaMod) {
    this.actaMaquinaMod = actaMaquinaMod;
  }

  public String getActaIpMod() {
    return actaIpMod;
  }

  public void setActaIpMod(String actaIpMod) {
    this.actaIpMod = actaIpMod;
  }

  public SieduEventoEscuela getActaEvee() {
    return actaEvee;
  }

  public void setActaEvee(SieduEventoEscuela actaEvee) {
    this.actaEvee = actaEvee;
  }
  

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (actaActa != null ? actaActa.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduActaEvento)) {
      return false;
    }
    SieduActaEvento other = (SieduActaEvento) object;
    if ((this.actaActa == null && other.actaActa != null) || (this.actaActa != null && !this.actaActa.equals(other.actaActa))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduCertificadoEvento[ actaActa=" + actaActa + " ]";
  }

}
