package com.swopteam07.tablr.command;

public interface ICommand {

	/**
	 * Undoes this command
	 */
    void undo();
	
	/**
	 * executes this command
	 */
    void execute();

	/**
	 * returns the TableId that the command acts on or Null if no TableId is used
	 * @return TableId or null if not used
	 */
    Integer tableToUpdate();
	
	/**
	 * returns the RowId that the command acts on or Null if no RowId is used
	 * @return RowID
	 */
    Integer rowToUpdate();
	
	/**
	 * Returns the TableId that needs to be Deleted from the views
	 * @return TableId or null if not applicable
	 */
    Integer tableToDelete();
	
	/**
	 * Returns the RowId that needs to be deleted from the views
	 * @return RowId or Null if not apllicable
	 */
    Integer rowToDelete();
	
	/**
	 * Returns if a tableId Update is Required after the command is executed
	 * @return True if tableId was changed
	 * 			False if no tableId was changed
	 */
    boolean tablenameUpdateRequired();
	
	/**
	 * Returns if a tableDeletion is required in the views after the command is executed
	 * @return	True if tableDeletion is required
	 * 			False if no TableDeletion is Required
	 */
    boolean tableDeleteRequired();
	
	/**
	 * Returns if a RowDeletion is required in the views after the command is executed
	 * @return 	True if rowDeletion is required
	 * 			False if no rowDeletion is required
	 */
    boolean rowDeleteRequired();

	/**
	 * Returns if a tableUpdate is required in the views after the command is executed
	 * @return 	True if a tableUpdate is required
	 * 			False if no TableUpdate is required
	 */
    boolean tableUpdateRequired();
	
	/**
	 * Returns if a rowUpdate is required in the views after the command is executed
	 * @return 	True if a rowUpdate is required
	 * 			False if no rowUpdate is required
	 */
    boolean rowUpdateRequired();
}
