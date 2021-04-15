package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.event.TableRowSelectedEvent;
import com.swopteam07.tablr.UI.handlers.TableCellWidthChangeHandler;
import com.swopteam07.tablr.UI.handlers.TableChangeHandler;
import com.swopteam07.tablr.UI.event.TableCellWidthChangeEvent;
import com.swopteam07.tablr.UI.event.TableChangeEvent;
import com.swopteam07.tablr.UI.handlers.TableRowSelectedHandler;

import java.awt.Graphics;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * A class representing a table. A table consists of a collection of rows.
 */
public class Table extends DrawableContainer<TableRow> {

    /**
     * Variable registering the default width for (new) each column in a table.
     */
    private final int defaultColumnWidth;

    /**
     * Variable registering the height of a row in this table.
     */
    private final int defaultRowHeight;

    /**
     * A list containing the width of each column in the table for a given header.
     * The sizes of the columnWidths and the headers lists must match at all times.
     * Not using a Map<Sting, Integer> because lists are ordered and a sets.keySet() method does not guarantee ordering.
     * Using lists then, provides the user with certainty regarding the order of columns in this table.
     */
    private final List<Integer> columnWidths = new ArrayList<>();

    /**
     * A list containing the headers of each column in the table.
     */
    private final List<String> headers = new ArrayList<>();

    /**
     * The ids used for the cells in this table.
     */
    private final List<Integer> columnIds;

    /**
     * Maps row id to the row corresponding to the id.
     */
    private final HashMap<Integer, TableRow> rows = new HashMap<>();

    /**
     * List of the observers that are alerted when the width of the cells of this table changes.
     */
    private final List<TableCellWidthChangeHandler> cellWidthHandlers = new ArrayList<>();

    /**
     * List of the observers that are alerted when the contents of this table changes.
     */
    private final List<TableChangeHandler> tableChangeHandlers = new ArrayList<>();

    /**
     * +     * List of the observers that are alerted when a row within this table is selected.
     * +
     */
    private final List<TableRowSelectedHandler> tableRowSelectedHandlers = new ArrayList<>();

    /**
     * Handler that deals with changes in this table.
     */
    private final TableChangeHandler tableChangeHandler = (event) -> tableChangeHandlers.forEach(h -> h.handleEvent(event));

    /**
     * Create a new table with the given dimensions, visibility state and headers.
     *
     * @param x           The x coordinate of the top left corner of the component relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param y           The y coordinate of the top left corner of the component.relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param visible     The visibility state of the new component.
     * @param headers     The headers for each column in the table.
     * @param columnWidth The default width of a cell.
     * @param rowHeight   The default height of a row.
     * @param columnIds   The ids to be used for the cells in each row.
     * @param parent      The component that contains this component or null if this component has no parent.
     * @effect The table is initialized as a drawable component with the given parameters.
     * | super(x, y, 2 * rowHeight, 0, visible, parent)
     * @effect The new object contains all the headers.
     * | setHeaders(headers, columnIds)
     */
    public Table(int x, int y, boolean visible, int columnWidth, int rowHeight, String[] headers, int[] columnIds, Component parent) throws IllegalArgumentException {
        super(x, y, rowHeight, 0, visible, parent);
        setHMargin(15);
        setVMargin(rowHeight);
        this.defaultColumnWidth = columnWidth;
        this.defaultRowHeight = rowHeight;
        this.columnIds = Arrays.stream(columnIds).boxed().collect(Collectors.toList());
        setHeaders(headers);
    }

    /**
     * Create a new visible table with the given dimensions and headers.
     *
     * @param x           The x coordinate of the top left corner of the component relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param y           The y coordinate of the top left corner of the component.relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param headers     The headers for each column in the table.
     * @param columnWidth The default width of a cell.
     * @param rowHeight   The default height of a row.
     * @param columnIds   The ids to be used for the cells in each row.
     * @param parent      The component that contains this component or null if this component has no parent.
     * @effect The table is initialized with the given parameters.
     * | this(x, y, true, columnWidth, rowHeight, headers, columnIds, parent)
     */
    public Table(int x, int y, int columnWidth, int rowHeight, String[] headers, int[] columnIds, Component parent) {
        this(x, y, true, columnWidth, rowHeight, headers, columnIds, parent);
    }

    /**
     * Create a new visible table with the given dimensions and headers.
     *
     * @param x           The x coordinate of the top left corner of the component relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param y           The y coordinate of the top left corner of the component.relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param headers     The headers for each column in the table.
     * @param columnWidth The default width of a cell.
     * @param rowHeight   The default height of a row.
     * @param parent      The component that contains this component or null if this component has no parent.
     * @effect The table is initialized with the given parameters.
     * | this(x, y, true, columnWidth, rowHeight, parent)
     */
    public Table(int x, int y, int columnWidth, int rowHeight, String[] headers, Component parent) {
        this(x, y, true, columnWidth, rowHeight, headers, IntStream.range(0, headers.length).toArray(), parent);
    }

