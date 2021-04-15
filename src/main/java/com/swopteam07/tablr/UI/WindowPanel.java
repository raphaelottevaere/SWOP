package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.handlers.WindowPanelClosedHandler;
import com.swopteam07.tablr.UI.event.WindowPanelClosedEvent;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A draggable panel that contains a close button.
 */
public class WindowPanel extends Panel {

    /**
     * The height of the header bar, i.e. titlebar of the window panel.
     */
    private int headerBarHeight = 30;

    /**
     * The margin used on the horizontal axis for the close button.
     */
    private int closeButtonMarginX = 10;

    /**
     * The length of 1 size of the square that contains the close button.
     */
    private int closeButtonLength = 15;

    /**
     * The margin used on the vertical axis for the close button.
     */
    private int closeButtonMarginY = 5;

    /**
     * The minimal width for this panel.
     */
    private final int minWidth = 100;

    /**
     * The minimal height for this panel.
     */
    private final int minHeight = 100;

    /**
     * The title of this panel.
     */
    private final Label title;

    /**
     * The listeners that should be alerted when the panel is closed.
     */
    private final List<WindowPanelClosedHandler> windowPanelCloseHandlers = new ArrayList<>();

    /**
     * The x coordinate within this panel where the last mouse event occurred.
     */
    private int xClicked;

    /**
     * The y coordinate within this panel where the last mouse event occurred.
     */
    private int yClicked;

    /**
     * Enum containing the element that is currently being moved.
     * This is used to ensure that, for instance, the height of the window doesn't increase when dragging the window
     * around.
     */
    private enum Move {
        WINDOW,
        TOP_BORDER,
        BOTTOM_BORDER,
        LEFT_BORDER,
        RIGHT_BORDER,
        TOP_LEFT_CORNER,
        TOP_RIGHT_CORNER,
        BOTTOM_LEFT_CORNER,
        BOTTOM_RIGHT_CORNER
    }

    /**
     * Keeps track of what is currently being moved around.
     * Is reset every time a click event is registered that isn't a drag event.
     */
    private Move currentMove;

    /**
     * Create a new window panel.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The window panel is initialized as a panel wight the specified parameters.
     * | super(x, y, height, width, visible, parent)
     */
    public WindowPanel(int x, int y, int height, int width, boolean visible, Component parent) {
        super(x, y, height, width, visible, parent);
        int availableLabelSpace = width - closeButtonMarginX - closeButtonLength;

        // The title label is not added to children because clicks shouldn't propagate to the label,
        // any clicks on the label should be registered as clicks on the
        title = new Label(4, 2, headerBarHeight, availableLabelSpace, "", this);
        title.setMoveWhenScrolling(false);

        // The margin is larger than 0 to ensure that borders can be dragged without much trouble.
        setHMargin(15);
        setVMargin(15);
    }

