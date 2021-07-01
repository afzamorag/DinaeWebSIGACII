/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EvaluacionProyectoGrado;
import javax.ejb.Local;

/**
 *
 * @author RafaelGomez
 */
@Local
public interface IEvaluacionProyectoGradoLocal {

  EvaluacionProyectoGrado guardarEvaluacionProyectoGrado(EvaluacionProyectoGrado evaluacionProyectoGrado) throws JpaDinaeException;

  EvaluacionProyectoGrado getEvaluacionProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

}
