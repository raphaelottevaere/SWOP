package com.swopteam07.tablr.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.swopteam07.tablr.controller.sql.QueryFactory;
import com.swopteam07.tablr.controller.sql.QueryParser;
import com.swopteam07.tablr.controller.sql.query.Query;
import com.swopteam07.tablr.controller.sql.query.data.RawCell;
import com.swopteam07.tablr.controller.sql.query.data.RowData;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;
import com.swopteam07.tablr.controller.sql.query.id.TableId;
import com.swopteam07.tablr.model.Database;
import com.swopteam07.tablr.model.Row;
import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.model.cell.SqlDerivedCell;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.model.column.StringColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryFacade
{

	@Deprecated
	public static int parseQuery(String strSql, Database database, String tableName)
	{
		int tableId = createTable(database, tableName);
		List<LockId> lockIds = parseQueryIntoExistingTable(strSql, database, tableId);
		return tableId;
	}

	/**
	 * parses a given query and outputs the resulting data into a table
	 *
	 * @param strSql   the String containing the query
	 * @param database the reference to the database which will house the query output
	 * @param tableId  the id refering to the table which will house the query output inside of the database
	 * @return a list of LockId's containing table and column id's which are referrd to in the query
	 */
	public static List<LockId> parseQueryIntoExistingTable(String strSql, Database database, int tableId)
	{
		QueryParser queryParser = new QueryParser<>(new QueryFactory());
		Query query = (Query) queryParser.parseString(strSql).get();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.valueToTree(database);
		RowData table = query.exec((ArrayNode) jsonNode.get("tables"));
		formatTable(database, table, tableId);
		return convertToLockId(query.getUsedItems(), database);
	}

	/**
	 * converts sqlid to lockid for use in the controller
	 * @param usedItems all references made by the query to the model
	 * @param database the model to which the usedItems applies
	 * @return a list of Lockid's which contain table and column id's from the database
	 */
	private static List<LockId> convertToLockId(List<SqlId> usedItems, Database database)
	{
		List<LockId> lockIds = new ArrayList<>(usedItems.size());
		for (SqlId usedItem : usedItems)
		{
			if (!(usedItem instanceof TableId))

			{
				String searchTableName = usedItem.getNames().get(0);
				String finalSearchTableName = searchTableName;
				Optional<SqlId> optionalTranslationOfTableName = usedItems.stream().filter(obj -> obj instanceof TableId)
						.filter(obj -> obj.getAlias().equals(finalSearchTableName)).findAny();
				if (optionalTranslationOfTableName.isPresent())
					searchTableName = optionalTranslationOfTableName.get().getNames().get(0);

				int tableId = database.getTablesNames().indexOf(searchTableName);
				int columnid = -1;
				for (Column column : database.getTable(tableId).getColumns())
				{
					if (column.getName().equals(usedItem.getNames().get(1)))
					{
						columnid = column.getId();
						break;
					}
				}
				lockIds.add(new LockId(tableId, columnid));
			}
		}

		return lockIds;
	}

	/**
	 * creates and renames a table
	 * @param database database where the new table will be created
	 * @param tableName the name which the new table is going to receive
	 * @return the table id from the database
	 */
	private static int createTable(Database database, String tableName)
	{
		int tableId = database.addTable();
		database.editTableName(tableId, tableName);

		return tableId;
	}

	/**
	 * clears the table and inputs the query output into the database
	 * @param database database which contains the table referd to by tableid
	 * @param tables query output
	 * @param tableId the id of the table used to output the tables (query output) data
	 * @effect clears any existing rows and columns in the table referd to by tableid
	 * @effect converts the data inside tables to the database
	 */
	private static void formatTable(Database database, RowData tables, int tableId)
	{
		//TODO LINK CELLS
		clearTable(database, tableId);
		database.setComputedTable(tableId);
		for (int i = 0; i < tables.getColumns().size(); i++)
		{
			String column = tables.getColumns().get(i);
			database.addExistingColumn(tableId, i, new StringColumn(i, column, true));
		}
		for (int rowId = 0; rowId < tables.getRows().size(); rowId++)
		{
			List<RawCell> row = tables.getRows().get(rowId);
			Row newRow = new Row(rowId);
			for (int i = 0; i < row.size(); i++)
			{
				RawCell rawCell = row.get(i);
				newRow.setCell(i, new SqlDerivedCell(rawCell.value, database.getTable(rawCell.tableId).getRow(rawCell.rowId).getCell(rawCell.columnId),
						value -> database.setValue(rawCell.tableId, rawCell.rowId, rawCell.columnId, value)));
				//database.setValue(tableId, rowId, i, rawCell.value);
			}
			database.addPreExistingRow(newRow, tableId, rowId);
		}
	}

	/**
	 * clears existing table in the database
	 * @param database model which is going to be cleared
	 * @param tableId the id of the table which is going to be cleared
	 * @effect if the table which corresponds to the tableId has rows or columns those are deleted
	 */
	private static void clearTable(Database database, int tableId)
	{
		Table table = database.getTable(tableId);
		for (int rowId : table.getAllRowID())
		{
			database.deleteRow(tableId, rowId);
		}
		for (int columnId : table.getColumnIds())
		{
			database.deleteColumn(tableId, columnId);
		}
	}

	/**
	 * Checks whether given string is a valid query
	 * @param strSql query to be executed
	 * @param database model which contains names for validation
	 * @return returns a boolean true if the given query is valid otherwise will return false
	 */
	public static boolean isValidQuery(String strSql, Database database)
	{
		try
		{
			QueryParser queryParser = new QueryParser<>(new QueryFactory());
			Query query = (Query) queryParser.parseString(strSql).get();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.valueToTree(database);
			query.exec((ArrayNode) jsonNode.get("tables"));
		} catch (Exception ex)
		{
			return false;
		}
		return true;
	}

}
