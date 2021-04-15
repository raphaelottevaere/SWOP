package com.swopteam07.tablr.controller.sql.query.expressions.literal;

import com.swopteam07.tablr.controller.sql.SourceLocation;

public class BoolExpr extends DataExpr<Boolean>
{
	public BoolExpr(SourceLocation source, Boolean data)
	{
		super(source, data);
	}
}
