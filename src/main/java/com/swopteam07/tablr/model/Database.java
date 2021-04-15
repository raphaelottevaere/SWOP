package com.swopteam07.tablr.model;

import com.swopteam07.tablr.controller.LockId;
import com.swopteam07.tablr.controller.QueryFacade;
import com.swopteam07.tablr.model.cell.DataCell;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.model.column.DataType;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Singleton
 * Provides an API with which to manipulate elements in the domain model.
 */
public class Database {

    /**
     * Maps the id of a table to that table
     */
    private final Map<Integer, Table> tables = new TreeMap<>();

    private final static AtomicReference<Database> uniqueInstance = new AtomicReference<>();

    private final Map<Integer, List<LockId>> lockIds = new HashMap<>();
    /**
     * Auto incrementing id.
     */
    private int id = 0;

    /**
     * Check if a string is a valid updated table name for the given table.
     *
     * @param tableId The id of the table for which to check the name.
     * @param name    The new name of the table.
     * @return True of the name isn't empty and in is not in use by any table other than the given one.
     */
    public boolean isValidTableName(int tableId, String name) {
        if (checkifLockedTable(tableId) && !this.getTableName(tableId).equals(name))
            return false;
        return !name.isEmpty() && tables.values().stream().allMatch(c -> !c.getName().equals(name) ||
                c.getId() == tableId);
    }

    private boolean checkifLockedTable(int tableId)
    {
        List<LockId> lockIdList = this.lockIds.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return lockIdList.stream().anyMatch(obj -> obj.getTableId() == tableId);
    }

    /**
     * Add a new table to the database.
     *
     * @return The id of the new table.
     */
    public int addTable() {
        int i = tables.size();

        while (!isValidTableName(id, "Table" + i))
		{
            i++;
        }

        String tableName = "Table" + i;
        Table table = new Table(id, tableName);
        this.tables.put(id, table);
        int newId = id;
        id++;
        return newId;
    }
    
    /**
     * @param tableId
     * Add a new table to the database with the given id.
     */
    public void addTable(int tableId, String tableName) {
        Table table = new Table(tableId, tableName);
        this.tables.put(tableId, table);
    }

    /**
     * Get all the tables in the database.
     *
     * @return Array of tables in this database.
     */
    public Table[] getTables() {
        return tables.values().stream().map(Table::new).toArray(Table[]::new);
    }

    /**
     * Get a specific table from the database.
     *
     * @param tableId The id of the table to get.
     * @return The table with the specified id.
     * @throws DomainException Thrown when there is no table with the given id.
     */
    public Table getTable(int tableId) {
        if (tables.containsKey(tableId))
            return new Table(tables.get(tableId));
        else
            throw new DomainException("There is no table with id " + tableId);
    }

    /**
     * Get the names of the tables in the database.
     *
     * @return List of the names of the tables in this database.
     */
    public List<String> getTablesNames() {
        return tables.values().stream().map(Table::getName).collect(Collectors.toList());
    }

    /**
     * Change the name of a table in the database.
     *
     * @param id      The id of the table for which to change the name.
     * @param newName The new name of the table.
     */
    public void editTableName(int id, String newName) {
        if (checkifLockedTable(id))
            throw new DomainException("Table is locked");

        if (!tables.containsKey(id))
            throw new DomainException("Id is Out of Bound");

        if (!isValidTableName(id, newName)) {
            throw new DomainException("Name can not be empty");
        }
        Table t = tables.get(id);
        t.setName(newName);

        tables.put(id, t);
    }

    /**
     * Delete a table.
     *
     * @param id The id of the table to delete.
     */
    public void deleteTable(int id) {
		for (Integer depId : this.lockIds.keySet())
		{
			if (this.lockIds.get(depId).stream().anyMatch(obj -> obj.getTableId() == id))
				deleteTable(depId);
		}
		this.lockIds.put(id, Collections.emptyList());


        if (!tables.containsKey(id)) {
            throw new DomainException(" table with id:  " + id + " does not exist");
        }

        tables.remove(id);
    }

