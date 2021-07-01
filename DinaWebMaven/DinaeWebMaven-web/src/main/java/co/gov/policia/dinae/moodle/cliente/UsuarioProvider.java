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

import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.moodle.config.Parameters;
import co.gov.policia.dinae.moodle.dto.Usuario;

/**
 * Clase que utiliza los servision web de moodle par ausuarios..
 * @author ferney
 *
 */
public class UsuarioProvider extends ClienteWebServer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7049820773939048860L;
	
	private final String const_create_user = "core_user_create_users";
	private final String cont_get_criterio_usuarios = "core_user_get_users";
        private final String const_update_user = "core_user_update_users";
	
	private Usuario usuario;
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	/**
	 * Instancia la clase con el server 
	 * @param server
	 */
	public UsuarioProvider(Server server) {
		super(server);
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * Establece un nuevo usuario
	 */
	public void newUsuario() {
		this.usuario = new Usuario();
	}
	
	/**
	 * Establece y devuelve una nuevo usuario
	 * @return
	 */
	public Usuario getNewUusuario() {
		this.newUsuario();
		return this.usuario;
	}
	
	/**
	 * Llena los datos de un usuario con el resultado del servicio
	 * @param jsObj
	 * @return
	 */
	private Usuario jsonToUsuario(JSONObject jsObj) {
		
		Usuario nUsuario = new Usuario();
		
		try {
								
			nUsuario.setId(this.getJsonInteger(jsObj,"id"));
			nUsuario.setUsername(this.getJsonString(jsObj,"username"));
			nUsuario.setUsername(this.getJsonString(jsObj,"idnumber"));
			nUsuario.setLastname(this.getJsonString(jsObj,"lastname"));
			nUsuario.setFullname(this.getJsonString(jsObj,"fullname"));
			nUsuario.setEmail(this.getJsonString(jsObj,"email"));
			//nUsuario.setDepartment(this.getJsonString(jsObj,"department"));
			nUsuario.setFirstaccess(this.getJsonInteger(jsObj,"firstaccess"));
			nUsuario.setLastaccess(this.getJsonInteger(jsObj,"lastaccess"));
			nUsuario.setAuth(this.getJsonString(jsObj,"auth"));
			nUsuario.setSuspended(this.getJsonBoolean(jsObj,"suspended"));
			nUsuario.setConfirmed(this.getJsonBoolean(jsObj,"confirmed"));
			nUsuario.setLang(this.getJsonString(jsObj,"lang"));
			nUsuario.setTheme(this.getJsonString(jsObj,"theme"));
			nUsuario.setTimezone(this.getJsonString(jsObj,"timezone"));
			nUsuario.setMailformat(this.getJsonInteger(jsObj,"mailformat"));
			nUsuario.setDescription(this.getJsonString(jsObj,"description"));
			nUsuario.setDescriptionformat(this.getJsonInteger(jsObj,"descriptionformat"));
			nUsuario.setCity(this.getJsonString(jsObj,"city"));
			nUsuario.setCountry(this.getJsonString(jsObj,"country"));
			nUsuario.setProfileimageurl(this.getJsonString(jsObj,"profileimageurl"));
			nUsuario.setProfileimageurlsmall(this.getJsonString(jsObj,"profileimageurlsmall"));
                        nUsuario.setDepartment(this.getJsonString(jsObj, "firstnamephonetic"));
                        nUsuario.setInstitution(this.getJsonString(jsObj, "lastnamephonetic"));
			nUsuario.setMiddlename(this.getJsonString(jsObj, "middlename"));
		} catch (Exception e) {
			this.addErrorException("JSONException","E-JSON", e.getMessage());
		}
		
		return nUsuario;
	}
	
	/**
	 * Busca un usuario segun unos parametros
	 * @param parameters
	 * @return
	 */
	public boolean getUsuarioCriterio(Parameters parameters) {
		boolean bOk = false;
		this.usuarios.clear();
		String content = this.getServer().getData(this.getServer().getUrlService(this.cont_get_criterio_usuarios),parameters.getStringPos());
		if(this.validarJson(content)) {
			JSONObject jObj = this.getServer().getJsonObject(content);
			if(jObj != null) {
				JSONArray jsonArray = this.getJsonArray(jObj, "users");
				for (int i = 0;i<jsonArray.length (); i++) {
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Usuario nUsuario = this.jsonToUsuario(jsonObject);
						this.usuarios.add(nUsuario);
					} catch (Exception e) {
						this.addErrorException("JSONException","E-JSON", "Formato no valido");
					}
				}
                                if(this.usuarios.size() > 0) {
                                    bOk = true;
                                } else {
                                    this.addErrorException("Result","NOTUSER", "No hay Usuario");
                                }
			} else {
				this.addErrorException("JSONException","E-JSON", "Formato no valido");
			}
		}
		return bOk;
	}
	
	/** 
	 * Selecciona un usuario degun un indice del arreglo
	 * @param index
	 */
	public void selectUsuario(int index) {
		this.usuario = this.usuarios.get(index);
	}
	
	/** 
	 * Crea un usuario
	 * @return
	 */
	public boolean crearUser() {
                String result = this.usuario.validarDatos();
		if(result.equals("OK") &&  this.sendData(const_create_user, this.usuario.getStringPosCreate("0"))) {
			JSONObject jsObj = null;
			if(this.getReqJsonArray() != null) {
				try {
					jsObj = this.getReqJsonArray().getJSONObject(0);
				} catch (Exception e) {}
			} else {
				jsObj = this.getReqJsonObject();
			}
						
			if(jsObj != null) {
				this.usuario.setId(this.getJsonInteger(jsObj,"id"));							
			}
			
			return true;
		} else if (!result.equals("OK")) {
                    this.addErrorException("Result","USERVAL", result);
                }
		return false;
	}
	

	/** 
	 * actualiza el username
	 * @return
	 */
	public boolean updateUser() {
            if(this.sendData(const_update_user, this.usuario.getStringPosUpdate("0"))) {
                    JSONObject jsObj = null;
                    if(this.getReqJsonArray() != null) {
                            try {
                                    jsObj = this.getReqJsonArray().getJSONObject(0);
                            } catch (Exception e) {}
                    } else {
                            jsObj = this.getReqJsonObject();
                    }

                    if(jsObj != null) {
                            this.usuario.setId(this.getJsonInteger(jsObj,"id"));							
                    }

                    return true;
            }
            return false;
	}
        
        public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}

