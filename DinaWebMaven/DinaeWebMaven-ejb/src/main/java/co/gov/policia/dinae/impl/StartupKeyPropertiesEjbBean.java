package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.constantes.IConstantes;
import co.gov.policia.dinae.dto.CategoriaPolicialDTO;
import co.gov.policia.dinae.dto.ConstantesDTO;
import co.gov.policia.dinae.dto.CorreoParametrizaDTO;
import co.gov.policia.dinae.dto.GradoCategoriaDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.Constantes;
import co.gov.policia.dinae.modelo.GradoCategoria;
import co.gov.policia.dinae.modelo.KeyProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@Singleton
@Startup
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StartupKeyPropertiesEjbBean implements Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @PostConstruct
  public void inicializar() {
    try {
      //REALIZA LA CARGA DE LOS PROPERTIES
      getKeyProperties();
      //REALIZA LA CARGA DE LAS PAGINAS QUE NO REQUIEREN LOGIN
      cargarPaginaNoRequierenLogin();

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("ERROR AL CARGAR LAS PROPIEDADES.. " + e.getMessage());
    }
  }

  /**
   *
   * @return @throws JpaDinaeException
   */
  private void getKeyProperties() throws JpaDinaeException {

    try {

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " --------------------------------------- ");
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CARGANDO PROPERTIES");

      List<KeyProperties> lista = entityManager.createNamedQuery("KeyProperties.findAll").getResultList();

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " SE ENCONTRARON ".concat(String.valueOf(lista.size())).concat(" CLAVES"));

      KeyPropertiesFactory kpf = KeyPropertiesFactory.getInstance();
      for (KeyProperties keyProperties : lista) {
        kpf.putKeyValueBundle(keyProperties.getClave(), keyProperties.getValor());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, keyProperties.getClave().concat(",").concat(keyProperties.getValor()));
      }

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "FIN DE LA CARGA DE PROPIERTIES");
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " --------------------------------------- ");

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CARGA LDAP_JNDI_WEBLOGIC_PARAM");
      List<Constantes> listaConstantes = entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", IConstantes.LDAP_JNDI_WEBLOGIC_PARAM)
              .getResultList();

      for (Constantes constantes : listaConstantes) {
        kpf.putKeyValueConexionLDAPWeblogic(constantes.getCodigo(), constantes.getValor());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, constantes.getCodigo().concat(",").concat(constantes.getValor()));
      }

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " --------------------------------------- ");
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CARGA LDAP_JNDI_WEBLOGIC_PATRON_BUSQUEDA");
      //SE ESPERA UN SOLO VALOR
      listaConstantes = entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", IConstantes.LDAP_JNDI_WEBLOGIC_PATRON_BUSQUEDA)
              .getResultList();
      KeyPropertiesFactory.setPatronBusquedaLDAPweblogic(listaConstantes.get(0).getValor());

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " --------------------------------------- ");
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CARGA LDAP_JNDI_WEBLOGIC_PATRON_BUSQUEDA");
      //SE ESPERA UN SOLO VALOR
      listaConstantes = entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", IConstantes.LDAP_JNDI_WEBLOGIC_ATRIBUTO_BUSQUEDA)
              .getResultList();
      KeyPropertiesFactory.setAtributoBusquedaLDAPweblogic(listaConstantes.get(0).getValor());

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " --------------------------------------- ");
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CARGA CONFIGURACION CORREO");
      //CONSULTAMOS LOS DATOS DE USUARIO Y CONTRASEÑA
      //SE ESPERA UN SOLO REGISTRO
      List<Constantes> constanteUsuario = entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", IConstantes.TIPO_CONFIGURACION_MAIL_DINAE_USUARIO)
              .getResultList();
      KeyPropertiesFactory.setUsuarioMail(constanteUsuario.get(0).getValor());

      //SE ESPERA UN SOLO REGISTRO
      List<Constantes> constanteClave = entityManager.createNamedQuery("Constantes.findAllPorTipo")
              .setParameter("tipo", IConstantes.TIPO_CONFIGURACION_MAIL_DINAE_CLAVE)
              .getResultList();
      KeyPropertiesFactory.setUsuarioClave(constanteClave.get(0).getValor());

      KeyPropertiesFactory.setDebugConsole(false);
      try {

        try {
          List<Constantes> constanteDebugMail = entityManager.createNamedQuery("Constantes.findAllPorTipo")
                  .setParameter("tipo", IConstantes.TIPO_CONFIGURACION_MAIL_DINAE_DEBUG_MAIL)
                  .getResultList();

          KeyPropertiesFactory.setDebugConsole(Boolean.valueOf(constanteDebugMail.get(0).getValor()));

        } catch (IndexOutOfBoundsException iobe) {
          //NO SE ENCUENTRA PARAMETRZADA LA PROPIEDAD TIPO_CONFIGURACION_MAIL_DINAE_DEBUG_MAIL
          Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ":WARNING:: debugConsole NO SE ENCUENTRA PARAMETRZADA LA PROPIEDAD TIPO_CONFIGURACION_MAIL_DINAE_DEBUG_MAIL ", iobe);
        }

      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ":WARNING:: debugConsole NO ESTA PARAMETRIZADO CORRECTAMENTE ", e);
      }

      KeyPropertiesFactory.setListaConstantesCorreo(entityManager.createNamedQuery("ConstantesDTO.findAllPorTipo")
              .setParameter("tipo", IConstantes.TIPO_CONFIGURACION_MAIL_DINAE_PARAM)
              .getResultList());

      List<CorreoParametrizaDTO> listaCorreoParametriza = entityManager.createNamedQuery("CorreoParametrizaDTO.findColdigoLabelAllProperties")
              .getResultList();
      for (CorreoParametrizaDTO unCorreoParametrizaDTO : listaCorreoParametriza) {

        KeyPropertiesFactory.getMapaCorreoParametrizaDTO().put(unCorreoParametrizaDTO.getCodigo(), unCorreoParametrizaDTO);

      }

      try {

        List<ConstantesDTO> constanteParametrosConBDgeneReportes = entityManager.createNamedQuery("ConstantesDTO.findAllPorTipo")
                .setParameter("tipo", IConstantes.CONFIG_CONEXION_BD_GENERACION_REPORTE)
                .getResultList();

        for (ConstantesDTO unaConstantesDTO : constanteParametrosConBDgeneReportes) {

          KeyPropertiesFactory.getKeyValuesConexionBdGeneracionReportes().put(unaConstantesDTO.getCodigo(), unaConstantesDTO.getValor());

        }

      } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ":WARNING:: debugConsole NO ESTA PARAMETRIZADO CORRECTAMENTE ", e);
      }

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, " ----------------------CARGA DE CATEGORIAS POLICIALES----------------- ");
      //CARGAMOS LAS CATEGORIAS POLICIALES
      List<CategoriaPolicialDTO> listaCategoriaPolicialDTO = entityManager.createNamedQuery("CategoriaPolicialDTO.findAll")
              .getResultList();
      for (CategoriaPolicialDTO unaCategoriaPolicialDTO : listaCategoriaPolicialDTO) {

        KeyPropertiesFactory.getListaCategoriaPolicialDTO().add(unaCategoriaPolicialDTO);
        //POR CADA CATEGORIA CARGAMOS LOS GRADOS
        List<GradoCategoria> listaGradoCategoria = entityManager.createNamedQuery("GradoCategoria.findAllPorIdcategoria")
                .setParameter("idCategoria", unaCategoriaPolicialDTO.getIdCategoria())
                .getResultList();
        List<GradoCategoriaDTO> listaRetorna = new ArrayList<GradoCategoriaDTO>();
        for (GradoCategoria unGradoCategoria : listaGradoCategoria) {

          GradoCategoriaDTO gradoCategoriaDTO = new GradoCategoriaDTO();
          gradoCategoriaDTO.setAlfabitico(unGradoCategoria.getAlfabitico());
          gradoCategoriaDTO.setDescripcion(unGradoCategoria.getDescripcion());
          gradoCategoriaDTO.setId(unGradoCategoria.getId());
          gradoCategoriaDTO.setIdCategoria(unGradoCategoria.getIdCategoria());
          gradoCategoriaDTO.setNaturaleza(unGradoCategoria.getNaturaleza());
          gradoCategoriaDTO.setVigente(unGradoCategoria.getVigente());

          listaRetorna.add(gradoCategoriaDTO);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "CATEGORIA: {0} CONTIENE {1} GRADOS ", new Object[]{unaCategoriaPolicialDTO.getDescripcion(), String.valueOf(listaRetorna.size())});

        KeyPropertiesFactory.getMapaListaGradoCategoriaDTO().put(unaCategoriaPolicialDTO.getIdCategoria(), listaRetorna);
      }

      //CONSULTAMOS LA RUTA DONDE SE ALMACENAN LOS ARCHIVOS DE VIECO (archivos relacionados al PAE)
      //SE ESPERA UN SOLO REGISTRO
      try {
        List<Constantes> constantesVIECO = entityManager.createNamedQuery("Constantes.findAllPorTipo").setParameter("tipo", IConstantes.PATH_FILES_VIECO).getResultList();
        KeyPropertiesFactory.setPathFilesVIECO(constantesVIECO.get(0).getValor());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "PATH FILES VIECO LOADED");
      } catch (IndexOutOfBoundsException iobe) {
        //NO SE ENCUENTRA PARAMETRZADA LA PROPIEDAD PATH_FILES_VIECO
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ":WARNING:: debugConsole NO SE ENCUENTRA PARAMETRZADA LA PROPIEDAD PATH_FILES_VIECO ", iobe);
      }

    } catch (Exception e) {
      throw new JpaDinaeException(e.getMessage(), e);
    }
  }

  /**
   *
   */
  private void cargarPaginaNoRequierenLogin() {

    KeyPropertiesFactory kpf = KeyPropertiesFactory.getInstance();
    kpf.addPaginaNoRequiereLogin("/pages/secured/cu_ne_4/consultar_propuesta_necesidad_investigacion.xhtml");
    kpf.addPaginaNoRequiereLogin("/pages/secured/cu_ne_7/include_detalle_propuesta_necesidad.xhtml");

  }
}
