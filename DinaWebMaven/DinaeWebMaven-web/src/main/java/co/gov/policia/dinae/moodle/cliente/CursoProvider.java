/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.cliente;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import co.gov.policia.dinae.moodle.config.Parameter;
import co.gov.policia.dinae.moodle.config.Parameters;
import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.moodle.dto.Curso;
import co.gov.policia.dinae.moodle.dto.CursoFormatOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que utiliza los servision web de moodle par cursos..
 * @author ferney
 *
 */
public class CursoProvider extends ClienteWebServer {

	
    /**
     * 
     */
    private static final long serialVersionUID = 3052259065829890946L;

    private final String const_get_all_courses = "core_course_get_courses";
    private final String const_create_course = "core_course_create_courses";
    private final String const_get_criterio_cursos ="core_course_get_courses_by_field";
    private final String const_update_curse = "core_course_update_courses";
    private final String const_duplicate_curse = "core_course_duplicate_course";
    private final String const_agregar_usuario_rol = "enrol_manual_enrol_users";
    private final String const_quitar_usuario_rol = "enrol_manual_unenrol_users";
    private final String const_course_users = "core_enrol_get_enrolled_users";
    private final String const_delete_courses = "core_course_delete_courses";

    public final Integer roleDocente = 3;
    public final Integer roleAlumno  = 5;

    private Curso curso;
    private List<Curso> cursos = new ArrayList<Curso>();
    private Integer idNewCurso;

    /**
     * Instancia la clase con el Server
     * @param server
     */
    public CursoProvider(Server server) {
            super(server);
            // TODO Auto-generated constructor stub
    }


    /** 
     * Establece un nuevo curso
     */
    public void newCurso() {
            this.curso = new Curso();
    }

    /**
     * Establece y devuelve un nuevo usuario
     * @return
     */
    public Curso getNewCurso() {
            this.newCurso();
            return this.curso;
    }

    /**
     * Llena los datos de un curso con el resultado del servicio
     * @param jsObj
     * @return
     */
    private Curso jsonToCurso(JSONObject jsObj) {
            Curso nCurso = new Curso();

            try {
                    nCurso.setId(this.getJsonInteger(jsObj,"id"));			
                    nCurso.setCategoryid(this.getJsonInteger(jsObj,"categoryid"));
                    nCurso.setCategorysortorder(this.getJsonInteger(jsObj,"categorysortorder"));
                    nCurso.setFullname(this.getJsonString(jsObj,"fullname"));
                    nCurso.setDisplayname(this.getJsonString(jsObj,"displayname"));
                    nCurso.setIdnumber(this.getJsonString(jsObj,"idnumber"));
                    nCurso.setSummary(this.getJsonString(jsObj,"summary"));
                    nCurso.setSummaryformat(this.getJsonInteger(jsObj,"summaryformat"));
                    nCurso.setFormat(this.getJsonString(jsObj,"format"));
                    nCurso.setShowgrades(this.getJsonInteger(jsObj,"showgrades"));
                    nCurso.setNewsitems(this.getJsonInteger(jsObj,"newsitems"));
                    nCurso.setStartdate(this.getJsonDate(jsObj,"startdate"));
                    nCurso.setEnddate(this.getJsonDate(jsObj,"enddate"));
                    nCurso.setNumsections(this.getJsonInteger(jsObj,"numsections"));
                    nCurso.setMaxbytes(this.getJsonInteger(jsObj,"maxbytes"));
                    nCurso.setShowreports(this.getJsonInteger(jsObj,"showreports"));
                    nCurso.setVisible(this.getJsonInteger(jsObj,"visible"));
                    nCurso.setGroupmode(this.getJsonInteger(jsObj,"groupmode"));
                    nCurso.setGroupmodeforce(this.getJsonInteger(jsObj,"groupmodeforce"));
                    nCurso.setDefaultgroupingid(this.getJsonInteger(jsObj,"defaultgroupingid"));
                    nCurso.setTimecreated(this.getJsonDate(jsObj,"timecreated"));
                    nCurso.setTimemodified(this.getJsonDate(jsObj,"timemodified"));
                    nCurso.setEnablecompletion(this.getJsonInteger(jsObj,"enablecompletion"));
                    nCurso.setCompletionnotify(this.getJsonInteger(jsObj,"completionnotify"));
                    nCurso.setLang(this.getJsonString(jsObj,"lang"));
                    nCurso.setForcetheme(this.getJsonString(jsObj,"forcetheme"));
                    nCurso.setShortname(this.getJsonString(jsObj,"shortname"));
                    nCurso.setHiddensections(this.getJsonInteger(jsObj,"hiddensections"));

                    JSONArray formats = this.getJsonArray(jsObj, "courseformatoptions");
                    if(formats != null && formats.length() > 0) {
                            for (int i = 0;i<formats.length (); i++) {
                                    try {
                                            JSONObject jsonObject = formats.getJSONObject(i);
                                            CursoFormatOption f = new CursoFormatOption();
                                            f.setName(this.getJsonString(jsonObject, "name"));
                                            f.setValue(this.getJsonInteger(jsonObject, "value"));
                                            nCurso.getFormatOptions().add(f);
                                    } catch (Exception e) {}
                            }
                    }

            } catch (Exception e) {
                    this.addErrorException("JSONException","E-JSON", e.getMessage());
            }
            return nCurso;
    }

