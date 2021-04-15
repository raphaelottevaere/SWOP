package com.swopteam07.tablr.UI.handlers;

public abstract class MouseClickListener {

    private int tableId;

    public MouseClickListener(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return this.tableId;
    }

    public abstract void handle(int clickCount);

}
