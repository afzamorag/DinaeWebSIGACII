/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.UnidadDependencia;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "SIEDU_PARTICIPANTE_EVENTO")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = SieduParticipanteEvento.FIND_ALL, query = "SELECT s FROM SieduParticipanteEvento s"),
  @NamedQuery(name = SieduParticipanteEvento.FIND_BY_PARE_EVEE, query =  "SELECT s FROM SieduParticipanteEvento s WHERE s.pareEvee =:evento"),
  @NamedQuery(name = SieduParticipanteEvento.FIND_BY_PAREEVEE_PAREPERS, query = "SELECT s FROM SieduParticipanteEvento s WHERE s.pareEvee.eveeEvee =:evento AND s.parePers.persPers =:participante"),
  @NamedQuery(name = SieduParticipanteEvento.FIND_BY_EVEN_PARENROIDEN, query = "SELECT s FROM SieduParticipanteEvento s WHERE s.pareEvee.eveeEvee =:evento AND s.parePers.persNroid =:identificacion"),
  @NamedQuery(name = SieduParticipanteEvento.FIND_CAPACITACION_FUNCIONARIO, query = "SELECT s FROM SieduParticipanteEvento s WHERE s.parePers.persNroid =:identificacion")
})
public class SieduParticipanteEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_BY_PARE_EVEE = "SieduParticipanteEvento.findByPareEvee";
  public static final String FIND_ALL = "SieduParticipanteEvento.findAll";
  public static final String FIND_BY_PAREEVEE_PAREPERS = "SieduParticipanteEvento.findByPareeveeParepers";
  public static final String FIND_BY_EVEN_PARENROIDEN = "SieduParticipanteEvento.findByEvenParennroiden";
  public static final String FIND_CAPACITACION_FUNCIONARIO = "SieduParticipanteEvento.findCapacitacionFuncionario";
  
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "PARE_PARE", nullable = false)
  @SequenceGenerator(name = "SIEDU_PARTICIPANTE_EVENTO_SEQ_GEN", sequenceName = "SIEDU_PARTICIPANTE_EVENTO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_PARTICIPANTE_EVENTO_SEQ_GEN")
  private Long parePare;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PARE_ESTADO", nullable = false, length = 30)
  private String pareEstado;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PARE_EDAD", nullable = false)
  private Integer pareEdad;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1)
  @Column(name = "PARE_CUMPLE_PERFIL", nullable = false, length = 1)
  private String pareCumplePerfil;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PARE_TIEMPO_SERVICIO", nullable = false)
  private Integer pareTiempoServicio;
  
  
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "PARE_UDE_UDEPE", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "PARE_UDE_FUERZA", referencedColumnName = "FUERZA")
  })
  private UnidadDependencia pareUdeUdepe;
  
  
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "PARE_CARGO_FUERZA", referencedColumnName = "FUERZA"),
    @JoinColumn(name = "PARE_CARGO", referencedColumnName = "CARGO")
  })
  private Cargos pareCargo;
  
  
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "PARE_GRADO_FUERZA", referencedColumnName = "FUERZA"),
    @JoinColumn(name = "PARE_GRADO_ALFABETICO", referencedColumnName = "ALFABETICO")
  })
  private Grado pareGradosNumerico; 
  
  
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "PARE_CATEGORIA_FUERZA", referencedColumnName = "FUERZA"),
    @JoinColumn(name = "PARE_CATEGORIA_ID", referencedColumnName = "ID_CATEGORIA")
  })
  private Categoria pareGradosIdCategoria;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PARE_USU_CREA", nullable = false, length = 30)
  private String pareUsuCrea;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "PARE_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date pareFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PARE_MAQUINA_CREA", nullable = false, length = 100)
  private String pareMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PARE_IP_CREA", nullable = false, length = 100)
  private String pareIpCrea;
  @Size(max = 30)
  @Column(name = "PARE_USU_MOD", length = 30)
  private String pareUsuMod;
  @Column(name = "PARE_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date pareFechaMod;
  @Size(max = 100)
  @Column(name = "PARE_MAQUINA_MOD", length = 100)
  private String pareMaquinaMod;
  @Size(max = 100)
  @Column(name = "PARE_IP_MOD", length = 100)
  private String pareIpMod;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "PARE_SIGLA_PAPA", nullable = false, length = 100)
  private String pareSiglaPapa;
  
  @ManyToOne(optional = false)
  @JoinColumns({
    @JoinColumn(name = "PARE_UDE_UDEPE_UNIDAD", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "PARE_UDE_UNIDAD_FUERZA", referencedColumnName = "FUERZA")
  })
  private UnidadDependencia pareUdeUnidad;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "inaePare", fetch = FetchType.LAZY)
  private List<SieduInasisteEvento> sieduInasisteEventoList;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "certPare", fetch = FetchType.LAZY)
  private List<SieduCertificadoEvento> sieduCertificadoEventoList;
  
  
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARE_PERS", referencedColumnName = "PERS_PERS", nullable = false)
  private SieduPersona parePers;
  
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARE_EVEE", referencedColumnName = "EVEE_EVEE", nullable = false)
  private SieduEventoEscuela pareEvee;

  public SieduParticipanteEvento() {
  }

  public SieduParticipanteEvento(Long parePare) {
    this.parePare = parePare;
  }

  public SieduParticipanteEvento(long parePare, String pareEstado, Integer pareEdad, String pareCumplePerfil, Integer pareTiempoServicio, UnidadDependencia pareUdeUdepe, Cargos pareCargo, Grado pareGradosNumerico, Categoria pareGradosIdCategoria, String pareUsuCrea, Date pareFechaCrea, String pareMaquinaCrea, String pareIpCrea) {
    this.parePare = parePare;
    this.pareEstado = pareEstado;
    this.pareEdad = pareEdad;
    this.pareCumplePerfil = pareCumplePerfil;
    this.pareTiempoServicio = pareTiempoServicio;
    this.pareUdeUdepe = pareUdeUdepe;
    this.pareCargo = pareCargo;
    this.pareGradosNumerico = pareGradosNumerico;
    this.pareGradosIdCategoria = pareGradosIdCategoria;
    this.pareUsuCrea = pareUsuCrea;
    this.pareFechaCrea = pareFechaCrea;
    this.pareMaquinaCrea = pareMaquinaCrea;
    this.pareIpCrea = pareIpCrea;
  }

  public SieduParticipanteEvento(Long parePare, String pareEstado, Integer pareEdad, String pareCumplePerfil, Integer pareTiempoServicio, UnidadDependencia pareUdeUdepe, Cargos pareCargo, Grado pareGradosNumerico, Categoria pareGradosIdCategoria, String pareUsuCrea, Date pareFechaCrea, String pareMaquinaCrea, String pareIpCrea, String pareSiglaPapa, UnidadDependencia pareUdeUnidad, SieduPersona parePers, SieduEventoEscuela pareEvee) {
    this.parePare = parePare;
    this.pareEstado = pareEstado;
    this.pareEdad = pareEdad;
    this.pareCumplePerfil = pareCumplePerfil;
    this.pareTiempoServicio = pareTiempoServicio;
    this.pareUdeUdepe = pareUdeUdepe;
    this.pareCargo = pareCargo;
    this.pareGradosNumerico = pareGradosNumerico;
    this.pareGradosIdCategoria = pareGradosIdCategoria;
    this.pareUsuCrea = pareUsuCrea;
    this.pareFechaCrea = pareFechaCrea;
    this.pareMaquinaCrea = pareMaquinaCrea;
    this.pareIpCrea = pareIpCrea;
    this.pareSiglaPapa = pareSiglaPapa;
    this.pareUdeUnidad = pareUdeUnidad;
    this.parePers = parePers;
    this.pareEvee = pareEvee;
  }

  public Long getParePare() {
    return parePare;
  }

  public void setParePare(Long parePare) {
    this.parePare = parePare;
  }

  public String getPareEstado() {
    return pareEstado;
  }

  public void setPareEstado(String pareEstado) {
    this.pareEstado = pareEstado;
  }

  public Integer getPareEdad() {
    return pareEdad;
  }

  public void setPareEdad(Integer pareEdad) {
    this.pareEdad = pareEdad;
  }

  public String getPareCumplePerfil() {
    return pareCumplePerfil;
  }

  public void setPareCumplePerfil(String pareCumplePerfil) {
    this.pareCumplePerfil = pareCumplePerfil;
  }

  public Integer getPareTiempoServicio() {
    return pareTiempoServicio;
  }

  public void setPareTiempoServicio(Integer pareTiempoServicio) {
    this.pareTiempoServicio = pareTiempoServicio;
  }

  public UnidadDependencia getPareUdeUdepe() {
    return pareUdeUdepe;
  }

  public void setPareUdeUdepe(UnidadDependencia pareUdeUdepe) {
    this.pareUdeUdepe = pareUdeUdepe;
  }

  public Cargos getPareCargo() {
    return pareCargo;
  }

  public void setPareCargo(Cargos pareCargo) {
    this.pareCargo = pareCargo;
  }

  public Grado getPareGradosNumerico() {
    return pareGradosNumerico;
  }

  public void setPareGradosNumerico(Grado pareGradosNumerico) {
    this.pareGradosNumerico = pareGradosNumerico;
  }

  public Categoria getPareGradosIdCategoria() {
    return pareGradosIdCategoria;
  }

  public void setPareGradosIdCategoria(Categoria pareGradosIdCategoria) {
    this.pareGradosIdCategoria = pareGradosIdCategoria;
  }

  public String getPareUsuCrea() {
    return pareUsuCrea;
  }

  public void setPareUsuCrea(String pareUsuCrea) {
    this.pareUsuCrea = pareUsuCrea;
  }

  public Date getPareFechaCrea() {
    return pareFechaCrea;
  }

  public void setPareFechaCrea(Date pareFechaCrea) {
    this.pareFechaCrea = pareFechaCrea;
  }

  public String getPareMaquinaCrea() {
    return pareMaquinaCrea;
  }

  public void setPareMaquinaCrea(String pareMaquinaCrea) {
    this.pareMaquinaCrea = pareMaquinaCrea;
  }

  public String getPareIpCrea() {
    return pareIpCrea;
  }

  public void setPareIpCrea(String pareIpCrea) {
    this.pareIpCrea = pareIpCrea;
  }

  public String getPareUsuMod() {
    return pareUsuMod;
  }

  public void setPareUsuMod(String pareUsuMod) {
    this.pareUsuMod = pareUsuMod;
  }

  public Date getPareFechaMod() {
    return pareFechaMod;
  }

  public void setPareFechaMod(Date pareFechaMod) {
    this.pareFechaMod = pareFechaMod;
  }

  public String getPareMaquinaMod() {
    return pareMaquinaMod;
  }

  public void setPareMaquinaMod(String pareMaquinaMod) {
    this.pareMaquinaMod = pareMaquinaMod;
  }

  public String getPareIpMod() {
    return pareIpMod;
  }

  public void setPareIpMod(String pareIpMod) {
    this.pareIpMod = pareIpMod;
  }

  public String getPareSiglaPapa() {
    return pareSiglaPapa;
  }

  public void setPareSiglaPapa(String pareSiglaPapa) {
    this.pareSiglaPapa = pareSiglaPapa;
  }

  public UnidadDependencia getPareUdeUnidad() {
    return pareUdeUnidad;
  }

  public void setPareUdeUnidad(UnidadDependencia pareUdeUnidad) {
    this.pareUdeUnidad = pareUdeUnidad;
  }

  @XmlTransient
  public List<SieduInasisteEvento> getSieduInasisteEventoList() {
    return sieduInasisteEventoList;
  }

  public void setSieduInasisteEventoList(List<SieduInasisteEvento> sieduInasisteEventoList) {
    this.sieduInasisteEventoList = sieduInasisteEventoList;
  }

  @XmlTransient
  public List<SieduCertificadoEvento> getSieduCertificadoEventoList() {
    return sieduCertificadoEventoList;
  }

  public void setSieduCertificadoEventoList(List<SieduCertificadoEvento> sieduCertificadoEventoList) {
    this.sieduCertificadoEventoList = sieduCertificadoEventoList;
  }

  public SieduPersona getParePers() {
    return parePers;
  }

  public void setParePers(SieduPersona parePers) {
    this.parePers = parePers;
  }

  public SieduEventoEscuela getPareEvee() {
    return pareEvee;
  }

  public void setPareEvee(SieduEventoEscuela pareEvee) {
    this.pareEvee = pareEvee;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (parePare != null ? parePare.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduParticipanteEvento)) {
      return false;
    }
    SieduParticipanteEvento other = (SieduParticipanteEvento) object;
    if ((this.parePare == null && other.parePare != null) || (this.parePare != null && !this.parePare.equals(other.parePare))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento[ parePare=" + parePare + " ]";
  }
}
