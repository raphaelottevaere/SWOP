package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.handlers.ScrollOffsetChangedHandler;
import com.swopteam07.tablr.UI.event.ScrollOffsetChangedEvent;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Scrollbar extends Component {

    /**
     * Indicates the amount of blank space left after the scroll.
     * Do not keep this to low since having to scroll down/right every click is rather irritating.
     */
    final int scrollMargin = 200;

    /**
     * The length of the scroll element.
     */
    private int scrollElemLength;

    /**
     * Indicates where the movable part of the scrollbar is positioned.
     */
    private int scrollPosition = 0;

    /**
     * Indicate the percentage of the content that is before the scroll element.
     */
    private double beforeScrollElement = 0;

    /**
     * The size of the content this scrollbar can show.
     */
    private int contentSize;

    /**
     * The number of units the scroll element moves when a uses clicks before or after the scroll element.
     */
    private int stepSize;

    /**
     * Indicates whether or not the scrollbar is enabled.
     */
    boolean enabled;

    /**
     * The offset that is determined by the position of the scroll bar.
     * And offset of 0 indicates that the scroll element is at the beginning of the scrollbar.
     */
    private int offset = 0;

    /**
     * The maximum offset that can be used.
     * When the offset is equal to this value the rightmost or bottommost content is shown to the user.
     */
    int maxOffset;

    /**
     * Indicates if the scroll element is currently being dragged around.
     */
    private boolean currentlyScrolling = false;

    /**
     * List of all the scrollOffsetChangedHandlers that should be notified when the scrollbar is moved.
     */
    private final List<ScrollOffsetChangedHandler> scrollOffsetChangedHandlers = new ArrayList<>();

    /**
     * Indicates whether or not the offset should currently be updated.
     * When the content size changes but the user has already scrolled down, you should see the content grow/shrink not
     * see the bottom of the new content at the same place as it was before adding content.
     */
    private boolean updateOffset = true;

    /**
     * Create a new visible scrollbar with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height      The height of the component.
     * @param width       The width of the component.
     * @param contentSize The width of the content this scrollbar can be used to show.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The scrollbar is initialized as a component with the given parameters.
     * | super(x, y, height, width, true, parent)
     * @effect The content size is set.
     * | setContentSize(contentSize)
     * @effect The z index is initialized to be the highest integer value.
     * | setZIndex(Integer.MAX_VALUE)
     */
    Scrollbar(int x, int y, int height, int width, int contentSize, Component parent) {
        super(x, y, height, width, true, parent);
        // Scrollbars should always be shown on top.
        setZIndex(Integer.MAX_VALUE);
        setContentSize(contentSize);
        setMoveWhenScrolling(false);
    }

    /**
     * @return The length of the scroll element.
     */
    public int getScrollElemLength() {
        return scrollElemLength;
    }

    /**
     * Set the new length for the scroll element.
     * @param scrollElemLength The new length of the scroll element.
     */
    protected void setScrollElemLength(int scrollElemLength) {
        this.scrollElemLength = scrollElemLength;
        placeScrollElement();
    }

    /**
     * Determine the new length of the scroll element.
     */
    protected abstract void updateScrollElemLength();

    /**
     * Add a new observer that is to be notified when the scroll element is moved.
     *
     * @param handler The handler.
     */
    public final void addScrollOffsetChangedHandler(ScrollOffsetChangedHandler handler) {
        scrollOffsetChangedHandlers.add(handler);
    }

    /**
     * Remove an observer. The observer won't be notified anymore when the scroll element is moved.
     *
     * @param handler The handler.
     */
    public final void removeScrollOffsetChangedHandler(ScrollOffsetChangedHandler handler) {
        scrollOffsetChangedHandlers.remove(handler);
    }

    /**
     * Enable or disable the scrollbar based on the contentSize.
     */
    protected abstract void setEnabled();

    /**
     * Check if the scrollbar is enabled.
     *
     * @return True if the scrollbar is enabled false otherwise.
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * @return The width of the content this scrollbar can be used to show.
     */
    public final int getContentSize() {
        return contentSize;
    }

    /**
     * Check if the scroll element is currently being dragged around by the user.
     *
     * @return True if the user is dragging the scroll element around, false otherwise.
     */
    public boolean isCurrentlyScrolling() {
        return currentlyScrolling;
    }

    /**
     * Set the new value for the currently scrolling flag.
     *
     * @param currentlyScrolling The new value.
     * @post The new value for the currently scrolling flag is equal to the specified value.
     * | new.isCurrentlyScrolling() == currentlyScrolling
     */
    public void setCurrentlyScrolling(boolean currentlyScrolling) {
        this.currentlyScrolling = currentlyScrolling;
    }

    /**
     * Set the new content size.
     *
     * @param contentSize The new content size.
     * @throws IllegalAccessError Thrown when the contentSize is smaller than 0.
     *                            | contentSize &lt; 0
     * @post The new content size is equal to the specified content size.
     * | new.getContentSize() == contentSize
     * @effect The scrollbar is enabled if necessary.
     * | setEnabled()
     * @effect The maximum offset is recalculated.
     * | setMaxOffset()
     */
    public final void setContentSize(int contentSize) {
        if (contentSize < 0) throw new IllegalArgumentException("The content size cannot be a negative value");
        this.contentSize = contentSize;
        updateOffset = false;
        dimensionsChanged();
        updateOffset = true;
    }

    /**
     * @return The position where the scroll element is currently at.
     */
    final int getScrollPosition() {
        return scrollPosition;
    }

    /**
     * Set the step size.
     *
     * @param stepSize The step size.
     * @throws IllegalAccessError Thrown when the step size is smaller than or equal to 0.
     *                            | stepSize &lt;= 0
     * @post The new step size is equal to the given step size, if the step size is larger than 0.
     * | if stepSize &lt; 0
     * |    new.getStepSize() == stepSize
     */
    public final void setStepSize(int stepSize) throws IllegalArgumentException {
        if (stepSize > 0)
            this.stepSize = stepSize;
    }

    /**
     * Get the step size of this scroll bar.
     * @return The step size.
     */
    public final int getStepSize() {
        return stepSize;
    }

    /**
     * Set the new scroll position.
     *
     * @param scrollPosition The new scroll position.
     */
    final void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
        updateBeforeScrollElement();

        // The following method need nog be executed during construction.
        if (scrollOffsetChangedHandlers == null) return;
        if (updateOffset) updateOffset();
    }

    /**
     * Method that is called to update any changes are made to the dimensions of this scrollbar or
     * the dimensions of the scroll content.
     */
    protected void dimensionsChanged() {
        setEnabled();

        if (isEnabled()) {
            setMaxOffset();
            updateScrollElemLength();
        }
    }

    /**
     * Update the offset.
     */
    protected abstract void updateOffset();

    /**
     * Determine the maximum offset.
     */
    protected abstract void setMaxOffset();

    /**
     * Set a new value for the offset.
     * Changing this value notifies all scrollOffsetChangedHandlers of the new value.
     *
     * @param offset The new value.
     */
    protected final void setOffset(int offset) {
        ScrollOffsetChangedEvent evt = new ScrollOffsetChangedEvent(this.offset, offset);
        this.offset = offset;
        scrollOffsetChangedHandlers.forEach(o -> o.handleEvent(evt));
    }

    /**
     * @return The x coordinate of the scroll element.
     */
    protected abstract int getScrollElementX();

    /**
     * @return The y coordinate of the scroll element.
     */
    protected abstract int getScrollElementY();

    /**
     * @return The width of the scroll element.
     */
    protected abstract int getScrollElementWidth();

    /**
     * @return The height of the scroll element.
     */
    protected abstract int getScrollElementHeight();

    /**
     * Change the scroll elements position based on a mouse drag event.
     *
     * @param x The x coordinate to which the scroll element was dragged.
     * @param y The y coordinate to which the scroll element was dragged.
     */
    protected final void setNewScrollPositionDragged(int x, int y) {
        setScrollPosition(getScrollPositionClosestTo(x, y));
    }

    /**
     * Change the scroll elements position based on a mouse click event somewhere in the scrollbar.
     *
     * @param x The x coordinate where the user clicked.
     * @param y The y coordinate where the user clicked.
     */
    protected abstract void setNewScrollPositionClicked(int x, int y);

    /**
     * Draws the element that a user moves to scroll in the window.
     *
     * @param g The graphics object on which to paint.
     */
    private void drawScrollElement(Graphics g) {
        g.setColor(getColor("Accent2"));
        g.fillRect(getScrollElementX(), getScrollElementY(), getScrollElementWidth(), getScrollElementHeight());
        g.setColor(getColor("Standard"));
    }

    /**
     * Check if a given (x,y) coordinate is currently located on the scroll element.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the coordinate is in scroll element, false otherwise.
     */
    private boolean isInScrollElement(int x, int y) {
        return x >= getScrollElementX() && x <= getScrollElementX() + getScrollElementWidth() &&
                y >= getScrollElementY() && y <= getScrollElementY() + getScrollElementHeight();
    }

    /**
     * Calculate the percentage of the content that is before the scroll element.
     */
    protected abstract void updateBeforeScrollElement();

    /**
     * Set the percentage of the content that is before the scroll element.
     *
     * @param beforeScrollElement The percentage of the content that is before the scroll element.
     */
    protected void setBeforeScrollElement(double beforeScrollElement) {
        this.beforeScrollElement = beforeScrollElement;
    }

    /**
     * Get the percentage of the content that is before the scroll element.
     *
     * @return The percentage of the content that is before the scroll element.
     */
    protected double getBeforeScrollElement() {
        return beforeScrollElement;
    }

    /**
     * Place the scroll element in the correct position when the dimensions of the scrollbar have changed.
     */
    protected abstract void placeScrollElement();

    /**
     * Check if a given (x,y) coordinate is located before the scroll element.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the coordinate is before the scroll element, false otherwise.
     */
    protected abstract boolean isBeforeScrollElement(int x, int y);

    /**
     * Given a coordinate that falls within the scrollbar, get the scrollPosition closest to this coordinate.
     *
     * @param x The x coordinate for which to get the scroll position.
     * @param y The y coordinate for which to get the scroll position.
     * @return The scroll position closest to the given coordinate.
     */
    protected abstract int getScrollPositionClosestTo(int x, int y);

    /**
     * React to a user clicking on the scrollbar, if the scrollbar is not enabled do nothing.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     * @effect The handleClickEvent method of the Component class is called.
     * | super.handleClickEvent(id, x, y, clickCount)
     * @effect If the user clicked in the scroll element the setNewScrollPositionDragged function is called.
     * | if (isInScrollElement(x,y))
     * |    setNewScrollPositionDragged(x,y)
     * If the uses didn't click in the scroll element the setNewScrollPosition method id called.
     * | else
     * |    setNewScrollPositionClicked(x,y)
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);
        if (!isEnabled()) return;

        if (id != 506) setCurrentlyScrolling(false);

        // Using the currently scrolling flag allows the user to move his mouse faster, thereby ensuring a better UX.
        if ((isInScrollElement(x, y) || isCurrentlyScrolling()) && id == 506) {
            setCurrentlyScrolling(true);
            setNewScrollPositionDragged(x, y); // 506 indicates a drag event.
        } else if (id == 501 && !isInScrollElement(x, y)) setNewScrollPositionClicked(x, y);
    }

    /**
     * Draw the scrollbar. If the scrollbar is disabled the "Disabled" color is used.
     *
     * @param g The graphics object on which to paint.
     */
    @Override
    protected void paint(Graphics g) {
        super.paint(g);

        if (!isEnabled()) {
            g.setColor(getColor("Disabled"));
            g.fillRect(getDrawX(), getDrawY(), getWidth(), getHeight());
            g.setColor(getColor("Disabled"));
        } else
            drawScrollElement(g);
    }
}