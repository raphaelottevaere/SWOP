package com.swopteam07.tablr.controller.sql.query.expressions.equation;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public class NotEqualExpr extends Expression<Boolean>
{
	private Expression exprOne, exprTwo;

	public NotEqualExpr(SourceLocation sourceLocation, Expression expr1, Expression expr2)
	{
		super(sourceLocation);
		exprOne = expr1;
		exprTwo = expr2;
	}


	@Override
	public Boolean getValue(Environment env)
	{
		return (exprOne.getValue(env) != exprTwo.getValue(env));
	}

	@Override
	public List<SqlId> findReferences()
	{
		List references = this.exprOne.findReferences();
		references.addAll(this.exprTwo.findReferences());
		return references;
	}
}
