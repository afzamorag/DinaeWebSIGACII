/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.policia.dinae.modelo.Carreras;
import co.gov.policia.dinae.modelo.NivelesAcademicos;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.constantes.EstadoEvaluacionEnum;
import co.gov.policia.dinae.siedu.constantes.SieduEventoEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.TipoParametroEvaluacionEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.dto.PreguntaEvaluacionReporteDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.EvaluacionFiltro;
import co.gov.policia.dinae.siedu.filtros.EventoFiltro;
import co.gov.policia.dinae.siedu.modelo.Categoria;
import co.gov.policia.dinae.siedu.modelo.CategoriaPK;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.Evaluacion;
import co.gov.policia.dinae.siedu.modelo.EvaluacionDesarrollo;
import co.gov.policia.dinae.siedu.modelo.Grado;
import co.gov.policia.dinae.siedu.modelo.GradoPK;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.ParametroEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacion;
import co.gov.policia.dinae.siedu.modelo.PreguntaEvaluacionPK;
import co.gov.policia.dinae.siedu.modelo.SieduCompetencia;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGrado;
import co.gov.policia.dinae.siedu.modelo.SieduEvalGradoPK;
import co.gov.policia.dinae.siedu.modelo.SieduEvento;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.CompetenciaService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionDesarrolloService;
import co.gov.policia.dinae.siedu.servicios.EvaluacionService;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.servicios.ParametroEvaluacionService;
import co.gov.policia.dinae.siedu.servicios.PreguntaEvaluacionService;
import co.gov.policia.dinae.siedu.servicios.SieduEventoService;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import co.gov.policia.dinae.siedu.util.reports.GeneradorReportes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Juan Jose Buzon
 */
@Named
@SessionScoped
public class EvaluacionController extends AbstractController {

  private static final long serialVersionUID = -8442109553673864055L;

  private static final Logger LOG = LoggerFactory.getLogger(EvaluacionController.class);

  @EJB
  private APPService serviceAPP;
  @EJB
  private EvaluacionService evaluacionService;
  @EJB
  private PreguntaEvaluacionService preguntaEvaluacionService;
  @EJB
  private CompetenciaService competenciaService;
  @EJB
  private PAEService paeService;
  @EJB
  private ParametroEvaluacionService parametroEvaluacionService;
  @EJB
  private SieduEventoService eventoService;
  @EJB
  private EvaluacionDesarrolloService evaluacionDesarrolloService;

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;

  private EvaluacionFiltro filtroEvaluaciones;
  private List<PAE> paes;
  private List<Dominio> procesos;
  private List<NivelesAcademicos> nivelesAcademicos;
  private List<Carreras> programasAcademicosFiltro;
  private List<Carreras> programasAcademicos;
  private List<Dominio> modalidades;
  private List<Categoria> categorias;
  private List<Categoria> categoriasEvaluacion;
  private List<Grado> gradosFiltro;
  private List<Grado> grados;
  private List<Evaluacion> evaluacionesFilter;
  private Evaluacion selected;
  private List<Evaluacion> evaluaciones;
  private List<ParametroEvaluacion> aspectos;
  private List<ParametroEvaluacion> preguntas;
  private List<ParametroEvaluacion> factores;
  private List<SieduCompetencia> competencias;
  private PreguntaEvaluacion preguntaEvaluacion;
  private String outcomeCancelarPregunta;
  private NavEnum optionNavEnum;
  private boolean validaRol = false;

