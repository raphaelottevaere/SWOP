package com.swopteam07.tablr.UI.event;

public class ScrollOffsetChangedEvent extends ChangeEvent<Integer> {

    /**
     * Create a new change event.
     *
     * @param oldValue The offset before the user moved the scroll element.
     * @param newValue The offset after the user moved the scroll element.
     */
    public ScrollOffsetChangedEvent(Integer oldValue, Integer newValue) throws IllegalArgumentException {
        super(oldValue, newValue);
    }

}
