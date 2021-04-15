package com.swopteam07.tablr.controller.sql.query.expressions.numer;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.Collections;
import java.util.List;

public abstract class NumberExpr extends Expression<Integer>
{
	private final Expression<Integer> doubleOne;
	private final Expression<Integer> doubleTwo;

	NumberExpr(SourceLocation sourceLocation, Expression<Integer> double1, Expression<Integer> double2)
	{
		super(sourceLocation);
		doubleOne = double1;
		doubleTwo = double2;
	}

	protected abstract Integer exec(Integer dOne, Integer dTwo);

	public Integer getValue(Environment env)
	{
		return exec(doubleOne.getValue(env), doubleTwo.getValue(env));
	}

	@Override
	public List<SqlId> findReferences()
	{
		return Collections.emptyList();
	}
}
