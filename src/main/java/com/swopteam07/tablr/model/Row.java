package com.swopteam07.tablr.model;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.cell.DataCell;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * A row in a table in the database.
 */
public class Row {

    /**
     * Id that uniquely identifies this row.
     */
    private final int id;

    /**
     * The cells contained in this row.
     * Maps the cell id -> cell.
     * The cell id is equal to the id of the column that contains the cell.
     */
    private final Map<Integer, Cell> cells = new TreeMap<>();

    /**
     * Create a new row.
     *
     * @param id The id of the row.
     */
    public Row(int id) {
        this.id = id;
    }

    /**
     * Copy constructor.
     *
     * @param original The row to copy.
     */
    Row(Row original) {
        id = original.id;
        original.cells.keySet().forEach(k -> this.cells.put(k, original.getCell(k)));
    }

    /**
     * Gets the cell that is linked to the current ID
     *
     * @param column The id of the column that contains the cell.
     * @return a single Cell
     * @throws DomainException if there is no cell with the current ID
     */
    public Cell getCell(int column) {
        if (!cells.containsKey(column))
            throw new DomainException("There is no cell with ID " + column + " in the row.");
        return cells.get(column).copy();
    }

    /**
     * Get the id of this row.
     * @return The id of this row.
     */
    public int getId() {
        return id;
    }

    /**
     * Set a new value for a cell.
     *
     * @param cellId The id of the cell for which to set the new value.
     * @param value  The new value for the cell.
     */
    public void setValue(int cellId, Object value) {
        cells.get(cellId).setValue(value);
    }

    /**
     * Add or replace a new cell to the row.
     *
     * @param column The id of the column to which the new cell belongs.
     * @param cell   The new cell.
     */
    public void setCell(int column, Cell cell) {
        cells.put(column, cell);
    }

    /**
     * Get the number of cells in this row.
     * @return The number of cells in this row.
     */
    public int getNbCells() {
        return cells.size();
    }

    /**
     * Delete the cell with the given id.
     *
     * @param id The id of the column that contains the cell.
     */
    public void deleteCell(int id) {
        cells.remove(id);
    }

    /**
     * Changes an entire row of cells into datCells which can be used for transport
     *
     * @return A dataCell array that resembles the Cells of this row in the correct order
     */
    public DataCell[] getDataCells() {
        ArrayList<DataCell> dc = new ArrayList<>();
        cells.values().forEach(s -> dc.add(new DataCell(s.getDataType(), s.getValue(), s.allowsBlanks())));
        return dc.toArray(DataCell[]::new);
    }

    /**
     * Changes a single cell with the given ID into datCells which can be used for transport
     *
     * @param cellID The id of the cell for which to change the cell into a datacell.
     * @return A dataCell array that resembles the Cells of this row in the correct order
     */
    public DataCell getDataCell(int cellID) {
        Cell c = getCell(cellID);
        return new DataCell(c.getDataType(), c.getValue(), c.allowsBlanks());

    }

    /**
     * Gets the value of cell
     * @param cellId The id of the cell for which to retrieve the value.
     * @return value of cell in Object
     */
	public Object getCellValue(int cellId) {
		return getCell(cellId).getValue();
	}

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof Row)) return false;
        Row otherRow = (Row) other;
        return this.id == otherRow.id && cells.keySet().stream()
                .allMatch(k -> otherRow.cells.containsKey(k) && otherRow.cells.get(k).equals(this.cells.get(k)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cells);
    }
}
