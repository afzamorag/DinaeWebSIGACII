package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.Objects;
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

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "FUNCIONARIO_NECESIDAD")
@NamedQueries({
  @NamedQuery(name = "FuncionarioNecesidad.findAll", query = "SELECT f FROM FuncionarioNecesidad f"),
  @NamedQuery(name = "FuncionarioNecesidad.EliminarPorID", query = "DELETE FROM FuncionarioNecesidad f WHERE f.idFuncionarioNecesidad = :idFuncionarioNecesidad"),
  @NamedQuery(name = "FuncionarioNecesidad.findAllPorPropuestaNecesidad", query = "SELECT f FROM FuncionarioNecesidad f WHERE f.propuestaNecesidad.idPropuestaNecesidad = :idPropuestaNecesidad")})
public class FuncionarioNecesidad implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FuncionarioNecesidad_seq_gen")
  @SequenceGenerator(name = "FuncionarioNecesidad_seq_gen", sequenceName = "SEC_FUNCIONARIO_NECESIDAD", allocationSize = 1)
  @Column(name = "ID_FUNCIONARIO_NECESIDAD")
  private Long idFuncionarioNecesidad;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "CARGO")
  private String cargo;

  @JoinColumn(name = "ID_PROPUESTA_NECESIDAD", referencedColumnName = "ID_PROPUESTA_NECESIDAD")
  @ManyToOne(fetch = FetchType.LAZY)
  private PropuestaNecesidad propuestaNecesidad;

  public FuncionarioNecesidad() {
  }

  public FuncionarioNecesidad(Long idFuncionarioNecesidad) {
    this.idFuncionarioNecesidad = idFuncionarioNecesidad;
  }

  /**
   *
   * @param idFuncionarioNecesidad
   * @param identificacion
   * @param nombreCompleto
   * @param correo
   * @param grado
   * @param telefono
   * @param cargo
   */
  public FuncionarioNecesidad(Long idFuncionarioNecesidad, String identificacion, String nombreCompleto, String correo, String grado, String telefono, String cargo) {
    this.idFuncionarioNecesidad = idFuncionarioNecesidad;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.grado = grado;
    this.telefono = telefono;
    this.cargo = cargo;
  }

  public Long getIdFuncionarioNecesidad() {
    return idFuncionarioNecesidad;
  }

  public void setIdFuncionarioNecesidad(Long idFuncionarioNecesidad) {
    this.idFuncionarioNecesidad = idFuncionarioNecesidad;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idFuncionarioNecesidad != null ? idFuncionarioNecesidad.hashCode() : 0);
    return hash;
  }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FuncionarioNecesidad other = (FuncionarioNecesidad) obj;
        if (this.identificacion != null) {
            return this.getIdentificacion().equals(other.getIdentificacion());
        }
        if (!Objects.equals(this.idFuncionarioNecesidad, other.idFuncionarioNecesidad)) {
            return false;
        }
        if (!Objects.equals(this.propuestaNecesidad, other.propuestaNecesidad)) {
            return false;
        }
        return true;
    }



  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.FuncionarioNecesidad[ idFuncionarioNecesidad=" + idFuncionarioNecesidad + " ]";
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  @Override
  public String getLlaveModel() {

    if (idFuncionarioNecesidad == null) {
      return null;
    }

    return String.valueOf(idFuncionarioNecesidad);
  }

}
