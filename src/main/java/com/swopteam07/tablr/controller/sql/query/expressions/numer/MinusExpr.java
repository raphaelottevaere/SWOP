package com.swopteam07.tablr.controller.sql.query.expressions.numer;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;

public class MinusExpr extends NumberExpr
{
	public MinusExpr(SourceLocation ctx, Expression left, Expression right)
	{
		super(ctx, left, right);
	}

	@Override
	protected Integer exec(Integer dOne, Integer dTwo)
	{
		return dOne - dTwo;
	}
}
