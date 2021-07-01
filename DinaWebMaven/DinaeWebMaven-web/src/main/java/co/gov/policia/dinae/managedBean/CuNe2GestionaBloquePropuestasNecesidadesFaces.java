package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IAreaCienciaTecnologiaLocal;
import co.gov.policia.dinae.interfaces.IComentarioLocal;
import co.gov.policia.dinae.interfaces.IFuncionarioNecesidadLocal;
import co.gov.policia.dinae.interfaces.ILineaLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadCuNe07;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FuncionarioNecesidad;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import static co.gov.policia.dinae.util.JSFUtils.copiarArchivoRutaFisica;
import co.gov.policia.dinae.util.UtilidadesItem;
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
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "cuNe2GestionaBloquePropuestasNecesidadesFaces")
@javax.enterprise.context.SessionScoped
public class CuNe2GestionaBloquePropuestasNecesidadesFaces extends JSFUtils implements Serializable, IPropuestaNecesidadCuNe07 {

  @javax.inject.Inject
  private CuNe5JefeLoteNecesidadesFaces cuNe5JefeLoteNecesidadesFaces;

  @javax.inject.Inject
  private CuNe4ConsultaPropuestaDeNecesidadFaces cuNe4ConsultaPropuestaDeNecesidadFaces;

  @EJB
  private IPeriodoLocal iPeriodoLocal;

  @EJB
  private IPropuestaNecesidadLocal iPropuestaNecesidadLocal;

  @EJB
  private IAreaCienciaTecnologiaLocal iAreaCienciaTecnologia;

  @EJB
  private ILineaLocal iLineaLocal;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  @EJB
  private IMailSessionLocal iMailSessionBean;

  @EJB
  private IFuncionarioNecesidadLocal iFuncionarioNecesidadLocal;

  @EJB
  private IComentarioLocal iComentarioLocal;

  private List<PropuestaNecesidad> listaPropuestaNecesidad;
  private List<SelectItem> listaLineaItem;
  private List<SelectItem> listaAreaCienciaTecnologiaItem;
  private Periodo periodoSeleccionado;
  private PropuestaNecesidad propuestaNecesidad;
  private Long areaCienciaTecnologiaSeleccionada;
  private Long lineaSeleccionada;
  private String numeroDocumento;
  private String numeroTelefono;
  private VistaFuncionario vistaFuncionario;
  private List<FuncionarioNecesidad> listaFuncionariosNecesidad;
  private FuncionarioNecesidad funcionarioNecesidadSeleccionadoEliminar;
  private String nombreArchivoSubido;
  private FileUploadEvent eventArchivoSubido;
  private ListGenericDataModel listaPeriodoModel;
  private List<Comentario> listaComentarioNecesidad;
  private boolean soloLecturaPropuestaNecesidad;

  private static final List<Long> listaIdEstadosAutorizadoRegistroNecesidad = new ArrayList<Long>(3);

