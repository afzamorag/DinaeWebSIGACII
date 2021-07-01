package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "PERIODO")
@Cacheable(false)
@NamedQueries({
  @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p ORDER BY p.idPeriodo DESC"),
  @NamedQuery(name = "Periodo.DISTINCTfindAllAniosProyectosInstitucionales", query = "SELECT p FROM Periodo p WHERE p.anio IS NOT NULL AND p.estado = 'P' ORDER BY p.anio DESC"),
  @NamedQuery(name = "Periodo.findAllNecesidades", query = "SELECT p FROM Periodo p WHERE p.tipoRegistro = 'NECESIDAD' AND p.idPeriodo <> 1 ORDER BY p.idPeriodo DESC"),
  @NamedQuery(name = "Periodo.findByEstado", query = "SELECT p FROM Periodo p WHERE p.estado = :estado"),
  @NamedQuery(name = "Periodo.findByEstadoYFechaInicioFechaFinEntre", query = "SELECT p FROM Periodo p WHERE p.estado = :estado AND :fechaActual BETWEEN p.fechaInicio AND p.fechaFin"),
  @NamedQuery(name = "Periodo.findConvocaEnsayoCriticoAbiertaPublicada", query = "SELECT p FROM Periodo p WHERE p.estadoConvocatorio.idConstantes = :estadoConvocatoria AND :fechaActual BETWEEN p.fechaInicio AND p.fechaFin AND p.tipoRegistro = 'CONVOCATORIA' AND p.tipoPeriodo.idConstantes = :tipoPeriodo"),
  @NamedQuery(name = "PeriodoDTO.findAllPorTipoConvocatoria", query = "SELECT NEW co.gov.policia.dinae.dto.PeriodoDTO( c.idPeriodo, c.nombreConvocatorio, c.concecutivo, c.estadoConvocatorio.valor, c.estadoConvocatorio.idConstantes, c.fechaInicio, c.fechaFin ) FROM Periodo c WHERE c.tipoPeriodo.idConstantes = :idConstantes AND c.tipoRegistro = :tipoRegistro ORDER BY c.concecutivo"),
  @NamedQuery(name = "Periodo.findAllConvocatoriasParaBusquedaProyectos", query = "SELECT p FROM Periodo p WHERE p.concecutivoConvocatoria IS NOT NULL"),
  @NamedQuery(name = "Periodo.UpdateEstadoPeriodo", query = "UPDATE Periodo p SET p.estadoConvocatorio = :estado WHERE p.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "Periodo.UpdateArchivosConvocatoriaEnsayo", query = "UPDATE Periodo p SET p.nombreArchivoFisicoConvocatoriaEnsayo = :nombreArchivoFisicoConvocatoriaEnsayo, p.nombreArchivoOriginalConvocatoriaEnsayo = :nombreArchivoOriginalConvocatoriaEnsayo, p.estadoConvocatorio = :estado WHERE p.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "Periodo.UpdateArchivosPropuestaConvocatoriaReporte", query = "UPDATE Periodo p SET p.nombreArchivoFisicoPropuestaConvocatoriaReporte = :nombreArchivoFisicoPropuestaConvocatoriaReporte, p.nombreArchivoOriginalPropuestaConvocatoriaReporte = :nombreArchivoOriginalPropuestaConvocatoriaReporte WHERE p.idPeriodo = :idPeriodo"),
  @NamedQuery(name = "Periodo.UpdateArchivosPropuestaNecesidadReporte", query = "UPDATE Periodo p SET p.nombreArchivoFisicoPropuestaNecesidadReporte = :nombreArchivoFisicoPropuestaNecesidadReporte, p.nombreArchivoOriginalPropuestaNecesidadReporte = :nombreArchivoOriginalPropuestaNecesidadReporte WHERE p.idPeriodo = :idPeriodo")
})
public class Periodo implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Periodo_seq_gen")
  @SequenceGenerator(name = "Periodo_seq_gen", sequenceName = "SEC_PERIODO", allocationSize = 1)
  @Column(name = "ID_PERIODO")
  private Long idPeriodo;

  @Column(name = "ANIO")
  private Short anio;

  @Column(name = "DESCRIPCION", length = 500)
  @Size(min = 0, max = 500, message = "Campo Descripción supera el máximo permitido")
  private String descripcion;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;

  @Column(name = "CANTIDAD")
  private Short cantidad;

  @Column(name = "ESTADO")
  private Character estado;

  @Column(name = "NOMBRE_ARCHIVO")
  private String nombreArchivo;

  @NotNull(message = "EL TIPO DE REGISTRO ES OBLIGATORIO, POR DEFECTO ES 'NECESIDAD', 'CONVOCATORIA' ES OTRO VALOR POSIBLE")
  @Column(name = "TIPO_REGISTRO")
  @Pattern(regexp = "NECESIDAD|CONVOCATORIA", message = "EL TIPO DE REGISTRO NO ES VALIDO, LOS POSIBLES VALORES SON: 'NECESIDAD' Y 'CONVOCATORIA'")
  private String tipoRegistro;

  @Column(name = "CONSECUTIVO_ENSAYO")
  private Long concecutivo;

  @Column(name = "CONSECUTIVO_CONVOCATORIA")
  private Long concecutivoConvocatoria;

  @JoinColumn(name = "ID_TIPO_PERIODO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes tipoPeriodo;

  @Column(name = "NOMBRE_CONVOCATORIA")
  private String nombreConvocatorio;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Column(name = "IDENTIFICADOR_USUARIO_CREA")
  private String identificadorUsuarioCrea;

  @Column(name = "IDENTIFICADOR_USUARIO_MODIF")
  private String identificadorUsuarioModif;

  @JoinColumn(name = "ID_ESTADO_CONVOCATORIA", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Constantes estadoConvocatorio;

  @OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<TipoUnidadPeriodo> tipoUnidadPeriodoList;

  @OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<UnidadPolicialPeriodo> unidadPolicialPeriodoList;

  @Column(name = "NOM_ARCHI_FISICO_CONV_ENSAYO")
  private String nombreArchivoFisicoConvocatoriaEnsayo;

  @Column(name = "NOM_ARCHI_ORIGINAL_CONV_ENSAYO")
  private String nombreArchivoOriginalConvocatoriaEnsayo;

  @Column(name = "NOM_ARCHI_FISI_PROP_CONV")
  private String nombreArchivoFisicoPropuestaConvocatoriaReporte;

  @Column(name = "NOM_ARCHI_ORIGIN_PROP_CONV")
  private String nombreArchivoOriginalPropuestaConvocatoriaReporte;

  @Column(name = "NOM_ARCHI_FISI_PROP_NECE")
  private String nombreArchivoFisicoPropuestaNecesidadReporte;

  @Column(name = "NOM_ARCHI_ORIGIN_PROP_NECE")
  private String nombreArchivoOriginalPropuestaNecesidadReporte;

  public Periodo() {
  }

  public Periodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Periodo(Long idPeriodo, Short anio, String descripcion, Date fechaInicio, Date fechaFin, Short cantidad, Character estado) {
    this.idPeriodo = idPeriodo;
    this.anio = anio;
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.cantidad = cantidad;
    this.estado = estado;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  public Short getAnio() {
    return anio;
  }

  public void setAnio(Short anio) {
    this.anio = anio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Short getCantidad() {
    return cantidad;
  }

  public void setCantidad(Short cantidad) {
    this.cantidad = cantidad;
  }

  public Character getEstado() {
    return estado;
  }

  public void setEstado(Character estado) {
    this.estado = estado;
  }

  public List<TipoUnidadPeriodo> getTipoUnidadPeriodoList() {
    return tipoUnidadPeriodoList;
  }

  public void setTipoUnidadPeriodoList(List<TipoUnidadPeriodo> tipoUnidadPeriodoList) {
    this.tipoUnidadPeriodoList = tipoUnidadPeriodoList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPeriodo != null ? idPeriodo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Periodo)) {
      return false;
    }
    Periodo other = (Periodo) object;
    if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Periodo[ idPeriodo=" + idPeriodo + " ]";
  }

  @Override
  @Transient
  public String getLlaveModel() {
    return String.valueOf(idPeriodo);
  }

  public List<UnidadPolicialPeriodo> getUnidadPolicialPeriodoList() {
    return unidadPolicialPeriodoList;
  }

  public void setUnidadPolicialPeriodoList(List<UnidadPolicialPeriodo> unidadPolicialPeriodoList) {
    this.unidadPolicialPeriodoList = unidadPolicialPeriodoList;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public Constantes getTipoPeriodo() {
    return tipoPeriodo;
  }

  public void setTipoPeriodo(Constantes tipoPeriodo) {
    this.tipoPeriodo = tipoPeriodo;
  }

  public String getNombreConvocatorio() {
    return nombreConvocatorio;
  }

  public void setNombreConvocatorio(String nombreConvocatorio) {
    this.nombreConvocatorio = nombreConvocatorio;
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  public String getIdentificadorUsuarioCrea() {
    return identificadorUsuarioCrea;
  }

  public void setIdentificadorUsuarioCrea(String identificadorUsuarioCrea) {
    this.identificadorUsuarioCrea = identificadorUsuarioCrea;
  }

  public String getIdentificadorUsuarioModif() {
    return identificadorUsuarioModif;
  }

  public void setIdentificadorUsuarioModif(String identificadorUsuarioModif) {
    this.identificadorUsuarioModif = identificadorUsuarioModif;
  }

  public Constantes getEstadoConvocatorio() {
    return estadoConvocatorio;
  }

  public void setEstadoConvocatorio(Constantes estadoConvocatorio) {
    this.estadoConvocatorio = estadoConvocatorio;
  }

  public Long getConcecutivo() {
    return concecutivo;
  }

  public void setConcecutivo(Long concecutivo) {
    this.concecutivo = concecutivo;
  }

  public String getTipoRegistro() {
    return tipoRegistro;
  }

  public void setTipoRegistro(String tipoRegistro) {
    this.tipoRegistro = tipoRegistro;
  }

  /**
   * @return the concecutivoConvocatoria
   */
  public Long getConcecutivoConvocatoria() {
    return concecutivoConvocatoria;
  }

  /**
   * @param concecutivoConvocatoria the concecutivoConvocatoria to set
   */
  public void setConcecutivoConvocatoria(Long concecutivoConvocatoria) {
    this.concecutivoConvocatoria = concecutivoConvocatoria;
  }

  public String getNombreArchivoFisicoConvocatoriaEnsayo() {
    return nombreArchivoFisicoConvocatoriaEnsayo;
  }

  public void setNombreArchivoFisicoConvocatoriaEnsayo(String nombreArchivoFisicoConvocatoriaEnsayo) {
    this.nombreArchivoFisicoConvocatoriaEnsayo = nombreArchivoFisicoConvocatoriaEnsayo;
  }

  public String getNombreArchivoOriginalConvocatoriaEnsayo() {
    return nombreArchivoOriginalConvocatoriaEnsayo;
  }

  public void setNombreArchivoOriginalConvocatoriaEnsayo(String nombreArchivoOriginalConvocatoriaEnsayo) {
    this.nombreArchivoOriginalConvocatoriaEnsayo = nombreArchivoOriginalConvocatoriaEnsayo;
  }

  public String getNombreArchivoFisicoPropuestaConvocatoriaReporte() {
    return nombreArchivoFisicoPropuestaConvocatoriaReporte;
  }

  public void setNombreArchivoFisicoPropuestaConvocatoriaReporte(String nombreArchivoFisicoPropuestaConvocatoriaReporte) {
    this.nombreArchivoFisicoPropuestaConvocatoriaReporte = nombreArchivoFisicoPropuestaConvocatoriaReporte;
  }

  public String getNombreArchivoOriginalPropuestaConvocatoriaReporte() {
    return nombreArchivoOriginalPropuestaConvocatoriaReporte;
  }

  public void setNombreArchivoOriginalPropuestaConvocatoriaReporte(String nombreArchivoOriginalPropuestaConvocatoriaReporte) {
    this.nombreArchivoOriginalPropuestaConvocatoriaReporte = nombreArchivoOriginalPropuestaConvocatoriaReporte;
  }

  public String getNombreArchivoFisicoPropuestaNecesidadReporte() {
    return nombreArchivoFisicoPropuestaNecesidadReporte;
  }

  public void setNombreArchivoFisicoPropuestaNecesidadReporte(String nombreArchivoFisicoPropuestaNecesidadReporte) {
    this.nombreArchivoFisicoPropuestaNecesidadReporte = nombreArchivoFisicoPropuestaNecesidadReporte;
  }

  public String getNombreArchivoOriginalPropuestaNecesidadReporte() {
    return nombreArchivoOriginalPropuestaNecesidadReporte;
  }

  public void setNombreArchivoOriginalPropuestaNecesidadReporte(String nombreArchivoOriginalPropuestaNecesidadReporte) {
    this.nombreArchivoOriginalPropuestaNecesidadReporte = nombreArchivoOriginalPropuestaNecesidadReporte;
  }

}
