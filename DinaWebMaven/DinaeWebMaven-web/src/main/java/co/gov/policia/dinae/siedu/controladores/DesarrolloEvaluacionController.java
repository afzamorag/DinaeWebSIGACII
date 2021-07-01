package co.gov.policia.dinae.siedu.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.dto.AspectoDTO;
import co.gov.policia.dinae.siedu.dto.EventoDTO;
import co.gov.policia.dinae.siedu.dto.PreguntaDTO;
import co.gov.policia.dinae.siedu.filtros.EventoFiltro;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrollo;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrolloPK;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipante;
import co.gov.policia.dinae.siedu.modelo.EvaluacionParticipantePK;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.CompetenciaService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionDesarrolloService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionParticipanteService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionService;
import co.gov.policia.dinae.siedu.servicios.ParametroEvaluacionService;
import co.gov.policia.dinae.siedu.servicios.PreguntaEvaluacionService;
import co.gov.policia.dinae.siedu.servicios.SieduEventoEscuelaService;
import co.gov.policia.dinae.siedu.servicios.SieduEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Juan Jose Buzon
 */
@Named
@SessionScoped
public class DesarrolloEvaluacionController extends AbstractController {

    /**
     *
     */
    private static final long serialVersionUID = -1846704086471615665L;

    private static final Logger LOG = LoggerFactory.getLogger(DesarrolloEvaluacionController.class);

    @EJB
    private APPService serviceAPP;
    @EJB
    private EvaluacionService evaluacionService;
    @EJB
    private PreguntaEvaluacionService preguntaEvaluacionService;
    @EJB
    private CompetenciaService competenciaService;
    @EJB
    private ParametroEvaluacionService parametroEvaluacionService;
    @EJB
    private SieduEventoService eventoService;
    @EJB
    private SieduParticipanteEventoService participanteEventoService;
    @EJB
    private EvaluacionDesarrolloService evaluacionDesarrolloService;
    @EJB
    private SieduEventoEscuelaService serviceEventoEscuela;

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;

    private List<EventoDTO> eventos;
    private Long idEvento;
    private EventoDTO evento;
    private Evaluacion evaluacion;
    private SieduParticipanteEvento participanteEvento;
    private List<AspectoDTO> aspectos;
    private String sugerencia;
    private boolean disabled;

