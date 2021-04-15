package com.swopteam07.tablr.UI;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A class representing a container that contains other components,
 * when this component is drawn all it's children are also drawn.
 */
public abstract class Container<T extends Component> extends Component {

    /**
     * A list containing the children of this component.
     */
    final List<T> children = new ArrayList<>();

    /**
     * The deepest child that was selected the last time a user clicked in the component.
     */
    private Component selectedComponent = null;

    /**
     * The width of the content in this component.
     */
    private int contentWidth = 0;

    private int contentHeight = 0;

    /**
     * Create a new container with the given dimensions and visibility state.
     *
     * @param x       The x coordinate of the top left corner of the component relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param y       The y coordinate of the top left corner of the component.relative to the parent component if it
     *                exists or the window if it doesn't.
     * @param height  The height of the component.
     * @param width   The width of the component.
     * @param visible The visibility state of the new component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @effect The container component is initialized as a component with the given parameters.
     * | super(x, y, height, width, visible)
     * @effect The elements of the children collection will be added to the children of this component.
     * | addChildren(children)
     */
    public Container(int x, int y, int height, int width, boolean visible, Component parent) {
        super(x, y, height, width, visible, parent);
    }

    /**
     * Create a new visible container with the given dimensions and  children.
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height The height of the component.
     * @param width  The width of the component.
     * @param parent The component that contains this component or null if this component has no parent.
     * @effect The container component is initialized with the given parameters.
     * | this(x, y, height, width, visible, parent)
     */
    public Container(int x, int y, int height, int width, Component parent) {
        this(x, y, height, width, true, parent);
    }

    /**
     * Return the number of children contained in this component.
     *
     * @return The number of children in this container.
     */
    protected int getNbChildren() {
        return this.children.size();
    }

    /**
     * Get the child at the given index.
     *
     * @param index The index to get the child for.
     * @return The child at the specified index.
     * @throws IndexOutOfBoundsException | index &lt; 0 || index &gt;= getNbChildren()
     */
    protected T getChildAt(int index) {

        if (index < 0 || index >= getNbChildren())
            throw new IndexOutOfBoundsException();

        return this.children.get(index);
    }

    /**
     * Add a new child to this component.
     *
     * @param child The child to add to this component.
     * @post The child will be added to the children of this component.
     * | new.hasAsChild(child)
     * @post The number of children is incremented by 1.
     * | new.getNbChildren() == getNbChildren() + 1
     */
    protected void addChild(T child) {
        this.children.add(child);
        updateContentWidthAndHeight();
    }

    /**
     * Add a collection of components to the children of this component.
     *
     * @param children The children to add.
     * @post The elements of the children collection will be added to the children of this component.
     * | for I in 0..(children.size() + 1)
     * |   new.hasAsChild(children.get(I))
     */
    protected void addChildren(Collection<T> children) {
        this.children.addAll(children);
    }

    /**
     * Check if this component contains the given child.
     *
     * @param child The child to check for.
     * @return The given child is registered in this component.
     * | for some I in 0..(getNbChildren() - 1)
     * |   getChildAt(I) == child
     */
    protected boolean hasAsChild(T child) {
        return this.children.contains(child);
    }

    /**
     * @return All the children in this component.
     * | for I in 0..(getNbChildren() -1)
     * |   result[I] == getChildAt(I)
     */
    protected List<T> getChildren() {
        return children;
    }

