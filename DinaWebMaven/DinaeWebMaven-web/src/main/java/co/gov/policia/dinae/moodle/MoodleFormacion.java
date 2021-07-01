/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import co.gov.policia.dinae.moodle.cliente.CategoriaProvider;
import co.gov.policia.dinae.moodle.cliente.CursoProvider;
import co.gov.policia.dinae.moodle.cliente.UsuarioProvider;
import co.gov.policia.dinae.moodle.config.Parameters;
import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.moodle.dto.Categoria;
import co.gov.policia.dinae.moodle.dto.Curso;
import co.gov.policia.dinae.moodle.dto.Usuario;
import co.gov.policia.dinae.moodle.errorhandler.Error;
import co.gov.policia.dinae.siedu.modelo.SieduDatosEmpleadoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendHttpService;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ferney Controlador que se comunica con Moodle para cursos de
 * formación Crea y Actualiza Cursos Asigna y deasigna Alumnos y Docentes Crea
 * Usuarios.
 */
public class MoodleFormacion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 611041355832672691L;

    protected Server server;
    protected CategoriaProvider categoriaProvider;
    protected CursoProvider cursoProvider;
    protected UsuarioProvider usuarioProvider;
    protected Error error;
    protected List<Error> errores = new ArrayList<Error>();
    protected boolean conectado = false;
    protected String prefijoCurso;
    protected Curso curso;
    protected Usuario usuario;
    protected Categoria categoria;
    protected String sCursoSemilla;
    protected String sIdProgPensum;
    protected String sCategoria;
    protected String titulo;

    /**
     * Instacia la clase con los valores por defectos de conexion a Moodle
     */
    public MoodleFormacion(LogMoodleSendDataService serLog,LogMoodleSendHttpService serUri) {
        super();
        this.prefijoCurso = "F";
        this.server = new Server(serLog,serUri);
        this.titulo = "FORMACION";
        loadProvider();
    }

    /**
     * Instacia la clase con los valores por defectos de conexion a Moodle
     */
    public MoodleFormacion(LogMoodleSendDataService serLog,LogMoodleSendHttpService serUri, String sPrefijo) {
        super();
        this.prefijoCurso = sPrefijo;
        this.server = new Server(serLog,serUri);
        this.titulo = "FORMACION";
        if(!sPrefijo.equals("F")) this.titulo = "CAPACITACION";
        loadProvider();
    }
    
    
    /**
     * se crean las instancias a las clases de comunicación con moodle
     */
    private void loadProvider() {
        this.categoriaProvider = new CategoriaProvider(this.server);
        this.cursoProvider = new CursoProvider(this.server);
        this.usuarioProvider = new UsuarioProvider(this.server);
        // aqui se llenan las categorias
        if (!this.categoriaProvider.getCategoriaCriterio()) {
            this.errores.addAll(this.categoriaProvider.getErrores());
            this.error = this.categoriaProvider.getErrorLast();
        } else {
            this.conectado = true;
        }
    }

    /**
     * Devuelve el listado de Categorias
     *
     * @return
     */
    public List<Categoria> getCategorias() {
        return this.categoriaProvider.getCategorias();
    }

    /**
     * Buscar un node de categoría
     *
     * @param lst
     * @param sCat
     * @return
     */
    private Categoria buscarNodo(List<Categoria> lst, String sCat) {
        for (Categoria ct : lst) {
            if (ct.getName().toUpperCase().equals(sCat.toUpperCase())) {
                return ct;
            } else {
                Categoria ct2 = buscarNodo(ct.getDetella(), sCat);
                if (ct2 != null) {
                    return ct2;
                }
            }
        }
        return null;
    }

    /**
     * Busca una categoria segun clave
     *
     * @param key
     * @return
     */
    public boolean buscarCategoria(String key) {
        if (this.conectado) {
            this.categoria = null;
            if (this.categoriaProvider.getCategorias().size() > 0) {
                String sCatPadre = "";
                if (this.prefijoCurso.equals("F")) {
                    sCatPadre = "VIACA";
                } else {
                    sCatPadre = "VIECO";
                }
                Categoria cPadre = this.buscarNodo(this.getCategorias(), sCatPadre);
                if (cPadre != null) {
                    this.categoria = this.buscarNodo(cPadre.getDetella(), key);
                }
                if (this.categoria != null) {
                    return true;
                } else {
                    this.addErrorException("Result", "NOTFINDCATEGORIA", "No hay categoria para ese criterio");
                }
            } else {
                this.addErrorException("Result", "NOTCATEGORIA", "No hay categorias");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Busca un curso con la categoria seleccionada
     *
     * @param cursoSemilla
     * @param idProgPensum
     * @return
     */
    public boolean buscarCurso(String cursoSemilla, String idProgPensum) {
        this.sCursoSemilla = cursoSemilla;
        this.sIdProgPensum = idProgPensum;
        if (this.categoria != null) {
            return this.buscarCursoCategoria(cursoSemilla, idProgPensum, this.categoria.getId().toString(), true);
        } else {
            this.addErrorException("Result", "NOTSELCATEGORIA", "No hay categoria seleccionada");
        }
        return false;
    }

    /**
     * Busca el curoso por categoria
     *
     * @param cursoSemilla
     * @param idProgPensum
     * @param categoria
     * @return
     */
    public boolean buscarCurso(String cursoSemilla, String idProgPensum, String categoria) {
        this.sCursoSemilla = cursoSemilla;
        this.sIdProgPensum = idProgPensum;
        this.sCategoria = categoria;
        if (this.buscarCategoria(categoria)) {
            return this.buscarCursoCategoria(cursoSemilla, idProgPensum, this.categoria.getId().toString(), true);
        } else {
            return false;
        }
    }

    /**
     * Busca el curso sin crearlo
     *
     * @param cursoSemilla
     * @param idProgPensum
     * @param categoria
     * @return
     */
    public boolean buscarCursoNoCreate(String cursoSemilla, String idProgPensum, String categoria) {
        this.sCursoSemilla = cursoSemilla;
        this.sIdProgPensum = idProgPensum;
        this.sCategoria = categoria;
        if (this.buscarCategoria(categoria)) {
            return this.buscarCursoCategoria(cursoSemilla, idProgPensum, this.categoria.getId().toString(), false);
        } else {
            return false;
        }
    }

    /**
     * Busca un curso, si no existe lo copia del curso semilla
     *
     * @param cursoSemilla
     * @param idProspenso
     * @param categoria
     * @return
     */
    private boolean buscarCursoCategoria(String cursoSemilla, String idProgPensum, String categoria, boolean pCreate) {
        this.sCursoSemilla = cursoSemilla;
        this.sIdProgPensum = idProgPensum;
        this.sCategoria = categoria;
        boolean bOk = false;
        if (this.conectado) {
            this.curso = null;
            // se busca el curso ...
            if (this.cursoProvider.getCursoByShortName(cursoSemilla + "_" + prefijoCurso + "_" + idProgPensum)) {
                this.curso = this.cursoProvider.getCurso();
                this.cursoProvider.getUsuariosByCurso();
                bOk = true;
            } else if (pCreate) {
                if (this.cursoProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                    // Busca el curso semilla;
                    this.cursoProvider.cleanErrors();
                    if (this.cursoProvider.getCursoByShortName(cursoSemilla)) {
                        //if (this.cursoProvider.getCurso().getVisible().intValue() != 1) {
                        //    this.addErrorException("Result", "NOTDISPONIBLE", "EL curso semilla no esta disponible");
                        //} else {
                            // se duplica el curso semilla
                            this.cursoProvider.cleanErrors();
                            Parameters parameters = new Parameters();
                            parameters.addParameter("courseid", this.cursoProvider.getCurso().getId().toString());
                            parameters.addParameter("fullname", this.cursoProvider.getCurso().getFullname());
                            parameters.addParameter("shortname", cursoSemilla + "_" + prefijoCurso + "_" + idProgPensum);
                            if (this.prefijoCurso.equals("F")) {
                                parameters.addParameter("categoryid", this.cursoProvider.getCurso().getCategoryid().toString());
                            } else {
                                parameters.addParameter("categoryid", categoria);
                            }
                            parameters.addParameter("visible", "1");
                            if (this.cursoProvider.duplicarCurso(parameters)) {
                                if (this.cursoProvider.getCursoByShortName(cursoSemilla + "_" + prefijoCurso + "_" + idProgPensum)) {
                                    this.curso = this.cursoProvider.getCurso();
                                    this.cursoProvider.getUsuariosByCurso();
                                    this.usuariosDesasignar();
                                    bOk = true;
                                } else {
                                    this.addErrorException(this.cursoProvider.getErrores());
                                }
                            } else {
                                this.addErrorException(this.cursoProvider.getErrores());
                            }
                        //}
                    } else if (this.cursoProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                        this.addErrorException("Result", "NOTSEMILLA", "No hay curso semilla");
                    } else {
                        this.addErrorException(this.cursoProvider.getErrores());
                    }
                } else {
                    this.addErrorException(this.cursoProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No existe el curso");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return bOk;
    }

    /**
     * Devuelve un JSONArray del una cadena
     *
     * @param content
     * @return
     */
    public JSONArray getJson(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve el JSON Object de una cadena
     *
     * @param content
     * @return
     */
    public JSONObject getJsonObject(String content) {
        try {
            JSONObject jsObject = new JSONObject(content);
            return jsObject;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Actualiza la información basica del curso
     *
     * @return
     */
    public boolean updateCurso() {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                this.cursoProvider.setCurso(this.curso);
                if (this.cursoProvider.updateCurso()) {
                    return true;
                } else {
                    this.addErrorException(this.cursoProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Asigna el docente al curso..
     *
     * @param documento
     * @return
     */
    public boolean docenteAsingar(String documento) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                // se busca el usuario...
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se debe buscar por
                 * idnumber
                 */
                //parameters.addParameter("username", documento);
                parameters.addParameter("idnumber", documento);
                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    if (this.usuarioProvider.getUsuarios().size() > 0) {
                        this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                        this.usuario = this.usuarioProvider.getUsuario();
                        // se hace la asignación...
                        parameters.getParameters().clear();
                        parameters.addParameter("roleid", this.cursoProvider.roleDocente.toString());
                        parameters.addParameter("userid", this.usuario.getId().toString());
                        parameters.addParameter("courseid", this.curso.getId().toString());
                        if (this.cursoProvider.agregarUsuarioRol(parameters)) {
                            return true;
                        } else {
                            this.addErrorException(this.cursoProvider.getErrorLast());
                        }
                    } else {
                        this.addErrorException("Result", "NOTUSER", "No se consiguio el usuario");
                        this.usuario = this.usuarioProvider.getNewUusuario();
                    }
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                    this.addErrorException("Result", "NOTUSER", "No se consiguio el usuario");
                    this.usuario = this.usuarioProvider.getNewUusuario();
                } else {
                    this.addErrorException(this.usuarioProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Se asigna el alumno al curso
     *
     * @return
     */
    private boolean alumnoAsignar() {
        this.usuario = this.usuarioProvider.getUsuario();
        if (this.curso.getAlumnos().containsKey(this.usuario.getUsername())) {
            this.curso.getAlumnos().remove(this.usuario.getUsername());
        }
        // se hace la asignación...
        Parameters parameters = new Parameters();
        parameters.addParameter("roleid", this.cursoProvider.roleAlumno.toString());
        parameters.addParameter("userid", this.usuario.getId().toString());
        parameters.addParameter("courseid", this.curso.getId().toString());
        if (this.cursoProvider.agregarUsuarioRol(parameters)) {
            return true;
        } else {
            Error e = new Error();
            e.setErrorcode(this.cursoProvider.getErrorLast().getErrorcode());
            e.setExeption(this.cursoProvider.getErrorLast().getExeption());
            String sMensaje = "";
            if (this.usuario.getIdnumber() != null) {
                sMensaje = this.usuario.getIdnumber();
            }
            sMensaje += ": " + this.cursoProvider.getErrorLast().getMessage();
            e.setMessage(sMensaje);
            this.addErrorException(e);
        }
        return false;
    }
    
    private boolean esIgual(String sValUno, String sValDos) {
        if(sValUno == null && sValDos == null) {
            return true;
        } else if(sValUno != null && sValDos != null) {
            if(sValUno.equals(sValDos)) return true;
        }
        return false;
    }

    private boolean crearUsuario(SieduPersona persona, SieduDatosEmpleadoDTO datosEmpleadoDTO, boolean bAlumno) {
        Usuario usuario = this.usuarioProvider.getNewUusuario();

        /**
         * Configurar el metodo en la clase SieduPersona para
         * obtener el usuarioEmpresarial y luego pasar el parametro
         * al metodo usuario.setIdnumber();
         */
        usuario.setUsername(persona.getPersUsuarioEmpresarial());
        /**
         * Se asigna el metodo persona.getPersNroid() a la propiedad
         * idnumber del Objeto Usuario
         *
         * @lastmodby: Julio Lopez
         * @lastmoddt: 24/11/2017
         */
        usuario.setIdnumber(persona.getPersNroid());

        usuario.setFirstname(persona.getPersNombres());
        usuario.setLastname(persona.getPersApellidos());
        usuario.setFullname(persona.getPersNombres() + " " + persona.getPersApellidos());
        if (persona.getPersEmail() == null || persona.getPersEmail().isEmpty()) {
            usuario.setEmail(persona.getPersCorreo());
        } else {
            usuario.setEmail(persona.getPersEmail());
        }
        usuario.setDepartment(datosEmpleadoDTO.getUnidadGrupoLabora());               
        usuario.setMiddlename(persona.getPersGrado());                    
        usuario.setInstitution(datosEmpleadoDTO.getSiglaLabora());
        usuario.setFirstaccess(0);
        usuario.setLastaccess(0);
        
        if(persona.getPersEmpConsecutivo() != null) {
            usuario.setAuth("ldap");
            usuario.setPassword("Dinae99#");
        } else {
            usuario.setAuth("manual");
            usuario.setPassword("Cc-"+persona.getPersNroid());
        }
        
        usuario.setTimezone("99");
        usuario.setTheme("");
        usuario.setMailformat(1);
        usuario.setDescription("");
        usuario.setDescriptionformat(1);
        usuario.setProfileimageurlsmall("");
        usuario.setProfileimageurl("");
        if (this.usuarioProvider.crearUser()) {
            if(bAlumno) {
                return this.alumnoAsignar();
            } else {
                return this.docenteAsignar();
            }
        } else {
            this.addErrorException(this.usuarioProvider.getErrorLast());
        }
        return false;
    }
    
    private void updateUsuario(SieduPersona persona, SieduDatosEmpleadoDTO datosEmpleadoDTO) {
        if(datosEmpleadoDTO.getUnidadGrupoLabora() == null) datosEmpleadoDTO.setUnidadGrupoLabora("");
        if(datosEmpleadoDTO.getSiglaLabora() == null) datosEmpleadoDTO.setSiglaLabora("");
        String sEmail = "";
        if (persona.getPersEmail() == null || persona.getPersEmail().isEmpty()) {
            sEmail = persona.getPersCorreo();
        } else {
            sEmail = persona.getPersEmail();
        }
        if (!this.usuarioProvider.getUsuario().getUsername().equals(persona.getPersUsuarioEmpresarial()) ||
            !this.esIgual(this.usuarioProvider.getUsuario().getDepartment(), datosEmpleadoDTO.getUnidadGrupoLabora()) ||
            !this.esIgual(this.usuarioProvider.getUsuario().getMiddlename(), persona.getPersGrado()) ||
            !this.esIgual(this.usuarioProvider.getUsuario().getEmail(), sEmail) ||
            !this.esIgual(this.usuarioProvider.getUsuario().getInstitution(), datosEmpleadoDTO.getSiglaLabora())) {
            this.usuarioProvider.getUsuario().setUsername(persona.getPersUsuarioEmpresarial());
            this.usuarioProvider.getUsuario().setDepartment(datosEmpleadoDTO.getUnidadGrupoLabora());               
            this.usuarioProvider.getUsuario().setMiddlename(persona.getPersGrado());                    
            this.usuarioProvider.getUsuario().setEmail(sEmail);
            this.usuarioProvider.getUsuario().setInstitution(datosEmpleadoDTO.getSiglaLabora());
            this.usuarioProvider.updateUser();
        }
    }
    
    /**
     * Asigna el alumno con el Modelo persona
     *
     * @param persona
     * @return
     *
     */
    public boolean alumnoAsignar(SieduPersona persona, SieduDatosEmpleadoDTO datosEmpleado) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se realizará la búsqueda
                 * por el campo "idnumber" y al campo "username" se le asigna el
                 * parametro usuarioEmpresarial
                 *
                 * @lastmodby: Julio Lopez
                 * @lastmoddt: 24/11/2017
                 *
                 */
                //parameters.addParameter("username", persona.getPersNroid());
                parameters.addParameter("idnumber", persona.getPersNroid());

                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    if (this.curso.getAlumnos().containsKey(this.usuarioProvider.getUsuario().getUsername())) {
                        this.curso.getAlumnos().remove(this.usuarioProvider.getUsuario().getUsername());
                    }
                    this.updateUsuario(persona, datosEmpleado);
                    return alumnoAsignar();
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTUSER")) {
                    return this.crearUsuario(persona, datosEmpleado,true);
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    public boolean cursoEliminar() {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                if (this.cursoProvider.deleteCurso()) {
                    return true;
                } else {
                    this.addErrorException(this.cursoProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Se asigna el alumno y si no esta lo crea
     *
     * @param documento
     * @param nombre
     * @param apellido
     * @param correo
     * @return
     */
    public boolean alumnoAsignar(String documento, String nombre, String apellido, String correo, String usuarioEmpresarial, boolean bPolicia) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se realizará la búsqueda
                 * por el campo "idnumber" y al campo "username" se le asigna el
                 * parametro usuarioEmpresarial
                 *
                 * @lastmodby: Julio Lopez
                 * @lastmoddt: 24/11/2017
                 *
                 */
                //parameters.addParameter("username", documento);                
                parameters.addParameter("idnumber", documento);
                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    if (this.curso.getAlumnos().containsKey(this.usuarioProvider.getUsuario().getUsername())) {
                        this.curso.getAlumnos().remove(this.usuarioProvider.getUsuario().getUsername());
                    }
                    if (!this.usuarioProvider.getUsuario().getUsername().equals(usuarioEmpresarial)) {
                        this.usuarioProvider.getUsuario().setUsername(usuarioEmpresarial);
                        this.usuarioProvider.updateUser();
                    }
                    return alumnoAsignar();
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTUSER")) {
                    Usuario usuario = this.usuarioProvider.getNewUusuario();

                    /**
                     * Se comenta la linea de código ya que el parametro que
                     * recibe el metodo setUsername() es ahora
                     * usuarioEmpresarial
                     *
                     * @lastmodby: Julio Lopez
                     * @lastmoddt: 24/11/2017
                     *
                     */
                    //usuario.setUsername(documento);
                    usuario.setUsername(usuarioEmpresarial);
                    usuario.setIdnumber(documento);

                    usuario.setFirstname(nombre);
                    usuario.setLastname(apellido);
                    usuario.setFullname(nombre + " " + apellido);
                    usuario.setEmail(correo);
                    usuario.setDepartment("");
                    usuario.setFirstaccess(0);
                    usuario.setLastaccess(0);
                    if(bPolicia) {
                        usuario.setAuth("ldap");
                        usuario.setPassword("Dinae99#");
                    } else {
                        usuario.setAuth("manual");                        
                        usuario.setPassword("Cc-"+documento);
                    }
                    usuario.setTimezone("99");
                    usuario.setTheme("");
                    usuario.setMailformat(1);
                    usuario.setDescription("");
                    usuario.setDescriptionformat(1);
                    usuario.setProfileimageurlsmall("");
                    usuario.setProfileimageurl("");
                    if (this.usuarioProvider.crearUser()) {
                        return alumnoAsignar();
                    } else {
                        this.addErrorException(this.usuarioProvider.getErrorLast());
                    }
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Se asinga el alumno al curso...
     *
     * @param documento
     * @return
     */
    public boolean alumnoAsignar(String documento) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                // se busca el usuario...
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se debe buscar por
                 * idnumber
                 *
                 * @lastmodby: Julio Lopez
                 * @lastmoddt: 24/11/2017
                 *
                 */
                //parameters.addParameter("username", documento);
                parameters.addParameter("idnumber", documento);

                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    this.usuario = this.usuarioProvider.getUsuario();
                    // se hace la asignación...
                    parameters.getParameters().clear();
                    parameters.addParameter("roleid", this.cursoProvider.roleAlumno.toString());
                    parameters.addParameter("userid", this.usuario.getId().toString());
                    parameters.addParameter("courseid", this.curso.getId().toString());
                    if (this.cursoProvider.agregarUsuarioRol(parameters)) {
                        return true;
                    } else {
                        this.addErrorException(this.cursoProvider.getErrorLast());
                    }
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                    this.addErrorException("Result", "NOTUSER", "No se consiguio el usuario");
                    this.usuario = this.usuarioProvider.getNewUusuario();
                } else {
                    this.addErrorException(this.usuarioProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Asigna el docente al curso...
     *
     * @return
     */
    private boolean docenteAsignar() {
        this.usuario = this.usuarioProvider.getUsuario();
        if (this.curso.getDocentes().containsKey(this.usuario.getUsername())) {
            this.curso.getDocentes().remove(this.usuario.getUsername());
        }
        // se hace la asignación...
        Parameters parameters = new Parameters();
        parameters.addParameter("roleid", this.cursoProvider.roleDocente.toString());
        parameters.addParameter("userid", this.usuario.getId().toString());
        parameters.addParameter("courseid", this.curso.getId().toString());
        if (this.cursoProvider.agregarUsuarioRol(parameters)) {
            return true;
        } else {
            Error e = new Error();
            e.setErrorcode(this.cursoProvider.getErrorLast().getErrorcode());
            e.setExeption(this.cursoProvider.getErrorLast().getExeption());
            String sMensaje = "";
            if (this.usuario.getIdnumber() != null) {
                sMensaje = this.usuario.getIdnumber();
            }
            sMensaje += ": " + this.cursoProvider.getErrorLast().getMessage();
            e.setMessage(sMensaje);
            this.addErrorException(e);
        }
        return false;
    }

    /**
     * Asigna el docente con el Modelo persona
     *
     * @param persona
     * @return
     */
    public boolean docenteAsignar(SieduPersona persona,SieduDatosEmpleadoDTO datosEmpleado) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se realizará la búsqueda
                 * por el campo "idnumber" y al campo "username" se le asigna el
                 * parametro usuarioEmpresarial, ya no se busca el usuario por
                 * el campo username sino por idnumber
                 *
                 * @lastmodby: Julio Lopez
                 * @lastmoddt: 24/11/2017
                 *
                 */
                //parameters.addParameter("username", persona.getPersNroid());
                parameters.addParameter("idnumber", persona.getPersNroid());

                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    if (this.curso.getDocentes().containsKey(this.usuarioProvider.getUsuario().getUsername())) {
                        this.curso.getDocentes().remove(this.usuarioProvider.getUsuario().getUsername());
                    }
                    this.updateUsuario(persona, datosEmpleado);
                    return docenteAsignar();
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTUSER")) {
                    return this.crearUsuario(persona, datosEmpleado,false);                    
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Asigna el docente y lo crea si no existe
     *
     * @param documento
     * @param nombre
     * @param apellido
     * @param correo
     * @return
     */
    public boolean docenteAsignar(String documento, String nombre, String apellido, String correo, String usuarioEmpresarial, boolean bPolicia) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se realizará la búsqueda
                 * por el campo "idnumber" y al campo "username" se le asigna el
                 * parametro usuarioEmpresarial
                 *
                 * @lastmodby: Julio Lopez
                 * @lastmoddt: 24/11/2017
                 *
                 */
                //parameters.addParameter("username", documento);
                parameters.addParameter("idnumber", documento);

                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    if (this.curso.getDocentes().containsKey(this.usuarioProvider.getUsuario().getUsername())) {
                        this.curso.getDocentes().remove(this.usuarioProvider.getUsuario().getUsername());
                    }
                    if (!this.usuarioProvider.getUsuario().getUsername().equals(usuarioEmpresarial)) {
                        this.usuarioProvider.getUsuario().setUsername(usuarioEmpresarial);
                        this.usuarioProvider.updateUser();
                    }
                    return docenteAsignar();
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTUSER")) {
                    Usuario usuario = this.usuarioProvider.getNewUusuario();

                    /**
                     * Se comenta la linea de código ya que el parametro que
                     * recibe el metodo setUsername() es ahora
                     * usuarioEmpresarial
                     *
                     * @lastmodby: Julio Lopez
                     * @lastmoddt: 24/11/2017
                     *
                     */
                    //usuario.setUsername(documento);
                    usuario.setUsername(usuarioEmpresarial);
                    usuario.setIdnumber(documento);

                    usuario.setFirstname(nombre);
                    usuario.setLastname(apellido);
                    usuario.setFullname(nombre + " " + apellido);
                    usuario.setEmail(correo);
                    usuario.setDepartment("");
                    usuario.setFirstaccess(0);
                    usuario.setLastaccess(0);
                    if(bPolicia) {
                        usuario.setAuth("ldap");
                        usuario.setPassword("Dinae99#");
                    } else {
                        usuario.setAuth("manual");
                        usuario.setPassword("Cc-"+documento);
                    }
                    usuario.setTimezone("99");
                    usuario.setTheme("");
                    usuario.setMailformat(1);
                    usuario.setDescription("");
                    usuario.setDescriptionformat(1);
                    usuario.setProfileimageurlsmall("");
                    usuario.setProfileimageurl("");
                    if (this.usuarioProvider.crearUser()) {
                        return docenteAsignar();
                    } else {
                        this.addErrorException(this.usuarioProvider.getErrorLast());
                    }
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Desasigna el docente del curso..
     *
     * @param documento
     * @return
     */
    public boolean docenteDesasignar(String documento) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                // se busca el usuario...
                Parameters parameters = new Parameters();

                /**
                 * Se comenta la linea de código ya que se debe buscar por el
                 * idnumber que hace referencia al documento de identificacion
                 */
                //parameters.addParameter("username", documento);
                parameters.addParameter("idnumber", documento);

                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    this.usuario = this.usuarioProvider.getUsuario();
                    // se hace la asignación...
                    parameters.getParameters().clear();
                    parameters.addParameter("roleid", this.cursoProvider.roleDocente.toString());
                    parameters.addParameter("userid", this.usuario.getId().toString());
                    parameters.addParameter("courseid", this.curso.getId().toString());
                    if (this.cursoProvider.quitarUsuarioRol(parameters)) {
                        return true;
                    } else {
                        this.addErrorException(this.cursoProvider.getErrorLast());
                    }
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                    this.addErrorException("Result", "NOTUSER", "No se consiguio el usuario");
                    this.usuario = this.usuarioProvider.getNewUusuario();
                } else {
                    this.addErrorException(this.usuarioProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    public boolean usuariosDesasignar() {
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                Parameters parameters = new Parameters();
                for (Map.Entry<String, Integer> entry : this.curso.getAlumnos().entrySet()) {
                    Integer value = (Integer) entry.getValue();
                    parameters.getParameters().clear();
                    parameters.addParameter("roleid", this.cursoProvider.roleAlumno.toString());
                    parameters.addParameter("userid", value.toString());
                    parameters.addParameter("courseid", this.curso.getId().toString());
                    this.cursoProvider.quitarUsuarioRol(parameters);
                }
                for (Map.Entry<String, Integer> entry : this.curso.getDocentes().entrySet()) {
                    Integer value = (Integer) entry.getValue();
                    parameters.getParameters().clear();
                    parameters.addParameter("roleid", this.cursoProvider.roleDocente.toString());
                    parameters.addParameter("userid", value.toString());
                    parameters.addParameter("courseid", this.curso.getId().toString());
                    this.cursoProvider.quitarUsuarioRol(parameters);
                }
                this.curso.limpiarAsignaciones();
                return true;
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Desasigna el alumno del curso..
     *
     * @param documento
     * @return
     */
    public boolean alumnoDesasignar(String documento) {
        cleanErrors();
        if (this.conectado) {
            if (this.curso != null && this.curso.getId() != null) {
                // se busca el usuario...
                Parameters parameters = new Parameters();
                /**
                 * Se comenta la linea de código ya que se debe buscar por el
                 * idnumber que hace referencia al documento de identificacion
                 */
                //parameters.addParameter("username", documento);
                parameters.addParameter("idnumber", documento);
                if (this.usuarioProvider.getUsuarioCriterio(parameters)) {
                    this.usuarioProvider.selectUsuario(this.usuarioProvider.getUsuarios().size() - 1);
                    this.usuario = this.usuarioProvider.getUsuario();
                    // se hace la asignación...
                    parameters.getParameters().clear();
                    parameters.addParameter("roleid", this.cursoProvider.roleAlumno.toString());
                    parameters.addParameter("userid", this.usuario.getId().toString());
                    parameters.addParameter("courseid", this.curso.getId().toString());
                    if (this.cursoProvider.quitarUsuarioRol(parameters)) {
                        return true;
                    } else {
                        this.addErrorException(this.cursoProvider.getErrorLast());
                    }
                } else if (this.usuarioProvider.getErrorLast().getErrorcode().equals("NOTDATA")) {
                    this.addErrorException("Result", "NOTUSER", "No se consiguio el usuario");
                    this.usuario = this.usuarioProvider.getNewUusuario();
                } else {
                    this.addErrorException(this.usuarioProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTCURSO", "No hay curso seleccionado");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Crea una nueva instancia del objeto usuario...
     *
     * @return
     */
    public boolean nuevoUsuario() {
        if (this.conectado) {
            this.usuario = this.usuarioProvider.getNewUusuario();
            return true;
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Crea un usuario en moodle
     *
     * @return
     */
    public boolean crearUsuario() {
        cleanErrors();
        if (this.conectado) {
            if (this.usuario != null && this.usuario.getId() == null) {
                this.usuarioProvider.setUsuario(this.usuario);
                if (this.usuarioProvider.crearUser()) {
                    return true;
                } else {
                    this.addErrorException(this.usuarioProvider.getErrorLast());
                }
            } else {
                this.addErrorException("Result", "NOTNEWUSER", "No se ha instacionado una clase de usuario");
            }
        } else {
            this.addErrorException("Result", "NOTCONECT", "No hay conexion");
        }
        return false;
    }

    /**
     * Limpia los errores
     */
    public void cleanErrors() {
        this.errores.clear();
        this.error = null;
    }

    /**
     * Se establece error según los parametros de Moodle
     *
     * @param exception
     * @param errorcode
     * @param message
     */
    protected void addErrorException(String exception, String errorcode, String message) {
        this.error = new Error(exception, errorcode, message);
        this.errores.add(error);
    }

    /**
     * Se establece el error por una clase Error
     *
     * @param error
     */
    protected void addErrorException(Error error) {
        this.error = error;
        this.errores.add(error);
    }

    /**
     * Se establece el error por un valor de Json
     *
     * @param sError
     */
    protected void addErrorException(String sError) {
        JSONArray jsonArray = this.server.getJson(sError);
        if (jsonArray != null) {
            if (jsonArray.length() > 0) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    this.addErrorException(jsonObject.getString("exeption"), jsonObject.getString("errorcode"), jsonObject.getString("message"));
                } catch (Exception e) {
                    this.addErrorException("JSONException", "E-JSON", "Formato no valído");
                }
            } else {
                this.addErrorException("JSONException", "E-JSON", "Formato no valído");
            }
        } else {
            try {
                JSONObject jsObj = this.getServer().getJsonObject(sError);
                if (jsObj != null) {
                    this.addErrorException(jsObj.getString("exception"), jsObj.getString("errorcode"), jsObj.getString("message"));
                } else {
                    this.addErrorException("JSONException", "E-JSON", "Formato no valído");
                }
            } catch (Exception e) {
                this.addErrorException("JSONException", "E-JSON", "Formato no valído");
            }
        }
    }

    /*
     * Se establece el error por una lista de errores
     */
    protected void addErrorException(List<Error> lstError) {
        this.errores.addAll(lstError);
        if (this.errores.size() > 0) {
            this.error = this.errores.get(this.errores.size() - 1);
        }
    }

    public void cursoUpdateOnly(String cursoSemilla, String idProgPensum, String categoria, Date fechaIni, Date fechaFin) {
        if (this.buscarCursoNoCreate(cursoSemilla, idProgPensum, categoria)) {
            this.updateFechas(fechaIni,fechaFin);
        } else {
            this.getServer().addLogError(this.titulo, "buscarCurso " + cursoSemilla, this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
        }
    }
    
    public void updateFechas(Date fechaIni, Date fechaFin) {
        try {
            this.getCurso().setStartdate(fechaIni);
            this.getCurso().setEnddate(fechaFin);
            if (!this.updateCurso()) {
                this.getServer().addLogError(this.titulo, "updateCurso(" + this.sIdProgPensum , this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
            }
        } catch (Exception ex) {
            this.getServer().addLogError(this.titulo, "updateCurso(" + this.sIdProgPensum, "JAVAERROR", "UPDATECURSO", ex.toString());
        }
    }
    
    
    
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Error> getErrores() {
        return errores;
    }

    public void setErrores(List<Error> errores) {
        this.errores = errores;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    protected String getPrefijoCurso() {
        return prefijoCurso;
    }

    protected void setPrefijoCurso(String prefijoCurso) {
        this.prefijoCurso = prefijoCurso;
    }

    public CategoriaProvider getCategoriaProvider() {
        return categoriaProvider;
    }

    public CursoProvider getCursoProvider() {
        return cursoProvider;
    }

    public UsuarioProvider getUsuarioProvider() {
        return usuarioProvider;
    }

}
