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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author cguzman
 */
@Entity
@Table(name = "ENSAYO_CRITICO")
@NamedQueries({
  @NamedQuery(name = "EnsayoCritico.findAll", query = "SELECT e FROM EnsayoCritico e"),
  @NamedQuery(name = "EnsayoCritico.findByIdEnsayoCritico", query = "SELECT e FROM EnsayoCritico e WHERE e.idEnsayoCritico = :idEnsayoCritico"),
  @NamedQuery(name = "EnsayoCritico.findByTituloEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.tituloEnsayo = :tituloEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByResumenEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.resumenEnsayo = :resumenEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByPalabrasClaveEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.palabrasClaveEnsayo = :palabrasClaveEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByAbstractEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.abstractEnsayo = :abstractEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByKeywordsEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.keywordsEnsayo = :keywordsEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByIntroduccionEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.introduccionEnsayo = :introduccionEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByNombreArchivo", query = "SELECT e FROM EnsayoCritico e WHERE e.nombreArchivo = :nombreArchivo"),
  @NamedQuery(name = "EnsayoCritico.findByNombreArchivoOriginal", query = "SELECT e FROM EnsayoCritico e WHERE e.nombreArchivoOriginal = :nombreArchivoOriginal"),
  @NamedQuery(name = "EnsayoCritico.findByConclusionesEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.conclusionesEnsayo = :conclusionesEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByPropuestaEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.propuestaEnsayo = :propuestaEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByBibliografiaEnsayo", query = "SELECT e FROM EnsayoCritico e WHERE e.bibliografiaEnsayo = :bibliografiaEnsayo"),
  @NamedQuery(name = "EnsayoCritico.findByFechaCreacion", query = "SELECT e FROM EnsayoCritico e WHERE e.fechaCreacion = :fechaCreacion"),
  @NamedQuery(name = "EnsayoCritico.findByFechaModificacion", query = "SELECT e FROM EnsayoCritico e WHERE e.fechaModificacion = :fechaModificacion"),
  @NamedQuery(name = "EnsayoCritico.findByNombreMaquina", query = "SELECT e FROM EnsayoCritico e WHERE e.nombreMaquina = :nombreMaquina"),
  @NamedQuery(name = "EnsayoCritico.findByPeriodo", query = "SELECT e FROM EnsayoCritico e WHERE e.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "EnsayoCritico.findByPeriodoUsuario", query = "SELECT e FROM EnsayoCritico e WHERE e.periodo.idPeriodo = :idPeriodo AND e.identificacion = :identificadorUsuario"),
  @NamedQuery(name = "EnsayoCritico.findByPeriodoUnidadCobertura", query = "SELECT e FROM EnsayoCritico e, UsuarioUnidadPolicial u WHERE e.identificacion = u.identificadorUsuario AND u.unidadPolicial.siglaDepende = :siglaPapa AND e.estadoCuCo8.idConstantes IN :idEstados AND e.periodo.idPeriodo = :idPeriodo ORDER BY u.unidadPolicial.nombre, e.tituloEnsayo "),
  @NamedQuery(name = "EnsayoCritico.findByPeriodoAllUnidades", query = "SELECT e FROM EnsayoCritico e, UsuarioUnidadPolicial u WHERE e.identificacion = u.identificadorUsuario AND e.estadoCuCo10.idConstantes IN :idEstados AND e.periodo.idPeriodo = :idPeriodo ORDER BY u.unidadPolicial.nombre, e.tituloEnsayo"),
  @NamedQuery(name = "EnsayoCritico.countByPeriodoVicin", query = "SELECT COUNT(e) FROM EnsayoCritico e WHERE e.periodo.idPeriodo = :idPeriodo AND e.estadoCuCo8.idConstantes = 199 AND e.calificacionCuCo8.idConstantes = 213 ")})

@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "EnsayoCritico.resultadoConvocatoriaEnsayo", procedureName = "PRC_REPORTE_CUCO_10",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_NUM_CONVOCA", queryParameter = "idConvocatoria", mode = ParameterMode.IN, type = Long.class)
          })
})

