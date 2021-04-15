package com.swopteam07.tablr.command;

public class AddColumnCommand extends DeleteCommand {

	private int colId;

	public AddColumnCommand(int tableId) {
			this.tableId = tableId;
		}

	@Override
	public void undo() {
		database.deleteColumn(tableId, colId);
	}

	@Override
	public void execute() {
		colId = database.addColumn(tableId);
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

	@Override
	public boolean tableDeleteRequired() {
		return false;
	}

	@Override
	public boolean rowDeleteRequired() {
		return false;
	}
}