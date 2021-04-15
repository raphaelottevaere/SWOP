package com.swopteam07.tablr.command;

public class AddRowCommand extends DeleteCommand {

	public AddRowCommand(int tableId) {
		this.tableId = tableId;
		executed=false;
	}

	@Override
	public void undo() {
		database.deleteRow(tableId, rowId);
		executed =false;
	}

	@Override
	public void execute() {
		rowId = database.addRow(tableId);
		executed=true;
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
		return !executed;
	}

	@Override
	public boolean tableUpdateRequired() {
		return true;
	}

	@Override
	public boolean rowUpdateRequired() {
		return true;
	}


}