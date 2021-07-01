/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.config;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Se agregan los parametros para usar en los Provider
 * @author ferney
 *
 */
public class Parameters {

	private List<Parameter> parameters = new ArrayList<Parameter>();

	/**
	 *  Instancia la clase
	 */
	public Parameters() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Agrega un parametro por valores
	 * @param name
	 * @param value
	 */
	public void addParameter(String name, String value) {
		Parameter p = new Parameter(name,value);
		this.parameters.add(p);
	}
	
	/**
	 * Devuelve una cadena para parametros por nombre
	 * @return
	 */
	public String getStringNameValue() {
		String sResult = "";
		for(Parameter p: this.parameters) {
			if(!sResult.isEmpty()) sResult += "&";
			try {
				sResult += p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
			} catch (Exception e) {}
		}
		
		return sResult;
	}
	
	/**
	 * Devuelve una cadena de los parametros por nombre arreglo
	 * @param sName
	 * @return
	 */
	public String getStringTypeName(String sName) {
		String sResult = ""; 
		for(Parameter p: this.parameters) {
			if(!sResult.isEmpty()) sResult += "&";
			try {
				sResult += "" + sName + "[0][" + p.getName() + "]=" + URLEncoder.encode(p.getValue(), "UTF-8");
			} catch (Exception e) {}
		}
		
		return sResult;
	}
	
	/**
	 * Devuelve una cadena como valores de criteria
	 * @return
	 */
	public String getStringPos() {
		String sResult = "";
		Integer i = 0;
		for(Parameter p: this.parameters) {
			sResult = Funciones.addPostParameter("criteria",sResult, i.toString(), "key",  p.getName(), true);
			sResult = Funciones.addPostParameter("criteria",sResult, i.toString(), "value",  p.getValue(), true);
		}
		
		return sResult;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
