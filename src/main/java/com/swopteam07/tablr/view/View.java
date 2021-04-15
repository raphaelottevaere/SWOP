package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.Component;
import com.swopteam07.tablr.UI.Table;
import com.swopteam07.tablr.UI.WindowPanel;
import com.swopteam07.tablr.controller.Controller;

import java.awt.*;

/**
 * Class that collects all the common attributes and methods for views such as TableId Design Mode and TableId Row Mode.
 *
 * @author Sebastiaan Henau
 */
public abstract class View {

    /**
     * The root component of this view. All other components are contained within.
     */
    protected final WindowPanel root;

    /**
     * A reference to the controller.
     */
    protected final Controller controller;

    /**
     * Indicates whether or not the view is currently in a valid state, i.e. there are no illegal values entered.
     */
    protected boolean validState = true;

    /**
     * The last component that was selected by a click in this view.
     */
    protected Component lastSelectedComponent;

    /**
     * Indicates whether the previous key that was pressed was control.
     */
    private boolean ctrlPressed = false;

    public View(WindowPanel root, Controller controller) {
        this.root = root;
        this.controller = controller;
    }

    /**
     * Get the root component of this view.
     * @return The root component of this view.
     */
    public final WindowPanel getRoot() {
        return this.root;
    }

    /**
     * Deal with a user clicking beneath the component.
     *
     * @param y          The y coordinate of the point where the user clicked.
     * @param clickCount The number of times the user clicked beneath view.
     */
    public abstract void clickedBeneathView(int y, int clickCount);

    /**
     * Deal with a user pressing escape.
     */
    public void escapePressed() {
    }

    /**
     * Deal with a user pressing enter.
     */
    public void enterPressed() {
    }

    /**
     * Reacts to the control + enter key combination.
     */
    public void ctrlEnterPressed() {
    }

    /**
     * Reacts to the control + F key combination.
     */
    public void ctrlFPressed() {
    }

    /**
     * Reacts to the pageUp key combination.
     */
    public void pageUpPressed(){

    }
    /**
     * Reacts to the pagedown key combination.
     */
    public void pageDownPressed(){

    }

    /**
     * Reacts to the control + N key combination.
     */
    public void ctrlNPressed(){

    }

    /**
     * Reacts to the control + N key combination.
     */
    public void ctrlDPressed(){

    }

    /**
     * Check if the view is in an illegal state, i.e. if any illegal values have been entered by the user.
	 * @return True if this view only contains valid literal, false otherwise.
     */
    public boolean hasValidState() {
        return validState;
    }

    /**
     * Set new widths for the tables contained in this view.
     *
     * @param widths The new widths of the columns of this table.
     */
    public abstract void updateCellWidths(int[] widths);

    /**
     * Blocks or unblock all interaction with anything but the cell given in the TableChangeEvent.
     * Blocks when isInIllegalState() and unblocks when ! isInIllegalState().
     *
     * @param rowId  The id of the row which contains the cell that can still be edited.
     * @param cellId The id of the cell that can still be edited.
     * @param table  Reference to the table on which to block a single cell.
     */
    public void blockUnblockInteraction(int rowId, int cellId, Table table) {
        root.setCanBeSelected(validState);
        root.setAllowsUnselect(validState);
        table.getCell(rowId, cellId).setValid(validState);
    }

    /**
     * Paint this component.
     *
     * @param g The graphics object on which to paint.
     */
    public final void paint(Graphics g) {
        root.paint(g);
    }

    /**
     * React to user mouse input.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate on which the mouse event occurred.
     * @param y          The y coordinate on which the mouse event occurred.
     * @param clickCount The number of times the user clicked.
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        root.handleClickEvent(id, x, y, clickCount);
        if (id == 500)
            clickedBeneathView(y, clickCount);

        if (contains(x, y) && lastSelectedComponent != null && !root.getSelectedComponent().equals(lastSelectedComponent))
            selectedDifferentComponent();

        if (contains(x, y)) lastSelectedComponent = root.getSelectedComponent();

        // Mark the root element as active (selected) this ensures that there is always a view selected.
        getRoot().setSelected(true);
    }

    /**
     * Action that should be taken when the user has selected a different part of the root view.
     */
    public abstract void selectedDifferentComponent();

    /**
     * React to user pressing keys on the keyboard.
     *
     * @param id      The type of key event.
     * @param keyCode The code of the key that was pressed.
     * @param keyChar A character interpretation of the key that was pressed.
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar) {
        root.handleKeyEvent(id, keyCode, keyChar);
        if (keyCode == 27)
            escapePressed();
        else if (keyCode == 10 && ctrlPressed)
            ctrlEnterPressed();
        else if (keyCode == 10)
            enterPressed();
        else if (keyCode==70 && ctrlPressed)
            ctrlFPressed();
        else if (keyCode==33)
            pageUpPressed();
        else if (keyCode==34)
            pageDownPressed();
        else if (keyCode==78 && ctrlPressed)
            ctrlNPressed();
        else if (keyCode==68 && ctrlPressed)
            ctrlDPressed();

        ctrlPressed = keyCode == 17;
    }
    
    /**
     * @return 	The id of the table that this view allows the user to edit.
     * 			Or null if the View doesn't allow a table to be editted.
     */
    public abstract Integer getTableId();

    /**
     * Check if the view contains the x and y coordinate.
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the view contains the coordinate, false otherwise.
     */
    public boolean contains(int x, int y) {
        if (!root.isVisible())
            return false;
        return root.contains(x, y);
    }
}