    /**
     * Change the name of a column in the given table.
     *
     * @param tableId  The id of the table that contains the column to update.
     * @param columnId The id of the column to update.
     * @param name     The new name of the column.
     */
    public void updateColumnName(int tableId, int columnId, String name) throws DomainException {
        if (checkIfLockedColumn(tableId, columnId))
            throw new DomainException("Column is locked");

        tables.get(tableId).updateColumnName(columnId, name);
    }

    /**
     * Check if a name if valid for a column in a table.
     *
     * @param tableId  The id of the table that contains the column to update.
     * @param columnId The id of the column to update.
     * @param name     The new name of the column.
     * @return True if the name if valid, false otherwise.
     */
    public boolean isValidColumnName(int tableId, int columnId, String name) {
        if (this.checkIfLockedColumn(tableId, columnId) && !this.getColumnName(tableId, columnId).equals(name))
            return false;
        return tables.get(tableId).isValidColumnName(columnId, name);
    }

    //TODO commentaar
    private boolean checkIfLockedColumn(int tableId, int columnId)
    {
        List<LockId> lockIdList = this.lockIds.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return lockIdList.stream().anyMatch(obj -> obj.getTableId() == tableId && obj.getColumnId() == columnId);
    }

    /**
     *
     * @param tableId
     * @return true if the table is computed
     */
    public boolean isComputed(int tableId){
        return getTable(tableId).isComputed();
    }

    /**
     * Get all the columns in the given table.
     *
     * @param tableId The id of the table for which to get the columns.
     * @return Array of the columns in the specified table.
     */
    public Column[] getColumns(int tableId) {
        return tables.get(tableId).getColumns();
    }

    /**
     * Get the rows in a table in this database.
     *
     * @param tableId The id of the table from which to get the rows.
     * @return Array of the rows contained within the specified table.
     */
    public Row[] getRows(int tableId) {
        return getTable(tableId).getRows();
    }

    /**
     * Get the number of columns in the specified table.
     *
     * @param tableId The table for which to count the columns.
     * @return The number of columns in the specified table.
     */
    public int getNbColumns(int tableId) {
        return getTable(tableId).getNbColumns();
    }

    /**
     * Add a new column to the database, this column is a string column and has
     * "" as the default value.
     *
     * @param tableId The id of the table to add the new column to.
     * @return The id of the new column.
     */
    public int addColumn(int tableId) {
        return tables.get(tableId).addColumn();
    }

    /**
     * Update the type of a column.
     *
     * @param tableId  The id of the table for which to update a column.
     * @param columnId The id of the column to update.
     * @param text     Textual representation of the new type.
     */
    public void updateColumnType(int tableId, int columnId, String text) {
        tables.get(tableId).updateColumnType(columnId, DataType.valueOf(text));
    }

    /**
	 * Get the literal types supported by this database.
     *
	 * @return Array of literal types supported by the database.
     */
    public String[] getDataTypes() {
        return Arrays.stream(DataType.values()).map(Enum::toString).toArray(String[]::new);
    }

    /**
     * @return A reference to this singleton database.
     */
    public synchronized static Database getInstance() {
        if (uniqueInstance.get() == null)
            uniqueInstance.set(new Database());

        return uniqueInstance.get();
    }

    /**
     * @apiNote FOR USE IN TESTING ONLY
     */
    public synchronized static void removeInstanceReference() {
        uniqueInstance.set(null);
    }

    /**
     * Gets all ColumnHeaders from the given table
     *
     * @param tableId The id of the table for which to get the column names.
     * @return All Column headers from the table
     */
    public String[] getAllColumnsHeader(int tableId) {
        return getTable(tableId).getColumnsHeader();
    }

