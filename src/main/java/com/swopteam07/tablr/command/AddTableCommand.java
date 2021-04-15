package com.swopteam07.tablr.command;

public class AddTableCommand extends DeleteCommand {

	public AddTableCommand() {}
	String tableName;

	@Override
	public void undo() {
		tableName = database.getTable(tableId).getName();
		database.deleteTable(tableId);
		executed=false;
	}

	@Override
	public void execute() {
		if(tableId==null) {
			tableId = database.addTable();
		}
		else {
			database.addTable(tableId, tableName);
		}
		executed=true;
	}

	@Override
	public boolean tablenameUpdateRequired() {
		return false;
	}

	@Override
	public boolean tableDeleteRequired() {
		return !executed;
	}

	@Override
	public boolean rowDeleteRequired() {
		return false;
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
