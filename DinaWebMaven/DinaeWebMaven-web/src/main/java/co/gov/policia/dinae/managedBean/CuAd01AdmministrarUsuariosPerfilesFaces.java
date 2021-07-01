package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.constantes.IConstantesRole;
import co.gov.policia.dinae.dto.InvestigadorProyectoDTO;
import co.gov.policia.dinae.dto.RolDTO;
import co.gov.policia.dinae.dto.RolUsuarioDTO;
import co.gov.policia.dinae.dto.UnidadPolicialDTO;
import co.gov.policia.dinae.dto.UsuarioRolUnidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IUsuarioRolLocal;
import co.gov.policia.dinae.interfaces.IUsuarioRolUnidadLocal;
import co.gov.policia.dinae.interfaces.IUsuarioUnidadPolicialLocal;
import co.gov.policia.dinae.interfaces.IVistaFuncionarioLocal;
import co.gov.policia.dinae.model.ListGenericDataModel;
import co.gov.policia.dinae.modelo.Rol;
import co.gov.policia.dinae.modelo.UnidadPolicial;
import co.gov.policia.dinae.modelo.UsuarioRol;
import co.gov.policia.dinae.modelo.UsuarioUnidadPolicial;
import co.gov.policia.dinae.modelo.VistaFuncionario;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Édder Peña Barranco
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 * @since 2013/12/15
 */
@javax.inject.Named(value = "cuAd01TipoUsuarioAdministrador")
@javax.enterprise.context.SessionScoped
public class CuAd01AdmministrarUsuariosPerfilesFaces extends JSFUtils implements Serializable {

    @EJB
    private IVistaFuncionarioLocal iVistaFuncionarioLocal;

    @EJB
    private IUnidadPolicialLocal _iUnidadPolicialLocal;

    @EJB
    private IUsuarioRolUnidadLocal _iUsuarioRolUnidadLocal;

    @EJB
    private IVistaFuncionarioLocal _iVistaFuncionario;

    @EJB
    private IUsuarioRolLocal _iUsuarioRol;

    @EJB
    private IUsuarioUnidadPolicialLocal _iUsuarioUnidad;

    private List<UnidadPolicialDTO> _listaUnidadesPolicialesActivas;

    private List<UsuarioRolUnidadDTO> _listaUsuariosAdministativosUnidad;

    private List<UsuarioRolUnidadDTO> _listaUsuariosRoles;

    private List<UsuarioRolUnidadDTO> _listaUsuariosRolesEduco;

    private ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosAdministativosUnidadModel;

    private ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosUnidadEducoModel;

    private ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosRolesModel;

    private ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosBusquedaRolesModel;

    private List<UsuarioRolUnidadDTO> _listaUsuariosRolesUsuarioUnidadPolicial;

    private String _tipoUsuarioAdministrar;

    private String _unidadPolicialValue;

    private List<InvestigadorProyectoDTO> _listaDatosUsuarioSeleccionado;

    private List<InvestigadorProyectoDTO> _listaDatosNuevoUsuario;

    private UsuarioRolUnidadDTO _usuarioModificarRolAdmin;

    private UsuarioRolUnidadDTO _usuarioModificarRoles;

    private UsuarioRolUnidadDTO _usuarioSeleccionadoModificarRoles;

    private String _nombreUnidad;

    private String _nombreRolModificar;

    private String _identificacion;

    private InputText _inputIdentificacion;

    private boolean _disableCambiarUsuario;

    private RolDTO[] _rolesSeleccionados;

    private List<SelectItem> _rolesBusqueda;
    private Long _idRolSeleccionadoBusqueda;
    private String identificacionBusqueda;

    private List<RolDTO> _listaRolesDTO;

    private ListGenericDataModel<RolDTO> _listaRolesDTOModel;

    private boolean _origenUnidad;

    private boolean _modifica;

    private boolean _crearUsuario;

    private int idTabSeleccionado;

    private VistaFuncionario vistaFuncionarioAdministraUsuarioUnidad;
    private UnidadPolicial unidadPolicialAdministraUsuarioUnidad;
    private String identificacionBusquedaAdministraUsuarioUnidad;
    private Long idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad;

    private String identificacionjefeUnidadBuscar;
    private VistaFuncionario vistaFuncionarioSeleccionado;
    private String nombreUnidadPolicialSeleccionado;
    private Long idUnidadPolicialSeleccionado;
    private String mensajePopupJefeUnidad;
    private boolean mostrarAgregarJefeUnidadPolicial;
    private boolean mostrarAgregarJefeGrupoInvestigacion;

    /**
     *
     * @return @throws Exception
     */
    public String initReturnCU() throws Exception {

        _tipoUsuarioAdministrar = null;
        _listaUnidadesPolicialesActivas = new ArrayList<UnidadPolicialDTO>();

        inicializarCampos();

        //CARGAMOS TODOS LOS ROLES
        List<RolDTO> listaRoles = _iUsuarioRolUnidadLocal.findAllRoles();

        _rolesBusqueda = new ArrayList<SelectItem>();
        for (RolDTO rolDTO : listaRoles) {

            _rolesBusqueda.add(new SelectItem(rolDTO.getIdRol(), rolDTO.getNombre()));

        }

        //CARGAR LAS UNIDADES 
        cargarListadoUnidadesPolicialesActivas();

        return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();
    }

