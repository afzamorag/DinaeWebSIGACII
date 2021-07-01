package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author RafaelGomez
 */
@Entity
@Table(name = "PROGRAMAS_ESCUELAS")
@NamedQueries({
  @NamedQuery(name = "ProgramasEscuelas.findAll", query = "SELECT p FROM ProgramasEscuelas p"),
  @NamedQuery(name = "ProgramasEscuelas.findByCodEscuela", query = "SELECT p FROM ProgramasEscuelas p WHERE p.codEscuela = :codEscuela"),
  @NamedQuery(name = "ProgramasEscuelas.findByDescripcionDependencia", query = "SELECT p FROM ProgramasEscuelas p WHERE p.descripcionDependencia = :descripcionDependencia"),
  @NamedQuery(name = "ProgramasEscuelas.findByIdPrograma", query = "SELECT p FROM ProgramasEscuelas p WHERE p.idPrograma = :idPrograma"),
  @NamedQuery(name = "ProgramasEscuelas.findByDescripcion", query = "SELECT p FROM ProgramasEscuelas p WHERE p.descripcion = :descripcion")})
public class ProgramasEscuelas implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "COD_ESCUELA")
  private Long codEscuela;

  @NotNull
  @Size(min = 1, max = 60)
  @Column(name = "DESCRIPCION_DEPENDENCIA")
  private String descripcionDependencia;

  @NotNull
  @Id
  @Column(name = "ID_PROGRAMA")
  private Long idPrograma;

  @NotNull
  @Size(min = 1, max = 140)
  @Column(name = "DESCRIPCION")
  private String descripcion;

  public ProgramasEscuelas() {
  }

  public Long getCodEscuela() {
    return codEscuela;
  }

  public void setCodEscuela(Long codEscuela) {
    this.codEscuela = codEscuela;
  }

  public String getDescripcionDependencia() {
    return descripcionDependencia;
  }

  public void setDescripcionDependencia(String descripcionDependencia) {
    this.descripcionDependencia = descripcionDependencia;
  }

  public Long getIdPrograma() {
    return idPrograma;
  }

  public void setIdPrograma(Long idPrograma) {
    this.idPrograma = idPrograma;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
