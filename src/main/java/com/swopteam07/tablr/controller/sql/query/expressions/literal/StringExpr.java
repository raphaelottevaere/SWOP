package com.swopteam07.tablr.controller.sql.query.expressions.literal;

import com.swopteam07.tablr.controller.sql.SourceLocation;

public class StringExpr extends DataExpr<String>
{
	public StringExpr(SourceLocation source, String data)
	{
		super(source, data);
	}
}