    public DesarrolloEvaluacionController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        cargarEventos();
    }

    public String initReturnCU() throws Exception {
        releaseControllers();
        return navigationFaces.redirectCUDesarrolloEvaluacion();
    }

    private void cargarEventos() {
        LOG.trace("metodo: cargarEventos()");
        try {
            EventoFiltro filtro = null;
            eventos = eventoService.findForEvaluationByParticipant(loginFaces.getPerfilUsuarioDTO().getIdentificacion());
        } catch (Exception e) {
            LOG.error("metodo: cargarTiposParametros() ->> mensaje: {}",
                    e.getMessage());
            addErrorMessage(
                    getPropertyFromBundle("commons.msg.error.read.list.summary"),
                    getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String desarrollarEvaluacion() {
        LOG.trace("metodo: desarrollarEvaluacion()");
        String outcome = null;
        try {
            for (EventoDTO evento1 : eventos) {
                if (idEvento.equals(evento1.getIdEvento())) {
                    this.evento = evento1;
                    break;
                }
            }
            participanteEvento = participanteEventoService.findById(evento.getIdParticipanteEvento());
            SieduEventoEscuela ee = serviceEventoEscuela.findById(participanteEvento.getPareEvee().getEveeEvee());
            this.evaluacion = ee.getEvaluacion();
            if (evaluacion != null) {
                cargarAspectos();
                this.disabled = false;
                outcome = "desarrolloEvaluacion.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            LOG.error("metodo: desarrollarEvaluacion() ->> mensaje: {}",
                    e.getMessage());
            addErrorMessage(
                    getPropertyFromBundle("commons.msg.error.read.list.summary"),
                    getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
        return outcome;
    }

    private void cargarAspectos() {
        this.aspectos = new ArrayList<>();
        AspectoDTO aspectoDTO = null;
        PreguntaDTO preguntaDTO = null;
        List<PreguntaEvaluacion> preguntasEvaluacion = evaluacion.getPreguntasEvaluacion();
        Collections.sort(preguntasEvaluacion, new Comparator<PreguntaEvaluacion>() {
            @Override
            public int compare(PreguntaEvaluacion o1, PreguntaEvaluacion o2) {
                return o1.getAspecto().getId().compareTo(o2.getAspecto().getId());
            }
        });
        for (PreguntaEvaluacion preguntaEvaluacion : preguntasEvaluacion) {
            if (aspectoDTO == null || !preguntaEvaluacion.getAspecto().getId().equals(aspectoDTO.getIdParametroEvaluacion())) {
                aspectoDTO = new AspectoDTO();
                aspectoDTO.setIdParametroEvaluacion(preguntaEvaluacion.getAspecto().getId());
                aspectoDTO.setDescripcion(preguntaEvaluacion.getAspecto().getDescripcion());
                aspectoDTO.setPreguntasDTO(new ArrayList<PreguntaDTO>());
                this.aspectos.add(aspectoDTO);
            }
            preguntaDTO = new PreguntaDTO();
            preguntaDTO.setPreguntaEvaluacionPK(preguntaEvaluacion.getId());
            preguntaDTO.setIdParametroEvaluacion(preguntaEvaluacion.getPregunta().getId());
            preguntaDTO.setDescripcion(preguntaEvaluacion.getPregunta().getDescripcion());

            aspectoDTO.getPreguntasDTO().add(preguntaDTO);
        }

    }

    public String guardarDesarrolloEvaluacion() {
        LOG.trace("metodo: guardarDesarrolloEvaluacion()");
        String outcome = null;
        try {
            List<EvaluacionDesarrollo> evaluacionDesarrollos = new ArrayList<>();
            boolean complete = true;
            EvaluacionDesarrollo evaluacionDesarrollo = null;
            EvaluacionDesarrolloPK evaluacionDesarrolloPK = null;
            for (AspectoDTO aspectoDTO : aspectos) {
                for (PreguntaDTO preguntaDTO : aspectoDTO.getPreguntasDTO()) {
                    evaluacionDesarrolloPK = new EvaluacionDesarrolloPK();
                    evaluacionDesarrolloPK.setIdEvaluacion(preguntaDTO.getPreguntaEvaluacionPK().getIdEvaluacion());
                    evaluacionDesarrolloPK.setIdParticipanteEvento(evento.getIdParticipanteEvento());
                    evaluacionDesarrolloPK.setIdPregunta(preguntaDTO.getPreguntaEvaluacionPK().getIdPregunta());

                    evaluacionDesarrollo = new EvaluacionDesarrollo();
                    evaluacionDesarrollo.setEvaluacionDesarrolloPK(evaluacionDesarrolloPK);
                    if (preguntaDTO.getValor() == null) {
                        complete = false;
                    } else {
                        evaluacionDesarrollo.setValor(preguntaDTO.getValor());
                    }
                    evaluacionDesarrollo.setUsuarioCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                    evaluacionDesarrollos.add(evaluacionDesarrollo);
                }
            }

            evaluacionDesarrolloService.create(evaluacionDesarrollos, this.sugerencia);
            this.disabled = true;
            this.eventos.remove(new EventoDTO(idEvento));
            idEvento = null;
//            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            outcome = "filtroDesarrolloEvaluacion.xhtml?faces-redirect=true";
        } catch (Exception e) {
            LOG.error("metodo: guardarDesarrolloEvaluacion() ->> mensaje: {}",
                    e.getMessage());
            addErrorMessage(
                    getPropertyFromBundle("commons.msg.error.save.summary"),
                    getPropertyFromBundle("commons.msg.error.save.detail"));
        }
        return outcome;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public List<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<AspectoDTO> getAspectos() {
        return aspectos;
    }

    public void setAspectos(List<AspectoDTO> preguntasEvaluacion) {
        this.aspectos = preguntasEvaluacion;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO eventoDTO) {
        this.evento = eventoDTO;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
