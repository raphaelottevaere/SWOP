package com.swopteam07.tablr.model;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.cell.DataCell;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.model.column.ColumnFactory;
import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.model.column.StringColumn;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Class representing a table in the database.
 */
public class Table {

    /**
     * The name of this table.
     */
    private String name;

    /**
     * The name of this table.
     */
    private boolean isComputed = false;

    /**
     * The rows contained in this table.
     * Maps rowId -> table.
     */
    private final Map<Integer, Row> rows = new TreeMap<>();

    /**
     * The columns in this table.
     * Maps columnId -> column.
     */
    private final Map<Integer, Column> columns = new TreeMap<>();

    /**
     * The id of this table.
     */
    private final int id;

    /**
     * Autoincrementing id for the rows of this table.
     */
    private int rowId = 0;

    /**
     * Autoincrementing id for the columns of this table.
     */
    private int columnId = 0;
    private String queryString = "";

    /**
     * Create a new table.
     *
     * @param id      The id of this table.
     * @param name    The name of this table.
     * @param rows    The rows contained in this table.
     * @param columns The columns contained in this table.
     */
    public Table(int id, String name, ArrayList<Row> rows, ArrayList<Column> columns) {
        this.id = id;
        setName(name);

        if (rows.stream().anyMatch(r -> r.getNbCells() != columns.size()))
            throw new DomainException(
                    "Each row needs to contain exactly as much cells as there are columns in this table.");

        IntStream.range(rowId, rows.size()).forEach(i -> this.rows.put(i, rows.get(i)));
        rowId += rows.size();

        IntStream.range(columnId, columns.size()).forEach(i -> this.columns.put(i, columns.get(i)));
        columnId += rows.size();
    }

    /**
     * Copy constructor.
     *
     * @param original The table to copy.
     */
    Table(Table original) {
        id = original.id;
        name = original.name;
        original.rows.keySet().forEach(k -> rows.put(k, original.rows.get(k)));
        original.columns.keySet().forEach(k -> columns.put(k, original.columns.get(k).copy()));
        setQuery(original.getQuery());
    }

