package com.swopteam07.tablr.UI;

import com.swopteam07.tablr.UI.handlers.MouseClickListener;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class representing a basic UI component.
 */
public abstract class Component implements Comparable<Component> {

    /**
     * Variable registering the x coordinate of the top left corner of this component.
     * If this component has a parent component this is the x coordinate relative to that parent,
     * otherwise it's the x coordinate relative to the top left corner of the application.
     */
    private int x;

    /**
     * Variable registering the y coordinate of the top left corner of this component.
     * If this component has a parent component this is the y coordinate relative to that parent,
     * otherwise it's the y coordinate relative to the top left corner of the application.
     */
    private int y;

    /**
     * Variable registering the width of this component.
     */
    private int width;

    /**
     * Variable registering the height of this component.
     */
    private int height;

    /**
     * Variable registering whether or not this component is visible.
     */
    private boolean visible;

    /**
     * Variable registering whether or not the component is currently active.
     */
    private boolean selected;

    /**
     * Flag that indicates whether the given component is allowed to be unselected.
     */
    private boolean allowsUnselect;

    /**
     * Indicates if the component is allowed to be selected.
     */
    private boolean canBeSelected;

    /**
     * Used to alert users to a change in parameters of implementing classes.
     */
    protected PropertyChangeSupport support;

    /**
     * List keeping track observers for click events.
     */
    protected final List<MouseClickListener> mouseClickListeners = new ArrayList<>();

    /**
     * A variable that indicates whether the text is valid or not.
     */
    private boolean valid;


    /**
     * Indicates if the component should move when the user scrolls.
     */
    private boolean moveWhenScrolling = true;

    /**
     * The horizontal margin used when determining if a given x coordinate falls within the component.
     */
    private int hMargin = 0;

    /**
     * The vertical margin used when determining if a given y coordinate falls within the component.
     */
    private int vMargin = 0;

    /**
     * Index that determines in what order components get drawn and to which components the click/key events get
     * propagated.
     */
    private int zIndex = 0;

    /**
     * The component that contains this component.
     * If this component isn't contained in another component this is null.
     */
    private Component parent;

    /**
     * Keeps track of the color used throughout the application.
     * Has colors for
     * - Invalid: Used for components that contain invalid values.
     * - Disabled: Used for components that are disabled.
     * - Standard: Used for all drawing that doesn't have any special requirements.
     * - Background: Used for components that have a background.
     * - Border: Used for borders around components that have them.
     * - Accent: Used for accentuating a specific part of a component.
     * - Accent2: Used for accentuating a specific part of a component when the first accent color is already in use.
     * - Accent3: Used for accentuating a specific part of a component when the first and second accent colors are already in use.
     * - Selected: Used to indicate that the component is selected.
     */
    private final Map<String, Color> colors = new HashMap<>();

    /**
     * Create a new component with the given dimensions and visibility state.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The x coordinate of the new component will be equal to the given x coordinate.
     * | setX(x)
     * @effect The y coordinate of the new component will be equal to the given y coordinate.
     * | setY(y)
     * @effect The height of the new component will be equal to the given height.
     * | setHeight(height)
     * @effect The width of the new component will be equal to the given width.
     * | setWidth(width)
     * @effect The visibility state of the new component will be equal to the given visibility state.
     * | setVisible(visible)
     * @effect The component is not selected.
     * | setSelected(false)
     * @effect The component can be unselected if selected.
     * | setAllowsUnselect(true)
     * @effect The component can be selected.
     * | setCanBeSelected(true)
     * @effect The component is valid.
     * | setValid(true)
     */
    public Component(int x, int y, int height, int width, boolean visible, Component parent) {
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        setVisible(visible);
        setSelected(false);
        setCanBeSelected(true);
        setAllowsUnselect(true);
        setValid(true);
        this.parent = parent;
        this.support = new PropertyChangeSupport(this);
        colors.put("Invalid", Color.RED);
        colors.put("Disabled", Color.GRAY);
        colors.put("Standard", Color.BLACK);
        colors.put("Background", new Color(250, 250, 250));
        colors.put("Border", new Color(209, 209, 232));
        colors.put("Accent", new Color(230, 230, 252));
        colors.put("Accent2", new Color(55, 71, 82));
        colors.put("Accent3", new Color(102, 102, 102, 200));
        colors.put("Selected", new Color(200, 200, 200, 100));
    }

