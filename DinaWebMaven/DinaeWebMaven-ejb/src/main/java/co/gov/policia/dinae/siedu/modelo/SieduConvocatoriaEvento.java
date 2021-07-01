/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
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
@Table(name = "SIEDU_CONVOCATORIA_EVENTO")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = SieduConvocatoriaEvento.FIND_ALL, query = "SELECT s FROM SieduConvocatoriaEvento s"),
  @NamedQuery(name = SieduConvocatoriaEvento.FIND_BY_CONE_EVEE, query = "SELECT s FROM SieduConvocatoriaEvento s WHERE s.coneEvee =:evento")
})
public class SieduConvocatoriaEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_CONE_EVEE = "SieduConvocatoriaEvento.findByConeEvee";
  public static final String FIND_ALL = "SieduConvocatoriaEvento.findAll";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "CONE_CONE", nullable = false)
  @SequenceGenerator(name = "CONVOCATORIA_SEQ_GEN", sequenceName = "SIEDU_CONVOCATORIA_EVENTO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONVOCATORIA_SEQ_GEN")
  private Long coneCone;
  @JoinColumns({
    @JoinColumn(name = "CONE_UDE_UDEPE", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "CONE_UDE_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private UnidadDependencia coneUdeUdepe;  
  @JoinColumn(name = "CONE_DOM_TPCOMU", referencedColumnName = "ID_DOMINIO", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Dominio tipoDocumento;  
  @Size(max = 20)
  @Column(name = "CONE_NRO_COMU", length = 20)
  private String coneNroComu;
  @Column(name = "CONE_NRO_CONVOCADOS")
  private int coneNroConvocados;
  @Column(name = "CONE_NRO_PRESENTADOS")
  private Integer coneNroPresentados;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "CONE_USU_CREA", nullable = false, length = 30)
  private String coneUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CONE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date coneFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CONE_MAQUINA_CREA", nullable = false, length = 100)
  private String coneMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CONE_IP_CREA", nullable = false, length = 100)
  private String coneIpCrea;
  @Size(max = 30)
  @Column(name = "CONE_USU_MOD", length = 30)
  private String coneUsuMod;
  @Column(name = "CONE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date coneFechaMod;
  @Size(max = 100)
  @Column(name = "CONE_MAQUINA_MOD", length = 100)
  private String coneMaquinaMod;
  @Size(max = 100)
  @Column(name = "CONE_IP_MOD", length = 100)
  private String coneIpMod;
  @JoinColumn(name = "CONE_EVEE", referencedColumnName = "EVEE_EVEE", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduEventoEscuela coneEvee;

  public SieduConvocatoriaEvento() {
  }

  public SieduConvocatoriaEvento(Long coneCone) {
    this.coneCone = coneCone;
  }

  public SieduConvocatoriaEvento(Long coneCone, UnidadDependencia coneUdeUdepe, String coneUsuCrea, Date coneFechaCrea, String coneMaquinaCrea, String coneIpCrea) {
    this.coneCone = coneCone;
    this.coneUdeUdepe = coneUdeUdepe;
    this.coneUsuCrea = coneUsuCrea;
    this.coneFechaCrea = coneFechaCrea;
    this.coneMaquinaCrea = coneMaquinaCrea;
    this.coneIpCrea = coneIpCrea;
  }

  public Long getConeCone() {
    return coneCone;
  }

  public void setConeCone(Long coneCone) {
    this.coneCone = coneCone;
  }

  public UnidadDependencia getConeUdeUdepe() {
    return coneUdeUdepe;
  }

  public void setConeUdeUdepe(UnidadDependencia coneUdeUdepe) {
    this.coneUdeUdepe = coneUdeUdepe;
  }

  public Dominio getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Dominio tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getConeNroComu() {
    return coneNroComu;
  }

  public void setConeNroComu(String coneNroComu) {
    this.coneNroComu = coneNroComu;
  }

  public int getConeNroConvocados() {
    return coneNroConvocados;
  }

  public void setConeNroConvocados(int coneNroConvocados) {
    this.coneNroConvocados = coneNroConvocados;
  }

  public Integer getConeNroPresentados() {
    return coneNroPresentados;
  }

  public void setConeNroPresentados(Integer coneNroPresentados) {
    this.coneNroPresentados = coneNroPresentados;
  }

  public String getConeUsuCrea() {
    return coneUsuCrea;
  }

  public void setConeUsuCrea(String coneUsuCrea) {
    this.coneUsuCrea = coneUsuCrea;
  }

  public Date getConeFechaCrea() {
    return coneFechaCrea;
  }

  public void setConeFechaCrea(Date coneFechaCrea) {
    this.coneFechaCrea = coneFechaCrea;
  }

  public String getConeMaquinaCrea() {
    return coneMaquinaCrea;
  }

  public void setConeMaquinaCrea(String coneMaquinaCrea) {
    this.coneMaquinaCrea = coneMaquinaCrea;
  }

  public String getConeIpCrea() {
    return coneIpCrea;
  }

  public void setConeIpCrea(String coneIpCrea) {
    this.coneIpCrea = coneIpCrea;
  }

  public String getConeUsuMod() {
    return coneUsuMod;
  }

  public void setConeUsuMod(String coneUsuMod) {
    this.coneUsuMod = coneUsuMod;
  }

  public Date getConeFechaMod() {
    return coneFechaMod;
  }

  public void setConeFechaMod(Date coneFechaMod) {
    this.coneFechaMod = coneFechaMod;
  }

  public String getConeMaquinaMod() {
    return coneMaquinaMod;
  }

  public void setConeMaquinaMod(String coneMaquinaMod) {
    this.coneMaquinaMod = coneMaquinaMod;
  }

  public String getConeIpMod() {
    return coneIpMod;
  }

  public void setConeIpMod(String coneIpMod) {
    this.coneIpMod = coneIpMod;
  }

  public SieduEventoEscuela getConeEvee() {
    return coneEvee;
  }

  public void setConeEvee(SieduEventoEscuela coneEvee) {
    this.coneEvee = coneEvee;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (coneCone != null ? coneCone.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduConvocatoriaEvento)) {
      return false;
    }
    SieduConvocatoriaEvento other = (SieduConvocatoriaEvento) object;
    if ((this.coneCone == null && other.coneCone != null) || (this.coneCone != null && !this.coneCone.equals(other.coneCone))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduConvocatoriaEvento[ coneCone=" + coneCone + " ]";
  }
  
}
