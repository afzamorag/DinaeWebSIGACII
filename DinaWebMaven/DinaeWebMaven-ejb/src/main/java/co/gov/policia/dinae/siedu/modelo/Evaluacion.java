/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
import javax.persistence.Transient;

/**
 *
 * @author Juan Jose Buzon
 */
@Entity
@Table(name = "SIEDU_EVALUACION")
@NamedQueries({
  @NamedQuery(name = Evaluacion.FIND_BY_STATUS, query = "SELECT e FROM Evaluacion e WHERE e.estadoEvaluacion = :estado"),
  @NamedQuery(name = Evaluacion.FIND_ALL_LIGHT_LOAD, query = "SELECT NEW co.gov.policia.dinae.siedu.modelo.Evaluacion(e.id, e.evalDescri, e.estadoEvaluacion) FROM Evaluacion e"),
  @NamedQuery(name = Evaluacion.FIND_BY_STATUS_LIGHT_LOAD, query = "SELECT e FROM Evaluacion e WHERE e.estadoEvaluacion = :estado")
//  @NamedQuery(name = Evaluacion.FIND_FOR_DEVELOP_BY_EVENT, query = "SELECT e FROM Evaluacion e JOIN FETCH e.preguntasEvaluacion WHERE e.aplicada = 'N' AND e.estadoEvaluacion = :estado AND e.evento.id = :idEvent")
//  @NamedQuery(name = Evaluacion.FIND_FOR_DEVELOP_BY_EVENT, query = "SELECT DISTINCT e FROM Evaluacion e JOIN FETCH e.preguntasEvaluacion pe WHERE e.aplicada = 'N' AND e.estadoEvaluacion = :estado AND e.evento.id = :idEvent")
})
@NamedNativeQueries({
	@NamedNativeQuery(name=Evaluacion.FIND_FOR_DEVELOP_BY_EVENT, 
			query="SELECT eva.EVAL_EVAL, eva.EVAL_DESCRI, epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN, epr.EVPRE_VLR_PORC, peva.PEVAL_PEVAL, "
					+ "peva.PEVAL_DESCRI, pevp.PEVAL_PEVAL, pevp.PEVAL_DESCRI "
					+ "FROM SIEDU_EVALUACION eva "
						+ "JOIN SIEDU_EVAL_PREGUNTA epr ON epr.EVPRE_EVAL = eva.EVAL_EVAL "
			//			+ "JOIN SIEDU_EVENTO eve ON eve.EVEN_EVEN = eva.EVAL_EVEN "
						+ "JOIN SIEDU_PARAMETRO_EVALUACION peva ON peva.PEVAL_PEVAL = epr.EVPRE_PEVAL_ASPEC "
						+ "JOIN SIEDU_PARAMETRO_EVALUACION pevp ON pevp.PEVAL_PEVAL = epr.EVPRE_PEVAL_PREG "
					+ "WHERE eva.EVAL_APLICADA = 'N' "
					+ "AND eva.EVAL_ESTADO = ? "
					+ "AND eve.EVAL_EVEN = ? ORDER BY epr.EVPRE_PEVAL_PREG, epr.EVPRE_ORDEN")
})
public class Evaluacion implements Serializable {

  private static final long serialVersionUID = -2457739836436680629L;

  public static final String FIND_BY_STATUS = "Evaluacion.findByStatus";
  public static final String FIND_ALL_LIGHT_LOAD = "Evaluacion.findAllLightLoad";
  public static final String FIND_BY_STATUS_LIGHT_LOAD = "Evaluacion.findByStatusLightLoad";
  public static final String FIND_BY_FILTER = "Evaluacion.findByFilter";
  public static final String FIND_FOR_DEVELOP_BY_EVENT = "Evaluacion.findForDevelopByEvent";
  public static final String FIND_BY_ID_LOAD_QUESTIONS = "Evaluacion.findByIdLoadQuestions";

