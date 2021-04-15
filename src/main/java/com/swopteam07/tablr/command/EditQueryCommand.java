package com.swopteam07.tablr.command;

public class EditQueryCommand extends UpdateCommand {

	String query;
	String oldQuery;

	public EditQueryCommand(int tableId, String query) {
		this.tableId=tableId;
		this.query=query;
		this.oldQuery = database.getQuery(tableId);
	}

	@Override
	public void undo() {
		database.editQuery(tableId, oldQuery);
	}

	@Override
	public void execute() {
		database.editQuery(tableId, query);
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
		return true;
	}
}
