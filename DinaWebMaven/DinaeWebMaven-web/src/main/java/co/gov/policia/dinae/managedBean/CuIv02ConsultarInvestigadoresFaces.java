package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ESTADO_VINCULACION_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.NIVEL_FORMACION_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_CARACTERISTICAS_INVESTIGADOR;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_ARTICULO_EN_REVISTA;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_ARTICULO_EN_REVISTA_INDEXADA;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_CAPITULO_EN_LIBRO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_LIBRO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_PUBLICACION_OTRO;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_VINCULACION_PROYECTO;
import co.gov.policia.dinae.dto.AntiguedadDTO;
import co.gov.policia.dinae.dto.InformacionAdicionalPersonaSiatDTO;
import co.gov.policia.dinae.dto.InvestigadorDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.GeneradorReportesServicio;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.interfaces.UtilJPA;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FormacionInvestigador;
import co.gov.policia.dinae.modelo.InvestigacionDesarrollada;
import co.gov.policia.dinae.modelo.Investigador;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.PublicacionInvestigador;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Édder Peña Barranco
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 * @since 2013/12/24
 */
@Named(value = "cuIv02ConsultarInvestigadores")
@SessionScoped
public class CuIv02ConsultarInvestigadoresFaces extends JSFUtils implements Serializable {

  @EJB
  private IUnidadPolicialLocal iUnidadPolicial;

  @EJB
  private IConstantesLocal iConstantes;

  @EJB
  private IInvestigadorLocal iInvestigador;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyecto;

  @EJB
  private IVistaFuncionarioLocal iVistaFuncionarioLocal;

  private Long unidadPolicial;
  private List<SelectItem> listaItemsUnidadesPoliciales;
  private String numIdentificacion;
  private String nombresInvestigador;
  private String apellidosInvestigador;
  private Long estado;
  private List<SelectItem> listaItemsEstados;
  private String profesorPolicial;
  private List<Long> nivelesFormacionSeleccionados;
  private List<SelectItem> listaItemsNivelesFormacion;
  private List<Long> caracteristicasSeleccionadas;
  private List<SelectItem> listaItemsCaracteristicas;
  private Long idInvestigadorSeleccionado;
  private String gradoInvestigador;
  private List<SelectItem> listaItemsGrados;
  private List<SelectItem> listaCategoriaPolicial;
  private Long idCategoriaPolicial;
  private Long vinculacion;
  private List<SelectItem> listaItemsVinculacion;
  private List<InvestigadorDTO> listaInvestigadores;
  private List<Proyecto> listaTrabajosOProyectos;
  private Investigador investigadorSeleccionado;
  private InvestigadorDTO investigadorSeleccionadoDTO;
  private String nombreGradoInvestigador;
  private String nombreInvestigador;
  private String emailInvestigador;
  private String cargoInvestigador;
  private String direccionInvestigador;
  private String nombreCiudadInvestigador;
  private String nombreDeptoInvestigador;
  private String aniosInstitucionInvestigador;
  private String mesesInstitucionInvestigador;
  private String diasInstitucionInvestigador;
  private String tipoVinculacionInvestigador;
  private String funcionesInvestigador;
  private List<FormacionInvestigador> listaFormacionesInvestigador;
  private VistaFuncionario investigadorSIATH;
  private boolean esInvestigadorSIATH = false;
  private List<InvestigadorProyecto> listaInvestigadoresVICIN;
  private List<InvestigacionDesarrollada> listaOtrasInvestigaciones;
  private List<PublicacionInvestigador> listaPublicaciones;
  private PublicacionInvestigador publicacionSeleccionada;
  private boolean mostrarCuandoEsRevista = false;
  private boolean mostrarCuandoEsLibro = false;
  private boolean mostrarCuandoEsCapituloEnLibro = false;
  private boolean mostrarCuandoEsOtro = false;
  private boolean mostrarDetallesPublicacion = false;

  private final String labelAnios = " Años, ";
  private final String labelAnio = " Año, ";
  private final String labelMeses = " Meses, ";
  private final String labelMes = " Mes, ";
  private final String labelDias = " Dias";
  private final String labelDia = " Día";

  private String profesorPolicialSIAT;

