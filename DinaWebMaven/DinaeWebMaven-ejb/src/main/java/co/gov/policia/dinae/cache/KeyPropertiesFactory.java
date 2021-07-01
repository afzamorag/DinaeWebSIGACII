package co.gov.policia.dinae.cache;

import co.gov.policia.dinae.dto.CategoriaPolicialDTO;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.CorreoParametrizaDTO;
import co.gov.policia.dinae.dto.GradoCategoriaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
public class KeyPropertiesFactory implements Serializable {

  private static KeyPropertiesFactory keyPropertiesFactory;
  private static Map<String, String> keyValues;
  private static List<String> paginaUrlNoRequierenLogin;

  private static Map<String, String> keyValuesParammetrosConexionLDAPWebLogic;
  private static String patronBusquedaLDAPweblogic;
  private static String atributoBusquedaLDAPweblogic;

  //PROPIEDADES MAIL
  private static String usuarioMail;
  private static String usuarioClave;
  private static boolean debugConsole;
  private static List<ConstantesDTO> listaConstantesCorreo;

  //MAPA DE PLANTILLAS CORREO
  private static Map<String, CorreoParametrizaDTO> mapaCorreoParametrizaDTO;

  //PARAMETROS CONEXION BD GENERACION REPORTES
  private static Map<String, String> keyValuesConexionBdGeneracionReportes;

  //CATEGORIAS POLICIALES
  private static List<CategoriaPolicialDTO> listaCategoriaPolicialDTO;
  private static Map<Long, List<GradoCategoriaDTO>> mapaListaGradoCategoriaDTO;

  // PATH FILES VIECO
  private static String pathFilesVIECO;

  /**
   *
   */
  private KeyPropertiesFactory() {

    keyValues = new HashMap<String, String>();
    paginaUrlNoRequierenLogin = new ArrayList<String>();
    keyValuesParammetrosConexionLDAPWebLogic = new HashMap<String, String>();
    patronBusquedaLDAPweblogic = null;
    atributoBusquedaLDAPweblogic = null;
    usuarioMail = null;
    usuarioClave = null;
    debugConsole = false;
    listaConstantesCorreo = null;
    mapaCorreoParametrizaDTO = null;
    listaCategoriaPolicialDTO = new ArrayList<CategoriaPolicialDTO>();
    mapaListaGradoCategoriaDTO = new HashMap<Long, List<GradoCategoriaDTO>>();
  }

  /**
   *
   * @return
   */
  public static KeyPropertiesFactory getInstance() {

    if (keyPropertiesFactory == null) {
      keyPropertiesFactory = new KeyPropertiesFactory();
    }
    return keyPropertiesFactory;
  }

  /**
   *
   * @param key
   * @return
   */
  public String value(String key) {

    String k = keyValues.get(key);
    return k == null ? key.concat(" -----NOT FOUND-----") : k;

  }

  /**
   *
   * @param key
   * @param parametros
   * @return
   */
  public String value(String key, Object[] parametros) {

    String k = keyValues.get(key);
    return k == null ? key.concat(" -----NOT FOUND-----") : String.format(k, parametros);

  }

  /**
   *
   * @param key
   * @param value
   */
  public void putKeyValueBundle(String key, String value) {
    keyValues.put(key, value);
  }

  /**
   *
   * @param url
   * @return
   */
  public boolean paginaNoRequiereLogin(String url) {

    return paginaUrlNoRequierenLogin.contains(url);

  }

  /**
   *
   * @param url
   */
  public void addPaginaNoRequiereLogin(String url) {

    paginaUrlNoRequierenLogin.add(url);

  }

  /**
   *
   * @param key
   * @param value
   */
  public void putKeyValueConexionLDAPWeblogic(String key, String value) {
    keyValuesParammetrosConexionLDAPWebLogic.put(key, value);
  }

  public Map<String, String> getKeyValuesParammetrosConexionLDAPWebLogic() {

    return keyValuesParammetrosConexionLDAPWebLogic;
  }

