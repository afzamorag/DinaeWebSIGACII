package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author RafaelGomez
 */
@Entity
@Table(name = "EVALUACION_PROYECTO_GRADO")
@NamedQueries({
  @NamedQuery(name = "EvaluacionProyectoGrado.findAll", query = "SELECT e FROM EvaluacionProyectoGrado e"),
  @NamedQuery(name = "EvaluacionProyectoGrado.findEvaluacionProyectoByIdProyecto", query = "SELECT e FROM EvaluacionProyectoGrado e WHERE e.proyecto.idProyecto = :idProyecto")
})
public class EvaluacionProyectoGrado implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EvaluacionProyectoGrado_seq_gen")
  @SequenceGenerator(name = "EvaluacionProyectoGrado_seq_gen", sequenceName = "SEC_EVALUACION_PROYECTO_GRADO", allocationSize = 1)
  @Column(name = "ID_EVALUACION_PROY_GRADO")
  private Long idEvaluacionProyGrado;

  @Column(name = "FECHA_SUSTENTACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaSustentacion;

  @Column(name = "NOTA_TRABAJO")
  private BigDecimal notaTrabajo;

  @Column(name = "NOTA_SUSTENTACION")
  private BigDecimal notaSustentacion;

  @Column(name = "NOTA_FINAL")
  private BigDecimal notaFinal;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto proyecto;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "evaluacionProyectoGrado")
  private List<EvaluadoresProyectoGrado> evaluadoresProyectoGradoList;

  public EvaluacionProyectoGrado() {
  }

  public EvaluacionProyectoGrado(Long idEvaluacionProyGrado) {
    this.idEvaluacionProyGrado = idEvaluacionProyGrado;
  }

  public EvaluacionProyectoGrado(Long idEvaluacionProyGrado, BigDecimal notaSustentacion, Date fechaRegistro, String maquina) {
    this.idEvaluacionProyGrado = idEvaluacionProyGrado;
    this.notaSustentacion = notaSustentacion;
    this.fechaRegistro = fechaRegistro;
    this.maquina = maquina;
  }

  public Long getIdEvaluacionProyGrado() {
    return idEvaluacionProyGrado;
  }

  public void setIdEvaluacionProyGrado(Long idEvaluacionProyGrado) {
    this.idEvaluacionProyGrado = idEvaluacionProyGrado;
  }

  public Date getFechaSustentacion() {
    return fechaSustentacion;
  }

  public void setFechaSustentacion(Date fechaSustentacion) {
    this.fechaSustentacion = fechaSustentacion;
  }

  public BigDecimal getNotaTrabajo() {
    return notaTrabajo;
  }

  public void setNotaTrabajo(BigDecimal notaTrabajo) {
    this.notaTrabajo = notaTrabajo;
  }

  public BigDecimal getNotaSustentacion() {
    return notaSustentacion;
  }

  public void setNotaSustentacion(BigDecimal notaSustentacion) {
    this.notaSustentacion = notaSustentacion;
  }

  public BigDecimal getNotaFinal() {
    return notaFinal;
  }

  public void setNotaFinal(BigDecimal notaFinal) {
    this.notaFinal = notaFinal;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEvaluacionProyGrado != null ? idEvaluacionProyGrado.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EvaluacionProyectoGrado)) {
      return false;
    }
    EvaluacionProyectoGrado other = (EvaluacionProyectoGrado) object;
    if ((this.idEvaluacionProyGrado == null && other.idEvaluacionProyGrado != null) || (this.idEvaluacionProyGrado != null && !this.idEvaluacionProyGrado.equals(other.idEvaluacionProyGrado))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EvaluacionProyectoGrado[ idEvaluacionProyGrado=" + idEvaluacionProyGrado + " ]";
  }

  public List<EvaluadoresProyectoGrado> getEvaluadoresProyectoGradoList() {
    return evaluadoresProyectoGradoList;
  }

  public void setEvaluadoresProyectoGradoList(List<EvaluadoresProyectoGrado> evaluadoresProyectoGradoList) {
    this.evaluadoresProyectoGradoList = evaluadoresProyectoGradoList;
  }

}
