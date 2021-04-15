package com.swopteam07.tablr.UI.event;

public class ChangeEvent<T> implements Event {

    /**
     * The value before the change.
     */
    private final T oldValue;

    /**
     * The value after the change.
     */
    private final T newValue;

    /**
     * Create a new change event.
     * @param oldValue The value before the change (can be null).
     * @param newValue The value after the change (can be null).
     */
    public ChangeEvent(T oldValue, T newValue) throws IllegalArgumentException {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * @return Get the value after the change.
     */
    public T getOldValue() {
        return oldValue;
    }

    /**
     * @return Get the value before the change.
     */
    public T getNewValue() {
        return newValue;
    }
}
