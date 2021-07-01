/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.dto;


import java.io.Serializable;

/**
 * Clase Opcion de Formato de Curso
 * @author ferney
 *
 */
public class CursoFormatOption implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5497400933673376095L;
	
	private String name;
	private Integer value;
	
	/**
	 * Instancia la clase sin datos
	 **/
	public CursoFormatOption() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instancia la calse con valores
	 * @param name
	 * @param value
	 */
	public CursoFormatOption(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
