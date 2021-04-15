package com.swopteam07.tablr.controller.sql;


import com.swopteam07.tablr.controller.sql.generated.SqlBaseVisitor;
import com.swopteam07.tablr.controller.sql.generated.SqlParser;
import com.swopteam07.tablr.controller.sql.query.data.JoinExpression;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParserVisitor<Q, Select, From, Expr, dbId> extends SqlBaseVisitor<Void>
{
	private final IQueryFactory<Q, Select, From, Expr, dbId> factory;
	private Q mainQ;
	private SelectVisitor selectVisitor;
	private FromVisitor fromCtxVisitor;
	private IdVistor idVistor;
	private ExpressionVisitor expressionVisitor;
	private JoinExpressionVisitor joinExpressionVisitor;

	public ParserVisitor(IQueryFactory<Q, Select, From, Expr, dbId> factory)
	{
		this.factory = factory;
		this.selectVisitor = new SelectVisitor();
		this.fromCtxVisitor = new FromVisitor();
		this.expressionVisitor = new ExpressionVisitor();
		this.expressionVisitor = new ExpressionVisitor();
		this.idVistor = new IdVistor();
		this.joinExpressionVisitor = new JoinExpressionVisitor();
	}

	@Override
	public Void visitQuery(SqlParser.QueryContext ctx)
	{

		Select selectionCtx = selectVisitor.visit(ctx.select);
		From fromCtx = fromCtxVisitor.visit(ctx.from);
		Expr condit = factory.createBoolean(toSourceLocation(ctx), "TRUE");
		if (ctx.where != null)
			condit = expressionVisitor.visit(ctx.where);
		this.mainQ = factory.createQuery(selectionCtx, fromCtx, condit, toSourceLocation(ctx));
		return null;
	}


	private SourceLocation toSourceLocation(ParserRuleContext ctx)
	{
		int line = ctx.getStart().getLine();
		int column = ctx.getStart().getCharPositionInLine();
		return new SourceLocation(line, column);
	}

	public Optional<Q> getQuery()
	{
		return Optional.of(this.mainQ);
	}

	public enum Operator
	{
		OR("OR"), AND("AND"), EQUAL("="), ST("<"), LT(">"), PLUS("+"), MINUS("-");
		private String text;

		Operator(String text)
		{
			this.text = text;
		}

		public static Operator fromString(String text)
		{
			for (Operator b : Operator.values())
			{
				if (b.text.equalsIgnoreCase(text))
				{
					return b;
				}
			}
			throw new IllegalArgumentException("error");
		}

		public String getText()
		{
			return this.text;
		}
	}

	private class SelectVisitor extends SqlBaseVisitor<Select>
	{
		@Override
		public Select visitSelect_stmnt(SqlParser.Select_stmntContext ctx)
		{
			List<SqlParser.Result_columnContext> resultColumns = ctx.result_column();
			List<dbId> collect = resultColumns.stream().map(obj -> idVistor.visit(obj)).collect(Collectors.toList());
			return factory.createSelectStatement(collect, toSourceLocation(ctx));
		}

		@Override
		public Select visitResult_column(SqlParser.Result_columnContext ctx)
		{
			return super.visitResult_column(ctx);
		}
	}

	private class FromVisitor extends SqlBaseVisitor<From>
	{
		@Override
		public From visitFrom_stmnt(SqlParser.From_stmntContext ctx)
		{
			if (ctx.table() == null)
				return fromCtxVisitor.visit(ctx.join_clause());
			dbId visit = idVistor.visit(ctx.table());
			SqlParser.Join_clauseContext join_clauseContext = ctx.join_clause();
			From jc = null;
			if (join_clauseContext != null)
				jc = fromCtxVisitor.visit(join_clauseContext);
			return factory.createFromStmnt(Collections.singletonList(visit), jc, toSourceLocation(ctx));
		}

		@Override
		public From visitJoin_clause(SqlParser.Join_clauseContext ctx)
		{
			List<SqlParser.TableContext> tables = ctx.table();
			List<dbId> tableSqlids = tables.stream().map(obj -> idVistor.visit(obj)).collect(Collectors.toList());
			if (tables.size() == 2)
			{
				JoinExpression onJoin = joinExpressionVisitor.visit(ctx.join_constraint());
				return factory.createDataProvider(tableSqlids.get(0), tableSqlids.get(1), onJoin, toSourceLocation(ctx));
			} else
			{
				assert ctx.join_clause() != null;
				assert tables.size() == 1;
				var from = fromCtxVisitor.visit(ctx.join_clause());
				var onJoin = joinExpressionVisitor.visit(ctx.join_constraint());
				return factory.createFromStmnt(tableSqlids, from, onJoin, toSourceLocation(ctx));
			}
		}

	}

	//TODO REFACTOR
	private class JoinExpressionVisitor extends SqlBaseVisitor<JoinExpression>
	{
		@Override
		public JoinExpression visitJoin_constraint(SqlParser.Join_constraintContext ctx)
		{
			assert ctx.cell_name().size() == 2;
			List<SqlParser.Cell_nameContext> cellNames = ctx.cell_name();
			List<dbId> cellrefs = cellNames.stream().map(obj -> idVistor.visit(obj)).collect(Collectors.toList());
			return factory.createJoinExpression(cellrefs.get(0), cellrefs.get(1), toSourceLocation(ctx));//TODO SUPPORT MORE THEN ONLY "="
		}
	}
	private class ExpressionVisitor extends SqlBaseVisitor<Expr>
	{
		@Override
		public Expr visitJoin_constraint(SqlParser.Join_constraintContext ctx)
		{
			assert ctx.cell_name().size() == 2;
			List<SqlParser.Cell_nameContext> cellNames = ctx.cell_name();
			List<dbId> cellrefs = cellNames.stream().map(obj -> idVistor.visit(obj)).collect(Collectors.toList());
			Expr referenceExpr = factory.createReferenceExpr(toSourceLocation(ctx), cellrefs.get(0));
			Expr referenceExpr2 = factory.createReferenceExpr(toSourceLocation(ctx), cellrefs.get(1));
			return factory.createEqualExpr(referenceExpr, referenceExpr2, toSourceLocation(ctx));
		}

//		@Override
//		public Expr visitWhere_stmnt(SqlParser.Where_stmntContext ctx)
//		{
//			return expressionVisitor.visit(ctx.boolean_expr());
//		}

		@Override
		public Expr visitValue_expr(SqlParser.Value_exprContext ctx)
		{
			if (ctx.boolean_value() != null)
				return factory.createBoolean(toSourceLocation(ctx), ctx.boolean_value().getText());
			if (ctx.literal_value() != null)
				return visit(ctx.literal_value());
			if (ctx.cell_name() != null)
			{
				dbId visit = idVistor.visit(ctx.cell_name());
				return factory.createReferenceExpr(toSourceLocation(ctx), visit);
			}
			Expr left = visit(ctx.left);
			Expr right = visit(ctx.right);
			switch (Operator.fromString(ctx.op.getText()))
			{
				case OR:
					return factory.createOrExprional(left, right, toSourceLocation(ctx));
				case AND:
					return factory.createAndExprional(left, right, toSourceLocation(ctx));
				case EQUAL:
					return factory.createEqualExpr(left, right, toSourceLocation(ctx));
				case ST:
					return factory.createSmallerThen(left, right, toSourceLocation(ctx));
				case LT:
					return factory.createLargerThen(left, right, toSourceLocation(ctx));
				case PLUS:
					return factory.createPlus(left, right, toSourceLocation(ctx));
				case MINUS:
					return factory.createMinus(left, right, toSourceLocation(ctx));
			}
			throw new IllegalArgumentException("operator not valid");
		}

		@Override
		public Expr visitLiteral_value(SqlParser.Literal_valueContext ctx)
		{
			if (ctx.NUMERIC_LITERAL() != null)
				return factory.createIntegerLiteral(ctx.NUMERIC_LITERAL().getText(), toSourceLocation(ctx));
			return factory.createStringLiteral(ctx.any_name().getText(), toSourceLocation(ctx));
		}
	}

	private class IdVistor extends SqlBaseVisitor<dbId>
	{

		@Override
		public dbId visitResult_column(SqlParser.Result_columnContext ctx)
		{
			String tableName = (ctx.table_name() == null) ? "" : ctx.table_name().any_name().getText();
			String columnName = (ctx.column_name() == null) ? "" : ctx.column_name().any_name().getText();
			String alias = (ctx.alias() == null) ? "" : ctx.alias().any_name().getText();
			return factory.createResultColumn(tableName, columnName, alias, toSourceLocation(ctx));
		}

		@Override
		public dbId visitTable(SqlParser.TableContext ctx)
		{
			String dbName = (ctx.database_name() == null) ? "" : ctx.database_name().getText();
			String tableName = (ctx.table_name() == null) ? "" : ctx.table_name().getText();
			String alias = (ctx.alias() == null) ? "" : ctx.alias().any_name().getText();
			return factory.createTableId(dbName, tableName, alias, toSourceLocation(ctx));
		}

		@Override
		public dbId visitCell_name(SqlParser.Cell_nameContext ctx)
		{
			String dbName = (ctx.database_name() == null) ? "" : ctx.database_name().getText();
			String tableName = (ctx.table_name() == null) ? "" : ctx.table_name().getText();
			String columnName = (ctx.column_name() == null) ? "" : ctx.column_name().getText();
			return factory.createCellName(dbName, tableName, columnName, toSourceLocation(ctx));
		}
	}
}
