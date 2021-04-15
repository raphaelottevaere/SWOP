package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.DomainException;

public class DefaultColumnValueCommand extends UpdateCommand {

	private int columnId;
	private String newValue;
	private Object oldValue;

	public DefaultColumnValueCommand(int tableId, int columnId, String defaultValue) {
		this.tableId = tableId;
		this.columnId = columnId;
		this.newValue = defaultValue;
		this.oldValue = database.getDefaultValue(tableId, columnId);
	}

	@Override
	public void undo() {
		try {
			database.setBackDefaultValue(tableId, columnId, oldValue);
		} catch (DomainException exc) {
			exc.printStackTrace();
		}
	}

	@Override
	public void execute() {
		try {
			database.updateDefaultValue(tableId, columnId, newValue);
		} catch (DomainException exc) {
			exc.printStackTrace();
		}
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
