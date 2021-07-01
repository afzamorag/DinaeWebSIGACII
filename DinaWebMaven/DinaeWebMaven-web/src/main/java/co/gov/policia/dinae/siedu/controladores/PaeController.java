/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.siedu.util.NavEnum;
import co.gov.policia.dinae.siedu.constantes.PAEEstadoEnum;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.excepciones.ValidationsException;
import co.gov.policia.dinae.siedu.modelo.Archivo;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.modelo.PAENovedad;
import co.gov.policia.dinae.siedu.servicios.APPJPAService;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.NecesidadService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import co.gov.policia.dinae.siedu.util.exception.ExceptionUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class PaeController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(DominioController.class);

    public enum Option {
        ADD,
        MODIFY,
        GENERATE;
    }

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private PAEService service;
    @EJB
    private NecesidadService serviceNecesidad;
    @EJB
    private APPService appService;
    private List<SelectItem> vigencias;
    private String vigencia;
    private PAE pae;
    private PAENovedad novedad;
    private List<PAE> list;
    private NavEnum optionNavEnum;
    private Option option;
    private List<Dominio> tipoDocumentos;

    public PaeController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.optionNavEnum = NavEnum.LIST;
        this.loadVigencias();
        this.pae = null;
        this.option = null;
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirect_CU01_CAP_ADMINISTRAR_PAE();
    }

    private void loadVigencias() {
        LOG.trace("metodo: loadVigencias()");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer currentYear = calendar.get(Calendar.YEAR);
        Integer nextYear = currentYear + 1;
        Integer lastYear = currentYear - 1;
        this.vigencias = new ArrayList<>();
        this.vigencias.add(new SelectItem(lastYear.toString()));
        this.vigencias.add(new SelectItem(currentYear.toString()));
        this.vigencias.add(new SelectItem(nextYear.toString()));
        documentoList();
    }

    public void onFindPAE() {
            LOG.trace("metodo: onFindPAE()");
        try {
            this.pae = service.consultarPAE(this.vigencia);
            if (this.pae == null) {
                this.option = Option.ADD;
                addInfoMessage(getPropertyFromBundle("pae.msg.find.error.summary"), getPropertyFromBundle("pae.msg.find.error.detail"));
            } else if (this.pae.getEstado().equals(PAEEstadoEnum.ABIERTO.toString())) {
                this.option = Option.GENERATE;
                addInfoMessage(getPropertyFromBundle("pae.msg.find.active.summary"), getPropertyFromBundle("pae.msg.find.active.detail"));
            } else {
                this.option = Option.MODIFY;
                addInfoMessage(getPropertyFromBundle("pae.msg.find.inactive.summary"), getPropertyFromBundle("pae.msg.find.inactive.detail"));
            }
        } catch (Exception ex) {
            LOG.error("metodo: onFindPAE() ->> mensaje ->> {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
        }
    }

    public boolean enableAddPAE() {
        LOG.trace("metodo: enabledAdd()");
        return this.option != null
                && this.option == Option.ADD
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onAddPAE() {
        LOG.trace("metodo: onAddPAE()");
        this.optionNavEnum = NavEnum.DETAILS;
        this.pae = new PAE();
        this.pae.setVigencia(this.vigencia);
        this.novedad = new PAENovedad();
        this.novedad.setPae(this.pae);
    }

    public boolean enabledModify() {
        LOG.trace("metodo: enabledModify()");
        return this.option != null
                && this.option == Option.MODIFY
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onModifyPAE() {
        LOG.trace("metodo: onModifyPAE()");
        this.optionNavEnum = NavEnum.DETAILS;
        this.novedad = new PAENovedad();
        this.novedad.setPae(this.pae);
    }

    public boolean enabledGenerate() {
        LOG.trace("metodo: enabledGenerate()");
        return this.option != null
                && this.option == Option.GENERATE
                && (loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString()));
    }

    public void onGeneratePAE() {
        LOG.trace("metodo: onGeneratePAE()");
        try {
            this.validate();
            this.optionNavEnum = NavEnum.DETAILS;
            this.novedad = new PAENovedad();
            this.novedad.setPae(this.pae);
        } catch (ValidationsException ex) {
            LOG.error("metodo: onGeneratePAE() ->> mensaje ->> {}", ex.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        LOG.trace("method: handleFileUpload(FileUploadEvent)");
        try {
            UploadedFile fileUploadedFile = event.getFile();
            if (this.novedad.getDocumento() == null) {
                this.novedad.setDocumento(new Archivo());
            }
            this.novedad.getDocumento().setContentType(fileUploadedFile.getContentType());
            this.novedad.getDocumento().setDocumentPath(fileUploadedFile.getFileName());
            this.novedad.getDocumento().setInputStream(fileUploadedFile.getInputstream());
        } catch (IOException ex) {
            LOG.error("method: handleFileUpload(FileUploadEvent). Message --> {}", ex.getMessage());
        }
    }

    public void onSave() {
        LOG.trace("metodo: onSave()");
        if (this.novedad.getDocumento() == null) {
            addErrorMessage(getPropertyFromBundle("pae.msg.error.validation.novedad.documento.summary"), getPropertyFromBundle("pae.msg.error.validation.novedad.documento.detail"), "documento");
        } else {
            switch (this.option) {
                case ADD:
                    this.pae.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    this.novedad.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    try {
                        this.service.abrirPAE(this.pae, this.novedad);
                        this.onFindPAE();
                        this.optionNavEnum = NavEnum.LIST;
                        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                    } catch (Exception ex) {
                        LOG.error("metodo: onSave() ->> mensaje ->> {}", ex.getMessage());
                        if (ExceptionUtil.isException(ex, "SIEDU_PAE_VIGENCIA_UK")) {
                            addErrorMessage(getPropertyFromBundle("pae.msg.error.save.uk.summary"), getPropertyFromBundle("pae.msg.error.save.uk.detail"));
                        } else {
                            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                        }
                    }
                    break;
                case GENERATE:
                    try {
                        // validaciones
                        this.validate();
                        this.pae.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                        this.novedad.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                        try {
                            this.service.cerrarPAE(this.pae, this.novedad);
                            this.onFindPAE();
                            this.optionNavEnum = NavEnum.LIST;
                            addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                        } catch (Exception ex) {
                            LOG.error("metodo: onSave() ->> mensaje ->> {}", ex.getMessage());
                            addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                        }
                    } catch (ValidationsException ex) {
                        LOG.error("metodo: onSave() ->> mensaje ->> {}", ex.getMessage());
                    }
                    break;
                case MODIFY:
                    this.pae.setModUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    this.novedad.setCreaUsuario(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado()); // auditoria
                    try {
                        this.service.reabrirPAE(this.pae, this.novedad);
                        this.onFindPAE();
                        this.optionNavEnum = NavEnum.LIST;
                        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
                    } catch (Exception ex) {
                        LOG.error("metodo: onSave() ->> mensaje ->> {}", ex.getMessage());
                        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
                    }
                    break;
                default:
                    LOG.info("Opcion no valida");
                    break;
            }
        }
    }

    private void validate() throws ValidationsException {
        LOG.trace("metodo: validate()");
        try {
            {
                Integer cantidadNecesidades = this.serviceNecesidad.obtenerCantidadNecesidades(this.getPae().getId());
                if (cantidadNecesidades == 0) {
                    addErrorMessage(getPropertyFromBundle("necesidad.msg.error.validation.no.records.found"));
                    throw new ValidationsException();
                }
            }
            {
                List<String> msgs = this.serviceNecesidad.validarProcesoNecesidades(this.pae.getId());
                if (msgs != null && !msgs.isEmpty()) {
                    addErrorMessage(getPropertyFromBundle("necesidad.msg.error.validation.complete"));
                    for (String msg : msgs) {
                        addErrorMessage(msg);
                    }
                    throw new ValidationsException();
                }
            }
            {
                List<String> msgs = this.serviceNecesidad.validarEstadoNecesidades(this.pae.getId());
                if (msgs != null && !msgs.isEmpty()) {
                    addErrorMessage(getPropertyFromBundle("necesidad.msg.error.validation.status"));
                    for (String msg : msgs) {
                        addErrorMessage(msg);
                    }
                    throw new ValidationsException();
                }
            }
        } catch (ServiceException ex) {
            LOG.error("metodo: validate() ->> mensaje ->> {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
        }
    }

    public void onCancel() {
        LOG.trace("metodo: onCancel()");
        this.optionNavEnum = NavEnum.LIST;
        this.novedad = null;
    }

    public StreamedContent handleFileDownload(Archivo documento) {
        LOG.trace("method: handleFileDownload()");
        StreamedContent file = null;
        if (documento.getBase64() != null) {
            try {
                file = new DefaultStreamedContent(documento.getInputStream(), documento.getContentType(), documento.getNameWhitExtension());
            } catch (IOException ex) {
                LOG.error("Error in method: handleFileDownload()", ex);
                addErrorMessage("Error", ex.getMessage());
            }
        } else {
            addErrorMessage("Error", "No es posible cargar el archivo");
        }
        return file;
    }

    public boolean showList() {
        LOG.trace("metodo: showList()");
        boolean showList = (optionNavEnum == NavEnum.LIST);
        return showList;
    }

    public boolean showDetails() {
        LOG.trace("metodo: showDetails()");
        boolean showDetails = (optionNavEnum == NavEnum.DETAILS);
        return showDetails;
    }

    public void documentoList() {
        try {
            tipoDocumentos = appService.loadTiposDocumentos();
        } catch (ServiceException ex) {
            LOG.error("metodo: validate() ->> mensaje ->> {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
        }
    }

    public List<SelectItem> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<SelectItem> vigencias) {
        this.vigencias = vigencias;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public PAE getPae() {
        return pae;
    }

    public void setPae(PAE pae) {
        this.pae = pae;
    }

    public PAENovedad getNovedad() {
        return novedad;
    }

    public void setNovedad(PAENovedad novedad) {
        this.novedad = novedad;
    }

    public List<PAE> getList() {
        return list;
    }

    public void setList(List<PAE> list) {
        this.list = list;
    }

    public List<Dominio> getTipoDocumentos() {
        return tipoDocumentos;
    }

    public void setTipoDocumentos(List<Dominio> tipoDocumentos) {
        this.tipoDocumentos = tipoDocumentos;
    }

}
