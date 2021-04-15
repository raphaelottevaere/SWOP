package com.swopteam07.tablr.UI.event;

public class TextFieldChangeEvent extends ChangeEvent<String> {

    /**
     * Create a new change event.
     *
     * @param oldValue The value before the change (can be null).
     * @param newValue The value after the change (can be null).
     */
    public TextFieldChangeEvent(String oldValue, String newValue) throws IllegalArgumentException {
        super(oldValue, newValue);
    }
}
