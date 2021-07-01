/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Table(name = "SIEDU_TEMA")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = SieduTema.FIND_ALL, query = "SELECT s FROM SieduTema s"),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_EVENTO, query = "SELECT s FROM SieduTema s WHERE s.temaEven.id = :idSieduEvento AND s.temaTemaPadre IS NULL ORDER BY s.dominioTematica.id, s.temaDescri"),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_EVENT, query = "SELECT s FROM SieduTema s WHERE s.temaEven = :evento AND s.temaTemaPadre IS NULL "),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_CARRERA, query = "SELECT s FROM SieduTema s JOIN FETCH s.temaEven WHERE s.temaEven.carrera.carrerasPK.idCarrera = :idCarrera AND s.temaEven.dominioProceso.id = :idProceso AND s.temaEven.dominioModalidad.id = :idModalidad AND s.temaEven.estado = 'VIGENTE' AND s.temaTemaPadre is null"),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_EVENTO_TEMA_PAPA, query = "SELECT s FROM SieduTema s WHERE s.temaTemaPadre.id = :idSieduTema"),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_EVENTO_ALL, query = "SELECT s FROM SieduTema s  WHERE s.temaEven.id = :evento"),
  @NamedQuery(name = SieduTema.FIND_TEMAS_BY_TEMA_TEMA_PADRE, query = "SELECT s FROM SieduTema s WHERE s.temaTemaPadre.id = :temaTemaPadre AND NOT EXISTS (SELECT d FROM SieduDocenteEvento d WHERE d.doceTema.id = s.id AND d.doceEvee.eveeEvee = :eveeEvee)")
  
})
@XmlRootElement
public class SieduTema implements Serializable {

