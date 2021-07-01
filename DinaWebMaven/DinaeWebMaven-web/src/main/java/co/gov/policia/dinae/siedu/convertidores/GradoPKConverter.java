/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.convertidores;

import co.gov.policia.dinae.siedu.modelo.GradoPK;

import javax.faces.convert.FacesConverter;

/**
 *
 * @author Juan Jose Buzon
 */
@FacesConverter("gradoPKConverter")
public class GradoPKConverter extends AbstractXMLConverter<GradoPK> {

    @Override
    public Class<GradoPK> getClazz() {
        return GradoPK.class;
    }
    
}
