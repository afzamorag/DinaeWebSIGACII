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
@Table(name = "INSTITUCIONES_PROYECTO")
@NamedQueries({
  @NamedQuery(name = "InstitucionesProyecto.findAll", query = "SELECT s FROM InstitucionesProyecto s"),
  @NamedQuery(name = "InstitucionesProyectoDTO.findPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.InstitucionesProyectoDTO( s.idInstitucionesProyecto, s.aporteInvestigicacion, s.institucion.idConstantes, s.institucion.valor, s.proyecto.idProyecto, s.valorOtroTipo ) FROM InstitucionesProyecto s  WHERE s.proyecto.idProyecto = :idProyecto"),
  @NamedQuery(name = "InstitucionesProyecto.EliminarInstitucionesProyectoPorIdInstitucionesProyecto", query = "DELETE FROM InstitucionesProyecto s WHERE s.idInstitucionesProyecto = :idInstitucionesProyecto")
})
public class InstitucionesProyecto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InstitucionesProyecto_seq_gen")
  @SequenceGenerator(name = "InstitucionesProyecto_seq_gen", sequenceName = "SEC_INSTITUCIONES_PROYECTO", allocationSize = 1)
  @Column(name = "ID_INSTITUCION_PROYECTO")
  private Long idInstitucionesProyecto;

  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

  @JoinColumn(name = "ID_USUARIO_ROL", referencedColumnName = "ID_USUARIO_ROL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UsuarioRol usuarioRol;

  @JoinColumn(name = "ID_INSTITUCION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Constantes institucion;

  @Column(name = "APORTE_INVESTIGACION")
  private String aporteInvestigicacion;

  @Column(name = "VALOR_OTRO_TIPO")
  private String valorOtroTipo;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Proyecto proyecto;

  public InstitucionesProyecto() {
  }

  public InstitucionesProyecto(Long idInstitucionesProyecto) {
    this.idInstitucionesProyecto = idInstitucionesProyecto;
  }

  public Long getIdInstitucionesProyecto() {
    return idInstitucionesProyecto;
  }

  public void setIdInstitucionesProyecto(Long idInstitucionesProyecto) {
    this.idInstitucionesProyecto = idInstitucionesProyecto;
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

  public Constantes getInstitucion() {
    return institucion;
  }

  public void setInstitucion(Constantes institucion) {
    this.institucion = institucion;
  }

  public String getAporteInvestigicacion() {
    return aporteInvestigicacion;
  }

  public void setAporteInvestigicacion(String aporteInvestigicacion) {
    this.aporteInvestigicacion = aporteInvestigicacion;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

  public String getValorOtroTipo() {
    return valorOtroTipo;
  }

  public void setValorOtroTipo(String valorOtroTipo) {
    this.valorOtroTipo = valorOtroTipo;
  }

}
