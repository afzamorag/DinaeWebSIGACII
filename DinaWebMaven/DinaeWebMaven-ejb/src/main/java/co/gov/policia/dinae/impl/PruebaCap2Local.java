/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.impl;

import co.gov.policia.dinae.exceptions.JpaDinaeException;
import co.gov.policia.dinae.interfaces.IPruebaCapc2Local;
import co.gov.policia.dinae.modelo.PruebaCap2;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Guillermo Blanco Banquéz <rafaelg.blancob@gmail.com>
 */
@Stateless
public class PruebaCap2Local implements IPruebaCapc2Local, Serializable {

  @PersistenceContext(unitName = "DinaeWeb-PU")
  private EntityManager entityManager;

  @Override
  public List<PruebaCap2> getPrueba2Local() throws JpaDinaeException {
    return entityManager.createNamedQuery("PruebaCap2.findAll").getResultList();
  }

}
