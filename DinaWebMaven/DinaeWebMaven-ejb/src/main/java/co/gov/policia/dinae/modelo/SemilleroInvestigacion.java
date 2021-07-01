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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SEMILLERO_INVESTIGACION")
@NamedQueries({
  @NamedQuery(name = "SemilleroInvestigacion.findAll", query = "SELECT s FROM SemilleroInvestigacion s"),
  @NamedQuery(name = "SemilleroInvestigacion.findAllDTO", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroInvestigacionDTO( s.idSemillero, s.nombre, s.unidadPolicial.idUnidadPolicial, s.unidadPolicial.nombre, s.fechaRegistro, s.fechaInicio, s.fechaFin, s.trabajoIndependiente, s.temaTituloTrabajo ) FROM SemilleroInvestigacion s ORDER BY s.fechaRegistro ASC"),
  @NamedQuery(name = "SemilleroInvestigacion.findAllPorUnidadPolicial", query = "SELECT s FROM SemilleroInvestigacion s WHERE s.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "SemilleroInvestigacionDTO.findAllPorUnidadPolicial", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroInvestigacionDTO( s.idSemillero, s.nombre, s.unidadPolicial.idUnidadPolicial, s.unidadPolicial.nombre, s.fechaRegistro, s.fechaInicio, s.fechaFin, s.trabajoIndependiente, s.temaTituloTrabajo ) FROM SemilleroInvestigacion s WHERE s.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "SemilleroInvestigacionDTO.findByIdSemillero", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroInvestigacionDTO( s.idSemillero, s.nombre, s.unidadPolicial.idUnidadPolicial, s.unidadPolicial.nombre, s.fechaRegistro, s.fechaInicio, s.fechaFin, s.trabajoIndependiente, s.temaTituloTrabajo ) FROM SemilleroInvestigacion s WHERE s.idSemillero = :idSemillero")
})
public class SemilleroInvestigacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SemilleroInvestigacion_seq_gen")
  @SequenceGenerator(name = "SemilleroInvestigacion_seq_gen", sequenceName = "SEC_SEMILLERO_INVESTIGACION", allocationSize = 1)
  @Column(name = "ID_SEMILLERO")
  private Long idSemillero;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "MISION_SEMILLERO")
  private String misionSemillero;

  @Column(name = "VISION_SEMILLERO")
  private String visionSemillero;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_ACTUALIZACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_USUARIO_ROL_ACTUALIZA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolActualiza;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "semilleroInvestigacion")
  private List<SemilleroProyecto> semilleroProyectoList;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @Column(name = "OBJETIVO_GENERAL")
  private String objetivoGeneral;

  @Column(name = "OBJETIVO_ESPECIFICO")
  private String objetivoEspecifico;

  @Column(name = "METODOLOGIA_TRABAJO")
  private String metodologiaTrabajo;

  @Column(name = "PRODUCTO_O_RESULTADO")
  private String productoOresultado;

  @Column(name = "NOMBRE_RED_NODO_PERTENECE")
  private String nombreRedNodoPertenece;

  @Column(name = "PERTENECE_RED_SEMILL")
  private Character perteneceRedSemillero;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaFin;

  @Column(name = "JEFEUNIDAD_GRADO")
  private String jefeUnidadGrado;

  @Column(name = "JEFEUNIDAD_NOMBRES")
  private String jefeUnidadNombres;

  @Column(name = "JEFEUNIDAD_APELLIDOS")
  private String jefeUnidadApellidos;

  @Column(name = "JEFEUNIDAD_CORREO")
  private String jefeUnidadCorreo;

  @Column(name = "JEFEUNIDAD_TELEFONO")
  private String jefeUnidadTelefono;

  @Column(name = "JEFEUNIDAD_IDENTIFICACION")
  private String jefeUnidadIdentificacion;

  @JoinColumn(name = "JEFEUNIDAD_ID_UNIDAD", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial jefeUnidadUnidadPolicial;

  @Column(name = "JEFEUNIDAD_CARGO")
  private String jefeUnidadCargo;

  @Column(name = "JEFEUNIDAD_NOMBRE_COMPLETO")
  private String jefeUnidadNombreCompleto;

  @Column(name = "MAQUINA")
  private String maquina;

  @Column(name = "MAQUINA_ACTUALIZA")
  private String maquinaActualiza;

  @Column(name = "TEMA_TITULO_TRABAJO")
  private String temaTituloTrabajo;

  @Column(name = "TRABAJO_INDEPENDIENTE")
  private String trabajoIndependiente;

  @Column(name = "USUARIO_ACTUALIZA")
  private String usuarioActualiza;

  @Column(name = "USUARIO_REGISTRA")
  private String usuarioRegistra;

  public SemilleroInvestigacion() {
  }

  public SemilleroInvestigacion(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public SemilleroInvestigacion(Long idSemillero, String nombre, String misionSemillero) {
    this.idSemillero = idSemillero;
    this.nombre = nombre;
    this.misionSemillero = misionSemillero;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getMisionSemillero() {
    return misionSemillero;
  }

  public void setMisionSemillero(String misionSemillero) {
    this.misionSemillero = misionSemillero;
  }

  public String getVisionSemillero() {
    return visionSemillero;
  }

  public void setVisionSemillero(String visionSemillero) {
    this.visionSemillero = visionSemillero;
  }

  public List<SemilleroProyecto> getSemilleroProyectoList() {
    return semilleroProyectoList;
  }

  public void setSemilleroProyectoList(List<SemilleroProyecto> semilleroProyectoList) {
    this.semilleroProyectoList = semilleroProyectoList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idSemillero != null ? idSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SemilleroInvestigacion)) {
      return false;
    }
    SemilleroInvestigacion other = (SemilleroInvestigacion) object;
    if ((this.idSemillero == null && other.idSemillero != null) || (this.idSemillero != null && !this.idSemillero.equals(other.idSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.SemilleroInvestigacion[ idSemillero=" + idSemillero + " ]";
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getObjetivoGeneral() {
    return objetivoGeneral;
  }

  public void setObjetivoGeneral(String objetivoGeneral) {
    this.objetivoGeneral = objetivoGeneral;
  }

  public String getObjetivoEspecifico() {
    return objetivoEspecifico;
  }

  public void setObjetivoEspecifico(String objetivoEspecifico) {
    this.objetivoEspecifico = objetivoEspecifico;
  }

  public String getMetodologiaTrabajo() {
    return metodologiaTrabajo;
  }

  public void setMetodologiaTrabajo(String metodologiaTrabajo) {
    this.metodologiaTrabajo = metodologiaTrabajo;
  }

  public String getProductoOresultado() {
    return productoOresultado;
  }

  public void setProductoOresultado(String productoOresultado) {
    this.productoOresultado = productoOresultado;
  }

  public String getNombreRedNodoPertenece() {
    return nombreRedNodoPertenece;
  }

  public void setNombreRedNodoPertenece(String nombreRedNodoPertenece) {
    this.nombreRedNodoPertenece = nombreRedNodoPertenece;
  }

  public Character getPerteneceRedSemillero() {
    return perteneceRedSemillero;
  }

  public void setPerteneceRedSemillero(Character perteneceRedSemillero) {
    this.perteneceRedSemillero = perteneceRedSemillero;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getJefeUnidadGrado() {
    return jefeUnidadGrado;
  }

  public void setJefeUnidadGrado(String jefeUnidadGrado) {
    this.jefeUnidadGrado = jefeUnidadGrado;
  }

  public String getJefeUnidadNombres() {
    return jefeUnidadNombres;
  }

  public void setJefeUnidadNombres(String jefeUnidadNombres) {
    this.jefeUnidadNombres = jefeUnidadNombres;
  }

  public String getJefeUnidadApellidos() {
    return jefeUnidadApellidos;
  }

  public void setJefeUnidadApellidos(String jefeUnidadApellidos) {
    this.jefeUnidadApellidos = jefeUnidadApellidos;
  }

  public String getJefeUnidadCorreo() {
    return jefeUnidadCorreo;
  }

  public void setJefeUnidadCorreo(String jefeUnidadCorreo) {
    this.jefeUnidadCorreo = jefeUnidadCorreo;
  }

  public String getJefeUnidadTelefono() {
    return jefeUnidadTelefono;
  }

  public void setJefeUnidadTelefono(String jefeUnidadTelefono) {
    this.jefeUnidadTelefono = jefeUnidadTelefono;
  }

  public UnidadPolicial getJefeUnidadUnidadPolicial() {
    return jefeUnidadUnidadPolicial;
  }

  public void setJefeUnidadUnidadPolicial(UnidadPolicial jefeUnidadUnidadPolicial) {
    this.jefeUnidadUnidadPolicial = jefeUnidadUnidadPolicial;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getJefeUnidadIdentificacion() {
    return jefeUnidadIdentificacion;
  }

  public void setJefeUnidadIdentificacion(String jefeUnidadIdentificacion) {
    this.jefeUnidadIdentificacion = jefeUnidadIdentificacion;
  }

  public String getJefeUnidadCargo() {
    return jefeUnidadCargo;
  }

  public void setJefeUnidadCargo(String jefeUnidadCargo) {
    this.jefeUnidadCargo = jefeUnidadCargo;
  }

  public String getJefeUnidadNombreCompleto() {
    return jefeUnidadNombreCompleto;
  }

  public void setJefeUnidadNombreCompleto(String jefeUnidadNombreCompleto) {
    this.jefeUnidadNombreCompleto = jefeUnidadNombreCompleto;
  }

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
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

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  public String getMaquinaActualiza() {
    return maquinaActualiza;
  }

  public void setMaquinaActualiza(String maquinaActualiza) {
    this.maquinaActualiza = maquinaActualiza;
  }

  public String getTemaTituloTrabajo() {
    return temaTituloTrabajo;
  }

  public void setTemaTituloTrabajo(String temaTituloTrabajo) {
    this.temaTituloTrabajo = temaTituloTrabajo;
  }

  public String getTrabajoIndependiente() {
    return trabajoIndependiente;
  }

  public void setTrabajoIndependiente(String trabajoIndependiente) {
    this.trabajoIndependiente = trabajoIndependiente;
  }

  public String getUsuarioActualiza() {
    return usuarioActualiza;
  }

  public void setUsuarioActualiza(String usuarioActualiza) {
    this.usuarioActualiza = usuarioActualiza;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

}
