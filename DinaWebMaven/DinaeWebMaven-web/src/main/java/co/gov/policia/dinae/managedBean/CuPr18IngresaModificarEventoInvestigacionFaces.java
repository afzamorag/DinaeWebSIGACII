package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.EventoInvestigacionDTO;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.EventoInvestigacion;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Permite al usuario gestionar los eventos de investigación del sistema ACTORES: Encargado de eventos en la VICIN
 *
 * El usuario debe estar autenticado en el sistema y ha seleccionado la opción: Proyecto de investigación -> Gestionar eventos.
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuPr18IngresaModificarEventoInvestigacionFaces")
@javax.enterprise.context.SessionScoped
public class CuPr18IngresaModificarEventoInvestigacionFaces extends JSFUtils implements Serializable {

  @EJB
  private IConstantesLocal iConstantesLocal;

  @EJB
  private IEventoProyectoLocal iEventoProyectoLocal;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  @EJB
  private IInvestigadorLocal iInvestigadorLocal;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;

  private List<EventoInvestigacionDTO> listaEventoInvestigacion;
  private EventoInvestigacion eventoInvestigacion;
  private List<SelectItem> listaTipoEventoItem;
  private VistaFuncionario vistaFuncionario;
  private FileUploadEvent eventArchivoSubido;
  private String identificacionFuncionario;
  private List<InvestigadorProyecto> listaInvestigadoresProyecto;
  private UnidadPolicial unidadPolicialInvestigador;
  private String nombreArchivoSubido;
  private UsuarioRol usuarioRol;
  private Long idTipoEventoSeleccionadoBusqueda;
  private String anioSeleccionadoBusqueda;
  private List<SelectItem> listaAniosEventosItem;

  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      idTipoEventoSeleccionadoBusqueda = null;
      anioSeleccionadoBusqueda = null;

