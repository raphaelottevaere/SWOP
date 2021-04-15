package com.swopteam07.tablr.controller;

public class LockId
{
	private int tableId;
	private int columnId;


	public LockId(int tableId, int columnid)
	{
		this.tableId = tableId;
		this.columnId = columnid;
	}

	public int getTableId()
	{
		return tableId;
	}

	public int getColumnId()
	{
		return columnId;
	}
}
