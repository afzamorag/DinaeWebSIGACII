/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.policia.dinae.siedu.convertidores;


import co.gov.policia.dinae.siedu.modelo.CategoriaPK;

import javax.faces.convert.FacesConverter;

/**
 *
 * @author Juan Jose Buzon
 */
@FacesConverter(value = "categoriaPKConverter")
public class CategoriaPKConverter extends AbstractXMLConverter<CategoriaPK> {

    @Override
    public Class<CategoriaPK> getClazz() {
        return CategoriaPK.class;
    }
    
}
