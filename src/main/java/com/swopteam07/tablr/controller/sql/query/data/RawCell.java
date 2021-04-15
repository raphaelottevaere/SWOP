package com.swopteam07.tablr.controller.sql.query.data;

public class RawCell
{
	public Integer tableId;
	public Integer columnId;
	public Integer rowId;
	public Object value;

	public RawCell(Integer tableId, Integer columnId, Integer rowId, Object value)
	{
		this.tableId = tableId;
		this.columnId = columnId;
		this.rowId = rowId;
		this.value = value;
	}
}
