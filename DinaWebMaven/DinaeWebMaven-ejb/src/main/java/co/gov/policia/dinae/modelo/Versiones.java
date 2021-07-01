package co.gov.policia.dinae.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Entity
@Table(name = "VERSIONES")
@NamedQueries({
  @NamedQuery(name = "Versiones.findAllPorProyecto", query = "SELECT NEW co.gov.policia.dinae.dto.ProyectoVersionDTO ( v.idVersion, v.proyecto.idProyecto, v.fechaVersion ) FROM Versiones v WHERE v.proyecto.idProyecto = :idProyecto ORDER BY v.fechaVersion ASC"),})
@NamedStoredProcedureQuery(name = "ProcedimientoGenerarVersionProyecto", procedureName = "PRC_VERSION_PROYECTO",
        returnsResultSet = false, parameters = {
          @StoredProcedureParameter(name = "P_ID_PROYECTO", queryParameter = "idProyecto", mode = ParameterMode.IN, type = Long.class),
          @StoredProcedureParameter(name = "ID_VERSION_PROYECTO", queryParameter = "idVersion", mode = ParameterMode.IN, type = Long.class)
        })
public class Versiones implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID_VERSION")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Versiones_seq_gen")
  @SequenceGenerator(name = "Versiones_seq_gen", sequenceName = "SEC_VERSIONES", allocationSize = 1)
  private Long idVersion;

  @Column(name = "FECHA_VERSION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaVersion;

  @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
  @ManyToOne
  private Proyecto proyecto;

  public Versiones() {
  }

  public Long getIdVersion() {
    return idVersion;
  }

  public void setIdVersion(Long idVersion) {
    this.idVersion = idVersion;
  }

  public Date getFechaVersion() {
    return fechaVersion;
  }

  public void setFechaVersion(Date fechaVersion) {
    this.fechaVersion = fechaVersion;
  }

  public Proyecto getProyecto() {
    return proyecto;
  }

  public void setProyecto(Proyecto proyecto) {
    this.proyecto = proyecto;
  }

}