    /**
     * Create a new visible component with the given dimensions.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The component is initialized with the given parameters.
     * | this(x, y, height, width, true, parent)
     */
    public Component(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Initialize a component to have a 0 surface area.
     *
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The component is initialized with 0 for all parameters.
     * | this(0, 0, 0, 0, parent)
     */
    public Component(Component parent) {
        this(0, 0, 0, 0, parent);
    }

    /**
     * Check if a given coordinate is contained within this component.
     *
     * @param x The x coordinate to check for.
     * @param y The y coordinate to check for.
     * @return False if the given x coordinate is smaller than the x coordinate of the top left corner of the component.
     * | if (x &lt; getDrawX() - getMargin())
     * |   result == false
     * False if the given y coordinate is smaller than the y coordinate of the top left corner of the component.
     * | else if (y &lt; getDrawY() - getMargin())
     * |   result == false
     * False if the given x coordinate is not smaller than the top left x coordinate incremented with the width.
     * | else if (x &gt; getDrawX() + getWidth() + getMargin())
     * |   result == false
     * False if the given y coordinate is not smaller than the top left y coordinate incremented with the height.
     * | else if (y &gt; getDrawY() + getHeight() + getMargin())
     * |   result == false
     * True in all other cases.
     * | else
     * |   result == true
     */
    public boolean contains(int x, int y) {
        return x >= getDrawX() - getHMargin() && x <= getDrawX() + getWidth() + getHMargin() &&
                y >= getDrawY() - getVMargin() && y <= getDrawY() + getHeight() + getVMargin();
    }

    /**
     * @param x The x coordinate of the top left corner of the component relative to the parent component if it
     *          exists or the window if it doesn't.
     * @post The new x coordinate will be equal to the given value.
     * | new.getX() == x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return The x coordinate of the top left corner of the component relative to the parent component if it
     * exists or the window if it doesn't.
     */
    public final int getX() {
        return this.x;
    }

    /**
     * @return The x value to be used when drawing components.
     */
    public int getDrawX() {
        if (parent == null) return getX();
        int drawX = parent.getDrawX() + getX();
        if (parent instanceof Scrollable && moveWhenScrolling) return drawX - ((Scrollable) parent).getXOffset();
        return drawX;
    }

    /**
     * @param y The y coordinate of the top left corner of the component.
     * @post The new y coordinate will be equal to the given y value.
     * | new.getY() == y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return The y coordinate of the top left corner of this component.
     */
    public final int getY() {
        return this.y;
    }

    /**
     * @return The y value to be used when drawing moveWhenScrolling content.
     */
    public final int getDrawY() {
        if (parent == null) return getY();
        int drawY = parent.getDrawY() + getY();
        if (parent instanceof Scrollable && moveWhenScrolling) return drawY - ((Scrollable) parent).getYOffset();
        return drawY;
    }

    /**
     * @param width The new width of the component.
     * @post The new width will be equal to the given width.
     * | new.getWidth() == width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return The width of this component.
     */
    public final int getWidth() {
        return this.width;
    }

    /**
     * @param height The new height of the component.
     * @post The new height is equal to the specified height.
     * | new.getHeight() == height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return The height of this component.
     */
    public final int getHeight() {
        return this.height;
    }

    /**
     * @param visible | The new visibility state for the component.
     * @post The new visibility state will be equal to the given visibility state.
     * | new.isVisible() == visible
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return True if the component is visible, false otherwise.
     */
    public final boolean isVisible() {
        return this.visible;
    }

    /**
     * Ensure that this component is not selected.
     *
     * @effect This component is not selected.
     * | setSelected(false)
     */
    public void unselect() {
        if (allowsUnselect())
            setSelected(false);
    }

    /**
     * @param selected The new value for the selected field.
     * @post The new selected value is equal to the given value.
     * | new.isSelected() == selected
     */
    public final void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return True if the component is selected, false otherwise.
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set the new value of the allowsUnselect flag.
     *
     * @param allowsUnselect The new value.
     */
    public void setAllowsUnselect(boolean allowsUnselect) {
        this.allowsUnselect = allowsUnselect;
    }

    /**
     * @return True if it is allowed to unselect the component, false otherwise.
     */
    public final boolean allowsUnselect() {
        return this.allowsUnselect;
    }

    /**
     * Set a new value for the canBeSelected flag.
     *
     * @param canBeSelected The new value.
     */
    public void setCanBeSelected(boolean canBeSelected) {
        this.canBeSelected = canBeSelected;
    }

    /**
     * @return True if the component can be selected, false otherwise.
     */
    public final boolean canBeSelected() {
        return canBeSelected;
    }

    /**
     * Translate the component along the x axis.
     *
     * @param x The value to be used for the translation.
     * @post If the given value produces a negative coordinate the new value of x will be 0.
     * | if (getX() + x &lt; 0)
     * |    new.getX() == 0
     * Other wise the new value of x will be the current value added with the translation value.
     * | else
     * |    new.getX() == getX() + x
     */
    public void translateX(int x) {
        setX(getX() + x < 0 ? 0 : getX() + x);
    }

    /**
     * Translate the component along the y axis.
     *
     * @param y The value to be used for the translation.
     * @post If the given value produces a negative coordinate the new value of y will be 0.
     * | if (getY() + y &lt; 0)
     * |    new.getY() == 0
     * Other wise the new value of y will be the current value added with the translation value.
     * | else
     * |    new.getY() == getY() + y
     */
    public void translateY(int y) {
        setY(getY() + y < 0 ? 0 : getY() + y);
    }

    /**
     * Change whether or not this component moves when the user scrolls.
     *
     * @param moveWhenScrolling The new value of the moveWhenScrolling flag.
     * @post The new value of the moveWhenScrolling flag is equal to the specified value.
     * | new.this.moveWhenScrolling = moveWhenScrolling
     */
    final void setMoveWhenScrolling(boolean moveWhenScrolling) {
        this.moveWhenScrolling = moveWhenScrolling;
    }

    /**
     * Get the color associated with the given attribute.
     *
     * @param key The attribute for which to get the color.
     * @return The color used when drawing an element that uses the specified attribute.
     */
    public final Color getColor(String key) {
        if (!colors.containsKey(key))
            throw new IllegalArgumentException(key + " does not correspond to a valid attribute that can be colored");
        return colors.get(key);
    }

    /**
     * Set the color used for the specified attribute.
     *
     * @param key   The attribute for which to set the color.
     * @param color The color used when drawing an element that uses the specified attribute.
     */
    public final void setColor(String key, Color color) {
        if (!colors.containsKey(key))
            throw new IllegalArgumentException(key + " does not correspond to a valid attribute that can be colored");
        colors.put(key, color);
    }

    /**
     * Deal with a user clicking on the component.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     * @effect Unselect all components.
     * | unselect()
     */
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        if (id == 500) unselect();

        if (contains(x, y)) {
            if (canBeSelected && id == 500) setSelected(true);
            mouseClickListeners.forEach(l -> l.handle(clickCount));
        }
    }

