package com.swopteam07.tablr.UI.event;

public class TableRowSelectedEvent implements Event {

    /**
     * The id of the row that is currently selected.
     */
    private final int rowId;

    /**
     * Create a new table row selected event.
     *
     * @param rowId The id of the row that is currently selected.
     */
    public TableRowSelectedEvent(int rowId) {
        this.rowId = rowId;
    }

    /**
     * Get the id of the row that is currently selected in the table.
     *
     * @return The id of the row that is currently selected in the table.
     */
    public int getRowId() {
        return this.rowId;
    }

}
