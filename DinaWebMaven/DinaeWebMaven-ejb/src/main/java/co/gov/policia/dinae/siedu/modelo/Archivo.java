/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.modelo;

import co.gov.policia.dinae.siedu.util.file.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Entity
@Table(name = "SIEDU_ARCHIVO")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Archivo.findAll", query = "SELECT s FROM Archivo s")
})
public class Archivo implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(Archivo.class);
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ARCH_ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIEDU_ARCHIVO_SEQ_GEN")
  @SequenceGenerator(name = "SIEDU_ARCHIVO_SEQ_GEN", sequenceName = "SIEDU_ARCHIVO_SEQ", allocationSize = 1)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCH_NOMBRE", nullable = false, length = 100)
  private String name;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "ARCH_EXT", nullable = false, length = 10)
  private String ext;
  @Size(max = 50)
  @Column(name = "ARCH_CONTENT_TYPE", length = 50)
  private String contentType;
  @Size(max = 100)
  @Column(name = "ARCH_TITULO", length = 100)
  private String title;
  @Transient
  private String base64;
  // auditoria
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "ARCH_USU_CREA", nullable = false, length = 30)
  private String creaUsuario;
  @Basic(optional = false)
  @NotNull
  @Column(name = "ARCH_FECHA_CREA", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creaFecha;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCH_MAQUINA_CREA", nullable = false, length = 100)
  private String creaMaquina;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "ARCH_IP_CREA", nullable = false, length = 100)
  private String creaIP;
  @Size(max = 30)
  @Column(name = "ARCH_USU_MOD", length = 30)
  private String modUsuario;
  @Column(name = "ARCH_FECHA_MOD")
  @Temporal(TemporalType.TIMESTAMP)
  private Date modFecha;
  @Size(max = 100)
  @Column(name = "ARCH_MAQUINA_MOD", length = 100)
  private String modMaquina;
  @Size(max = 100)
  @Column(name = "ARCH_IP_MOD", length = 100)
  private String modIP;

  /**
   * @param documentPath the rutaDocumento to set
   */
  public void setDocumentPath(String documentPath) {
    this.setName(FilenameUtils.getBaseName(documentPath));
    this.setExt(FilenameUtils.getExtension(documentPath));
  }

  /**
   * id concatenado con la extension
   *
   * @return
   */
  public String getIdWhitExtension() {
    if (getId() != null && getExt() != null) {
      return getId().toString().concat(".").concat(getExt());
    }
    return null;
  }

  /**
   * nombre concatenado con la extension
   *
   * @return
   */
  public String getNameWhitExtension() {
    if (getName() != null && getExt() != null) {
      return getName().concat(".").concat(getExt());
    }
    return null;
  }

  /**
   *
   * @return @throws IOException
   */
  public InputStream getInputStream() throws IOException {
    if (getBase64() == null) {
      return null;
    } else {
      try {
        return FileUtil.base64ToInputStream(getBase64());
      } catch (IOException ex) {
        LOG.error("Error en <<getImputStream>> ->> mensaje ->> {}", ex.getMessage());
        throw ex;
      }
    }
  }

  /**
   * @param inputStream the inputStream to set
   * @throws java.io.IOException
   */
  public void setInputStream(InputStream inputStream) throws IOException {
    if (inputStream != null) {
      try {
        setBase64(FileUtil.inputStreamToBase64(inputStream));
      } catch (IOException ex) {
        LOG.error("Error en <<setInputStream>> ->> mensaje ->> {}", ex.getMessage());
        throw ex;
      }
    }
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the ext
   */
  public String getExt() {
    return ext;
  }

  /**
   * @param ext the ext to set
   */
  public void setExt(String ext) {
    this.ext = ext;
  }

  /**
   * @return the contentType
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * @param contentType the contentType to set
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the base64
   */
  public String getBase64() {
    return base64;
  }

  /**
   * @param base64 the base64 to set
   */
  public void setBase64(String base64) {
    this.base64 = base64;
  }

  /**
   * @return the creaUsuario
   */
  public String getCreaUsuario() {
    return creaUsuario;
  }

  /**
   * @param creaUsuario the creaUsuario to set
   */
  public void setCreaUsuario(String creaUsuario) {
    this.creaUsuario = creaUsuario;
  }

  /**
   * @return the creaFecha
   */
  public Date getCreaFecha() {
    return creaFecha;
  }

  /**
   * @param creaFecha the creaFecha to set
   */
  public void setCreaFecha(Date creaFecha) {
    this.creaFecha = creaFecha;
  }

  /**
   * @return the creaMaquina
   */
  public String getCreaMaquina() {
    return creaMaquina;
  }

  /**
   * @param creaMaquina the creaMaquina to set
   */
  public void setCreaMaquina(String creaMaquina) {
    this.creaMaquina = creaMaquina;
  }

  /**
   * @return the creaIP
   */
  public String getCreaIP() {
    return creaIP;
  }

  /**
   * @param creaIP the creaIP to set
   */
  public void setCreaIP(String creaIP) {
    this.creaIP = creaIP;
  }

  /**
   * @return the modUsuario
   */
  public String getModUsuario() {
    return modUsuario;
  }

  /**
   * @param modUsuario the modUsuario to set
   */
  public void setModUsuario(String modUsuario) {
    this.modUsuario = modUsuario;
  }

  /**
   * @return the modFecha
   */
  public Date getModFecha() {
    return modFecha;
  }

  /**
   * @param modFecha the modFecha to set
   */
  public void setModFecha(Date modFecha) {
    this.modFecha = modFecha;
  }

  /**
   * @return the modMaquina
   */
  public String getModMaquina() {
    return modMaquina;
  }

  /**
   * @param modMaquina the modMaquina to set
   */
  public void setModMaquina(String modMaquina) {
    this.modMaquina = modMaquina;
  }

  /**
   * @return the modIP
   */
  public String getModIP() {
    return modIP;
  }

  /**
   * @param modIP the modIP to set
   */
  public void setModIP(String modIP) {
    this.modIP = modIP;
  }

}
