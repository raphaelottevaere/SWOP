package com.swopteam07.tablr.UI;

import java.util.Arrays;

/**
 * A UI component that circles through a collection of values when clicked.
 */
public class OptionLabel extends Label {

    /**
     * The options through which the component cycles when a user clicks the component.
     */
    private final String[] options;

    /**
     * The index of the option that is currently shown.
     */
    private int index;

    /**
     * Create a new Option Label
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height   The height of the component.
     * @param width    The width of the component.
     * @param visible  The visibility state of the new component.
     * @param selected The option that should be selected when the component is first shown to the user.
     * @param options  All the options that will be displayed on the component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @throws IllegalArgumentException Thrown when the options array is empty.
     * @throws IllegalArgumentException Thrown when the selected option is not contained within the options array.
     * @effect The OptionLabel is initialized as a label with the given parameters.
     * | super(x, y, height, width, visible, selected, parent)
     */
    public OptionLabel(int x, int y, int height, int width, boolean visible, String selected, String[] options, Component parent) {
        super(x, y, height, width, visible, selected, parent);

        if (options.length == 0)
            throw new IllegalArgumentException("The options need to contains at least 1 option.");

        index = Arrays.asList(options).indexOf(selected);

        if (index == -1)
            throw new IllegalArgumentException("The selected text is not contained within the options");

        this.options = options;
    }

    /**
     * Create a new visible Option Label
     *
     * @param x      The x coordinate of the top left corner of the component relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param y      The y coordinate of the top left corner of the component.relative to the parent component if it
     *               exists or the window if it doesn't.
     * @param height   The height of the component.
     * @param width    The width of the component.
     * @param selected The option that should be selected when the component is first shown to the user.
     * @param options  All the options that will be displayed on the component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @throws IllegalArgumentException Thrown when the options array is empty.
     * @throws IllegalArgumentException Thrown when the selected option is not contained within the options array.
     * @effect The OptionLabel is initialized with the given parameters and true for the visible parameter.
     * | this(x, y, height, width, true, selected, parent)
     */
    public OptionLabel(int x, int y, int height, int width, String selected, String[] options, Component parent) {
        this(x, y, height, width, true, selected, options, parent);
    }

    /**
     * Create a new visible Option Label with a 0 surface area.
     *
     * @param selected The option that should be selected when the component is first shown to the user.
     * @param options  All the options that will be displayed on the component.
     * @param parent  The component that contains this component or null if this component has no parent.
     * @throws IllegalArgumentException Thrown when the options array is empty.
     * @throws IllegalArgumentException Thrown when the selected option is not contained within the options array.
     * @effect The OptionLabel is initialized with 0 for the area parameters.
     * | this(0, 0, 0, 0, true, selected, parent)
     */
    public OptionLabel(String selected, String[] options, Component parent) {
        this(0, 0, 0, 0, selected, options, parent);
    }

    /**
     * Deal with a user clicking the component. The next option will be shown.
     *
     * @param id         The type of mouse event.
     * @param x          The x coordinate of the mouse click.
     * @param y          The y coordinate of the mouse click.
     * @param clickCount The number of times that was clicked.
     */
    @Override
    public void handleClickEvent(int id, int x, int y, int clickCount) {
        if (id != 500) // Only respond to click events.
            return;

        super.handleClickEvent(id, x, y, clickCount);

        if (isSelected()) {
            index = (index + 1) % options.length;
            setText(options[index]);
        }

    }

    /**
     * Set the selected option for this option label and alert any observers to the change.
     *
     * @param text The new text for this label.
     */
    @Override
    public void setText(String text) {
        String old = getText();
        super.setText(text);

        if (support != null)
            support.firePropertyChange("text", old, text);
    }
}
