/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.filtros.CoberturaFiltro;
import co.gov.policia.dinae.siedu.modelo.Cobertura;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.modelo.SieduConvocatoriaEvento;
import co.gov.policia.dinae.siedu.modelo.SieduEventoEscuela;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.CoberturaJPAService;
import co.gov.policia.dinae.siedu.servicios.CoberturaService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.SieduConvocatoriaEventoService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 * description
 *
 * @author: Andrés Felipe Zamora Garzón <af.zamorag@gmail.com>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SieduConvocatoriaEventoController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SieduConvocatoriaEventoController.class);

    @Inject
    protected LoginFaces loginFaces;
    @EJB
    private SieduConvocatoriaEventoService service;
    @EJB
    private CoberturaService coberturaService;
    @EJB
    private APPService appService;
    private SieduConvocatoriaEvento selected;
    private Dominio tipoDocumento;
    private List<SieduConvocatoriaEvento> list;
    private boolean editable;
    private NavEnum optionNavEnum;
    private SieduEventoEscuela eventoEscuela;
    private CoberturaFiltro filtroCobertura;
    private List<Dominio> lstDocuments;

    public SieduConvocatoriaEventoController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        optionNavEnum = NavEnum.LIST;
        SieduEventoEscuelaController eventoEscuelaController = this.findManagedBean("sieduEventoEscuelaController");
        eventoEscuela = eventoEscuelaController.getSelected();
        //setSelected(null);
        setEditable(false);
        setSelected(new SieduConvocatoriaEvento());
        loadList();
        this.loadTypeDocument();
        this.filtroCobertura = new CoberturaFiltro();
    }

    public void loadTypeDocument() {
        LOG.trace("metodo: loadTypeDocument()");
        try {
            this.lstDocuments = new ArrayList<>();
            this.lstDocuments = appService.consultarDominios(TipoDominioEnum.TIPO_DOCUMENTO.getId());
        } catch (Exception ex) {
            LOG.error("metodo: loadTypeDocument() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    /**
     *
     */
    public void loadList() {
        LOG.trace("metodo: loadList()");
        try {
            setList(service.findListById(eventoEscuela));
        } catch (Exception ex) {
            LOG.error("metodo: loadList() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void loadList(SieduEventoEscuela evento) {
        LOG.trace("metodo: loadList()");
        try {
            setList(service.findListById(evento));
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
        setSelected(new SieduConvocatoriaEvento());
        initializeEdit();
    }

    /**
     *
     * @param event
     */
    public void onRowSelect(SelectEvent event) {
        LOG.trace("metodo: onRowSelect() ->> parametros: event {}", event);
        setSelected((SieduConvocatoriaEvento) event.getObject());
        setEditable(false);
        optionNavEnum = NavEnum.DETAILS;
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
     */
    public void onDelete() {
        LOG.trace("metodo: onDelete()");
        try {
            service.delete(getSelected());
            initialize();
            addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public void onRowEdit(SelectEvent event) {
        LOG.trace("metodo: onRowEdit()");
        try {
            onRowSelect(event);
            selected.setConeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            service.update(selected);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
            setSelected(new SieduConvocatoriaEvento());
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
    }

    public void onSave() {
        LOG.trace("metodo: onSave()");
        try {
            getSelected().setConeEvee(eventoEscuela);
            if (getSelected().getConeCone() == null) {
                selected.setConeUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.create(getSelected());
                loadList();
                setSelected(new SieduConvocatoriaEvento());

            } else {
                selected.setConeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                service.update(getSelected());
                loadList();
                setSelected(new SieduConvocatoriaEvento());
            }
            setEditable(false);
            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));

        } catch (Exception ex) {
            LOG.error("metodo: onSave() ->> mensaje: {}", ex.getMessage());
            if (ExceptionUtil.isException(ex, "USR_DISIGAC2.SIEDU_CONVOC_EVENTO_UK")) {
                addErrorMessage(getPropertyFromBundle("siedueventoescuela.convocatoria.msg.error.save.uk.summary"), getPropertyFromBundle("siedueventoescuela.convocatoria.msg.error.save.uk.detail"));
            } else {
                addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
            }
        }
    }

    /**
     *
     */
    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        setEditable(false);
        if (getSelected().getConeCone() == null) {
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

    public List<Cobertura> unidades() {
        LOG.trace("method: unidades()");
        try {
            this.filtroCobertura.setEscuela(eventoEscuela.getEveeCapa().getEscuela());
            this.filtroCobertura.setEstrategia(eventoEscuela.getEveeCapa().getEstrategia());
            this.filtroCobertura.setPae(eventoEscuela.getEveeCapa().getPae());
            return coberturaService.findByFilter(this.filtroCobertura);
        } catch (Exception ex) {
            LOG.error("metodo: method: unidades() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            return null;
        }
    }

    public void onRemove(SieduConvocatoriaEvento convocatoria) {
        LOG.trace("metodo: onRemove");
        try {
            convocatoria.setConeUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
            service.delete(convocatoria);
            loadList();
        } catch (Exception ex) {
            LOG.error("metodo: onDelete() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.delete.summary"), getPropertyFromBundle("commons.msg.error.delete.detail"));
        }
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
        boolean newRecord = (getSelected() != null && getSelected().getConeCone() == null);
        return newRecord;
    }

    /**
     * @return the selected
     */
    public SieduConvocatoriaEvento getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(SieduConvocatoriaEvento selected) {
        this.selected = selected;
    }

    public Dominio getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Dominio tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the list
     */
    public List<SieduConvocatoriaEvento> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SieduConvocatoriaEvento> list) {
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

    public SieduEventoEscuela getEventoEscuela() {
        return eventoEscuela;
    }

    public void setEventoEscuela(SieduEventoEscuela eventoEscuela) {
        this.eventoEscuela = eventoEscuela;
    }

    public CoberturaFiltro getFiltroCobertura() {
        return filtroCobertura;
    }

    public void setFiltroCobertura(CoberturaFiltro filtroCobertura) {
        this.filtroCobertura = filtroCobertura;
    }

    public List<Dominio> getLstDocuments() {
        return lstDocuments;
    }

    public void setLstDocuments(List<Dominio> lstDocuments) {
        this.lstDocuments = lstDocuments;
    }

}
