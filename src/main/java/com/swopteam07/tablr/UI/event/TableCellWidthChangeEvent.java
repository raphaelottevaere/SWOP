package com.swopteam07.tablr.UI.event;

import java.util.List;

public class TableCellWidthChangeEvent extends ChangeEvent<List<Integer>> {

    /**
     * Create a new change event.
     *
     * @param oldValue The value before the change (can be null).
     * @param newValue The value after the change (can be null).
     */
    public TableCellWidthChangeEvent(List<Integer> oldValue, List<Integer> newValue) throws IllegalArgumentException {
        super(oldValue, newValue);
    }
}
