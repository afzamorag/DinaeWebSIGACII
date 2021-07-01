//package co.gov.policia.dinae.converter;
//
//import com.sun.faces.renderkit.html_basic.LabelRenderer;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.Map;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.context.ResponseWriter;
//
///**
// *
// * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
// */
//public class LabelRenderKit extends LabelRenderer implements Serializable {
//
//  @Override
//  public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
//
//    final ResponseWriter writer = context.getResponseWriter();
//
//    final Map<String, Object> attrs = component.getAttributes();
//    final String forAttr = (String) attrs.get("for");
//
//    if (forAttr != null) {
//
//      final UIComponent forComponenent = component.findComponent(forAttr);
//      if (forComponenent != null) {
//
//        final Map<String, Object> forAtrrs = forComponenent.getAttributes();
//
//        if (forAtrrs != null) {
//
//          final Boolean required = (Boolean) forAtrrs.get("required");
//          if (required != null && required) {
//
//            writer.startElement("span", null);
//            //writer.writeAttribute("id", component.getClientId(context) + "RequiredLabel", null);
//            writer.writeAttribute("style", "color:red", null);
//            //writer.writeAttribute("styleClass", "requiredLabel", null);
//            writer.writeText("*", null);
//            writer.endElement("span");
//          }
//        }
//
//      }
//
//    }
//
//    super.encodeBegin(context, component);
//
//  }
//}
