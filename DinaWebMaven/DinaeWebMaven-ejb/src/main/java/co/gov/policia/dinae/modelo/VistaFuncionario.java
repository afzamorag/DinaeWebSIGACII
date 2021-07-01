package co.gov.policia.dinae.modelo;

import co.gov.policia.dinae.dto.AntiguedadDTO;
import co.gov.policia.dinae.interfaces.IDataModel;
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
import javax.persistence.Transient;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "VISTA_FUNCIONARIO")
@NamedQueries({
  @NamedQuery(name = "VistaFuncionario.findAll", query = "SELECT v FROM VistaFuncionario v"),
  @NamedQuery(name = "VistaFuncionario.findTodosCorreo", query = "SELECT DISTINCT v.correo FROM VistaFuncionario v WHERE v.correo IS NOT NULL "),
  @NamedQuery(name = "VistaFuncionario.findAllPorIdentificacion", query = "SELECT v FROM VistaFuncionario v WHERE v.identificacion = :identificacion"),
  @NamedQuery(name = "VistaFuncionario.findCorreoPorIdentificacion", query = "SELECT v.correo FROM VistaFuncionario v WHERE v.identificacion = :identificacion AND v.correo IS NOT NULL"),
  @NamedQuery(name = "VistaFuncionario.findCodigoUnidadLaborarPorIdentificacion", query = "SELECT v.codigoUnidadLaboral FROM VistaFuncionario v WHERE v.identificacion = :identificacion AND v.codigoUnidadLaboral IS NOT NULL"),
  @NamedQuery(name = "VistaFuncionario.findSiglaLaborandoPorIdentificacion", query = "SELECT v.siglaLaborando FROM VistaFuncionario v WHERE v.identificacion = :identificacion AND v.codigoUnidadLaboral IS NOT NULL")
})
public class VistaFuncionario implements Serializable, IDataModel {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID_VISTA_FUNCIONARIO")
  private Long idVistaFuncionario;

  @Column(name = "IDENTIFICACION")
  private String identificacion;

  @Column(name = "NOMBRE_COMPLETO")
  private String nombreCompleto;

  @Column(name = "CORREO")
  private String correo;

  @Column(name = "GRADO")
  private String grado;

  @Column(name = "CARGO")
  private String cargo;

  @Column(name = "NOMBRES")
  private String nombres;

  @Column(name = "APELLIDOS")
  private String apellidos;

  @Column(name = "COD_CARGO")
  private String codigoCargo;

  @Column(name = "DIREC_EMPLEA")
  private String direccionEmpleado;

  @Column(name = "DEPTO_RESIDE")
  private String departamentoReside;

  @Column(name = "COD_DEPTO_RESIDE")
  private String codigoDepartamentoReside;

  @Column(name = "CIUDAD_RESIDE")
  private String ciudadReside;

  @Column(name = "COD_CIU_RES")
  private String codigoCiudadReside;

  @Column(name = "SIGLA_LABORANDO")
  private String siglaLaborando;

