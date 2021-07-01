package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.ValidacionCompromisoPeriodoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.ICompromisoPeriodoLocal;
import co.gov.policia.dinae.interfaces.IPeriodoLocal;
import co.gov.policia.dinae.modelo.CompromisoPeriodo;
import co.gov.policia.dinae.modelo.Periodo;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author cguzman
 */
@Named(value = "validacionesGeneralesFaces")
@RequestScoped
public class ValidacionesGeneralesFaces implements Serializable {

    @EJB
    private IPeriodoLocal _iPeriodoLocal;

    @EJB
    private ICompromisoPeriodoLocal _iCompromisoPeriodoLocal;

    public String validarCompromisosPeriodo(Long idPeriodo) throws JpaDinaeException {

        Periodo periodo = _iPeriodoLocal.getPeriodoPorId(idPeriodo);
        List<ValidacionCompromisoPeriodoDTO> compromisoPeriodos = _iCompromisoPeriodoLocal.conteoValidacionComprimisosPeriodo(idPeriodo);
        int contFormulacion = 0, contInformeAvance = 0, contInformeFinal = 0;

        if (compromisoPeriodos.isEmpty()) {
            return IConstantes.TIPO_REGISTRO_PERIODO_NECESIDAD.equals(periodo.getTipoRegistro())
                    ? IConstantes.VALIDACIONES_NECESIDADES_ERROR_NO_COMPROMISOS
                    : IConstantes.VALIDACIONES_CONVOCATORIAS_ERROR_NO_COMPROMISOS;
        }

        for (ValidacionCompromisoPeriodoDTO compromiso : compromisoPeriodos) {

            if (IConstantes.COMPROMISO_PERIODO_FORMULACION_DEL_PROYECTO.compareTo(compromiso.getIdTipoCompromiso()) == 0) {
                contFormulacion = compromiso.getCantidadCompromisos().intValue();
            } else if (IConstantes.COMPROMISO_PERIODO_INFORME_DE_AVANCE.compareTo(compromiso.getIdTipoCompromiso()) == 0) {
                contInformeAvance = compromiso.getCantidadCompromisos().intValue();
            } else if (IConstantes.COMPROMISO_PERIODO_INFORME_FINAL.compareTo(compromiso.getIdTipoCompromiso()) == 0) {
                contInformeFinal = compromiso.getCantidadCompromisos().intValue();
            }
        }

        if (IConstantes.TIPO_REGISTRO_PERIODO_NECESIDAD.equals(periodo.getTipoRegistro())) {
            if (contFormulacion == 0) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_FORMULACION_REQUERIDA;
            }

            if (contFormulacion > 1) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_FORMULACION_MULTIPLE;
            }

            if (contInformeAvance == 0) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_INFROME_AVANCE_REQUERIDO;
            }

            if (contInformeFinal == 0) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_INFORME_FINAL_REQUERIDO;
            }

            if (contInformeFinal > 1) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_INFORME_FINAL_MULTIPLE;
            }
        } else if (IConstantes.TIPO_REGISTRO_PERIODO_CONVOCATORIA.equals(periodo.getTipoRegistro())) {

            if (contFormulacion > 1) {
                return IConstantes.VALIDACIONES_CONVOCATORIAS_ERROR_FORMULACION_MULTIPLE;
            }

            if (contFormulacion == 0) {
                return IConstantes.VALIDACIONES_NECESIDADES_ERROR_FORMULACION_REQUERIDA;
            }

            if (contInformeAvance == 0) {
                return IConstantes.VALIDACIONES_CONVOCATORIAS_ERROR_INFORME_AVANCE_REQUERIDO;
            }

            if (contInformeFinal == 0) {
                return IConstantes.VALIDACIONES_CONVOCATORIAS_ERROR_INFORME_FINAL_REQUERIDO;
            }

            if (contInformeFinal > 1) {
                return IConstantes.VALIDACIONES_CONVOCATORIAS_ERROR_INFORME_FINAL_MULTIPLE;
            }
        }
        return null;
    }    
}
