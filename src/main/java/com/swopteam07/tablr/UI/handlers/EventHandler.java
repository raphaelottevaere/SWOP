package com.swopteam07.tablr.UI.handlers;

import com.swopteam07.tablr.UI.event.Event;

public abstract interface EventHandler<T extends Event> {

    void handleEvent(T event);
}
