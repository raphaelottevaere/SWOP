package com.swopteam07.tablr.controller.sql.query.expressions;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public abstract class Expression<dataType>
{
	private final SourceLocation source;

	protected Expression(SourceLocation sourceLocation)
	{
		source = sourceLocation;
	}

	public abstract dataType getValue(Environment env);

	protected SourceLocation getSourceLocation()
	{
		return source;
	}

	public abstract List<SqlId> findReferences();
}