  @Id
  @Column(name = "EVAL_EVAL")
  @SequenceGenerator(name = "SIEDU_EVALUACION_SEQ_GEN", sequenceName = "SIEDU_EVALUACION_SEQ", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_EVALUACION_SEQ_GEN")
  private Long id;

  @Column(name = "EVAL_DESCRI")
  private String evalDescri;

  @Column(name = "EVAL_NRO_PREG")
  private Long evalNroPreg;

  @Column(name = "EVAL_FECHA")
  @Temporal(TemporalType.DATE)
  private Date evalFecha;

  @Column(name = "EVAL_ESTADO")
  @Enumerated(EnumType.STRING)
  private EstadoEvaluacionEnum estadoEvaluacion;

  @Column(name = "EVAL_USU_CREA")
  private String evalUsuCrea;

  @Column(name = "EVAL_FECHA_CREA")
  @Temporal(TemporalType.DATE)
  private Date evalFechaCrea;

  @Column(name = "EVAL_MAQUINA_CREA")
  private String evalMaquinaCrea;

  @Column(name = "EVAL_IP_CREA")
  private String evalIpCrea;

  @Column(name = "EVAL_USU_MOD")
  private String evalUsuMod;

  @Column(name = "EVAL_FECHA_MOD")
  @Temporal(TemporalType.DATE)
  private Date evalFechaMod;

  @Column(name = "EVAL_MAQUINA_MOD")
  private String evalMaquinaMod;

  @Column(name = "EVAL_IP_MOD")
  private String evalIpMod;

  @Column(name = "EVAL_APLICADA")
  private String aplicada;

  @ManyToOne
  @JoinColumn(name = "EVAL_PAE")
  private PAE pae;

  @ManyToOne
  @JoinColumn(name = "EVAL_EVEN")
  private SieduEvento evento;

  // @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.REMOVE)
  // private List<SieduEvalCategoria> evalCategorias;
  @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.REMOVE)
  private List<SieduEvalGrado> evalGrados;

