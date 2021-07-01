/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.controladores;

import co.gov.policia.dinae.moodle.MoodleFormacion;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.dto.SieduFechasMaxMinEventoDTO;
import co.gov.policia.dinae.siedu.modelo.Alumnos;
import co.gov.policia.dinae.siedu.modelo.AlumnosAsignatura;
import co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos;
import co.gov.policia.dinae.siedu.modelo.SieduAsignaturasDocente;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendHttpService;
import co.gov.policia.dinae.siedu.servicios.SieduAlumnosAsignaturaService;
import co.gov.policia.dinae.siedu.servicios.SieduAlumnosService;
import co.gov.policia.dinae.siedu.servicios.SieduAsignaturasDocenteService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionAlumnosService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionDocenteService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ferney Duran - Catedra
 */
@Named(value = "SigaciServiceFaces")
@RequestScoped
public class SigaciServiceFaces extends AbstractController implements Serializable {

    // token para validar la petición
    private String token = "token123456789moodle";
    private String result = "";

    @EJB
    private SieduAlumnosAsignaturaService serviceAlumnos;

    @EJB
    private SieduAsignaturasDocenteService serviceDocentes;

    @EJB
    private LogMoodleSendDataService serviceLog;

    @EJB
    private LogMoodleSendHttpService serviceUri;

    @EJB
    private SieduAlumnosService serviceAlumno;

    @EJB
    private SieduProgramacionAlumnosService serviceProgramaAlumnos;

    @EJB
    private SieduProgramacionDocenteService servicioPrograma;

    private MoodleFormacion moodleServicio;

