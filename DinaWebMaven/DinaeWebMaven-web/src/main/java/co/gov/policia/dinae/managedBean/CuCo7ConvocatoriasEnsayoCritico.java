package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.PerfilUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IEnsayoCriticoLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EnsayoCritico;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.util.JSFUtils;
import static co.gov.policia.dinae.util.JSFUtils.copiarArchivoRutaFisica;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuCo7ConvocatoriasEnsayoCriticoFaces")
@javax.enterprise.context.SessionScoped
public class CuCo7ConvocatoriasEnsayoCritico extends JSFUtils implements Serializable {

  @EJB
  private IPeriodoLocal _iPeriodo;

  @EJB
  private IEnsayoCriticoLocal _iEnsayoCritico;

  private List<Periodo> _listaConvocatoriasEnsayoCritico;

  private ListGenericDataModel _listaConvocatoriasEnsayoCriticoModel;

  private Periodo _convocatoriaEnsayoCritico;

  private FileUploadEvent _eventArchivoSubido;

  private String _nombreArchivoEnsayo;

  private String _tituloEnsayo;

  private String _resumenEnsayo;

  private String _palabrasClaveEnsayo;

  private String _abstractEnsayo;

  private String _keywordsEnsayo;

  private String _introduccionEnsayo;

  private String _desarrolloEnsayo;

  private String _conclusionRecomendacionEnsayo;

  private String _propuestaEnsayo;

  private String _bibliografiaEnsayo;

  private String _grado;

  private String _nombreApellidos;

  private String _email;

  private EnsayoCritico _ensayoCritico;

  private boolean _esVisibleEnlaceDescarga;

  private boolean _esVisibleNombreArchivo;

  private List<PerfilUsuarioDTO> _listaAutor;

  private boolean _deshabilitarCamposEnsayo;

