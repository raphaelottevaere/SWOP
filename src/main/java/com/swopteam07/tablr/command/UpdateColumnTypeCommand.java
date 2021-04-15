package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.DomainException;

public class UpdateColumnTypeCommand extends UpdateCommand {

	private int columnId;
	private String newType;
	private String oldType;

	public UpdateColumnTypeCommand(int tableId, int columnId, String type) {
		this.tableId = tableId;
		this.columnId = columnId;
		this.oldType = database.getColumnType(tableId, columnId);
		this.newType = type;
	}

	@Override
	public void undo() {
		try {
			database.updateColumnType(tableId, columnId, oldType);
		} catch (DomainException exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public void execute() {
		try {
			database.updateColumnType(tableId, columnId, newType);
		} catch (DomainException exp) {
			exp.printStackTrace();
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
