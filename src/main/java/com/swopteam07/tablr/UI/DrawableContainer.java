package com.swopteam07.tablr.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A class representing a container that can be drawn.
 */
public abstract class DrawableContainer<T extends Component> extends Container<T> {

    /**
     * Create a new drawable container with the given dimensions and visibility state and children.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height   The height of the component.
     * @param width    The width of the component.
     * @param visible  The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The container component is initialized as a drawable component with the given parameters.
     * | super(x, y, height, width, visible, parent)
     * @effect The elements of the children collection will be added to the children of this component.
     * | addChildren(children)
     */
    public DrawableContainer(int x, int y, int height, int width, boolean visible, Component parent) {
        super(x, y, height, width, visible, parent);
    }

    /**
     * Create a new visible container with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height   The height of the component.
     * @param width    The width of the component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The container component is initialized with the given parameters.
     * | this(x, y, height, width, visible, new ArrayList&lt;&gt;())
     */
    public DrawableContainer(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Paint this component and it's children.
     *
     * @param g The graphics object to paint on.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }



}