  /**
   *
   */
  private void init() {

    profesorPolicialSIAT = null;
    unidadPolicial = null;
    listaItemsUnidadesPoliciales = null;
    numIdentificacion = null;
    nombresInvestigador = null;
    apellidosInvestigador = null;
    estado = null;
    listaItemsEstados = null;
    profesorPolicial = null;
    nivelesFormacionSeleccionados = null;
    listaItemsNivelesFormacion = null;
    caracteristicasSeleccionadas = null;
    listaItemsCaracteristicas = null;
    idInvestigadorSeleccionado = null;
    gradoInvestigador = null;
    listaItemsGrados = null;
    vinculacion = null;
    listaItemsVinculacion = null;
    listaInvestigadores = null;
    listaTrabajosOProyectos = null;
    investigadorSeleccionado = null;
    nombreGradoInvestigador = null;
    nombreInvestigador = null;
    emailInvestigador = null;
    cargoInvestigador = null;
    direccionInvestigador = null;
    nombreCiudadInvestigador = null;
    nombreDeptoInvestigador = null;
    aniosInstitucionInvestigador = null;
    mesesInstitucionInvestigador = null;
    diasInstitucionInvestigador = null;
    tipoVinculacionInvestigador = null;
    funcionesInvestigador = null;
    listaFormacionesInvestigador = null;
    investigadorSIATH = null;
    esInvestigadorSIATH = false;
    listaInvestigadoresVICIN = null;
    listaOtrasInvestigaciones = null;
    listaPublicaciones = null;
    publicacionSeleccionada = null;
    mostrarCuandoEsRevista = false;
    mostrarCuandoEsLibro = false;
    mostrarCuandoEsCapituloEnLibro = false;
    mostrarCuandoEsOtro = false;
    mostrarDetallesPublicacion = false;
    investigadorSeleccionadoDTO = null;
    listaCategoriaPolicial = new ArrayList<SelectItem>();
    idCategoriaPolicial = null;
  }

  public String initReturnCU() {

    init();

    cargarListaUnidadesPoliciales();
    cargarListaEstadosInvestigador();
    cargarListaNivelesFormacion();
    cargarListaCaracteristicasInvestigador();
    cargarListaTiposVinculacion();
    cargarListaCategoriaPolicial();

    return navigationFaces.redirectCuIv02ConsultarInvestigadores();
  }

