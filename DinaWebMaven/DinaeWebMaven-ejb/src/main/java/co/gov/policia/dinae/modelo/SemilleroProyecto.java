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
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SEMILLERO_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "SemilleroProyecto.findAll", query = "SELECT s FROM SemilleroProyecto s"),
  @NamedQuery(name = "SemilleroProyecto.EliminarSemilleroInvesPorIdSemillosInvestigacion", query = "DELETE FROM SemilleroProyecto s WHERE s.idSemilleroProyecto = :idSemilleroProyecto"),
  @NamedQuery(name = "SemilleroProyectoDTO.findPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroProyectoDTO( s.idSemilleroProyecto, s.semilleroInvestigacion.idSemillero, s.semilleroInvestigacion.nombre, s.aporteInvestigacion ) FROM SemilleroProyecto s WHERE s.proyecto.idProyecto = :idProyecto AND s.unidadPolicial IS NULL"),
  @NamedQuery(name = "SemilleroProyectoDTO.findPorProyectoYuniadadPolicial", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroProyectoDTO( s.idSemilleroProyecto, s.aporteInvestigacion, s.unidadPolicial.nombre, s.unidadPolicial.idUnidadPolicial ) FROM SemilleroProyecto s WHERE s.proyecto.idProyecto = :idProyecto AND s.unidadPolicial IS NOT NULL"),
  @NamedQuery(name = "SemilleroProyecto.findAllBySemilleroInvestigacion", query = "SELECT s FROM SemilleroProyecto s WHERE s.semilleroInvestigacion.idSemillero = :idSemillero AND ( s.proyecto.codigoProyecto LIKE 'VIC%' OR s.proyecto.codigoProyecto LIKE 'CONV%' ) ")
})
public class SemilleroProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SemilleroProyecto_seq_gen")
  @SequenceGenerator(name = "SemilleroProyecto_seq_gen", sequenceName = "SEC_SEMILLERO_PROYECTO", allocationSize = 1)
  @Column(name = "ID_SEMILLERO_PROYECTO")
  private Long idSemilleroProyecto;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @Column(name = "USUARIO")
  private String usuario;

  @JoinColumn(name = "ID_SEMILLERO", referencedColumnName = "ID_SEMILLERO")
  @ManyToOne(optional = false)
  private SemilleroInvestigacion semilleroInvestigacion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false)
  private Proyecto proyecto;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @Column(name = "APORTE_INVESTIGACION")
  private String aporteInvestigacion;

  public SemilleroProyecto() {
  }

  public SemilleroProyecto(Long idSemilleroProyecto) {
    this.idSemilleroProyecto = idSemilleroProyecto;
  }

  public Long getIdSemilleroProyecto() {
    return idSemilleroProyecto;
  }

  public void setIdSemilleroProyecto(Long idSemilleroProyecto) {
    this.idSemilleroProyecto = idSemilleroProyecto;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public SemilleroInvestigacion getSemilleroInvestigacion() {
    return semilleroInvestigacion;
  }

  public void setSemilleroInvestigacion(SemilleroInvestigacion semilleroInvestigacion) {
    this.semilleroInvestigacion = semilleroInvestigacion;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idSemilleroProyecto != null ? idSemilleroProyecto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SemilleroProyecto)) {
      return false;
    }
    SemilleroProyecto other = (SemilleroProyecto) object;
    if ((this.idSemilleroProyecto == null && other.idSemilleroProyecto != null) || (this.idSemilleroProyecto != null && !this.idSemilleroProyecto.equals(other.idSemilleroProyecto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.SemilleroProyecto[ idSemilleroProyecto=" + idSemilleroProyecto + " ]";
  }

  public String getAporteInvestigacion() {
    return aporteInvestigacion;
  }

  public void setAporteInvestigacion(String aporteInvestigacion) {
    this.aporteInvestigacion = aporteInvestigacion;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

}
