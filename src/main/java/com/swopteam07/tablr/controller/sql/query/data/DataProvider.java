package com.swopteam07.tablr.controller.sql.query.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataProvider
{
	private final SourceLocation source;
	private final List<SqlId> tbls;
	private final JoinExpression onJoin;
	private DataProvider extend;

	/**
	 * creates a new DataProvider responsible for extracting data from model required by query
	 *
	 * @param ctx    location in query string
	 * @param tbl1   reference to table required by query
	 * @param tbl2   reference to table required by query
	 * @param onJoin condition of joining tbl1 and tbl2
	 */
	public DataProvider(SourceLocation ctx, SqlId tbl1, SqlId tbl2, JoinExpression onJoin)
	{
		source = ctx;
		this.tbls = new ArrayList<>(2);
		this.tbls.add(tbl1);
		this.tbls.add(tbl2);
		this.onJoin = onJoin;
		this.extend = null;
	}

	/**
	 * creates a new DataProvider responsible for extracting data from model required by query
	 * @param ctx location in query string
	 * @param sqlIds reference to tables required by query
	 * @param jc reference to additional tables
	 */
	public DataProvider(SourceLocation ctx, List<SqlId> sqlIds, DataProvider jc)
	{
		source = ctx;
		this.tbls = sqlIds;
		onJoin = null;
		this.extend = jc;
	}

	/**
	 * creates a new DataProvider responsible for extracting data from model required by query
	 * @param ctx location in query string
	 * @param sqlIds reference to tables required by query
	 * @param jc reference to additional tables
	 * @param onJoin condition of joining tbl1 and tbl2
	 */
	public DataProvider(SourceLocation ctx, List<SqlId> sqlIds, DataProvider jc, JoinExpression onJoin)
	{
		source = ctx;
		this.tbls = sqlIds;
		this.onJoin = onJoin;
		this.extend = jc;
	}


	protected SourceLocation getSourceLocation()
	{
		return source;
	}

	public SourceLocation getSource()
	{
		return source;
	}

	public List<SqlId> getTbls()
	{
		return tbls;
	}

	public JoinExpression getOnJoin()
	{
		return onJoin;
	}

	public DataProvider getExtend()
	{
		return extend;
	}

	/**
	 * receives the model to be converted into RowData
	 * @param tables a json object representing the model
	 * @return
	 */
	public List<RowData> formatEnvironment(ArrayNode tables)
	{
		List<RowData> foundTables = new LinkedList<>();
		lookfortable:
		for (SqlId sqlId : this.getTbls())
		{
			for (JsonNode tableNode : tables)
			{
				if (tableNode.get("name").asText().equals(sqlId.getNames().get(0)))
				{
					foundTables.add(new RowData(tableNode, sqlId));
					continue lookfortable;
				}
			}
		}
		if (getExtend() != null)
		{
			List<RowData> extended = this.getExtend().formatEnvironment(tables);
			//merge(node, jsonNode);//TODO better
			extended.addAll(foundTables);
			foundTables = extended;
		}
		if (getOnJoin() != null)
		{
			assert foundTables.size() == 2; // ASSERT ON INNER JOIN 2 TABLES
			RowData rowData = joinMerge(foundTables.get(0), foundTables.get(1));
			foundTables.clear();
			foundTables.add(rowData);
		} else
			foundTables.forEach(RowData::formatColumnNames);
		return foundTables;
	}

	/**
	 * merges tables acording to the onJoin expression
	 * @param leftTable table to be merged
	 * @param rightTable table to be merged
	 * @return a RowData containing the data of both tables
	 */
	private RowData joinMerge(RowData leftTable, RowData rightTable)
	{
		return this.getOnJoin().merge(leftTable, rightTable);
	}


}
