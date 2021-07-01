package co.gov.policia.dinae.dto;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.EventosCapacitacionSemillero;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class EventosCapacitacionSemilleroDTO implements Serializable, IDataModel {

  private Long idEventCapaSemillero;

  private String nombreEvento;

  private String lugarEvento;

  private Date fechaInicioEvento;

  private Date fechaFinalizacionEvento;

  private Long idCiudad;

  private String claseTemaPonencia;

  private Long modalidadParticipacion;

  private Short numeroOficiales;

  private Short numeroSuboficiales;

  private Short numeroEstudiantes;

  private Short numeroNoUniformados;

  private String archivoEvidenciaDocumental;

  private String archivoEvidenciaFotog;

  private String archivoEvidenciaDocumentalOriginal;

  private String archivoEvidenciaFotogOriginal;

  private Date fechaCreacion;

  private String maquina;

  private Date fechaActualizacion;

  private String maquinaActualiza;

  private Long idUsuarioRol;

  private Long idUsuarioRolActualiza;

  private Long idSemillero;

  private String modalidadParticpacionValor;

  public EventosCapacitacionSemilleroDTO() {
  }

  public EventosCapacitacionSemilleroDTO(EventosCapacitacionSemillero eventoSemilleroProyecto) {
    this.idEventCapaSemillero = eventoSemilleroProyecto.getIdEventCapaSemillero();
    this.nombreEvento = eventoSemilleroProyecto.getNombreEvento();
    this.lugarEvento = eventoSemilleroProyecto.getLugarEvento();
    this.fechaInicioEvento = eventoSemilleroProyecto.getFechaInicioEvento();
    this.fechaFinalizacionEvento = eventoSemilleroProyecto.getFechaFinalizacionEvento();
    this.idCiudad = eventoSemilleroProyecto.getIdCiudad();
    this.modalidadParticipacion = eventoSemilleroProyecto.getModalidadParticipacion() == null ? null : eventoSemilleroProyecto.getModalidadParticipacion().getIdConstantes();
    this.numeroOficiales = eventoSemilleroProyecto.getNumeroOficiales();
    this.numeroSuboficiales = eventoSemilleroProyecto.getNumeroSuboficiales();
    this.numeroEstudiantes = eventoSemilleroProyecto.getNumeroEstudiantes();
    this.numeroNoUniformados = eventoSemilleroProyecto.getNumeroNoUniformados();
    this.archivoEvidenciaDocumental = eventoSemilleroProyecto.getArchivoEvidenciaDocumental();
    this.archivoEvidenciaFotog = eventoSemilleroProyecto.getArchivoEvidenciaFotog();
    this.fechaCreacion = eventoSemilleroProyecto.getFechaCreacion();
    this.maquina = eventoSemilleroProyecto.getMaquina();
    this.fechaActualizacion = eventoSemilleroProyecto.getFechaActualizacion();
    this.maquinaActualiza = eventoSemilleroProyecto.getMaquinaActualiza();
    this.idUsuarioRol = eventoSemilleroProyecto.getUsuarioRol().getIdUsuarioRol();
    this.idUsuarioRolActualiza = (eventoSemilleroProyecto.getUsuarioRolActualiza() == null) ? null : eventoSemilleroProyecto.getUsuarioRolActualiza().getIdUsuarioRol();
    this.idSemillero = eventoSemilleroProyecto.getSemilleroInvestigacion().getIdSemillero();
    this.modalidadParticpacionValor = (eventoSemilleroProyecto.getModalidadParticipacion() != null) ? eventoSemilleroProyecto.getModalidadParticipacion().getValor() : null;
    this.claseTemaPonencia = eventoSemilleroProyecto.getClaseTemaPonencia();
    this.archivoEvidenciaDocumentalOriginal = eventoSemilleroProyecto.getArchivoEvidenciaDocumentalOriginal();
    this.archivoEvidenciaFotogOriginal = eventoSemilleroProyecto.getArchivoEvidenciaFotogOriginal();
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

  public Long getModalidadParticipacion() {
    return modalidadParticipacion;
  }

  public void setModalidadParticipacion(Long modalidadParticipacion) {
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

  public Long getIdUsuarioRol() {
    return idUsuarioRol;
  }

  public void setIdUsuarioRol(Long idUsuarioRol) {
    this.idUsuarioRol = idUsuarioRol;
  }

  public Long getIdUsuarioRolActualiza() {
    return idUsuarioRolActualiza;
  }

  public void setIdUsuarioRolActualiza(Long idUsuarioRolActualiza) {
    this.idUsuarioRolActualiza = idUsuarioRolActualiza;
  }

  public Long getIdSemillero() {
    return idSemillero;
  }

  public void setIdSemillero(Long idSemillero) {
    this.idSemillero = idSemillero;
  }

  public String getModalidadParticpacionValor() {
    return modalidadParticpacionValor;
  }

  public void setModalidadParticpacionValor(String modalidadParticpacionValor) {
    this.modalidadParticpacionValor = modalidadParticpacionValor;
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
  public String getLlaveModel() {
    if (this.idEventCapaSemillero == null) {
      return null;
    }

    return this.idEventCapaSemillero.toString();
  }

}