    /**
     * Llena el listado de cursos segun un arreglo Json
     * @param jsonArray
     */
    private void llenarCursoJArray(JSONArray jsonArray) {
            for (int i = 0;i<jsonArray.length (); i++) {
                    try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Curso nCurso = this.jsonToCurso(jsonObject);
                            this.cursos.add(nCurso);
                    } catch (Exception e) {
                            this.addErrorException("JSONException","E-JSON", "Formato no valído");
                    }
            }
    }

    /**
     * Llena el arreglo de los cursos segun una cadena Json
     * @param content
     * @return
     */
    private boolean llenarCursos(String content) {
            boolean bOk = false;
            if(this.validarJson(content)) {
                    JSONArray jsonArray = this.getServer().getJson(content);
                    if(jsonArray != null) {
                            if(jsonArray.length() > 0 ) {
                                    llenarCursoJArray(jsonArray);
                                    bOk = true;
                            } else {
                                    this.addErrorException("Result","NOTDATA", "No hay cursos");
                            }
                    } else {
                            JSONObject jObj = this.getServer().getJsonObject(content);
                            if(jObj != null) {
                                    try {
                                            JSONArray jArray = jObj.getJSONArray("courses");
                                            if(jArray != null && jArray.length() > 0) {
                                                    llenarCursoJArray(jArray); 
                                                    bOk = true;
                                            } else {
                                                    this.addErrorException("Result","NOTDATA", "No hay cursos");
                                            }
                                    } catch (Exception e) {
                                            this.addErrorException("JSONException","E-JSON", "Formato no valído");
                                    }
                            } else {
                                    this.addErrorException("JSONException","E-JSON", "Formato no valído");
                            }
                    }
            }
            return bOk;
    }

    /**
     * Busca todos los cursos
     * @return
     */
    public boolean callAllCourses() {
            boolean bOk = false;
            this.cursos.clear();
            String content = this.getServer().getData(this.getServer().getUrlService(this.const_get_all_courses));
            bOk = llenarCursos(content);
            return bOk;
    } 

    /**
     * Selecciona un curso del arreglo
     * @param index
     */
    public void selectCurso(int index) {
            this.curso = this.cursos.get(index);
    }

    /**
     * Actualiza el curso seleccionado
     * @return
     */
    public boolean updateCurso() {
            if(this.sendData(const_update_curse, this.curso.getStringPos("0"))) {
                    JSONObject jsObj = null;
                    if(this.getReqJsonArray() != null) {
                            try {
                                    jsObj = this.getReqJsonArray().getJSONObject(0);
                            } catch (Exception e) {}
                    } else {
                            jsObj = this.getReqJsonObject();
                    }
                    if(jsObj != null) {
                            try {
                                    JSONArray jsonArray = jsObj.getJSONArray("warnings");
                                    if(jsonArray != null && jsonArray.length() == 0) {
                                            return true;
                                    } else {
                                            this.addErrorException("Result","NOTUPDATE", jsonArray.toString());						
                                    }
                            } catch (Exception e) {}
                    }
            }
            return false;
    }
    
    /**
     * Elimina el curso seleccionado...
     * @return 
     */
    public boolean deleteCurso() {
        if(this.sendData(this.const_delete_courses, this.curso.getStringDelete())) {
            JSONObject jsObj = null;
            if(this.getReqJsonArray() != null) {
                    try {
                            jsObj = this.getReqJsonArray().getJSONObject(0);
                    } catch (Exception e) {}
            } else {
                    jsObj = this.getReqJsonObject();
            }
            if(jsObj != null) {
                    try {
                            JSONArray jsonArray = jsObj.getJSONArray("warnings");
                            if(jsonArray != null && jsonArray.length() == 0) {
                                    return true;
                            } else {
                                    this.addErrorException("Result","NOTUPDATE", jsonArray.toString());						
                            }
                    } catch (Exception e) {}
            }
        }
        return false;
    }

    /**
     * Crea un nuevo curso
     */
    public boolean crearCurso() {
            if(this.sendData(const_create_course, this.curso.getStringPosCreate("0"))) {
                    JSONObject jsObj = null;
                    if(this.getReqJsonArray() != null) {
                            try {
                                    jsObj = this.getReqJsonArray().getJSONObject(0);
                            } catch (Exception e) {}
                    } else {
                            jsObj = this.getReqJsonObject();
                    }
                    if(jsObj != null) {
                            this.curso.setId(this.getJsonInteger(jsObj,"id"));			
                            this.curso.setShortname(this.getJsonString(jsObj,"shortname"));
                    }
                    return true;
            }
            return false;
    }

    /**
     * Quita un usiario rol del curso
     * @param parameters
     * @return
     */
    public boolean quitarUsuarioRol(Parameters parameters) {
            boolean bOk = false;
            try {
                    String content = this.getServer().getData(this.getServer().getUrlService(this.const_quitar_usuario_rol), parameters.getStringTypeName("enrolments"));
                    if(this.validarJson(content)) {
                            JSONObject jsObj = null;
                            if(this.getReqJsonArray() != null) {
                                    try {
                                            jsObj = this.getReqJsonArray().getJSONObject(0);
                                    } catch (Exception e) {}
                            } else {
                                    jsObj = this.getReqJsonObject();
                            }
                            if(jsObj != null) {
                                    //this.idNewCurso = this.getJsonInteger(jsObj,"id"); 
                            }
                            bOk = true;
                    } 
            } catch (Exception e) {
                    this.addErrorException("JSONException","E-JSON", "Valor no perimitido");
            }
            return bOk;
    }

    /**
     * Agrega un usuario rol del curso
     * @param parameters
     * @return
     */
    public boolean agregarUsuarioRol(Parameters parameters) {
            boolean bOk = false;
            try {
                    String content = this.getServer().getData(this.getServer().getUrlService(this.const_agregar_usuario_rol), parameters.getStringTypeName("enrolments"));
                    if(this.validarJson(content)) {
                            JSONObject jsObj = null;
                            if(this.getReqJsonArray() != null) {
                                    try {
                                            jsObj = this.getReqJsonArray().getJSONObject(0);
                                    } catch (Exception e) {}
                            } else {
                                    jsObj = this.getReqJsonObject();
                            }
                            if(jsObj != null) {
                                    //this.idNewCurso = this.getJsonInteger(jsObj,"id"); 
                            }
                            bOk = true;
                    } 
            } catch (Exception e) {
                    this.addErrorException("JSONException","E-JSON", "Valor no perimitido");
            }
            return bOk;
    }

    /**
     * Duplica un curso segun parametros
     * @param parameters
     * @return
     */
    public boolean duplicarCurso(Parameters parameters) {
            boolean bOk = false;
            this.idNewCurso = null;
            try {
                    String content = this.getServer().getData(this.getServer().getUrlService(this.const_duplicate_curse), parameters.getStringNameValue());
                    if(this.validarJson(content)) {
                            JSONObject jsObj = null;
                            if(this.getReqJsonArray() != null) {
                                    try {
                                            jsObj = this.getReqJsonArray().getJSONObject(0);
                                    } catch (Exception e) {}
                            } else {
                                    jsObj = this.getReqJsonObject();
                            }
                            if(jsObj != null) {
                                    this.idNewCurso = this.getJsonInteger(jsObj,"id"); 
                            }
                            bOk = true;
                    } 
            } catch (Exception e) {
                    this.addErrorException("JSONException","E-JSON", "Valor no perimitido");
            }
            return bOk;
    }

    /** 
     * Busca un curso por el shorname
     * @param shorName
     * @return
     */
    public boolean getCursoByShortName(String shorName) {
            Parameter parameter = new Parameter("shortname", shorName);
            boolean bOk = getCursosCriterios(parameter);
            if(bOk) {
                    this.curso = this.cursos.get(this.cursos.size()-1);
            }
            return bOk;
    }
    
    /**
     * Busca los docentes y estudiantes del curso
     * @return 
     */
    public boolean getUsuariosByCurso() {
        if(this.curso != null && this.curso.getId() != null) {
            String sParameter = "courseid=" + this.curso.getId();
            try {
                String content = this.getServer().getData(this.getServer().getUrlService(this.const_course_users),sParameter);
                if(this.validarJson(content)) {
                    if(this.getReqJsonArray() != null) {
                        for (int i = 0;i< this.getReqJsonArray().length (); i++) {
                            JSONObject jsObj = this.getReqJsonArray().getJSONObject(i);
                            String sCedula = this.getJsonString(jsObj,"username");
                            Integer idUsuario = this.getJsonInteger(jsObj, "id");
                            JSONArray jArray = this.getJsonArray(jsObj, "roles");
                            for(int j = 0; j < jArray.length(); j++) {
                                JSONObject jsRol = jArray.getJSONObject(j);
                                Integer iRol = this.getJsonInteger(jsRol, "roleid");
                                if(iRol.intValue() == this.roleAlumno.intValue()) {
                                    this.curso.getAlumnos().put(sCedula, idUsuario);
                                } else if(iRol.intValue() == this.roleDocente.intValue()) {
                                    this.curso.getDocentes().put(sCedula, idUsuario);
                                }
                            }
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                this.addErrorException("JSONException","E-JSON", "Valor no perimitido");
            }
        }
        return false;
    }

    /**
     * Busca los curson segun criterios
     * @param parameter
     * @return
     */
    public boolean getCursosCriterios(Parameter parameter)  {
            boolean bOk = false;
            this.cursos.clear(); 
            String content = this.getServer().getData(this.getServer().getUrlService(this.const_get_criterio_cursos),parameter.getStringField());
            bOk = llenarCursos(content);
            return bOk;
    }

    public Curso getCurso() {
            return curso;
    }

    public void setCurso(Curso curso) {
            this.curso = curso;
    }

    public List<Curso> getCursos() {
            return cursos;
    }

    public void setCursos(List<Curso> cursos) {
            this.cursos = cursos;
    }

    public Integer getIdNewCurso() {
            return idNewCurso;
    }

    public void setIdNewCurso(Integer idNewCurso) {
            this.idNewCurso = idNewCurso;
    }
}