  public static final String FIND_ALL = "SieduTema.findAll";
  public static final String FIND_TEMAS_BY_EVENTO = "SieduTema.findTemasByEvento";
  public static final String FIND_TEMAS_BY_EVENTO_TEMA_PAPA = "SieduTema.findTemasByEventoTemaPapa";
  public static final String FIND_TEMAS_BY_CARRERA = "SieduTema.findTemasByCarrera";
  public static final String FIND_TEMAS_BY_EVENTO_ALL = "SieduTema.findTemasByCarreraAll";
  public static final String FIND_TEMAS_BY_TEMA_TEMA_PADRE = "SieduTema.findTemasByTemaTemaPadre";
  public static final String FIND_TEMAS_BY_EVENT = "SieduTema.buscarTemasByEvento";

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "TEMA_TEMA", nullable = false)
  @SequenceGenerator(name = "SIEDU_TEMA_SEQ_GEN", sequenceName = "SIEDU_TEMA_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_TEMA_SEQ_GEN")
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TEMA_ORDEN")
  private short temaOrden;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 500)
  @Column(name = "TEMA_DESCRI", nullable = false, length = 500)
  private String temaDescri;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TEMA_HORAS", nullable = false)
  private short temaHoras;
  @JoinColumn(name = "TEMA_DOM_TEMATICA", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio dominioTematica;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "TEMA_USU_CREA", nullable = false, length = 30)
  private String temaUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TEMA_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date temaFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "TEMA_MAQUINA_CREA", nullable = false, length = 100)
  private String temaMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "TEMA_IP_CREA", nullable = false, length = 100)
  private String temaIpCrea;
  @Size(max = 30)
  @Column(name = "TEMA_USU_MOD", length = 30)
  private String temaUsuMod;
  @Column(name = "TEMA_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date temaFechaMod;
  @Size(max = 100)
  @Column(name = "TEMA_MAQUINA_MOD", length = 100)
  private String temaMaquinaMod;
  @Size(max = 100)
  @Column(name = "TEMA_IP_MOD", length = 100)
  private String temaIpMod;
  @OneToMany(mappedBy = "doceTema", fetch = FetchType.LAZY)
  private List<SieduDocenteEvento> sieduDocenteEventoList;
  @OneToMany(mappedBy = "temaTemaPadre", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<SieduTema> sieduTemaList;
  @JoinColumn(name = "TEMA_TEMA_PADRE", referencedColumnName = "TEMA_TEMA")
  @ManyToOne(fetch = FetchType.LAZY)
  private SieduTema temaTemaPadre;
  @JoinColumn(name = "TEMA_EVEN", referencedColumnName = "EVEN_EVEN", nullable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SieduEvento temaEven;

  public SieduTema() {
  }

  public SieduTema(Long id) {
    this.id = id;
  }

  public SieduTema(Long id, short temaOrden, String temaDescri, short temaHoras, String temaUsuCrea, Date temaFechaCrea, String temaMaquinaCrea, String temaIpCrea) {
    this.id = id;
    this.temaOrden = temaOrden;
    this.temaDescri = temaDescri;
    this.temaHoras = temaHoras;
    this.temaUsuCrea = temaUsuCrea;
    this.temaFechaCrea = temaFechaCrea;
    this.temaMaquinaCrea = temaMaquinaCrea;
    this.temaIpCrea = temaIpCrea;
  }

  public Long getTemaTema() {
    return id;
  }

  public void setTemaTema(Long id) {
    this.id = id;
  }

  public short getTemaOrden() {
    return temaOrden;
  }

  public void setTemaOrden(short temaOrden) {
    this.temaOrden = temaOrden;
  }

  public String getTemaDescri() {
    return temaDescri;
  }

  public void setTemaDescri(String temaDescri) {
    this.temaDescri = temaDescri;
  }

  public short getTemaHoras() {
    return temaHoras;
  }

  public void setTemaHoras(short temaHoras) {
    this.temaHoras = temaHoras;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Dominio getDominioTematica() {
    return dominioTematica;
  }

  public void setDominioTematica(Dominio dominioTematica) {
    this.dominioTematica = dominioTematica;
  }

  @XmlTransient
  public String getTemaUsuCrea() {
    return temaUsuCrea;
  }

  public void setTemaUsuCrea(String temaUsuCrea) {
    this.temaUsuCrea = temaUsuCrea;
  }

  @XmlTransient
  public Date getTemaFechaCrea() {
    return temaFechaCrea;
  }

  public void setTemaFechaCrea(Date temaFechaCrea) {
    this.temaFechaCrea = temaFechaCrea;
  }

  @XmlTransient
  public String getTemaMaquinaCrea() {
    return temaMaquinaCrea;
  }

  public void setTemaMaquinaCrea(String temaMaquinaCrea) {
    this.temaMaquinaCrea = temaMaquinaCrea;
  }

  @XmlTransient
  public String getTemaIpCrea() {
    return temaIpCrea;
  }

  public void setTemaIpCrea(String temaIpCrea) {
    this.temaIpCrea = temaIpCrea;
  }

  @XmlTransient
  public String getTemaUsuMod() {
    return temaUsuMod;
  }

  public void setTemaUsuMod(String temaUsuMod) {
    this.temaUsuMod = temaUsuMod;
  }

  @XmlTransient
  public Date getTemaFechaMod() {
    return temaFechaMod;
  }

  public void setTemaFechaMod(Date temaFechaMod) {
    this.temaFechaMod = temaFechaMod;
  }

  @XmlTransient
  public String getTemaMaquinaMod() {
    return temaMaquinaMod;
  }

  public void setTemaMaquinaMod(String temaMaquinaMod) {
    this.temaMaquinaMod = temaMaquinaMod;
  }

  @XmlTransient
  public String getTemaIpMod() {
    return temaIpMod;
  }

  public void setTemaIpMod(String temaIpMod) {
    this.temaIpMod = temaIpMod;
  }

  @XmlTransient
  public List<SieduDocenteEvento> getSieduDocenteEventoList() {
    return sieduDocenteEventoList;
  }

  public void setSieduDocenteEventoList(List<SieduDocenteEvento> sieduDocenteEventoList) {
    this.sieduDocenteEventoList = sieduDocenteEventoList;
  }

  @XmlTransient
  public List<SieduTema> getSieduTemaList() {
    return sieduTemaList;
  }

  public void setSieduTemaList(List<SieduTema> sieduTemaList) {
    this.sieduTemaList = sieduTemaList;
  }

  public SieduTema getTemaTemaPadre() {
    return temaTemaPadre;
  }

  public void setTemaTemaPadre(SieduTema temaTemaPadre) {
    this.temaTemaPadre = temaTemaPadre;
  }

  public SieduEvento getTemaEven() {
    return temaEven;
  }

  public void setTemaEven(SieduEvento temaEven) {
    this.temaEven = temaEven;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SieduTema other = (SieduTema) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SieduTema{" + "id=" + id + '}';
  }
}