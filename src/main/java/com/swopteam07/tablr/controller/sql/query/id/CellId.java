package com.swopteam07.tablr.controller.sql.query.id;

import com.swopteam07.tablr.controller.sql.SourceLocation;

public class CellId extends SqlId
{
	public CellId(SourceLocation ctx, String databaseName, String tableName, String columnName)
	{
		super(ctx, null, databaseName, tableName, columnName);
	}

	@Override
	public String getColumnName()
	{
		return this.getNames().get(1);
	}
}
