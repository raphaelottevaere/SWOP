package com.swopteam07.tablr.UI.event;

public class CheckboxChangeEvent extends ChangeEvent<Boolean> {

    /**
     * Create a new change event.
     *
     * @param oldValue The value before the change (can be null).
     * @param newValue The value after the change (can be null).
     */
    public CheckboxChangeEvent(Boolean oldValue, Boolean newValue) throws IllegalArgumentException {
        super(oldValue, newValue);
    }
}
