package com.swopteam07.tablr.controller.sql.query.expressions;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.LinkedList;
import java.util.List;

public class ReferenceExpr extends Expression<Object>
{

	private final SqlId sqlId;

	public ReferenceExpr(SourceLocation sourceLocation, SqlId sqlId)
	{
		super(sourceLocation);
		this.sqlId = sqlId;
	}

	@Override
	public Object getValue(Environment env)
	{
		return env.getValue(sqlId);
	}

	@Override
	public List<SqlId> findReferences()
	{
		LinkedList<SqlId> sqlIds = new LinkedList<>();
		sqlIds.add(this.sqlId);
		return sqlIds;
	}
}
