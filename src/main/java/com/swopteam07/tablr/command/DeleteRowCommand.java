package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.Row;

public class DeleteRowCommand extends DeleteCommand {

    private Row row;

    public DeleteRowCommand(int tableId, int rowId) {
        this.tableId = tableId;
        this.rowId = rowId;
        executed = false;
    }

    @Override
    public void undo() {
        database.addPreExistingRow(row, tableId, rowId);
        executed = false;
    }

    @Override
    public void execute() {
        this.row = database.getRow(tableId, rowId);
        database.deleteRow(tableId, rowId);
        executed = true;
    }

    @Override
    public boolean tablenameUpdateRequired() {
        return false;
    }

    @Override
    public boolean tableDeleteRequired() {
        return false;
    }

    @Override
    public boolean rowDeleteRequired() {
        return true;
    }

    @Override
    public boolean tableUpdateRequired() {
        return false;
    }

    @Override
    public boolean rowUpdateRequired() {
        return true;
    }

}