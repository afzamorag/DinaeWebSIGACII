/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.cliente;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import co.gov.policia.dinae.moodle.config.Parameters;
import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.moodle.dto.Categoria;

/**
 * Clase que utiliza los servision web de moodle para las categorias..
 * @author ferney
 *
 */
public class CategoriaProvider  extends ClienteWebServer {

    /**
     * 
     */
    private static final long serialVersionUID = 5147387611763276382L;

    private final String const_get_categories = "core_course_get_categories";
    
    private Categoria categoria;
    private List<Categoria> categorias = new ArrayList<Categoria>();
    
    /**
     *  Instancia la clase con el server 
     * @param server
     */
    public CategoriaProvider(Server server) {
        super(server);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Llena los datos dela categoria con el resultado del servicio
     * @param jsObj
     * @return
     */
    private Categoria jsonToCategoria(JSONObject jsObj) {
        Categoria nCategoria = new Categoria();
        try {
            nCategoria.setId(this.getJsonInteger(jsObj,"id"));
            nCategoria.setName(this.getJsonString(jsObj,"name"));
            nCategoria.setIdnumber(this.getJsonString(jsObj,"idnumber"));
            nCategoria.setDescription(this.getJsonString(jsObj,"description"));
            nCategoria.setDescriptionformat(this.getJsonInteger(jsObj,"descriptionformat"));
            nCategoria.setParent(this.getJsonInteger(jsObj,"parent"));
            nCategoria.setSortorder(this.getJsonInteger(jsObj,"sortorder"));
            nCategoria.setCoursecount(this.getJsonInteger(jsObj,"coursecount"));
            nCategoria.setVisible(this.getJsonInteger(jsObj,"visible"));
            nCategoria.setVisibleold(this.getJsonInteger(jsObj,"visibleold"));
            nCategoria.setTimemodified(this.getJsonDate(jsObj,"timemodified"));
            nCategoria.setDepth(this.getJsonInteger(jsObj,"depth"));
            nCategoria.setDescription(this.getJsonString(jsObj,"description"));
            nCategoria.setPath(this.getJsonString(jsObj,"path"));
            nCategoria.setTheme(this.getJsonString(jsObj,"theme"));
        } catch (Exception e) {
            this.addErrorException("JSONException","E-JSON", e.getMessage());
        }
        
        return nCategoria;
    }
    
    /**
     * Devuelve todas las categorias
     * @return
     */
    public boolean getCategoriaCriterio() {
        return getCategoriaCriterio(null);
    }
    
    /**
     * Devuelve las categorias segun criterios
     * @param parameters
     * @return
     */
    public boolean getCategoriaCriterio(Parameters parameters) {
        boolean bOk = false;
        this.categorias.clear();
        String content = "";
        if(parameters != null) {
            content = this.getServer().getData(this.getServer().getUrlService(this.const_get_categories),parameters.getStringPos());
        } else {
            content = this.getServer().getData(this.getServer().getUrlService(this.const_get_categories));
        }
        if(this.validarJson(content)) {
            JSONArray jObj = this.getServer().getJson(content);
            if(jObj != null) {
                if(jObj.length() > 0) {
                    List<Categoria> lst = new ArrayList<Categoria>();
                    for (int i = 0;i<jObj.length (); i++) {
                        try {
                            JSONObject jsonObject = jObj.getJSONObject(i);
                            Categoria nCategoria = this.jsonToCategoria(jsonObject);
                            lst.add(nCategoria);
                        } catch (Exception e) {
                            this.addErrorException("JSONException","E-JSON", "Formato no valído");
                        }
                    }
                    bOk = true;
                    for(Categoria ct: lst) {
                        if(ct.getParent() == null || ct.getParent().intValue() == 0) {
                            ct.setLevel(1);
                            this.buscarHijos(lst, ct, 2);
                            this.categorias.add(ct);
                        }
                    }
                } else {
                    this.addErrorException("Result","NOTDATA", "No hay categorías");
                }
            } else {
                this.addErrorException("JSONException","E-JSON", "Formato no valído");
            }
        }
        return bOk;
    }
    
    /**
     * Busca el hijo de una categoria...
     * @param lstOrigen
     * @param cat
     * @param level
     */
    private void buscarHijos(List<Categoria> lstOrigen, Categoria cat, Integer level) {
        for(Categoria hijo: lstOrigen) {
            if(hijo.getParent().intValue() == cat.getId().intValue()) {
                hijo.setLevel(level);
                cat.getDetella().add(hijo);
                this.buscarHijos(lstOrigen, hijo, level + 1);
            }
        }
    }
    
    /**
     * Establece una categoria del arreglo
     * @param index
     */
    public void selectCategoria(int index) {
        this.categoria = this.categorias.get(index);
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }    
}