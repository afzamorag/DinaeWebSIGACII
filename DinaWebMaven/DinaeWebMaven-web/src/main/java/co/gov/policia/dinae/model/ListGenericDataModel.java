package co.gov.policia.dinae.model;

import co.gov.policia.dinae.interfaces.IDataModel;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 * @param <T>
 */
public class ListGenericDataModel<T extends IDataModel> extends ListDataModel<T> implements SelectableDataModel<T>, Serializable {

  public ListGenericDataModel(List<T> data) {
    super(data);
  }

  @Override
  public Object getRowKey(T t) {

    if (t != null) {
      return t.getLlaveModel();
    }

    return null;
  }

  @Override
  public T getRowData(String string) {

    List<T> objs = (List<T>) getWrappedData();

    for (T idata : objs) {

      if (string.equals(idata.getLlaveModel())) {
        return idata;
      }
    }
    return null;
  }

  public int getNumeroRegistro() {
    return getRowCount();
  }
}
