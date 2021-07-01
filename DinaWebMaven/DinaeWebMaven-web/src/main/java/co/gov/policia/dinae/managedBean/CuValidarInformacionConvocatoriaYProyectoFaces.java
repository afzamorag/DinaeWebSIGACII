package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.interfaces.IFuenteProyectoLocal;
import co.gov.policia.dinae.interfaces.IInvestigadorProyectoLocal;
import co.gov.policia.dinae.interfaces.IProyectoLocal;
import co.gov.policia.dinae.interfaces.ITemaLocal;
import co.gov.policia.dinae.interfaces.ITemaProyectoLocal;
import co.gov.policia.dinae.modelo.InvestigadorProyecto;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.modelo.Tema;
import co.gov.policia.dinae.modelo.TemaProyecto;
import co.gov.policia.dinae.util.JSFUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
@javax.inject.Named(value = "cuValidarInformacionConvocatoriaYProyectoFaces")
@javax.enterprise.context.SessionScoped
public class CuValidarInformacionConvocatoriaYProyectoFaces extends JSFUtils implements Serializable {

  @EJB
  private IProyectoLocal iProyectoLocal;

  @EJB
  private IInvestigadorProyectoLocal iInvestigadorProyectoLocal;

  @EJB
  private ITemaLocal iTemaLocal;

  @EJB
  private ITemaProyectoLocal iTemaProyectoLocal;

  @EJB
  private IFuenteProyectoLocal iFuenteProyectoLocal;

  /**
   *
   * @param listaProuestasProyectoValidar
   * @return
   * @throws Exception
   */
  public boolean validarInformacionValidaConvocatoriaYproyecto(List<Long> listaProuestasProyectoValidar) throws Exception {

    //SE VALIDA QUE LA INFORMACIÓN ESTE COMPLETA, 
    //ES DECIR QUE SE ENCUENTRE LA INFORMACIÓN  REQUERIDA INCLUYENDO LOS CASOS DE USO 
    //CU-PR-01, CU-PR-02, CU-PR-03, CU-PR-04 y CU-PR-05
    for (Long idProyectoActualizar : listaProuestasProyectoValidar) {

      Proyecto unProyecto = iProyectoLocal.obtenerProyectoPorId(idProyectoActualizar);
      //1.Información básica
      if (unProyecto.getTema() == null || unProyecto.getTema().trim().length() == 0
              || unProyecto.getTituloPropuesto() == null || unProyecto.getTituloPropuesto().trim().length() == 0
              || unProyecto.getLinea() == null
              || unProyecto.getFuncionProyecto() == null || unProyecto.getFuncionProyecto().trim().length() == 0) {
        adicionaMensajeError(
                keyPropertiesFactory.value(
                        "cu_co_3_lbl_msg_error_falta_info_enviar_infobasica",
                        new Object[]{""}));
        //new Object[]{ unProyecto.getTituloPropuesto() }) );
        return false;
      }
      //CUANDO EL REGISTRO, ES UN PROYECTO, SE VALIDA QUE LA FECHA DE APROBACION COMITE Y NUMERO DE ACTA, SE ENCUENTREN LLENOS.
      if (unProyecto.getCodigoProyecto() != null
              && (unProyecto.getFechaAprobacionComite() == null || unProyecto.getNroActaAprobacionComite() == null)) {

        adicionaMensajeError(
                keyPropertiesFactory.value(
                        "cu_co_3_lbl_msg_error_falta_info_enviar_infobasica",
                        new Object[]{""}));

      }

      //2.Talento humano
      //CU-PR-03 - Talento Humano
      int contador = iInvestigadorProyectoLocal.cuentaInvestigadorProyectoPorProyecto(idProyectoActualizar);
      if (contador == 0) {
        adicionaMensajeError(
                keyPropertiesFactory.value(
                        "cu_co_3_lbl_msg_error_falta_info_enviar_talentohumano",
                        new Object[]{""}));
        //new Object[]{ unProyecto.getTituloPropuesto() }) );
        return false;
      }

      //3.Planteamiento del proyecto
      //CU-PR-04 - Planteamiento
      List<Tema> listaTemas = iTemaLocal.getListaTemaTodos(IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO);
      for (Tema unTema : listaTemas) {

        TemaProyecto temaProyectoSeleccionado = iTemaProyectoLocal.getTemaProyectoPorTemaYproyecto(unTema.getIdTema(), unProyecto.getIdProyecto());

        if (temaProyectoSeleccionado == null
                || temaProyectoSeleccionado.getTexto() == null
                || temaProyectoSeleccionado.getTexto().trim().length() == 0
                || temaProyectoSeleccionado.getTexto().trim().equals("<br>")) {

          adicionaMensajeError("Debe ingresar información para la propuesta: Plantamiento Proyecto");
          return false;
        }
      }

      /*int contadorTemas = iTemaLocal.cuentaTemaTodos( IConstantes.DESTINO_TEMA_CU_PR_01_PROYECTO );
            
            int contadorTemasPorProyecto = iTemaProyectoLocal.cuentaTemaProyectoPorProyecto( idProyectoActualizar );
            //EL NUMEROD DE TEMA POR PROYECTO REGISTRADOS DEBE SER IGUAL AL NUMERO DE TEMAS HAY HABILITADOS
            if( contadorTemas != contadorTemasPorProyecto ){
                adicionaMensajeError( "Debe ingresar información para la propuesta: Plantamiento Proyecto" );
                                //new Object[]{ unProyecto.getTituloPropuesto() }) );
                return false;
            }*/
      //CU-PR-05
      //VERIFICAMOS LAS FUENTES DEL PROYECTO
      if (unProyecto.getCodigoProyecto() != null) {

        int numeroFuentes = iFuenteProyectoLocal.contarFuentesBaseProyectoByIdProyecto(unProyecto.getIdProyecto());
        if (numeroFuentes == 0) {

          adicionaMensajeError(
                  keyPropertiesFactory.value(
                          "cu_pr_5_lbl_msg_error_falta_info_enviar_presupuesto_f_finan",
                          new Object[]{""}));
          //new Object[]{ unProyecto.getTituloPropuesto() }) );
          return false;

        }

      }

      //VALIDAMOS LA INFORMACION DEL PERSONAL INVESTIGACION (PRESUPUESTO)
      //VERIFICAMOS QUE LOS INVESTIGADORES TENGAN LAS FUENTES FINANCIERAS SELECCIONADAS
      List<InvestigadorProyecto> listaInvestigadoresProyecto = iInvestigadorProyectoLocal.getListaInvestigadorProyectoPorProyecto(idProyectoActualizar);

      for (InvestigadorProyecto investigadorProyecto : listaInvestigadoresProyecto) {

        if (investigadorProyecto.getFuenteProyecto() == null) {
          adicionaMensajeError("Debe asignar las fuentes a los investigadores del proyecto");
          return false;
        }
      }

    }
    return true;
  }
}
