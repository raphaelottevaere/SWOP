package com.swopteam07.tablr.controller.sql.query.expressions.equation;


import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;

public class GreaterOrEqualExpr extends EquationExpr
{
	public GreaterOrEqualExpr(SourceLocation sourceLocation, Expression<Integer> double1, Expression<Integer> double2)
	{
		super(sourceLocation, double1, double2);
	}

	protected boolean exec(Integer dOne, Integer dTwo)
	{
		return dOne >= dTwo;
	}
}
