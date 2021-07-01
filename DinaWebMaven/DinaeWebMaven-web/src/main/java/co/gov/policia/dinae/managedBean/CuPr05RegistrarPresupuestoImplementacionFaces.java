package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_FUENTE_FINANCIERA;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IConstantesLocal;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.FuenteProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import static co.gov.policia.dinae.constantes.IConstantes.CODIGO_LUGAR_GEOGRAFICO_COLOMBIA;
import static co.gov.policia.dinae.constantes.IConstantes.LUGAR_GEOGRAFICO_COLOMBIA;
import static co.gov.policia.dinae.constantes.IConstantes.OTROS_GASTOS_TIPO;
import static co.gov.policia.dinae.constantes.IConstantes.OTROS_GASTOS_RUBROS;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_ORIGEN_EQUIPO_INVESTIGACION;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_INVESTIGADOR_PROYECTO_INVESTIGADOR_PRINCIPAL;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.interfaces.ILugarGeograficoLocal;
import co.gov.policia.dinae.interfaces.IEquiposInvestigacionLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.modelo.EquiposInvestigacion;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.LugarGeografico;
import co.gov.policia.dinae.modelo.UsuarioRol;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import static co.gov.policia.dinae.constantes.IConstantes.TIPO_GASTOS_EVENTOS_RELACIONADOS;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.EventoProyectoDTO;
import co.gov.policia.dinae.dto.FuenteProyectoDTO;
import co.gov.policia.dinae.dto.LugarGeograficoDTO;
import co.gov.policia.dinae.dto.OtrosGastosProyectoDTO;
import co.gov.policia.dinae.dto.TipoGastoEventoDTO;
import co.gov.policia.dinae.dto.ViajesProyectoDTO;
import co.gov.policia.dinae.interfaces.ICompromisoImplementacionLocal;
import co.gov.policia.dinae.interfaces.IEventoProyectoLocal;
import co.gov.policia.dinae.interfaces.IImplementacionProyectoLocal;
import co.gov.policia.dinae.interfaces.IOtrosGastosProyectoLocal;
import co.gov.policia.dinae.interfaces.IPlanTrabajoImplementacionLocal;
import co.gov.policia.dinae.interfaces.ITipoGastoEventoLocal;
import co.gov.policia.dinae.interfaces.IViajesProyectoLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.CompromisoImplementacion;
import co.gov.policia.dinae.modelo.EventoProyecto;
import co.gov.policia.dinae.modelo.ImplementacionesProyecto;
import co.gov.policia.dinae.modelo.OtrosGastosProyecto;
import co.gov.policia.dinae.modelo.PlanTrabajoImplementacion;
import co.gov.policia.dinae.modelo.TipoGastoEvento;
import co.gov.policia.dinae.modelo.ViajesProyecto;
import co.gov.policia.dinae.util.PresupuestoUtil;
import co.gov.policia.dinae.util.UtilidadesItem;
import java.math.BigInteger;
import javax.ejb.TransactionRolledbackLocalException;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author cguzman
 */
@javax.inject.Named(value = "presupuestoImplemetacionProyectoFaces")
@javax.enterprise.context.SessionScoped
public class CuPr05RegistrarPresupuestoImplementacionFaces extends JSFUtils implements Serializable {

    @javax.inject.Inject
    protected CuPr1ProyectoFaces proyectoFaces;

    @javax.inject.Inject
    protected CuPr15_1_2_AvanceImplemenacionFaces cuPr15_1_2_AvanceImplemenacionFaces;

    @javax.inject.Inject
    private CuPr21RegistrarPlanDeTrabajoFaces cuPr21RegistrarPlanDeTrabajoFaces;

    @Inject
    private PresupuestoUtilMB presupuestoUtilFaces;

    @EJB
    private IFuenteProyectoLocal iFuente;

    @EJB
    private IConstantesLocal iConstantes;

    @EJB
    private IInvestigadorProyectoLocal iInvestigador;

    @EJB
    private IEquiposInvestigacionLocal iEquipos;

    @EJB
    private ILugarGeograficoLocal iCiudad;

    @EJB
    private IEventoProyectoLocal iEvento;

    @EJB
    private ITipoGastoEventoLocal iTipoGasto;

    @EJB
    private IViajesProyectoLocal iViajesProyecto;

    @EJB
    private IOtrosGastosProyectoLocal iOtrosGastosProyecto;

    @EJB
    private IPlanTrabajoImplementacionLocal _iPlanTrabajoImpl;

    @EJB
    private ICompromisoImplementacionLocal _iCompromisoImplementacion;

    @EJB
    private IImplementacionProyectoLocal _iImplProyecto;

    private int idTabSeleccionado;

    private List<SelectItem> _listaFuentesProyectoItems;

    private String nombreFuenteFinanciera;
    private Long tipoFuente;
    private List<SelectItem> listaItemsTiposFuentes;
    private List<Constantes> listaTiposFuentes;

    private List<FuenteProyectoDTO> _listaFuentesProyecto;
    private List<SelectItem> _listaFuentesProyectoItem;

    private FuenteProyecto _fuenteSeleccionada;

    private FuenteProyectoDTO _fuenteProyectoDTO;

    private FuenteProyecto _fuenteProyecto;

    private boolean _esModificacion;

    private ListGenericDataModel _listaFuentesFinancierasModel;

    private UsuarioRol _usuarioRol;

    private List<InvestigadorProyecto> _listaInvestigadoresProyecto;

    private List<SelectItem> _listaOrigenesFondos;

    private EquiposInvestigacion _equipo;

    private List<EquiposInvestigacion> _listaEquiposInvestigacion;

    private ListGenericDataModel _listaEquiposInvestigacionModel;

    private boolean _esModificaEquipo;

    private boolean permiteEditar;

    private String _nombreEquipo;

    private Long _origenEquipo;

    private Double _valorEquipo;

    private Long _fuenteEquipo;

    private String _especificacionesEquipo;

    private String _tituloEvento;

    private String _objetivoEvento;

    private Long _departamentoEvento;

    private Long _ciudadEvento;

    private Long _fuenteEvento;

    private Double _costoEvento;

    private Long[] _tipoGastoEvento;

    private List<LugarGeografico> _listaDepartamentos;
    
    private List<LugarGeograficoDTO> _departamentosList;

    private List<LugarGeografico> _listaCiudadesDepartamento;

    private List<SelectItem> _listaTipoGastoEvento;

    private EventoProyecto _eventoProyecto;

    private EventoProyectoDTO _eventoProyectoDto;

    private ListGenericDataModel _listaEventosProyectoModel;

    private List<EventoProyecto> _listaEventosProyecto;

    private List<EventoProyectoDTO> _listaEventosProyectoDto;

    private boolean _esModificaEvento;

    private List<InvestigadorProyecto> _listaInvestigadorViaje;

    private Long _idInvetigadorViaje;

    private String _eventoViaje;

    private List<LugarGeografico> _listaDepartamentoOrigenViaje;
    
    private List<LugarGeograficoDTO> _listaDepartamentoOrigenViajeDto;

    private Long _idDepartamentoOrigenViaje;

    private List<LugarGeografico> _listaCiudadOrigenViaje;

    private Long _idCiudadOrigenViaje;

    private List<LugarGeograficoDTO> _listaDepartamentoDestinoViaje;

    private Long _idDepartamentoDestinoViaje;

    private List<LugarGeografico> _listaCiudadDestinoViaje;

    private Long _idCiudadDestinoViaje;

    private Double _costoPasajesViaje;

    private Double _costoViaticosViaje;

    private Long _idFuenteProyectoViaje;

    private ViajesProyecto _viajesProyecto;

    private ViajesProyectoDTO _viajesProyectoDto;

    private boolean _esModificaViaje;

    private List<ViajesProyecto> _listaViajesProyecto;

    private List<ViajesProyectoDTO> _listaViajesProyectoDto;

    private ListGenericDataModel _listaViajesProyectoModel;

    private Long _idTipoRubroOtrosGastos;

    private List<SelectItem> _listaTipoRubroOtrosGastos;

    private Long _idTipoOtrosGastos;

    private List<SelectItem> _listaTipoOtrosGastos;

    private Double _valorOtrosGastos;

    private Long _idFuenteOtrosGastos;

    private List<OtrosGastosProyecto> _listaOtrosGastosProyecto;

    private List<OtrosGastosProyectoDTO> _listaOtrosGastosProyectoDTO;

    private ListGenericDataModel _listaOtrosGastosProyectoModel;

    private OtrosGastosProyecto _otrosGastosProyecto;

    private OtrosGastosProyectoDTO _otrosGastosProyectoDTO;

    private boolean _esModificaOtrosGastosProyecto;

    private List<SelectItem> _listaTipoFuente;

    private String _ingresoCU;

    public static final String CU_PR_1 = "CU_PR_1";

    public static final String CU_PR_7 = "CU_PR_7";

    public static final String CU_PR_21 = "CU_PR_21";

    public static final String CU_PR_15 = "CU_PR_15";

    private static final String FUENTE_FINANCIERA = "Fuente financiera";

    private static final String EQUIPO_INVESTIGACION = "Equipo de la investigación";

    private static final String EVENTO = "Evento";

    private static final String VIAJE = "Viaje";

    private static final String GASTO = "Gasto";

    private Long _idImplementacionProyecto;

    private Long _idCompromisoImplementacion;

    private String _importPresupuestoPage;

    private Long idInvestigadorPersonal;

    private PlanTrabajoImplementacion _planTrabajo;

    private String _registraUsuario;

    private String _maquina;

    private ImplementacionesProyecto _proyectoImplemetacion;

    /**
     *
     */
    @PostConstruct
    public void init() {
        _fuenteProyecto = new FuenteProyecto();
        //_fuenteProyecto.setTipoFuente(new Constantes());

        //cargarListaInvestigadores();
        //cargarListaEquipos();
    }

