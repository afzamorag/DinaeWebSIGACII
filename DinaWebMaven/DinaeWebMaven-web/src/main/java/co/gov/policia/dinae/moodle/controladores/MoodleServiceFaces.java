/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.controladores;

import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.modelo.AlumnosAsignatura;
import co.gov.policia.dinae.siedu.modelo.LogMoodleSendData;
import co.gov.policia.dinae.siedu.modelo.ProgramacionAlumnos;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import co.gov.policia.dinae.siedu.servicios.LogMoodleSendDataService;
import co.gov.policia.dinae.siedu.servicios.SieduAlumnosAsignaturaService;
import co.gov.policia.dinae.siedu.servicios.SieduEventoEscuelaService;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import co.gov.policia.dinae.siedu.servicios.SieduProgramacionAlumnosService;
import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
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
@Named(value = "MoodleServiceFaces")
@RequestScoped
public class MoodleServiceFaces extends AbstractController implements Serializable{
    
    // token para validar la petición
    private String token = "token123456789moodle";
    private String result = ""; 

    @EJB
    private SieduEventoEscuelaService eventoService;
    
    @EJB
    private SieduParticipanteEventoService serviceParticipante;
    
    @EJB
    private SieduPersonaService servicePersona;

    @EJB
    private SieduAlumnosAsignaturaService serviceSeekAlumnos;

    @EJB
    private SieduProgramacionAlumnosService serviceAlumnos;
    
