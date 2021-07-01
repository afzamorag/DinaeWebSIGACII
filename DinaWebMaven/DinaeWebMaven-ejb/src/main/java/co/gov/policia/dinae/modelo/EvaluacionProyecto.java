package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "EVALUACION_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EvaluacionProyecto.findAll", query = "SELECT e FROM EvaluacionProyecto e"),
  @NamedQuery(name = "EvaluacionProyecto.SumaValorCriterioPorProyecto", query = "SELECT SUM( e.valorCriterio ) FROM EvaluacionProyecto e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EvaluacionProyecto.findValorCriterioPorProtectoYcriterio", query = "SELECT e.valorCriterio FROM EvaluacionProyecto e WHERE e.proyecto.idProyecto = :idProyecto AND e.criterioEvaluacion.idCriterioEvaluacion = :idCriterio"),
  @NamedQuery(name = "EvaluacionProyecto.CriterioPorProtectoYcriterio", query = "SELECT e FROM EvaluacionProyecto e WHERE e.proyecto.idProyecto = :idProyecto AND e.criterioEvaluacion.idCriterioEvaluacion = :idCriterio"),
  @NamedQuery(name = "EvaluacionProyecto.CriterioPorEnsayoCritico", query = "SELECT e FROM EvaluacionProyecto e WHERE e.ensayoCritico.idEnsayoCritico = :idEnsayoCritico"),
  @NamedQuery(name = "EvaluacionProyecto.getEvaluacionProyecto", query = "SELECT e FROM EvaluacionProyecto e WHERE e.proyecto.idProyecto = :idProyecto")
})
public class EvaluacionProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EvaluacionProyecto_seq_gen")
  @SequenceGenerator(name = "EvaluacionProyecto_seq_gen", sequenceName = "SEC_EVALUACION_PROYECTO", allocationSize = 1)
  @Column(name = "ID_EVALUACION_PROYECTO")
  private Long idEvaluacionProyecto;

  @Column(name = "VALOR_CRITERIO")
  private BigDecimal valorCriterio;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_CRITERIO_EVALUACION", referencedColumnName = "ID_CRITERIO_EVALUACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CriterioEvaluacion criterioEvaluacion;

  @JoinColumn(name = "ID_ENSAYO_CRITICO", referencedColumnName = "ID_ENSAYO_CRITICO")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private EnsayoCritico ensayoCritico;

  @Column(name = "VALOR_CRITERIO_VICIN")
  private BigDecimal valorCriterioVicin;

  @JoinColumn(name = "ID_USUARIO_ROL_EVALUADOR", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "NOTA")
  private BigDecimal nota;

  public EvaluacionProyecto() {
  }

  public EvaluacionProyecto(Long idEvaluacionProyecto) {
    this.idEvaluacionProyecto = idEvaluacionProyecto;
  }

  public Long getIdEvaluacionProyecto() {
    return idEvaluacionProyecto;
  }

  public void setIdEvaluacionProyecto(Long idEvaluacionProyecto) {
    this.idEvaluacionProyecto = idEvaluacionProyecto;
  }

  public BigDecimal getValorCriterio() {
    return valorCriterio;
  }

  public void setValorCriterio(BigDecimal valorCriterio) {
    this.valorCriterio = valorCriterio;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEvaluacionProyecto != null ? idEvaluacionProyecto.hashCode() : 0);
    return hash;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public CriterioEvaluacion getCriterioEvaluacion() {
    return criterioEvaluacion;
  }

  public void setCriterioEvaluacion(CriterioEvaluacion criterioEvaluacion) {
    this.criterioEvaluacion = criterioEvaluacion;
  }

  public EnsayoCritico getEnsayoCritico() {
    return ensayoCritico;
  }

  public void setEnsayoCritico(EnsayoCritico ensayoCritico) {
    this.ensayoCritico = ensayoCritico;
  }

  public BigDecimal getValorCriterioVicin() {
    return valorCriterioVicin;
  }

  public void setValorCriterioVicin(BigDecimal valorCriterioVicin) {
    this.valorCriterioVicin = valorCriterioVicin;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public BigDecimal getNota() {
    return nota;
  }

  public void setNota(BigDecimal nota) {
    this.nota = nota;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EvaluacionProyecto)) {
      return false;
    }
    EvaluacionProyecto other = (EvaluacionProyecto) object;
    if ((this.idEvaluacionProyecto == null && other.idEvaluacionProyecto != null) || (this.idEvaluacionProyecto != null && !this.idEvaluacionProyecto.equals(other.idEvaluacionProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EvaluacionProyecto[ idEvaluacionProyecto=" + idEvaluacionProyecto + " ]";
  }

}
