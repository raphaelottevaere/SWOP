package com.swopteam07.tablr.controller.sql.query.id;

import com.swopteam07.tablr.controller.sql.SourceLocation;

public class TableId extends SqlId
{

	public TableId(SourceLocation sourceLocation, String dbName, String tableName, String alias)
	{
		super(sourceLocation, alias, dbName, tableName);
	}

	@Override
	public String getColumnName()
	{
		return null;
	}
}
