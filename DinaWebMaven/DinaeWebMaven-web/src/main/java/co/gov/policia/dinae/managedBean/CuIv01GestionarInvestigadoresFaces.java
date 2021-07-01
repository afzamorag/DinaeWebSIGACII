package co.gov.policia.dinae.managedBean;

import static co.gov.policia.dinae.constantes.IConstantes.CODIGO_LUGAR_GEOGRAFICO_BOGOTA;
import static co.gov.policia.dinae.constantes.IConstantes.CODIGO_LUGAR_GEOGRAFICO_COLOMBIA;
import static co.gov.policia.dinae.constantes.IConstantes.LUGAR_GEOGRAFICO_COLOMBIA;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_VINCULACION_PROYECTO;
import static co.gov.policia.dinae.constantes.IConstantes.AREAS_SABER_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.NIVEL_FORMACION_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_ARTICULO_EN_REVISTA;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_ARTICULO_EN_REVISTA_INDEXADA;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_LIBRO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_CAPITULO_EN_LIBRO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_OTRO;
import co.gov.policia.dinae.dto.AntiguedadDTO;
import co.gov.policia.dinae.dto.InformacionAdicionalPersonaSiatDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.InvestigacionDesarrollada;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.PublicacionInvestigador;
import co.gov.policia.dinae.modelo.VistaFormacionFuncionario;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Édder Peña Barranco
 * @since 2013/12/06
 */
@javax.inject.Named(value = "cuIv01GestionarInvestigadoresFaces")
@javax.enterprise.context.SessionScoped
public class CuIv01GestionarInvestigadoresFaces extends JSFUtils implements Serializable {

  /**
   *
   */
  @EJB
  private ILugarGeograficoLocal iCiudad;

  @EJB
  private IConstantesLocal iConstantes;

  @EJB
  private IInvestigadorLocal iInvestigador;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  private int idTabSeleccionado;
  private Long idInvestigadorSeleccionado;
  private String numIdentificacionInv;
  private Long departamentoExpedicion;
  private List<SelectItem> listaItemsDepartamentos;
  private String ciudadExpedicion;
  private List<SelectItem> listaItemsCiudades;
  private String nombreInvestigador;
  private String apellidosInvestigador;
  private String correoInvestigador;
  private String telefonoInvestigador;
  private String direccionInvestigador;
  private Long departamentoResidencia;
  private List<SelectItem> listaItemsDeptosResidencia;
  private String ciudadResidencia;
  private List<SelectItem> listaItemsCiudadesRes;
  private String profesorPolicial;
  private String profesorPolicialSIAT;
  private Long tipoVinculacion;
  private List<SelectItem> listaItemsTiposVinculacion;
  private String funcionesInvestigador;
  private Long nivelFormacion;
  private Long areaSaber;
  private List<SelectItem> listaItemsAreaSaber;
  private String tituloObtenido;
  private Date fechaFinalizacion;
  private List<Investigador> listaInvestigadores;
  private Investigador investigadorSeleccionado;
  private VistaFuncionario vistaFuncionarioSeleccionado;
  private List<VistaFormacionFuncionario> listaVistaFormacion;
  private List<InvestigacionDesarrollada> listaOtrasInvestigaciones;
  private InvestigacionDesarrollada investigacionSeleccionada;
  private String tituloInvestigacion;
  private String resumenInvestigacion;
  private String institucionInvestigacion;
  private Date fechaInicio;
  private Date fechaFin;
  private List<PublicacionInvestigador> listaPublicaciones;
  private PublicacionInvestigador publicacionSeleccionada;
  private Long tipoPublicacion;
  private List<SelectItem> listaItemsTipoPublicacion;
  private String tituloPublicacion;
  private String resumenPublicacion;
  private Date fechaPublicacion;
  private Long paisPublicacion;
  private String nombrePaisPublicacion;
  private List<SelectItem> listaItemsPaises;
  private String codigoISSN;
  private String nombreRevista;
  private String volumenRevista;
  private String fasciculoRevista;
  private String serieRevista;
  private int pagInicioRevista;
  private String codigoISBN;
  private String nombreLibro;
  private String editorialLibro;
  private int pagInicioLibro;
  private List<SelectItem> listaItemsNivelesFormacion;
  private List<SelectItem> listaItemsProfesorPolicial;
  /**
   * Lista de las formaciones profesionales del investigador
   */
  private List<FormacionInvestigador> listaFormacionesInvestigador;
  /**
   * Lista que sirve como respaldo de la lista original para verificaciones posteriores
   */
  private List<FormacionInvestigador> listaFormacionesInvestigadorBackup;

  /**
   * Determina si se debe mostrar el botón de Agregar
   */
  private boolean mostrarBotonGuardar = true;
  /**
   * Determina si se debe mostrar el botón de agregar en la pestaña de otras investigaciones
   */
  private boolean mostrarBotonGuardarInvestigacion = true;
  /**
   * Determina si se debe mostrar el panel con los datos de la formación del investigador
   */
  private boolean mostrarPanelFormacion;
  /**
   * Es verdadero cuando el tipo de publicación sea revista
   */
  private boolean mostrarCuandoEsRevista;
  /**
   * Es verdadero cuando el tipo de publicación sea libro
   */
  private boolean mostrarCuandoEsLibro;
  /**
   * Es verdadero cuando el tipo de publicación sea libro
   */
  private boolean mostrarCuandoEsCapituloEnLibro;
  /**
   * Es verdadero cuando el tipo de publicación sea otro
   */
  private boolean mostrarCuandoEsOtro;
  /**
   * Determina si un investigador ya existe en la base de datos
   */
  private boolean existeInvestigador;
  /**
   * Determina si un investigador está habilitado para ser guardado
   */
  private boolean habilitadoParaGuardar;
  /**
   * Determina si se puede ingresar información del investigador
   */
  private boolean camposDeshabilitados = true;
  /**
   * Determina si los campos de la formación del investigador deben ser obligatorios
   */
  private boolean formacionRequerida = true;

  private Date fechaActual = new Date();

  /**
   *
   */
  @PostConstruct
  public void init() {
    borrarFormulario();
    cargarListaDepartamentosExp();
    cargarListaDepartamentosResidencia();
    cargarListaTiposVinculacion();
    cargarListaAreasSaber();
    cargarListaPaises();
    cargarListaNivelesFormacion();
    cargarListaTiposPublicaciones();
    cargarListaInvestigadores();
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    return navigationFaces.redirectCuIv01GestionarInvestigador();
  }

  private void borrarFormulario() {

    existeInvestigador = false;
    camposDeshabilitados = true;
    numIdentificacionInv = null;
    departamentoExpedicion = -1L;
    ciudadExpedicion = "-1";
    listaItemsCiudades = null;
    nombreInvestigador = null;
    apellidosInvestigador = null;
    correoInvestigador = null;
    telefonoInvestigador = null;
    direccionInvestigador = null;
    departamentoResidencia = -1L;
    ciudadResidencia = "-1";
    listaItemsCiudadesRes = null;
    profesorPolicial = null;
    profesorPolicialSIAT = null;
    funcionesInvestigador = null;
    nivelFormacion = null;
    areaSaber = null;
    tituloObtenido = null;
    fechaFinalizacion = null;
    listaFormacionesInvestigador = null;
    investigadorSeleccionado = null;
    vistaFuncionarioSeleccionado = null;
    listaVistaFormacion = null;
    investigacionSeleccionada = null;
    publicacionSeleccionada = null;
  }

