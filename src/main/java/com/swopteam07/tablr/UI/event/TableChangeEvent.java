package com.swopteam07.tablr.UI.event;

import com.swopteam07.tablr.UI.DrawableComponent;

public class TableChangeEvent implements Event {

    /**
     * The id of the row in which the change occurred.
     */
    private final Integer rowId;

    /**
     * The id of the cell in which to change occurred.
     */
    private final Integer cellId;

    /**
     * The component in which the change occurred.
     */
    private final DrawableComponent changedComponent;

    public TableChangeEvent(Integer rowId, Integer cellId, DrawableComponent changedComponent) {
        this.cellId = cellId;
        this.rowId = rowId;
        this.changedComponent = changedComponent;
    }

    /**
     * Get the id of the cell that was changed.
     *
     * @return The id of the cell that was changed.
     */
    public Integer getCellId() {
        return cellId;
    }

    /**
     * The id of the row that was changed.
     *
     * @return The id of the row that was changed.
     */
    public Integer getRowId() {
        return rowId;
    }

    /**
     * Get the component that was changed.
     *
     * @return The component that was changed.
     */
    public DrawableComponent getChangedComponent() {
        return changedComponent;
    }
}
