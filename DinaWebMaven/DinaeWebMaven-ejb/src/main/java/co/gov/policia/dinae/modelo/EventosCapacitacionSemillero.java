package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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
 * @author cguzman
 */
@Entity
@Table(name = "EVENTOS_CAPACITACION_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "EventosCapacitacionSemillero.findAll", query = "SELECT e FROM EventosCapacitacionSemillero e"),
  @NamedQuery(name = "EventosCapacitacionSemillero.findByIdSemillero", query = "SELECT e FROM EventosCapacitacionSemillero e WHERE e.semilleroInvestigacion.idSemillero = :idSemillero")})
public class EventosCapacitacionSemillero implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EventosCapacitacionSemillero_seq_gen")
  @SequenceGenerator(name = "EventosCapacitacionSemillero_seq_gen", sequenceName = "SEC_EVENTOS_CAPACITA_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_EVENT_CAPA_SEMILLERO")
  private Long idEventCapaSemillero;

  @Column(name = "NOMBRE_EVENTO")
  private String nombreEvento;

  @Column(name = "LUGAR_EVENTO")
  private String lugarEvento;

  @Column(name = "FECHA_INICIO_EVENTO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicioEvento;

  @Column(name = "FECHA_FINALIZACION_EVENTO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFinalizacionEvento;

  @Column(name = "CLASE_TEMA_PONENCIA")
  private String claseTemaPonencia;

  @Column(name = "ID_CIUDAD")
  private Long idCiudad;

  @JoinColumn(name = "MODALIDAD_PARTICIPACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes modalidadParticipacion;

  @Column(name = "NUMERO_OFICIALES")
  private Short numeroOficiales;

  @Column(name = "NUMERO_SUBOFICIALES")
  private Short numeroSuboficiales;

  @Column(name = "NUMERO_ESTUDIANTES")
  private Short numeroEstudiantes;

  @Column(name = "NUMERO_NO_UNIFORMADOS")
  private Short numeroNoUniformados;

  @Column(name = "ARCHIVO_EVIDENCIA_DOCUMENTAL")
  private String archivoEvidenciaDocumental;

  @Column(name = "ARCHIVO_EVIDENCIA_FOTOG")
  private String archivoEvidenciaFotog;

  @Column(name = "ARCHIVO_EVIDENCIA_DOC_ORIG")
  private String archivoEvidenciaDocumentalOriginal;

  @Column(name = "ARCHIVO_EVIDENCIA_FOTOG_ORIG")
  private String archivoEvidenciaFotogOriginal;

  @Column(name = "FECHA_CREACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @JoinColumn(name = "ID_SEMILLERO", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemilleroInvestigacion semilleroInvestigacion;

  public EventosCapacitacionSemillero() {
  }

  public EventosCapacitacionSemillero(Long idEventCapaSemillero) {
    this.idEventCapaSemillero = idEventCapaSemillero;
  }

  public Long getIdEventCapaSemillero() {
    return idEventCapaSemillero;
  }

  public void setIdEventCapaSemillero(Long idEventCapaSemillero) {
    this.idEventCapaSemillero = idEventCapaSemillero;
  }

  public String getNombreEvento() {
    return nombreEvento;
  }

  public void setNombreEvento(String nombreEvento) {
    this.nombreEvento = nombreEvento;
  }

  public String getLugarEvento() {
    return lugarEvento;
  }

  public void setLugarEvento(String lugarEvento) {
    this.lugarEvento = lugarEvento;
  }

  public Date getFechaInicioEvento() {
    return fechaInicioEvento;
  }

  public void setFechaInicioEvento(Date fechaInicioEvento) {
    this.fechaInicioEvento = fechaInicioEvento;
  }

  public Date getFechaFinalizacionEvento() {
    return fechaFinalizacionEvento;
  }

  public void setFechaFinalizacionEvento(Date fechaFinalizacionEvento) {
    this.fechaFinalizacionEvento = fechaFinalizacionEvento;
  }

  public Long getIdCiudad() {
    return idCiudad;
  }

  public void setIdCiudad(Long idCiudad) {
    this.idCiudad = idCiudad;
  }

  public Constantes getModalidadParticipacion() {
    return modalidadParticipacion;
  }

  public void setModalidadParticipacion(Constantes modalidadParticipacion) {
    this.modalidadParticipacion = modalidadParticipacion;
  }

  public Short getNumeroOficiales() {
    return numeroOficiales;
  }

  public void setNumeroOficiales(Short numeroOficiales) {
    this.numeroOficiales = numeroOficiales;
  }

  public Short getNumeroSuboficiales() {
    return numeroSuboficiales;
  }

  public void setNumeroSuboficiales(Short numeroSuboficiales) {
    this.numeroSuboficiales = numeroSuboficiales;
  }

  public Short getNumeroEstudiantes() {
    return numeroEstudiantes;
  }

  public void setNumeroEstudiantes(Short numeroEstudiantes) {
    this.numeroEstudiantes = numeroEstudiantes;
  }

  public Short getNumeroNoUniformados() {
    return numeroNoUniformados;
  }

  public void setNumeroNoUniformados(Short numeroNoUniformados) {
    this.numeroNoUniformados = numeroNoUniformados;
  }

  public String getArchivoEvidenciaDocumental() {
    return archivoEvidenciaDocumental;
  }

  public void setArchivoEvidenciaDocumental(String archivoEvidenciaDocumental) {
    this.archivoEvidenciaDocumental = archivoEvidenciaDocumental;
  }

  public String getArchivoEvidenciaFotog() {
    return archivoEvidenciaFotog;
  }

  public void setArchivoEvidenciaFotog(String archivoEvidenciaFotog) {
    this.archivoEvidenciaFotog = archivoEvidenciaFotog;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
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

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public UsuarioRol getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(UsuarioRol usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public String getClaseTemaPonencia() {
    return claseTemaPonencia;
  }

  public void setClaseTemaPonencia(String claseTemaPonencia) {
    this.claseTemaPonencia = claseTemaPonencia;
  }

  public String getArchivoEvidenciaDocumentalOriginal() {
    return archivoEvidenciaDocumentalOriginal;
  }

  public void setArchivoEvidenciaDocumentalOriginal(String archivoEvidenciaDocumentalOriginal) {
    this.archivoEvidenciaDocumentalOriginal = archivoEvidenciaDocumentalOriginal;
  }

  public String getArchivoEvidenciaFotogOriginal() {
    return archivoEvidenciaFotogOriginal;
  }

  public void setArchivoEvidenciaFotogOriginal(String archivoEvidenciaFotogOriginal) {
    this.archivoEvidenciaFotogOriginal = archivoEvidenciaFotogOriginal;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEventCapaSemillero != null ? idEventCapaSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EventosCapacitacionSemillero)) {
      return false;
    }
    EventosCapacitacionSemillero other = (EventosCapacitacionSemillero) object;
    if ((this.idEventCapaSemillero == null && other.idEventCapaSemillero != null) || (this.idEventCapaSemillero != null && !this.idEventCapaSemillero.equals(other.idEventCapaSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EventosCapacitacionSemillero[ idEventCapaSemillero=" + idEventCapaSemillero + " ]";
  }

}