  /**
   *
   * @return
   */
  public String initReturnCU() {
    try {

      if (loginFaces.getPerfilUsuarioDTO().getUnidadPolicial() == null) {
        adicionaMensajeError("Para acceder a esta opción es indispensable que el usuario pertenezca a una Unidad Policial");
        return null;
      }

      _listaConvocatoriasEnsayoCritico = _iPeriodo.getPeriodosUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA,
              IConstantes.TIPO_CONVOCATORIA_GESTION_ENSAYO_CRITICO);

      _listaConvocatoriasEnsayoCriticoModel = new ListGenericDataModel(_listaConvocatoriasEnsayoCritico);
      return navigationFaces.redirectCuCo7ConvocatoriasEnsayoCriticoRedirect();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaSeleccionConvocatoriaEnsayo(SelectEvent event) {
    try {
      if (_convocatoriaEnsayoCritico == null) {
        return;
      }

      PerfilUsuarioDTO perfilUsuarioDTO = loginFaces.getPerfilUsuarioDTO();
      _listaAutor = new ArrayList<PerfilUsuarioDTO>(1);
      _listaAutor.add(perfilUsuarioDTO);
      _ensayoCritico = _iEnsayoCritico.findByPeriodoUsuario(_convocatoriaEnsayoCritico.getIdPeriodo(), perfilUsuarioDTO.getIdentificacion());

      if (_ensayoCritico == null) {

        inicializarEnsayoCritico();

      }

      _deshabilitarCamposEnsayo = false;

      if (_ensayoCritico.getIdEnsayoCritico() != null) {

        if (_ensayoCritico.getDesarrolloEnsayo() != null) {
          byte[] desarrolloEnsayo = _ensayoCritico.getDesarrolloEnsayo();
          String desarrolloEnsayoString = new String(desarrolloEnsayo);
          _ensayoCritico.setDesarrolloEnsayoString(desarrolloEnsayoString);
        } else {
          _ensayoCritico.setDesarrolloEnsayoString(null);
        }

        if (IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EN_ELABORACION.compareTo(_ensayoCritico.getEstadoCuCo8().getIdConstantes()) != 0) {
          _deshabilitarCamposEnsayo = true;
        }

      } else {
        _esVisibleEnlaceDescarga = false;
        _esVisibleNombreArchivo = false;

      }

      cargarCampos();

      navigationFaces.redirectFacesCuCo7IngresarEnsayoCritico();

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   */
  private void cargarCampos() {
    _nombreArchivoEnsayo = _ensayoCritico.getNombreArchivoOriginal();
    _esVisibleEnlaceDescarga = true;
    _esVisibleNombreArchivo = false;
    _tituloEnsayo = _ensayoCritico.getTituloEnsayo();
    _resumenEnsayo = _ensayoCritico.getResumenEnsayo();
    _palabrasClaveEnsayo = _ensayoCritico.getPalabrasClaveEnsayo();
    _abstractEnsayo = _ensayoCritico.getAbstractEnsayo();
    _keywordsEnsayo = _ensayoCritico.getKeywordsEnsayo();
    _introduccionEnsayo = _ensayoCritico.getIntroduccionEnsayo();
    _desarrolloEnsayo = _ensayoCritico.getDesarrolloEnsayoString();
    _conclusionRecomendacionEnsayo = _ensayoCritico.getConclusionesEnsayo();
    _propuestaEnsayo = _ensayoCritico.getPropuestaEnsayo();
    _bibliografiaEnsayo = _ensayoCritico.getBibliografiaEnsayo();
  }

  /**
   *
   */
  private void cargarCamposObjeto() {

    _ensayoCritico.setTituloEnsayo(_tituloEnsayo);
    _ensayoCritico.setResumenEnsayo(_resumenEnsayo);
    _ensayoCritico.setPalabrasClaveEnsayo(_palabrasClaveEnsayo);
    _ensayoCritico.setAbstractEnsayo(_abstractEnsayo);
    _ensayoCritico.setKeywordsEnsayo(_keywordsEnsayo);
    _ensayoCritico.setIntroduccionEnsayo(_introduccionEnsayo);

    byte[] desarrolloEnsayo = (_desarrolloEnsayo == null) ? null : _desarrolloEnsayo.getBytes();
    _ensayoCritico.setDesarrolloEnsayo(desarrolloEnsayo);

    _ensayoCritico.setConclusionesEnsayo(_conclusionRecomendacionEnsayo);
    _ensayoCritico.setPropuestaEnsayo(_propuestaEnsayo);
    _ensayoCritico.setBibliografiaEnsayo(_bibliografiaEnsayo);
  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      _eventArchivoSubido = event;
      _nombreArchivoEnsayo = event.getFile().getFileName();
      _esVisibleNombreArchivo = true;
      _esVisibleEnlaceDescarga = false;
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(_nombreArchivoEnsayo));

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO 7 Ingresar Ensayo Critico (handleFileUpload) ", e);
    }
  }

  /**
   *
   * @param event
   */
  public void enviarEnsayo(ActionEvent event) {
    try {
      validarCampos();

      if (_ensayoCritico == null) {

        inicializarEnsayoCritico();

      } else {

        _ensayoCritico.setNombreMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      }

      validarArchivoEnsayo();
      _ensayoCritico.setEstadoCuCo8(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_RECIBIDO));
      _ensayoCritico.setFechaPresentacion(new Date());
      cargarCamposObjeto();

      //UNIDAD POLICIAL A LA QUE PERTENECE EL ENSAYO
      //UNIDAD_POLICIAL DEL USUARIO
      _ensayoCritico.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));

