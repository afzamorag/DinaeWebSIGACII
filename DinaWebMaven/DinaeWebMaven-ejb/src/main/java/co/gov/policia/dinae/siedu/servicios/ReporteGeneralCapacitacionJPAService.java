/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.servicios;

import co.gov.policia.dinae.siedu.dto.VwmPersonalCapacitadoDTO;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.filtros.ReporteGeneralCapacitacionFiltro;
import co.gov.policia.dinae.siedu.modelo.VwmPersonalCapacitado;
import co.gov.policia.dinae.siedu.sdo.GenericSDO;
import co.gov.policia.dinae.siedu.sdo.GenericSDOQualifier;
import co.gov.policia.dinae.siedu.sdo.SDO;
import co.gov.policia.dinae.siedu.util.ReportBuild;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.StreamedContent;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@Stateless
public class ReporteGeneralCapacitacionJPAService implements ReporteGeneralCapacitacionService {

    private static final Logger LOG = LoggerFactory.getLogger(ReporteGeneralCapacitacionJPAService.class);
    @PersistenceContext(unitName = "DinaeWeb-PU")
    private EntityManager em;
    @Inject
    @GenericSDOQualifier
    private SDO sdo;

    private ReportBuild rBuilder;
    private Workbook workbook = new XSSFWorkbook();
    private Sheet sheet = workbook.createSheet();

    @PostConstruct
    public void init() {
        LOG.trace("metodo: init()");
        if (sdo == null) {
            sdo = new GenericSDO();
        }
    }

    @Override
    public StreamedContent findByFilterTrainneds(ReporteGeneralCapacitacionFiltro filtro) throws ServiceException {
        StreamedContent report = null;
        LOG.trace("metodo: findByFilterCapacitados() ->> parametros: filtro {}", filtro);
        List<VwmPersonalCapacitadoDTO> list;
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> params = null;
        jpql.append("SELECT NEW co.gov.policia.dinae.siedu.dto.VwmPersonalCapacitadoDTO(d.vigencia, d.siglaEscuela, d.nivelAcademico, d.estrategia, d.modalidad, d.codigoPrograma, d.programa, d.trimestre, d.fechaInicio, d.fechaFinal, d.direccionCapacitada, d.regionalCapacitada, d.ubicacionUnidad, d.unidadCapacitada, d.categoria, d.grado, d.genero, d.cargoAnterior, d.identificacion, d.nombres, d.apellidos, d.estadoParticipante) FROM VwmPersonalCapacitado d WHERE 1 = 1 ");
        if (filtro != null) {
            params = new HashMap<>();
            if (filtro.getCategoria() != null) {
                jpql.append("AND d.categoria = :categoria ");
                params.put("categoria", filtro.getCategoria());
            }
            if (filtro.getCodigoPrograma() != null) {
                jpql.append("AND d.codigoPrograma = :codigoPrograma ");
                params.put("codigoPrograma", filtro.getCodigoPrograma());
            }
            if (filtro.getDireccionCapacitada() != null) {
                jpql.append("AND d.idDireccionCapacitada = :idDireccionCapacitada ");
                params.put("idDireccionCapacitada", filtro.getDireccionCapacitada());
            }
            if (filtro.getEscuela() != null) {
                jpql.append("AND d.idEscuela = :idEscuela ");
                params.put("idEscuela", filtro.getEscuela());
            }
            if (filtro.getEstrategia() != null) {
                jpql.append("AND d.idEstrategia = :idEstrategia ");
                params.put("idEstrategia", filtro.getEstrategia());
            }
            if (filtro.getIdModalidad() != null) {
                jpql.append("AND d.idModalidad = :idModalidad ");
                params.put("idModalidad", filtro.getIdModalidad());
            }
            if (filtro.getNivelAcademico() != null) {
                jpql.append("AND d.idNivelAcademico = :idNivelAcademico ");
                params.put("idNivelAcademico", filtro.getNivelAcademico());
            }
            if (filtro.getTrimestre() != 0) {
                jpql.append("AND d.trimestre = :trimestre ");
                params.put("trimestre", filtro.getTrimestre());
            }
            if (filtro.getUbicacionUnidad() != null) {
                jpql.append("AND d.ubicacionUnidad = :ubicacionUnidad ");
                params.put("ubicacionUnidad", filtro.getUbicacionUnidad());
            }
            if (filtro.getUnidadLaborando() != null) {
                jpql.append("AND d.idUnidadCapacitada = :idUnidadCapacitada ");
                params.put("idUnidadCapacitada", filtro.getUnidadLaborando());
            }
            if (filtro.getVigencia() != null) {
                jpql.append("AND d.vigencia = :vigencia ");
                params.put("vigencia", filtro.getVigencia());
            }
        }
        try {
            list = sdo.getResultListByJPQLQuery(em, jpql.toString(), params);
            if (!list.isEmpty()) {
                this.rBuilder = new ReportBuild();
                VwmPersonalCapacitadoDTO obj = new VwmPersonalCapacitadoDTO();
                this.getSheet(obj);
                this.sheet = rBuilder.getSheet(obj, sheet);
                int rowCount = 0;
                for (VwmPersonalCapacitadoDTO record : list) {
                    Row row = sheet.createRow(++rowCount);
                    row.createCell(0).setCellValue((String) record.getVigencia());                    
                    row.createCell(1).setCellValue((String) record.getSiglaEscuela());                    
                    row.createCell(2).setCellValue((String) record.getNivelAcademico());                    
                    row.createCell(3).setCellValue((String) record.getEstrategia());                    
                    row.createCell(4).setCellValue((String) record.getModalidad());
                    row.createCell(5).setCellValue(record.getCodigoPrograma());
                    row.createCell(6).setCellValue((String) record.getPrograma());
                    row.createCell(7).setCellValue(record.getTrimestre());
                    row.createCell(8).setCellValue((String) record.getFechaInicio());
                    row.createCell(9).setCellValue((String) record.getFechaFinal());                    
                    row.createCell(10).setCellValue((String) record.getDireccionCapacitada());
                    row.createCell(11).setCellValue((String) record.getRegionalCapacitada());                    
                    row.createCell(12).setCellValue((String) record.getUbicacionUnidad());                    
                    row.createCell(13).setCellValue((String) record.getUnidadCapacitada());                    
                    row.createCell(14).setCellValue((String) record.getCategoria());
                    row.createCell(15).setCellValue((String) record.getGrado());
                    row.createCell(16).setCellValue((String) record.getGenero());
                    row.createCell(17).setCellValue((String) record.getCargoAnterior());
                    row.createCell(18).setCellValue((String) record.getIdentificacion());
                    row.createCell(19).setCellValue((String) record.getNombres());
                    row.createCell(20).setCellValue((String) record.getApellidos());                                        
                    row.createCell(21).setCellValue((String) record.getEstadoParticipante());                    
                }
                report = this.rBuilder.writeExcel(this.workbook);
            }
        } catch (Exception ex) {
            LOG.error("metodo: findByFilterCapacitados() ->> mensaje: {}", ex.getMessage());
            throw new ServiceException(ex);
        }
        return report;
    }

    public void getSheet(Object obj) {
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("REPORTE");
    }
}
