package com.swopteam07.tablr.controller.sql.query.expressions.numer;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;

public class PlusExpr extends NumberExpr
{

	public PlusExpr(SourceLocation sourceLocation, Expression<Integer> double1, Expression<Integer> double2)
	{
		super(sourceLocation, double1, double2);
	}

	@Override
	protected Integer exec(Integer dOne, Integer dTwo)
	{
		return dOne + dTwo;
	}


}
