package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.PropuestaNecesidadDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.PropuestaNecesidad;
import co.gov.policia.dinae.modelo.Proyecto;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Local
public interface IPropuestaNecesidadLocal {

  /**
   *
   * @param idPropuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  PropuestaNecesidad consultar(Long idPropuestaNecesidad) throws JpaDinaeException;

  /**
   * Cuantas propuesta necesiades tiene estado 'En elaboración', 'Aceptada´ y 'No aceptada'
   *
   * @param idPeriodo
   * @param idEstadoElaboracion
   * @param idEstadoAceptado
   * @param idEstadoNoAceptado
   * @return 0 Cuando no existen registros que no cumplen con la condicion, -1 cuando no existen registros que contar
   * @throws JpaDinaeException
   */
  int contarPropuestaNecesidadPorPeriodoYestados(Long idPeriodo, Long idEstadoElaboracion, Long idEstadoAceptado, Long idEstadoNoAceptado) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  int contarPropuestaNecesidadPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  int contarPropuestaNecesidadPorPeriodoyUnidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodo(Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYunidadPolicial(Long idPeriodo, Long idUnidadPolicial) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @param listaIdEstados
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYunidadPolicialyEstados(Long idPeriodo, Long idUnidadPolicial, List<Long> listaIdEstados) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @param idEstado
   * @return
   * @throws JpaDinaeException
   */
  int getNumeroPropuestaNecesidadPorPeriodoYunidadYestado(Long idPeriodo, Long idUnidadPolicial, Long idEstado) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idUnidadPolicial
   * @param idEstado
   * @param idTipoRol
   * @return
   * @throws JpaDinaeException
   */
  int getNumeroPropuestaNecesidadDesdeEjecutorPorPeriodoYunidadYestado(Long idPeriodo, Long idUnidadPolicial, Long idEstado, Long idTipoRol) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idEstado1
   * @param idEstado2
   * @param idEstado3
   * @param idUnidadPolicialSeleccionada
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoYEstado(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3, Long idUnidadPolicialSeleccionada) throws JpaDinaeException;

  /**
   *
   * @param idPeriodo
   * @param idEstado1
   * @param idEstado2
   * @param idEstado3
   * @param idUnidadPolicialSeleccionada
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidadDTO> getPropuestaNecesidadDTOPorPeriodoYEstado(Long idPeriodo, Long idEstado1, Long idEstado2, Long idEstado3, Long idUnidadPolicialSeleccionada) throws JpaDinaeException;

  /**
   *
   * @param propuestaNecesidad
   * @param tipoRolAdicionar
   * @param idUnidadPolicialEjecutorNecesidad
   * @return
   * @throws JpaDinaeException
   */
  PropuestaNecesidad guardarPropuestaCreaEjecutorNecesidad(PropuestaNecesidad propuestaNecesidad, Long tipoRolAdicionar, Long idUnidadPolicialEjecutorNecesidad) throws JpaDinaeException;

  /**
   * Metodo que almacena la propuesta
   *
   * @param propuestaNecesidad
   * @return
   * @throws JpaDinaeException
   */
  PropuestaNecesidad guardar(PropuestaNecesidad propuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param listaPropuestaNecesidad
   * @throws JpaDinaeException
   */
  void guardarListaPropuesta(List<PropuestaNecesidad> listaPropuestaNecesidad) throws JpaDinaeException;

  /**
   *
   * @param listaPropuestaNecesidad
   * @param listaProyectos
   * @throws JpaDinaeException
   */
  void guardarListaPropuestaYgenerarProyecto(List<PropuestaNecesidad> listaPropuestaNecesidad, List<Proyecto> listaProyectos, String nombreReporteUnico, String nombreReporte, Long idPeriodo) throws JpaDinaeException;

  /**
   *
   * @param listaPropuestaNecesidad
   * @throws JpaDinaeException
   */
  void guardarListaPropuestaCreaEjecutorNecesidad(List<PropuestaNecesidad> listaPropuestaNecesidad) throws JpaDinaeException;

  /**
   * Método que cuenta cuantas propuestas tiene un periodo es un estado específico
   *
   * @param idPeriodo Id del periodo
   * @param idEstado Id del estado de las propuestas
   * @return Número de propuestas encontradas
   * @throws JpaDinaeException
   */
  int getNumeroPropuestaNecesidadPorPeriodoYestado(Long idPeriodo, Long idEstado) throws JpaDinaeException;

  /**
   * Método que busca las propuesta de necesidad de unidad en un periodo y por un estado especifíco
   *
   * @param idPeriodo Id del periodo a buscar
   * @param idUnidad id de la Unidad
   * @param idEstado id del Estado a buscar
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> getPropuestaNecesidadPorPeriodoPorUnidadPorEstado(Long idPeriodo, Long idUnidad, Long idEstado) throws JpaDinaeException;

  /**
   * Método que realiza el proceso de una busqueda de una propuesta de necesidad por filtros
   *
   * @param area Area a la cual pertenece la propuesta
   * @param linea Liena a la cual pertenece la propuestas
   * @param idUnidadPolicial Unidad policial a la que pertenece la propuesta
   * @param tema Tema de la propuestas
   * @param fechaInicial Fecha inicial de la propuestas (Requerido)
   * @param fechaFinal Fecha final de la propuesta (Requerido)
     * @param listaIdUnidades
   * @return
   * @throws JpaDinaeException
   */
  List<PropuestaNecesidad> consultaPropuestaNecesidades(Long area, Long linea, Long idUnidadPolicial, String tema, Date fechaInicial, Date fechaFinal, List<Long> listaIdUnidades) throws JpaDinaeException;

  /**
   * Método que envia una lista de prupuesta de necesiad a Vicin.
   *
   * @param propuestaNecesidades Lista de propuesta de necesidades realizado
   * @param identificacion
   * @throws JpaDinaeException
   */
  void enviarPropuestaVicin(List<PropuestaNecesidad> propuestaNecesidades, String identificacion) throws JpaDinaeException;

  /**
   * Método que devuelve las propuesta a los grupos de investigación debido a que alguna no fue aceptada
   *
   * @param propuestaNecesidades Listado de propuesta de necesidad
   * @param identificacion
   * @param nombreCompleto
   * @throws JpaDinaeException
   */
  void enviarPropuestaGrupoInvestigacion(List<PropuestaNecesidad> propuestaNecesidades, String identificacion, String nombreCompleto) throws JpaDinaeException;

  /**
   * Método para buscar las propuestas de un periodo por estado enviado a vicin,Preaprobada, Revisada
   *
   * @param idPeriodo
   * @param idEstadoVicin
   * @param idEstadoPreaprobada
   * @param IdEstadoRevisada
   * @return
   * @throws JpaDinaeException
   */
  int getNumeroPropuestaNecesidadPorPeriodoYestadoVicinPreaprobadaRevisada(Long idPeriodo, Long idEstadoVicin, Long idEstadoPreaprobada, Long IdEstadoRevisada) throws JpaDinaeException;
  
  /**
   * 
   * @param pn
   * @throws ServiceException 
   */
  public void eliminar(PropuestaNecesidad pn) throws ServiceException;
  
    /**
   *
   * @param record
     * @throws co.gov.policia.dinae.siedu.excepciones.ServiceException
   */
  public void update(PropuestaNecesidad record) throws ServiceException;
  
  /**
   * 
   * @param propuestaNecesidad
   * @param proyecto
   * @throws JpaDinaeException 
   */
  
  void guardarPropuestaYgenerarProyecto(PropuestaNecesidad propuestaNecesidad, Proyecto proyecto) throws JpaDinaeException;
  
}
