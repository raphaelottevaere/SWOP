package com.swopteam07.tablr.command;

public class ColumnNameCommand extends UpdateCommand{
	
	private String previousValue;
	private String newValue;
	private int columnId;

	public ColumnNameCommand(String newValue, int tableId, int columnId) {
		this.newValue = newValue;
		this.tableId = tableId;
		this.columnId = columnId;
		this.previousValue = database.getColumnName(tableId, columnId);
	}

	@Override
	public void undo() {
		database.updateColumnName(tableId, columnId, previousValue);
	}

	@Override
	public void execute() {
		database.updateColumnName(tableId, columnId, newValue);
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