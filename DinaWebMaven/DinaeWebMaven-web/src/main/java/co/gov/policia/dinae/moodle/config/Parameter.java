/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.config;

import java.io.Serializable;

/**
 * Calse de Parametro
 * @author ferney
 *
 */
public class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1721731224091791420L;
	
	private String name;
	private String value;
	
	/**
	 *  Instancia la clase sin valores
	 */
	public Parameter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *  Instancia la clase con valores
	 * @param name
	 * @param value
	 */
	public Parameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Devuele el parametro como cadena field value
	 * @return
	 */
	public String getStringField() {
		String sResult = "field=";
		sResult += Funciones.urlEncoder(this.name);
		sResult += "&value=";
		sResult += Funciones.urlEncoder(this.value);
		return sResult;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
