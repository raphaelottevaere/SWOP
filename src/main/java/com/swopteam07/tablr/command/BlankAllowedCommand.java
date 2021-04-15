package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.DomainException;

public class BlankAllowedCommand extends UpdateCommand {

	boolean allowed;
	private int columnId;

	public BlankAllowedCommand(int tableId, int columnId, boolean value) {
		this.tableId = tableId;
		this.columnId = columnId;
		this.allowed = value;
	}

	@Override
	public void undo() {
		try {
			database.updateBlanksAllowed(tableId, columnId, !allowed);
		} catch (DomainException exc) {
			// Getting here means something terrible has gone wrong
			// Normally all undo and execute function should only be done if this is allowed
			// Meaning a change has occurred in the database between the last command and this command
			// The Commands are not build to allow this to happen in this way
		}
	}

	@Override
	public void execute() {
		try {
			database.updateBlanksAllowed(tableId, columnId, allowed);
		} catch (DomainException exc) {
			// Getting here means something terrible has gone wrong
			// Normally all undo and execute function should only be done if this is allowed
			// Meaning a change has occurred in the database between the last command and this command
			// The Commands are not build to allow this to happen in this way
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
