package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.Checkbox;
import com.swopteam07.tablr.UI.Component;
import com.swopteam07.tablr.UI.DrawableComponent;
import com.swopteam07.tablr.UI.TextField;
import com.swopteam07.tablr.model.cell.DataCell;
import com.swopteam07.tablr.model.column.DataType;

/**
 * Changes a DataCell (which contains a DataType and a Value to an UI element
 *
 * @author rapha
 */
public class CellFactory {

    /**
     * Changes the given dataCell to a new UI Component the value and the type depends on the values from the DataCell
     *
     * @param dc     Datacell containing a value and a DATATYPE as defined in the enum DataType
     * @param parent The parent component for the UI component created by this factory.
     * @return TextBox if DataType is Either Integer EMAIL or String
     * CheckBox if DataType is Boolean
     * @throws UnsupportedOperationException if the DataType does not gave an implemented UI component.
     */
    public DrawableComponent dataCellToUI(DataCell dc, Component parent) {
        if (dc.getType() == DataType.BOOLEAN) {
            if (dc.getValue() == null)
                return new Checkbox(null, dc.allowsBlanks(), parent);
            else
                return new Checkbox((Boolean) dc.getValue(), dc.allowsBlanks(), parent);
        } else if (dc.getType() == DataType.STRING || dc.getType() == DataType.EMAIL) {
            if (dc.getValue() == null)
                return new TextField("", parent);
            else
                return new TextField((String) dc.getValue(), parent);
        } else if (dc.getType() == DataType.INTEGER) {
            if (dc.getValue() == null)
                return new TextField("", parent);
            else
                return new TextField(((Integer) dc.getValue()).toString(), parent);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public DrawableComponent dataCellToUI(DataCell dc, Component parent, int x, int y, int height, int width) {
        DrawableComponent component = dataCellToUI(dc, parent);
        component.setX(x);
        component.setY(y);
        component.setHeight(height);
        component.setWidth(width);
        return component;
    }
}
