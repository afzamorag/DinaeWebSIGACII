package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.CronogramaSemillero;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class CronogramaSemilleroDTO implements Serializable, IDataModel {

  private Long idCronogramaSemillero;

  private String actividad;

  private String objetivo;

  private String responsable;

  private String productoResultadoEsperado;

  private Date fechaCumplimiento;

  private String archivoEvidenciaFoto;

  private String archivoEvidencia;

  private String archivoEvidenciaFotoOriginal;

  private String archivoEvidenciaOriginal;

  private Date fechaCreacion;

  private String maquina;

  private Date fechaActualizacion;

  private String maquinaActualiza;

  private Long usuarioRol;

  private Long usuarioRolActualiza;

  private Long semilleroInvestigacion;

  private Long semillerosImplementacion;

  private Long semilleroProyecto;

  public CronogramaSemilleroDTO() {
  }

  public CronogramaSemilleroDTO(CronogramaSemillero cronograma) {

    this.idCronogramaSemillero = cronograma.getIdCronogramaSemillero();
    this.actividad = cronograma.getActividad();
    this.objetivo = cronograma.getObjetivo();
    this.responsable = cronograma.getResponsable();
    this.productoResultadoEsperado = cronograma.getProductoResultadoEsperado();
    this.fechaCumplimiento = cronograma.getFechaCumplimiento();
    this.archivoEvidenciaFoto = cronograma.getArchivoEvidenciaFoto();
    this.archivoEvidencia = cronograma.getArchivoEvidencia();
    this.archivoEvidenciaFotoOriginal = cronograma.getArchivoEvidenciaFotoOriginal();
    this.archivoEvidenciaOriginal = cronograma.getArchivoEvidenciaOriginal();
    this.fechaCreacion = cronograma.getFechaCreacion();
    this.maquina = cronograma.getMaquina();
    this.fechaActualizacion = cronograma.getFechaActualizacion();
    this.maquinaActualiza = cronograma.getMaquinaActualiza();
    this.usuarioRol = cronograma.getUsuarioRol().getIdUsuarioRol();
    this.usuarioRolActualiza = (cronograma.getUsuarioRolActualiza() == null) ? null : cronograma.getUsuarioRolActualiza().getIdUsuarioRol();
    this.semilleroInvestigacion = (cronograma.getSemilleroInvestigacion() == null) ? null : cronograma.getSemilleroInvestigacion().getIdSemillero();
    this.semillerosImplementacion = (cronograma.getSemilleroProyecto() == null) ? null : cronograma.getSemilleroProyecto().getIdSemilleroProyecto();
    this.semilleroProyecto = (cronograma.getSemilleroProyecto() == null) ? null : cronograma.getSemilleroProyecto().getIdSemilleroProyecto();
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

  public Long getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(Long usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Long getUsuarioRolActualiza() {
    return usuarioRolActualiza;
  }

  public void setUsuarioRolActualiza(Long usuarioRolActualiza) {
    this.usuarioRolActualiza = usuarioRolActualiza;
  }

  public Long getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(Long semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public Long getSemillerosImplementacion() {
    return semillerosImplementacion;
  }

  public void setSemillerosImplementacion(Long semillerosImplementacion) {
    this.semillerosImplementacion = semillerosImplementacion;
  }

  public Long getSemilleroProyecto() {
    return semilleroProyecto;
  }

  public void setSemilleroProyecto(Long semilleroProyecto) {
    this.semilleroProyecto = semilleroProyecto;
  }

  @Override
  public String getLlaveModel() {
    if (idCronogramaSemillero == null) {
      return null;
    }

    return idCronogramaSemillero.toString();
  }

}
