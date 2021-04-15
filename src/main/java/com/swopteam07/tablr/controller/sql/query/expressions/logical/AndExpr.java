package com.swopteam07.tablr.controller.sql.query.expressions.logical;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;

public class AndExpr extends LogicalExpr
{
	public AndExpr(SourceLocation sourceLocation, Expression<Boolean> expr1, Expression<Boolean> expr2)
	{
		super(sourceLocation, expr1, expr2);
	}

	protected Boolean exec(boolean bool1, boolean bool2)
	{
		return bool1 && bool2;
	}
}
