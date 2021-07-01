/*
 * Soft Studio LTDA
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.controladores;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IUnidadDependenciaLocal;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.UnidadDependenciaEnum;
import co.gov.policia.dinae.siedu.dto.SeguimientoPAEDTO;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.DashboardCapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.DashboardCapacitacion;
import co.gov.policia.dinae.siedu.modelo.PAE;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.DashboardCapacitacionService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.gov.policia.dinae.siedu.servicios.PAEService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Named
@SessionScoped
public class SeguimientoDesarrolloPAEController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SeguimientoDesarrolloPAEController.class);

    @Inject
    protected LoginFaces loginFaces;
    @Inject
    protected NavigationFaces navigationFaces;
    @EJB
    private PAEService servicePAE;
    @EJB
    private APPService serviceAPP;
    @EJB
    private IUnidadDependenciaLocal unidadDependencia;
    @EJB
    private DashboardCapacitacionService service;

    private DashboardCapacitacionFiltro filtro;
    private List<PAE> vigencias;
    private List<UnidadDependencia> escuelas;
    private List<DashboardCapacitacion> list;
    private List<DashboardCapacitacion> listAvanceGeneral;
    private List<DashboardCapacitacion> listAvanceGeneralAnio;
    private List<DashboardCapacitacion> listAvanceMetaGeneral;
    private Date currentDay;
    private Long iSem;
    private Long iiSem;
    private Long iiiSem;
    private Long ivSem;
    private Long iSemC;
    private Long iiSemC;
    private Long iiiSemC;
    private Long ivSemC;
    private Long metaGeneralDinae;
    private Long totalGeneralDinae;
    private Long metaGeneral;
    private Long totalGeneral;
    private double avanceGeneralValue;
    private Long avanceGeneralValueDinae;
    private String year;
    private BarChartModel barModelGeneral;
    private BarChartModel barModel;
    private BarChartModel barModelTrimI;
    private BarChartModel barModelTrimII;
    private BarChartModel barModelTrimIII;
    private BarChartModel barModelTrimIV;
    private List<SeguimientoPAEDTO> listSeguimientoPAEDTO;
    private boolean renderObject;
    private boolean validaRol;
    private Integer maxValue;
    private ArrayList<Integer> arrayList;
    private String unidad;

    public SeguimientoDesarrolloPAEController() {
        LOG.trace("metodo: constructor()");
    }

    @PostConstruct
    public void initialize() {
        LOG.trace("metodo: initialize()");
        this.filtro = new DashboardCapacitacionFiltro();
        this.loadVigencias();
        this.loadEscuelas();
        this.list = new ArrayList<>();
        this.listAvanceGeneralAnio = new ArrayList<>();
        this.listAvanceMetaGeneral = new ArrayList<>();
        this.currentDay = this.parseCurrentDate();
        this.iSem = 0L;
        this.iiSem = 0L;
        this.iiiSem = 0L;
        this.ivSem = 0L;
        this.iSemC = 0L;
        this.iiSemC = 0L;
        this.iiiSemC = 0L;
        this.ivSemC = 0L;
        this.metaGeneral = 0L;
        this.totalGeneral = 0L;
        this.metaGeneralDinae = 0L;
        this.totalGeneralDinae = 0L;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        SimpleDateFormat form1 = new SimpleDateFormat("yyyy");
        year = form1.format(c.getTime());
        this.listSeguimientoPAEDTO = new ArrayList<>();
        this.renderObject = false;
        this.barModelGeneral = new BarChartModel();
        this.barModel = new BarChartModel();
        this.barModelTrimI = new BarChartModel();
        this.barModelTrimII = new BarChartModel();
        this.barModelTrimIII = new BarChartModel();
        this.barModelTrimIV = new BarChartModel();
        this.maxValue = 0;
        this.arrayList = new ArrayList<Integer>();
        this.unidad = "";
        this.avanceGeneralValueDinae = 0L;
        this.validaRol = false;
        this.validaRol();
    }

    public String initReturnCU() {
        LOG.trace("metodo: initReturnCU()");
        releaseControllers();
        return navigationFaces.redirect_CU_SEGUIMIENTO_DESARROLLO_PAE();
    }

    private void loadVigencias() {
        LOG.trace("metodo: loadVigencias()");
        try {
            setVigencias(servicePAE.consultarVigencias());
        } catch (Exception ex) {
            LOG.error("metodo: loadVigencias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    private void loadEscuelas() {
        LOG.trace("metodo: loadEscuelas()");
        try {
            setEscuelas(serviceAPP.consultarEscuelasVigentes());
            this.escuelas.add(this.unidadDependencia.getUnidadDependenciaById(23536L));
        } catch (Exception ex) {
            LOG.error("metodo: loadEscuelas() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public boolean validaRol() {
        validaRol = loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.VIECO.toString()) || loginFaces.getPerfilUsuarioDTO().getRolDepenencia().equals(UnidadDependenciaEnum.TELEM.toString());
        return validaRol;
    }

    public String balance(Long value) {
        if (value < 70) {
            return "FAILURE";
        } else if (value >= 70 && value < 90) {
            return "PARTIAL_SUCCESS";
        } else {
            return "SUCCESS";
        }
    }

    public String balanceGeneralSemI() {
        Long x;
        Date date = parseDate(year + "-01-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (this.iSem > 0) {
                x = (this.iSemC * 100) / this.iSem;
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String balanceGeneralSemII() {
        Long x;
        Date date = parseDate(year + "-04-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (this.iiSem > 0) {
                x = (this.iiSemC * 100) / this.iiSem;
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String balanceGeneralSemIII() {
        Long x;
        Date date = parseDate(year + "-07-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (this.iiiSem > 0) {
                x = (this.iiiSemC * 100) / this.iiiSem;
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String balanceGeneralSemIV() {
        Long x;
        Date date = parseDate(year + "-10-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (this.ivSem > 0) {
                x = (this.ivSemC * 100) / this.ivSem;
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public void findCapacitaciones() {
        LOG.trace("metodo: findCapacitaciones()");
        try {
            this.iSem = 0L;
            this.iiSem = 0L;
            this.iiiSem = 0L;
            this.ivSem = 0L;
            this.iSemC = 0L;
            this.iiSemC = 0L;
            this.iiiSemC = 0L;
            this.ivSemC = 0L;
            this.metaGeneral = 0L;
            this.totalGeneral = 0L;
            this.avanceGeneralValue = 0L;
            if (!this.validaRol) {
                this.filtro.setEscuela(this.loginFaces.getPerfilUsuarioDTO().getUnidadDependencia());
            }
            setList(this.service.findByFilter(filtro));
            if (!this.list.isEmpty()) {
                for (DashboardCapacitacion i : this.list) {
                    this.iSem += i.getmT1();
                    this.iiSem += i.getmT2();
                    this.iiiSem += i.getmT3();
                    this.ivSem += i.getmT4();
                    this.iSemC += i.getcT1();
                    this.iiSemC += i.getcT2();
                    this.iiiSemC += i.getcT3();
                    this.ivSemC += i.getcT4();
                    this.metaGeneral += i.getMeta();
                    this.totalGeneral += i.getTotal();
                }
                this.avanceGeneralValue = (this.totalGeneral * 100) / this.metaGeneral;
                long tmp = Math.round(this.avanceGeneralValue);
                this.avanceGeneralValue = (double) tmp;
                this.createBarModels();
                this.renderObject = true;
                this.avanceGeneralEstrategias();
                this.createBarModelsTrim();
                this.listAvanceGeneralAnio = this.service.findYearGeneralTargetByFilter(filtro);
            }
        } catch (Exception ex) {
            LOG.error("metodo: find() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String generalAvance() {
        double x = this.avanceGeneralValue / this.list.size();
        if (x < 70) {
            return "FAILURE";
        } else if (x >= 70 && x < 90) {
            return "PARTIAL_SUCCESS";
        } else {
            return "SUCCESS";
        }
    }

    public String generalAvance(DashboardCapacitacion record) {
        Long x = (record.getTotal() * 100) / record.getMeta();
        if (x < 70) {
            return "FAILURE";
        } else if (x >= 70 && x < 90) {
            return "PARTIAL_SUCCESS";
        } else {
            return "SUCCESS";
        }
    }

    public Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date parseCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(cal.getTime());
        String formatted = format1.format(cal.getTime());
        Date currentDate = this.parseDate(formatted);
        return currentDate;
    }

    public String avanceTrimestreI(DashboardCapacitacion record) {
        LOG.trace("metodo: avanceTrimestreI()");
        Long x = -1L;
        Date date = parseDate(record.getVigencia() + "-01-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (record.getmT1() != null && record.getmT1() > 0) {
                x = (record.getcT1() * 100) / record.getmT1();
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String avanceTrimestreII(DashboardCapacitacion record) {
        LOG.trace("metodo: avanceTrimestreII()");
        Long x = -1L;
        Date date = parseDate(record.getVigencia() + "-04-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (record.getmT2() != null && record.getmT2() > 0) {
                x = (record.getcT2() * 100) / record.getmT2();
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String avanceTrimestreIII(DashboardCapacitacion record) {
        LOG.trace("metodo: avanceTrimestreIII()");
        Long x = -1L;
        Date date = parseDate(record.getVigencia() + "-07-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (record.getmT3() != null && record.getmT3() > 0) {
                x = (record.getcT3() * 100) / record.getmT3();
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String avanceTrimestreIV(DashboardCapacitacion record) {
        LOG.trace("metodo: avanceTrimestreIV()");
        Long x = -1L;
        Date date = parseDate(record.getVigencia() + "-10-01");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else {
            if (record.getmT4() != null && record.getmT4() > 0) {
                x = (record.getcT4() * 100) / record.getmT4();
            } else {
                x = 100L;
            }
            if (x < 70) {
                return "FAILURE";
            } else if (x >= 70 && x < 90) {
                return "PARTIAL_SUCCESS";
            } else {
                return "SUCCESS";
            }
        }
    }

    public String avanceGeneral(DashboardCapacitacion record) {
        LOG.trace("metodo: avanceTrimestreIV()");
        Date date = parseDate(record.getVigencia() + "-12-15");
        if (date.compareTo(currentDay) >= 0) {
            return "PENDING";
        } else if (record.getAvance() < 70) {
            return "FAILURE";
        } else if (record.getAvance() >= 70 && record.getAvance() < 90) {
            return "PARTIAL_SUCCESS";
        } else {
            return "SUCCESS";
        }
    }

    /*
      Método para crear tabla de avance general     
     */
    public void avanceGeneralEstrategias() throws ServiceException {
        LOG.trace("metodo: avanceGeneralEstrategias()");
        try {
            this.setListAvanceGeneral(this.service.findGeneralTargetByFilter(filtro));
        } catch (Exception ex) {
            LOG.error("metodo: avanceGeneralEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public void avanceGeneralPAE() {
        LOG.trace("método: avanceGeneralEstrategias()");
        try {
            this.setListAvanceMetaGeneral(this.service.findYearGeneralTarget(filtro));
        } catch (Exception ex) {
            LOG.error("metodo: avanceGeneralEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
        }
    }

    public String avanceEstrategia(DashboardCapacitacion record) throws ServiceException {
        LOG.trace("metodo: avanceEstrategia()");
        Long avanceEstrategia;
        String porcentaje;
        try {
            avanceEstrategia = (record.getTotal() * 100) / record.getMeta();
            porcentaje = avanceEstrategia.toString() + "%";
            return porcentaje;
        } catch (Exception ex) {
            LOG.error("metodo: avanceGeneralEstrategias() ->> mensaje: {}", ex.getMessage());
            addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
            return null;
        }
    }

    /*
    Fin Método crear tabla de avance general
     */

 /*
    Inicio métodos creación de la gráfica.    
     */
    public BarChartModel getBarModelGeneral() {
        return barModelGeneral;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public BarChartModel getBarModelTrimI() {
        return barModelTrimI;
    }

    public BarChartModel getBarModelTrimII() {
        return barModelTrimII;
    }

    public BarChartModel getBarModelTrimIII() {
        return barModelTrimIII;
    }

    public BarChartModel getBarModelTrimIV() {
        return barModelTrimIV;
    }

    private BarChartModel initBarModel() throws ServiceException, JpaDinaeException {
        this.listSeguimientoPAEDTO = new ArrayList<>();
        this.arrayList = new ArrayList<Integer>();
        BarChartModel model = new BarChartModel();
        ChartSeries metaEstrategia = new ChartSeries();
        ChartSeries ejecutadoEstrategia = new ChartSeries();
        ejecutadoEstrategia.setLabel("EJECUTADO");
        metaEstrategia.setLabel("META");
        this.setListSeguimientoPAEDTO(this.service.findByFilterGraph(filtro));
        if (!this.listSeguimientoPAEDTO.isEmpty()) {
            for (SeguimientoPAEDTO i : this.listSeguimientoPAEDTO) {
                metaEstrategia.set(i.getEstrategia(), 100);
                ejecutadoEstrategia.set(i.getEstrategia(), (i.getTotal() * 100) / i.getMeta());
                arrayList.add(100);
            }
            model.addSeries(metaEstrategia);
            model.addSeries(ejecutadoEstrategia);
            model.setMouseoverHighlight(true);
            model.setShowDatatip(true);
            model.setShowPointLabels(true);
            model.setAnimate(true);
            return model;
        } else {
            return null;
        }
    }

    private BarChartModel initBarModelGeneral() throws ServiceException, JpaDinaeException {
        this.listAvanceMetaGeneral = new ArrayList<>();
        this.arrayList = new ArrayList<Integer>();
        BarChartModel model = new BarChartModel();
        ChartSeries metaEstrategia = new ChartSeries();
        ChartSeries ejecutadoEstrategia = new ChartSeries();
        ejecutadoEstrategia.setLabel("EJECUTADO");
        metaEstrategia.setLabel("META");
        this.setListAvanceMetaGeneral(this.service.findYearGeneralTarget(filtro));
        if (!this.listAvanceMetaGeneral.isEmpty()) {
            for (DashboardCapacitacion i : this.listAvanceMetaGeneral) {
                metaEstrategia.set(i.getEstrategia(), 100);
                ejecutadoEstrategia.set(i.getEstrategia(), (i.getTotal() * 100) / i.getMeta());
                this.metaGeneralDinae += i.getMeta();
                this.totalGeneralDinae += i.getTotal();
                this.arrayList.add(100);
            }
            this.avanceGeneralValueDinae = (this.totalGeneralDinae * 100) / this.metaGeneralDinae;
            model.addSeries(metaEstrategia);
            model.addSeries(ejecutadoEstrategia);
            model.setMouseoverHighlight(true);
            model.setShowDatatip(true);
            model.setShowPointLabels(true);
            model.setAnimate(true);
            return model;
        } else {
            return null;
        }
    }

    private BarChartModel initBarModelTrimI() throws ServiceException, JpaDinaeException {
        BarChartModel modelTrimI = new BarChartModel();
        ChartSeries metaEstrategiaTrimI = new ChartSeries();
        metaEstrategiaTrimI.setLabel("META");
        ChartSeries ejecutadoEstrategiaTrimI = new ChartSeries();
        ejecutadoEstrategiaTrimI.setLabel("EJECUTADO");
        for (DashboardCapacitacion i : this.listAvanceGeneral) {
            if (i.getmT1() > 0) {
                metaEstrategiaTrimI.set(i.getEstrategia(), 100);
                ejecutadoEstrategiaTrimI.set(i.getEstrategia(), (i.getcT1() * 100) / i.getmT1());
            } else {
                metaEstrategiaTrimI.set(i.getEstrategia(), 0);
                ejecutadoEstrategiaTrimI.set(i.getEstrategia(), 0);
            }
        }
        modelTrimI.addSeries(metaEstrategiaTrimI);
        modelTrimI.addSeries(ejecutadoEstrategiaTrimI);
        modelTrimI.setTitle(getPropertyFromBundle("capacitacion.trimestreT1"));
        modelTrimI.setMouseoverHighlight(true);
        modelTrimI.setShowDatatip(true);
        modelTrimI.setShowPointLabels(true);
        modelTrimI.setAnimate(true);
        return modelTrimI;
    }

    private BarChartModel initBarModelTrimII() throws ServiceException, JpaDinaeException {
        BarChartModel modelTrimII = new BarChartModel();
        ChartSeries metaEstrategiaTrimII = new ChartSeries();
        metaEstrategiaTrimII.setLabel("META");
        ChartSeries ejecutadoEstrategiaTrimII = new ChartSeries();
        ejecutadoEstrategiaTrimII.setLabel("EJECUTADO");
        for (DashboardCapacitacion i : this.listAvanceGeneral) {
            if (i.getmT2() > 0) {
                metaEstrategiaTrimII.set(i.getEstrategia(), 100);
                ejecutadoEstrategiaTrimII.set(i.getEstrategia(), (i.getcT2() * 100) / i.getmT2());
            } else {
                metaEstrategiaTrimII.set(i.getEstrategia(), 0);
                ejecutadoEstrategiaTrimII.set(i.getEstrategia(), 0);
            }

        }
        modelTrimII.addSeries(metaEstrategiaTrimII);
        modelTrimII.addSeries(ejecutadoEstrategiaTrimII);
        modelTrimII.setTitle(getPropertyFromBundle("capacitacion.trimestreT2"));
        modelTrimII.setMouseoverHighlight(true);
        modelTrimII.setShowDatatip(true);
        modelTrimII.setShowPointLabels(true);
        modelTrimII.setAnimate(true);
        return modelTrimII;
    }

    private BarChartModel initBarModelTrimIII() throws ServiceException, JpaDinaeException {
        BarChartModel modelTrimIII = new BarChartModel();
        ChartSeries metaEstrategiaTrimIII = new ChartSeries();
        metaEstrategiaTrimIII.setLabel("META");
        ChartSeries ejecutadoEstrategiaTrimIII = new ChartSeries();
        ejecutadoEstrategiaTrimIII.setLabel("EJECUTADO");
        for (DashboardCapacitacion i : this.listAvanceGeneral) {
            if (i.getmT3() > 0) {
                metaEstrategiaTrimIII.set(i.getEstrategia(), 100);
                ejecutadoEstrategiaTrimIII.set(i.getEstrategia(), (i.getcT3() * 100) / i.getmT3());
            } else {
                metaEstrategiaTrimIII.set(i.getEstrategia(), 0);
                ejecutadoEstrategiaTrimIII.set(i.getEstrategia(), 0);
            }
        }
        modelTrimIII.addSeries(metaEstrategiaTrimIII);
        modelTrimIII.addSeries(ejecutadoEstrategiaTrimIII);
        modelTrimIII.setTitle(getPropertyFromBundle("capacitacion.trimestreT3"));
        modelTrimIII.setMouseoverHighlight(true);
        modelTrimIII.setShowDatatip(true);
        modelTrimIII.setShowPointLabels(true);
        modelTrimIII.setAnimate(true);
        return modelTrimIII;
    }

    private BarChartModel initBarModelTrimIV() throws ServiceException, JpaDinaeException {
        BarChartModel modelTrimIV = new BarChartModel();
        ChartSeries metaEstrategiaTrimIV = new ChartSeries();
        metaEstrategiaTrimIV.setLabel("META");
        ChartSeries ejecutadoEstrategiaTrimIV = new ChartSeries();
        ejecutadoEstrategiaTrimIV.setLabel("EJECUTADO");
        for (DashboardCapacitacion i : this.listAvanceGeneral) {
            if (i.getmT4() > 0) {
                metaEstrategiaTrimIV.set(i.getEstrategia(), 100);
                ejecutadoEstrategiaTrimIV.set(i.getEstrategia(), (i.getcT4() * 100) / i.getmT4());
            } else {
                metaEstrategiaTrimIV.set(i.getEstrategia(), 0);
                ejecutadoEstrategiaTrimIV.set(i.getEstrategia(), 0);
            }
        }
        modelTrimIV.addSeries(metaEstrategiaTrimIV);
        modelTrimIV.addSeries(ejecutadoEstrategiaTrimIV);
        modelTrimIV.setTitle(getPropertyFromBundle("capacitacion.trimestreT4"));
        modelTrimIV.setMouseoverHighlight(true);
        modelTrimIV.setShowDatatip(true);
        modelTrimIV.setShowPointLabels(true);
        modelTrimIV.setAnimate(true);
        return modelTrimIV;
    }

    private void createBarModelGeneral() throws ServiceException, JpaDinaeException {
        this.barModelGeneral = this.initBarModelGeneral();
        barModelGeneral.setLegendPosition("ne");
        Axis xAxis = barModelGeneral.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModelGeneral.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void createBarModelTrimI() throws ServiceException, JpaDinaeException {
        this.barModelTrimI = initBarModelTrimI();
        barModelTrimI.setLegendPosition("ne");
        Axis xAxis = barModelTrimI.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModelTrimI.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void createBarModelTrimII() throws ServiceException, JpaDinaeException {
        this.barModelTrimII = initBarModelTrimII();
        barModelTrimII.setLegendPosition("ne");
        Axis xAxis = barModelTrimII.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModelTrimII.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void createBarModelTrimIII() throws ServiceException, JpaDinaeException {
        this.barModelTrimIII = initBarModelTrimIII();
        barModelTrimIII.setLegendPosition("ne");
        Axis xAxis = barModelTrimIII.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModelTrimIII.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void createBarModelTrimIV() throws ServiceException, JpaDinaeException {
        this.barModelTrimIV = initBarModelTrimIV();
        barModelTrimIV.setLegendPosition("ne");
        Axis xAxis = barModelTrimIV.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModelTrimIV.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void createBarModels() throws ServiceException, JpaDinaeException {
        createBarModel();
    }

    private void createBarModelsTrim() throws ServiceException, JpaDinaeException {
        this.createBarModelTrimI();
        this.createBarModelTrimII();
        this.createBarModelTrimIII();
        this.createBarModelTrimIV();
        this.createBarModelGeneral();
    }

    private void createBarModel() throws ServiceException, JpaDinaeException {
        this.barModel = initBarModel();
        if (this.filtro.getEscuela() == null) {
            this.unidad = "DIRECCIÓN NACIONAL DE ESCUELAS Y ESCUELAS";
        } else {
            this.unidad = this.unidadDependencia.getUnidadDependenciaById(filtro.getEscuela()).getDescripcionDependencia();
        }
        barModel.setLegendPosition("ne");
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setTickAngle(-45);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Total programado %");
        yAxis.setMin(0);
        this.maxValue = 100;
        yAxis.setMax(this.maxValue);
    }

    /*
    Fin métodos creación de la gráfica.
     */
    public Long onCalculateAVG(DashboardCapacitacion record) {
        Long avg;
        avg = (record.getTotal() * 100) / record.getMeta();
        return avg;
    }

    public void onBack() {
        this.renderObject = false;
    }

    public List<PAE> getVigencias() {
        return vigencias;
    }

    public void setVigencias(List<PAE> vigencias) {
        this.vigencias = vigencias;
    }

    public DashboardCapacitacionFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(DashboardCapacitacionFiltro filtro) {
        this.filtro = filtro;
    }

    public List<UnidadDependencia> getEscuelas() {
        return escuelas;
    }

    public void setEscuelas(List<UnidadDependencia> escuelas) {
        this.escuelas = escuelas;
    }

    public List<DashboardCapacitacion> getList() {
        return list;
    }

    public void setList(List<DashboardCapacitacion> list) {
        this.list = list;
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
    }

    public Long getiSem() {
        return iSem;
    }

    public void setiSem(Long iSem) {
        this.iSem = iSem;
    }

    public Long getIiSem() {
        return iiSem;
    }

    public void setIiSem(Long iiSem) {
        this.iiSem = iiSem;
    }

    public Long getIiiSem() {
        return iiiSem;
    }

    public void setIiiSem(Long iiiSem) {
        this.iiiSem = iiiSem;
    }

    public Long getIvSem() {
        return ivSem;
    }

    public void setIvSem(Long ivSem) {
        this.ivSem = ivSem;
    }

    public Long getiSemC() {
        return iSemC;
    }

    public void setiSemC(Long iSemC) {
        this.iSemC = iSemC;
    }

    public Long getIiSemC() {
        return iiSemC;
    }

    public void setIiSemC(Long iiSemC) {
        this.iiSemC = iiSemC;
    }

    public Long getIiiSemC() {
        return iiiSemC;
    }

    public void setIiiSemC(Long iiiSemC) {
        this.iiiSemC = iiiSemC;
    }

    public Long getIvSemC() {
        return ivSemC;
    }

    public void setIvSemC(Long ivSemC) {
        this.ivSemC = ivSemC;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getMetaGeneral() {
        return metaGeneral;
    }

    public void setMetaGeneral(Long metaGeneral) {
        this.metaGeneral = metaGeneral;
    }

    public Long getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(Long totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    public double getAvanceGeneralValue() {
        return avanceGeneralValue;
    }

    public void setAvanceGeneralValue(double avanceGeneralValue) {
        this.avanceGeneralValue = avanceGeneralValue;
    }

    public List<SeguimientoPAEDTO> getListSeguimientoPAEDTO() {
        return listSeguimientoPAEDTO;
    }

    public void setListSeguimientoPAEDTO(List<SeguimientoPAEDTO> listSeguimientoPAEDTO) {
        this.listSeguimientoPAEDTO = listSeguimientoPAEDTO;
    }

    public boolean isRenderObject() {
        return renderObject;
    }

    public void setRenderObject(boolean renderObject) {
        this.renderObject = renderObject;
    }

    public List<DashboardCapacitacion> getListAvanceGeneral() {
        return listAvanceGeneral;
    }

    public void setListAvanceGeneral(List<DashboardCapacitacion> listAvanceGeneral) {
        this.listAvanceGeneral = listAvanceGeneral;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public List<DashboardCapacitacion> getListAvanceGeneralAnio() {
        return listAvanceGeneralAnio;
    }

    public void setListAvanceGeneralAnio(List<DashboardCapacitacion> listAvanceGeneralAnio) {
        this.listAvanceGeneralAnio = listAvanceGeneralAnio;
    }

    public List<DashboardCapacitacion> getListAvanceMetaGeneral() {
        return listAvanceMetaGeneral;
    }

    public void setListAvanceMetaGeneral(List<DashboardCapacitacion> listAvanceMetaGeneral) {
        this.listAvanceMetaGeneral = listAvanceMetaGeneral;
    }

    public Long getMetaGeneralDinae() {
        return metaGeneralDinae;
    }

    public void setMetaGeneralDinae(Long metaGeneralDinae) {
        this.metaGeneralDinae = metaGeneralDinae;
    }

    public Long getTotalGeneralDinae() {
        return totalGeneralDinae;
    }

    public void setTotalGeneralDinae(Long totalGeneralDinae) {
        this.totalGeneralDinae = totalGeneralDinae;
    }

    public Long getAvanceGeneralValueDinae() {
        return avanceGeneralValueDinae;
    }

    public void setAvanceGeneralValueDinae(Long avanceGeneralValueDinae) {
        this.avanceGeneralValueDinae = avanceGeneralValueDinae;
    }

    public boolean isValidaRol() {
        return validaRol;
    }

    public void setValidaRol(boolean validaRol) {
        this.validaRol = validaRol;
    }

}
