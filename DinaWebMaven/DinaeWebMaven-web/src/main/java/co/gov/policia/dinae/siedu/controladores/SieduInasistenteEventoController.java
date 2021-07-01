/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.modelo.SieduDatosEmpleadoDTO;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.SieduInasisteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduParticipanteEvento;
import co.gov.policia.dinae.siedu.modelo.SieduPersona;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduInasisteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduParticipanteEventoService;
import co.gov.policia.dinae.siedu.servicios.SieduPersonaService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * description
 *
 * @author: ANDRÃƒâ€°S FELIPE ZAMORA GARZÃƒâ€œN <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SieduInasistenteEventoController extends AbstractController {

  private static final Logger LOG = LoggerFactory.getLogger(SieduInasistenteEventoController.class);

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
  @EJB
  private SieduInasisteEventoService service;
  @EJB
  private SieduPersonaService servicePersona;
  @EJB
  private SieduInasisteEventoService serviceInasiste;
  @EJB
  private SieduParticipanteEventoService serviceParticipante;
  private SieduInasisteEvento selected;
  private List<SieduInasisteEvento> list;
  private boolean editable;
  private NavEnum optionNavEnum;
  private String identificacion;
  private SieduEventoEscuela eventoEscuela;
  private int tiempoMinInasiste;
  private int tiempoMaximInasiste;
  private int tiempoInasiste;

  public SieduInasistenteEventoController() {
    LOG.trace("metodo: constructor()");
  }

  @PostConstruct
  public void initialize() {
    LOG.trace("metodo: initialize()");
    optionNavEnum = NavEnum.LIST;
    setSelected(new SieduInasisteEvento());
    setEditable(false);
    identificacion = "";
    SieduEventoEscuelaController eventoEscuelaController = this.findManagedBean("sieduEventoEscuelaController");
    eventoEscuela = eventoEscuelaController.getSelected();
    tiempoMinInasiste = 1;
    tiempoMaximInasiste = 0;
    tiempoInasiste = 0;
    loadList();
  }

  /**
   *
   * @return
   */
  public String initReturnCU() {
    LOG.trace("metodo: initReturnCU()");
    return navigationFaces.redirect_CU09_CAP_BUSCAR_EVENTO_ESCUELA();
  }

  /**
   *
   */
  public void loadList() {
    LOG.trace("metodo: loadList()");
    try {
      setList(service.findByInaepareEvee(eventoEscuela));
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }
  
  public void loadList(SieduEventoEscuela evento) {
    LOG.trace("metodo: loadList()");
    try {
      setList(service.findByInaepareEvee(evento));
    } catch (Exception ex) {
      LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
  }

  /**
   *
   */
  private void initializeEdit() {
    LOG.trace("metodo: initializeEdit()");
    setEditable(true);
  }

  /**
   *
   */
  public void onCreate() {
    LOG.trace("metodo: onCreate()");
    optionNavEnum = NavEnum.DETAILS;
    setSelected(new SieduInasisteEvento());
    initializeEdit();
  }

  /**
   *
   * @param event
   */
  public void onRowSelect(SelectEvent event) {
    LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
    setSelected((SieduInasisteEvento) event.getObject());
    setEditable(false);
    optionNavEnum = NavEnum.DETAILS;
  }

  public void onRowEdit(RowEditEvent event) {
    LOG.trace("metodo: onRowEdit()");
    try {
      int tiempoEvento = (eventoEscuela.getEveeEven().getEvenIntenHoras() * 60);
      int tiempoInasistencia = 0;
      int tiempoRestante = 0;
      tiempoInasistencia = tiempoInasistenciaUpdate(selected);
      tiempoRestante = tiempoEvento - tiempoInasistencia;
      if (selected.getInaeTiempo() > tiempoRestante) {
        loadList();
        addErrorMessage(getPropertyFromBundle("eventoescuela.inasistencia.msg.error.maxtime.sumary"), getPropertyFromBundle("eventoescuela.inasistencia.msg.error.maxtime.details"));
        addErrorMessage(getPropertyFromBundle("eventoescuela.inasistencia.msg.error.maxtimeerror.details") + tiempoRestante);
      } else {
        String cod = loginFaces.getPerfilUsuarioDTO().getCodigoUnidadLaboral();
        selected.setInaeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        service.update((SieduInasisteEvento) event.getObject());
        loadList();
        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
      }
    } catch (Exception ex) {
      LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
  }

  /**
   *
   * @return
   */
  public int tiempoInasistencia() {
    LOG.trace("metodo: tiempoInasistencia()");
    tiempoInasiste = 0;
    List<SieduInasisteEvento> inasistenciasPorparticipante;
    try {
      inasistenciasPorparticipante = service.findByInaepare(identificacion, eventoEscuela);
      if (!inasistenciasPorparticipante.isEmpty()) {
        for (SieduInasisteEvento ina : inasistenciasPorparticipante) {
          tiempoInasiste += ina.getInaeTiempo();
        }
      }
    } catch (Exception ex) {
      LOG.error("metodo: tiempoInasistencia() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
    return tiempoInasiste;
  }

  public int tiempoInasistenciaUpdate(SieduInasisteEvento selected) {
    LOG.trace("metodo: tiempoInasistencia()");
    tiempoInasiste = 0;
    List<SieduInasisteEvento> inasistenciasPorparticipante;
    try {
      inasistenciasPorparticipante = service.findByInaepare(selected.getInaePare().getParePers().getPersNroid(), eventoEscuela);
      if (!inasistenciasPorparticipante.isEmpty()) {
        for (SieduInasisteEvento ina : inasistenciasPorparticipante) {
          if (!ina.getInaeInae().equals(selected.getInaeInae())) {
            tiempoInasiste += ina.getInaeTiempo();
          }
        }
      }
    } catch (Exception ex) {
      LOG.error("metodo: tiempoInasistencia() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
    return tiempoInasiste;
  }

  /**
   *
   */
  public void onEdit() {
    LOG.trace("metodo: onEdit()");
    initializeEdit();
  }

  /**
   *
   * @param record
   */
  public void onDelete(SieduInasisteEvento record) {
    LOG.trace("metodo: onDelete()");
    try {
      selected.setInaeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
      service.delete(record);
      initialize();
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (Exception ex) {
      LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
    }
  }

  /**
   *
   */
  public void onSave() {
    LOG.trace("metodo: onSave()");
    try {
      SieduPersona persona = servicePersona.findByIdentificacion(identificacion);
      if (persona == null || persona.getPersPers() == null) {
        addErrorMessage(getPropertyFromBundle("eventoesuela.msg.error.nodatafound.sumary"), getPropertyFromBundle("eventoesuela.msg.error.nodatafound.details"));
      } else {
        SieduInasisteEvento inasisteFecha = serviceInasiste.findByInaepareInaefecha(persona, selected.getInaeFecha());
        if (inasisteFecha == null) {
          SieduParticipanteEvento participante = serviceParticipante.findByPareeveeParepers(eventoEscuela, persona);
          if (participante == null) {
            addErrorMessage(getPropertyFromBundle("eventoesuela.inasistencia.msg.error.noparticipante.sumary"), getPropertyFromBundle("eventoesuela.inasistencia.msg.error.noparticipante.details"));
          } else {
            int tiempoEvento = (eventoEscuela.getEveeEven().getEvenIntenHoras() * 60);
            int tiempoInasistencia = 0;
            tiempoInasistencia = tiempoInasistencia() + selected.getInaeTiempo();
            if (tiempoInasistencia >= tiempoEvento) {
              int tiempoMaxIna = 0;
              tiempoMaxIna = tiempoEvento - tiempoInasistencia();
              addErrorMessage(getPropertyFromBundle("eventoescuela.inasistencia.msg.error.maxtime.sumary"), getPropertyFromBundle("eventoescuela.inasistencia.msg.error.maxtime.details"));
              addErrorMessage("El tiempo mÃ¡ximo permitido para el participante para este registro es " + tiempoMaxIna + " minutos");
            } else {
              if (getSelected().getInaeInae() == null) {
                selected.setInaeUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                selected.setInaePare(participante);
                service.create(getSelected());
                loadList();
                setSelected(new SieduInasisteEvento());
              } else {
                selected.setInaeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
                service.update(getSelected());
                loadList();
              }
              setEditable(false);
              addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            }
          }
        } else {
          addErrorMessage(getPropertyFromBundle("eventoescuela.inasistencia.msg.error.uk.sumary"), getPropertyFromBundle("eventoescuela.inasistencia.msg.error.uk.detail"));
        }
      }
    } catch (Exception ex) {
      LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
      addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
    }
  }

  /**
   *
   */
  public void onCancel() {
    LOG.trace("metodo: onCancel()");
    setEditable(false);
    if (getSelected().getInaeInae() == null) {
      optionNavEnum = NavEnum.LIST;
      setSelected(null);
    }
  }

  /**
   *
   */
  public void onBack() {
    LOG.trace("metodo: onBack()");
    initialize();
  }

  /**
   *
   * @return
   */
  public boolean isShowList() {
    boolean showList = (optionNavEnum == NavEnum.LIST);
    return showList;
  }

  /**
   *
   * @return
   */
  public boolean isShowDetails() {
    boolean showDetails = (optionNavEnum == NavEnum.DETAILS);
    return showDetails;
  }

  /**
   *
   * @return
   */
  public boolean isNewRecord() {
    boolean newRecord = (getSelected() != null && getSelected().getInaeInae() == null);
    return newRecord;
  }

  /**
   * @return the selected
   */
  public SieduInasisteEvento getSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(SieduInasisteEvento selected) {
    this.selected = selected;
  }

  /**
   * @return the list
   */
  public List<SieduInasisteEvento> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<SieduInasisteEvento> list) {
    this.list = list;
  }

  /**
   * @return the editable
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   * @param editable the editable to set
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public String getIdentificacion() {
    return identificacion;
  }

  public void setIdentificacion(String identificacion) {
    this.identificacion = identificacion;
  }

  public int getTiempoMinInasiste() {
    return tiempoMinInasiste;
  }

  public void setTiempoMinInasiste(int tiempoMinInasiste) {
    this.tiempoMinInasiste = tiempoMinInasiste;
  }

  public int getTiempoMaximInasiste() {
    return tiempoMaximInasiste;
  }

  public void setTiempoMaximInasiste(int tiempoMaximInasiste) {
    this.tiempoMaximInasiste = tiempoMaximInasiste;
  }

  public int getTiempoInasiste() {
    return tiempoInasiste;
  }

  public void setTiempoInasiste(int tiempoInasiste) {
    this.tiempoInasiste = tiempoInasiste;
  }

}