    @EJB
    private LogMoodleSendDataService serviceLog;
    
    
    @PostConstruct
    private void init() {
        this.result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        /**
         * Se asigno el operador += para concatenar el encabezado
         * @lastmodby: Julio Lopez
         * @lastmoddt: 24/11/2017
         */
        this.result += "\t<root>\n";
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext != null) {
                HttpServletRequest hRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                if(hRequest != null) {
                    Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    if(params != null && !params.isEmpty()) {
                        if(params.containsKey("wsToken")) {
                            if(params.get("wsToken") != null && params.get("wsToken").equals(this.token)) {
                                String tipo = seekParameter(params,"tipo");
                                String curso= seekParameter(params,"curso");
                                String documento = seekParameter(params,"documento");
                                String notas = seekParameter(params,"notas","0");
                                String estado = seekParameter(params,"estado");
                                if(tipo.toLowerCase().equals("f")) {
                                    this.cargarNotaFormador(curso, documento, notas);
                                } else if(tipo.toLowerCase().equals("c")) {
                                    this.cargarEstadoCapacitacion(curso, documento, estado);
                                } else {
                                    this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "ETIPO", "TIPO INCORRECTO");
                                }
                            } else {
                                this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "ITOKEN", "NO EXISTE EL TOKEN");
                            }
                        } else {
                            this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "ITOKEN", "NO EXISTE EL TOKEN");
                        }
                    } else {
                        this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "NPAAMETERS", "NO HAY PARAMETROS PARA LA PETICIÓN");
                    }
                } else {
                    this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "NREQUEST", "NO SE CARGO EL REQUEST");
                }
            } else {
                this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "NCONTEXT", "NO SE CARGO EL CONTEXTO");
            }
        } catch (Exception e) {
            this.addLogError("MOODLESERVICIO", "N/A", "EXECUTE", "JERROR", e.toString());
        }
        this.result += "\t</root>";
    }
    
    /**
     * Se carga el estado del estudiante para el curso de capacitación
     * @param idCursoSemUnidad
     * @param documento
     * @param estado 
     */
    private void cargarEstadoCapacitacion(String idCursoSemUnidad, String documento, String estado) {
        if(idCursoSemUnidad.isEmpty()) {
            this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "EXECUTE", "NCURSO", "NO SE INNDICO EL CURSO");
        } else if(documento.isEmpty()) {
            this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "EXECUTE", "NDOCUMENTO", "NO SE INNDICO EL DOCUMENTO");
        } else if(estado.isEmpty() || 
                (!estado.toUpperCase().equals("A") &&
                 !estado.toUpperCase().equals("N") &&
                 !estado.toUpperCase().equals("D"))) {
            this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "EXECUTE", "NESTADO", "NO SE INNDICO EL ESTADO O NO ES UN VALOR VALIDO");
        } else {
            try {
                String sEstado = "X";
                 switch (estado.toUpperCase()) {
                     case "A":
                         sEstado = "APROBADO";
                         break;
                     case "N":    
                         sEstado = "NO APROBADO";
                         break;
                     default:    
                         sEstado = "DESERTADO";
                 }
                SieduEventoEscuela evento = this.buscarEventoCapacitacion(idCursoSemUnidad); 
                if(evento != null) {
                    SieduPersona persona = this.buscarPeronaCapacitacion(documento);
                    if (persona != null) {
                         SieduParticipanteEvento participante = this.buscarRegistroParticipanteCapacitacion(evento, persona);
                         if(participante != null) {
                             try {
                                 participante.setPareEstado(sEstado);
                                 participante.setPareIpMod("MOODLE");
                                 participante.setPareFechaMod(new Date());
                                 participante.setPareUsuMod("MOODLE");
                                 participante.setPareMaquinaMod("srvpvirtual");
                                 serviceParticipante.update(participante);
                                this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                             } catch (Exception e) {
                                this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "JAVA", "JERROR", e.toString());
                             }
                         } else {
                             this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "SIGAC", "FPARTICIPANTE", "NO SE ENCONTRO EL PARTICIPANTE");
                         }
                    } else {
                        this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "SIGAC", "FUSUARIO", "NO HAY UN USUARIO CON ESE DOCUMENTO");
                    }
                } else {
                    this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "MOODLE", "FCURSO", "NO SE ENCONTRO EL CURSO");
                }
            } catch (Exception e) {
                this.addLogError("CAPACITACION", "cargarEstadoCapacitacion(" + idCursoSemUnidad + "," + documento + "," + estado + ")", "JAVA", "JERROR", e.toString());
            }
        }
    }
    
    /**
     * Busnca el ojecto Participante del evento..
     * @param evento
     * @param persona
     * @return 
     */
    private SieduParticipanteEvento buscarRegistroParticipanteCapacitacion(SieduEventoEscuela evento, SieduPersona persona) {
        try {
            SieduParticipanteEvento participante = serviceParticipante.findByPareeveeParepers(evento, persona);
            return participante;
        } catch (Exception e) {
            this.addLogError("CAPACITACION", "buscarRegistroParticipanteCapacitacion", "SIGAC", "JERROR", e.toString());
        }
        return null;
    }

    /**
     * Busca el objeto persona.
     * @param documento
     * @return 
     */
    private SieduPersona buscarPeronaCapacitacion(String  documento) {
        try {
            SieduPersona persona = servicePersona.findByIdentificacion(documento);
            return persona;
        } catch (Exception e) {
            this.addLogError("CAPACITACION", "buscarPeronaCapacitacion(" + documento + ")", "SIGAC", "JERROR", e.toString());
        }
        return null;
    }
    
    /**
     * Devuelve el objeto evento
     * @param idCursoSemUnidad
     * @return 
     */
    private SieduEventoEscuela buscarEventoCapacitacion(String idCursoSemUnidad) {
        try {
            SieduEventoEscuela evento =  eventoService.findById(Long.valueOf(idCursoSemUnidad));
            return evento;
        } catch (Exception e) {
            this.addLogError("CAPACITACION", "buscarEventoCapacitacion(" + idCursoSemUnidad + ")", "SIGAC", "JERROR", e.toString());
        }
        return null;
    }
    
    /**
     * Se carga la nota al estudiante para el curso de formación
     * @param idProgPemsun
     * @param documento
     * @param nota 
     */
    private void cargarNotaFormador(String idProgPemsun, String documento, String nota) {
        if(idProgPemsun.isEmpty()) {
            this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "EXECUTE", "NCURSO", "NO SE INNDICO EL CURSO");
        } else if(documento.isEmpty()) {
            this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "EXECUTE",  "NDOCUMENTO", "NO SE INNDICO EL DOCUMENTO");
        } else if(nota.isEmpty()) {
            this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "EXECUTE",  "NNOTA", "NO SE INNDICO LA NOTA");
        } else {
            String[] aNotas = nota.split(";");
            if(aNotas.length == 4 ) {
                Long idP = Long.parseLong(idProgPemsun);
                BigDecimal idPd; 
                try {
                    List<AlumnosAsignatura> lst = serviceSeekAlumnos.findByProgamaAlumno(BigInteger.valueOf(idP),documento);
                    if(lst != null && !lst.isEmpty()) {
                        //idP = lst.get(0).getIdProgalumno().longValue();
                        idPd = BigDecimal.valueOf(lst.get(0).getIdProgalumno().intValue());
                        //ProgramacionAlumnos alumno = this.serviceAlumnos.findById(idPd);
                        ProgramacionAlumnos alumno = this.serviceAlumnos.findById(idPd);
                        if(alumno != null) {
                            alumno.setParcial1(StringToDouble(aNotas[0]));
                            alumno.setParcial2(StringToDouble(aNotas[1]));
                            alumno.setParcial3(StringToDouble(aNotas[2]));
                            alumno.setDefinitiva(StringToDouble(aNotas[3]));
                            this.serviceAlumnos.update(alumno);
                             this.setReturn("SUCCESSFUL", "OPERACION SATISFACTORIA");
                        } else {
                            this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "SIGAC", "RALUMNO", "ALUMNO NO ENCONTRADO");
                        }
                    } else {
                        this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "SIGAC", "RALUMNO", "ALUMNO NO ENCONTRADO");
                    }
                } catch (Exception e) {
                    this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "JAVA", "JAVA", e.toString());
                }
            } else {
                this.addLogError("FORMACION", "cargarNotaFormador(" + idProgPemsun + "," + documento + "," + nota + ")", "EXECUTE", "RNOTA", "NOTAS INVALIDAS");
            }
        }
    }
    
    
    /**
     * Convierte un String a Double
     * Si el string no es numerico devuelve nulo
     * @param sValor
     * @return 
     */
    private BigDecimal StringToDouble(String sValor) {
        BigDecimal retVal = BigDecimal.ZERO;
        try {
            Double x = Double.parseDouble(sValor);
            retVal = BigDecimal.valueOf(x);
        } catch (Exception e) {}
        return retVal;
    }
    
    /**
     * Busca una clave en los parametros para obtener su valor
     * Se puede indicar un valor por defecto
     * @param params
     * @param key
     * @param vDefault
     * @return 
     */
    private String seekParameter(Map params, String key, String vDefault) {
        String sResult = vDefault;
        if(params.containsKey(key) && params.get(key) != null) {
            sResult = (String)params.get(key);
        }
        return sResult;
    }
    
    /**
     * Busca una clave en los parametros para obtener su valor
     * Asigna vacio si no lo consigue
     * @param params
     * @param key
     * @return 
     */
    private String seekParameter(Map params, String key) {
        return seekParameter(params,key,"");
    }
    
    /**
     * Guarda el log del error
     * @param modulo
     * @param item
     * @param tipo
     * @param pCode
     * @param pMessage 
     */
    private void addLogError(String modulo, String item, String tipo, String pCode, String pMessage) {
        try {
            this.setReturn(pCode, pMessage,false);
            LogMoodleSendData lError = new LogMoodleSendData();
            lError.setFecha(new Date());
            lError.setModulo(modulo);
            lError.setItem(item);
            lError.setTipo(tipo);
            lError.setCodigo(pCode);
            lError.setDescripcion(pMessage);
            lError = this.serviceLog.create(lError);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Se prepara la variable result 
     * para imprimir en la vista con CDATA
     * @param pCode
     * @param pMessage 
     */
    private void setReturn(String pCode, String pMessage) {
        this.setReturn(pCode, pMessage, true);
    }

    /**
     * Se prepara la variable result 
     * para imprimir en la vista
     * indicado si es no con CDATA en el resultado
     * @param pCode
     * @param pMessage
     * @param pCdata 
     */
    private void setReturn(String pCode, String pMessage, boolean pCdata) {
        this.result  += "<result>";
        if(pCdata) {
            this.result += "\t<code><![CDATA[" + pCode + "]]></code>";
        } else {
            this.result += "\t<code>" + pCode + "</code>";
        }
        if(pCdata) {
            this.result += "\t<message><![CDATA[" + pMessage + "]]></message>";
        } else {
            this.result += "\t<message>" + pMessage + "</message>";
        }
        this.result += "</result>";
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String valor) {
        this.result = valor;
    }
    
}
