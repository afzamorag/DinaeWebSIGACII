/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.interfaces;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.modelo.PruebaCap2;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rafael Guillermo Blanco Banquéz <rafaelg.blancob@gmail.com>
 */
@Local
public interface IPruebaCapc2Local {

  List<PruebaCap2> getPrueba2Local() throws JpaDinaeException;
}
