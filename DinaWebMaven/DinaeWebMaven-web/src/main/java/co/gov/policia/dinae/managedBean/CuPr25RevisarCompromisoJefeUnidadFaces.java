package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.CompromisoProyectoDTO;
import co.gov.policia.dinae.dto.DetalleMailCompromisoDTO;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ICompromisoProyectoLocal;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.CompromisoProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr25RevisarCompromisoJefeUnidad")
@javax.enterprise.context.SessionScoped
public class CuPr25RevisarCompromisoJefeUnidadFaces extends JSFUtils implements Serializable {

  private ListGenericDataModel<CompromisoProyectoDTO> listaCompromisoProyectoDTO;
  private CompromisoProyectoDTO compromisoProyectoDTOSeleccionado;
  private List<SelectItem> listaItemResultadoCompromiso;

  @javax.inject.Inject
  private CuPr11ConsultarDetalleCompromisoFaces cuPr11ConsultarDetalleCompromisoFaces;

  @javax.inject.Inject
  private CuPr6ConsultarDetalleProyectoInvestigacion cuPr6ConsultarDetalleProyectoInvestigacion;

  @javax.inject.Inject
  private CuPr21RegistrarPlanDeTrabajoFaces cuPr21RegistrarPlanDeTrabajoFaces;

  @javax.inject.Inject
  private CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

  @EJB
  private IConstantesLocal iConstantesLocal;
  @EJB
  private ICompromisoProyectoLocal iCompromisoProyectoLocal;
  @EJB
  private ICompromisoImplementacionLocal iCompromisoImplementacionLocal;
  @EJB
  private IMailSessionLocal iMailSessionLocal;

  private static final List<Long> listaIdEstadoCompromiso = new ArrayList<Long>();

  static {
    listaIdEstadoCompromiso.add(IConstantes.ESTADO_COMPROMISO_PROYECTO_EN_REVISION_DEL_JEFE);
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      listaItemResultadoCompromiso = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesDTOPorTipo(IConstantes.TIPO_ESTADO_RESULTADO_COMPROMISO_PROYECTO),
              "idConstantes", "valor");

      cargarListaCompromisos();