  @Column(name = "COD_UNID_LAB")
  private String codigoUnidadLaboral;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "CIU_EXP_DOC")
  private String ciudadExpedicionDocumento;

  @Column(name = "COD_CIU_EXP_DOC")
  private String codigoCiudadExpedicionDocumento;

  @Column(name = "NUMERO_CELULAR")
  private String celular;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "COD_CATEG_EMP")
  private String codigoCategoriaEmpleado;

  @Column(name = "GRADO_ALFABETICO")
  private String gradoAlfabetico;

  @Column(name = "INGRESO_INSTITU")
  @Temporal(TemporalType.DATE)
  private Date ingresoInstitucion;
  
  @Column(name = "USUARIO_EMPRESARIAL")
  private String usuarioEmpresarial;
  
   @Column(name = "COD_UNID_LAB_REAL")
  private String unidadLaboraReal;

  @Transient
  private AntiguedadDTO antiguedadDTO;

  @Transient
  private UnidadPolicial unidadPolicial;

  /**
   * Este campo se utiliza para saber si la persona viene de vista funcionario o de la tabla investigador
   */
  @Transient
  private boolean informacionSIATH = true;

  @Transient
  private String origenSIAToInvestigador = "PLANTA";

  /**
   *
   */
  public VistaFuncionario() {

    informacionSIATH = true;
    origenSIAToInvestigador = "PLANTA";
  }

  /**
   *
   * @param idVistaFuncionario
   */
  public VistaFuncionario(Long idVistaFuncionario) {
    this.idVistaFuncionario = idVistaFuncionario;
  }

  /**
   *
   * @param idVistaFuncionario
   * @param identificacion
   * @param nombreCompleto
   * @param correo
   * @param grado
   */
  public VistaFuncionario(Long idVistaFuncionario, String identificacion, String nombreCompleto, String correo, String grado) {
    this.idVistaFuncionario = idVistaFuncionario;
    this.identificacion = identificacion;
    this.nombreCompleto = nombreCompleto;
    this.correo = correo;
    this.grado = grado;
  }

  public Long getIdVistaFuncionario() {
    return idVistaFuncionario;
  }

  public void setIdVistaFuncionario(Long idVistaFuncionario) {
    this.idVistaFuncionario = idVistaFuncionario;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idVistaFuncionario != null ? idVistaFuncionario.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof VistaFuncionario)) {
      return false;
    }
    VistaFuncionario other = (VistaFuncionario) object;
    if ((this.idVistaFuncionario == null && other.idVistaFuncionario != null) || (this.idVistaFuncionario != null && !this.idVistaFuncionario.equals(other.idVistaFuncionario))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return "co.gov.policia.dinae.modelo.VistaFuncionario[  idVistaFuncionario = " + idVistaFuncionario
            + "        identificacion = " + identificacion
            + "        nombreCompleto = " + nombreCompleto
            + "        correo = " + correo
            + "        grado = " + grado + " s]";
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getCodigoCargo() {
    return codigoCargo;
  }

  public void setCodigoCargo(String codigoCargo) {
    this.codigoCargo = codigoCargo;
  }

  public String getDireccionEmpleado() {
    return direccionEmpleado;
  }

  public void setDireccionEmpleado(String direccionEmpleado) {
    this.direccionEmpleado = direccionEmpleado;
  }

  public String getDepartamentoReside() {
    return departamentoReside;
  }

  public void setDepartamentoReside(String departamentoReside) {
    this.departamentoReside = departamentoReside;
  }

  public String getCodigoDepartamentoReside() {
    return codigoDepartamentoReside;
  }

  public void setCodigoDepartamentoReside(String codigoDepartamentoReside) {
    this.codigoDepartamentoReside = codigoDepartamentoReside;
  }

  public String getCiudadReside() {
    return ciudadReside;
  }

  public void setCiudadReside(String ciudadReside) {
    this.ciudadReside = ciudadReside;
  }

  public String getCodigoCiudadReside() {
    return codigoCiudadReside;
  }

  public void setCodigoCiudadReside(String codigoCiudadReside) {
    this.codigoCiudadReside = codigoCiudadReside;
  }

  public String getSiglaLaborando() {
    return siglaLaborando;
  }

  public void setSiglaLaborando(String siglaLaborando) {
    this.siglaLaborando = siglaLaborando;
  }

  public String getCodigoUnidadLaboral() {
    return codigoUnidadLaboral;
  }

  public void setCodigoUnidadLaboral(String codigoUnidadLaboral) {
    this.codigoUnidadLaboral = codigoUnidadLaboral;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCiudadExpedicionDocumento() {
    return ciudadExpedicionDocumento;
  }

  public void setCiudadExpedicionDocumento(String ciudadExpedicionDocumento) {
    this.ciudadExpedicionDocumento = ciudadExpedicionDocumento;
  }

  public String getCodigoCiudadExpedicionDocumento() {
    return codigoCiudadExpedicionDocumento;
  }

  public void setCodigoCiudadExpedicionDocumento(String codigoCiudadExpedicionDocumento) {
    this.codigoCiudadExpedicionDocumento = codigoCiudadExpedicionDocumento;
  }

  public String getCelular() {
    return celular;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCodigoCategoriaEmpleado() {
    return codigoCategoriaEmpleado;
  }

  public void setCodigoCategoriaEmpleado(String codigoCategoriaEmpleado) {
    this.codigoCategoriaEmpleado = codigoCategoriaEmpleado;
  }

  public Date getIngresoInstitucion() {
    return ingresoInstitucion;
  }

  public void setIngresoInstitucion(Date ingresoInstitucion) {

    this.ingresoInstitucion = ingresoInstitucion;
  }

  public AntiguedadDTO getAntiguedadDTO() {
    return antiguedadDTO;
  }

  public void setAntiguedadDTO(AntiguedadDTO antiguedadDTO) {
    this.antiguedadDTO = antiguedadDTO;
  }

  public boolean isInformacionSIATH() {
    return informacionSIATH;
  }

  public void setInformacionSIATH(boolean informacionSIATH) {
    this.informacionSIATH = informacionSIATH;
  }

  public String getOrigenSIAToInvestigador() {
    return origenSIAToInvestigador;
  }

  public void setOrigenSIAToInvestigador(String origenSIAToInvestigador) {
    this.origenSIAToInvestigador = origenSIAToInvestigador;
  }

  public String getGradoAlfabetico() {
    return gradoAlfabetico;
  }

  public void setGradoAlfabetico(String gradoAlfabetico) {
    this.gradoAlfabetico = gradoAlfabetico;
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

    public String getUsuarioEmpresarial() {
        return usuarioEmpresarial;
    }

    public void setUsuarioEmpresarial(String usuarioEmpresarial) {
        this.usuarioEmpresarial = usuarioEmpresarial;
    }

    public String getUnidadLaboraReal() {
        return unidadLaboraReal;
    }

    public void setUnidadLaboraReal(String unidadLaboraReal) {
        this.unidadLaboraReal = unidadLaboraReal;
    }
  
    

  @Override
  public String getLlaveModel() {

    if (idVistaFuncionario == null) {
      return null;
    }
    return String.valueOf(idVistaFuncionario);
  }

}
