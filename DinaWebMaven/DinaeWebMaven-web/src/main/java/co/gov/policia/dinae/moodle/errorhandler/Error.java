/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.errorhandler;

import java.io.Serializable;

/**
 * Clase que errores
 * @author ferney
 *
 */
public class Error implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1944995310941017271L;
	
	private String exeption;
	private String errorcode;
	private String message;
	
	/**
	 * Instancia la clase
	 */
	public Error() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Instancia la clase segun parametros
	 * @param exeption
	 * @param errorcode
	 * @param message
	 */
	public Error(String exeption, String errorcode, String message) {
		super();
		this.exeption = exeption;
		this.errorcode = errorcode;
		this.message = message;
	}
	public String getExeption() {
		return exeption;
	}
	public void setExeption(String exeption) {
		this.exeption = exeption;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
