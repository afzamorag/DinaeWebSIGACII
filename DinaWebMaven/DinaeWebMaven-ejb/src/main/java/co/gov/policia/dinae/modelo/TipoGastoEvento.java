package co.gov.policia.dinae.modelo;

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

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/03
 */
@Entity
@Table(name = "TIPO_GASTO_EVENTO")
@NamedQueries({
  @NamedQuery(name = "TipoGastoEvento.findTipoGastoEventoByNombreTipoGastoAndTipo", query = "SELECT t FROM TipoGastoEvento t where t.tipoGasto.valor = :tipoGastoValor AND t.tipoGasto.tipo = :tipoGastoTipo"),
  @NamedQuery(name = "TipoGastoEvento.findTipoGastoEventioByEvento", query = "SELECT t FROM TipoGastoEvento t where t.eventoProyecto.idEventoProyecto = :idEventoProyecto")})
public class TipoGastoEvento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoGastoEventoSeqGen")
  @SequenceGenerator(name = "TipoGastoEventoSeqGen", sequenceName = "SEC_TIPO_GASTO_EVENTO", allocationSize = 1)
  @Column(name = "ID_GASTO_EVENTO")
  private Long idGastoEvento;

  @JoinColumn(name = "ID_TIPO_GASTO", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne
  private Constantes tipoGasto;

  @JoinColumn(name = "ID_EVENTO_PROYECTO", referencedColumnName = "ID_EVENTO_PROYECTO")
  @ManyToOne(fetch = FetchType.LAZY)
  private EventoProyecto eventoProyecto;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne
  private UsuarioRol usuarioRol;

  @Column(name = "MAQUINA")
  private String maquina;

  public Long getIdGastoEvento() {
    return idGastoEvento;
  }

  public void setIdGastoEvento(Long idGastoEvento) {
    this.idGastoEvento = idGastoEvento;
  }

  public Constantes getTipoGasto() {
    return tipoGasto;
  }

  public void setTipoGasto(Constantes idTipoGasto) {
    this.tipoGasto = idTipoGasto;
  }

  public EventoProyecto getEventoProyecto() {
    return eventoProyecto;
  }

  public void setEventoProyecto(EventoProyecto eventoProyecto) {
    this.eventoProyecto = eventoProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public UsuarioRol getUsuarioRol() {
    return usuarioRol;
  }

  public void setUsuarioRol(UsuarioRol usuarioRol) {
    this.usuarioRol = usuarioRol;
  }

  public String getMaquina() {
    return maquina;
  }

  public void setMaquina(String maquina) {
    this.maquina = maquina;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idGastoEvento != null ? idGastoEvento.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TipoGastoEvento)) {
      return false;
    }
    TipoGastoEvento other = (TipoGastoEvento) object;
    if ((this.idGastoEvento == null && other.idGastoEvento != null) || (this.idGastoEvento != null && !this.idGastoEvento.equals(other.idGastoEvento))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.TipoGastoEvento[ id=" + idGastoEvento + " ]";
  }

}
