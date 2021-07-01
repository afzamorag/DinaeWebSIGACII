package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.siedu.modelo.SieduPropuestaAsignada;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "PROPUESTA_NECESIDAD")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = "PropuestaNecesidad.findAll", query = "SELECT p FROM PropuestaNecesidad p"),
  @NamedQuery(name = "PropuestaNecesidad.cuentaPropuestasDashBoard", query = "SELECT COUNT(p) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.constantes.idConstantes IN (3,4,18)"),
  @NamedQuery(name = "PropuestaNecesidad.findAllByPeriodo", query = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "PropuestaNecesidad.findAllByPeriodoYunidadPolicial", query = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ORDER BY p.idPropuestaNecesidad ASC"),
  @NamedQuery(name = "PropuestaNecesidad.ContarByPeriodoYunidadPolicialYEstado", query = "SELECT COUNT(p) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial AND p.constantes.idConstantes = :idEstado"),
  @NamedQuery(name = "PropuestaNecesidad.ContarByPeriodoYEstado", query = "SELECT COUNT(p) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.constantes.idConstantes = :idEstado"),
  @NamedQuery(name = "PropuestaNecesidad.contarByPeriodoYEstados", query = "SELECT COUNT( p ) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstadoElaboracion OR p.constantes.idConstantes = :idEstadoAceptado OR p.constantes.idConstantes = :idEstadoNoAceptado )"),
  @NamedQuery(name = "PropuestaNecesidad.contarTodasPropuestasPorPeriodo", query = "SELECT COUNT( p ) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "PropuestaNecesidad.contarTodasPropuestasPorPeriodoYunidad", query = "SELECT COUNT( p ) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial"),
  @NamedQuery(name = "PropuestaNecesidad.ContarByPeriodoYEstadosVicincAceptadaRevisada", query = "SELECT COUNT(p) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.constantes.idConstantes  IN (:idEstadoVicin,:idEstadoPreaprobada,:idEstadoRevisada)"),
  @NamedQuery(name = "PropuestaNecesidad.findAllByPeriodoYListaEstado", query = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstado1 OR p.constantes.idConstantes = :idEstado2 OR p.constantes.idConstantes = :idEstado3 ) ORDER BY p.unidadPolicial.nombre ASC, p.concecutivo ASC "),
  @NamedQuery(name = "PropuestaNecesidadDTO.findAllByPeriodoYListaEstado", query = "SELECT NEW co.gov.policia.dinae.dto.PropuestaNecesidadDTO ( p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre ) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstado1 OR p.constantes.idConstantes = :idEstado2 OR p.constantes.idConstantes = :idEstado3 )"),
  @NamedQuery(name = "PropuestaNecesidad.findAllByPeriodoYListaEstadoYunidadPolicial", query = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstado1 OR p.constantes.idConstantes = :idEstado2 OR p.constantes.idConstantes = :idEstado3 ) AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ORDER BY p.unidadPolicial.nombre ASC, p.concecutivo ASC  "),
  @NamedQuery(name = "PropuestaNecesidadDTO.findAllByPeriodoYListaEstadoYunidadPolicial", query = "SELECT NEW co.gov.policia.dinae.dto.PropuestaNecesidadDTO ( p.unidadPolicial.idUnidadPolicial, p.unidadPolicial.nombre ) FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND ( p.constantes.idConstantes = :idEstado1 OR p.constantes.idConstantes = :idEstado2 OR p.constantes.idConstantes = :idEstado3 ) AND p.unidadPolicial.idUnidadPolicial = :idUnidadPolicial ORDER BY p.unidadPolicial.nombre ASC"),
  @NamedQuery(name = "PropuestaNecesidad.findAllByPeriodoByUnidadByEstado", query = "SELECT p FROM PropuestaNecesidad p WHERE p.periodo.idPeriodo = :idPeriodo AND p.unidadPolicial.idUnidadPolicial =:idUnidadPolicial AND p.constantes.idConstantes = :estado ")
})
public class PropuestaNecesidad implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_PROPUESTA_NECESIDAD")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PropuestaNecesidad_seq_gen")
  @SequenceGenerator(name = "PropuestaNecesidad_seq_gen", sequenceName = "SEC_PROPUESTA_NECESIDAD", allocationSize = 1)
  private Long idPropuestaNecesidad;

  @Column(name = "TEMA")
  private String tema;

  @Column(name = "RESULTADO_ESPERADO")
  private String resultadoEsperado;

  @Column(name = "NOMBRE_ARCHIVO")
  private String nombreArchivo;

  @Column(name = "IDENTIFICADOR_USUARIO_MODIF")
  private String identificadorUsuarioModif;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FUENTE_INFORMACION")
  private String fuenteInformacion;

  @Column(name = "POSIBLE_ACTIVIDAD")
  private String posibleActividad;

  @Column(name = "FECHA_MODIFICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;

  @Column(name = "FECHA_ENVIO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEnvio;

  @Column(name = "IDENTIFICADOR_USUARIO_ENVIO")
  private String identificadorUsuarioEnvio;

  @Column(name = "TIPO_CONTENIDO_ARCHIVO")
  private String tipoContenidoArchivo;

  @OneToMany(mappedBy = "propuestaNecesidad", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private List<FuncionarioNecesidad> funcionarioNecesidadList;
  
  @OneToOne(mappedBy = "propuestaNecesidad", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private SieduPropuestaAsignada propuestaAsignada;
  
  @OneToMany(mappedBy = "propuestaNecesidad", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private List<CompromisoPeriodo> compromisoPeriodoList;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @JoinColumn(name = "ID_PERIODO", referencedColumnName = "ID_PERIODO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Periodo periodo;

  @JoinColumn(name = "ID_LINEA", referencedColumnName = "ID_LINEA")
  @ManyToOne(fetch = FetchType.EAGER)
  private Linea linea;

  @JoinColumn(name = "ID_CONSTANTES", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(fetch = FetchType.EAGER)
  private Constantes constantes;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Column(name = "CONCECUTIVO")
  private Short concecutivo;

  @Column(name = "ROL_ACTUAL")
  private String rolActual;
  
  @Column(name = "TITULO")
  private String titulo;
  
  @Column(name = "ID_BNE")
  private Long idBancoNecesidad; 
  
  @Column(name = "MAQUINA_CREA")
  private String maquinaCrea;
  
    @Column(name = "IP_CREA")
  private String ipCrea;
    
      @Column(name = "MAQUINA_MODIFICA")
  private String maquinaModifica;
      
        @Column(name = "IP_MODIFICA")
  private String ipModifica;
        
              @Column(name = "IDENTIFICADOR_USUARIO_CREACION")
  private String identificadorUsuarioCrea;
  /*@JoinColumns({
    @JoinColumn(name = "UDE_CONSECUTIVO", referencedColumnName = "CONSECUTIVO"),
    @JoinColumn(name = "UDE_FUERZA", referencedColumnName = "FUERZA")
  })
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UnidadDependencia unidad;*/
  

  @Transient
  private String comentario;

  @Transient
  private Long idContantes;

  @Transient
  private UnidadPolicial unidadPolicialReponsableEjecutora;

  @Transient
  private String estadoDescripcion;
  
  

  public PropuestaNecesidad() {
  }

  public PropuestaNecesidad(Long idPropuestaNecesidad) {
    this.idPropuestaNecesidad = idPropuestaNecesidad;
  }

  /**
   *
   * @param idPropuestaNecesidad
   * @param tema
   * @param resultadoEsperado
   * @param nombreArchivo
   * @param identificadorUsuarioModif
   * @param fechaRegistro
   * @param fuenteInformacion
   * @param posibleActividad
   * @param fechaModificacion
   * @param fechaEnvio
   * @param identificadorUsuarioEnvio
   * @param funcionarioNecesidadList
   * @param unidadPolicial
   * @param periodo
   * @param linea
   * @param constantes
   * @param nombreArchivoFisico
   * @param concecutivo
   * @param comentario
   * @param idContantes 
   */
  public PropuestaNecesidad(Long idPropuestaNecesidad, String tema, String resultadoEsperado, String nombreArchivo, String identificadorUsuarioModif, Date fechaRegistro, String fuenteInformacion, String posibleActividad, Date fechaModificacion, Date fechaEnvio, String identificadorUsuarioEnvio, List<FuncionarioNecesidad> funcionarioNecesidadList, UnidadPolicial unidadPolicial, Periodo periodo, Linea linea, Constantes constantes, String nombreArchivoFisico, Short concecutivo, String comentario, Long idContantes) {
    this.idPropuestaNecesidad = idPropuestaNecesidad;
    this.tema = tema;
    this.resultadoEsperado = resultadoEsperado;
    this.nombreArchivo = nombreArchivo;
    this.identificadorUsuarioModif = identificadorUsuarioModif;
    this.fechaRegistro = fechaRegistro;
    this.fuenteInformacion = fuenteInformacion;
    this.posibleActividad = posibleActividad;
    this.fechaModificacion = fechaModificacion;
    this.fechaEnvio = fechaEnvio;
    this.identificadorUsuarioEnvio = identificadorUsuarioEnvio;
    this.funcionarioNecesidadList = funcionarioNecesidadList;
    this.unidadPolicial = unidadPolicial;
    this.periodo = periodo;
    this.linea = linea;
    this.constantes = constantes;
    this.nombreArchivoFisico = nombreArchivoFisico;
    this.concecutivo = concecutivo;
    this.comentario = comentario;
    this.idContantes = idContantes;
  }

  public Long getIdPropuestaNecesidad() {
    return idPropuestaNecesidad;
  }

  public void setIdPropuestaNecesidad(Long idPropuestaNecesidad) {
    this.idPropuestaNecesidad = idPropuestaNecesidad;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getResultadoEsperado() {
    return resultadoEsperado;
  }

  public void setResultadoEsperado(String resultadoEsperado) {
    this.resultadoEsperado = resultadoEsperado;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getIdentificadorUsuarioModif() {
    return identificadorUsuarioModif;
  }

  public void setIdentificadorUsuarioModif(String identificadorUsuarioModif) {
    this.identificadorUsuarioModif = identificadorUsuarioModif;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getFuenteInformacion() {
    return fuenteInformacion;
  }

  public void setFuenteInformacion(String fuenteInformacion) {
    this.fuenteInformacion = fuenteInformacion;
  }

  public String getPosibleActividad() {
    return posibleActividad;
  }

  public void setPosibleActividad(String posibleActividad) {
    this.posibleActividad = posibleActividad;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public Date getFechaEnvio() {
    return fechaEnvio;
  }

  public void setFechaEnvio(Date fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
  }

  public String getIdentificadorUsuarioEnvio() {
    return identificadorUsuarioEnvio;
  }

  public void setIdentificadorUsuarioEnvio(String identificadorUsuarioEnvio) {
    this.identificadorUsuarioEnvio = identificadorUsuarioEnvio;
  }

  public List<FuncionarioNecesidad> getFuncionarioNecesidadList() {
    return funcionarioNecesidadList;
  }

  public void setFuncionarioNecesidadList(List<FuncionarioNecesidad> funcionarioNecesidadList) {
    this.funcionarioNecesidadList = funcionarioNecesidadList;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public Periodo getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Periodo periodo) {
    this.periodo = periodo;
  }

  public Linea getLinea() {
    return linea;
  }

  public void setLinea(Linea linea) {
    this.linea = linea;
  }

  public Constantes getConstantes() {
    return constantes;
  }

  public void setConstantes(Constantes constantes) {
    this.constantes = constantes;
  }

  public String getTipoContenidoArchivo() {
    return tipoContenidoArchivo;
  }

  public void setTipoContenidoArchivo(String tipoContenidoArchivo) {
    this.tipoContenidoArchivo = tipoContenidoArchivo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPropuestaNecesidad != null ? idPropuestaNecesidad.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof PropuestaNecesidad)) {
      return false;
    }
    PropuestaNecesidad other = (PropuestaNecesidad) object;
    if ((this.idPropuestaNecesidad == null && other.idPropuestaNecesidad != null) || (this.idPropuestaNecesidad != null && !this.idPropuestaNecesidad.equals(other.idPropuestaNecesidad))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.PropuestaNecesidad[ idPropuestaNecesidad=" + idPropuestaNecesidad + " ]";
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  @Override
  public String getLlaveModel() {
    return String.valueOf(idPropuestaNecesidad);
  }

  public Short getConcecutivo() {
    return concecutivo;
  }

  public void setConcecutivo(Short concecutivo) {
    this.concecutivo = concecutivo;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public Long getIdContantes() {
    return idContantes;
  }

  public void setIdContantes(Long idContantes) {
    this.idContantes = idContantes;
  }

  public UnidadPolicial getUnidadPolicialReponsableEjecutora() {
    return unidadPolicialReponsableEjecutora;
  }

  public void setUnidadPolicialReponsableEjecutora(UnidadPolicial unidadPolicialReponsableEjecutora) {
    this.unidadPolicialReponsableEjecutora = unidadPolicialReponsableEjecutora;
  }

  public String getRolActual() {
    return rolActual;
  }

  public void setRolActual(String rolActual) {
    this.rolActual = rolActual;
  }

  public String getEstadoDescripcion() {

    if (constantes == null) {
      return "";
    }

    if (rolActual == null || "PUBLICADA_JEFE_UNIDAD".equals(rolActual)) {
      return constantes.getValor();
    }
    return "Enviada a VICIN";

  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Long getIdBancoNecesidad() {
    return idBancoNecesidad;
  }

  public void setIdBancoNecesidad(Long idBancoNecesidad) {
    this.idBancoNecesidad = idBancoNecesidad;
  }
  

  /*public UnidadDependencia getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }*/

    public String getMaquinaCrea() {
        return maquinaCrea;
    }

    public void setMaquinaCrea(String maquinaCrea) {
        this.maquinaCrea = maquinaCrea;
    }

    public String getIpCrea() {
        return ipCrea;
    }

    public void setIpCrea(String ipCrea) {
        this.ipCrea = ipCrea;
    }

    public String getMaquinaModifica() {
        return maquinaModifica;
    }

    public void setMaquinaModifica(String maquinaModifica) {
        this.maquinaModifica = maquinaModifica;
    }

    public String getIpModifica() {
        return ipModifica;
    }

    public void setIpModifica(String ipModifica) {
        this.ipModifica = ipModifica;
    }

    public String getIdentificadorUsuarioCrea() {
        return identificadorUsuarioCrea;
    }

    public void setIdentificadorUsuarioCrea(String identificadorUsuarioCrea) {
        this.identificadorUsuarioCrea = identificadorUsuarioCrea;
    }

    public List<CompromisoPeriodo> getCompromisoPeriodoList() {
        return compromisoPeriodoList;
    }

    public void setCompromisoPeriodoList(List<CompromisoPeriodo> compromisoPeriodoList) {
        this.compromisoPeriodoList = compromisoPeriodoList;
    }

    public SieduPropuestaAsignada getPropuestaAsignada() {
        return propuestaAsignada;
    }

    public void setPropuestaAsignada(SieduPropuestaAsignada propuestaAsignada) {
        this.propuestaAsignada = propuestaAsignada;
    }

   
     
}
