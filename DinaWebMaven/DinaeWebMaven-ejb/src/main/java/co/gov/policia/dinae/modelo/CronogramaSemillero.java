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
@Table(name = "CRONOGRAMA_SEMILLERO")
@NamedQueries({
  @NamedQuery(name = "CronogramaSemillero.findAll", query = "SELECT c FROM CronogramaSemillero c"),
  @NamedQuery(name = "CronogramaSemillero.findAllActivitiesBySemillero", query = "SELECT c FROM CronogramaSemillero c WHERE c.semilleroInvestigacion.idSemillero = :idSemillero"),
  @NamedQuery(name = "CronogramaSemillero.findAllActivitiesBySemilleroProyecto", query = "SELECT c FROM CronogramaSemillero c WHERE c.semilleroProyecto.idSemilleroProyecto = :idSemilleroProyecto"),
  @NamedQuery(name = "CronogramaSemillero.findAllActivitiesBySemillerosImplementacion", query = "SELECT c FROM CronogramaSemillero c WHERE c.semillerosImplementacion.idSemilleroImplemetacion = :idSemilleroImplemetacion")
})
public class CronogramaSemillero implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CronogramaSemillero_seq_gen")
  @SequenceGenerator(name = "CronogramaSemillero_seq_gen", sequenceName = "SEC_CRONOGRAMA_SEMILLERO", allocationSize = 1)
  @Column(name = "ID_CRONOGRAMA_SEMILLERO")
  private Long idCronogramaSemillero;

  @Column(name = "ACTIVIDAD", nullable = true, length = 512)
  private String actividad;

  @Column(name = "OBJETIVO", nullable = true, length = 512)
  private String objetivo;

  @Column(name = "RESPONSABLE", nullable = true, length = 512)
  private String responsable;

  @Column(name = "PRODUCTO_RESULTADO_ESPERADO", nullable = true, length = 512)
  private String productoResultadoEsperado;

  @Column(name = "FECHA_CUMPLIMIENTO", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCumplimiento;

  @Column(name = "ARCHIVO_EVIDENCIA_FOTO", nullable = true, length = 512)
  private String archivoEvidenciaFoto;

  @Column(name = "ARCHIVO_EVIDENCIA", nullable = true, length = 512)
  private String archivoEvidencia;

  @Column(name = "ARCHIVO_EVIDENCIA_FOTO_ORIG", nullable = true, length = 512)
  private String archivoEvidenciaFotoOriginal;

  @Column(name = "ARCHIVO_EVIDENCIA_ORIGINAL", nullable = true, length = 512)
  private String archivoEvidenciaOriginal;

  @Column(name = "FECHA_CREACION", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "MAQUINA", nullable = true, length = 50)
  private String maquina;

  @Column(name = "FECHA_ACTUALIZACION", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @Column(name = "MAQUINA_ACTUALIZA", nullable = true, length = 50)
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

  @JoinColumn(name = "ID_SEMILLERO_IMPLEMENTACION", referencedColumnName = "ID_SEMILLERO_IMPLEMETACION")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemillerosImplementacion semillerosImplementacion;

  @JoinColumn(name = "ID_SEMILLERO_PROYECTO", referencedColumnName = "ID_SEMILLERO_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private SemilleroProyecto semilleroProyecto;

  public CronogramaSemillero() {
  }

  public CronogramaSemillero(Long idCronogramaSemillero) {
    this.idCronogramaSemillero = idCronogramaSemillero;
  }

  public Long getIdCronogramaSemillero() {
    return idCronogramaSemillero;
  }

  public void setIdCronogramaSemillero(Long idCronogramaSemillero) {
    this.idCronogramaSemillero = idCronogramaSemillero;
  }

  public String getActividad() {
    return actividad;
  }

  public void setActividad(String actividad) {
    this.actividad = actividad;
  }

  public String getObjetivo() {
    return objetivo;
  }

  public void setObjetivo(String objetivo) {
    this.objetivo = objetivo;
  }

  public String getResponsable() {
    return responsable;
  }

  public void setResponsable(String responsable) {
    this.responsable = responsable;
  }

  public String getProductoResultadoEsperado() {
    return productoResultadoEsperado;
  }

  public void setProductoResultadoEsperado(String productoResultadoEsperado) {
    this.productoResultadoEsperado = productoResultadoEsperado;
  }

  public Date getFechaCumplimiento() {
    return fechaCumplimiento;
  }

  public void setFechaCumplimiento(Date fechaCumplimiento) {
    this.fechaCumplimiento = fechaCumplimiento;
  }

  public String getArchivoEvidenciaFoto() {
    return archivoEvidenciaFoto;
  }

  public void setArchivoEvidenciaFoto(String archivoEvidenciaFoto) {
    this.archivoEvidenciaFoto = archivoEvidenciaFoto;
  }

  public String getArchivoEvidencia() {
    return archivoEvidencia;
  }

  public void setArchivoEvidencia(String archivoEvidencia) {
    this.archivoEvidencia = archivoEvidencia;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
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

  public UsuarioRol getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(UsuarioRol usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
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

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public SemillerosImplementacion getSemillerosImplementacion() {
    return semillerosImplementacion;
  }

  public void setSemillerosImplementacion(SemillerosImplementacion semillerosImplementacion) {
    this.semillerosImplementacion = semillerosImplementacion;
  }

  public SemilleroProyecto getSemilleroProyecto() {
    return semilleroProyecto;
  }

  public void setSemilleroProyecto(SemilleroProyecto semilleroProyecto) {
    this.semilleroProyecto = semilleroProyecto;
  }

  public String getArchivoEvidenciaFotoOriginal() {
    return archivoEvidenciaFotoOriginal;
  }

  public void setArchivoEvidenciaFotoOriginal(String archivoEvidenciaFotoOriginal) {
    this.archivoEvidenciaFotoOriginal = archivoEvidenciaFotoOriginal;
  }

  public String getArchivoEvidenciaOriginal() {
    return archivoEvidenciaOriginal;
  }

  public void setArchivoEvidenciaOriginal(String archivoEvidenciaOriginal) {
    this.archivoEvidenciaOriginal = archivoEvidenciaOriginal;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCronogramaSemillero != null ? idCronogramaSemillero.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CronogramaSemillero)) {
      return false;
    }
    CronogramaSemillero other = (CronogramaSemillero) object;
    if ((this.idCronogramaSemillero == null && other.idCronogramaSemillero != null) || (this.idCronogramaSemillero != null && !this.idCronogramaSemillero.equals(other.idCronogramaSemillero))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.CronogramaSemillero[ idCronogramaSemillero=" + idCronogramaSemillero + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (this.idCronogramaSemillero == null) {
      return null;
    }

    return null;
  }

}
