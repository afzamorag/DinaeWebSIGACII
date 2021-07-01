package co.gov.policia.dinae.modelo;

import java.io.Serializable;
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
@Table(name = "USUARIO_UNIDAD_POLICIAL")
@NamedQueries({
  @NamedQuery(name = "UsuarioUnidadPolicial.findAll", query = "SELECT u FROM UsuarioUnidadPolicial u"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findUsusarioUnidadPolicialPorIdentificacion", query = "SELECT u FROM UsuarioUnidadPolicial u WHERE u.identificadorUsuario = :identificacion"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findAllPorIdentificacion", query = "SELECT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO(u.unidadPolicial.idUnidadPolicial, u.unidadPolicial.nombre) FROM UsuarioUnidadPolicial u WHERE u.identificadorUsuario = :identificacion"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findAllPorIdentificacionDatosRol", query = "SELECT NEW co.gov.policia.dinae.dto.UnidadPolicialDTO(u.unidadPolicial.idUnidadPolicial, u.unidadPolicial.nombre, u.unidadPolicial.siglaFisica, u.unidadPolicial.siglaPapa) FROM UsuarioUnidadPolicial u WHERE u.identificadorUsuario = :identificacion"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findAllPorUnidadByRole", query = "SELECT u.identificadorUsuario FROM UsuarioUnidadPolicial u WHERE u.unidadPolicial.tipoUnidad.idTipoUnidad = :tipoUnidad AND u.identificadorUsuario IN (SELECT ur.identificadorUsuario FROM  UsuarioRol ur WHERE ur.rol.idRol = :role AND ur.activo = 'S' )"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findUsuarioPorUnidadByRole", query = "SELECT u.identificadorUsuario FROM UsuarioUnidadPolicial u WHERE u.unidadPolicial.idUnidadPolicial = :unidadPolicial AND u.identificadorUsuario IN (SELECT ur.identificadorUsuario FROM  UsuarioRol ur WHERE ur.rol.idRol = :role AND ur.activo = 'S' )"),
  @NamedQuery(name = "UsuarioUnidadPolicial.findByIdUnidadIdentificacion", query = "SELECT u FROM UsuarioUnidadPolicial u WHERE u.unidadPolicial.idUnidadPolicial = :idUnidad AND u.identificadorUsuario = :identificacion")
})
public class UsuarioUnidadPolicial implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_USUARIO_UNIDAD_POLICIAL")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UsuarioUnidadPolicial_seq_gen")
  @SequenceGenerator(name = "UsuarioUnidadPolicial_seq_gen", sequenceName = "SEC_USUARIO_UNIDAD_POLICIAL", allocationSize = 1)
  private Long idUsuarioUnidadPolicial;

  @Column(name = "IDENTIFICADOR_USUARIO")
  private String identificadorUsuario;

  @JoinColumn(name = "ID_UNIDAD_POLICIAL", referencedColumnName = "ID_UNIDAD_POLICIAL")
  @ManyToOne(fetch = FetchType.LAZY)
  private UnidadPolicial unidadPolicial;

  public UsuarioUnidadPolicial() {
  }

  public UsuarioUnidadPolicial(Long idUsuarioUnidadPolicial) {
    this.idUsuarioUnidadPolicial = idUsuarioUnidadPolicial;
  }

  public UsuarioUnidadPolicial(Long idUsuarioUnidadPolicial, String identificadorUsuario) {
    this.idUsuarioUnidadPolicial = idUsuarioUnidadPolicial;
    this.identificadorUsuario = identificadorUsuario;
  }

  public Long getIdUsuarioUnidadPolicial() {
    return idUsuarioUnidadPolicial;
  }

  public void setIdUsuarioUnidadPolicial(Long idUsuarioUnidadPolicial) {
    this.idUsuarioUnidadPolicial = idUsuarioUnidadPolicial;
  }

  public String getIdentificadorUsuario() {
    return identificadorUsuario;
  }

  public void setIdentificadorUsuario(String identificadorUsuario) {
    this.identificadorUsuario = identificadorUsuario;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUsuarioUnidadPolicial != null ? idUsuarioUnidadPolicial.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsuarioUnidadPolicial)) {
      return false;
    }
    UsuarioUnidadPolicial other = (UsuarioUnidadPolicial) object;
    if ((this.idUsuarioUnidadPolicial == null && other.idUsuarioUnidadPolicial != null) || (this.idUsuarioUnidadPolicial != null && !this.idUsuarioUnidadPolicial.equals(other.idUsuarioUnidadPolicial))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.gov.policia.dinae.modelo.UsuarioUnidadPolicial[ idUsuarioUnidadPolicial=" + idUsuarioUnidadPolicial + " ]";
  }

  public UnidadPolicial getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(UnidadPolicial unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

}