    /**
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {

        idTabSeleccionado = 0;

        if (event == null || event.getTab() == null) {
            return;
        }
        if ("idTabPerfiles".equals(event.getTab().getId())) {

            idTabSeleccionado = 0;

        } else if ("idTabUsuarios".equals(event.getTab().getId())) {

            vistaFuncionarioAdministraUsuarioUnidad = null;
            identificacionBusquedaAdministraUsuarioUnidad = null;

            idTabSeleccionado = 1;
        }
    }

    /**
     *
     * @return
     */
    public String guardarModificarUsuarioRol() {
        try {

            Long[] idRolesFiltro = null;
            if (_tipoUsuarioAdministrar.equals("V")) {

                idRolesFiltro = IConstantesRole.ROLES_VICIN;

            } else if (_tipoUsuarioAdministrar.equals("U")) {

                idRolesFiltro = IConstantesRole.ROLES_UNIDAD;

            }

            Map<Long, RolUsuarioDTO> usuarioRolesMap = new HashMap<Long, RolUsuarioDTO>();

            if (!_modifica) {
                validarCampos();
            } else {

                List<RolUsuarioDTO> usuarioRoles = _iUsuarioRol.getAllRolUsuarioDTOByIdentificacionYroles(_identificacion, idRolesFiltro);

                for (RolUsuarioDTO rol : usuarioRoles) {
                    rol.setActivo("N");
                    usuarioRolesMap.put(rol.getIdUsuarioRol(), rol);
                }
            }

            if (_rolesSeleccionados != null) {

                for (RolDTO rol : _rolesSeleccionados) {

                    UsuarioRol rolExistente = _iUsuarioRol.findByUsuarioYRol(rol.getIdRol(), _identificacion);

                    if (rolExistente == null) {

                        UsuarioRol usuarioRol = new UsuarioRol();
                        usuarioRol.setIdUsuarioRol(null);
                        usuarioRol.setIdentificadorUsuario(_identificacion);
                        usuarioRol.setRol(new Rol(rol.getIdRol()));
                        usuarioRol.setFechaInicio(new Date());
                        usuarioRol.setActivo("S");

                        _iUsuarioRol.guardarUsuarioRol(usuarioRol);

                    } else if (usuarioRolesMap.containsKey(rolExistente.getIdUsuarioRol())) {
                        usuarioRolesMap.get(rolExistente.getIdUsuarioRol()).setActivo("S");
                    }
                }

                for (Long key : usuarioRolesMap.keySet()) {

                    if ("N".equals(usuarioRolesMap.get(key).getActivo())) {
                        _iUsuarioRol.deshabilitarUsuarioRol(key);
                    }
                }

                if (_origenUnidad) {

                    Long idUnidad = Long.valueOf(_unidadPolicialValue.split("-")[0]);

                    UsuarioUnidadPolicial usuarioUnidadPolicial = _iUsuarioUnidad.findByIdUnidadIdentificacion(idUnidad, _identificacion);

                    if (usuarioUnidadPolicial == null) {
                        usuarioUnidadPolicial = new UsuarioUnidadPolicial();
                    }
                    usuarioUnidadPolicial.setIdentificadorUsuario(_identificacion);
                    usuarioUnidadPolicial.setUnidadPolicial(new UnidadPolicial(idUnidad));

                    _iUsuarioUnidad.guardarUsuarioUnidadPolicial(usuarioUnidadPolicial);
                }

                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));
            }

        } catch (Exception ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param esVicin
     * @return
     */
    public String irCrearUsuario(boolean esVicin) {
        _origenUnidad = !esVicin;
        inicializarCrearUsuario();
        cargarRoles();
        _modifica = false;
        _crearUsuario = true;
        return navigationFaces.redirectCuAd01_2AdministracionUsuarioPerfilesModificarRolRedirect();
    }

    /**
     *
     */
    private void inicializarCrearUsuario() {
        _identificacion = null;
        _inputIdentificacion = new InputText();
        _listaDatosNuevoUsuario = new ArrayList<InvestigadorProyectoDTO>();
        _listaRolesDTOModel = null;
        _listaRolesDTO = new ArrayList<RolDTO>();
        _rolesSeleccionados = null;
    }

    /**
     *
     * @param e
     */
    public void cargarDatosUsuarioModificarRoles() {
        try {
            if (_usuarioSeleccionadoModificarRoles == null) {

                return;
            }

            _identificacion = _usuarioSeleccionadoModificarRoles.getIdentificacionUsuario();
            _nombreUnidad = _usuarioSeleccionadoModificarRoles.getNombreUnidad();

            _listaDatosNuevoUsuario = new ArrayList<InvestigadorProyectoDTO>();

            InvestigadorProyectoDTO datosUsuario = new InvestigadorProyectoDTO();
            datosUsuario.setGrado(_usuarioSeleccionadoModificarRoles.getGradoUsuario());
            datosUsuario.setNombreCompleto(_usuarioSeleccionadoModificarRoles.getNombreCompletoUsuario());
            datosUsuario.setCargo(_usuarioSeleccionadoModificarRoles.getCargoUsuario());
            datosUsuario.setCorreo(_usuarioSeleccionadoModificarRoles.getCorreoUsuario());

            _listaDatosNuevoUsuario.add(datosUsuario);
            List<RolDTO> roles = _iUsuarioRolUnidadLocal.findRolesByIds(_usuarioSeleccionadoModificarRoles.getRolesId());

            if (!roles.isEmpty()) {
                _rolesSeleccionados = roles.toArray(new RolDTO[roles.size()]);
            }

            _origenUnidad = "U".equals(_tipoUsuarioAdministrar);
            cargarRoles();
            _modifica = true;

            navigationFaces.redirectFacesRedirectCuAd01_2AdministracionUsuarioPerfilesModificarRol();

        } catch (IOException ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);

        } catch (JpaDinaeException ex) {

            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     */
    private void cargarRoles() {
        try {
            _listaRolesDTO = new ArrayList<RolDTO>();

            if (!_origenUnidad) {
                _listaRolesDTO = _iUsuarioRolUnidadLocal.findRolesByIds(IConstantesRole.ROLES_VICIN);
            } else {

                _listaRolesDTO = _iUsuarioRolUnidadLocal.findRolesByIds(IConstantesRole.ROLES_UNIDAD);
                _listaRolesDTO.addAll(_iUsuarioRolUnidadLocal.findRolesByIds(IConstantesRole.ROLES_SECRETARIA_ACADEMICA));
                _listaRolesDTO.addAll(_iUsuarioRolUnidadLocal.findRolesByIds(IConstantesRole.ROLES_VICERRECTORIA_EDUCACION_CONTINUA));
            }

            _listaRolesDTOModel = new ListGenericDataModel<RolDTO>(_listaRolesDTO);

        } catch (JpaDinaeException ex) {
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     */
    public void noSeleccionUsuarioRoles(UnselectEvent event) {

        inicializarCampos();
    }

    /**
     *
     * @return
     */
    public String buscarUsuario() {
        try {

            _listaDatosNuevoUsuario = new ArrayList<InvestigadorProyectoDTO>();

            if (_identificacion != null && !"".equals(_identificacion.trim())) {

                VistaFuncionario vistaFuncionario = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(_identificacion);
                InvestigadorProyectoDTO investiga;

                if (vistaFuncionario != null) {

                    if (!_crearUsuario) {

                        if (vistaFuncionario.getCodigoUnidadLaboral() != null) {
                            if (_usuarioModificarRolAdmin.getIdUnidadPolicial().compareTo(Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral())) != 0) {
                                adicionaMensajeError("El N° de identificación ingresado no pertenece a la unidad policial");
                                _inputIdentificacion.setValid(false);
                                return null;
                            }
                        } else {
                            adicionaMensajeError("El N° de identificación ingresado no poseé unidad policial");
                            _inputIdentificacion.setValid(false);
                            return null;
                        }
                    } else {

                        List<RolUsuarioDTO> usuarioRoles = _iUsuarioRol.getAllRolUsuarioDTOByIdentificacion(_identificacion);
                        if (!usuarioRoles.isEmpty()) {
                            adicionaMensajeAdvertencia("NOTA: Este usuario tiene asignados roles de unidad.");
                        }

                        UnidadPolicialDTO usuarioUnidad = _iUsuarioUnidad.findAllPorIdentificacion(_identificacion);
                        if (usuarioUnidad != null) {

                            String idUnidadPolicialStr = _unidadPolicialValue.split("-")[0];

                            if (idUnidadPolicialStr != null && idUnidadPolicialStr.trim().length() > 0 && !idUnidadPolicialStr.equals("-1")) {

                                if (usuarioUnidad.getIdUnidadPolicial().compareTo(Long.valueOf(idUnidadPolicialStr)) != 0) {
                                    adicionaMensajeError("El N° de identificación ingresado no pertenece a la unidad policial");
                                    _inputIdentificacion.setValid(false);
                                    return null;
                                }
                            }
                        }
                    }

                    investiga = new InvestigadorProyectoDTO();
                    investiga.setGrado(vistaFuncionario.getGrado());
                    investiga.setNombreCompleto(vistaFuncionario.getNombreCompleto());
                    investiga.setCorreo(vistaFuncionario.getCorreo());
                    investiga.setIdentificacion(_identificacion);
                    investiga.setCargo(vistaFuncionario.getCargo());
                    investiga.setTelefono(vistaFuncionario.getTelefono());
                    investiga.setIdUnidadPolicial(vistaFuncionario.getCodigoUnidadLaboral() == null ? null : Long.valueOf(vistaFuncionario.getCodigoUnidadLaboral()));
                    investiga.setIdentificacion(vistaFuncionario.getIdentificacion());

                    _listaDatosNuevoUsuario = new ArrayList<InvestigadorProyectoDTO>();
                    _listaDatosNuevoUsuario.add(investiga);

                    return null;
                }

                adicionaMensajeError(keyPropertiesFactory.value("cu_pr_3_lbl_identificacion_no_encontrada"));
                _inputIdentificacion.setValid(false);

            } else {
                adicionaMensajeError("El N° de identificación esta vacío");
                _inputIdentificacion.setValid(false);
            }
        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuPr12EvaluacionProyectosInvestigacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @throws JpaDinaeException
     */
    public void validarCampos() throws JpaDinaeException {

        if (_identificacion == null || "".equals(_identificacion.trim())) {
            _inputIdentificacion.setValid(false);
            throw new JpaDinaeException("El campo 'N° de identificación' es obligatorio");
        }
        _inputIdentificacion.setValid(true);

        if (_listaDatosNuevoUsuario == null || _listaDatosNuevoUsuario.isEmpty()) {
            throw new JpaDinaeException("Los datos del usuario a asignar el rol, son requeridos");
        }

    }

    /**
     *
     * @return
     */
    public String guardarCambioRolUsuario() {

        try {
            validarCampos();

            UsuarioRol usuarioRol = _iUsuarioRol.findByUsuarioYRol(_usuarioModificarRolAdmin.getIdRol(), _usuarioModificarRolAdmin.getIdentificacionUsuario());

            if (usuarioRol != null) {

                _iUsuarioRol.deshabilitarUsuarioRol(usuarioRol.getIdUsuarioRol());

                UsuarioRol nuevoUsuarioRol = new UsuarioRol();
                nuevoUsuarioRol.setIdUsuarioRol(null);
                nuevoUsuarioRol.setIdentificadorUsuario(_identificacion);
                nuevoUsuarioRol.setRol(new Rol(_usuarioModificarRolAdmin.getIdRol()));
                nuevoUsuarioRol.setFechaInicio(new Date());
                nuevoUsuarioRol.setActivo("S");

                _iUsuarioRol.guardarUsuarioRol(nuevoUsuarioRol);

                Long[] rolesAsociados = new Long[0];

                if (usuarioRol.getRol().getIdRol().compareTo(IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD) == 0) {
                    rolesAsociados = IConstantesRole.ROLES_DEPENDE_JEFE_GRUPO_INVESTIGACION_UNIDAD;
                } else if (usuarioRol.getRol().getIdRol().compareTo(IConstantesRole.ADMINISTRADOR_DE_INVESTIGACION_SIGAC_EN_LA_UNIDAD_POLICIAL) == 0) {
                    rolesAsociados = IConstantesRole.ROLES_DEPENDE_JEFE_INVESTIGACION_SIGAC_UNIDAD;
                }

                for (Long idRol : rolesAsociados) {

                    UsuarioRol usuarioRolAsociado = _iUsuarioRol.findByUsuarioYRol(idRol, _usuarioModificarRolAdmin.getIdentificacionUsuario());

                    if (usuarioRolAsociado != null) {

                        _iUsuarioRol.deshabilitarUsuarioRol(usuarioRolAsociado.getIdUsuarioRol());

                        UsuarioRol rolAsignado = _iUsuarioRol.findByUsuarioYRol(idRol, _identificacion);

                        if (rolAsignado == null) {

                            UsuarioRol nuevoUsuarioRolAsociado = new UsuarioRol();
                            nuevoUsuarioRolAsociado.setIdUsuarioRol(null);
                            nuevoUsuarioRolAsociado.setIdentificadorUsuario(_identificacion);
                            nuevoUsuarioRolAsociado.setRol(new Rol(idRol));
                            nuevoUsuarioRolAsociado.setFechaInicio(new Date());
                            nuevoUsuarioRolAsociado.setActivo("S");

                            _iUsuarioRol.guardarUsuarioRol(nuevoUsuarioRolAsociado);
                        }
                    }
                }

                _disableCambiarUsuario = true;

                adicionaMensajeInformativo(keyPropertiesFactory.value("cu_iv_3_lbl_informacioon_almacenada_ok"));
            }

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(ex.getMessage());
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @param e
     */
    public void cargarDatosUsuarioModificar(SelectEvent e) {
        try {
            if (_usuarioModificarRolAdmin == null) {
                return;
            }

            _nombreRolModificar = _usuarioModificarRolAdmin.getNombreRol();
            _nombreUnidad = _usuarioModificarRolAdmin.getNombreUnidad();

            _listaDatosUsuarioSeleccionado = new ArrayList<InvestigadorProyectoDTO>();

            InvestigadorProyectoDTO datosUsuario = new InvestigadorProyectoDTO();
            datosUsuario.setGrado(_usuarioModificarRolAdmin.getGradoUsuario());
            datosUsuario.setNombreCompleto(_usuarioModificarRolAdmin.getNombreCompletoUsuario());
            datosUsuario.setCargo(_usuarioModificarRolAdmin.getCargoUsuario());
            datosUsuario.setCorreo(_usuarioModificarRolAdmin.getCorreoUsuario());
            datosUsuario.setIdentificacion(_usuarioModificarRolAdmin.getIdentificacionUsuario());

            _listaDatosUsuarioSeleccionado.add(datosUsuario);

            _inputIdentificacion = new InputText();
            _identificacion = null;

            _listaDatosNuevoUsuario = new ArrayList<InvestigadorProyectoDTO>();

            navigationFaces.redirectFacesRedirectCuAd01_1AdministracionUsuarioPerfilesModificarRol();

            _listaUsuariosRolesUsuarioUnidadPolicial = _iUsuarioRolUnidadLocal.findAllPorIdentificacion(_usuarioModificarRolAdmin.getIdentificacionUsuario());

        } catch (Exception ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param event
     */
    public void noSeleccionUsuarioRol(UnselectEvent event) {

        inicializarCampos();
    }

    /**
     *
     */
    private void inicializarCampos() {

        idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad = null;
        unidadPolicialAdministraUsuarioUnidad = null;
        vistaFuncionarioAdministraUsuarioUnidad = null;
        identificacionBusquedaAdministraUsuarioUnidad = null;
        idTabSeleccionado = 0;
        _idRolSeleccionadoBusqueda = null;
        identificacionBusqueda = null;
        _unidadPolicialValue = "-1";
        _listaUsuariosAdministativosUnidad = new ArrayList<UsuarioRolUnidadDTO>();
        _listaUsuariosRoles = new ArrayList<UsuarioRolUnidadDTO>();
        _listaDatosUsuarioSeleccionado = new ArrayList<InvestigadorProyectoDTO>();
        _listaUsuariosAdministativosUnidadModel = null;
        _listaUsuariosBusquedaRolesModel = null;
        _listaUsuariosRolesUsuarioUnidadPolicial = null;
        _listaUsuariosUnidadEducoModel = null;
        _nombreRolModificar = null;
        _nombreUnidad = null;
        _usuarioModificarRolAdmin = null;
        _disableCambiarUsuario = false;
        _usuarioSeleccionadoModificarRoles = null;
        identificacionjefeUnidadBuscar = null;
        vistaFuncionarioSeleccionado = null;
        nombreUnidadPolicialSeleccionado = null;
        idUnidadPolicialSeleccionado = null;
        mensajePopupJefeUnidad = null;
        mostrarAgregarJefeUnidadPolicial = false;
        mostrarAgregarJefeGrupoInvestigacion = false;

    }

    /**
     *
     * @throws Exception
     */
    private void consultarDatosPerfilSeleccionado() throws Exception {

        //Usuarios de VICIN
        if ("V".equals(_tipoUsuarioAdministrar)) {

            _listaUsuariosRoles = new ArrayList<UsuarioRolUnidadDTO>();
            cargarListaUsuariosRoles(null, (Long) null);

        } //Usuarios de unidad
        else if ("U".equals(_tipoUsuarioAdministrar)) {

            //LAS UNIDADES SE CARGAN AL INGRESAR AL CU
            //cargarListadoUnidadesPolicialesActivas();
        } //Rol específico
        else if ("RE".equals(_tipoUsuarioAdministrar)) {

        }//Rol específico
        else if ("CC".equals(_tipoUsuarioAdministrar)) {

        }

    }

    /**
     *
     * @return
     */
    public String accionRadio() {

        try {

            inicializarCampos();

            consultarDatosPerfilSeleccionado();

            return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

        } catch (Exception ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public String actualizarUnidadPolicial() {

        try {

            _iUsuarioUnidad.actualizarUnidadPolicialUsuario(idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad, identificacionBusquedaAdministraUsuarioUnidad);

            idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad = null;
            buscarUsuarioPorIdentificacion();
            adicionaMensajeInformativo("La unidad policial fue actualizada correctamente");

            return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, e);

        }

        return null;
    }

    /**
     *
     * @return
     */
    public String nuevaBusquedaAdministrausuarioUnidad() {

        vistaFuncionarioAdministraUsuarioUnidad = null;
        identificacionBusquedaAdministraUsuarioUnidad = null;

        return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

    }

    /**
     *
     */
    public String buscarUsuarioPorIdentificacion() {
        try {

            vistaFuncionarioAdministraUsuarioUnidad = null;
            unidadPolicialAdministraUsuarioUnidad = null;
            if (identificacionBusquedaAdministraUsuarioUnidad == null || identificacionBusquedaAdministraUsuarioUnidad.trim().length() == 0) {

                adicionaMensajeError("Debe ingresar la identificación");
                return null;

            }

            vistaFuncionarioAdministraUsuarioUnidad = _iVistaFuncionario.getVistaFuncionarioPorIdentificacion(identificacionBusquedaAdministraUsuarioUnidad);
            if (vistaFuncionarioAdministraUsuarioUnidad == null) {

                adicionaMensajeError("No se encontró persona con esta identificación");
                return null;

            }
            //VERIFICAMOS SI EL USUARIO TIENE UNIDAD
            if (vistaFuncionarioAdministraUsuarioUnidad.getCodigoUnidadLaboral() != null) {

                unidadPolicialAdministraUsuarioUnidad = _iUnidadPolicialLocal.obtenerUnidadPolicialPorId(Long.valueOf(vistaFuncionarioAdministraUsuarioUnidad.getCodigoUnidadLaboral().trim()));

            }

            return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

        } catch (Exception ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     */
    public void buscarRolBusqueda() {
        try {

            if (_idRolSeleccionadoBusqueda == null || _idRolSeleccionadoBusqueda == -1) {

                return;

            }

            _listaUsuariosAdministativosUnidad = _iUsuarioRolUnidadLocal.findAllPorRol(Arrays.asList(_idRolSeleccionadoBusqueda));
            if (_listaUsuariosAdministativosUnidad.isEmpty()) {

                adicionaMensajeError("La búsqueda no encontró resultados");

            }

            _listaUsuariosBusquedaRolesModel = new ListGenericDataModel<UsuarioRolUnidadDTO>(_listaUsuariosAdministativosUnidad);

        } catch (Exception ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public String buscarIdentificacionBusqueda() {
        try {

            if (identificacionBusqueda == null || identificacionBusqueda.trim().length() == 0) {

                adicionaMensajeError("Debe ingresar la identificación");
                return null;

            }

            _listaUsuariosAdministativosUnidad = _iUsuarioRolUnidadLocal.findAllPorIdentificacion(identificacionBusqueda);
            if (_listaUsuariosAdministativosUnidad.isEmpty()) {

                adicionaMensajeError("La búsqueda no encontró resultados");

            }

            _listaUsuariosBusquedaRolesModel = new ListGenericDataModel<UsuarioRolUnidadDTO>(_listaUsuariosAdministativosUnidad);

            return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

        } catch (Exception ex) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     */
    public void cargarRolesTipoUnidadPolicial() {
        try {

            _listaUsuariosAdministativosUnidad = new ArrayList<UsuarioRolUnidadDTO>();

            if (_tipoUsuarioAdministrar != null && "U".equals(_tipoUsuarioAdministrar)) {

                if (_unidadPolicialValue != null && !"-1".equals(_unidadPolicialValue)) {

                    String idUnidadPolicialStr = _unidadPolicialValue.split("-")[0];
                    Long idUnidad = Long.valueOf(idUnidadPolicialStr);
                    String idTipoUnidadPolicial = _unidadPolicialValue.split("-")[1];
                    idUnidadPolicialSeleccionado = Long.valueOf(idUnidadPolicialStr);
                    nombreUnidadPolicialSeleccionado = _unidadPolicialValue.split("-")[2].concat(" - ").concat(_unidadPolicialValue.split("-")[3]);

                    mostrarAgregarJefeUnidadPolicial = false;
                    mostrarAgregarJefeGrupoInvestigacion = false;

                    Long[] roles = new Long[2];
                    roles[0] = IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL;
                    Long[] noRoles = new Long[3];
                    noRoles[0] = IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL;
                    noRoles[1] = IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD;
                    noRoles[2] = IConstantesRole.PARAMETRIZADOR_DESARROLLO_PAE;

                    // Si la unidad es de tipo escuela
                    if (IConstantes.TIPO_UNIDAD_POLICIAL_ESCUELAS.compareTo(Long.valueOf(idTipoUnidadPolicial)) == 0) {

                        mostrarAgregarJefeUnidadPolicial = true;
                        mostrarAgregarJefeGrupoInvestigacion = true;

                        roles[1] = IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD;
                        _listaUsuariosAdministativosUnidad = _iUsuarioRolUnidadLocal.findAllUnidadRoles(false, idUnidad, roles);
                        for (UsuarioRolUnidadDTO unUsuarioRolUnidadDTO : _listaUsuariosAdministativosUnidad) {

                            if (IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL.equals(unUsuarioRolUnidadDTO.getIdRol())) {
                                mostrarAgregarJefeUnidadPolicial = false;
                            }

                            if (IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD.equals(unUsuarioRolUnidadDTO.getIdRol())) {
                                mostrarAgregarJefeGrupoInvestigacion = false;
                            }

                        }

                    } else if (IConstantes.TIPO_UNIDAD_POLICIAL_OTROS.compareTo(Long.valueOf(idTipoUnidadPolicial)) == 0) {

                        roles[1] = IConstantesRole.ADMINISTRADOR_DE_INVESTIGACION_SIGAC_EN_LA_UNIDAD_POLICIAL;
                        _listaUsuariosAdministativosUnidad = _iUsuarioRolUnidadLocal.findAllUnidadRoles(true, idUnidad, roles);
                    }

                    _listaUsuariosRoles = new ArrayList<UsuarioRolUnidadDTO>();
                    _listaUsuariosRolesEduco = new ArrayList<UsuarioRolUnidadDTO>();
                    cargarListaUsuariosRoles(idUnidad, noRoles);

                    _listaUsuariosAdministativosUnidadModel = new ListGenericDataModel<UsuarioRolUnidadDTO>(_listaUsuariosAdministativosUnidad);
                }
            }

        } catch (JpaDinaeException ex) {
            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param idUnidad
     * @param roles
     * @throws JpaDinaeException
     */
    private void cargarListaUsuariosRoles(Long idUnidad, Long... roles) throws JpaDinaeException {

        List<UsuarioRolUnidadDTO> listaUsuarioRolUnidad;
        List<UsuarioRolUnidadDTO> listaUsuarioRolUnidadEduco = new ArrayList<UsuarioRolUnidadDTO>();

        if (idUnidad != null && (roles != null && roles.length > 0)) {
            listaUsuarioRolUnidad = _iUsuarioRolUnidadLocal.findAllUnidadNoRolAdmin(idUnidad, roles);
            listaUsuarioRolUnidadEduco = _iUsuarioRolUnidadLocal.findAllUnidadRoles(false, idUnidad, IConstantesRole.PARAMETRIZADOR_DESARROLLO_PAE);
        } else {
            listaUsuarioRolUnidad = _iUsuarioRolUnidadLocal.findAllVicin();
        }

        Map<String, List<UsuarioRolUnidadDTO>> mapaUsuarioRol = new HashMap<String, List<UsuarioRolUnidadDTO>>();
        Map<String, List<UsuarioRolUnidadDTO>> mapaUsuarioRolEduco = new HashMap<String, List<UsuarioRolUnidadDTO>>();

        Long[] rolesAdmin = {IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL, IConstantesRole.JEFE_DEL_GRUPO_DE_INVESTIGACION_EN_LA_UNIDAD};

        if (!listaUsuarioRolUnidad.isEmpty()) {

            for (UsuarioRolUnidadDTO item : listaUsuarioRolUnidad) {

                // Consultamos si el usuario tiene o no roles administrativos
                List<UsuarioRol> usuarioRolesAdmin = _iUsuarioRol.findByUsuarioYAdminRoles(item.getIdentificacionUsuario(), rolesAdmin);

                // Si tiene roles administrativos, no lo tenemos en cuenta.
                if (usuarioRolesAdmin != null && !usuarioRolesAdmin.isEmpty()) {
                    continue;
                }

                if (mapaUsuarioRol.containsKey(item.getIdentificacionUsuario())) {
                    mapaUsuarioRol.get(item.getIdentificacionUsuario()).add(item);
                } else {
                    List<UsuarioRolUnidadDTO> textoRoles = new ArrayList<UsuarioRolUnidadDTO>();
                    textoRoles.add(item);
                    mapaUsuarioRol.put(item.getIdentificacionUsuario(), textoRoles);
                }
            }

            Set<String> keySet = mapaUsuarioRol.keySet();
            for (String key : keySet) {

                List<UsuarioRolUnidadDTO> listaUsuarioRolesUnidad = mapaUsuarioRol.get(key);
                String rolesTexto = "";
                List<Long> rolesIds = new ArrayList<Long>();

                int cont = 0;
                int tam = listaUsuarioRolesUnidad.size() - 1;

                String grado = null;
                String nombre = null;
                String cargo = null;
                String correo = null;

                Collections.sort(listaUsuarioRolesUnidad);
                for (UsuarioRolUnidadDTO unUsuarioRolUnidadDTO : listaUsuarioRolesUnidad) {

                    if (cont < tam) {
                        rolesTexto += "*" + unUsuarioRolUnidadDTO.getNombreRol() + "<br />";
                    } else {
                        rolesTexto += "*" + unUsuarioRolUnidadDTO.getNombreRol();

                        grado = unUsuarioRolUnidadDTO.getGradoUsuario();
                        nombre = unUsuarioRolUnidadDTO.getNombreCompletoUsuario();
                        cargo = unUsuarioRolUnidadDTO.getCargoUsuario();
                        correo = unUsuarioRolUnidadDTO.getCorreoUsuario();
                    }

                    rolesIds.add(unUsuarioRolUnidadDTO.getIdRol());
                    cont++;
                }

                UsuarioRolUnidadDTO usuarioRoles = new UsuarioRolUnidadDTO();
                usuarioRoles.setGradoUsuario(grado);
                usuarioRoles.setNombreCompletoUsuario(nombre);
                usuarioRoles.setRolesTexto(rolesTexto);
                usuarioRoles.setRolesId(rolesIds.toArray(new Long[rolesIds.size()]));
                usuarioRoles.setIdentificacionUsuario(key);
                usuarioRoles.setCargoUsuario(cargo);
                usuarioRoles.setCorreoUsuario(correo);

                _listaUsuariosRoles.add(usuarioRoles);
            }

            _listaUsuariosRolesModel = new ListGenericDataModel<UsuarioRolUnidadDTO>(_listaUsuariosRoles);

        }
        
        
        else{
            _listaUsuariosRolesModel=null;
        }

        if (!listaUsuarioRolUnidadEduco.isEmpty()) {

            for (UsuarioRolUnidadDTO item : listaUsuarioRolUnidadEduco) {

                // Consultamos si el usuario tiene o no roles administrativos
                List<UsuarioRol> usuarioRolesAdmin = _iUsuarioRol.findByUsuarioYAdminRoles(item.getIdentificacionUsuario(), rolesAdmin);

                // Si tiene roles administrativos, no lo tenemos en cuenta.
                if (usuarioRolesAdmin != null && !usuarioRolesAdmin.isEmpty()) {
                    continue;
                }

                if (mapaUsuarioRolEduco.containsKey(item.getIdentificacionUsuario())) {
                    mapaUsuarioRolEduco.get(item.getIdentificacionUsuario()).add(item);
                } else {
                    List<UsuarioRolUnidadDTO> textoRoles = new ArrayList<UsuarioRolUnidadDTO>();
                    textoRoles.add(item);
                    mapaUsuarioRolEduco.put(item.getIdentificacionUsuario(), textoRoles);
                }
            }

            Set<String> keySet = mapaUsuarioRolEduco.keySet();
            for (String key : keySet) {

                List<UsuarioRolUnidadDTO> listaUsuarioRolesUnidadEduco = mapaUsuarioRolEduco.get(key);
                String rolesTexto = "";
                List<Long> rolesIds = new ArrayList<Long>();

                int cont = 0;
                int tam = listaUsuarioRolesUnidadEduco.size() - 1;

                String grado = null;
                String nombre = null;
                String cargo = null;
                String correo = null;

                Collections.sort(listaUsuarioRolesUnidadEduco);
                for (UsuarioRolUnidadDTO unUsuarioRolUnidadDTO : listaUsuarioRolesUnidadEduco) {

                    if (cont < tam) {
                        rolesTexto += "*" + unUsuarioRolUnidadDTO.getNombreRol() + "<br />";
                    } else {
                        rolesTexto += "*" + unUsuarioRolUnidadDTO.getNombreRol();

                        grado = unUsuarioRolUnidadDTO.getGradoUsuario();
                        nombre = unUsuarioRolUnidadDTO.getNombreCompletoUsuario();
                        cargo = unUsuarioRolUnidadDTO.getCargoUsuario();
                        correo = unUsuarioRolUnidadDTO.getCorreoUsuario();
                    }

                    rolesIds.add(unUsuarioRolUnidadDTO.getIdRol());
                    cont++;
                }

                UsuarioRolUnidadDTO usuarioRoles = new UsuarioRolUnidadDTO();
                usuarioRoles.setGradoUsuario(grado);
                usuarioRoles.setNombreCompletoUsuario(nombre);
                usuarioRoles.setRolesTexto(rolesTexto);
                usuarioRoles.setRolesId(rolesIds.toArray(new Long[rolesIds.size()]));
                usuarioRoles.setIdentificacionUsuario(key);
                usuarioRoles.setCargoUsuario(cargo);
                usuarioRoles.setCorreoUsuario(correo);

                _listaUsuariosRolesEduco.add(usuarioRoles);
            }
            _listaUsuariosUnidadEducoModel = new ListGenericDataModel<UsuarioRolUnidadDTO>(_listaUsuariosRolesEduco);
        }
        else{
            _listaUsuariosUnidadEducoModel=null;
        }

    }

    /**
     *
     * @throws JpaDinaeException
     */
    private void cargarListadoUnidadesPolicialesActivas() throws JpaDinaeException {

        _listaUnidadesPolicialesActivas = _iUnidadPolicialLocal.getAllUnidadesPolicialesActivasDTO();

    }

    public List<UnidadPolicialDTO> getListaUnidadesPolicialesActivas() {
        return _listaUnidadesPolicialesActivas;
    }

    public void setListaUnidadesPolicialesActivas(List<UnidadPolicialDTO> _listaUnidadesPolicialesActivas) {
        this._listaUnidadesPolicialesActivas = _listaUnidadesPolicialesActivas;
    }

    public String getTipoUsuarioAdministrar() {
        return _tipoUsuarioAdministrar;
    }

    public void setTipoUsuarioAdministrar(String _tipoUsuarioAdministrar) {
        this._tipoUsuarioAdministrar = _tipoUsuarioAdministrar;
    }

    public String getUnidadPolicialValue() {
        return _unidadPolicialValue;
    }

    public void setUnidadPolicialValue(String _unidadPolicialValue) {
        this._unidadPolicialValue = _unidadPolicialValue;
    }

    public List<UsuarioRolUnidadDTO> getListaUsuariosAdministativosUnidad() {
        return _listaUsuariosAdministativosUnidad;
    }

    public void setListaUsuariosAdministativosUnidad(List<UsuarioRolUnidadDTO> _listaUsuariosAdministativosUnidad) {
        this._listaUsuariosAdministativosUnidad = _listaUsuariosAdministativosUnidad;
    }

    public List<UsuarioRolUnidadDTO> getListaUsuariosRoles() {
        return _listaUsuariosRoles;
    }

    public void setListaUsuariosRoles(List<UsuarioRolUnidadDTO> _listaUsuariosRoles) {
        this._listaUsuariosRoles = _listaUsuariosRoles;
    }

    public ListGenericDataModel<UsuarioRolUnidadDTO> getListaUsuariosAdministativosUnidadModel() {
        return _listaUsuariosAdministativosUnidadModel;
    }

    public void setListaUsuariosAdministativosUnidadModel(ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosAdministativosUnidadModel) {
        this._listaUsuariosAdministativosUnidadModel = _listaUsuariosAdministativosUnidadModel;
    }

    public ListGenericDataModel<UsuarioRolUnidadDTO> getListaUsuariosRolesModel() {
        return _listaUsuariosRolesModel;
    }

    public void setListaUsuariosRolesModel(ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosRolesModel) {
        this._listaUsuariosRolesModel = _listaUsuariosRolesModel;
    }

    public List<InvestigadorProyectoDTO> getListaDatosUsuarioSeleccionado() {
        return _listaDatosUsuarioSeleccionado;
    }

    public void setListaDatosUsuarioSeleccionado(List<InvestigadorProyectoDTO> _listaDatosUsuarioSeleccionado) {
        this._listaDatosUsuarioSeleccionado = _listaDatosUsuarioSeleccionado;
    }

    public UsuarioRolUnidadDTO getUsuarioModificarRolAdmin() {
        return _usuarioModificarRolAdmin;
    }

    public void setUsuarioModificarRolAdmin(UsuarioRolUnidadDTO _usuarioModificarRolAdmin) {
        this._usuarioModificarRolAdmin = _usuarioModificarRolAdmin;
    }

    public UsuarioRolUnidadDTO getUsuarioModificarRoles() {
        return _usuarioModificarRoles;
    }

    public void setUsuarioModificarRoles(UsuarioRolUnidadDTO _usuarioModificarRoles) {
        this._usuarioModificarRoles = _usuarioModificarRoles;
    }

    public String getNombreUnidad() {
        return _nombreUnidad;
    }

    public void setNombreUnidad(String _nombreUnidad) {
        this._nombreUnidad = _nombreUnidad;
    }

    public String getNombreRolModificar() {
        return _nombreRolModificar;
    }

    public void setNombreRolModificar(String _nombreRolModificar) {
        this._nombreRolModificar = _nombreRolModificar;
    }

    public List<InvestigadorProyectoDTO> getListaDatosNuevoUsuario() {
        return _listaDatosNuevoUsuario;
    }

    public void setListaDatosNuevoUsuario(List<InvestigadorProyectoDTO> _listaDatosNuevoUsuario) {
        this._listaDatosNuevoUsuario = _listaDatosNuevoUsuario;
    }

    public String getIdentificacion() {
        return _identificacion;
    }

    public void setIdentificacion(String _identificacion) {
        this._identificacion = _identificacion;
    }

    public InputText getInputIdentificacion() {
        return _inputIdentificacion;
    }

    public void setInputIdentificacion(InputText _inputIdentificacion) {
        this._inputIdentificacion = _inputIdentificacion;
    }

    public boolean isDisableCambiarUsuario() {
        return _disableCambiarUsuario;
    }

    public void setDisableCambiarUsuario(boolean _disableCambiarUsuario) {
        this._disableCambiarUsuario = _disableCambiarUsuario;
    }

    public RolDTO[] getRolesSeleccionados() {
        return _rolesSeleccionados;
    }

    public void setRolesSeleccionados(RolDTO[] _rolesSeleccionados) {
        this._rolesSeleccionados = _rolesSeleccionados;
    }

    public List<RolDTO> getListaRolesDTO() {
        return _listaRolesDTO;
    }

    public void setListaRolesDTO(List<RolDTO> _listaRolesDTO) {
        this._listaRolesDTO = _listaRolesDTO;
    }

    public ListGenericDataModel<RolDTO> getListaRolesDTOModel() {
        return _listaRolesDTOModel;
    }

    public void setListaRolesDTOModel(ListGenericDataModel<RolDTO> _listaRolesDTOModel) {
        this._listaRolesDTOModel = _listaRolesDTOModel;
    }

    public boolean isOrigenUnidad() {
        return _origenUnidad;
    }

    public void setOrigenUnidad(boolean _origenUnidad) {
        this._origenUnidad = _origenUnidad;
    }

    public boolean isModifica() {
        return _modifica;
    }

    public void setModifica(boolean _modifica) {
        this._modifica = _modifica;
    }

    public UsuarioRolUnidadDTO getUsuarioSeleccionadoModificarRoles() {
        return _usuarioSeleccionadoModificarRoles;
    }

    public void setUsuarioSeleccionadoModificarRoles(UsuarioRolUnidadDTO _usuarioSeleccionadoModificarRoles) {
        this._usuarioSeleccionadoModificarRoles = _usuarioSeleccionadoModificarRoles;
    }

    /**
     *
     * @return
     */
    public String regresar() {

        try {

            if ("U".equals(_tipoUsuarioAdministrar)) {

                cargarRolesTipoUnidadPolicial();

            } else if ("RE".equals(_tipoUsuarioAdministrar)) {

                buscarRolBusqueda();

            } else if ("V".equals(_tipoUsuarioAdministrar)) {

                _listaUsuariosRoles = new ArrayList<UsuarioRolUnidadDTO>();
                cargarListaUsuariosRoles(null, (Long) null);

            }

            return navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect();

        } catch (Exception e) {

            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public boolean esRolEspecifico() {

        return "RE".equals(_tipoUsuarioAdministrar);

    }

    public List<SelectItem> getRolesBusqueda() {
        return _rolesBusqueda;
    }

    public void setRolesBusqueda(List<SelectItem> _rolesBusqueda) {
        this._rolesBusqueda = _rolesBusqueda;
    }

    public Long getIdRolSeleccionadoBusqueda() {
        return _idRolSeleccionadoBusqueda;
    }

    public void setIdRolSeleccionadoBusqueda(Long _idRolSeleccionadoBusqueda) {
        this._idRolSeleccionadoBusqueda = _idRolSeleccionadoBusqueda;
    }

    public ListGenericDataModel<UsuarioRolUnidadDTO> getListaUsuariosBusquedaRolesModel() {
        return _listaUsuariosBusquedaRolesModel;
    }

    public void setListaUsuariosBusquedaRolesModel(ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosBusquedaRolesModel) {
        this._listaUsuariosBusquedaRolesModel = _listaUsuariosBusquedaRolesModel;
    }

    public boolean isEsVacioUnidadPolicialValueSplit() {

        return _unidadPolicialValue == null || _unidadPolicialValue.trim().length() == 0;

    }

    public String getUnidadPolicialValueSplit() {

        if (_unidadPolicialValue == null) {
            return "";
        }

        try {

            return _unidadPolicialValue.split("-")[2].concat(" - ").concat(_unidadPolicialValue.split("-")[3]);

        } catch (Exception e) {
            return "";
        }
    }

    public String getIdentificacionBusqueda() {
        return identificacionBusqueda;
    }

    public void setIdentificacionBusqueda(String identificacionBusqueda) {
        this.identificacionBusqueda = identificacionBusqueda;
    }

    public List<UsuarioRolUnidadDTO> getListaUsuariosRolesUsuarioUnidadPolicial() {
        return _listaUsuariosRolesUsuarioUnidadPolicial;
    }

    public void setListaUsuariosRolesUsuarioUnidadPolicial(List<UsuarioRolUnidadDTO> _listaUsuariosRolesUsuarioUnidadPolicial) {
        this._listaUsuariosRolesUsuarioUnidadPolicial = _listaUsuariosRolesUsuarioUnidadPolicial;
    }

    public int getIdTabSeleccionado() {
        return idTabSeleccionado;
    }

    public void setIdTabSeleccionado(int idTabSeleccionado) {
        this.idTabSeleccionado = idTabSeleccionado;
    }

    public String getIdentificacionBusquedaAdministraUsuarioUnidad() {
        return identificacionBusquedaAdministraUsuarioUnidad;
    }

    public void setIdentificacionBusquedaAdministraUsuarioUnidad(String identificacionBusquedaAdministraUsuarioUnidad) {
        this.identificacionBusquedaAdministraUsuarioUnidad = identificacionBusquedaAdministraUsuarioUnidad;
    }

    public VistaFuncionario getVistaFuncionarioAdministraUsuarioUnidad() {
        return vistaFuncionarioAdministraUsuarioUnidad;
    }

    public void setVistaFuncionarioAdministraUsuarioUnidad(VistaFuncionario vistaFuncionarioAdministraUsuarioUnidad) {
        this.vistaFuncionarioAdministraUsuarioUnidad = vistaFuncionarioAdministraUsuarioUnidad;
    }

    public UnidadPolicial getUnidadPolicialAdministraUsuarioUnidad() {
        return unidadPolicialAdministraUsuarioUnidad;
    }

    public void setUnidadPolicialAdministraUsuarioUnidad(UnidadPolicial unidadPolicialAdministraUsuarioUnidad) {
        this.unidadPolicialAdministraUsuarioUnidad = unidadPolicialAdministraUsuarioUnidad;
    }

    public Long getIdNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad() {
        return idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad;
    }

    public void setIdNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad(Long idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad) {
        this.idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad = idNuevaUnidadPolicialSeleccionadaAdministraUsuarioUnidad;
    }

    public String getIdentificacionjefeUnidadBuscar() {
        return identificacionjefeUnidadBuscar;
    }

    public void setIdentificacionjefeUnidadBuscar(String identificacionjefeUnidadBuscar) {
        this.identificacionjefeUnidadBuscar = identificacionjefeUnidadBuscar;
    }

    /**
     *
     * @param actionEvent
     */
    public void adicionarNuevoJefeGrupoInvestigacion(ActionEvent actionEvent) {

        try {

            _iUsuarioRol.guardarUsuarioRolGrupoInvestigacion(vistaFuncionarioSeleccionado.getIdentificacion());

            cargarRolesTipoUnidadPolicial();

            adicionaMensajeInformativo("Roles asignados correctamente.");

            navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect());

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    /**
     *
     * @param actionEvent
     */
    public void adicionarNuevojefeUnidad(ActionEvent actionEvent) {

        try {

            UsuarioRol nuevoUsuarioRol = new UsuarioRol();
            nuevoUsuarioRol.setIdentificadorUsuario(vistaFuncionarioSeleccionado.getIdentificacion());
            nuevoUsuarioRol.setRol(new Rol(IConstantesRole.JEFE_DE_LA_UNIDAD_POLICIAL));
            nuevoUsuarioRol.setFechaInicio(new Date());
            nuevoUsuarioRol.setActivo("S");

            _iUsuarioRol.guardarUsuarioRol(nuevoUsuarioRol);

            cargarRolesTipoUnidadPolicial();

            adicionaMensajeInformativo("Roles asignados correctamente.");

            navigationFaces.redirectFacesCuGenerico(navigationFaces.redirectCuAd01AdministracionUsuarioPerfilesRedirect());

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    /**
     *
     */
    public void popupAdicionarJefeUnidad() {

        vistaFuncionarioSeleccionado = null;
        identificacionjefeUnidadBuscar = null;
        mensajePopupJefeUnidad = null;

    }

    /**
     *
     */
    public void popupAdicionarJefeGrupoInvestigacion() {

        vistaFuncionarioSeleccionado = null;
        identificacionjefeUnidadBuscar = null;
        mensajePopupJefeUnidad = null;

    }

    /**
     *
     */
    public void buscarJefeUnidad() {

        try {

            vistaFuncionarioSeleccionado = null;
            mensajePopupJefeUnidad = null;
            if (identificacionjefeUnidadBuscar == null) {

                mensajePopupJefeUnidad = "Ingrese un número de identificación";
                return;
            }

            vistaFuncionarioSeleccionado = iVistaFuncionarioLocal.getVistaFuncionarioPorIdentificacion(identificacionjefeUnidadBuscar);

            if (vistaFuncionarioSeleccionado == null) {

                mensajePopupJefeUnidad = "Identificación no encontrada";
                return;
            }
            if (vistaFuncionarioSeleccionado.getCodigoUnidadLaboral() == null
                    || !idUnidadPolicialSeleccionado.equals(Long.parseLong(vistaFuncionarioSeleccionado.getCodigoUnidadLaboral().trim()))) {

                mensajePopupJefeUnidad = "La persona seleccionada no pertenece a la unidad: ".concat(nombreUnidadPolicialSeleccionado);
                vistaFuncionarioSeleccionado = null;

            }

        } catch (Exception e) {

            adicionaMensajeError(keyPropertiesFactory.value("general_mensaje_error_exception"));
            Logger.getLogger(CuAd01AdmministrarUsuariosPerfilesFaces.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    public boolean isSeleccionoUnidadPolicialValue() {

        return _unidadPolicialValue != null && !_unidadPolicialValue.trim().equals("-1");

    }

    public boolean isSeleccionoVistaFuncionarioSeleccionadoValue() {

        return vistaFuncionarioSeleccionado != null && vistaFuncionarioSeleccionado.getIdVistaFuncionario() != null;

    }

    public VistaFuncionario getVistaFuncionarioSeleccionado() {
        return vistaFuncionarioSeleccionado;
    }

    public void setVistaFuncionarioSeleccionado(VistaFuncionario vistaFuncionarioSeleccionado) {
        this.vistaFuncionarioSeleccionado = vistaFuncionarioSeleccionado;
    }

    public String getNombreUnidadPolicialSeleccionado() {
        return nombreUnidadPolicialSeleccionado;
    }

    public void setNombreUnidadPolicialSeleccionado(String nombreUnidadPolicialSeleccionado) {
        this.nombreUnidadPolicialSeleccionado = nombreUnidadPolicialSeleccionado;
    }

    public Long getIdUnidadPolicialSeleccionado() {
        return idUnidadPolicialSeleccionado;
    }

    public void setIdUnidadPolicialSeleccionado(Long idUnidadPolicialSeleccionado) {
        this.idUnidadPolicialSeleccionado = idUnidadPolicialSeleccionado;
    }

    public String getMensajePopupJefeUnidad() {
        return mensajePopupJefeUnidad;
    }

    public void setMensajePopupJefeUnidad(String mensajePopupJefeUnidad) {
        this.mensajePopupJefeUnidad = mensajePopupJefeUnidad;
    }

    public boolean isMostrarAgregarJefeUnidadPolicial() {
        return mostrarAgregarJefeUnidadPolicial;
    }

    public void setMostrarAgregarJefeUnidadPolicial(boolean mostrarAgregarJefeUnidadPolicial) {
        this.mostrarAgregarJefeUnidadPolicial = mostrarAgregarJefeUnidadPolicial;
    }

    public boolean isMostrarAgregarJefeGrupoInvestigacion() {
        return mostrarAgregarJefeGrupoInvestigacion;
    }

    public void setMostrarAgregarJefeGrupoInvestigacion(boolean mostrarAgregarJefeGrupoInvestigacion) {
        this.mostrarAgregarJefeGrupoInvestigacion = mostrarAgregarJefeGrupoInvestigacion;
    }

    public ListGenericDataModel<UsuarioRolUnidadDTO> getListaUsuariosUnidadEducoModel() {
        return _listaUsuariosUnidadEducoModel;
    }

    public void setListaUsuariosUnidadEducoModel(ListGenericDataModel<UsuarioRolUnidadDTO> _listaUsuariosUnidadEducoModel) {
        this._listaUsuariosUnidadEducoModel = _listaUsuariosUnidadEducoModel;
    }

    public List<UsuarioRolUnidadDTO> getListaUsuariosRolesEduco() {
        return _listaUsuariosRolesEduco;
    }

    public void setListaUsuariosRolesEduco(List<UsuarioRolUnidadDTO> _listaUsuariosRolesEduco) {
        this._listaUsuariosRolesEduco = _listaUsuariosRolesEduco;
    }

}
