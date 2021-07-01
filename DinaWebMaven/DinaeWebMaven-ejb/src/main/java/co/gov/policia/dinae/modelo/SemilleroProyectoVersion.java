package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SEMILLERO_PROYECTO_VERSION")
@NamedQueries({
  @NamedQuery(name = "SemilleroProyectoVersionSemilleroProyectoDTO.findPorProyectoYuniadadPolicial", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroProyectoDTO( s.idSemilleroProyecto, s.aporteInvestigacion, s.unidadPolicial.nombre, s.unidadPolicial.idUnidadPolicial ) From SemilleroProyectoVersion s WHERE s.proyectoVersion.idProyectoVersion = :idProyectoVersion AND s.unidadPolicial IS NOT NULL"),
  @NamedQuery(name = "SemilleroProyectoVersionSemilleroProyectoDTO.findPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.SemilleroProyectoDTO( s.idSemilleroProyecto, s.semilleroInvestigacion.idSemillero, s.semilleroInvestigacion.nombre, s.aporteInvestigacion ) FROM SemilleroProyectoVersion s WHERE s.proyectoVersion.idProyectoVersion = :idProyectoVersion AND s.unidadPolicial IS NULL"),})
public class SemilleroProyectoVersion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
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

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO_VERSION")
  @ManyToOne(optional = false)
  private ProyectoVersion proyectoVersion;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  @Column(name = "APORTE_INVESTIGACION")
  private String aporteInvestigacion;

  public SemilleroProyectoVersion() {
  }

  public SemilleroProyectoVersion(Long idSemilleroProyecto) {
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

  public ProyectoVersion getProyectoVersion() {
    return proyectoVersion;
  }

  public void setProyectoVersion(ProyectoVersion proyectoVersion) {
    this.proyectoVersion = proyectoVersion;
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