  /**
   * ***********************************************************************
   */
  /**
   * *********** CARGAR DATOS INICIALES ************
   */
  private void cargarListaUnidadesPoliciales() {
    List<UnidadPolicial> unidadesPoliciales;
    if (listaItemsUnidadesPoliciales == null || listaItemsUnidadesPoliciales.isEmpty()) {
      listaItemsUnidadesPoliciales = new ArrayList<SelectItem>();
    }
    try {
      unidadesPoliciales = iUnidadPolicial.getAllUnidadesPolicialesActivas();
      for (UnidadPolicial up : unidadesPoliciales) {
        listaItemsUnidadesPoliciales.add(new SelectItem(up.getIdUnidadPolicial(), up.getSiglaFisicaYnombreUnidad()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("cu_pr_13_error_unidades_policiales_no_encontradas"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaEstadosInvestigador() {
    List<Constantes> constantes;
    listaItemsEstados = new ArrayList<SelectItem>();
    try {
      constantes = iConstantes.getConstantesPorTipo(TIPO_ESTADO_VINCULACION_INVESTIGADOR);
      for (Constantes con : constantes) {
        listaItemsEstados.add(new SelectItem(con.getIdConstantes(), con.getValor()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_02_error_estados_investigadores_no_encontrados"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaNivelesFormacion() {
    List<Constantes> nivelesFormacion;
    listaItemsNivelesFormacion = new ArrayList<SelectItem>();
    try {
      nivelesFormacion = iConstantes.getConstantesPorTipo(NIVEL_FORMACION_INVESTIGADOR);
      for (Constantes con : nivelesFormacion) {
        listaItemsNivelesFormacion.add(new SelectItem(con.getIdConstantes(), con.getValor()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_02_error_niveles_formacion_no_encontrados"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaCaracteristicasInvestigador() {
    List<Constantes> caracteristicas;
    listaItemsCaracteristicas = new ArrayList<SelectItem>();
    try {
      caracteristicas = iConstantes.getConstantesPorTipo(TIPO_CARACTERISTICAS_INVESTIGADOR);
      for (Constantes con : caracteristicas) {
        listaItemsCaracteristicas.add(new SelectItem(con.getIdConstantes(), con.getValor()));
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_02_error_caracteristicas_no_encontradas"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaGrados() {

    try {

      if (idCategoriaPolicial == null || idCategoriaPolicial.equals(-1L)) {

        listaItemsGrados = new ArrayList<SelectItem>();
        return;
      }

      listaItemsGrados = UtilidadesItem.getListaSel(
              KeyPropertiesFactory.getMapaListaGradoCategoriaDTO().get(idCategoriaPolicial),
              "alfabitico", "albabeticoDescripcion");
    } catch (Exception ex) {
      adicionaMensajeError(keyPropertiesFactory.value("cu_iv_02_error_grados_no_encontrados"));
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void cargarListaTiposVinculacion() {
    listaItemsVinculacion = new ArrayList<SelectItem>();
    List<Constantes> listaTiposVinculacion;
    try {
      listaTiposVinculacion = iConstantes.getConstantesPorTipo(TIPO_VINCULACION_PROYECTO);
      for (Constantes cons : listaTiposVinculacion) {
        listaItemsVinculacion.add(new SelectItem(cons.getIdConstantes(), cons.getValor()));
      }
    } catch (JpaDinaeException ex) {
      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void cargarListaInvestigacionesVICIN() {
    if (investigadorSeleccionado == null) {

      adicionaMensajeError("No se seleccionó ningún investigador. No se encontraron resultados");
      return;
    }
    try {
      listaInvestigadoresVICIN = iInvestigadorProyecto.getListaInvestigadorProyectoByIdentificacion(
              investigadorSeleccionado.getNumeroIdentificacion());
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param esInvestigador
   */
  private void cargarListaOtrasInvestigaciones(boolean esInvestigador, String identificacion) {

    if (esInvestigador && investigadorSeleccionado == null) {

      adicionaMensajeError("No se seleccionó ningún investigador. No se encontraron resultados");
      return;
    }

    try {

      listaOtrasInvestigaciones = iInvestigador.getInvestigacionesDesarrolladasByNumIdentificacion(esInvestigador, identificacion);
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param esInvestigador
   */
  private void cargarListaPublicaciones(boolean esInvestigador, String identificacion) {

    if (esInvestigador && investigadorSeleccionado == null) {
      adicionaMensajeError("No se seleccionó ningún investigador. No se encontraron resultados");
      return;
    }
    try {
      listaPublicaciones = iInvestigador.getPublicacionesByNumIdentificacion(esInvestigador, identificacion);
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public String buscarInvestigador() {
    boolean filtroIngresado = false;
    Long criterioUnidadPolicial = null;
    String criterioIdentificacion = null;
    String criterioNombres = null;
    String criterioApellidos = null;
    Long criterioEstado = null;
    String criterioProfesorPolicial = null;
    List<Long> criterioNivelesFormacion = new ArrayList<Long>();
    List<Long> criterioCaracteristicas = new ArrayList<Long>();
    String criterioGrado = null;
    Long criterioVinculacion = null;

    if (unidadPolicial != null) {
      criterioUnidadPolicial = unidadPolicial;
      filtroIngresado = true;
    }
    if (numIdentificacion != null && !numIdentificacion.trim().equals("")) {
      criterioIdentificacion = numIdentificacion;
      filtroIngresado = true;
    }
    if (nombresInvestigador != null && !nombresInvestigador.trim().equals("")) {
      criterioNombres = nombresInvestigador;
      filtroIngresado = true;
    }
    if (apellidosInvestigador != null && !apellidosInvestigador.trim().equals("")) {
      criterioApellidos = apellidosInvestigador;
      filtroIngresado = true;
    }
    if (estado != null && estado > 0) {
      criterioEstado = estado;
      filtroIngresado = true;
    }
    if (profesorPolicial != null && !profesorPolicial.trim().equals("")) {
      criterioProfesorPolicial = profesorPolicial;
      filtroIngresado = true;
    }
    if (nivelesFormacionSeleccionados != null && !nivelesFormacionSeleccionados.isEmpty()) {
      criterioNivelesFormacion = nivelesFormacionSeleccionados;
      filtroIngresado = true;
    }
    if (caracteristicasSeleccionadas != null && !caracteristicasSeleccionadas.isEmpty()) {
      criterioCaracteristicas = caracteristicasSeleccionadas;
      filtroIngresado = true;
    }
    if (gradoInvestigador != null && gradoInvestigador.trim().length() > 0) {
      criterioGrado = gradoInvestigador.trim();
      filtroIngresado = true;
    }
    if (vinculacion != null && vinculacion > 0) {
      criterioVinculacion = vinculacion;
      filtroIngresado = true;
    }

    if (!filtroIngresado) {
      adicionaMensajeError("Debe ingresar algún criterio para la búsqueda");
      return null;
    }
    try {
      listaInvestigadores = iInvestigador.getInvestigadoresYPersonalSiatFiltros(criterioUnidadPolicial, criterioIdentificacion, criterioNombres,
              criterioApellidos, criterioEstado, criterioProfesorPolicial, criterioNivelesFormacion,
              criterioCaracteristicas, criterioGrado, criterioVinculacion);

      if (listaInvestigadores.isEmpty()) {
        adicionaMensajeError("La consulta no retornó información");
      }
    } catch (JpaDinaeException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    return "";
  }

  public String regresarConsultarInvestigadores() {
    return navigationFaces.redirectCuIv02ConsultarInvestigadores();
  }

  public String borrarFormulario() {
    numIdentificacion = null;
    nombresInvestigador = null;
    apellidosInvestigador = null;
    estado = -1L;
    profesorPolicial = null;
    nivelesFormacionSeleccionados = new ArrayList<Long>();
    caracteristicasSeleccionadas = new ArrayList<Long>();
    gradoInvestigador = null;
    vinculacion = -1L;
    listaInvestigadores = new ArrayList<InvestigadorDTO>();
    return "";
  }

  /**
   *
   * @param event
   */
  public void irDetallesInvestigador(SelectEvent event) {
    try {
      listaFormacionesInvestigador = new ArrayList<FormacionInvestigador>();

      if (investigadorSeleccionadoDTO == null || investigadorSeleccionadoDTO.getOrigen() == null) {
        return;
      }

      investigadorSIATH = null;
      esInvestigadorSIATH = false;
      investigadorSeleccionado = null;

      if (investigadorSeleccionadoDTO.getOrigen().equals("INVESTIGADOR")) {

        investigadorSeleccionado = iInvestigador.getInvestigadorPorNumeroIdentificacion(investigadorSeleccionadoDTO.getIdentifica());
        idInvestigadorSeleccionado = investigadorSeleccionado.getIdInvestigador();
        nombreGradoInvestigador = investigadorSeleccionado.getGrado();
        nombreInvestigador = investigadorSeleccionado.getNombres().concat(" ").concat(investigadorSeleccionado.getApellidos());
        emailInvestigador = investigadorSeleccionado.getCorreoElectronico();
        direccionInvestigador = investigadorSeleccionado.getDireccion();
        nombreCiudadInvestigador = investigadorSeleccionado.getNombreCiudadResidencia();
        nombreDeptoInvestigador = investigadorSeleccionado.getNombreDeptoResidencia();
        tipoVinculacionInvestigador = investigadorSeleccionado.getTipoVinculacion().getValor();
        funcionesInvestigador = investigadorSeleccionado.getFuncionInvestigador();
        listaFormacionesInvestigador = investigadorSeleccionado.getFormacionInvestigadorList();

      } else if (investigadorSeleccionadoDTO.getOrigen().equals("SIATH")) {

        investigadorSIATH = iInvestigador.getInvestigadorSIATHByIdentificacion(investigadorSeleccionadoDTO.getIdentifica());

        if (investigadorSIATH != null) {

          esInvestigadorSIATH = true;
          cargoInvestigador = investigadorSIATH.getCargo();
          nombreGradoInvestigador = investigadorSIATH.getGrado();
          nombreInvestigador = investigadorSIATH.getNombreCompleto();
          emailInvestigador = investigadorSIATH.getCorreo();
          direccionInvestigador = investigadorSIATH.getDireccionEmpleado();
          nombreCiudadInvestigador = investigadorSIATH.getCiudadReside();
          nombreDeptoInvestigador = investigadorSIATH.getDepartamentoReside();

          aniosInstitucionInvestigador = " - ";

          AntiguedadDTO antiguedadDTO = new AntiguedadDTO(0L, 0L, 0L);
          if (investigadorSIATH.getIngresoInstitucion() != null) {

            antiguedadDTO = UtilJPA.calcularAntiguedadEntreDosfecha(investigadorSIATH.getIngresoInstitucion(), new Date());
          }

          int anio = antiguedadDTO.getAnosAntiguedad().intValue();
          int mes = antiguedadDTO.getMesesAntiguiedad().intValue();
          int dias = antiguedadDTO.getDiasAntiguedad().intValue();

          aniosInstitucionInvestigador = ((anio > 0) ? ((anio == 1) ? anio + " Año, " : anio + " Años, ") : "")
                  + ((mes > 0) ? ((mes == 1) ? mes + " Mes, " : mes + " Meses, ") : "")
                  + ((dias > 0) ? ((dias == 1) ? dias + " Dia" : dias + " dias") : "");

          InformacionAdicionalPersonaSiatDTO informacionAdicionalPersonaSiatDTO = iVistaFuncionarioLocal.obtenerInformacionAdicionalPersonaSIAT(investigadorSeleccionadoDTO.getIdentifica());

          profesorPolicialSIAT = informacionAdicionalPersonaSiatDTO != null ? informacionAdicionalPersonaSiatDTO.getProfesorPolicial() : null;
        }
      }

      cargarListaInvestigacionesVICIN();
      cargarListaOtrasInvestigaciones(investigadorSeleccionadoDTO.getOrigen().equals("INVESTIGADOR"), investigadorSeleccionadoDTO.getIdentifica());
      cargarListaPublicaciones(investigadorSeleccionadoDTO.getOrigen().equals("INVESTIGADOR"), investigadorSeleccionadoDTO.getIdentifica());

      navigationFaces.redirectFacesCuIv02DetallesInvestigador();
    } catch (JpaDinaeException jpae) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, jpae);
    } catch (IOException ex) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void seleccionarPublicacionConsultada() {
    mostrarDetallesPublicacion = true;
    if (publicacionSeleccionada.getTipoPublicacion().getIdConstantes().equals(TIPO_PUBLICACION_ARTICULO_EN_REVISTA)
            || publicacionSeleccionada.getTipoPublicacion().getIdConstantes().equals(TIPO_PUBLICACION_ARTICULO_EN_REVISTA_INDEXADA)) {
      mostrarCuandoEsRevista = true;
      mostrarCuandoEsLibro = false;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = false;
    } else if (publicacionSeleccionada.getTipoPublicacion().getIdConstantes().equals(TIPO_PUBLICACION_LIBRO)) {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = true;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = false;
    } else if (publicacionSeleccionada.getTipoPublicacion().getIdConstantes().equals(TIPO_PUBLICACION_CAPITULO_EN_LIBRO)) {
      mostrarCuandoEsRevista = false;
      mostrarCuandoEsLibro = true;
      mostrarCuandoEsOtro = true;
      mostrarCuandoEsCapituloEnLibro = true;
    } else if (publicacionSeleccionada.getTipoPublicacion().getIdConstantes().equals(TIPO_PUBLICACION_OTRO)) {
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

  /**
   * ***********************************************************************
   */
  /**
   * ********** SETTERS Y GETTERS ************
   */
  public Long getUnidadPolicial() {
    return unidadPolicial;
  }

  public void setUnidadPolicial(Long unidadPolicial) {
    this.unidadPolicial = unidadPolicial;
  }

  public List<SelectItem> getListaItemsUnidadesPoliciales() {
    return listaItemsUnidadesPoliciales;
  }

  public void setListaItemsUnidadesPoliciales(List<SelectItem> listaItemsUnidadesPoliciales) {
    this.listaItemsUnidadesPoliciales = listaItemsUnidadesPoliciales;
  }

  public String getNumIdentificacion() {
    return numIdentificacion;
  }

  public void setNumIdentificacion(String numIdentificacion) {
    this.numIdentificacion = numIdentificacion;
  }

  public String getNombresInvestigador() {
    return nombresInvestigador;
  }

  public void setNombresInvestigador(String nombresInvestigador) {
    this.nombresInvestigador = nombresInvestigador;
  }

  public String getApellidosInvestigador() {
    return apellidosInvestigador;
  }

  public void setApellidosInvestigador(String apellidosInvestigador) {
    this.apellidosInvestigador = apellidosInvestigador;
  }

  public Long getEstado() {
    return estado;
  }

  public void setEstado(Long estado) {
    this.estado = estado;
  }

  public List<SelectItem> getListaItemsEstados() {
    return listaItemsEstados;
  }

  public void setListaItemsEstados(List<SelectItem> listaItemsEstados) {
    this.listaItemsEstados = listaItemsEstados;
  }

  public String getProfesorPolicial() {
    return profesorPolicial;
  }

  public void setProfesorPolicial(String profesorPolicial) {
    this.profesorPolicial = profesorPolicial;
  }

  public List<Long> getNivelesFormacionSeleccionados() {
    return nivelesFormacionSeleccionados;
  }

  public void setNivelesFormacionSeleccionados(List<Long> nivelesFormacionSeleccionados) {
    this.nivelesFormacionSeleccionados = nivelesFormacionSeleccionados;
  }

  public List<SelectItem> getListaItemsNivelesFormacion() {
    return listaItemsNivelesFormacion;
  }

  public void setListaItemsNivelesFormacion(List<SelectItem> listaItemsNivelesFormacion) {
    this.listaItemsNivelesFormacion = listaItemsNivelesFormacion;
  }

  public List<Long> getCaracteristicasSeleccionadas() {
    return caracteristicasSeleccionadas;
  }

  public void setCaracteristicasSeleccionadas(List<Long> caracteristicasSeleccionadas) {
    this.caracteristicasSeleccionadas = caracteristicasSeleccionadas;
  }

  public List<SelectItem> getListaItemsCaracteristicas() {
    return listaItemsCaracteristicas;
  }

  public void setListaItemsCaracteristicas(List<SelectItem> listaItemsCaracteristicas) {
    this.listaItemsCaracteristicas = listaItemsCaracteristicas;
  }

  public Long getIdInvestigadorSeleccionado() {
    return idInvestigadorSeleccionado;
  }

  public void setIdInvestigadorSeleccionado(Long idInvestigadorSeleccionado) {
    this.idInvestigadorSeleccionado = idInvestigadorSeleccionado;
  }

  public String getGradoInvestigador() {
    return gradoInvestigador;
  }

  public void setGradoInvestigador(String gradoInvestigador) {
    this.gradoInvestigador = gradoInvestigador;
  }

  public List<SelectItem> getListaItemsGrados() {
    return listaItemsGrados;
  }

  public void setListaItemsGrados(List<SelectItem> listaItemsGrados) {
    this.listaItemsGrados = listaItemsGrados;
  }

  public Long getVinculacion() {
    return vinculacion;
  }

  public void setVinculacion(Long vinculacion) {
    this.vinculacion = vinculacion;
  }

  public List<SelectItem> getListaItemsVinculacion() {
    return listaItemsVinculacion;
  }

  public void setListaItemsVinculacion(List<SelectItem> listaItemsVinculacion) {
    this.listaItemsVinculacion = listaItemsVinculacion;
  }

  public List<InvestigadorDTO> getListaInvestigadores() {
    return listaInvestigadores;
  }

  public void setListaInvestigadores(List<InvestigadorDTO> listaInvestigadores) {
    this.listaInvestigadores = listaInvestigadores;
  }

  public List<Proyecto> getListaTrabajosOProyectos() {
    return listaTrabajosOProyectos;
  }

  public void setListaTrabajosOProyectos(List<Proyecto> listaTrabajosOProyectos) {
    this.listaTrabajosOProyectos = listaTrabajosOProyectos;
  }

  public Investigador getInvestigadorSeleccionado() {
    return investigadorSeleccionado;
  }

  public void setInvestigadorSeleccionado(Investigador investigadorSeleccionado) {
    this.investigadorSeleccionado = investigadorSeleccionado;
  }

  public String getNombreGradoInvestigador() {
    return nombreGradoInvestigador;
  }

  public void setNombreGradoInvestigador(String nombreGradoInvestigador) {
    this.nombreGradoInvestigador = nombreGradoInvestigador;
  }

  public String getNombreInvestigador() {
    return nombreInvestigador;
  }

  public void setNombreInvestigador(String nombreInvestigador) {
    this.nombreInvestigador = nombreInvestigador;
  }

  public String getEmailInvestigador() {
    return emailInvestigador;
  }

  public void setEmailInvestigador(String emailInvestigador) {
    this.emailInvestigador = emailInvestigador;
  }

  public String getCargoInvestigador() {
    return cargoInvestigador;
  }

  public void setCargoInvestigador(String cargoInvestigador) {
    this.cargoInvestigador = cargoInvestigador;
  }

  public String getDireccionInvestigador() {
    return direccionInvestigador;
  }

  public void setDireccionInvestigador(String direccionInvestigador) {
    this.direccionInvestigador = direccionInvestigador;
  }

  public String getNombreCiudadInvestigador() {
    return nombreCiudadInvestigador;
  }

  public void setNombreCiudadInvestigador(String nombreCiudadInvestigador) {
    this.nombreCiudadInvestigador = nombreCiudadInvestigador;
  }

  public String getNombreDeptoInvestigador() {
    return nombreDeptoInvestigador;
  }

  public void setNombreDeptoInvestigador(String nombreDeptoInvestigador) {
    this.nombreDeptoInvestigador = nombreDeptoInvestigador;
  }

  public String getAniosInstitucionInvestigador() {
    return aniosInstitucionInvestigador;
  }

  public void setAniosInstitucionInvestigador(String aniosInstitucionInvestigador) {
    this.aniosInstitucionInvestigador = aniosInstitucionInvestigador;
  }

  public String getMesesInstitucionInvestigador() {
    return mesesInstitucionInvestigador;
  }

  public void setMesesInstitucionInvestigador(String mesesInstitucionInvestigador) {
    this.mesesInstitucionInvestigador = mesesInstitucionInvestigador;
  }

  public String getDiasInstitucionInvestigador() {
    return diasInstitucionInvestigador;
  }

  public void setDiasInstitucionInvestigador(String diasInstitucionInvestigador) {
    this.diasInstitucionInvestigador = diasInstitucionInvestigador;
  }

  public String getTipoVinculacionInvestigador() {
    return tipoVinculacionInvestigador;
  }

  public void setTipoVinculacionInvestigador(String tipoVinculacionInvestigador) {
    this.tipoVinculacionInvestigador = tipoVinculacionInvestigador;
  }

  public String getFuncionesInvestigador() {
    return funcionesInvestigador;
  }

  public void setFuncionesInvestigador(String funcionesInvestigador) {
    this.funcionesInvestigador = funcionesInvestigador;
  }

  public List<FormacionInvestigador> getListaFormacionesInvestigador() {
    return listaFormacionesInvestigador;
  }

  public void setListaFormacionesInvestigador(List<FormacionInvestigador> listaFormacionesInvestigador) {
    this.listaFormacionesInvestigador = listaFormacionesInvestigador;
  }

  public String getLabelAnios() {
    return labelAnios;
  }

  public String getLabelAnio() {
    return labelAnio;
  }

  public String getLabelMeses() {
    return labelMeses;
  }

  public String getLabelMes() {
    return labelMes;
  }

  public String getLabelDias() {
    return labelDias;
  }

  public String getLabelDia() {
    return labelDia;
  }

  public boolean isEsInvestigadorSIATH() {
    return esInvestigadorSIATH;
  }

  public void setEsInvestigadorSIATH(boolean esInvestigadorSIATH) {
    this.esInvestigadorSIATH = esInvestigadorSIATH;
  }

  public List<InvestigadorProyecto> getListaInvestigadoresVICIN() {
    return listaInvestigadoresVICIN;
  }

  public void setListaInvestigacionVICIN(List<InvestigadorProyecto> listaInvestigadoresVICIN) {
    this.listaInvestigadoresVICIN = listaInvestigadoresVICIN;
  }

  public List<InvestigacionDesarrollada> getListaOtrasInvestigaciones() {
    return listaOtrasInvestigaciones;
  }

  public void setListaOtrasInvestigaciones(List<InvestigacionDesarrollada> listaOtrasInvestigaciones) {
    this.listaOtrasInvestigaciones = listaOtrasInvestigaciones;
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

  public boolean isMostrarDetallesPublicacion() {
    return mostrarDetallesPublicacion;
  }

  public void setMostrarDetallesPublicacion(boolean mostrarDetallesPublicacion) {
    this.mostrarDetallesPublicacion = mostrarDetallesPublicacion;
  }

  public InvestigadorDTO getInvestigadorSeleccionadoDTO() {
    return investigadorSeleccionadoDTO;
  }

  public void setInvestigadorSeleccionadoDTO(InvestigadorDTO investigadorSeleccionadoDTO) {
    this.investigadorSeleccionadoDTO = investigadorSeleccionadoDTO;
  }

  /**
   *
   */
  private void cargarListaCategoriaPolicial() {

    try {

      listaCategoriaPolicial = UtilidadesItem.getListaSel(KeyPropertiesFactory.getListaCategoriaPolicialDTO(), "idCategoria", "descripcion");

    } catch (Exception e) {

      Logger.getLogger(CuIv01GestionarInvestigadoresFaces.class.getName()).log(Level.SEVERE, null, e);

    }
  }

  public List<SelectItem> getListaCategoriaPolicial() {
    return listaCategoriaPolicial;
  }

  public void setListaCategoriaPolicial(List<SelectItem> listaCategoriaPolicial) {
    this.listaCategoriaPolicial = listaCategoriaPolicial;
  }

  public Long getIdCategoriaPolicial() {
    return idCategoriaPolicial;
  }

  public void setIdCategoriaPolicial(Long idCategoriaPolicial) {
    this.idCategoriaPolicial = idCategoriaPolicial;
  }

  public void handleCategoriaSeleccionadaSeleccionadaChange() {

    cargarListaGrados();
  }

  /**
   *
   * @return
   */
  public StreamedContent getGenerarReporteDetalle() {

    try {

      if (investigadorSeleccionadoDTO.getOrigen() == null || investigadorSeleccionadoDTO.getIdentifica() == null) {
        return null;
      }

      String sesion = String.valueOf(System.currentTimeMillis());

      iInvestigador.buscarNivelesAcademicos(sesion, investigadorSeleccionadoDTO.getIdentifica(), investigadorSeleccionadoDTO.getOrigen());

      HashMap mapa = new HashMap();
      mapa.put("p_cedula", investigadorSeleccionadoDTO.getIdentifica());
      mapa.put("p_sesion", sesion);
      mapa.put("p_id_investigador", -1);
      mapa.put("p_tipo_vinculacion", investigadorSeleccionadoDTO.getNombreTipoVinculacion());

      UnidadPolicial unidadPolicialLocal;
      try {

        unidadPolicialLocal = iUnidadPolicial.getUnidadPolicialPorIdentificacionOinvestigador(investigadorSeleccionadoDTO.getIdentifica(), investigadorSeleccionadoDTO.getOrigen());

      } catch (Exception e) {

        unidadPolicialLocal = null;

      }

      mapa.put("p_unidad", unidadPolicialLocal != null ? unidadPolicialLocal.getSiglaFisicaYnombreUnidad() : null);

      if (investigadorSeleccionadoDTO.getOrigen().equals("INVESTIGADOR")) {

        mapa.put("p_grado", investigadorSeleccionado.getGrado());
        mapa.put("p_nombres", investigadorSeleccionado.getNombres().concat(" ").concat(investigadorSeleccionado.getApellidos()));
        mapa.put("p_correo", investigadorSeleccionado.getCorreoElectronico());

      } else if (investigadorSeleccionadoDTO.getOrigen().equals("SIATH")) {

        mapa.put("p_nombres", investigadorSIATH.getNombreCompleto());
        mapa.put("p_correo", investigadorSIATH.getCorreo());

      }

      byte[] bitesPdf = GeneradorReportesServicio.getInstancia().generarReporte(mapa, "reporte14.jasper");

      InputStream stream = new ByteArrayInputStream(bitesPdf);

      String contentType = "application/octet-stream";

      StreamedContent download = new DefaultStreamedContent(stream, contentType, "14_FORMATO INVESTIGADOR.pdf");

      return download;

    } catch (Exception e) {

      adicionaMensajeError("Error al generar el reporte");
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "REPORTE_JASPER ERROR (getDownloadContentProperty)", e);
    }
    return null;
  }

  public String getProfesorPolicialSIAT() {
    return profesorPolicialSIAT;
  }

  public void setProfesorPolicialSIAT(String profesorPolicialSIAT) {
    this.profesorPolicialSIAT = profesorPolicialSIAT;
  }

}
