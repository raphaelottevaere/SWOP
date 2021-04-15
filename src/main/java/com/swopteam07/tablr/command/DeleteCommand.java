package com.swopteam07.tablr.command;

public abstract class DeleteCommand extends BaseCommand {
	
	/**
	 * checks if the last execution of this command was 
	 * 	Executed()	-&gt; True
	 * 	Undo() 		-&gt; False
	 */
	protected boolean executed;
	
	/**
	 * Undoes this command
	 */
	@Override
	public abstract void undo();
	
	/**
	 * executes this command
	 */
	@Override
	public abstract void execute();
	
	@Override
	public Integer tableToDelete() {
		return tableId;
	}
	
	@Override
	public Integer rowToDelete() {
		return rowId;
	}
	
	@Override
	public Integer tableToUpdate() {
		return tableId;
	}

	@Override
	public Integer rowToUpdate() {
		return rowId;
	}
	
}