package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.event.TableCellChangeEvent;
import com.swopteam07.tablr.UI.handlers.TableCellChangeHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a single cell in a table.
 */
class TableCell extends Container<DrawableComponent> {

    /**
     * The id that uniquely identifies this cell within it's row.
     */
    private final int id;

    /**
     * Margin used around the contents of the cell.
     */
    private final int cellMargin = 2;

    /**
     * Observers that will be alerted when the content of the cell changes.
     */
    private final List<TableCellChangeHandler> tableCellChangeHandlers = new ArrayList<>();

    /**
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param id      The id of that uniquely identifies this cell within it's row.
     * @param content The contents of this cell.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The content of this cell is set using the setContent() method.
     * | setContent(content)
     */
    public TableCell(int x, int y, int height, int width, int id, DrawableComponent content, Component parent) {
        super(x, y, height, width, true, parent);
        this.id = id;
        setContent(content);
        setCellWidth(width, x);
    }

    /**
     * Set the new content for this cell.
     *
     * @param content The new content for this cell.
     */
    public void setContent(DrawableComponent content) {
        content.setParent(this);
        content.setX(cellMargin);
        content.setY(cellMargin);
        content.setHeight(getHeight() - 2 * cellMargin);
        content.setWidth(getWidth() - 2 * cellMargin);
        children.add(0, content);
        content.addPropertyChangeListener(evt -> {
            TableCellChangeEvent evt2 = new TableCellChangeEvent(getContent(), getId());
            tableCellChangeHandlers.forEach(h -> h.handleEvent(evt2));
        });
    }

    /**
     * @return The content of the cell.
     */
    public DrawableComponent getContent() {
        return children.get(0);
    }

    /**
     * Set the new width for this cell.
     *
     * @param cellWidth The new width.
     * @param x         The x coordinate of the left top corner of this cell relative to the top left corner of the table row.
     */
    public void setCellWidth(int cellWidth, int x) {
        setWidth(cellWidth);
        getContent().setWidth(cellWidth - 2 * cellMargin);
        getContent().setX(cellMargin);
    }

    /**
     * @return The id that uniquely identifies this cell within it's row.
     */
    public int getId() {
        return id;
    }

    /**
     * Add a handler that will be alerted when the content of the cell changes.
     *
     * @param handler The handler to add.
     */
    void addTableCellChangeHandler(TableCellChangeHandler handler) {
        tableCellChangeHandlers.add(handler);
    }

    /**
     * Remove a handler that will no longer be alerted when the content of the cell changes.
     *
     * @param handler The handler to remove.
     */
    void removeTableCellChangeHandler(TableCellChangeHandler handler) {
        tableCellChangeHandlers.remove(handler);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Color tmp = g.getColor();
        g.setColor(Color.RED);
        g.drawRect(getDrawX(), getDrawY(), getWidth(), getHeight());


        g.setColor(tmp);
    }
}
