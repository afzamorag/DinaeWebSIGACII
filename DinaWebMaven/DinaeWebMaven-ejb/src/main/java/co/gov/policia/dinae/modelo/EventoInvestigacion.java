package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "EventoInvestigacion.findAll", query = "SELECT u FROM EventoInvestigacion u"),
  @NamedQuery(name = "EventoInvestigacion.findAllDTO", query = "SELECT NEW co.gov.policia.dinae.dto.EventoInvestigacionDTO( u.idEventoInvestigacion, u.nombre, u.fechaInicio, u.fechaFin, u.tipoEvento.valor ) FROM EventoInvestigacion u"),
  @NamedQuery(name = "EventoInvestigacion.findAllDTOPorTipoEvento", query = "SELECT NEW co.gov.policia.dinae.dto.EventoInvestigacionDTO( u.idEventoInvestigacion, u.nombre, u.fechaInicio, u.fechaFin, u.tipoEvento.valor ) FROM EventoInvestigacion u WHERE u.tipoEvento.idConstantes = :idTipoEvento"),})
@Table(name = "EVENTO_INVESTIGACION")
public class EventoInvestigacion implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EventoInvestigacion_seq_gen")
  @SequenceGenerator(name = "EventoInvestigacion_seq_gen", sequenceName = "SEC_EVENTO_INVESTIGACION", allocationSize = 1)
  @Column(name = "ID_EVENTO_INVESTIGACION")
  private Long idEventoInvestigacion;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "FECHA_ACTUALIZA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualiza;

  @Column(name = "USUARIO")
  private String usuarioRegistra;

  @Column(name = "MAQUINA")
  private String maquina;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_TIPO_EVENTO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes tipoEvento;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "MODALIDAD_VIRTUAL")
  private Character modalidadVirtual;

  @Column(name = "MODALIDAD_PRESENCIAL")
  private Character modalidadPresencial;

  @Column(name = "FECHA_INICIO")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;

  @Column(name = "LUGAR")
  private String lugar;

  @Column(name = "CIUDAD")
  private String ciudad;

  @Column(name = "NOMBRE_ARCHIVO")
  private String nombreArchivo;

  @Column(name = "NOMBRE_ARCHIVO_FISICO")
  private String nombreArchivoFisico;

  @Transient
  private List<String> opcionesSeleccionadaModalidad;

  @Transient
  private String modalidad;

  public EventoInvestigacion() {

    opcionesSeleccionadaModalidad = new ArrayList<String>();

  }

  public Long getIdEventoInvestigacion() {
    return idEventoInvestigacion;
  }

  public void setIdEventoInvestigacion(Long idEventoInvestigacion) {
    this.idEventoInvestigacion = idEventoInvestigacion;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Date getFechaActualiza() {
    return fechaActualiza;
  }

  public void setFechaActualiza(Date fechaActualiza) {
    this.fechaActualiza = fechaActualiza;
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

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public Constantes getTipoEvento() {
    return tipoEvento;
  }

  public void setTipoEvento(Constantes tipoEvento) {
    this.tipoEvento = tipoEvento;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getNombreArchivoFisico() {
    return nombreArchivoFisico;
  }

  public void setNombreArchivoFisico(String nombreArchivoFisico) {
    this.nombreArchivoFisico = nombreArchivoFisico;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Character getModalidadVirtual() {
    return modalidadVirtual;
  }

  public void setModalidadVirtual(Character modalidadVirtual) {
    this.modalidadVirtual = modalidadVirtual;

    if (modalidadVirtual != null && 'S' == modalidadVirtual) {

      if (this.modalidad == null) {

        this.modalidad = "Virtual";

      } else {

        this.modalidad = modalidad.concat(" - Virtual");

      }

      getOpcionesSeleccionadaModalidad().add("V");
    }
  }

  public Character getModalidadPresencial() {
    return modalidadPresencial;
  }

  public void setModalidadPresencial(Character modalidadPresencial) {
    this.modalidadPresencial = modalidadPresencial;

    if (modalidadPresencial != null && 'S' == modalidadPresencial) {

      if (this.modalidad == null) {

        this.modalidad = "Presencial";

      } else {

        this.modalidad = modalidad.concat(" - Presencial");

      }

      getOpcionesSeleccionadaModalidad().add("P");
    }

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

  public String getLugar() {
    return lugar;
  }

  public void setLugar(String lugar) {
    this.lugar = lugar;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public List<String> getOpcionesSeleccionadaModalidad() {
    if (opcionesSeleccionadaModalidad == null) {
      opcionesSeleccionadaModalidad = new ArrayList<String>();
    }
    return opcionesSeleccionadaModalidad;
  }

  public void setOpcionesSeleccionadaModalidad(List<String> opcionesSeleccionadaModalidad) {
    this.opcionesSeleccionadaModalidad = opcionesSeleccionadaModalidad;
  }

  public String getModalidad() {

    if (modalidad != null && modalidad.startsWith(" - ")) {
      modalidad = this.modalidad.replaceFirst(" - ", "");
    }
    return modalidad;
  }

  public void setModalidad(String modalidad) {
    this.modalidad = modalidad;
  }

  @Override
  public String getLlaveModel() {
    return String.valueOf(idEventoInvestigacion);
  }
}