  public String getPatronBusquedaLDAPweblogic() {
    return patronBusquedaLDAPweblogic;
  }

  public static void setPatronBusquedaLDAPweblogic(String patronBusquedaLDAPweblogic) {
    KeyPropertiesFactory.patronBusquedaLDAPweblogic = patronBusquedaLDAPweblogic;
  }

  public String getAtributoBusquedaLDAPweblogic() {
    return atributoBusquedaLDAPweblogic;
  }

  public static void setAtributoBusquedaLDAPweblogic(String atributoBusquedaLDAPweblogic) {
    KeyPropertiesFactory.atributoBusquedaLDAPweblogic = atributoBusquedaLDAPweblogic;
  }

  public static String getUsuarioMail() {
    return usuarioMail;
  }

  public static void setUsuarioMail(String usuarioMail) {
    KeyPropertiesFactory.usuarioMail = usuarioMail;
  }

  public static String getUsuarioClave() {
    return usuarioClave;
  }

  public static void setUsuarioClave(String usuarioClave) {
    KeyPropertiesFactory.usuarioClave = usuarioClave;
  }

  public static boolean isDebugConsole() {
    return debugConsole;
  }

  public static void setDebugConsole(boolean debugConsole) {
    KeyPropertiesFactory.debugConsole = debugConsole;
  }

  public static List<ConstantesDTO> getListaConstantesCorreo() {
    if (listaConstantesCorreo == null) {
      listaConstantesCorreo = new ArrayList<ConstantesDTO>();
    }
    return listaConstantesCorreo;
  }

  public static void setListaConstantesCorreo(List<ConstantesDTO> listaConstantesCorreo) {
    KeyPropertiesFactory.listaConstantesCorreo = listaConstantesCorreo;
  }

  public static Map<String, CorreoParametrizaDTO> getMapaCorreoParametrizaDTO() {
    if (mapaCorreoParametrizaDTO == null) {
      mapaCorreoParametrizaDTO = new HashMap<String, CorreoParametrizaDTO>();
    }
    return mapaCorreoParametrizaDTO;
  }

  public static void setMapaCorreoParametrizaDTO(Map<String, CorreoParametrizaDTO> mapaCorreoParametrizaDTO) {
    KeyPropertiesFactory.mapaCorreoParametrizaDTO = mapaCorreoParametrizaDTO;
  }

  public static Map<String, String> getKeyValuesConexionBdGeneracionReportes() {

    if (keyValuesConexionBdGeneracionReportes == null) {
      keyValuesConexionBdGeneracionReportes = new HashMap<String, String>();
    }
    return keyValuesConexionBdGeneracionReportes;
  }

  public static void setKeyValuesConexionBdGeneracionReportes(Map<String, String> keyValuesConexionBdGeneracionReportes) {
    KeyPropertiesFactory.keyValuesConexionBdGeneracionReportes = keyValuesConexionBdGeneracionReportes;
  }

  public static List<CategoriaPolicialDTO> getListaCategoriaPolicialDTO() {
    return listaCategoriaPolicialDTO;
  }

  public static void setListaCategoriaPolicialDTO(List<CategoriaPolicialDTO> listaCategoriaPolicialDTO) {
    KeyPropertiesFactory.listaCategoriaPolicialDTO = listaCategoriaPolicialDTO;
  }

  public static Map<Long, List<GradoCategoriaDTO>> getMapaListaGradoCategoriaDTO() {
    return mapaListaGradoCategoriaDTO;
  }

  public static void setMapaListaGradoCategoriaDTO(Map<Long, List<GradoCategoriaDTO>> mapaListaGradoCategoriaDTO) {
    KeyPropertiesFactory.mapaListaGradoCategoriaDTO = mapaListaGradoCategoriaDTO;
  }

  public static String getPathFilesVIECO() {
    return pathFilesVIECO;
  }

  public static void setPathFilesVIECO(String pathFilesVIECO) {
    KeyPropertiesFactory.pathFilesVIECO = pathFilesVIECO;
  }
}
