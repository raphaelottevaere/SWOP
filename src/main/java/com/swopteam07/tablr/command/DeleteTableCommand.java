package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.Table;

public class DeleteTableCommand extends DeleteCommand {
	
	private Table table;

	public DeleteTableCommand(int tableId) {
			this.tableId = tableId;
			executed=false;
		}

	@Override
	public void undo() {
		database.addExistingTable(table, tableId);
		executed=false;
	}

	@Override
	public void execute() {
		table = database.getTable(tableId);
		database.deleteTable(tableId);
		executed=true;
	}

	@Override
	public boolean tablenameUpdateRequired() {
		return false;
	}

	@Override
	public boolean tableDeleteRequired() {
		return executed;
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