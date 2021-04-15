package com.swopteam07.tablr.command;

public class CellChangeCommand extends UpdateCommand{
	
	private Object previousValue;
	private Object newValue;
	private int cellId;

	public CellChangeCommand(Object newValue, int tableId, int rowId, int cellId) {
		this.newValue = newValue;
		this.tableId = tableId;
		this.rowId = rowId;
		this.cellId = cellId;
		this.previousValue = database.getCellValue(tableId,rowId,cellId);
	}

	@Override
	public void undo() {
		database.setValue(tableId, rowId, cellId, previousValue);
	}

	@Override
	public void execute() {
		database.setValue(tableId, rowId, cellId, newValue);
	}

	@Override
	public boolean tablenameUpdateRequired() {
		return false;
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
