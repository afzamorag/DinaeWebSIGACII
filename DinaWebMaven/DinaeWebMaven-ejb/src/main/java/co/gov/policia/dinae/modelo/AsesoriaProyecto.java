package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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
import javax.persistence.Transient;

/**
 *
 * @author RafaelGomez
 */
@Entity
@Table(name = "ASESORIA_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "AsesoriaProyecto.findAll", query = "SELECT a FROM AsesoriaProyecto a"),
  @NamedQuery(name = "AsesoriaProyecto.findAllByIdProyecto", query = "SELECT a FROM AsesoriaProyecto a WHERE a.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "AsesoriaProyectoDTO.findAllByIdProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.AsesoriaProyectoDTO( a.idAsesoriaProyecto, a.fechaRegistro, a.virtual, a.presencial, a.telefonica, a.otros, a.resultadoAsesoria,a.evaluador ) FROM AsesoriaProyecto a WHERE a.proyecto.idProyecto = :idProyecto")
})
public class AsesoriaProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AsesoriaProyecto_seq_gen")
  @SequenceGenerator(name = "AsesoriaProyecto_seq_gen", sequenceName = "SEC_ASESORIA_PROYECTO", allocationSize = 1)
  @Column(name = "ID_ASESORIA_PROYECTO")
  private Long idAsesoriaProyecto;

  @Column(name = "RESULTADO_ASESORIA")
  private String resultadoAsesoria;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "EVALUADOR")
  private String evaluador;

  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

  @Column(name = "VIRTUAL")
  private Character virtual;

  @Column(name = "PRESENCIAL")
  private Character presencial;

  @Column(name = "TELEFONICA")
  private Character telefonica;

  @Column(name = "OTROS")
  private Character otros;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "asesoriaProyecto")
  private List<ModalidadAsesoriaProyecto> modalidadesAsesoriaProyecto;

  @Transient
  private String nombresYApellidosEvaluador;

  @Transient
  private String gradoEvaluador;

  public AsesoriaProyecto() {
  }

  public AsesoriaProyecto(Long idAsesoriaProyecto) {
    this.idAsesoriaProyecto = idAsesoriaProyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idAsesoriaProyecto != null ? idAsesoriaProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof AsesoriaProyecto)) {
      return false;
    }
    AsesoriaProyecto other = (AsesoriaProyecto) object;
    if ((this.idAsesoriaProyecto == null && other.idAsesoriaProyecto != null) || (this.idAsesoriaProyecto != null && !this.idAsesoriaProyecto.equals(other.idAsesoriaProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.AsesoriaProyecto[ idAsesoriaProyecto=" + idAsesoriaProyecto + " ]";
  }

  public Long getIdAsesoriaProyecto() {
    return idAsesoriaProyecto;
  }

  public void setIdAsesoriaProyecto(Long idAsesoriaProyecto) {
    this.idAsesoriaProyecto = idAsesoriaProyecto;
  }

  public String getResultadoAsesoria() {
    return resultadoAsesoria;
  }

  public void setResultadoAsesoria(String resultadoAsesoria) {
    this.resultadoAsesoria = resultadoAsesoria;
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

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getNombresYApellidosEvaluador() {
    return nombresYApellidosEvaluador;
  }

  public void setNombresYApellidosEvaluador(String nombresYApellidosEvaluador) {
    this.nombresYApellidosEvaluador = nombresYApellidosEvaluador;
  }

  /**
   * @return the modalidadesAsesoriaProyecto
   */
  public List<ModalidadAsesoriaProyecto> getModalidadesAsesoriaProyecto() {
    return modalidadesAsesoriaProyecto;
  }

  /**
   * @param modalidadesAsesoriaProyecto the modalidadesAsesoriaProyecto to set
   */
  public void setModalidadesAsesoriaProyecto(List<ModalidadAsesoriaProyecto> modalidadesAsesoriaProyecto) {
    this.modalidadesAsesoriaProyecto = modalidadesAsesoriaProyecto;
  }

  /**
   * @return the gradoEvaluador
   */
  public String getGradoEvaluador() {
    return gradoEvaluador;
  }

  /**
   * @param gradoEvaluador the gradoEvaluador to set
   */
  public void setGradoEvaluador(String gradoEvaluador) {
    this.gradoEvaluador = gradoEvaluador;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public Character getVirtual() {
    return virtual;
  }

  public void setVirtual(Character virtual) {
    this.virtual = virtual;
  }

  public Character getPresencial() {
    return presencial;
  }

  public void setPresencial(Character presencial) {
    this.presencial = presencial;
  }

  public Character getTelefonica() {
    return telefonica;
  }

  public void setTelefonica(Character telefonica) {
    this.telefonica = telefonica;
  }

  public Character getOtros() {
    return otros;
  }

  public void setOtros(Character otros) {
    this.otros = otros;
  }

  public UsuarioRol getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(UsuarioRol usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public String getEvaluador() {
    return evaluador;
  }

  public void setEvaluador(String evaluador) {
    this.evaluador = evaluador;
  }

}
