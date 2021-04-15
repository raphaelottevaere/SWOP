package com.swopteam07.tablr.command;

import com.swopteam07.tablr.model.Database;

public abstract class BaseCommand implements ICommand {

    /**
     * Reference to the database instance used to execute the commands on.
     * This class is needed for testing purposes, the reference needs to be reset every test.
     */
    Database database = Database.getInstance();
    
	/**
	 * the rowId of the tables that the command was executed upon
	 */
	protected Integer rowId;
	/**
	 * the tableId of the tableId that the command was executed upon
	 */
	protected Integer tableId;

}
