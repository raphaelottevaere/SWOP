package com.swopteam07.tablr.controller.sql.query.data;

import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public class Environment
{
	List<String> columns;
	List<RawCell> rows;

	public Environment(List<String> columns, List<RawCell> row)
	{
		this.columns = columns;
		this.rows = row;
	}

	public Object getValue(SqlId sqlId)
	{
		int id = this.columns.indexOf(sqlId.toString());
		return rows.get(id).value;
	}
}
