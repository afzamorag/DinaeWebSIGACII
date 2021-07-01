package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@Entity
@Table(name = "SALIDA_BUSQUEDA_INVESTI_SIAT")
@NamedQueries({
  @NamedQuery(name = "SalidaBusquedaInvestigadorSiat.findAllPorSesion", query = "SELECT new co.gov.policia.dinae.dto.InvestigadorDTO( i.id, i.idTipoVincula, i.nombreTipoVincula, i.grado, i.identifica, i.nombresApellidos, i.correo, i.origen ) FROM SalidaBusquedaInvestigadorSiat i WHERE i.sesion = :sesion"),})
public class SalidaBusquedaInvestigadorSiat implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "SESION")
  private String sesion;

  @Column(name = "IDENTIFICA")
  private String identifica;

  @Column(name = "ORIGEN")
  private String origen;

  @Column(name = "ID_TIPO_VINCULA")
  private Long idTipoVincula;

  @Column(name = "NOMBRE_TIPO_VINCULA")
  private String nombreTipoVincula;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "NOMBRES_APELLIDOS")
  private String nombresApellidos;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "TELEFONO")
  private String telefono;

  public SalidaBusquedaInvestigadorSiat() {
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sesion != null ? sesion.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SalidaBusquedaInvestigadorSiat)) {
      return false;
    }
    SalidaBusquedaInvestigadorSiat other = (SalidaBusquedaInvestigadorSiat) object;
    if ((this.sesion == null && other.sesion != null) || (this.sesion != null && !this.sesion.equals(other.sesion))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.SalidaBusquedaInvestigadorSiat[ sesion=" + sesion + " ]";
  }

  public String getSesion() {
    return sesion;
  }

  public void setSesion(String sesion) {
    this.sesion = sesion;
  }

  public String getIdentifica() {
    return identifica;
  }

  public void setIdentifica(String identifica) {
    this.identifica = identifica;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public Long getIdTipoVincula() {
    return idTipoVincula;
  }

  public void setIdTipoVincula(Long idTipoVincula) {
    this.idTipoVincula = idTipoVincula;
  }

  public String getNombreTipoVincula() {
    return nombreTipoVincula;
  }

  public void setNombreTipoVincula(String nombreTipoVincula) {
    this.nombreTipoVincula = nombreTipoVincula;
  }

  public String getGrado() {
    return grado;
  }

  public void setGrado(String grado) {
    this.grado = grado;
  }

  public String getNombresApellidos() {
    return nombresApellidos;
  }

  public void setNombresApellidos(String nombresApellidos) {
    this.nombresApellidos = nombresApellidos;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