    /**
     * Gets all RowIDs from the given table
     *
     * @param tableId The id of the table for which to get the row ids.
     * @return AllRow IDs (int[]) from the given table
     */
    public int[] getAllRowsID(int tableId) {
        return getTable(tableId).getAllRowID();
    }

    /**
     * Deletes the given row from the given tableID
     *
     * @param tableId The id of the table from which to delete a row.
     * @param rowId   The id of the row to delete.
     */
    public void deleteRow(int tableId, int rowId) {
        tables.get(tableId).deleteRow(rowId);
    }

    /**
     * Check for the given cell in the given row in the given tableID if the given value is valid
     *
     * @param tableId  The id of the table for which to check.
     * @param columnId The id of the cell for which to check.
     * @param value    The value for which to check.
     * @return cell.IsValid(value)
     */
    public boolean checkValidValue(int tableId, int columnId, Object value) {
        return getTable(tableId).checkValidValue(columnId, value);
    }

    /**
     * Sets the value for the given cell in the given row in the given tableID to the given value if allowed
     *
     * @param tableId The id of the table that contains the cell for which to set a new value.
     * @param rowId   The if of the row that contains the cell for which to set a new value.
     * @param cellId  The id of the cell for which to set a new value.
     * @param value   The new value.
     */
    public void setValue(int tableId, int rowId, int cellId, Object value) {
        tables.get(tableId).setValue(rowId, cellId, value);
        if (this.checkIfLockedColumn(tableId, cellId))
        {
            for (Integer depid : this.lockIds.keySet())
            {
                if (this.lockIds.get(depid).stream().anyMatch(obj -> obj.getTableId() == tableId && obj.getColumnId() == cellId))
                {
                    this.editQuery(depid, getTable(depid).getQuery());
                }
            }
        }
        //this.editQuery(tableId, tables.get(tableId).getQuery());
    }

    /**
     * Delete a column from the database.
     *
     * @param tableId  The id of the table from which to delete a column.
     * @param columnId The id of the column to delete.
     */
    public void deleteColumn(int tableId, int columnId) {
		if (checkIfLockedColumn(tableId, columnId))
			for (Integer depId : this.lockIds.keySet())
			{
				if (this.lockIds.get(depId).stream().anyMatch(obj -> obj.getTableId() == tableId))
					deleteTable(depId);
			}
        tables.get(tableId).deleteColumn(columnId);
    }

    /**
     * Get the name of a table in this database.
     *
     * @param tableId The id of the table for which to get the name.
     * @return The name of the table with the specified id.
     */
    public String getTableName(Integer tableId) {
        return getTable(tableId).getName();
    }

    /**
	 * Adds a row to the given TableId
     *
     * @param tableId the id of the table to which the row must be added.
     * @return the new Rows ID
     */
    public int addRow(int tableId) {
        return tables.get(tableId).addRow();
    }

    /**
     * gets DataCells from the given table and RowID
     *
     * @param tableId The id of the table from which to get the cells.
     * @param rowId   The id of the row from which to get the cells.
     * @return DataCell[] containing pure datacells
     */
    public DataCell[] getDataCellsFromRow(int tableId, int rowId) {
        return getTable(tableId).getDataCellsFromRow(rowId);
    }

    /**
     * Update the allows blank value of a column.
     *
     * @param tableId       The id of the table that contains the column to update.
     * @param columnId      The id of the column to update.
     * @param blanksAllowed The new value.
     */
    public void updateBlanksAllowed(int tableId, Integer columnId, boolean blanksAllowed) {
        tables.get(tableId).updateBlanksAllowed(columnId, blanksAllowed);
    }

    /**
     * Update the default value of column.
     *
     * @param tableId      The id of the table that contains to column to update.
     * @param columnId     The id of the column to update.
     * @param defaultValue The new defaultValue.
     */
    public void updateDefaultValue(int tableId, int columnId, String defaultValue) {
        tables.get(tableId).updateDefaultValue(columnId, defaultValue);
    }

