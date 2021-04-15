package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.event.TableRowSelectedEvent;

import com.swopteam07.tablr.UI.handlers.TableCellChangeHandler;
import com.swopteam07.tablr.UI.handlers.TableChangeHandler;
import com.swopteam07.tablr.UI.event.TableChangeEvent;
import com.swopteam07.tablr.UI.handlers.TableRowSelectedHandler;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.IntStream;

/**
 * A class representing a row in a table.
 */
class TableRow extends Container<TableCell> {

    /**
     * The width of each cell in this table.
     */
    private List<Integer> cellWidths;

    /**
     * A Maps that maps the id of a cell to the cell.
     */
    protected Map<Integer, TableCell> cells = new HashMap<>();

    /**
     * True if cells in this row are selected.
     */
    private boolean cellsSelected = false;

    /**
     * The color used to indicate to the user that the row is selected.
     */
    private Color rowSelectedColor = getColor("Selected");

    /**
     * The id of this row.
     */
    private final int id;

    /**
     * Handlers that are fired when a cell in this row changes.
     */
    private final List<TableChangeHandler> tableChangeHandlers = new ArrayList<>();

    /**
     * +     * List of the observers that are alerted when this row is selected.
     * +
     */
    private final List<TableRowSelectedHandler> tableRowSelectedHandlers = new ArrayList<>();

    /**
     * /**
     * Handler that react when the content of a cell in this row changes.
     */
    private TableCellChangeHandler tableCellChangeHandler = (event) -> {
        TableChangeEvent event2 = new TableChangeEvent(getId(), event.getCellId(), event.getChangedComponent());
        tableChangeHandlers.forEach(h -> h.handleEvent(event2));
    };

    /**
     * Create a new table row with the given dimension and id.
     *
     * @param x          The x coordinate of the top left corner of the component relative to the parent component if it
     *                   exists or the window if it doesn't.
     * @param y          The y coordinate of the top left corner of the component.relative to the parent component if it
     *                   exists or the window if it doesn't.
     * @param height     The height of the component.
     * @param id         The id of this table row.
     * @param cellWidths The width of each cell in this row.
     * @param components The components that make up the cells of this table row.
     * @param hMargin    The margin the table containing this row added to the left and right of this row.
     * @param ids        The ids that uniquely identify the cells within this row.
     * @param parent     The component that contains this component or null if this component has no parent.
     * @effect The table row is initialized as a component with the given
     * parameters. | super(x, y, height, 0)
     * @effect The components are added to the row. | addCells(cellWidths,
     * components)
     * @post The id of the new table row will be equal to the given id. |
     * new.getId() == id
     */
    public TableRow(int x, int y, int height, int id, int[] cellWidths, DrawableComponent[] components, int hMargin, int[] ids, Table parent) {
        super(x, y, height, Arrays.stream(cellWidths).sum(), parent);
        this.id = id;
        this.cellWidths = new ArrayList<>();
        setHMargin(hMargin);
        setCells(cellWidths, components, ids);
    }

    /**
     * Create a new table row with the given dimension and id.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param id      The id of this table row.
     * @param hMargin The margin the table containing this row added to the left and right of this row.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The table row is initialized as a component with the given
     * parameters. | super(x, y, height, 0)
     * @effect The components are added to the row. | addCells(cellWidths,
     * components)
     * @post The id of the new table row will be equal to the given id. |
     * new.getId() == id
     */
    public TableRow(int x, int y, int height, int id, int hMargin, Table parent) {
        super(x, y, height, 0, parent);
        this.id = id;
        this.cellWidths = new ArrayList<>();
        setHMargin(hMargin);
    }

    /**
     * Get the id of this table row.
     *
     * @return The id of this row.
     */
    public int getId() {
        return id;
    }


    /**
     * Get the contents of a cell in this row.
     *
     * @param cellId The id of the cell for which to get content.
     * @return The component that is contained in the specified cell of this row.
     */
    public DrawableComponent getCell(int cellId) throws IndexOutOfBoundsException {
        return cells.get(cellId).getContent();
    }

    /**
     * Add a new cell to the table.
     *
     * @param cellWidth The width of the new cell.
     * @param content   The contents of the new cell.
     * @param id        The id of the new cell.
     */
    private void addCell(int cellWidth, DrawableComponent content, int id) {
        TableCell newCell = new TableCell(getWidth(), 0, getHeight(), cellWidth, id, content, this);
        cells.put(id, newCell);
        children.add(newCell);
        newCell.addTableCellChangeHandler(tableCellChangeHandler);
        setWidth(getWidth() + cellWidth);
        cellWidths.add(cellWidth);
    }