    /**
     * Deal with a user pressing a key.
     *
     * @param id      The type of key event.
     * @param keyCode The number of key that was pressed.
     * @param keyChar The character that was typed.
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar) {
    }

    /**
     * Add a new listener that is alerted when a field of an implementing class changes.
     *
     * @param listener The new listener
     */
    void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener The listener to be removed.
     */
    void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Remove a mouse click listener.
     *
     * @param mouseClickListener Listener that will be alerted when the component is clicked.
     */
    public void removeMouseClickListener(MouseClickListener mouseClickListener) {
        if (mouseClickListener == null)
            throw new IllegalArgumentException("No null elements in the mouseClickListener list");

        mouseClickListeners.remove(mouseClickListener);
    }

    /**
     * Add a new mouse click listener.
     *
     * @param mouseClickListener Listener that will no longer be alerted when the component is clicked.
     */
    public void addMouseClickListener(MouseClickListener mouseClickListener) {
        if (mouseClickListener == null)
            throw new IllegalArgumentException("Can't add a null mouseClickListener to the list of listeners");
        mouseClickListeners.add(mouseClickListener);
    }

    /**
	 * @return True if the component contains valid literal, false otherwise.
     */
    public final boolean isValid() {
        return this.valid;
    }

    /**
     * Set the valid flag for this textfield.
     *
     * @param valid The new value of the valid field.
     */
    public final void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Set the margin used when checking if the component contains a given x coordinate.
     *
     * @param hMargin The new margin.
     */
    protected final void setHMargin(int hMargin) {
        this.hMargin = hMargin;
    }

    /**
     * @return the margin used when checking if the component contains a given x coordinate.
     */
    protected final int getHMargin() {
        return hMargin;
    }

    /**
     * Set the vertical margin used when checking if the component contains a given y coordinate.
     *
     * @param vMargin The new margin.
     */
    protected final void setVMargin(int vMargin) {
        this.vMargin = vMargin;
    }

    /**
     * @return the margin used when checking if the component contains a given y coordinate.
     */
    protected final int getVMargin() {
        return vMargin;
    }

    /**
     * Set a new value for the z-index of this component. A higher index indicates that the component will
     * be drawn later than components with a lower z-index.
     *
     * @param zIndex The new index.
     * @post The new zIndex is equal to the specified z-index.
     * | new.getZIndex() == zIndex
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * @return The z-index of this component.
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * @return The parent of this component, can be null.
     */
    public Component getParent() {
        return parent;
    }

    protected void setParent(Component parent) {
        this.parent = parent;
    }

    /**
     * Compare the components based on their z-index.
     *
     * @param other The component to compare this component to.
     * @return If the z index of this component is lower than the z index of the other component the result is -1
     */
    @Override
    public int compareTo(Component other) {
        if (other == null) throw new IllegalArgumentException("A component cannot be compared to a null reference");
        return Integer.compare(this.getZIndex(), other.getZIndex());
    }

    /**
     * Draw this component on a canvas.
     *
     * @param g The graphics object on which to paint.
     */
    protected void paint(Graphics g) {
        if (!isVisible()) return;

        if (!isValid()) {
            // Draw a red border around non valid components.
            g.setColor(colors.get("Invalid"));
            g.drawRect(getDrawX() - 1, getDrawY() - 1, getWidth(), getHeight());
            g.drawRect(getDrawX(), getDrawY(), getWidth(), getHeight());
            g.setColor(colors.get("Standard"));
        }
    }
}
