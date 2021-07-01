package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.EnsayoCriticoUnidadView;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuCo9ConsultarDetallesEnsayoCriticoFaces")
@javax.enterprise.context.SessionScoped
public class CuCo9ConsultarDetallesEnsayoCritico extends JSFUtils implements Serializable {

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritico;

  private EnsayoCritico _ensayoCritico;

  private String _desarrolloEnsayo;

  private List<PerfilUsuarioDTO> _listaAutor;

  private EnsayoCriticoUnidadView _ensayoCriticoView;

  @EJB
  private IVistaFuncionarioLocal _iVistaFuncionario;

  @javax.inject.Inject
  private CuCo8EvaluacionEnsayoCritico cuCo8EvaluacionEnsayoCriticoFaces;

  private boolean _esEncargadoVicin;

  private boolean _mostrarBtnEvaluacion;

  /**
   *
   * @param ensayoCriticoView
   */
  public void initReturnCU(EnsayoCriticoUnidadView ensayoCriticoView) {
    try {

      _mostrarBtnEvaluacion = true;
      _ensayoCriticoView = ensayoCriticoView;
      _ensayoCritico = _iEnsayoCritico.findByIdEnsayoCritico(ensayoCriticoView.getIdEnsayoCritico());
      _desarrolloEnsayo = (_ensayoCritico.getDesarrolloEnsayo() == null) ? "No se encontró desarrollo del ensayo" : new String(_ensayoCritico.getDesarrolloEnsayo());

      VistaFuncionario datosUsuario = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(_ensayoCritico.getIdentificacion());

      UnidadPolicialDTO unidadPolicialDTO = new UnidadPolicialDTO(
              _ensayoCritico.getUnidadPolicial().getIdUnidadPolicial(),
              _ensayoCritico.getUnidadPolicial().getNombre(),
              _ensayoCritico.getUnidadPolicial().getSiglaFisica());

      PerfilUsuarioDTO perfilUsuarioDTO = new PerfilUsuarioDTO(datosUsuario.getGrado(), datosUsuario.getNombreCompleto(),
              datosUsuario.getCorreo(), unidadPolicialDTO);

      _listaAutor = new ArrayList<PerfilUsuarioDTO>(1);
      _listaAutor.add(perfilUsuarioDTO);

      Long estadoEvauado = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO;
      Long estadoNoAprobadoUnidad = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO_UNIDAD;

      Long estadoGanador = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_GANADOR;
      Long estadoNoAprobado = IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO;

      _esEncargadoVicin = !(perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN) != null);

      if (_esEncargadoVicin) {

        if (_ensayoCritico.getEstadoCuCo10() != null) {

          Long idEstado = _ensayoCritico.getEstadoCuCo10().getIdConstantes();

          if ((idEstado.compareTo(estadoGanador) == 0 || idEstado.compareTo(estadoNoAprobado) == 0)) {
            _mostrarBtnEvaluacion = false;
          }
        }

      } else {

        Long idEstado = _ensayoCritico.getEstadoCuCo8().getIdConstantes();

        if ((idEstado.compareTo(estadoEvauado) == 0 && _ensayoCritico.getEstadoCuCo10() != null)
                || idEstado.compareTo(estadoNoAprobadoUnidad) == 0) {
          _mostrarBtnEvaluacion = false;
        }
      }

      navigationFaces.redirectFacesCuCo9ConsultarDetallesEnsayoCritico();
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo9ConsultarDetallesEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo9ConsultarDetallesEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String irEvaluacion() {
    try {
      _ensayoCritico = _iEnsayoCritico.findByIdEnsayoCritico(_ensayoCriticoView.getIdEnsayoCritico());

      PerfilUsuarioDTO perfilUsuarioDTO = loginFaces.getPerfilUsuarioDTO();

      if (perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_ENSAYOS_EN_LA_UNIDAD_POLICIAL) != null) {
        _esEncargadoVicin = false;
      } else if (perfilUsuarioDTO.getRolUsuarioPorIdRolDTO(IConstantesRole.ENCARGADO_DE_EVALUACION_DE_ENSAYOS_EN_LA_VICIN) != null) {
        _esEncargadoVicin = true;
      }

      if (_esEncargadoVicin) {

        if (!(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO_VICIN.compareTo(_ensayoCritico.getEstadoCuCo10().getIdConstantes()) == 0)
                && !(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_GANADOR.compareTo(_ensayoCritico.getEstadoCuCo10().getIdConstantes()) == 0)
                && !(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_NO_APROBADO.compareTo(_ensayoCritico.getEstadoCuCo10().getIdConstantes()) == 0)) {

          _ensayoCritico.setEstadoCuCo10(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_LEIDO_VICIN));
          _ensayoCritico = _iEnsayoCritico.saveOrUpdate(_ensayoCritico);
        }
      } else if (!(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EVALUADO.compareTo(_ensayoCritico.getEstadoCuCo8().getIdConstantes()) == 0)) {
        _ensayoCritico.setEstadoCuCo8(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_LEIDO));
        _ensayoCritico = _iEnsayoCritico.saveOrUpdate(_ensayoCritico);
      }