    /**
     * Set the cells of this tableRow.
     *
     * @param cellWidths The width of the new cells.
     * @param contents   The contents of the new cells.
     * @param ids        The ids of the cells in this row.
     * @throws IllegalArgumentException Thrown if the arrays of cellWidths and components don't
     *                                  contain an equal number of entries.
     */
    public void setCells(int[] cellWidths, DrawableComponent[] contents, int[] ids) {
        if (cellWidths.length != contents.length)
            throw new IllegalArgumentException("The number of entries in cellWidths and components needs to be equal.");

        if (ids.length != contents.length)
            throw new IllegalArgumentException("The number of ids needs to be equal to the number of components.");

        children.clear();
        cells.clear();

        setWidth(0); // Initial width of the row, will be incremented in addCell()
        IntStream.range(0, cellWidths.length).forEach(i -> addCell(cellWidths[i], contents[i], ids[i]));
    }

    /**
     * The number of cells contained in this table row.
     *
     * @return The number of cells in this row.
     */
    public int getNbCells() {
        return getNbChildren();
    }

    /**
     * Set the width of the cells in this row.
     *
     * @param cellWidths The new widths of each cell in this table.
     */
    protected void setCellWidths(int[] cellWidths) {
        if (cellWidths.length != getNbCells())
            throw new IllegalArgumentException("De number of cellWidths needs to be equal to the number of cells.");

        this.cellWidths.clear();
        Arrays.stream(cellWidths).forEach(c -> this.cellWidths.add(c));
        setWidth(Arrays.stream(cellWidths).sum() + 2 * getHMargin());

        // Update the width and x coordinate of each cell.
        int x = 0;
        for (int i = 0; i < getNbCells(); i++) {
            children.get(i).setCellWidth(cellWidths[i], x);
            children.get(i).setX(x);
            x += cellWidths[i];
        }
    }

    /**
     * Get the cells in this table row.
     *
     * @return Array of the widths of the columns in this row.
     */
    protected int[] getCellWidths() {
        return cellWidths.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Move the row up 1 row in the table.
     */
    public void moveUp() {
        setY(getY() - getHeight());
    }

    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);
        cellsSelected = x > getDrawX() + getHMargin();
        if (isSelectedWithoutCells() && tableRowSelectedHandlers != null) {
            TableRowSelectedEvent evt = new TableRowSelectedEvent(this.id);
            tableRowSelectedHandlers.forEach(h -> h.handleEvent(evt));
        }
    }

    /**
     * @return True if no cells are selected within this row but the row itself is selected.
     */
    protected boolean isSelectedWithoutCells() {
        return isSelected() && !cellsSelected;
    }

    /**
     * Set the color that is used to indicate that the row is selected.
     *
     * @param rowSelectedColor The new color.
     */
    protected void setRowSelectedColor(Color rowSelectedColor) {
        this.rowSelectedColor = rowSelectedColor;
    }

    /**
     * Paint the row of the table.
     *
     * @param g The graphics object to paint on.
     */
    @Override
    public void paint(Graphics g) {
        // Color the background of the row to indicate it is selected.
        if (isSelectedWithoutCells()) {
            g.setColor(rowSelectedColor);
            g.fillRect(getDrawX(), getDrawY(), getWidth(), getHeight());
            g.setColor(getColor("Standard"));
        }
        super.paint(g);
    }

    /**
     * Add a listener that will be alerted when the content of the row changes.
     *
     * @param handler The handler to add.
     */
    void addTableChangeHandler(TableChangeHandler handler) {
        tableChangeHandlers.add(handler);
    }

    /**
     * Remove a listener that will no longer be alerted when the content of the row changes.
     *
     * @param handler The handler to add.
     */
    void removeTableChangeHandler(TableChangeHandler handler) {
        tableChangeHandlers.remove(handler);
    }

    /**
     * Add a handler that is alerted when this row is selected.
     *
     * @param handler The handler to be alerted when this row is selected.
     */
    public void addTableRowSelectedHandler(TableRowSelectedHandler handler) {
        tableRowSelectedHandlers.add(handler);
    }

    /**
     * Remove a handler that will no longer be alerted when this row is selected.
     *
     * @param handler The handler that will no longer be alerted when this row is selected.
     */
    public void removeTableRowSelectedHandler(TableRowSelectedHandler handler) {
        tableRowSelectedHandlers.remove(handler);
    }
}