    /**
     * Check if the specified value is valid for the specified column in the specified table.
     *
     * @param tableId  The table to check for.
     * @param columnId The id of the column to check for.
     * @param newValue The value to check for.
     * @return True is the value is valid, false otherwise.
     */
    public boolean isValidDefaultValue(int tableId, int columnId, String newValue) {
        return getTable(tableId).isValidDefaultValue(columnId, newValue);
    }

    /**
     * Get a column for a specified table.
     *
     * @param tableId  The id of the table from which to get the column.
     * @param columnId The id of the column to get.
     * @return The column.
     */
    public Column getColumn(int tableId, int columnId) {
        return getTable(tableId).getColumn(columnId);
    }

    /**
     * Gets a dataCell for a singelCellId
     *
     * @param tableId The id of the table that contains the cell for which to get the DataCell.
     * @param rowID   The id of the row for which to get the DataCell.
     * @param cellID  The if of the cell to get.
     * @return A single Datacell for CellID
     */
    @Deprecated
    public DataCell getDataCellsFromRow(int tableId, int rowID, int cellID) {
        return getTable(tableId).getDataCell(rowID, cellID);
    }

    /**
     * Get the ids of the columns in a table.
     *
     * @param tableId The id of the table for which to get the column ids.
     * @return Array of the ids of the columns in the specified table.
     */
    public int[] getColumnIds(int tableId) {
        return getTable(tableId).getColumnIds();
    }

    /**
     * Get the number of tables in this database.
     *
     * @return The number of tables.
     */
    public int getTableCount() {
        return this.tables.size();
    }

    /**
     * Get the name of the column with the specified id in the specified table.
     *
     * @param tableId  The id of the table for which to get the column.
     * @param columnId The id of the column to get.
     * @return The name of the column.
     */
    public String getColumnName(int tableId, int columnId) {
        return getTable(tableId).getColumnName(columnId);
    }

    /**
     * Get the ids of all tables in this database.
     *
     * @return Array of the ids of the tables in this database.
     */
    public Integer[] getAllTableIds() {
        return tables.keySet().toArray(Integer[]::new);
    }

    /**
     * Get the value of the cell with the given cellId from the table with tableId, the row in said table with the given rowId
     *
     * @param tableId The id of the table for which to get the cell value.
     * @param rowId   The id of the row that contains the cell.
     * @param cellId  The id of the cell for which to retrieve the value.
     * @return Value of the cell if the cell exists
     */
    public Object getCellValue(int tableId, int rowId, int cellId) {
        return getTable(tableId).getCellValue(rowId, cellId);
    }

    /**
     * Gets the default value of a column for the given tableId and columnId
     *
     * @param tableId  The id of the table that contains the column for which the default value will be retrieved.
     * @param columnId The id of the column from which to retrieve the default value.
     * @return defaultValue
     */
    public Object getDefaultValue(int tableId, int columnId) {
        return getTable(tableId).getDefaultValue(columnId);
    }

    /**
     * Sets the default value to the given value
     *
     * @param tableId  The id of the table.
     * @param columnId The if of the column.
     * @param value    The new default value.
     */
    public void setBackDefaultValue(int tableId, int columnId, Object value) {
        try {
            tables.get(tableId).setDefaultValue(columnId, value);
        } catch (Exception e) {
            throw new DomainException("Object and Excpected class are not of the same class");
        }
    }

    /**
     * Checks if the specified column allows blanks.
     *
     * @param tableId  The id of the table that contains the column.
     * @param columnId The id of the column for which to check if blanks are allowed.
     * @return blankValue allowed for column
     */
    public boolean checkBlanksAllowed(int tableId, Integer columnId) {
        return getColumn(tableId, columnId).allowsBlank();
    }

