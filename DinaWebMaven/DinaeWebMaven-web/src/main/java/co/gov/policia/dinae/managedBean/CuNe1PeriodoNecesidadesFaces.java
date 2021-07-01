package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IMailSessionLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.interfaces.IPropuestaNecesidadLocal;
import co.gov.policia.dinae.interfaces.ITipoUnidadLocal;
import co.gov.policia.dinae.interfaces.ITipoUnidadPeriodoLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.TipoUnidad;
import co.gov.policia.dinae.modelo.TipoUnidadPeriodo;
import co.gov.policia.dinae.dto.ArchivoReporte;
import co.gov.policia.dinae.util.JSFUtils;
import co.gov.policia.dinae.dto.StringUtils;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Faces que maneja el proceso de Creación y Lista de Periodos de necesidad
 *
 * @author juan
 */
@javax.inject.Named(value = "cuNe1PeriodoNecesidadesFaces")
@javax.enterprise.context.SessionScoped
public class CuNe1PeriodoNecesidadesFaces extends JSFUtils implements Serializable {

  @javax.inject.Inject
  private CuNe6EvaluarPropuestasNecesidadesInvestigacionFaces cuNe6EvaluarPropuestasNecesidadesInvestigacionFaces;

  @EJB
  private IUnidadPolicialLocal iUnidadPolicialLocal;
  @EJB
  private ITipoUnidadLocal tipoUnidadLocal;
  @EJB
  private IPeriodoLocal iPeriodoLocal;
  @EJB
  private IConstantesLocal iConstantesLocal;
  @EJB
  private ITipoUnidadPeriodoLocal iTipoUnidadPeriodoLocal;
  @EJB
  private IUsuarioUnidadPolicialLocal iUsuarioUnidadPolicialLocal;
  @EJB
  private IMailSessionLocal iMailSessionBean;
  @EJB
  private IPropuestaNecesidadLocal iPropuestaNecesidadLocal;

  private Integer periodo;
  private String descripción;
  private String cantidadPropuestaUnidad;
  private Integer peridoMaximo;
  private HashMap<String, String> tipoUnidades = new HashMap<String, String>();
  private Date fechaInicio;
  private Date fechaFinal;
  private Date fechaFinalHito;
  private Periodo periodoSelect;
  private List<SelectItem> selectsTiposHitos;
  private List<Periodo> periodos = new ArrayList<Periodo>();
  private String activeIndex = "0";
  private List<UnidadPolicialDTO> listaUnidadesPolicialesDTOperiodo;

  private static final List<Long> listaRoleCorreoIdRegistroNece = new ArrayList<Long>();

  static {
    listaRoleCorreoIdRegistroNece.add(IConstantesRole.AUTORIZADO_PARA_REGISTRO_DE_NECESIDADES_EN_LA_UNIDAD_POLICIAL);
  }

  public CuNe1PeriodoNecesidadesFaces() {
  }

  public String initReturnCU() {

    init();

    return navigationFaces.redirectCuNe01();

  }

  @javax.annotation.PostConstruct
  public void init() {

    listaUnidadesPolicialesDTOperiodo = new ArrayList<UnidadPolicialDTO>();
    this.activeIndex = "0";
    this.cantidadPropuestaUnidad = keyPropertiesFactory.value("cu_ne_1_cantidad_propuesta_por_defecto");
  }

  /**
   * Retorna un hasmap con los tipos de unidades existentes
   *
   * @return
   */
  public HashMap<String, String> getTipoUnidades() {
    if (tipoUnidades.isEmpty()) {
      try {
        List<TipoUnidad> tipo = tipoUnidadLocal.getTipoUnidades();
        for (TipoUnidad tp : tipo) {
          tipoUnidades.put(tp.getNombre(), tp.getIdTipoUnidad().toString());
        }

      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-01 Periodo de Necesidades", ex);
      }
    }
    return tipoUnidades;
  }

  public void setTipoUnidades(HashMap<String, String> tipoUnidades) {
    this.tipoUnidades = tipoUnidades;
  }

  public Periodo getPeriodoSelect() {
    return periodoSelect;
  }

  public void setPeriodoSelect(Periodo periodoSelect) {
    this.periodoSelect = periodoSelect;
  }

  public Date getFechaFinalHito() {
    return fechaFinalHito;
  }