    /**
     * Get the headers of this table.
     *
     * @return The headers of this table.
     */
    public String[] getHeaders() {
        return headers.toArray(new String[0]);
    }

    /**
     * Get the table's nth column header.
     *
     * @param index The index to get the header for.
     * @return The header for the given column.
     * @throws IndexOutOfBoundsException | index &lt; 0 || index &gt;= getNbColumns()
     */
    public String getHeaderAt(int index) {
        if (index < 0 || index >= getNbColumns())
            throw new IndexOutOfBoundsException();

        return headers.get(index);
    }

    /**
     * Check if this table contains a specific header.
     *
     * @param header The header to check for.
     * @return True if the headers of this table contain the given header.
     * | result = (
     * |   for some I in 0..(getNbHeaders() - 1)
     * |       getHeaderAt(I) == header
     * | )
     */
    public boolean hasAsHeader(String header) {
        return this.headers.contains(header);
    }

    /**
     * Set the new headers of the columns in this table.
     *
     * @param headers The headers to add to the column.
     * @param ids     The ids to be used for the columns corresponding to each of the headers.
     * @throws IllegalArgumentException Thrown when the sizes of both arguments are not equal.
     */
    public void setHeaders(String[] headers, int[] ids) {

        if (headers.length != ids.length)
            throw new IllegalArgumentException("The header and id arrays need to be of equal length");

        // Make a copy of the column widths,
        // if the new headers contain a header that already existed, it's width is kept.
        Map<String, Integer> oldWidths = new HashMap<>();
        IntStream.range(0, this.headers.size()).forEach(i -> oldWidths.put(this.headers.get(i), columnWidths.get(i)));

        this.headers.clear();
        columnWidths.clear();

        for (String header : headers) {
            this.headers.add(header);
            setColumnWidth(header, oldWidths.getOrDefault(header, this.defaultColumnWidth));
        }
        setColumnIds(ids);
        builtHeaderRow();
    }

    /**
     * Set the new headers of the columns in this table.
     *
     * @param headers The headers to add to the column.
     * @effect Calls the setHeaders(headers, cellIds) method with the cellIds a list of ints from [0,headers.size() - 1]
     */
    public void setHeaders(String[] headers) {
        int[] ids = IntStream.range(0, headers.length).toArray();
        setHeaders(headers, ids);
    }

    /**
     * Built the first row of a table, i.e. the row that contains the titles of each column.
     */
    private void builtHeaderRow() {
        Label[] labels = new Label[getNbColumns()];
        TableHeader headerRow = new TableHeader(getHMargin(), 0, defaultRowHeight, getHMargin(), this);

        for (int i = 0; i < getNbColumns(); i++) {
            labels[i] = new Label(getHeaderAt(i), headerRow);
        }
        headerRow.setCells(getColumnWidths(), labels, getColumnIds());

        // Attach a listener to the headerRow that responds to changes in the size of the columns.
        headerRow.addPropertyChangeListener(evt -> {
            List<Integer> oldWidths = Arrays.stream(getColumnWidths()).boxed().collect(Collectors.toList());
            int[] newColumnWidths = (int[]) evt.getNewValue();
            setColumnWidths(newColumnWidths);

            // Make a copy of the columnWidths so they can't be altered in subscribers.
            // Then alert any subscribers to the changes.
            List<Integer> newWidths = Arrays.stream(getColumnWidths()).boxed().collect(Collectors.toList());
            TableCellWidthChangeEvent evt2 = new TableCellWidthChangeEvent(oldWidths, newWidths);
            cellWidthHandlers.forEach(h -> h.handleEvent(evt2));
        });
        // Header should always be the first row in the table.
        children.add(0, headerRow);
        setHeight(getNbRows() * defaultRowHeight);
    }

    /**
     * Return the cell with the given row and cell id.
     *
     * @param rowId  The id of the row from which to retrieve the cell.
     * @param cellId The id of the cell to retrieve.
     * @return The drawable component at the specified index.
     */
    public DrawableComponent getCell(int rowId, int cellId) {
        return getRowWithId(rowId).getCell(cellId);
    }

    /**
     * Get the number of columns in this table.
     *
     * @return The number of columns in this table.
     */
    public int getNbColumns() {
        return headers.size();
    }

