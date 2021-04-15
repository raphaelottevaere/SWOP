package com.swopteam07.tablr.UI;

public interface Scrollable {

    /**
     * Add a horizontal scrollbar to the component.
     */
    void enableHorizontalScrollbar();

    /**
     * Add a vertical scrollbar to the component.
     */
    void enableVerticalScrollbar();

    /**
     * Remove the horizontal scrollbar from the drawn user interface.
     */
    void disableHorizontalScrollbar();

    /**
     * Remove the vertical scrollbar from the drawn user interface.
     */
    void disableVerticalScrollbar();

    /**
     * @return The amount by which the x coordinate of scrollable elements needs to shifted to the left,
     * based on the position of the scroll button in the horizontal scrollbar.
     * If the horizontal scrollbar isn't enabled the result is always 0.
     */
    int getXOffset();

    /**
     * @return The amount by which the y coordinate of the scrollable elements needs to be shifted to the top,
     * based on the position of the scroll button in the vertical scrollbar.
     * If the vertical scrollbar isn't enabled the result is always 0.
     */
    int getYOffset();

    /**
     * Set the amount by which the x axis is shifted to the left when drawing.
     *
     * @param xOffset The new offset.
     */
    void setXOffset(int xOffset);

    /**
     * Set the amount by which the y axis is shifted to the top when drawing.
     *
     * @param yOffset The new offset.
     */
    void setYOffset(int yOffset);
}