  @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.REMOVE)
  private List<PreguntaEvaluacion> preguntasEvaluacion;

  @Transient
  private Dominio proceso;

  @Transient
  private Dominio modalidad;

  @Transient
  private List<CategoriaPK> categoriaPks;

  @Transient
  private List<GradoPK> gradoPKs;

  public Evaluacion() {
    // evalCategorias = new ArrayList<>();
    evalGrados = new ArrayList<>();
    preguntasEvaluacion = new ArrayList<>();
  }

  public Evaluacion(Long id, String evalDescri,
          EstadoEvaluacionEnum estadoEvaluacion) {
    this.id = id;
    this.evalDescri = evalDescri;
    this.estadoEvaluacion = estadoEvaluacion;
    // evalCategorias = new ArrayList<>();
    evalGrados = new ArrayList<>();
    preguntasEvaluacion = new ArrayList<>();
  }

  public Evaluacion(Long id, String evalDescri,
          String estadoEvaluacion) {
    this.id = id;
    this.evalDescri = evalDescri;
    this.estadoEvaluacion = EstadoEvaluacionEnum.valueOf(estadoEvaluacion);
    // evalCategorias = new ArrayList<>();
    evalGrados = new ArrayList<>();
    preguntasEvaluacion = new ArrayList<>();
  }

    public Evaluacion(Evaluacion evaluacion) {
        this.id = evaluacion.getId();
        this.evalDescri = evaluacion.getEvalDescri();
        this.evalNroPreg = evaluacion.getEvalNroPreg();
        this.evalFecha = evaluacion.getEvalFecha();
        this.estadoEvaluacion = evaluacion.getEstadoEvaluacion();
        this.evalUsuCrea = evaluacion.getEvalUsuCrea();
        this.evalFechaCrea = evaluacion.getEvalFechaCrea();
        this.evalMaquinaCrea = evaluacion.getEvalMaquinaCrea();
        this.evalIpCrea = evaluacion.getEvalIpCrea();
        this.evalUsuMod = evaluacion.getEvalUsuMod();
        this.evalFechaMod = evaluacion.getEvalFechaMod();
        this.evalMaquinaMod = evaluacion.getEvalMaquinaMod();
        this.evalIpMod = evaluacion.getEvalIpMod();
        this.aplicada = evaluacion.getAplicada();
        this.pae = evaluacion.getPae();
        this.evento = evaluacion.getEvento();
        this.evalGrados = evaluacion.getEvalGrados();
        this.preguntasEvaluacion = evaluacion.getPreguntasEvaluacion();
        this.proceso = evaluacion.getProceso();
        this.modalidad = evaluacion.getModalidad();
        this.categoriaPks = evaluacion.getCategoriaPks();
        this.gradoPKs = evaluacion.getGradoPKs();
    }

  public String getEvalDescri() {
    return evalDescri;
  }

  public void setEvalDescri(String evalDescri) {
    this.evalDescri = evalDescri;
  }

  public Dominio getProceso() {
    return proceso;
  }

  public void setProceso(Dominio evalDomProce) {
    this.proceso = evalDomProce;
  }

  public Dominio getModalidad() {
    return modalidad;
  }

  public void setModalidad(Dominio evalDomModal) {
    this.modalidad = evalDomModal;
  }

  public Long getEvalNroPreg() {
    return evalNroPreg;
  }

  public void setEvalNroPreg(Long evalNroPreg) {
    this.evalNroPreg = evalNroPreg;
  }

  public Date getEvalFecha() {
    return evalFecha;
  }

  public void setEvalFecha(Date evalFecha) {
    this.evalFecha = evalFecha;
  }

  public EstadoEvaluacionEnum getEstadoEvaluacion() {
    return estadoEvaluacion;
  }

  public void setEstadoEvaluacion(EstadoEvaluacionEnum estadoEvaluacion) {
    this.estadoEvaluacion = estadoEvaluacion;
  }

  public String getEvalUsuCrea() {
    return evalUsuCrea;
  }

  public void setEvalUsuCrea(String evalUsuCrea) {
    this.evalUsuCrea = evalUsuCrea;
  }

  public Date getEvalFechaCrea() {
    return evalFechaCrea;
  }

  public void setEvalFechaCrea(Date evalFechaCrea) {
    this.evalFechaCrea = evalFechaCrea;
  }

  public String getEvalMaquinaCrea() {
    return evalMaquinaCrea;
  }

  public void setEvalMaquinaCrea(String evalMaquinaCrea) {
    this.evalMaquinaCrea = evalMaquinaCrea;
  }

  public String getEvalIpCrea() {
    return evalIpCrea;
  }

  public void setEvalIpCrea(String evalIpCrea) {
    this.evalIpCrea = evalIpCrea;
  }

  public String getEvalUsuMod() {
    return evalUsuMod;
  }

  public void setEvalUsuMod(String evalUsuMod) {
    this.evalUsuMod = evalUsuMod;
  }

  public Date getEvalFechaMod() {
    return evalFechaMod;
  }

  public void setEvalFechaMod(Date evalFechaMod) {
    this.evalFechaMod = evalFechaMod;
  }

  public String getEvalMaquinaMod() {
    return evalMaquinaMod;
  }

  public void setEvalMaquinaMod(String evalMaquinaMod) {
    this.evalMaquinaMod = evalMaquinaMod;
  }

  public String getEvalIpMod() {
    return evalIpMod;
  }

  public void setEvalIpMod(String evalIpMod) {
    this.evalIpMod = evalIpMod;
  }

  public PAE getPae() {
    return pae;
  }

  public void setPae(PAE pae) {
    this.pae = pae;
  }

  public SieduEvento getEvento() {
    return evento;
  }

  public void setEvento(SieduEvento evento) {
    this.evento = evento;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  // public List<SieduEvalCategoria> getEvalCategorias() {
  // return evalCategorias;
  // }
  //
  // public void setEvalCategorias(List<SieduEvalCategoria> evalCategorias) {
  // this.evalCategorias = evalCategorias;
  // }
  public List<SieduEvalGrado> getEvalGrados() {
    return evalGrados;
  }

  public void setEvalGrados(List<SieduEvalGrado> evalGrados) {
    this.evalGrados = evalGrados;
  }

  public List<PreguntaEvaluacion> getPreguntasEvaluacion() {
    return preguntasEvaluacion;
  }

  public void setPreguntasEvaluacion(
          List<PreguntaEvaluacion> preguntasEvaluacion) {
    this.preguntasEvaluacion = preguntasEvaluacion;
  }

  public String getAplicada() {
    return aplicada;
  }

  public void setAplicada(String aplicada) {
    this.aplicada = aplicada;
  }

  public List<CategoriaPK> getCategoriaPks() {
    return categoriaPks;
  }

  public void setCategoriaPks(List<CategoriaPK> categoriaPks) {
    this.categoriaPks = categoriaPks;
  }

  public List<GradoPK> getGradoPKs() {
    return gradoPKs;
  }

  public void setGradoPKs(List<GradoPK> grados) {
    this.gradoPKs = grados;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are
    // not set
    if (!(object instanceof Evaluacion)) {
      return false;
    }
    Evaluacion other = (Evaluacion) object;
    if ((this.id == null && other.id != null)
            || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.siedu.modelo.SieduEvaluacion[ id=" + id
            + " ]";
  }

}
