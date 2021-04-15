package com.swopteam07.tablr.command;

public class TableNameCommand extends UpdateCommand{
	
	private String previousValue;
	private String newValue;

	public TableNameCommand(String newValue, int tableId) {
		this.newValue = newValue;
		this.tableId = tableId;
		this.previousValue = database.getTableName(tableId);
	}

	@Override
	public void undo() {
		database.editTableName(tableId, previousValue);
	}

	@Override
	public void execute() {
		database.editTableName(tableId, newValue);
	}

	@Override
	public boolean tablenameUpdateRequired() {
		return true;
	}

	@Override
	public boolean tableUpdateRequired() {
		return true;
	}

	@Override
	public boolean rowUpdateRequired() {
		return false;
	}
}