public class EnsayoCritico implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EnsayoCritico_seq_gen")
  @SequenceGenerator(name = "EnsayoCritico_seq_gen", sequenceName = "SEC_ENSAYO_CRITICO", allocationSize = 1)
  @Column(name = "ID_ENSAYO_CRITICO")
  private Long idEnsayoCritico;

  @Column(name = "TITULO_ENSAYO", nullable = false, length = 512)
  private String tituloEnsayo;

  @Column(name = "RESUMEN_ENSAYO", nullable = false, length = 4000)
  private String resumenEnsayo;

  @Column(name = "PALABRAS_CLAVE_ENSAYO", nullable = false, length = 256)
  private String palabrasClaveEnsayo;

  @Column(name = "ABSTRACT_ENSAYO", nullable = false, length = 4000)
  private String abstractEnsayo;

  @Column(name = "KEYWORDS_ENSAYO", nullable = false, length = 256)
  private String keywordsEnsayo;

  @Column(name = "INTRODUCCION_ENSAYO", nullable = false, length = 4000)
  private String introduccionEnsayo;

  @Lob
  @Column(name = "DESARROLLO_ENSAYO", nullable = false)
  private byte[] desarrolloEnsayo;

  @Transient
  private String desarrolloEnsayoString;

  @Column(name = "NOMBRE_ARCHIVO", nullable = false, length = 100)
  private String nombreArchivo;

  @Column(name = "NOMBRE_ARCHIVO_ORIGINAL", nullable = false, length = 100)
  private String nombreArchivoOriginal;

  @Column(name = "CONCLUSIONES_ENSAYO", nullable = false, length = 4000)
  private String conclusionesEnsayo;

  @Column(name = "PROPUESTA_ENSAYO", nullable = false, length = 4000)
  private String propuestaEnsayo;

  @Column(name = "BIBLIOGRAFIA_ENSAYO", nullable = false, length = 4000)
  private String bibliografiaEnsayo;

  @Column(name = "FECHA_CREACION", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "FECHA_MODIFICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;

  @Column(name = "NOMBRE_MAQUINA", nullable = false, length = 50)
  private String nombreMaquina;

  @JoinColumn(name = "ID_USUARIO_ROL_MODIFCA", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolModifca;

  @JoinColumn(name = "ID_USUARIO_ROL_CREACION", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRolCreacion;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Periodo periodo;

  @JoinColumn(name = "ID_CALIFICACION_CUCO8", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes calificacionCuCo8;

  @JoinColumn(name = "ID_CALIFICACION_CUCO10", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes calificacionCuCo10;

  @JoinColumn(name = "ID_ESTADO_CUCO8", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes estadoCuCo8;

  @JoinColumn(name = "ID_ESTADO_CUCO10", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes estadoCuCo10;

  @Transient
  private String nombreUnidadPolicial;

  @Transient
  private Long idUnidadPolicial;

  @Column(name = "FECHA_PRESENTACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaPresentacion;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "GRADO_Y_NOMBRES")
  private String gradoYnombres;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.EAGER)
  private UnidadPolicial unidadPolicial;

  @Column(name = "IDENTIFICA_EVALUA_CUCO8")
  private String identificaEvaluaCuCo8;

  @Column(name = "NOMBRE_APELLIDO_EVALUA_CUCO8")
  private String nombreApellidoEvaluaCuCo8;

  @Column(name = "IDENTIFICA_EVALUA_CUCO10")
  private String identificaEvaluaCuCo10;

  @Column(name = "NOMBRE_APELLIDO_EVALUA_CUCO10")
  private String nombreApellidoEvaluaCuCo10;

  public EnsayoCritico() {
  }

  public EnsayoCritico(Long idEnsayoCritico) {
    this.idEnsayoCritico = idEnsayoCritico;
  }

  public EnsayoCritico(Long idEnsayoCritico, String tituloEnsayo, String resumenEnsayo, String palabrasClaveEnsayo, String abstractEnsayo, String keywordsEnsayo, String introduccionEnsayo, byte[] desarrolloEnsayo, String nombreArchivo, String nombreArchivoOriginal, String conclusionesEnsayo, String propuestaEnsayo, String bibliografiaEnsayo, Date fechaCreacion, String nombreMaquina) {
    this.idEnsayoCritico = idEnsayoCritico;
    this.tituloEnsayo = tituloEnsayo;
    this.resumenEnsayo = resumenEnsayo;
    this.palabrasClaveEnsayo = palabrasClaveEnsayo;
    this.abstractEnsayo = abstractEnsayo;
    this.keywordsEnsayo = keywordsEnsayo;
    this.introduccionEnsayo = introduccionEnsayo;
    this.desarrolloEnsayo = desarrolloEnsayo;
    this.nombreArchivo = nombreArchivo;
    this.nombreArchivoOriginal = nombreArchivoOriginal;
    this.conclusionesEnsayo = conclusionesEnsayo;
    this.propuestaEnsayo = propuestaEnsayo;
    this.bibliografiaEnsayo = bibliografiaEnsayo;
    this.fechaCreacion = fechaCreacion;
    this.nombreMaquina = nombreMaquina;
  }

  public Long getIdEnsayoCritico() {
    return idEnsayoCritico;
  }

  public void setIdEnsayoCritico(Long idEnsayoCritico) {
    this.idEnsayoCritico = idEnsayoCritico;
  }

  public String getTituloEnsayo() {
    return tituloEnsayo;
  }

  public void setTituloEnsayo(String tituloEnsayo) {
    this.tituloEnsayo = tituloEnsayo;
  }

  public String getResumenEnsayo() {
    return resumenEnsayo;
  }

  public void setResumenEnsayo(String resumenEnsayo) {
    this.resumenEnsayo = resumenEnsayo;
  }

  public String getPalabrasClaveEnsayo() {
    return palabrasClaveEnsayo;
  }

  public void setPalabrasClaveEnsayo(String palabrasClaveEnsayo) {
    this.palabrasClaveEnsayo = palabrasClaveEnsayo;
  }

  public String getAbstractEnsayo() {
    return abstractEnsayo;
  }

  public void setAbstractEnsayo(String abstractEnsayo) {
    this.abstractEnsayo = abstractEnsayo;
  }

  public String getKeywordsEnsayo() {
    return keywordsEnsayo;
  }

  public void setKeywordsEnsayo(String keywordsEnsayo) {
    this.keywordsEnsayo = keywordsEnsayo;
  }

  public String getIntroduccionEnsayo() {
    return introduccionEnsayo;
  }

  public void setIntroduccionEnsayo(String introduccionEnsayo) {
    this.introduccionEnsayo = introduccionEnsayo;
  }

  public byte[] getDesarrolloEnsayo() {
    return desarrolloEnsayo;
  }

  public void setDesarrolloEnsayo(byte[] desarrolloEnsayo) {
    this.desarrolloEnsayo = desarrolloEnsayo;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getNombreArchivoOriginal() {
    return nombreArchivoOriginal;
  }

  public void setNombreArchivoOriginal(String nombreArchivoOriginal) {
    this.nombreArchivoOriginal = nombreArchivoOriginal;
  }

  public String getConclusionesEnsayo() {
    return conclusionesEnsayo;
  }

  public void setConclusionesEnsayo(String conclusionesEnsayo) {
    this.conclusionesEnsayo = conclusionesEnsayo;
  }

  public String getPropuestaEnsayo() {
    return propuestaEnsayo;
  }

  public void setPropuestaEnsayo(String propuestaEnsayo) {
    this.propuestaEnsayo = propuestaEnsayo;
  }

  public String getBibliografiaEnsayo() {
    return bibliografiaEnsayo;
  }

  public void setBibliografiaEnsayo(String bibliografiaEnsayo) {
    this.bibliografiaEnsayo = bibliografiaEnsayo;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getNombreMaquina() {
    return nombreMaquina;
  }

  public void setNombreMaquina(String nombreMaquina) {
    this.nombreMaquina = nombreMaquina;
  }

  public UsuarioRol getUsuarioRolModifca() {
    return usuarioRolModifca;
  }

  public void setUsuarioRolModifca(UsuarioRol usuarioRolModifca) {
    this.usuarioRolModifca = usuarioRolModifca;
  }

  public UsuarioRol getUsuarioRolCreacion() {
    return usuarioRolCreacion;
  }

  public void setUsuarioRolCreacion(UsuarioRol usuarioRolCreacion) {
    this.usuarioRolCreacion = usuarioRolCreacion;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  public Constantes getCalificacionCuCo8() {
    return calificacionCuCo8;
  }

  public void setCalificacionCuCo8(Constantes calificacionCuCo8) {
    this.calificacionCuCo8 = calificacionCuCo8;
  }

  public Constantes getCalificacionCuCo10() {
    return calificacionCuCo10;
  }

  public void setCalificacionCuCo10(Constantes calificacionCuCo10) {
    this.calificacionCuCo10 = calificacionCuCo10;
  }

  public Constantes getEstadoCuCo8() {
    return estadoCuCo8;
  }

  public void setEstadoCuCo8(Constantes estadoCuCo8) {
    this.estadoCuCo8 = estadoCuCo8;
  }

  public Constantes getEstadoCuCo10() {
    return estadoCuCo10;
  }

  public void setEstadoCuCo10(Constantes estadoCuCo10) {
    this.estadoCuCo10 = estadoCuCo10;
  }

  public String getDesarrolloEnsayoString() {
    if (desarrolloEnsayoString != null) {
      desarrolloEnsayo = desarrolloEnsayoString.getBytes();
    }
    return desarrolloEnsayoString;
  }

  public void setDesarrolloEnsayoString(String desarrolloEnsayoString) {
    this.desarrolloEnsayoString = desarrolloEnsayoString;
  }

  public String getNombreUnidadPolicial() {
    return nombreUnidadPolicial;
  }

  public void setNombreUnidadPolicial(String nombreUnidadPolicial) {
    this.nombreUnidadPolicial = nombreUnidadPolicial;
  }

  public Long getIdUnidadPolicial() {
    return idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long idUnidadPolicial) {
    this.idUnidadPolicial = idUnidadPolicial;
  }

  public Date getFechaPresentacion() {
    return fechaPresentacion;
  }

  public void setFechaPresentacion(Date fechaPresentacion) {
    this.fechaPresentacion = fechaPresentacion;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEnsayoCritico != null ? idEnsayoCritico.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EnsayoCritico)) {
      return false;
    }
    EnsayoCritico other = (EnsayoCritico) object;
    if ((this.idEnsayoCritico == null && other.idEnsayoCritico != null) || (this.idEnsayoCritico != null && !this.idEnsayoCritico.equals(other.idEnsayoCritico))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EnsayoCritico[ idEnsayoCritico=" + idEnsayoCritico + " ]";
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public String getGradoYnombres() {
    return gradoYnombres;
  }

  public void setGradoYnombres(String gradoYnombres) {
    this.gradoYnombres = gradoYnombres;
  }

  @Override
  public String getLlaveModel() {

    return String.valueOf(idEnsayoCritico);

  }

  public String getIdentificaEvaluaCuCo8() {
    return identificaEvaluaCuCo8;
  }

  public void setIdentificaEvaluaCuCo8(String identificaEvaluaCuCo8) {
    this.identificaEvaluaCuCo8 = identificaEvaluaCuCo8;
  }

  public String getNombreApellidoEvaluaCuCo8() {
    return nombreApellidoEvaluaCuCo8;
  }

  public void setNombreApellidoEvaluaCuCo8(String nombreApellidoEvaluaCuCo8) {
    this.nombreApellidoEvaluaCuCo8 = nombreApellidoEvaluaCuCo8;
  }

  public String getIdentificaEvaluaCuCo10() {
    return identificaEvaluaCuCo10;
  }

  public void setIdentificaEvaluaCuCo10(String identificaEvaluaCuCo10) {
    this.identificaEvaluaCuCo10 = identificaEvaluaCuCo10;
  }

  public String getNombreApellidoEvaluaCuCo10() {
    return nombreApellidoEvaluaCuCo10;
  }

  public void setNombreApellidoEvaluaCuCo10(String nombreApellidoEvaluaCuCo10) {
    this.nombreApellidoEvaluaCuCo10 = nombreApellidoEvaluaCuCo10;
  }

}
