package com.swopteam07.tablr.controller.sql.query;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.swopteam07.tablr.controller.sql.query.data.DataProvider;
import com.swopteam07.tablr.controller.sql.query.data.Environment;
import com.swopteam07.tablr.controller.sql.query.data.RawCell;
import com.swopteam07.tablr.controller.sql.query.data.RowData;
import com.swopteam07.tablr.controller.sql.query.expressions.Expression;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;
import com.swopteam07.tablr.controller.sql.query.output.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Query
{
	private final DataProvider fromCtx;
	private final Selection selectionCtx;
	private Expression<Boolean> whereCondition;
	private List<SqlId> usedItems;

	/**
	 * class representing a complete query
	 *
	 * @param selectionCtx class for representing output columns
	 * @param fromCtx      class for representing input data
	 * @param condit       class for representing row conditions
	 */
	public Query(Selection selectionCtx, DataProvider fromCtx, Expression condit)
	{
		whereCondition = condit;
		this.fromCtx = fromCtx;
		this.selectionCtx = selectionCtx;
	}

	public DataProvider getFromCtx()
	{
		return fromCtx;
	}

	public Selection getSelectionCtx()
	{
		return selectionCtx;
	}

	public Expression<Boolean> getWhereCondition()
	{
		return whereCondition;
	}

	/**
	 * executes the query
	 * @param jsonNode json representation of the model
	 * @return RowData class containing the collected data according to the query
	 */
	public RowData exec(ArrayNode jsonNode)
	{
		this.usedItems = findSqlids();
		List<RowData> node = this.fromCtx.formatEnvironment(jsonNode);
		List<RowData> collect = node.stream().map(this::filter).collect(Collectors.toList());
		RowData sort = this.sort(collect);
		return sort;
	}

	/**
	 * finds all used references to the model inside the query
	 * @return
	 */
	private List<SqlId> findSqlids()
	{
		List<SqlId> tbls = new ArrayList<>(this.fromCtx.getTbls());
		DataProvider extend = this.fromCtx.getExtend();
		while (extend != null)
		{
			tbls.addAll(extend.getTbls());
			tbls.add(extend.getOnJoin().getLeftid());
			tbls.add(extend.getOnJoin().getRightId());
			extend = extend.getExtend();
		}
		tbls.addAll(this.whereCondition.findReferences());
		tbls.addAll(this.selectionCtx.getResultColumns());
		return tbls;
	}

	/**
	 * sorts the imported tables into single output table
	 * @param collect list of imported tables
	 * @return a single table containing all selected columns
	 */
	private RowData sort(List<RowData> collect)
	{
		List<String> selectedColumns = new ArrayList<>();
		List<List<RawCell>> columnsData = new ArrayList<>();
		for (SqlId resultColumn : this.selectionCtx.getResultColumns())
		{
			String columnName = resultColumn.getNames().stream().reduce((s, s2) -> s + "/" + s2).get();
			RowData rowData = collect.stream().filter(obj -> obj.getColumns().contains(columnName)).findFirst().get();
			selectedColumns.add(resultColumn.getResultColumnAlias());
			int columnId = rowData.getFormatedColumnId(resultColumn);
			var columnData = rowData.getColumnData(columnId);
			columnsData.add(columnData);
		}
		List<List<RawCell>> rows = this.convertColumnsToRows(columnsData);
		return new RowData(selectedColumns, rows, "output");
	}

	/**
	 * rotates table data 90 degrees
	 * @param columnsData data to be rotated
	 * @return rotated table data
	 */
	private List<List<RawCell>> convertColumnsToRows(List<List<RawCell>> columnsData)
	{
		List<List<RawCell>> rows = new ArrayList<>();
		for (int iRows = 0; iRows < columnsData.get(0).size(); iRows++)
		{
			List<RawCell> row = new ArrayList<>();
			for (int iColumns = 0; iColumns < columnsData.size(); iColumns++)
			{
				row.add(columnsData.get(iColumns).get(iRows));
			}
			rows.add(row);
		}
		return rows;
	}

	/**
	 * filters rows based on the row condition
	 * @param tables table who's rows are to be filtered
	 * @return the filtered RowData containing only matching rows
	 */
	public RowData filter(RowData tables)
	{
		List<List<RawCell>> newRows = new ArrayList<>();
		for (List<RawCell> row : tables.getRows())
		{
			if (this.whereCondition.getValue(new Environment(tables.getColumns(), row)))
			{
				newRows.add(row);
			}
		}
		return new RowData(tables.getColumns(), newRows, tables.getSqlId());
	}

	public List<SqlId> getUsedItems()
	{
		return findSqlids();
	}
}
