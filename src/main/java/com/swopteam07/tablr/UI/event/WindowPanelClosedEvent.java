package com.swopteam07.tablr.UI.event;

import com.swopteam07.tablr.UI.WindowPanel;

public class WindowPanelClosedEvent implements Event {



    private WindowPanel closedWindow;

    public WindowPanelClosedEvent(WindowPanel closedWindow) {
        this.closedWindow = closedWindow;
    }

    public WindowPanel getClosedWindow() {
        return closedWindow;
    }


}