    /**
     * Create a new visible window panel.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The window panel is initialized with true for the visible parameter.
     * | this(x, y, height, width, true, parent)
     */
    public WindowPanel(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Set the new title for this window panel.
     *
     * @param title The new title.
     * @post The new title is equal to the specified title.
     * | new.getTitle().equals(title)
     */
    public void setTitle(String title) {
        if (title == null)
            throw new IllegalArgumentException("The title cannot be null, use an empty string instead.");
        this.title.setText(title);
    }

    /**
     * @return The title of this window panel.
     */
    public String getTitle() {
        return title.getText();
    }

    /**
     * Add a vertical scrollbar to the component.
     */
    @Override
    public void enableVerticalScrollbar() {
        super.enableVerticalScrollbar();
        vertical.setHeight(getHeight() - headerBarHeight);
        vertical.setY(headerBarHeight);
    }


    /**
     * Check if the specified coordinate pair is in the close button.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the pair is in the close button.
     */
    private boolean isInCloseButton(int x, int y) {
        int rightX = getDrawX() + getWidth() - closeButtonMarginX - closeButtonLength;
        int leftX = rightX + closeButtonLength;
        int top = getDrawY() + closeButtonMarginY;
        int bottom = top + closeButtonLength;
        return x >= rightX && x <= leftX && y >= top && y <= bottom;
    }

    /**
     * Check if the given coordinate pair is located in the title bar.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the coordinate is in the title bar and not in the close button.
     */
    private boolean isInTitleBar(int x, int y) {
        return y >= getDrawY() && y <= getDrawY() + headerBarHeight && !isInCloseButton(x, y);
    }

    /**
     * Check if a given x coordinate is located on the left border of the window panel.
     *
     * @param x The x coordinate for which to check.
     * @return True if the coordinate is located on the left border, false otherwise.
     */
    private boolean isInLeftBorder(int x) {
        return getDrawX() <= x && getDrawX() + getHMargin() >= x;
    }

    /**
     * Check if a given x coordinate is located on the right border of the window panel.
     *
     * @param x The x coordinate for which to check.
     * @return True if the coordinate is located on the right border, false otherwise.
     */
    private boolean isInRightBorder(int x) {
        return getDrawX() + getWidth() < x && getDrawX() + getWidth() + getHMargin() >= x;
    }

    /**
     * Check if a given y coordinate is located on the top border of the window panel.
     *
     * @param y The y coordinate for which to check.
     * @return True if the coordinate is located on the top border, false otherwise.
     */
    private boolean isInTopBorder(int y) {
        return getDrawY() < y && getDrawY() + getHMargin() >= y;
    }

    /**
     * Check if a given y coordinate is located on the bottom border of the panel.
     *
     * @param y The y coordinate for which to check.
     * @return True if the coordinate is located on the bottom border, false otherwise.
     */
    private boolean isInBottomBorder(int y) {
        return getDrawY() + getHeight() < y && getDrawY() + getHeight() + getHMargin() >= y;
    }

    /**
     * Check if whether or not this containers propagates mouse/key events to children.
     *
     * @return True if neither the vertical or horizontal scroll button is being moved, false otherwise.
     */
    @Override
    protected boolean allowsPropagationToChildren() {
        return !vertical.isCurrentlyScrolling() && !horizontal.isCurrentlyScrolling() && currentMove == null;
    }

    /**
     * React to a user clicking on the window panel.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);
        if (isInCloseButton(x, y) && id == 500) { // 500 is a mouse click.
            // In case windowPanelCloseHandlers aren't attached or users don't properly remove references to the instance.
            setVisible(false);
            WindowPanelClosedEvent evt = new WindowPanelClosedEvent(this);
            windowPanelCloseHandlers.forEach(observers -> observers.handleEvent(evt));
            return; // Prevent deeply nested conditional structures.
        }

        if (id != 506) currentMove = null;
        else if (!vertical.isCurrentlyScrolling() && !horizontal.isCurrentlyScrolling())
            handleDragEvent(x, y);

        yClicked = y;
        xClicked = x;
    }

    /**
     * Determine which kind of drag event happened and which actions to take to handle it.
     *
     * @param x The x coordinate the user clicked on.
     * @param y The y coordinate the user clicked on.
     */
    private void handleDragEvent(int x, int y) {
        // Select the correct drag event if it isn't set yet.
        if (currentMove == null) {
            if (isInTitleBar(x, y)) currentMove = Move.WINDOW;
            else if (isInLeftBorder(x) && isInTopBorder(y)) currentMove = Move.TOP_LEFT_CORNER;
            else if (isInLeftBorder(x) && isInBottomBorder(y)) currentMove = Move.BOTTOM_LEFT_CORNER;
            else if (isInRightBorder(x) && isInTopBorder(y)) currentMove = Move.TOP_RIGHT_CORNER;
            else if (isInRightBorder(x) && isInBottomBorder(y)) currentMove = Move.BOTTOM_RIGHT_CORNER;
            else if (isInLeftBorder(x)) currentMove = Move.LEFT_BORDER;
            else if (isInRightBorder(x)) currentMove = Move.RIGHT_BORDER;
            else if (isInTopBorder(y)) currentMove = Move.TOP_BORDER;
            else if (isInBottomBorder(y)) currentMove = Move.BOTTOM_BORDER;
        }

        // React to a drag event with the appropriate resize or move functionality.
        if (currentMove == Move.WINDOW) {
            moveWindowPanel(x, y);
            return;
        }

        if (currentMove == Move.TOP_RIGHT_CORNER || currentMove == Move.TOP_LEFT_CORNER || currentMove == Move.TOP_BORDER)
            resizeTop(y);

        if (currentMove == Move.TOP_LEFT_CORNER || currentMove == Move.LEFT_BORDER || currentMove == Move.BOTTOM_LEFT_CORNER)
            resizeLeft(x);

        if (currentMove == Move.BOTTOM_LEFT_CORNER || currentMove == Move.BOTTOM_RIGHT_CORNER || currentMove == Move.BOTTOM_BORDER)
            resizeBottom(y);

        if (currentMove == Move.RIGHT_BORDER || currentMove == Move.TOP_RIGHT_CORNER || currentMove == Move.BOTTOM_RIGHT_CORNER)
            resizeRight(x);
    }

    /**
     * Move the window panel based on the parameters and the last x,y pair the user clicked on.
     *
     * @param x The x coordinate the user dragged his cursor to.
     * @param y The y coordinate the user dragged his cursor to.
     */
    private void moveWindowPanel(int x, int y) {
        int xDiff = x - xClicked;
        int yDiff = y - yClicked;
        int xTranslation = getX() + xDiff > 0 ? xDiff : 0;
        int yTranslation = getY() + yDiff > 0 ? yDiff : 0;
        translateX(xTranslation);
        translateY(yTranslation);
        if (verticalEnabled) vertical.placeScrollElement();
        if (horizontalEnabled) horizontal.placeScrollElement();
    }

    /**
     * Increase or decrease the y coordinate and the height of this panel.
     *
     * @param y The y coordinate that the user clicked on.
     */
    private void resizeTop(int y) {
        int yDiff = y - yClicked;
        int newHeight = getHeight() + (-1 * yDiff);

        // Ensure that the top and bottom borders don't cross and that the window doesn't move above
        // the top of the window.
        if (newHeight < minHeight || getY() + yDiff < 0) return;

        // Update the y coordinate for this panel.
        translateY(yDiff);
        setHeight(newHeight);
        horizontal.translateY(-1 * yDiff);
        vertical.setHeight(newHeight - headerBarHeight);
    }

    /**
     * Increase or decrease the width of this component.
     *
     * @param x The x coordinate the user clicked on.
     */
    private void resizeRight(int x) {
        int xDiff = x - xClicked;
        if (getWidth() + xDiff < minWidth) return;
        setWidth(getWidth() + xDiff);
        vertical.translateX(xDiff);
        horizontal.setWidth(horizontal.getWidth() + xDiff);
    }

    /**
     * Increase or decrease the height of this component.
     *
     * @param y The y coordinate the user clicked on.
     */
    private void resizeBottom(int y) {
        int yDiff = y - yClicked;
        if (getHeight() + yDiff < minWidth) return;
        setHeight(getHeight() + yDiff);
        horizontal.translateY(yDiff);
        vertical.setHeight(vertical.getHeight() + yDiff);
    }

    /**
     * Increase or decrease the x coordinate and width of this component.
     *
     * @param x The x coordinate that the user clicked on.
     */
    private void resizeLeft(int x) {
        int xDiff = x - xClicked;
        int newX = getX() + xDiff;
        int newWidth = getWidth() + (-1 * xDiff);

        // Ensure that the borders cannot cross
        if (newWidth < minWidth || newX < 0) return;

        setWidth(newWidth);
        translateX(xDiff);
        vertical.translateX(-1 * xDiff);
        horizontal.setWidth(newWidth - (verticalEnabled ? getScrollBarSize() : 0));
    }

    /**
     * Draw the header bar of this window panel. A header bar contains a colored background,
     * a title and a close button.
     *
     * @param g The graphics object to paint on.
     */
    private void paintHeaderBar(Graphics g) {
        // Draw the colored background of the header bar.
        g.setColor(getColor("Accent"));
        g.fillRect(getDrawX(), getDrawY(), getWidth(), headerBarHeight);
        g.setColor(getColor("Standard"));

        // Draw the close button.
        int rightX = getDrawX() + getWidth() - closeButtonMarginX - closeButtonLength;
        int leftX = rightX + closeButtonLength;
        int top = getDrawY() + closeButtonMarginY;
        int bottom = top + closeButtonLength;
        g.drawLine(rightX, top, leftX, bottom);
        g.drawLine(rightX, bottom, leftX, top);

        // Draw the title.
        title.paint(g);
    }

    /**
     * Draw a border around the window panel.
     *
     * @param g The graphics object to draw on.
     */
    private void paintBorder(Graphics g) {
        g.setColor(getColor("Border"));
        // The Java Graphics API does really weird things, two rectangles can't be drawn with the second inside the
        // first and their borders touching. Drawing each line separately doesn't work either, only drawing each border
        // as a filled rectangle seems to work...
        g.fillRect(getDrawX(), getDrawY(), getWidth(), 2);
        g.fillRect(getDrawX(), getDrawY(), 2, getHeight());
        g.fillRect(getDrawX(), getDrawY() + getHeight(), getWidth(), 2);
        // No clue why this needs getHeight() + 2, but without it the line isn't high enough.
        g.fillRect(getDrawX() + getWidth(), getDrawY(), 2, getHeight() + 2);
    }

    /**
     * Draw the panel if it's visible.
     *
     * @param g The graphics object to paint on.
     */
    @Override
    public void paint(Graphics g) {
        if (!isVisible()) return;

        // Draw a border around this window panel if it is the active one.
        if (isSelected()) {
            g.setColor(getColor("Accent3"));
            g.fillRect(getDrawX() + 6, getDrawY() + 6, getWidth(), getHeight());
            g.setColor(getColor("Standard"));
        }

        super.paint(g);
        paintHeaderBar(g);
        paintBorder(g);
    }

    /**
     * Add an observer that will be notified when the user has pressed the close button of the window.
     *
     * @param observer The observer to have.
     */
    public void addWindowPanelCloseHandler(WindowPanelClosedHandler observer) {
        windowPanelCloseHandlers.add(observer);
    }

    /**
     * Remove an observer so that it will no longer be notified when the user has pressed the close button.
     *
     * @param observer The observer to remove.
     */
    public void removeWindowPanelCloseHandler(WindowPanelClosedHandler observer) {
        windowPanelCloseHandlers.remove(observer);
    }
}
