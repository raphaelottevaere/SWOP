package com.swopteam07.tablr.UI;

import java.awt.*;

/**
 * A Class representing a UI component that can be drawn on the screen and that has meaning on it's own.
 */
public abstract class DrawableComponent extends Component {

    /**
     * Create a new drawable component with the given dimensions and visibility state.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The drawable component is initialized as a component with the given parameters.
     * | super(x, y, height, width, visible, parent)
     */
    public DrawableComponent(int x, int y, int height, int width, boolean visible, Component parent) {
        super(x, y, height, width, visible, parent);
    }

    /**
     * Create a new visible drawable component with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The drawable component is initialized with the given parameters.
     * | this(x, y, height, width, true, parent)
     */
    public DrawableComponent(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Create a new drawable component with a 0 surface area.
     *
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The drawable component is initialized with 0 for all parameters.
     * | this(0, 0, 0, 0)
     */
    public DrawableComponent(Component parent) {
        this(0, 0, 0, 0, parent);
    }

    /**
     * Draw the component on a canvas.
     *
     * @param g The graphics object on which to paint.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

}
