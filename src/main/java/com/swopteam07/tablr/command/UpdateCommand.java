package com.swopteam07.tablr.command;

public abstract class UpdateCommand extends BaseCommand {
	
	@Override
	public abstract void undo();
	
	@Override
	public abstract void execute();

	@Override
	public Integer tableToUpdate() {
		return tableId;
	}
	
	@Override
	public Integer rowToUpdate() {
		return rowId;
	}
	
	@Override
	public final Integer tableToDelete() {
		return null;
	}
	
	@Override
	public final Integer rowToDelete() {
		return null;
	}
	
	/**
	 * Always returns false as an UpdateCommand never requires deletion
	 */
	@Override
	public final boolean tableDeleteRequired() {
		return false;
	}
	
	/**
	 * Always returns false as an UpdateCommand never requires deletion
	 */
	@Override
	public final boolean rowDeleteRequired() {
		return false;
	}
}