    /**
     * Set the children of this component.
     *
     * @param children The new children of this component.
     * @post The new children of this component are equal to the given children.
     * | for each I in 0..(getNbChildren() - 1)
     * |    children.get(I) == new.getChildAt(I)
     * @post The number of children is equal to the length of the given number of children.
     * | new.getNbChildren() == children.length()
     */
    protected void setChildren(Collection<T> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    /**
     * Ensure that the children of this container are not selected.
     *
     * @effect Unselect each child.
     * | for each I in 0..(getNbChildren() + 1)
     * |   getChildAt(I).unselect()
     */
    @Override
    public void unselect() {
        super.unselect();
        children.forEach(Component::unselect);
    }

    /**
     * Set a new value for the canBeSelected flag
     *
     * @param canBeSelected The new value.
     */
    @Override
    public void setCanBeSelected(boolean canBeSelected) {
        super.setCanBeSelected(canBeSelected);
        if (children != null)
            children.forEach(c -> c.setCanBeSelected(canBeSelected));
    }

    /**
     * Set the new value of the allowsUnselect flag.
     *
     * @param allowsUnselect The new value.
     */
    @Override
    public void setAllowsUnselect(boolean allowsUnselect) {
        super.setAllowsUnselect(allowsUnselect);
        if (children != null)
            children.stream().filter(Component::isSelected).forEach(c -> c.setAllowsUnselect(canBeSelected()));
    }

    /**
     * Check if whether or not this containers propagates mouse/key events to children.
     *
     * @return True
     */
    protected boolean allowsPropagationToChildren() {
        return true;
    }

    /**
     * @return The deepest child that the user clicked in the last time a click was registered in this component.
     */
    public Component getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * Deal with a user click on the component.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     * @effect If a child exists that contains the point that was clicked delegate handling to that child.
     * | if (for some I in 0..(getNbChildren() + 1)
     * |       getChildAt(I).contains(x,y))
     * |   getChildAt(I).handleClickEvent(x, y, clickCount)
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        super.handleClickEvent(id, x, y, clickCount);

        if (!allowsPropagationToChildren()) return;

        // Keep a copy here, the value might change because listeners get fired before the end of this method is reached.
        boolean canBeSelectedTmp = canBeSelected();

        // Ensure that clicks get propagated to the component with the highest z index.
        Component child = children.stream().filter(c -> c.contains(x, y)).min(Collections.reverseOrder()).orElse(null);
        if (child != null) child.handleClickEvent(id, x, y, clickCount);

        // No other components can be selected when an invalid value has been entered.
        if (!canBeSelectedTmp) return;

        if (child != null)
            selectedComponent = child instanceof Container ? ((Container) child).getSelectedComponent() : child;
        else
            selectedComponent = this;
    }

    /**
     * Deal with a user pressing a key.
     *
     * @param id      The type of key event.
     * @param keyCode The number of key that was pressed.
     * @param keyChar The character that was typed.
     * @effect If the component is not selected delegate the handling to the appropriate child.
     * | if (! isSelected())
     * |   for each I in 0..(getNbChildren() + 1)
     * |       getChildAt(I).handleKeyEvent(id, keyCode, keyChar)
     */
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (isSelected() && allowsPropagationToChildren())
            children.stream().filter(Component::isSelected).forEach(child -> child.handleKeyEvent(id, keyCode, keyChar));
    }

    /**
     * Determine what the max x coordinate of the rightmost corners of the children and the max y coordinate of
     * the bottom of the children are.
     * This method should be called by resizable children when they're resized.
     */
    protected void updateContentWidthAndHeight() {
        // Scrollbars always take up all the available space, therefore they can't be taken into account
        // when determining if the scrollbars should be enabled, which this function is used for.
        contentWidth = children.stream()
                .filter(c -> !(c instanceof Scrollbar))
                .mapToInt(o -> o.getX() + o.getWidth())
                .max()
                .orElse(0);

        contentHeight = children.stream()
                .filter(c -> !(c instanceof Scrollbar))
                .mapToInt(o -> o.getY() + o.getHeight())
                .max()
                .orElse(0);
    }

    /**
     * @return The width of the components in this container.
     */
    public final int getContentWidth() {
        return contentWidth;
    }

    /**
     * @return The height of the components in this container.
     */
    public final int getContentHeight() {
        return contentHeight;
    }

    /**
     * Check if a child of this container is selected.
     *
     * @return True if any of the children of the container are selected.
     */
    public boolean childIsSelected() {
        return children.stream().anyMatch(child -> {
            if (child instanceof Container)
                return child.isSelected() || ((Container) child).childIsSelected();
            else
                return child.isSelected();
        });
    }

    /**
     * Paint the container component and all it's children.
     *
     * @param g The graphics object to paint on.
     */
    @Override
    protected void paint(Graphics g) {
        Collections.sort(this.children);
        children.forEach(child -> child.paint(g));
    }
}