  static {

    listaIdEstadosAutorizadoRegistroNecesidad.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION);
    listaIdEstadosAutorizadoRegistroNecesidad.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA);

  }

  private static final List<Long> listaIdEstadosJefeUnidadRegistroNecesidad = new ArrayList<Long>(1);

  static {

    listaIdEstadosJefeUnidadRegistroNecesidad.add(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBACION_JEFE_UNIDAD);

  }

  public CuNe2GestionaBloquePropuestasNecesidadesFaces() {
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    return navigationFaces.redirectCuNe02Redirect();

  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {
    try {

      listaLineaItem = null;
      listaAreaCienciaTecnologiaItem = null;
      periodoSeleccionado = null;
      propuestaNecesidad = null;
      funcionarioNecesidadSeleccionadoEliminar = null;
      areaCienciaTecnologiaSeleccionada = null;
      lineaSeleccionada = null;
      numeroDocumento = null;
      numeroTelefono = null;
      vistaFuncionario = null;
      listaFuncionariosNecesidad = null;
      nombreArchivoSubido = null;
      eventArchivoSubido = null;
      listaPeriodoModel = null;
      listaComentarioNecesidad = null;

      //CARGAMOS LA LISTA DE PERIODOS
      //SE CONSULTA LA LISTA DE LOS PERIODOS EN LOS CUALES LA FECHA ACTUAL 
      //SE ENCUENTRA ENTRE LA FECHA DE INICIO Y FECHA FIN DEL PERIODO (PERIODO DE APERTURA) 
      //Y ESTÁ HABILITADA PARA LA UNIDAD DEL USUARIO PARA ESTE CRONOGRAMA. 
      List<Periodo> listaPeriodos = iPeriodoLocal.getPeriodosFechaActualEntreFechaInicioYfechaFin(
              new Date(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.ESTADO_PERIODO_PUBLICADO);

      listaPeriodoModel = new ListGenericDataModel(listaPeriodos);

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación", e);
    }
  }

  /**
   *
   * @param propuestaNecesidad
   * @return
   */
  public String verComentariosJefePropuestaSeleccionada(PropuestaNecesidad propuestaNecesidad) {

    try {

      this.propuestaNecesidad = propuestaNecesidad;
      listaComentarioNecesidad = iComentarioLocal.getComentarioNecesidadPorPropuestaNecesidad(propuestaNecesidad.getIdPropuestaNecesidad());

      return navigationFaces.redirectCuNe02ComentarioPropuestaNecesidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (verComentariosJefePropuestaSeleccionada)", e);

    }
    return null;

  }

  /**
   *
   * @param propuestaNecesidadSeleccionada
   * @throws Exception
   */
  private void cargarPropuestaNecesidadSeleccionada(PropuestaNecesidad propuestaNecesidadSeleccionada) throws Exception {

    reiniciarDatosAdicionarModificarPropuesta();

    funcionarioNecesidadSeleccionadoEliminar = null;
    this.propuestaNecesidad = propuestaNecesidadSeleccionada;
    listaFuncionariosNecesidad = iFuncionarioNecesidadLocal.getListaFuncionarioNecesidad(propuestaNecesidadSeleccionada.getIdPropuestaNecesidad());
    Linea linea = iLineaLocal.obtenerLineaPorId(propuestaNecesidadSeleccionada.getLinea().getIdLinea());
    lineaSeleccionada = linea.getIdLinea();
    areaCienciaTecnologiaSeleccionada = linea.getAreaCienciaTecnologia().getIdAreaCienciaTecnologia();
    nombreArchivoSubido = propuestaNecesidadSeleccionada.getNombreArchivo();

    //ACTIALIZAMOS LA LISTA DE LINEAS
    handleAreaCienciaTecnologiaSeleccionadaChange();

  }

  /**
   *
   * @param propuestaNecesidadSeleccionada
   * @return
   */
  public String consultarPropuestaSeleccionada(PropuestaNecesidad propuestaNecesidadSeleccionada) {

    try {
      //soloLecturaPropuestaNecesidad SE HABILITA PARA QUE NO PUEDE MODIFICAR
      soloLecturaPropuestaNecesidad = true;

      cargarPropuestaNecesidadSeleccionada(propuestaNecesidadSeleccionada);

      return navigationFaces.redirectCuNe03Redirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (modicicarPropuestaSeleccionada)", e);

    }

    return null;

  }

  /**
   *
   * @param propuestaNecesidadSeleccionada
   * @return
   */
  public String modificarPropuestaSeleccionada(PropuestaNecesidad propuestaNecesidadSeleccionada) {

    try {

      //soloLecturaPropuestaNecesidad SE DESHABILITA PARA QUE PUEDE MODIFICAR
      soloLecturaPropuestaNecesidad = false;

      cargarPropuestaNecesidadSeleccionada(propuestaNecesidadSeleccionada);

      return navigationFaces.redirectCuNe03Redirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (modicicarPropuestaSeleccionada)", e);

    }

    return null;
  }

  /**
   *
   * @return
   */
  public String agregarNuevaPropuesta() {

    try {

      //PRIMERO VALIDAMOS
      //CONSULTAMOS EL NUMERO DE PROPUESTA DE ESTE PERIODO Y UNIDAD QUE SE ENCUENTRAN EN ESTADO ACEPTADA
      int cantidadPropuestas = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);

      if ((cantidadPropuestas + listaPropuestaNecesidad.size()) == periodoSeleccionado.getCantidad()) {

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_2_lbl_propuestas_listado_cantidad_maxima_requ"));
        return null;

      }

      reiniciarDatosAdicionarModificarPropuesta();

      return navigationFaces.redirectCuNe03();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (agregarNuevaPropuesta)", e);
    }
    return null;
  }

  private void reiniciarDatosAdicionarModificarPropuesta() {

    //CARGAMOS LAS LISTA
    cargarListaArea();

    //CREAMOS INSTANCIA DE NUEVA PROPUESTA NECESIDAD
    propuestaNecesidad = new PropuestaNecesidad();

    funcionarioNecesidadSeleccionadoEliminar = null;

    areaCienciaTecnologiaSeleccionada = null;

    numeroDocumento = null;

    numeroTelefono = null;

    vistaFuncionario = new VistaFuncionario();

    listaFuncionariosNecesidad = new ArrayList<FuncionarioNecesidad>();

    nombreArchivoSubido = null;

    eventArchivoSubido = null;

    lineaSeleccionada = null;

  }

  /**
   *
   * @param propuestaNecesidadRecibida
   */
  @Override
  public void setearPropuestaNecesidadCuNe07(PropuestaNecesidad propuestaNecesidadRecibida) {

    try {

      //CONSULTAMOS LA PROPUESTA
      funcionarioNecesidadSeleccionadoEliminar = null;

      propuestaNecesidad = iPropuestaNecesidadLocal.consultar(propuestaNecesidadRecibida.getIdPropuestaNecesidad());
      propuestaNecesidad.setIdPropuestaNecesidad(null);

      //CONSULTAMOS LA LISTA DE FUNCIONARIOS
      listaFuncionariosNecesidad = iFuncionarioNecesidadLocal.getListaFuncionarioNecesidad(propuestaNecesidadRecibida.getIdPropuestaNecesidad());
      for (FuncionarioNecesidad funcionarioNecesidad : listaFuncionariosNecesidad) {

        funcionarioNecesidad.setIdFuncionarioNecesidad(null);

      }

      //CONSULTAMOS LA LINEA
      lineaSeleccionada = propuestaNecesidad.getLinea().getIdLinea();

      guardarDetallePropuestaNecesidad();

      //ACTUALIZAMOS LA PROPUESTA COPIADA, INDICANDO QUE ESTA PROPOPUESTA FUE COPIADA
      propuestaNecesidadRecibida.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_COPIADA));

      iPropuestaNecesidadLocal.guardar(propuestaNecesidadRecibida);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación - (consultarPropuestasHistoricas)", e);
    }

  }

  /**
   *
   * @return
   */
  public String consultarPropuestasHistoricas() {

    try {

      //PRIMERO VALIDAMOS
      //CONSULTAMOS EL NUMERO DE PROPUESTA DE ESTE PERIODO Y UNIDAD QUE SE ENCUENTRAN EN ESTADO ACEPTADA
      int cantidadPropuestas = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);

      if ((cantidadPropuestas + listaPropuestaNecesidad.size()) == periodoSeleccionado.getCantidad()) {

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_2_lbl_propuestas_listado_cantidad_maxima_requ"));
        return null;

      }

      List<Long> listaIdsUnidadesPeriodo = new ArrayList<Long>();
      listaIdsUnidadesPeriodo.add(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      cuNe4ConsultaPropuestaDeNecesidadFaces.fijarPropuesta(
              navigationFaces.redirectCuNe02DetallePropuesta(),
              true,
              this,
              listaIdsUnidadesPeriodo);

      return navigationFaces.redirectCuNe04ConsultarPropuestaNecesidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación - (consultarPropuestasHistoricas)", e);
    }

    return null;

  }

  /**
   *
   * @param periodoSeleccionado
   * @param listaIdEstados
   */
  private void cargarListaPropuestas(Periodo periodoSeleccionado, List<Long> listaIdEstados) {

    try {

      if (listaIdEstados != null && !listaIdEstados.isEmpty()) {

        listaPropuestaNecesidad = iPropuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoYunidadPolicialyEstados(
                periodoSeleccionado.getIdPeriodo(),
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(), listaIdEstados);
      } else {

        listaPropuestaNecesidad = iPropuestaNecesidadLocal.getPropuestaNecesidadPorPeriodoYunidadPolicial(
                periodoSeleccionado.getIdPeriodo(),
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación - (cargarListaPropuestas)", e);

    }
  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionPeriodo(UnselectEvent event) {
    periodoSeleccionado = null;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaSeleccionPeriodo(SelectEvent event) {

    if (periodoSeleccionado == null) {
      //FINALIZA EL PROCESO
      return;
    }

    try {
      //VERIFICAMOS SI TIENE PERMISO
      //VALIDAMOS
      //SE VALIDA QUE EL ACTOR AUTENTICADO SEA ' AUTORIZADO PARA REGISTRO DE NECESIDADES EN LA UNIDAD POLICIAL ' 
      boolean permisoRolAutorizado = loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.AUTORIZADO_PARA_REGISTRO_DE_NECESIDADES_EN_LA_UNIDAD_POLICIAL);
      if (permisoRolAutorizado) {

        //EL ACTOR AUTENTICADO ES ' AUTORIZADO PARA REGISTRO DE NECESIDADES EN LA UNIDAD POLICIAL ' 
        //Y LAS PROPUESTAS TENGAN ESTADO A 
        //'EN ELABORACIÓN', 'ACEPTADA´ Y 'NO ACEPTADA'
        cargarListaPropuestas(periodoSeleccionado, listaIdEstadosAutorizadoRegistroNecesidad);

        if (listaPropuestaNecesidad.isEmpty()) {
          //VERIFICAMOS SI YA SE REGISTRARON PROPUESTAS PARA ESTE PERIODO Y UNIDAD
          cargarListaPropuestas(periodoSeleccionado, null);
          //SI EXISTEN PROPUESTAS SE KE PERMITE REVISARLAS
        }
        //REDIRECCIONAMOS A LA CARGA DE LAS NECESIDADES
        navigationFaces.redirectFacesCuNe02DetallePropuesta();
        //FINALIZA EL PROCESO
        return;
      }

      //EL ACTOR AUTENTICADO ES EL JEFE DE LA UNIDAD Y  LAS PROPUESTAS TENGAN ESTADO 'EN APROBACIÓN JEFE DE UNIDAD'
      //VERIFICAMOS SI TIENE PERMISO
      //VERIFICAMOS SI EL EL ACTOR AUTENTICADO ES EL JEFE DE LA UNIDAD 
      boolean permisoRolJefeUnidad = loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL);

      //NO TIENE PERMISO
      if (!permisoRolJefeUnidad) {
        //FINALIZA EL PROCESO
        return;
      }

      //EL ACTOR AUTENTICADO ES ' AUTORIZADO PARA REGISTRO DE NECESIDADES EN LA UNIDAD POLICIAL ' 
      //Y LAS PROPUESTAS TENGAN ESTADO A 
      //APROBACION_JEFE_UNIDAD
      cargarListaPropuestas(periodoSeleccionado, listaIdEstadosJefeUnidadRegistroNecesidad);

      if (!listaPropuestaNecesidad.isEmpty()) {
        //CU-NE-05 Revisar propuestas de necesidades de investigación
        cuNe5JefeLoteNecesidadesFaces.fijarPeriodo(periodoSeleccionado, listaPropuestaNecesidad);
        navigationFaces.redirectFacesCuNe05JefeLoteNecesidades();

      } else {
        //NO EXISTEN PROPUESTAS PENDIENTE DE APROBAR
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_2_lbl_no_existen_propuestas_pend_aprobar"));
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (enviarPropuestaSeleccionPeriodo)", e);

    }
  }

  /**
   *
   * @param periodoSeleccionado
   */
  public void setPeriodoSeleccionado(Periodo periodoSeleccionado) {

    this.periodoSeleccionado = periodoSeleccionado;

  }

  public Periodo getPeriodoSeleccionado() {
    return periodoSeleccionado;
  }

  /**
   *
   */
  private void cargarListaArea() {

    try {

      //CARGAMOS LA LISTA DE AREAS
      listaAreaCienciaTecnologiaItem = UtilidadesItem.getListaSel(iAreaCienciaTecnologia.getAreaCienciaTecnologias(), "idAreaCienciaTecnologia", "nombre");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (cargarListaAreas) ", e);
    }

  }

  /**
   *
   */
  public void handleAreaCienciaTecnologiaSeleccionadaChange() {

    if (areaCienciaTecnologiaSeleccionada != null) {

      try {

        listaLineaItem = UtilidadesItem.getListaSel(iLineaLocal.getLineasPorArea(areaCienciaTecnologiaSeleccionada), "idLinea", "nombre");

      } catch (Exception e) {

        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);
      }
    } else {

      listaLineaItem = new ArrayList<SelectItem>();
    }
  }

  /**
   *
   * @return
   */
  public String enviarPropuestaNecesidad() {

    try {

      //PRIMERO VALIDAMOS
      //QUE NO SE AGREGUEN MÁS PROPUESTAS DE LA CANTIDAD MÁXIMA REQUERIDA
      if (listaPropuestaNecesidad == null || listaPropuestaNecesidad.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_2_lbl_propuesta_lista_incompleta"));
        return null;

      }

      //CONSULTAMOS EL NUMERO DE PROPUESTA DE ESTE PERIODO Y UNIDAD QUE SE ENCUENTRAN EN ESTADO ACEPTADA
      int cantidadPropuestas = iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA);

      if ((cantidadPropuestas + listaPropuestaNecesidad.size()) != periodoSeleccionado.getCantidad()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_2_lbl_propuesta_lista_incompleta"));
        return null;

      }

      short contadorConcecutivo = 0;
      //VALIDAMOS QUE LAS PROPUESTAS SE ENCUENTREN EN ESTADO ELABORACION
      for (PropuestaNecesidad unaPropuestaNecesidad : listaPropuestaNecesidad) {
        contadorConcecutivo++;
        //LAS PROPUESTAS QUE ESTAN EN ESTADO NO ACEPTADA: ES POR QUE EL USUARIO NO LAS HA ACTUALIZADO
        if (!unaPropuestaNecesidad.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION)) {
          String mensaje = keyPropertiesFactory.value("cu_ne_6_lbl_propuestas_definir_estado_envia_jefe_unidad", new String[]{String.valueOf(contadorConcecutivo)});
          adicionaMensajeError(mensaje);
          //FINALIZAMOS EL PROCESO
          return null;
        }
      }

      //EL CONCESITIVO SOLO APLICA CUANDO SE CREA LA PROPUESTA.
      //CUANDO SE ESTA ACTUALIZANDO LA PROPUESTA NO SE ACTUALIZAN LOS CONCECUTIVO
      contadorConcecutivo = 0;
      //El sistema asigna a cada propuesta enviado el estado 'En aprobación Jefe de Unidad'
      for (PropuestaNecesidad unaPropuestaNecesidad : listaPropuestaNecesidad) {

        //LAS PROPUESTAS QUE ESTEN EN ESTADO ACEPTADO NO SE APLICAN CAMBIOS, ESTAN QUEDAN SON SU MISMO ESTADO
        if (unaPropuestaNecesidad.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ACEPTADA)) {
          continue;
        }
        //LAS PROPUESTAS QUE ESTAN EN ESTADO NO ACEPTADA: ES POR QUE EL USUARIO NO LAS HA ACTUALIZADO
        if (unaPropuestaNecesidad.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA)) {
          adicionaMensajeError("La propuesta con tema: \"".concat(unaPropuestaNecesidad.getTema().trim().concat("\" debe estar en estado Elaboración. Modifique la propuesta.")));
          //FINALIZAMOS EL PROCESO
          return null;
        }
        //LA PROPUESTA ESTA EN ESTADO ELABORACION  
        if (unaPropuestaNecesidad.getIdPropuestaNecesidad() == null) {
          //EL CONCESITIVO SOLO APLICA CUANDO SE CREA LA PROPUESTA.
          //CUANDO SE ESTA ACTUALIZANDO LA PROPUESTA NO SE ACTUALIZAN LOS CONCECUTIVO
          unaPropuestaNecesidad.setConcecutivo(++contadorConcecutivo);
        }

        unaPropuestaNecesidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBACION_JEFE_UNIDAD));
        unaPropuestaNecesidad.setIdentificadorUsuarioEnvio(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      }

      iPropuestaNecesidadLocal.guardarListaPropuestaCreaEjecutorNecesidad(listaPropuestaNecesidad);
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_2_lbl_envio_propuesta_jefe_unidad"));

      try {

        iMailSessionBean.enviarMail_RolUnidadPolicial(
                IConstantes.CU_NE_02_JEFES_UNIDAD,
                null,
                null,
                IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      } catch (Exception e) {

        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-02 - Mail", e);

      }

      //INICIAMOS EL CU
      init();
      //RETORNAMOS A LA VENTANA DE PERIODOS
      return navigationFaces.redirectCuNe02Redirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (enviarPropuestaNecesidad)", e);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String guardarPropuesta() {

    try {

      //VERIFICAMOS SI EL USUARIO INGRESO ALGUN ARCHIVO
      if (eventArchivoSubido != null) {
        //VERIFICAMOS SI LA RUTA FISICA DONDE SE ALMACENAN LOS ARCHIVOS EXISTE
        //Y LOS PERMISOS SON VALIDOS
        File directorioFinal = null;

        try {

          directorioFinal = new File(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte"));

        } catch (NullPointerException e) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
          return null;
        }

        if (!directorioFinal.exists()) {
          adicionaMensajeError("ERROR...ARCHIVO O DIRECTORIO NO EXISTE: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
          return null;
        }

        if (directorioFinal.isFile()) {
          adicionaMensajeError("ERROR...LA RUTA DEBE SER UN DIRECTORIO, MAS NO UN ARCHIVO: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
          return null;
        }

        if (!directorioFinal.canWrite()) {
          adicionaMensajeError("ERROR...NO EXISTEN PRIVILEGIOS DE ESCRIBIR EN EL DIRECTORIO: ".concat(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte")));
          return null;
        }
      }

      //ALMACENAMOS LA PROPUESTA
      boolean ocurrieronErrores = guardarDetallePropuestaNecesidad();
      if (ocurrieronErrores) {
        //OCURRIERON ERRORES 
        return null;
      }

      //LO REDIRECCIONAMOS A MOSTRAR DETALLE PROPUESTA
      return navigationFaces.redirectCuNe02DetallePropuesta();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (guardarPropuesta) ", e);

    }

    return null;

  }

  /**
   *
   * @return si ocurrieron errores al lamacenar la propuesta
   * @throws Exception
   */
  private boolean guardarDetallePropuestaNecesidad() throws Exception {

    //VERIFICAMOS SI ESTAMOS MODIFICANDO O CREANDO UN NUEVO REGISTRO
    if (propuestaNecesidad.getIdPropuestaNecesidad() == null) {

      //CREANDO UN NUEVO REGISTRO
      if (listaFuncionariosNecesidad == null || listaFuncionariosNecesidad.isEmpty()) {
        adicionaMensajeError("No se han ingresado funcionarios  que proponen el tema.");
        return true;
      }
      if (periodoSeleccionado == null) {
        adicionaMensajeError("No se seleccionado un periodo válido.");
        return true;
      }

      String[] archivo = new String[]{null, null};
      if (eventArchivoSubido != null) {
        archivo = almacenarArchivoFisico();
      }

      propuestaNecesidad.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));
      propuestaNecesidad.setFechaRegistro(new Date());
      propuestaNecesidad.setFuncionarioNecesidadList(listaFuncionariosNecesidad);
      propuestaNecesidad.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      propuestaNecesidad.setPeriodo(periodoSeleccionado);
      propuestaNecesidad.setLinea(new Linea(lineaSeleccionada));
      propuestaNecesidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION));
      propuestaNecesidad.setNombreArchivo(archivo[0]);//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      propuestaNecesidad.setNombreArchivoFisico(archivo[1]);//NOMBRE DEL ARCHIVO COMO FUE GUARDADO
      //CONSULTAMOS CUANTAS PROPUESTAS PARA ESTE PERIODO SE HAN INSERTADO
      //PARA GENERAR EL CONCECUTIVO
      int cantidadPropuesta = iPropuestaNecesidadLocal.contarPropuestaNecesidadPorPeriodoyUnidadPolicial(
              periodoSeleccionado.getIdPeriodo(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());
      //INCREMENTAMOS EL CONCECUTIVO
      cantidadPropuesta += 1;
      propuestaNecesidad.setConcecutivo((short) cantidadPropuesta);

    } else {

      String[] archivo = new String[]{null, null};
      if (eventArchivoSubido != null) {

        archivo = almacenarArchivoFisico();
        if (archivo == null) {
          //OCURRIERON ERRORES 
          return true;
        }
      }
      //MODIFICANDO
      propuestaNecesidad.setFechaModificacion(new Date());
      propuestaNecesidad.setIdentificadorUsuarioModif(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
      propuestaNecesidad.setLinea(new Linea(lineaSeleccionada));
      propuestaNecesidad.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));
      propuestaNecesidad.setFuncionarioNecesidadList(listaFuncionariosNecesidad);
      propuestaNecesidad.setNombreArchivo(archivo[0] != null ? archivo[0] : propuestaNecesidad.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      propuestaNecesidad.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : propuestaNecesidad.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO
      propuestaNecesidad.setConstantes(new Constantes(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION));
    }

    propuestaNecesidad = iPropuestaNecesidadLocal.guardarPropuestaCreaEjecutorNecesidad(propuestaNecesidad, null, propuestaNecesidad.getUnidadPolicial().getIdUnidadPolicial());

    //ACTUALIZAMOS LA LISTA PROPUESTAS 
    cargarListaPropuestas(periodoSeleccionado, listaIdEstadosAutorizadoRegistroNecesidad);

    //MENSAJE SATISFACCION
    adicionaMensajeInformativo(keyPropertiesFactory.value("cu_ne_2_lbl_necesidad_inv_registrada"));

    //LA PROPUESTA FUE ALMACENADA CORRECTAMENTE
    return false;
  }

  /**
   * Retorna true o false dependiendo si el archivo fue creado correctamente
   *
   * @return
   */
  private String[] almacenarArchivoFisico() {

    //ALMACENAMOS EL ARCHIVO EN CASO EXISTA
    try {

      if (eventArchivoSubido != null) {

        String nombreArchivoOriginal = eventArchivoSubido.getFile().getFileName();
        String extension = "";
        int i = nombreArchivoOriginal.lastIndexOf('.');
        if (i > 0) {
          extension = nombreArchivoOriginal.substring(i);
        }
        String nombreArchivoFisico = "PROPUESTA_NECESIDAD".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación( guardarPropuesta )", e);
    }

    return null;

  }

  /**
   *
   */
  public void agregarFuncionario() {

    try {

      if (vistaFuncionario == null || vistaFuncionario.getIdentificacion() == null) {

        adicionaMensajeError("Primero ingrese un documento de identidad válido");
        return;

      }
      if (numeroTelefono == null || numeroTelefono.trim().length() == 0) {

        adicionaMensajeError("Ingrese un número de telefono correcto");
        return;

      }

      FuncionarioNecesidad funcionarioNecesidad = new FuncionarioNecesidad(
              null, vistaFuncionario.getIdentificacion(),
              vistaFuncionario.getNombreCompleto(),
              vistaFuncionario.getCorreo(),
              vistaFuncionario.getGrado(),
              numeroTelefono,
              vistaFuncionario.getCargo());
      funcionarioNecesidad.setPropuestaNecesidad(propuestaNecesidad);

      listaFuncionariosNecesidad.add(funcionarioNecesidad);

      vistaFuncionario = new VistaFuncionario();
      numeroDocumento = null;
      numeroTelefono = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (agregarFuncionario) ", e);

    }

  }

  /**
   *
   */
  public void buscarFuncionario() {

    vistaFuncionario = new VistaFuncionario();
    numeroTelefono = null;

    if (numeroDocumento == null || numeroDocumento.trim().length() == 0) {
      adicionaMensajeError("El número de documento esta vacio.");
      return;
    }

    try {

      //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA
      if (listaFuncionariosNecesidad != null && !listaFuncionariosNecesidad.isEmpty()) {
        for (FuncionarioNecesidad unFuncionarioNecesidad : listaFuncionariosNecesidad) {
          if (unFuncionarioNecesidad.getIdentificacion().equals(numeroDocumento)) {
            adicionaMensajeError("La identificación ingresada ya se encuentra registra como funcionario: ".concat(unFuncionarioNecesidad.getNombreCompleto()));
            return;
          }
        }

      }

      vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(numeroDocumento.trim());
      if (vistaFuncionario == null) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_2_lbl_identificacion_no_encontrada"));
        vistaFuncionario = new VistaFuncionario();
        return;
      }

      numeroTelefono = vistaFuncionario.getTelefono();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación (handleAreaCienciaTecnologiaSeleccionadaChange) ", e);

    }

  }

  public List<SelectItem> getListaAreaCienciaTecnologiaItem() {
    return listaAreaCienciaTecnologiaItem;
  }

  public IPeriodoLocal getiPeriodoLocal() {
    return iPeriodoLocal;
  }

  public void setiPeriodoLocal(IPeriodoLocal iPeriodoLocal) {
    this.iPeriodoLocal = iPeriodoLocal;
  }

  public List<PropuestaNecesidad> getListaPropuestaNecesidad() {
    return listaPropuestaNecesidad;
  }

  public void setListaPropuestaNecesidad(List<PropuestaNecesidad> listaPropuestaNecesidad) {
    this.listaPropuestaNecesidad = listaPropuestaNecesidad;
  }

  public PropuestaNecesidad getPropuestaNecesidad() {
    return propuestaNecesidad;
  }

  public void setPropuestaNecesidad(PropuestaNecesidad propuestaNecesidad) {
    this.propuestaNecesidad = propuestaNecesidad;
  }

  public Long getAreaCienciaTecnologiaSeleccionada() {
    return areaCienciaTecnologiaSeleccionada;
  }

  public void setAreaCienciaTecnologiaSeleccionada(Long areaCienciaTecnologiaSeleccionada) {
    this.areaCienciaTecnologiaSeleccionada = areaCienciaTecnologiaSeleccionada;
  }

  public Long getLineaSeleccionada() {
    return lineaSeleccionada;
  }

  public void setLineaSeleccionada(Long lineaSeleccionada) {
    this.lineaSeleccionada = lineaSeleccionada;
  }

  public List<SelectItem> getListaLineaItem() {
    return listaLineaItem;
  }

  public void setListaLineaItem(List<SelectItem> listaLineaItem) {
    this.listaLineaItem = listaLineaItem;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public VistaFuncionario getVistaFuncionario() {

    if (vistaFuncionario == null) {
      vistaFuncionario = new VistaFuncionario();
    }

    return vistaFuncionario;
  }

  public String getNumeroTelefono() {
    return numeroTelefono;
  }

  public void setNumeroTelefono(String numeroTelefono) {
    this.numeroTelefono = numeroTelefono;
  }

  public List<FuncionarioNecesidad> getListaFuncionariosNecesidad() {
    return listaFuncionariosNecesidad;
  }

  /**
   *
   * @param event
   */
  public void handleFileUpload(FileUploadEvent event) {
    try {

      eventArchivoSubido = event;
      nombreArchivoSubido = event.getFile().getFileName();
      adicionaMensajeInformativo("El archivo fue cargado exitosamente: ".concat(nombreArchivoSubido));

    } catch (Exception e) {
      adicionaMensajeError(e.getMessage());
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación( handleFileUpload )", e);
    }
  }

  public String getNombreArchivoSubido() {
    return nombreArchivoSubido;
  }

  public void setNombreArchivoSubido(String nombreArchivoSubido) {
    this.nombreArchivoSubido = nombreArchivoSubido;
  }

  /**
   *
   * @return
   */
  public String getNombreUnidadPolicial() {
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return "";
    }
    return loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getSiglaFisicaYnombreUnidad();
  }

  public ListGenericDataModel getListaPeriodoModel() {
    return listaPeriodoModel;
  }

  public void setListaPeriodoModel(ListGenericDataModel listaPeriodoModel) {
    this.listaPeriodoModel = listaPeriodoModel;
  }

  public List<Comentario> getListaComentarioNecesidad() {
    return listaComentarioNecesidad;
  }

  public void setListaComentarioNecesidad(List<Comentario> listaComentarioNecesidad) {
    this.listaComentarioNecesidad = listaComentarioNecesidad;
  }

  /**
   *
   * @param unaPropuestaNecesidadMostrar
   * @return
   */
  public boolean mostrarBtnModificarPropuesta(PropuestaNecesidad unaPropuestaNecesidadMostrar) {

    return unaPropuestaNecesidadMostrar.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION)
            || unaPropuestaNecesidadMostrar.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA);

  }

  /**
   *
   * @return
   */
  public boolean isMostrarBtnEnviarPropuesta() {

    if (listaPropuestaNecesidad == null || listaPropuestaNecesidad.isEmpty()) {

      return false;

    }

    for (PropuestaNecesidad unaPropuesta : listaPropuestaNecesidad) {

      if (unaPropuesta.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION)
              || unaPropuesta.getConstantes().getIdConstantes().equals(IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_NO_ACEPTADA)) {
        //MIENTRAS EL ESTADO DE LA PROPUESTA SEA "EN ELABORACION" O "NO ACEPTADA"
        continue;
      }
      //ESTADO DIFERENTE A "EN ELABORACION" O "NO ACEPTADA"
      return false;

    }
    return true;
  }

  /**
   *
   * @return
   */
  public boolean isMostrarBtnEnviar() {

    //SI LA LISTA ES NULA O ESTA VACIA
    if (listaPropuestaNecesidad == null || listaPropuestaNecesidad.isEmpty()) {
      return false;
    }

    //VERIFICAMOS SI EXISTE ALGUNA PROPUESTA NECESIDAD RECHAZADA O EN ELABORACION
    for (PropuestaNecesidad unaPropuestaNecesidadMostrar : listaPropuestaNecesidad) {

      if (mostrarBtnModificarPropuesta(unaPropuestaNecesidadMostrar)) {
        return true;
      }
    }
    return false;
  }

  /**
   *
   * @return
   */
  public boolean isMostrarLinkAgregarNuevaPropuesta() {

    if (listaPropuestaNecesidad != null && listaPropuestaNecesidad.size() >= periodoSeleccionado.getCantidad()) {

      return false;

    }
    return true;
  }

  public boolean isSoloLecturaPropuestaNecesidad() {
    return soloLecturaPropuestaNecesidad;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return propuestaNecesidad != null && propuestaNecesidad.getNombreArchivo() != null && propuestaNecesidad.getNombreArchivoFisico() != null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (propuestaNecesidad != null && propuestaNecesidad.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_ne_2_dir_file_archivo_soporte") + propuestaNecesidad.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, propuestaNecesidad.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación( getDownloadContentProperty )", e);
    }
    return null;
  }

  public FuncionarioNecesidad getFuncionarioNecesidadSeleccionadoEliminar() {
    return funcionarioNecesidadSeleccionadoEliminar;
  }

  public void setFuncionarioNecesidadSeleccionadoEliminar(FuncionarioNecesidad funcionarioNecesidadSeleccionadoEliminar) {
    this.funcionarioNecesidadSeleccionadoEliminar = funcionarioNecesidadSeleccionadoEliminar;
  }

  /**
   *
   * @param e
   */
  public void eliminarFuncionario(ActionEvent ev) {

    if (funcionarioNecesidadSeleccionadoEliminar == null) {
      return;
    }

    try {

      iFuncionarioNecesidadLocal.eliminarFuncionarioNecesidad(funcionarioNecesidadSeleccionadoEliminar.getIdFuncionarioNecesidad());

      listaFuncionariosNecesidad = iFuncionarioNecesidadLocal.getListaFuncionarioNecesidad(propuestaNecesidad.getIdPropuestaNecesidad());

      adicionaMensajeInformativo("Funcionario eliminado correctamente");

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-02 Gestionar bloque de propuestas de necesidades de investigación", e);

    }
  }

}
