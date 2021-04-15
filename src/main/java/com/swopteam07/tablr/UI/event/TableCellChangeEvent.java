package com.swopteam07.tablr.UI.event;

import com.swopteam07.tablr.UI.DrawableComponent;

public class TableCellChangeEvent implements Event {

    /**
     * The id of the cell that was changed.
     */
    private final int cellId;

    /**
     * The component that was changed.
     */
    private final DrawableComponent changedComponent;

    /**
     * Create a new table cell change event.
     *
     * @param changedComponent The component that was changed.
     * @param cellId           The id of the cell that was changed.
     */
    public TableCellChangeEvent(DrawableComponent changedComponent, int cellId) {
        this.cellId = cellId;
        this.changedComponent = changedComponent;
    }

    /**
     * Get the id of the cell that was changed.
     *
     * @return The id of the changed cell.
     */
    public int getCellId() {
        return cellId;
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