    /**
     *
     * @param esInterna
     * @throws JpaDinaeException
     */
    private void inicializarFuenteProyecto(boolean esInterna) throws JpaDinaeException {
        _fuenteProyecto = new FuenteProyecto();
        //_fuenteProyecto.setTipoFuente(esInterna ? IConstantes.ID_TIPO_FUENTE_FINANCIERA_INTERNA : -1L);
        _fuenteProyecto.setTipoFuente(esInterna ? new Constantes(IConstantes.ID_TIPO_FUENTE_FINANCIERA_INTERNA) : new Constantes(-1L));

        _fuenteProyecto.setPlanTrabajoImplementacion(_planTrabajo);

        _fuenteProyecto.setUsuarioRol(_usuarioRol);
        _fuenteProyecto.setUsuarioRegistra(_registraUsuario);
        _fuenteProyecto.setMaquina(_maquina);

        _fuenteProyectoDTO = new FuenteProyectoDTO();
    }

    /**
     *
     * @param idImplementacionProyecto
     * @param idCompromisoImplementacion
     * @param origen
     * @param permiteEditar
     */
    public void initProyecto(Long idImplementacionProyecto, Long idCompromisoImplementacion, String origen, boolean permiteEditar) {
        try {

            this.permiteEditar = permiteEditar;
            _listaFuentesProyectoItem = null;
            _ingresoCU = origen;
            idTabSeleccionado = 0;

            CompromisoImplementacion compromisoImpl = _iCompromisoImplementacion.obtenerCompromisoImplementacionPorId(idCompromisoImplementacion);
            if (IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO.compareTo(compromisoImpl.getIdTipoCompromiso().getIdConstantes()) == 0) {
                _planTrabajo = _iPlanTrabajoImpl.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(idImplementacionProyecto, idCompromisoImplementacion);
            } else {
                _planTrabajo = obtenerPlanTrabajoProyecto(idImplementacionProyecto);
            }

            _proyectoImplemetacion = _iImplProyecto.getImplementacionProyecto(idImplementacionProyecto);
            _idImplementacionProyecto = idImplementacionProyecto;
            _idCompromisoImplementacion = idCompromisoImplementacion;

            cargarListadoFuentesProyecto();

            Constantes constanteVicin = iConstantes.getConstantesPorIdConstante(IConstantes.ID_NOMBRE_FUENTE_FINANCIERA_CONSTANTE_POLICIA_DINAE);
            Constantes constantePonal = iConstantes.getConstantesPorIdConstante(IConstantes.ID_NOMBRE_FUENTE_FINANCIERA_CONSTANTE_PONAL);

            boolean esFuenteVicin = false;
            boolean esFuentePonal = false;
            boolean esFuenteUnidad = false;

            RolUsuarioDTO rolUsuarioDTO = loginFaces.getPerfilUsuarioDTO().getRolUsuarioPorIdRolDTO(IConstantesRole.RESPONSABLE_DE_PROYECTOS_DE_INVESTIGACION_EN_LA_UNIDAD_POLICIAL);
            _usuarioRol = null;
            if (rolUsuarioDTO != null) {
                _usuarioRol = new UsuarioRol(rolUsuarioDTO.getIdUsuarioRol());
            }

            _registraUsuario = loginFaces.getPerfilUsuarioDTO().getIdentificacion();
            _maquina = loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario();

            String nombreUnidadPolicial = _planTrabajo.getImplementacionesProyecto().getUnidadPolicial().getSiglaFisica();

            // Validar que existan al menos las fuentes base para el proyecto.
            if (!_listaFuentesProyecto.isEmpty()) {

                for (FuenteProyectoDTO fuente : _listaFuentesProyecto) {

                    if (IConstantes.ID_TIPO_FUENTE_FINANCIERA_INTERNA.compareTo(fuente.getIdTipoFuente()) == 0
                            && "Y".equals(String.valueOf(fuente.getFuenteBase().charValue()))) {

                        if (constanteVicin.getCodigo().equals(fuente.getNombreFuente())) {
                            esFuenteVicin = true;
                        }

                        if (constantePonal.getCodigo().equals(fuente.getNombreFuente())) {
                            esFuentePonal = true;
                        }

                        if (fuente.getNombreFuente().equals(nombreUnidadPolicial)) {
                            esFuenteUnidad = true;
                        }

                        if (esFuentePonal && esFuenteUnidad && esFuenteVicin) {
                            break;
                        }
                    }
                }
            }

            // Crear fuente policia nacional
            if (!esFuentePonal) {
                inicializarFuenteProyecto(true);
                _fuenteProyecto.setNombreFuente(constantePonal.getCodigo());
                crearFuente(true);
            }

            // Crear fuente vicin
            if (!esFuenteVicin) {
                inicializarFuenteProyecto(true);
                _fuenteProyecto.setNombreFuente(constanteVicin.getCodigo());
                crearFuente(true);

            }

            if (!esFuenteUnidad) {
                inicializarFuenteProyecto(true);
                _fuenteProyecto.setNombreFuente(nombreUnidadPolicial);
                crearFuente(true);

            }

            inicializarFuenteProyecto(false);
            cargarListaTipoFuente();
            cargarListadoFuentesProyecto();

            cargarListaOrigenesFondos();
            inicializarEquiposInvestigacion();

            cargarInvestigadoresProyecto();

            inicializarEventosRelacionados();

            inicializarViajesInvestigador();
            cargarViajesProyecto();
            nuevoViajeInvestigador();

            inicializarOtrosGastos();
            nuevoOtroGastoProyecto();

        } catch (JpaDinaeException ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void cargarListaTipoFuente() throws JpaDinaeException {
        List<ConstantesDTO> listaTiposFuente = iConstantes.getConstantesDTOPorTipo(TIPO_FUENTE_FINANCIERA);
        if (listaTiposFuente.isEmpty()) {
            _listaTipoFuente = new ArrayList<SelectItem>();
            return;
        }

        _listaTipoFuente = UtilidadesItem.getListaSel(listaTiposFuente, "idConstantes", "valor");
    }

    private void cargarListadoFuentesProyecto() throws JpaDinaeException {
        _listaFuentesProyecto = iFuente.findFuentesByPlanTrabajoImpl(_planTrabajo.getIdPlanTrabajo());

        if (_listaFuentesProyecto.isEmpty()) {
            _listaFuentesProyecto = new ArrayList<FuenteProyectoDTO>();
        }
        _listaFuentesProyectoItems = UtilidadesItem.getListaSel(_listaFuentesProyecto, "idFuenteProyecto", "nombreFuente");
        _listaFuentesFinancierasModel = new ListGenericDataModel(_listaFuentesProyecto);
        _esModificacion = false;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaOrigenesFondos() throws JpaDinaeException {

        List<Constantes> origen = iConstantes.getConstantesPorTipo(TIPO_ORIGEN_EQUIPO_INVESTIGACION);
        if (origen.isEmpty()) {
            _listaOrigenesFondos = new ArrayList<SelectItem>();
            return;
        }

        _listaOrigenesFondos = UtilidadesItem.getListaSel(origen, "idConstantes", "valor");
    }

    /**
     *
     */
    private void inicializarEquiposInvestigacion() throws JpaDinaeException {

        _equipo = new EquiposInvestigacion();
        _equipo.setOrigen(new Constantes(-1L));
        _equipo.setFuenteProyecto(new FuenteProyecto(-1L));

        cargarListaEquipos();

        _esModificaEquipo = false;
        _nombreEquipo = null;
        _origenEquipo = -1L;
        _valorEquipo = null;
        _fuenteEquipo = -1L;
        _especificacionesEquipo = null;
    }

    /**
     *
     * @param idConstante
     * @return
     */
    public String tomarValorConstante(Long idConstante) {
        try {
            return iConstantes.getConstantesPorIdConstante(idConstante).getValor();
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @param tabIndex
     * @return
     */
    public String guardar(Integer tabIndex) {
        try {

            if (tabIndex.compareTo(0) == 0) {
                guardarTabFuentesProyecto();
            }
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void guardarTabFuentesProyecto() throws JpaDinaeException {

        if (_listaFuentesProyecto.size() < 7) {

            if (nombreFuenteFinanciera == null || "".equals(nombreFuenteFinanciera)) {
                adicionaMensajeError("El campo 'Nombre de la fuente' es obligatorio");
                return;
            }

            if (tipoFuente == null || tipoFuente.compareTo(-1L) == 0) {
                adicionaMensajeError("Tipo de la fuente");
                return;
            }

            if (_fuenteProyecto == null) {
                inicializarFuenteProyecto(false);
            }

            _fuenteProyecto.setNombreFuente(nombreFuenteFinanciera);
            //_fuenteProyecto.setTipoFuente(tipoFuente);
            _fuenteProyecto.setTipoFuente(new Constantes(tipoFuente));

            crearFuente(false);

            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));
        } else {

            adicionaMensajeError("No es posible registrar más fuentes para este proyecto.");
        }

    }

    /**
     * Crear Fuente.
     *
     * @param nombreFuente
     * @param tipoFuente
     * @param esFuenteBase
     * @throws JpaDinaeException
     */
    private void crearFuente(boolean esFuenteBase) throws JpaDinaeException {

        _fuenteProyecto.setFuenteBase(esFuenteBase ? 'Y' : 'N');
        _fuenteProyecto = iFuente.saveOrUpdate(_fuenteProyecto);

        _listaFuentesProyecto.clear();

        _listaFuentesProyecto = iFuente.findFuentesByPlanTrabajoImpl(_planTrabajo.getIdPlanTrabajo());

        _listaFuentesFinancierasModel = new ListGenericDataModel(_listaFuentesProyecto);
        inicializarFuenteProyecto(false);

        //ACTUALIZAMOS LA LISTA DE FUENTE ITEM
        _listaFuentesProyectoItems = UtilidadesItem.getListaSel(_listaFuentesProyecto, "idFuenteProyecto", "nombreFuente");

        _esModificacion = false;

        nombreFuenteFinanciera = null;
        tipoFuente = null;

    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarInvestigadoresProyecto() throws JpaDinaeException {
        _listaInvestigadoresProyecto = iInvestigador.getListaInvestigadorProyectoPorPlanTrabajoImpl(_planTrabajo.getIdPlanTrabajo());

        int index = 0;
        for (InvestigadorProyecto i : _listaInvestigadoresProyecto) {
            if (i.getFuenteProyecto() == null) {
                i.setFuenteProyecto(new FuenteProyecto());
                i.setFuenteAsociada("NOVALUE");
            } else {
                i.setFuenteAsociada(i.getFuenteProyecto().getIdFuenteProyecto().toString());
            }
            _listaInvestigadoresProyecto.set(index, i);
            index++;
        }
    }

    /**
     *
     * @param event
     */
    public void cargarDatosFuenteProyecto(SelectEvent event) {
        try {
            if (_fuenteProyectoDTO == null) {
                return;
            }

            _fuenteProyecto = iFuente.getFuenteFinancieraById(_fuenteProyectoDTO.getIdFuenteProyecto());

            if (_fuenteProyecto.getNombreFuente().equals(iConstantes.getConstantesPorIdConstante(IConstantes.ID_NOMBRE_FUENTE_FINANCIERA_CONSTANTE_POLICIA_DINAE).getCodigo())
                    || _fuenteProyecto.getNombreFuente().equals(iConstantes.getConstantesPorIdConstante(IConstantes.ID_NOMBRE_FUENTE_FINANCIERA_CONSTANTE_PONAL).getCodigo())
                    || _fuenteProyecto.getNombreFuente().equals(_planTrabajo.getImplementacionesProyecto().getUnidadPolicial().getNombre())) {
                adicionaMensajeAdvertencia("Las fuentes básicas no pueden ser modificadas");
                inicializarFuenteProyecto(false);
                return;
            }

            nombreFuenteFinanciera = _fuenteProyecto.getNombreFuente();
            //tipoFuente = _fuenteProyecto.getTipoFuente();
            tipoFuente = _fuenteProyecto.getTipoFuente().getIdConstantes();

            _esModificacion = true;
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String cancelarModificacionFuente() {
        try {
            inicializarFuenteProyecto(false);
            nombreFuenteFinanciera = null;
            tipoFuente = -1L;
            _esModificacion = false;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @param e
     */
    public void cargarDatosEquipo(SelectEvent e) {
        if (_equipo == null) {
            return;
        }

        _esModificaEquipo = true;

        _nombreEquipo = _equipo.getNombreEquipo();
        _origenEquipo = _equipo.getOrigen().getIdConstantes();
        _valorEquipo = _equipo.getValor().doubleValue();
        _fuenteEquipo = _equipo.getFuenteProyecto().getIdFuenteProyecto();
        _especificacionesEquipo = _equipo.getEspecificaciones();

    }

    /**
     *
     * @param event
     */
    public void noSeleccionEquipos(UnselectEvent event) {
        try {
            inicializarEquiposInvestigacion();

            _esModificaEquipo = false;
            _nombreEquipo = null;
            _origenEquipo = -1L;
            _valorEquipo = null;
            _fuenteEquipo = -1L;
            _especificacionesEquipo = null;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String cancelarModificarEquipos() {
        try {
            inicializarEquiposInvestigacion();

            _esModificaEquipo = false;
            _nombreEquipo = null;
            _origenEquipo = -1L;
            _valorEquipo = null;
            _fuenteEquipo = -1L;
            _especificacionesEquipo = null;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @param ip
     * @param field
     * @return
     */
    public BigDecimal calcularCostoInvestigadores(InvestigadorProyecto ip, int field) {
        BigDecimal valor = new BigDecimal(BigInteger.ZERO);

        if ((field == 0) && ip.getTipoVinculacion().getIdConstantes().equals(TIPO_INVESTIGADOR_PROYECTO_INVESTIGADOR_PRINCIPAL)) {
            valor = ip.getValorHoraInvestigadorImplementacion().multiply(ip.getHorasTotalesImplementacion());
        }

        if (field == 1) {
            valor = ip.getValorHoraInvestigadorImplementacion().multiply(ip.getHorasTotalesImplementacion());
        }

        return valor;
    }

    /**
     *
     * @param event
     */
    public void noSeleccionFuenteProyecto(UnselectEvent event) {
        try {
            inicializarFuenteProyecto(false);
            _esModificacion = false;
            nombreFuenteFinanciera = null;
            tipoFuente = null;
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param index
     * @param idInvestigador
     */
    public void guardarFuenteSeleccionada(int index, Long idInvestigador) {
        try {

            String itemSeleccionado = _listaInvestigadoresProyecto.get(index).getFuenteAsociada();
            if (itemSeleccionado.equals("NOVALUE")) {
                return;
            }

            FuenteProyecto fuente = new FuenteProyecto(Long.valueOf(itemSeleccionado));
            InvestigadorProyecto investigadorProyecto = iInvestigador.obtenerInvestigadorProyectoPorId(idInvestigador);

            investigadorProyecto.setFuenteProyecto(fuente);
            iInvestigador.guardarInvestigadorProyecto(investigadorProyecto);

            _listaInvestigadoresProyecto.clear();
            cargarInvestigadoresProyecto();

            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @return
     */
    public String updateEquipoInvestigacion() {

        try {
            if (_nombreEquipo == null || "".equals(_nombreEquipo)) {
                adicionaMensajeError(keyPropertiesFactory.value(
                        "cu_pr_05_error_nombre_equipo_faltante"));
                return null;
            }
            if (_valorEquipo == null || _valorEquipo.compareTo(0D) <= 0) {
                adicionaMensajeError(keyPropertiesFactory.value(
                        "cu_pr_05_error_valor_equipo_faltante"));
                return null;
            }
            if (Double.isNaN(_valorEquipo)) {
                adicionaMensajeError(keyPropertiesFactory.value(
                        "cu_pr_05_error_valor_equipo_no_valido"));
                return null;
            }
            if (_especificacionesEquipo == null || "".equals(_especificacionesEquipo)) {
                adicionaMensajeError(keyPropertiesFactory.value(
                        "cu_pr_05_error_especificaciones_equipo_faltante"));
                return null;
            }
            if (_fuenteEquipo == null || _fuenteEquipo.compareTo(-1L) == 0) {
                adicionaMensajeError(keyPropertiesFactory.value(
                        "cu_pr_05_error_fuente_financiera_equipo_faltante"));
                return null;
            }

            if (_origenEquipo == null || _origenEquipo.compareTo(-1L) == 0) {
                adicionaMensajeError("El Campo 'Origen' es obligatorio");
                return null;
            }

            if (_equipo == null) {
                _equipo = new EquiposInvestigacion();
            }

            _equipo.setNombreEquipo(_nombreEquipo);
            _equipo.setOrigen(new Constantes(_origenEquipo));
            _equipo.setValor(new BigDecimal(_valorEquipo.doubleValue()));
            _equipo.setFuenteProyecto(new FuenteProyecto(_fuenteEquipo));
            _equipo.setEspecificaciones(_especificacionesEquipo);
            _equipo = iEquipos.saveOrUpdate(_equipo);

            cargarListaEquipos();
            inicializarEquiposInvestigacion();
            _esModificaEquipo = false;
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));
        } catch (JpaDinaeException e) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-CO-01 Gestionar convocatorias (guardarConvocatoria)", e);

        }
        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaEquipos() throws JpaDinaeException {
        _listaEquiposInvestigacion = new ArrayList<EquiposInvestigacion>();
        _listaEquiposInvestigacion = iEquipos.findByPlanTrabajo(_planTrabajo.getIdPlanTrabajo());
        _listaEquiposInvestigacionModel = new ListGenericDataModel(_listaEquiposInvestigacion);
    }

        
      /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaDepartamentos() throws JpaDinaeException {
        _departamentosList = iCiudad.getListaDepartamentos(10L);
        if (_departamentosList == null || _departamentosList.isEmpty()) {
            _departamentosList = new ArrayList<LugarGeograficoDTO>();
        }
    }

    /**
     *
     */
    public void cargarCiudadesDepartamento() {
        if (_departamentoEvento != null && !(_departamentoEvento.compareTo(-1L) == 0)) {
            try {
                _listaCiudadesDepartamento = iCiudad.getListaLugaresByCodDepartamento(_departamentoEvento);
                if (_listaCiudadesDepartamento == null || _listaCiudadesDepartamento.isEmpty()) {
                    _listaCiudadesDepartamento = new ArrayList<LugarGeografico>();
                }
            } catch (JpaDinaeException ex) {
                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void inicializarEventosRelacionados() throws JpaDinaeException {

        nuevoEventoProyecto();

        _tituloEvento = null;
        _objetivoEvento = null;
        _departamentoEvento = -1L;
        _ciudadEvento = -1L;
        _fuenteEvento = -1L;
        _costoEvento = null;
        _tipoGastoEvento = new Long[0];
        _eventoProyectoDto = null;

        cargarListaDepartamentos();
        _listaCiudadesDepartamento = new ArrayList<LugarGeografico>();
        cargarListaTiposGasto();
        cargarListaEventosRelacionados();
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaEventosRelacionados() throws JpaDinaeException {

        _listaEventosProyectoDto = iEvento.findAllByPlanTrabajoDTO(_planTrabajo.getIdPlanTrabajo());

        if (_listaEventosProyectoDto == null || _listaEventosProyectoDto.isEmpty()) {
            _listaEventosProyectoDto = new ArrayList<EventoProyectoDTO>();
        } else {

            int index = 0;
            for (EventoProyectoDTO evt : _listaEventosProyectoDto) {

                String tipoGastoEvento = "";

                List<TipoGastoEvento> tipoGastoEventos = iTipoGasto.findTipoGastoEventioByEvento(evt.getIdEventoProyecto());
                List<TipoGastoEventoDTO> tipoGastoEventosDto = new ArrayList<TipoGastoEventoDTO>(tipoGastoEventos.size());

                int cont = 1;
                for (TipoGastoEvento tge : tipoGastoEventos) {

                    if (cont < tipoGastoEventos.size()) {
                        tipoGastoEvento += tge.getTipoGasto().getValor() + ", ";
                    } else {
                        tipoGastoEvento += tge.getTipoGasto().getValor();
                    }

                    tipoGastoEventosDto.add(new TipoGastoEventoDTO(tge));

                    cont++;
                }

                _listaEventosProyectoDto.get(index).setGastoEventoList(tipoGastoEventosDto);
                _listaEventosProyectoDto.get(index).setTipoGastoEvento(tipoGastoEvento);
                index++;
            }

        }

        _listaEventosProyectoModel = new ListGenericDataModel(_listaEventosProyectoDto);
    }

    /**
     *
     */
    private void nuevoEventoProyecto() {
        _eventoProyecto = new EventoProyecto();
        _eventoProyecto.setFechaRegistro(new Date());
        _eventoProyecto.setUsuarioRol(_usuarioRol);
    }

    /**
     *
     */
    private void cargarListaTiposGasto() throws JpaDinaeException {

        _listaTipoGastoEvento = UtilidadesItem.getListaSel(
                iConstantes.getConstantesPorTipo(TIPO_GASTOS_EVENTOS_RELACIONADOS), "idConstantes", "valor"
        );

    }

    /**
     *
     * @return
     */
    private List<TipoGastoEvento> arrayTipoGastoAListaTipoGasto() {

        List<TipoGastoEvento> tipoGastoEventos = new ArrayList<TipoGastoEvento>();

        if (_tipoGastoEvento.length > 0) {
            for (Long idTipoGasto : _tipoGastoEvento) {

                TipoGastoEvento gastoEvento = new TipoGastoEvento();
                gastoEvento.setEventoProyecto(_eventoProyecto);
                gastoEvento.setTipoGasto(new Constantes(idTipoGasto));
                gastoEvento.setUsuarioRol(_usuarioRol);
                gastoEvento.setMaquina(loginFaces.getPerfilUsuarioDTO().getMaquinaDTO().getIpLoginRemotoUsuario());

                tipoGastoEventos.add(gastoEvento);
            }
        }

        return tipoGastoEventos;
    }

    /**
     *
     * @return
     */
    public String guardarTabEventos() {
        try {
            validarCamposTabEventos();

            if (_eventoProyecto == null) {
                nuevoEventoProyecto();
            }

            _eventoProyecto.setCodigoCiudad(String.valueOf(_ciudadEvento.longValue()));
            _eventoProyecto.setNombreCiudad(iCiudad.findMunicipioByCodMunicipio(_ciudadEvento).getDescMunicipio());
            _eventoProyecto.setCosto(BigDecimal.valueOf(_costoEvento.doubleValue()));

            _eventoProyecto.setObjetivoEvento(_objetivoEvento);
            _eventoProyecto.setTituloEvento(_tituloEvento);
            _eventoProyecto.setFuenteProyecto(new FuenteProyecto(_fuenteEvento));

            _eventoProyecto = iEvento.saveOrUpdate(_eventoProyecto);

            List<TipoGastoEvento> _new = arrayTipoGastoAListaTipoGasto();
            List<TipoGastoEvento> _old = iTipoGasto.findTipoGastoEventioByEvento(_eventoProyecto.getIdEventoProyecto());

            iTipoGasto.replaceTipoGastoProyecto(_old, _new);

            inicializarEventosRelacionados();
            _esModificaEvento = false;
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(ex.getMessage());
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void validarCamposTabEventos() throws JpaDinaeException {

        if (_tituloEvento == null || "".equals(_tituloEvento)) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_column_titulo_evento") + "', es obligatorio");
        }

        if (_objetivoEvento == null || "".equals(_objetivoEvento)) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_column_objetivo_evento") + "', es obligatorio");
        }

        if (_departamentoEvento == null || _departamentoEvento.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_departamento_evento") + "', es obligatorio");
        }

        if (_ciudadEvento == null || _ciudadEvento.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_column_ciudad_evento") + "', es obligatorio");
        }

        if (_fuenteEvento == null || _fuenteEvento.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_fuente_evento") + "', es obligatorio");
        }

        if (_costoEvento == null) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_costo_evento") + "', es obligatorio");
        }

        if (_costoEvento.compareTo(0D) < 0) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_costo_evento") + "', no acepta valores negativos!");
        }

        if (Double.isNaN(_costoEvento)) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_costo_evento") + "', debe tener un valor numérico válido");
        }

        if (_tipoGastoEvento == null || _tipoGastoEvento.length == 0) {
            throw new JpaDinaeException("El campo '" + keyPropertiesFactory.value("cu_pr_5_lbl_tipos_gasto_evento") + "', es obligatorio");
        }
    }

    /**
     *
     * @param e
     */
    public void cargarDatosEventoProyecto(SelectEvent e) {

        try {
            if (_eventoProyectoDto == null) {
                return;
            }

            _eventoProyecto = iEvento.findById(_eventoProyectoDto.getIdEventoProyecto());
            _esModificaEvento = true;

            _tituloEvento = _eventoProyecto.getTituloEvento();
            _objetivoEvento = _eventoProyecto.getObjetivoEvento();
            _departamentoEvento = iCiudad.findMunicipioByCodMunicipio(Long.valueOf(_eventoProyecto.getCodigoCiudad())).getCodDepartamento();

            cargarCiudadesDepartamento();

            _ciudadEvento = Long.valueOf(_eventoProyecto.getCodigoCiudad());
            _fuenteEvento = _eventoProyecto.getFuenteProyecto().getIdFuenteProyecto();
            _costoEvento = Double.valueOf(_eventoProyecto.getCosto().doubleValue());

            List<TipoGastoEventoDTO> tipoGastoEventos = _eventoProyectoDto.getGastoEventoList();
            _tipoGastoEvento = new Long[tipoGastoEventos.size()];
            int index = 0;

            for (TipoGastoEventoDTO gastoEvento : tipoGastoEventos) {
                _tipoGastoEvento[index] = gastoEvento.getIdTipoGasto();
                index++;
            }
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param event
     */
    public void noSeleccionEventoProyecto(UnselectEvent event) {
        try {
            inicializarEventosRelacionados();

            _tituloEvento = null;
            _objetivoEvento = null;
            _departamentoEvento = -1L;
            _ciudadEvento = -1L;
            _listaCiudadesDepartamento = new ArrayList<LugarGeografico>();
            _fuenteEvento = -1L;
            _costoEvento = null;
            _tipoGastoEvento = new Long[0];
            _eventoProyectoDto = null;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String cancelarModificarEvento() {
        try {

            _esModificaEvento = false;
            inicializarEventosRelacionados();

            _tituloEvento = null;
            _objetivoEvento = null;
            _departamentoEvento = -1L;
            _ciudadEvento = -1L;
            _listaCiudadesDepartamento = new ArrayList<LugarGeografico>();
            _fuenteEvento = -1L;
            _costoEvento = null;
            _tipoGastoEvento = new Long[0];
            _eventoProyectoDto = null;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void inicializarViajesInvestigador() throws JpaDinaeException {
        cargarListaInvestigadoresViaje();
        cargarListaDepartamentoOrigenViaje();
        cargarListaDepartamentoDestinoViaje();
        cargarListadoFuentesProyecto();

        _idInvetigadorViaje = -1L;
        _eventoViaje = null;
        _idDepartamentoOrigenViaje = -1L;
        _listaCiudadOrigenViaje = new ArrayList<LugarGeografico>();
        _idCiudadOrigenViaje = -1L;
        _idDepartamentoDestinoViaje = -1L;
        _listaCiudadDestinoViaje = new ArrayList<LugarGeografico>();
        _idCiudadDestinoViaje = -1L;
        _costoPasajesViaje = null;
        _costoViaticosViaje = null;
        _idFuenteProyectoViaje = -1L;
        _viajesProyectoDto = null;

    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarViajesProyecto() throws JpaDinaeException {

        _listaViajesProyectoDto = iViajesProyecto.findViajesByPlanTrabajo(_planTrabajo.getIdPlanTrabajo());

        if (_listaViajesProyectoDto.isEmpty()) {
            _listaViajesProyectoDto = new ArrayList<ViajesProyectoDTO>();
        }
        _listaViajesProyectoModel = new ListGenericDataModel(_listaViajesProyectoDto);
    }

    private void nuevoViajeInvestigador() {
        _viajesProyecto = new ViajesProyecto();
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaInvestigadoresViaje() throws JpaDinaeException {

        _listaInvestigadorViaje = iInvestigador.getListaInvestigadorProyectoPorPlanTrabajoImpl(_planTrabajo.getIdPlanTrabajo());

        if (_listaInvestigadorViaje == null || _listaInvestigadorViaje.isEmpty()) {
            _listaInvestigadorViaje = new ArrayList<InvestigadorProyecto>();
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaDepartamentoOrigenViaje() throws JpaDinaeException {

        _listaDepartamentoOrigenViajeDto = iCiudad.getListaDepartamentos(10L);
        if (_listaDepartamentoOrigenViajeDto == null || _listaDepartamentoOrigenViajeDto.isEmpty()) {
            _listaDepartamentoOrigenViajeDto = new ArrayList<LugarGeograficoDTO>();
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    public void cargarListaCiudadOrigenViaje() throws JpaDinaeException {

        if (_idDepartamentoOrigenViaje != null && !(_idDepartamentoOrigenViaje.compareTo(-1L) == 0)) {
            try {
                _listaCiudadOrigenViaje = iCiudad.getListaLugaresByCodDepartamento(_idDepartamentoOrigenViaje);
                if (_listaCiudadOrigenViaje == null || _listaCiudadOrigenViaje.isEmpty()) {
                    _listaCiudadOrigenViaje = new ArrayList<LugarGeografico>();
                }
            } catch (JpaDinaeException ex) {
                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaDepartamentoDestinoViaje() throws JpaDinaeException {

        _listaDepartamentoDestinoViaje = iCiudad.getListaDepartamentos(10L);
        if (_listaDepartamentoDestinoViaje == null || _listaDepartamentoDestinoViaje.isEmpty()) {
            _listaDepartamentoDestinoViaje = new ArrayList<LugarGeograficoDTO>();
        }
    }

    /**
     *
     * @throws JpaDinaeException
     */
    public void cargarListaCiudadDestinoViaje() throws JpaDinaeException {

        if (_idDepartamentoDestinoViaje != null && !(_idDepartamentoDestinoViaje.compareTo(-1L) == 0)) {
            try {
                _listaCiudadDestinoViaje = iCiudad.getListaLugaresByCodDepartamento(_idDepartamentoDestinoViaje);
                if (_listaCiudadDestinoViaje == null || _listaCiudadDestinoViaje.isEmpty()) {
                    _listaCiudadDestinoViaje = new ArrayList<LugarGeografico>();
                }
            } catch (JpaDinaeException ex) {
                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param e
     */
    public void cargarDatosViajesProyecto(SelectEvent e) {

        try {
            if (_viajesProyectoDto == null) {
                return;
            }

            _viajesProyecto = iViajesProyecto.findById(_viajesProyectoDto.getIdViajeProyecto());

            _esModificaViaje = true;

            _idInvetigadorViaje = _viajesProyecto.getInvestigadoresProyecto().getIdInvestigadorProyecto();
            _eventoViaje = _viajesProyecto.getEvento();

            _idDepartamentoOrigenViaje = iCiudad.findMunicipioByCodMunicipio(Long.valueOf(_viajesProyecto.getCodigoCiudadOrigen())).getCodDepartamento();

            cargarListaCiudadOrigenViaje();
            _idCiudadOrigenViaje = Long.valueOf(_viajesProyecto.getCodigoCiudadOrigen());

            _idDepartamentoDestinoViaje = iCiudad.findMunicipioByCodMunicipio(Long.valueOf(_viajesProyecto.getCodigoCiudadDestino())).getCodDepartamento();

            cargarListaCiudadDestinoViaje();
            _idCiudadDestinoViaje = Long.valueOf(_viajesProyecto.getCodigoCiudadDestino());

            _costoPasajesViaje = Double.valueOf(_viajesProyecto.getCostosPasajes().doubleValue());
            _costoViaticosViaje = Double.valueOf(_viajesProyecto.getCostosViaticos().doubleValue());

            _idFuenteProyectoViaje = _viajesProyecto.getFuenteProyecto().getIdFuenteProyecto();

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param event
     */
    public void noSeleccionViajeProyecto(UnselectEvent event) {
        try {
            inicializarViajesInvestigador();

            _idInvetigadorViaje = -1L;
            _eventoViaje = null;
            _idDepartamentoOrigenViaje = -1L;
            _listaCiudadOrigenViaje = new ArrayList<LugarGeografico>();
            _idCiudadOrigenViaje = -1L;
            _idDepartamentoDestinoViaje = -1L;
            _listaCiudadDestinoViaje = new ArrayList<LugarGeografico>();
            _idCiudadDestinoViaje = -1L;
            _costoPasajesViaje = null;
            _costoViaticosViaje = null;
            _idFuenteProyectoViaje = -1L;

            _viajesProyectoDto = null;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String cancelarModificarViaje() {
        try {

            _esModificaViaje = false;
            inicializarViajesInvestigador();

            _viajesProyectoDto = null;

            _idInvetigadorViaje = -1L;
            _eventoViaje = null;
            _idDepartamentoOrigenViaje = -1L;
            _listaCiudadOrigenViaje = new ArrayList<LugarGeografico>();
            _idCiudadOrigenViaje = -1L;
            _idDepartamentoDestinoViaje = -1L;
            _listaCiudadDestinoViaje = new ArrayList<LugarGeografico>();
            _idCiudadDestinoViaje = -1L;
            _costoPasajesViaje = null;
            _costoViaticosViaje = null;
            _idFuenteProyectoViaje = -1L;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public String guardarTabViajes() {
        try {
            validarCamposTabViajes();

            if (_viajesProyecto == null) {
                nuevoViajeInvestigador();
            }

            _viajesProyecto.setCodigoCiudadDestino(String.valueOf(_idCiudadDestinoViaje));
            _viajesProyecto.setCodigoCiudadOrigen(String.valueOf(_idCiudadOrigenViaje));
            _viajesProyecto.setCostosPasajes(BigDecimal.valueOf(_costoPasajesViaje.doubleValue()));
            _viajesProyecto.setCostosViaticos(BigDecimal.valueOf(_costoViaticosViaje.doubleValue()));
            _viajesProyecto.setEvento(_eventoViaje);
            _viajesProyecto.setFuenteProyecto(new FuenteProyecto(_idFuenteProyectoViaje));
            _viajesProyecto.setInvestigadoresProyecto(new InvestigadorProyecto(_idInvetigadorViaje));
            _viajesProyecto.setNombreCiudadDestino(iCiudad.findMunicipioByCodMunicipio(_idCiudadDestinoViaje).getDescMunicipio());
            _viajesProyecto.setNombreCiudadOrigen(iCiudad.findMunicipioByCodMunicipio(_idCiudadOrigenViaje).getDescMunicipio());

            iViajesProyecto.saveOrUpdate(_viajesProyecto);

            inicializarViajesInvestigador();
            nuevoViajeInvestigador();
            _esModificaViaje = false;
            cargarViajesProyecto();
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(ex.getMessage());
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void validarCamposTabViajes() throws JpaDinaeException {

        if (_idInvetigadorViaje == null || _idInvetigadorViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Nombre del Investigador', es obligatorio");
        }

        if (_eventoViaje == null || "".equals(_eventoViaje)) {
            throw new JpaDinaeException("El campo 'Evento', es obligatorio");
        }

        if (_idDepartamentoOrigenViaje == null || _idDepartamentoOrigenViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Departamento Origen', es obligatorio");
        }

        if (_idCiudadOrigenViaje == null || _idCiudadOrigenViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Ciudad Origen', es obligatorio");
        }

        if (_idDepartamentoDestinoViaje == null || _idDepartamentoDestinoViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Departamento Destino', es obligatorio");
        }

        if (_idCiudadDestinoViaje == null || _idCiudadDestinoViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Ciudad Destino', es obligatorio");
        }

        if (_idFuenteProyectoViaje == null || _idFuenteProyectoViaje.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Fuente', es obligatorio");
        }

        if (_costoPasajesViaje == null) {
            throw new JpaDinaeException("El campo 'Costo Pasajes', es obligatorio");
        }

        if (_costoPasajesViaje.compareTo(0D) < 0) {
            throw new JpaDinaeException("El campo 'Costo Pasaje', no acepta valores negativos!");
        }

        if (Double.isNaN(_costoPasajesViaje)) {
            throw new JpaDinaeException("El campo 'Costo Pasaje', debe tener un valor numérico válido");
        }

        if (_costoViaticosViaje == null) {
            throw new JpaDinaeException("El campo 'Costo Viaticos', es obligatorio");
        }

        if (_costoViaticosViaje.compareTo(0D) < 0) {
            throw new JpaDinaeException("El campo 'Costo Viaticos', no acepta valores negativos!");
        }

        if (Double.isNaN(_costoViaticosViaje)) {
            throw new JpaDinaeException("El campo 'Costo Viaticos', debe tener un valor numérico válido");
        }

    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void inicializarOtrosGastos() throws JpaDinaeException {

        _idTipoRubroOtrosGastos = -1L;
        _idTipoOtrosGastos = -1L;
        _valorOtrosGastos = null;
        _idFuenteOtrosGastos = -1L;
        _otrosGastosProyectoDTO = null;

        cargarListaTipoRubroOtrosGastos();
        cargarListaTipoOtrosGastos();

        cargarListadoFuentesProyecto();
        cargarListaOtrosGastosProyecto();
    }

    /**
     *
     */
    private void nuevoOtroGastoProyecto() {
        _otrosGastosProyecto = new OtrosGastosProyecto();
        _otrosGastosProyecto.setFechaRegistro(new Date());

        if (_usuarioRol != null) {

            _otrosGastosProyecto.setIdUsuarioRol(_usuarioRol.getIdUsuarioRol());

        }
    }

    /**
     *
     */
    private void cargarListaOtrosGastosProyecto() throws JpaDinaeException {

        _listaOtrosGastosProyectoDTO = iOtrosGastosProyecto.findOtrosGastosByPlanTrabajoDTO(_planTrabajo.getIdPlanTrabajo());
        _listaOtrosGastosProyectoModel = new ListGenericDataModel(_listaOtrosGastosProyectoDTO);
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaTipoRubroOtrosGastos() throws JpaDinaeException {
        _listaTipoRubroOtrosGastos = UtilidadesItem.getListaSel(
                iConstantes.getConstantesPorTipo(OTROS_GASTOS_RUBROS), "idConstantes", "valor"
        );
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListaTipoOtrosGastos() throws JpaDinaeException {
        _listaTipoOtrosGastos = UtilidadesItem.getListaSel(
                iConstantes.getConstantesPorTipo(OTROS_GASTOS_TIPO), "idConstantes", "valor"
        );
    }

    /**
     *
     * @param e
     */
    public void cargarDatosOtrosGastosProyecto(SelectEvent e) {
        try {
            if (_otrosGastosProyectoDTO == null) {
                return;
            }

            _otrosGastosProyecto = iOtrosGastosProyecto.findById(_otrosGastosProyectoDTO.getIdOtrosGastosProyecto());
            _esModificaOtrosGastosProyecto = true;

            _idTipoRubroOtrosGastos = _otrosGastosProyecto.getIdTipoRubro();
            _idTipoOtrosGastos = _otrosGastosProyecto.getIdTipoEspecieEfectivo();
            _idFuenteOtrosGastos = _otrosGastosProyecto.getFuenteProyecto().getIdFuenteProyecto();
            _valorOtrosGastos = Double.valueOf(_otrosGastosProyecto.getValor().doubleValue());
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param event
     */
    public void noSeleccionOtrosGastosProyecto(UnselectEvent event) {
        try {
            inicializarOtrosGastos();

            _otrosGastosProyectoDTO = null;
            _idTipoRubroOtrosGastos = -1L;
            _idTipoOtrosGastos = -1L;
            _valorOtrosGastos = null;
            _idFuenteOtrosGastos = -1L;

            _esModificaOtrosGastosProyecto = false;

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String cancelarModificarOtrosGastosProyecto() {
        try {

            _esModificaOtrosGastosProyecto = false;
            inicializarOtrosGastos();

            _idTipoRubroOtrosGastos = -1L;
            _idTipoOtrosGastos = -1L;
            _valorOtrosGastos = null;
            _idFuenteOtrosGastos = -1L;
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public String guardarTabOtrosGastosProyecto() {
        try {
            validarCamposTabOtrosGastosProyecto();

            if (_otrosGastosProyecto == null) {
                nuevoOtroGastoProyecto();
            }

            _otrosGastosProyecto.setFuenteProyecto(new FuenteProyecto(_idFuenteOtrosGastos));
            _otrosGastosProyecto.setIdTipoEspecieEfectivo(_idTipoOtrosGastos);
            _otrosGastosProyecto.setIdTipoRubro(_idTipoRubroOtrosGastos);
            _otrosGastosProyecto.setValor(_valorOtrosGastos.longValue());

            iOtrosGastosProyecto.saveOrUpdate(_otrosGastosProyecto);

            inicializarOtrosGastos();
            nuevoOtroGastoProyecto();
            _esModificaOtrosGastosProyecto = false;
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_guardado_exitoso"));

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(ex.getMessage());
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @throws Exception
     */
    private PlanTrabajoImplementacion obtenerPlanTrabajoProyecto(Long idImplementacionProyecto) throws JpaDinaeException {

        CompromisoImplementacion compromisoPlanTrabajo = _iCompromisoImplementacion.findByIdImplementacionProyYtipoCompromisoNoCorregido(
                idImplementacionProyecto, IConstantes.TIPO_COMPROMISO_IMPLEMENTACION_PLAN_DE_TRABAJO);
        if (compromisoPlanTrabajo != null) {
            return _iPlanTrabajoImpl.findPlanTrabajoImplementacionByIdImplemenatcionProYIdCompromisoProImpl(
                    idImplementacionProyecto, compromisoPlanTrabajo.getIdCompromisoImplementacion());
        }

        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void validarCamposTabOtrosGastosProyecto() throws JpaDinaeException {

        OtrosGastosProyectoDTO conteoRubro = iOtrosGastosProyecto.contarOtrosGastosRubroPlanTrabajo(_planTrabajo.getIdPlanTrabajo(), _idTipoRubroOtrosGastos);
        int cont = 0;

        if (conteoRubro != null) {
            cont = conteoRubro.getConteo();
        }

        if (!(cont < 1) && _otrosGastosProyectoDTO == null) {
            String rubro = iConstantes.getConstantesPorIdConstante(_idTipoRubroOtrosGastos).getValor();
            throw new JpaDinaeException("El rubro '" + rubro + "' ya fué registrado anteriormente");
        }

        if (_idTipoRubroOtrosGastos == null || _idTipoRubroOtrosGastos.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Tipo Rubro', es obligatorio");
        }

        if (_idTipoOtrosGastos == null || _idTipoOtrosGastos.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Tipo', es obligatorio");
        }

        if (_idFuenteOtrosGastos == null || _idFuenteOtrosGastos.compareTo(-1L) == 0) {
            throw new JpaDinaeException("El campo 'Fuente', es obligatorio");
        }

        if (_valorOtrosGastos == null) {
            throw new JpaDinaeException("El campo 'Valor', es obligatorio");
        }

        if (_valorOtrosGastos.compareTo(0D) < 0) {
            throw new JpaDinaeException("El campo 'Valor', no acepta valores negativos!");
        }

        if (Double.isNaN(_valorOtrosGastos)) {
            throw new JpaDinaeException("El campo 'Valor', debe tener un valor numérico válido");
        }

    }

    /**
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        try {
            idTabSeleccionado = 0;
            _importPresupuestoPage = null;
            if (event == null || event.getTab() == null) {
                return;
            }
            if ("tabFuentesFinancieras".equals(event.getTab().getId())) {

                idTabSeleccionado = 0;

            } else if ("tabPersonalInvestigacion".equals(event.getTab().getId())) {

                idTabSeleccionado = 1;

            } else if ("tabEquiposInvestigacion".equals(event.getTab().getId())) {

                idTabSeleccionado = 2;

            } else if ("tabEventosRelacionados".equals(event.getTab().getId())) {

                idTabSeleccionado = 3;

            } else if ("tabViajesRelacionados".equals(event.getTab().getId())) {

                idTabSeleccionado = 4;

            } else if ("tabOtrosGastosProyecto".equals(event.getTab().getId())) {

                idTabSeleccionado = 5;

            } else if ("tabPresupuestoGlobal".equals(event.getTab().getId())) {

                idTabSeleccionado = 6;
                presupuestoUtilFaces.construirPresupuesto(PresupuestoUtil.PRESUPUESTO_TIPO_VALUE_GLOBAL_IMPL, _idImplementacionProyecto, null);
                _importPresupuestoPage = "../include_pantallas_genericas/includePresupuestoGlobalGenerico.xhtml";
            }

        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String regresar() {
        String navegacion = null;
        try {

            if (CU_PR_21.equals(_ingresoCU)) {

                if (isSoloConsulta()) {

                    navegacion = navigationFaces.redirectCuPr21RegistrarPlanDeTrabajoRedirect();

                } else {

                    navegacion = cuPr21RegistrarPlanDeTrabajoFaces.initReturnCU_DESDE_PR_20(_idImplementacionProyecto, _idCompromisoImplementacion, false);
                }
            }

            if (CU_PR_15.equals(_ingresoCU)) {
                navegacion = cuPr15_1_2_AvanceImplemenacionFaces.initReturnCU_DESDE_PR_20(_idImplementacionProyecto, _idCompromisoImplementacion, false);
            }

        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return navegacion;
    }

    /**
     *
     * @param e
     */
    public void eliminarFuenteFinanciera(ActionEvent e) {
        try {
            if (_fuenteProyectoDTO != null) {

                _fuenteProyecto = iFuente.getFuenteFinancieraById(_fuenteProyectoDTO.getIdFuenteProyecto());

                iFuente.delete(_fuenteProyecto);
                adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_eliminar_exitoso", new Object[]{FUENTE_FINANCIERA}));
            }

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransactionRolledbackLocalException ex) {
            adicionaMensajeError("No se puede eliminar la fuente seleccionada porque está siendo utilizada por algún rubro del presupuesto. Primero debe asignar otra fuente a los rubros que utiliza la fuente a eliminar.");
        } finally {
            try {
                _fuenteProyecto = null;
                _fuenteProyectoDTO = null;
                nombreFuenteFinanciera = null;
                tipoFuente = -1L;

                cargarListadoFuentesProyecto();

            } catch (JpaDinaeException ex) {
                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param e
     */
    public void eliminarEquipoInvestigacion(ActionEvent e) {
        try {
            _listaEquiposInvestigacion.remove(_equipo);
            iEquipos.delete(_equipo);

            cargarListaEquipos();
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_eliminar_exitoso", new Object[]{EQUIPO_INVESTIGACION}));
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param e
     */
    public void eliminarEventoProyecto(ActionEvent e) {
        try {

            if (_eventoProyectoDto == null) {
                return;
            }

            _eventoProyecto = iEvento.findById(_eventoProyectoDto.getIdEventoProyecto());

            _listaEventosProyectoDto.remove(_eventoProyectoDto);
            iEvento.delete(_eventoProyecto);

            cargarListaEventosRelacionados();
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_eliminar_exitoso", new Object[]{EVENTO}));
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param e
     */
    public void eliminarViajeRelacionado(ActionEvent e) {
        try {

            if (_viajesProyectoDto == null) {
                return;
            }

            _viajesProyecto = iViajesProyecto.findById(_viajesProyectoDto.getIdViajeProyecto());

            _listaViajesProyectoDto.remove(_viajesProyectoDto);
            iViajesProyecto.delete(_viajesProyecto);
            cargarViajesProyecto();
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_eliminar_exitoso", new Object[]{VIAJE}));
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param e
     */
    public void eliminarOtrosGastos(ActionEvent e) {
        try {

            if (_otrosGastosProyectoDTO == null) {
                return;
            }

            _otrosGastosProyecto = iOtrosGastosProyecto.findById(_otrosGastosProyectoDTO.getIdOtrosGastosProyecto());
            _listaOtrosGastosProyectoDTO.remove(_otrosGastosProyectoDTO);
            iOtrosGastosProyecto.delete(_otrosGastosProyecto);

            cargarListaOtrosGastosProyecto();
            adicionaMensajeInformativo(keyPropertiesFactory.value("general_mensaje_eliminar_exitoso", new Object[]{GASTO}));
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr05RegistrarPresupuestoImplementacionFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void ejecutarPresupuestoGlobal() {

    }

    public String getNombreFuenteFinanciera() {
        return nombreFuenteFinanciera;
    }

    public void setNombreFuenteFinanciera(String nombreFuenteFinanciera) {
        this.nombreFuenteFinanciera = nombreFuenteFinanciera;
    }

    public Long getTipoFuente() {
        return tipoFuente;
    }

    public void setTipoFuente(Long tipoFuente) {
        this.tipoFuente = tipoFuente;
    }

    public List<SelectItem> getListaItemsTiposFuentes() {
        if (listaItemsTiposFuentes == null) {
            listaTiposFuentes = new ArrayList<Constantes>();
            listaItemsTiposFuentes = new ArrayList<SelectItem>();
            try {
                listaTiposFuentes = iConstantes.getConstantesPorTipo(TIPO_FUENTE_FINANCIERA);
                for (Constantes con : listaTiposFuentes) {
                    listaItemsTiposFuentes.add(new SelectItem(con.getIdConstantes(), con.getValor()));
                }
                //listaItemsTiposFuentes = UtilidadesItem.getListaSel(listaTiposFuentes, "valor", "valor");
            } catch (JpaDinaeException ex) {
                adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "CU-PR-05 Consulta de fuentes financieras (getListaFuentesFinancieras) ", ex);
            }
        }
        return listaItemsTiposFuentes;
    }

    public void setListaItemsTiposFuentes(List listaItemsTiposFuentes) {
        this.listaItemsTiposFuentes = listaItemsTiposFuentes;
    }

    public List<Constantes> getListaTiposFuentes() {
        return listaTiposFuentes;
    }

    public void setListaTiposFuentes(List<Constantes> listaTiposFuentes) {
        this.listaTiposFuentes = listaTiposFuentes;
    }

    public List<FuenteProyectoDTO> getListaFuentesProyecto() {
        return _listaFuentesProyecto;
    }

    public void setListaFuentesProyecto(List<FuenteProyectoDTO> _listaFuentesProyecto) {
        this._listaFuentesProyecto = _listaFuentesProyecto;
    }

    public List<SelectItem> getListaFuentesProyectoItems() {
        return _listaFuentesProyectoItems;
    }

    public void setListaFuentesProyectoItems(List<SelectItem> _listaFuentesProyectoItems) {
        this._listaFuentesProyectoItems = _listaFuentesProyectoItems;
    }

    public FuenteProyecto getFuenteSeleccionada() {
        return _fuenteSeleccionada;
    }

    public void setFuenteSeleccionada(FuenteProyecto _fuenteSeleccionada) {
        this._fuenteSeleccionada = _fuenteSeleccionada;
    }

    public FuenteProyecto getFuenteProyecto() {
        return _fuenteProyecto;
    }

    public void setFuenteProyecto(FuenteProyecto _fuenteProyecto) {
        this._fuenteProyecto = _fuenteProyecto;
    }

    public Long getOrigenEquipo() {
        return _origenEquipo;
    }

    public void setOrigenEquipo(Long _origenEquipo) {
        this._origenEquipo = _origenEquipo;
    }

    public Double getValorEquipo() {
        return _valorEquipo;
    }

    public void setValorEquipo(Double _valorEquipo) {
        this._valorEquipo = _valorEquipo;
    }

    public Long getFuenteEquipo() {
        return _fuenteEquipo;
    }

    public void setFuenteEquipo(Long _fuenteEquipo) {
        this._fuenteEquipo = _fuenteEquipo;
    }

    public String getEspecificacionesEquipo() {
        return _especificacionesEquipo;
    }

    public void setEspecificacionesEquipo(String _especificacionesEquipo) {
        this._especificacionesEquipo = _especificacionesEquipo;
    }

    public int getIdTabSeleccionado() {
        return idTabSeleccionado;
    }

    public void setIdTabSeleccionado(int idTabSeleccionado) {
        this.idTabSeleccionado = idTabSeleccionado;
    }

    public ListGenericDataModel getListaFuentesFinancierasModel() {
        return _listaFuentesFinancierasModel;
    }

    public void setListaFuentesFinancierasModel(ListGenericDataModel _listaFuentesFinancierasModel) {
        this._listaFuentesFinancierasModel = _listaFuentesFinancierasModel;
    }

    public boolean isEsModificacion() {
        return _esModificacion;
    }

    public void setEsModificacion(boolean _esModificacion) {
        this._esModificacion = _esModificacion;
    }

    public List<InvestigadorProyecto> getListaInvestigadoresProyecto() {
        return _listaInvestigadoresProyecto;
    }

    public void setListaInvestigadoresProyecto(List<InvestigadorProyecto> _listaInvestigadoresProyecto) {
        this._listaInvestigadoresProyecto = _listaInvestigadoresProyecto;
    }

    public List<SelectItem> getListaOrigenesFondos() {
        return _listaOrigenesFondos;
    }

    public void setListaOrigenesFondos(List<SelectItem> _listaOrigenesFondos) {
        this._listaOrigenesFondos = _listaOrigenesFondos;
    }

    public EquiposInvestigacion getEquipo() {
        return _equipo;
    }

    public void setEquipo(EquiposInvestigacion _equipo) {
        this._equipo = _equipo;
    }

    public List<EquiposInvestigacion> getListaEquiposInvestigacion() {
        return _listaEquiposInvestigacion;
    }

    public void setListaEquiposInvestigacion(List<EquiposInvestigacion> _listaEquiposInvestigacion) {
        this._listaEquiposInvestigacion = _listaEquiposInvestigacion;
    }

    public ListGenericDataModel getListaEquiposInvestigacionModel() {
        return _listaEquiposInvestigacionModel;
    }

    public void setListaEquiposInvestigacionModel(ListGenericDataModel _listaEquiposInvestigacionModel) {
        this._listaEquiposInvestigacionModel = _listaEquiposInvestigacionModel;
    }

    public boolean isEsModificaEquipo() {
        return _esModificaEquipo;
    }

    public void setEsModificaEquipo(boolean _esModificaEquipo) {
        this._esModificaEquipo = _esModificaEquipo;
    }

    public String getNombreEquipo() {
        return _nombreEquipo;
    }

    public void setNombreEquipo(String _nombreEquipo) {
        this._nombreEquipo = _nombreEquipo;
    }

    public String getTituloEvento() {
        return _tituloEvento;
    }

    public void setTituloEvento(String _tituloEvento) {
        this._tituloEvento = _tituloEvento;
    }

    public String getObjetivoEvento() {
        return _objetivoEvento;
    }

    public void setObjetivoEvento(String _objetivoEvento) {
        this._objetivoEvento = _objetivoEvento;
    }

    public Long getDepartamentoEvento() {
        return _departamentoEvento;
    }

    public void setDepartamentoEvento(Long _departamentoEvento) {
        this._departamentoEvento = _departamentoEvento;
    }

    public Long getCiudadEvento() {
        return _ciudadEvento;
    }

    public void setCiudadEvento(Long _ciudadEvento) {
        this._ciudadEvento = _ciudadEvento;
    }

    public Long getFuenteEvento() {
        return _fuenteEvento;
    }

    public void setFuenteEvento(Long _fuenteEvento) {
        this._fuenteEvento = _fuenteEvento;
    }

    public Double getCostoEvento() {
        return _costoEvento;
    }

    public void setCostoEvento(Double _costoEvento) {
        this._costoEvento = _costoEvento;
    }

    public Long[] getTipoGastoEvento() {
        return _tipoGastoEvento;
    }

    public void setTipoGastoEvento(Long[] _tipoGastoEvento) {
        this._tipoGastoEvento = _tipoGastoEvento;
    }

    public List<LugarGeografico> getListaDepartamentos() {
        return _listaDepartamentos;
    }

    public void setListaDepartamentos(List<LugarGeografico> _listaDepartamentos) {
        this._listaDepartamentos = _listaDepartamentos;
    }

    public List<LugarGeograficoDTO> getDepartamentosList() {
        return _departamentosList;
    }

    public void setDepartamentosList(List<LugarGeograficoDTO> _departamentosList) {
        this._departamentosList = _departamentosList;
    }

    public List<LugarGeografico> getListaCiudadesDepartamento() {
        return _listaCiudadesDepartamento;
    }

    public void setListaCiudadesDepartamento(List<LugarGeografico> _listaCiudadesDepartamento) {
        this._listaCiudadesDepartamento = _listaCiudadesDepartamento;
    }

    public List<SelectItem> getListaTipoGastoEvento() {
        return _listaTipoGastoEvento;
    }

    public void setListaTipoGastoEvento(List<SelectItem> _listaTipoGastoEvento) {
        this._listaTipoGastoEvento = _listaTipoGastoEvento;
    }

    public ListGenericDataModel getListaEventosProyectoModel() {
        return _listaEventosProyectoModel;
    }

    public void setListaEventosProyectoModel(ListGenericDataModel _listaEventosProyectoModel) {
        this._listaEventosProyectoModel = _listaEventosProyectoModel;
    }

    public List<EventoProyecto> getListaEventosProyecto() {
        return _listaEventosProyecto;
    }

    public void setListaEventosProyecto(List<EventoProyecto> _listaEventosProyecto) {
        this._listaEventosProyecto = _listaEventosProyecto;
    }

    public boolean isEsModificaEvento() {
        return _esModificaEvento;
    }

    public void setEsModificaEvento(boolean _esModificaEvento) {
        this._esModificaEvento = _esModificaEvento;
    }

    public EventoProyecto getEventoProyecto() {
        return _eventoProyecto;
    }

    public void setEventoProyecto(EventoProyecto _eventoProyecto) {
        this._eventoProyecto = _eventoProyecto;
    }

    public List<InvestigadorProyecto> getListaInvestigadorViaje() {
        return _listaInvestigadorViaje;
    }

    public void setListaInvestigadorViaje(List<InvestigadorProyecto> _listaInvestigadorViaje) {
        this._listaInvestigadorViaje = _listaInvestigadorViaje;
    }

    public Long getIdInvetigadorViaje() {
        return _idInvetigadorViaje;
    }

    public void setIdInvetigadorViaje(Long _idInvetigadorViaje) {
        this._idInvetigadorViaje = _idInvetigadorViaje;
    }

    public String getEventoViaje() {
        return _eventoViaje;
    }

    public void setEventoViaje(String _eventoViaje) {
        this._eventoViaje = _eventoViaje;
    }

    public List<LugarGeografico> getListaDepartamentoOrigenViaje() {
        return _listaDepartamentoOrigenViaje;
    }
    
     public List<LugarGeograficoDTO> getListaDepartamentoOrigenViajeDto() {
        return _listaDepartamentoOrigenViajeDto;
    }

    public void setListaDepartamentoOrigenViajeDto(List<LugarGeograficoDTO> _listaDepartamentoOrigenViajeDto) {
        this._listaDepartamentoOrigenViajeDto = _listaDepartamentoOrigenViajeDto;
    }

    public void setListaDepartamentoOrigenViaje(List<LugarGeografico> _listaDepartamentoOrigenViaje) {
        this._listaDepartamentoOrigenViaje = _listaDepartamentoOrigenViaje;
    }

    public Long getIdDepartamentoOrigenViaje() {
        return _idDepartamentoOrigenViaje;
    }

    public void setIdDepartamentoOrigenViaje(Long _idDepartamentoOrigenViaje) {
        this._idDepartamentoOrigenViaje = _idDepartamentoOrigenViaje;
    }

    public List<LugarGeografico> getListaCiudadOrigenViaje() {
        return _listaCiudadOrigenViaje;
    }

    public void setListaCiudadOrigenViaje(List<LugarGeografico> _listaCiudadOrigenViaje) {
        this._listaCiudadOrigenViaje = _listaCiudadOrigenViaje;
    }

    public Long getIdCiudadOrigenViaje() {
        return _idCiudadOrigenViaje;
    }

    public void setIdCiudadOrigenViaje(Long _idCiudadOrigenViaje) {
        this._idCiudadOrigenViaje = _idCiudadOrigenViaje;
    }

    public List<LugarGeograficoDTO> getListaDepartamentoDestinoViaje() {
        return _listaDepartamentoDestinoViaje;
    }

    public void setListaDepartametoDestinoViaje(List<LugarGeograficoDTO> _listaDepartametoDestinoViaje) {
        this._listaDepartamentoDestinoViaje = _listaDepartametoDestinoViaje;
    }

    public Long getIdDepartamentoDestinoViaje() {
        return _idDepartamentoDestinoViaje;
    }

    public void setIdDepartamentoDestinoViaje(Long _idDepartamentoDestinoViaje) {
        this._idDepartamentoDestinoViaje = _idDepartamentoDestinoViaje;
    }

    public List<LugarGeografico> getListaCiudadDestinoViaje() {
        return _listaCiudadDestinoViaje;
    }

    public void setListaCiudadDestinoViaje(List<LugarGeografico> _listaCiudadDestinoViaje) {
        this._listaCiudadDestinoViaje = _listaCiudadDestinoViaje;
    }

    public Long getIdCiudadDestinoViaje() {
        return _idCiudadDestinoViaje;
    }

    public void setIdCiudadDestinoViaje(Long _idCiudadDestinoViaje) {
        this._idCiudadDestinoViaje = _idCiudadDestinoViaje;
    }

    public Double getCostoPasajesViaje() {
        return _costoPasajesViaje;
    }

    public void setCostoPasajesViaje(Double _costoPasajesViaje) {
        this._costoPasajesViaje = _costoPasajesViaje;
    }

    public Double getCostoViaticosViaje() {
        return _costoViaticosViaje;
    }

    public void setCostoViaticosViaje(Double _costoViaticosViaje) {
        this._costoViaticosViaje = _costoViaticosViaje;
    }

    public Long getIdFuenteProyectoViaje() {
        return _idFuenteProyectoViaje;
    }

    public void setIdFuenteProyectoViaje(Long _idFuenteProyectoViaje) {
        this._idFuenteProyectoViaje = _idFuenteProyectoViaje;
    }

    public ViajesProyecto getViajesProyecto() {
        return _viajesProyecto;
    }

    public void setViajesProyecto(ViajesProyecto _viajesProyecto) {
        this._viajesProyecto = _viajesProyecto;
    }

    public boolean isEsModificaViaje() {
        return _esModificaViaje;
    }

    public void setEsModificaViaje(boolean _esModificaViaje) {
        this._esModificaViaje = _esModificaViaje;
    }

    public List<ViajesProyecto> getListaViajesProyecto() {
        return _listaViajesProyecto;
    }

    public void setListaViajesProyecto(List<ViajesProyecto> _listaViajesProyecto) {
        this._listaViajesProyecto = _listaViajesProyecto;
    }

    public ListGenericDataModel getListaViajesProyectoModel() {
        return _listaViajesProyectoModel;
    }

    public void setListaViajesProyectoModel(ListGenericDataModel _listaViajesProyectoModel) {
        this._listaViajesProyectoModel = _listaViajesProyectoModel;
    }

    public Long getIdTipoRubroOtrosGastos() {
        return _idTipoRubroOtrosGastos;
    }

    public void setIdTipoRubroOtrosGastos(Long _idTipoRubroOtrosGastos) {
        this._idTipoRubroOtrosGastos = _idTipoRubroOtrosGastos;
    }

    public List<SelectItem> getListaTipoRubroOtrosGastos() {
        return _listaTipoRubroOtrosGastos;
    }

    public void setListaTipoRubroOtrosGastos(List<SelectItem> _listaTipoRubroOtrosGastos) {
        this._listaTipoRubroOtrosGastos = _listaTipoRubroOtrosGastos;
    }

    public Long getIdTipoOtrosGastos() {
        return _idTipoOtrosGastos;
    }

    public void setIdTipoOtrosGastos(Long _idTipoOtrosGastos) {
        this._idTipoOtrosGastos = _idTipoOtrosGastos;
    }

    public List<SelectItem> getListaTipoOtrosGastos() {
        return _listaTipoOtrosGastos;
    }

    public void setListaTipoOtrosGastos(List<SelectItem> _listaTipoOtrosGastos) {
        this._listaTipoOtrosGastos = _listaTipoOtrosGastos;
    }

    public Long getIdFuenteOtrosGastos() {
        return _idFuenteOtrosGastos;
    }

    public void setIdFuenteOtrosGastos(Long _idFuenteOtrosGastos) {
        this._idFuenteOtrosGastos = _idFuenteOtrosGastos;
    }

    public List<OtrosGastosProyecto> getListaOtrosGastosProyecto() {
        return _listaOtrosGastosProyecto;
    }

    public void setListaOtrosGastosProyecto(List<OtrosGastosProyecto> _listaOtrosGastosProyecto) {
        this._listaOtrosGastosProyecto = _listaOtrosGastosProyecto;
    }

    public ListGenericDataModel getListaOtrosGastosProyectoModel() {
        return _listaOtrosGastosProyectoModel;
    }

    public void setListaOtrosGastosProyectoModel(ListGenericDataModel _listaOtrosGastosProyectoModel) {
        this._listaOtrosGastosProyectoModel = _listaOtrosGastosProyectoModel;
    }

    public OtrosGastosProyecto getOtrosGastosProyecto() {
        return _otrosGastosProyecto;
    }

    public void setOtrosGastosProyecto(OtrosGastosProyecto _otrosGastosProyecto) {
        this._otrosGastosProyecto = _otrosGastosProyecto;
    }

    public boolean isEsModificaOtrosGastosProyecto() {
        return _esModificaOtrosGastosProyecto;
    }

    public void setEsModificaOtrosGastosProyecto(boolean _esModificaOtrosGastosProyecto) {
        this._esModificaOtrosGastosProyecto = _esModificaOtrosGastosProyecto;
    }

    public Double getValorOtrosGastos() {
        return _valorOtrosGastos;
    }

    public void setValorOtrosGastos(Double _valorOtrosGastos) {
        this._valorOtrosGastos = _valorOtrosGastos;
    }

    public List<SelectItem> getListaTipoFuente() {
        return _listaTipoFuente;
    }

    public void setListaTipoFuente(List<SelectItem> _listaTipoFuente) {
        this._listaTipoFuente = _listaTipoFuente;
    }

    public String getImportPresupuestoPage() {
        return _importPresupuestoPage;
    }

    public void setImportPresupuestoPage(String _importPresupuestoPage) {
        this._importPresupuestoPage = _importPresupuestoPage;
    }

    public FuenteProyectoDTO getFuenteProyectoDTO() {
        return _fuenteProyectoDTO;
    }

    public void setFuenteProyectoDTO(FuenteProyectoDTO _fuenteProyectoDTO) {
        this._fuenteProyectoDTO = _fuenteProyectoDTO;
    }

    public List<SelectItem> getListaFuentesProyectoItem() {

        if (_listaFuentesProyecto == null) {
            return new ArrayList<SelectItem>();
        }

        _listaFuentesProyectoItem = new ArrayList<SelectItem>();

        for (FuenteProyectoDTO unFuenteProyectoDTO : _listaFuentesProyecto) {
            _listaFuentesProyectoItem.add(new SelectItem(unFuenteProyectoDTO.getIdFuenteProyecto(), unFuenteProyectoDTO.getNombreFuente()));
        }

        return _listaFuentesProyectoItem;
    }

    public Long getIdInvestigadorPersonal() {
        return idInvestigadorPersonal;
    }

    public void setIdInvestigadorPersonal(Long idInvestigadorPersonal) {
        this.idInvestigadorPersonal = idInvestigadorPersonal;
    }

    public List<OtrosGastosProyectoDTO> getListaOtrosGastosProyectoDTO() {
        return _listaOtrosGastosProyectoDTO;
    }

    public void setListaOtrosGastosProyectoDTO(List<OtrosGastosProyectoDTO> _listaOtrosGastosProyectoDTO) {
        this._listaOtrosGastosProyectoDTO = _listaOtrosGastosProyectoDTO;
    }

    public OtrosGastosProyectoDTO getOtrosGastosProyectoDTO() {
        return _otrosGastosProyectoDTO;
    }

    public void setOtrosGastosProyectoDTO(OtrosGastosProyectoDTO _otrosGastosProyectoDTO) {
        this._otrosGastosProyectoDTO = _otrosGastosProyectoDTO;
    }

    public ViajesProyectoDTO getViajesProyectoDto() {
        return _viajesProyectoDto;
    }

    public void setViajesProyectoDto(ViajesProyectoDTO _viajesProyectoDto) {
        this._viajesProyectoDto = _viajesProyectoDto;
    }

    public List<ViajesProyectoDTO> getListaViajesProyectoDto() {
        return _listaViajesProyectoDto;
    }

    public void setListaViajesProyectoDto(List<ViajesProyectoDTO> _listaViajesProyectoDto) {
        this._listaViajesProyectoDto = _listaViajesProyectoDto;
    }

    public List<EventoProyectoDTO> getListaEventosProyectoDto() {
        return _listaEventosProyectoDto;
    }

    public void setListaEventosProyectoDto(List<EventoProyectoDTO> _listaEventosProyectoDto) {
        this._listaEventosProyectoDto = _listaEventosProyectoDto;
    }

    public EventoProyectoDTO getEventoProyectoDto() {
        return _eventoProyectoDto;
    }

    public void setEventoProyectoDto(EventoProyectoDTO _eventoProyectoDto) {
        this._eventoProyectoDto = _eventoProyectoDto;
    }

    public PlanTrabajoImplementacion getPlanTrabajo() {
        return _planTrabajo;
    }

    public void setPlanTrabajo(PlanTrabajoImplementacion _planTrabajo) {
        this._planTrabajo = _planTrabajo;
    }

    public ImplementacionesProyecto getProyectoImplemetacion() {
        return _proyectoImplemetacion;
    }

    public void setProyectoImplemetacion(ImplementacionesProyecto _proyectoImplemetacion) {
        this._proyectoImplemetacion = _proyectoImplemetacion;
    }

    /**
     *
     * @param idProyectoImplementacion
     * @param idCompromisoProyectoImpl
     * @throws JpaDinaeException
     */
    public void initProyectoDesdeCU15(Long idProyectoImplementacion, Long idCompromisoProyectoImpl) throws JpaDinaeException {

        if (idProyectoImplementacion != null && idCompromisoProyectoImpl != null) {

            this._idCompromisoImplementacion = idCompromisoProyectoImpl;
            this._idImplementacionProyecto = idProyectoImplementacion;

            initProyecto(idProyectoImplementacion, idCompromisoProyectoImpl, CU_PR_15, true);
        }
    }

    public boolean isSoloConsulta() {

        return CU_PR_21.equals(_ingresoCU) && permiteEditar;

    }
}
