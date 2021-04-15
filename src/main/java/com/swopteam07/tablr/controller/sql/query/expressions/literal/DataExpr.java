package com.swopteam07.tablr.controller.sql.query.expressions.literal;


import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.Collections;
import java.util.List;

public abstract class DataExpr<dataType> extends Expression<dataType>
{
	private final dataType dtValue;

	DataExpr(SourceLocation source, dataType data)
	{
		super(source);
		dtValue = data;
	}

	public dataType getValue(Environment env)
	{
		return dtValue;
	}

	@Override
	public List<SqlId> findReferences()
	{
		return Collections.emptyList();
	}
}

