package co.gov.policia.dinae.util;

import co.gov.policia.dinae.interfaces.IDataModel;
import co.gov.policia.dinae.modelo.AreaCienciaTecnologia;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.GrupoInvestigacion;
import co.gov.policia.dinae.modelo.Linea;
import co.gov.policia.dinae.modelo.Tema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cguzman
 */
public class TablaGenericaParametros implements Serializable, IDataModel {

  public static final String TIPO_PARAM_GRUPO_INV = "GRUPO_INVESTIGACION";
  public static final String TIPO_PARAM_INSTITUCIONES = "INSTITUCIONES";
  public static final String TIPO_PARAM_AREA = "AREA";
  public static final String TIPO_PARAM_LINEA = "LINEA";
  public static final String TIPO_PARAM_TIPO_INV = "TIPO_INVESTIGADOR";
  public static final String TIPO_PARAM_ITEMS_PLANTEAMIENTO_PROYECTO = "ITEMS_PLANTEAMIENTO_PROYECTO";
  public static final String TIPO_PARAM_ITEMS_RESULTADOS_INFORME_FINAL_IMPL = "ITEMS_RESULTADOS_INFORME_FINAL_IMPL";
  public static final String TIPO_PARAM_ITEMS_INFO_TENER_ENCUENTA_IMPL = "ITEMS_INFO_TENER_ENCUENTA_IMPL";
  public static final String TIPO_PARAM_ITEMS_INFORME_AVANCE_IMPL = "ITEMS_INFORME_AVANCE_IMPL";
  public static final String TIPO_PARAM_ITEMS_TENER_CUENTA_INFORME_AVANCE_IMPL = "ITEMS_TENER_CUENTA_INFORME_AVANCE_IMPL";

  private Long idReg;

  private String valor;

  private String tipo;

  private String estado;

  private Object objetoDatos;

  private String sufijoParametro;

  public TablaGenericaParametros(Long idReg, String valor, String tipo, String estado, Object objetoDatos, String sufijoParametro) {
    this.idReg = idReg;
    this.valor = valor;
    this.tipo = tipo;
    this.estado = estado;
    this.objetoDatos = objetoDatos;
    this.sufijoParametro = sufijoParametro;
  }

  public TablaGenericaParametros(Long idReg, String valor, String tipo, String estado, Object objetoDatos) {
    this.idReg = idReg;
    this.valor = valor;
    this.tipo = tipo;
    this.estado = estado;
    this.objetoDatos = objetoDatos;
  }

  public TablaGenericaParametros() {
  }

  /**
   *
   * @param obj
   * @param tipo
   * @param sufijo
   * @return
   */
  public static List<TablaGenericaParametros> obtenerTablaGenericaParametros(List obj, String tipo, String sufijo) {

    List<TablaGenericaParametros> retorno = null;

    if (obj != null && tipo != null) {
      retorno = llenarListaTablaGenerica(obj, tipo, sufijo);
    }

    return retorno;
  }

  /**
   *
   * @param obj
   * @param tipo
   * @return
   */
  public static List<TablaGenericaParametros> obtenerTablaGenericaParametros(List obj, String tipo) {
    return obtenerTablaGenericaParametros(obj, tipo, null);
  }

  /**
   *
   * @param datos
   * @param tipo
   * @param sufijo
   * @return
   */
  private static List llenarListaTablaGenerica(List<Object> datos, String tipo, String sufijo) {

    List lista = new ArrayList();

    if (!datos.isEmpty()) {

      for (Object obj : datos) {

        if (obj instanceof GrupoInvestigacion) {
          GrupoInvestigacion grupoObj = (GrupoInvestigacion) obj;
          TablaGenericaParametros item = new TablaGenericaParametros(grupoObj.getIdGrupoInvestigacion(), grupoObj.getDescripcion(), tipo, grupoObj.getEstado(), grupoObj);
          lista.add(item);
        }

        if (obj instanceof Constantes) {
          Constantes constObj = (Constantes) obj;
          TablaGenericaParametros item = new TablaGenericaParametros(constObj.getIdConstantes(), constObj.getValor(), tipo, constObj.getEstado(), constObj, sufijo);
          lista.add(item);
        }

        if (obj instanceof AreaCienciaTecnologia) {
          AreaCienciaTecnologia areaObj = (AreaCienciaTecnologia) obj;
          TablaGenericaParametros item = new TablaGenericaParametros(areaObj.getIdAreaCienciaTecnologia(), areaObj.getNombre(), tipo, areaObj.getEstado(), areaObj);
          lista.add(item);
        }

        if (obj instanceof Linea) {
          Linea lineaObj = (Linea) obj;
          TablaGenericaParametros item = new TablaGenericaParametros(lineaObj.getIdLinea(), lineaObj.getNombre(), tipo, lineaObj.getEstado(), lineaObj);
          lista.add(item);
        }

        if (obj instanceof Tema) {
          Tema temaObj = (Tema) obj;
          TablaGenericaParametros item = new TablaGenericaParametros(temaObj.getIdTema(), temaObj.getDescripcionTema(), tipo, temaObj.getEstado(), temaObj, sufijo);
          lista.add(item);
        }
      }
    }

    return lista;
  }

  public Long getIdReg() {
    return idReg;
  }

  public void setIdReg(Long idReg) {
    this.idReg = idReg;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Object getObjetoDatos() {
    return objetoDatos;
  }

  public void setObjetoDatos(Object objetoDatos) {
    this.objetoDatos = objetoDatos;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getSufijoParametro() {
    return sufijoParametro;
  }

  public void setSufijoParametro(String sufijoParametro) {
    this.sufijoParametro = sufijoParametro;
  }

  @Override
  public String getLlaveModel() {
    if (this.idReg == null) {
      return null;
    }

    return this.idReg.toString();
  }

}
