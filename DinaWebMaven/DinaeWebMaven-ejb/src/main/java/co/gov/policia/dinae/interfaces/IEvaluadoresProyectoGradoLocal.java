/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.dto.EvaluadoresProyectoGradoDTO;
import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.EvaluadoresProyectoGrado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author RafaelGomez
 */
@Local
public interface IEvaluadoresProyectoGradoLocal {

  EvaluadoresProyectoGrado guardarEvaluadoresProyectoGrado(EvaluadoresProyectoGrado evaluadoresProyectoGrado) throws JpaDinaeException;

  List<EvaluadoresProyectoGradoDTO> getListaEvaluadoresProyectoDTOByIdProyecto(Long idProyecto) throws JpaDinaeException;

  List<EvaluadoresProyectoGrado> getListaEvaluadoresProyectoByIdProyecto(Long idProyecto) throws JpaDinaeException;

}
