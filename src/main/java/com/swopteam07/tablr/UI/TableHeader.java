package com.swopteam07.tablr.UI;

/**
 * A class representing the first row in a table.
 */
public class TableHeader extends TableRow {

    /**
     * Variable indicating whether or not the table is currently being resized.
     */
    private boolean resizing;

    /**
     * The index of the cell which is being resized.
     */
    private int resizingCell;

    /**
     * Create a new table header with the given dimension and id.
     *
     * @param x          The x coordinate of the top left corner of the component relative to the parent component if it
     *                   exists or the window if it doesn't.
     * @param y          The y coordinate of the top left corner of the component.relative to the parent component if it
     *                   exists or the window if it doesn't.
     * @param height     The height of the component.
     * @param cellWidths The width of each cell in this row.
     * @param components The components that make up the cells of this table row.
     * @param hMargin    The margin the table containing this row added to the left and right of this row.
     * @param ids        The ids that uniquely identify the cells within this row.
     * @param parent     The component that contains this component or null if this component has no parent.
     * @effect The table header is initialized as a table row with the given parameters.
     * | super(x, y, height, 0, cellWidth, components, hMargin, ids)
     */
    public TableHeader(int x, int y, int height, int[] cellWidths, DrawableComponent[] components, int hMargin, int[] ids, Table parent) {
        super(x, y, height, 0, cellWidths, components, hMargin, ids, parent);
        setRowSelectedColor(getColor("Accent"));
        resizing = false;
    }

    /**
     * Create a new table header with the given dimension and id.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param hMargin The margin the table containing this row added to the left and right of this row.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The table header is initialized as a table row with the given parameters.
     * | super(x, y, height, 0, cellWidth, components, hMargin, ids)
     */
    public TableHeader(int x, int y, int height, int hMargin, Table parent) {
        super(x, y, height, 0, hMargin, parent);
        setRowSelectedColor(getColor("Accent"));
        resizing = false;
    }

    /**
     * Check if a x coordinate is located on (with a certain error) the right border of a cell.
     *
     * @param x    The x coordinate.
     * @param cell The cell for which to check.
     * @return True if the coordinate is located on the right border of the cell.
     * | (x >= cell.getDrawX() + cell.getWidth() - epison) || (x <= cell.getX() + cell.getWidth() + epsilon)
     */
    private boolean onRightBorder(int x, DrawableComponent cell) {
        int border = cell.getDrawX() + cell.getWidth();
        return x >= border - getHMargin() && x <= border + getHMargin();
    }

    /**
     * Increase or decrease the size of the cells within this table.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);

        // 506 == drag event
        if (id == 506) {
            // Enable resizing if needed.
            if (!resizing) {
                for (Integer cellId : cells.keySet()) {
                    if (onRightBorder(x, cells.get(cellId).getContent())) {
                        resizing = true;
                        resizingCell = cellId;
                        break;
                    }
                }
            }

            // If resizing register the new width of the column.
            if (resizing) {
                // Ensure that each column has a minimum width of 20.
                int leftBorder = children.get(resizingCell).getDrawX();
                int newWidth = x > leftBorder + 20 ? x - leftBorder : 20;

                int[] newCellWidths = getCellWidths();
                newCellWidths[resizingCell] = newWidth;
                support.firePropertyChange("cellWidths", getCellWidths(), newCellWidths);
            }
        } else {
            resizing = false;
        }
    }
}