  public EvaluacionController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    filtroEvaluaciones = new EvaluacionFiltro();
    evaluaciones = new ArrayList<>();
    grados = new ArrayList<>();
    optionNavEnum = NavEnum.LIST;
    validaRol();
    cargarVigencias();
    cargarProcesos();
    cargarNivelesAcademicos();
    cargarModalidades();
    cargarCategorias();
    cargarEvaluacionesFilter();
  }

  public String initReturnCU() throws Exception {
    releaseControllers();
    return navigationFaces.redirectCUAdministrarEvaluaciones();
  }

  public boolean validaRol() {
    validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
    return validaRol;
  }

  private void cargarParametrosPreguntasEvaluacion() {
    LOG.trace("metodo: cargarParametrosEvaluacion()");
    try {
      competencias = competenciaService.consultarCompetencias();
      aspectos = parametroEvaluacionService.findByType(TipoParametroEvaluacionEnum.ASPECTOS.getCode());
      preguntas = parametroEvaluacionService.findByType(TipoParametroEvaluacionEnum.PREGUNTAS.getCode());
    } catch (Exception ex) {
      LOG.error("metodo: cargarParametrosEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void cargarGradosPorCategorias() {
    LOG.trace("method: gradosPorCategorias()");
    if (optionNavEnum == NavEnum.LIST) {
      if (filtroEvaluaciones.getPloblacionNivel() == null || filtroEvaluaciones.getPloblacionNivel().isEmpty()) {
        gradosFiltro = null;
      } else {
        gradosFiltro = cargarGrados(filtroEvaluaciones.getPloblacionNivel());
      }
    } else if (optionNavEnum == NavEnum.NEW || optionNavEnum == NavEnum.EDIT) {
      if (selected.getCategoriaPks() == null || selected.getCategoriaPks().isEmpty()) {
        grados = null;
      } else {
        grados = cargarGrados(selected.getCategoriaPks());
      }
    }
  }

  private List<Grado> cargarGrados(List<CategoriaPK> categoriaPKs) {
    try {
      return serviceAPP.consultarGradosVigentesPorCategorias(categoriaPKs);
    } catch (Exception ex) {
      LOG.error("metodo: cargarGrados() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
      return null;
    }
  }

  public void cargarModalidades() {
    LOG.trace("method: cargarModalidades()");
    try {
      modalidades = serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD.getId());
    } catch (ServiceException ex) {
      LOG.error("metodo: cargarModalidades() ->> mensaje ->> {}", ex.getMessage());
    }
  }

  /**
   *
   */
  public void cargarProgramasAcademicosPorNivel() {
    LOG.trace("metodo: cargarProgramasAcademicosPorNivel()");
    if (optionNavEnum == NavEnum.LIST) {
      if (filtroEvaluaciones.getNivelAcademico() == null || filtroEvaluaciones.getNivelAcademico() == 0) {
        programasAcademicosFiltro = null;
      } else {
        programasAcademicosFiltro = cargarProgramasAcademicos(filtroEvaluaciones.getNivelAcademico());
      }
    } else if (optionNavEnum == NavEnum.NEW || optionNavEnum == NavEnum.EDIT) {
      this.categoriasEvaluacion = null;
      final Long idNivelAcademico = selected.getEvento().getCarrera().getNivelAcademico().getIdNivelAcademico();
      if (idNivelAcademico == null || idNivelAcademico == 0) {
        programasAcademicos = null;
      } else {
        programasAcademicos = cargarProgramasAcademicos(idNivelAcademico);
      }
    }
  }

  private List<Carreras> cargarProgramasAcademicos(Long nivelAcademico) {
    try {
      return serviceAPP.consultarCarrerasVigentes(nivelAcademico);
    } catch (Exception ex) {
      LOG.error("metodo: cargarProgramasAcademicosPorNivel() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
      return null;
    }
  }

  private void cargarCategorias() {
    LOG.trace("metodo: loadUnidades()");
    try {
      categorias = serviceAPP.consultarCategorias();
    } catch (Exception ex) {
      LOG.error("metodo: cargarGrados() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  private void cargarEvaluacionesFilter() {
    LOG.trace("metodo: cargarEvaluacionesFilter()");
    try {
      evaluacionesFilter = evaluacionService.findAllLoadIdAndDescription();
    } catch (Exception ex) {
      LOG.error("metodo: cargarEvaluacionesFilter() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  /**
   *
   */
  private void cargarNivelesAcademicos() {
    LOG.trace("metodo: loadNivelesAcademicos()");
    try {
      nivelesAcademicos = serviceAPP.consultarNivelesAcademicos();
    } catch (Exception ex) {
      LOG.error("metodo: loadCarreras() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  /**
   *
   */
  private void cargarProcesos() {
    LOG.trace("metodo: loadProcesos()");
    try {
      procesos = serviceAPP.consultarDominios(TipoDominioEnum.PROCESO.getId());
    } catch (Exception ex) {
      LOG.error("metodo: loadProcesos() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  /**
   *
   * @return
   */
  public void cargarVigencias() {
    LOG.trace("method: cargarVigencias()");
    try {
      paes = paeService.consultarVigencias();
    } catch (ServiceException ex) {
      LOG.error("Error en <<cargarVigencias>> ->> mensaje ->> {}", ex.getMessage());
    }
  }

  public void buscarEvaluaciones() {
    LOG.trace("metodo: buscarEvaluaciones()");
    try {
//            setEvalGrados(evaluacionGradoService.findByEvaluationFilter(filtroEvaluaciones));
      evaluaciones = evaluacionService.findByEvaluationFilter(filtroEvaluaciones);
      if (evaluaciones.isEmpty()) {
        addInfoMessage(getPropertyFromBundle("commons.msg.success.summary"), getPropertyFromBundle("commons.dt.emptymessage"));
      }
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      evaluaciones = null;
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void eliminarEvaluacion() {
    LOG.trace("metodo: eliminarEvaluacion()");
    try {
      evaluacionService.delete(selected.getId());
      evaluaciones.remove(selected);
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public String verDetalle() {
    LOG.trace("metodo: verDetalle()");
    String outcome = null;
    try {
      selected.setPreguntasEvaluacion(preguntaEvaluacionService.findByEvaluation(selected.getId()));
      optionNavEnum = NavEnum.DETAILS;
      outcome = "detalleEvaluacion.xhtml?faces-redirect=true";
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
    return outcome;
  }

  public String nuevaVersionEvaluacion() {
    LOG.trace("metodo: nuevaVersionEvaluacion()");
    String outcome = null;
    try {
      selected.setPreguntasEvaluacion(preguntaEvaluacionService.findByEvaluation(selected.getId()));
      outcome = "nuevaVersionEvaluacion.xhtml?faces-redirect=true";
    } catch (Exception ex) {
      LOG.error("metodo: nuevaVersionEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
    return outcome;
  }

  public void generarNuevaVersionEvaluacion() {
    LOG.trace("metodo: generarNuevaVersionEvaluacion()");
    try {
      selected = evaluacionService.newVersion(selected);
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: generarNuevaVersionEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public void eliminarPreguntaEvaluacion() {
    LOG.trace("metodo: eliminarPregunta()");
    try {
      preguntaEvaluacionService.delete(preguntaEvaluacion.getId());
      selected.getPreguntasEvaluacion().remove(preguntaEvaluacion);
      if (selected.getPreguntasEvaluacion().size() < selected.getEvalNroPreg()) {
        selected.setEstadoEvaluacion(EstadoEvaluacionEnum.PENDIENTE);
        selected.setEvalUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        evaluacionService.update(selected);
      }
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public String agregarEvaluacion() {
     LOG.trace("metodo: agregarEvaluacion()");
    String outcome = null;
    try {
      selected = new Evaluacion();
      selected.setPae(new PAE());

      Dominio evalDomProce = new Dominio();
      selected.setProceso(evalDomProce);

      Dominio evalDomModal = new Dominio();
      selected.setModalidad(evalDomModal);

      SieduEvento evento = new SieduEvento();
      Carreras carrera = new Carreras();
      NivelesAcademicos nivelAcademico = new NivelesAcademicos();
      carrera.setNivelAcademico(nivelAcademico);
      evento.setCarrera(carrera);
      selected.setEvento(evento);

//            selected.setEvalCategorias(new ArrayList<SieduEvalCategoria>());
      selected.setEvalGrados(new ArrayList<SieduEvalGrado>());
      selected.setPreguntasEvaluacion(new ArrayList<PreguntaEvaluacion>());

      selected.setCategoriaPks(new ArrayList<CategoriaPK>());
      selected.setGradoPKs(new ArrayList<GradoPK>());

      this.grados = new ArrayList<>();
      this.categoriasEvaluacion = new ArrayList<>();

      optionNavEnum = NavEnum.NEW;
      outcome = this.navigationFaces.redirectCUAgregarEvaluacion();
    } catch (Exception ex) {
      LOG.error("metodo: agregarEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
    return outcome;
  }

  public String editarEvaluacion() {
    LOG.trace("metodo: editarEvaluacion()");
    String outcome = null;
    try {
      optionNavEnum = NavEnum.EDIT;

      selected.setCategoriaPks(new ArrayList<CategoriaPK>());
      selected.setGradoPKs(new ArrayList<GradoPK>());
      for (SieduEvalGrado evalGrado : selected.getEvalGrados()) {
        selected.getCategoriaPks().add(evalGrado.getGrado().getCategoria().getId());
        selected.getGradoPKs().add(evalGrado.getGrado().getId());
      }

      cargarProgramasAcademicosPorNivel();
      cargarCategoriasEvaluacion();
      cargarGradosPorCategorias();

      selected.setPreguntasEvaluacion(preguntaEvaluacionService.findByEvaluation(selected.getId()));
      outcome = "evaluacion.xhtml?faces-redirect=true";
    } catch (Exception ex) {
      LOG.error("metodo: editarEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
    return outcome;
  }

  public void guardarEvaluacion() {
    LOG.trace("metodo: editarEvaluacion()");
    try {
      if (this.selected.getEvento() == null || this.selected.getEvento().getId() == null) {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("evaluacion.msg.error.noevent.summary"));
        return;
      }

      SieduEvalGrado evalGrado = null;
      SieduEvalGradoPK idEvalGrado = null;
      selected.setEvalGrados(new ArrayList<SieduEvalGrado>());
      for (GradoPK gradoPK : selected.getGradoPKs()) {
        idEvalGrado = new SieduEvalGradoPK();
        idEvalGrado.setEvgrGradAlfabetico(gradoPK.getAlfabetico());
        idEvalGrado.setEvgrGradFuerza(gradoPK.getFuerza());

        evalGrado = new SieduEvalGrado();
        evalGrado.setId(idEvalGrado);
        evalGrado.setGrado(buscarGrado(gradoPK));

        selected.getEvalGrados().add(evalGrado);
      }

      if (selected.getId() == null) {
        selected.setEvalUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        selected = evaluacionService.create(selected, selected.getEvalGrados());
        evaluaciones.add(selected);
      } else {
        selected.setEvalUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        evaluacionService.updateWithDegrees(selected);
      }
      addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
    } catch (Exception ex) {
      if (optionNavEnum == NavEnum.NEW) {
        selected.setId(null);
        selected.setEstadoEvaluacion(null);
      }
      LOG.error("metodo: editarEvaluacion() ->> mensaje: {}", ex.getMessage());
      if (ExceptionUtil.isException(ex, "SIEDU_EVALUACION_EVEN_UK")) {
        addErrorMessage(getPropertyFromBundle("evaluacion.msg.error.save.status.uk.summary"), getPropertyFromBundle("evaluacion.msg.error.save.status.uk.detail"));
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }
  }

  private Grado buscarGrado(GradoPK gradoPk) {
    Grado grado = null;
    for (Grado g : grados) {
      if (gradoPk.equals(g.getId())) {
        grado = g;
        break;
      }
    }
    return grado;
  }

  public String agregarPregunta() {
    LOG.trace("metodo: agregarPregunta()");
    String outcome = null;
    try {

      cargarParametrosPreguntasEvaluacion();

      preguntaEvaluacion = new PreguntaEvaluacion();
      PreguntaEvaluacionPK id = new PreguntaEvaluacionPK();
      id.setIdEvaluacion(selected.getId());
      preguntaEvaluacion.setId(id);
      preguntaEvaluacion.setAspecto(new ParametroEvaluacion());
      preguntaEvaluacion.setCompetencia(new SieduCompetencia());
      preguntaEvaluacion.setEvaluacion(selected);
      preguntaEvaluacion.setPregunta(new ParametroEvaluacion());

      outcome = "preguntaEvaluacion.xhtml?faces-redirect=true";
    } catch (Exception ex) {
      LOG.error("metodo: agregarPregunta() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
    return outcome;
  }

  public String guardarPreguntaEvaluacion() {
    LOG.trace("metodo: guardarPreguntaEvaluacion()");
    String outcome = null;
    try {
      final long numPreguntas = selected.getPreguntasEvaluacion().size();
      double porcentaje = 0;
      boolean existPercentage = false;
      final List<PreguntaEvaluacion> preguntasEvaluacion = selected.getPreguntasEvaluacion();
      for (PreguntaEvaluacion preguntaEvaluacion1 : preguntasEvaluacion) {
        if (preguntaEvaluacion1.getValorPorcentaje() != null) {
          existPercentage = true;
          porcentaje += preguntaEvaluacion1.getValorPorcentaje().doubleValue();
        }
      }
      boolean valid = true;
      if (existPercentage) {
        if (preguntaEvaluacion.getValorPorcentaje() == null) {
          valid = false;
          addErrorMessage(getPropertyFromBundle("commons.msg.empty.value.summary"), getPropertyFromBundle("commons.msg.empty.value.detail"), "somValor");
        } else {
          porcentaje += preguntaEvaluacion.getValorPorcentaje().doubleValue();
          if ((numPreguntas + 1) == selected.getEvalNroPreg() && porcentaje < 100) {
            valid = false;
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("evaluacion.msg.error.totalpercentagenot100.summary"));
          } else if (porcentaje > 100) {
            valid = false;
            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("evaluacion.msg.error.totalpercentagegreater100.summary"));
          }
        }
      } else if (numPreguntas != 0 && preguntaEvaluacion.getValorPorcentaje() != null) {
        valid = false;
        addErrorMessage(getPropertyFromBundle("commons.msg.empty.value.summary"), getPropertyFromBundle("commons.msg.empty.value.detail"), "somValor");
      }

      if (valid) {
        preguntaEvaluacion.getId().setIdPregunta(preguntaEvaluacion.getPregunta().getId());
        preguntaEvaluacion.setOrden((numPreguntas + 1));
        preguntaEvaluacion.setUsuarioCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        preguntaEvaluacion = preguntaEvaluacionService.create(preguntaEvaluacion);
        selected.getPreguntasEvaluacion().add(preguntaEvaluacion);
        outcome = "detalleEvaluacion.xhtml?faces-redirect=true";
      }
    } catch (Exception ex) {
      LOG.error("metodo: guardarPreguntaEvaluacion() ->> mensaje: {}", ex.getMessage());
      if (ExceptionUtil.isException(ex, "SIEDU_EVAL_PREGUNTA_PK")) {
        addErrorMessage(getPropertyFromBundle("evaluacion.msg.error.save.question.pk.summary"), getPropertyFromBundle("evaluacion.msg.error.save.question.pk.detail"));
      } else {
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }
    return outcome;
  }

  public void cargarCategoriasEvaluacion() {
    LOG.trace("metodo: cargarCategoriasEvaluacion()");
    try {
      if (selected.getProceso().getId() == null || selected.getModalidad().getId() == null || selected.getEvento().getCarrera().getCarrerasPK() == null) {
        categoriasEvaluacion = null;
      } else {
        EventoFiltro eventoFiltro = new EventoFiltro();
        eventoFiltro.setCarrera(selected.getEvento().getCarrera());
        eventoFiltro.setModalidad(selected.getModalidad());
        eventoFiltro.setProceso(selected.getProceso());
        final List<SieduEvento> eventos = eventoService.findByFilter(eventoFiltro);
        SieduEvento evento = null;
        for (SieduEvento sieduEvento : eventos) {
          if (SieduEventoEstadoEnum.VIGENTE.name().equals(sieduEvento.getEstado())) {
            evento = sieduEvento;
            break;
          }
        }
        if (evento == null) {
          evento = new SieduEvento();
          Carreras carrera = new Carreras();
          NivelesAcademicos nivelAcademico = new NivelesAcademicos();
          carrera.setNivelAcademico(nivelAcademico);
          evento.setCarrera(carrera);
          categoriasEvaluacion = null;
        } else {
          categoriasEvaluacion = serviceAPP.consultarCategoriasEvento(evento.getId());
        }
        this.selected.setEvento(evento);
      }
      grados = null;
      if (optionNavEnum != NavEnum.EDIT) {
        selected.setCategoriaPks(new ArrayList<CategoriaPK>());
        selected.setGradoPKs(new ArrayList<GradoPK>());
      }
    } catch (Exception ex) {
      LOG.error("metodo: cargarCategoriasEvaluacion() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  public StreamedContent getReportePDF() {
    StreamedContent reportePDF = null;
    try {
      List<PreguntaEvaluacionReporteDTO> preguntaEvaluacionReporteDTOs = cargarDataReporte();

      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(preguntaEvaluacionReporteDTOs);

      SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");

      Map<String, Object> parametros = new HashMap<>();
      ResourceBundle bundle = getBundle();
      parametros.put("REPORT_RESOURCE_BUNDLE", bundle);
//		parametros.put("cliente", fdDTO.getNombreCliente());

      Map<String, String> parametrosR = new HashMap<>();
      parametrosR.put("tipo", "pdf");

      String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/evaluacion.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "evaluacion.pdf");

//            InputStream pPlantilla = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/reportes/evaluacion.jasper");            
//            final byte[] report = ReportEngine.exportarReportePDF(pPlantilla, parametros, ds);
//            reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(report), "application/pdf ", "evaluacion.pdf");
    } catch (Exception e) {
      LOG.error("metodo: cargarCategoriasEvaluacion() ->> mensaje: {}", e.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
    }
    return reportePDF;
  }

  private List<PreguntaEvaluacionReporteDTO> cargarDataReporte() throws ServiceException {
    List<PreguntaEvaluacionReporteDTO> preguntaEvaluacionReporteDTOs = new ArrayList<>();
    final List<PreguntaEvaluacion> preguntaEvaluacions = preguntaEvaluacionService.findByEvaluation(selected.getId());
    for (PreguntaEvaluacion preguntaEvaluacion : preguntaEvaluacions) {
      preguntaEvaluacionReporteDTOs.add(mapearAPreguntaReporte(preguntaEvaluacion));
    }
    return preguntaEvaluacionReporteDTOs;
  }

  private PreguntaEvaluacionReporteDTO mapearAPreguntaReporte(PreguntaEvaluacion preguntaEvaluacion) {
    PreguntaEvaluacionReporteDTO preguntaEvaluacionReporteDTO = new PreguntaEvaluacionReporteDTO();
    preguntaEvaluacionReporteDTO.setIdAspecto(preguntaEvaluacion.getAspecto().getId());
    preguntaEvaluacionReporteDTO.setDescripcionAspecto(preguntaEvaluacion.getAspecto().getDescripcion());
    preguntaEvaluacionReporteDTO.setPregunta(preguntaEvaluacion.getPregunta().getDescripcion());
    return preguntaEvaluacionReporteDTO;
  }

  private PreguntaEvaluacionReporteDTO mapearAPreguntaReporte(EvaluacionDesarrollo evaluacionDesarrollo) {
    PreguntaEvaluacionReporteDTO preguntaEvaluacionReporteDTO = new PreguntaEvaluacionReporteDTO();
    preguntaEvaluacionReporteDTO.setIdAspecto(evaluacionDesarrollo.getPreguntaEvaluacion().getAspecto().getId());
    preguntaEvaluacionReporteDTO.setDescripcionAspecto(evaluacionDesarrollo.getPreguntaEvaluacion().getAspecto().getDescripcion());
    preguntaEvaluacionReporteDTO.setPregunta(evaluacionDesarrollo.getPreguntaEvaluacion().getPregunta().getDescripcion());
    if (evaluacionDesarrollo.getValor() != null) {
      switch (evaluacionDesarrollo.getValor()) {
        case 1:
          preguntaEvaluacionReporteDTO.setValor1("X");
          break;
        case 2:
          preguntaEvaluacionReporteDTO.setValor2("X");
          break;
        case 3:
          preguntaEvaluacionReporteDTO.setValor3("X");
          break;
        case 4:
          preguntaEvaluacionReporteDTO.setValor4("X");
          break;
        default:
          preguntaEvaluacionReporteDTO.setValor5("X");
      }
    }
    return preguntaEvaluacionReporteDTO;
  }

  public boolean isValidaRol() {
    return validaRol;
  }

  public void setValidaRol(boolean validaRol) {
    this.validaRol = validaRol;
  }

  public EvaluacionFiltro getFiltroEvaluaciones() {
    return filtroEvaluaciones;
  }

  public void setFiltroEvaluaciones(EvaluacionFiltro filtroEvaluaciones) {
    this.filtroEvaluaciones = filtroEvaluaciones;
  }

  public List<Categoria> getCategorias() {
    return categorias;
  }

  public void setCategorias(List<Categoria> categorias) {
    this.categorias = categorias;
  }

  public List<Grado> getGrados() {
    return grados;
  }

  public void setGrados(List<Grado> grados) {
    this.grados = grados;
  }

  public List<Evaluacion> getEvaluacionesFilter() {
    return evaluacionesFilter;
  }

  public void setEvaluacionesFilter(List<Evaluacion> evaluacionesFilter) {
    this.evaluacionesFilter = evaluacionesFilter;
  }

  public Evaluacion getSelected() {
    return selected;
  }

  public void setSelected(Evaluacion selected) {
    this.selected = selected;
  }

  public List<Evaluacion> getEvaluaciones() {
    return evaluaciones;
  }

  public void setEvaluaciones(List<Evaluacion> evaluaciones) {
    this.evaluaciones = evaluaciones;
  }

  public List<ParametroEvaluacion> getPreguntas() {
    return preguntas;
  }

  public void setPreguntas(List<ParametroEvaluacion> preguntas) {
    this.preguntas = preguntas;
  }

  public List<SieduCompetencia> getCompetencias() {
    return competencias;
  }

  public void setCompetencias(List<SieduCompetencia> competencias) {
    this.competencias = competencias;
  }

  public PreguntaEvaluacion getPreguntaEvaluacion() {
    return preguntaEvaluacion;
  }

  public void setPreguntaEvaluacion(
          PreguntaEvaluacion preguntaEvaluacionSelected) {
    this.preguntaEvaluacion = preguntaEvaluacionSelected;
  }

  public List<ParametroEvaluacion> getAspectos() {
    return aspectos;
  }

  public void setAspectos(List<ParametroEvaluacion> aspectos) {
    this.aspectos = aspectos;
  }

  public List<ParametroEvaluacion> getFactores() {
    return factores;
  }

  public void setFactores(List<ParametroEvaluacion> factores) {
    this.factores = factores;
  }

  public String getOutcomeCancelarPregunta() {
    return outcomeCancelarPregunta;
  }

  public void setOutcomeCancelarPregunta(String outcomeCancelarPregunta) {
    this.outcomeCancelarPregunta = outcomeCancelarPregunta;
  }

  public List<PAE> getVigencias() {
    return paes;
  }

  public void setVigencias(List<PAE> vigencias) {
    this.paes = vigencias;
  }

  public List<Dominio> getProcesos() {
    return procesos;
  }

  public void setProcesos(List<Dominio> procesos) {
    this.procesos = procesos;
  }

  public List<NivelesAcademicos> getNivelesAcademicos() {
    return nivelesAcademicos;
  }

  public void setNivelesAcademicos(List<NivelesAcademicos> nivelesAcademicos) {
    this.nivelesAcademicos = nivelesAcademicos;
  }

  public List<Carreras> getProgramasAcademicos() {
    return programasAcademicos;
  }

  public void setProgramasAcademicos(List<Carreras> programasAcademicos) {
    this.programasAcademicos = programasAcademicos;
  }

  public List<Dominio> getModalidades() {
    return modalidades;
  }

  public void setModalidades(List<Dominio> modalidades) {
    this.modalidades = modalidades;
  }

  public NavEnum getOptionNavEnum() {
    return optionNavEnum;
  }

  public void setOptionNavEnum(NavEnum optionNavEnum) {
    this.optionNavEnum = optionNavEnum;
  }

  public List<Grado> getGradosFiltro() {
    return gradosFiltro;
  }

  public void setGradosFiltro(List<Grado> gradosFiltro) {
    this.gradosFiltro = gradosFiltro;
  }

  public List<Carreras> getProgramasAcademicosFiltro() {
    return programasAcademicosFiltro;
  }

  public void setProgramasAcademicosFiltro(List<Carreras> programasAcademicosFiltro) {
    this.programasAcademicosFiltro = programasAcademicosFiltro;
  }

  public List<Categoria> getCategoriasEvaluacion() {
    return categoriasEvaluacion;
  }

  public void setCategoriasEvaluacion(List<Categoria> categoriasEvaluacion) {
    this.categoriasEvaluacion = categoriasEvaluacion;
  }

}
