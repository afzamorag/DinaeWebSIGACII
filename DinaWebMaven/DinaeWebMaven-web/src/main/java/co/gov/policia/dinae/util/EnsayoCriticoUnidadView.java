package co.gov.policia.dinae.util;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cguzman
 */
public class EnsayoCriticoUnidadView implements IDataModel, Serializable {

  private EnsayoCritico _ensayo;

  private Long _idUnidadPolicial;

  private String _nombreUnidad;

  private Long _idEnsayoCritico;

  private String _tituloEnsayo;

  private String _consecutivoEnsayo;

  private Date _fechaPresentacion;

  private String _estadoEnsayo;

  private String _evaluacionEnsayo;

  private Long _estadoCalifacion;

  private boolean _esEstadoVicin;

  private Long idPeriodo;

  private boolean consultarEnsayoCritico;

  public Long getIdUnidadPolicial() {
    return _idUnidadPolicial;
  }

  public void setIdUnidadPolicial(Long _idUnidadPolicial) {
    this._idUnidadPolicial = _idUnidadPolicial;
  }

  public String getNombreUnidad() {
    return _nombreUnidad;
  }

  public void setNombreUnidad(String _nombreUnidad) {
    this._nombreUnidad = _nombreUnidad;
  }

  public Long getIdEnsayoCritico() {
    return _idEnsayoCritico;
  }

  public void setIdEnsayoCritico(Long _idEnsayoCritico) {
    this._idEnsayoCritico = _idEnsayoCritico;
  }

  public String getTituloEnsayo() {
    return _tituloEnsayo;
  }

  public void setTituloEnsayo(String _tituloEnsayo) {
    this._tituloEnsayo = _tituloEnsayo;
  }

  public String getConsecutivoEnsayo() {
    return _consecutivoEnsayo;
  }

  public void setConsecutivoEnsayo(String _consecutivoEnsayo) {
    this._consecutivoEnsayo = _consecutivoEnsayo;
  }

  public Date getFechaPresentacion() {
    return _fechaPresentacion;
  }

  public void setFechaPresentacion(Date _fechaPresentacion) {
    this._fechaPresentacion = _fechaPresentacion;
  }

  public String getEstadoEnsayo() {
    return _estadoEnsayo;
  }

  public void setEstadoEnsayo(String _estadoEnsayo) {
    this._estadoEnsayo = _estadoEnsayo;
  }

  public String getEvaluacionEnsayo() {
    return _evaluacionEnsayo;
  }

  public void setEvaluacionEnsayo(String _evaluacionEnsayo) {
    this._evaluacionEnsayo = _evaluacionEnsayo;
  }

  public Long getEstadoCalifacion() {
    return _estadoCalifacion;
  }

  public void setEstadoCalifacion(Long _estadoCalifacion) {
    this._estadoCalifacion = _estadoCalifacion;
  }

  public EnsayoCritico getEnsayo() {
    return _ensayo;
  }

  public void setEnsayo(EnsayoCritico _ensayo) {
    this._ensayo = _ensayo;
  }

  public boolean isEsEstadoVicin() {
    return _esEstadoVicin;
  }

  public void setEsEstadoVicin(boolean _esEstadoVicin) {
    this._esEstadoVicin = _esEstadoVicin;
  }

  public Long getIdPeriodo() {
    return idPeriodo;
  }

  public void setIdPeriodo(Long idPeriodo) {
    this.idPeriodo = idPeriodo;
  }

  @Override
  public String getLlaveModel() {
    if (_idEnsayoCritico == null) {
      return null;
    }

    return _idEnsayoCritico.toString();
  }

  public boolean isConsultarEnsayoCritico() {
    return consultarEnsayoCritico;
  }

  public void setConsultarEnsayoCritico(boolean consultarEnsayoCritico) {
    this.consultarEnsayoCritico = consultarEnsayoCritico;
  }

}
