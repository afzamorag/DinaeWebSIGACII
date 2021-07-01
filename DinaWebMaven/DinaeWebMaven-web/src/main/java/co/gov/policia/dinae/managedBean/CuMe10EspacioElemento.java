package co.gov.policia.dinae.managedBean;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.NavigationFaces;
import co.gov.policia.dinae.modelo.UnidadDependencia;
import co.gov.policia.dinae.siedu.constantes.TipoDominioEnum;
import co.gov.policia.dinae.siedu.controladores.AbstractController;
import co.gov.policia.dinae.siedu.excepciones.ServiceException;
import co.gov.policia.dinae.siedu.modelo.Dominio;
import co.gov.policia.dinae.siedu.modelo.SieduElemento;
import co.gov.policia.dinae.siedu.modelo.SieduSubtipoElemento;
import co.gov.policia.dinae.siedu.modelo.SieduTipoElemento;
import co.gov.policia.dinae.siedu.servicios.APPService;
import co.gov.policia.dinae.siedu.servicios.SieduElementoService;
import co.gov.policia.dinae.siedu.servicios.SieduSubtipoElementoService;
import co.gov.policia.dinae.siedu.servicios.SieduTipoElementoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import javax.inject.Inject;

/**
 * description
 *
 * @author: Fredy Wilches <fredy.wilches@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
@javax.inject.Named(value = "cuMe10EspacioElemento")
@javax.enterprise.context.SessionScoped
public class CuMe10EspacioElemento extends AbstractController implements Serializable {

  @Inject
  protected LoginFaces loginFaces;
  @Inject
  protected NavigationFaces navigationFaces;
   
  

  // Filtro
  private UnidadDependencia unidad;
  private String documentoFuncionario;
  private boolean busco;
  private List<SieduTipoElemento> tiposElementos;
  private List<SieduSubtipoElemento> subtipos;
  private SieduTipoElemento tipoElemento;
  
  private List<SieduElemento> elementos;
  private SieduElemento elementoSeleccionado;
  
  @EJB
  private SieduElementoService serviceElemento;
  
  @EJB
  private SieduTipoElementoService serviceTipoElemento;
  
  @EJB
  private SieduSubtipoElementoService serviceSubtipoElemento;
  
  @EJB
  private APPService serviceAPP;  
  
  protected final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();
  
  private List<Dominio> tiposInvestigacion;
  
  //0 solo lectura
  //1 Edición
  //2 Nuevo
  private Integer editando;
  
  private List<SelectItem> estados;
  
  /**
   *
   */
  @javax.annotation.PostConstruct
  public void init() {

    try {

      tiposElementos=serviceTipoElemento.findAll();
      
      this.subtipos = new ArrayList<>();
      
      this.elementos=new ArrayList<>();
      
      this.elementoSeleccionado = new SieduElemento();
      this.elementoSeleccionado.setTipoElemento(this.tipoElemento);
      this.elementoSeleccionado.setUnidad(this.unidad);
      
      tiposInvestigacion=serviceAPP.consultarDominios(TipoDominioEnum.MODALIDAD_PROGRAMACION.getId());
      
      estados=new ArrayList<>();
      estados.add(new SelectItem('S',"Servicio"));
      estados.add(new SelectItem('B',"Baja"));
      estados.add(new SelectItem('M',"Mantenimiento"));
           
    } catch (Exception e) {
      addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-10", e);
    }

  }

  /**
   *
   * @return
   */
  public String initReturnCU() {

    init();

    try {
      return navigationFaces.redirectCuMe10EspacioEquipoElementoInvestigacion();

    } catch (Exception e) {

      addErrorMessage(getPropertyFromBundle("commons.msg.error.summary"), getPropertyFromBundle("commons.msg.error.detail"));
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "CU-ME-10", e);

    }

    return null;
  }



  public void editar(SieduElemento pi){
    this.elementoSeleccionado = pi;
    this.editando = 0;
    this.findSubtipos();
  }
  
  public void nuevo(){
    this.elementoSeleccionado = new SieduElemento();
    this.elementoSeleccionado.setTipoElemento(this.tipoElemento);
    this.elementoSeleccionado.setUnidad(this.unidad);    
    this.editando=2;
    
  }
  
  public void guardar(){
    
    
    if (editando==1){
      try {
        this.elementoSeleccionado.setElemUsuMod(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        this.elementoSeleccionado.setElemIpMod(getHostAddress());
        this.elementoSeleccionado.setElemMaquinaMod(getHostName());
        this.elementoSeleccionado.setElemFechaMod(new Date());

        serviceElemento.update(this.elementoSeleccionado);
        
        
        addInfoMessage("Elemento almacenado exitosamente");
        buscar();
        
      } catch (ServiceException ex) {
        Logger.getLogger(CuMe1InvestigacionUnidad.class.getName()).log(Level.SEVERE, null, ex);
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
    }else{
      try {
        this.elementoSeleccionado.setElemUsuCrea(loginFaces.getPerfilUsuarioDTO().getUsuarioLogueado());
        this.elementoSeleccionado.setElemFechaCrea(new Date());
        this.elementoSeleccionado.setElemIpCrea(getHostAddress());
        this.elementoSeleccionado.setElemMaquinaCrea(getHostName());

        this.elementoSeleccionado=serviceElemento.create(this.elementoSeleccionado);
        addInfoMessage(getPropertyFromBundle("commons.msg.success.save.summary"), getPropertyFromBundle("commons.msg.success.save.detail"));
        
        buscar();
        
      } catch (ServiceException ex) {
        Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
        addErrorMessage(getPropertyFromBundle("commons.msg.error.save.summary"), getPropertyFromBundle("commons.msg.error.save.detail"));
      }
      
    }
    
  }
  
  public void eliminar(){
    try {
     
      this.serviceElemento.delete(this.elementoSeleccionado);
      this.buscar();
      
      addInfoMessage(getPropertyFromBundle("commons.msg.success.delete.summary"), getPropertyFromBundle("commons.msg.success.delete.detail"));
    } catch (ServiceException ex) {
      Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
      addErrorMessage(getPropertyFromBundle("commons.msg.error.read.list.summary"), getPropertyFromBundle("commons.msg.error.read.list.detail"));
    }
    
  }

  public void editar(){
    this.editando=1;
    
  }
  
  public void buscar() {
    this.elementos=new ArrayList<>();
    Map<String, Object> params = new HashMap();
    if (this.unidad!=null){
      params.put("unidad", unidad);
    }
    if (this.tipoElemento!=null){
      params.put("tipoElemento", tipoElemento);
    }
    
    try {
      this.elementos = this.serviceElemento.findByFilter(params);
      busco=true;
     
    } catch (ServiceException ex) {
      
      Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
      "CU-ME-10 Búsqueda de Elementos ", ex);
    }
  }

  public Integer getEditando() {
    return editando;
  }

  public void setEditando(Integer editando) {
    this.editando = editando;
  }

  public String getDocumentoFuncionario() {
    return documentoFuncionario;
  }

  public void setDocumentoFuncionario(String documentoFuncionario) {
    this.documentoFuncionario = documentoFuncionario;
  }

  public UnidadDependencia getUnidad() {
    return unidad;
  }

  public void setUnidad(UnidadDependencia unidad) {
    this.unidad = unidad;
  }

  public List<SieduTipoElemento> getTiposElementos() {
    return tiposElementos;
  }

  public void setTiposElementos(List<SieduTipoElemento> tiposElementos) {
    this.tiposElementos = tiposElementos;
  }

  public SieduTipoElemento getTipoElemento() {
    return tipoElemento;
  }

  public void setTipoElemento(SieduTipoElemento tipoElemento) {
    this.tipoElemento = tipoElemento;
  }

  public List<SieduElemento> getElementos() {
    return elementos;
  }

  public void setElementos(List<SieduElemento> elementos) {
    this.elementos = elementos;
  }

  public SieduElemento getElementoSeleccionado() {
    return elementoSeleccionado;
  }

  public void setElementoSeleccionado(SieduElemento elementoSeleccionado) {
    this.elementoSeleccionado = elementoSeleccionado;
  }

  public void findSubtipos() {
    
    if (this.elementoSeleccionado!=null && this.elementoSeleccionado.getTipoElemento()!=null){
      Map<String, Object> parametros=new HashMap();
      parametros.put("idTipo", this.elementoSeleccionado.getTipoElemento().getIdTipoElemento());
      try {
        subtipos=this.serviceSubtipoElemento.findByFilter(parametros);
      } catch (ServiceException ex) {
        Logger.getLogger(CuMe10EspacioElemento.class.getName()).log(Level.SEVERE, null, ex);
      }
    }else
      subtipos= new ArrayList<>();
  }

  public List<SieduSubtipoElemento> getSubtipos() {
    return subtipos;
  }
  
  public void setSubtipos(List<SieduSubtipoElemento> subtipos) {
    this.subtipos = subtipos;
  }

  public List<Dominio> getTiposInvestigacion() {
    return tiposInvestigacion;
  }

  public void setTiposInvestigacion(List<Dominio> tiposInvestigacion) {
    this.tiposInvestigacion = tiposInvestigacion;
  }

  public List<SelectItem> getEstados() {
    return estados;
  }

  public void setEstados(List<SelectItem> estados) {
    this.estados = estados;
  }   

    public boolean isBusco() {
        return busco;
    }

    public void setBusco(boolean busco) {
        this.busco = busco;
    }

}