      usuarioRol = null;
      nombreArchivoSubido = null;
      unidadPolicialInvestigador = null;
      vistaFuncionario = null;
      listaInvestigadoresProyecto = null;
      identificacionFuncionario = null;
      eventArchivoSubido = null;
      listaTipoEventoItem = null;
      eventoInvestigacion = null;
      listaEventoInvestigacion = null;
      listaAniosEventosItem = null;
    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);
    }
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {

      usuarioRol = new UsuarioRol(
              loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(
                      IConstantesRole.ENCARGADO_DE_EVENTOS_EN_LA_VICIN).getIdUsuarioRol());

      listaTipoEventoItem = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_EVENTO_INVESTIGA),
              "idConstantes",
              "valor");

      listaAniosEventosItem = new ArrayList<SelectItem>();

      for (int anio = 2010; anio <= 2020; anio++) {

        listaAniosEventosItem.add(new SelectItem(String.valueOf(anio), String.valueOf(anio)));

      }

      cargarListaEventoInvestigacion(null, null);

      cargarListaInvestigadorPorEventoInvestigacion();

      return navigationFaces.redirectCuPr18IngresaModificarEventoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String buscarEvento() {

    try {

      cargarListaEventoInvestigacion(idTipoEventoSeleccionadoBusqueda, anioSeleccionadoBusqueda);

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }
    return null;
  }

  /**
   *
   * @return
   */
  public String verTodos() {

    try {

      return initReturnCU();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }
    return null;
  }

  /**
   *
   * @param tipoEvento
   * @param anio
   * @throws Exception
   */
  private void cargarListaEventoInvestigacion(Long tipoEvento, String anio) throws Exception {

    listaEventoInvestigacion = iEventoProyectoLocal.getTodosListaEventoInvestigacion(tipoEvento, anio);

  }

  /**
   *
   * @throws Exception
   */
  private void cargarListaInvestigadorPorEventoInvestigacion() throws Exception {

    if (eventoInvestigacion == null || eventoInvestigacion.getIdEventoInvestigacion() == null) {

      listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();

    } else {

      listaInvestigadoresProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorEventoInvestigacion(eventoInvestigacion.getIdEventoInvestigacion());

    }

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

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18", e);
    }
  }

  /**
   *
   * @return
   */
  public String agregarInvestigadorAenevtoInvestiga() {

    try {

      if (vistaFuncionario == null) {
        return null;
      }

      //CREAMMOS EL INVESTIGADOR
      InvestigadorProyecto investigadorProyecto = new InvestigadorProyecto();

      investigadorProyecto.setIdentificacion(vistaFuncionario.getIdentificacion());
      investigadorProyecto.setNombreCompleto(vistaFuncionario.getNombreCompleto());
      investigadorProyecto.setCorreo(vistaFuncionario.getCorreo());
      investigadorProyecto.setTelefono(vistaFuncionario.getTelefono());
      investigadorProyecto.setOrigenSiafOinvestigador(vistaFuncionario.isInformacionSIATH() ? 'S' : 'I');
      investigadorProyecto.setUnidadPolicial(unidadPolicialInvestigador);
      investigadorProyecto.setCargo(vistaFuncionario.getCargo());
      investigadorProyecto.setEventoInvestigacion(eventoInvestigacion);
      investigadorProyecto.setFechaRegistro(new Date());

      if (eventoInvestigacion.getIdEventoInvestigacion() == null) {

        //LO ADICIONAMOS A LA LISTA, YA QUE EL EVENTO NO HA SIDO CREADO AUN
        listaInvestigadoresProyecto.add(investigadorProyecto);

      } else {

        //GUARDAMOS EL INVESTIGADOR 
        iInvestigadorProyectoLocal.guardarInvestigadorProyecto(investigadorProyecto);

        //CARGAMOS LA LISTA DE LOS INVESTIGADORES
        cargarListaInvestigadorPorEventoInvestigacion();
      }

      identificacionFuncionario = null;
      vistaFuncionario = null;
      unidadPolicialInvestigador = null;

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18", e);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public String guardarEventoInvestigacion() {

    try {

      String[] archivo = {null, null};
      if (eventArchivoSubido != null) {
        archivo = almacenarArchivoFisico();
      }

      if (eventoInvestigacion.getIdEventoInvestigacion() == null) {

        if (co.gov.policia.dinae.util.DatesUtils.compareDate(eventoInvestigacion.getFechaInicio(), new Date()) == -1) {

          //La fecha ingresada es menor a la fecha actual.
          adicionaMensajeError("La fecha inicio debe ser mayor o igual a la fecha actual.");
          return null;

        }

        if (co.gov.policia.dinae.util.DatesUtils.compareDate(eventoInvestigacion.getFechaFin(), new Date()) == -1) {

          //La fecha ingresada es menor a la fecha actual.
          adicionaMensajeError("La fecha fin debe ser mayor o igual a la fecha actual.");
          return null;

        }

      }

      if (co.gov.policia.dinae.util.DatesUtils.compareDate(eventoInvestigacion.getFechaInicio(), eventoInvestigacion.getFechaFin()) == 1) {

        //La fecha ingresada es menor a la fecha actual.
        adicionaMensajeError("La fecha inicio NO dede ser mayor al al fecha fin.");
        return null;

      }

      eventoInvestigacion.setNombreArchivo(archivo[0] != null ? archivo[0] : eventoInvestigacion.getNombreArchivo());//NOMBRE DEL ARCHIVO COMO EL USUARIO LO CARGO
      eventoInvestigacion.setNombreArchivoFisico(archivo[1] != null ? archivo[1] : eventoInvestigacion.getNombreArchivoFisico());//NOMBRE DEL ARCHIVO COMO FUE GUARDADO

      boolean almacenaInvestigadores = eventoInvestigacion.getIdEventoInvestigacion() == null;

      eventoInvestigacion.setModalidadPresencial(eventoInvestigacion.getOpcionesSeleccionadaModalidad().contains("P") ? 'S' : 'N');
      eventoInvestigacion.setModalidadVirtual(eventoInvestigacion.getOpcionesSeleccionadaModalidad().contains("V") ? 'S' : 'N');

      eventoInvestigacion.setFechaActualiza(new Date());

      iEventoProyectoLocal.guardarEventoInvestigacion(eventoInvestigacion);

      if (almacenaInvestigadores) {

        //GUARDAMOS LOS INVESTIGADORES
        for (InvestigadorProyecto unInvestigadorProyecto : listaInvestigadoresProyecto) {

          iInvestigadorProyectoLocal.guardarInvestigadorProyecto(unInvestigadorProyecto);

        }
      }

      //ACTUALIZAMOS LA LISTA DE EVENTOS
      cargarListaEventoInvestigacion(null, null);

      listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();
      eventoInvestigacion = new EventoInvestigacion();
      eventArchivoSubido = null;
      nombreArchivoSubido = null;

      adicionaMensajeInformativo("Evento almacenado correctamente.");

      return navigationFaces.redirectCuPr18IngresaModificarEventoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }
    return null;
  }

  /**
   *
   * @param idEventoInvestigacion
   * @throws Exception
   */
  private void cargarDatosEvento(Long idEventoInvestigacion) throws Exception {

    listaInvestigadoresProyecto = new ArrayList<InvestigadorProyecto>();
    nombreArchivoSubido = null;
    eventoInvestigacion = iEventoProyectoLocal.buscarEventoInvestigacionPorId(idEventoInvestigacion);

    if ('S' == eventoInvestigacion.getModalidadPresencial()) {

      eventoInvestigacion.getOpcionesSeleccionadaModalidad().add("P");
    }

    if ('S' == eventoInvestigacion.getModalidadVirtual()) {

      eventoInvestigacion.getOpcionesSeleccionadaModalidad().add("V");
    }

    //ACTUALIZAMOS LA LISTA DE INVESTIGADORES POR EVENTO
    cargarListaInvestigadorPorEventoInvestigacion();

  }

  /**
   *
   * @param idEventoInvestigacion
   * @return
   */
  public String modificarEvento(Long idEventoInvestigacion) {

    try {

      cargarDatosEvento(idEventoInvestigacion);

      return navigationFaces.redirectCuPr18RegistrarEventoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }

    return null;

  }

  /**
   *
   * @param idEventoInvestigacion
   * @return
   */
  public String consultarEvento(Long idEventoInvestigacion) {

    try {

      cargarDatosEvento(idEventoInvestigacion);

      return navigationFaces.redirectCuPr18ConsultarEventoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }

    return null;

  }

  /**
   *
   * @return
   */
  public String limpiarFuncionario() {

    vistaFuncionario = null;
    unidadPolicialInvestigador = null;

    return null;
  }

  /**
   * Método para buscar funcionarios en el sistema SIATH.
   *
   * @return
   */
  public String buscarFuncionario() {

    vistaFuncionario = null;
    unidadPolicialInvestigador = null;

    if (identificacionFuncionario == null || identificacionFuncionario.trim().length() == 0) {
      adicionaMensajeError("El número de documento esta vacio.");
      return null;
    }

    try {

      //VERIFICAMOS QUE EL FUNCIONARIO A AGREGAR NO EXISTA EN LA LISTA
      if (listaInvestigadoresProyecto != null) {
        for (InvestigadorProyecto unInvestigadoresProyecto : listaInvestigadoresProyecto) {
          if (unInvestigadoresProyecto.getIdentificacion().equals(identificacionFuncionario)) {
            adicionaMensajeError(keyPropertiesFactory.value("cu_pr_1_lbl_error_add_investigador_ya_existe"));
            return null;
          }
        }

      }

      vistaFuncionario = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacionFuncionario);

      if (vistaFuncionario == null) {

        //BUSCAMOS LA IDENTIFICACION EN LA LISTA DE FUNCIONARIOS
        Investigador investigador = iInvestigadorLocal.getInvestigadorPorNumeroIdentificacion(identificacionFuncionario);

        if (investigador == null) {

          adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_identificacion_no_encontrada"));
          vistaFuncionario = null;
          return null;

        } else {

          vistaFuncionario = new VistaFuncionario();
          setearCamposInvestigadorToVistaFuncionario(investigador);

        }
      } else {

        try {

          //VERIFICAMOS SI EL FUNCIONARIO TIIENE UNIDAD POLICIAL
          if (vistaFuncionario.getCodigoUnidadLaboral() != null && vistaFuncionario.getCodigoUnidadLaboral().trim().length() > 0) {

            Long idUnidadPolicial = Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral().trim());
            unidadPolicialInvestigador = iUnidadPolicialLocal.obtenerUnidadPolicialPorId(idUnidadPolicial);

          }

        } catch (Exception e) {

          vistaFuncionario = null;

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18", e);

        }

      }
    } catch (Exception e) {

      vistaFuncionario = null;

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18", e);

    }

    return null;
  }

  /**
   *
   * @param investigador
   */
  private void setearCamposInvestigadorToVistaFuncionario(Investigador investigador) {

    vistaFuncionario.setIdentificacion(investigador.getNumeroIdentificacion());
    vistaFuncionario.setNombres(investigador.getNombres());
    vistaFuncionario.setApellidos(investigador.getApellidos());
    vistaFuncionario.setNombreCompleto(investigador.getNombres().concat(" ").concat(investigador.getApellidos()));
    vistaFuncionario.setCorreo(investigador.getCorreoElectronico());
    vistaFuncionario.setTelefono(investigador.getTelefono());
    vistaFuncionario.setDireccionEmpleado(investigador.getDireccion());
    vistaFuncionario.setInformacionSIATH(false);
    vistaFuncionario.setOrigenSIAToInvestigador(investigador.getTipoVinculacion().getValor());
    vistaFuncionario.setDepartamentoReside(investigador.getNombreDeptoResidencia());
    vistaFuncionario.setCiudadReside(investigador.getNombreCiudadResidencia());

  }

  public StreamedContent getDownloadContentProperty() {

    try {

      if (eventoInvestigacion != null && eventoInvestigacion.getNombreArchivo() != null) {

        String name = keyPropertiesFactory.value("cu_pr_18_dir_file_archivo_soporte") + eventoInvestigacion.getNombreArchivoFisico();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, eventoInvestigacion.getNombreArchivo());

        return download;
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (getDownloadContentProperty)", e);
    }
    return null;
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
        String nombreArchivoFisico = "EVENTO_INVESTGA_".concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(extension);

        copiarArchivoRutaFisica(keyPropertiesFactory.value("cu_pr_18_dir_file_archivo_soporte"), eventArchivoSubido.getFile().getInputstream(),
                nombreArchivoFisico);

        //NOMBRE ORIGINAL Y NOMBRE ARCHIVO GUARDADO
        return new String[]{nombreArchivoOriginal, nombreArchivoFisico};
      }

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18", e);
    }

    return null;

  }

  /**
   *
   * @return
   */
  public String registrarNuevoEvento() {

    try {

      eventoInvestigacion = new EventoInvestigacion();
      eventoInvestigacion.setTipoEvento(new Constantes());
      eventoInvestigacion.setUsuarioRol(usuarioRol);
      eventoInvestigacion.setUsuarioRegistra(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      eventoInvestigacion.setMaquina(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());

      return navigationFaces.redirectCuPr18RegistrarEventoInvestigacionRedirect();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-PR-18 (init)", e);

    }
    return null;
  }

  public List<EventoInvestigacionDTO> getListaEventoInvestigacion() {
    return listaEventoInvestigacion;
  }

  public void setListaEventoInvestigacion(List<EventoInvestigacionDTO> listaEventoInvestigacion) {
    this.listaEventoInvestigacion = listaEventoInvestigacion;
  }

  public EventoInvestigacion getEventoInvestigacion() {
    return eventoInvestigacion;
  }

  public void setEventoInvestigacion(EventoInvestigacion eventoInvestigacion) {
    this.eventoInvestigacion = eventoInvestigacion;
  }

  public List<SelectItem> getListaTipoEventoItem() {
    return listaTipoEventoItem;
  }

  public VistaFuncionario getVistaFuncionario() {
    return vistaFuncionario;
  }

  public void setVistaFuncionario(VistaFuncionario vistaFuncionario) {
    this.vistaFuncionario = vistaFuncionario;
  }

  public FileUploadEvent getEventArchivoSubido() {
    return eventArchivoSubido;
  }

  public void setEventArchivoSubido(FileUploadEvent eventArchivoSubido) {
    this.eventArchivoSubido = eventArchivoSubido;
  }

  public String getIdentificacionFuncionario() {
    return identificacionFuncionario;
  }

  public void setIdentificacionFuncionario(String identificacionFuncionario) {
    this.identificacionFuncionario = identificacionFuncionario;
  }

  public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
    return listaInvestigadoresProyecto;
  }

  public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> listaInvestigadoresProyecto) {
    this.listaInvestigadoresProyecto = listaInvestigadoresProyecto;
  }

  public UnidadPolicial getUnidadPolicialInvestigador() {
    return unidadPolicialInvestigador;
  }

  public void setUnidadPolicialInvestigador(UnidadPolicial unidadPolicialInvestigador) {
    this.unidadPolicialInvestigador = unidadPolicialInvestigador;
  }

  public boolean isMostrarLinkDescargaArchivo() {
    return eventoInvestigacion != null && eventoInvestigacion.getNombreArchivo() != null && eventoInvestigacion.getNombreArchivoFisico() != null;
  }

  public String getNombreArchivoSubido() {
    return nombreArchivoSubido;
  }

  public Long getIdTipoEventoSeleccionadoBusqueda() {
    return idTipoEventoSeleccionadoBusqueda;
  }

  public void setIdTipoEventoSeleccionadoBusqueda(Long idTipoEventoSeleccionadoBusqueda) {
    this.idTipoEventoSeleccionadoBusqueda = idTipoEventoSeleccionadoBusqueda;
  }

  public String getAnioSeleccionadoBusqueda() {
    return anioSeleccionadoBusqueda;
  }

  public void setAnioSeleccionadoBusqueda(String anioSeleccionadoBusqueda) {
    this.anioSeleccionadoBusqueda = anioSeleccionadoBusqueda;
  }

  public List<SelectItem> getListaAniosEventosItem() {
    return listaAniosEventosItem;
  }

  public void setListaAniosEventosItem(List<SelectItem> listaAniosEventosItem) {
    this.listaAniosEventosItem = listaAniosEventosItem;
  }
}