      _iEnsayoCritico.saveOrUpdate(_ensayoCritico);
      adicionaMensajeInformativo("La operación se realizó con éxito!!, Ensayo enviado");
      _deshabilitarCamposEnsayo = true;

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
    }
  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico() {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      if (_eventArchivoSubido != null) {

        String nombreArchivoOriginal = _eventArchivoSubido.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "ENSAYO_CRITICO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_co_7_upload_path"), _eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-7 Ingresar Ensayo Critico (almacenarArchivoFisico) ", e);
    }

    return null;

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void validarArchivoEnsayo() throws JpaDinaeException {

    String mensajeError = "Se ha generado un error: %s, ruta: %s";

    try {

      if (_nombreArchivoEnsayo != null && _eventArchivoSubido != null) {

        File directorioFinal = new File(keyPropertiesFactory.value("cu_co_7_upload_path"));

        if (!directorioFinal.exists()) {
          throw new JpaDinaeException(String.format(mensajeError, new Object[]{"Archivo o directorio no existe", keyPropertiesFactory.value("cu_co_7_upload_path")}));
        }

        if (directorioFinal.isFile()) {
          throw new JpaDinaeException(String.format(mensajeError, new Object[]{"La ruta es un archivo mas no un directorio", keyPropertiesFactory.value("cu_co_7_upload_path")}));
        }

        if (!directorioFinal.canWrite()) {
          throw new JpaDinaeException(String.format(mensajeError, new Object[]{"No se poseen privilegios necesarios para escribir en el directorio", keyPropertiesFactory.value("cu_co_7_upload_path")}));
        }

        String[] archivo = almacenarArchivoFisico();

        if (archivo != null && archivo[0] != null && archivo[1] != null) {
          _ensayoCritico.setNombreArchivo(archivo[1]);
          _ensayoCritico.setNombreArchivoOriginal(archivo[0]);
        }
      }

    } catch (NullPointerException e) {
      throw new JpaDinaeException(String.format(mensajeError, new Object[]{e.getMessage(), keyPropertiesFactory.value("cu_co_7_upload_path")}));
    }

  }

  /**
   *
   */
  private void inicializarEnsayoCritico() {

    _ensayoCritico = new EnsayoCritico();
    _ensayoCritico.setUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
    _ensayoCritico.setIdentificacion(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
    String gradoNombre = loginFaces.getPerfilUsuarioDTO().getGrado() == null ? "" : loginFaces.getPerfilUsuarioDTO().getGrado().concat(" - ");
    gradoNombre = gradoNombre.concat(loginFaces.getPerfilUsuarioDTO().getNombreCompleto());
    _ensayoCritico.setGradoYnombres(gradoNombre);
    _ensayoCritico.setEstadoCuCo8(new Constantes(IConstantes.TIPO_ESTADO_ENSAYO_CRITICO_EN_ELABORACION));
    _ensayoCritico.setNombreMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
    _ensayoCritico.setPeriodo(_convocatoriaEnsayoCritico);
  }

  /**
   *
   * @return
   */
  public String guardarEnsayo() {
    try {

      String mensaje = "El campo %s, es obligatorio";
      if (_tituloEnsayo == null || "".equals(_tituloEnsayo)) {
        adicionaMensajeError(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_titulo_ensayo")));
        return null;
      }

      if (_ensayoCritico == null) {

        inicializarEnsayoCritico();

      } else {

        _ensayoCritico.setNombreMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());
      }

      validarArchivoEnsayo();
      cargarCamposObjeto();

      //UNIDAD POLICIAL A LA QUE PERTENECE EL ENSAYO
      //UNIDAD_POLICIAL DEL USUARIO
      _ensayoCritico.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));

      _ensayoCritico = _iEnsayoCritico.saveOrUpdate(_ensayoCritico);
      adicionaMensajeInformativo("La operación se realizó de manera exitosa!");

      _esVisibleEnlaceDescarga = true;
      _esVisibleNombreArchivo = false;

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(ex.getMessage());
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
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

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _ensayoCritico.getNombreArchivo());
        return download;
      }
    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-7 Ingresar Ensayo Critico (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String regresar() {
    return initReturnCU();
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void validarCampos() throws JpaDinaeException {

    String mensaje = "El campo %s, es obligatorio";

    if (_tituloEnsayo == null || "".equals(_tituloEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_titulo_ensayo")));
    }

    if (_resumenEnsayo == null || "".equals(_resumenEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_resumen")));
    }

    if (_palabrasClaveEnsayo == null || "".equals(_palabrasClaveEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_palabras_clave")));
    }

    if (_abstractEnsayo == null || "".equals(_abstractEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_abstract")));
    }

    if (_keywordsEnsayo == null || "".equals(_keywordsEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_keywords")));
    }

    if (_introduccionEnsayo == null || "".equals(_introduccionEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_introduccion")));
    }

    if (_desarrolloEnsayo == null || "".equals(_desarrolloEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_desarrollo_ensayo")));
    }

    if ((_nombreArchivoEnsayo == null && _eventArchivoSubido == null)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_archivo_ensayo")));
    }

    if (_conclusionRecomendacionEnsayo == null || "".equals(_conclusionRecomendacionEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_conlusiones_recomendaciones")));
    }

    if (_propuestaEnsayo == null || "".equals(_propuestaEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_propuesta")));
    }

    if (_bibliografiaEnsayo == null || "".equals(_bibliografiaEnsayo)) {
      throw new JpaDinaeException(String.format(mensaje, keyPropertiesFactory.value("cu_co_7_lbl_bibliografia_cybergrafia")));
    }

  }

  /**
   *
   * @param idConvocatoria
   * @return
   */
  public boolean isMostrarLinkDescargaArchivo(Long idConvocatoria) {
    try {
      if (idConvocatoria == null) {
        return false;
      }

      Periodo convocatoria = _iPeriodo.getPeriodoPorId(idConvocatoria);

      return convocatoria != null && convocatoria.getNombreArchivo() != null && convocatoria.getNombreArchivoFisico() != null;

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuCo7ConvocatoriasEnsayoCritico.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  /**
   *
   * @param idConvocatoria
   * @return
   */
  public StreamedContent descargarCondicionesConvocatoria(Long idConvocatoria) {

    try {

      if (idConvocatoria == null) {
        return null;
      }

      Periodo convocatoria = _iPeriodo.getPeriodoPorId(idConvocatoria);

      if (convocatoria != null && convocatoria.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte") + convocatoria.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, convocatoria.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-07 Consultar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionConvocatoria(UnselectEvent event) {
    _convocatoriaEnsayoCritico = null;
  }

  public Periodo getConvocatoriaEnsayoCritico() {
    return _convocatoriaEnsayoCritico;
  }

  public void setConvocatoriaEnsayoCritico(Periodo _convocatoriaEnsayoCritico) {
    this._convocatoriaEnsayoCritico = _convocatoriaEnsayoCritico;
  }

  public List<Periodo> getListaConvocatoriasEnsayoCritico() {
    return _listaConvocatoriasEnsayoCritico;
  }

  public void setListaConvocatoriasEnsayoCritico(List<Periodo> _listaConvocatoriasEnsayoCritico) {
    this._listaConvocatoriasEnsayoCritico = _listaConvocatoriasEnsayoCritico;
  }

  public ListGenericDataModel getListaConvocatoriasEnsayoCriticoModel() {
    return _listaConvocatoriasEnsayoCriticoModel;
  }

  public void setListaConvocatoriasEnsayoCriticoModel(ListGenericDataModel _listaConvocatoriasEnsayoCriticoModel) {
    this._listaConvocatoriasEnsayoCriticoModel = _listaConvocatoriasEnsayoCriticoModel;
  }

  public FileUploadEvent getEventArchivoSubido() {
    return _eventArchivoSubido;
  }

  public void setEventArchivoSubido(FileUploadEvent _eventArchivoSubido) {
    this._eventArchivoSubido = _eventArchivoSubido;
  }

  public String getNombreArchivoEnsayo() {
    return _nombreArchivoEnsayo;
  }

  public void setNombreArchivoEnsayo(String _nombreArchivoEnsayo) {
    this._nombreArchivoEnsayo = _nombreArchivoEnsayo;
  }

  public String getTituloEnsayo() {
    return _tituloEnsayo;
  }

  public void setTituloEnsayo(String _tituloEnsayo) {
    this._tituloEnsayo = _tituloEnsayo;
  }

  public String getResumenEnsayo() {
    return _resumenEnsayo;
  }

  public void setResumenEnsayo(String _resumenEnsayo) {
    this._resumenEnsayo = _resumenEnsayo;
  }

  public String getPalabrasClaveEnsayo() {
    return _palabrasClaveEnsayo;
  }

  public void setPalabrasClaveEnsayo(String _palabrasClaveEnsayo) {
    this._palabrasClaveEnsayo = _palabrasClaveEnsayo;
  }

  public String getAbstractEnsayo() {
    return _abstractEnsayo;
  }

  public void setAbstractEnsayo(String _abstractEnsayo) {
    this._abstractEnsayo = _abstractEnsayo;
  }

  public String getKeywordsEnsayo() {
    return _keywordsEnsayo;
  }

  public void setKeywordsEnsayo(String _keywordsEnsayo) {
    this._keywordsEnsayo = _keywordsEnsayo;
  }

  public String getIntroduccionEnsayo() {
    return _introduccionEnsayo;
  }

  public void setIntroduccionEnsayo(String _introduccionEnsayo) {
    this._introduccionEnsayo = _introduccionEnsayo;
  }

  public String getDesarrolloEnsayo() {
    return _desarrolloEnsayo;
  }

  public void setDesarrolloEnsayo(String _desarrolloEnsayo) {
    this._desarrolloEnsayo = _desarrolloEnsayo;
  }

  public String getConclusionRecomendacionEnsayo() {
    return _conclusionRecomendacionEnsayo;
  }

  public void setConclusionRecomendacionEnsayo(String _conclusionRecomendacionEnsayo) {
    this._conclusionRecomendacionEnsayo = _conclusionRecomendacionEnsayo;
  }

  public String getPropuestaEnsayo() {
    return _propuestaEnsayo;
  }

  public void setPropuestaEnsayo(String _propuestaEnsayo) {
    this._propuestaEnsayo = _propuestaEnsayo;
  }

  public String getBibliografiaEnsayo() {
    return _bibliografiaEnsayo;
  }

  public void setBibliografiaEnsayo(String _bibliografiaEnsayo) {
    this._bibliografiaEnsayo = _bibliografiaEnsayo;
  }

  public String getGrado() {
    return _grado;
  }

  public void setGrado(String _grado) {
    this._grado = _grado;
  }

  public String getNombreApellidos() {
    return _nombreApellidos;
  }

  public void setNombreApellidos(String _nombreApellidos) {
    this._nombreApellidos = _nombreApellidos;
  }

  public String getEmail() {
    return _email;
  }

  public void setEmail(String _email) {
    this._email = _email;
  }

  public EnsayoCritico getEnsayoCritico() {
    return _ensayoCritico;
  }

  public void setEnsayoCritico(EnsayoCritico _ensayoCritico) {
    this._ensayoCritico = _ensayoCritico;
  }

  public boolean isEsVisibleEnlaceDescarga() {
    return _esVisibleEnlaceDescarga;
  }

  public void setEsVisibleEnlaceDescarga(boolean esVisibleEnlaceDescarga) {
    this._esVisibleEnlaceDescarga = esVisibleEnlaceDescarga;
  }

  public boolean isEsVisibleNombreArchivo() {
    return _esVisibleNombreArchivo;
  }

  public void setEsVisibleNombreArchivo(boolean _esVisibleNombreArchivo) {
    this._esVisibleNombreArchivo = _esVisibleNombreArchivo;
  }

  public List<PerfilUsuarioDTO> getListaAutor() {
    return _listaAutor;
  }

  public void setListaAutor(List<PerfilUsuarioDTO> _listaAutor) {
    this._listaAutor = _listaAutor;
  }

  public boolean isDeshabilitarCamposEnsayo() {
    return _deshabilitarCamposEnsayo;
  }

  public void setDeshabilitarCamposEnsayo(boolean _deshabilitarCamposEnsayo) {
    this._deshabilitarCamposEnsayo = _deshabilitarCamposEnsayo;
  }
}
