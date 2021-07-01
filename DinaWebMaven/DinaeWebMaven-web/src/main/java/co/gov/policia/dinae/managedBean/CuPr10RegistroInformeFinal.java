package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IInformeAvanceLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.IReseniaInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IResultadosInvestigacionLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.InformeAvance;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.ReseniaInvestigacion;
import co.gov.policia.dinae.modelo.ResultadosInvestigacion;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "cuPr10RegistroInformeFinal")
@javax.enterprise.context.SessionScoped
public class CuPr10RegistroInformeFinal extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuPr19ConsultarProyectosVigentesAsignadosFaces cuPr19ConsultarProyectosVigentesAsignadosFaces;

  @EJB
  private IProyectoLocal _iProyecto;

  @EJB
  private IInformeAvanceLocal _iinformeAvance;

  @EJB
  private IConstantesLocal _iConstantes;

  @EJB
  private IResultadosInvestigacionLocal _iResultadosInvestigacion;

  @EJB
  private IReseniaInvestigacionLocal _iReseniaInvestigacion;

  @EJB
  private ICompromisoProyectoLocal _iCompromisoProyecto;

  @javax.inject.Inject
  private IncludeInformacionInformeProyectoGenericoFaces includeInformacionInformeProyectoGenericoFaces;

  @javax.inject.Inject
  private CuPr7RegistrarAvanceInvestigacionFaces cuPr7RegistrarAvanceInvestigacionFaces;

  private Proyecto _proyecto;

  private InformeAvance _informeAvanceAnterior;

  private InformeAvance _informeAvance;

  private ResultadosInvestigacion _resultadosInvestigacion;

  private List<ResultadosInvestigacion> _resultadosInvestigacionList;

  private FileUploadEvent _eventArchivoSubido;

  private String _nombreArchivoInformeFinalProyecto;

  private Integer _activeTabIndex;

  private List<SelectItem> _listaTipoProducto;

  private String _idTipoProducto;

  private String _descripcionResultadoInvestiga;

  private DataTable _dataTable;

  private boolean _esModificacion;

  private List<ResultadosInvestigacion> _resultadosInvestigacionListCopia;

  private ReseniaInvestigacion _reseniaInvestigacion;

  private ListGenericDataModel _listaResultadosInvestigacionModel;

  private boolean _mostrarEnlaceDescarga;

  private Long _idCompromisoProyecto;

  private Date _fechaFinalizacion;

  /**
   *
   * @param idProyecto
   * @param idCompromiso
   * @return
   */
  public String initRetrunCUFromCUPR19(Long idProyecto, Long idCompromiso) {
    try {

      _proyecto = _iProyecto.obtenerProyectoPorId(idProyecto);
      _idCompromisoProyecto = idCompromiso;

      _informeAvance = _iinformeAvance.findInformeAvanceFinalByProyecto(_proyecto.getIdProyecto(), IConstantes.TIPO_INFORME_AVANCE_FINAL, idCompromiso);
      _nombreArchivoInformeFinalProyecto = "";

      _informeAvanceAnterior = _iinformeAvance.findLastInformeAvanceByProyecto(_proyecto.getIdProyecto());

      if (_informeAvanceAnterior != null) {

        if (_informeAvance == null) {

          _informeAvanceAnterior = _iinformeAvance.findLastInformeAvanceByProyecto(_proyecto.getIdProyecto());
          _informeAvance = new InformeAvance();
          _informeAvance.setFechaInicio(_informeAvanceAnterior.getFechaFinalizacion());
          _informeAvance.setFechaFinalizacion(new Date());

          _informeAvance.setTipoInformeAvance(new Constantes(IConstantes.TIPO_INFORME_AVANCE_FINAL));
          _informeAvance.setProyecto(_proyecto);

          RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
          UsuarioRol usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

          _informeAvance.setIdUsuarioRol(usuarioRol);
          _informeAvance.setCompromisoProyecto(new CompromisoProyecto(idCompromiso));
          _iinformeAvance.saveOrUpdate(_informeAvance);

          _resultadosInvestigacionList = new ArrayList<ResultadosInvestigacion>();

          _esModificacion = false;
          _resultadosInvestigacionListCopia = null;
          _mostrarEnlaceDescarga = false;

        }

        _resultadosInvestigacionList = _iResultadosInvestigacion.findByInformeAvanceProyecto(_informeAvance.getIdInformeAvance(), _proyecto.getIdProyecto());
        _listaResultadosInvestigacionModel = new ListGenericDataModel(_resultadosInvestigacionList);
        _resultadosInvestigacionListCopia = new ArrayList<ResultadosInvestigacion>(_resultadosInvestigacionList.size());
        _resultadosInvestigacionListCopia.addAll(_resultadosInvestigacionList);

        _reseniaInvestigacion = _iReseniaInvestigacion.findByInformeFinalProyecto(_informeAvance.getIdInformeAvance(), _proyecto.getIdProyecto());
        if (_reseniaInvestigacion == null) {
          inicializarReseniaInvestigacion();
        }

        if (_informeAvance.getNombreArchivo() != null) {
          _nombreArchivoInformeFinalProyecto = _informeAvance.getNombreArchivo();
          _mostrarEnlaceDescarga = true;
        }

        inicializarResultadosInvestigacion("", "");
        includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(this._proyecto);

        _activeTabIndex = 0;
        _fechaFinalizacion = _informeAvance.getFechaFinalizacion();

        CompromisoProyecto compromisoProyecto = _iCompromisoProyecto.obtenerCompromisoProyecto(_idCompromisoProyecto);
        compromisoProyecto.setEstado(new Constantes(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_ELABORACION));

        _iCompromisoProyecto.agregarCompromisoProyecto(compromisoProyecto);

        construirListaTipoProducto();
      } else {
        adicionaMensajeAdvertencia("Se debe diligenciar los Informes de Avance antes de diligenciar el Informe Final");
        return null;
      }

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception") + ": " + ex.getMessage());
      Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, null, ex);
    }

    return navigationFaces.redirectCuPr10RegistroInformeFinalRedirect();
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void construirListaTipoProducto() throws JpaDinaeException {

    List<Constantes> constantesTipoProducto = _iConstantes.getConstantesPorTipo(IConstantes.INFORME_AVANCE_FINAL_TIPO_PRODUCTO);
    if (constantesTipoProducto != null && !constantesTipoProducto.isEmpty()) {
      _listaTipoProducto = new ArrayList<SelectItem>(constantesTipoProducto.size());

      for (Constantes cons : constantesTipoProducto) {
        _listaTipoProducto.add(new SelectItem(String.valueOf(cons.getIdConstantes().longValue()), cons.getValor()));
      }
    }
  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      _eventArchivoSubido = event;
      _nombreArchivoInformeFinalProyecto = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(_nombreArchivoInformeFinalProyecto));
      _mostrarEnlaceDescarga = false;

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-10 Registro Archivo (handleFileUpload) ", e);
    }
  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico(String ruta) throws JpaDinaeException {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      if (_eventArchivoSubido != null) {

        String nombreArchivoOriginal = _eventArchivoSubido.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "INFORME_FINAL_PROYECTO".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(ruta, _eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-10 Registro Archivo (almacenarArchivoFisico) ", e);
      throw new JpaDinaeException(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }

    return null;

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void guardarTabRegistroArchivo() throws Exception {

    String[] archivo;
    String ruta = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general");

    if (_eventArchivoSubido != null) {
      //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
      //Y LOS PERMISOS SON VALIDOS
      if (_informeAvance.getTipoContenidoArchivo() != null) {

        File directorioFinal;
        String mensajeFallo;
        try {

          directorioFinal = new File(ruta);

        } catch (NullPointerException e) {
          mensajeFallo = "ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(ruta);
          Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, mensajeFallo, e);
          throw new JpaDinaeException(mensajeFallo);
        }

        if (!directorioFinal.exists()) {
          mensajeFallo = "ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(ruta);
          JpaDinaeException e = new JpaDinaeException(mensajeFallo);
          Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, mensajeFallo, e);
          throw e;
        }

        if (directorioFinal.isFile()) {
          mensajeFallo = "ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(ruta);
          JpaDinaeException e = new JpaDinaeException(mensajeFallo);
          Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, mensajeFallo, e);
          throw e;
        }

        if (!directorioFinal.canWrite()) {
          mensajeFallo = "ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(ruta);
          JpaDinaeException e = new JpaDinaeException(mensajeFallo);
          Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, mensajeFallo, e);
          throw e;
        }

        archivo = almacenarArchivoFisico(ruta);

        _informeAvance.setNombreArchivo(archivo[0]);
        _informeAvance.setNombreArchivoFisico(archivo[1]);

        _iinformeAvance.saveOrUpdate(_informeAvance);

        _mostrarEnlaceDescarga = true;
        _nombreArchivoInformeFinalProyecto = archivo[0];

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

      } else {
        throw new JpaDinaeException("El campo 'Descripción' es obligatirio!");
      }
    } else {
      throw new JpaDinaeException("El campo 'Archivo del Informe' es obligatorio!");
    }

  }

  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    _activeTabIndex = 0;

    if (event == null || event.getTab() == null) {
      return;
    }

    if ("tabInfoBasicas".equals(event.getTab().getId())) {
      _activeTabIndex = 0;
      includeInformacionInformeProyectoGenericoFaces.inicializarDatosProyecto(this._proyecto);
    } else if ("tabRegistrarArchivo".equals(event.getTab().getId())) {
      _activeTabIndex = 1;
    } else if ("tabResultadoInvestiga".equals(event.getTab().getId())) {
      _activeTabIndex = 2;
    } else if ("tabResenaInvestiga".equals(event.getTab().getId())) {
      _activeTabIndex = 3;
    }

  }

  /**
   *
   * @param tabIndex
   * @return
   */
  public String guardar(Integer tabIndex) {
    try {

      if (tabIndex.compareTo(0) == 0) {
        guardarTabInfoBasica();
      } else if (tabIndex.compareTo(1) == 0) {
        guardarTabRegistroArchivo();
      } else if (tabIndex.compareTo(2) == 0) {
        guardarDatosResultadoInvestigacion();
      } else if (tabIndex.compareTo(3) == 0) {
        guardarDatosTabReseniaInvestiga();
      }

    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(ex.getMessage());
    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-10", ex);
    }
    return null;
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void guardarTabInfoBasica() throws JpaDinaeException {

    if (_fechaFinalizacion != null) {
      _informeAvance.setFechaFinalizacion(_fechaFinalizacion);
      _informeAvance = _iinformeAvance.saveOrUpdate(_informeAvance);

      _informeAvance = _iinformeAvance.findInformeAvanceFinalByProyecto(_proyecto.getIdProyecto(), IConstantes.TIPO_INFORME_AVANCE_FINAL, _idCompromisoProyecto);
      _fechaFinalizacion = _informeAvance.getFechaFinalizacion();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } else {
      throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_10_lbl_periodo_informe") + "' es obligatorio");
    }
  }

  /**
   *
   * @param event
   */
  public void noSeleccionResultadosInvestigacion(UnselectEvent event) {
    inicializarReseniaInvestigacion();
  }

  /**
   *
   * @param changeEvent
   */
  public void cambioFechaFinChangeEvent(ValueChangeEvent changeEvent) {

    if (changeEvent == null) {
      return;
    }

    _fechaFinalizacion = (Date) changeEvent.getNewValue();
  }

  /**
   *
   * @param event
   */
  public void cargarDatosResultadosInvestigacion(SelectEvent event) {

    if (_resultadosInvestigacion == null) {
      return;
    }

    _idTipoProducto = String.valueOf(_resultadosInvestigacion.getIdTipoProducto().getIdConstantes());
    _descripcionResultadoInvestiga = _resultadosInvestigacion.getDescripcion();
    _esModificacion = true;
  }

  /**
   *
   * @return
   */
  public String cancelarModificacion() {
    inicializarResultadosInvestigacion("", "");
    return null;
  }

  /**
   *
   * @param tipoProducto
   * @param descripcion
   */
  private void inicializarResultadosInvestigacion(String tipoProducto, String descripcion) {
    this._resultadosInvestigacion = new ResultadosInvestigacion();
    this._resultadosInvestigacion.setIdInformeAvance(_informeAvance);
    this._resultadosInvestigacion.setIdProyecto(_proyecto);
    this._resultadosInvestigacion.setIdTipoProducto(new Constantes());

    RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
    UsuarioRol usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());
    this._resultadosInvestigacion.setIdUsuarioRol(usuarioRol);

    _idTipoProducto = tipoProducto;
    _descripcionResultadoInvestiga = descripcion;
    _esModificacion = false;
  }

  /**
   *
   */
  private void inicializarReseniaInvestigacion() {
    _reseniaInvestigacion = new ReseniaInvestigacion();
    _reseniaInvestigacion.setIdInformeAvance(_informeAvance);
    _reseniaInvestigacion.setIdProyecto(_proyecto);

    RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
    _reseniaInvestigacion.setIdUsuarioRol(rolUsuarioDTO.getIdUsuarioRol());

  }

  /**
   *
   */
  public void eliminarResultadosInvestigacion() {
    try {
      _resultadosInvestigacionList.remove(_resultadosInvestigacion);
      _iResultadosInvestigacion.delete(_resultadosInvestigacion);

      _resultadosInvestigacionList = _iResultadosInvestigacion.findByInformeAvanceProyecto(_informeAvance.getIdInformeAvance(), _proyecto.getIdProyecto());
      _listaResultadosInvestigacionModel = new ListGenericDataModel(_resultadosInvestigacionList);

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarCompromiso(ActionEvent actionEvent) {

    try {

      //VALIDAMOS QUE LA INFORMACION ESTE COMPLETO
      if (_informeAvance == null || _informeAvance.getNombreArchivo() == null || _informeAvance.getNombreArchivoFisico() == null) {

        adicionaMensajeError("Antes de enviar el Informe de Final debe registrar el archivo");
        return;

      }

      if (_listaResultadosInvestigacionModel == null || _listaResultadosInvestigacionModel.getNumeroRegistro() == 0) {

        adicionaMensajeError("Antes de enviar el Informe Final debe registrar el Resultado de la Investigación");
        return;

      }

      if (_reseniaInvestigacion == null || _reseniaInvestigacion.getResumenPalabras() == null
              || _reseniaInvestigacion.getPalabrasClave() == null
              || _reseniaInvestigacion.getAbstract1() == null
              || _reseniaInvestigacion.getKeywords() == null
              || _reseniaInvestigacion.getConclusion() == null
              || _reseniaInvestigacion.getRecomendaciones() == null) {

        adicionaMensajeError("Antes de enviar el Informe Final debe registrar la Reseña de la Investigación");
        return;
      }

      cuPr7RegistrarAvanceInvestigacionFaces.initReturnCU_Desde_CU_PR_10(_proyecto.getIdProyecto(), _idCompromisoProyecto);

      //REALIZAMOS LAS VALIDACIONES RESPECTIVAS
      if (!cuPr7RegistrarAvanceInvestigacionFaces.validarDatosInforme()) {
        return;
      }

      cuPr7RegistrarAvanceInvestigacionFaces.setIdCompromisoProyecto(_idCompromisoProyecto);
      cuPr7RegistrarAvanceInvestigacionFaces.setRegistraLlamdoCU(CuPr7RegistrarAvanceInvestigacionFaces.ORIGEN_LLAMADO_CU.CU_PR_10);
      cuPr7RegistrarAvanceInvestigacionFaces.setFechaFinalInforme(_fechaFinalizacion);
      cuPr7RegistrarAvanceInvestigacionFaces.setFechaInicalInforme(_informeAvance.getFechaInicio());
      cuPr7RegistrarAvanceInvestigacionFaces.setProyectoSeleccionado(_proyecto);

      cuPr7RegistrarAvanceInvestigacionFaces.enviarDetalleCompromiso(actionEvent);

      //RECARGAMOS LOS DATOS DEL CU_PR_19
      String retorno = cuPr19ConsultarProyectosVigentesAsignadosFaces.initReturnCU();

      //REDIRECCIONAMOS AL CU CU_PR_19
      navigationFaces.redirectFacesCuGenerico(retorno);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-10  ", e);

    }

  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void guardarDatosResultadoInvestigacion() throws Exception {

    try {

      if (_idTipoProducto == null || "".equals(_idTipoProducto)) {
        adicionaMensajeError("El campo 'Tipo de producto' es obligatorio");
        return;
      }

      if (_descripcionResultadoInvestiga == null || "".equals(_descripcionResultadoInvestiga)) {
        adicionaMensajeError("El campo 'Descripción' es obligatorio");
        return;
      }

      if (_resultadosInvestigacion == null) {
        inicializarResultadosInvestigacion(_idTipoProducto, _descripcionResultadoInvestiga);
      }
      _resultadosInvestigacion.setIdTipoProducto(new Constantes(Long.valueOf(_idTipoProducto)));
      _resultadosInvestigacion.setDescripcion(_descripcionResultadoInvestiga);
      _iResultadosInvestigacion.saveOrUpdate(_resultadosInvestigacion);

      _resultadosInvestigacionList.clear();
      _resultadosInvestigacionList = _iResultadosInvestigacion.findByInformeAvanceProyecto(_informeAvance.getIdInformeAvance(), _proyecto.getIdProyecto());
      _listaResultadosInvestigacionModel = new ListGenericDataModel(_resultadosInvestigacionList);
      inicializarResultadosInvestigacion("", "");

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException e) {
      throw new Exception(e);
    }

  }

  private void validarCamposReseniaInvestigacion() throws JpaDinaeException {

    if (_reseniaInvestigacion != null) {

      if (_reseniaInvestigacion.getResumenPalabras() == null || "".equals(_reseniaInvestigacion.getResumenPalabras().trim())) {
        throw new JpaDinaeException("El campo 'Resumen' es obligatorio");
      }

      if (_reseniaInvestigacion.getPalabrasClave() == null || "".equals(_reseniaInvestigacion.getPalabrasClave().trim())) {
        throw new JpaDinaeException("El campo 'Palabras Clave' es obligatorio");
      }

      if (_reseniaInvestigacion.getAbstract1() == null || "".equals(_reseniaInvestigacion.getAbstract1().trim())) {
        throw new JpaDinaeException("El campo 'Abstract' es obligatorio");
      }

      if (_reseniaInvestigacion.getKeywords() == null || "".equals(_reseniaInvestigacion.getKeywords().trim())) {
        throw new JpaDinaeException("El campo 'Keywords' es obligatorio");
      }

      if (_reseniaInvestigacion.getConclusion() == null || "".equals(_reseniaInvestigacion.getConclusion().trim())) {
        throw new JpaDinaeException("El campo 'Conclusión' es obligatorio");
      }

      if (_reseniaInvestigacion.getRecomendaciones() == null || "".equals(_reseniaInvestigacion.getRecomendaciones().trim())) {
        throw new JpaDinaeException("El campo 'Recomendaciones' es obligatorio");
      }
    }
  }

  /**
   *
   * @throws JpaDinaeException
   */
  private void guardarDatosTabReseniaInvestiga() throws Exception {

    try {

      validarCamposReseniaInvestigacion();
      _reseniaInvestigacion = _iReseniaInvestigacion.saveOrUpdate(_reseniaInvestigacion);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));

    } catch (JpaDinaeException e) {

      throw new Exception(e);
    }
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {
    try {
      if (_informeAvance != null && _informeAvance.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_pr_10_upload_path") + _informeAvance.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, _informeAvance.getNombreArchivo());
        return download;
      }
    } catch (FileNotFoundException e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String invocarCuPr7() {
    try {
      return cuPr7RegistrarAvanceInvestigacionFaces.initReturnCU_Desde_CU_PR_10(_proyecto.getIdProyecto(), _idCompromisoProyecto);
    } catch (Exception ex) {
      adicionaMensajeError("Error indeterminado al tratar de invocar al CU-PR-7: " + ex.getMessage());
      Logger.getLogger(CuPr10RegistroInformeFinal.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String regresarCuPr19() {
    return navigationFaces.redirectCuPr19ConsultarProyectosVigentesAsignadosRedirect();
  }

  public Proyecto getProyecto() {
    return _proyecto;
  }

  public void setProyecto(Proyecto _proyecto) {
    this._proyecto = _proyecto;
  }

  public InformeAvance getInformeAvanceAnterior() {
    return _informeAvanceAnterior;
  }

  public void setInformeAvanceAnterior(InformeAvance _informeAvanceAnterior) {
    this._informeAvanceAnterior = _informeAvanceAnterior;
  }

  public InformeAvance getInformeAvance() {
    return _informeAvance;
  }

  public void setInformeAvance(InformeAvance _informeAvance) {
    this._informeAvance = _informeAvance;
  }

  public Integer getActiveTabIndex() {
    return _activeTabIndex;
  }

  public void setActiveTabIndex(Integer _activeTabIndex) {
    this._activeTabIndex = _activeTabIndex;
  }

  public List<SelectItem> getListaTipoProducto() {
    return _listaTipoProducto;
  }

  public void setListaTipoProducto(List<SelectItem> listaTipoProducto) {
    this._listaTipoProducto = listaTipoProducto;
  }

  public ResultadosInvestigacion getResultadosInvestigacion() {
    return _resultadosInvestigacion;
  }

  public void setResultadosInvestigacion(ResultadosInvestigacion _resultadosInvestigacion) {
    this._resultadosInvestigacion = _resultadosInvestigacion;
  }

  public List<ResultadosInvestigacion> getResultadosInvestigacionList() {
    return _resultadosInvestigacionList;
  }

  public void setResultadosInvestigacionList(List<ResultadosInvestigacion> _resultadosInvestigacionList) {
    this._resultadosInvestigacionList = _resultadosInvestigacionList;
  }

  public String getIdTipoProducto() {
    return _idTipoProducto;
  }

  public void setIdTipoProducto(String _idTipoProducto) {
    this._idTipoProducto = _idTipoProducto;
  }

  public String getDescripcionResultadoInvestiga() {
    return _descripcionResultadoInvestiga;
  }

  public void setDescripcionResultadoInvestiga(String _descripcionResultadoInvestiga) {
    this._descripcionResultadoInvestiga = _descripcionResultadoInvestiga;
  }

  public DataTable getDataTable() {
    return _dataTable;
  }

  public void setDataTable(DataTable _dataTable) {
    this._dataTable = _dataTable;
  }

  public ReseniaInvestigacion getReseniaInvestigacion() {
    return _reseniaInvestigacion;
  }

  public void setReseniaInvestigacion(ReseniaInvestigacion _reseniaInvestigacion) {
    this._reseniaInvestigacion = _reseniaInvestigacion;
  }

  public boolean isEsModificacion() {
    return _esModificacion;
  }

  public void setEsModificacion(boolean _esModificacion) {
    this._esModificacion = _esModificacion;
  }

  public ListGenericDataModel getListaResultadosInvestigacionModel() {
    return _listaResultadosInvestigacionModel;
  }

  public void setListaResultadosInvestigacionModel(ListGenericDataModel _listaResultadosInvestigacionModel) {
    this._listaResultadosInvestigacionModel = _listaResultadosInvestigacionModel;
  }

  public boolean isMostrarEnlaceDescarga() {
    return _mostrarEnlaceDescarga;
  }

  public void setMostrarEnlaceDescarga(boolean _mostrarEnlaceDescarga) {
    this._mostrarEnlaceDescarga = _mostrarEnlaceDescarga;
  }

  public String getNombreArchivoInformeFinalProyecto() {
    return _nombreArchivoInformeFinalProyecto;
  }

  public void setNombreArchivoInformeFinalProyecto(String _nombreArchivoInformeFinalProyecto) {
    this._nombreArchivoInformeFinalProyecto = _nombreArchivoInformeFinalProyecto;
  }

  public Date getFechaFinalizacion() {
    return _fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date _fechaFinalizacion) {
    this._fechaFinalizacion = _fechaFinalizacion;
  }

}
