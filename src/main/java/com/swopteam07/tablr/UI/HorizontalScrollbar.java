package com.swopteam07.tablr.UI;

public class HorizontalScrollbar extends Scrollbar {

    /**
     * Create a new horizontal scrollbar with the given dimensions.
     *
     * @param x           The x coordinate of the top left corner of the component relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param y           The y coordinate of the top left corner of the component.relative to the parent component if it
     *                    exists or the window if it doesn't.
     * @param height      The height of the component.
     * @param width       The width of the component.
     * @param contentSize The width of the content this scrollbar can be used to show.
     * @param parent      The component that contains this component or null if this component has no parent.
     * @effect The horizontal scrollbar is initialized as a scrollbar with the given parameters.
     * | super(x, y, height, width, true, parent)
     * @effect The scroll position is initialized to be at the beginning of the scrollbar.
     * | setScrollPosition(x + getScrollElemLength() /2)
     */
    HorizontalScrollbar(int x, int y, int height, int width, int contentSize, Component parent) {
        super(x, y, height, width, contentSize, parent);
        setScrollPosition(x + getScrollElemLength() / 2);
    }

    /**
     * Set the length of the scroll element so that the ratio of
     * the length of the scroll button to the width of the scrollbar equals the ratio of
     * the width of the content area to the width of the content.
     */
    @Override
    protected void updateScrollElemLength() {
        // Use the max method to ensure that the scroll element is large enough to be dragged easily.
        int size = Math.max(20, 180 * getWidth() / getContentSize());
        setScrollElemLength(size);
    }

    /**
     * Set the width and update the step size.
     *
     * @param width The new width of the component.
     * @effect The setWidth() method of the Component class is called.
     * | super.setWidth(width)
     * @effect The scrollbar's step size is set to 10% of the width.
     * | setStepSize(0.15 * (width - getScrollElemLength()))
     */
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        setStepSize((int) Math.rint(0.15 * (width - getScrollElemLength())));
        dimensionsChanged();
    }

    /**
     * @post The scrollbar is enabled if the width is lower than the content size
     * | new.isEnabled() == getWidth() &lt; getContentSize()
     */
    @Override
    protected void setEnabled() {
        enabled = getContentSize() > 0 && getContentSize() > getWidth() - scrollMargin;
    }

    /**
     * Change the offset to a new position determined by the scroll position.
     *
     * @effect The setOffset method is called with the the percentage of the maxOffset that corresponds to the
     * percentage of the scrollbar that is before the scroll element as argument.
     * | setOffset(getScrollPosition() / getWidth() * maxOffset)
     */
    @Override
    protected void updateOffset() {
        double percentage = getBeforeScrollElement() * maxOffset;
        setOffset((int) Math.rint(percentage));
    }

    /**
     * Determine the maximum offset that is allowed.
     *
     * @post The new maximum offset is equal to the difference between the width of the content this scrollbar manages
     * and the width of the scrollbar itself added with the margin on scrollbars.
     */
    @Override
    protected void setMaxOffset() {
        maxOffset = getContentSize() + scrollMargin - getWidth();
    }

    /**
     * @return The x coordinate of the scroll element.
     */
    @Override
    protected int getScrollElementX() {
        return getScrollPosition() - getScrollElemLength() / 2;
    }

    /**
     * @return The y coordinate of the scroll element.
     */
    @Override
    protected int getScrollElementY() {
        return getDrawY();
    }

    /**
     * @return The width of the scroll element.
     */
    @Override
    protected int getScrollElementWidth() {
        return getScrollElemLength();
    }

    /**
     * @return The height of the scroll element.
     */
    @Override
    protected int getScrollElementHeight() {
        return getHeight();
    }

    /**
     * Move the scroll element up or down the scrollbar based on the user clicking somewhere in the scrollbar.
     *
     * @param x The x coordinate where the user clicked.
     * @param y The y coordinate where the user clicked.
     */
    @Override
    protected void setNewScrollPositionClicked(int x, int y) {
        int newX = isBeforeScrollElement(x, y) ? getScrollPosition() - getStepSize() : getScrollPosition() + getStepSize();
        setScrollPosition(getScrollPositionClosestTo(newX, y));
    }

    /**
     * Calculate the percentage of the content that is before the scroll element.
     */
    @Override
    protected void updateBeforeScrollElement() {
        setBeforeScrollElement(
                1.0 * (getScrollPosition() - getDrawX() - getScrollElemLength() / 2) / (getWidth() - getScrollElemLength()));
    }

    /**
     * Place the scroll element in the correct position when the dimensions of the scrollbar have changed.
     */
    @Override
    protected void placeScrollElement() {
        int x = (int)
                (getDrawX() + getBeforeScrollElement() * (getWidth() - getScrollElemLength()) + getScrollElemLength() / 2.0);
        setScrollPosition(getScrollPositionClosestTo(x, 0));
    }

    /**
     * @param x The x coordinate of the top left corner of the component.
     * @effect The setX() method of the superclass is called.
     * |super.setX(x)
     * @effect The dimensionsChanged method is called.
     * | dimensionsChanged()
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        dimensionsChanged();
    }

    /**
     * Check if a coordinate pair is before of after the scroll element.
     *
     * @param x The x coordinate for which to check.
     * @param y The y coordinate for which to check.
     * @return True if the scroll element is before the specified pair, false otherwise.
     */
    @Override
    protected boolean isBeforeScrollElement(int x, int y) {
        return x < getScrollPosition() - getScrollElemLength() / 2;
    }

    /**
     * Determine the scroll position closest to the given coordinate pair.
     *
     * @param x The x coordinate for which to get the scroll position.
     * @param y The y coordinate for which to get the scroll position.
     * @return The new scroll position.
     */
    @Override
    protected int getScrollPositionClosestTo(int x, int y) {
        if (x < getDrawX() + getScrollElemLength() / 2) return getDrawX() + getScrollElemLength() / 2;
        if (x > getDrawX() + getWidth() - getScrollElemLength() / 2)
            return getDrawX() + getWidth() - getScrollElemLength() / 2;
        else return x;
    }
}
