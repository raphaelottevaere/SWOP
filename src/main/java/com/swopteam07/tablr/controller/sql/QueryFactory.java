package com.swopteam07.tablr.controller.sql;

import com.swopteam07.tablr.controller.sql.query.Query;
import com.swopteam07.tablr.controller.sql.query.data.DataProvider;
import com.swopteam07.tablr.controller.sql.query.data.JoinExpression;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.expressions.ReferenceExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.equation.EqualExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.equation.GreaterThanExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.equation.LessThanExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.literal.BoolExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.literal.IntExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.literal.StringExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.logical.AndExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.logical.OrExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.numer.MinusExpr;
import com.swopteam07.tablr.controller.sql.query.expressions.numer.PlusExpr;
import com.swopteam07.tablr.controller.sql.query.id.CellId;
import com.swopteam07.tablr.controller.sql.query.id.ResultColumn;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;
import com.swopteam07.tablr.controller.sql.query.id.TableId;
import com.swopteam07.tablr.controller.sql.query.output.Selection;

import java.util.List;

public class QueryFactory implements IQueryFactory<Query, Selection, DataProvider, Expression, SqlId>
{
	@Override
	public Query createQuery(Selection selectionCtx, DataProvider fromCtx, Expression condit, SourceLocation ctx)
	{
		return new Query(selectionCtx, fromCtx, condit);
	}

	@Override
	public Expression createOrExprional(Expression left, Expression right, SourceLocation ctx)
	{
		return new OrExpr(ctx, left, right);
	}

	@Override
	public Expression createAndExprional(Expression left, Expression right, SourceLocation ctx)
	{
		return new AndExpr(ctx, left, right);
	}

	@Override
	public Expression createEqualExpr(Expression left, Expression right, SourceLocation ctx)
	{
		return new EqualExpr(ctx, left, right);
	}

	@Override
	public Expression createSmallerThen(Expression left, Expression right, SourceLocation ctx)
	{
		return new LessThanExpr(ctx, left, right);
	}

	@Override
	public Expression createLargerThen(Expression left, Expression right, SourceLocation ctx)
	{
		return new GreaterThanExpr(ctx, left, right);
	}

	@Override
	public SqlId createResultColumn(String table_nameContext, String column_nameContext, String alias, SourceLocation ctx)
	{
		return new ResultColumn(ctx, table_nameContext, column_nameContext, alias);
	}

	@Override
	public Selection createSelectStatement(List<SqlId> collect, SourceLocation ctx)
	{
		return new Selection(ctx, collect);
	}

	@Override
	public SqlId createTableId(String dbName, String tableName, String alias, SourceLocation ctx)
	{
		return new TableId(ctx, dbName, tableName, alias);
	}

	@Override
	public DataProvider createDataProvider(SqlId tbl1, SqlId tbl2, JoinExpression onJoin, SourceLocation ctx)
	{
		return new DataProvider(ctx, tbl1, tbl2, onJoin);
	}

	@Override
	public SqlId createCellName(String databaseName, String tableName, String columnName, SourceLocation ctx)
	{
		return new CellId(ctx, databaseName, tableName, columnName);
	}

	@Override
	public Expression createReferenceExpr(SourceLocation ctx, SqlId cellref)
	{
		return new ReferenceExpr(ctx, cellref);
	}

	@Override
	public Expression createBoolean(SourceLocation ctx, String text)
	{
		return new BoolExpr(ctx, Boolean.parseBoolean(text));
	}

	@Override
	public Expression createPlus(Expression left, Expression right, SourceLocation ctx)
	{
		return new PlusExpr(ctx, left, right);
	}

	@Override
	public Expression createMinus(Expression left, Expression right, SourceLocation ctx)
	{
		return new MinusExpr(ctx, left, right);
	}

	@Override
	public Expression createStringLiteral(String str, SourceLocation ctx)
	{
		return new StringExpr(ctx, str);
	}

	@Override
	public Expression createIntegerLiteral(String text, SourceLocation ctx)
	{
		return new IntExpr(ctx, Integer.parseInt(text));
	}

	@Override
	public DataProvider createFromStmnt(List<SqlId> sqlIds, DataProvider jc, SourceLocation ctx)
	{
		return new DataProvider(ctx, sqlIds, jc);
	}

	@Override
	public JoinExpression createJoinExpression(SqlId dbId, SqlId dbId1, SourceLocation toSourceLocation)
	{
		return new JoinExpression(toSourceLocation, dbId, dbId1);
	}

	@Override
	public DataProvider createFromStmnt(List<SqlId> tableSqlids, DataProvider from, JoinExpression onJoin, SourceLocation toSourceLocation)
	{
		return new DataProvider(toSourceLocation, tableSqlids, from, onJoin);
	}

}
