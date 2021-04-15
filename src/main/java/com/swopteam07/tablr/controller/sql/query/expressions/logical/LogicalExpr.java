package com.swopteam07.tablr.controller.sql.query.expressions.logical;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public abstract class LogicalExpr extends Expression<Boolean>
{
	private final Expression<Boolean> exprOne;
	private final Expression<Boolean> exprTwo;

	LogicalExpr(SourceLocation source, Expression<Boolean> expr1, Expression<Boolean> expr2)
	{
		super(source);
		exprOne = expr1;
		exprTwo = expr2;
	}

	protected abstract Boolean exec(boolean bool1, boolean bool2);

	public Boolean getValue(Environment env)
	{
		return exec(exprOne.getValue(env), exprTwo.getValue(env));
	}

	@Override
	public List<SqlId> findReferences()
	{
		List references = this.exprOne.findReferences();
		references.addAll(this.exprTwo.findReferences());
		return references;
	}
}
