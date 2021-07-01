package co.gov.policia.dinae.siedu.convertidores;

import javax.faces.convert.FacesConverter;

import co.gov.policia.dinae.modelo.CarrerasPK;

/**
*
* @author Juan Jose Buzon
*/
@FacesConverter("carreraPKConverter")
public class CarreraPKConverter  extends AbstractXMLConverter<CarrerasPK> {

	@Override
	public Class<CarrerasPK> getClazz() {
		return CarrerasPK.class;
	}

}
