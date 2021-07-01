package co.gov.policia.dinae.siedu.convertidores;

import javax.faces.convert.FacesConverter;

import co.gov.policia.dinae.siedu.dto.EventoDTO;

/**
*
* @author Juan Jose Buzon
*/
@FacesConverter("eventoDTOConverter")
public class EventoDTOConverter extends AbstractXMLConverter<EventoDTO> {

	@Override
	public Class<EventoDTO> getClazz() {
		return EventoDTO.class;
	}

}
