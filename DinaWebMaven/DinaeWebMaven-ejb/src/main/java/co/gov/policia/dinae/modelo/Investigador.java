package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Édder Javier Peña Barranco
 */
@Entity
@Table(name = "INVESTIGADOR")
@NamedQueries({
  @NamedQuery(name = "Investigador.findAll", query = "SELECT i FROM Investigador i"),
  @NamedQuery(name = "Investigador.findByIdInvestigador", query = "SELECT i FROM Investigador i WHERE i.idInvestigador = :idInvestigador"),
  @NamedQuery(name = "Investigador.findByNumeroIdentificacion", query = "SELECT i FROM Investigador i WHERE i.numeroIdentificacion = :numeroIdentificacion"),
  @NamedQuery(name = "Investigador.findByCodigoCiudadExpedicion", query = "SELECT i FROM Investigador i WHERE i.codigoCiudadExpedicion = :codigoCiudadExpedicion"),
  @NamedQuery(name = "Investigador.findByNombreCiudadExpedicion", query = "SELECT i FROM Investigador i WHERE i.nombreCiudadExpedicion = :nombreCiudadExpedicion"),
  @NamedQuery(name = "Investigador.findByNombres", query = "SELECT i FROM Investigador i WHERE i.nombres = :nombres"),
  @NamedQuery(name = "Investigador.findByApellidos", query = "SELECT i FROM Investigador i WHERE i.apellidos = :apellidos"),
  @NamedQuery(name = "Investigador.findByCorreoElectronico", query = "SELECT i FROM Investigador i WHERE i.correoElectronico = :correoElectronico"),
  @NamedQuery(name = "Investigador.findByTelefono", query = "SELECT i FROM Investigador i WHERE i.telefono = :telefono"),
  @NamedQuery(name = "Investigador.findByDireccion", query = "SELECT i FROM Investigador i WHERE i.direccion = :direccion"),
  @NamedQuery(name = "Investigador.findByCodigoDeptoResidencia", query = "SELECT i FROM Investigador i WHERE i.codigoDeptoResidencia = :codigoDeptoResidencia"),
  @NamedQuery(name = "Investigador.findByNombreDeptoResidencia", query = "SELECT i FROM Investigador i WHERE i.nombreDeptoResidencia = :nombreDeptoResidencia"),
  @NamedQuery(name = "Investigador.findByCodigoCiudadResidencia", query = "SELECT i FROM Investigador i WHERE i.codigoCiudadResidencia = :codigoCiudadResidencia"),
  @NamedQuery(name = "Investigador.findByNombreCiudadResidencia", query = "SELECT i FROM Investigador i WHERE i.nombreCiudadResidencia = :nombreCiudadResidencia"),
  @NamedQuery(name = "Investigador.findByProfesorPolicial", query = "SELECT i FROM Investigador i WHERE i.profesorPolicial = :profesorPolicial"),
  @NamedQuery(name = "Investigador.findByFuncionInvestigador", query = "SELECT i FROM Investigador i WHERE i.funcionInvestigador = :funcionInvestigador"),
  @NamedQuery(name = "Investigador.findByCodigoDeptoExpedicion", query = "SELECT i FROM Investigador i WHERE i.codigoDeptoExpedicion = :codigoDeptoExpedicion"),
  @NamedQuery(name = "Investigador.findByNombreDeptoExpedicion", query = "SELECT i FROM Investigador i WHERE i.nombreDeptoExpedicion = :nombreDeptoExpedicion")
})
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "Investigador.BuscardorPersonal", procedureName = "PRC_BUSCADOR_INVESTIGADOR_SIAT",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_SESION", queryParameter = "P_SESION", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_ID_UNIDAD_POLICIAL", queryParameter = "P_ID_UNIDAD_POLICIAL", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_IDENTIFICACION", queryParameter = "P_IDENTIFICACION", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_NOMBRES", queryParameter = "P_NOMBRES", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_APELLIDOS", queryParameter = "P_APELLIDOS", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_PROFESOR_POLICIAL", queryParameter = "P_PROFESOR_POLICIAL", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_ID_ESTADO", queryParameter = "P_ID_ESTADO", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_NIVEL_PREGRADO", queryParameter = "P_ID_NIVEL_PREGRADO", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_NIVEL_POSTGRADO", queryParameter = "P_ID_NIVEL_POSTGRADO", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_NIVEL_ESPECILIZACION", queryParameter = "P_ID_NIVEL_ESPECILIZACION", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_NIVEL_MAESTRIA", queryParameter = "P_ID_NIVEL_MAESTRIA", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_NIVEL_DOCTORADO", queryParameter = "P_ID_NIVEL_DOCTORADO", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_CARAC_CON_PUBLICACION", queryParameter = "P_ID_CARAC_CON_PUBLICACION", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_CARAC_CON_INVESTIGACION", queryParameter = "P_ID_CARAC_CON_INVESTIGACION", mode = ParameterMode.IN, type = Long.class),
            @StoredProcedureParameter(name = "P_ID_GRADO", queryParameter = "P_ID_GRADO", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_ID_VINCULA", queryParameter = "P_ID_VINCULA", mode = ParameterMode.IN, type = Long.class),}
  ),
  @NamedStoredProcedureQuery(name = "Investigador.BuscardorEstudiosPersonal", procedureName = "PRC_BUSCADOR_ESTUDIOS_PERSONA",
          returnsResultSet = false, parameters = {
            @StoredProcedureParameter(name = "P_SESION", queryParameter = "P_SESION", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_IDENTIFICACION", queryParameter = "P_IDENTIFICACION", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "P_ORIGEN", queryParameter = "P_ORIGEN", mode = ParameterMode.IN, type = String.class),}
  )
})
public class Investigador implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Investigador_seq_gen")
  @SequenceGenerator(name = "Investigador_seq_gen", sequenceName = "SEC_INVESTIGADOR", allocationSize = 1)
  @Column(name = "ID_INVESTIGADOR")
  private Long idInvestigador;

  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "NUMERO_IDENTIFICACION")
  private String numeroIdentificacion;

  @Size(max = 6)
  @Column(name = "CODIGO_CIUDAD_EXPEDICION")
  private String codigoCiudadExpedicion;

  @Size(max = 100)
  @Column(name = "NOMBRE_CIUDAD_EXPEDICION")
  private String nombreCiudadExpedicion;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOMBRES")
  private String nombres;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "APELLIDOS")
  private String apellidos;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "CORREO_ELECTRONICO")
  private String correoElectronico;

  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "TELEFONO")
  private String telefono;

  @Basic(optional = false)
  @Size(min = 1, max = 100)
  @Column(name = "DIRECCION")
  private String direccion;

  @NotNull
  @Size(min = 1, max = 6)
  @Column(name = "CODIGO_DEPTO_RESIDENCIA")
  private String codigoDeptoResidencia;

  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "NOMBRE_DEPTO_RESIDENCIA")
  private String nombreDeptoResidencia;

  @NotNull
  @Size(min = 1, max = 6)
  @Column(name = "CODIGO_CIUDAD_RESIDENCIA")
  private String codigoCiudadResidencia;

  @Size(max = 100)
  @Column(name = "NOMBRE_CIUDAD_RESIDENCIA")
  private String nombreCiudadResidencia;

  @Column(name = "PROFESOR_POLICIAL")
  private Character profesorPolicial;

  @Size(max = 512)
  @Column(name = "FUNCION_INVESTIGADOR")
  private String funcionInvestigador;

  @Size(max = 6)
  @Column(name = "CODIGO_DEPTO_EXPEDICION")
  private String codigoDeptoExpedicion;

  @Size(max = 100)
  @Column(name = "NOMBRE_DEPTO_EXPEDICION")
  private String nombreDeptoExpedicion;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "idInvestigador")
  private List<FormacionInvestigador> formacionInvestigadorList;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "idInvestigador")
  private List<InvestigacionDesarrollada> investigacionDesarrolladaList;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "investigador", fetch = FetchType.LAZY)
  private List<PublicacionInvestigador> publicacionInvestigadorList;

  @JoinColumn(name = "TIPO_VINCULACION", referencedColumnName = "ID_CONSTANTES")
  @ManyToOne
  private Constantes tipoVinculacion;

  @Column(name = "ID_UNIDAD_POLICIAL_CREA")
  private Long idUnidadPolicialCrea;

  @Column(name = "ID_UNIDAD_POLICIAL_MODIFICA")
  private Long idUnidadPolicialModifica;

  @Transient
  private String grado;

  public Investigador() {
  }

  public Investigador(Long idInvestigador) {
    this.idInvestigador = idInvestigador;
  }

  public Investigador(Long idInvestigador, String numeroIdentificacion, String nombres, String apellidos,
          String correoElectronico, String telefono, String direccion, String codigoDeptoResidencia,
          String nombreDeptoResidencia, String codigoCiudadResidencia) {
    this.idInvestigador = idInvestigador;
    this.numeroIdentificacion = numeroIdentificacion;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correoElectronico = correoElectronico;
    this.telefono = telefono;
    this.direccion = direccion;
    this.codigoDeptoResidencia = codigoDeptoResidencia;
    this.nombreDeptoResidencia = nombreDeptoResidencia;
    this.codigoCiudadResidencia = codigoCiudadResidencia;
  }

  public Long getIdInvestigador() {
    return idInvestigador;
  }

  public void setIdInvestigador(Long idInvestigador) {
    this.idInvestigador = idInvestigador;
  }

  public String getNumeroIdentificacion() {
    return numeroIdentificacion;
  }

  public void setNumeroIdentificacion(String numeroIdentificacion) {
    this.numeroIdentificacion = numeroIdentificacion;
  }

  public String getCodigoCiudadExpedicion() {
    return codigoCiudadExpedicion;
  }

  public void setCodigoCiudadExpedicion(String codigoCiudadExpedicion) {
    this.codigoCiudadExpedicion = codigoCiudadExpedicion;
  }

  public String getNombreCiudadExpedicion() {
    return nombreCiudadExpedicion;
  }

  public void setNombreCiudadExpedicion(String nombreCiudadExpedicion) {
    this.nombreCiudadExpedicion = nombreCiudadExpedicion;
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

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico) {
    this.correoElectronico = correoElectronico;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getCodigoDeptoResidencia() {
    return codigoDeptoResidencia;
  }

  public void setCodigoDeptoResidencia(String codigoDeptoResidencia) {
    this.codigoDeptoResidencia = codigoDeptoResidencia;
  }

  public String getNombreDeptoResidencia() {
    return nombreDeptoResidencia;
  }

  public void setNombreDeptoResidencia(String nombreDeptoResidencia) {
    this.nombreDeptoResidencia = nombreDeptoResidencia;
  }

  public String getCodigoCiudadResidencia() {
    return codigoCiudadResidencia;
  }

  public void setCodigoCiudadResidencia(String codigoCiudadResidencia) {
    this.codigoCiudadResidencia = codigoCiudadResidencia;
  }

  public String getNombreCiudadResidencia() {
    return nombreCiudadResidencia;
  }

  public void setNombreCiudadResidencia(String nombreCiudadResidencia) {
    this.nombreCiudadResidencia = nombreCiudadResidencia;
  }

  public Character getProfesorPolicial() {
    return profesorPolicial;
  }

  public void setProfesorPolicial(Character profesorPolicial) {
    this.profesorPolicial = profesorPolicial;
  }

  public String getFuncionInvestigador() {
    return funcionInvestigador;
  }

  public void setFuncionInvestigador(String funcionInvestigador) {
    this.funcionInvestigador = funcionInvestigador;
  }

  public String getCodigoDeptoExpedicion() {
    return codigoDeptoExpedicion;
  }

  public void setCodigoDeptoExpedicion(String codigoDeptoExpedicion) {
    this.codigoDeptoExpedicion = codigoDeptoExpedicion;
  }

  public String getNombreDeptoExpedicion() {
    return nombreDeptoExpedicion;
  }

  public void setNombreDeptoExpedicion(String nombreDeptoExpedicion) {
    this.nombreDeptoExpedicion = nombreDeptoExpedicion;
  }

  public List<FormacionInvestigador> getFormacionInvestigadorList() {
    return formacionInvestigadorList;
  }

  public void setFormacionInvestigadorList(List<FormacionInvestigador> formacionInvestigadorList) {
    this.formacionInvestigadorList = formacionInvestigadorList;
  }

  public List<InvestigacionDesarrollada> getInvestigacionDesarrolladaList() {
    return investigacionDesarrolladaList;
  }

  public void setInvestigacionDesarrolladaList(List<InvestigacionDesarrollada> investigacionDesarrolladaList) {
    this.investigacionDesarrolladaList = investigacionDesarrolladaList;
  }

  public Constantes getTipoVinculacion() {
    return tipoVinculacion;
  }

  public void setTipoVinculacion(Constantes tipoVinculacion) {
    this.tipoVinculacion = tipoVinculacion;
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
    hash += (idInvestigador != null ? idInvestigador.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Investigador)) {
      return false;
    }
    Investigador other = (Investigador) object;
    if ((this.idInvestigador == null && other.idInvestigador != null) || (this.idInvestigador != null && !this.idInvestigador.equals(other.idInvestigador))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.Investigador[ idInvestigador=" + idInvestigador + " ]";
  }

  public List<PublicacionInvestigador> getPublicacionInvestigadorList() {
    return publicacionInvestigadorList;
  }

  public void setPublicacionInvestigadorList(List<PublicacionInvestigador> publicacionInvestigadorList) {
    this.publicacionInvestigadorList = publicacionInvestigadorList;
  }

  public Long getIdUnidadPolicialCrea() {
    return idUnidadPolicialCrea;
  }

  public void setIdUnidadPolicialCrea(Long idUnidadPolicialCrea) {
    this.idUnidadPolicialCrea = idUnidadPolicialCrea;
  }

  public Long getIdUnidadPolicialModifica() {
    return idUnidadPolicialModifica;
  }

  public void setIdUnidadPolicialModifica(Long idUnidadPolicialModifica) {
    this.idUnidadPolicialModifica = idUnidadPolicialModifica;
  }

}
