/*
 * Soft Studio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.convertidores;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @param <T>
 * @since: 1.0
 */
public abstract class AbstractXMLConverter<T> implements Converter {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractXMLConverter.class);

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
    LOG.trace("Entro a: <<getAsObject>> parametros / context ->> {} / component ->> {} / value ->> {}", context, component, value);
    T instance = null;
    if (value != null && !value.isEmpty()) {
      instance = (T) unmarshal(value);
    }
    return instance;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
    LOG.trace("Entro a: <<getAsString>> parametros / context ->> {} / component ->> {} / value ->> {}", context, component, value);
    if (value == null) {
      return null;
    }
    return marshal(value);
  }

  /**
   *
   * @param xml
   * @return
   */
  public Object unmarshal(String xml) {
    LOG.trace("Entro a: <<unmarshal>> parametros / xml ->> {}", xml);
    Object object = null;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(getClazz());
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      Reader reader = new StringReader(xml);
      object = jaxbUnmarshaller.unmarshal(reader);
      LOG.trace("left the method: <<unmarshal>>");
    } catch (JAXBException ex) {
      LOG.error("error in method unmarshal()", ex);
    }
    return object;
  }

  /**
   *
   * @param instancia
   * @return
   */
  public String marshal(Object instancia) {
    LOG.trace("Entro a: <<marshal>> parametros / instancia  ->> {}", instancia);
    String xml = null;
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(getClazz());
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
      jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      StringWriter sw = new StringWriter();
      jaxbMarshaller.marshal(instancia, sw);
      xml = sw.toString();
      LOG.trace("left the method: <<marshal>>");
    } catch (JAXBException ex) {
      LOG.error("error in method marshal()", ex);
    }
    return xml;
  }

  /**
   *
   * @return
   */
  public abstract Class<T> getClazz();

}
