package com.swopteam07.tablr.command;

import java.util.Map;
import java.util.TreeMap;
import com.swopteam07.tablr.model.column.Column;

public class DeleteColumnCommand extends DeleteCommand {

	private Column column;
	private int columnId;
	private Map<Integer, Object> cells = new TreeMap<Integer, Object>();

	public DeleteColumnCommand(int tableId, int columnId) {
		this.tableId = tableId;
		this.columnId = columnId;
	}

	@Override
	public void undo() {
		database.addExistingColumn(tableId, columnId, column);
		int[] rows = database.getAllRowsID(tableId);
		for (int r : rows) {
			database.setValue(tableId, r, columnId, cells.get(r));
		}
	}

	@Override
	public void execute() {
        column = database.getColumn(tableId, columnId);
		int[] rows = database.getAllRowsID(tableId);
		for (int r : rows) {
			cells.put(r, database.getCellValue(tableId, r, columnId));
		}
		database.deleteColumn(tableId, columnId);
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