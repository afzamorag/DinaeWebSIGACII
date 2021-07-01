package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author RafaelGomez
 */
@Entity
@Table(name = "EVALUADORES_PROYECTO_GRADO")
@NamedQueries({
  @NamedQuery(name = "EvaluadoresProyectoGrado.findAll", query = "SELECT e FROM EvaluadoresProyectoGrado e"),
  @NamedQuery(name = "EvaluadoresProyectoDTO.findAllByIdProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.EvaluadoresProyectoGradoDTO(e.idEvaluadorProy, e.identificacion, e.fechaRegistro, e.grado, e.correo, e.cargo, e.telefono) FROM EvaluadoresProyectoGrado e, EvaluacionProyectoGrado ev WHERE (e.evaluacionProyectoGrado.idEvaluacionProyGrado = ev.idEvaluacionProyGrado AND ev.proyecto.idProyecto = :idProyecto)"),
  @NamedQuery(name = "EvaluadoresProyecto.findAllByIdProyecto", query = "SELECT e FROM EvaluadoresProyectoGrado e, EvaluacionProyectoGrado ev WHERE (e.evaluacionProyectoGrado.idEvaluacionProyGrado = ev.idEvaluacionProyGrado AND ev.proyecto.idProyecto = :idProyecto)")
})
public class EvaluadoresProyectoGrado implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_EVALUADOR_PROY")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EvaluadoresProyectoGrado_seq_gen")
  @SequenceGenerator(name = "EvaluadoresProyectoGrado_seq_gen", sequenceName = "SEC_EVALUADORES_PROYECTO_GRADO", allocationSize = 1)
  private Long idEvaluadorProy;

  @NotNull
  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @NotNull
  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @NotNull
  @Column(name = "ID_USUARIO_ROL")
  private Long idUsuarioRol;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "CARGO")
  private String cargo;

  @Size(max = 100)
  @Column(name = "TELEFONO")
  private String telefono;

  @JoinColumn(name = "ID_EVALUACION_PROY_GRADO", referencedColumnName = "ID_EVALUACION_PROY_GRADO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private EvaluacionProyectoGrado evaluacionProyectoGrado;

  @Transient
  private String nombreCompleto;

  public EvaluadoresProyectoGrado() {
  }

  public EvaluadoresProyectoGrado(Long idEvaluadorProy) {
    this.idEvaluadorProy = idEvaluadorProy;
  }

  public EvaluadoresProyectoGrado(Long idEvaluadorProy, String identificacion, Date fechaRegistro, Long idUsuarioRol, String telefono) {
    this.idEvaluadorProy = idEvaluadorProy;
    this.identificacion = identificacion;
    this.fechaRegistro = fechaRegistro;
    this.idUsuarioRol = idUsuarioRol;
    this.telefono = telefono;
  }

  public Long getIdEvaluadorProy() {
    return idEvaluadorProy;
  }

  public void setIdEvaluadorProy(Long idEvaluadorProy) {
    this.idEvaluadorProy = idEvaluadorProy;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public EvaluacionProyectoGrado getEvaluacionProyectoGrado() {
    return evaluacionProyectoGrado;
  }

  public void setEvaluacionProyectoGrado(EvaluacionProyectoGrado evaluacionProyectoGrado) {
    this.evaluacionProyectoGrado = evaluacionProyectoGrado;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEvaluadorProy != null ? idEvaluadorProy.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EvaluadoresProyectoGrado)) {
      return false;
    }
    EvaluadoresProyectoGrado other = (EvaluadoresProyectoGrado) object;
    if ((this.idEvaluadorProy == null && other.idEvaluadorProy != null) || (this.idEvaluadorProy != null && !this.idEvaluadorProy.equals(other.idEvaluadorProy))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EvaluadoresProyectoGrado[ idEvaluadorProy=" + idEvaluadorProy + " ]";
  }

  /**
   * @return the nombreCompleto
   */
  public String getNombreCompleto() {
    return nombreCompleto;
  }

  /**
   * @param nombreCompleto the nombreCompleto to set
   */
  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

}
