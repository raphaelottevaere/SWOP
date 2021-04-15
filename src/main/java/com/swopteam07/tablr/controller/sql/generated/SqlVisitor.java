// Generated from /home/covert/code/tablr/src/main/java/com/swopteam07/tablr/controller/sql/generated/Sql.g4 by ANTLR 4.7.2
package com.swopteam07.tablr.controller.sql.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface SqlVisitor<T> extends ParseTreeVisitor<T>
{
	/**
	 * Visit a parse tree produced by {@link SqlParser#query}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(SqlParser.QueryContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#select_stmnt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_stmnt(SqlParser.Select_stmntContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#from_stmnt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_stmnt(SqlParser.From_stmntContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#where_stmnt}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhere_stmnt(SqlParser.Where_stmntContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#result_column}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult_column(SqlParser.Result_columnContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#table}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable(SqlParser.TableContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#join_constraint}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_constraint(SqlParser.Join_constraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#join_operator}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_operator(SqlParser.Join_operatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#join_clause}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_clause(SqlParser.Join_clauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#value_expr}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue_expr(SqlParser.Value_exprContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#literal_value}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_value(SqlParser.Literal_valueContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#boolean_value}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_value(SqlParser.Boolean_valueContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#alias}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlias(SqlParser.AliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#expr_operator}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_operator(SqlParser.Expr_operatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#cell_name}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCell_name(SqlParser.Cell_nameContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#column_name}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_name(SqlParser.Column_nameContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#table_name}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_name(SqlParser.Table_nameContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#database_name}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatabase_name(SqlParser.Database_nameContext ctx);

	/**
	 * Visit a parse tree produced by {@link SqlParser#any_name}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_name(SqlParser.Any_nameContext ctx);
}