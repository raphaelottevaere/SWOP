package com.swopteam07.tablr.UI;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Rectangle;

/**
 * A class representing a container in which to place other components.
 */
public class Panel extends DrawableContainer<Component> implements Scrollable {

    /**
     * The height (horizontal) or width (vertical) that is used for scrollbars.
     */
    private final int scrollBarSize = 15;

    /**
     * The horizontal scrollbar for this panel.
     */
    final Scrollbar horizontal;

    /**
     * Indicates whether or not the horizontal scrollbar is shown to the user.
     */
    boolean horizontalEnabled = false;

    /**
     * The vertical scrollbar for this panel.
     */
    final Scrollbar vertical;

    /**
     * Indicates whether or not the vertical scrollbar is show to the user.
     */
    boolean verticalEnabled = false;

    /**
     * The offset used for the x axis when drawing the component.
     * If xOffset is 10 then, when drawing getX() - 10 will be used.
     */
    private int xOffset = 0;

    /**
     * The offset used for the y axis when drawing the component.
     * If yOffset is 10 then, when drawing, getY() - 10 will be used.
     */
    private int yOffset = 0;

    /**
     * Create a new panel with the given dimensions, visibility state.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The panel is initialized as a container component with the given dimensions, visibility state and children.
     * | super(x, y, height, width, visible, parent)
     */
    public Panel(int x, int y, int height, int width, boolean visible, Component parent) {
        super(x, y, height, width, visible, parent);
        horizontal = createHorizontalScrollbar();
        vertical = createVerticalScrollbar();
    }

    /**
     * Create a new visible panel with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The panel is initialized with the given parameters.
     * | this(x, y, height, width, true, parent)
     */
    public Panel(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Creates a horizontal scrollbar and attaches a listener for scrollPosition changes.
     *
     * @return The new horizontal scrollbar.
     */
    private HorizontalScrollbar createHorizontalScrollbar() {
        HorizontalScrollbar horizontal =
                new HorizontalScrollbar(0, getHeight() - scrollBarSize, scrollBarSize, getWidth(), getWidth(), this);
        horizontal.addScrollOffsetChangedHandler(event -> setXOffset(event.getNewValue()));
        return horizontal;
    }

    /**
     * Creates a vertical scrollbar and attaches a listener for scrollPosition changes.
     *
     * @return The new vertical scrollbar.
     */
    private VerticalScrollbar createVerticalScrollbar() {
        VerticalScrollbar vertical =
                new VerticalScrollbar(getWidth() - scrollBarSize, 0, getHeight(), scrollBarSize, getHeight(), this);
        vertical.addScrollOffsetChangedHandler(event -> setYOffset(event.getNewValue()));
        return vertical;
    }

    /**
     * Enable the horizontal scrollbar for this component.
     * If the scrollbar is already enabled nothing will change.
     */
    public final void enableHorizontalScrollbar() {
        if (horizontalEnabled) return;
        horizontal.setWidth(verticalEnabled ? getWidth() - scrollBarSize : getWidth());
        horizontal.setContentSize(getContentWidth());
        addChild(horizontal);
        horizontalEnabled = true;
    }

    /**
     * Disable the horizontal scrollbar for this component.
     */
    public final void disableHorizontalScrollbar() {
        horizontalEnabled = false;
        children.remove(horizontal);
    }

    /**
     * Enable the vertical scrollbar for this component.
     */
    public void enableVerticalScrollbar() {
        if (verticalEnabled) return;
        vertical.setHeight(getHeight());
        vertical.setContentSize(getContentHeight());
        horizontal.setWidth(getWidth() - scrollBarSize);
        addChild(vertical);
        verticalEnabled = true;
    }

    /**
     * Disable the vertical scrollbar for this component.
     */
    public final void disableVerticalScrollbar() {
        verticalEnabled = false;
        children.remove(vertical);
    }

    /**
     * Add a child to this panel.
     *
     * @param component The child component.
     */
    @Override
    public void addChild(Component component) {
        super.addChild(component);
    }

    /**
     * Delete all children
     */
    public void emptyPanel() {
        children.clear();
    }

    /**
     * Get the child at the specified position.
     *
     * @param id The id of the child to get.
     * @return The child at the specified position.
     */
    @Override
    public Component getChildAt(int id) {
        return children.get(id);
    }

    /**
     * @return Get the width for a vertical scrollbar or the height for a horizontal scrollbar.
     */
    protected final int getScrollBarSize() {
        return scrollBarSize;
    }

    /**
     * Check if whether or not this containers propagates mouse/key events to children.
     *
     * @return True if neither the vertical or horizontal scroll button is being moved, false otherwise.
     */
    @Override
    protected boolean allowsPropagationToChildren() {
        // Ensure that if the scrollbars are currently being moved all input goes to the scrollbar instead of the
        // child that contains the coordinate.
        return !vertical.isCurrentlyScrolling() && !horizontal.isCurrentlyScrolling();
    }

    /**
     * @return The amount by which the x axis is shifted to the left when drawing.
     */
    public int getXOffset() {
        return horizontalEnabled ? this.xOffset : 0;
    }

    /**
     * @return The amount by which the y axis is shifted when drawing.
     */
    public int getYOffset() {
        return verticalEnabled ? this.yOffset : 0;
    }

    /**
     * Set the amount by which the x axis is shifted to the left when drawing.
     *
     * @param xOffset The new offset.
     */
    public void setXOffset(int xOffset) {
        this.xOffset = xOffset < 0 ? 0 : xOffset;
    }

    /**
     * Set the amount by which the y axis is shifted to the top when drawing.
     *
     * @param yOffset The new offset.
     */
    public void setYOffset(int yOffset) {
        this.yOffset = yOffset < 0 ? 0 : yOffset;
    }

    @Override
    public void updateContentWidthAndHeight() {
        super.updateContentWidthAndHeight();
        vertical.setContentSize(getContentHeight());
        horizontal.setContentSize(getContentWidth());
    }

    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        if (id != 506) {
            vertical.setCurrentlyScrolling(false);
            horizontal.setCurrentlyScrolling(false);
        }
        super.handleClickEvent(id, x, y, clickCount);
        // Ensures that even if the cursor moves out of the scrollbars the drag event continues.
        if (vertical.isCurrentlyScrolling()) vertical.handleClickEvent(id, x, y, clickCount);
        else if (horizontal.isCurrentlyScrolling()) horizontal.handleClickEvent(id, x, y, clickCount);
    }

    /**
     * Paint the panel and it's contents.
     *
     * @param g The graphics object to paint on.
     */
    public void paint(Graphics g) {
        if (!isVisible()) return;

        // Clip to the size of this panel.
        Shape tmpClip = g.getClip();
        if (verticalEnabled || horizontalEnabled)
            g.setClip(new Rectangle(getDrawX(), getDrawY(), getWidth(), getHeight()));

        g.setColor(getColor("Background"));
        g.fillRect(getDrawX(), getDrawY(), getWidth(), getHeight());
        g.setColor(getColor("Standard"));
        super.paint(g);
        g.setClip(tmpClip);
    }
}
