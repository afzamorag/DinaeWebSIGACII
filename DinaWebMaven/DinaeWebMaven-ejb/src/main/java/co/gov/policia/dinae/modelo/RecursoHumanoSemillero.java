package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
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
@Table(name = "RECURSO_HUMANO_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "RecursoHumanoSemillero.findAll", query = "SELECT r FROM RecursoHumanoSemillero r"),
  @NamedQuery(name = "RecursoHumanoSemillero.findAllBySemillero", query = "SELECT r FROM RecursoHumanoSemillero r WHERE r.semilleroInvestigacion.idSemillero = :idSemillero"),
  @NamedQuery(name = "RecursoHumanoSemillero.findByIdAndSemilleroId", query = "SELECT r FROM RecursoHumanoSemillero r WHERE r.semilleroInvestigacion.idSemillero = :idSemillero AND r.identificacion = :identificacion"),
  @NamedQuery(name = "RecursoHumanoSemillero.findBySemilleroAndEstadoActivoFecha", query = "SELECT r FROM RecursoHumanoSemillero r WHERE r.semilleroInvestigacion.idSemillero = :idSemillero AND (r.estado = 'A' OR (r.estado = 'I' AND r.fechaInactiva >= :fechaOtorgamiento))")})
public class RecursoHumanoSemillero implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RecursoHumanoSemillero_seq_gen")
  @SequenceGenerator(name = "RecursoHumanoSemillero_seq_gen", sequenceName = "SEC_RECURSO_HUMANO_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_RECURSO_HUMANO_SEMI")
  private Long idRecursoHumanoSemi;

  @Column(name = "IDENTIFICACION", nullable = false, length = 20)
  private String identificacion;

  @Column(name = "CARGO", nullable = true, length = 200)
  private String cargo;

  @Column(name = "TELEFONO", nullable = true, length = 20)
  private String telefono;

  @Column(name = "CORREO_ELECTRONICO", nullable = true, length = 50)
  private String correoElectronico;

  @Column(name = "NOMBRES", nullable = false, length = 100)
  private String nombres;

  @Column(name = "COMPANIA", nullable = false, length = 30)
  private String compania;

  @Column(name = "CURSO_O_PROMOCION", nullable = true, length = 30)
  private String cursoOPromocion;

  @Column(name = "FECHA_INCIO", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaIncio;

  @Column(name = "FECHA_FIN", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFin;

  @Column(name = "ESTADO", nullable = true, length = 1)
  private String estado;

  @Column(name = "FECHA_REGISTRO", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_INACTIVA", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInactiva;

  @Column(name = "USUARIO_REGISTRA", nullable = true, length = 50)
  private String usuarioRegistra;

  @Column(name = "MAQUINA", nullable = true, length = 50)
  private String maquina;

  @Column(name = "FECHA_ACTUALIZACION", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "USUARIO_ACTUALIZA", nullable = true, length = 20)
  private String usuarioActualiza;

  @Column(name = "MAQUINA_ACTUALIZA", nullable = true, length = 50)
  private String maquinaActualiza;

  @JoinColumn(name = "ID_SEMILLERO", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemilleroInvestigacion semilleroInvestigacion;

  @JoinColumn(name = "TIPO_RELACION_SEMILLERO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoRelacionSemillero;

  @JoinColumn(name = "TIPO_VINCULACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoVinculacion;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @Column(name = "GRADO")
  private String grado;

  public RecursoHumanoSemillero() {
  }

  public RecursoHumanoSemillero(Long idRecursoHumanoSemi) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
  }

  public RecursoHumanoSemillero(Long idRecursoHumanoSemi, String identificacion, String nombres, String compania) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
    this.identificacion = identificacion;
    this.nombres = nombres;
    this.compania = compania;
  }

  public Long getIdRecursoHumanoSemi() {
    return idRecursoHumanoSemi;
  }

  public void setIdRecursoHumanoSemi(Long idRecursoHumanoSemi) {
    this.idRecursoHumanoSemi = idRecursoHumanoSemi;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
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

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico) {
    this.correoElectronico = correoElectronico;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getCompania() {
    return compania;
  }

  public void setCompania(String compania) {
    this.compania = compania;
  }

  public String getCursoOPromocion() {
    return cursoOPromocion;
  }

  public void setCursoOPromocion(String cursoOPromocion) {
    this.cursoOPromocion = cursoOPromocion;
  }

  public Date getFechaIncio() {
    return fechaIncio;
  }

  public void setFechaIncio(Date fechaIncio) {
    this.fechaIncio = fechaIncio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Constantes getTipoVinculacion() {
    return tipoVinculacion;
  }

  public void setTipoVinculacion(Constantes tipoVinculacion) {
    this.tipoVinculacion = tipoVinculacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
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

  public String getUsuarioActualiza() {
    return usuarioActualiza;
  }

  public void setUsuarioActualiza(String usuarioActualiza) {
    this.usuarioActualiza = usuarioActualiza;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public Constantes getTipoRelacionSemillero() {
    return tipoRelacionSemillero;
  }

  public void setTipoRelacionSemillero(Constantes tipoRelacionSemillero) {
    this.tipoRelacionSemillero = tipoRelacionSemillero;
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

  public Date getFechaInactiva() {
    return fechaInactiva;
  }

  public void setFechaInactiva(Date fechaInactiva) {
    this.fechaInactiva = fechaInactiva;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idRecursoHumanoSemi != null ? idRecursoHumanoSemi.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof RecursoHumanoSemillero)) {
      return false;
    }
    RecursoHumanoSemillero other = (RecursoHumanoSemillero) object;
    if ((this.idRecursoHumanoSemi == null && other.idRecursoHumanoSemi != null) || (this.idRecursoHumanoSemi != null && !this.idRecursoHumanoSemi.equals(other.idRecursoHumanoSemi))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.RecursoHumanoSemillero[ idRecursoHumanoSemi=" + idRecursoHumanoSemi + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (this.idRecursoHumanoSemi == null) {
      return null;
    }

    return this.idRecursoHumanoSemi.toString();
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

}