  /**
   * ************ ACCIONES
   *
   ************
   * @return
   */
  public String agregarFormacionInvestigador() {

    try {

      FormacionInvestigador formacion = new FormacionInvestigador();

      formacion.setNivelFormacion(iConstantes.getConstantesPorIdConstante(nivelFormacion));
      formacion.setAreaSaber((areaSaber == null || areaSaber.compareTo(-1L) == 0) ? null : iConstantes.getConstantesPorIdConstante(areaSaber));
      formacion.setTituloObtenido(tituloObtenido);
      formacion.setFechaFinalizacion(fechaFinalizacion);
      formacion.setIdInvestigador(investigadorSeleccionado);

      if (listaFormacionesInvestigador == null || listaFormacionesInvestigador.isEmpty()) {

        listaFormacionesInvestigador = new ArrayList<FormacionInvestigador>();

      }

      listaFormacionesInvestigador.add(formacion);

      iInvestigador.insertDatosInvestigador(null, listaFormacionesInvestigador);

      formacionRequerida = false;
      nivelFormacion = null;
      areaSaber = null;
      tituloObtenido = null;
      fechaFinalizacion = null;

    } catch (Exception ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   *
   * @return
   */
  public String nuevoInvestigador() {

    vistaFuncionarioSeleccionado = null;
    listaVistaFormacion = null;
    investigadorSeleccionado = null;
    numIdentificacionInv = null;
    departamentoExpedicion = -1L;
    ciudadExpedicion = "-1";
    nombreInvestigador = null;
    apellidosInvestigador = null;
    correoInvestigador = null;
    telefonoInvestigador = null;
    direccionInvestigador = null;
    departamentoResidencia = -1L;
    ciudadResidencia = null;
    tipoVinculacion = null;
    funcionesInvestigador = null;
    nivelFormacion = null;
    areaSaber = null;
    tituloObtenido = null;
    fechaFinalizacion = null;
    mostrarPanelFormacion = false;
    existeInvestigador = false;
    camposDeshabilitados = true;
    formacionRequerida = true;
    listaFormacionesInvestigador = null;

    return navigationFaces.redirectCuIv01GestionarInvestigador();
  }

  /**
   *
   */
  public void registrarInvestigacion() {

    try {

      if (listaOtrasInvestigaciones == null) {
        listaOtrasInvestigaciones = new ArrayList<InvestigacionDesarrollada>();
      }
      InvestigacionDesarrollada investigacionDesarrollada = new InvestigacionDesarrollada();
      if (investigacionSeleccionada != null) {
        investigacionDesarrollada.setIdInvestiDesarrolInvesttiga(investigacionSeleccionada.getIdInvestigacionDesarrollada());
      }
      if (!validarModificacionesInvestigacion()) {
        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_sin_modificaciones"));
        return;
      }

      if (isEsFuncionario()) {

        investigacionDesarrollada.setIdentificacionSiat(vistaFuncionarioSeleccionado.getIdentificacion().trim());

      } else {

        investigacionDesarrollada.setIdInvestigador(investigadorSeleccionado);

      }

      investigacionDesarrollada.setTituloInvestigacion(tituloInvestigacion);
      investigacionDesarrollada.setResumen(resumenInvestigacion);
      investigacionDesarrollada.setInstitucion(institucionInvestigacion);
      investigacionDesarrollada.setFechaInicio(fechaInicio);
      investigacionDesarrollada.setFechaFin(fechaFin);

      if (investigacionDesarrollada.getFechaIngresoRegistro() == null) {

        investigacionDesarrollada.setFechaIngresoRegistro(new Date());

      } else {

        investigacionDesarrollada.setFechaModificaRegistro(new Date());

      }

      iInvestigador.guardarInvestigacionDesarrollada(investigacionDesarrollada);

      if (mostrarBotonGuardarInvestigacion) {
        mostrarBotonGuardarInvestigacion = false;
      } else {
        mostrarBotonGuardarInvestigacion = true;
      }

      cargarOtrasInvestigaciones();
      tituloInvestigacion = null;
      resumenInvestigacion = null;
      institucionInvestigacion = null;
      fechaInicio = null;
      fechaFin = null;

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_guardar_investigacion_exitosa"));

    } catch (Exception ex) {

      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_01_error_guardar_investigacion"));

    }
  }

  private boolean validarModificacionesInvestigacion() {
    boolean modificado = false;
    if (investigacionSeleccionada == null || investigacionSeleccionada.getIdInvestigacionDesarrollada() == null) {
      return true;
    }
    if (tituloInvestigacion != null & !tituloInvestigacion.equals("")) {
      if (!tituloInvestigacion.equals(investigacionSeleccionada.getTituloInvestigacion())) {
        modificado = true;
      }
    }
    if (resumenInvestigacion != null && !resumenInvestigacion.equals("")) {
      if (!resumenInvestigacion.equals(investigacionSeleccionada.getResumen())) {
        modificado = true;
      }
    }
    if (institucionInvestigacion != null && !institucionInvestigacion.equals("")) {
      if (!institucionInvestigacion.equals(investigacionSeleccionada.getInstitucion())) {
        modificado = true;
      }
    }
    if (fechaInicio != null && !fechaInicio.equals(investigacionSeleccionada.getFechaInicio())) {
      modificado = true;
    }
    if (fechaFin != null && !fechaFin.equals(investigacionSeleccionada.getFechaFin())) {
      modificado = true;
    }
    return modificado;
  }

  /**
   *
   */
  public void registrarPublicacionesInvestigador() {

    if (listaPublicaciones == null) {
      listaPublicaciones = new ArrayList<PublicacionInvestigador>();
    }
    PublicacionInvestigador publicacion = new PublicacionInvestigador();
    if (publicacionSeleccionada != null) {
      publicacion.setIdPublicacionInvestigador(publicacionSeleccionada.getIdPublicacionInvestigador());
    }
    publicacion.setTipoPublicacion(new Constantes(tipoPublicacion));
    publicacion.setTituloPublicacion(tituloPublicacion);
    publicacion.setResumenPublicacion(resumenPublicacion);
    publicacion.setFechaPublicacion(fechaPublicacion);
    publicacion.setCodigoPaisPublicacion(String.valueOf(paisPublicacion));

    for (SelectItem pais : listaItemsPaises) {
      if (pais.getValue().equals(paisPublicacion)) {
        nombrePaisPublicacion = pais.getLabel();
        break;
      }
    }
    publicacion.setNombrePaisPublicacion(nombrePaisPublicacion);

    if (isEsFuncionario()) {

      publicacion.setIdentificacionSiat(vistaFuncionarioSeleccionado.getIdentificacion().trim());

    } else {

      publicacion.setInvestigador(investigadorSeleccionado);

    }
    publicacion.setCodigoIssn(codigoISSN);
    publicacion.setNombreRevista(nombreRevista);
    publicacion.setVolumenRevista(volumenRevista);
    publicacion.setFasciculoRevista(fasciculoRevista);
    publicacion.setSerieRevista(serieRevista);
    publicacion.setPaginaInicioRevista(pagInicioRevista);
    publicacion.setCodigoIsbn(codigoISBN);
    publicacion.setNombreLibro(nombreLibro);
    publicacion.setEditorialLibro(editorialLibro);
    publicacion.setPaginaInicioLibro(pagInicioLibro);
    try {
      iInvestigador.guardarPublicacionInvestigador(publicacion);
      if (mostrarBotonGuardar) {
        mostrarBotonGuardar = false;
      } else {
        mostrarBotonGuardar = true;
      }
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_guardar_investigacion_exitosa"));
    } catch (JpaDinaeException ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_01_error_guardar_publicacion"));
    }

    cargarPublicacionesInvestigador();
    tipoPublicacion = -1L;
    tituloPublicacion = null;
    resumenPublicacion = null;
    fechaPublicacion = null;
    paisPublicacion = -1L;
    nombrePaisPublicacion = null;
    codigoISSN = null;
    nombreRevista = null;
    volumenRevista = null;
    fasciculoRevista = null;
    serieRevista = null;
    pagInicioRevista = 0;
    codigoISBN = null;
    nombreLibro = null;
    editorialLibro = null;
    pagInicioLibro = 0;
  }

  public void seleccionarInvestigador() {

  }

  /**
   *
   * @return
   */
  public String buscarInvestigadorPorNumeroIdentificacion() {
    try {

      listaVistaFormacion = null;
      vistaFuncionarioSeleccionado = null;
      investigadorSeleccionado = null;

      //VERIFIAMOS QUE EL NUMERO DE IDENTIFICACIÓN
      if (numIdentificacionInv == null || "".equals(numIdentificacionInv.trim())) {
        return null;
      }

      //BUSCAMOS EL NÚMERO DE IDENTIFICACIÓN EN LA TABLA INVESTIGADOR
      investigadorSeleccionado = iInvestigador.getInvestigadorPorNumeroIdentificacion(numIdentificacionInv);

      //SI NO SE ENCONTRO EL INVESTIGADOR
      if (investigadorSeleccionado == null) {
        //LO BUSCAMOS EN VISTA FUNCIONARIO
        vistaFuncionarioSeleccionado = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(numIdentificacionInv);

        if (vistaFuncionarioSeleccionado == null) {

          adicionaMensajeError("No se encontraron resultados. Verifique el número de identificación ingresado.");
          return null;
        }

        //CALCULAMOS LA ANTIGUEDAD
        AntiguedadDTO antiguedadDTO = new AntiguedadDTO(0L, 0L, 0L);
        if (vistaFuncionarioSeleccionado.getIngresoInstitucion() != null) {

          antiguedadDTO = UtilJPA.calcularAntiguedadEntreDosfecha(vistaFuncionarioSeleccionado.getIngresoInstitucion(), new Date());
        }
        vistaFuncionarioSeleccionado.setAntiguedadDTO(antiguedadDTO);

        InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO = iVistaFuncionarioLocal.obtenerInformacionAdicionalPersonaSIAT(numIdentificacionInv);

        profesorPolicialSIAT = informacionAdicionalPersonaSiatDTO != null ? informacionAdicionalPersonaSiatDTO.getProfesorPolicial() : null;

        //CARGAMOS LAS FORMACIONES DE LA PERSONA DESDE LA VISTA
        listaVistaFormacion = iVistaFuncionarioLocal.getListaVistaFormacionFuncionarioPorIdentificacion(numIdentificacionInv);

        cargarPublicacionesInvestigador();
        cargarOtrasInvestigaciones();

        return null;

      }

      if (investigadorSeleccionado != null) {

        idInvestigadorSeleccionado = investigadorSeleccionado.getIdInvestigador();
        numIdentificacionInv = investigadorSeleccionado.getNumeroIdentificacion();
        departamentoExpedicion = (investigadorSeleccionado.getCodigoDeptoExpedicion() == null) ? null : Long.parseLong(investigadorSeleccionado.getCodigoDeptoExpedicion());

        obtenerCiudadesPorDeptoExp();

        ciudadExpedicion = investigadorSeleccionado.getCodigoCiudadExpedicion();
        nombreInvestigador = investigadorSeleccionado.getNombres();
        apellidosInvestigador = investigadorSeleccionado.getApellidos();
        correoInvestigador = investigadorSeleccionado.getCorreoElectronico();
        telefonoInvestigador = investigadorSeleccionado.getTelefono();
        direccionInvestigador = investigadorSeleccionado.getDireccion();
        departamentoResidencia = Long.parseLong(investigadorSeleccionado.getCodigoDeptoResidencia());

        obtenerCiudadesPorDeptoRes();

        ciudadResidencia = investigadorSeleccionado.getCodigoCiudadResidencia();
        tipoVinculacion = investigadorSeleccionado.getTipoVinculacion().getIdConstantes();
        funcionesInvestigador = investigadorSeleccionado.getFuncionInvestigador();

        if (investigadorSeleccionado.getProfesorPolicial() != null && investigadorSeleccionado.getProfesorPolicial().equals('Y')) {
          profesorPolicial = "Si";
        } else {
          profesorPolicial = "No";
        }

        existeInvestigador = true;

      }

      if (investigadorSeleccionado == null && vistaFuncionarioSeleccionado == null) {

        adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_investigador_no_encontrado"));

        investigadorSeleccionado = new Investigador();
        idInvestigadorSeleccionado = null;
        //numIdentificacionInv = null;
        departamentoExpedicion = -1L;
        ciudadExpedicion = "-1L";
        nombreInvestigador = null;
        apellidosInvestigador = null;
        correoInvestigador = null;
        telefonoInvestigador = null;
        direccionInvestigador = null;
        departamentoResidencia = -1L;
        ciudadResidencia = "-1L";
        tipoVinculacion = -1L;
        listaFormacionesInvestigador = new ArrayList<FormacionInvestigador>();
        funcionesInvestigador = null;
        profesorPolicial = null;
        profesorPolicialSIAT = null;
        existeInvestigador = false;
        formacionRequerida = true;
      }
      mostrarPanelFormacion = true;
      mostrarBotonGuardar = true;
      cargarListaFormacionesInvestigador();
      cargarOtrasInvestigaciones();
      cargarPublicacionesInvestigador();

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }

  /**
   *
   * @return
   */
  public String actualizarInformacionPersonaSIAT() {

    try {

      if (vistaFuncionarioSeleccionado == null) {
        return null;
      }

      if (profesorPolicialSIAT == null) {

        profesorPolicialSIAT = "N";
      }

      InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO = new InformacionAdicionalPersonaSiatDTO();
      informacionAdicionalPersonaSiatDTO.setIdentificacacion(vistaFuncionarioSeleccionado.getIdentificacion());
      informacionAdicionalPersonaSiatDTO.setProfesorPolicial(profesorPolicialSIAT);

      iVistaFuncionarioLocal.actualizarInformacionAdicionalPersonaSIAT(informacionAdicionalPersonaSiatDTO);

      return navigationFaces.redirectCuIv01GestionarInvestigador();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, e);

    }
    return null;
  }

  /**
   *
   * @param event
   */
  public void guardarInformacionInvestigador(ActionEvent event) {

    if (idInvestigadorSeleccionado != null && idInvestigadorSeleccionado > 0L) {
      investigadorSeleccionado.setIdInvestigador(idInvestigadorSeleccionado);
    } else {
      investigadorSeleccionado = new Investigador();
    }

    if (!validarModificacionesRealizadas()) {
      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_sin_modificaciones"));
      return;
    }

    investigadorSeleccionado.setNumeroIdentificacion(numIdentificacionInv);

    if (departamentoExpedicion != null && departamentoExpedicion.compareTo(-1L) != 0) {

      investigadorSeleccionado.setCodigoDeptoExpedicion(String.valueOf(departamentoExpedicion));
      // ASIGNANDO EL NOMBRE DEL DEPARTAMENTO SELECCIONADO
      for (SelectItem item : listaItemsDepartamentos) {
        if (item.getValue().equals(departamentoExpedicion)) {
          investigadorSeleccionado.setNombreDeptoExpedicion(item.getLabel());
          break;
        }
      }
    }

    if (ciudadExpedicion != null && !"-1".equals(ciudadExpedicion)) {
      investigadorSeleccionado.setCodigoCiudadExpedicion(ciudadExpedicion);

      // ASIGNANDO EL NOMBRE DE LA CIUDAD SELECCIONADA
      for (SelectItem item : listaItemsCiudades) {
        if (item.getValue().toString().equals(ciudadExpedicion)) {
          investigadorSeleccionado.setNombreCiudadExpedicion(item.getLabel());
          break;
        }
      }
    }

    investigadorSeleccionado.setNombres(nombreInvestigador);
    investigadorSeleccionado.setApellidos(apellidosInvestigador);
    investigadorSeleccionado.setCorreoElectronico(correoInvestigador);
    investigadorSeleccionado.setTelefono(telefonoInvestigador);
    investigadorSeleccionado.setDireccion(direccionInvestigador);

    if (departamentoResidencia != null && departamentoResidencia.compareTo(-1L) != 0) {

      investigadorSeleccionado.setCodigoDeptoResidencia(String.valueOf(departamentoResidencia));
      for (SelectItem item : listaItemsDeptosResidencia) {
        if (item.getValue().equals(departamentoResidencia)) {
          investigadorSeleccionado.setNombreDeptoResidencia(item.getLabel());
          break;
        }
      }
    }

    if (ciudadResidencia != null && !"-1".equals(ciudadResidencia)) {

      investigadorSeleccionado.setCodigoCiudadResidencia(ciudadResidencia);
      for (SelectItem item : listaItemsCiudadesRes) {
        if (item.getValue().toString().equals(ciudadResidencia)) {
          investigadorSeleccionado.setNombreCiudadResidencia(item.getLabel());
          break;
        }
      }
    }
    if (profesorPolicial != null && !profesorPolicial.equals("")) {
      if (profesorPolicial.equals("Si")) {
        investigadorSeleccionado.setProfesorPolicial('Y');
      } else {
        investigadorSeleccionado.setProfesorPolicial('N');
      }
    }

    investigadorSeleccionado.setTipoVinculacion(new Constantes(tipoVinculacion));
    investigadorSeleccionado.setFuncionInvestigador(funcionesInvestigador);

    try {

      if (investigadorSeleccionado.getIdUnidadPolicialCrea() == null
              && loginFaces.getPerfilUsuarioDTO().getUnidadPolicial() != null
              && loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial() != null) {

        investigadorSeleccionado.setIdUnidadPolicialCrea(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      } else if (loginFaces.getPerfilUsuarioDTO().getUnidadPolicial() != null
              && loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial() != null) {

        investigadorSeleccionado.setIdUnidadPolicialModifica(loginFaces.getPerfilUsuarioDTO().getUnidadPolicial().getIdUnidadPolicial());

      }

      investigadorSeleccionado = iInvestigador.insertDatosInvestigador(investigadorSeleccionado, listaFormacionesInvestigador);

      adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_01_info_add_investigador_exitoso"));
      existeInvestigador = true;
      habilitadoParaGuardar = true;

    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception e) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, e);
    }
    //return navigationFaces.redirectCuIv01GestionarInvestigador();
  }

  private boolean validarModificacionesRealizadas() {
    boolean modificaciones = false;

    if (investigadorSeleccionado == null || investigadorSeleccionado.getIdInvestigador() == null) {
      return true;
    }
    // SE VALIDA QUE HAYAN MODIFICACIONES 
    if (departamentoExpedicion != null && departamentoExpedicion > 0L) {
      if (!departamentoExpedicion.equals(Long.parseLong(investigadorSeleccionado.getCodigoDeptoExpedicion()))) {
        modificaciones = true;
      }
    }
    // SE VALIDA QUE HAYAN MODIFICACIONES
    if (ciudadExpedicion != null && !ciudadExpedicion.equals("")) {
      if (!ciudadExpedicion.equals(investigadorSeleccionado.getCodigoCiudadExpedicion())) {
        modificaciones = true;
      }
    }
    if (nombreInvestigador != null && !nombreInvestigador.equals("")) {
      if (!nombreInvestigador.equals(investigadorSeleccionado.getNombres())) {
        modificaciones = true;
      }
    }
    if (apellidosInvestigador != null && !apellidosInvestigador.equals("")) {
      if (!apellidosInvestigador.equals(investigadorSeleccionado.getApellidos())) {
        modificaciones = true;
      }
    }
    if (correoInvestigador != null && !correoInvestigador.equals("")) {
      if (!correoInvestigador.equals(investigadorSeleccionado.getCorreoElectronico())) {
        modificaciones = true;
      }
    }
    if (telefonoInvestigador != null && !telefonoInvestigador.equals("")) {
      if (!telefonoInvestigador.equals(investigadorSeleccionado.getTelefono())) {
        modificaciones = true;
      }
    }
    if (direccionInvestigador != null && !direccionInvestigador.equals("")) {
      if (!direccionInvestigador.equals(investigadorSeleccionado.getDireccion())) {
        modificaciones = true;
      }
    }
    if (departamentoResidencia != null && departamentoResidencia > 0L) {
      if (!departamentoResidencia.equals(Long.parseLong(investigadorSeleccionado.getCodigoDeptoResidencia()))) {
        modificaciones = true;
      }
    }
    if (ciudadResidencia != null && !ciudadResidencia.equals("")) {
      if (!ciudadResidencia.equals(investigadorSeleccionado.getCodigoCiudadResidencia())) {
        modificaciones = true;
      }
    }
    if (profesorPolicial != null && !profesorPolicial.equals("")) {
      Character esProfesorPolicial;
      if (profesorPolicial.equals("Si")) {
        esProfesorPolicial = 'Y';
      } else {
        esProfesorPolicial = 'N';
      }
      if (!esProfesorPolicial.equals(investigadorSeleccionado.getProfesorPolicial())) {
        modificaciones = true;
      }
    }
    if (tipoVinculacion != null && tipoVinculacion > 0L) {
      if (!tipoVinculacion.equals(investigadorSeleccionado.getTipoVinculacion().getIdConstantes())) {
        modificaciones = true;
      }
    }
    if (funcionesInvestigador != null && !funcionesInvestigador.equals("")) {
      if (!funcionesInvestigador.equals(investigadorSeleccionado.getFuncionInvestigador())) {
        modificaciones = true;
      }
    }
    if (listaFormacionesInvestigadorBackup != null && listaFormacionesInvestigador != null
            && listaFormacionesInvestigadorBackup.size() < listaFormacionesInvestigador.size()) {
      modificaciones = true;
    }
    return modificaciones;
  }

  /**
   * ***********************************************************************
   */
  /**
   * ********** CARGUE DE LISTAS ************
   */
  /**
   * ***********************************************************************
   */
  private void cargarListaDepartamentosExp() {
    List<LugarGeografico> listaDepartamentos;
    listaItemsDepartamentos = new ArrayList<SelectItem>();
    try {
      listaDepartamentos = iCiudad.getListaLugares(CODIGO_LUGAR_GEOGRAFICO_COLOMBIA,
              LUGAR_GEOGRAFICO_COLOMBIA);
      for (LugarGeografico lugar : listaDepartamentos) {
        if (!lugar.getCodMunicipio().equals(CODIGO_LUGAR_GEOGRAFICO_BOGOTA)) {
          listaItemsDepartamentos.add(new SelectItem(lugar.getCodMunicipio(), lugar.getDescMunicipio()));
        }
      }
      //listaItemsDeptosOrigen = listaItemsDepartamentos;
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaDepartamentosResidencia() {
    List<LugarGeografico> listaDepartamentos;
    listaItemsDeptosResidencia = new ArrayList<SelectItem>();
    try {
      listaDepartamentos = iCiudad.getListaLugares(CODIGO_LUGAR_GEOGRAFICO_COLOMBIA,
              LUGAR_GEOGRAFICO_COLOMBIA);
      for (LugarGeografico lugar : listaDepartamentos) {
        if (!lugar.getCodMunicipio().equals(CODIGO_LUGAR_GEOGRAFICO_BOGOTA)) {
          listaItemsDeptosResidencia.add(new SelectItem(lugar.getCodMunicipio(), lugar.getDescMunicipio()));
        }
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaTiposVinculacion() {
    listaItemsTiposVinculacion = new ArrayList<SelectItem>();
    List<Constantes> listaTiposVinculacion;
    try {
      listaTiposVinculacion = iConstantes.getConstantesPorTipo(TIPO_VINCULACION_PROYECTO);
      for (Constantes cons : listaTiposVinculacion) {
        listaItemsTiposVinculacion.add(new SelectItem(cons.getIdConstantes(), cons.getValor()));
      }
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void cargarListaAreasSaber() {
    listaItemsAreaSaber = new ArrayList<SelectItem>();
    List<Constantes> listaAreasSaber;
    try {
      listaAreasSaber = iConstantes.getConstantesPorTipo(AREAS_SABER_INVESTIGADOR);
      for (Constantes cons : listaAreasSaber) {
        listaItemsAreaSaber.add(new SelectItem(cons.getIdConstantes(), cons.getValor()));
      }
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaPaises() {
    List<LugarGeografico> listaPaises;
    listaItemsPaises = new ArrayList<SelectItem>();
    try {
      listaPaises = iCiudad.getListaPaises();
      for (LugarGeografico lugar : listaPaises) {
        listaItemsPaises.add(new SelectItem(lugar.getCodMunicipio(), lugar.getDescMunicipio()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaNivelesFormacion() {
    List<Constantes> nivelesFormacion;
    listaItemsNivelesFormacion = new ArrayList<SelectItem>();
    try {
      nivelesFormacion = iConstantes.getConstantesPorTipo(NIVEL_FORMACION_INVESTIGADOR);
      for (Constantes nivel : nivelesFormacion) {
        listaItemsNivelesFormacion.add(new SelectItem(nivel.getIdConstantes(), nivel.getValor()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaTiposPublicaciones() {
    List<Constantes> listaTiposPublicaciones;
    listaItemsTipoPublicacion = new ArrayList<SelectItem>();
    try {
      listaTiposPublicaciones = iConstantes.getConstantesPorTipo(TIPO_PUBLICACION_INVESTIGADOR);
      for (Constantes tp : listaTiposPublicaciones) {
        listaItemsTipoPublicacion.add(new SelectItem(tp.getIdConstantes(), tp.getValor()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaInvestigadores() {
    /*try
         {
         iInvestigador.getFormacionByNumeroIdentificacion(numIdentificacionInv);
            
         } 
         catch (JpaDinaeException ex) 
         {
         adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
         Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
         }*/
  }

  private void cargarListaFormacionesInvestigador() {
    try {
      listaFormacionesInvestigador = iInvestigador.getFormacionInvestigadorByNumIdentificacion(numIdentificacionInv);
      listaFormacionesInvestigadorBackup = listaFormacionesInvestigador;
      formacionRequerida = false;
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  /**
   *
   */
  private void cargarOtrasInvestigaciones() {
    try {

      listaOtrasInvestigaciones = iInvestigador.getInvestigacionesDesarrolladasByNumIdentificacion(isEsInvestigador(), numIdentificacionInv);

    } catch (JpaDinaeException ex) {

      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  /**
   *
   */
  private void cargarPublicacionesInvestigador() {
    try {

      listaPublicaciones = iInvestigador.getPublicacionesByNumIdentificacion(isEsInvestigador(), numIdentificacionInv);

    } catch (JpaDinaeException ex) {

      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  /**
   * ***********************************************************************
   */
  /**
   * ************ EVENTOS *********
   */
  /**
   *
   * @param event
   */
  public void onTabChange(TabChangeEvent event) {

    idTabSeleccionado = 0;
    if (event == null || event.getTab() == null) {
      return;
    }
    if ("tabInformacionBasica".equals(event.getTab().getId())) {
      idTabSeleccionado = 0;
    } else if ("tabPersonalInvestigacion".equals(event.getTab().getId())) {
      idTabSeleccionado = 1;
    } else if ("tabOtrasInvestigaciones".equals(event.getTab().getId())) {
      idTabSeleccionado = 2;
    } else if ("tabPublicaciones".equals(event.getTab().getId())) {
      idTabSeleccionado = 3;
    }
  }

  /**
   * Permite obtener las ciudades correspondientes a un departamento seleccionado
   */
  public void obtenerCiudadesPorDeptoExp() {
    listaItemsCiudades = new ArrayList<SelectItem>();
    List<LugarGeografico> listaCiudades;
    try {
      if (departamentoExpedicion != null && departamentoExpedicion != -1L) {
        listaCiudades = iCiudad.getListaLugaresByCodDepartamento(departamentoExpedicion);
        for (LugarGeografico lg : listaCiudades) {
          listaItemsCiudades.add(new SelectItem(lg.getCodMunicipio(), lg.getDescMunicipio()));
        }
      }
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  /**
   * Permite obtener las ciudades correspondientes a un departamento seleccionado
   */
  public void obtenerCiudadesPorDeptoRes() {
    listaItemsCiudadesRes = new ArrayList<SelectItem>();
    List<LugarGeografico> listaCiudades;
    try {
      if (departamentoResidencia != null && departamentoResidencia != -1L) {
        listaCiudades = iCiudad.getListaLugaresByCodDepartamento(departamentoResidencia);
        for (LugarGeografico lg : listaCiudades) {
          listaItemsCiudadesRes.add(new SelectItem(lg.getCodMunicipio(), lg.getDescMunicipio()));
        }
      }
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
    }
  }

  public void filtrarCamposPorTipoDePublicacion() {
    if (tipoPublicacion.equals(TIPO_PUBLICACION_ARTICULO_EN_REVISTA)
            || tipoPublicacion.equals(TIPO_PUBLICACION_ARTICULO_EN_REVISTA_INDEXADA)) {
      mostrarCuandoEsRevista = true;
      mostrarCuandoEsLibro = false;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = false;
    } else if (tipoPublicacion.equals(TIPO_PUBLICACION_LIBRO)) {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = true;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = false;
    } else if (tipoPublicacion.equals(TIPO_PUBLICACION_CAPITULO_EN_LIBRO)) {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = true;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = true;
    } else if (tipoPublicacion.equals(TIPO_PUBLICACION_OTRO)) {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = false;
      mostrarCuandoEsOtro = false;
      mostrarCuandoEsCapituloEnLibro = false;
    } else {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = false;
      mostrarCuandoEsOtro = false;
      mostrarCuandoEsCapituloEnLibro = false;
    }
  }

  public void seleccionarInvestigacion() {
    tituloInvestigacion = investigacionSeleccionada.getTituloInvestigacion();
    resumenInvestigacion = investigacionSeleccionada.getResumen();
    institucionInvestigacion = investigacionSeleccionada.getInstitucion();
    fechaInicio = investigacionSeleccionada.getFechaInicio();
    fechaFin = investigacionSeleccionada.getFechaFin();
    mostrarBotonGuardarInvestigacion = false;
  }

  public void seleccionarPublicacion() {
    mostrarBotonGuardar = false;
    tipoPublicacion = publicacionSeleccionada.getTipoPublicacion().getIdConstantes();
    tituloPublicacion = publicacionSeleccionada.getTituloPublicacion();
    resumenPublicacion = publicacionSeleccionada.getResumenPublicacion();
    fechaPublicacion = publicacionSeleccionada.getFechaPublicacion();
    try {
      paisPublicacion = Long.parseLong(publicacionSeleccionada.getCodigoPaisPublicacion());
    } catch (NumberFormatException nfe) {
      paisPublicacion = -1L;
    }
    if (paisPublicacion != null && paisPublicacion > 0L) {
      for (SelectItem pais : listaItemsPaises) {
        if (paisPublicacion.equals(pais.getValue())) {
          nombrePaisPublicacion = pais.getLabel();
          break;
        }
      }
    }
    codigoISSN = publicacionSeleccionada.getCodigoIssn();
    nombreRevista = publicacionSeleccionada.getNombreRevista();
    volumenRevista = publicacionSeleccionada.getVolumenRevista();
    fasciculoRevista = publicacionSeleccionada.getFasciculoRevista();
    serieRevista = publicacionSeleccionada.getSerieRevista();
    pagInicioRevista = publicacionSeleccionada.getPaginaInicioRevista();
    codigoISBN = publicacionSeleccionada.getCodigoIsbn();
    nombreLibro = publicacionSeleccionada.getNombreLibro();
    editorialLibro = publicacionSeleccionada.getEditorialLibro();
    pagInicioLibro = publicacionSeleccionada.getPaginaInicioLibro();

  }

  /**
   * ***********************************************************************
   */
  /**
   * ********** GETTERS Y SETTERS *************
   */
  public int getIdTabSeleccionado() {
    return idTabSeleccionado;
  }

  public void setIdTabSeleccionado(int idTabSeleccionado) {
    this.idTabSeleccionado = idTabSeleccionado;
  }

  public Long getIdInvestigadorSeleccionado() {
    return idInvestigadorSeleccionado;
  }

  public void setIdInvestigadorSeleccionado(Long idInvestigadorSeleccionado) {
    this.idInvestigadorSeleccionado = idInvestigadorSeleccionado;
  }

  public String getNumIdentificacionInv() {
    return numIdentificacionInv;
  }

  public void setNumIdentificacionInv(String numIdentificacionInv) {
    this.numIdentificacionInv = numIdentificacionInv;
  }

  public Long getDepartamentoExpedicion() {
    return departamentoExpedicion;
  }

  public void setDepartamentoExpedicion(Long departamentoExpedicion) {
    this.departamentoExpedicion = departamentoExpedicion;
  }

  public List<SelectItem> getListaItemsDepartamentos() {
    return listaItemsDepartamentos;
  }

  public void setListaItemsDepartamentos(List<SelectItem> listaItemsDepartamentos) {
    this.listaItemsDepartamentos = listaItemsDepartamentos;
  }

  public String getCiudadExpedicion() {
    return ciudadExpedicion;
  }

  public void setCiudadExpedicion(String ciudadExpedicion) {
    this.ciudadExpedicion = ciudadExpedicion;
  }

  public List<SelectItem> getListaItemsCiudades() {
    return listaItemsCiudades;
  }

  public void setListaItemsCiudades(List<SelectItem> listaItemsCiudades) {
    this.listaItemsCiudades = listaItemsCiudades;
  }

  public String getNombreInvestigador() {
    return nombreInvestigador;
  }

  public void setNombreInvestigador(String nombreInvestigador) {
    this.nombreInvestigador = nombreInvestigador;
  }

  public String getApellidosInvestigador() {
    return apellidosInvestigador;
  }

  public void setApellidosInvestigador(String apellidosInvestigador) {
    this.apellidosInvestigador = apellidosInvestigador;
  }

  public String getCorreoInvestigador() {
    return correoInvestigador;
  }

  public void setCorreoInvestigador(String correoInvestigador) {
    this.correoInvestigador = correoInvestigador;
  }

  public String getTelefonoInvestigador() {
    return telefonoInvestigador;
  }

  public void setTelefonoInvestigador(String telefonoInvestigador) {
    this.telefonoInvestigador = telefonoInvestigador;
  }

  public String getDireccionInvestigador() {
    return direccionInvestigador;
  }

  public void setDireccionInvestigador(String direccionInvestigador) {
    this.direccionInvestigador = direccionInvestigador;
  }

  public Long getDepartamentoResidencia() {
    return departamentoResidencia;
  }

  public void setDepartamentoResidencia(Long departamentoResidencia) {
    this.departamentoResidencia = departamentoResidencia;
  }

  public List<SelectItem> getListaItemsDeptosResidencia() {
    return listaItemsDeptosResidencia;
  }

  public void setListaItemsDeptosResidencia(List<SelectItem> listaItemsDeptosResidencia) {
    this.listaItemsDeptosResidencia = listaItemsDeptosResidencia;
  }

  public String getCiudadResidencia() {
    return ciudadResidencia;
  }

  public void setCiudadResidencia(String ciudadResidencia) {
    this.ciudadResidencia = ciudadResidencia;
  }

  public List<SelectItem> getListaItemsCiudadesRes() {
    return listaItemsCiudadesRes;
  }

  public void setListaItemsCiudadesRes(List<SelectItem> listaItemsCiudadesRes) {
    this.listaItemsCiudadesRes = listaItemsCiudadesRes;
  }

  public String getProfesorPolicial() {
    return profesorPolicial;
  }

  public void setProfesorPolicial(String profesorPolicial) {
    this.profesorPolicial = profesorPolicial;
  }

  public Long getTipoVinculacion() {
    return tipoVinculacion;
  }

  public void setTipoVinculacion(Long tipoVinculacion) {
    this.tipoVinculacion = tipoVinculacion;
  }

  public List<SelectItem> getListaItemsTiposVinculacion() {
    return listaItemsTiposVinculacion;
  }

  public void setListaItemsTiposVinculacion(List<SelectItem> listaItemsTiposVinculacion) {
    this.listaItemsTiposVinculacion = listaItemsTiposVinculacion;
  }

  public String getFuncionesInvestigador() {
    return funcionesInvestigador;
  }

  public void setFuncionesInvestigador(String funcionesInvestigador) {
    this.funcionesInvestigador = funcionesInvestigador;
  }

  public Long getNivelFormacion() {
    return nivelFormacion;
  }

  public void setNivelFormacion(Long nivelFormacion) {
    this.nivelFormacion = nivelFormacion;
  }

  public Long getAreaSaber() {
    return areaSaber;
  }

  public void setAreaSaber(Long areaSaber) {
    this.areaSaber = areaSaber;
  }

  public List<SelectItem> getListaItemsAreaSaber() {
    return listaItemsAreaSaber;
  }

  public void setListaItemsAreaSaber(List<SelectItem> listaItemsAreaSaber) {
    this.listaItemsAreaSaber = listaItemsAreaSaber;
  }

  public String getTituloObtenido() {
    return tituloObtenido;
  }

  public void setTituloObtenido(String tituloObtenido) {
    this.tituloObtenido = tituloObtenido;
  }

  public Date getFechaFinalizacion() {
    return fechaFinalizacion;
  }

  public void setFechaFinalizacion(Date fechaFinalizacion) {
    this.fechaFinalizacion = fechaFinalizacion;
  }

  public boolean isMostrarBotonGuardar() {
    return mostrarBotonGuardar;
  }

  public void setMostrarBotonGuardar(boolean mostrarBotonGuardar) {
    this.mostrarBotonGuardar = mostrarBotonGuardar;
  }

  public List<Investigador> getListaInvestigadores() {
    return listaInvestigadores;
  }

  public void setListaInvestigadores(List<Investigador> listaInvestigadores) {
    this.listaInvestigadores = listaInvestigadores;
  }

  public Investigador getInvestigadorSeleccionado() {
    return investigadorSeleccionado;
  }

  public void setInvestigadorSeleccionado(Investigador investigadorSeleccionado) {
    this.investigadorSeleccionado = investigadorSeleccionado;
  }

  public List<InvestigacionDesarrollada> getListaOtrasInvestigaciones() {
    return listaOtrasInvestigaciones;
  }

  public void setListaOtrasInvestigaciones(List<InvestigacionDesarrollada> listaOtrasInvestigaciones) {
    this.listaOtrasInvestigaciones = listaOtrasInvestigaciones;
  }

  public InvestigacionDesarrollada getInvestigacionSeleccionada() {
    return investigacionSeleccionada;
  }

  public void setInvestigacionSeleccionada(InvestigacionDesarrollada investigacionSeleccionada) {
    this.investigacionSeleccionada = investigacionSeleccionada;
  }

  public String getTituloInvestigacion() {
    return tituloInvestigacion;
  }

  public void setTituloInvestigacion(String tituloInvestigacion) {
    this.tituloInvestigacion = tituloInvestigacion;
  }

  public String getResumenInvestigacion() {
    return resumenInvestigacion;
  }

  public void setResumenInvestigacion(String resumenInvestigacion) {
    this.resumenInvestigacion = resumenInvestigacion;
  }

  public String getInstitucionInvestigacion() {
    return institucionInvestigacion;
  }

  public void setInstitucionInvestigacion(String institucionInvestigacion) {
    this.institucionInvestigacion = institucionInvestigacion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public boolean isMostrarBotonGuardarInvestigacion() {
    return mostrarBotonGuardarInvestigacion;
  }

  public void setMostrarBotonGuardarInvestigacion(boolean mostrarBotonGuardarInvestigacion) {
    this.mostrarBotonGuardarInvestigacion = mostrarBotonGuardarInvestigacion;
  }

  public List<PublicacionInvestigador> getListaPublicaciones() {
    return listaPublicaciones;
  }

  public void setListaPublicaciones(List<PublicacionInvestigador> listaPublicaciones) {
    this.listaPublicaciones = listaPublicaciones;
  }

  public PublicacionInvestigador getPublicacionSeleccionada() {
    return publicacionSeleccionada;
  }

  public void setPublicacionSeleccionada(PublicacionInvestigador publicacionSeleccionada) {
    this.publicacionSeleccionada = publicacionSeleccionada;
  }

  public Long getTipoPublicacion() {
    return tipoPublicacion;
  }

  public void setTipoPublicacion(Long tipoPublicacion) {
    this.tipoPublicacion = tipoPublicacion;
  }

  public List<SelectItem> getListaItemsTipoPublicacion() {
    return listaItemsTipoPublicacion;
  }

  public void setListaItemsTipoPublicacion(List<SelectItem> listaItemsTipoPublicacion) {
    this.listaItemsTipoPublicacion = listaItemsTipoPublicacion;
  }

  public String getTituloPublicacion() {
    return tituloPublicacion;
  }

  public void setTituloPublicacion(String tituloPublicacion) {
    this.tituloPublicacion = tituloPublicacion;
  }

  public String getResumenPublicacion() {
    return resumenPublicacion;
  }

  public void setResumenPublicacion(String resumenPublicacion) {
    this.resumenPublicacion = resumenPublicacion;
  }

  public Date getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(Date fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public Long getPaisPublicacion() {
    return paisPublicacion;
  }

  public void setPaisPublicacion(Long paisPublicacion) {
    this.paisPublicacion = paisPublicacion;
  }

  public List<SelectItem> getListaItemsPaises() {
    return listaItemsPaises;
  }

  public void setListaItemsPaises(List<SelectItem> listaItemsPaises) {
    this.listaItemsPaises = listaItemsPaises;
  }

  public String getCodigoISSN() {
    return codigoISSN;
  }

  public void setCodigoISSN(String codigoISSN) {
    this.codigoISSN = codigoISSN;
  }

  public String getNombreRevista() {
    return nombreRevista;
  }

  public void setNombreRevista(String nombreRevista) {
    this.nombreRevista = nombreRevista;
  }

  public String getVolumenRevista() {
    return volumenRevista;
  }

  public void setVolumenRevista(String volumenRevista) {
    this.volumenRevista = volumenRevista;
  }

  public String getFasciculoRevista() {
    return fasciculoRevista;
  }

  public void setFasciculoRevista(String fasciculoRevista) {
    this.fasciculoRevista = fasciculoRevista;
  }

  public String getSerieRevista() {
    return serieRevista;
  }

  public void setSerieRevista(String serieRevista) {
    this.serieRevista = serieRevista;
  }

  public int getPagInicioRevista() {
    return pagInicioRevista;
  }

  public void setPagInicioRevista(int pagInicioRevista) {
    this.pagInicioRevista = pagInicioRevista;
  }

  public String getCodigoISBN() {
    return codigoISBN;
  }

  public void setCodigoISBN(String codigoISBN) {
    this.codigoISBN = codigoISBN;
  }

  public String getNombreLibro() {
    return nombreLibro;
  }

  public void setNombreLibro(String nombreLibro) {
    this.nombreLibro = nombreLibro;
  }

  public String getEditorialLibro() {
    return editorialLibro;
  }

  public void setEditorialLibro(String editorialLibro) {
    this.editorialLibro = editorialLibro;
  }

  public int getPagInicioLibro() {
    return pagInicioLibro;
  }

  public void setPagInicioLibro(int pagInicioLibro) {
    this.pagInicioLibro = pagInicioLibro;
  }

  public List<SelectItem> getListaItemsNivelesFormacion() {
    return listaItemsNivelesFormacion;
  }

  public void setListaItemsNivelesFormacion(List<SelectItem> listaItemsNivelesFormacion) {
    this.listaItemsNivelesFormacion = listaItemsNivelesFormacion;
  }

  public List<SelectItem> getListaItemsProfesorPolicial() {
    return listaItemsProfesorPolicial;
  }

  public void setListaItemsProfesorPolicial(List<SelectItem> listaItemsProfesorPolicial) {
    this.listaItemsProfesorPolicial = listaItemsProfesorPolicial;
  }

  public boolean isMostrarPanelFormacion() {
    return mostrarPanelFormacion;
  }

  public void setMostrarPanelFormacion(boolean mostrarPanelFormacion) {
    this.mostrarPanelFormacion = mostrarPanelFormacion;
  }

  public List<FormacionInvestigador> getListaFormacionesInvestigador() {
    return listaFormacionesInvestigador;
  }

  public void setListaFormacionesInvestigador(List<FormacionInvestigador> listaFormacionesInvestigador) {
    this.listaFormacionesInvestigador = listaFormacionesInvestigador;
  }

  public boolean isMostrarCuandoEsRevista() {
    return mostrarCuandoEsRevista;
  }

  public void setMostrarCuandoEsRevista(boolean mostrarCuandoEsRevista) {
    this.mostrarCuandoEsRevista = mostrarCuandoEsRevista;
  }

  public boolean isMostrarCuandoEsLibro() {
    return mostrarCuandoEsLibro;
  }

  public void setMostrarCuandoEsLibro(boolean mostrarCuandoEsLibro) {
    this.mostrarCuandoEsLibro = mostrarCuandoEsLibro;
  }

  public boolean isMostrarCuandoEsCapituloEnLibro() {
    return mostrarCuandoEsCapituloEnLibro;
  }

  public void setMostrarCuandoEsCapituloEnLibro(boolean mostrarCuandoEsCapituloEnLibro) {
    this.mostrarCuandoEsCapituloEnLibro = mostrarCuandoEsCapituloEnLibro;
  }

  public boolean isMostrarCuandoEsOtro() {
    return mostrarCuandoEsOtro;
  }

  public void setMostrarCuandoEsOtro(boolean mostrarCuandoEsOtro) {
    this.mostrarCuandoEsOtro = mostrarCuandoEsOtro;
  }

  public boolean isExisteInvestigador() {
    return existeInvestigador;
  }

  public void setExisteInvestigador(boolean existeInvestigador) {
    this.existeInvestigador = existeInvestigador;
  }

  public boolean isHabilitadoParaGuardar() {
    return habilitadoParaGuardar;
  }

  public void setHabilitadoParaGuardar(boolean habilitadoParaGuardar) {
    this.habilitadoParaGuardar = habilitadoParaGuardar;
  }

  public boolean isCamposDeshabilitados() {
    return camposDeshabilitados;
  }

  public void setCamposDeshabilitados(boolean camposDeshabilitados) {
    this.camposDeshabilitados = camposDeshabilitados;
  }

  public boolean isFormacionRequerida() {
    return formacionRequerida;
  }

  public void setFormacionRequerida(boolean formacionRequerida) {
    this.formacionRequerida = formacionRequerida;
  }

  public Date getFechaActual() {
    return fechaActual;
  }

  public void setFechaActual(Date fechaActual) {
    this.fechaActual = fechaActual;
  }

  public String getNombrePaisPublicacion() {
    return nombrePaisPublicacion;
  }

  public void setNombrePaisPublicacion(String nombrePaisPublicacion) {
    this.nombrePaisPublicacion = nombrePaisPublicacion;
  }

  public boolean isEsInvestigador() {

    return investigadorSeleccionado != null;

  }

  public boolean isEsFuncionario() {

    return vistaFuncionarioSeleccionado != null;

  }

  public VistaFuncionario getVistaFuncionarioSeleccionado() {
    return vistaFuncionarioSeleccionado;
  }

  public void setVistaFuncionarioSeleccionado(VistaFuncionario vistaFuncionarioSeleccionado) {
    this.vistaFuncionarioSeleccionado = vistaFuncionarioSeleccionado;
  }

  public List<VistaFormacionFuncionario> getListaVistaFormacion() {
    return listaVistaFormacion;
  }

  public void setListaVistaFormacion(List<VistaFormacionFuncionario> listaVistaFormacion) {
    this.listaVistaFormacion = listaVistaFormacion;
  }

  public String getProfesorPolicialSIAT() {
    return profesorPolicialSIAT;
  }

  public void setProfesorPolicialSIAT(String profesorPolicialSIAT) {
    this.profesorPolicialSIAT = profesorPolicialSIAT;
  }

}
