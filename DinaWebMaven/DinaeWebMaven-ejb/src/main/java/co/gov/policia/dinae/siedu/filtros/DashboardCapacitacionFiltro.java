/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.filtros;

import java.io.Serializable;

/**
 *
 * @author ANDRES.ZAMORAG <af.zamorag@gmail.com>
 */
public class DashboardCapacitacionFiltro implements Serializable{

    private String vigencia;
    private Long escuela;

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public Long getEscuela() {
        return escuela;
    }

    public void setEscuela(Long escuela) {
        this.escuela = escuela;
    }

}