    /**
     * Create a new table with no rows and no columns.
     *
     * @param id   The id of the table.
     * @param name The name of the table.
     */
    public Table(int id, String name) {
        this(id, name, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Get the name of this table.
     *
     * @return The name of this table.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the id of this table.
     *
     * @return The if of this table.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set a new name for this table.
     *
     * @param name The new name.
     * @throws DomainException Thrown when the new name is not valid.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new DomainException("Name can not ben empty");
        this.name = name;
    }

    /**
     * Checks if the given name is a valid name for a column.
     *
     * @param columnId The id of the column whose name is to be changed.
     * @param name     The new name for the column.
     * @return False if the name is already in use by a column with an ID not equal to the given ID, true otherwise.
     */
    public boolean isValidColumnName(int columnId, String name) {
        return !name.isEmpty() && columns.values().stream().allMatch(c -> !c.getName().equals(name) ||
                c.getId() == columnId);
    }

    /**
     * Update the name of a column in this table.
     *
     * @param columnId The id of the column for which to update the name.
     * @param name     The new name of the column.
     * @throws DomainException Thrown when the the column name is invalid.
     */
    public void updateColumnName(int columnId, String name) throws DomainException {
        if (!isValidColumnName(columnId, name))
            throw new DomainException("The column name " + name + " is already in the table with id " + columnId);

        columns.get(columnId).setName(name);
    }

    /**
     * Get the columns in this table.
     *
     * @return Array of all the columns in this table.
     */
    public Column[] getColumns() {
        return columns.values().stream().map(Column::copy).toArray(Column[]::new);
    }

    /**
     * Get a column in this table.
     *
     * @param id The id of the column to get.
     * @return The column with the specified id.
     * @throws DomainException thrown when there is no column with the given id.
     */
    public Column getColumn(int id) {
        if (!columns.containsKey(id))
            throw new DomainException("There is no column with id " + columnId + " in the table with id " + getId());
        return columns.get(id).copy();
    }

    /**
     * Get the name of the column with the specified id.
     *
     * @param id The id of the column for which to get the name.
     * @return The name of the column.
     */
    public String getColumnName(int id) {
        return getColumn(id).getName();
    }

    /**
     * Add a new column to this table.
     * This column will be of the STRING type, allow blanks and have "" as the default value.
     *
     * @return The id of the new column.
     */
    public int addColumn() {

        int i = columns.size();

        // In case the user assigned some unusual names.
        while (!isValidColumnName(columnId, "Column" + i)) {
            i++;
        }

        String name = "Column" + i;

        Column column = new StringColumn(columnId, name);
        columns.put(columnId, column);
        int newId = columnId;
        columnId++;
        rows.values().forEach(row -> row.setCell(column.getId(), column.createCell()));
        return newId;
    }

    /**
     * Get the rows in this table.
     *
     * @return Array of the rows contained within this table.
     */
    public Row[] getRows() {
        return rows.values().stream().map(Row::new).toArray(Row[]::new);
    }

    /**
     * Update the type of a column in this table.
     *
     * @param columnId The id of the column to update.
     * @param type     The new type of the column.
     * @throws DomainException Thrown if the column can't be changed to the new type.
     */
    public void updateColumnType(int columnId, DataType type) throws DomainException {
        try {
            convertColumn(columnId, type);
        } catch (Exception exc) {
            throw new DomainException("Column with id " + columnId + " can't be converted to a " + type + " column.");
        }
    }

    /**
     * Convert a column to a new type.
     *
     * @param columnId The id of the column that needs converting.
     * @param type     The new type of the column.
     * @throws DomainException          Thrown when the column was in an illegal state prior to this executing this method.
     * @throws IllegalArgumentException Thrown when a column's values couldn't be converted to the new type.
     */
    private void convertColumn(int columnId, DataType type) throws DomainException {
        Column oldColumn = getColumn(columnId);
        Column newColumn = columnChange(oldColumn, type, columnId);

        Map<Integer, Cell> newCells = convertColumnHelper(newColumn, columnId);

        // No errors were thrown when the program reaches here.
        // Now the cells and column can be replaced.
        columns.put(columnId, newColumn);
        rows.keySet().forEach(id -> rows.get(id).setCell(columnId, newCells.get(id)));
    }

    /**
     * Helper function for ConvertColumn and isValidType
     *
     * @param oldColumn The old column.
     * @param type      The type that the old column needs to be changed to.
     * @return The newly converted column.
     */
    private Column columnChange(Column oldColumn, DataType type, int columnId) {
        Column newColumn;
        ColumnFactory factory = new ColumnFactory();

        // Build the new column.
        if (oldColumn.getDefaultValue() == null && !oldColumn.allowsBlank())
            throw new DomainException("Something went TERRIBLY wrong, this exception should never be thrown");

        if (oldColumn.defaultIsBlank())
            newColumn = factory.getColumn(type, columnId, oldColumn.getName(), true);
        else
            newColumn = factory.getColumn(type, columnId, oldColumn.getName(), oldColumn.allowsBlank(),
                    oldColumn.getDefaultValue().toString());
        return newColumn;
    }

    /**
     * Helper function for ConvertColumn and isValidType
     *
     * @param newColumn The newly converted column.
     * @param columnId  The id of column.
     * @return Map to links the id of a cell to the converted cell.
     */
    private Map<Integer, Cell> convertColumnHelper(Column newColumn, int columnId) {
        // Create the new cells for the column.
        // Can't immediately save them in the rows map because an error might still occur.
        Map<Integer, Cell> newCells = new HashMap<>();
        rows.keySet().forEach(id -> {
            Object value = getRow(id).getCell(columnId).getValue();

            if (value == null && !newColumn.allowsBlank())
                throw new DomainException("Something went TERRIBLY wrong, this exception should never be thrown");

            newCells.put(id, newColumn.createCell(newColumn.parseValue(value == null ? null : value.toString())));
        });
        return newCells;
    }

    /**
     * Returns all the columns names/headers from the current table
     *
     * @return all the columns names from this table
     */
    public String[] getColumnsHeader() {
        return columns.values().stream().map(Column::getName).toArray(String[]::new);
    }

    /**
     * Gets all rowIDs from the table
     *
     * @return all rowIds that can be used to access a Row
     */
    public int[] getAllRowID() {
        return rows.keySet().stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Deletes the given row from the Table
     *
     * @param rowId the id of the row to delete.
     */
    public void deleteRow(int rowId) {
        rows.remove(rowId);
    }

    /**
     * Checks if the given value is a valid value for the given column.
     *
     * @param columnId The if of the cell for which to check.
     * @param value    The value for which to check.
     * @return cell.isValid(value)
     */
    public boolean checkValidValue(int columnId, Object value) {
        try {
            getColumn(columnId).parseValue(value.toString());
        } catch (IllegalArgumentException exc) {
            return false;
        }
        return true;
    }

    /**
     * Sets the value to the given Cell in the given Row if cell.isValid(value) == true
     *
     * @param rowId  The id of the row that contains the cell for which to set a new value.
     * @param cellId The id of the cell for which to set a new value.
     * @param value  The new value of the cell.
     */
    public void setValue(int rowId, int cellId, Object value) {
        rows.get(rowId).setValue(cellId, value instanceof String ? getColumn(cellId).parseValue((String) value) : value);
    }

    /**
     * Gets a row from the table
     *
     * @param rowId The id of the row to get.
     * @return The row with the specified id.
     * @throws DomainException Thrown when there is no table with the given id.
     */
    public Row getRow(int rowId) {
        if (!rows.containsKey(rowId))
            throw new DomainException("There is no row with id " + rowId);
        return new Row(rows.get(rowId));
    }

    /**
     * Delete the column with the given ID from the table.
     *
     * @param columnId The id of the column to delete.
     */
    public void deleteColumn(int columnId) {
        if (!columns.containsKey(columnId))
            throw new DomainException("There is no column with id " + columnId + " in the table with id " + getId());

        columns.remove(columnId);
        rows.values().forEach(row -> row.deleteCell(columnId));
    }

    /**
     * Adds a row to the table with the correct columns
     *
     * @return id for the new Row
     */
    public int addRow() {
        Column[] c = getColumns();
        Row r = new Row(rowId);
        for (Column column : c) {
            r.setCell(column.getId(), column.createCell());
        }
        rows.put(rowId, r);
        int returnValue = rowId;
        rowId++;
        return returnValue;
    }

    /**
     * Transforms the cells from the given row into DataCells for use outside of model
     *
     * @param rowID The id of the row which to get the DataCell.
     * @return Datacell array for the cells in the given rowID
     */
    public DataCell[] getDataCellsFromRow(int rowID) {
        return getRow(rowID).getDataCells();
    }

    /**
     * Check if a column contains any blank values.
     *
     * @param columnId The id of the column for which to check.
     * @return True if the default value of the column or any of the cells in the column contain a blank value.
     */
    private boolean containsBlanks(int columnId) {
        return getColumn(columnId).defaultIsBlank() ||
                rows.values().stream().anyMatch(row -> row.getCell(columnId).getValue() == null);
    }

    /**
     * Update the blanks allowed value of a column.
     *
     * @param columnId      The id of the column for which to update the allows blank value.
     * @param blanksAllowed The new value.
     */
    public void updateBlanksAllowed(int columnId, boolean blanksAllowed) {
        if (!blanksAllowed && containsBlanks(columnId))
            throw new DomainException("The column with id " + columnId + " can't be changed to " +
                    (getColumn(columnId).allowsBlank() ? " no longer " : "") + " accept blanks.");
        columns.get(columnId).setAllowsBlanks(blanksAllowed);
        rows.values().stream().map(r -> r.getCell(columnId)).forEach(c -> c.setAllowsBlanks(blanksAllowed));
    }

    /**
     * Update the default value of a column.
     *
     * @param columnId     The id of the column for which to update the default value.
     * @param defaultValue The new default value.
     */
    public void updateDefaultValue(int columnId, String defaultValue) {
        columns.get(columnId).updateDefaultValue(defaultValue);
    }

    /**
     * Check if the specified string is a valid default value for the specified column.
     *
     * @param columnId The column to check for.
     * @param newValue The value to check for.
     * @return True if the value if valid, false otherwise.
     */
    public boolean isValidDefaultValue(int columnId, String newValue) {
        return getColumn(columnId).isValidDefault(newValue);
    }

    /**
     * Returns a single cell as a dataCell
     *
     * @param rowID  The id of the row that contains the cell.
     * @param cellID The id of the cell to get.
     * @return datacell for the given cellID
     */
    public DataCell getDataCell(int rowID, int cellID) {
        return getRow(rowID).getDataCell(cellID);
    }

    /**
     * Get the ids of the columns in this table.
     *
     * @return Array of the ids of the columns in this table.
     */
    public int[] getColumnIds() {
        return columns.values().stream().mapToInt(Column::getId).toArray();
    }

    /**
     * Get the number of columns in this table.
     *
     * @return The number of columns in this table.
     */
    public int getNbColumns() {
        return columns.size();
    }

    /**
     * Get the number of rows in this table.
     *
     * @return The number of rows in this table.
     */
    public int getNbRows() {
        return rows.size();
    }

    /**
     * Get the value of the cell with the given cellId from the row  with the given rowId
     *
     * @param rowId The id of the row for which to retrieve the cell value.
     * @param cellId The id of the cell for which to retrieve the value.
     * @return Value of the cell if the cell exists
     */
    public Object getCellValue(int rowId, int cellId) {
        return getRow(rowId).getCellValue(cellId);
    }

    /**
     * Get the default value of a column for the given columnId
     *
     * @param columnId The id of the column.
     * @return Default value
     */
    public Object getDefaultValue(int columnId) {
        return getColumn(columnId).getDefaultValue();
    }

    /**
     * Sets the default value to the given value
     *
     * @param columnId The id of the column for which to set the default value.
     * @param value The new default value.
     */
    public void setDefaultValue(int columnId, Object value) {
        columns.get(columnId).setDefaultValue(value);
    }

    /**
     * Gets the String representation of the Type of the Column
     *
     * @param columnId The id of the column for which to get the type.
     * @return String representation of the Type of the Column
     */
    public String getColumnType(int columnId) {
        return getColumn(columnId).getType();
    }

    /**
     * Checks if the new Type if a Valid Type for the column
     *
     * @param columnId The id of the column for which to check.
     * @param dataType The type for which to check if it is valid.
     * @return isValid(type)
     */
    public boolean isValidColumnType(Integer columnId, DataType dataType) {
        try {
            Column oldColumn = getColumn(columnId);
            Column newColumn = columnChange(oldColumn, dataType, columnId);
            convertColumnHelper(newColumn, columnId);
            return true;
        } catch (Exception exc) {
            //exc.printStackTrace();
            return false;
        }
    }

    /**
     * Adds an existing row made out of Datacells to the table, if the rowID is not in use
     *
     * @param row   Row made out of dataCell in the right order
     * @param rowId RowId
     */
    public void addPreExistingRow(Row row, int rowId) {
        if (rows.get(rowId) != null)
            return;
        rows.put(rowId, row);
    }

    /**
     * Checks if BlanksAllowed is valid for the Column with Id ColId
     *
     * @param columnId The id of the column for which to check if the new value if valid.
     * @param blanksAllowed The new value.
     * @return
     * |    if (blanksAllowed == true) true
     * |    else (column(columnId).hasNoNulls()
     */
    public boolean isValidBlanksAllowed(int columnId, boolean blanksAllowed) {
        if (blanksAllowed)
            return true;
        try {
            Column column = getColumn(columnId);
            return !column.defaultIsBlank() && !rows.values().stream().anyMatch(row -> row.getCell(columnId).getValue() == null);
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * Add Existing column to the table, also generates the cells for the new column
     * If the columnID is not in use
     *
     * @param columnId ColumnId
     * @param column   Column
     */
    public void addExistingColumn(int columnId, Column column) {
        if (columns.get(columnId) == null) {
            columns.put(columnId, column);
            rows.values().forEach(row -> row.setCell(column.getId(), column.createCell()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof Table)) return false;
        Table otherTable = (Table) other;
        return otherTable.name.equals(this.name) && otherTable.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

	/**
	 *
	 * Returns the Querie of a table, or "" if its a stored table
	 * @return Querie of the table or "" if it is a stored Table
	 */
	public String getQuery() {
        return this.queryString;
    }

    public void setQuery(String query)
    {
        this.queryString = query;
    }

    public boolean isComputed()
    {
        return isComputed;
    }

    public void setComputed()
    {
        isComputed = true;
    }
}