  public void setFechaFinalHito(Date fechaFinalHito) {
    this.fechaFinalHito = fechaFinalHito;
  }

  public List<SelectItem> getSelectsTiposHitos() {
    if (this.selectsTiposHitos == null) {
      try {
        this.selectsTiposHitos = UtilidadesItem.getListaSel(iConstantesLocal.getConstantesPorTipo(IConstantes.TIPO_HITO_PERIODO), "codigo", "valor");
      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-01 Periodo de Necesidades -(getSelectsTiposHitos)", ex);
      }
    }
    return selectsTiposHitos;
  }

  public void setSelectsTiposHitos(List<SelectItem> selectsTiposHitos) {
    this.selectsTiposHitos = selectsTiposHitos;
  }

  public List<Periodo> getPeriodos() {

    if (this.periodos.isEmpty()) {
      try {
        this.periodos = this.iPeriodoLocal.getPeriodosNecesidades();
      } catch (JpaDinaeException ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                "CU-NE-01 Periodo de Necesidades -(getPeriodos)", ex);
      }
    }
    return periodos;
  }

  public void setPeriodos(List<Periodo> periodos) {
    this.periodos = periodos;
  }

  public String getActiveIndex() {
    return activeIndex;
  }

  public void setActiveIndex(String activeIndex) {
    this.activeIndex = activeIndex;
  }

  /**
   * Se inicializan los componentes de la pagina Faces
   */
  public void inicializarComponentes() {

    this.listaUnidadesPolicialesDTOperiodo = new ArrayList<UnidadPolicialDTO>();
    this.periodo = null;
    this.descripción = "";
    this.fechaInicio = null;
    this.fechaFinal = null;
    this.cantidadPropuestaUnidad = keyPropertiesFactory.value("cu_ne_1_cantidad_propuesta_por_defecto");
  }

  /**
   * Se cambia el estado al periodo a Publicado
   */
  public void publicarPeriodo() {

    if (this.periodoSelect == null) {

      try {

        guardarPeriodo(IConstantes.ESTADO_PERIODO_PUBLICADO);

        //EL ENVIO DE CORREO NO DEBE AFECTAR NINGUN PROCESO 
        try {

          enviarMailCreacionPeriodo(iTipoUnidadPeriodoLocal.getTipoUnidadPeriodoByPeriodo(periodoSelect));
        } catch (Exception e) {

          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Envio correo", e);
        }

      } catch (Exception ex) {
        adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(publicarPeriodo)", ex);

      }
    } else {

      activeIndex = "0";

      periodoSelect.setEstado(IConstantes.ESTADO_PERIODO_PUBLICADO);

      if (modificarPeriodo("T")) {

        periodoSelect.setEstado(IConstantes.ESTADO_PERIODO_TEMPORAL);

      } else {

        try {

          enviarMailCreacionPeriodo(iTipoUnidadPeriodoLocal.getTipoUnidadPeriodoByPeriodo(periodoSelect));

        } catch (JpaDinaeException ex) {

          adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(publicarPeriodo)", ex);
        }
      }

    }
  }

  /**
   * Se agregan hitos al periodo
   */
  public void buscarPeriodos() {

    try {

      periodos = iPeriodoLocal.getPeriodosNecesidades();

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(buscarPeriodos)", ex);

    }
  }

  /**
   * Método que hace validaciones al momento de crear el periodo
   *
   * @return
   */
  private String validadGuardarperiodo(Boolean validaFechaInicio) {

    String msg = "";
    if (this.fechaInicio.after(fechaFinal)) {

      return keyPropertiesFactory.value("cu_ne_1_error_fecha_final");
    }
    if (validaFechaInicio) {

      if (co.gov.policia.dinae.util.DatesUtils.compareDate(this.fechaInicio, Calendar.getInstance().getTime()) < 0) {
        return keyPropertiesFactory.value("cu_ne_1_error_fecha_inicio");
      }
    }

    return msg;
  }

  /**
   * Método que arma mensaje de mail cuando el Proyecto es publicado
   *
   * @param tipoUnidades
   */
  private void enviarMailCreacionPeriodo(List<TipoUnidadPeriodo> tipoUnidades) {

    try {

      List<Long> listaIdTiposUnidades = new ArrayList<Long>();
      for (TipoUnidadPeriodo unTipoUnidad : tipoUnidades) {

        listaIdTiposUnidades.add(unTipoUnidad.getTipoUnidad().getIdTipoUnidad());

      }

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      parametrosContenido.put("{_parametro1_}", String.valueOf(periodoSelect.getCantidad()));
      parametrosContenido.put("{_parametro2_}", String.valueOf(periodoSelect.getAnio()));
      parametrosContenido.put("{_parametro3_}", UtilidadesItem.getFechaFormateadaFormatoCorto(periodoSelect.getFechaInicio()));
      parametrosContenido.put("{_parametro4_}", UtilidadesItem.getFechaFormateadaFormatoCorto(periodoSelect.getFechaFin()));

      iMailSessionBean.enviarMail_ListaTipoUnidades_ListaRoles(
              IConstantes.CU_NE_01_PERIODO_PUBLICADO,
              null,
              parametrosContenido,
              listaIdTiposUnidades,
              listaRoleCorreoIdRegistroNece);

    } catch (Exception ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(enviarMailCreacionPeriodo)", ex);

    }
  }

  /**
   *
   * @param tipoUnidades
   */
  private void enviarModificacionPeriodo(List<TipoUnidadPeriodo> tipoUnidades) {
    try {

      List<Long> listaIdTiposUnidades = new ArrayList<Long>();
      for (TipoUnidadPeriodo unTipoUnidad : tipoUnidades) {

        listaIdTiposUnidades.add(unTipoUnidad.getTipoUnidad().getIdTipoUnidad());

      }

      Map<String, Object> parametrosContenido = new HashMap<String, Object>();
      parametrosContenido.put("{_parametro1_}", String.valueOf(periodoSelect.getCantidad()));
      parametrosContenido.put("{_parametro2_}", String.valueOf(periodoSelect.getAnio()));
      parametrosContenido.put("{_parametro3_}", UtilidadesItem.getFechaFormateadaFormatoCorto(periodoSelect.getFechaInicio()));
      parametrosContenido.put("{_parametro4_}", UtilidadesItem.getFechaFormateadaFormatoCorto(periodoSelect.getFechaFin()));
      parametrosContenido.put(IConstantes.CONTENIDO_ADJUNTO_MAIL, new ArchivoReporte(new byte[1024], "ArchivoPrueba.pdf"));

      iMailSessionBean.enviarMail_ListaTipoUnidades_ListaRoles(
              IConstantes.CU_NE_01_PERIODO_PUBLICADO,
              null,
              parametrosContenido,
              listaIdTiposUnidades,
              listaRoleCorreoIdRegistroNece);

    } catch (Exception ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(enviarMailCreacionPeriodo)", ex);
    }
  }

  /**
   * Guardar Periodo Seleccionado
   */
  private void guardarPeriodo(Character estado) {

    try {

      if (getContadorUnidadesSeleccionadas() == 0) {
        adicionaMensajeError("No se han seleccionado las unidades para este periodo.");
        return;
      }
      String validaciones = this.validadGuardarperiodo(true);
      if (validaciones.equals("")) {

        Long idTipoUnidadUnidaPolicial = null;
        Long idTipoUnidadOtros = null;
        for (UnidadPolicialDTO unidadPolicialDTO : listaUnidadesPolicialesDTOperiodo) {

          if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS)) {
            idTipoUnidadUnidaPolicial = IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS;
          } else if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_OTROS)) {
            idTipoUnidadOtros = IConstantes.TIPO_UNIDAD_POLICIAL_OTROS;
          }

          if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
            break;
          }

        }

        //Se arma lista de Tipos de unidades.
        List<TipoUnidad> listaTipoUnidades = new ArrayList<TipoUnidad>();
        if (idTipoUnidadUnidaPolicial != null) {
          TipoUnidad tipoUnidad = tipoUnidadLocal.getTipoUnidadById(idTipoUnidadUnidaPolicial);
          listaTipoUnidades.add(tipoUnidad);
        }
        if (idTipoUnidadOtros != null) {
          TipoUnidad tipoUnidad = tipoUnidadLocal.getTipoUnidadById(idTipoUnidadOtros);
          listaTipoUnidades.add(tipoUnidad);
        }

        String descripcionFormateada = StringUtils.ajustarCadenaANumeroCaracter(descripción, 500);
        periodoSelect = iPeriodoLocal.guardarPeriodo(periodo, listaTipoUnidades, descripcionFormateada, fechaInicio, fechaFinal, Integer.parseInt(cantidadPropuestaUnidad), estado, listaUnidadesPolicialesDTOperiodo);
        periodos = iPeriodoLocal.getPeriodosNecesidades();
        inicializarComponentes();
        activeIndex = "0";

        try {

          navigationFaces.redirectFacesCuNe01();

        } catch (Exception ex) {

          Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(guardar)", ex);
          adicionaMensajeError(keyPropertiesFactory.value("cu_ne_1_error_agregando_periodo"));
        }

      } else {

        adicionaMensajeError(validaciones);
        this.activeIndex = "1";
      }
    } catch (JpaDinaeException ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(guardar)", ex);
      adicionaMensajeError(keyPropertiesFactory.value("cu_ne_1_error_agregando_periodo"));

      this.activeIndex = "1";

    }
  }

  /**
   * Método que realiza el proceso de guardado de un periodo
   */
  public void guardar() {
    this.guardarPeriodo(IConstantes.ESTADO_PERIODO_TEMPORAL);
  }

  public void modificar() {
    this.modificarPeriodo("P");
  }

  /**
   * Método que realiza el proceso de Modificado de un periodo
   *
   * @param tipoMail
   * @return
   */
  public Boolean modificarPeriodo(String tipoMail) {
    try {

      if (getContadorUnidadesSeleccionadas() == 0) {
        adicionaMensajeError("No se han seleccionado las unidades para este periodo.");
        return false;
      }
      Boolean validaFechaInicio = !this.periodoSelect.getFechaInicio().equals(this.fechaInicio);
      String validaciones = this.validadGuardarperiodo(validaFechaInicio);
      if (validaciones.equals("")) {

        periodoSelect.setFechaFin(fechaFinal);

        periodoSelect.setFechaInicio(fechaInicio);
        Integer cantidad = Integer.parseInt(cantidadPropuestaUnidad);

        periodoSelect.setCantidad(cantidad.shortValue());
        periodoSelect.setAnio(periodo.shortValue());
        periodoSelect.setDescripcion(StringUtils.ajustarCadenaANumeroCaracter(descripción, 500));
        periodoSelect.setTipoUnidadPeriodoList(null);

        Long idTipoUnidadUnidaPolicial = null;
        Long idTipoUnidadOtros = null;
        for (UnidadPolicialDTO unidadPolicialDTO : listaUnidadesPolicialesDTOperiodo) {

          if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS)) {
            idTipoUnidadUnidaPolicial = IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS;
          } else if (unidadPolicialDTO.isSeleccionado() && unidadPolicialDTO.getIdTipoUnidad().equals(IConstantes.TIPO_UNIDAD_POLICIAL_OTROS)) {
            idTipoUnidadOtros = IConstantes.TIPO_UNIDAD_POLICIAL_OTROS;
          }

          if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
            break;
          }

        }

        //Se arma lista de Tipos de unidades.
        List<TipoUnidad> listaTipoUnidades = new ArrayList<TipoUnidad>();
        if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
          TipoUnidad tipoUnidad = tipoUnidadLocal.getTipoUnidadById(idTipoUnidadOtros);
          listaTipoUnidades.add(tipoUnidad);
        }
        if (idTipoUnidadOtros != null && idTipoUnidadUnidaPolicial != null) {
          TipoUnidad tipoUnidad = tipoUnidadLocal.getTipoUnidadById(idTipoUnidadOtros);
          listaTipoUnidades.add(tipoUnidad);
        }

        List<TipoUnidadPeriodo> tipoUnidadesPeriodo = new ArrayList<TipoUnidadPeriodo>();

        for (TipoUnidad tipoUnidad : listaTipoUnidades) {

          TipoUnidadPeriodo tipoUnidadPeriodo = new TipoUnidadPeriodo();
          tipoUnidadPeriodo.setTipoUnidad(tipoUnidad);
          tipoUnidadPeriodo.setPeriodo(periodoSelect);
          tipoUnidadesPeriodo.add(tipoUnidadPeriodo);

        }

        periodoSelect.setTipoUnidadPeriodoList(tipoUnidadesPeriodo);

        iPeriodoLocal.modificarPeriodio(periodoSelect, listaUnidadesPolicialesDTOperiodo);
        //PERIODO ESTA EN ESTADO PUBLICADO SE ENVIA MAIL  NOTIFICANDO EL CAMBIO
        if (tipoMail.equals("P")) {
          enviarModificacionPeriodo(tipoUnidadesPeriodo);
        } else {
          enviarMailCreacionPeriodo(tipoUnidadesPeriodo);
        }

        inicializarComponentes();
        periodos = iPeriodoLocal.getPeriodosNecesidades();
        activeIndex = "0";
        navigationFaces.redirectFacesCuNe01();

        return true;

      } else {

        adicionaMensajeError(validaciones);
        this.activeIndex = "1";
        return false;
      }
    } catch (Exception ex) {

      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-NE-01 Periodo de Necesidades -(guardar)", ex);
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      activeIndex = "1";

    }
    return false;
  }

  /**
   * Método que es invocado en el enlace de agregar el periodo y reinicia los elementos
   */
  public void linkAgregarPeriodo() {

    try {

      this.periodoSelect = null;
      inicializarComponentes();
      Integer mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;

      Integer anioActual = Calendar.getInstance().get(Calendar.YEAR);
      this.periodo = mesActual >= IConstantes.MAXIMO_MES_AGREGAR_PERIODO_ANIO_ACTUAL ? anioActual + 1 : anioActual;
      this.peridoMaximo = mesActual >= IConstantes.MAXIMO_MES_AGREGAR_PERIODO_ANIO_ACTUAL ? periodo : periodo + 1;
      this.activeIndex = "1";
      listaUnidadesPolicialesDTOperiodo = iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();

    } catch (Exception e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

    }

  }

  /**
   * Método que revisa propuestas de necesidad y envia un periodo al caso de uso CU-NE-06
   */
  public void revisarPropuestasNecesidad() {
    try {
      if (periodoSelect == null) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_1_error_periodo_no_fijado"));

      } else if (!periodoSelect.getEstado().equals(IConstantes.ESTADO_PERIODO_PUBLICADO)) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_1_error_periodo_no_publicado"));

      } else if (iPropuestaNecesidadLocal.getNumeroPropuestaNecesidadPorPeriodoYestadoVicinPreaprobadaRevisada(periodoSelect.getIdPeriodo(), IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_ENVIADA_A_VICIN,
              IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_PRE_APROBADA, IConstantes.CONSTANTES_ESTADO_PROPUESTA_NECESIDAD_REVISADA) == 0) {

        adicionaMensajeError(keyPropertiesFactory.value("cu_ne_1_error_periodo_no_tiene_propuestas_vicin"));

      } else {
        //REDIRECCIONAMOS A AL CASO DE USO CU_NE_6 para revisar propuestas enviadas a vicin            
        //SETEAMOS EL PERIODO Y REDIRECCIONAMOS
        cuNe6EvaluarPropuestasNecesidadesInvestigacionFaces.initPeriodo(periodoSelect);
      }

    } catch (Exception e) {
      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
              "CU-NE-01 (revisarPropuestasNecesidad)", e);
    }
  }

  /**
   * Método que selecciona el periodo en lista, y al cual solo se puede agregar hitos y modificar fecha Final
   */
  public void seleccionarPeriodoAModificar() {
    try {

      this.periodo = (int) this.periodoSelect.getAnio();
      this.descripción = this.periodoSelect.getDescripcion();
      this.fechaInicio = this.periodoSelect.getFechaInicio();
      this.fechaFinal = this.periodoSelect.getFechaFin();
      this.cantidadPropuestaUnidad = "" + this.periodoSelect.getCantidad();

      this.activeIndex = "1";
      Integer anioActual = Calendar.getInstance().get(Calendar.YEAR);
      Integer mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;
      this.peridoMaximo = mesActual >= IConstantes.MAXIMO_MES_AGREGAR_PERIODO_ANIO_ACTUAL ? anioActual : anioActual + 1;

      //CARGAMOS LA UNIDADES
      listaUnidadesPolicialesDTOperiodo = iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();
      //BUSCAMOS LAS UNIDADES SELECCIONADAS
      List<Long> lista = iPeriodoLocal.getIdsUnidadesPolicialesPorPeriodo(periodoSelect.getIdPeriodo());
      for (Long unIdPeriodoSeleccionado : lista) {
        for (UnidadPolicialDTO unaUnidadDTO : listaUnidadesPolicialesDTOperiodo) {
          if (unIdPeriodoSeleccionado.equals(unaUnidadDTO.getIdUnidadPolicial())) {
            unaUnidadDTO.setSeleccionado(true);
            break;
          }
        }
      }

    } catch (JpaDinaeException ex) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(CuNe1PeriodoNecesidadesFaces.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   * Método que determina si los componentes se debe deshabilitar dependiendo de la condición que se ha seleccionado un periodo y que este en estado Publicado
   *
   * @return
   */
  public Boolean deshabilitarComponentes() {
    if (periodoSelect != null) {
      if (periodoSelect.getEstado().equals(IConstantes.ESTADO_PERIODO_PUBLICADO)) {
        return true;
      }
    }
    return false;
  }

  public String regresar() {
    this.activeIndex = "0";
    return this.navigationFaces.redirectCuNe01();

  }

  public boolean mostrarBtnModificarPeriodo(Periodo periodo) {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.ENCARGADO_DE_PERIODOS_DE_PROPUESTAS_DE_NECESIDADES_EN_VICIN);
  }

  public boolean mostrarBtnRevisarPeriodo(Periodo periodo) {

    //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA EL ROL DE: ENCARGADO_DE_CONVOCATORIAS_EN_VICIN
    if (loginFaces == null || loginFaces.getPerfilUsuarioDTO() == null) {
      return false;
    }
    return loginFaces.getPerfilUsuarioDTO().validarRol(IConstantesRole.EVALUADOR_DE_PROPUESTAS_DE_NECESIDADES_EN_LA_VICIN);

  }

  public boolean isMostrarLinkReporteNecesidades() {

    return periodoSelect != null && periodoSelect.getNombreArchivoOriginalPropuestaNecesidadReporte() != null;

  }

  /**
   *
   * @return
   */
  public StreamedContent getDownloadContentProperty() {
    try {
      if (isMostrarLinkReporteNecesidades()) {

        String name = keyPropertiesFactory.value("proyecto_dinae_ruta_fisica_general") + periodoSelect.getNombreArchivoOriginalPropuestaNecesidadReporte();

        InputStream stream = new FileInputStream(name);
        String contentType = "application/octet-stream";

        StreamedContent download = new DefaultStreamedContent(stream, contentType, periodoSelect.getNombreArchivoFisicoPropuestaNecesidadReporte());

        return download;
      }
    } catch (FileNotFoundException e) {

      adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
    }
    return null;
  }

  public List<UnidadPolicialDTO> getListaUnidadesPolicialesDTOperiodo() {
    return listaUnidadesPolicialesDTOperiodo;
  }

  public void setListaUnidadesPolicialesDTOperiodo(List<UnidadPolicialDTO> listaUnidadesPolicialesDTOperiodo) {
    this.listaUnidadesPolicialesDTOperiodo = listaUnidadesPolicialesDTOperiodo;
  }

  public int getContadorUnidadesSeleccionadas() {
    if (listaUnidadesPolicialesDTOperiodo == null || listaUnidadesPolicialesDTOperiodo.isEmpty()) {
      return 0;
    }
    int contadorUnidadesSeleccionadas = 0;
    for (UnidadPolicialDTO unaUnidadDTO : listaUnidadesPolicialesDTOperiodo) {
      if (unaUnidadDTO.isSeleccionado()) {
        contadorUnidadesSeleccionadas += 1;
      }
    }
    return contadorUnidadesSeleccionadas;
  }

  public Date getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public ITipoUnidadLocal getTipoUnidadLocal() {

    return tipoUnidadLocal;
  }

  public void setTipoUnidadLocal(ITipoUnidadLocal tipoUnidadLocal) {
    this.tipoUnidadLocal = tipoUnidadLocal;
  }

  public Integer getPeridoMaximo() {

    return peridoMaximo;
  }

  public void setPeridoMaximo(Integer peridoMaximo) {
    this.peridoMaximo = peridoMaximo;
  }

  public Integer getPeriodo() {
    return periodo;
  }

  public void setPeriodo(Integer periodo) {
    this.periodo = periodo;
  }

  public String getDescripción() {
    return descripción;
  }

  public void setDescripción(String descripción) {
    this.descripción = descripción;
  }

  public String getCantidadPropuestaUnidad() {
    return cantidadPropuestaUnidad;
  }

  public void setCantidadPropuestaUnidad(String cantidadPropuestaUnidad) {
    this.cantidadPropuestaUnidad = cantidadPropuestaUnidad;
  }
}