    @PostConstruct
    private void init() {
        this.moodleServicio = new MoodleFormacion(serviceLog, serviceUri);
        this.result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        this.result += "\t<root>\n";
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                HttpServletRequest hRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                if (hRequest != null) {
                    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    if (params != null && !params.isEmpty()) {
                        if (params.containsKey("wsToken")) {
                            if (params.get("wsToken") != null && params.get("wsToken").equals(this.token)) {
                                String tipo = seekParameter(params, "tipo");
                                String escuela = seekParameter(params, "escuela");
                                String idprogpensum = seekParameter(params, "idprogpensum");
                                String semilla = seekParameter(params, "semilla");
                                String documento = seekParameter(params, "documento");
                                switch (tipo.toLowerCase()) {
                                    case "addcur":
                                        addCurso(idprogpensum, escuela, semilla);
                                        break;
                                    case "updcur":
                                        updCurso(idprogpensum, escuela, semilla);
                                        break;
                                    case "addpar":
                                        addParticipante(idprogpensum, escuela, semilla, documento);
                                        break;
                                    case "delpar":
                                        delParticipante(idprogpensum, escuela, semilla, documento);
                                        break;
                                    case "adddoc":
                                        addDocente(idprogpensum, escuela, semilla, documento);
                                        break;
                                    case "deldoc":
                                        delDocente(idprogpensum, escuela, semilla, documento);
                                        break;
                                    default:
                                        this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "ETIPO", "TIPO INCORRECTO");
                                }
                            } else {
                                this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "ITOKEN", "NO EXISTE EL TOKEN");
                            }
                        } else {
                            this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "NTOKEN", "NO EXISTE EL TOKEN");
                        }
                    } else {
                        this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "NPAAMETERS", "NO HAY PARAMETROS PARA LA PETICIÓN");
                    }
                } else {
                    this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "NREQUEST", "NO SE CARGO EL REQUEST");
                }
            } else {
                this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "EXECUTE", "NCONTEXT", "NO SE CARGO EL CONTEXTO");
            }
        } catch (Exception e) {
            this.moodleServicio.getServer().addLogError("FORMACION", "N/A", "JAVA", "JERROR", e.toString());
        }
        this.result += "\t</root>";
    }

    /**
     * Agrega un participante si no lo crea
     *
     * @param idprogpensum
     * @param escuela
     * @param semilla
     * @param documento
     */
    private void addParticipante(String idprogpensum, String escuela, String semilla, String documento) {
        if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
            try {
                Long l = Long.valueOf(idprogpensum);
                BigInteger x = BigInteger.valueOf(l);
                //List<AlumnosAsignatura> lstAlumnos=new ArrayList<>();               
                //lstAlumnos = this.serviceAlumnos.findByProgamaAlumno(x, documento);
                Alumnos alumno = null;
                alumno = this.serviceAlumno.findByIdentificacion(documento);
                if (alumno != null) {
                    //for(AlumnosAsignatura aln: lstAlumnos) {       
                    boolean bPolicia = true;
                    if(alumno.getEmpleado() == null || alumno.getEmpleado().isEmpty()) bPolicia = false;
                    
                    if (!this.moodleServicio.alumnoAsignar(alumno.getIdentificacion(), alumno.getNombres(), alumno.getApellidos(), alumno.getCorreoElectronico(), alumno.getUsuarioEmpresarial(),bPolicia)) {
                        this.moodleServicio.getServer().addLogError("FORMACION", "addParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                        this.deleteParticipante(idprogpensum, documento);
                    } else {
                        this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                    }
                    //}
                }
            } catch (Exception e) {
                this.moodleServicio.getServer().addLogError("FORMACION", "addParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, "JAVA", "JERROR", e.toString());
            }
        } else {
            this.addCurso(idprogpensum, escuela, semilla);
        }
    }

    /**
     *
     * @param idprogpensum
     * @param escuela
     * @param semilla
     * @param documento
     */
    private void addDocente(String idprogpensum, String escuela, String semilla, String documento) {
        if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
            try {
                Long l = Long.valueOf(idprogpensum);
                BigInteger x = BigInteger.valueOf(l);

                List<SieduAsignaturasDocente> lstFormadores = new ArrayList<>();
                lstFormadores = this.serviceDocentes.findByProgamaDocente(BigInteger.valueOf(l), documento);
                if (lstFormadores != null && !lstFormadores.isEmpty()) {
                    for (SieduAsignaturasDocente doc : lstFormadores) {
                        boolean bPolicia = true;
                        if(doc.getEmpleado() != null && !doc.getEmpleado().toUpperCase().trim().equals("SI")) bPolicia = false;
                        
                        if (!this.moodleServicio.docenteAsignar(doc.getIdentificacion(), doc.getNombres(), doc.getApellidos(), doc.getCorreoElectronico(), doc.getUsuarioEmpresarial(),bPolicia)) {
                            this.moodleServicio.getServer().addLogError("FORMACION", "addDocente(" + idprogpensum + "," + escuela + "," + semilla + "," + doc.getIdentificacion(), this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                this.moodleServicio.getServer().addLogError("FORMACION", "addDocente(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, "JAVA", "JERROR", e.toString());
            }
        } else {
            this.addCurso(idprogpensum, escuela, semilla);
        }
    }

    /**
     *
     * @param idprogpensum
     * @param documento
     */
    private void deleteParticipante(String idprogpensum, String documento) {
        try {
            Long idP = Long.parseLong(idprogpensum);
            BigDecimal idPd;
            List<AlumnosAsignatura> lst = this.serviceAlumnos.findByProgamaAlumno(BigInteger.valueOf(idP), documento);
            if (lst != null && !lst.isEmpty()) {
                idPd = BigDecimal.valueOf(lst.get(0).getIdProgalumno().intValue());
                ProgramacionAlumnos alumnoA = this.serviceProgramaAlumnos.findById(idPd);
                if (alumnoA != null) {
                    this.serviceProgramaAlumnos.delete(alumnoA);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Aqui se desasigna un participante
     *
     * @param idprogpensum
     * @param escuela
     * @param semilla
     * @param documento
     */
    private void delParticipante(String idprogpensum, String escuela, String semilla, String documento) {
        if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
            if (!this.moodleServicio.alumnoDesasignar(documento)) {
                this.moodleServicio.getServer().addLogError("FORMACION", "delParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
            } else // Aqui se elimina el curso si no hay Participantes
            if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
                if (this.moodleServicio.getCurso().getAlumnos().isEmpty()) {
                    if (!this.moodleServicio.cursoEliminar()) {
                        this.moodleServicio.getServer().addLogError("FORMACION", "delCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                    } else {
                        this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                    }
                } else {
                    this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                }
            } else {
                this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
            }
        } else {
            this.moodleServicio.getServer().addLogError("FORMACION", "delParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
        }
    }

    /**
     *
     * @param idprogpensum
     * @param escuela
     * @param semilla
     * @param documento
     */
    private void delDocente(String idprogpensum, String escuela, String semilla, String documento) {
        if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
            if (!this.moodleServicio.docenteDesasignar(documento)) {
                this.moodleServicio.getServer().addLogError("FORMACION", "delParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
            }
        } else {
            this.moodleServicio.getServer().addLogError("FORMACION", "delParticipante(" + idprogpensum + "," + escuela + "," + semilla + "," + documento, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
        }
    }

    /**
     *
     * @param idprogpensum
     */
    private void updateFechas(Long idprogpensum) {
        try {
            SieduFechasMaxMinEventoDTO fechas = this.servicioPrograma.findByidProgDocente(BigDecimal.valueOf(idprogpensum));
            if (fechas != null) {
                this.moodleServicio.getCurso().setStartdate(fechas.getMinFecha());
                this.moodleServicio.getCurso().setEnddate(fechas.getMaxFecha());
                if (!this.moodleServicio.updateCurso()) {
                    this.moodleServicio.getServer().addLogError("FORMACION", "updateCurso(" + idprogpensum, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se crea el curso con los docentes y los participantes
     *
     * @param idprogpensum
     * @param escuela
     * @param semilla
     */
    private void addCurso(String idprogpensum, String escuela, String semilla) {
        if (this.moodleServicio.buscarCurso(semilla, idprogpensum, escuela)) {
            try {
                boolean bDocente = true;

                Long l = Long.valueOf(idprogpensum);
                // Se asigna los docentes...
                List<SieduAsignaturasDocente> lstFormadores = new ArrayList<>();
                lstFormadores = this.serviceDocentes.findByProgamaDocente(BigInteger.valueOf(l), null);
                if (lstFormadores != null && !lstFormadores.isEmpty()) {
                    for (SieduAsignaturasDocente doc : lstFormadores) { 
                        boolean bPolicia = true;
                        if(doc.getEmpleado() != null && !doc.getEmpleado().toUpperCase().trim().equals("SI")) bPolicia = false;                        
                        if (!this.moodleServicio.docenteAsignar(doc.getIdentificacion(), doc.getNombres(), doc.getApellidos(), doc.getCorreoElectronico(), doc.getUsuarioEmpresarial(),bPolicia)) {
                            this.moodleServicio.getServer().addLogError("FORMACION", "addCursoDoc(" + idprogpensum + "," + escuela + "," + semilla + "," + doc.getIdentificacion(), this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                            bDocente = false; 
                            break;
                        }
                    }
                }

                List<AlumnosAsignatura> lstAlumnos = new ArrayList<>();
                lstAlumnos = this.serviceAlumnos.findByProgamaAlumno(BigInteger.valueOf(l), null);
                if (bDocente) {
                    if (lstAlumnos != null && !lstAlumnos.isEmpty()) {
                        for (AlumnosAsignatura aln : lstAlumnos) {
                            boolean bPolicia = true;
                            if(aln.getEmpleado() != null && !aln.getEmpleado().toUpperCase().trim().equals("SI")) bPolicia = false;
                            if (!this.moodleServicio.alumnoAsignar(aln.getIdentificacion(), aln.getNombres(), aln.getApellidos(), aln.getCorreoElectronico(), aln.getUsuarioEmpresarial(),bPolicia)) {
                                this.moodleServicio.getServer().addLogError("FORMACION", "addCursoAlumnos(" + idprogpensum + "," + escuela + "," + semilla + "," + aln.getIdentificacion(), this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
                                this.deleteParticipante(idprogpensum, aln.getIdentificacion());
                            }
                        }
                    }
                    this.moodleServicio.usuariosDesasignar();

                    // Aqui se busca la fecha de los cursos
                    this.updateFechas(l);
                    this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                } else {
                    if(this.moodleServicio.cursoEliminar()) {
                        this.moodleServicio.getServer().addLogError("FORMACION", "delCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), "JERROR", "ERROR DELETE CURSOS");
                    }
                    try {
                        if (lstAlumnos != null && !lstAlumnos.isEmpty()) {
                            for (AlumnosAsignatura aln : lstAlumnos) {
                                this.deleteParticipante(idprogpensum, aln.getIdentificacion());
                            }
                        }
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                    
                }
            } catch (Exception e) {
                this.moodleServicio.getServer().addLogError("FORMACION", "addCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), "JERROR", e.toString());

                try {
                    Long l = Long.valueOf(idprogpensum);
                    List<AlumnosAsignatura> lstAlumnos = new ArrayList<>();
                    lstAlumnos = this.serviceAlumnos.findByProgamaAlumno(BigInteger.valueOf(l), null);
                    if (lstAlumnos != null && !lstAlumnos.isEmpty()) {
                        for (AlumnosAsignatura aln : lstAlumnos) {
                            this.deleteParticipante(idprogpensum, aln.getIdentificacion());
                        }
                    }
                } catch (Exception e1) {
                }
            }
        } else {
            this.moodleServicio.getServer().addLogError("FORMACION", "addCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
            try {
                Long l = Long.valueOf(idprogpensum);
                List<AlumnosAsignatura> lstAlumnos = new ArrayList<>();
                lstAlumnos = this.serviceAlumnos.findByProgamaAlumno(BigInteger.valueOf(l), null);
                if (lstAlumnos != null && !lstAlumnos.isEmpty()) {
                    for (AlumnosAsignatura aln : lstAlumnos) {
                        this.deleteParticipante(idprogpensum, aln.getIdentificacion());
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void updCurso(String idprogpensum, String escuela, String semilla) {
        if (this.moodleServicio.buscarCursoNoCreate(semilla, idprogpensum, escuela)) {
            try {
                Long l = Long.valueOf(idprogpensum);
                this.updateFechas(l);
                this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
            } catch (Exception e) {
                this.moodleServicio.getServer().addLogError("FORMACION", "updCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), "JERROR", e.toString());
            }
        } else {
            this.moodleServicio.getServer().addLogError("FORMACION", "updCurso(" + idprogpensum + "," + escuela + "," + semilla, this.moodleServicio.getError().getExeption(), this.moodleServicio.getError().getErrorcode(), this.moodleServicio.getError().getMessage());
        }
    }

    /**
     * Busca una clave en los parametros para obtener su valor Se puede indicar
     * un valor por defecto
     *
     * @param params
     * @param key
     * @param vDefault
     * @return
     */
    private String seekParameter(Map params, String key, String vDefault) {
        String sResult = vDefault;
        if (params.containsKey(key) && params.get(key) != null) {
            sResult = (String) params.get(key);
        }
        return sResult;
    }

    /**
     * Busca una clave en los parametros para obtener su valor Asigna vacio si
     * no lo consigue
     *
     * @param params
     * @param key
     * @return
     */
    private String seekParameter(Map params, String key) {
        return seekParameter(params, key, "");
    }

    /**
     * Se prepara la variable result para imprimir en la vista con CDATA
     *
     * @param pCode
     * @param pMessage
     */
    private void setReturn(String pCode, String pMessage) {
        this.setReturn(pCode, pMessage, true);
    }

    /**
     * Se prepara la variable result para imprimir en la vista indicado si es no
     * con CDATA en el resultado
     *
     * @param pCode
     * @param pMessage
     * @param pCdata
     */
    private void setReturn(String pCode, String pMessage, boolean pCdata) {
        this.result += "\t\t<result>\n";
        if (pCdata) {
            this.result += "\t\t\t<code><![CDATA[" + pCode + "]]></code>\n";
        } else {
            this.result += "\t\t\t<code>" + pCode + "</code>\n";
        }
        if (pCdata) {
            this.result += "\t\t\t<message><![CDATA[" + pMessage + "]]></message>\n";
        } else {
            this.result += "\t\t\t<message>" + pMessage + "</message>\n";
        }
        this.result += "\t\t</result>\n";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String valor) {
        this.result = valor;
    }

}