      return cuCo8EvaluacionEnsayoCriticoFaces.iniciarEvaluacion(_ensayoCriticoView);
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo9ConsultarDetallesEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarFormato() {

    try {

      HashMap mapa = new HashMap();
      mapa.put("p_id_ensayo", _ensayoCritico.getIdEnsayoCritico().intValue());

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte7.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "7_FORMATO ENSAYO CRITICO.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getGenerarFormato)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String regresar() {

    if (_ensayoCriticoView != null && _ensayoCriticoView.isConsultarEnsayoCritico()) {

      return "/pages/secured/cu_co_6/detallesConvocatoria.xhtml?faces-redirect=true";

    } else {

      cuCo8EvaluacionEnsayoCriticoFaces.setEnsayoItemSeleccionado(null);
      return cuCo8EvaluacionEnsayoCriticoFaces.redireccionarEvaluarEnsayos(_ensayoCriticoView.getIdPeriodo());

    }

  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {
    try {
      if (_ensayoCritico != null && _ensayoCritico.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_7_upload_path") + _ensayoCritico.getNombreArchivo();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _ensayoCritico.getNombreArchivoOriginal());

        return download;
      }
    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-7 Ingresar Ensayo Critico (getDownloadContentProperty)", e);
    }
    return null;
  }

  public EnsayoCritico getEnsayoCritico() {
    return _ensayoCritico;
  }

  public void setEnsayoCritico(EnsayoCritico _ensayoCritico) {
    this._ensayoCritico = _ensayoCritico;
  }

  public String getDesarrolloEnsayo() {
    return _desarrolloEnsayo;
  }

  public void setDesarrolloEnsayo(String _desarrolloEnsayo) {
    this._desarrolloEnsayo = _desarrolloEnsayo;
  }

  public IEnsayoCriticoLocal getiEnsayoCritico() {
    return _iEnsayoCritico;
  }

  public void setiEnsayoCritico(IEnsayoCriticoLocal _iEnsayoCritico) {
    this._iEnsayoCritico = _iEnsayoCritico;
  }

  public List<PerfilUsuarioDTO> getListaAutor() {
    return _listaAutor;
  }

  public void setListaAutor(List<PerfilUsuarioDTO> _listaAutor) {
    this._listaAutor = _listaAutor;
  }

  public EnsayoCriticoUnidadView getEnsayoCriticoView() {
    return _ensayoCriticoView;
  }

  public void setEnsayoCriticoView(EnsayoCriticoUnidadView _ensayoCriticoView) {
    this._ensayoCriticoView = _ensayoCriticoView;
  }

  public boolean isMostrarBtnGenerarFormato() {
    return _ensayoCriticoView != null && _ensayoCriticoView.isConsultarEnsayoCritico();
  }

  public boolean isMostrarBtnEvaluacion() {
    return _mostrarBtnEvaluacion && _ensayoCriticoView != null && !_ensayoCriticoView.isConsultarEnsayoCritico();
  }

  public void setMostrarBtnEvaluacion(boolean _mostrarBtnEvaluacion) {
    this._mostrarBtnEvaluacion = _mostrarBtnEvaluacion;
  }
}
