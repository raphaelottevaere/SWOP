package com.swopteam07.tablr.controller.sql.query.id;

import com.swopteam07.tablr.controller.sql.SourceLocation;

public class ResultColumn extends SqlId
{
	public ResultColumn(SourceLocation ctx, String table_nameContext, String column_nameContext, String alias)
	{
		super(ctx, alias, table_nameContext, column_nameContext);
	}

	@Override
	public String getColumnName()
	{
		return this.getNames().get(1);
	}
}
