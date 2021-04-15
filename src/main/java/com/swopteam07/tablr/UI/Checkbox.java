package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.event.CheckboxChangeEvent;
import com.swopteam07.tablr.UI.handlers.CheckboxChangeHandler;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a container in which to place other components.
 */
public class Checkbox extends DrawableComponent {

    /**
     * A variable that indicates the checkbox value.
     */
    private Boolean value;

    /**
     * Indicates whether or not to include null in the rotation.
     * If false the rotation is: true -> false -> true
     * If true  the rotation is: true -> false -> blank -> true
     */
    private boolean includeBlank;

    /**
     * List of the observers that are alerted when the contents of this Checkbox changes.
     */
    private final List<CheckboxChangeHandler> checkboxChangeHandlers = new ArrayList<>();

    /**
     * Create a new checkbox with the given dimensions, visibility state and value.
     *
     * @param x            The x coordinate of the top left corner of the component relative to the parent component if it
     *                     exists or the window if it doesn't.
     * @param y            The y coordinate of the top left corner of the component.relative to the parent component if it
     *                     exists or the window if it doesn't.
     * @param height       The height of the component.
     * @param width        The width of the component.
     * @param visible      The visibility state of the new component.
     * @param value        A Boolean object or null.
     * @param includeBlank Indicates whether or not this checkbox allows for blank values.
     * @param parent       The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with the given dimensions, visibility state, value and activation state.
     * | super(x, y, height, width, visible, parent)
     */
    public Checkbox(int x, int y, int height, int width, boolean visible, Boolean value, boolean includeBlank, Component parent) {
        super(x, y, height, width, visible, parent);
        this.value = value;
        setIncludeBlank(includeBlank);
    }

    /**
     * Create a new checkbox with the given dimensions, visibility state and value.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param value   A Boolean object or null.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with the given dimensions, visibility state, value and activation state.
     * | super(x, y, height, width, visible, parent)
     */
    public Checkbox(int x, int y, int height, int width, boolean visible, Boolean value, Component parent) {
        this(x, y, height, width, visible, value, false, parent);
    }

    /**
     * Create a new checkbox with the given dimensions, visibility state, value and activation state.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param value  A boolean value.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with the given dimensions, visibility state, value and activation state.
     * | super(x, y, height, width, visible, parent)
     */
    public Checkbox(int x, int y, int height, int width, Boolean value, Component parent) {
        this(x, y, height, width, true, value, parent);
    }

    /**
     * Create a new checkbox with the given dimensions, visibility state, value and activation state.
     *
     * @param x            The x coordinate of the top left corner of the component relative to the parent component if it
     *                     exists or the window if it doesn't.
     * @param y            The y coordinate of the top left corner of the component.relative to the parent component if it
     *                     exists or the window if it doesn't.
     * @param height       The height of the component.
     * @param width        The width of the component.
     * @param value        A boolean value.
     * @param includeBlank Indicates whether or not this checkbox allows for blank values.
     * @param parent       The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with the given dimensions, visibility state, value and activation state.
     * | super(x, y, height, width, visible, parent)
     */
    public Checkbox(int x, int y, int height, int width, Boolean value, boolean includeBlank, Component parent) {
        this(x, y, height, width, true, value, includeBlank, parent);
    }

    /**
     * Create a new checkbox with a 0 surface area and the given value.
     *
     * @param value  A Boolean object or null.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with 0 for all parameters except value.
     * | this(0, 0, 0, 0, value, parent)
     */
    public Checkbox(Boolean value, Component parent) {
        this(0, 0, 0, 0, value, parent);
    }

    /**
     * Create a new checkbox with a 0 surface area and the given value.
     *
     * @param value        A Boolean object or null.
     * @param includeBlank Indicates whether or not this checkbox allows for blank values.
     * @param parent       The component that contains this component or null if this component has no parent.
     * @effect The checkbox is initialized with 0 for all parameters except value.
     * | this(0, 0, 0, 0, value, parent)
     */
    public Checkbox(Boolean value, boolean includeBlank, Component parent) {
        this(0, 0, 0, 0, value, includeBlank, parent);
    }

    /**
     * Set a new value for the includes blank option.
     *
     * @param includeBlank The new value.
     * @post The new value for the includesBlank variable is equal to the given variable.
     * | new.includesBlank() == includeBlank
     */
    public void setIncludeBlank(boolean includeBlank) {
        this.includeBlank = includeBlank;
    }

    /**
     * Check if this checkbox includes blanks in it's rotation.
     *
     * @return True if this checkbox allows for blank values.
     */
    public boolean includesBlank() {
        return this.includeBlank;
    }

    /**
     * Get the value of this checkbox.
     *
     * @return The value of this checkbox.
     */
    public Boolean getValue() {
        return this.value;
    }

    /**
     * @param value The new value for this checkbox
     * @post The new value is equal to the given value.
     * | new.getValue().equals(value)
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

    /**
     * Change the value of this checkbox to the next value in it's rotation.
     */
    private void toggleValue() {
        Boolean oldValue = value;
        if (value == null) {
            value = true;
        } else if (!value && includesBlank())
            value = null;
        else
            value = !value;

        if (support != null)
            support.firePropertyChange("value", oldValue, value);
        if (checkboxChangeHandlers != null) {
            CheckboxChangeEvent evt = new CheckboxChangeEvent(oldValue, getValue());
            checkboxChangeHandlers.forEach(h -> h.handleEvent(evt));
        }
    }

    /**
     * Draw a rectangle around this component.
     *
     * @param g The graphics object to draw on.
     */
    private void drawRect(Graphics g) {
        g.drawRect(getDrawX(), getDrawY(), getWidth(), getHeight());
    }

    /**
     * Draw the checkbox on a canvas.
     *
     * @param g The graphics object on which to paint.
     */
    @Override
    public void paint(Graphics g) {
        if (!isVisible()) return;

        if (value == null) g.setColor(getColor("Disabled"));
        else g.setColor(getColor("Background"));
        g.fillRect(getDrawX(), getDrawY(), getWidth(), getHeight());

        g.setColor(getColor("Standard"));
        drawRect(g);

        if (value != null && value) {
            g.drawLine(getDrawX(), getDrawY(), getDrawX() + getWidth(), getDrawY() + getHeight());
            g.drawLine(getDrawX(), getDrawY() + getHeight(), getDrawX() + getWidth(), getDrawY());
        }
        super.paint(g);
    }

    /**
     * Change the value of the checkbox to the next value in the rotation.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     */
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);
        if (id == 500 && isSelected())
            toggleValue();
    }

    /**
     * Add an observer that is alerted when the content of this cell changes.
     *
     * @param handler The handler to add.
     */
    public void addCheckboxChangeHandler(CheckboxChangeHandler handler) {
        checkboxChangeHandlers.add(handler);
    }

    /**
     * Remove an observer that is no longer alerted when the content of this cell changes.
     *
     * @param handler The handler to remove.
     */
    public void removeCheckboxChangeHandler(CheckboxChangeHandler handler) {

        checkboxChangeHandlers.remove(handler);
    }
}
