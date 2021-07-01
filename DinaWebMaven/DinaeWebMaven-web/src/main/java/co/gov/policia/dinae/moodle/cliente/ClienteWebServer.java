/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import co.gov.policia.dinae.moodle.config.Server;
import co.gov.policia.dinae.moodle.errorhandler.Error;

/**
 * Clase base para utilizar los servicios web de moodle
 * @author ferney
 *
 */
public class ClienteWebServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2391435188647750436L;
	private final long MAGIC=86400000L;
	
	private Server server;
	private List<Error> errores = new ArrayList<Error>();
	private Error errorLast;
	private boolean hasError = false;
	private JSONArray reqJsonArray;
	private JSONObject reqJsonObject;
	
	/**
	 * Instancia la clase sin datos
	 */
	public ClienteWebServer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instancia la clase con el server 
	 * @param server
	 */
	public ClienteWebServer(Server server) {
		super();
		this.server = server;
	}
	
	/**
	 * Valida si el resultado es un json y si es un error lo registra
	 * @param sValor
	 * @return
	 */
	protected boolean validarJson(String sValor) {
		boolean bOk = false;
		if(sValor != null) {
			if(!sValor.isEmpty()) {
				if(sValor.indexOf("errorcode") > 0) {
					this.addErrorException(sValor);
				} else {
					this.request(sValor);
					bOk = true;
				}
			} else {
				this.addErrorException("JSONException","E-JSON", "Formato no valído");
			}
		} else {
			this.addErrorException(this.getServer().getError());
		}
		return bOk;
	}
	
	/**
	 * Limpia los errores
	 */
	public void cleanErrors() {
		this.errores.clear();
		this.errorLast = null;
	}	
	
	/**
	 * Agrega un error segun parametros
	 * @param exception
	 * @param errorcode
	 * @param message
	 */
	protected void addErrorException(String exception, String errorcode, String message) {
		this.errorLast = new Error(exception,errorcode,message);
		this.errores.add(errorLast);
	}
	
	/**
	 * Agrega un clase error
	 * @param error
	 */
	protected void addErrorException(Error error) {
		this.errorLast = error;
		this.errores.add(errorLast);
	}
	
	/**
	 * Agrega un error por una cadena json
	 * @param sError
	 */
	protected void addErrorException(String sError) {
		JSONArray jsonArray = this.server.getJson(sError);
		if(jsonArray != null) {
			if(jsonArray.length() > 0) {
				try {
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					this.addErrorException(jsonObject.getString("exeption"), jsonObject.getString("errorcode"), jsonObject.getString("message"));
				} catch (Exception e) {
					this.addErrorException("JSONException","E-JSON", "Formato no valído");
				}
			} else {
				this.addErrorException("JSONException","E-JSON", "Formato no valído");
			}
		} else {
			try {
				JSONObject jsObj = this.getServer().getJsonObject(sError);
				if(jsObj != null) {
					this.addErrorException(jsObj.getString("exception"), jsObj.getString("errorcode"), jsObj.getString("message"));
				} else {
					this.addErrorException("JSONException","E-JSON", "Formato no valído");
				}
			} catch (Exception e) {
				this.addErrorException("JSONException","E-JSON", "Formato no valído");
			}
		}
	}

	/**
	 * Devuelve el valor string de un json
	 * @param jsObj
	 * @param key
	 * @return
	 */
	protected String getJsonString(JSONObject jsObj, String key) {
		String sResult = null;
		try {
			sResult = jsObj.getString(key);
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Devuelve el valor entero de un json
	 * @param jsObj
	 * @param key
	 * @return
	 */
	protected Integer getJsonInteger(JSONObject jsObj, String key) {
		Integer sResult = null;
		try {
			sResult = jsObj.getInt(key);
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Devuelve el valor booleano de un Json
	 * @param jsObj
	 * @param key
	 * @return
	 */
	protected Boolean getJsonBoolean(JSONObject jsObj, String key) {
		Boolean sResult = null;
		try {
			sResult = jsObj.getBoolean(key);
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Devuelve la fecha de un json
	 * @param jsObj
	 * @param key
	 * @return
	 */
	protected Date getJsonDate(JSONObject jsObj, String key) {
		Date sResult = null;
		try {
                    long unix_seconds = jsObj.getLong(key);
                    sResult = new Date(unix_seconds*1000L); 
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Devuelve un json array de un json
	 * @param jsObj
	 * @param key
	 * @return
	 */
	protected JSONArray getJsonArray(JSONObject jsObj, String key) {
		try {
			return jsObj.getJSONArray(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Envoca el servicio web
	 * @param service
	 * @param sParameters
	 * @return
	 */
	protected boolean sendData(String service, String sParameters) {
		boolean bOk = false;
		this.reqJsonArray = null;
		this.reqJsonObject = null;
		String content = this.getServer().getData(this.getServer().getUrlService(service),sParameters);
		if(this.validarJson(content)) {
			bOk = true;
		}
		return bOk;
	}
	
	protected void request(String content) {
		this.reqJsonArray = this.getServer().getJson(content);
		if(this.reqJsonArray == null) {
			this.reqJsonObject = this.getServer().getJsonObject(content);
		}
	}

	
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public List<Error> getErrores() {
		return errores;
	}
	public void setErrores(List<Error> errores) {
		this.errores = errores;
	}
	public Error getErrorLast() {
		return errorLast;
	}
	public void setErrorLast(Error errorLast) {
		this.errorLast = errorLast;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public JSONArray getReqJsonArray() {
		return reqJsonArray;
	}
	public void setReqJsonArray(JSONArray reqJsonArray) {
		this.reqJsonArray = reqJsonArray;
	}
	public JSONObject getReqJsonObject() {
		return reqJsonObject;
	}
	public void setReqJsonObject(JSONObject reqJsonObject) {
		this.reqJsonObject = reqJsonObject;
	}
	
}