    /**
     * @return A list of the ids used for the cells on each row of the table.
     */
    public int[] getColumnIds() {
        return columnIds.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Set new column ids to be used when creating columns.
     *
     * @param ids The new ids.
     */
    public void setColumnIds(int[] ids) {
        if (ids.length != getNbColumns())
            throw new IllegalArgumentException("The number of ids needs to be equal to the number of columns.");

        columnIds.clear();
        Arrays.stream(ids).forEach(columnIds::add);
    }

    /**
     * Set the width for the column corresponding to the given header.
     *
     * @param header The header for which to set the width.
     * @param width  The width of the column corresponding to the given header.
     * @throws IllegalArgumentException Thrown when the table does not contain a row identified by the header.
     *                                  |!hasAsHeader(header)
     * @post The new width for the header is equal to the given width.
     * |new.getColumnWidth(header) == width
     */
    public void setColumnWidth(String header, int width) {

        if (!hasAsHeader(header))
            throw new IllegalArgumentException("The given header does not identify a column in this table");

        columnWidths.add(headers.indexOf(header), width);

        // Update the total width of this table to reflect the changes made in the column widths.
        setWidth(2 * getHMargin() + columnWidths.stream().mapToInt(Integer::intValue).sum());
    }

    /**
     * Set the widths of all the columns in this table.
     *
     * @param widths The new widths of the columns in this table.
     * @throws IllegalArgumentException Thrown when the length of the widths array doesn't correspond to the number of
     *                                  columns.
     *                                  | widths.length != getHeaders().size()
     */
    public void setColumnWidths(int[] widths) {
        if (widths.length != headers.size())
            throw new IllegalArgumentException("Length of the widths array does not equal the number of columns in this table");
        columnWidths.clear();
        Arrays.stream(widths).forEach(columnWidths::add);
        IntStream.range(0, getNbRows()).forEach(i -> getRowAt(i).setCellWidths(widths));
        setWidth(2 * getHMargin() + Arrays.stream(widths).sum());
        if (getParent() != null && getParent() instanceof Panel) ((Panel) getParent()).updateContentWidthAndHeight();
    }

    /**
     * @return A list containing the width of each column in this table.
     */
    public int[] getColumnWidths() {
        return columnWidths.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Add a new row to the table.
     *
     * @param components The components that make up the new row.
     * @param id         The id of the new table row.
     * @throws IllegalArgumentException Thrown if the components contain more elements than there are columns.
     */
    public void addRow(DrawableComponent[] components, int id) {

        if (components.length != getNbColumns())
            throw new IllegalArgumentException("The components array contains more elements than there are columns");

        int y = getNbRows() * defaultRowHeight;
        TableRow newRow = new TableRow(getHMargin(), y, defaultRowHeight, id, getColumnWidths(), components, getHMargin(), getColumnIds(), this);
        rows.put(id, newRow);

        // Respond to changes in the cells of the row.
        newRow.addTableChangeHandler(tableChangeHandler);
        addChild(newRow);
        setHeight(getNbRows() * this.defaultRowHeight);
        newRow.addTableRowSelectedHandler(evt -> tableRowSelectedHandlers.forEach(h -> h.handleEvent(evt)));

        setHeight(getNbRows() * defaultRowHeight);
        if (getParent() != null && getParent() instanceof Panel) ((Panel) getParent()).updateContentWidthAndHeight();
    }

    /**
     * Add a new row using the number of elements in the row table as ID.
     *
     * @param components The components that make up the new row.
     */
    public void addRow(DrawableComponent[] components) {
        addRow(components, getNbRows() - 1);
    }

    /**
     * Return the identifier of the last row in the table.
     *
     * @return The index of the last row in this table.
     */
    public int getLastRowIdentifier() {
        return getNbRows() - 1;
    }

    /**
     * Get the number of rows in this table.
     *
     * @return The number of rows in this table.
     */
    public int getNbRows() {
        return getNbChildren();
    }

    /**
     * Get the row at the given index.
     *
     * @param index The index for which to get the row.
     * @return The row at the specified index.
     */
    public TableRow getRowAt(int index) {
        return getChildAt(index);
    }

    /**
     * Get the row with the given id.
     *
     * @param id The id of the row to get.
     * @return The row with the specified id.
     */
    public TableRow getRowWithId(int id) {
        if (!rows.containsKey(id))
            throw new IllegalArgumentException("There is no row with the given id in this table");
        return rows.get(id);
    }

    /**
     * Remove the row with the given id.
     *
     * @param id The id for which to delete the row.
     */
    public void removeRow(int id) {

        TableRow toDelete = rows.get(id);
        if (toDelete == null) return;

        // Move each subsequent row up.
        IntStream.range(children.indexOf(toDelete), children.size()).mapToObj(children::get).forEach(TableRow::moveUp);

        rows.remove(id);
        children.remove(toDelete);
        setHeight(getHeight() - defaultRowHeight);
        if (getParent() != null && getParent() instanceof Panel)
            ((Panel) getParent()).updateContentWidthAndHeight();
    }

    /**
     * Draw the table.
     *
     * @param g The graphics object to paint on.
     */
    @Override
    public void paint(Graphics g) {
        paintHeader(g);
        super.paint(g);
        paintGrid(g);
    }

    /**
     * Paint the header bar of this table.
     *
     * @param g The graphics object om which to paint.
     */
    private void paintHeader(Graphics g) {
        g.setColor(getColor("Border"));
        g.drawRect(getDrawX() + getHMargin(), getDrawY(), getWidth() - 2 * getHMargin(), defaultRowHeight);

        g.setColor(getColor("Accent"));
        g.fillRect(getDrawX() + getHMargin(), getDrawY(), getWidth() - 2 * getHMargin(), defaultRowHeight);

        g.setColor(getColor("Standard"));
    }

    /**
     * Paint the grid of the table, i.e. the lines between each row and each column.
     *
     * @param g The graphics object om which to paint.
     */
    private void paintGrid(Graphics g) {

        if (getNbRows() == 0) return;

        g.setColor(getColor("Border"));

        // Draw vertical lines.
        int x = getDrawX() + getHMargin();
        for (int i = 0; i < getNbColumns(); i++) {
            g.drawLine(x, getDrawY(), x, getDrawY() + getHeight());
            x += this.columnWidths.get(i);
        }
        g.drawLine(x, getDrawY(), x, getDrawY() + getHeight());

        // Draw horizontal lines
        int y = getDrawY();
        for (int i = 0; i <= getNbRows(); i++) {
            g.drawLine(getDrawX() + getHMargin(), y, getDrawX() + getWidth() - getHMargin(), y);
            y += defaultRowHeight;
        }

        g.setColor(getColor("Standard"));
    }

    /**
     * Empty this table of all rows.
     */
    public void empty() {
        children.clear();
        rows.clear();
        setHeight(0);
    }

    /**
     * Empty this table of all rows but keep the headers.
     */
    public void emptyRows() {
        if (children.size() >= 2)
            children.subList(1, children.size()).clear();
        rows.clear();
        setHeight(defaultRowHeight); // Headers are kept, so the table contains 1 row.
    }

    /**
     * Deal with a user pressing a key.
     *
     * @param id      The type of key event.
     * @param keyCode The number of key that was pressed.
     * @param keyChar The character that was typed.
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar) {

        super.handleKeyEvent(id, keyCode, keyChar);

        // Delete the selected row, if there is such a row.
        if (keyCode == 127) {
            Optional<TableRow> tableRowOpt = children.stream().filter(TableRow::isSelectedWithoutCells).findFirst();

            if (!tableRowOpt.isPresent())
                return;

            TableRow tableRow = tableRowOpt.get();
            TableChangeEvent event = new TableChangeEvent(tableRow.getId(), null, null);
            tableChangeHandlers.forEach(h -> h.handleEvent(event));
            removeRow(tableRow.getId());
        }
    }

    /**
     * Add a new observer that is alerted when the widths of the columns in this table change.
     *
     * @param observer The observer to have.
     */
    public void addCellWidthsHandler(TableCellWidthChangeHandler observer) {
        cellWidthHandlers.add(observer);
    }

    /**
     * Remove an observer that is no longer alerted when the widths of the columns in this table change.
     *
     * @param observer The observer to remove.
     */
    public void removeCellWidthsHandler(TableCellWidthChangeHandler observer) {
        cellWidthHandlers.remove(observer);
    }

    /**
     * Add an observer that is alerted when the content of this cell changes.
     *
     * @param handler The handler to add.
     */
    public void addTableChangeHandler(TableChangeHandler handler) {
        tableChangeHandlers.add(handler);
    }

    /**
     * Remove an observer that is no longer alerted when the content of this cell changes.
     *
     * @param handler The handler to remove.
     */
    public void removeTableChangeHandler(TableChangeHandler handler) {
        tableChangeHandlers.remove(handler);
    }

    /**
     * Add a handler that is alerted when a row is selected in this table.
     *
     * @param handler The handler to be alerted when a row is selected in this table.
     */
    public void addTableRowSelectedHandler(TableRowSelectedHandler handler) {
        tableRowSelectedHandlers.add(handler);
    }

    /**
     * Remove a handler that will no longer be alerted when a row is selected in this table.
     *
     * @param handler The handler that will no longer be alerted when a row is selected in this table.
     */
    public void removeTableRowSelectedHandler(TableRowSelectedHandler handler) {
        tableRowSelectedHandlers.remove(handler);
    }
}
