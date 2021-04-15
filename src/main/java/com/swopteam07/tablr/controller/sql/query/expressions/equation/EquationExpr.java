package com.swopteam07.tablr.controller.sql.query.expressions.equation;


import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public abstract class EquationExpr extends Expression<Boolean>
{
	private final Expression<Integer> doubleOne;
	private final Expression<Integer> doubleTwo;

	EquationExpr(SourceLocation sourceLocation, Expression<Integer> double1, Expression<Integer> double2)
	{
		super(sourceLocation);
		doubleOne = double1;
		doubleTwo = double2;
	}

	protected abstract boolean exec(Integer dOne, Integer dTwo);

	public Boolean getValue(Environment env)
	{
		return exec(doubleOne.getValue(env), doubleTwo.getValue(env));
	}

	public List<SqlId> findReferences()
	{
		List references = this.doubleOne.findReferences();
		references.addAll(this.doubleTwo.findReferences());
		return references;
	}
}
