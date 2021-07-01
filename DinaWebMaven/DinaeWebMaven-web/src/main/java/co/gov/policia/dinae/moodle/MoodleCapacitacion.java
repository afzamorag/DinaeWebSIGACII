/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle;
import co.gov.policia.dinae.siedu.controladores.SieduEventoEscuelaController;
import co.gov.policia.dinae.siedu.modelo.SieduDatosEmpleadoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduDocenteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.List;
/**
 * 
 * @author ferney
 * Controlador que se comunica con Moodle para cursos de Capacitacion
 * Crea y Actualiza Cursos
 * Asigna y deasigna Alumnos y Docentes
 * Crea Usuarios.  
 */
public class MoodleCapacitacion extends MoodleFormacion {
    /**
     * 
     */
    private static final long serialVersionUID = -3117335603605258592L;

    private SieduEventoEscuelaController controler;

    /**
     * Constructor
     * @param control 
     */
    public MoodleCapacitacion(SieduEventoEscuelaController control) {
            super(control.getServiceLog(),control.getServiceUri(), "C");
            this.controler = control;
    }

    /**
     * Crea el curso segun su semila y si existe actualiza las fechas
     * @return 
     */
    public boolean onSave() {
        if (!this.buscarCurso(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            this.getServer().addLogError(this.titulo, "buscarCurso " + this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
        } else {
            this.updateFechas(this.controler.getSelected().getEveeFechaini(),this.controler.getSelected().getEveeFechafin());
            return true;
        }
        return false;
    }
    
    /**    
     * Procediminto que actualiza solo las fechas.
     */
    public void cursoUpdateOnly() {
        if (this.buscarCursoNoCreate(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            this.updateFechas(this.controler.getSelected().getEveeFechaini(),this.controler.getSelected().getEveeFechafin());
        } else {
            this.getServer().addLogError(this.titulo, "buscarCurso " + this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
        }
    }
    
    /**
     * Sincroniza el curso en moodle con los docentes y alumnos de sigac. 
     */
    public void moodleMigrate() {
        if (this.buscarCurso(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            try {
                for (int i = this.controler.getSelected().getSieduParticipanteEventoList().size() - 1; i >= 0; i--) {
                    SieduParticipanteEvento a = this.controler.getSelected().getSieduParticipanteEventoList().get(i);
                    SieduDatosEmpleadoDTO datosEmpleado = new SieduDatosEmpleadoDTO();
                    try {
                        datosEmpleado = this.controler.getServiceParticipante().findDatosEmpleadoPolicial(Long.valueOf(a.getParePers().getPersNroid()));
                    if (datosEmpleado == null) {
                        datosEmpleado = new SieduDatosEmpleadoDTO();
                    }
                    } catch (Exception ey) {}
                    if (!this.alumnoAsignar(a.getParePers(), datosEmpleado)) {
                        try {
                            if (this.getError() != null) {
                                this.getServer().addLogError(this.titulo, "add alumno " + a.getParePers().getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                            } else {
                                this.getServer().addLogError(this.titulo, "add alumno " + a.getParePers().getPersNroid(), "moodle", "usuario", "datos incompletos");
                            }
                            a.setPareUsuMod(this.controler.getLoginFaces().getPerfilUsuarioDTO().getUsuarioLogueado());
                            this.controler.getServiceParticipante().delete(a);
                            this.controler.getSelected().getSieduParticipanteEventoList().remove(i);
                        } catch (Exception ex) {
                            this.controler.addErrorMessage(this.controler.getPropertyFromBundle("commons.msg.error.read.list.summary"), this.controler.getPropertyFromBundle("commons.msg.error.read.list.detail"));
                        }
                    }
                }
                List<SieduDocenteEvento> docentes = this.controler.getServiceDocente().findListById(this.controler.getSelected());
                for (int i = docentes.size() - 1; i >= 0; i--) {
                    SieduDocenteEvento b = docentes.get(i);
                    SieduDatosEmpleadoDTO datosEmpleado = new SieduDatosEmpleadoDTO();
                    try {
                        datosEmpleado = this.controler.getServiceParticipante().findDatosEmpleadoPolicial(Long.valueOf(b.getDocePers().getPersNroid()));
                    if (datosEmpleado == null) {
                        datosEmpleado = new SieduDatosEmpleadoDTO();
                    }
                    } catch (Exception ey) {}
                    if (!this.docenteAsignar(b.getDocePers(),datosEmpleado)) {
                        try {
                            if (this.getError() != null) {
                                this.getServer().addLogError(this.titulo, "add docente " + b.getDocePers().getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                            } else {
                                this.getServer().addLogError(this.titulo, "add docente " + b.getDocePers().getPersNroid(), "moodle", "docente", "datos incompletos");
                            }
                            this.controler.getServiceDocente().delete(b);
                            docentes.remove(i);
                        } catch (Exception ex) {
                        }
                    }
                }
                this.usuariosDesasignar();
                this.updateFechas(this.controler.getSelected().getEveeFechaini(),this.controler.getSelected().getEveeFechafin());
            } catch (Exception e) {
            }
        } else {
            this.getServer().addLogError(this.titulo, "buscarCurso " + this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
        }
    }

    /**
     * Asigna un estudiante al curso
     *
     * @param persona
     * @return 
     */
    public boolean moodleMigrateAsignarAlumno(SieduPersona persona) {
        SieduDatosEmpleadoDTO datosEmpleado = new SieduDatosEmpleadoDTO();
        try {
            datosEmpleado = this.controler.getServiceParticipante().findDatosEmpleadoPolicial(Long.valueOf(persona.getPersNroid()));
            if (datosEmpleado == null) {
                datosEmpleado = new SieduDatosEmpleadoDTO();
            }
        } catch (Exception ex) {
        }
        if (this.buscarCursoNoCreate(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            try {
                if (!this.alumnoAsignar(persona, datosEmpleado)) {
                    if (this.getError() != null) {
                        this.getServer().addLogError(this.titulo, "add alumno " + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                    } else {
                        this.getServer().addLogError(this.titulo, "add alumno " + persona.getPersNroid(), "moodle", "alumno", "datos incompletos");
                    }
                    return false;
                } else {
                    this.getServer().addLogError(this.titulo, "buscarCurso " + this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                    return false;
                }
            } catch (Exception e) {
            }
        } else if (this.buscarCurso(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            this.updateFechas(this.controler.getSelected().getEveeFechaini(),this.controler.getSelected().getEveeFechafin());
            if (!this.alumnoAsignar(persona, datosEmpleado)) {
                this.getServer().addLogError(this.titulo, "add alumno " + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                return false;
            }
        } else {
            //else agregado 17052018 cuando se inserta el alumno y no hay curso lo mantiene en SIGAC ya que por defecto el método retorna true, ver line 786
            return false;
        }
        return true;
    }
    
    /**
     * Desasigna un estudiante al curso
     * @param persona
     * @return 
     */
    public boolean moodleMigrateDeasignarAlumno(SieduPersona persona) {
        if (this.buscarCursoNoCreate(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            if (!this.alumnoDesasignar(persona.getPersNroid())) {
                this.getServer().addLogError(this.titulo, "del alumno " + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
            } else if(this.controler.getSelected().getSieduParticipanteEventoList().size() < 2) {
                if (!this.cursoEliminar()) {
                    this.getServer().addLogError(this.titulo, "delCurso(" + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                }
            }
        }
        return true;
    }

    /**
     * Asigna un docente al curso
     * @param persona
     * @return 
     */
    public boolean moodleMigrateAsignarDocente(SieduPersona persona) {
        SieduDatosEmpleadoDTO datosEmpleado = new SieduDatosEmpleadoDTO();
        try {
            datosEmpleado = this.controler.getServiceParticipante().findDatosEmpleadoPolicial(Long.valueOf(persona.getPersNroid()));
            if (datosEmpleado == null) {
                datosEmpleado = new SieduDatosEmpleadoDTO();
            }
        } catch (Exception ex) {}
        if (this.buscarCursoNoCreate(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            try {
                if (!this.docenteAsignar(persona, datosEmpleado)) {
                    this.getServer().addLogError(this.titulo, "add docente " + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
                    return false;
                }
            } catch (Exception e) {
            }
        } else {
            //Agregada 17052018 valida cuando no se puede crear el docente en Moodle
            return false;
        }
        return true;
    }

    /**
     * Desasigna un docente en el curso
     * @param persona
     * @return 
     */
    public boolean moodleMigrateDeasignarDocente(SieduPersona persona) {
        if (this.buscarCursoNoCreate(this.controler.getSelected().getEveeCapa().getCarrera().getCarrerasPK().getIdCarrera().toString(), this.controler.getSelected().getEveeEvee().toString(), this.controler.getSelected().getEveeCapa().getEscuela().getSiglaFisica())) {
            if (!this.docenteDesasignar(persona.getPersNroid())) {
                this.getServer().addLogError(this.titulo, "del docente " + persona.getPersNroid(), this.getError().getExeption(), this.getError().getErrorcode(), this.getError().getMessage());
            }
        }
        return true;
    }
    
        
}
