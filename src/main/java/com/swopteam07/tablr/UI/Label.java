package com.swopteam07.tablr.UI;

import java.awt.*;

/**
 * A class representing a label, i.e. string of text.
 */
public class Label extends DrawableComponent {

    /**
     * The text displayed on this label.
     */
    private String text;

    /**
     * Create a new label with the given dimensions and visibility state.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param text    The text to be displayed in the label.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The label is initialized as a component with the given dimensions and visibility state.
     * | super(x, y, height, width, visible, parent)
     * @effect The text of the new label is equal to the given text.
     * | setText(text)
     * @post The textfield is initialized as containing valid text.
     * | new.isValid()
     */
    public Label(int x, int y, int height, int width, boolean visible, String text, Component parent) {
        super(x, y, height, width, visible, parent);
        setText(text);
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
     * @param text   The text to be displayed in the label.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect Initialize the label with the given parameters.
     * | this(x, y, height, width, true, text, parent)
     */
    public Label(int x, int y, int height, int width, String text, Component parent) {
        this(x, y, height, width, true, text, parent);
    }

    /**
     * Create a new label with a 0 surface area.
     *
     * @param text   The text to be displayed in the label.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The label is initialized with 0 for all parameters.
     * | this(0, 0, 0, 0, text, parent)
     */
    public Label(String text, Component parent) {
        this(0, 0, 0, 0, text, parent);
    }

    /**
     * Get the text of this label.
     *
     * @return The text of this label.
     */
    public String getText() {
        return this.text;
    }

    /**
     * @param text The new text for this label.
     * @post The new text is equal to the given text.
     * | new.getText().equals(text)
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Draw the label on a canvas.
     *
     * @param g The graphics object on which to paint.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(getColor("Standard"));
        if (isVisible()) {
            // The (x,y) coordinate for a string is the left bottom corner.
            // Calculate the correct coordinates so the text is written within the rectangle and centered vertically.
            int y = getDrawY() + getHeight() - (getHeight() - g.getFontMetrics().getHeight());
            g.drawString(this.text, getDrawX(), y);
        }

        super.paint(g);
    }
}
