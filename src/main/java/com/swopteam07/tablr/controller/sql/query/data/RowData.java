package com.swopteam07.tablr.controller.sql.query.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;
import com.swopteam07.tablr.controller.sql.query.id.TableId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RowData
{
	private SqlId name;
	private List<String> columns = new LinkedList<>();
	private List<List<RawCell>> rows = new LinkedList<>();

	/**
	 * Representation of a table
	 *
	 * @param jsonNode json object containing a table to be converted into RowData
	 * @param sqlId    id of the table represented by the json object
	 */
	public RowData(JsonNode jsonNode, SqlId sqlId)
	{
		this.name = sqlId;
		initRows(jsonNode);
		initColumns(jsonNode);
	}

	public RowData(List<String> newColumns, List<List<RawCell>> newRows, String s)
	{
		this.name = new TableId(null, "", "", s);
		this.rows = newRows;
		this.columns = newColumns;
	}

	public RowData(List<String> newColumns, List<List<RawCell>> newRows, SqlId s)
	{
		this.name = s;
		this.rows = newRows;
		this.columns = newColumns;
	}

	private void initRows(JsonNode jsonNode)
	{
		//rows
		for (JsonNode jsonRow : jsonNode.get("rows"))
		{
			ArrayList<RawCell> row = new ArrayList<>();
			for (int columnId = 0; columnId < jsonRow.get("dataCells").size(); columnId++)
			{
				var cell = jsonRow.get("dataCells").get(columnId);
				switch (cell.get("type").asText())
				{//jsonNode.get("columns").get(columnId).get("id").asInt()
					//TODO ADD CASES
					case "INTEGER":
						row.add(new RawCell(jsonNode.get("id").asInt(), columnId, jsonRow.get("id").asInt(), cell.get("value").asInt()));
						break;
					case "STRING":
					case "MAIL":
						row.add(new RawCell(jsonNode.get("id").asInt(), columnId, jsonRow.get("id").asInt(), cell.get("value").asText()));
						break;
					case "BOOLEAN":
						row.add(new RawCell(jsonNode.get("id").asInt(), columnId, jsonRow.get("id").asInt(), cell.get("value").asBoolean()));
						break;
				}
			}
			this.rows.add(row);
		}
	}

	private void initColumns(JsonNode jsonNode)
	{
		//columns
		for (JsonNode column : jsonNode.get("columns"))
		{
			this.columns.add(column.get("name").asText());
		}
	}

	public String getName()
	{
		return name.getAlias();
	}//TODO VERIFY

	public List<String> getColumns()
	{
		return List.copyOf(columns);
	}

	public List<List<RawCell>> getRows()
	{
		return rows.stream().map(List::copyOf).collect(Collectors.toUnmodifiableList());
	}

	public int getColumnId(SqlId sqlId)
	{
		//assert this.name.getAlias().equals(sqlId.getAlias());
		String tableName = sqlId.getNames().get(0);
		String columnName = sqlId.getNames().get(1);
		//assert tableId.equals(this.getName()); TODO ALIAS deftig
		return this.columns.stream().map(obj ->
		{
			String[] split = obj.split("/");
			return split[split.length - 1];
		}).collect(Collectors.toList()).indexOf(columnName);
	}

	public int getFormatedColumnId(SqlId sqlId)
	{
		String columnName = this.columns.stream().filter(obj -> obj.equals(sqlId.toString())).findFirst().get();
		return this.columns.indexOf(columnName);
	}

	public List<RawCell> search(int columnid, RawCell match)
	{
		assert this.rows.stream().filter(obj -> obj.get(columnid).value.equals(match.value)).count() < 2;
		return this.rows.stream().filter(obj -> obj.get(columnid).value.equals(match.value)).findFirst().orElse(null);
	}

	public SqlId getSqlId()
	{
		return this.name;
	}

	public List<RawCell> getColumnData(int columnId)
	{
		return rows.stream().map(obj -> obj.get(columnId)).collect(Collectors.toList());
	}

	public void formatColumnNames()
	{
		if (this.name.getNames().size() == 0)
			throw new IllegalStateException();
		List<String> collect = this.getColumns().stream().map(obj -> this.getName() + "/" + obj).collect(Collectors.toList());
		this.name = new TableId(null, "", "", this.name.getAlias());
		this.columns = collect;
	}
}
