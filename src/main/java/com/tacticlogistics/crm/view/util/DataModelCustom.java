package com.tacticlogistics.crm.view.util;

import com.tacticlogistics.crm.model.entities.EntityId;
import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author casarmiento
 * @param <T>
 */
public class DataModelCustom<T extends EntityId> extends ListDataModel<T>
        implements SelectableDataModel<T>, Serializable {

    private static final long serialVersionUID = 1L;

    public DataModelCustom(List<T> data) {
        super(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getRowData(String rowKey) {
        List<T> list = (List<T>) getWrappedData();

        for (T ejb : list) {
            if (ejb.getId() == (new Integer(rowKey))) {
                return ejb;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(T item) {
        return item.getId();
    }
}
