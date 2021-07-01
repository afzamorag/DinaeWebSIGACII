/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.modelo.Carreras;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
 * description
 *
 * @author: Juan Carlos Cifuentes Murcia <juan.cifuentes@correo.policia.gov.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_EVENTO")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = SieduEvento.FIND_ALL, query = "SELECT s FROM SieduEvento s"),
  @NamedQuery(name = SieduEvento.FIND_EVENTOS_BY_FILTRO, query = "SELECT c FROM SieduEvento c WHERE c.dominioModalidad.id = :idModalidad and c.dominioProceso.id = :idProceso and c.carrera.carrerasPK.idCarrera = :idCarrera and c.estado in ('VIGENTE','PENDIENTE')"),
  @NamedQuery(name = SieduEvento.FIND_BY_NIVEL_ACADEMICO, query = "SELECT c FROM SieduEvento c WHERE c.carrera.carrerasPK.idCarrera = :idCarrera"),
  @NamedQuery(name = SieduEvento.FIND_BY_ID_CARRERA_VIGENTE, query = "SELECT c FROM SieduEvento c WHERE c.carrera.carrerasPK.idCarrera = :idCarrera and c.dominioModalidad.id = :idModalidad and c.dominioProceso.id = :idProceso and c.estado = 'VIGENTE'")
})
@NamedNativeQueries({
  @NamedNativeQuery(
          name = SieduEvento.FIND_FOR_EVALUATION_BY_PARTICIPANT, query = "SELECT c.ID_CARRERA, c.FUERZA, c.DESCRIPCION, e.EVEN_EVEN, e.EVEN_DOM_MODAL, e.EVEN_DOM_PROCE, pe.PARE_PARE "
          + "FROM CARRERAS c "
          + "JOIN SIEDU_EVENTO e ON c.ID_CARRERA = e.EVEN_ID_CARRERA AND c.FUERZA = e.EVEN_FUERZA "
          + "JOIN SIEDU_EVENTO_ESCUELA ee ON e.EVEN_EVEN = ee.EVEE_EVEN "
          + "JOIN SIEDU_PARTICIPANTE_EVENTO pe ON ee.EVEE_EVEE = pe.PARE_EVEE "
          + "JOIN SIEDU_PERSONA p ON pe.PARE_PERS = p.PERS_PERS "
          + "JOIN SIEDU_EVALUACION eva ON e.EVEN_EVEN = eva.EVAL_EVEN "
          + "WHERE p.PERS_NROID = ?1 "
          + "AND EXISTS (SELECT 1 FROM SIEDU_EVAL_PARTICIPANTE ep WHERE eva.EVAL_EVAL = ep.EVPA_EVAL AND pe.PARE_PARE = ep.EVPA_PARE AND ep.EVPA_ESTADO = 'A')")
})
@XmlRootElement
public class SieduEvento implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String FIND_ALL = "SieduEvento.findAll";
  public static final String FIND_EVENTOS_BY_FILTRO = "SieduEvento.findEventosByFiltro";
  public static final String FIND_BY_NIVEL_ACADEMICO = "SieduEvento.findByNivelAcademico";
  public static final String FIND_FOR_EVALUATION_BY_PARTICIPANT = "SieduEvento.findForEvaluationByParticipant";

  public static final String FIND_BY_ID_CARRERA_VIGENTE = "SieduEvento.findByIdCarreraVigente";
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVEN_EVEN", nullable = false)
  @SequenceGenerator(name = "SIEDU_EVENTO_SEQ_GEN", sequenceName = "SIEDU_EVENTO_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_EVENTO_SEQ_GEN")
  private Long id;
  @JoinColumn(name = "EVEN_DOM_MODAL", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio dominioModalidad;
  @JoinColumn(name = "EVEN_DOM_PROCE", referencedColumnName = "ID_DOMINIO")
  @ManyToOne(optional = false)
  private Dominio dominioProceso;
  @JoinColumns({
    @JoinColumn(name = "EVEN_ID_CARRERA", referencedColumnName = "ID_CARRERA"),
    @JoinColumn(name = "EVEN_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false)
  private Carreras carrera;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2000)
  @Column(name = "EVEN_JUSTIFICA", nullable = false, length = 2000)
  private String evenJustifica;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2000)
  @Column(name = "EVEN_OBJE_GRAL", nullable = false, length = 2000)
  private String evenObjeGral;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2000)
  @Column(name = "EVEN_OBJE_ESPE", nullable = false, length = 2000)
  private String evenObjeEspe;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 2000)
  @Column(name = "EVEN_DIRIGIDOA", nullable = false, length = 2000)
  private String evenDirigidoa;
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVEN_INTEN_HORAS", nullable = false)
  private short evenIntenHoras;
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVEN_FECHA_VER", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date evenFechaVer;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "EVEN_ESTADO", nullable = false, length = 30)
  private String estado;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "EVEN_USU_CREA", nullable = false, length = 30)
  private String evenUsuCrea;
  @Basic(optional = false)
  @NotNull
  @Column(name = "EVEN_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date evenFechaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "EVEN_MAQUINA_CREA", nullable = false, length = 100)
  private String evenMaquinaCrea;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "EVEN_IP_CREA", nullable = false, length = 100)
  private String evenIpCrea;
  @Size(max = 30)
  @Column(name = "EVEN_USU_MOD", length = 30)
  private String evenUsuMod;
  @Column(name = "EVEN_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date evenFechaMod;
  @Size(max = 100)
  @Column(name = "EVEN_MAQUINA_MOD", length = 100)
  private String evenMaquinaMod;
  @Size(max = 100)
  @Column(name = "EVEN_IP_MOD", length = 100)
  private String evenIpMod;

  @OneToMany(mappedBy = "evcaEven", cascade = CascadeType.REMOVE)
  private List<SieduEventoCategoria> eventoCategorias;

  @OneToMany(mappedBy = "sieduEvento", cascade = CascadeType.REMOVE)
  private List<SieduCompetenciaEvento> eventoCompetencias;

  @OneToMany(mappedBy = "temaEven", cascade = CascadeType.REMOVE)
  private List<SieduTema> eventoTemas;

  public SieduEvento() {
  }

  public SieduEvento(Long evenEven) {
    this.id = evenEven;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public Dominio getDominioModalidad() {
    return dominioModalidad;
  }

  public void setDominioModalidad(Dominio dominioModalidad) {
    this.dominioModalidad = dominioModalidad;
  }

  public Dominio getDominioProceso() {
    return dominioProceso;
  }

  public void setDominioProceso(Dominio dominioProceso) {
    this.dominioProceso = dominioProceso;
  }

  /**
   * @return the carrera
   */
  public Carreras getCarrera() {
    return carrera;
  }

  /**
   * @param carrera the carrera to set
   */
  public void setCarrera(Carreras carrera) {
    this.carrera = carrera;
  }

  /**
   * @return the evenJustifica
   */
  public String getEvenJustifica() {
    return evenJustifica;
  }

  /**
   * @param evenJustifica the evenJustifica to set
   */
  public void setEvenJustifica(String evenJustifica) {
    this.evenJustifica = evenJustifica;
  }

  /**
   * @return the evenObjeGral
   */
  public String getEvenObjeGral() {
    return evenObjeGral;
  }

  /**
   * @param evenObjeGral the evenObjeGral to set
   */
  public void setEvenObjeGral(String evenObjeGral) {
    this.evenObjeGral = evenObjeGral;
  }

  /**
   * @return the evenObjeEspe
   */
  public String getEvenObjeEspe() {
    return evenObjeEspe;
  }

  /**
   * @param evenObjeEspe the evenObjeEspe to set
   */
  public void setEvenObjeEspe(String evenObjeEspe) {
    this.evenObjeEspe = evenObjeEspe;
  }

  /**
   * @return the evenDirigidoa
   */
  public String getEvenDirigidoa() {
    return evenDirigidoa;
  }

  /**
   * @param evenDirigidoa the evenDirigidoa to set
   */
  public void setEvenDirigidoa(String evenDirigidoa) {
    this.evenDirigidoa = evenDirigidoa;
  }

  /**
   * @return the evenIntenHoras
   */
  public short getEvenIntenHoras() {
    return evenIntenHoras;
  }

  /**
   * @param evenIntenHoras the evenIntenHoras to set
   */
  public void setEvenIntenHoras(short evenIntenHoras) {
    this.evenIntenHoras = evenIntenHoras;
  }

  /**
   * @return the evenFechaVer
   */
  public Date getEvenFechaVer() {
    return evenFechaVer;
  }

  /**
   * @param evenFechaVer the evenFechaVer to set
   */
  public void setEvenFechaVer(Date evenFechaVer) {
    this.evenFechaVer = evenFechaVer;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * @return the evenUsuCrea
   */
  @XmlTransient
  public String getEvenUsuCrea() {
    return evenUsuCrea;
  }

  /**
   * @param evenUsuCrea the evenUsuCrea to set
   */
  public void setEvenUsuCrea(String evenUsuCrea) {
    this.evenUsuCrea = evenUsuCrea;
  }

  /**
   * @return the evenFechaCrea
   */
  @XmlTransient
  public Date getEvenFechaCrea() {
    return evenFechaCrea;
  }

  /**
   * @param evenFechaCrea the evenFechaCrea to set
   */
  public void setEvenFechaCrea(Date evenFechaCrea) {
    this.evenFechaCrea = evenFechaCrea;
  }

  /**
   * @return the evenMaquinaCrea
   */
  @XmlTransient
  public String getEvenMaquinaCrea() {
    return evenMaquinaCrea;
  }

  /**
   * @param evenMaquinaCrea the evenMaquinaCrea to set
   */
  public void setEvenMaquinaCrea(String evenMaquinaCrea) {
    this.evenMaquinaCrea = evenMaquinaCrea;
  }

  /**
   * @return the evenIpCrea
   */
  @XmlTransient
  public String getEvenIpCrea() {
    return evenIpCrea;
  }

  /**
   * @param evenIpCrea the evenIpCrea to set
   */
  public void setEvenIpCrea(String evenIpCrea) {
    this.evenIpCrea = evenIpCrea;
  }

  /**
   * @return the evenUsuMod
   */
  @XmlTransient
  public String getEvenUsuMod() {
    return evenUsuMod;
  }

  /**
   * @param evenUsuMod the evenUsuMod to set
   */
  public void setEvenUsuMod(String evenUsuMod) {
    this.evenUsuMod = evenUsuMod;
  }

  /**
   * @return the evenFechaMod
   */
  @XmlTransient
  public Date getEvenFechaMod() {
    return evenFechaMod;
  }

  /**
   * @param evenFechaMod the evenFechaMod to set
   */
  public void setEvenFechaMod(Date evenFechaMod) {
    this.evenFechaMod = evenFechaMod;
  }

  /**
   * @return the evenMaquinaMod
   */
  @XmlTransient
  public String getEvenMaquinaMod() {
    return evenMaquinaMod;
  }

  /**
   * @param evenMaquinaMod the evenMaquinaMod to set
   */
  public void setEvenMaquinaMod(String evenMaquinaMod) {
    this.evenMaquinaMod = evenMaquinaMod;
  }

  /**
   * @return the evenIpMod
   */
  @XmlTransient
  public String getEvenIpMod() {
    return evenIpMod;
  }

  /**
   * @param evenIpMod the evenIpMod to set
   */
  public void setEvenIpMod(String evenIpMod) {
    this.evenIpMod = evenIpMod;
  }

  @XmlTransient
  public List<SieduEventoCategoria> getEventoCategorias() {
    return eventoCategorias;
  }

  public void setEventoCategorias(List<SieduEventoCategoria> eventoCategorias) {
    this.eventoCategorias = eventoCategorias;
  }

  @XmlTransient
  public List<SieduCompetenciaEvento> getEventoCompetencias() {
    return eventoCompetencias;
  }

  public void setEventoCompetencias(List<SieduCompetenciaEvento> eventoCompetencias) {
    this.eventoCompetencias = eventoCompetencias;
  }

  @XmlTransient
  public List<SieduTema> getEventoTemas() {
    return eventoTemas;
  }

  public void setEventoTemas(List<SieduTema> eventoTemas) {
    this.eventoTemas = eventoTemas;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (getId() != null ? getId().hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SieduEvento)) {
      return false;
    }
    SieduEvento other = (SieduEvento) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduEvento[ evenEven=" + getId() + " ]";
  }

}
