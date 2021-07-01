package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "EVENTO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "EventoProyecto.findAll", query = "SELECT e FROM EventoProyecto e"),
  @NamedQuery(name = "EventoProyecto.findAllByProyecto", query = "SELECT e FROM EventoProyecto e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EventoProyecto.findAllByPlanTrabajo", query = "SELECT e FROM EventoProyecto e WHERE e.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo"),
  @NamedQuery(name = "EventoProyectoDTO.findAllByProyectoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.EventoProyectoDTO(e.idEventoProyecto, e.costo, e.tituloEvento, e.objetivoEvento, e.codigoCiudad, e.nombreCiudad, e.fechaRegistro, e.fuenteProyecto.idFuenteProyecto, e.fuenteProyecto.nombreFuente, e.proyecto.idProyecto, e.proyecto.tituloPropuesto, e.usuarioRol.idUsuarioRol) FROM EventoProyecto e WHERE e.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "EventoProyectoDTO.findAllByPlanTrabajoDTO", query = "SELECT NEW co.gov.policia.dinae.dto.EventoProyectoDTO(e.idEventoProyecto, e.costo, e.tituloEvento, e.objetivoEvento, e.codigoCiudad, e.nombreCiudad, e.fechaRegistro, e.fuenteProyecto.idFuenteProyecto, e.fuenteProyecto.nombreFuente, e.usuarioRol.idUsuarioRol) FROM EventoProyecto e WHERE e.fuenteProyecto.planTrabajoImplementacion.idPlanTrabajo = :idPlanTrabajo")})
public class EventoProyecto implements IDataModel, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EventoProyectoSeqGen")
  @SequenceGenerator(name = "EventoProyectoSeqGen", sequenceName = "SEC_EVENTO_PROYECTO", allocationSize = 1)
  @Column(name = "ID_EVENTO_PROYECTO")
  private Long idEventoProyecto;

  @Column(name = "COSTO")
  private BigDecimal costo;

  @Column(name = "TITULO_EVENTO")
  private String tituloEvento;

  @Column(name = "OBJETIVO_EVENTO")
  private String objetivoEvento;

  @Column(name = "CODIGO_CIUDAD")
  private String codigoCiudad;

  @Column(name = "NOMBRE_CIUDAD")
  private String nombreCiudad;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_FUENTE_PROYECTO", referencedColumnName = "ID_FUENTE_PROYECTO")
  @ManyToOne(optional = false)
  private FuenteProyecto fuenteProyecto;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoProyecto")
  private List<TipoGastoEvento> gastoEventoList;

  @Transient
  private String tipoGastoEvento;

  @Transient
  private boolean seleccionable;

  public EventoProyecto() {
  }

  public EventoProyecto(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public EventoProyecto(Long idEventoProyecto, BigDecimal costo) {
    this.idEventoProyecto = idEventoProyecto;
    this.costo = costo;
  }

  public Long getIdEventoProyecto() {
    return idEventoProyecto;
  }

  public void setIdEventoProyecto(Long idEventoProyecto) {
    this.idEventoProyecto = idEventoProyecto;
  }

  public BigDecimal getCosto() {
    return costo;
  }

  public void setCosto(BigDecimal costo) {
    this.costo = costo;
  }

  public String getTituloEvento() {
    return tituloEvento;
  }

  public void setTituloEvento(String tituloEvento) {
    this.tituloEvento = tituloEvento;
  }

  public String getObjetivoEvento() {
    return objetivoEvento;
  }

  public void setObjetivoEvento(String objetivoEvento) {
    this.objetivoEvento = objetivoEvento;
  }

  public FuenteProyecto getFuenteProyecto() {
    return fuenteProyecto;
  }

  public void setFuenteProyecto(FuenteProyecto fuenteProyecto) {
    this.fuenteProyecto = fuenteProyecto;
  }

  public List<TipoGastoEvento> getGastoEventoList() {
    return gastoEventoList;
  }

  public void setGastoEventoList(List<TipoGastoEvento> gastoEventoList) {
    this.gastoEventoList = gastoEventoList;
  }

  public String getCodigoCiudad() {
    return codigoCiudad;
  }

  public void setCodigoCiudad(String codigoCiudad) {
    this.codigoCiudad = codigoCiudad;
  }

  public String getNombreCiudad() {
    return nombreCiudad;
  }

  public void setNombreCiudad(String nombreCiudad) {
    this.nombreCiudad = nombreCiudad;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getTipoGastoEvento() {
    return tipoGastoEvento;
  }

  public void setTipoGastoEvento(String tipoGastoEvento) {
    this.tipoGastoEvento = tipoGastoEvento;
  }

  public boolean isSeleccionable() {
    return seleccionable;
  }

  public void setSeleccionable(boolean seleccionable) {
    this.seleccionable = seleccionable;
  }

  public String getTipoGasto() {
    String retorno = "";
    for (TipoGastoEvento tipoGasto : gastoEventoList) {
      retorno = retorno.concat(tipoGasto.getTipoGasto().getValor() + "\n");
    }
    return retorno;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idEventoProyecto != null ? idEventoProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EventoProyecto)) {
      return false;
    }
    EventoProyecto other = (EventoProyecto) object;
    if ((this.idEventoProyecto == null && other.idEventoProyecto != null) || (this.idEventoProyecto != null && !this.idEventoProyecto.equals(other.idEventoProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.EventoProyecto[ idEventoProyecto=" + idEventoProyecto + " ]";
  }

  @Override
  public String getLlaveModel() {
    if (idEventoProyecto == null) {
      return null;
    }

    return idEventoProyecto.toString();
  }

}
