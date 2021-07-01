package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "NIVACA_VICIN_DINAE")
@NamedQueries({
  @NamedQuery(name = "VistaFormacionFuncionario.findAllByIdentificacion", query = "SELECT f FROM VistaFormacionFuncionario f WHERE f.identificacion = :identificacion")})
public class VistaFormacionFuncionario implements Serializable {

  private static final long serialVersionUID = 1L;

  /*@Id
    @Column(name = "ID_FORMACION_INV")
    private Long idVistaFuncionario;*/
  @Id
  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "TITULO")
  private String titulo;

  @Column(name = "NIVEL_ACADEMICO")
  private String nivelAcademico;

  @Column(name = "FECHA_TERMINO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaTermino;

  public VistaFormacionFuncionario() {
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getNivelAcademico() {
    return nivelAcademico;
  }

  public void setNivelAcademico(String nivelAcademico) {
    this.nivelAcademico = nivelAcademico;
  }

  public Date getFechaTermino() {
    return fechaTermino;
  }

  public void setFechaTermino(Date fechaTermino) {
    this.fechaTermino = fechaTermino;
  }

}
