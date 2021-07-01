package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.ProyectoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IComentarioLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Comentario;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.util.JSFUtils;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuCo2ParticipaConvocatoriasFaces")
@javax.enterprise.context.SessionScoped
public class CuCo2ParticipaConvocatoriasFaces extends JSFUtils implements Serializable {

  private ListGenericDataModel listaConvotariaModel;
  private Periodo convotariaSeleccionado;

  @javax.inject.Inject
  private CuPr1ProyectoFaces cuPr1ProyectoFaces;

  @javax.inject.Inject
  private CuCo3RevisarPropuestaProyectoFaces cuCo3RevisarPropuestaProyectoFaces;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuValidarInformacionConvocatoriaYProyectoFaces cuValidarInformacionConvocatoriaYProyectoFaces;

  @EJB
  private IPeriodoLocal iPeriodoLocal;
  @EJB
  private IProyectoLocal iProyectoLocal;
  @EJB
  private IComentarioLocal iComentarioLocal;
  @EJB
  private IMailSessionLocal iMailSessionBean;

  private List<Comentario> listaComentarios;
  private List<ProyectoDTO> listaProyectoPorPeriodo;

  private static final List<Long> listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada = new ArrayList<Long>(2);

  static {

    listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION);
    listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD);
    listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD);
    listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_ENVIADA_A_VICIN);

  }

  private static final List<Long> listaIdEstadosPropuestaConvocatoriaEstadoRevisionJefeUnidad = new ArrayList<Long>(2);

  static {

    listaIdEstadosPropuestaConvocatoriaEstadoRevisionJefeUnidad.add(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD);

  }
  private boolean seleccionarTodaspropuesta;

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();
    return navigationFaces.redirectCuCo2ParticipaConvocatoriasRedirect();

  }

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      seleccionarTodaspropuesta = false;
      convotariaSeleccionado = null;

      List<Periodo> listaConvocados = iPeriodoLocal.getPeriodosFechaActualEntreFechaInicioYfechaFinUnidadPolicialYestadoConvocatoriaYtipoConvocatoria(
              new Date(),
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
              IConstantes.TIPO_ESTADO_CONVOCATORIA_PUBLICADA,
              IConstantes.TIPO_CONVOCATORIA_GESTION_PARA_FINANCIACION);

      listaConvotariaModel = new ListGenericDataModel(listaConvocados);

    } catch (JpaDinaeException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (init)", e);
    }

  }

  /**
   * Ya no ha seleccionado un periodo
   *
   * @param event
   */
  public void noSeleccionConvocatoria(UnselectEvent event) {
    convotariaSeleccionado = null;
  }

  /**
   * Se ha seleccionado un periodo
   *
   * @param event
   */
  public void enviarPropuestaSeleccionConvocatoria(SelectEvent event) {

    if (convotariaSeleccionado == null) {
      //FINALIZA EL PROCESO
      return;
    }

    try {

      //VERICAMOS ROL DEL USUARIO
      //AUTORIZADO PARA REGISTRAR PROPUESTAS EN CONVOCATORIAS EN LA UNIDAD POLICIAL, JEFE DE LA UNIDAD POLICIAL.
      if (loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.AUTORIZADO_PARA_REGISTRAR_PROPUESTAS_EN_CONVOCATORIAS_EN_LA_UNIDAD_POLICIAL)) {

        //PRIMERO VERIFICAMOS SI PARA ESTA PROPUESTA NO SE HA REGISTRADO CONVOCATORIAS
        /*int cuenta = iProyectoLocal.cuentaNumeroPropuestasConvocatoriaPorConvocatoriaYunidadPolicial(
                        convotariaSeleccionado.getIdPeriodo(),
                        loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial() );
                
                if( cuenta == 0){
                    //NO EXISTEN PROPUESTAS DE CONVOCATORIAS CREADAS PARA ESTE PROPUESTA
                    navigationFaces.redirectFacesCuCo2DetalleParticipaConvocatorias();
                    
                }
                
                if(listaProyectoPorPeriodo == null || listaProyectoPorPeriodo.isEmpty() ){
                    //NO EXISTEN PROPUESTA DE CONVOCATORIA CON ESTADO: 
                    //CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION O CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBACION_JEFE_UNIDAD
                    //PARA CONSULTAR O MODIFICAR
                    adicionaMensajeError(keyPropertiesFactory.value("cu_co_2_lbl_msg_error_no_hay_propuestas_para_modi_o_consul") );
                    return;
                }
                
                boolean continua = false;
                //EL SISTEMA VALIDA QUE LAS PROPUESTAS TENGAN ESTADO ‘En elaboración’ o ‘No aceptada por Jefe de Unidad’
                for (ProyectoDTO unProyectoDTO : listaProyectoPorPeriodo) {
                    
                    if(unProyectoDTO.getIdEstado().equals( IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION )
                             || unProyectoDTO.getIdEstado().equals( IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD ) ){
                        
                        continua = true;
                        break;
                    }
                }
                //SI NO CONTINUA Y QUE LA LISTA CONTENGA VALORES
                if( !continua ){
                    //NO EXISTEN PROPUESTA DE CONVOCATORIA CON ESTADO: 
                    //CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_EN_ELABORACION O CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_APROBACION_JEFE_UNIDAD
                    //PARA CONSULTAR O MODIFICAR
                    adicionaMensajeError( keyPropertiesFactory.value("cu_co_2_lbl_msg_error_no_hay_propuestas_para_modi_o_consul") );
                    return;
                }*/
        //CARGAMOS LOS PROYECTOS ASOCIADOS A DICHO PERIODO(CONVOCATORIA)
        cargarListaPropuestaConvocatorias();

        navigationFaces.redirectFacesCuCo2DetalleParticipaConvocatorias();

      } else if (loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL)) {

        //CONTAMOS LAS PROPUESTAS DE CONVOCATORIA QUE EXISTEN EN ESTADO: En revisión del Jefe de la Unidad
        List<Proyecto> listaPropuestasProyecto = iProyectoLocal.getPropuestasConvocatoriaPorConvocatoriaYunidadPolicialYListaIdEstado(
                convotariaSeleccionado.getIdPeriodo(),
                loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
                listaIdEstadosPropuestaConvocatoriaEstadoRevisionJefeUnidad);

        if (listaPropuestasProyecto.isEmpty()) {
          adicionaMensajeError(keyPropertiesFactory.value("cu_co_2_lbl_error_no_existen_convocat_est_revision_jefe_unid"));
          return;
        }
        //REDIRECCIONAMOS AL CU-CO-03
        cuCo3RevisarPropuestaProyectoFaces.initReturnCU_Desde_CU_CO_2(convotariaSeleccionado, listaPropuestasProyecto);
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (enviarPropuestaSeleccionPeriodo)", e);

    }

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarPropuestasJefeUnidad(ActionEvent actionEvent) {

    try {

      if (convotariaSeleccionado.getIdPeriodo() == null) {
        return;
      }

      if (listaProyectoPorPeriodo == null || listaProyectoPorPeriodo.isEmpty()) {
        adicionaMensajeError(keyPropertiesFactory.value("cu_co_2_lbl_msg_error_nohay_propuestas_enviar"));
        return;
      }

      //POR CADA PROPUESTA VALIDAMOS
      List<Long> listaProuestasAactualizar = new ArrayList<Long>();

      for (ProyectoDTO unProyectoDTO : listaProyectoPorPeriodo) {

        if (!unProyectoDTO.getIdEstado().equals(IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION)) {

          //adicionaMensajeError( keyPropertiesFactory.value( "cu_co_2_lbl_propuestas_definir_estado_envia_jefe_unidad", new Object[]{ String.valueOf( filaPropuesta )} ));
          continue;
        }
        //ADICIONAMOS LA PROPUESTA CONVOCATORIA QUE SE DESEA ACTUALIZAR(CAMBIAR DE ESTADO )
        listaProuestasAactualizar.add(unProyectoDTO.getId());
      }

      if (listaProuestasAactualizar.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_co_2_lbl_msg_error_nohay_propuestas_enviar"));
        return;
      }

      //VALIDAMOS LA INFORMACION DE LAS PESTANIAS
      if (!cuValidarInformacionConvocatoriaYProyectoFaces.validarInformacionValidaConvocatoriaYproyecto(listaProuestasAactualizar)) {
        //LA VALIDACION HA FALLADO
        return;
      }

      //CAMBIA EL ESTADO DEL COMPROMISO A ‘En revisión del Jefe’ 
      iProyectoLocal.actualizarEstadoListaIdProyecto(listaProuestasAactualizar,
              IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD);

      //ENVÍA UN CORREO ELECTRÓNICO AL JEFE DE UNIDAD.
      iMailSessionBean.enviarMail_RolUnidadPolicial(
              IConstantes.CU_CO_02_ENVIO_PROPUESTA_REVISION,
              null,
              null,
              IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL,
              loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      //REDIRECCIONAMOS
      navigationFaces.redirectFacesCuCo2ParticipaConvocatorias();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (enviarPropuestas)", e);

    }
  }

  /**
   * Metodo que carga las propuestas de convocatoria segun la unidad del usuario logueado
   *
   * @throws Exception
   */
  private void cargarListaPropuestaConvocatorias() throws Exception {

    listaProyectoPorPeriodo = iProyectoLocal.getListaProyectoDTOPorPeriodoYestadoYunidadPolicial(
            convotariaSeleccionado.getIdPeriodo(),
            listaIdEstadosPropuestaConvocatoriaEstadosElaboracionYnoAceptada,
            loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

  }

  /**
   *
   * @return
   */
  public String agregarNuevoProyecto() {

    try {

      Proyecto nuevoProyecto = new Proyecto();
      nuevoProyecto.setPeriodo(convotariaSeleccionado);
      nuevoProyecto.setUnidadPolicial(new UnidadPolicial(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial()));

      return cuPr1ProyectoFaces.initReturnCU_Desde_CU_CO_02(nuevoProyecto);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (agregarNuevoProyecto)", e);

    }

    return null;
  }

  public String consultarProyectoSeleccionado(ProyectoDTO proyectoDTO) {

    try {

      return cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_CO_02(proyectoDTO.getId());

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (consultarProyectoSeleccionado)", e);

    }

    return null;

  }

  public String modificarProyectoSeleccionado(ProyectoDTO proyectoDTO) {

    try {

      Proyecto proyectoSeleccionado = iProyectoLocal.obtenerProyectoPorId(proyectoDTO.getId());
      return cuPr1ProyectoFaces.initReturnCU_Desde_CU_CO_02(proyectoSeleccionado);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (modificarProyectoSeleccionado)", e);

    }

    return null;
  }

  public Proyecto getProyectoSeleccionadoInformacionComentario() {
    return proyectoSeleccionadoInformacionComentario;
  }

  private Proyecto proyectoSeleccionadoInformacionComentario;

  public String verComentariosJefeProyectoSeleccionado(ProyectoDTO proyectoDTO) {

    try {

      proyectoSeleccionadoInformacionComentario = iProyectoLocal.obtenerProyectoPorId(proyectoDTO.getId());
      listaComentarios = iComentarioLocal.getComentarioProyectoPorProyecto(proyectoDTO.getId());

      return navigationFaces.redirectCuCo2ComentariosProyectoRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-CO-02 Participar en convocatoria para financiación (verComentariosJefeProyectoSeleccionada)", e);

    }
    return null;

  }

  /**
   *
   * @param proyectoDTO
   * @return
   */
  public boolean isPuedeModificarConvocatoria(ProyectoDTO proyectoDTO) {

    if (proyectoDTO == null) {
      return false;
    }

    return IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_ELABORACION.equals(proyectoDTO.getIdEstado())
            || IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD.equals(proyectoDTO.getIdEstado());
  }

  /**
   *
   * @param proyectoDTO
   * @return
   */
  public boolean isPuedeConsultarComentarioConvocatoria(ProyectoDTO proyectoDTO) {

    if (proyectoDTO == null) {
      return false;
    }

    return IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_NO_ACEPTADO_POR_JEFE_DE_UNIDAD.equals(proyectoDTO.getIdEstado());
  }

  /**
   *
   * @param proyectoDTO
   * @return
   */
  public boolean isPuedeConsultarConvocatoria(ProyectoDTO proyectoDTO) {

    if (proyectoDTO == null) {
      return false;
    }

    return IConstantes.TIPO_ESTADO_PROPUESTA_CONVOCATORIA_EN_REVISION_DE_JEFE_DE_LA_UNIDAD.equals(proyectoDTO.getIdEstado());
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return convotariaSeleccionado != null && convotariaSeleccionado.getNombreArchivo() != null && convotariaSeleccionado.getNombreArchivoFisico() != null;
  }

  public boolean isMostrarLinkDescargaArchivoReporte() {
    return convotariaSeleccionado != null && convotariaSeleccionado.getNombreArchivoFisicoPropuestaConvocatoriaReporte() != null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {

    try {

      if (convotariaSeleccionado != null && convotariaSeleccionado.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_co_1_dir_file_archivo_soporte") + convotariaSeleccionado.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, convotariaSeleccionado.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentPropertyReporte() {

    try {

      if (convotariaSeleccionado != null && convotariaSeleccionado.getNombreArchivoFisicoPropuestaConvocatoriaReporte() != null) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + convotariaSeleccionado.getNombreArchivoFisicoPropuestaConvocatoriaReporte();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, convotariaSeleccionado.getNombreArchivoOriginalPropuestaConvocatoriaReporte());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
  }

  public void selecccionarTodas() {

    if (listaProyectoPorPeriodo == null) {
      return;
    }
    for (ProyectoDTO proyectoDTO : listaProyectoPorPeriodo) {
      proyectoDTO.setSelecconado(seleccionarTodaspropuesta);
    }
  }

  public boolean isSeleccionarTodaspropuesta() {
    return seleccionarTodaspropuesta;
  }

  public void setSeleccionarTodaspropuesta(boolean seleccionarTodaspropuesta) {
    this.seleccionarTodaspropuesta = seleccionarTodaspropuesta;
  }

  public boolean isMostrarBtnEnviar() {

    if (listaProyectoPorPeriodo == null || listaProyectoPorPeriodo.isEmpty()) {
      return false;
    }

    for (ProyectoDTO unProyectoDTO : listaProyectoPorPeriodo) {
      //SI EXISTEN ALGUNA PROPUES QUE SE PUEDE MODIFICAR, SE HABILITA EN BTN DE ENVIAR
      if (isPuedeModificarConvocatoria(unProyectoDTO)) {
        return true;
      }
    }

    return false;
  }

  public ListGenericDataModel getListaConvotariaModel() {
    return listaConvotariaModel;
  }

  public void setListaConvotariaModel(ListGenericDataModel listaConvotariaModel) {
    this.listaConvotariaModel = listaConvotariaModel;
  }

  public Periodo getConvotariaSeleccionado() {
    return convotariaSeleccionado;
  }

  public void setConvotariaSeleccionado(Periodo convotariaSeleccionado) {
    this.convotariaSeleccionado = convotariaSeleccionado;
  }

  public int getNumeroRegistro() {

    if (listaConvotariaModel == null) {
      return 0;
    }
    return listaConvotariaModel.getRowCount();
  }

  /**
   *
   * @return
   */
  public String getNombreUnidadPolicial() {
    return loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getNombre();
  }

  public List<ProyectoDTO> getListaProyectoPorPeriodo() {
    return listaProyectoPorPeriodo;
  }

  public void setListaProyectoPorPeriodo(List<ProyectoDTO> listaProyectoPorPeriodo) {
    this.listaProyectoPorPeriodo = listaProyectoPorPeriodo;
  }

  public List<Comentario> getListaComentarios() {
    return listaComentarios;
  }

  public void setListaComentarios(List<Comentario> listaComentarios) {
    this.listaComentarios = listaComentarios;
  }

}
