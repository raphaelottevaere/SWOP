package com.swopteam07.tablr.controller.sql;

import com.swopteam07.tablr.controller.sql.query.data.JoinExpression;

import java.util.List;

public interface IQueryFactory<Q, S, F, E, ID>
{

	Q createQuery(S selectionCtx, F fromCtx, E condit, SourceLocation ctx);

	E createOrExprional(E left, E right, SourceLocation ctx);

	E createAndExprional(E left, E right, SourceLocation ctx);

	E createEqualExpr(E left, E right, SourceLocation ctx);

	E createSmallerThen(E left, E right, SourceLocation ctx);

	E createLargerThen(E left, E right, SourceLocation ctx);

	ID createResultColumn(String table_nameContext, String column_nameContext, String alias, SourceLocation ctx);

	S createSelectStatement(List<ID> collect, SourceLocation ctx);

	ID createTableId(String dbName, String tableName, String alias, SourceLocation ctx);

	F createDataProvider(ID tbl1, ID tbl2, JoinExpression onJoin, SourceLocation ctx);

	ID createCellName(String datbaseName, String tableName, String columnName, SourceLocation ctx);

	E createReferenceExpr(SourceLocation ctx, ID cellref);

	E createBoolean(SourceLocation ctx, String text);

	E createPlus(E left, E right, SourceLocation ctx);

	E createMinus(E left, E right, SourceLocation ctx);

	E createStringLiteral(String str, SourceLocation ctx);

	E createIntegerLiteral(String text, SourceLocation toSourceLocation);

	F createFromStmnt(List<ID> ids, F jc, SourceLocation ctx);

	JoinExpression createJoinExpression(ID dbId, ID dbId1, SourceLocation toSourceLocation);

	F createFromStmnt(List<ID> tableSqlids, F from, JoinExpression onJoin, SourceLocation toSourceLocation);
}