    /**
     * Gets the String representation of the Type of the Column
     *
     * @param tableId  The id of table that contains the column.
     * @param columnId The id of the column for which to retrieve the type.
     * @return String representation of the Type of the Column
     */
    public String getColumnType(int tableId, int columnId) {
        return getTable(tableId).getColumnType(columnId);
    }

    /**
     * Checks if the new Type if a Valid Type for the column
     *
     * @param tableId  The id of table that contains the column.
     * @param columnId The id of the column for which to check if the type is valid.
     * @param type     The type for which to check.
     * @return isValid(type)
     */
    public boolean isValidColumnType(int tableId, Integer columnId, String type) {
        return getTable(tableId).isValidColumnType(columnId, DataType.valueOf(type));
    }

    /**
     * Adds a preExisting row made out of dataCells to a table, and row
     *
     * @param row     Made out of dataCells
     * @param tableId TableId for the table to added to
     * @param rowId   The rowId on which the row is added
     */
    public void addPreExistingRow(Row row, int tableId, int rowId) {
        tables.get(tableId).addPreExistingRow(row, rowId);
    }

    /**
     * Checks if the BlanksAllowed value is valid for the given column
     *
     * @param tableId       The id of the table for which to check.
     * @param colId         The id of the column for which to check.
     * @param blanksAllowed The new blanks allowed value.
     * @return | if(blanksAllowed == false) Column(colId).hasNoNulls();
     * | if(blanksAllowed==true) true;
     */
    public boolean isValidBlanksAllowed(int tableId, Integer colId, boolean blanksAllowed) {
        return getTable(tableId).isValidBlanksAllowed(colId, blanksAllowed);
    }

    /**
     * Adds a Existing table to the database if the tableId doesnt exist yet
     *
     * @param table   The table to add.
     * @param tableId The id of the table.
     */
    public void addExistingTable(Table table, int tableId) {
        tables.putIfAbsent(tableId, table);
    }

    /**
     * Add an existing column to the table if no column exists with the columnId
     *
     * @param tableId  The id of the table.
     * @param columnId The id of the column.
     * @param column   The column to add.
     */
    public void addExistingColumn(int tableId, int columnId, Column column) {
        tables.get(tableId).addExistingColumn(columnId, column);
    }

    public DataCell getDataForCell(int tableId, int rowId, int columnId) {
        return getTable(tableId).getDataCell(rowId, columnId);
    }

    /**
     * Retrieve the specified row from the specified table.
     *
     * @param tableId The id of the table from which to get the row.
     * @param rowId   The id of the row to retrieve.
     * @return The retrieved row.
     */
    public Row getRow(int tableId, int rowId) {
        return getTable(tableId).getRow(rowId);
    }

    /**
     * Checks if the query is valid for the qiven table
     * @param tableId
     * @param query
     * @return validQuery(query)==True;
     */
	public boolean isValidQuery(Integer tableId, String query) {
		try
		{
			return QueryFacade.isValidQuery(query, this);
		} catch (Exception ignored)
		{
			return true;
		}
	}

	/**
	 * Edits the query for the given tableId
	 * @param tableId
	 * @param query
	 */
	public void editQuery(int tableId, String query) {
        if (this.isValidQuery(tableId, query))
        {
            this.updateQuery(tableId, query);
            List<LockId> lockIds = QueryFacade.parseQueryIntoExistingTable(query, this, tableId);
            this.lockIds.put(tableId, lockIds);
        }
    }

    private void updateQuery(Integer tableId, String query)
    {
        Table t = tables.get(tableId);
        t.setQuery(query);

        tables.put(tableId, t);
    }

    /**
	 * Gets the query for the given tableId
	 * @param tableId
	 */
	public String getQuery(int tableId) {
		Table t = tables.get(tableId);
		if(t==null)
			return "";
		return t.getQuery();
	}

    public void setComputedTable(int tableId)
    {
        tables.get(tableId).setComputed();
    }
}
