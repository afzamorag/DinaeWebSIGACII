/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.moodle.config;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Clase Funciones Comunes
 * @author ferney
 *
 */
public class Funciones implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6313006133819833674L;

	/**
	 * Devuelve un String con un parametro por nombre arreglo
	 * @param entidad
	 * @param sResult
	 * @param pos
	 * @param key
	 * @param valor
	 * @param emptyAdd
	 * @return
	 */
	public static String addPostParameter(String entidad,String sResult, String pos, String key, String valor, boolean emptyAdd) {
		String sReturn = sResult;
		if(emptyAdd || (valor != null && !valor.isEmpty())) {
			if(!sReturn.isEmpty()) sReturn += "&";
			sReturn += entidad + "[" + pos  +"][" + key + "]=";
			if(valor != null && !valor.isEmpty()) {
				try {
					sReturn +=  URLEncoder.encode(valor, "UTF-8");
				} catch (Exception e) {}
			} 
		}
		return sReturn;
	}

	/**
	 * Devuelve un String con un parametro por nombre arreglo y sub arreglo
	 * @param entidad
	 * @param subEntidad
	 * @param sResult
	 * @param pos
	 * @param posSub
	 * @param key
	 * @param valor
	 * @param emptyAdd
	 * @return
	 */
	public static String addPostParameter(String entidad,String subEntidad, String sResult, String pos,String posSub, String key, String valor, boolean emptyAdd) {
		String sReturn = sResult;
		if(emptyAdd || (valor != null && !valor.isEmpty())) {
			if(!sReturn.isEmpty()) sReturn += "&";
			sReturn += entidad + "[" + pos  +"][" + subEntidad + "][" + posSub + "][" + key + "]=";
			if(valor != null && !valor.isEmpty()) {
				try {
					sReturn +=  URLEncoder.encode(valor, "UTF-8");
				} catch (Exception e) {}
			} 
		}
		return sReturn;
	}
	
	/**
	 * Convierte un entero a string
	 * @param value
	 * @return
	 */
	public static String IntegerToString(Integer value) {
		String sResult = null;
		try {
			sResult = String.valueOf(value);
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Convierte un a fecha a string
	 * @param fecha
	 * @return
	 */
	public static String DateToString(Date fecha) {
		String sResult = null;
		try {
                    long lTime = (long)fecha.getTime() / 1000;
                    sResult = String.valueOf(lTime);
		} catch (Exception e) {}
		return sResult;
	}
	
	/**
	 * Codifica un valor par url
	 * @param sVal
	 * @return
	 */
	public static String urlEncoder(String sVal) {
		String sResult = "";
		try {
			sResult = URLEncoder.encode(sVal, "UTF-8");
		} catch (Exception e) {}
		return sResult;
	}

}