      return navigationFaces.redirectCuPr25RevisarCompromisoJefeUnidad();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);

    }

    return null;

  }

  @javax.annotation.PostConstruct
  public void init() {

    try {

      listaItemResultadoCompromiso = null;
      compromisoProyectoDTOSeleccionado = null;
      listaCompromisoProyectoDTO = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);
    }

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaCompromisos() throws Exception {

    listaCompromisoProyectoDTO = new ListGenericDataModel<CompromisoProyectoDTO>(
            iCompromisoProyectoLocal.getCompromisoProyectoDTOPorUnidadPolicial(
                    loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial(),
                    listaIdEstadoCompromiso)
    );

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarGrupoInvestigacion(ActionEvent actionEvent) {

    try {

      //CREAMOS LA LISTA DE LOS CORREOS A ENVIAR
      List<DetalleMailCompromisoDTO> listaDetalleMailCompromisoDTO = new ArrayList<DetalleMailCompromisoDTO>();

      List<CompromisoProyectoDTO> listaCompromisosAceptadosEnviarGrupo = new ArrayList<CompromisoProyectoDTO>();
      //VERIFICAMOS QUE COMPROMISOS ESTAN EN ESTADO NO ACEPTADO Y QUE TENGAN SU RESPECTIVO COMENTARIO
      int contador = 0;
      for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

        contador += 1;
        if (IConstantes.TIPO_ESTADO_RESULTADO_COMPROMISO_PROYECTO_NO_ACEPTADO.equals(unCompromisoProyectoDTO.getResultadoTemporal())) {

          if (unCompromisoProyectoDTO.getComentario() == null || unCompromisoProyectoDTO.getComentario().trim().length() == 0) {

            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_25_lbl_compromiso_no_comentario", new String[]{String.valueOf(contador)}));
            return;
          }

          listaCompromisosAceptadosEnviarGrupo.add(unCompromisoProyectoDTO);

          //El sistema envía mail al Responsable de proyectos de investigación en la unidad policial 
          DetalleMailCompromisoDTO detalleMailCompromisoDTOresponsable = new DetalleMailCompromisoDTO();
          detalleMailCompromisoDTOresponsable.getParametrosAsunto().put("{_parametro1_}", unCompromisoProyectoDTO.getCompromiso());
          detalleMailCompromisoDTOresponsable.getParametrosContenido().put("{_parametro1_}", unCompromisoProyectoDTO.getCompromiso());
          detalleMailCompromisoDTOresponsable.setIdRol(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
          detalleMailCompromisoDTOresponsable.setIdUnidadPolicial(unCompromisoProyectoDTO.getIdUnidadPolicial());

          listaDetalleMailCompromisoDTO.add(detalleMailCompromisoDTOresponsable);

        }

      }

      if (listaCompromisosAceptadosEnviarGrupo.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_25_lbl_mensaje_error_noexisten_propuesta_envia_grupo_inves"));
        return;
      }

      iCompromisoProyectoLocal.enviarGrupoInvestigacionListCompromisoProyectoDTO(
              listaCompromisosAceptadosEnviarGrupo,
              loginFaces.getPerfilUsuarioDTO().getIdentificacion(),
              loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado(),
              loginFaces.getPerfilUsuarioDTO().getNombreCompleto(),
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL).getIdUsuarioRol());

      //ENVIAMOS LOS MAIL
      for (DetalleMailCompromisoDTO detalleMailCompromisoDTO : listaDetalleMailCompromisoDTO) {

        try {

          iMailSessionLocal.enviarMail_RolUnidadPolicial(
                  IConstantes.CU_PR_25_GRUPO_INVESTIGACION,
                  detalleMailCompromisoDTO.getParametrosAsunto(),
                  detalleMailCompromisoDTO.getParametrosContenido(),
                  detalleMailCompromisoDTO.getIdRol(),
                  detalleMailCompromisoDTO.getIdUnidadPolicial());

        } catch (Exception e) {
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad - ERROR AL ENVIAR MAIL (enviarGrupoInvestigacion)", e);
        }

      }

      cargarListaCompromisos();

      navigationFaces.redirectFacesCuPr25RevisarCompromisoJefeUnidad();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_25_lbl_mensaje_envia_grupo_investiga"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);

    }

  }

  /**
   *
   * @param actionEvent
   */
  public void enviarVicin(ActionEvent actionEvent) {

    try {

      //CREAMOS LA LISTA DE LOS CORREOS A ENVIAR
      List<DetalleMailCompromisoDTO> listaDetalleMailCompromisoDTO = new ArrayList<DetalleMailCompromisoDTO>();

      List<CompromisoProyectoDTO> listaCompromisosAceptadosEnviarVicin = new ArrayList<CompromisoProyectoDTO>();
      //VERIFICAMOS QUE COMPROMISOS ESTAN EN ESTADO NO ACEPTADO Y QUE TENGAN SU RESPECTIVO COMENTARIO
      for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

        if (IConstantes.TIPO_ESTADO_RESULTADO_COMPROMISO_PROYECTO_ACEPTADO.equals(unCompromisoProyectoDTO.getResultadoTemporal())) {

          listaCompromisosAceptadosEnviarVicin.add(unCompromisoProyectoDTO);

          Long idRolProyectoInstitucionalOconvocatoria = null;
          if (unCompromisoProyectoDTO.getCodigoProyecto().startsWith(keyPropertiesFactory.value("cu_ne_6_codigo_proyecto_inicia_generacion"))) {
            //ENVÍA MAIL AL  Encargado de revisión de compromisos de proyectos institucionales VICIN 
            idRolProyectoInstitucionalOconvocatoria = IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_INSTITUCIONALES_VICIN;

          } else if (unCompromisoProyectoDTO.getCodigoProyecto().startsWith(keyPropertiesFactory.value("cu_co_4_codigo_proyecto_inicia_generacion"))) {
            //o al Encargado de revisión de compromisos de proyectos de convocatoria VICIN, SEGÚN EL TIPO DE PROYECTO. 
            idRolProyectoInstitucionalOconvocatoria = IConstantesRole.ENCARGADO_DE_REVISION_DE_COMPROMISOS_DE_PROYECTOS_DE_CONVOCATORIA_VICIN;
          }

          //CODIGO DEL PROYECTO ENCONTRADO
          if (idRolProyectoInstitucionalOconvocatoria != null) {

            DetalleMailCompromisoDTO detalleMailCompromisoDTO = new DetalleMailCompromisoDTO();
            detalleMailCompromisoDTO.getParametrosAsunto().put("{_parametro1_}", unCompromisoProyectoDTO.getCompromiso());
            detalleMailCompromisoDTO.getParametrosAsunto().put("{_parametro2_}", unCompromisoProyectoDTO.getNombreUnidadPolicial());

            detalleMailCompromisoDTO.getParametrosContenido().put("{_parametro1_}", unCompromisoProyectoDTO.getNombreUnidadPolicial());
            detalleMailCompromisoDTO.getParametrosContenido().put("{_parametro2_}", unCompromisoProyectoDTO.getCompromiso());
            detalleMailCompromisoDTO.setIdRol(idRolProyectoInstitucionalOconvocatoria);

            listaDetalleMailCompromisoDTO.add(detalleMailCompromisoDTO);
          }

        }
      }

      if (listaCompromisosAceptadosEnviarVicin.isEmpty()) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_pr_25_lbl_error_nocompromisos_enviar"));
        return;
      }

      iCompromisoProyectoLocal.enviarVicinListCompromisoProyectoDTO(listaCompromisosAceptadosEnviarVicin);

      //ENVIAMOS LOS MAIL
      for (DetalleMailCompromisoDTO detalleMailCompromisoDTO : listaDetalleMailCompromisoDTO) {

        try {

          iMailSessionLocal.enviarMail_ListaRoles(
                  IConstantes.CU_PR_25_VICIN,
                  detalleMailCompromisoDTO.getParametrosAsunto(),
                  detalleMailCompromisoDTO.getParametrosContenido(),
                  Arrays.asList(detalleMailCompromisoDTO.getIdRol()));
        } catch (Exception e) {
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad - ERROR AL ENVIAR MAIL (enviarVicin)", e);
        }

      }

      cargarListaCompromisos();

      navigationFaces.redirectFacesCuPr25RevisarCompromisoJefeUnidad();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_25_lbl_mensaje_envia_vicin_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);

    }

  }

  /**
   *
   * @return
   */
  public String guardar() {

    try {

      List<CompromisoProyectoDTO> listaCompromisosAceptadosGuardar = new ArrayList<CompromisoProyectoDTO>();
      //VERIFICAMOS QUE COMPROMISOS ESTAN EN ESTADO NO ACEPTADO Y QUE TENGAN SU RESPECTIVO COMENTARIO
      for (CompromisoProyectoDTO unCompromisoProyectoDTO : listaCompromisoProyectoDTO) {

        listaCompromisosAceptadosGuardar.add(unCompromisoProyectoDTO);
      }

      iCompromisoProyectoLocal.guardarListCompromisoProyectoDTO(listaCompromisosAceptadosGuardar);

      cargarListaCompromisos();

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_pr_25_lbl_mensaje_guarda_compromiso_ok"));

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);

    }

    return null;
  }

  public void noSeleccionCompromiso(UnselectEvent event) {

    compromisoProyectoDTOSeleccionado = null;

  }

  public void handledFijarComentario(CompromisoProyectoDTO compromisoProyectoDTO, String comentario) {
    compromisoProyectoDTO.setComentario(comentario);
  }

  /**
   *
   * @param event
   */
  public void seleccionComprimiso(SelectEvent event) {
    try {

      if (compromisoProyectoDTOSeleccionado == null) {

        return;

      }

      //VERIFICAMOS K TIPO DE COMPROMISO ES
      if (IConstantes.ORIGEN_COMPROMISO_PROYECTO.equals(compromisoProyectoDTOSeleccionado.getOrigenCompromiso())) {

        CompromisoProyecto compromisoProyectoBusqueda = iCompromisoProyectoLocal.obtenerCompromisoProyecto(compromisoProyectoDTOSeleccionado.getIdCompromiso());
        Long idTipoCompromiso = compromisoProyectoBusqueda.getCompromisoPeriodo().getTipoCompromiso().getIdConstantes();

        if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE)
                || idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_INFORME_FINAL)) {
          //El sistema muestra el detalle del compromiso  (Informe de avance o informe final) 
          //CU-PR-11 Consultar detalle de compromiso
          String retorno = cuPr11ConsultarDetalleCompromisoFaces.initReturnCU_Llamado_Desde_CUPR25(compromisoProyectoDTOSeleccionado.getIdCompromiso(), true);
          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (idTipoCompromiso.equals(IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO)) {
          //El sistema identifica que el compromiso a revisar corresponde a un Formulación de proyectos.
          //1.El sistema muestra el detalle del Formulación del  proyecto. CU-PR-06
          String retorno = cuPr6ConsultarDetalleProyectoInvestigacion.initReturnCU_Desde_CU_PR_25(compromisoProyectoBusqueda.getProyecto().getIdProyecto());
          navigationFaces.redirectFacesCuGenerico(retorno);

        }
      } else if (IConstantes.ORIGEN_COMPROMISO_IMPLEMENTACION.equals(compromisoProyectoDTOSeleccionado.getOrigenCompromiso())) {

        CompromisoImplementacion compromisoImplementacionBusqueda = iCompromisoImplementacionLocal.
                obtenerCompromisoImplementacionPorId(compromisoProyectoDTOSeleccionado.getIdCompromiso());

        if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO)) {
          //El sistema identifica que el compromiso a revisar corresponde al plan de trabajo de la implementación.
          //1.El sistema muestra el detalle del plan de trabajo. 
          //Grupo de datos [3] que corresponde a los datos para consulta 
          //del CU-PR-21 Registrar plan de trabajo de la implementación de la investigación.
          String retorno = cuPr21RegistrarPlanDeTrabajoFaces.initReturnCU_DESDE_CU_PR_25(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(),
                  true);

          navigationFaces.redirectFacesCuGenerico(retorno);

        } else if (compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_AVANCE)
                || compromisoImplementacionBusqueda.getIdTipoCompromiso().getIdConstantes().equals(IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_INFORME_FINAL)) {

          //1.El sistema muestra el detalle del informe de avance o informe final, 
          //según corresponda. Grupo de datos [5] que corresponde a los datos para consulta del 
          //CU-PR-15 Registrar informe de avance de la implementación de la investigación.
          String retorno = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_25(
                  compromisoImplementacionBusqueda.getImplementacionesProyecto().getIdImplementacionProy(),
                  compromisoImplementacionBusqueda.getIdCompromisoImplementacion(), true);

          navigationFaces.redirectFacesCuGenerico(retorno);
        }

      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-25 Revisar compromisos Jefe de Unidad", e);
    }
  }

  public boolean compromisoNoAceptada(Long idConstante) {

    return IConstantes.TIPO_ESTADO_RESULTADO_COMPROMISO_PROYECTO_NO_ACEPTADO.equals(idConstante);

  }

  public ListGenericDataModel<CompromisoProyectoDTO> getListaCompromisoProyectoDTO() {
    return listaCompromisoProyectoDTO;
  }

  public void setListaCompromisoProyectoDTO(ListGenericDataModel<CompromisoProyectoDTO> listaCompromisoProyectoDTO) {
    this.listaCompromisoProyectoDTO = listaCompromisoProyectoDTO;
  }

  public CompromisoProyectoDTO getCompromisoProyectoDTOSeleccionado() {
    return compromisoProyectoDTOSeleccionado;
  }

  public void setCompromisoProyectoDTOSeleccionado(CompromisoProyectoDTO compromisoProyectoDTOSeleccionado) {
    this.compromisoProyectoDTOSeleccionado = compromisoProyectoDTOSeleccionado;
  }

  public int getNumeroRegistroCompromisos() {

    if (listaCompromisoProyectoDTO == null) {
      return 0;
    }

    return listaCompromisoProyectoDTO.getNumeroRegistro();
  }

  public List<SelectItem> getListaItemResultadoCompromiso() {
    return listaItemResultadoCompromiso;
  }

  public void setListaItemResultadoCompromiso(List<SelectItem> listaItemResultadoCompromiso) {
    this.listaItemResultadoCompromiso = listaItemResultadoCompromiso;
  }

  public boolean isMostrarBtns() {

    return listaCompromisoProyectoDTO != null && listaCompromisoProyectoDTO.getNumeroRegistro() > 0;

  }
}
